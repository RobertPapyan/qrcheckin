package com.qrcheckin.qrcheckin.Interceptors;

import com.qrcheckin.qrcheckin.Models.User;
import com.qrcheckin.qrcheckin.Repositories.UserRepository;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

@Component
public class AuthUserInterceptor implements HandlerInterceptor {

    private final UserRepository userRepository;

    public AuthUserInterceptor(UserRepository userRepository){
        this.userRepository = userRepository;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        if (modelAndView != null && SecurityContextHolder.getContext().getAuthentication() != null) {
            Authentication auth = SecurityContextHolder.getContext().getAuthentication();

            var principal = auth.getPrincipal();

            var requestUri = request.getRequestURI();
            modelAndView.addObject("request_uri", requestUri);

            if (principal instanceof User user) {
                if(requestUri.equals("/dashboard/students") || requestUri.equals("/dashboard/groups")){
                    user = userRepository.findByEmailWithGroups(user.getEmail()).orElse(user);
                }
                modelAndView.addObject("user", user);
            }
        }
    }
}
