package com.inspirenetz.api.test.core.builder;

import com.inspirenetz.api.core.dictionary.CustomerSegmentComparisonType;
import com.inspirenetz.api.core.dictionary.IndicatorStatus;
import com.inspirenetz.api.core.dictionary.RuleApplicationType;
import com.inspirenetz.api.core.domain.Tier;

import java.util.Date;

/**
 * Created by sandheepgr on 2/9/14.
 */
public class TierBuilder {
    private Long tieId;
    private Long tieParentGroup = 0L;
    private String tieName = "";
    private Integer tieClass = 0;
    private Integer tierIsTransferPointsAllowedInd = IndicatorStatus.NO;
    private Integer tiePointInd = IndicatorStatus.NO;
    private Double tiePointValue1 = 0.0;
    private Integer tiePointCompType = CustomerSegmentComparisonType.MORE_THAN;
    private Double tiePointValue2 = 0.0;
    private Integer tieAmountInd = IndicatorStatus.NO;
    private Double tieAmountValue1 = 0.0;
    private Integer tieAmountCompType = CustomerSegmentComparisonType.MORE_THAN;
    private Double tieAmountValue2 = 0.0;
    private Integer tieRuleApplicationType = RuleApplicationType.EITHER;
    private Date createdAt;
    private String createdBy;
    private Date updatedAt;
    private String updatedBy;

    private TierBuilder() {
    }

    public static TierBuilder aTier() {
        return new TierBuilder();
    }

    public TierBuilder withTieId(Long tieId) {
        this.tieId = tieId;
        return this;
    }

    public TierBuilder withTieParentGroup(Long tieParentGroup) {
        this.tieParentGroup = tieParentGroup;
        return this;
    }

    public TierBuilder withTieName(String tieName) {
        this.tieName = tieName;
        return this;
    }

    public TierBuilder withTieClass(Integer tieClass) {
        this.tieClass = tieClass;
        return this;
    }

    public TierBuilder withTierIsTransferPointsAllowedInd(Integer tierIsTransferPointsAllowedInd) {
        this.tierIsTransferPointsAllowedInd = tierIsTransferPointsAllowedInd;
        return this;
    }

    public TierBuilder withTiePointInd(Integer tiePointInd) {
        this.tiePointInd = tiePointInd;
        return this;
    }

    public TierBuilder withTiePointValue1(Double tiePointValue1) {
        this.tiePointValue1 = tiePointValue1;
        return this;
    }

    public TierBuilder withTiePointCompType(Integer tiePointCompType) {
        this.tiePointCompType = tiePointCompType;
        return this;
    }

    public TierBuilder withTiePointValue2(Double tiePointValue2) {
        this.tiePointValue2 = tiePointValue2;
        return this;
    }

    public TierBuilder withTieAmountInd(Integer tieAmountInd) {
        this.tieAmountInd = tieAmountInd;
        return this;
    }

    public TierBuilder withTieAmountValue1(Double tieAmountValue1) {
        this.tieAmountValue1 = tieAmountValue1;
        return this;
    }

    public TierBuilder withTieAmountCompType(Integer tieAmountCompType) {
        this.tieAmountCompType = tieAmountCompType;
        return this;
    }

    public TierBuilder withTieAmountValue2(Double tieAmountValue2) {
        this.tieAmountValue2 = tieAmountValue2;
        return this;
    }

    public TierBuilder withTieRuleApplicationType(Integer tieRuleApplicationType) {
        this.tieRuleApplicationType = tieRuleApplicationType;
        return this;
    }

    public TierBuilder withCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
        return this;
    }

    public TierBuilder withCreatedBy(String createdBy) {
        this.createdBy = createdBy;
        return this;
    }

    public TierBuilder withUpdatedAt(Date updatedAt) {
        this.updatedAt = updatedAt;
        return this;
    }

    public TierBuilder withUpdatedBy(String updatedBy) {
        this.updatedBy = updatedBy;
        return this;
    }

    public Tier build() {
        Tier tier = new Tier();
        tier.setTieId(tieId);
        tier.setTieParentGroup(tieParentGroup);
        tier.setTieName(tieName);
        tier.setTieClass(tieClass);
        tier.setTieIsTransferPointsAllowedInd(tierIsTransferPointsAllowedInd);
        tier.setTiePointInd(tiePointInd);
        tier.setTiePointValue1(tiePointValue1);
        tier.setTiePointCompType(tiePointCompType);
        tier.setTiePointValue2(tiePointValue2);
        tier.setTieAmountInd(tieAmountInd);
        tier.setTieAmountValue1(tieAmountValue1);
        tier.setTieAmountCompType(tieAmountCompType);
        tier.setTieAmountValue2(tieAmountValue2);
        tier.setTieRuleApplicationType(tieRuleApplicationType);
        tier.setCreatedAt(createdAt);
        tier.setCreatedBy(createdBy);
        tier.setUpdatedAt(updatedAt);
        tier.setUpdatedBy(updatedBy);
        return tier;
    }
}
