package com.inspirenetz.api.test.core.builder;

import com.inspirenetz.api.core.dictionary.CashPaymentStatus;
import com.inspirenetz.api.core.dictionary.IndicatorStatus;
import com.inspirenetz.api.core.dictionary.OnlineOrderStatus;
import com.inspirenetz.api.core.domain.OnlineOrder;

import java.sql.Date;
import java.sql.Time;
import java.sql.Timestamp;

/**
 * Created by sandheepgr on 30/7/14.
 */
public class OnlineOrderBuilder {
    private Long ordId;
    private Date ordDate;
    private Time ordTime;
    private Long ordMerchantNo = 0L;
    private Integer ordStatus = OnlineOrderStatus.NEW;
    private String ordProductCode = "";
    private Double ordQty =  1.0;
    private String ordLoyaltyId = "";
    private Long ordOrderSlot = 0L;
    private Long ordOrderLocation = 0L;
    private Integer ordDeliveryInd = IndicatorStatus.NO;
    private String ordDeliveryAddr1 = "";
    private String ordDeliveryAddr2 = "";
    private String ordDeliveryAddr3 = "";
    private String ordDeliveryCity ="";
    private String ordDeliveryState = "";
    private String ordDeliveryCountry ="";
    private String ordDeliveryPostcode = "";
    private String ordContactNumber = "";
    private Double ordAmount = 0.0;
    private Double ordAdditionalCharges = 0.0;
    private Integer ordCashPaymentStatus = CashPaymentStatus.NOT_PAID;
    private String ordRef = "";
    private Timestamp ordTimestamp;
    private Long ordUserNo = 0L;
    private Integer ordUniqueBatchTrackingId = 0;
    private String ordDeliveryCourierInfo = "";
    private String ordDeliveryCourierTracking = "";
    private java.util.Date createdAt;
    private String createdBy;
    private java.util.Date updatedAt;
    private String updatedBy;

    private OnlineOrderBuilder() {
    }

    public static OnlineOrderBuilder anOnlineOrder() {
        return new OnlineOrderBuilder();
    }

    public OnlineOrderBuilder withOrdId(Long ordId) {
        this.ordId = ordId;
        return this;
    }

    public OnlineOrderBuilder withOrdDate(Date ordDate) {
        this.ordDate = ordDate;
        return this;
    }

    public OnlineOrderBuilder withOrdTime(Time ordTime) {
        this.ordTime = ordTime;
        return this;
    }

    public OnlineOrderBuilder withOrdMerchantNo(Long ordMerchantNo) {
        this.ordMerchantNo = ordMerchantNo;
        return this;
    }

    public OnlineOrderBuilder withOrdStatus(Integer ordStatus) {
        this.ordStatus = ordStatus;
        return this;
    }

    public OnlineOrderBuilder withOrdProductCode(String ordProductCode) {
        this.ordProductCode = ordProductCode;
        return this;
    }

    public OnlineOrderBuilder withOrdQty(Double ordQty) {
        this.ordQty = ordQty;
        return this;
    }

    public OnlineOrderBuilder withOrdLoyaltyId(String ordLoyaltyId) {
        this.ordLoyaltyId = ordLoyaltyId;
        return this;
    }

    public OnlineOrderBuilder withOrdOrderSlot(Long ordOrderSlot) {
        this.ordOrderSlot = ordOrderSlot;
        return this;
    }

    public OnlineOrderBuilder withOrdOrderLocation(Long ordOrderLocation) {
        this.ordOrderLocation = ordOrderLocation;
        return this;
    }

    public OnlineOrderBuilder withOrdDeliveryInd(Integer ordDeliveryInd) {
        this.ordDeliveryInd = ordDeliveryInd;
        return this;
    }

    public OnlineOrderBuilder withOrdDeliveryAddr1(String ordDeliveryAddr1) {
        this.ordDeliveryAddr1 = ordDeliveryAddr1;
        return this;
    }

    public OnlineOrderBuilder withOrdDeliveryAddr2(String ordDeliveryAddr2) {
        this.ordDeliveryAddr2 = ordDeliveryAddr2;
        return this;
    }

    public OnlineOrderBuilder withOrdDeliveryAddr3(String ordDeliveryAddr3) {
        this.ordDeliveryAddr3 = ordDeliveryAddr3;
        return this;
    }

    public OnlineOrderBuilder withOrdDeliveryCity(String ordDeliveryCity) {
        this.ordDeliveryCity = ordDeliveryCity;
        return this;
    }

    public OnlineOrderBuilder withOrdDeliveryState(String ordDeliveryState) {
        this.ordDeliveryState = ordDeliveryState;
        return this;
    }

    public OnlineOrderBuilder withOrdDeliveryCountry(String ordDeliveryCountry) {
        this.ordDeliveryCountry = ordDeliveryCountry;
        return this;
    }

    public OnlineOrderBuilder withOrdDeliveryPostcode(String ordDeliveryPostcode) {
        this.ordDeliveryPostcode = ordDeliveryPostcode;
        return this;
    }

    public OnlineOrderBuilder withOrdContactNumber(String ordContactNumber) {
        this.ordContactNumber = ordContactNumber;
        return this;
    }

    public OnlineOrderBuilder withOrdAmount(Double ordAmount) {
        this.ordAmount = ordAmount;
        return this;
    }

    public OnlineOrderBuilder withOrdAdditionalCharges(Double ordAdditionalCharges) {
        this.ordAdditionalCharges = ordAdditionalCharges;
        return this;
    }

    public OnlineOrderBuilder withOrdCashPaymentStatus(Integer ordCashPaymentStatus) {
        this.ordCashPaymentStatus = ordCashPaymentStatus;
        return this;
    }

    public OnlineOrderBuilder withOrdRef(String ordRef) {
        this.ordRef = ordRef;
        return this;
    }

    public OnlineOrderBuilder withOrdTimestamp(Timestamp ordTimestamp) {
        this.ordTimestamp = ordTimestamp;
        return this;
    }

    public OnlineOrderBuilder withOrdUserNo(Long ordUserNo) {
        this.ordUserNo = ordUserNo;
        return this;
    }

    public OnlineOrderBuilder withOrdUniqueBatchTrackingId(Integer ordUniqueBatchTrackingId) {
        this.ordUniqueBatchTrackingId = ordUniqueBatchTrackingId;
        return this;
    }

    public OnlineOrderBuilder withOrdDeliveryCourierInfo(String ordDeliveryCourierInfo) {
        this.ordDeliveryCourierInfo = ordDeliveryCourierInfo;
        return this;
    }

    public OnlineOrderBuilder withOrdDeliveryCourierTracking(String ordDeliveryCourierTracking) {
        this.ordDeliveryCourierTracking = ordDeliveryCourierTracking;
        return this;
    }

    public OnlineOrderBuilder withCreatedAt(java.util.Date createdAt) {
        this.createdAt = createdAt;
        return this;
    }

    public OnlineOrderBuilder withCreatedBy(String createdBy) {
        this.createdBy = createdBy;
        return this;
    }

    public OnlineOrderBuilder withUpdatedAt(java.util.Date updatedAt) {
        this.updatedAt = updatedAt;
        return this;
    }

    public OnlineOrderBuilder withUpdatedBy(String updatedBy) {
        this.updatedBy = updatedBy;
        return this;
    }

    public OnlineOrder build() {
        OnlineOrder onlineOrder = new OnlineOrder();
        onlineOrder.setOrdId(ordId);
        onlineOrder.setOrdDate(ordDate);
        onlineOrder.setOrdTime(ordTime);
        onlineOrder.setOrdMerchantNo(ordMerchantNo);
        onlineOrder.setOrdStatus(ordStatus);
        onlineOrder.setOrdProductCode(ordProductCode);
        onlineOrder.setOrdQty(ordQty);
        onlineOrder.setOrdLoyaltyId(ordLoyaltyId);
        onlineOrder.setOrdOrderSlot(ordOrderSlot);
        onlineOrder.setOrdOrderLocation(ordOrderLocation);
        onlineOrder.setOrdDeliveryInd(ordDeliveryInd);
        onlineOrder.setOrdDeliveryAddr1(ordDeliveryAddr1);
        onlineOrder.setOrdDeliveryAddr2(ordDeliveryAddr2);
        onlineOrder.setOrdDeliveryAddr3(ordDeliveryAddr3);
        onlineOrder.setOrdDeliveryCity(ordDeliveryCity);
        onlineOrder.setOrdDeliveryState(ordDeliveryState);
        onlineOrder.setOrdDeliveryCountry(ordDeliveryCountry);
        onlineOrder.setOrdDeliveryPostcode(ordDeliveryPostcode);
        onlineOrder.setOrdContactNumber(ordContactNumber);
        onlineOrder.setOrdAmount(ordAmount);
        onlineOrder.setOrdAdditionalCharges(ordAdditionalCharges);
        onlineOrder.setOrdCashPaymentStatus(ordCashPaymentStatus);
        onlineOrder.setOrdRef(ordRef);
        onlineOrder.setOrdTimestamp(ordTimestamp);
        onlineOrder.setOrdUserNo(ordUserNo);
        onlineOrder.setOrdUniqueBatchTrackingId(ordUniqueBatchTrackingId);
        onlineOrder.setOrdDeliveryCourierInfo(ordDeliveryCourierInfo);
        onlineOrder.setOrdDeliveryCourierTracking(ordDeliveryCourierTracking);
        onlineOrder.setCreatedAt(createdAt);
        onlineOrder.setCreatedBy(createdBy);
        onlineOrder.setUpdatedAt(updatedAt);
        onlineOrder.setUpdatedBy(updatedBy);
        return onlineOrder;
    }
}
