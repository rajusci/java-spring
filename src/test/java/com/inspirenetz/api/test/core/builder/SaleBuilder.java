package com.inspirenetz.api.test.core.builder;

import com.inspirenetz.api.core.dictionary.PurchaseStatus;
import com.inspirenetz.api.core.dictionary.SaleType;
import com.inspirenetz.api.core.domain.Sale;
import com.inspirenetz.api.core.domain.SaleExtension;

import java.sql.Date;
import java.sql.Time;
import java.sql.Timestamp;
import java.util.Set;

/**
 * Created by sandheepgr on 13/8/14.
 */
public class SaleBuilder {
    private Long salId;
    private Long salMerchantNo = 0L;
    private String salLoyaltyId = "";
    private Long salLocation =0L;
    private Date salDate;
    private Time salTime;
    private int salType = SaleType.STANDARD_PURCHASE;
    private Double salAmount = new Double(0);
    private int salCurrency = 356;
    private int salPaymentMode = 1;
    private int salTxnChannel = 1;
    private Double salQuantity = new Double(0);
    private String salPaymentReference;
    private int salStatus = PurchaseStatus.NEW;
    private Timestamp salTimestamp;
    private Long salUserNo = 0L;
    private Set<SaleExtension> saleExtensionSet;
    private java.util.Date createdAt;
    private String createdBy;
    private java.util.Date updatedAt;
    private String updatedBy;

    private SaleBuilder() {
    }

    public static SaleBuilder aSale() {
        return new SaleBuilder();
    }

    public SaleBuilder withSalId(Long salId) {
        this.salId = salId;
        return this;
    }

    public SaleBuilder withSalMerchantNo(Long salMerchantNo) {
        this.salMerchantNo = salMerchantNo;
        return this;
    }

    public SaleBuilder withSalLoyaltyId(String salLoyaltyId) {
        this.salLoyaltyId = salLoyaltyId;
        return this;
    }

    public SaleBuilder withSalLocation(Long salLocation) {
        this.salLocation = salLocation;
        return this;
    }

    public SaleBuilder withSalDate(Date salDate) {
        this.salDate = salDate;
        return this;
    }

    public SaleBuilder withSalTime(Time salTime) {
        this.salTime = salTime;
        return this;
    }

    public SaleBuilder withSalType(int salType) {
        this.salType = salType;
        return this;
    }

    public SaleBuilder withSalAmount(Double salAmount) {
        this.salAmount = salAmount;
        return this;
    }

    public SaleBuilder withSalCurrency(int salCurrency) {
        this.salCurrency = salCurrency;
        return this;
    }

    public SaleBuilder withSalPaymentMode(int salPaymentMode) {
        this.salPaymentMode = salPaymentMode;
        return this;
    }

    public SaleBuilder withSalTxnChannel(int salTxnChannel) {
        this.salTxnChannel = salTxnChannel;
        return this;
    }

    public SaleBuilder withSalQuantity(Double salQuantity) {
        this.salQuantity = salQuantity;
        return this;
    }

    public SaleBuilder withSalPaymentReference(String salPaymentReference) {
        this.salPaymentReference = salPaymentReference;
        return this;
    }

    public SaleBuilder withSalStatus(int salStatus) {
        this.salStatus = salStatus;
        return this;
    }

    public SaleBuilder withSalTimestamp(Timestamp salTimestamp) {
        this.salTimestamp = salTimestamp;
        return this;
    }

    public SaleBuilder withSalUserNo(Long salUserNo) {
        this.salUserNo = salUserNo;
        return this;
    }

    public SaleBuilder withSaleInfoSet(Set<SaleExtension> saleExtensionSet) {
        this.saleExtensionSet = saleExtensionSet;
        return this;
    }

    public SaleBuilder withCreatedAt(java.util.Date createdAt) {
        this.createdAt = createdAt;
        return this;
    }

    public SaleBuilder withCreatedBy(String createdBy) {
        this.createdBy = createdBy;
        return this;
    }

    public SaleBuilder withUpdatedAt(java.util.Date updatedAt) {
        this.updatedAt = updatedAt;
        return this;
    }

    public SaleBuilder withUpdatedBy(String updatedBy) {
        this.updatedBy = updatedBy;
        return this;
    }

    public Sale build() {
        Sale sale = new Sale();
        sale.setSalId(salId);
        sale.setSalMerchantNo(salMerchantNo);
        sale.setSalLoyaltyId(salLoyaltyId);
        sale.setSalLocation(salLocation);
        sale.setSalDate(salDate);
        sale.setSalTime(salTime);
        sale.setSalType(salType);
        sale.setSalAmount(salAmount);
        sale.setSalCurrency(salCurrency);
        sale.setSalPaymentMode(salPaymentMode);
        sale.setSalTxnChannel(salTxnChannel);
        sale.setSalQuantity(salQuantity);
        sale.setSalPaymentReference(salPaymentReference);
        sale.setSalStatus(salStatus);
        sale.setSalTimestamp(salTimestamp);
        sale.setSalUserNo(salUserNo);
        sale.setSaleExtensionSet(saleExtensionSet);
        sale.setCreatedAt(createdAt);
        sale.setCreatedBy(createdBy);
        sale.setUpdatedAt(updatedAt);
        sale.setUpdatedBy(updatedBy);
        return sale;
    }
}
