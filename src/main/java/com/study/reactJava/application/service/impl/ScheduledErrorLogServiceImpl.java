package com.study.reactJava.application.service.impl;

import com.study.reactJava.application.service.PushService;
import com.study.reactJava.domain.entity.ScheduledErrorLogEntity;
import com.study.reactJava.domain.repository.ScheduledErrorLogRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.Objects;

@Slf4j
@Service
@RequiredArgsConstructor
public class ScheduledErrorLogServiceImpl {

    private final ScheduledErrorLogRepository scheduledErrorLogRepository;

    public void saveLog(String event) {
        scheduledErrorLogRepository.save(ScheduledErrorLogEntity.builder()
                .event(event)
                .pushSuccess("0")
                .pushTime(0)
                .build());
    }

    public void saveErrorLog(Map<String, PushService> pushServiceMap, PushService pushService) {
        pushServiceMap.forEach((key, value) -> {
            if (Objects.equals(value, pushService)) {
                saveLog(key);
            }
        });
    }

    public void plusPushTime(ScheduledErrorLogEntity scheduledErrorLogEntity) {
        scheduledErrorLogEntity.setPushTime(scheduledErrorLogEntity.getPushTime() + 1);
        scheduledErrorLogRepository.save(scheduledErrorLogEntity);
    }

    public void successLog(ScheduledErrorLogEntity scheduledErrorLogEntity) {
        scheduledErrorLogEntity.setPushSuccess("1");
        scheduledErrorLogRepository.save(scheduledErrorLogEntity);
    }
}
