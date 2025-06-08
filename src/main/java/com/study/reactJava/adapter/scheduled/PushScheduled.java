package com.study.reactJava.adapter.scheduled;


import com.study.reactJava.application.service.PushService;
import com.study.reactJava.application.service.impl.*;
import com.study.reactJava.domain.entity.ScheduledErrorLogEntity;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

@Slf4j
@Component
@RequiredArgsConstructor
public class PushScheduled {

    private final Map<String, PushService> pushServiceMap;

    private final CurrencyPushServiceImpl currencyPushService;

    private final WeatherServiceImpl weatherService;

    private final ScheduledServiceImpl scheduledService;

    private final ScheduledErrorLogServiceImpl scheduledErrorLogService;

    private final SchedulingTaskManageServiceImpl schedulingTaskManageService;


    @Scheduled(cron = "0 0 9 * * ? ")
    public void pushCurrency() {
        try {
            currencyPushService.push();
        } catch (Exception e) {
            log.error("错误", e);
            scheduledErrorLogService.saveErrorLog(pushServiceMap, currencyPushService);
        }
    }

    @Scheduled(cron = "0 5 8,11,18 * * ? ")
    public void pushWeather() {
        try {
            weatherService.push();
        } catch (Exception e) {
            log.error("错误", e);
            scheduledErrorLogService.saveErrorLog(pushServiceMap, weatherService);
        }
    }

    @Scheduled(cron = "0 0/5 * * * ?")
    public void pushAgain() {
        List<ScheduledErrorLogEntity> scheduledErrorLogEntityList = scheduledService.queryErrorLog();
        for (ScheduledErrorLogEntity scheduledErrorLogEntity : scheduledErrorLogEntityList) {
            try {
                pushServiceMap.get(scheduledErrorLogEntity.getEvent()).push();
                scheduledErrorLogService.successLog(scheduledErrorLogEntity);
            } catch (Exception e) {
                scheduledErrorLogService.plusPushTime(scheduledErrorLogEntity);
            }
        }
    }

    @Scheduled(cron = "0 0/1 * * * ?")
    public void checkScheduledNotification() {
        schedulingTaskManageService.configNotification();
    }

}
