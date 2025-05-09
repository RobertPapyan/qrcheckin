package com.qrcheckin.qrcheckin.Config;

import com.qrcheckin.qrcheckin.Interceptors.AuthRedirectInterceptor;
import com.qrcheckin.qrcheckin.Interceptors.AuthUserInterceptor;
import com.qrcheckin.qrcheckin.Interceptors.PaginationInterceptor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    private final AuthUserInterceptor authUserInterceptor;

    private final AuthRedirectInterceptor authRedirectInterceptor;

    private final PaginationInterceptor paginationInterceptor;

    public WebConfig(AuthUserInterceptor authUserInterceptor, AuthRedirectInterceptor authRedirectInterceptor,
                     PaginationInterceptor paginationInterceptor){
        this.authUserInterceptor = authUserInterceptor;
        this.authRedirectInterceptor = authRedirectInterceptor;
        this.paginationInterceptor = paginationInterceptor;
    }


    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(authUserInterceptor)
                .addPathPatterns("/dashboard/**");

        registry.addInterceptor(paginationInterceptor)
                .addPathPatterns("/dashboard/**");

        registry.addInterceptor(authRedirectInterceptor)
                .addPathPatterns("/login","/auth/**");
    }
}
