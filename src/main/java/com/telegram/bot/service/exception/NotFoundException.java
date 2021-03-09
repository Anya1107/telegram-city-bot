package com.telegram.bot.service.exception;

public class NotFoundException extends RuntimeException{
    private String value;
    private String method;

    public NotFoundException() {
    }

    public NotFoundException(String message, String value, String method) {
        super(message);
        this.value = value;
        this.method = method;
    }

    public NotFoundException(String message, Throwable cause) {
        super(message, cause);
    }

    public NotFoundException(Throwable cause) {
        super(cause);
    }

    public NotFoundException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }

    public String getValue() {
        return value;
    }

    public String getMethod() {
        return method;
    }
}