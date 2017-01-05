package com.inspirenetz.api.test.core.builder;

import com.inspirenetz.api.core.domain.CouponTransaction;

import java.util.Date;

/**
 * Created by sandheepgr on 18/6/14.
 */
public class CouponTransactionBuilder {
    private Long cptId;
    private Long cptMerchantNo;
    private String cptCouponCode = "";
    private String cptLoyaltyId = "0";
    private Long cptPurchaseId = 0L;
    private int cptCouponCount = 0;
    private Date createdAt;
    private String createdBy;
    private Date updatedAt;
    private String updatedBy;

    private CouponTransactionBuilder() {
    }

    public static CouponTransactionBuilder aCouponTransaction() {
        return new CouponTransactionBuilder();
    }

    public CouponTransactionBuilder withCptId(Long cptId) {
        this.cptId = cptId;
        return this;
    }

    public CouponTransactionBuilder withCptMerchantNo(Long cptMerchantNo) {
        this.cptMerchantNo = cptMerchantNo;
        return this;
    }

    public CouponTransactionBuilder withCptCouponCode(String cptCouponCode) {
        this.cptCouponCode = cptCouponCode;
        return this;
    }

    public CouponTransactionBuilder withCptLoyaltyId(String cptLoyaltyId) {
        this.cptLoyaltyId = cptLoyaltyId;
        return this;
    }

    public CouponTransactionBuilder withCptPurchaseId(Long cptPurchaseId) {
        this.cptPurchaseId = cptPurchaseId;
        return this;
    }

    public CouponTransactionBuilder withCptCouponCount(int cptCouponCount) {
        this.cptCouponCount = cptCouponCount;
        return this;
    }

    public CouponTransactionBuilder withCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
        return this;
    }

    public CouponTransactionBuilder withCreatedBy(String createdBy) {
        this.createdBy = createdBy;
        return this;
    }

    public CouponTransactionBuilder withUpdatedAt(Date updatedAt) {
        this.updatedAt = updatedAt;
        return this;
    }

    public CouponTransactionBuilder withUpdatedBy(String updatedBy) {
        this.updatedBy = updatedBy;
        return this;
    }

    public CouponTransaction build() {
        CouponTransaction couponTransaction = new CouponTransaction();
        couponTransaction.setCptId(cptId);
        couponTransaction.setCptMerchantNo(cptMerchantNo);
        couponTransaction.setCptCouponCode(cptCouponCode);
        couponTransaction.setCptLoyaltyId(cptLoyaltyId);
        couponTransaction.setCptPurchaseId(cptPurchaseId);
        couponTransaction.setCptCouponCount(cptCouponCount);
        couponTransaction.setCreatedAt(createdAt);
        couponTransaction.setCreatedBy(createdBy);
        couponTransaction.setUpdatedAt(updatedAt);
        couponTransaction.setUpdatedBy(updatedBy);
        return couponTransaction;
    }
}
