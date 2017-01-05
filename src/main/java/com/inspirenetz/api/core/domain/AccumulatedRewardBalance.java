package com.inspirenetz.api.core.domain;

import javax.persistence.*;

/**
 * Created by sandheepgr on 23/8/14.
 */
@Entity
@Table(name = "ACCUMULATED_REWARD_BALANCE")
public class AccumulatedRewardBalance extends AuditedEntity{


    @Id
    @Column(name = "ARB_ID", nullable = false, insertable = true, updatable = true, length = 10, precision = 0)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long arbId;

    @Basic
    @Column(name = "ARB_MERCHANT_NO", nullable = false, insertable = true, updatable = true, length = 10, precision = 0)
    private Long arbMerchantNo =0l;

    @Basic
    @Column(name = "ARB_LOYALTY_ID", nullable = false, insertable = true, updatable = true, length = 20, precision = 0)
    private String arbLoyaltyId = "";

    @Basic
    @Column(name = "ARB_REWARD_CURRENCY", nullable = false, insertable = true, updatable = true, length = 10, precision = 0)
    private Long arbRewardCurrency = 0L;

    @Basic
    @Column(name = "ARB_REWARD_BALANCE", nullable = true, insertable = true, updatable = true, length = 10, precision = 2)
    private Double arbRewardBalance = 0.0;


    public Long getArbId() {
        return arbId;
    }

    public void setArbId(Long arbId) {
        this.arbId = arbId;
    }

    public Long getArbMerchantNo() {
        return arbMerchantNo;
    }

    public void setArbMerchantNo(Long arbMerchantNo) {
        this.arbMerchantNo = arbMerchantNo;
    }

    public String getArbLoyaltyId() {
        return arbLoyaltyId;
    }

    public void setArbLoyaltyId(String arbLoyaltyId) {
        this.arbLoyaltyId = arbLoyaltyId;
    }

    public Long getArbRewardCurrency() {
        return arbRewardCurrency;
    }

    public void setArbRewardCurrency(Long arbRewardCurrency) {
        this.arbRewardCurrency = arbRewardCurrency;
    }

    public Double getArbRewardBalance() {
        return arbRewardBalance;
    }

    public void setArbRewardBalance(Double arbRewardBalance) {
        this.arbRewardBalance = arbRewardBalance;
    }


    @Override
    public String toString() {
        return "AccumulatedRewardBalance{" +
                "arbId=" + arbId +
                ", arbMerchantNo=" + arbMerchantNo +
                ", arbLoyaltyId='" + arbLoyaltyId + '\'' +
                ", arbRewardCurrency=" + arbRewardCurrency +
                ", arbRewardBalance=" + arbRewardBalance +
                '}';
    }
}
