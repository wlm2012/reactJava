package com.study.reactJava.application.dto.dto;

import java.util.List;

public record HeFengWeatherDTO(
	String fxLink,
	String code,
	String updateTime,
	List<HourlyItem> hourly
) {
}