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
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;
import org.springframework.scheduling.support.CronTrigger;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ScheduledFuture;

@Slf4j
@Service
@RequiredArgsConstructor
public class SchedulingTaskManageServiceImpl {

    private final ThreadPoolTaskScheduler threadPoolTaskScheduler;

    private final ScheduledNotificationRepository notificationRepository;

    private final RestTemplate restTemplate;

    @Value("${ntfy.url}")
    private String ntfyUrl;

    private final Map<Long, ScheduledFuture<?>> cacheMap = new HashMap<>();

    /**
     * 删除定时任务
     */
    private void stopSchedulingTask(Long key) {
        if (!cacheMap.containsKey(key)) {
            log.info(".......当前key【{}】没有定时任务......", key);
            return;
        }
        ScheduledFuture<?> future = cacheMap.get(key);
        if (Objects.nonNull(future)) {
            //关闭当前定时任务
            future.cancel(Boolean.TRUE);
            cacheMap.remove(key);
            log.info("删除【{}}】对应定时任务成功", key);
        } else {
            log.info("删除任务{}，失败", key);
        }
    }

    /**
     * 创建定时任务
     *
     * @param key      任务key
     * @param runnable 当前线程
     * @param cron     定时任务cron
     */
    private void createSchedulingTask(Long key, Runnable runnable, String cron) {
        ScheduledFuture<?> schedule = threadPoolTaskScheduler.schedule(runnable, new CronTrigger(cron));
        if (Objects.isNull(schedule)) {
            log.info("创建任务失败，{}", key);
            return;
        }
        cacheMap.put(key, schedule);
        log.info("【{}】创建定时任务成功", key);
    }

    public void configNotification() {
        List<ScheduledNotificationEntity> scheduledEntityList;
        if (MapUtil.isEmpty(cacheMap)) {
            scheduledEntityList = notificationRepository.findByValid("1");
        } else {
            LocalDateTime minusTime = LocalDateTime.now().minusMinutes(5).minusSeconds(30);
            scheduledEntityList = notificationRepository.findByUpdateTimeAfter(minusTime);
        }

        if (CollectionUtil.isEmpty(scheduledEntityList)) {
            return;
        }

        for (ScheduledNotificationEntity scheduledEntity : scheduledEntityList) {
            if (Objects.equals(scheduledEntity.getValid(), "1")) {
                if (cacheMap.containsKey(scheduledEntity.getId())) {
                    stopSchedulingTask(scheduledEntity.getId());
                }
                createSchedulingTask(scheduledEntity.getId(), () -> {
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
                }, scheduledEntity.getExpression());
            } else if (cacheMap.containsKey(scheduledEntity.getId())) {
                stopSchedulingTask(scheduledEntity.getId());
            }

        }
    }

}
