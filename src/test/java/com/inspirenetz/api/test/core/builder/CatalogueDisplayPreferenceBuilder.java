package com.inspirenetz.api.test.core.builder;

import com.inspirenetz.api.core.domain.CatalogueDisplayPreference;

/**
 * Created by saneesh-ci on 21/10/14.
 */
public class CatalogueDisplayPreferenceBuilder {
    private Long cdpMerchantNo;
    private Long cdpId;
    private String cdpPreferences;

    private CatalogueDisplayPreferenceBuilder() {
    }

    public static CatalogueDisplayPreferenceBuilder aCatalogueDisplayPreference() {
        return new CatalogueDisplayPreferenceBuilder();
    }

    public CatalogueDisplayPreferenceBuilder withCdpMerchantNo(Long cdpMerchantNo) {
        this.cdpMerchantNo = cdpMerchantNo;
        return this;
    }

    public CatalogueDisplayPreferenceBuilder withCdpId(Long cdpId) {
        this.cdpId = cdpId;
        return this;
    }

    public CatalogueDisplayPreferenceBuilder withCdpPreferences(String cdpPreferences) {
        this.cdpPreferences = cdpPreferences;
        return this;
    }

    public CatalogueDisplayPreferenceBuilder but() {
        return aCatalogueDisplayPreference().withCdpMerchantNo(cdpMerchantNo).withCdpId(cdpId).withCdpPreferences(cdpPreferences);
    }

    public CatalogueDisplayPreference build() {
        CatalogueDisplayPreference catalogueDisplayPreference = new CatalogueDisplayPreference();
        catalogueDisplayPreference.setCdpMerchantNo(cdpMerchantNo);
        catalogueDisplayPreference.setCdpId(cdpId);
        catalogueDisplayPreference.setCdpPreferences(cdpPreferences);
        return catalogueDisplayPreference;
    }
}
