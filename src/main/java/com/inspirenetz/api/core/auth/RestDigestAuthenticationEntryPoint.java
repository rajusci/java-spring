package com.inspirenetz.api.core.auth;

import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.www.DigestAuthenticationEntryPoint;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpServletResponseWrapper;
import java.io.IOException;

/**
 * Created by ffl on 07/06/14.
 */
public class RestDigestAuthenticationEntryPoint extends DigestAuthenticationEntryPoint{

    @Override
    public void commence(HttpServletRequest request,
                         HttpServletResponse response, AuthenticationException authException)
            throws IOException, ServletException {


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

            } else {

                ngBrowserVersion = "false";

            }

        }


        if ( ngBrowserVersion.equals("true") && response.getHeader("Access-Control-Allow-Origin") == null ) {

            String originHeader = request.getHeader("Origin");
            response.addHeader("Access-Control-Allow-Origin", originHeader);

            response.addHeader("Access-Control-Allow-Methods", "GET, POST");
            response.addHeader("Access-Control-Allow-Headers", "Content-Type");
            response.addHeader("Access-Control-Allow-Headers", "*");
            response.addHeader("Access-Control-Allow-Headers", "ngBrowserVersion");
            response.addHeader("Access-Control-Allow-Headers", "Authorization");
            response.addHeader("Access-Control-Expose-Headers","WWW-Authenticate");
            response.addHeader("Access-Control-Max-Age", "1800");

        }


        if ( request.getMethod().equals("OPTIONS") ) {

            return;

        }

        // Check if the request has got an additional header indicating that the request is from
        // the angular version ( browser version)
        if ( request != null  && ngBrowserVersion.equals("true")) {

            // Set the header in the response
            response.setHeader("ngBrowserVersion",ngBrowserVersion);

        }

        // Call the super function
        super.commence(request, new UnauthorizedHttpResponse(response), authException);

    }


    private static class UnauthorizedHttpResponse extends HttpServletResponseWrapper{

        // Set the response here
        HttpServletResponse response;

        public UnauthorizedHttpResponse(HttpServletResponse response) {

            // Call super response
            super(response);

            // Set the current response
            this.response = response;

        }

        @Override
        public void sendError(int sc, String msg) throws IOException {

            // Variable holding the header value from angularVersion
            String ngBrowserVersion = response.getHeader("ngBrowserVersion");

            // check if we are sending 401, and if the header is present, then we need
            // to set the sc to FORBIDDEN
            if(sc == HttpServletResponse.SC_UNAUTHORIZED && (ngBrowserVersion != null && ngBrowserVersion.equals("true"))) {

                sc = HttpServletResponse.SC_FORBIDDEN;

            }

            // call the super function
            super.sendError(sc, msg);

        }

    }
}
