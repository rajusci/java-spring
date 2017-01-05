package com.inspirenetz.api.core.auth;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

import java.util.Collection;

/**
 * Created by sandheepgr on 16/4/14.
 */
public class AuthUser extends User implements AuthUserDetails {

    private Long merchantNo = new Long(0L);

    private Long userNo;

    private Long userLocation = new Long(0L);

    private String userLoginId;

    private String ipAddress;

    private int userType;

    private Long thirdPartyVendorNo;



    public AuthUser(String username, String password, Collection<? extends GrantedAuthority> authorities) {
        super(username, password, authorities);
    }

    public AuthUser(String username, String password, boolean enabled, boolean accountNonExpired, boolean credentialsNonExpired, boolean accountNonLocked, Collection<? extends GrantedAuthority> authorities) {
        super(username, password, enabled, accountNonExpired, credentialsNonExpired, accountNonLocked, authorities);
    }




    public int getUserType() {
        return userType;
    }

    public void setUserType(int userType) {
        this.userType = userType;
    }

    public String getIpAddress() {
        return ipAddress;
    }

    public void setIpAddress(String ipAddress) {
        this.ipAddress = ipAddress;
    }

    public Long getMerchantNo() {
        return merchantNo;
    }

    public void setMerchantNo(Long merchantNo) {
        this.merchantNo = merchantNo;
    }

    public Long getUserNo() {
        return userNo;
    }

    public void setUserNo(Long userNo) {
        this.userNo = userNo;
    }

    public Long getUserLocation() {
        return userLocation;
    }

    public void setUserLocation(Long userLocation) {
        this.userLocation = userLocation;
    }

    public String getUserLoginId() {
        return userLoginId;
    }

    public void setUserLoginId(String userLoginId) {
        this.userLoginId = userLoginId;
    }

    public Long getThirdPartyVendorNo() {
        return thirdPartyVendorNo;
    }

    public void setThirdPartyVendorNo(Long thirdPartyVendorNo) {
        this.thirdPartyVendorNo = thirdPartyVendorNo;
    }



}
