package com.qrcheckin.qrcheckin.Config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix = "app")
public class AppConfig {
    private String path;

    public String getPath() {
        return path;
    }

    public void setPath(String p) {
        this.path = p;
    }

    public String getPublicPath(){
        return this.getPath() + "/src/main/resources/static/public";
    }
}
