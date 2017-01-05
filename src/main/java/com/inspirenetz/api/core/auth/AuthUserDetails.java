package com.inspirenetz.api.core.auth;

import org.springframework.security.core.userdetails.UserDetails;

/**
 * Created by sandheepgr on 16/4/14.
 */
public interface AuthUserDetails extends UserDetails {

    public Long getMerchantNo();
    public Long getUserNo();
    public Long getUserLocation();
    public String getUserLoginId();
    public String getIpAddress();
    public int getUserType();

}
