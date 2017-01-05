package com.inspirenetz.api.rest.resource;

import com.inspirenetz.api.core.dictionary.FunctionType;
import com.inspirenetz.api.core.dictionary.StrIndicatorStatus;
import com.inspirenetz.api.core.dictionary.UserAccessRightAccessEnableFlag;



/**
 * Created by sandheepgr on 26/6/14.
 */
public class FunctionResource extends BaseResource {

    private Long fncFunctionCode;

    private String fncFunctionName = "";

    private String fncPageId = "";

    private String fncDescription = "";

    private String fncMerchantAdminEnabled = UserAccessRightAccessEnableFlag.DISABLED;

    private String fncMerchantUserEnabled = UserAccessRightAccessEnableFlag.DISABLED;

    private String fncCustomerEnabled = UserAccessRightAccessEnableFlag.DISABLED;

    private String fncAdminEnabled = UserAccessRightAccessEnableFlag.DISABLED;

    private String fncVendorUserEnabled = UserAccessRightAccessEnableFlag.DISABLED;

    private String fncNotifySa = StrIndicatorStatus.YES;

    private String fncNotifyAdmin = StrIndicatorStatus.NO;

    private String fncNotifyMa = StrIndicatorStatus.NO;

    private String fncNotifyMu;

    private String fncNotifyUser = StrIndicatorStatus.NO;

    private String fncLog = "";

    private String fncNotifyText = "";

    private Integer fncType = FunctionType.ADMIN_ACTIVITY;


    public Integer getFncType() {
        return fncType;
    }

    public void setFncType(Integer fncType) {
        this.fncType = fncType;
    }

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
}
