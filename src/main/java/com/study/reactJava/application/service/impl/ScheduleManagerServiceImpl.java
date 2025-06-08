package com.study.reactJava.application.service.impl;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.map.MapUtil;
import com.study.reactJava.domain.entity.ScheduledNotificationEntity;
import com.study.reactJava.domain.repository.ScheduledNotificationRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.SchedulingConfigurer;
import org.springframework.scheduling.config.CronTask;
import org.springframework.scheduling.config.ScheduledTaskRegistrar;
import org.springframework.scheduling.support.CronTrigger;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDateTime;
import java.util.*;

@Slf4j
@Service
@RequiredArgsConstructor
public class ScheduleManagerServiceImpl implements SchedulingConfigurer {

    private final Map<Long, CustomerCronTask> cronTaskMap = new HashMap<>();

    private final ScheduledNotificationRepository scheduledNotificationRepository;

    private final RestTemplate restTemplate;

    @Value("${ntfy.url}")
    private String ntfyUrl;

    private ScheduledTaskRegistrar taskRegistrar;

    public void configNotification() {
        List<ScheduledNotificationEntity> scheduledEntityList;
        if (MapUtil.isEmpty(cronTaskMap)) {
            scheduledEntityList = scheduledNotificationRepository.findByValid("1");
        } else {
            LocalDateTime minusTime = LocalDateTime.now().minusMinutes(5).minusSeconds(30);
            scheduledEntityList = scheduledNotificationRepository.findByUpdateTimeAfter(minusTime);
        }

        if (CollectionUtil.isEmpty(scheduledEntityList)) {
            return;
        }

        List<CronTask> cronTaskList = taskRegistrar.getCronTaskList();

        for (ScheduledNotificationEntity scheduledEntity : scheduledEntityList) {
            long id = scheduledEntity.getId();
            boolean update = false;
            // 如果存在则更新
            if (cronTaskMap.containsKey(id)) {
                CustomerCronTask cronTask = cronTaskMap.get(id);
                List<CronTask> cronTaskArrayList = new ArrayList<>();
                for (CronTask task : cronTaskList) {
                    if (cronTask == task) {
                        if (Objects.equals(scheduledEntity.getValid(), "1")
                                && !Objects.equals(cronTask.getContent(), scheduledEntity.getContent())) {
                            update = true;
                            continue;
                        } else {
                            update = true;
                            continue;
                        }
                    }
                    cronTaskArrayList.add(task);
                }
                if (update) {
//                    taskRegistrar.setCronTasksList(cronTaskArrayList);
                }
            } else if (Objects.equals(scheduledEntity.getValid(), "1")) {
                CustomerCronTask cronTask = new CustomerCronTask(() -> {
                    HttpHeaders httpHeaders = new HttpHeaders();
                    httpHeaders.add("Title", "通知");
                    httpHeaders.add("Content-type", "application/json; charset=utf-8");
                    HttpEntity<String> httpEntity = new HttpEntity<>(scheduledEntity.getContent(), httpHeaders);
                    try {
                        ResponseEntity<String> stringResponseEntity = restTemplate.postForEntity(ntfyUrl, httpEntity, String.class);
                        log.info("收到：{}", stringResponseEntity.getBody());
                    } catch (Exception e) {
                        log.error(e.getMessage());
                    }
                }, scheduledEntity.getExpression(), scheduledEntity.getContent());
                cronTaskMap.put(scheduledEntity.getId(), cronTask);
                taskRegistrar.addCronTask(cronTask);
            }
        }
    }

    @Override
    public void configureTasks(ScheduledTaskRegistrar taskRegistrar) {
        this.taskRegistrar = taskRegistrar;
    }

    class CustomerCronTask extends CronTask {

        public String getContent() {
            return content;
        }

        private String content;


        public CustomerCronTask(Runnable runnable, String expression) {
            super(runnable, expression);
        }

        public CustomerCronTask(Runnable runnable, String expression, String content) {
            super(runnable, expression);
            this.content = content;
        }

        public CustomerCronTask(Runnable runnable, CronTrigger cronTrigger) {
            super(runnable, cronTrigger);
        }

        public CustomerCronTask(Runnable runnable, CronTrigger cronTrigger, String content) {
            super(runnable, cronTrigger);
            this.content = content;
        }
    }
}