package com.inspirenetz.api.test.core.fixture;

import com.inspirenetz.api.core.domain.SaleSKU;
import com.inspirenetz.api.test.core.builder.SaleSKUBuilder;

import java.util.HashSet;
import java.util.Set;

/**
 * Created by sandheepgr on 28/5/14.
 */
public class SaleSKUFixture {


    public static SaleSKU standardSaleSku() {

        SaleSKU purchaseSKU = SaleSKUBuilder.aSaleSKU()
                .withSsuSaleId(1L)
                .withSsuProductCode("PRD10001")
                .withSsuTransactionType(0)
                .withSsuPrice(1500.0)
                .withSsuDiscountPercent(15.0)
                .withSsuQty(1.0)
                .build();


        return purchaseSKU;

    }

    public static SaleSKU updatedStandardSaleSku(SaleSKU purchaseSKU) {

        purchaseSKU.setSsuBrand("BRN10001");
        purchaseSKU.setSsuCategory1("PCY10001");


        return purchaseSKU;

    }

    public static Set<SaleSKU> standardSaleSkus() {

        Set<SaleSKU> purchaseSKUSet =  new HashSet<>(0);

        SaleSKU purchaseSKU1 = SaleSKUBuilder.aSaleSKU()
                .withSsuSaleId(1L)
                .withSsuProductCode("PRD10001")
                .withSsuTransactionType(0)
                .withSsuPrice(100.0)
                .withSsuQty(1.0)
                .build();

        purchaseSKUSet.add(purchaseSKU1);



        SaleSKU purchaseSKU2 = SaleSKUBuilder.aSaleSKU()
                .withSsuSaleId(1L)
                .withSsuProductCode("PRD10002")
                .withSsuTransactionType(0)
                .withSsuPrice(120.0)
                .withSsuQty(2.0)
                .build();

        purchaseSKUSet.add(purchaseSKU2);


        return purchaseSKUSet;

    }



}
