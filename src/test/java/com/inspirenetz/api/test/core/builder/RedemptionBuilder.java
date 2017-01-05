package com.inspirenetz.api.test.core.builder;

import com.inspirenetz.api.core.dictionary.RedemptionStatus;
import com.inspirenetz.api.core.dictionary.RedemptionType;
import com.inspirenetz.api.core.domain.Product;
import com.inspirenetz.api.core.domain.Redemption;
import com.inspirenetz.api.core.domain.RewardCurrency;

import java.sql.Date;
import java.sql.Time;

/**
 * Created by saneesh-ci on 3/10/14.
 */
public class RedemptionBuilder {
    private Long rdmId;
    private Date rdmDate;
    private Time rdmTime;
    private Long rdmMerchantNo;
    private int rdmStatus = RedemptionStatus.RDM_STATUS_NEW;
    private int rdmType = RedemptionType.CASHBACK;
    private double rdmCashbackAmount = 0 ;
    private Long rdmRewardCurrencyId = 0L;
    private double rdmRewardCurrencyQty = 0;
    private String rdmProductCode = "";
    private double rdmQty = 1;
    private String rdmLoyaltyId;
    private int rdmDeliveryInd;
    private String rdmDeliveryAddr1;
    private String rdmDeliveryAddr2;
    private String rdmDeliveryAddr3;
    private String rdmDeliveryCity;
    private String rdmDeliveryState;
    private String rdmDeliveryCountry;
    private String rdmDeliveryPostcode;
    private String rdmContactNumber;
    private Double rdmCashAmount = 0.0 ;
    private int rdmCashPaymentStatus = 0;
    private String rdmRef;
    private String rdmMerchantRef;
    private Long rdmUserNo;
    private String rdmUniqueBatchTrackingId = "0";
    private String rdmDeliveryCourierInfo;
    private String rdmDeliveryCourierTracking;
    private String rdmExternalReference;
    private RewardCurrency rewardCurrency;
    private Product product;
    private java.util.Date createdAt;
    private String createdBy;
    private java.util.Date updatedAt;
    private String updatedBy;

    private RedemptionBuilder() {
    }

    public static RedemptionBuilder aRedemption() {
        return new RedemptionBuilder();
    }

    public RedemptionBuilder withRdmId(Long rdmId) {
        this.rdmId = rdmId;
        return this;
    }

    public RedemptionBuilder withRdmDate(Date rdmDate) {
        this.rdmDate = rdmDate;
        return this;
    }

    public RedemptionBuilder withRdmTime(Time rdmTime) {
        this.rdmTime = rdmTime;
        return this;
    }

    public RedemptionBuilder withRdmMerchantNo(Long rdmMerchantNo) {
        this.rdmMerchantNo = rdmMerchantNo;
        return this;
    }

    public RedemptionBuilder withRdmStatus(int rdmStatus) {
        this.rdmStatus = rdmStatus;
        return this;
    }

    public RedemptionBuilder withRdmType(int rdmType) {
        this.rdmType = rdmType;
        return this;
    }

    public RedemptionBuilder withRdmCashbackAmount(double rdmCashbackAmount) {
        this.rdmCashbackAmount = rdmCashbackAmount;
        return this;
    }

    public RedemptionBuilder withRdmRewardCurrencyId(Long rdmRewardCurrencyId) {
        this.rdmRewardCurrencyId = rdmRewardCurrencyId;
        return this;
    }

    public RedemptionBuilder withRdmRewardCurrencyQty(double rdmRewardCurrencyQty) {
        this.rdmRewardCurrencyQty = rdmRewardCurrencyQty;
        return this;
    }

    public RedemptionBuilder withRdmProductCode(String rdmProductCode) {
        this.rdmProductCode = rdmProductCode;
        return this;
    }

    public RedemptionBuilder withRdmQty(double rdmQty) {
        this.rdmQty = rdmQty;
        return this;
    }

    public RedemptionBuilder withRdmLoyaltyId(String rdmLoyaltyId) {
        this.rdmLoyaltyId = rdmLoyaltyId;
        return this;
    }

    public RedemptionBuilder withRdmDeliveryInd(int rdmDeliveryInd) {
        this.rdmDeliveryInd = rdmDeliveryInd;
        return this;
    }

    public RedemptionBuilder withRdmDeliveryAddr1(String rdmDeliveryAddr1) {
        this.rdmDeliveryAddr1 = rdmDeliveryAddr1;
        return this;
    }

    public RedemptionBuilder withRdmDeliveryAddr2(String rdmDeliveryAddr2) {
        this.rdmDeliveryAddr2 = rdmDeliveryAddr2;
        return this;
    }

    public RedemptionBuilder withRdmDeliveryAddr3(String rdmDeliveryAddr3) {
        this.rdmDeliveryAddr3 = rdmDeliveryAddr3;
        return this;
    }

    public RedemptionBuilder withRdmDeliveryCity(String rdmDeliveryCity) {
        this.rdmDeliveryCity = rdmDeliveryCity;
        return this;
    }

    public RedemptionBuilder withRdmDeliveryState(String rdmDeliveryState) {
        this.rdmDeliveryState = rdmDeliveryState;
        return this;
    }

    public RedemptionBuilder withRdmDeliveryCountry(String rdmDeliveryCountry) {
        this.rdmDeliveryCountry = rdmDeliveryCountry;
        return this;
    }

    public RedemptionBuilder withRdmDeliveryPostcode(String rdmDeliveryPostcode) {
        this.rdmDeliveryPostcode = rdmDeliveryPostcode;
        return this;
    }

    public RedemptionBuilder withRdmContactNumber(String rdmContactNumber) {
        this.rdmContactNumber = rdmContactNumber;
        return this;
    }

    public RedemptionBuilder withRdmCashAmount(Double rdmCashAmount) {
        this.rdmCashAmount = rdmCashAmount;
        return this;
    }

    public RedemptionBuilder withRdmCashPaymentStatus(int rdmCashPaymentStatus) {
        this.rdmCashPaymentStatus = rdmCashPaymentStatus;
        return this;
    }

    public RedemptionBuilder withRdmRef(String rdmRef) {
        this.rdmRef = rdmRef;
        return this;
    }

    public RedemptionBuilder withRdmMerchantRef(String rdmMerchantRef) {
        this.rdmMerchantRef = rdmMerchantRef;
        return this;
    }

    public RedemptionBuilder withRdmUserNo(Long rdmUserNo) {
        this.rdmUserNo = rdmUserNo;
        return this;
    }

    public RedemptionBuilder withRdmUniqueBatchTrackingId(String rdmUniqueBatchTrackingId) {
        this.rdmUniqueBatchTrackingId = rdmUniqueBatchTrackingId;
        return this;
    }

    public RedemptionBuilder withRdmDeliveryCourierInfo(String rdmDeliveryCourierInfo) {
        this.rdmDeliveryCourierInfo = rdmDeliveryCourierInfo;
        return this;
    }

    public RedemptionBuilder withRdmDeliveryCourierTracking(String rdmDeliveryCourierTracking) {
        this.rdmDeliveryCourierTracking = rdmDeliveryCourierTracking;
        return this;
    }

    public RedemptionBuilder withRdmExternalReference(String rdmExternalReference) {
        this.rdmExternalReference = rdmExternalReference;
        return this;
    }

    public RedemptionBuilder withRewardCurrency(RewardCurrency rewardCurrency) {
        this.rewardCurrency = rewardCurrency;
        return this;
    }

    public RedemptionBuilder withProduct(Product product) {
        this.product = product;
        return this;
    }

    public RedemptionBuilder withCreatedAt(java.util.Date createdAt) {
        this.createdAt = createdAt;
        return this;
    }

    public RedemptionBuilder withCreatedBy(String createdBy) {
        this.createdBy = createdBy;
        return this;
    }

    public RedemptionBuilder withUpdatedAt(java.util.Date updatedAt) {
        this.updatedAt = updatedAt;
        return this;
    }

    public RedemptionBuilder withUpdatedBy(String updatedBy) {
        this.updatedBy = updatedBy;
        return this;
    }

    public RedemptionBuilder but() {
        return aRedemption().withRdmId(rdmId).withRdmDate(rdmDate).withRdmTime(rdmTime).withRdmMerchantNo(rdmMerchantNo).withRdmStatus(rdmStatus).withRdmType(rdmType).withRdmCashbackAmount(rdmCashbackAmount).withRdmRewardCurrencyId(rdmRewardCurrencyId).withRdmRewardCurrencyQty(rdmRewardCurrencyQty).withRdmProductCode(rdmProductCode).withRdmQty(rdmQty).withRdmLoyaltyId(rdmLoyaltyId).withRdmDeliveryInd(rdmDeliveryInd).withRdmDeliveryAddr1(rdmDeliveryAddr1).withRdmDeliveryAddr2(rdmDeliveryAddr2).withRdmDeliveryAddr3(rdmDeliveryAddr3).withRdmDeliveryCity(rdmDeliveryCity).withRdmDeliveryState(rdmDeliveryState).withRdmDeliveryCountry(rdmDeliveryCountry).withRdmDeliveryPostcode(rdmDeliveryPostcode).withRdmContactNumber(rdmContactNumber).withRdmCashAmount(rdmCashAmount).withRdmCashPaymentStatus(rdmCashPaymentStatus).withRdmRef(rdmRef).withRdmMerchantRef(rdmMerchantRef).withRdmUserNo(rdmUserNo).withRdmUniqueBatchTrackingId(rdmUniqueBatchTrackingId).withRdmDeliveryCourierInfo(rdmDeliveryCourierInfo).withRdmDeliveryCourierTracking(rdmDeliveryCourierTracking).withRdmExternalReference(rdmExternalReference).withRewardCurrency(rewardCurrency).withProduct(product).withCreatedAt(createdAt).withCreatedBy(createdBy).withUpdatedAt(updatedAt).withUpdatedBy(updatedBy);
    }

    public Redemption build() {
        Redemption redemption = new Redemption();
        redemption.setRdmId(rdmId);
        redemption.setRdmDate(rdmDate);
        redemption.setRdmTime(rdmTime);
        redemption.setRdmMerchantNo(rdmMerchantNo);
        redemption.setRdmStatus(rdmStatus);
        redemption.setRdmType(rdmType);
        redemption.setRdmCashbackAmount(rdmCashbackAmount);
        redemption.setRdmRewardCurrencyId(rdmRewardCurrencyId);
        redemption.setRdmRewardCurrencyQty(rdmRewardCurrencyQty);
        redemption.setRdmProductCode(rdmProductCode);
        redemption.setRdmQty(rdmQty);
        redemption.setRdmLoyaltyId(rdmLoyaltyId);
        redemption.setRdmDeliveryInd(rdmDeliveryInd);
        redemption.setRdmDeliveryAddr1(rdmDeliveryAddr1);
        redemption.setRdmDeliveryAddr2(rdmDeliveryAddr2);
        redemption.setRdmDeliveryAddr3(rdmDeliveryAddr3);
        redemption.setRdmDeliveryCity(rdmDeliveryCity);
        redemption.setRdmDeliveryState(rdmDeliveryState);
        redemption.setRdmDeliveryCountry(rdmDeliveryCountry);
        redemption.setRdmDeliveryPostcode(rdmDeliveryPostcode);
        redemption.setRdmContactNumber(rdmContactNumber);
        redemption.setRdmCashAmount(rdmCashAmount);
        redemption.setRdmCashPaymentStatus(rdmCashPaymentStatus);
        redemption.setRdmRef(rdmRef);
        redemption.setRdmMerchantRef(rdmMerchantRef);
        redemption.setRdmUserNo(rdmUserNo);
        redemption.setRdmUniqueBatchTrackingId(rdmUniqueBatchTrackingId);
        redemption.setRdmDeliveryCourierInfo(rdmDeliveryCourierInfo);
        redemption.setRdmDeliveryCourierTracking(rdmDeliveryCourierTracking);
        redemption.setRdmExternalReference(rdmExternalReference);
        redemption.setRewardCurrency(rewardCurrency);
       /* redemption.setProduct(product);*/
        redemption.setCreatedAt(createdAt);
        redemption.setCreatedBy(createdBy);
        redemption.setUpdatedAt(updatedAt);
        redemption.setUpdatedBy(updatedBy);
        return redemption;
    }
}
