package com.study.reactJava.common.CommonDO.exception;

public final class ServiceException extends RuntimeException {

    private final String message;

    public ServiceException(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}
