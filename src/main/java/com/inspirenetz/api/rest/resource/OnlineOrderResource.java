package com.inspirenetz.api.rest.resource;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.inspirenetz.api.core.dictionary.CashPaymentStatus;
import com.inspirenetz.api.core.dictionary.IndicatorStatus;
import com.inspirenetz.api.core.dictionary.OnlineOrderStatus;

import java.sql.Time;
import java.sql.Timestamp;
import java.util.Date;

/**
 * Created by sandheepgr on 15/4/14.
 */
public class OnlineOrderResource extends BaseResource {

    private Long ordId;

    @JsonFormat(shape=JsonFormat.Shape.STRING, pattern="yyyy-MM-dd")
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

    @JsonFormat(shape=JsonFormat.Shape.STRING, pattern="yyyy-MM-dd hh:mm:ss")
    private Timestamp ordTimestamp;

    private Long ordUserNo = 0L;

    private Integer ordUniqueBatchTrackingId = 0;

    private String ordDeliveryCourierInfo = "";

    private String ordDeliveryCourierTracking = "";


    public Long getOrdId() {
        return ordId;
    }

    public void setOrdId(Long ordId) {
        this.ordId = ordId;
    }

    public Date getOrdDate() {
        return ordDate;
    }

    public void setOrdDate(Date ordDate) {
        this.ordDate = ordDate;
    }

    public Time getOrdTime() {
        return ordTime;
    }

    public void setOrdTime(Time ordTime) {
        this.ordTime = ordTime;
    }

    public Long getOrdMerchantNo() {
        return ordMerchantNo;
    }

    public void setOrdMerchantNo(Long ordMerchantNo) {
        this.ordMerchantNo = ordMerchantNo;
    }

    public Integer getOrdStatus() {
        return ordStatus;
    }

    public void setOrdStatus(Integer ordStatus) {
        this.ordStatus = ordStatus;
    }

    public String getOrdProductCode() {
        return ordProductCode;
    }

    public void setOrdProductCode(String ordProductCode) {
        this.ordProductCode = ordProductCode;
    }

    public Double getOrdQty() {
        return ordQty;
    }

    public void setOrdQty(Double ordQty) {
        this.ordQty = ordQty;
    }

    public String getOrdLoyaltyId() {
        return ordLoyaltyId;
    }

    public void setOrdLoyaltyId(String ordLoyaltyId) {
        this.ordLoyaltyId = ordLoyaltyId;
    }

    public Long getOrdOrderSlot() {
        return ordOrderSlot;
    }

    public void setOrdOrderSlot(Long ordOrderSlot) {
        this.ordOrderSlot = ordOrderSlot;
    }

    public Long getOrdOrderLocation() {
        return ordOrderLocation;
    }

    public void setOrdOrderLocation(Long ordOrderLocation) {
        this.ordOrderLocation = ordOrderLocation;
    }

    public Integer getOrdDeliveryInd() {
        return ordDeliveryInd;
    }

    public void setOrdDeliveryInd(Integer ordDeliveryInd) {
        this.ordDeliveryInd = ordDeliveryInd;
    }

    public String getOrdDeliveryAddr1() {
        return ordDeliveryAddr1;
    }

    public void setOrdDeliveryAddr1(String ordDeliveryAddr1) {
        this.ordDeliveryAddr1 = ordDeliveryAddr1;
    }

    public String getOrdDeliveryAddr2() {
        return ordDeliveryAddr2;
    }

    public void setOrdDeliveryAddr2(String ordDeliveryAddr2) {
        this.ordDeliveryAddr2 = ordDeliveryAddr2;
    }

    public String getOrdDeliveryAddr3() {
        return ordDeliveryAddr3;
    }

    public void setOrdDeliveryAddr3(String ordDeliveryAddr3) {
        this.ordDeliveryAddr3 = ordDeliveryAddr3;
    }

    public String getOrdDeliveryCity() {
        return ordDeliveryCity;
    }

    public void setOrdDeliveryCity(String ordDeliveryCity) {
        this.ordDeliveryCity = ordDeliveryCity;
    }

    public String getOrdDeliveryState() {
        return ordDeliveryState;
    }

    public void setOrdDeliveryState(String ordDeliveryState) {
        this.ordDeliveryState = ordDeliveryState;
    }

    public String getOrdDeliveryCountry() {
        return ordDeliveryCountry;
    }

    public void setOrdDeliveryCountry(String ordDeliveryCountry) {
        this.ordDeliveryCountry = ordDeliveryCountry;
    }

    public String getOrdDeliveryPostcode() {
        return ordDeliveryPostcode;
    }

    public void setOrdDeliveryPostcode(String ordDeliveryPostcode) {
        this.ordDeliveryPostcode = ordDeliveryPostcode;
    }

    public String getOrdContactNumber() {
        return ordContactNumber;
    }

    public void setOrdContactNumber(String ordContactNumber) {
        this.ordContactNumber = ordContactNumber;
    }

    public Double getOrdAmount() {
        return ordAmount;
    }

    public void setOrdAmount(Double ordAmount) {
        this.ordAmount = ordAmount;
    }

    public Double getOrdAdditionalCharges() {
        return ordAdditionalCharges;
    }

    public void setOrdAdditionalCharges(Double ordAdditionalCharges) {
        this.ordAdditionalCharges = ordAdditionalCharges;
    }

    public Integer getOrdCashPaymentStatus() {
        return ordCashPaymentStatus;
    }

    public void setOrdCashPaymentStatus(Integer ordCashPaymentStatus) {
        this.ordCashPaymentStatus = ordCashPaymentStatus;
    }

    public String getOrdRef() {
        return ordRef;
    }

    public void setOrdRef(String ordRef) {
        this.ordRef = ordRef;
    }

    public Timestamp getOrdTimestamp() {
        return ordTimestamp;
    }

    public void setOrdTimestamp(Timestamp ordTimestamp) {
        this.ordTimestamp = ordTimestamp;
    }

    public Long getOrdUserNo() {
        return ordUserNo;
    }

    public void setOrdUserNo(Long ordUserNo) {
        this.ordUserNo = ordUserNo;
    }

    public Integer getOrdUniqueBatchTrackingId() {
        return ordUniqueBatchTrackingId;
    }

    public void setOrdUniqueBatchTrackingId(Integer ordUniqueBatchTrackingId) {
        this.ordUniqueBatchTrackingId = ordUniqueBatchTrackingId;
    }

    public String getOrdDeliveryCourierInfo() {
        return ordDeliveryCourierInfo;
    }

    public void setOrdDeliveryCourierInfo(String ordDeliveryCourierInfo) {
        this.ordDeliveryCourierInfo = ordDeliveryCourierInfo;
    }

    public String getOrdDeliveryCourierTracking() {
        return ordDeliveryCourierTracking;
    }

    public void setOrdDeliveryCourierTracking(String ordDeliveryCourierTracking) {
        this.ordDeliveryCourierTracking = ordDeliveryCourierTracking;
    }


    @Override
    public String toString() {
        return "OnlineOrderResource{" +
                "ordId=" + ordId +
                ", ordDate=" + ordDate +
                ", ordTime=" + ordTime +
                ", ordMerchantNo=" + ordMerchantNo +
                ", ordStatus=" + ordStatus +
                ", ordProductCode='" + ordProductCode + '\'' +
                ", ordQty=" + ordQty +
                ", ordLoyaltyId='" + ordLoyaltyId + '\'' +
                ", ordOrderSlot=" + ordOrderSlot +
                ", ordOrderLocation=" + ordOrderLocation +
                ", ordDeliveryInd=" + ordDeliveryInd +
                ", ordDeliveryAddr1='" + ordDeliveryAddr1 + '\'' +
                ", ordDeliveryAddr2='" + ordDeliveryAddr2 + '\'' +
                ", ordDeliveryAddr3='" + ordDeliveryAddr3 + '\'' +
                ", ordDeliveryCity='" + ordDeliveryCity + '\'' +
                ", ordDeliveryState='" + ordDeliveryState + '\'' +
                ", ordDeliveryCountry='" + ordDeliveryCountry + '\'' +
                ", ordDeliveryPostcode='" + ordDeliveryPostcode + '\'' +
                ", ordContactNumber='" + ordContactNumber + '\'' +
                ", ordAmount=" + ordAmount +
                ", ordAdditionalCharges=" + ordAdditionalCharges +
                ", ordCashPaymentStatus=" + ordCashPaymentStatus +
                ", ordRef='" + ordRef + '\'' +
                ", ordTimestamp=" + ordTimestamp +
                ", ordUserNo=" + ordUserNo +
                ", ordUniqueBatchTrackingId=" + ordUniqueBatchTrackingId +
                ", ordDeliveryCourierInfo='" + ordDeliveryCourierInfo + '\'' +
                ", ordDeliveryCourierTracking='" + ordDeliveryCourierTracking + '\'' +
                '}';
    }
}
