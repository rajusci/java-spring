package com.inspirenetz.api.test.core.builder;

import com.inspirenetz.api.core.dictionary.ImagePrimaryId;
import com.inspirenetz.api.core.dictionary.PointRewardData;
import com.inspirenetz.api.util.DBUtils;

import java.sql.Date;

/**
 * Created by sandheepgr on 27/5/14.
 */
public class PointRewardDataBuilder {
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
    private double txnAmount;
    // Optional fields
    private Long programId;
    private Long txnLocation;
    private Date txnDate;
    private Date expiryDt = DBUtils.covertToSqlDate("9999-12-31");
    private String auditDetails ;

    private PointRewardDataBuilder() {
    }

    public static PointRewardDataBuilder aPointRewardData() {
        return new PointRewardDataBuilder();
    }

    public PointRewardDataBuilder withMerchantNo(Long merchantNo) {
        this.merchantNo = merchantNo;
        return this;
    }

    public PointRewardDataBuilder withMerchantName(String merchantName) {
        this.merchantName = merchantName;
        return this;
    }

    public PointRewardDataBuilder withMerchantLogo(Long merchantLogo) {
        this.merchantLogo = merchantLogo;
        return this;
    }

    public PointRewardDataBuilder withLoyaltyId(String loyaltyId) {
        this.loyaltyId = loyaltyId;
        return this;
    }

    public PointRewardDataBuilder withUserNo(Long userNo) {
        this.userNo = userNo;
        return this;
    }

    public PointRewardDataBuilder withUsrFName(String usrFName) {
        this.usrFName = usrFName;
        return this;
    }

    public PointRewardDataBuilder withUsrLName(String usrLName) {
        this.usrLName = usrLName;
        return this;
    }

    public PointRewardDataBuilder withUsrProfilePic(Long usrProfilePic) {
        this.usrProfilePic = usrProfilePic;
        return this;
    }

    public PointRewardDataBuilder withRwdCurrencyId(Long rwdCurrencyId) {
        this.rwdCurrencyId = rwdCurrencyId;
        return this;
    }

    public PointRewardDataBuilder withRwdCurrencyName(String rwdCurrencyName) {
        this.rwdCurrencyName = rwdCurrencyName;
        return this;
    }

    public PointRewardDataBuilder withRewardQty(double rewardQty) {
        this.rewardQty = rewardQty;
        return this;
    }

    public PointRewardDataBuilder withTotalRewardQty(double totalRewardQty) {
        this.totalRewardQty = totalRewardQty;
        return this;
    }

    public PointRewardDataBuilder withTxnAmount(double txnAmount) {
        this.txnAmount = txnAmount;
        return this;
    }

    public PointRewardDataBuilder withProgramId(Long programId) {
        this.programId = programId;
        return this;
    }

    public PointRewardDataBuilder withTxnLocation(Long txnLocation) {
        this.txnLocation = txnLocation;
        return this;
    }

    public PointRewardDataBuilder withTxnDate(Date txnDate) {
        this.txnDate = txnDate;
        return this;
    }

    public PointRewardDataBuilder withExpiryDt(Date expiryDt) {
        this.expiryDt = expiryDt;
        return this;
    }

    public PointRewardDataBuilder withAuditDetails(String auditDetails) {
        this.auditDetails = auditDetails;
        return this;
    }

    public PointRewardData build() {
        PointRewardData pointRewardData = new PointRewardData();
        pointRewardData.setMerchantNo(merchantNo);
        pointRewardData.setMerchantName(merchantName);
        pointRewardData.setMerchantLogo(merchantLogo);
        pointRewardData.setLoyaltyId(loyaltyId);
        pointRewardData.setUserNo(userNo);
        pointRewardData.setUsrFName(usrFName);
        pointRewardData.setUsrLName(usrLName);
        pointRewardData.setUsrProfilePic(usrProfilePic);
        pointRewardData.setRwdCurrencyId(rwdCurrencyId);
        pointRewardData.setRwdCurrencyName(rwdCurrencyName);
        pointRewardData.setRewardQty(rewardQty);
        pointRewardData.setTotalRewardQty(totalRewardQty);
        pointRewardData.setTxnAmount(txnAmount);
        pointRewardData.setProgramId(programId);
        pointRewardData.setTxnLocation(txnLocation);
        pointRewardData.setTxnDate(txnDate);
        pointRewardData.setExpiryDt(expiryDt);
        pointRewardData.setAuditDetails(auditDetails);
        return pointRewardData;
    }
}
