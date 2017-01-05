package com.inspirenetz.api.core.domain;

import javax.persistence.*;

/**
 * Created by sandheepgr on 24/8/14.
 */
@Entity
@Table(name = "ACCOUNT_BUNDLING_SETTING")
public class AccountBundlingSetting extends AuditedEntity {


    @Id
    @Column(name = "ABS_ID", nullable = false, insertable = true, updatable = true, length = 10, precision = 0)
    @GeneratedValue  ( strategy = GenerationType.IDENTITY)
    private Long absId;

    @Basic
    @Column(name = "ABS_MERCHANT_NO", nullable = false, insertable = true, updatable = true, length = 10, precision = 0)
    private Long absMerchantNo;

    @Basic
    @Column(name = "ABS_LOCATION", nullable = false, insertable = true, updatable = true, length = 10, precision = 0)
    private Long absLocation;

    @Basic
    @Column(name = "ABS_LINKING_TYPE", nullable = true, insertable = true, updatable = true, length = 10, precision = 0)
    private Integer absLinkingType;

    @Basic
    @Column(name = "ABS_LINKING_ELIGIBILITY", nullable = true, insertable = true, updatable = true, length = 10, precision = 0)
    private Integer absLinkingEligibility;

    @Basic
    @Column(name = "ABS_PRIMARY_ACCOUNT_ELIGIBILITY", nullable = true, insertable = true, updatable = true, length = 10, precision = 0)
    private Integer absPrimaryAccountEligibility;

    @Basic
    @Column(name = "ABS_BUNDLING_ACTION_INITIATION", nullable = true, insertable = true, updatable = true, length = 10, precision = 0)
    private Integer absBundlingActionInitiation;

    @Basic
    @Column(name = "ABS_PRIMARY_ACCOUNT_CATEGORY", nullable = true, insertable = true, updatable = true, length = 100, precision = 0)
    private String absPrimaryAccountCategory;

    @Basic
    @Column(name = "ABS_BUNDLING_CONFIRMATION_TYPE", nullable = true, insertable = true, updatable = true, length = 10, precision = 0)
    private Integer absBundlingConfirmationType;

    @Basic
    @Column(name = "ABS_BUNDLING_REDEMPTION", nullable = true, insertable = true, updatable = true, length = 10, precision = 0)
    private Integer absBundlingRedemption;

    @Basic
    @Column(name = "ABS_LINKED_ACCOUNT_LIMIT", nullable = true, insertable = true, updatable = true, length = 10, precision = 0)
    private Integer absLinkedAccountLimit;

    @Basic
    @Column(name = "ABS_CONFIRMATION_EXPIRY_LIMIT", nullable = true, insertable = true, updatable = true, length = 10, precision = 0)
    private Integer absConfirmationExpiryLimit;

    @Basic
    @Column(name = "ABS_TIER_BEHAVIOUR", nullable = true, insertable = true, updatable = true, length = 10, precision = 0)
    private Integer absTierBehaviour;


    public Long getAbsId() {
        return absId;
    }

    public void setAbsId(Long absId) {
        this.absId = absId;
    }

    public Long getAbsMerchantNo() {
        return absMerchantNo;
    }

    public void setAbsMerchantNo(Long absMerchantNo) {
        this.absMerchantNo = absMerchantNo;
    }

    public Long getAbsLocation() {
        return absLocation;
    }

    public void setAbsLocation(Long absLocation) {
        this.absLocation = absLocation;
    }

    public Integer getAbsLinkingType() {
        return absLinkingType;
    }

    public void setAbsLinkingType(Integer absLinkingType) {
        this.absLinkingType = absLinkingType;
    }

    public Integer getAbsLinkingEligibility() {
        return absLinkingEligibility;
    }

    public void setAbsLinkingEligibility(Integer absLinkingEligibility) {
        this.absLinkingEligibility = absLinkingEligibility;
    }

    public Integer getAbsPrimaryAccountEligibility() {
        return absPrimaryAccountEligibility;
    }

    public void setAbsPrimaryAccountEligibility(Integer absPrimaryAccountEligibility) {
        this.absPrimaryAccountEligibility = absPrimaryAccountEligibility;
    }

    public Integer getAbsBundlingActionInitiation() {
        return absBundlingActionInitiation;
    }

    public void setAbsBundlingActionInitiation(Integer absBundlingActionInitiation) {
        this.absBundlingActionInitiation = absBundlingActionInitiation;
    }

    public String getAbsPrimaryAccountCategory() {
        return absPrimaryAccountCategory;
    }

    public void setAbsPrimaryAccountCategory(String absPrimaryAccountCategory) {
        this.absPrimaryAccountCategory = absPrimaryAccountCategory;
    }

    public Integer getAbsBundlingConfirmationType() {
        return absBundlingConfirmationType;
    }

    public void setAbsBundlingConfirmationType(Integer absBundlingConfirmationType) {
        this.absBundlingConfirmationType = absBundlingConfirmationType;
    }

    public Integer getAbsBundlingRedemption() {
        return absBundlingRedemption;
    }

    public void setAbsBundlingRedemption(Integer absBundlingRedemption) {
        this.absBundlingRedemption = absBundlingRedemption;
    }

    public Integer getAbsLinkedAccountLimit() {
        return absLinkedAccountLimit;
    }

    public void setAbsLinkedAccountLimit(Integer absLinkedAccountLimit) {
        this.absLinkedAccountLimit = absLinkedAccountLimit;
    }

    public Integer getAbsConfirmationExpiryLimit() {
        return absConfirmationExpiryLimit;
    }

    public void setAbsConfirmationExpiryLimit(Integer absConfirmationExpiryLimit) {
        this.absConfirmationExpiryLimit = absConfirmationExpiryLimit;
    }

    public Integer getAbsTierBehaviour() {
        return absTierBehaviour;
    }

    public void setAbsTierBehaviour(Integer absTierBehaviour) {
        this.absTierBehaviour = absTierBehaviour;
    }


    @Override
    public String toString() {
        return "AccountBundlingSetting{" +
                "absId=" + absId +
                ", absMerchantNo=" + absMerchantNo +
                ", absLocation=" + absLocation +
                ", absLinkingType=" + absLinkingType +
                ", absLinkingEligibility=" + absLinkingEligibility +
                ", absPrimaryAccountEligibility=" + absPrimaryAccountEligibility +
                ", absBundlingActionInitiation=" + absBundlingActionInitiation +
                ", absPrimaryAccountCategory='" + absPrimaryAccountCategory + '\'' +
                ", absBundlingConfirmationType=" + absBundlingConfirmationType +
                ", absBundlingRedemption=" + absBundlingRedemption +
                ", absLinkedAccountLimit=" + absLinkedAccountLimit +
                ", absConfirmationExpiryLimit=" + absConfirmationExpiryLimit +
                ", absTierBehaviour=" + absTierBehaviour +
                '}';
    }
}
