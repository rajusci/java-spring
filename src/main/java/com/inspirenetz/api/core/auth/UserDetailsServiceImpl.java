package com.inspirenetz.api.core.auth;


import com.inspirenetz.api.core.dictionary.UserStatus;
import com.inspirenetz.api.core.dictionary.UserType;
import com.inspirenetz.api.core.domain.User;
import com.inspirenetz.api.core.service.PasswordHistoryService;
import com.inspirenetz.api.core.service.UserService;
import com.inspirenetz.api.rest.exception.InspireNetzException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * User: ffl
 * Date: 13/3/14
 * Time: 6:07 PM
 */
@Component("uds")
public class UserDetailsServiceImpl implements UserDetailsService{

    @Autowired
    private UserService userService;

    @Autowired
    private PasswordHistoryService passwordHistoryService;

    @Override
    public AuthUserDetails loadUserByUsername(String s) throws UsernameNotFoundException {

        // Get the system user from the database
        com.inspirenetz.api.core.domain.User systemUser = userService.findByUsrLoginId(s);

        // If the system user is not null, then we need to create the authorities
        // and create the security user
        if(systemUser != null){

            // List holding the authorities
            List<GrantedAuthority> AUTHORITIES = new ArrayList<GrantedAuthority>();

            // Add the role of the user
            AUTHORITIES.add(new SimpleGrantedAuthority("ROLE_USER"));

            // for checking password is expired or not based on security settings
            boolean isExpired=true;

            boolean isAccountLockout=true;



            try {

                //set logged in details
                boolean isSetUseLogin =setLoginDetails(systemUser);

                // for check password is expire or not
                isExpired=isPasswordExpired(systemUser);

                // for check account is lockout or not
                isAccountLockout= isAccountLockout(systemUser.getUsrUserNo());



            } catch (InspireNetzException e) {

                e.printStackTrace();

            }

            //check user is active or not, if active set isEnabled to true
            boolean isEnabled = systemUser.getUsrStatus().intValue() == UserStatus.ACTIVE;

            // Set the user details
            AuthUser user = new AuthUser(systemUser.getUsrLoginId(),systemUser.getUsrPassword(), isEnabled, !isAccountLockout, !isExpired, true, AUTHORITIES);

            // Set the user number
            user.setUserNo(systemUser.getUsrUserNo());

            // Set the user login id
            user.setUserLoginId(systemUser.getUsrLoginId());

            // Set the user type
            user.setUserType(systemUser.getUsrUserType());


            // If the user type is merchnat user/merchant admin, we need to set the merchantno
            if ( systemUser.getUsrUserType() == UserType.MERCHANT_ADMIN || systemUser.getUsrUserType() == UserType.MERCHANT_USER ) {

                // Set the merchant number
                user.setMerchantNo(systemUser.getUsrMerchantNo());

                // Set the user location
                user.setUserLocation(systemUser.getUsrLocation());

            }

            //If the user type is redemption merchant user,we need to set third party vendor number
            if(systemUser.getUsrUserType()==UserType.REDEMPTION_MERCHANT_USER){

                //set third party vendor number
                user.setThirdPartyVendorNo(systemUser.getUsrThirdPartyVendorNo());

                //Set the user location
                user.setUserLocation(systemUser.getUsrLocation());
            }


            // Return the user object
            return user;

        }
        else
            throw new UsernameNotFoundException("Username not found");


    }

    private boolean setLoginDetails(User systemUser) {

        return passwordHistoryService.setLoggedInDetails(systemUser);
    }

    /**
     * @purpose:password is expired or not
     * @param user
     * @return boolean
     * @throws InspireNetzException
     */

    public boolean isPasswordExpired(User user) throws InspireNetzException {

        return passwordHistoryService.isPasswordExpired(user);
    }

    /**
     * @purpose:Account is lockout or not
     * @param usrUserNo
     * @return boolean
     * @throws InspireNetzException
     */

    public boolean isAccountLockout(Long usrUserNo) throws InspireNetzException {

        return userService.isAccountLockout(usrUserNo);
    }


}
