package com.inspirenetz.api.test.core.fixture;

import com.inspirenetz.api.core.domain.AccumulatedRewardBalance;
import com.inspirenetz.api.test.core.builder.AccumulatedRewardBalanceBuilder;

import java.util.HashSet;
import java.util.Set;

/**
 * Created by sandheepgr on 23/8/14.
 */
public class AccumulatedRewardBalanceFixture {

    public static AccumulatedRewardBalance standardAccumulatedRewardBalance() {

        AccumulatedRewardBalance accumulatedRewardBalance = AccumulatedRewardBalanceBuilder.anAccumulatedRewardBalance()
                .withArbMerchantNo(1L)
                .withArbLoyaltyId("9999888877776661")
                .withArbRewardCurrency(1L)
                .withArbRewardBalance(100.0)
                .build();

        return accumulatedRewardBalance;

    }


    public static AccumulatedRewardBalance updatedStandardAccumulatedRewardBalance(AccumulatedRewardBalance accumulatedRewardBalance) {

        accumulatedRewardBalance.setArbRewardBalance(120.0);

        return accumulatedRewardBalance;

    }



    public static Set<AccumulatedRewardBalance> standardAccumulatedRewardBalances() {

        Set<AccumulatedRewardBalance> accumulatedRewardBalanceSet = new HashSet<>(0);

        AccumulatedRewardBalance accumulatedRewardBalance1 = AccumulatedRewardBalanceBuilder.anAccumulatedRewardBalance()
                .withArbMerchantNo(1L)
                .withArbLoyaltyId("9999888877776661")
                .withArbRewardCurrency(1L)
                .withArbRewardBalance(100.0)
                .build();

        accumulatedRewardBalanceSet.add(accumulatedRewardBalance1);



        AccumulatedRewardBalance accumulatedRewardBalance2 = AccumulatedRewardBalanceBuilder.anAccumulatedRewardBalance()
                .withArbMerchantNo(1L)
                .withArbLoyaltyId("9999888877776661")
                .withArbRewardCurrency(2L)
                .withArbRewardBalance(100.0)
                .build();

        accumulatedRewardBalanceSet.add(accumulatedRewardBalance2);


        return accumulatedRewardBalanceSet;

    }
}
