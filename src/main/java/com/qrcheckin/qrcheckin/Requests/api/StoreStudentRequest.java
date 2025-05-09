package com.qrcheckin.qrcheckin.Requests.api;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import org.springframework.web.multipart.MultipartFile;

@Valid
public class StoreStudentRequest {

    @NotNull
    @NotBlank
    private String email;


    @NotNull
    @NotBlank
    private String name;

    @NotNull
    @NotBlank
    private String groupKey;

    private MultipartFile qr;

    //Getters

    public String getEmail(){ return  email; }
    public String getName(){ return name; }
    public String getGroupKey() { return groupKey; }
    public MultipartFile getQr(){ return qr; }

    //Setters

    public void setEmail(String email) {
        this.email = email;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setGroupKey(String key){
        this.groupKey = key;
    }
    public void setQr(MultipartFile qr) {
        this.qr = qr;
    }
}
