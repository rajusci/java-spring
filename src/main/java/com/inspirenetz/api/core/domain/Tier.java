package com.inspirenetz.api.core.domain;

import com.inspirenetz.api.core.dictionary.CustomerSegmentComparisonType;
import com.inspirenetz.api.core.dictionary.ImagePrimaryId;
import com.inspirenetz.api.core.dictionary.IndicatorStatus;
import com.inspirenetz.api.core.dictionary.RuleApplicationType;

import javax.persistence.*;

/**
 * Created by sandheepgr on 21/8/14.
 */
@Entity
@Table(name="TIER")
public class Tier extends AuditedEntity {



    @Id
    @Column(name = "TIE_ID", nullable = false, insertable = true, updatable = true, length = 10, precision = 0)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long tieId;

    @Basic
    @Column(name = "TIE_PARENT_GROUP", nullable = false, insertable = true, updatable = true, length = 10, precision = 0)
    private Long tieParentGroup = 0L;

    @Basic
    @Column(name = "TIE_NAME", nullable = false, insertable = true, updatable = true, length = 200, precision = 0)
    private String tieName = "";

    @Basic
    @Column(name = "TIE_CLASS", nullable = true, insertable = true, updatable = true, length = 45, precision = 0)
    private Integer tieClass = 0;

    @Basic
    @Column(name = "TIE_IS_TRANSFER_POINTS_ALLOWED_IND", nullable = true, insertable = true, updatable = true, length = 45, precision = 0)
    private Integer tieIsTransferPointsAllowedInd = IndicatorStatus.NO;

    @Basic
    @Column(name = "TIE_POINT_IND", nullable = false, insertable = true, updatable = true, length = 10, precision = 0)
    private Integer tiePointInd = IndicatorStatus.NO;

    @Basic
    @Column(name = "TIE_POINT_VALUE1", nullable = true, insertable = true, updatable = true, length = 22, precision = 0)
    private Double tiePointValue1 = 0.0;

    @Basic
    @Column(name = "TIE_POINT_COMP_TYPE", nullable = false, insertable = true, updatable = true, length = 10, precision = 0)
    private Integer tiePointCompType = CustomerSegmentComparisonType.MORE_THAN;

    @Basic
    @Column(name = "TIE_POINT_VALUE2", nullable = true, insertable = true, updatable = true, length = 22, precision = 0)
    private Double tiePointValue2 = 0.0;

    @Basic
    @Column(name = "TIE_AMOUNT_IND", nullable = false, insertable = true, updatable = true, length = 10, precision = 0)
    private Integer tieAmountInd = IndicatorStatus.NO;

    @Basic
    @Column(name = "TIE_AMOUNT_VALUE1", nullable = true, insertable = true, updatable = true, length = 22, precision = 0)
    private Double tieAmountValue1 = 0.0;

    @Basic
    @Column(name = "TIE_AMOUNT_COMP_TYPE", nullable = false, insertable = true, updatable = true, length = 10, precision = 0)
    private Integer tieAmountCompType = CustomerSegmentComparisonType.MORE_THAN;

    @Basic
    @Column(name = "TIE_AMOUNT_VALUE2", nullable = true, insertable = true, updatable = true, length = 22, precision = 0)
    private Double tieAmountValue2 = 0.0;

    @Basic
    @Column(name = "TIE_RULE_APPLICATION_TYPE", nullable = false, insertable = true, updatable = true, length = 10, precision = 0)
    private Integer tieRuleApplicationType = RuleApplicationType.EITHER;

    @Basic
    @Column(name = "TIE_IMAGE", nullable = true, insertable = true, updatable = true, length = 10, precision = 0)
    private Long tieImage = ImagePrimaryId.PRIMARY_TIER_IMAGE;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name="TIE_IMAGE",insertable = false,updatable = false)
    private Image image;

    @Transient
    private String tieImagePath;

    public Long getTieImage() {
        return tieImage;
    }

    public void setTieImage(Long tieImage) {
        this.tieImage = tieImage;
    }

    public Image getImage() {
        return image;
    }

    public void setImage(Image image) {
        this.image = image;
    }

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

    public Integer getTieIsTransferPointsAllowedInd() {
        return tieIsTransferPointsAllowedInd;
    }

    public void setTieIsTransferPointsAllowedInd(Integer tieIsTransferPointsAllowedInd) {
        this.tieIsTransferPointsAllowedInd = tieIsTransferPointsAllowedInd;
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

    @Override
    public String toString() {
        return "Tier{" +
                "tieId=" + tieId +
                ", tieParentGroup=" + tieParentGroup +
                ", tieName='" + tieName + '\'' +
                ", tieClass=" + tieClass +
                ", tieIsTransferPointsAllowedInd=" + tieIsTransferPointsAllowedInd +
                ", tiePointInd=" + tiePointInd +
                ", tiePointValue1=" + tiePointValue1 +
                ", tiePointCompType=" + tiePointCompType +
                ", tiePointValue2=" + tiePointValue2 +
                ", tieAmountInd=" + tieAmountInd +
                ", tieAmountValue1=" + tieAmountValue1 +
                ", tieAmountCompType=" + tieAmountCompType +
                ", tieAmountValue2=" + tieAmountValue2 +
                ", tieRuleApplicationType=" + tieRuleApplicationType +
                '}';
    }
}
