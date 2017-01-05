package com.inspirenetz.api.test.core.fixture;

import com.inspirenetz.api.core.dictionary.MerchantSettlementType;
import com.inspirenetz.api.core.domain.MerchantSettlement;
import com.inspirenetz.api.test.core.builder.MerchantSettlementBuilder;

import java.util.HashSet;
import java.util.Set;

/**
 * Created by saneesh-ci on 27/9/14.
 */
public class MerchantSettlementFixture {




    public static MerchantSettlement standardMerchantSettlement() {


        MerchantSettlement merchantSettlement = MerchantSettlementBuilder.aMerchantSettlement()
                .withMesSettlementType(MerchantSettlementType.LOAD_WALLET)
                .withMesInternalRef("100")
                .withMesIsSettled(1)
                .withMesLoyaltyId("9298390192")
                .withMesVendorNo(100L)
                .build();


        return merchantSettlement;


    }


    public static MerchantSettlement updatedStandardMerchantSettlement(MerchantSettlement merchantSettlement) {

        merchantSettlement.setMesIsSettled(0);

        return merchantSettlement;

    }


    public static Set<MerchantSettlement> standardMerchantSettlements() {

        Set<MerchantSettlement> merchantSettlements = new HashSet<MerchantSettlement>(0);

        MerchantSettlement merchantSettlementA = MerchantSettlementBuilder.aMerchantSettlement()
                .withMesSettlementType(MerchantSettlementType.LOAD_WALLET)
                .withMesInternalRef("100")
                .withMesIsSettled(1)
                .withMesLoyaltyId("9298390192")
                .build();

        merchantSettlements.add(merchantSettlementA);



        MerchantSettlement merchantSettlementB = MerchantSettlementBuilder.aMerchantSettlement()
                .withMesSettlementType(MerchantSettlementType.LOAD_WALLET)
                .withMesInternalRef("101")
                .withMesIsSettled(1)
                .withMesLoyaltyId("9295146528")
                .build();


        merchantSettlements.add(merchantSettlementB);



        return merchantSettlements;



    }
}
