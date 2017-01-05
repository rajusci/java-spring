package com.inspirenetz.api.core.dictionary;

import com.inspirenetz.api.util.DBUtils;

import java.sql.Date;

/**
 * Created by sandheepgr on 23/5/14.
 */
public class PointRewardData {


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


    // Reward Currency information
    private Long rwdCurrencyId;

    private String rwdCurrencyName = "";


    // Mandatory fields
    private double rewardQty = 0;

    private double totalRewardQty = 0;

    private double txnAmount = 0.0;

    private Integer txnType = TransactionType.PURCHASE;


    // Optional fields
    private Long programId;

    private Long txnLocation;

    private Date txnDate;

    private Date expiryDt = DBUtils.covertToSqlDate("9999-12-31");

    private String auditDetails = "" ;

    private boolean isAddToAccumulatedBalance = false;

    //redemption failure rewarding fields
    private boolean isFailedRedemption = false;
    private String trackingId="";
    private String remarks = "";
    private double refereeBonus=0.0;
    private double referrerBonus =0.0;
    private String refereeLoyaltyId;



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

    public String getRwdCurrencyName() {
        return rwdCurrencyName;
    }

    public void setRwdCurrencyName(String rwdCurrencyName) {
        this.rwdCurrencyName = rwdCurrencyName;
    }

    public double getTotalRewardQty() {
        return totalRewardQty;
    }

    public void setTotalRewardQty(double totalRewardQty) {
        this.totalRewardQty = totalRewardQty;
    }

    public String getAuditDetails() {
        return auditDetails;
    }

    public void setAuditDetails(String auditDetails) {
        this.auditDetails = auditDetails;
    }

    public Date getExpiryDt() {
        return expiryDt;
    }

    public void setExpiryDt(Date expiryDt) {
        this.expiryDt = expiryDt;
    }

    public Long getMerchantNo() {
        return merchantNo;
    }

    public void setMerchantNo(Long merchantNo) {
        this.merchantNo = merchantNo;
    }

    public String getLoyaltyId() {
        return loyaltyId;
    }

    public void setLoyaltyId(String loyaltyId) {
        this.loyaltyId = loyaltyId;
    }

    public Long getRwdCurrencyId() {
        return rwdCurrencyId;
    }

    public void setRwdCurrencyId(Long rwdCurrencyId) {
        this.rwdCurrencyId = rwdCurrencyId;
    }

    public double getRewardQty() {
        return rewardQty;
    }

    public void setRewardQty(double rewardQty) {
        this.rewardQty = rewardQty;
    }

    public double getTxnAmount() {
        return txnAmount;
    }

    public void setTxnAmount(double txnAmount) {
        this.txnAmount = txnAmount;
    }

    public Integer getTxnType() {
        return txnType;
    }

    public void setTxnType(Integer txnType) {
        this.txnType = txnType;
    }

    public Long getProgramId() {
        return programId;
    }

    public void setProgramId(Long programId) {
        this.programId = programId;
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

    public boolean isAddToAccumulatedBalance() {
        return isAddToAccumulatedBalance;
    }

    public void setAddToAccumulatedBalance(boolean isAddToAccumulatedBalance) {
        this.isAddToAccumulatedBalance = isAddToAccumulatedBalance;
    }

    public boolean isFailedRedemption() {
        return isFailedRedemption;
    }

    public void setFailedRedemption(boolean isFailedRedemption) {
        this.isFailedRedemption = isFailedRedemption;
    }

    public String getTrackingId() {
        return trackingId;
    }

    public void setTrackingId(String trackingId) {
        this.trackingId = trackingId;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    public double getRefereeBonus() {
        return refereeBonus;
    }

    public void setRefereeBonus(double refereeBonus) {
        this.refereeBonus = refereeBonus;
    }

    public double getReferrerBonus() {
        return referrerBonus;
    }

    public void setReferrerBonus(double referrerBonus) {
        this.referrerBonus = referrerBonus;
    }

    public String getRefereeLoyaltyId() {
        return refereeLoyaltyId;
    }

    public void setRefereeLoyaltyId(String refereeLoyaltyId) {
        this.refereeLoyaltyId = refereeLoyaltyId;
    }




    @Override
    public String toString() {
        return "PointRewardData{" +
                "merchantNo=" + merchantNo +
                ", merchantName='" + merchantName + '\'' +
                ", merchantLogo=" + merchantLogo +
                ", loyaltyId='" + loyaltyId + '\'' +
                ", userNo=" + userNo +
                ", usrFName='" + usrFName + '\'' +
                ", usrLName='" + usrLName + '\'' +
                ", usrProfilePic=" + usrProfilePic +
                ", rwdCurrencyId=" + rwdCurrencyId +
                ", rwdCurrencyName='" + rwdCurrencyName + '\'' +
                ", rewardQty=" + rewardQty +
                ", totalRewardQty=" + totalRewardQty +
                ", txnAmount=" + txnAmount +
                ", txnType=" + txnType +
                ", programId=" + programId +
                ", txnLocation=" + txnLocation +
                ", txnDate=" + txnDate +
                ", expiryDt=" + expiryDt +
                ", auditDetails='" + auditDetails + '\'' +
                ", isAddToAccumulatedBalance=" + isAddToAccumulatedBalance +
                ", isFailedRedemption=" + isFailedRedemption +
                ", trackingId='" + trackingId + '\'' +
                ", remarks='" + remarks + '\'' +
                ", refereeBonus=" + refereeBonus +
                ", referrerBonus=" + referrerBonus +
                ", refereeLoyaltyId='" + refereeLoyaltyId + '\'' +
                '}';
    }
}
