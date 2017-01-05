package com.inspirenetz.api.rest.resource;

/**
 * Created by sandheepgr on 15/4/14.
 */
public class AccountBundlingSettingResource extends BaseResource {

    private Long absId;

    private Long absMerchantNo;

    private Long absLocation;

    private Integer absLinkingType;

    private Integer absLinkingEligibility;

    private Integer absPrimaryAccountEligibility;

    private Integer absBundlingActionInitiation;

    private String absPrimaryAccountCategory;

    private Integer absBundlingConfirmationType;

    private Integer absBundlingRedemption;

    private Integer absLinkedAccountLimit;

    private Integer absConfirmationExpiryLimit;

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
        return "AccountBundlingSettingResource{" +
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
