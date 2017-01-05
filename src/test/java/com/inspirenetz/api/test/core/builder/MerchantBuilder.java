package com.inspirenetz.api.test.core.builder;

import com.inspirenetz.api.core.dictionary.*;
import com.inspirenetz.api.core.domain.Merchant;

import java.sql.Date;

/**
 * Created by sandheepgr on 14/5/14.
 */
public class MerchantBuilder {
    private Long merMerchantNo;
    private String merMerchantName;
    private String merUrlName;
    private int merExclusiveInd = IndicatorStatus.NO;
    private String merAddress1 = "";
    private String merAddress2 = "";
    private String merAddress3 = "";
    private String merCity;
    private String merState;
    private int merCountry = 356;
    private String merPostCode;
    private String merContactName;
    private String merContactEmail;
    private String merPhoneNo;
    private String merEmail;
    private Date merActivationDate;
    private int merStatus = MerchantStatus.ACCOUNT_ACTIVE;
    private int merSubscriptionPlan = 1;
    private int merLoyaltyIdType = MerchantLoyaltyIdType.GENERATED;
    private String merLoyaltyIdFrom ="0";
    private String merLoyaltyIdTo = "0";
    private int merMaxCustomers = 2000;
    private int merMaxUsers = 4;
    private String merSignupType = "";
    private String merAutoSignupEnable = IndicatorStatusChar.YES;
    private String merMembershipName;
    private String merPaymentModes = "";
    private String merCustIdTypes = "";
    private Long merMerchantImage = ImagePrimaryId.PRIMARY_MERCHANT_LOGO;
    private Long merCoverImage = ImagePrimaryId.PRIMARY_MERCHANT_COVER_IMAGE;
    private int merSignupRewardEnabledInd = IndicatorStatus.NO;
    private int merSignupRewardCurrency =0;
    private double merSignupRewardPoints = 0;
    private java.util.Date createdAt;
    private String createdBy;
    private java.util.Date updatedAt;
    private String updatedBy;

    private MerchantBuilder() {
    }

    public static MerchantBuilder aMerchant() {
        return new MerchantBuilder();
    }

    public MerchantBuilder withMerMerchantNo(Long merMerchantNo) {
        this.merMerchantNo = merMerchantNo;
        return this;
    }

    public MerchantBuilder withMerMerchantName(String merMerchantName) {
        this.merMerchantName = merMerchantName;
        return this;
    }

    public MerchantBuilder withMerUrlName(String merUrlName) {
        this.merUrlName = merUrlName;
        return this;
    }

    public MerchantBuilder withMerExclusiveInd(int merExclusiveInd) {
        this.merExclusiveInd = merExclusiveInd;
        return this;
    }

    public MerchantBuilder withMerAddress1(String merAddress1) {
        this.merAddress1 = merAddress1;
        return this;
    }

    public MerchantBuilder withMerAddress2(String merAddress2) {
        this.merAddress2 = merAddress2;
        return this;
    }

    public MerchantBuilder withMerAddress3(String merAddress3) {
        this.merAddress3 = merAddress3;
        return this;
    }

    public MerchantBuilder withMerCity(String merCity) {
        this.merCity = merCity;
        return this;
    }

    public MerchantBuilder withMerState(String merState) {
        this.merState = merState;
        return this;
    }

    public MerchantBuilder withMerCountry(int merCountry) {
        this.merCountry = merCountry;
        return this;
    }

    public MerchantBuilder withMerPostCode(String merPostCode) {
        this.merPostCode = merPostCode;
        return this;
    }

    public MerchantBuilder withMerContactName(String merContactName) {
        this.merContactName = merContactName;
        return this;
    }

    public MerchantBuilder withMerContactEmail(String merContactEmail) {
        this.merContactEmail = merContactEmail;
        return this;
    }

    public MerchantBuilder withMerPhoneNo(String merPhoneNo) {
        this.merPhoneNo = merPhoneNo;
        return this;
    }

    public MerchantBuilder withMerEmail(String merEmail) {
        this.merEmail = merEmail;
        return this;
    }

    public MerchantBuilder withMerActivationDate(Date merActivationDate) {
        this.merActivationDate = merActivationDate;
        return this;
    }

    public MerchantBuilder withMerStatus(int merStatus) {
        this.merStatus = merStatus;
        return this;
    }

    public MerchantBuilder withMerSubscriptionPlan(int merSubscriptionPlan) {
        this.merSubscriptionPlan = merSubscriptionPlan;
        return this;
    }

    public MerchantBuilder withMerLoyaltyIdType(int merLoyaltyIdType) {
        this.merLoyaltyIdType = merLoyaltyIdType;
        return this;
    }

    public MerchantBuilder withMerLoyaltyIdFrom(String merLoyaltyIdFrom) {
        this.merLoyaltyIdFrom = merLoyaltyIdFrom;
        return this;
    }

    public MerchantBuilder withMerLoyaltyIdTo(String merLoyaltyIdTo) {
        this.merLoyaltyIdTo = merLoyaltyIdTo;
        return this;
    }

    public MerchantBuilder withMerMaxCustomers(int merMaxCustomers) {
        this.merMaxCustomers = merMaxCustomers;
        return this;
    }

    public MerchantBuilder withMerMaxUsers(int merMaxUsers) {
        this.merMaxUsers = merMaxUsers;
        return this;
    }

    public MerchantBuilder withMerSignupType(String merSignupType) {
        this.merSignupType = merSignupType;
        return this;
    }

    public MerchantBuilder withMerAutoSignupEnable(String merAutoSignupEnable) {
        this.merAutoSignupEnable = merAutoSignupEnable;
        return this;
    }

    public MerchantBuilder withMerMembershipName(String merMembershipName) {
        this.merMembershipName = merMembershipName;
        return this;
    }

    public MerchantBuilder withMerPaymentModes(String merPaymentModes) {
        this.merPaymentModes = merPaymentModes;
        return this;
    }

    public MerchantBuilder withMerCustIdTypes(String merCustIdTypes) {
        this.merCustIdTypes = merCustIdTypes;
        return this;
    }

    public MerchantBuilder withMerMerchantImage(Long merMerchantImage) {
        this.merMerchantImage = merMerchantImage;
        return this;
    }

    public MerchantBuilder withMerCoverImage(Long merCoverImage) {
        this.merCoverImage = merCoverImage;
        return this;
    }

    public MerchantBuilder withMerSignupRewardEnabledInd(int merSignupRewardEnabledInd) {
        this.merSignupRewardEnabledInd = merSignupRewardEnabledInd;
        return this;
    }

    public MerchantBuilder withMerSignupRewardCurrency(int merSignupRewardCurrency) {
        this.merSignupRewardCurrency = merSignupRewardCurrency;
        return this;
    }

    public MerchantBuilder withMerSignupRewardPoints(double merSignupRewardPoints) {
        this.merSignupRewardPoints = merSignupRewardPoints;
        return this;
    }

    public MerchantBuilder withCreatedAt(java.util.Date createdAt) {
        this.createdAt = createdAt;
        return this;
    }

    public MerchantBuilder withCreatedBy(String createdBy) {
        this.createdBy = createdBy;
        return this;
    }

    public MerchantBuilder withUpdatedAt(java.util.Date updatedAt) {
        this.updatedAt = updatedAt;
        return this;
    }

    public MerchantBuilder withUpdatedBy(String updatedBy) {
        this.updatedBy = updatedBy;
        return this;
    }

    public Merchant build() {
        Merchant merchant = new Merchant();
        merchant.setMerMerchantNo(merMerchantNo);
        merchant.setMerMerchantName(merMerchantName);
        merchant.setMerUrlName(merUrlName);
        merchant.setMerExclusiveInd(merExclusiveInd);
        merchant.setMerAddress1(merAddress1);
        merchant.setMerAddress2(merAddress2);
        merchant.setMerAddress3(merAddress3);
        merchant.setMerCity(merCity);
        merchant.setMerState(merState);
        merchant.setMerCountry(merCountry);
        merchant.setMerPostCode(merPostCode);
        merchant.setMerContactName(merContactName);
        merchant.setMerContactEmail(merContactEmail);
        merchant.setMerPhoneNo(merPhoneNo);
        merchant.setMerEmail(merEmail);
        merchant.setMerActivationDate(merActivationDate);
        merchant.setMerStatus(merStatus);
        merchant.setMerSubscriptionPlan(merSubscriptionPlan);
        merchant.setMerLoyaltyIdType(merLoyaltyIdType);
        merchant.setMerLoyaltyIdFrom(merLoyaltyIdFrom);
        merchant.setMerLoyaltyIdTo(merLoyaltyIdTo);
        merchant.setMerMaxCustomers(merMaxCustomers);
        merchant.setMerMaxUsers(merMaxUsers);
        merchant.setMerSignupType(merSignupType);
        merchant.setMerAutoSignupEnable(merAutoSignupEnable);
        merchant.setMerMembershipName(merMembershipName);
        merchant.setMerPaymentModes(merPaymentModes);
        merchant.setMerCustIdTypes(merCustIdTypes);
        merchant.setMerMerchantImage(merMerchantImage);
        merchant.setMerCoverImage(merCoverImage);
        merchant.setMerSignupRewardEnabledInd(merSignupRewardEnabledInd);
        merchant.setMerSignupRewardCurrency(merSignupRewardCurrency);
        merchant.setMerSignupRewardPoints(merSignupRewardPoints);
        merchant.setCreatedAt(createdAt);
        merchant.setCreatedBy(createdBy);
        merchant.setUpdatedAt(updatedAt);
        merchant.setUpdatedBy(updatedBy);
        return merchant;
    }
}
