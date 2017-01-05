package com.inspirenetz.api.test.core.builder;

import com.inspirenetz.api.core.domain.MerchantLoyaltyId;

/**
 * Created by sandheepgr on 16/5/14.
 */
public class MerchantLoyaltyIdBuilder {
    private Long mliMerchantNo;
    private long mliLoyaltyIdIndex;
    private long mliLoyaltyIdFrom;
    private long mliLoyaltyIdTo;

    private MerchantLoyaltyIdBuilder() {
    }

    public static MerchantLoyaltyIdBuilder aMerchantLoyaltyId() {
        return new MerchantLoyaltyIdBuilder();
    }

    public MerchantLoyaltyIdBuilder withMliMerchantNo(Long mliMerchantNo) {
        this.mliMerchantNo = mliMerchantNo;
        return this;
    }

    public MerchantLoyaltyIdBuilder withMliLoyaltyIdIndex(long mliLoyaltyIdIndex) {
        this.mliLoyaltyIdIndex = mliLoyaltyIdIndex;
        return this;
    }

    public MerchantLoyaltyIdBuilder withMliLoyaltyIdFrom(long mliLoyaltyIdFrom) {
        this.mliLoyaltyIdFrom = mliLoyaltyIdFrom;
        return this;
    }

    public MerchantLoyaltyIdBuilder withMliLoyaltyIdTo(long mliLoyaltyIdTo) {
        this.mliLoyaltyIdTo = mliLoyaltyIdTo;
        return this;
    }

    public MerchantLoyaltyId build() {
        MerchantLoyaltyId merchantLoyaltyId = new MerchantLoyaltyId();
        merchantLoyaltyId.setMliMerchantNo(mliMerchantNo);
        merchantLoyaltyId.setMliLoyaltyIdIndex(mliLoyaltyIdIndex);
        merchantLoyaltyId.setMliLoyaltyIdFrom(mliLoyaltyIdFrom);
        merchantLoyaltyId.setMliLoyaltyIdTo(mliLoyaltyIdTo);
        return merchantLoyaltyId;
    }
}
