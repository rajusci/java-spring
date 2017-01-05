package com.inspirenetz.api.test.core.builder;

import com.inspirenetz.api.core.dictionary.FunctionType;
import com.inspirenetz.api.core.dictionary.StrIndicatorStatus;
import com.inspirenetz.api.core.dictionary.UserAccessRightAccessEnableFlag;
import com.inspirenetz.api.core.domain.Function;

import java.util.Date;

/**
 * Created by sandheepgr on 26/6/14.
 */
public class FunctionBuilder {
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
    private Date createdAt;
    private String createdBy;
    private Date updatedAt;
    private String updatedBy;

    private FunctionBuilder() {
    }

    public static FunctionBuilder aFunction() {
        return new FunctionBuilder();
    }

    public FunctionBuilder withFncFunctionCode(Long fncFunctionCode) {
        this.fncFunctionCode = fncFunctionCode;
        return this;
    }

    public FunctionBuilder withFncFunctionName(String fncFunctionName) {
        this.fncFunctionName = fncFunctionName;
        return this;
    }

    public FunctionBuilder withFncPageId(String fncPageId) {
        this.fncPageId = fncPageId;
        return this;
    }

    public FunctionBuilder withFncDescription(String fncDescription) {
        this.fncDescription = fncDescription;
        return this;
    }

    public FunctionBuilder withFncMerchantAdminEnabled(String fncMerchantAdminEnabled) {
        this.fncMerchantAdminEnabled = fncMerchantAdminEnabled;
        return this;
    }

    public FunctionBuilder withFncMerchantUserEnabled(String fncMerchantUserEnabled) {
        this.fncMerchantUserEnabled = fncMerchantUserEnabled;
        return this;
    }

    public FunctionBuilder withFncCustomerEnabled(String fncCustomerEnabled) {
        this.fncCustomerEnabled = fncCustomerEnabled;
        return this;
    }

    public FunctionBuilder withFncAdminEnabled(String fncAdminEnabled) {
        this.fncAdminEnabled = fncAdminEnabled;
        return this;
    }

    public FunctionBuilder withFncVendorUserEnabled(String fncVendorUserEnabled) {
        this.fncVendorUserEnabled = fncVendorUserEnabled;
        return this;
    }

    public FunctionBuilder withFncNotifySa(String fncNotifySa) {
        this.fncNotifySa = fncNotifySa;
        return this;
    }

    public FunctionBuilder withFncNotifyAdmin(String fncNotifyAdmin) {
        this.fncNotifyAdmin = fncNotifyAdmin;
        return this;
    }

    public FunctionBuilder withFncNotifyMa(String fncNotifyMa) {
        this.fncNotifyMa = fncNotifyMa;
        return this;
    }

    public FunctionBuilder withFncNotifyMu(String fncNotifyMu) {
        this.fncNotifyMu = fncNotifyMu;
        return this;
    }

    public FunctionBuilder withFncNotifyUser(String fncNotifyUser) {
        this.fncNotifyUser = fncNotifyUser;
        return this;
    }

    public FunctionBuilder withFncLog(String fncLog) {
        this.fncLog = fncLog;
        return this;
    }

    public FunctionBuilder withFncNotifyText(String fncNotifyText) {
        this.fncNotifyText = fncNotifyText;
        return this;
    }

    public FunctionBuilder withFncType(Integer fncType) {
        this.fncType = fncType;
        return this;
    }

    public FunctionBuilder withCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
        return this;
    }

    public FunctionBuilder withCreatedBy(String createdBy) {
        this.createdBy = createdBy;
        return this;
    }

    public FunctionBuilder withUpdatedAt(Date updatedAt) {
        this.updatedAt = updatedAt;
        return this;
    }

    public FunctionBuilder withUpdatedBy(String updatedBy) {
        this.updatedBy = updatedBy;
        return this;
    }

    public Function build() {
        Function function = new Function();
        function.setFncFunctionCode(fncFunctionCode);
        function.setFncFunctionName(fncFunctionName);
        function.setFncPageId(fncPageId);
        function.setFncDescription(fncDescription);
        function.setFncMerchantAdminEnabled(fncMerchantAdminEnabled);
        function.setFncMerchantUserEnabled(fncMerchantUserEnabled);
        function.setFncCustomerEnabled(fncCustomerEnabled);
        function.setFncAdminEnabled(fncAdminEnabled);
        function.setFncVendorUserEnabled(fncVendorUserEnabled);
        function.setFncNotifySa(fncNotifySa);
        function.setFncNotifyAdmin(fncNotifyAdmin);
        function.setFncNotifyMa(fncNotifyMa);
        function.setFncNotifyMu(fncNotifyMu);
        function.setFncNotifyUser(fncNotifyUser);
        function.setFncLog(fncLog);
        function.setFncNotifyText(fncNotifyText);
        function.setFncType(fncType);
        function.setCreatedAt(createdAt);
        function.setCreatedBy(createdBy);
        function.setUpdatedAt(updatedAt);
        function.setUpdatedBy(updatedBy);
        return function;
    }
}
