package com.study.reactJava.adapter.controller;

import com.study.reactJava.application.service.impl.AirQualityServiceImpl;
import com.study.reactJava.application.service.impl.CurrencyPushServiceImpl;
import com.study.reactJava.application.service.impl.SchedulingTaskManageServiceImpl;
import com.study.reactJava.application.service.impl.WeatherServiceImpl;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/push")
@RequiredArgsConstructor
public class PushController {

    private final CurrencyPushServiceImpl currencyPushService;

    private final WeatherServiceImpl weatherService;

    private final SchedulingTaskManageServiceImpl schedulingTaskManageService;

    private final AirQualityServiceImpl airQualityService;

    @GetMapping("/pushCurrency")
    public void pushCurrency() {
        currencyPushService.push();
    }

    @GetMapping("/pushWeather")
    public void pushWeather() {
        weatherService.push();
    }

    @PostMapping("/creatTask")
    public void createTask() {
//        schedulingTaskManageService.createSchedulingTask();
    }

    @GetMapping("/getAirquality")
    public void getAirquality() {
        airQualityService.pushAirQuality();
    }

    @GetMapping("/pushWeather1")
    public void pushWeather1() {
        airQualityService.pushWeather();
    }


}
