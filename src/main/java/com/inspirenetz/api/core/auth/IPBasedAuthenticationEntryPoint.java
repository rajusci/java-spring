package com.inspirenetz.api.core.auth;

import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.authentication.www.BasicAuthenticationEntryPoint;
import org.springframework.security.web.authentication.www.DigestAuthenticationEntryPoint;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpServletResponseWrapper;
import java.io.IOException;

/**
 * Created by ffl on 07/06/14.
 */
public class IPBasedAuthenticationEntryPoint extends BasicAuthenticationEntryPoint {


    @Override
    public void commence(HttpServletRequest request,
                         HttpServletResponse response, AuthenticationException authException)
            throws IOException, ServletException {

        // Check if the user is behind a proxy ( then get the original ip )
        String incomingIP = request.getHeader("X-FORWARDED-FOR");

        // If the incomingIP is not forwarded, then use getRemoteAddr
        if ( incomingIP == null ) {

            // Get the incomingIP
            incomingIP = request.getRemoteAddr();


        }

        // Send the error response
        response.sendError(HttpServletResponse.SC_UNAUTHORIZED, authException.getMessage());

    }

}
