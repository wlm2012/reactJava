package com.study.reactJava.application.service.impl;

import com.study.reactJava.domain.entity.ScheduledConfigEntity;
import com.study.reactJava.domain.entity.ScheduledLogEntity;
import com.study.reactJava.domain.repository.ScheduledConfigRepository;
import com.study.reactJava.domain.repository.ScheduledLogRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.time.LocalDateTime;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class ScheduledServiceImpl {

    private final ScheduledConfigRepository scheduledConfigRepository;

    private final ScheduledLogRepository scheduledLogRepository;

    public boolean checkNeedPush(String event) {
        if (!StringUtils.hasText(event)) {
            return true;
        }

        Optional<ScheduledConfigEntity> scheduledConfigEntityOptional = scheduledConfigRepository.findByEvent(event);
        if (scheduledConfigEntityOptional.isEmpty()) {
            return true;
        }

        Optional<ScheduledLogEntity> scheduledLogEntityOptional = scheduledLogRepository.findTopByEventOrderByTimeDesc(event);
        if (scheduledLogEntityOptional.isEmpty()) {
            return true;
        }
        ScheduledConfigEntity scheduledConfigEntity = scheduledConfigEntityOptional.get();
        ScheduledLogEntity scheduledLogEntity = scheduledLogEntityOptional.get();

        LocalDateTime plusMinutes = scheduledLogEntity.getTime().plusMinutes(scheduledConfigEntity.getTime());

        return plusMinutes.isBefore(LocalDateTime.now());
    }
}
