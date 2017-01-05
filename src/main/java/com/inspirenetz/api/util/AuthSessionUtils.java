package com.inspirenetz.api.util;

import com.inspirenetz.api.core.auth.AuthUser;
import com.inspirenetz.api.core.dictionary.APIErrorCode;
import com.inspirenetz.api.core.domain.*;
import com.inspirenetz.api.core.service.RoleService;
import com.inspirenetz.api.core.service.UserAccessRightService;
import com.inspirenetz.api.core.service.UserRoleService;
import com.inspirenetz.api.core.service.UserService;
import com.inspirenetz.api.rest.exception.InspireNetzException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetails;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

/**
 * Created by sandheepgr on 16/4/14.
 */
@Component
public class AuthSessionUtils {

    @Autowired
    private UserAccessRightService userAccessRightService;

    @Autowired
    private UserRoleService userRoleService;

    @Autowired
    private RoleService roleService;


    /**
     * Returns the domain User object for the currently logged in user, or null
     * if no User is logged in.
     *
     * @return User object for the currently logged in user, or null if no User
     *         is logged in.
     */
    public AuthUser getCurrentUser() {

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
    public  boolean isLoggedIn() {

        return getCurrentUser() != null;

    }


    /**
     * Utility method to get the merchant number of the merchant
     * using from the currently logged in user
     *
     * @return the merchant number
     */
    public Long getMerchantNo() {

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
    public Long getUserNo() {

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
    public Long getUserLocation() {

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
    public String getUserLoginId() {

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
    public  String getUserIpAddress() {

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
    public int getUserType() {

        // Get the AuthUser
        AuthUser user = getCurrentUser();

        // Check if the user is on
        if ( user != null ) {

            return user.getUserType();

        }

        // Return 0
        return 0;
    }


    /**
     * Function to update the audit details to the AuditedEntity passed
     *
     * @param auditedEntity - The object that has been passed to set the details
     */
    public void updateAuditDetails( AuditedEntity auditedEntity ) {

        // Get the userNo
        Long userNo = getUserNo();

        // Check if the createdBy field is empty
        if ( auditedEntity.getCreatedBy() == null || auditedEntity.getCreatedBy().equals("") ) {

            auditedEntity.setCreatedBy(Long.toString(userNo));

        } else {

            auditedEntity.setUpdatedBy(Long.toString(userNo));

        }

    }


    /**
     * Function to validate and check the access to a function for the currently
     * logged in user.
     * If the user does not have access, this will throw an exception
     *
     * @param fncCode           - The function code to which user access need to be validated
     *
     * @throws InspireNetzException
     */
    public void validateFunctionAccess(Long fncCode) throws InspireNetzException {

        // Check if the function code has access, otherwise throw exception
        if ( !isFunctionAccessAllowed(fncCode)  ) {

            throw new InspireNetzException(APIErrorCode.ERR_NOT_AUTHORIZED);

        }

    }

    /**
     * Check if the function code is enabled for the user
     *
     * @param fncCode   - The function code to which the user access need to be validated
     *
     * @return          - True if the user has got access
     *                    False if the user does not have access
     */
    public boolean isFunctionAccessAllowed(Long fncCode ) throws InspireNetzException {

        // Get the userNo
        Long userNo = getUserNo();

        // If the userNo is null or 0L, return false
        if ( userNo == null || userNo == 0L ) {

            return false;

        }

        //get the access right map for the user
        Map<Long,String> userAccessRightMap = userAccessRightService.getUarAsMap(userNo);

        //get the access right for the function code
        String isAccessEnabled = userAccessRightMap.get(fncCode);

        //if no access right is found return false
        if(isAccessEnabled == null){

            return false;
        }

        // check if the access enabled flag is Y
        return isAccessEnabled.equals("Y");

    }

    public boolean isFunctionAccessAllowed(Long fncCode ,User user) throws InspireNetzException {

        //check user is null
        if(user==null){

            return false;
        }
        Long userNo = user.getUsrUserNo();

        // If the userNo is null or 0L, return false
        if ( userNo == null || userNo == 0L ) {

            return false;

        }

        //get the access right map for the user
        Map<Long,String> userAccessRightMap = userAccessRightService.getUarAsMap(userNo);

        //get the access right for the function code
        String isAccessEnabled = userAccessRightMap.get(fncCode);

        //if no access right is found return false
        if(isAccessEnabled == null){

            return false;
        }

        // check if the access enabled flag is Y
        return isAccessEnabled.equals("Y");

    }



    /**
     * function to check if a particular role is assigned for a user
     * If the role is not assigned for the user, this will throw exception
     *
     * @param rolId  - The role id to be checked
     *
     * @throws InspireNetzException throw the exception
     */
    public void validateRole(Long rolId) throws InspireNetzException {

        // Check if the role is assigned
        if ( !isRoleAssigned(rolId) ) {

            // Throw exception
            throw new InspireNetzException(APIErrorCode.ERR_NOT_AUTHORIZED);

        }

    }

    /**
     * Function to check if a particular role is assigned to a user
     *
     * @param rolId - The role id to be checked
     *
     * @return      - True if the role is assigned,
     *                False if the role is not assigned
     */
    public boolean isRoleAssigned(Long rolId) {

        // Get the userNo for the user
        Long userNo = getUserNo();

        // Get the roles
        List<UserRole> userRoleList = userRoleService.findByUerUserId(userNo);

        // If the list null or empty , return false
        if ( userRoleList == null || userRoleList.isEmpty() ) {

            return false;

        }


        // Go through the list and see the entry
        for ( UserRole userRole : userRoleList ) {

            // Check if found
            if ( userRole.getUerRole().longValue() == rolId ) {

                return true;

            }

        }


        // If nothing matches return fals
        return false;
    }

    //get default role and identify user is under which role
    public Role getDefaultRole(User user) throws InspireNetzException {

        //find by role using default role
        Role role =roleService.findByRolId(user.getUsrDefaultRole() == null ? 0L : user.getUsrDefaultRole());

        //return role
        return  role;
    }


}
