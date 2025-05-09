package com.qrcheckin.qrcheckin.Records.api;

import com.fasterxml.jackson.annotation.JsonValue;

import java.util.Map;

public record ApiResponse(ResponseStatuses status, String message, Map<String,String> errors) {

    public ApiResponse(ResponseStatuses status, String message){
        this(status,message,null);
    }

    public static enum ResponseStatuses{
        SUCCESS("success"),ERROR("error");

        private final String value;

        ResponseStatuses(String value){
            this.value = value;
        }

        @JsonValue
        public String getValue() {
            return this.value;
        }
    }
}
