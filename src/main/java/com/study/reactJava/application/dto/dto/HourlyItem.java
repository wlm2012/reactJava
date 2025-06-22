package com.study.reactJava.application.dto.dto;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.time.LocalDateTime;

public record HourlyItem(
	String temp,
	String icon,
	String wind360,
	String windDir,
	String pressure,
	// 2025-06-22T12:00+00:00
	@JsonFormat(pattern = "yyyy-MM-dd'T'HH:mmXXX")
	LocalDateTime fxTime,
	String cloud,
	String precip,
	String dew,
	String humidity,
	String text,
	String windSpeed,
	String windScale
) {
}
