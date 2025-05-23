package com.study.reactJava.application.dto.dto;


import java.math.BigDecimal;
import java.time.LocalDate;

public record CurrencyDTO(
        LocalDate date,
        CurrencyUsd usd) {


    public record CurrencyUsd(
            BigDecimal cny
    ) {
    }
}
