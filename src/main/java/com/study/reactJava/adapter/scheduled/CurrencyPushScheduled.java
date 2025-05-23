package com.study.reactJava.adapter.scheduled;


import com.study.reactJava.application.service.impl.CurrencyPushServiceImpl;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class CurrencyPushScheduled {


    private final CurrencyPushServiceImpl currencyPushService;

    @Scheduled(cron = "* * 9 * * ? ")
    public void pushCurrency() {
        currencyPushService.pushCurrency();
    }
}
