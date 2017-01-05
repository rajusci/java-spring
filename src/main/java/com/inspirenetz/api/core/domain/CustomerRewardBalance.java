package com.inspirenetz.api.core.domain;

import javax.persistence.*;

/**
 * Created by sandheepgr on 17/2/14.
 */
@Entity
@Table(name="CUSTOMER_REWARD_BALANCE")
public class CustomerRewardBalance  extends AuditedEntity {

    @Id
    @Column(name = "CRB_ID",nullable = false)
    @Basic(fetch = FetchType.EAGER)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long crbId;

    @Column(name = "CRB_MERCHANT_NO",nullable = false)
    @Basic(fetch = FetchType.EAGER)
    private Long crbMerchantNo;

    @Column(name = "CRB_LOYALTY_ID",nullable = false)
    @Basic(fetch = FetchType.EAGER)
    private String crbLoyaltyId;

    @Column(name = "CRB_REWARD_CURRENCY",nullable = false)
    @Basic(fetch = FetchType.EAGER)
    private Long crbRewardCurrency;

    @Column(name = "CRB_REWARD_BALANCE",nullable = false)
    @Basic(fetch = FetchType.EAGER)
    private double crbRewardBalance;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "CRB_REWARD_CURRENCY",insertable = false,updatable = false)
    private RewardCurrency rewardCurrency;


    @Column(name = "version")
    @Version
    private Long version;



    public Long getCrbId() {
        return crbId;
    }

    public void setCrbId(Long crbId) {
        this.crbId = crbId;
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

    public Long getCrbRewardCurrency() {
        return crbRewardCurrency;
    }

    public void setCrbRewardCurrency(Long crbRewardCurrency) {
        this.crbRewardCurrency = crbRewardCurrency;
    }

    public double getCrbRewardBalance() {
        return crbRewardBalance;
    }

    public void setCrbRewardBalance(double crbRewardBalance) {
        this.crbRewardBalance = crbRewardBalance;
    }

    public RewardCurrency getRewardCurrency() {
        return rewardCurrency;
    }

    public void setRewardCurrency(RewardCurrency rewardCurrency) {
        this.rewardCurrency = rewardCurrency;
    }

    public Long getVersion() {
        return version;
    }

    public void setVersion(Long version) {
        this.version = version;
    }

    @Override
    public String toString() {
        return "CustomerRewardBalance{" +
                "crbId=" + crbId +
                ", crbMerchantNo=" + crbMerchantNo +
                ", crbLoyaltyId='" + crbLoyaltyId + '\'' +
                ", crbRewardCurrency=" + crbRewardCurrency +
                ", crbRewardBalance=" + crbRewardBalance +
                ", rewardCurrency=" + rewardCurrency +
                ", version=" + version +
                '}';
    }
}
