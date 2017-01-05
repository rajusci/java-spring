package com.inspirenetz.api.core.auth;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetails;

import java.security.Principal;

/**
 * Created by sandheepgr on 16/4/14.
 */
public class AuthSession {

    /**
     * Returns the domain User object for the currently logged in user, or null
     * if no User is logged in.
     *
     * @return User object for the currently logged in user, or null if no User
     *         is logged in.
     */
    public static AuthUser getCurrentUser() {

        // Get the principal
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();


        // Check if the principal is instanceof AuthUser
        if (principal instanceof AuthUser) {

            // Return the user object
            return ((AuthUser) principal);

        }

        // Return null
        return null;


    }


    /**
     * Utility method to determine if the current user is logged in /
     * authenticated.
     * <p>
     * Equivalent of calling:
     * <p>
     * <code>getCurrentUser() != null</code>
     *
     * @return if user is logged in
     */
    public static boolean isLoggedIn() {

        return getCurrentUser() != null;

    }


    /**
     * Utility method to get the merchant number of the merchant
     * using from the currently logged in user
     *
     * @return the merchant number
     */
    public static Long getMerchantNo() {

        // Get the AuthUser
        AuthUser user = getCurrentUser();

        // Check if the user is on
        if ( user != null ) {

            return user.getMerchantNo();

        }

        // Return 0
        return 0L;

    }


    /**
     * Utility method to get the user number of the user using the
     * currently logged in user
     *
     * @return the user number
     */
    public static Long getUserNo() {

        // Get the AuthUser
        AuthUser user = getCurrentUser();

        // Check if the user is on
        if ( user != null ) {

            return user.getUserNo();

        }

        // Return 0
        return 0L;
    }


    /**
     * Utility method to get the location value for the currently
     * logged in user
     *
     * @return the user location
     */
    public static Long getUserLocation() {

        // Get the AuthUser
        AuthUser user = getCurrentUser();

        // Check if the user is on
        if ( user != null ) {

            return user.getUserLocation();

        }

        // Return 0
        return 0L;
    }


    /**
     * Utility method to get the login id  value for the currently logged in user
     *
     *
     * @return - The user login id
     */
    public static String getUserLoginId() {

        // Get the AuthUser
        AuthUser user = getCurrentUser();

        // Check if the user is on
        if ( user != null ) {

            return user.getUserLoginId();

        }

        // Return 0
        return "";
    }


    /**
     * Utility method to get the ipaddress from which the user is requesting the
     * information.
     *
     * @return  - The Ip address from the which the user is associated
     */
    public static String getUserIpAddress() {

        // Get the Authenticate from current SecurityContextHolder
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();

        // Get the WebAuthenticateDetails object from authentication
        WebAuthenticationDetails details = (WebAuthenticationDetails) auth.getDetails();

        // Return the remoteAddress if the details object is not null
        if ( details  != null ) {

            // Return the remove address
            return   details.getRemoteAddress();

        } else {

            return "";

        }
    }


    /**
     *
     * Utility method to get the user type information for the user requesting
     *
     * @return - Return the user type
     */
    public static int getUserType() {

        // Get the AuthUser
        AuthUser user = getCurrentUser();

        // Check if the user is on
        if ( user != null ) {

            return user.getUserType();

        }

        // Return 0
        return 0;
    }
}
