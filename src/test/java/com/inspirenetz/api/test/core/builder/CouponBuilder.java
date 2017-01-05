package com.inspirenetz.api.test.core.builder;

import com.inspirenetz.api.core.dictionary.CouponAcceptType;
import com.inspirenetz.api.core.dictionary.CouponCodeType;
import com.inspirenetz.api.core.dictionary.CouponValueType;
import com.inspirenetz.api.core.dictionary.ImagePrimaryId;
import com.inspirenetz.api.core.domain.Coupon;

import java.sql.Date;

/**
 * Created by sandheepgr on 17/6/14.
 */
public class CouponBuilder {
    private Long cpnCouponId;
    private Long cpnMerchantNo;
    private String cpnCouponName = "";
    private String cpnPromoName = "";
    private String cpnCouponText = "";
    private Integer cpnCouponCodeType = CouponCodeType.FIXED;
    private String cpnCouponCode = "";
    private String cpnCouponCodeFrom = "";
    private String cpnCouponCodeTo ="";
    private Integer cpnCurrency = 356;
    private Integer cpnValueType = CouponValueType.AMOUNT;
    private Double cpnValue = 0.0;
    private Double cpnCapAmount = 0.0;
    private Double cpnBulkOrderQuantity = 0.0;
    private Double cpnBulkOrderFreeUnits = 0.0;
    private Double cpnMinTxnAmount = 0.0;
    private Date cpnExpiryDt;
    private Integer cpnAcceptType = CouponAcceptType.NO_LIMIT;
    private Integer cpnAcceptLimit = 0;
    private Integer cpnAcceptCount = 0;
    private Integer cpnMaxCouponsPerCustomer = 1;
    private Integer cpnMaxCouponsPerTransaction = 1;
    private Long cpnImage = ImagePrimaryId.PRIMARY_COUPON_IMAGE;
    private java.util.Date createdAt;
    private String createdBy;
    private java.util.Date updatedAt;
    private String updatedBy;

    private CouponBuilder() {
    }

    public static CouponBuilder aCoupon() {
        return new CouponBuilder();
    }

    public CouponBuilder withCpnCouponId(Long cpnCouponId) {
        this.cpnCouponId = cpnCouponId;
        return this;
    }

    public CouponBuilder withCpnMerchantNo(Long cpnMerchantNo) {
        this.cpnMerchantNo = cpnMerchantNo;
        return this;
    }

    public CouponBuilder withCpnCouponName(String cpnCouponName) {
        this.cpnCouponName = cpnCouponName;
        return this;
    }

    public CouponBuilder withCpnPromoName(String cpnPromoName) {
        this.cpnPromoName = cpnPromoName;
        return this;
    }

    public CouponBuilder withCpnCouponText(String cpnCouponText) {
        this.cpnCouponText = cpnCouponText;
        return this;
    }

    public CouponBuilder withCpnCouponCodeType(Integer cpnCouponCodeType) {
        this.cpnCouponCodeType = cpnCouponCodeType;
        return this;
    }

    public CouponBuilder withCpnCouponCode(String cpnCouponCode) {
        this.cpnCouponCode = cpnCouponCode;
        return this;
    }

    public CouponBuilder withCpnCouponCodeFrom(String cpnCouponCodeFrom) {
        this.cpnCouponCodeFrom = cpnCouponCodeFrom;
        return this;
    }

    public CouponBuilder withCpnCouponCodeTo(String cpnCouponCodeTo) {
        this.cpnCouponCodeTo = cpnCouponCodeTo;
        return this;
    }

    public CouponBuilder withCpnCurrency(Integer cpnCurrency) {
        this.cpnCurrency = cpnCurrency;
        return this;
    }

    public CouponBuilder withCpnValueType(Integer cpnValueType) {
        this.cpnValueType = cpnValueType;
        return this;
    }

    public CouponBuilder withCpnValue(Double cpnValue) {
        this.cpnValue = cpnValue;
        return this;
    }

    public CouponBuilder withCpnCapAmount(Double cpnCapAmount) {
        this.cpnCapAmount = cpnCapAmount;
        return this;
    }

    public CouponBuilder withCpnBulkOrderQuantity(Double cpnBulkOrderQuantity) {
        this.cpnBulkOrderQuantity = cpnBulkOrderQuantity;
        return this;
    }

    public CouponBuilder withCpnBulkOrderFreeUnits(Double cpnBulkOrderFreeUnits) {
        this.cpnBulkOrderFreeUnits = cpnBulkOrderFreeUnits;
        return this;
    }

    public CouponBuilder withCpnMinTxnAmount(Double cpnMinTxnAmount) {
        this.cpnMinTxnAmount = cpnMinTxnAmount;
        return this;
    }

    public CouponBuilder withCpnExpiryDt(Date cpnExpiryDt) {
        this.cpnExpiryDt = cpnExpiryDt;
        return this;
    }

    public CouponBuilder withCpnAcceptType(Integer cpnAcceptType) {
        this.cpnAcceptType = cpnAcceptType;
        return this;
    }

    public CouponBuilder withCpnAcceptLimit(Integer cpnAcceptLimit) {
        this.cpnAcceptLimit = cpnAcceptLimit;
        return this;
    }

    public CouponBuilder withCpnAcceptCount(Integer cpnAcceptCount) {
        this.cpnAcceptCount = cpnAcceptCount;
        return this;
    }

    public CouponBuilder withCpnMaxCouponsPerCustomer(Integer cpnMaxCouponsPerCustomer) {
        this.cpnMaxCouponsPerCustomer = cpnMaxCouponsPerCustomer;
        return this;
    }

    public CouponBuilder withCpnMaxCouponsPerTransaction(Integer cpnMaxCouponsPerTransaction) {
        this.cpnMaxCouponsPerTransaction = cpnMaxCouponsPerTransaction;
        return this;
    }

    public CouponBuilder withCpnImage(Long cpnImage) {
        this.cpnImage = cpnImage;
        return this;
    }

    public CouponBuilder withCreatedAt(java.util.Date createdAt) {
        this.createdAt = createdAt;
        return this;
    }

    public CouponBuilder withCreatedBy(String createdBy) {
        this.createdBy = createdBy;
        return this;
    }

    public CouponBuilder withUpdatedAt(java.util.Date updatedAt) {
        this.updatedAt = updatedAt;
        return this;
    }

    public CouponBuilder withUpdatedBy(String updatedBy) {
        this.updatedBy = updatedBy;
        return this;
    }

    public Coupon build() {
        Coupon coupon = new Coupon();
        coupon.setCpnCouponId(cpnCouponId);
        coupon.setCpnMerchantNo(cpnMerchantNo);
        coupon.setCpnCouponName(cpnCouponName);
        coupon.setCpnPromoName(cpnPromoName);
        coupon.setCpnCouponText(cpnCouponText);
        coupon.setCpnCouponCodeType(cpnCouponCodeType);
        coupon.setCpnCouponCode(cpnCouponCode);
        coupon.setCpnCouponCodeFrom(cpnCouponCodeFrom);
        coupon.setCpnCouponCodeTo(cpnCouponCodeTo);
        coupon.setCpnCurrency(cpnCurrency);
        coupon.setCpnValueType(cpnValueType);
        coupon.setCpnValue(cpnValue);
        coupon.setCpnCapAmount(cpnCapAmount);
        coupon.setCpnBulkOrderQuantity(cpnBulkOrderQuantity);
        coupon.setCpnBulkOrderFreeUnits(cpnBulkOrderFreeUnits);
        coupon.setCpnMinTxnAmount(cpnMinTxnAmount);
        coupon.setCpnExpiryDt(cpnExpiryDt);
        coupon.setCpnAcceptType(cpnAcceptType);
        coupon.setCpnAcceptLimit(cpnAcceptLimit);
        coupon.setCpnAcceptCount(cpnAcceptCount);
        coupon.setCpnMaxCouponsPerCustomer(cpnMaxCouponsPerCustomer);
        coupon.setCpnMaxCouponsPerTransaction(cpnMaxCouponsPerTransaction);
        coupon.setCpnImage(cpnImage);
        coupon.setCreatedAt(createdAt);
        coupon.setCreatedBy(createdBy);
        coupon.setUpdatedAt(updatedAt);
        coupon.setUpdatedBy(updatedBy);
        return coupon;
    }
}
