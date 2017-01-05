package com.inspirenetz.api.core.domain;

import javax.persistence.*;
import java.io.Serializable;

/**
 * Created by sandheepgr on 10/3/14.
 */
@Entity
@Table(name="LINKED_REWARD_BALANCE")
public class LinkedRewardBalance extends AuditedEntity implements Serializable{

    @Column(name = "LRB_ID")
    @Basic(fetch = FetchType.EAGER)
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long lrbId;

    @Column(name = "LRB_MERCHANT_NO",nullable = false)
    @Basic(fetch = FetchType.EAGER)
    private Long lrbMerchantNo;

    @Column(name = "LRB_PRIMARY_LOYALTY_ID",nullable = false)
    @Basic(fetch = FetchType.EAGER)
    private String lrbPrimaryLoyaltyId;

    @Column(name = "LRB_REWARD_CURRENCY",nullable = false)
    @Basic(fetch = FetchType.EAGER)
    private Long lrbRewardCurrency;

    @Column(name = "LRB_REWARD_BALANCE",nullable = false)
    @Basic(fetch = FetchType.EAGER)
    private Double lrbRewardBalance;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name ="LRB_REWARD_CURRENCY",insertable = false,updatable = false)
    private RewardCurrency rewardCurrency;



    public Long getLrbId() {
        return lrbId;
    }

    public void setLrbId(Long lrbId) {
        this.lrbId = lrbId;
    }

    public Long getLrbMerchantNo() {
        return lrbMerchantNo;
    }

    public void setLrbMerchantNo(Long lrbMerchantNo) {
        this.lrbMerchantNo = lrbMerchantNo;
    }

    public String getLrbPrimaryLoyaltyId() {
        return lrbPrimaryLoyaltyId;
    }

    public void setLrbPrimaryLoyaltyId(String lrbPrimaryLoyaltyId) {
        this.lrbPrimaryLoyaltyId = lrbPrimaryLoyaltyId;
    }

    public Long getLrbRewardCurrency() {
        return lrbRewardCurrency;
    }

    public void setLrbRewardCurrency(Long lrbRewardCurrency) {
        this.lrbRewardCurrency = lrbRewardCurrency;
    }

    public Double getLrbRewardBalance() {
        return lrbRewardBalance;
    }

    public void setLrbRewardBalance(Double lrbRewardBalance) {
        this.lrbRewardBalance = lrbRewardBalance;
    }

    public RewardCurrency getRewardCurrency() {
        return rewardCurrency;
    }

    public void setRewardCurrency(RewardCurrency rewardCurrency) {
        this.rewardCurrency = rewardCurrency;
    }

    @Override
    public String toString() {
        return "LinkedRewardBalance{" +
                "lrbId=" + lrbId +
                ", lrbMerchantNo=" + lrbMerchantNo +
                ", lrbPrimaryLoyaltyId='" + lrbPrimaryLoyaltyId + '\'' +
                ", lrbRewardCurrency=" + lrbRewardCurrency +
                ", lrbRewardBalance=" + lrbRewardBalance +
                '}';
    }
}
