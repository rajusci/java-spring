package com.inspirenetz.api.test.core.fixture;

import com.inspirenetz.api.core.domain.SaleSKUExtension;
import com.inspirenetz.api.test.core.builder.SaleSKUExtensionBuilder;

/**
 * Created by sandheepgr on 14/8/14.
 */
public class SaleSKUExtensionFixture {

    public static SaleSKUExtension standardSaleSKUExtension() {

        SaleSKUExtension saleInfo = SaleSKUExtensionBuilder.aSaleSKUExtension()
                .withSseSaleSkuId(129L)
                .withAttrId(12L)
                .withAttrValue("MY VALUE")
                .build();


        return saleInfo;

    }

}
