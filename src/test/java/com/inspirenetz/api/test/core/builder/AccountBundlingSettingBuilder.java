package com.inspirenetz.api.test.core.builder;

import com.inspirenetz.api.core.domain.AccountBundlingSetting;

/**
 * Created by sandheepgr on 24/8/14.
 */
public class AccountBundlingSettingBuilder {
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

    private AccountBundlingSettingBuilder() {
    }

    public static AccountBundlingSettingBuilder anAccountBundlingSetting() {
        return new AccountBundlingSettingBuilder();
    }

    public AccountBundlingSettingBuilder withAbsId(Long absId) {
        this.absId = absId;
        return this;
    }

    public AccountBundlingSettingBuilder withAbsMerchantNo(Long absMerchantNo) {
        this.absMerchantNo = absMerchantNo;
        return this;
    }

    public AccountBundlingSettingBuilder withAbsLocation(Long absLocation) {
        this.absLocation = absLocation;
        return this;
    }

    public AccountBundlingSettingBuilder withAbsLinkingType(Integer absLinkingType) {
        this.absLinkingType = absLinkingType;
        return this;
    }

    public AccountBundlingSettingBuilder withAbsLinkingEligibility(Integer absLinkingEligibility) {
        this.absLinkingEligibility = absLinkingEligibility;
        return this;
    }

    public AccountBundlingSettingBuilder withAbsPrimaryAccountEligibility(Integer absPrimaryAccountEligibility) {
        this.absPrimaryAccountEligibility = absPrimaryAccountEligibility;
        return this;
    }

    public AccountBundlingSettingBuilder withAbsBundlingActionInitiation(Integer absBundlingActionInitiation) {
        this.absBundlingActionInitiation = absBundlingActionInitiation;
        return this;
    }

    public AccountBundlingSettingBuilder withAbsPrimaryAccountCategory(String absPrimaryAccountCategory) {
        this.absPrimaryAccountCategory = absPrimaryAccountCategory;
        return this;
    }

    public AccountBundlingSettingBuilder withAbsBundlingConfirmationType(Integer absBundlingConfirmationType) {
        this.absBundlingConfirmationType = absBundlingConfirmationType;
        return this;
    }

    public AccountBundlingSettingBuilder withAbsBundlingRedemption(Integer absBundlingRedemption) {
        this.absBundlingRedemption = absBundlingRedemption;
        return this;
    }

    public AccountBundlingSettingBuilder withAbsLinkedAccountLimit(Integer absLinkedAccountLimit) {
        this.absLinkedAccountLimit = absLinkedAccountLimit;
        return this;
    }

    public AccountBundlingSettingBuilder withAbsConfirmationExpiryLimit(Integer absConfirmationExpiryLimit) {
        this.absConfirmationExpiryLimit = absConfirmationExpiryLimit;
        return this;
    }

    public AccountBundlingSettingBuilder withAbsTierBehaviour(Integer absTierBehaviour) {
        this.absTierBehaviour = absTierBehaviour;
        return this;
    }

    public AccountBundlingSetting build() {
        AccountBundlingSetting accountBundlingSetting = new AccountBundlingSetting();
        accountBundlingSetting.setAbsId(absId);
        accountBundlingSetting.setAbsMerchantNo(absMerchantNo);
        accountBundlingSetting.setAbsLocation(absLocation);
        accountBundlingSetting.setAbsLinkingType(absLinkingType);
        accountBundlingSetting.setAbsLinkingEligibility(absLinkingEligibility);
        accountBundlingSetting.setAbsPrimaryAccountEligibility(absPrimaryAccountEligibility);
        accountBundlingSetting.setAbsBundlingActionInitiation(absBundlingActionInitiation);
        accountBundlingSetting.setAbsPrimaryAccountCategory(absPrimaryAccountCategory);
        accountBundlingSetting.setAbsBundlingConfirmationType(absBundlingConfirmationType);
        accountBundlingSetting.setAbsBundlingRedemption(absBundlingRedemption);
        accountBundlingSetting.setAbsLinkedAccountLimit(absLinkedAccountLimit);
        accountBundlingSetting.setAbsConfirmationExpiryLimit(absConfirmationExpiryLimit);
        accountBundlingSetting.setAbsTierBehaviour(absTierBehaviour);
        return accountBundlingSetting;
    }
}
