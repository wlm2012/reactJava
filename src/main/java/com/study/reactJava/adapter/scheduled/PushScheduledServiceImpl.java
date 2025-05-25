package com.study.reactJava.adapter.scheduled;


import com.study.reactJava.application.service.impl.CurrencyPushServiceImpl;
import com.study.reactJava.application.service.impl.ScheduledLogServiceImpl;
import com.study.reactJava.application.service.impl.ScheduledServiceImpl;
import com.study.reactJava.application.service.impl.WeatherServiceImpl;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class PushScheduledServiceImpl {


    private final CurrencyPushServiceImpl currencyPushService;

    private final WeatherServiceImpl weatherService;

    private final ScheduledServiceImpl scheduledService;

    private final ScheduledLogServiceImpl scheduledLogService;


    @Scheduled(cron = "0 0,10,20 9 * * ? ")
    public void pushCurrency() {

        if (scheduledService.checkNeedPush("currencyPush")) {
            currencyPushService.pushCurrency();
            scheduledLogService.saveLog("currencyPush");
        }
    }

    @Scheduled(cron = "0 0,10,20 8,11,18 * * ? ")
    public void pushWeather() {
        if (scheduledService.checkNeedPush("weatherPush")) {
            weatherService.pushWeather();
            scheduledLogService.saveLog("weatherPush");
        }
    }
}
