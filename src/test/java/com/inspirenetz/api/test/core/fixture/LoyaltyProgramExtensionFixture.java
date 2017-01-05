package com.inspirenetz.api.test.core.fixture;

import com.inspirenetz.api.core.domain.LoyaltyProgramExtension;
import com.inspirenetz.api.test.core.builder.LoyaltyProgramExtensionBuilder;

/**
 * Created by sandheepgr on 14/8/14.
 */
public class LoyaltyProgramExtensionFixture {

    public static LoyaltyProgramExtension standardLoyaltyProgramInfo() {

        LoyaltyProgramExtension saleExtension = LoyaltyProgramExtensionBuilder.aLoyaltyProgramExtension()
                .withLpeLoyaltyProgramId(129L)
                .withAttrId(12L)
                .withAttrValue("MY VALUE")
                .build();


        return saleExtension;

    }

}
