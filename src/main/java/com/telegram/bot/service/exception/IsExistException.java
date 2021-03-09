package com.telegram.bot.service.exception;

public class IsExistException extends RuntimeException{
    private String value;
    private String method;


    public IsExistException() {
        super();
    }

    public IsExistException(String message, String value, String method) {
        super(message);
        this.value = value;
        this.method = method;
    }

    public IsExistException(String message, Throwable cause) {
        super(message, cause);
    }

    public IsExistException(Throwable cause) {
        super(cause);
    }

    protected IsExistException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }

    public String getValue() {
        return value;
    }

    public String getMethod() {
        return method;
    }
}