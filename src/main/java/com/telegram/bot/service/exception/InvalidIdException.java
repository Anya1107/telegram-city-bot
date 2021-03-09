package com.telegram.bot.service.exception;

public class InvalidIdException extends RuntimeException{
    private int id;
    private String method;

    public InvalidIdException() {
        super();
    }

    public InvalidIdException(String message, int id, String method) {
        super(message);
        this.id = id;
        this.method = method;
    }

    public InvalidIdException(String message, Throwable cause) {
        super(message, cause);
    }

    public InvalidIdException(Throwable cause) {
        super(cause);
    }

    protected InvalidIdException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }

    public int getId() {
        return id;
    }

    public String getMethod() {
        return method;
    }
}
