package com.qrcheckin.qrcheckin.Interceptors;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;


@Component
public class AuthRedirectInterceptor implements HandlerInterceptor {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        var auth = SecurityContextHolder.getContext().getAuthentication();

        if(auth == null) return  true;


        if(auth.isAuthenticated() && !(auth instanceof AnonymousAuthenticationToken)){
            response.sendRedirect("/dashboard/profile");
            return false;
        }

        return true;
    }
}
