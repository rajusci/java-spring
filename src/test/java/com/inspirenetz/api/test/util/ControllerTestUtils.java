package com.inspirenetz.api.test.util;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;

/**
 * Created by sandheepgr on 1/5/14.
 */
public class ControllerTestUtils {

    public static final String TEST_MERCHANT_ADMIN_LOGINID = "sjadmin";

    public static final String TEST_MERCHANT_USER_LOGINID = "sjuser";

    public static final String TEST_CUSTOMER_LOGINID = "8867987369";

    public static final String TEST_ADMIN_USER_LOGINID = "adminuser";

    public static final String TEST_SUPER_ADMIN_USER_LOGINID = "adminuser";

    public static final String TEST_REDEMPTION_MERCHANT = "8793684047";

    /**
     * Class representing the MockSecurityContect for the MockHttpSession
     * that will be passed to each of the MockMvc calls
     */
    public static class MockSecurityContext implements SecurityContext {

        private static final long serialVersionUID = -1386535243513362694L;

        private Authentication authentication;


        public MockSecurityContext(Authentication authentication) {
            this.authentication = authentication;
        }

        @Override
        public Authentication getAuthentication() {
            return this.authentication;
        }

        @Override
        public void setAuthentication(Authentication authentication) {
            this.authentication = authentication;
        }
    }


    /**
     * Methods to the get the UsernamePasswordAuthenticationToken ( Principal) for the request
     * Here we are passing the username that is present in the system and the UserDetailsService
     * object that is autowired in the calling Test class
     *
     * This function will call UserDetailsService implementation to load the user information
     * for the database and create an UsernamePasswordAuthenticationToken
     *
     * @param username              - The user name of the user that is existing in the system
     * @param userDetailsService    - Autowired UserDetailsService from the calling class
     *
     * @return - UsernamePasswordAuthenticationToken with the information for the specified username
     */
    public static UsernamePasswordAuthenticationToken  getPrincipal(String username, UserDetailsService userDetailsService) {

        // Get the user information
        UserDetails user = userDetailsService.loadUserByUsername(username);

        // Create the UsernamePasswordAuthenticateToken object
        UsernamePasswordAuthenticationToken authentication =
                new UsernamePasswordAuthenticationToken(
                        user,
                        user.getPassword(),
                        user.getAuthorities());


        // Return the object
        return authentication;
    }






}
