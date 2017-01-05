package com.inspirenetz.api.core.domain;

import javax.persistence.*;
import java.io.Serializable;

/**
 * Created by saneeshci on 29/9/14.
 */
@Entity
@Table(name="SECURITY_SETTINGS")
public class SecuritySetting extends AuditedEntity implements Serializable {


    @Column(name = "SEC_ID",nullable = false)
    @Basic(fetch = FetchType.EAGER)
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long secId;

    @Column(name = "SEC_PWD_PREFERENCES",nullable = true)
    @Basic(fetch = FetchType.EAGER)
    private String secPwdPreferences ="";

    @Column(name = "SEC_PWD_LENGTH",nullable = true)
    @Basic(fetch = FetchType.EAGER)
    private Long secPwdLength ;

    @Column(name = "SEC_PWD_EXPIRATION",nullable = true)
    @Basic(fetch = FetchType.EAGER)
    private Long secPwdExpiration ;

    @Column(name = "SEC_PWD_MIN_VALIDITY",nullable = true)
    @Basic(fetch = FetchType.EAGER)
    private Long secPwdMinValidity;

    @Column(name = "SEC_PWD_EXPIRY_NOTIFICATION",nullable = true)
    @Basic(fetch = FetchType.EAGER)
    private Long secPwdExpiryNotification;


    @Column(name = "SEC_PWD_INITIAL_ENABLED",nullable = true)
    @Basic(fetch = FetchType.EAGER)
    private String secPwdInitialEnabled ="N";


    @Column(name = "SEC_PWD_CHANGE_USER",nullable = true)
    @Basic(fetch = FetchType.EAGER)
    private String secPwdChangeUser ="N";

    @Column(name = "SEC_PWD_LOCKOUT",nullable = true)
    @Basic(fetch = FetchType.EAGER)
    private Long secPwdLockout;

    @Column(name = "SEC_IDLE_SESSION_TIMEOUT",nullable = true)
    @Basic(fetch = FetchType.EAGER)
    private Long secIdleSessionTimeout;

    @Column(name = "SEC_PWD_PAS_HISTORY", nullable = true)
    @Basic(fetch = FetchType.EAGER)
    private Long secPwdPasHistory;

    @Column(name = "SEC_MERCHANT_NO", nullable = false)
    @Basic(fetch = FetchType.EAGER)
    private Long secMerchantNo;

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

    public Long getSecPwdLength() {
        return secPwdLength;
    }

    public void setSecPwdLength(Long secPwdLength) {
        this.secPwdLength = secPwdLength;
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

    public Long getSecPwdPasHistory() {
        return secPwdPasHistory;
    }

    public void setSecPwdPasHistory(Long secPwdPasHistory) {
        this.secPwdPasHistory = secPwdPasHistory;
    }


    public Long getSecMerchantNo() {
        return secMerchantNo;
    }

    public void setSecMerchantNo(Long secMerchantNo) {
        this.secMerchantNo = secMerchantNo;
    }

    @Override
    public String toString() {
        return "SecuritySetting{" +
                "secId=" + secId +
                ", secPwdPreferences='" + secPwdPreferences + '\'' +
                ", secPwdLength=" + secPwdLength +
                ", secPwdExpiration=" + secPwdExpiration +
                ", secPwdMinValidity=" + secPwdMinValidity +
                ", secPwdExpiryNotification=" + secPwdExpiryNotification +
                ", secPwdInitialEnabled='" + secPwdInitialEnabled + '\'' +
                ", secPwdChangeUser='" + secPwdChangeUser + '\'' +
                ", secPwdLockout=" + secPwdLockout +
                ", secIdleSessionTimeout=" + secIdleSessionTimeout +
                ", secPwdPasHistory=" + secPwdPasHistory +
                ", secMerchantNo=" + secMerchantNo +
                '}';
    }
}
