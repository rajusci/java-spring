package com.inspirenetz.api.core.auth;


import com.inspirenetz.api.core.dictionary.UserType;
import com.inspirenetz.api.core.domain.User;
import com.inspirenetz.api.core.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class UserAuthenticationProvider implements AuthenticationProvider {

    Logger logger = LoggerFactory.getLogger(UserAuthenticationProvider.class);

    @Autowired
    private UserService userService;


    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {

        // Get the user object from the login id
        User user = userService.findByUsrLoginId(String.valueOf(authentication.getPrincipal()));

        // If the user is not null / authentication is  passed,the we need to return
        // the UsernamePasswordAuthenticationToken
        if(user != null && user.getUsrPassword().equals(authentication.getCredentials())){

            // The list of authorities
            List<GrantedAuthority> AUTHORITIES = new ArrayList<GrantedAuthority>();

            // Set the authority based on the user logged in
            AUTHORITIES.add(new SimpleGrantedAuthority("ROLE_USER"));

            // Create the UsernamePasswordAuthenticationToken token object from the
            // username, password and AUTHORITIES
            UsernamePasswordAuthenticationToken  token = new UsernamePasswordAuthenticationToken(authentication.getName(), authentication.getCredentials(), AUTHORITIES);

            // Return the token
            return token;

        }

        // If not validated, then return null object
        return null;
    }

    @Override
    public boolean supports(Class<?> aClass) {
        return aClass.equals(UsernamePasswordAuthenticationToken.class);  //To change body of implemented methods use File | Setting | File Templates.
    }
}
