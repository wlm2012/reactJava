package com.study.reactJava.application.dto.dto;

import java.util.List;

public record ForecastsItem(
	String province,
	List<CastsItem> casts,
	String city,
	String adcode,
	String reporttime
) {
}