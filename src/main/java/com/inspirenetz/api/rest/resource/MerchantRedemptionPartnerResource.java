package com.inspirenetz.api.rest.resource;

import com.inspirenetz.api.core.dictionary.IndicatorStatus;
import org.springframework.hateoas.ResourceSupport;

/**
 * Created by ameen on 25/6/15.
 */
public class MerchantRedemptionPartnerResource extends ResourceSupport {

    private Long mrpId;
    private Long mrpMerchantNo = 0L;
    private Long mrpRedemptionMerchant = 0L;
    private Integer  mrpEnabled= IndicatorStatus.NO;
    private String redemptionMerchantName;
    private Long mrpRewardCurrency;
    private Double mrpCashbackRatioDeno = 0.0;
    private Double mrpThresholdUnsettled;



    public Long getMrpId() {
        return mrpId;
    }

    public void setMrpId(Long mrpId) {
        this.mrpId = mrpId;
    }

    public Long getMrpMerchantNo() {
        return mrpMerchantNo;
    }

    public void setMrpMerchantNo(Long mrpMerchantNo) {
        this.mrpMerchantNo = mrpMerchantNo;
    }

    public Long getMrpRedemptionMerchant() {
        return mrpRedemptionMerchant;
    }

    public void setMrpRedemptionMerchant(Long mrpRedemptionMerchant) {
        this.mrpRedemptionMerchant = mrpRedemptionMerchant;
    }

    public Integer getMrpEnabled() {
        return mrpEnabled;
    }

    public void setMrpEnabled(Integer mrpEnabled) {
        this.mrpEnabled = mrpEnabled;
    }

    public String getRedemptionMerchantName() {
        return redemptionMerchantName;
    }

    public void setRedemptionMerchantName(String redemptionMerchantName) {
        this.redemptionMerchantName = redemptionMerchantName;
    }

    public Long getMrpRewardCurrency() {
        return mrpRewardCurrency;
    }

    public void setMrpRewardCurrency(Long mrpRewardCurrency) {
        this.mrpRewardCurrency = mrpRewardCurrency;
    }


    public Double getMrpCashbackRatioDeno() {
        return mrpCashbackRatioDeno;
    }

    public void setMrpCashbackRatioDeno(Double mrpCashbackRatioDeno) {
        this.mrpCashbackRatioDeno = mrpCashbackRatioDeno;
    }

    public Double getMrpThresholdUnsettled() {
        return mrpThresholdUnsettled;
    }

    public void setMrpThresholdUnsettled(Double mrpThresholdUnsettled) {
        this.mrpThresholdUnsettled = mrpThresholdUnsettled;

    }

    @Override
    public String toString() {
        return "MerchantRedemptionPartnerResource{" +
                "mrpId=" + mrpId +
                ", mrpMerchantNo=" + mrpMerchantNo +
                ", mrpRedemptionMerchant=" + mrpRedemptionMerchant +
                ", mrpEnabled=" + mrpEnabled +
                ", redemptionMerchantName='" + redemptionMerchantName + '\'' +
                ", mrpRewardCurrency=" + mrpRewardCurrency +
                ", mrpCashbackRatioDeno=" + mrpCashbackRatioDeno +
                '}';
    }
}
