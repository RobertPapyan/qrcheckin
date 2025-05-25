package com.qrcheckin.qrcheckin.Exception.api;

public class ApiFailedException extends RuntimeException {
    public ApiFailedException(String message) {
        super(message);
    }

    public ApiFailedException(String message, Throwable cause) {
        super(message, cause);
    }
}
