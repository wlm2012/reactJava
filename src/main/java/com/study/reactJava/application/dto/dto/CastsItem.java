package com.study.reactJava.application.dto.dto;

import java.time.LocalDate;

public record CastsItem(
	LocalDate date,
	String dayweather,
	String daywind,
	String week,
	String daypower,
	String daytemp,
	String nightwind,
	String nighttemp,
	String daytempFloat,
	String nighttempFloat,
	String nightweather,
	String nightpower
) {
}
