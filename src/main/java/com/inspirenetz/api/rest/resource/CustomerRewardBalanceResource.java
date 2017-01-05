package com.inspirenetz.api.rest.resource;


/**
 * Created by sandheepgr on 10/3/14.
 */
public class CustomerRewardBalanceResource extends BaseResource {

    private String crbLoyaltyId;

    private Long crbMerchantNo;

    private Long crbRewardCurrency;

    private double crbRewardBalance;

    private String rwdCurrencyName;

    private double rwdCashbackValue;

    private double rwdRatioDeno =1 ;

    private boolean isDrawChance;

    private String merchantName;



    public boolean isDrawChance() {
        return isDrawChance;
    }

    public void setDrawChance(boolean isDrawChance) {
        this.isDrawChance = isDrawChance;
    }

    public String getRwdCurrencyName() {
        return rwdCurrencyName;
    }

    public void setRwdCurrencyName(String rwdCurrencyName) {
        this.rwdCurrencyName = rwdCurrencyName;
    }

    public double getRwdCashbackValue() {
        return rwdCashbackValue;
    }

    public void setRwdCashbackValue(double rwdCashbackValue) {
        this.rwdCashbackValue = rwdCashbackValue;
    }

    public Long getCrbRewardCurrency() {
        return crbRewardCurrency;
    }

    public void setCrbRewardCurrency(Long crbRewardCurrency) {
        this.crbRewardCurrency = crbRewardCurrency;
    }

    public Long getCrbMerchantNo() {
        return crbMerchantNo;
    }

    public void setCrbMerchantNo(Long crbMerchantNo) {
        this.crbMerchantNo = crbMerchantNo;
    }

    public String getCrbLoyaltyId() {
        return crbLoyaltyId;
    }

    public void setCrbLoyaltyId(String crbLoyaltyId) {
        this.crbLoyaltyId = crbLoyaltyId;
    }

    public double getCrbRewardBalance() {
        return crbRewardBalance;
    }

    public void setCrbRewardBalance(double crbRewardBalance) {
        this.crbRewardBalance = crbRewardBalance;
    }

    public double getRwdRatioDeno() {
        return rwdRatioDeno;
    }

    public void setRwdRatioDeno(double rwdRatioDeno) {
        this.rwdRatioDeno = rwdRatioDeno;
    }

    public String getMerchantName() {
        return merchantName;
    }

    public void setMerchantName(String merchantName) {
        this.merchantName = merchantName;
    }



    @Override
    public String toString() {
        return "CustomerRewardBalanceResource{" +
                "crbLoyaltyId='" + crbLoyaltyId + '\'' +
                ", crbMerchantNo=" + crbMerchantNo +
                ", crbRewardCurrency=" + crbRewardCurrency +
                ", crbRewardBalance=" + crbRewardBalance +
                ", rwdCurrencyName='" + rwdCurrencyName + '\'' +
                ", rwdCashbackValue=" + rwdCashbackValue +
                ", rwdRatioDeno=" + rwdRatioDeno +
                ", isDrawChance=" + isDrawChance +
                ", merchantName='" + merchantName + '\'' +
                '}';
    }
}
