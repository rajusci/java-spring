package com.inspirenetz.api.test.core.fixture;

import com.inspirenetz.api.core.domain.MerchantLoyaltyId;
import com.inspirenetz.api.test.core.builder.MerchantLoyaltyIdBuilder;

/**
 * Created by sandheepgr on 16/5/14.
 */
public class MerchantLoyaltyIdFixture {

    public static MerchantLoyaltyId standardMerchantLoyaltyId() {

        MerchantLoyaltyId merchantLoyaltyId = MerchantLoyaltyIdBuilder.aMerchantLoyaltyId()
                .withMliMerchantNo(1L)
                .withMliLoyaltyIdIndex(9999888877776661L)
                .withMliLoyaltyIdFrom(9999888877776660L)
                .withMliLoyaltyIdTo(9999888877776669L)
                .build();

        return merchantLoyaltyId;

    }



    public static MerchantLoyaltyId updatedStandardMerchantLoyaltyId(MerchantLoyaltyId merchantLoyaltyId) {

        merchantLoyaltyId.setMliLoyaltyIdIndex(9999888877776662L);
        return merchantLoyaltyId;
    }
}
