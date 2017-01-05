package com.inspirenetz.api.test.core.fixture;

import com.inspirenetz.api.core.domain.LinkedRewardBalance;
import com.inspirenetz.api.test.core.builder.LinkedRewardBalanceBuilder;

import java.util.HashSet;
import java.util.Set;

/**
 * Created by saneesh-ci on 22/8/14.
 */
public class LinkedRewardBalanceFixture {

    public static LinkedRewardBalance standardLinkedRewardBalance() {

        LinkedRewardBalance linkedRewardBalance = LinkedRewardBalanceBuilder.aLinkedRewardBalance()
                .withLrbMerchantNo(1L)
                .withLrbPrimaryLoyaltyId("1000231")
                .withLrbRewardCurrency(1L)
                .withLrbRewardBalance(2.0)
                .build();


        return linkedRewardBalance;


    }


    public static LinkedRewardBalance updatedStandardLinkedRewardBalance(LinkedRewardBalance linkedRewardBalance) {

        linkedRewardBalance.setLrbRewardBalance(100.0);
        linkedRewardBalance.setLrbRewardCurrency(10l);

        return linkedRewardBalance;

    }


    public static Set<LinkedRewardBalance> standardLinkedRewardBalances() {

        Set<LinkedRewardBalance> linkedRewardBalances = new HashSet<LinkedRewardBalance>(0);

        LinkedRewardBalance pepsi  = LinkedRewardBalanceBuilder.aLinkedRewardBalance()
                .withLrbMerchantNo(1L)
                .withLrbPrimaryLoyaltyId("1000231")
                .withLrbRewardCurrency(1L)
                .withLrbRewardBalance(2.0)
                .build();

        linkedRewardBalances.add(pepsi);



        LinkedRewardBalance coke = LinkedRewardBalanceBuilder.aLinkedRewardBalance()
                .withLrbMerchantNo(1L)
                .withLrbPrimaryLoyaltyId("2000231")
                .withLrbRewardCurrency(2L)
                .withLrbRewardBalance(2.0)
                .build();

        linkedRewardBalances.add(coke);



        return linkedRewardBalances;



    }
}
