package com.study.reactJava.application.service.impl;

import cn.hutool.core.collection.CollectionUtil;
import com.study.reactJava.application.dto.dto.CastsItem;
import com.study.reactJava.application.dto.dto.ForecastsItem;
import com.study.reactJava.application.dto.dto.WeatherDTO;
import com.study.reactJava.application.service.PushService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDate;
import java.util.List;
import java.util.Objects;

@Slf4j
@Service
@RequiredArgsConstructor
public class WeatherServiceImpl implements PushService {

    @Value("${amap.key}")
    private String apiKey;

    @Value("${ntfy.url}")
    private String ntfyUrl;

    private final RestTemplate restTemplate;

    public void push() {
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add("Title", "天气");
        httpHeaders.add("Content-type", "application/json; charset=utf-8");


        ResponseEntity<WeatherDTO> weatherDTOResponseEntity
                = restTemplate.getForEntity("https://restapi.amap.com/v3/weather/weatherInfo?key=" + apiKey + "&city=330108&extensions=all", WeatherDTO.class);

        String message;
        WeatherDTO body = weatherDTOResponseEntity.getBody();
        if (body == null || CollectionUtil.isEmpty(body.forecasts()) || CollectionUtil.isEmpty(body.forecasts().getFirst().casts())) {
            message = "获取天气失败";
        } else {
            List<ForecastsItem> forecasts = body.forecasts();
            ForecastsItem forecastsItem = forecasts.getFirst();
            List<CastsItem> casts = forecastsItem.casts();
            String toDayMessage = """
                    今天：
                    白天：%s，晚上：%s
                    白天气温：%s，晚上气温：%s
                    """;
            String tomorrowMessage = """
                    明天：
                    白天：%s，晚上：%s
                    白天气温：%s，晚上气温：%s
                    """;
            for (CastsItem cast : casts) {
                if (Objects.equals(LocalDate.now(), cast.date())) {
                    toDayMessage = String.format(toDayMessage, cast.dayweather(), cast.nightweather(), cast.daytemp(), cast.nighttemp());
                }
                if (Objects.equals(LocalDate.now().plusDays(1), cast.date())) {
                    tomorrowMessage = String.format(tomorrowMessage, cast.dayweather(), cast.nightweather(), cast.daytemp(), cast.nighttemp());
                }
            }

            message = toDayMessage + tomorrowMessage;
        }
        HttpEntity<String> httpEntity = new HttpEntity<>(message, httpHeaders);
        restTemplate.postForEntity(ntfyUrl, httpEntity, String.class);
    }
}
