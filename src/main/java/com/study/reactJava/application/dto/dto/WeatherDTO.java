package com.study.reactJava.application.dto.dto;

import java.util.List;

public record WeatherDTO(
	String count,
	String infocode,
	String status,
	String info,
	List<ForecastsItem> forecasts
) {
}