package com.inspirenetz.api.core.dictionary;

import java.sql.Date;

/**
 * Created by sandheepgr on 23/5/14.
 */
public class PointDeductData {


    // Merchant Information fields
    private Long merchantNo;

    private String merchantName = "";

    private Long merchantLogo = ImagePrimaryId.PRIMARY_MERCHANT_LOGO;



    // Customer information
    private String loyaltyId ;

    private Long userNo;

    private String usrFName = "";

    private String usrLName = "";

    private Long usrProfilePic = ImagePrimaryId.PRIMARY_DEFAULT_IMAGE;

    private String remarks = "";

    // Reward Currency information
    private Long rwdCurrencyId;

    private String rwdCurrencyName = "";


    // Mandatory fields
    private double redeemQty = 0;



    // Optional fields
    private Long txnLocation;

    private Date txnDate;

    private String auditDetails = "" ;

    private Double txnAmount = 0.0;

    private Integer txnType = TransactionType.REDEEM;

    private String internalRef = "";

    private String externalRef = "";



    public Long getMerchantNo() {
        return merchantNo;
    }

    public void setMerchantNo(Long merchantNo) {
        this.merchantNo = merchantNo;
    }

    public String getMerchantName() {
        return merchantName;
    }

    public void setMerchantName(String merchantName) {
        this.merchantName = merchantName;
    }

    public Long getMerchantLogo() {
        return merchantLogo;
    }

    public void setMerchantLogo(Long merchantLogo) {
        this.merchantLogo = merchantLogo;
    }

    public String getLoyaltyId() {
        return loyaltyId;
    }

    public void setLoyaltyId(String loyaltyId) {
        this.loyaltyId = loyaltyId;
    }

    public Long getUserNo() {
        return userNo;
    }

    public void setUserNo(Long userNo) {
        this.userNo = userNo;
    }

    public String getUsrFName() {
        return usrFName;
    }

    public void setUsrFName(String usrFName) {
        this.usrFName = usrFName;
    }

    public String getUsrLName() {
        return usrLName;
    }

    public void setUsrLName(String usrLName) {
        this.usrLName = usrLName;
    }

    public Long getUsrProfilePic() {
        return usrProfilePic;
    }

    public void setUsrProfilePic(Long usrProfilePic) {
        this.usrProfilePic = usrProfilePic;
    }

    public Long getRwdCurrencyId() {
        return rwdCurrencyId;
    }

    public void setRwdCurrencyId(Long rwdCurrencyId) {
        this.rwdCurrencyId = rwdCurrencyId;
    }

    public String getRwdCurrencyName() {
        return rwdCurrencyName;
    }

    public void setRwdCurrencyName(String rwdCurrencyName) {
        this.rwdCurrencyName = rwdCurrencyName;
    }

    public double getRedeemQty() {
        return redeemQty;
    }

    public void setRedeemQty(double redeemQty) {
        this.redeemQty = redeemQty;
    }

    public Long getTxnLocation() {
        return txnLocation;
    }

    public void setTxnLocation(Long txnLocation) {
        this.txnLocation = txnLocation;
    }

    public Date getTxnDate() {
        return txnDate;
    }

    public void setTxnDate(Date txnDate) {
        this.txnDate = txnDate;
    }

    public String getAuditDetails() {
        return auditDetails;
    }

    public void setAuditDetails(String auditDetails) {
        this.auditDetails = auditDetails;
    }

    public Double getTxnAmount() {
        return txnAmount;
    }

    public void setTxnAmount(Double txnAmount) {
        this.txnAmount = txnAmount;
    }

    public Integer getTxnType() {
        return txnType;
    }

    public void setTxnType(Integer txnType) {
        this.txnType = txnType;
    }

    public String getInternalRef() {
        return internalRef;
    }

    public void setInternalRef(String internalRef) {
        this.internalRef = internalRef;
    }

    public String getExternalRef() {
        return externalRef;
    }

    public void setExternalRef(String externalRef) {
        this.externalRef = externalRef;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    @Override
    public String toString() {
        return "PointDeductData{" +
                "merchantNo=" + merchantNo +
                ", merchantName='" + merchantName + '\'' +
                ", merchantLogo=" + merchantLogo +
                ", loyaltyId='" + loyaltyId + '\'' +
                ", userNo=" + userNo +
                ", usrFName='" + usrFName + '\'' +
                ", usrLName='" + usrLName + '\'' +
                ", usrProfilePic=" + usrProfilePic +
                ", remarks='" + remarks + '\'' +
                ", rwdCurrencyId=" + rwdCurrencyId +
                ", rwdCurrencyName='" + rwdCurrencyName + '\'' +
                ", redeemQty=" + redeemQty +
                ", txnLocation=" + txnLocation +
                ", txnDate=" + txnDate +
                ", auditDetails='" + auditDetails + '\'' +
                ", txnAmount=" + txnAmount +
                ", txnType=" + txnType +
                ", internalRef='" + internalRef + '\'' +
                ", externalRef='" + externalRef + '\'' +
                '}';
    }
}
