package com.inspirenetz.api.core.domain;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Set;

/**
 * Created by sandheepgr on 20/8/14.
 */
@Entity
@Table(name = "TIER_GROUP")
public class TierGroup extends AuditedEntity {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "TIG_ID", nullable = false, insertable = true, updatable = true, length = 10, precision = 0)
    private Long tigId;

    @Basic
    @Column(name = "TIG_NAME", nullable = false, insertable = true, updatable = true, length = 100, precision = 0)
    private String tigName = "";

    @Basic
    @Column(name = "TIG_MERCHANT_NO", nullable = false, insertable = true, updatable = true, length = 10, precision = 0)
    private Long tigMerchantNo;

    @Basic
    @Column(name = "TIG_LOCATION", nullable = false, insertable = true, updatable = true, length = 10, precision = 0)
    private Long tigLocation =0L;

    @Basic
    @Column(name = "TIG_REWARD_CURRENCY", nullable = false, insertable = true, updatable = true, length = 10, precision = 0)
    private Long tigRewardCurrency;

    @Basic
    @Column(name = "TIG_TRANSACTION_CURRENCY", nullable = false, insertable = true, updatable = true, length = 10, precision = 0)
    private Long tigTransactionCurrency;

    @Basic
    @Column(name = "TIG_APPLICABLE_GROUP", nullable = true, insertable = true, updatable = true, length = 10, precision = 0)
    private String tigApplicableGroup="0";

    @Basic
    @Column(name = "TIG_EVALUATION_PERIOD_COMP_TYPE", nullable = false, insertable = true, updatable = true, length = 10, precision = 0)
    private Integer tigEvaluationPeriodCompType;

    @Basic
    @Column(name = "TIG_UPGRADE_CHECK_PERIOD", nullable = false, insertable = true, updatable = true, length = 10, precision = 0)
    private Integer tigUpgradeCheckPeriod;

    @Basic
    @Column(name = "TIG_DOWNGRADE_CHECK_PERIOD", nullable = false, insertable = true, updatable = true, length = 10, precision = 0)
    private Integer tigDowngradeCheckPeriod;

    @OneToMany(fetch = FetchType.EAGER)
    @JoinColumn(name="TIE_PARENT_GROUP",insertable = false,updatable = false)
    private Set<Tier> tierSet;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name="TIG_REWARD_CURRENCY",insertable =  false,updatable = false )
    private RewardCurrency rewardCurrency;




    public Long getTigId() {
        return tigId;
    }

    public void setTigId(Long tigId) {
        this.tigId = tigId;
    }

    public String getTigName() {
        return tigName;
    }

    public void setTigName(String tigName) {
        this.tigName = tigName;
    }

    public Long getTigMerchantNo() {
        return tigMerchantNo;
    }

    public void setTigMerchantNo(Long tigMerchantNo) {
        this.tigMerchantNo = tigMerchantNo;
    }

    public Long getTigLocation() {
        return tigLocation;
    }

    public void setTigLocation(Long tigLocation) {
        this.tigLocation = tigLocation;
    }

    public Long getTigRewardCurrency() {
        return tigRewardCurrency;
    }

    public void setTigRewardCurrency(Long tigRewardCurrency) {
        this.tigRewardCurrency = tigRewardCurrency;
    }

    public Long getTigTransactionCurrency() {
        return tigTransactionCurrency;
    }

    public void setTigTransactionCurrency(Long tigTransactionCurrency) {
        this.tigTransactionCurrency = tigTransactionCurrency;
    }

    public String getTigApplicableGroup() {
        return tigApplicableGroup;
    }

    public void setTigApplicableGroup(String tigApplicableGroup) {
        this.tigApplicableGroup = tigApplicableGroup;
    }

    public Integer getTigEvaluationPeriodCompType() {
        return tigEvaluationPeriodCompType;
    }

    public void setTigEvaluationPeriodCompType(Integer tigEvaluationPeriodCompType) {
        this.tigEvaluationPeriodCompType = tigEvaluationPeriodCompType;
    }

    public Integer getTigUpgradeCheckPeriod() {
        return tigUpgradeCheckPeriod;
    }

    public void setTigUpgradeCheckPeriod(Integer tigUpgradeCheckPeriod) {
        this.tigUpgradeCheckPeriod = tigUpgradeCheckPeriod;
    }

    public Integer getTigDowngradeCheckPeriod() {
        return tigDowngradeCheckPeriod;
    }

    public void setTigDowngradeCheckPeriod(Integer tigDowngradeCheckPeriod) {
        this.tigDowngradeCheckPeriod = tigDowngradeCheckPeriod;
    }

    public Set<Tier> getTierSet() {
        return tierSet;
    }

    public void setTierSet(Set<Tier> tierSet) {
        this.tierSet = tierSet;
    }

    public RewardCurrency getRewardCurrency() {
        return rewardCurrency;
    }

    public void setRewardCurrency(RewardCurrency rewardCurrency) {
        this.rewardCurrency = rewardCurrency;
    }

    @Override
    public String toString() {
        return "TierGroup{" +
                "tigId=" + tigId +
                ", tigName='" + tigName + '\'' +
                ", tigMerchantNo=" + tigMerchantNo +
                ", tigLocation=" + tigLocation +
                ", tigRewardCurrency=" + tigRewardCurrency +
                ", tigTransactionCurrency=" + tigTransactionCurrency +
                ", tigApplicableGroup=" + tigApplicableGroup +
                ", tigEvaluationPeriodCompType=" + tigEvaluationPeriodCompType +
                ", tigUpgradeCheckPeriod=" + tigUpgradeCheckPeriod +
                ", tigDowngradeCheckPeriod=" + tigDowngradeCheckPeriod +
                '}';
    }
}
