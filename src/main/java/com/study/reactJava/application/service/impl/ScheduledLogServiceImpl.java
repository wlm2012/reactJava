package com.study.reactJava.application.service.impl;

import com.study.reactJava.domain.entity.ScheduledLogEntity;
import com.study.reactJava.domain.repository.ScheduledLogRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Slf4j
@Service
@RequiredArgsConstructor
public class ScheduledLogServiceImpl {

    private final ScheduledLogRepository scheduledLogRepository;

    public void saveLog(String event) {
        scheduledLogRepository.save(ScheduledLogEntity.builder()
                .event(event)
                .time(LocalDateTime.now())
                .build());
    }
}
