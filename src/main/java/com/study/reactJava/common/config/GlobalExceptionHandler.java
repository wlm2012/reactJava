package com.study.reactJava.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.study.reactJava.CommonDO.exception.ServiceException;
import com.study.reactJava.CommonDO.resp.CommonPageResp;
import com.study.reactJava.CommonDO.resp.CommonResp;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.MethodParameter;
import org.springframework.data.domain.Page;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;




@Slf4j
@RestControllerAdvice
@RequiredArgsConstructor
public class GlobalExceptionHandler implements ResponseBodyAdvice<Object> {


    private final ObjectMapper objectMapper;

    @Override
    public boolean supports(MethodParameter returnType, Class<? extends HttpMessageConverter<?>> converterType) {
        return true;
    }

    @Override
    @SneakyThrows
    public Object beforeBodyWrite(Object body, MethodParameter returnType, MediaType selectedContentType,
                                  Class<? extends HttpMessageConverter<?>> selectedConverterType,
                                  ServerHttpRequest request, ServerHttpResponse response) {
        if (body instanceof String) {
            return objectMapper.writeValueAsString(CommonResp.success(body));
        }

        if (body instanceof Page<?>) {
            return CommonPageResp.success((Page<?>) body);
        }

        if (body instanceof CommonResp<?>) {
            return body;
        }

        return CommonResp.success(body);

    }

    @ExceptionHandler(ServiceException.class)
    public CommonResp<?> handleServiceException(ServiceException e) {
        log.error(e.getMessage(), e);
        return CommonResp.fail(e.getMessage());
    }


    @ExceptionHandler(Exception.class)
    public CommonResp<String> exception(Exception e, HttpServletRequest request) {
        String requestURI = request.getRequestURI();
        log.error("请求地址'{}',发生未知异常.", requestURI, e);
        return CommonResp.fail(e.getMessage());
    }


}