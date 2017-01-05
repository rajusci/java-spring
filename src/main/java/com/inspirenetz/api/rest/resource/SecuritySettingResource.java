package com.inspirenetz.api.rest.resource;


import java.util.Set;

/**
 * Created by sandheepgr on 15/4/14.
 */
public class SecuritySettingResource extends BaseResource {


    private Long secId;
    private String secPwdPreferences ="";
    private Long secPwdLength ;
    private Long secPwdExpiration ;
    private Long secPwdMinValidity;
    private Long secPwdExpiryNotification;
    private String secPwdInitialEnabled ="N";
    private String secPwdChangeUser ="N";
    private Long secPwdLockout;
    private Long secIdleSessionTimeout;
    private Long secPwdPasHistory;

    public Long getSecId() {
        return secId;
    }

    public void setSecId(Long secId) {
        this.secId = secId;
    }

    public String getSecPwdPreferences() {
        return secPwdPreferences;
    }

    public void setSecPwdPreferences(String secPwdPreferences) {
        this.secPwdPreferences = secPwdPreferences;
    }

    public Long getSecPwdExpiration() {
        return secPwdExpiration;
    }

    public void setSecPwdExpiration(Long secPwdExpiration) {
        this.secPwdExpiration = secPwdExpiration;
    }

    public Long getSecPwdMinValidity() {
        return secPwdMinValidity;
    }

    public void setSecPwdMinValidity(Long secPwdMinValidity) {
        this.secPwdMinValidity = secPwdMinValidity;
    }

    public Long getSecPwdExpiryNotification() {
        return secPwdExpiryNotification;
    }

    public void setSecPwdExpiryNotification(Long secPwdExpiryNotification) {
        this.secPwdExpiryNotification = secPwdExpiryNotification;
    }

    public String getSecPwdInitialEnabled() {
        return secPwdInitialEnabled;
    }

    public void setSecPwdInitialEnabled(String secPwdInitialEnabled) {
        this.secPwdInitialEnabled = secPwdInitialEnabled;
    }

    public String getSecPwdChangeUser() {
        return secPwdChangeUser;
    }

    public void setSecPwdChangeUser(String secPwdChangeUser) {
        this.secPwdChangeUser = secPwdChangeUser;
    }

    public Long getSecPwdLockout() {
        return secPwdLockout;
    }

    public void setSecPwdLockout(Long secPwdLockout) {
        this.secPwdLockout = secPwdLockout;
    }

    public Long getSecIdleSessionTimeout() {
        return secIdleSessionTimeout;
    }

    public void setSecIdleSessionTimeout(Long secIdleSessionTimeout) {
        this.secIdleSessionTimeout = secIdleSessionTimeout;
    }

    public Long getSecPwdLength() {        return secPwdLength;    }

    public void setSecPwdLength(Long secPwdLength) {        this.secPwdLength = secPwdLength;    }

    public Long getSecPwdPasHistory() {
        return secPwdPasHistory;
    }

    public void setSecPwdPasHistory(Long secPwdPasHistory) {
        this.secPwdPasHistory = secPwdPasHistory;
    }
}
