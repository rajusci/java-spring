package com.inspirenetz.api.rest.resource;

import java.sql.Date;

/**
 * Created by sandheepgr on 5/5/14.
 */
public class CustomerRewardExpiryResource extends BaseResource {

    private Long creMerchantNo;

    private String creLoyaltyId;

    private Long creRewardCurrencyId;

    private Date creExpiryDt;

    private double creRewardBalance;

    private String rwdCurrencyName;


    public Long getCreMerchantNo() {
        return creMerchantNo;
    }

    public void setCreMerchantNo(Long creMerchantNo) {
        this.creMerchantNo = creMerchantNo;
    }

    public String getCreLoyaltyId() {
        return creLoyaltyId;
    }

    public void setCreLoyaltyId(String creLoyaltyId) {
        this.creLoyaltyId = creLoyaltyId;
    }


    public Long getCreRewardCurrencyId() {
        return creRewardCurrencyId;
    }

    public void setCreRewardCurrencyId(Long creRewardCurrencyId) {
        this.creRewardCurrencyId = creRewardCurrencyId;
    }

    public Date getCreExpiryDt() {
        return creExpiryDt;
    }

    public void setCreExpiryDt(Date creExpiryDt) {
        this.creExpiryDt = creExpiryDt;
    }

    public double getCreRewardBalance() {
        return creRewardBalance;
    }

    public void setCreRewardBalance(double creRewardBalance) {
        this.creRewardBalance = creRewardBalance;
    }

    public String getRwdCurrencyName() {
        return rwdCurrencyName;
    }

    public void setRwdCurrencyName(String rwdCurrencyName) {
        this.rwdCurrencyName = rwdCurrencyName;
    }
}
