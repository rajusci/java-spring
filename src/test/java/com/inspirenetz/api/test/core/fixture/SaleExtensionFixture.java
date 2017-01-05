package com.inspirenetz.api.test.core.fixture;

import com.inspirenetz.api.core.domain.SaleExtension;
import com.inspirenetz.api.test.core.builder.SaleExtensionBuilder;

/**
 * Created by sandheepgr on 14/8/14.
 */
public class SaleExtensionFixture {

    public static SaleExtension standardSaleInfo() {

        SaleExtension saleExtension = SaleExtensionBuilder.aSaleExtension()
                .withSaeSaleId(129L)
                .withAttrId(12L)
                .withAttrValue("MY VALUE")
                .build();


        return saleExtension;

    }

}
