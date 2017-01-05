package com.inspirenetz.api.core.dictionary;

import java.io.Serializable;
import java.sql.Date;
import java.sql.Time;
import java.sql.Timestamp;

/**
 * Created by sandheepgr on 6/3/15.
 */
public class SaleResource implements Serializable{

    private Long salMerchantNo;

    private String salLoyaltyId;

    private Date salDate;

    private Time salTime;

    private Timestamp salTimestamp;

    private String salPaymentReference;

    private Double salAmount;

    private Double salQty;

    private Integer salType;

    private Integer salTxnChannel;

    private Long salLocation;


    private Double ssuPrice;

    private Double ssuQty;

    private Integer ssuTransactionType;

    private String ssuProductCode;

    private Double ssuMsfValue;

    private String ssuContracted;


    public SaleResource(Long salMerchantNo, String salLoyaltyId, Date salDate, Double salAmount, Long salLocation, Integer salTxnChannel, String salPaymentReference, String ssuProductCode, Double ssuPrice, Double ssuQty, Integer ssuTransactionType, Double ssuMsfValue, String ssuContracted,Integer salType,Time salTime,Timestamp salTimestamp) {
        this.salMerchantNo = salMerchantNo;
        this.salLoyaltyId = salLoyaltyId;
        this.salDate = salDate;
        this.salAmount = salAmount;
        this.salLocation = salLocation;
        this.salTxnChannel = salTxnChannel;
        this.salPaymentReference = salPaymentReference;
        this.ssuProductCode = ssuProductCode;
        this.ssuPrice = ssuPrice;
        this.ssuQty = ssuQty;
        this.ssuTransactionType = ssuTransactionType;
        this.ssuMsfValue = ssuMsfValue;
        this.ssuContracted = ssuContracted;
        this.salType = salType;
        this.salTime = salTime;
        this.salTimestamp = salTimestamp;
    }

    public Long getSalMerchantNo() {
        return salMerchantNo;
    }

    public void setSalMerchantNo(Long salMerchantNo) {
        this.salMerchantNo = salMerchantNo;
    }

    public String getSalLoyaltyId() {
        return salLoyaltyId;
    }

    public void setSalLoyaltyId(String salLoyaltyId) {
        this.salLoyaltyId = salLoyaltyId;
    }

    public Date getSalDate() {
        return salDate;
    }

    public void setSalDate(Date salDate) {
        this.salDate = salDate;
    }

    public String getSalPaymentReference() {
        return salPaymentReference;
    }

    public void setSalPaymentReference(String salPaymentReference) {
        this.salPaymentReference = salPaymentReference;
    }

    public Double getSalAmount() {
        return salAmount;
    }

    public void setSalAmount(Double salAmount) {
        this.salAmount = salAmount;
    }

    public Double getSalQty() {
        return salQty;
    }

    public void setSalQty(Double salQty) {
        this.salQty = salQty;
    }

    public Integer getSalType() {
        return salType;
    }

    public void setSalType(Integer salType) {
        this.salType = salType;
    }

    public Integer getSalTxnChannel() {
        return salTxnChannel;
    }

    public void setSalTxnChannel(Integer salTxnChannel) {
        this.salTxnChannel = salTxnChannel;
    }

    public Long getSalLocation() {
        return salLocation;
    }

    public void setSalLocation(Long salLocation) {
        this.salLocation = salLocation;
    }

    public Double getSsuPrice() {
        return ssuPrice;
    }

    public void setSsuPrice(Double ssuPrice) {
        this.ssuPrice = ssuPrice;
    }

    public Double getSsuQty() {
        return ssuQty;
    }

    public void setSsuQty(Double ssuQty) {
        this.ssuQty = ssuQty;
    }

    public Integer getSsuTransactionType() {
        return ssuTransactionType;
    }

    public void setSsuTransactionType(Integer ssuTransactionType) {
        this.ssuTransactionType = ssuTransactionType;
    }

    public String getSsuProductCode() {
        return ssuProductCode;
    }

    public void setSsuProductCode(String ssuProductCode) {
        this.ssuProductCode = ssuProductCode;
    }

    public Double getSsuMsfValue() {
        return ssuMsfValue;
    }

    public void setSsuMsfValue(Double ssuMsfValue) {
        this.ssuMsfValue = ssuMsfValue;
    }

    public String getSsuContracted() {
        return ssuContracted;
    }

    public void setSsuContracted(String ssuContracted) {
        this.ssuContracted = ssuContracted;
    }

    public Timestamp getSalTimestamp() {
        return salTimestamp;
    }

    public void setSalTimestamp(Timestamp salTimestamp) {
        this.salTimestamp = salTimestamp;
    }

    public Time getSalTime() {
        return salTime;
    }

    public void setSalTime(Time salTime) {
        this.salTime = salTime;
    }


    @Override
    public String toString() {
        return "SaleResource{" +
                "salMerchantNo=" + salMerchantNo +
                ", salLoyaltyId='" + salLoyaltyId + '\'' +
                ", salDate=" + salDate +
                ", salTime=" + salTime +
                ", salTimestamp=" + salTimestamp +
                ", salPaymentReference='" + salPaymentReference + '\'' +
                ", salAmount=" + salAmount +
                ", salQty=" + salQty +
                ", salType=" + salType +
                ", salTxnChannel=" + salTxnChannel +
                ", salLocation=" + salLocation +
                ", ssuPrice=" + ssuPrice +
                ", ssuQty=" + ssuQty +
                ", ssuTransactionType=" + ssuTransactionType +
                ", ssuProductCode='" + ssuProductCode + '\'' +
                ", ssuMsfValue=" + ssuMsfValue +
                ", ssuContracted='" + ssuContracted + '\'' +
                '}';
    }
}
