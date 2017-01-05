package com.inspirenetz.api.test.core.builder;

import com.inspirenetz.api.core.dictionary.PurchaseStatus;
import com.inspirenetz.api.core.dictionary.SaleType;
import com.inspirenetz.api.core.domain.Purchase;

import java.sql.Date;
import java.sql.Time;
import java.sql.Timestamp;

/**
 * Created by sandheepgr on 6/5/14.
 */
public class PurchaseBuilder {
    private Long prcId;
    private Long prcMerchantNo;
    private int prcType = SaleType.STANDARD_PURCHASE;
    private Date prcDate;
    private Time prcTime;
    private String prcLoyaltyId;
    private Double prcAmount = new Double(0);
    private int prcCurrency = 356;
    private int prcPaymentMode = 1;
    private Long prcLocation =  0L;
    private int prcTxnChannel = 1;
    private String prcPaymentReference;
    private int prcStatus = PurchaseStatus.NEW;
    private Double prcQuantity = new Double(0);
    private int prcDayOfWeek = 0;
    private Timestamp prcTimestamp;
    private Long prcUserNo = 0L;
    private java.util.Date createdAt;
    private String createdBy;
    private java.util.Date updatedAt;
    private String updatedBy;

    private PurchaseBuilder() {
    }

    public static PurchaseBuilder aPurchase() {
        return new PurchaseBuilder();
    }

    public PurchaseBuilder withPrcId(Long prcId) {
        this.prcId = prcId;
        return this;
    }

    public PurchaseBuilder withPrcMerchantNo(Long prcMerchantNo) {
        this.prcMerchantNo = prcMerchantNo;
        return this;
    }

    public PurchaseBuilder withPrcType(int prcType) {
        this.prcType = prcType;
        return this;
    }

    public PurchaseBuilder withPrcDate(Date prcDate) {
        this.prcDate = prcDate;
        return this;
    }

    public PurchaseBuilder withPrcTime(Time prcTime) {
        this.prcTime = prcTime;
        return this;
    }

    public PurchaseBuilder withPrcLoyaltyId(String prcLoyaltyId) {
        this.prcLoyaltyId = prcLoyaltyId;
        return this;
    }

    public PurchaseBuilder withPrcAmount(Double prcAmount) {
        this.prcAmount = prcAmount;
        return this;
    }

    public PurchaseBuilder withPrcCurrency(int prcCurrency) {
        this.prcCurrency = prcCurrency;
        return this;
    }

    public PurchaseBuilder withPrcPaymentMode(int prcPaymentMode) {
        this.prcPaymentMode = prcPaymentMode;
        return this;
    }

    public PurchaseBuilder withPrcLocation(Long prcLocation) {
        this.prcLocation = prcLocation;
        return this;
    }

    public PurchaseBuilder withPrcTxnChannel(int prcTxnChannel) {
        this.prcTxnChannel = prcTxnChannel;
        return this;
    }

    public PurchaseBuilder withPrcPaymentReference(String prcPaymentReference) {
        this.prcPaymentReference = prcPaymentReference;
        return this;
    }

    public PurchaseBuilder withPrcStatus(int prcStatus) {
        this.prcStatus = prcStatus;
        return this;
    }

    public PurchaseBuilder withPrcQuantity(Double prcQuantity) {
        this.prcQuantity = prcQuantity;
        return this;
    }

    public PurchaseBuilder withPrcDayOfWeek(int prcDayOfWeek) {
        this.prcDayOfWeek = prcDayOfWeek;
        return this;
    }

    public PurchaseBuilder withPrcTimestamp(Timestamp prcTimestamp) {
        this.prcTimestamp = prcTimestamp;
        return this;
    }

    public PurchaseBuilder withPrcUserNo(Long prcUserNo) {
        this.prcUserNo = prcUserNo;
        return this;
    }

    public PurchaseBuilder withCreatedAt(java.util.Date createdAt) {
        this.createdAt = createdAt;
        return this;
    }

    public PurchaseBuilder withCreatedBy(String createdBy) {
        this.createdBy = createdBy;
        return this;
    }

    public PurchaseBuilder withUpdatedAt(java.util.Date updatedAt) {
        this.updatedAt = updatedAt;
        return this;
    }

    public PurchaseBuilder withUpdatedBy(String updatedBy) {
        this.updatedBy = updatedBy;
        return this;
    }

    public Purchase build() {
        Purchase purchase = new Purchase();
        purchase.setPrcId(prcId);
        purchase.setPrcMerchantNo(prcMerchantNo);
        purchase.setPrcType(prcType);
        purchase.setPrcDate(prcDate);
        purchase.setPrcTime(prcTime);
        purchase.setPrcLoyaltyId(prcLoyaltyId);
        purchase.setPrcAmount(prcAmount);
        purchase.setPrcCurrency(prcCurrency);
        purchase.setPrcPaymentMode(prcPaymentMode);
        purchase.setPrcLocation(prcLocation);
        purchase.setPrcTxnChannel(prcTxnChannel);
        purchase.setPrcPaymentReference(prcPaymentReference);
        purchase.setPrcStatus(prcStatus);
        purchase.setPrcQuantity(prcQuantity);
        purchase.setPrcDayOfWeek(prcDayOfWeek);
        purchase.setPrcTimestamp(prcTimestamp);
        purchase.setPrcUserNo(prcUserNo);
        purchase.setCreatedAt(createdAt);
        purchase.setCreatedBy(createdBy);
        purchase.setUpdatedAt(updatedAt);
        purchase.setUpdatedBy(updatedBy);
        return purchase;
    }
}
