package com.inspirenetz.api.test.core.builder;

import com.inspirenetz.api.core.domain.SecuritySetting;

import java.util.Date;

/**
 * Created by ameenci on 10/10/14.
 */
public class SecuritySettingBuilder {
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
    private Date createdAt;
    private String createdBy;
    private Date updatedAt;
    private String updatedBy;

    private SecuritySettingBuilder() {
    }

    public static SecuritySettingBuilder aSecuritySetting() {
        return new SecuritySettingBuilder();
    }

    public SecuritySettingBuilder withSecId(Long secId) {
        this.secId = secId;
        return this;
    }

    public SecuritySettingBuilder withSecPwdPreferences(String secPwdPreferences) {
        this.secPwdPreferences = secPwdPreferences;
        return this;
    }

    public SecuritySettingBuilder withSecPwdLength(Long secPwdLength) {
        this.secPwdLength = secPwdLength;
        return this;
    }

    public SecuritySettingBuilder withSecPwdExpiration(Long secPwdExpiration) {
        this.secPwdExpiration = secPwdExpiration;
        return this;
    }

    public SecuritySettingBuilder withSecPwdMinValidity(Long secPwdMinValidity) {
        this.secPwdMinValidity = secPwdMinValidity;
        return this;
    }

    public SecuritySettingBuilder withSecPwdExpiryNotification(Long secPwdExpiryNotification) {
        this.secPwdExpiryNotification = secPwdExpiryNotification;
        return this;
    }

    public SecuritySettingBuilder withSecPwdInitialEnabled(String secPwdInitialEnabled) {
        this.secPwdInitialEnabled = secPwdInitialEnabled;
        return this;
    }

    public SecuritySettingBuilder withSecPwdChangeUser(String secPwdChangeUser) {
        this.secPwdChangeUser = secPwdChangeUser;
        return this;
    }

    public SecuritySettingBuilder withSecPwdLockout(Long secPwdLockout) {
        this.secPwdLockout = secPwdLockout;
        return this;
    }

    public SecuritySettingBuilder withSecIdleSessionTimeout(Long secIdleSessionTimeout) {
        this.secIdleSessionTimeout = secIdleSessionTimeout;
        return this;
    }

    public SecuritySettingBuilder withSecPwdPasHistory(Long secPwdPasHistory) {
        this.secPwdPasHistory = secPwdPasHistory;
        return this;
    }

    public SecuritySettingBuilder withCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
        return this;
    }

    public SecuritySettingBuilder withCreatedBy(String createdBy) {
        this.createdBy = createdBy;
        return this;
    }

    public SecuritySettingBuilder withUpdatedAt(Date updatedAt) {
        this.updatedAt = updatedAt;
        return this;
    }

    public SecuritySettingBuilder withUpdatedBy(String updatedBy) {
        this.updatedBy = updatedBy;
        return this;
    }

    public SecuritySettingBuilder but() {
        return aSecuritySetting().withSecId(secId).withSecPwdPreferences(secPwdPreferences).withSecPwdLength(secPwdLength).withSecPwdExpiration(secPwdExpiration).withSecPwdMinValidity(secPwdMinValidity).withSecPwdExpiryNotification(secPwdExpiryNotification).withSecPwdInitialEnabled(secPwdInitialEnabled).withSecPwdChangeUser(secPwdChangeUser).withSecPwdLockout(secPwdLockout).withSecIdleSessionTimeout(secIdleSessionTimeout).withSecPwdPasHistory(secPwdPasHistory).withCreatedAt(createdAt).withCreatedBy(createdBy).withUpdatedAt(updatedAt).withUpdatedBy(updatedBy);
    }

    public SecuritySetting build() {
        SecuritySetting securitySetting = new SecuritySetting();
        securitySetting.setSecId(secId);
        securitySetting.setSecPwdPreferences(secPwdPreferences);
        securitySetting.setSecPwdLength(secPwdLength);
        securitySetting.setSecPwdExpiration(secPwdExpiration);
        securitySetting.setSecPwdMinValidity(secPwdMinValidity);
        securitySetting.setSecPwdExpiryNotification(secPwdExpiryNotification);
        securitySetting.setSecPwdInitialEnabled(secPwdInitialEnabled);
        securitySetting.setSecPwdChangeUser(secPwdChangeUser);
        securitySetting.setSecPwdLockout(secPwdLockout);
        securitySetting.setSecIdleSessionTimeout(secIdleSessionTimeout);
        securitySetting.setSecPwdPasHistory(secPwdPasHistory);
        securitySetting.setCreatedAt(createdAt);
        securitySetting.setCreatedBy(createdBy);
        securitySetting.setUpdatedAt(updatedAt);
        securitySetting.setUpdatedBy(updatedBy);
        return securitySetting;
    }
}
