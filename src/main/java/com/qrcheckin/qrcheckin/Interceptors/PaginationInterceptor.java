package com.qrcheckin.qrcheckin.Interceptors;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

@Component
public class PaginationInterceptor implements HandlerInterceptor {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        var session = request.getSession();

        var perPage = request.getParameter("per-page");

        if(perPage != null){
            try {
                var parsed = Integer.parseInt(perPage);

                if(parsed > 0 && parsed < 100){
                    session.setAttribute("per-page",parsed);
                }
                return true;
            } catch (NumberFormatException _) {}
        }

        var sessionPerPage = session.getAttribute("per-page");

        if(sessionPerPage == null){
            session.setAttribute("per-page",5);
        }

        return true;
    }
}
