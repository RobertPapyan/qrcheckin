package com.qrcheckin.qrcheckin.Exception.api;

public class GroupNotFoundException extends RuntimeException {
    public GroupNotFoundException(String message) {
        super(message);
    }
}
