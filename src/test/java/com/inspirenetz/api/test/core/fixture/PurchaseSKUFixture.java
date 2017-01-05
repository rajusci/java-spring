package com.inspirenetz.api.test.core.fixture;

import com.inspirenetz.api.core.domain.PurchaseSKU;
import com.inspirenetz.api.test.core.builder.PurchaseSKUBuilder;

import java.util.HashSet;
import java.util.Set;

/**
 * Created by sandheepgr on 28/5/14.
 */
public class PurchaseSKUFixture {


    public static PurchaseSKU standardPurchaseSku() {

        PurchaseSKU purchaseSKU = PurchaseSKUBuilder.aPurchaseSKU()
                .withPkuPurchaseId(1L)
                .withPkuProductCode("PRD10001")
                .withPkuPrice(1500.0)
                .withPkuDiscountPercent(15.0)
                .withPkuQty(1)
                .build();


        return purchaseSKU;

    }

    public static PurchaseSKU updatedStandardPurchaseSku(PurchaseSKU purchaseSKU) {

        purchaseSKU.setPkuBrand("BRN10001");
        purchaseSKU.setPkuCategory1("PCY10001");


        return purchaseSKU;

    }

    public static Set<PurchaseSKU> standardPurchaseSkus() {

        Set<PurchaseSKU> purchaseSKUSet =  new HashSet<>(0);

        PurchaseSKU purchaseSKU1 = PurchaseSKUBuilder.aPurchaseSKU()
                .withPkuPurchaseId(1L)
                .withPkuProductCode("PRD10001")
                .withPkuPrice(100.0)
                .withPkuQty(1)
                .build();

        purchaseSKUSet.add(purchaseSKU1);



        PurchaseSKU purchaseSKU2 = PurchaseSKUBuilder.aPurchaseSKU()
                .withPkuPurchaseId(1L)
                .withPkuProductCode("PRD10002")
                .withPkuPrice(120.0)
                .withPkuQty(2)
                .build();

        purchaseSKUSet.add(purchaseSKU2);


        return purchaseSKUSet;

    }



}
