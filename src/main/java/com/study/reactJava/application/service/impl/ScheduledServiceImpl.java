package com.study.reactJava.application.service.impl;

import com.study.reactJava.domain.entity.ScheduledErrorLogEntity;
import com.study.reactJava.domain.repository.ScheduledErrorLogRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class ScheduledServiceImpl {

    private final ScheduledErrorLogRepository scheduledErrorLogRepository;


    public List<ScheduledErrorLogEntity> queryErrorLog() {
        return scheduledErrorLogRepository.findByPushSuccessAndPushTimeLessThan("0", 3);
    }
}
