package com.inspirenetz.api.test.core.builder;

import com.inspirenetz.api.core.domain.RedemptionMerchantLocation;

/**
 * Created by saneesh-ci on 25/9/14.
 */
public class RedemptionMerchantLocationBuilder {
    private String rmlLocation;
    private Long rmlMerNo;
    private Long rmlId;

    private RedemptionMerchantLocationBuilder() {
    }

    public static RedemptionMerchantLocationBuilder aRedemptionMerchantLocation() {
        return new RedemptionMerchantLocationBuilder();
    }

    public RedemptionMerchantLocationBuilder withRmlLocation(String rmlLocation) {
        this.rmlLocation = rmlLocation;
        return this;
    }

    public RedemptionMerchantLocationBuilder withRmlMerNo(Long rmlMerNo) {
        this.rmlMerNo = rmlMerNo;
        return this;
    }

    public RedemptionMerchantLocationBuilder withRmlId(Long rmlId) {
        this.rmlId = rmlId;
        return this;
    }

    public RedemptionMerchantLocation build() {
        RedemptionMerchantLocation redemptionMerchantLocation = new RedemptionMerchantLocation();
        redemptionMerchantLocation.setRmlLocation(rmlLocation);
        redemptionMerchantLocation.setRmlMerNo(rmlMerNo);
        redemptionMerchantLocation.setRmlId(rmlId);
        return redemptionMerchantLocation;
    }
}
