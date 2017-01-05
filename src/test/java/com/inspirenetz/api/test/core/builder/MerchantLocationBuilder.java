package com.inspirenetz.api.test.core.builder;

import com.inspirenetz.api.core.dictionary.IndicatorStatus;
import com.inspirenetz.api.core.domain.MerchantLocation;

/**
 * Created by sandheepgr on 14/5/14.
 */
public class MerchantLocationBuilder {
    private Long melId;
    private Long melMerchantNo;
    private String melLocation;
    private String melLatitude;
    private String melLongitude;
    private int melLocationInUse = IndicatorStatus.YES;

    private MerchantLocationBuilder() {
    }

    public static MerchantLocationBuilder aMerchantLocation() {
        return new MerchantLocationBuilder();
    }

    public MerchantLocationBuilder withMelId(Long melId) {
        this.melId = melId;
        return this;
    }

    public MerchantLocationBuilder withMelMerchantNo(Long melMerchantNo) {
        this.melMerchantNo = melMerchantNo;
        return this;
    }

    public MerchantLocationBuilder withMelLocation(String melLocation) {
        this.melLocation = melLocation;
        return this;
    }

    public MerchantLocationBuilder withMelLatitude(String melLatitude) {
        this.melLatitude = melLatitude;
        return this;
    }

    public MerchantLocationBuilder withMelLongitude(String melLongitude) {
        this.melLongitude = melLongitude;
        return this;
    }

    public MerchantLocationBuilder withMelLocationInUse(int melLocationInUse) {
        this.melLocationInUse = melLocationInUse;
        return this;
    }

    public MerchantLocation build() {
        MerchantLocation merchantLocation = new MerchantLocation();
        merchantLocation.setMelId(melId);
        merchantLocation.setMelMerchantNo(melMerchantNo);
        merchantLocation.setMelLocation(melLocation);
        merchantLocation.setMelLatitude(melLatitude);
        merchantLocation.setMelLongitude(melLongitude);
        merchantLocation.setMelLocationInUse(melLocationInUse);
        return merchantLocation;
    }
}
