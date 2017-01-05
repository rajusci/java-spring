package com.inspirenetz.api.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Properties;

public class SimpleCORSFilter extends OncePerRequestFilter {
    private static final Logger log = LoggerFactory.getLogger(SimpleCORSFilter.class);

    private Properties prop = new Properties();


    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        // Check if th
        // Variable holding the header value from angularVersion
        String ngBrowserVersion = request.getHeader("ngBrowserVersion");

        // If the ngBrowserVersion is null, then check if its present in the
        // Access-Control-Request-Headers
        if ( ngBrowserVersion == null ) {

            // check if the Access-Control-Request-Headers is set
            String requestHeader = request.getHeader("Access-Control-Request-Headers");

            // If the requestHeader ngbrowserversion, then we need to set ti the ngBrowserVersion as true
            if ( requestHeader != null && requestHeader.equals("ngbrowserversion") ) {

                ngBrowserVersion = "true";

            }

        }

        if(ngBrowserVersion != null && ngBrowserVersion.equals("true")) {

            String originHeader = request.getHeader("Origin");
            response.addHeader("Access-Control-Allow-Origin", originHeader);

            response.addHeader("Access-Control-Allow-Methods", "GET, POST");
            response.addHeader("Access-Control-Allow-Headers", "Content-Type");
            response.addHeader("Access-Control-Allow-Headers", "*");
            response.addHeader("Access-Control-Allow-Headers", "Authorization");
            response.addHeader("Access-Control-Allow-Headers", "ngBrowserVersion");
            response.addHeader("Access-Control-Expose-Headers","WWW-Authenticate");
            response.addHeader("Access-Control-Max-Age", "1800");
        }

        filterChain.doFilter(request, response);
    }

}
