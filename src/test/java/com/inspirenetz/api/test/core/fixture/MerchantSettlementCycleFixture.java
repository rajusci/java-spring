package com.inspirenetz.api.test.core.fixture;

import com.inspirenetz.api.core.dictionary.IndicatorStatus;
import com.inspirenetz.api.core.domain.MerchantSettlementCycle;
import com.inspirenetz.api.test.core.builder.MerchantSettlementCycleBuilder;

import java.sql.Date;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by saneesh-ci on 21/10/15.
 */
public class MerchantSettlementCycleFixture {




    public static MerchantSettlementCycle standardMerchantSettlementCycle() {


        MerchantSettlementCycle merchantSettlementCycle = MerchantSettlementCycleBuilder.aMerchantSettlementCycle()
                .withMscMerchantNo(1L)
                .withMscAmount(1000.0)
                .withMscRedemptionMerchant(1l)
                .withMscEndDate(Date.valueOf("2015-10-08"))
                .withMscStartDate(Date.valueOf("2015-10-02"))
                .withMscMerchantLocation(1L)
                .withMscStatus(IndicatorStatus.NO)
                .build();


        return merchantSettlementCycle;


    }


    public static MerchantSettlementCycle updatedStandardMerchantSettlementCycle(MerchantSettlementCycle merchantSettlementCycle) {

        merchantSettlementCycle.setMscStatus(IndicatorStatus.YES);

        return merchantSettlementCycle;

    }


    public static Set<MerchantSettlementCycle> standardMerchantSettlementCycles() {

        Set<MerchantSettlementCycle> merchantSettlementCycles = new HashSet<MerchantSettlementCycle>(0);

        MerchantSettlementCycle merchantSettlementCycleA = MerchantSettlementCycleBuilder.aMerchantSettlementCycle()
                .withMscMerchantNo(1L)
                .withMscAmount(1000.0)
                .withMscRedemptionMerchant(1l)
                .withMscEndDate(Date.valueOf("2015-10-07"))
                .withMscStartDate(Date.valueOf("2015-10-01"))
                .withMscMerchantLocation(1L)
                .withMscStatus(IndicatorStatus.NO)
                .build();

        merchantSettlementCycles.add(merchantSettlementCycleA);



        MerchantSettlementCycle merchantSettlementCycleB = MerchantSettlementCycleBuilder.aMerchantSettlementCycle()
                .withMscMerchantNo(1L)
                .withMscAmount(1000.0)
                .withMscRedemptionMerchant(1l)
                .withMscEndDate(Date.valueOf("2015-10-07"))
                .withMscStartDate(Date.valueOf("2015-10-01"))
                .withMscMerchantLocation(1L)
                .withMscStatus(IndicatorStatus.NO)
                .build();


        merchantSettlementCycles.add(merchantSettlementCycleB);



        return merchantSettlementCycles;



    }
}
