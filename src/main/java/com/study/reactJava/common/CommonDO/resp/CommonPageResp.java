package com.study.reactJava.CommonDO.resp;

import lombok.Data;
import org.springframework.data.domain.Page;

@Data
public class CommonPageResp<T> {

    private int code;

    private long total;

    private String message;

    private Iterable<T> content;


    public static <T> CommonPageResp<T> success(Page<T> content) {
        CommonPageResp<T> commonPageResp = new CommonPageResp<>();
        commonPageResp.setCode(200);
        commonPageResp.setMessage("success");
        commonPageResp.setTotal(content.getTotalElements());
        commonPageResp.setContent(content);
        return commonPageResp;
    }
}