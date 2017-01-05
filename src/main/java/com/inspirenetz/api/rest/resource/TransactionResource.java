package com.inspirenetz.api.rest.resource;

import com.inspirenetz.api.core.domain.PurchaseSKU;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by sandheepgr on 4/7/14.
 */
public class TransactionResource extends BaseResource {


    private Long txnId;

    private int txnType;

    private Long txnMerchantNo;

    private String txnLoyaltyId;

    private int txnStatus;

    private Date txnDate;

    private Long txnLocation;

    private String txnInternalRef;

    private String txnExternalRef;

    private Long txnRewardCurrencyId;

    private int txnCrDbInd;

    private double txnRewardQty;

    private double txnAmount;

    private Long txnProgramId;

    private double txnRewardPreBal;

    private double txnRewardPostBal;

    private Date txnRewardExpDt;

    private List<SaleSKUResource> saleSKUResources = new ArrayList<>(0);

    private String rwdCurrencyName;

    private String merchantName;

    private String locationName;

    private java.util.Date createdAt;




    public Long getTxnId() {
        return txnId;
    }

    public void setTxnId(Long txnId) {
        this.txnId = txnId;
    }

    public int getTxnType() {
        return txnType;
    }

    public void setTxnType(int txnType) {
        this.txnType = txnType;
    }

    public Long getTxnMerchantNo() {
        return txnMerchantNo;
    }

    public void setTxnMerchantNo(Long txnMerchantNo) {
        this.txnMerchantNo = txnMerchantNo;
    }

    public String getTxnLoyaltyId() {
        return txnLoyaltyId;
    }

    public void setTxnLoyaltyId(String txnLoyaltyId) {
        this.txnLoyaltyId = txnLoyaltyId;
    }

    public int getTxnStatus() {
        return txnStatus;
    }

    public void setTxnStatus(int txnStatus) {
        this.txnStatus = txnStatus;
    }

    public Date getTxnDate() {
        return txnDate;
    }

    public void setTxnDate(Date txnDate) {
        this.txnDate = txnDate;
    }

    public Long getTxnLocation() {
        return txnLocation;
    }

    public void setTxnLocation(Long txnLocation) {
        this.txnLocation = txnLocation;
    }

    public String getTxnInternalRef() {
        return txnInternalRef;
    }

    public void setTxnInternalRef(String txnInternalRef) {
        this.txnInternalRef = txnInternalRef;
    }

    public String getTxnExternalRef() {
        return txnExternalRef;
    }

    public void setTxnExternalRef(String txnExternalRef) {
        this.txnExternalRef = txnExternalRef;
    }

    public Long getTxnRewardCurrencyId() {
        return txnRewardCurrencyId;
    }

    public void setTxnRewardCurrencyId(Long txnRewardCurrencyId) {
        this.txnRewardCurrencyId = txnRewardCurrencyId;
    }

    public int getTxnCrDbInd() {
        return txnCrDbInd;
    }

    public void setTxnCrDbInd(int txnCrDbInd) {
        this.txnCrDbInd = txnCrDbInd;
    }

    public double getTxnRewardQty() {
        return txnRewardQty;
    }

    public void setTxnRewardQty(double txnRewardQty) {
        this.txnRewardQty = txnRewardQty;
    }

    public double getTxnAmount() {
        return txnAmount;
    }

    public void setTxnAmount(double txnAmount) {
        this.txnAmount = txnAmount;
    }

    public Long getTxnProgramId() {
        return txnProgramId;
    }

    public void setTxnProgramId(Long txnProgramId) {
        this.txnProgramId = txnProgramId;
    }

    public double getTxnRewardPreBal() {
        return txnRewardPreBal;
    }

    public void setTxnRewardPreBal(double txnRewardPreBal) {
        this.txnRewardPreBal = txnRewardPreBal;
    }

    public double getTxnRewardPostBal() {
        return txnRewardPostBal;
    }

    public void setTxnRewardPostBal(double txnRewardPostBal) {
        this.txnRewardPostBal = txnRewardPostBal;
    }

    public Date getTxnRewardExpDt() {
        return txnRewardExpDt;
    }

    public void setTxnRewardExpDt(Date txnRewardExpDt) {
        this.txnRewardExpDt = txnRewardExpDt;
    }

    public List<SaleSKUResource> getSaleSKUResources() {
        return saleSKUResources;
    }

    public void setSaleSKUResources(List<SaleSKUResource> saleSKUResources) {
        this.saleSKUResources = saleSKUResources;
    }

    public String getRwdCurrencyName() {
        return rwdCurrencyName;
    }

    public void setRwdCurrencyName(String rwdCurrencyName) {
        this.rwdCurrencyName = rwdCurrencyName;
    }

    public String getMerchantName() {
        return merchantName;
    }

    public void setMerchantName(String merchantName) {
        this.merchantName = merchantName;
    }

    public String getLocationName() {
        return locationName;
    }

    public void setLocationName(String locationName) {
        this.locationName = locationName;
    }


    public java.util.Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(java.util.Date createdAt) {
        this.createdAt = createdAt;
    }

    @Override
    public String toString() {
        return "TransactionResource{" +
                "txnId=" + txnId +
                ", txnType=" + txnType +
                ", txnMerchantNo=" + txnMerchantNo +
                ", txnLoyaltyId='" + txnLoyaltyId + '\'' +
                ", txnStatus=" + txnStatus +
                ", txnDate=" + txnDate +
                ", txnLocation=" + txnLocation +
                ", txnInternalRef='" + txnInternalRef + '\'' +
                ", txnExternalRef='" + txnExternalRef + '\'' +
                ", txnRewardCurrencyId=" + txnRewardCurrencyId +
                ", txnCrDbInd=" + txnCrDbInd +
                ", txnRewardQty=" + txnRewardQty +
                ", txnAmount=" + txnAmount +
                ", txnProgramId=" + txnProgramId +
                ", txnRewardPreBal=" + txnRewardPreBal +
                ", txnRewardPostBal=" + txnRewardPostBal +
                ", txnRewardExpDt=" + txnRewardExpDt +
                '}';
    }
}
