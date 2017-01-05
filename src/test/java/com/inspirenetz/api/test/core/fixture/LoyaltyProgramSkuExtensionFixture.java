package com.inspirenetz.api.test.core.fixture;

import com.inspirenetz.api.core.domain.LoyaltyProgramSkuExtension;
import com.inspirenetz.api.test.core.builder.LoyaltyProgramSkuExtensionBuilder;

/**
 * Created by sandheepgr on 14/8/14.
 */
public class LoyaltyProgramSkuExtensionFixture {

    public static LoyaltyProgramSkuExtension standardLoyaltyProgramSkuInfo() {

        LoyaltyProgramSkuExtension saleExtension = LoyaltyProgramSkuExtensionBuilder.aLoyaltyProgramSkuExtension()
                .withLueLoyaltyProgramSkuId(0L)
                .withAttrId(12L)
                .withAttrValue("MY VALUE")
                .build();


        return saleExtension;

    }

}
