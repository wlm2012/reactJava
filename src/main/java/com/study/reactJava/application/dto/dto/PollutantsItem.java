package com.study.reactJava.application.dto.dto;

import java.util.List;

public record PollutantsItem(
	String code,
	List<SubIndexesItem> subIndexes,
	String name,
	String fullName,
	Concentration concentration
) {
}