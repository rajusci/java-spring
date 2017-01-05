package com.inspirenetz.api.rest.resource;

import com.inspirenetz.api.core.dictionary.CustomerSegmentComparisonType;
import com.inspirenetz.api.core.dictionary.IndicatorStatus;
import com.inspirenetz.api.core.dictionary.RuleApplicationType;

/**
 * Created by sandheepgr on 21/8/14.
 */
public class TierResource extends BaseResource {


    private Long tieId;

    private Long tieParentGroup = 0L;

    private String tieName = "";

    private Integer tieClass = 0;

    private Integer tiePointInd = IndicatorStatus.NO;

    private Double tiePointValue1 = 0.0;

    private Integer tiePointCompType = CustomerSegmentComparisonType.MORE_THAN;

    private Double tiePointValue2 = 0.0;

    private Integer tieAmountInd = IndicatorStatus.NO;

    private Double tieAmountValue1 = 0.0;

    private Integer tieAmountCompType = CustomerSegmentComparisonType.MORE_THAN;

    private Double tieAmountValue2 = 0.0;

    private Integer tieRuleApplicationType = RuleApplicationType.EITHER;

    private String tieImagePath;

    private Long tieImage;



    public Long getTieId() {
        return tieId;
    }

    public void setTieId(Long tieId) {
        this.tieId = tieId;
    }

    public Long getTieParentGroup() {
        return tieParentGroup;
    }

    public void setTieParentGroup(Long tieParentGroup) {
        this.tieParentGroup = tieParentGroup;
    }

    public String getTieName() {
        return tieName;
    }

    public void setTieName(String tieName) {
        this.tieName = tieName;
    }

    public Integer getTieClass() {
        return tieClass;
    }

    public void setTieClass(Integer tieClass) {
        this.tieClass = tieClass;
    }

    public Integer getTiePointInd() {
        return tiePointInd;
    }

    public void setTiePointInd(Integer tiePointInd) {
        this.tiePointInd = tiePointInd;
    }

    public Double getTiePointValue1() {
        return tiePointValue1;
    }

    public void setTiePointValue1(Double tiePointValue1) {
        this.tiePointValue1 = tiePointValue1;
    }

    public Integer getTiePointCompType() {
        return tiePointCompType;
    }

    public void setTiePointCompType(Integer tiePointCompType) {
        this.tiePointCompType = tiePointCompType;
    }

    public Double getTiePointValue2() {
        return tiePointValue2;
    }

    public void setTiePointValue2(Double tiePointValue2) {
        this.tiePointValue2 = tiePointValue2;
    }

    public Integer getTieAmountInd() {
        return tieAmountInd;
    }

    public void setTieAmountInd(Integer tieAmountInd) {
        this.tieAmountInd = tieAmountInd;
    }

    public Double getTieAmountValue1() {
        return tieAmountValue1;
    }

    public void setTieAmountValue1(Double tieAmountValue1) {
        this.tieAmountValue1 = tieAmountValue1;
    }

    public Integer getTieAmountCompType() {
        return tieAmountCompType;
    }

    public void setTieAmountCompType(Integer tieAmountCompType) {
        this.tieAmountCompType = tieAmountCompType;
    }

    public Double getTieAmountValue2() {
        return tieAmountValue2;
    }

    public void setTieAmountValue2(Double tieAmountValue2) {
        this.tieAmountValue2 = tieAmountValue2;
    }

    public Integer getTieRuleApplicationType() {
        return tieRuleApplicationType;
    }

    public void setTieRuleApplicationType(Integer tieRuleApplicationType) {
        this.tieRuleApplicationType = tieRuleApplicationType;
    }

    public String getTieImagePath() {
        return tieImagePath;
    }

    public void setTieImagePath(String tieImagePath) {
        this.tieImagePath = tieImagePath;
    }

    public Long getTieImage() {
        return tieImage;
    }

    public void setTieImage(Long tieImage) {
        this.tieImage = tieImage;
    }

    @Override
    public String toString() {
        return "TierResource{" +
                "tieId=" + tieId +
                ", tieParentGroup=" + tieParentGroup +
                ", tieName='" + tieName + '\'' +
                ", tieClass=" + tieClass +
                ", tiePointInd=" + tiePointInd +
                ", tiePointValue1=" + tiePointValue1 +
                ", tiePointCompType=" + tiePointCompType +
                ", tiePointValue2=" + tiePointValue2 +
                ", tieAmountInd=" + tieAmountInd +
                ", tieAmountValue1=" + tieAmountValue1 +
                ", tieAmountCompType=" + tieAmountCompType +
                ", tieAmountValue2=" + tieAmountValue2 +
                ", tieRuleApplicationType=" + tieRuleApplicationType +
                ", tieImagePath='" + tieImagePath + '\'' +
                ", tieImage=" + tieImage +
                '}';
    }
}
