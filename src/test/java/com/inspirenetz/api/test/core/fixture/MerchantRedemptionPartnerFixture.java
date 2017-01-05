package com.inspirenetz.api.test.core.fixture;

import com.inspirenetz.api.core.dictionary.IndicatorStatus;
import com.inspirenetz.api.core.domain.MerchantRedemptionPartner;
import com.inspirenetz.api.test.core.builder.MerchantRedemptionPartnerBuilder;

import java.util.HashSet;
import java.util.Set;

/**
 * Created by ameen on 26/6/15.
 */
public class MerchantRedemptionPartnerFixture {

    public static MerchantRedemptionPartner standardMerchantRedemptionPartner() {

        MerchantRedemptionPartner merchantRedemptionPartner = MerchantRedemptionPartnerBuilder.aMerchantRedemptionPartner()
                .withMrpMerchantNo(1L)
                .withMrpRedemptionMerchant(1L)
                .withMrpEnabled(IndicatorStatus.YES)                        
                .build();

        return merchantRedemptionPartner;

    }


    public static MerchantRedemptionPartner updatedStandardMerchantRedemptionPartner(MerchantRedemptionPartner merchantRedemptionPartner) {

        merchantRedemptionPartner.setMrpEnabled(IndicatorStatus.NO);
        return merchantRedemptionPartner;

    }


    public static Set<MerchantRedemptionPartner> standardMerchantProgramSummaries() {

        Set<MerchantRedemptionPartner> merchantRedemptionPartnersner = new HashSet<>(0);

        MerchantRedemptionPartner merchantRedemptionPartner1 = MerchantRedemptionPartnerBuilder.aMerchantRedemptionPartner()
                .withMrpMerchantNo(1L)
                .withMrpRedemptionMerchant(1L)
                .withMrpEnabled(IndicatorStatus.YES)
                .build();

        merchantRedemptionPartnersner.add(merchantRedemptionPartner1);



        MerchantRedemptionPartner merchantRedemptionPartner2 = MerchantRedemptionPartnerBuilder.aMerchantRedemptionPartner()
                .withMrpMerchantNo(2L)
                .withMrpRedemptionMerchant(2L)
                .withMrpEnabled(IndicatorStatus.YES)
                .build();

        merchantRedemptionPartnersner.add(merchantRedemptionPartner2);
        // Return the set
        return merchantRedemptionPartnersner;

    }
}
