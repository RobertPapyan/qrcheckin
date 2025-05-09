package com.qrcheckin.qrcheckin.Exception.api;

import jakarta.servlet.http.HttpServletRequest;

public class UnauthenticatedApiCallException extends RuntimeException {

    private final HttpServletRequest request;

    public UnauthenticatedApiCallException(String message, HttpServletRequest request) {
        super(message);
        this.request = request;
    }

    public HttpServletRequest getRequest() {
        return request;
    }
}
