package com.inspirenetz.api.core.domain;

import com.inspirenetz.api.core.dictionary.RecordStatus;
import com.inspirenetz.api.core.dictionary.RedemptionStatus;
import com.inspirenetz.api.core.dictionary.RedemptionType;
import org.hibernate.validator.constraints.NotEmpty;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.sql.Time;
import java.sql.Date;

/**
 * Created by sandheepgr on 16/2/14.
 */
@Entity
@Table(name="REDEMPTIONS")
public class Redemption extends  AuditedEntity  {

    @Column(name = "RDM_ID",nullable = false)
    @Basic(fetch = FetchType.EAGER)
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long rdmId;

    @Column(name = "RDM_DATE",nullable = false)
    @Basic(fetch = FetchType.EAGER)
    private Date rdmDate;

    @Column(name = "RDM_TIME",nullable = false)
    @Basic(fetch = FetchType.EAGER)
    private Time rdmTime;

    @Column(name = "RDM_MERCHANT_NO",nullable = false)
    @Basic(fetch = FetchType.EAGER)
    private Long rdmMerchantNo;

    @Column(name = "RDM_STATUS",nullable = false)
    @Basic(fetch = FetchType.EAGER)
    private int rdmStatus = RedemptionStatus.RDM_STATUS_NEW;

    @Column(name = "RDM_TYPE",nullable = false)
    @Basic(fetch = FetchType.EAGER)
    private int rdmType = RedemptionType.CASHBACK;

    @Column(name = "RDM_CASHBACK_AMOUNT",nullable = true)
    @Basic(fetch = FetchType.EAGER)
    private double rdmCashbackAmount = 0 ;

    @Column(name = "RDM_REWARD_CURRENCY_ID",nullable = false)
    @Basic(fetch = FetchType.EAGER)
    private Long rdmRewardCurrencyId = 0L;

    @Column(name = "RDM_REWARD_CURRENCY_QTY",nullable = false)
    @Basic(fetch = FetchType.EAGER)
    private double rdmRewardCurrencyQty = 0;

    @Column(name = "RDM_PRODUCT_CODE",nullable = false)
    @Basic(fetch = FetchType.EAGER)
    private String rdmProductCode = "";

    @Column(name = "RDM_QTY",nullable = false)
    @Basic(fetch = FetchType.EAGER)
    private double rdmQty = 1;

    @Column(name = "RDM_LOYALTY_ID",nullable = false)
    @Basic(fetch = FetchType.EAGER)
    @NotNull(message = "{redemption.rdmloyaltyid.notnull}")
    @NotEmpty(message = "{redemption.rdmloyaltyid.notempty}")
    private String rdmLoyaltyId;

    @Column(name = "RDM_DELIVERY_IND",nullable = false)
    @Basic(fetch = FetchType.EAGER)
    private int rdmDeliveryInd;

    @Column(name = "RDM_DELIVERY_ADDR1",nullable = true)
    @Basic(fetch = FetchType.EAGER)
    @Size(max=50,message = "{redemption.rdmdeliveryaddr1.size}")
    private String rdmDeliveryAddr1;

    @Column(name = "RDM_DELIVERY_ADDR2",nullable = true)
    @Basic(fetch = FetchType.EAGER)
    @Size(max=50,message = "{redemption.rdmdeliveryaddr2.size}")
    private String rdmDeliveryAddr2;

    @Column(name = "RDM_DELIVERY_ADDR3",nullable = true)
    @Basic(fetch = FetchType.EAGER)
    @Size(max=50,message = "{redemption.rdmdeliveryaddr3.size}")
    private String rdmDeliveryAddr3;

    @Column(name = "RDM_DELIVERY_CITY",nullable = true)
    @Basic(fetch = FetchType.EAGER)
    @Size(max=50,message = "{redemption.rdmdeliverycity.size}")
    private String rdmDeliveryCity;

    @Column(name = "RDM_DELIVERY_STATE",nullable = true)
    @Basic(fetch = FetchType.EAGER)
    @Size(max=50,message = "{redemption.rdmdeliverystate.size}")
    private String rdmDeliveryState;

    @Column(name = "RDM_DELIVERY_COUNTRY",nullable = true)
    @Basic(fetch = FetchType.EAGER)
    @Size(max=100,message = "{redemption.rdmdeliverycountry.size}")
    private String rdmDeliveryCountry;

    @Column(name = "RDM_DELIVERY_POSTCODE",nullable = true)
    @Basic(fetch = FetchType.EAGER)
    @Size(max=10,message = "{redemption.rdmdeliverypostcode.size}")
    private String rdmDeliveryPostcode;

    @Column(name = "RDM_CONTACT_NUMBER",nullable = true)
    @Basic(fetch = FetchType.EAGER)
    @Size(max=12,message = "{redemption.rdmcontactnumber.size}")
    private String rdmContactNumber;

    @Column(name = "RDM_CASH_AMOUNT",nullable = true)
    @Basic(fetch = FetchType.EAGER)
    private Double rdmCashAmount = 0.0 ;

    @Column(name = "RDM_CASH_PAYMENT_STATUS",nullable = false)
    @Basic(fetch = FetchType.EAGER)
    private int rdmCashPaymentStatus = 0;

    @Column(name = "RDM_REF",nullable = true)
    @Basic(fetch = FetchType.EAGER)
    @Size(max=500,message = "{redemption.rdmref.size}")
    private String rdmRef;

    @Column(name = "RDM_MERCHANT_REF",nullable = true)
    @Basic(fetch = FetchType.EAGER)
    @Size(max=500,message = "{redemption.rdmmerchantref.size}")
    private String rdmMerchantRef;

    @Column(name = "RDM_USER_NO",nullable = false)
    @Basic(fetch = FetchType.EAGER)
    private Long rdmUserNo;

    @Column(name = "RDM_UNIQUE_BATCH_TRACKING_ID",nullable = true)
    @Basic(fetch = FetchType.EAGER)
    private String rdmUniqueBatchTrackingId ;

    @Column(name = "RDM_DELIVERY_COURIER_INFO",nullable = true)
    @Basic(fetch = FetchType.EAGER)
    private String rdmDeliveryCourierInfo;

    @Column(name = "RDM_DELIVERY_COURIER_TRACKING",nullable = true)
    @Basic(fetch = FetchType.EAGER)
    private String rdmDeliveryCourierTracking;

    @Column(name = "RDM_EXTERNAL_REFERENCE",nullable = true)
    @Basic(fetch = FetchType.EAGER)
    private String rdmExternalReference;

    @Column(name = "RDM_CHANNEL",nullable = true)
    @Basic(fetch = FetchType.EAGER)
    private Integer rdmChannel;

    @Column(name = "RDM_DEST_LOYALTY_ID",nullable = false)
    @Basic(fetch = FetchType.EAGER)
    private String rdmDestLoyaltyId = "0";


    @Column(name = "RDM_RECORD_STATUS",nullable = true)
    @Basic(fetch = FetchType.EAGER)
    private Integer rdmRecordStatus = RecordStatus.RECORD_STATUS_ACTIVE;

    @Column(name = "RDM_EXTERNAL_COST",nullable = true)
    @Basic(fetch = FetchType.EAGER)
    private Double rdmExternalCost = 0.0;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name="RDM_REWARD_CURRENCY_ID",insertable = false,updatable = false)
    private RewardCurrency rewardCurrency;

    @Column(name = "RDM_IDENTIFIER",nullable = true)
    @Basic(fetch = FetchType.EAGER)
    private String rdmIdenitifier;

    @Column(name = "RDM_PARTNER_NO",nullable = false)
    @Basic(fetch = FetchType.EAGER)
    private Long rdmPartnerNo;

    public Long getRdmMerchantNo() {
        return rdmMerchantNo;
    }

    public void setRdmMerchantNo(Long rdmMerchantNo) {
        this.rdmMerchantNo = rdmMerchantNo;
    }

    public Long getRdmRewardCurrencyId() {
        return rdmRewardCurrencyId;
    }

    public void setRdmRewardCurrencyId(Long rdmRewardCurrencyId) {
        this.rdmRewardCurrencyId = rdmRewardCurrencyId;
    }

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


    public RewardCurrency getRewardCurrency() {
        return rewardCurrency;
    }

    public void setRewardCurrency(RewardCurrency rewardCurrency) {
        this.rewardCurrency = rewardCurrency;
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

    public String getRdmDestLoyaltyId() {
        return rdmDestLoyaltyId;
    }

    public void setRdmDestLoyaltyId(String rdmDestLoyaltyId) {
        this.rdmDestLoyaltyId = rdmDestLoyaltyId;
    }

    public Integer getRdmRecordStatus() {
        return rdmRecordStatus;
    }

    public void setRdmRecordStatus(Integer rdmRecordStatus) {
        this.rdmRecordStatus = rdmRecordStatus;
    }

    public Double getRdmExternalCost() {
        return rdmExternalCost;
    }

    public void setRdmExternalCost(Double rdmExternalCost) {
        this.rdmExternalCost = rdmExternalCost;
    }

    public String getRdmIdenitifier() {
        return rdmIdenitifier;
    }

    public void setRdmIdenitifier(String rdmIdenitifier) {
        this.rdmIdenitifier = rdmIdenitifier;
    }

    public Long getRdmPartnerNo() {
        return rdmPartnerNo;
    }

    public void setRdmPartnerNo(Long rdmPartnerNo) {
        this.rdmPartnerNo = rdmPartnerNo;
    }

    @Override
    public String toString() {
        return "Redemption{" +
                "rdmId=" + rdmId +
                ", rdmDate=" + rdmDate +
                ", rdmTime=" + rdmTime +
                ", rdmMerchantNo=" + rdmMerchantNo +
                ", rdmStatus=" + rdmStatus +
                ", rdmType=" + rdmType +
                ", rdmCashbackAmount=" + rdmCashbackAmount +
                ", rdmRewardCurrencyId=" + rdmRewardCurrencyId +
                ", rdmRewardCurrencyQty=" + rdmRewardCurrencyQty +
                ", rdmProductCode='" + rdmProductCode + '\'' +
                ", rdmQty=" + rdmQty +
                ", rdmLoyaltyId='" + rdmLoyaltyId + '\'' +
                ", rdmDeliveryInd=" + rdmDeliveryInd +
                ", rdmDeliveryAddr1='" + rdmDeliveryAddr1 + '\'' +
                ", rdmDeliveryAddr2='" + rdmDeliveryAddr2 + '\'' +
                ", rdmDeliveryAddr3='" + rdmDeliveryAddr3 + '\'' +
                ", rdmDeliveryCity='" + rdmDeliveryCity + '\'' +
                ", rdmDeliveryState='" + rdmDeliveryState + '\'' +
                ", rdmDeliveryCountry='" + rdmDeliveryCountry + '\'' +
                ", rdmDeliveryPostcode='" + rdmDeliveryPostcode + '\'' +
                ", rdmContactNumber='" + rdmContactNumber + '\'' +
                ", rdmCashAmount=" + rdmCashAmount +
                ", rdmCashPaymentStatus=" + rdmCashPaymentStatus +
                ", rdmRef='" + rdmRef + '\'' +
                ", rdmMerchantRef='" + rdmMerchantRef + '\'' +
                ", rdmUserNo=" + rdmUserNo +
                ", rdmUniqueBatchTrackingId='" + rdmUniqueBatchTrackingId + '\'' +
                ", rdmDeliveryCourierInfo='" + rdmDeliveryCourierInfo + '\'' +
                ", rdmDeliveryCourierTracking='" + rdmDeliveryCourierTracking + '\'' +
                ", rdmExternalReference='" + rdmExternalReference + '\'' +
                ", rdmChannel=" + rdmChannel +
                ", rdmDestLoyaltyId='" + rdmDestLoyaltyId + '\'' +
                ", rdmRecordStatus=" + rdmRecordStatus +
                ", rdmExternalCost=" + rdmExternalCost +
                ", rewardCurrency=" + rewardCurrency +
                ", rdmIdenitifier='" + rdmIdenitifier + '\'' +
                ", rdmPartnerNo=" + rdmPartnerNo +
                '}';
    }
}
