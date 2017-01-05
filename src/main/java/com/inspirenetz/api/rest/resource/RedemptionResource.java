package com.inspirenetz.api.rest.resource;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.inspirenetz.api.core.dictionary.RequestChannel;
import com.inspirenetz.api.core.dictionary.RedemptionStatus;
import com.inspirenetz.api.core.dictionary.RedemptionType;

import java.sql.Time;
import java.sql.Date;

/**
 * Created by sandheepgr on 16/2/14.
 */
public class RedemptionResource extends BaseResource {


    private Long rdmId;

    @JsonFormat(shape=JsonFormat.Shape.STRING, pattern="yyyy-MM-dd")
    private Date rdmDate;

    @JsonFormat(shape=JsonFormat.Shape.STRING, pattern="hh:mm:ss")
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

    private Double rdmCashAmount;

    private int rdmCashPaymentStatus;

    private String rdmRef;

    private String rdmVendorName;

    private String rdmVendorContactNo;

    private String rdmMerchantRef;

    private Long rdmUserNo;

    private String rdmUniqueBatchTrackingId = "0";

    private String rdmDeliveryCourierInfo;

    private String rdmDeliveryCourierTracking;



    private String rwdCurrencyName;

    private String prdName;

    private String rdmExternalReference;

    private Integer rdmChannel = RequestChannel.RDM_CHANNEL_SMS;

    private String rdmLocation ="";

    private String rdmMerchantName ="";



    public Long getRdmId() {
        return rdmId;
    }

    public void setRdmId(Long rdmId) {
        this.rdmId = rdmId;
    }

    public Date getRdmDate() {
        return rdmDate;
    }

    public void setRdmDate(Date rdmDate) {
        this.rdmDate = rdmDate;
    }

    public Time getRdmTime() {
        return rdmTime;
    }

    public void setRdmTime(Time rdmTime) {
        this.rdmTime = rdmTime;
    }

    public Long getRdmMerchantNo() {
        return rdmMerchantNo;
    }

    public void setRdmMerchantNo(Long rdmMerchantNo) {
        this.rdmMerchantNo = rdmMerchantNo;
    }

    public int getRdmStatus() {
        return rdmStatus;
    }

    public void setRdmStatus(int rdmStatus) {
        this.rdmStatus = rdmStatus;
    }

    public int getRdmType() {
        return rdmType;
    }

    public void setRdmType(int rdmType) {
        this.rdmType = rdmType;
    }

    public double getRdmCashbackAmount() {
        return rdmCashbackAmount;
    }

    public void setRdmCashbackAmount(double rdmCashbackAmount) {
        this.rdmCashbackAmount = rdmCashbackAmount;
    }

    public Long getRdmRewardCurrencyId() {
        return rdmRewardCurrencyId;
    }

    public void setRdmRewardCurrencyId(Long rdmRewardCurrencyId) {
        this.rdmRewardCurrencyId = rdmRewardCurrencyId;
    }

    public double getRdmRewardCurrencyQty() {
        return rdmRewardCurrencyQty;
    }

    public void setRdmRewardCurrencyQty(double rdmRewardCurrencyQty) {
        this.rdmRewardCurrencyQty = rdmRewardCurrencyQty;
    }

    public String getRdmProductCode() {
        return rdmProductCode;
    }

    public void setRdmProductCode(String rdmProductCode) {
        this.rdmProductCode = rdmProductCode;
    }

    public double getRdmQty() {
        return rdmQty;
    }

    public void setRdmQty(double rdmQty) {
        this.rdmQty = rdmQty;
    }

    public String getRdmLoyaltyId() {
        return rdmLoyaltyId;
    }

    public void setRdmLoyaltyId(String rdmLoyaltyId) {
        this.rdmLoyaltyId = rdmLoyaltyId;
    }

    public int getRdmDeliveryInd() {
        return rdmDeliveryInd;
    }

    public void setRdmDeliveryInd(int rdmDeliveryInd) {
        this.rdmDeliveryInd = rdmDeliveryInd;
    }

    public String getRdmDeliveryAddr1() {
        return rdmDeliveryAddr1;
    }

    public void setRdmDeliveryAddr1(String rdmDeliveryAddr1) {
        this.rdmDeliveryAddr1 = rdmDeliveryAddr1;
    }

    public String getRdmDeliveryAddr2() {
        return rdmDeliveryAddr2;
    }

    public void setRdmDeliveryAddr2(String rdmDeliveryAddr2) {
        this.rdmDeliveryAddr2 = rdmDeliveryAddr2;
    }

    public String getRdmDeliveryAddr3() {
        return rdmDeliveryAddr3;
    }

    public void setRdmDeliveryAddr3(String rdmDeliveryAddr3) {
        this.rdmDeliveryAddr3 = rdmDeliveryAddr3;
    }

    public String getRdmDeliveryCity() {
        return rdmDeliveryCity;
    }

    public void setRdmDeliveryCity(String rdmDeliveryCity) {
        this.rdmDeliveryCity = rdmDeliveryCity;
    }

    public String getRdmDeliveryState() {
        return rdmDeliveryState;
    }

    public void setRdmDeliveryState(String rdmDeliveryState) {
        this.rdmDeliveryState = rdmDeliveryState;
    }

    public String getRdmDeliveryCountry() {
        return rdmDeliveryCountry;
    }

    public void setRdmDeliveryCountry(String rdmDeliveryCountry) {
        this.rdmDeliveryCountry = rdmDeliveryCountry;
    }

    public String getRdmDeliveryPostcode() {
        return rdmDeliveryPostcode;
    }

    public void setRdmDeliveryPostcode(String rdmDeliveryPostcode) {
        this.rdmDeliveryPostcode = rdmDeliveryPostcode;
    }

    public String getRdmContactNumber() {
        return rdmContactNumber;
    }

    public void setRdmContactNumber(String rdmContactNumber) {
        this.rdmContactNumber = rdmContactNumber;
    }

    public Double getRdmCashAmount() {
        return rdmCashAmount;
    }

    public void setRdmCashAmount(Double rdmCashAmount) {
        this.rdmCashAmount = rdmCashAmount;
    }

    public int getRdmCashPaymentStatus() {
        return rdmCashPaymentStatus;
    }

    public void setRdmCashPaymentStatus(int rdmCashPaymentStatus) {
        this.rdmCashPaymentStatus = rdmCashPaymentStatus;
    }

    public String getRdmRef() {
        return rdmRef;
    }

    public void setRdmRef(String rdmRef) {
        this.rdmRef = rdmRef;
    }

    public String getRdmMerchantRef() {
        return rdmMerchantRef;
    }

    public void setRdmMerchantRef(String rdmMerchantRef) {
        this.rdmMerchantRef = rdmMerchantRef;
    }

    public Long getRdmUserNo() {
        return rdmUserNo;
    }

    public void setRdmUserNo(Long rdmUserNo) {
        this.rdmUserNo = rdmUserNo;
    }

    public String getRdmUniqueBatchTrackingId() {
        return rdmUniqueBatchTrackingId;
    }

    public void setRdmUniqueBatchTrackingId(String rdmUniqueBatchTrackingId) {
        this.rdmUniqueBatchTrackingId = rdmUniqueBatchTrackingId;
    }

    public String getRdmDeliveryCourierInfo() {
        return rdmDeliveryCourierInfo;
    }

    public void setRdmDeliveryCourierInfo(String rdmDeliveryCourierInfo) {
        this.rdmDeliveryCourierInfo = rdmDeliveryCourierInfo;
    }

    public String getRdmDeliveryCourierTracking() {
        return rdmDeliveryCourierTracking;
    }

    public void setRdmDeliveryCourierTracking(String rdmDeliveryCourierTracking) {
        this.rdmDeliveryCourierTracking = rdmDeliveryCourierTracking;
    }

    public String getRwdCurrencyName() {
        return rwdCurrencyName;
    }

    public void setRwdCurrencyName(String rwdCurrencyName) {
        this.rwdCurrencyName = rwdCurrencyName;
    }

    public String getPrdName() {
        return prdName;
    }

    public void setPrdName(String prdName) {
        this.prdName = prdName;
    }

    public String getRdmExternalReference() {
        return rdmExternalReference;
    }

    public void setRdmExternalReference(String rdmExternalReference) {
        this.rdmExternalReference = rdmExternalReference;
    }

    public Integer getRdmChannel() {
        return rdmChannel;
    }

    public void setRdmChannel(Integer rdmChannel) {
        this.rdmChannel = rdmChannel;
    }

    public String getRdmLocation() {
        return rdmLocation;
    }

    public String getRdmMerchantName() {
        return rdmMerchantName;
    }

    public void setRdmMerchantName(String rdmMerchantName) {
        this.rdmMerchantName = rdmMerchantName;
    }

    public void setRdmLocation(String rdmLocation) {
        this.rdmLocation = rdmLocation;
    }

    public String getRdmVendorName() {
        return rdmVendorName;
    }

    public void setRdmVendorName(String rdmVendorName) {
        this.rdmVendorName = rdmVendorName;
    }

    public String getRdmVendorContactNo() {
        return rdmVendorContactNo;
    }

    public void setRdmVendorContactNo(String rdmVendorContactNo) {
        this.rdmVendorContactNo = rdmVendorContactNo;
    }
}
