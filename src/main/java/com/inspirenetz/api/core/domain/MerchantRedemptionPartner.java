package com.inspirenetz.api.core.domain;

import com.inspirenetz.api.core.dictionary.IndicatorStatus;
import org.hibernate.validator.constraints.Range;

import javax.persistence.*;

/**
 * Created by ameen on 25/6/15.
 */
@Entity
@Table(name = "MERCHANT_REDEMPTION_PARTNER")
public class MerchantRedemptionPartner extends AuditedEntity {


    @Column(name = "MRP_ID")
    @Basic(fetch = FetchType.EAGER)
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long mrpId;

    @Column(name = "MRP_MERCHANT_NO", nullable = false, insertable = true, updatable = true, length = 10, precision = 0)
    private Long mrpMerchantNo = 0L;

    @Column(name = "MRP_REDEMPTION_MERCHANT", nullable = false, insertable = true, updatable = true, length = 10, precision = 0)
    private Long mrpRedemptionMerchant = 0L;

    @Basic
    @Column(name = "MRP_ENABLED", nullable = false, insertable = true, updatable = true, length = 200, precision = 0)
    private Integer  mrpEnabled= IndicatorStatus.NO;

    @Column(name = "MRP_REWARD_CURRENCY", nullable = true, insertable = true, updatable = true, length = 10, precision = 0)
    private Long mrpRewardCurrency = 0L;

    @Basic
    @Column(name = "MRP_CASHBACK_RATIO_DENO", nullable = true)
    private Double mrpCashbackRatioDeno = 0.0;

    @Column(name = "MRP_THRESHOLD_UNSETTLED", nullable = true, insertable = true, updatable = true, length = 8, precision = 16,scale=2)
    private Double mrpThresholdUnsettled = 0.0;

    @Transient
    private String redemptionMerchantName;



    public Long getMrpId() {
        return mrpId;
    }

    public void setMrpId(Long mrpId) {
        this.mrpId = mrpId;
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

    public Long getMrpMerchantNo() {
        return mrpMerchantNo;
    }

    public void setMrpMerchantNo(Long mrpMerchantNo) {
        this.mrpMerchantNo = mrpMerchantNo;
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
        return "MerchantRedemptionPartner{" +
                "mrpId=" + mrpId +
                ", mrpMerchantNo=" + mrpMerchantNo +
                ", mrpRedemptionMerchant=" + mrpRedemptionMerchant +
                ", mrpEnabled=" + mrpEnabled +
                ", mrpRewardCurrency=" + mrpRewardCurrency +
                ", mrpCashbackRatioDeno=" + mrpCashbackRatioDeno +
                ", redemptionMerchantName='" + redemptionMerchantName + '\'' +
                '}';
    }
}
