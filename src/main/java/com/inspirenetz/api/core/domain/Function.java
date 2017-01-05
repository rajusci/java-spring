package com.inspirenetz.api.core.domain;

import com.inspirenetz.api.core.dictionary.FunctionType;
import com.inspirenetz.api.core.dictionary.StrIndicatorStatus;
import com.inspirenetz.api.core.dictionary.UserAccessRightAccessEnableFlag;

import javax.persistence.*;

/**
 * Created by sandheepgr on 26/6/14.
 */
@Entity
@Table(name = "FUNCTIONS")
public class Function  extends  AuditedEntity {
    
    @Id
    @Column(name = "FNC_FUNCTION_CODE", nullable = false, insertable = true, updatable = true, length = 10, precision = 0)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long fncFunctionCode;

    @Basic
    @Column(name = "FNC_FUNCTION_NAME", nullable = false, insertable = true, updatable = true, length = 50, precision = 0)
    private String fncFunctionName = "";

    @Basic
    @Column(name = "FNC_PAGE_ID", nullable = false, insertable = true, updatable = true, length = 100, precision = 0)
    private String fncPageId = "";

    @Basic
    @Column(name = "FNC_DESCRIPTION", nullable = true, insertable = true, updatable = true, length = 100, precision = 0)
    private String fncDescription = "";

    @Basic
    @Column(name = "FNC_MERCHANT_ADMIN_ENABLED", nullable = true, insertable = true, updatable = true, length = 1, precision = 0)
    private String fncMerchantAdminEnabled = UserAccessRightAccessEnableFlag.DISABLED;

    @Basic
    @Column(name = "FNC_MERCHANT_USER_ENABLED", nullable = true, insertable = true, updatable = true, length = 1, precision = 0)
    private String fncMerchantUserEnabled = UserAccessRightAccessEnableFlag.DISABLED;

    @Basic
    @Column(name = "FNC_CUSTOMER_ENABLED", nullable = true, insertable = true, updatable = true, length = 1, precision = 0)
    private String fncCustomerEnabled = UserAccessRightAccessEnableFlag.DISABLED;

    @Basic
    @Column(name = "FNC_ADMIN_ENABLED", nullable = true, insertable = true, updatable = true, length = 1, precision = 0)
    private String fncAdminEnabled = UserAccessRightAccessEnableFlag.DISABLED;

    @Basic
    @Column(name = "FNC_VENDOR_USER_ENABLED", nullable = true, insertable = true, updatable = true, length = 1, precision = 0)
    private String fncVendorUserEnabled = UserAccessRightAccessEnableFlag.DISABLED;

    @Basic
    @Column(name = "FNC_NOTIFY_SA", nullable = true, insertable = true, updatable = true, length = 1, precision = 0)
    private String fncNotifySa = StrIndicatorStatus.YES;

    @Basic
    @Column(name = "FNC_NOTIFY_ADMIN", nullable = true, insertable = true, updatable = true, length = 1, precision = 0)
    private String fncNotifyAdmin = StrIndicatorStatus.NO;

    @Basic
    @Column(name = "FNC_NOTIFY_MA", nullable = true, insertable = true, updatable = true, length = 1, precision = 0)
    private String fncNotifyMa = StrIndicatorStatus.NO;

    @Basic
    @Column(name = "FNC_NOTIFY_MU", nullable = true, insertable = true, updatable = true, length = 1, precision = 0)
    private String fncNotifyMu;

    @Basic
    @Column(name = "FNC_NOTIFY_USER", nullable = true, insertable = true, updatable = true, length = 1, precision = 0)
    private String fncNotifyUser = StrIndicatorStatus.NO;

    @Basic
    @Column(name = "FNC_LOG", nullable = false, insertable = true, updatable = true, length = 1, precision = 0)
    private String fncLog = "";

    @Basic
    @Column(name = "FNC_NOTIFY_TEXT", nullable = true, insertable = true, updatable = true, length = 100, precision = 0)
    private String fncNotifyText = "";

    @Basic
    @Column(name = "FNC_TYPE", nullable = false, insertable = true, updatable = true, length = 10, precision = 0)
    private Integer fncType = FunctionType.ADMIN_ACTIVITY;




    public Long getFncFunctionCode() {
        return fncFunctionCode;
    }

    public void setFncFunctionCode(Long fncFunctionCode) {
        this.fncFunctionCode = fncFunctionCode;
    }

    public String getFncFunctionName() {
        return fncFunctionName;
    }

    public void setFncFunctionName(String fncFunctionName) {
        this.fncFunctionName = fncFunctionName;
    }

    public String getFncPageId() {
        return fncPageId;
    }

    public void setFncPageId(String fncPageId) {
        this.fncPageId = fncPageId;
    }

    public String getFncDescription() {
        return fncDescription;
    }

    public void setFncDescription(String fncDescription) {
        this.fncDescription = fncDescription;
    }

    public String getFncMerchantAdminEnabled() {
        return fncMerchantAdminEnabled;
    }

    public void setFncMerchantAdminEnabled(String fncMerchantAdminEnabled) {
        this.fncMerchantAdminEnabled = fncMerchantAdminEnabled;
    }

    public String getFncMerchantUserEnabled() {
        return fncMerchantUserEnabled;
    }

    public void setFncMerchantUserEnabled(String fncMerchantUserEnabled) {
        this.fncMerchantUserEnabled = fncMerchantUserEnabled;
    }

    public String getFncCustomerEnabled() {
        return fncCustomerEnabled;
    }

    public void setFncCustomerEnabled(String fncCustomerEnabled) {
        this.fncCustomerEnabled = fncCustomerEnabled;
    }

    public String getFncAdminEnabled() {
        return fncAdminEnabled;
    }

    public void setFncAdminEnabled(String fncAdminEnabled) {
        this.fncAdminEnabled = fncAdminEnabled;
    }

    public String getFncVendorUserEnabled() {
        return fncVendorUserEnabled;
    }

    public void setFncVendorUserEnabled(String fncVendorUserEnabled) {
        this.fncVendorUserEnabled = fncVendorUserEnabled;
    }

    public String getFncNotifySa() {
        return fncNotifySa;
    }

    public void setFncNotifySa(String fncNotifySa) {
        this.fncNotifySa = fncNotifySa;
    }

    public String getFncNotifyAdmin() {
        return fncNotifyAdmin;
    }

    public void setFncNotifyAdmin(String fncNotifyAdmin) {
        this.fncNotifyAdmin = fncNotifyAdmin;
    }

    public String getFncNotifyMa() {
        return fncNotifyMa;
    }

    public void setFncNotifyMa(String fncNotifyMa) {
        this.fncNotifyMa = fncNotifyMa;
    }

    public String getFncNotifyMu() {
        return fncNotifyMu;
    }

    public void setFncNotifyMu(String fncNotifyMu) {
        this.fncNotifyMu = fncNotifyMu;
    }

    public String getFncNotifyUser() {
        return fncNotifyUser;
    }

    public void setFncNotifyUser(String fncNotifyUser) {
        this.fncNotifyUser = fncNotifyUser;
    }

    public String getFncLog() {
        return fncLog;
    }

    public void setFncLog(String fncLog) {
        this.fncLog = fncLog;
    }

    public String getFncNotifyText() {
        return fncNotifyText;
    }

    public void setFncNotifyText(String fncNotifyText) {
        this.fncNotifyText = fncNotifyText;
    }

    public Integer getFncType() {
        return fncType;
    }

    public void setFncType(Integer fncType) {
        this.fncType = fncType;
    }




    @Override
    public String toString() {
        return "Function{" +
                "fncFunctionCode=" + fncFunctionCode +
                ", fncFunctionName='" + fncFunctionName + '\'' +
                ", fncPageId='" + fncPageId + '\'' +
                ", fncDescription='" + fncDescription + '\'' +
                ", fncMerchantAdminEnabled='" + fncMerchantAdminEnabled + '\'' +
                ", fncMerchantUserEnabled='" + fncMerchantUserEnabled + '\'' +
                ", fncCustomerEnabled='" + fncCustomerEnabled + '\'' +
                ", fncAdminEnabled='" + fncAdminEnabled + '\'' +
                ", fncVendorUserEnabled='" + fncVendorUserEnabled + '\'' +
                ", fncNotifySa='" + fncNotifySa + '\'' +
                ", fncNotifyAdmin='" + fncNotifyAdmin + '\'' +
                ", fncNotifyMa='" + fncNotifyMa + '\'' +
                ", fncNotifyMu='" + fncNotifyMu + '\'' +
                ", fncNotifyUser='" + fncNotifyUser + '\'' +
                ", fncLog='" + fncLog + '\'' +
                ", fncNotifyText='" + fncNotifyText + '\'' +
                ", fncType=" + fncType +
                '}';
    }
}
