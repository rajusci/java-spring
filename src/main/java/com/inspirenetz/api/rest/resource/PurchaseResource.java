package com.inspirenetz.api.rest.resource;

import com.inspirenetz.api.core.domain.PurchaseSKU;

import java.sql.Date;
import java.sql.Time;
import java.util.List;

/**
 * Created by sandheepgr on 27/4/14.
 */
public class PurchaseResource extends BaseResource {

    private Long prcId;

    private Long prcMerchantNo;

    private int prcType;

    private Date prcDate;

    private Time prcTime;

    private String prcLoyaltyId;

    private Double prcAmount;

    private int prcCurrency;

    private int prcPaymentMode;

    private Long prcLocation;

    private int prcTxnChannel;

    private String prcPaymentReference;

    private int prcStatus;

    private Double prcQuantity;

    private int prcDayOfWeek;

    private Long prcUserNo;

    private List<PurchaseSKUResource> purchaseSKUList;



    public Long getPrcId() {
        return prcId;
    }

    public void setPrcId(Long prcId) {
        this.prcId = prcId;
    }

    public Long getPrcMerchantNo() {
        return prcMerchantNo;
    }

    public void setPrcMerchantNo(Long prcMerchantNo) {
        this.prcMerchantNo = prcMerchantNo;
    }

    public int getPrcType() {
        return prcType;
    }

    public void setPrcType(int prcType) {
        this.prcType = prcType;
    }

    public Date getPrcDate() {
        return prcDate;
    }

    public void setPrcDate(Date prcDate) {
        this.prcDate = prcDate;
    }

    public Time getPrcTime() {
        return prcTime;
    }

    public void setPrcTime(Time prcTime) {
        this.prcTime = prcTime;
    }

    public String getPrcLoyaltyId() {
        return prcLoyaltyId;
    }

    public void setPrcLoyaltyId(String prcLoyaltyId) {
        this.prcLoyaltyId = prcLoyaltyId;
    }

    public Double getPrcAmount() {
        return prcAmount;
    }

    public void setPrcAmount(Double prcAmount) {
        this.prcAmount = prcAmount;
    }

    public int getPrcCurrency() {
        return prcCurrency;
    }

    public void setPrcCurrency(int prcCurrency) {
        this.prcCurrency = prcCurrency;
    }

    public int getPrcPaymentMode() {
        return prcPaymentMode;
    }

    public void setPrcPaymentMode(int prcPaymentMode) {
        this.prcPaymentMode = prcPaymentMode;
    }

    public Long getPrcLocation() {
        return prcLocation;
    }

    public void setPrcLocation(Long prcLocation) {
        this.prcLocation = prcLocation;
    }

    public int getPrcTxnChannel() {
        return prcTxnChannel;
    }

    public void setPrcTxnChannel(int prcTxnChannel) {
        this.prcTxnChannel = prcTxnChannel;
    }

    public String getPrcPaymentReference() {
        return prcPaymentReference;
    }

    public void setPrcPaymentReference(String prcPaymentReference) {
        this.prcPaymentReference = prcPaymentReference;
    }

    public int getPrcStatus() {
        return prcStatus;
    }

    public void setPrcStatus(int prcStatus) {
        this.prcStatus = prcStatus;
    }

    public Double getPrcQuantity() {
        return prcQuantity;
    }

    public void setPrcQuantity(Double prcQuantity) {
        this.prcQuantity = prcQuantity;
    }

    public int getPrcDayOfWeek() {
        return prcDayOfWeek;
    }

    public void setPrcDayOfWeek(int prcDayOfWeek) {
        this.prcDayOfWeek = prcDayOfWeek;
    }

    public Long getPrcUserNo() {
        return prcUserNo;
    }

    public void setPrcUserNo(Long prcUserNo) {
        this.prcUserNo = prcUserNo;
    }

    public List<PurchaseSKUResource> getPurchaseSKUList() {
        return purchaseSKUList;
    }

    public void setPurchaseSKUList(List<PurchaseSKUResource> purchaseSKUList) {
        this.purchaseSKUList = purchaseSKUList;
    }
}
