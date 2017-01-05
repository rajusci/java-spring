package com.inspirenetz.api.test.core.builder;

import com.inspirenetz.api.core.dictionary.RedemptionVoucherStatus;
import com.inspirenetz.api.core.domain.RedemptionVoucher;

import java.sql.Date;

/**
 * Created by alameen on 9/12/14.
 */
public class RedemptionVoucherBuilder {
    private Long rvrId;
    private String rvrVoucherCode;
    private Long rvrCustomerNo;
    private Long rvrMerchant;
    private String rvrProductCode;
    private Integer rvrStatus = RedemptionVoucherStatus.NEW;
    private String rvrLoyaltyId;
    private Date rvrCreateDate;
    private Integer rvrVoucherType;
    private java.util.Date createdAt;
    private String createdBy;
    private java.util.Date updatedAt;
    private String updatedBy;

    private RedemptionVoucherBuilder() {
    }

    public static RedemptionVoucherBuilder aRedemptionVoucher() {
        return new RedemptionVoucherBuilder();
    }

    public RedemptionVoucherBuilder withRvrId(Long rvrId) {
        this.rvrId = rvrId;
        return this;
    }

    public RedemptionVoucherBuilder withRvrVoucherCode(String rvrVoucherCode) {
        this.rvrVoucherCode = rvrVoucherCode;
        return this;
    }

    public RedemptionVoucherBuilder withRvrCustomerNo(Long rvrCustomerNo) {
        this.rvrCustomerNo = rvrCustomerNo;
        return this;
    }

    public RedemptionVoucherBuilder withRvrMerchant(Long rvrMerchant) {
        this.rvrMerchant = rvrMerchant;
        return this;
    }

    public RedemptionVoucherBuilder withRvrProductCode(String rvrProductCode) {
        this.rvrProductCode = rvrProductCode;
        return this;
    }

    public RedemptionVoucherBuilder withRvrStatus(Integer rvrStatus) {
        this.rvrStatus = rvrStatus;
        return this;
    }

    public RedemptionVoucherBuilder withRvrLoyaltyId(String rvrLoyaltyId) {
        this.rvrLoyaltyId = rvrLoyaltyId;
        return this;
    }

    public RedemptionVoucherBuilder withRvrCreateDate(Date rvrCreateDate) {
        this.rvrCreateDate = rvrCreateDate;
        return this;
    }

    public RedemptionVoucherBuilder withRvrVoucherType(Integer rvrVoucherType) {
        this.rvrVoucherType = rvrVoucherType;
        return this;
    }

    public RedemptionVoucherBuilder withCreatedAt(java.util.Date createdAt) {
        this.createdAt = createdAt;
        return this;
    }

    public RedemptionVoucherBuilder withCreatedBy(String createdBy) {
        this.createdBy = createdBy;
        return this;
    }

    public RedemptionVoucherBuilder withUpdatedAt(java.util.Date updatedAt) {
        this.updatedAt = updatedAt;
        return this;
    }

    public RedemptionVoucherBuilder withUpdatedBy(String updatedBy) {
        this.updatedBy = updatedBy;
        return this;
    }

    public RedemptionVoucherBuilder but() {
        return aRedemptionVoucher().withRvrId(rvrId).withRvrVoucherCode(rvrVoucherCode).withRvrCustomerNo(rvrCustomerNo).withRvrMerchant(rvrMerchant).withRvrProductCode(rvrProductCode).withRvrStatus(rvrStatus).withRvrLoyaltyId(rvrLoyaltyId).withRvrCreateDate(rvrCreateDate).withRvrVoucherType(rvrVoucherType).withCreatedAt(createdAt).withCreatedBy(createdBy).withUpdatedAt(updatedAt).withUpdatedBy(updatedBy);
    }

    public RedemptionVoucher build() {
        RedemptionVoucher redemptionVoucher = new RedemptionVoucher();
        redemptionVoucher.setRvrId(rvrId);
        redemptionVoucher.setRvrVoucherCode(rvrVoucherCode);
        redemptionVoucher.setRvrCustomerNo(rvrCustomerNo);
        redemptionVoucher.setRvrMerchant(rvrMerchant);
        redemptionVoucher.setRvrProductCode(rvrProductCode);
        redemptionVoucher.setRvrStatus(rvrStatus);
        redemptionVoucher.setRvrLoyaltyId(rvrLoyaltyId);
        redemptionVoucher.setRvrCreateDate(rvrCreateDate);
        redemptionVoucher.setRvrVoucherType(rvrVoucherType);
        redemptionVoucher.setCreatedAt(createdAt);
        redemptionVoucher.setCreatedBy(createdBy);
        redemptionVoucher.setUpdatedAt(updatedAt);
        redemptionVoucher.setUpdatedBy(updatedBy);
        return redemptionVoucher;
    }
}
