package com.study.reactJava.application.dto.dto;

public record IndexesItem(
	String code,
	Color color,
	String level,
	String name,
	int aqi,
	String aqiDisplay,
	Object primaryPollutant,
	Health health,
	String category
) {
}
