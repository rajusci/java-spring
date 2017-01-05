package com.inspirenetz.api.test.core.fixture;

import com.inspirenetz.api.core.domain.LoyaltyExtension;
import com.inspirenetz.api.test.core.builder.LoyaltyExtensionBuilder;

import java.util.HashSet;
import java.util.Set;

/**
 * Created by saneesh-ci on 10/9/14.
 */
public class LoyaltyExtensionFixture {

    public static LoyaltyExtension standardLoyaltyExtension() {

        LoyaltyExtension loyaltyExtension = LoyaltyExtensionBuilder.aLoyaltyExtension()
                .withLexName("Loyalty program A")
                .withLexDescription("Description")
                .withLexFile("loyaltyFileA")
                .withLexMerchantNo(1L)
                .build();


        return loyaltyExtension;


    }


    public static LoyaltyExtension updatedStandardLoyaltyExtensions(LoyaltyExtension loyaltyExtension) {

        loyaltyExtension.setLexName("Loyalty Program C");
        loyaltyExtension.setLexDescription("New Description");

        return loyaltyExtension;

    }


    public static Set<LoyaltyExtension> standardLoyaltyExtensionss() {

        Set<LoyaltyExtension> loyaltyExtensions = new HashSet<LoyaltyExtension>(0);

        LoyaltyExtension lrq1  = LoyaltyExtensionBuilder.aLoyaltyExtension()
                .withLexName("Loyalty program A")
                .withLexDescription("Description")
                .withLexFile("loyaltyFileA")
                .withLexMerchantNo(1L)
                .build();

        loyaltyExtensions.add(lrq1);



        LoyaltyExtension lrq2 = LoyaltyExtensionBuilder.aLoyaltyExtension()
                .withLexName("Loyalty program B")
                .withLexDescription("Description")
                .withLexFile("loyaltyFileB")
                .withLexMerchantNo(1L)
                .build();

        loyaltyExtensions.add(lrq2);



        return loyaltyExtensions;



    }
}
