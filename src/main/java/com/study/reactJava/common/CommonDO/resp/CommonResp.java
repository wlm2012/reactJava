package com.study.reactJava.common.CommonDO.resp;

import lombok.Data;

@Data
public class CommonResp<T> {

    private int code;

    private String message;

    private T content;

    public static <T> CommonResp<T> success(T content) {
        CommonResp<T> commonResp = new CommonResp<>();
        commonResp.setCode(200);
        commonResp.setMessage("success");
        commonResp.setContent(content);
        return commonResp;
    }

    public static <T> CommonResp<T> fail(String message) {
        CommonResp<T> commonResp = new CommonResp<>();
        commonResp.setCode(500);
        commonResp.setMessage(message);
        return commonResp;
    }
}