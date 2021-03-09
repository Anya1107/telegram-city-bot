package com.telegram.bot.service.exception;

public class InvalidNameException extends RuntimeException{
    private String name;
    private String method;

    public InvalidNameException() {
        super();
    }

    public InvalidNameException(String message, String name, String method) {
        super(message);
        this.name = name;
        this.method = method;
    }

    public InvalidNameException(String message, Throwable cause) {
        super(message, cause);
    }

    public InvalidNameException(Throwable cause) {
        super(cause);
    }

    protected InvalidNameException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }

    public String getName() {
        return name;
    }

    public String getMethod() {
        return method;
    }
}