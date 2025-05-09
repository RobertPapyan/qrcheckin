package com.qrcheckin.qrcheckin.Interceptors.api;

import com.qrcheckin.qrcheckin.Exception.api.UnauthenticatedApiCallException;
import com.qrcheckin.qrcheckin.Repositories.UserRepository;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;


@Component
public class ApiKeyAuthenticationInterceptor implements HandlerInterceptor {

    private final UserRepository userRepo;

    @Autowired
    public ApiKeyAuthenticationInterceptor(UserRepository userReop){
        this.userRepo = userReop;
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        var key = request.getHeader("API_KEY");

        if(key != null && this.userRepo.existsByApiKey(key)){
            return  true;
        }

        throw  new UnauthenticatedApiCallException("Api key not valid",request);
    }
}
