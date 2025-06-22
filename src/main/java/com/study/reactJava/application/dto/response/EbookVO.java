package com.study.reactJava.application.dto.response;

import java.time.LocalDateTime;

public record EbookVO(
        String id,
        String code,
        String name,
        LocalDateTime createTime,
        LocalDateTime updateTime) {
}
