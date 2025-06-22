package com.study.reactJava.application.dto.dto;

import java.util.List;

public record HeFengAirqualityDTO(
        List<IndexesItem> indexes,
        List<PollutantsItem> pollutants
) {
}