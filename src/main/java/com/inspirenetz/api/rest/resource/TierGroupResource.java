package com.inspirenetz.api.rest.resource;

import com.inspirenetz.api.core.domain.Tier;

import java.util.Set;

/**
 * Created by saneeshci on 20/8/14.
 */
public class TierGroupResource extends BaseResource {

    private Long tigId;

    private String tigName;

    private Long tigLocation;

    private Long tigRewardCurrency;

    private Long tigTransactionCurrency;

    private String tigApplicableGroup="0";

    private Integer tigEvaluationPeriodCompType;

    private Integer tigUpgradeCheckPeriod;

    private Integer tigDowngradeCheckPeriod;

    private Set<Tier> tierSet;


    private String rwdCurrencyName = "";


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

    public String getRwdCurrencyName() {
        return rwdCurrencyName;
    }

    public void setRwdCurrencyName(String rwdCurrencyName) {
        this.rwdCurrencyName = rwdCurrencyName;
    }


    @Override
    public String toString() {
        return "TierGroupResource{" +
                "tigId=" + tigId +
                ", tigName='" + tigName + '\'' +
                ", tigLocation=" + tigLocation +
                ", tigRewardCurrency=" + tigRewardCurrency +
                ", tigTransactionCurrency=" + tigTransactionCurrency +
                ", tigApplicableGroup='" + tigApplicableGroup + '\'' +
                ", tigEvaluationPeriodCompType=" + tigEvaluationPeriodCompType +
                ", tigUpgradeCheckPeriod=" + tigUpgradeCheckPeriod +
                ", tigDowngradeCheckPeriod=" + tigDowngradeCheckPeriod +
                ", tierSet=" + tierSet +
                ", rwdCurrencyName='" + rwdCurrencyName + '\'' +
                '}';
    }
}
