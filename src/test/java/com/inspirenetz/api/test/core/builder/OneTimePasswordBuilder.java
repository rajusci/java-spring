package com.inspirenetz.api.test.core.builder;

import com.inspirenetz.api.core.dictionary.OTPStatus;
import com.inspirenetz.api.core.dictionary.OTPType;
import com.inspirenetz.api.core.domain.OneTimePassword;

import java.sql.Date;
import java.sql.Timestamp;

/**
 * Created by fayiz-ci on 2/25/15.
 */
public class OneTimePasswordBuilder {

    private Long otpId;

    private Long otpMerchantNo;

    private Long otpCustomerNo;

    private Integer otpType = OTPType.TRANSFER_POINTS;

    private String otpCode;

    private Integer otpStatus = OTPStatus.NEW;

    private Timestamp otpExpiry;

    private Date otpCreationDate;

    private Timestamp otpCreateTimestamp;

    private java.util.Date createdAt;

    private String createdBy;

    private java.util.Date updatedAt;

    private String updatedBy;

    private Integer otpRefType;

    private String otpReference;

    private OneTimePasswordBuilder() {
    }

    public static OneTimePasswordBuilder anOneTimePassword() {
        return new OneTimePasswordBuilder();
    }

    public OneTimePasswordBuilder withOtpId(Long otpId) {
        this.otpId = otpId;
        return this;
    }

    public OneTimePasswordBuilder withOtpMerchantNo(Long otpMerchantNo) {
        this.otpMerchantNo = otpMerchantNo;
        return this;
    }

    public OneTimePasswordBuilder withOtpCustomerNo(Long otpCustomerNo) {
        this.otpCustomerNo = otpCustomerNo;
        return this;
    }

    public OneTimePasswordBuilder withOtpType(Integer otpType) {
        this.otpType = otpType;
        return this;
    }

    public OneTimePasswordBuilder withOtpCode(String otpCode) {
        this.otpCode = otpCode;
        return this;
    }

    public OneTimePasswordBuilder withOtpStatus(Integer otpStatus) {
        this.otpStatus = otpStatus;
        return this;
    }

    public OneTimePasswordBuilder withOtpExpiry(Timestamp otpExpiry) {
        this.otpExpiry = otpExpiry;
        return this;
    }

    public OneTimePasswordBuilder withOtpCreationDate(Date otpCreationDate) {
        this.otpCreationDate = otpCreationDate;
        return this;
    }

    public OneTimePasswordBuilder withOtpCreateTimestamp(Timestamp otpCreateTimestamp) {
        this.otpCreateTimestamp = otpCreateTimestamp;
        return this;
    }

    public OneTimePasswordBuilder withCreatedAt(java.util.Date createdAt) {
        this.createdAt = createdAt;
        return this;
    }

    public OneTimePasswordBuilder withCreatedBy(String createdBy) {
        this.createdBy = createdBy;
        return this;
    }

    public OneTimePasswordBuilder withUpdatedAt(java.util.Date updatedAt) {
        this.updatedAt = updatedAt;
        return this;
    }

    public OneTimePasswordBuilder withUpdatedBy(String updatedBy) {
        this.updatedBy = updatedBy;
        return this;
    }

    public OneTimePasswordBuilder withOtpRefType(Integer otpRefType) {
        this.otpRefType = otpRefType;
        return this;
    }

    public OneTimePasswordBuilder withOtpReference(String otpReference) {
        this.otpReference = otpReference;
        return this;
    }

    public OneTimePasswordBuilder but() {
        return anOneTimePassword().withOtpId(otpId).withOtpMerchantNo(otpMerchantNo).withOtpCustomerNo(otpCustomerNo).withOtpType(otpType).withOtpCode(otpCode).withOtpStatus(otpStatus).withOtpExpiry(otpExpiry).withOtpCreationDate(otpCreationDate).withOtpCreateTimestamp(otpCreateTimestamp).withCreatedAt(createdAt).withCreatedBy(createdBy).withUpdatedAt(updatedAt).withUpdatedBy(updatedBy).withOtpRefType(otpRefType).withOtpReference(otpReference);
    }

    public OneTimePassword build() {
        OneTimePassword oneTimePassword = new OneTimePassword();
        oneTimePassword.setOtpId(otpId);
        oneTimePassword.setOtpMerchantNo(otpMerchantNo);
        oneTimePassword.setOtpCustomerNo(otpCustomerNo);
        oneTimePassword.setOtpType(otpType);
        oneTimePassword.setOtpCode(otpCode);
        oneTimePassword.setOtpStatus(otpStatus);
        oneTimePassword.setOtpExpiry(otpExpiry);
        oneTimePassword.setOtpCreationDate(otpCreationDate);
        oneTimePassword.setOtpCreateTimestamp(otpCreateTimestamp);
        oneTimePassword.setCreatedAt(createdAt);
        oneTimePassword.setCreatedBy(createdBy);
        oneTimePassword.setUpdatedAt(updatedAt);
        oneTimePassword.setUpdatedBy(updatedBy);
        oneTimePassword.setOtpRefType(otpRefType);
        oneTimePassword.setOtpReference(otpReference);
        return oneTimePassword;
    }
}
