package com.qrcheckin.qrcheckin.Config;
import com.qrcheckin.qrcheckin.Interceptors.api.ApiKeyAuthenticationInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.multipart.MultipartResolver;
import org.springframework.web.multipart.support.StandardServletMultipartResolver;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class ApiConfig implements WebMvcConfigurer {

    private final ApiKeyAuthenticationInterceptor keyInterceptor;

    public ApiConfig(ApiKeyAuthenticationInterceptor keyInterceptor){
        this.keyInterceptor = keyInterceptor;
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(this.keyInterceptor)
                .addPathPatterns("/api/**");
    }

    @Bean
    public MultipartResolver multipartResolver(){
        return new StandardServletMultipartResolver();
    }
}
