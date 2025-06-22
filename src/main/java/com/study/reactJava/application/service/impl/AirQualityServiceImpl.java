package com.study.reactJava.application.service.impl;

import cn.hutool.core.collection.CollectionUtil;
import com.study.reactJava.application.dto.dto.*;
import com.study.reactJava.infrastructure.utils.HeFengJwtUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class AirQualityServiceImpl {

    @Value("${hefeng.privateKey}")
    private String privateKey;

    @Value("${hefeng.kid}")
    private String kid;

    @Value("${hefeng.sub}")
    private String sub;

    @Value("${ntfy.url}")
    private String ntfyUrl;

    private final RestTemplate httpRestTemplate;

    private final RestTemplate restTemplate;

    public void pushAirQuality() {

        HttpEntity<HeFengAirqualityDTO> request = getHeFengAirqualityDTOHttpEntity();

        ResponseEntity<HeFengAirqualityDTO> airqualityDTOResponseEntity
                = httpRestTemplate.exchange("https://pf3tefwqtc.re.qweatherapi.com/airquality/v1/current/30.20/120.21",
                HttpMethod.GET,
                request,
                HeFengAirqualityDTO.class);

        String message;
        HeFengAirqualityDTO body = airqualityDTOResponseEntity.getBody();
        if (body == null || CollectionUtil.isEmpty(body.indexes())
                || CollectionUtil.isEmpty(body.pollutants())) {
            message = "获取空气质量失败";
        } else {
            List<IndexesItem> indexes = body.indexes();
            IndexesItem indexesItem = indexes.getFirst();
            int aqi = indexesItem.aqi();
            Health health = indexesItem.health();

            message = """
                    空气质量：%s
                    对人体影响：%s
                    一般人的指导：%s
                    敏感人士的指导：%s""";

            message = String.format(message, aqi, health.effect(), health.advice().generalPopulation(), health.advice().sensitivePopulation());

            for (PollutantsItem pollutant : body.pollutants()) {
                message = message + "\n" + pollutant.name() + "：" + pollutant.subIndexes().getFirst().aqi();
            }
        }

        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add("Title", "空气质量");
        httpHeaders.add("Content-type", "application/json; charset=utf-8");

        HttpEntity<String> httpEntity = new HttpEntity<>(message, httpHeaders);
        restTemplate.postForEntity(ntfyUrl, httpEntity, String.class);
    }

    /**
     * 获取带token的请求
     */
    private HttpEntity<HeFengAirqualityDTO> getHeFengAirqualityDTOHttpEntity() {
        String jwt = HeFengJwtUtil.getJwt(privateKey, kid, sub);
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Bearer " + jwt);
        headers.setContentType(MediaType.APPLICATION_JSON);
        return new HttpEntity<>(headers);
    }

    public void pushWeather() {
        HttpEntity<HeFengAirqualityDTO> request = getHeFengAirqualityDTOHttpEntity();

        ResponseEntity<HeFengWeatherDTO> weatherDTOResponseEntity
                = httpRestTemplate.exchange("https://pf3tefwqtc.re.qweatherapi.com/v7/grid-weather/24h?location=120.21,30.20",
                HttpMethod.GET,
                request,
                HeFengWeatherDTO.class);


        String message = "";
        HeFengWeatherDTO body = weatherDTOResponseEntity.getBody();
        if (body == null || CollectionUtil.isEmpty(body.hourly())) {
            message = "获取天气预报失败";
        } else {
            for (HourlyItem hourlyItem : body.hourly()) {
                LocalDate now = LocalDate.now();
                LocalDateTime eight = LocalDateTime.of(now.getYear(), now.getMonth(), now.getDayOfMonth(), 8, 0, 0);
                LocalDateTime twelve = LocalDateTime.of(now.getYear(), now.getMonth(), now.getDayOfMonth(), 12, 0, 0);
                LocalDateTime eighteen = LocalDateTime.of(now.getYear(), now.getMonth(), now.getDayOfMonth(), 18, 0, 0);
                LocalDateTime twenty = LocalDateTime.of(now.getYear(), now.getMonth(), now.getDayOfMonth(), 20, 0, 0);

                ArrayList<LocalDateTime> localDateTimes = new ArrayList<>();
                localDateTimes.add(eight);
                localDateTimes.add(twelve);
                localDateTimes.add(eighteen);
                localDateTimes.add(twenty);

                if (localDateTimes.contains(hourlyItem.fxTime())) {
                    String m = """
                            %s
                            温度：%s
                            %s
                            """;
                    m = String.format(m, hourlyItem.fxTime(), hourlyItem.temp(), hourlyItem.text());
                    message += m;
                }
            }
        }

        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add("Title", "天气");
        httpHeaders.add("Content-type", "application/json; charset=utf-8");

        HttpEntity<String> httpEntity = new HttpEntity<>(message, httpHeaders);
        restTemplate.postForEntity(ntfyUrl, httpEntity, String.class);
    }
}
