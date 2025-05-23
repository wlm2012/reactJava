package com.study.reactJava.application.service.impl;

import com.study.reactJava.application.dto.dto.CurrencyDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Slf4j
@Service
@RequiredArgsConstructor
public class CurrencyPushServiceImpl {

    @Value("${ntfy.url}")
    private String ntfyUrl;

    private final RestTemplate restTemplate;

    public void pushCurrency() {

        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add("Title", "汇率");
        httpHeaders.add("Content-type", "application/json; charset=utf-8");

        ResponseEntity<CurrencyDTO> currencyEntity
                = restTemplate.getForEntity("https://latest.currency-api.pages.dev/v1/currencies/usd.json", CurrencyDTO.class);

        String message = """
                日期：%s
                美元汇率为：%s
                """;
        CurrencyDTO body = currencyEntity.getBody();
        if (body == null) {
            message = "获取汇率失败";
        } else {
            message = String.format(message, body.date(), body.usd().cny());
        }
        HttpEntity<String> httpEntity = new HttpEntity<>(message, httpHeaders);
        restTemplate.postForEntity(ntfyUrl, httpEntity, String.class);
    }
}
