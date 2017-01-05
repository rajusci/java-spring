package com.inspirenetz.api.test.core.fixture;

import com.inspirenetz.api.core.dictionary.LoyaltyProgramSkuType;
import com.inspirenetz.api.core.domain.LoyaltyProgramSku;
import com.inspirenetz.api.test.core.builder.LoyaltyProgramSkuBuilder;

import java.util.HashSet;
import java.util.Set;

/**
 * Created by sandheepgr on 20/5/14.
 */
public class LoyaltyProgramSkuFixture {

    public static LoyaltyProgramSku standardLoyaltyProgramSku() {

        LoyaltyProgramSku loyaltyProgramSku = LoyaltyProgramSkuBuilder.aLoyaltyProgramSku()
                .withLpuProgramId(1L)
                .withLpuItemType(LoyaltyProgramSkuType.PRODUCT_CATEGORY)
                .withLpuItemCode("PRD10001")
                .withLpuTransactionType(0)
                .withLpuPrgRatioNum(1)
                .withLpuPrgRatioDeno(100)
                .build();

        return loyaltyProgramSku;

    }


    public static LoyaltyProgramSku updatedStandardLoyaltyProgramSku(LoyaltyProgramSku loyaltyProgramSku) {

        loyaltyProgramSku.setLpuPrgRatioNum(2);
        return loyaltyProgramSku;

    }



    public static Set<LoyaltyProgramSku> standardLoyaltyProgramSkus() {

        Set<LoyaltyProgramSku> loyaltyProgramSkuSet = new HashSet<>(0);

        LoyaltyProgramSku loyaltyProgramSku1 = LoyaltyProgramSkuBuilder.aLoyaltyProgramSku()
                .withLpuProgramId(1L)
                .withLpuItemType(LoyaltyProgramSkuType.PRODUCT)
                .withLpuItemCode("PRD10001")
                .withLpuTransactionType(0)
                .withLpuPrgRatioNum(1)
                .withLpuPrgRatioDeno(2)
                .build();

        loyaltyProgramSkuSet.add(loyaltyProgramSku1);


        LoyaltyProgramSku loyaltyProgramSku2 = LoyaltyProgramSkuBuilder.aLoyaltyProgramSku()
                .withLpuProgramId(1L)
                .withLpuItemType(LoyaltyProgramSkuType.PRODUCT_CATEGORY)
                .withLpuItemCode("CAT1003")
                .withLpuTransactionType(0)
                .withLpuPrgRatioNum(1)
                .withLpuPrgRatioDeno(2)
                .build();

        loyaltyProgramSkuSet.add(loyaltyProgramSku2);


        return loyaltyProgramSkuSet;

    }

}
