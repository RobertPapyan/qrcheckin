package com.qrcheckin.qrcheckin.Requests.api;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

@Valid
public class StoreGroupRequest {

    @NotNull
    @NotBlank
    private String name;


    @NotNull
    @NotBlank
    private String key;

    //Getters

    public String getKey(){ return  key; }
    public String getName(){ return name; }

    //Setters
    public void setKey(String key) {
        this.key = key;
    }

    public void setName(String name) {
        this.name = name;
    }

}
