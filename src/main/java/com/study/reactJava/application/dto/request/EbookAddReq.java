package com.study.reactJava.application.dto.request;

import java.time.LocalDateTime;

public record EbookAddReq(
        String code,
        String name,
        LocalDateTime createTime,
        LocalDateTime updateTime
) {
}
