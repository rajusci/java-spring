package com.inspirenetz.api.config;

import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class CORSInterceptor extends HandlerInterceptorAdapter {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {


        String origin = request.getHeader("Origin");
        if(!response.containsHeader("Access-Control-Allow-Origin"))
            response.addHeader("Access-Control-Allow-Origin", origin);

        return true;

    }
}
