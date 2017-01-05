package com.inspirenetz.api.test.core.fixture;

import com.inspirenetz.api.core.domain.CustomerRewardBalance;
import com.inspirenetz.api.test.core.builder.CustomerRewardBalanceBuilder;

import java.util.HashSet;
import java.util.Set;

/**
 * Created by sandheepgr on 17/2/14.
 */
public class CustomerRewardBalanceFixture {

    public static CustomerRewardBalance standardRewardBalance() {

        CustomerRewardBalance customerRewardBalance = CustomerRewardBalanceBuilder.aCustomerRewardBalance()
                .withCrbMerchantNo(1L)
                .withCrbLoyaltyId("1717171717")
                .withCrbRewardCurrency(1L)
                .withCrbRewardBalance(100)
                .build();

        return customerRewardBalance;


    }


    public static CustomerRewardBalance updatedStandardRewardBalance(CustomerRewardBalance customerRewardBalance) {

        customerRewardBalance.setCrbRewardBalance(200.13);
        return customerRewardBalance;
    }


    public static Set<CustomerRewardBalance> standardCustomerRewardBalances() {

        Set<CustomerRewardBalance> customerRewardBalanceSet = new HashSet<>();


        CustomerRewardBalance customerRewardBalance1 = CustomerRewardBalanceBuilder.aCustomerRewardBalance()
                .withCrbMerchantNo(1L)
                .withCrbLoyaltyId("9999888877776661")
                .withCrbRewardCurrency(1L)
                .withCrbRewardBalance(100)
                .build();

        customerRewardBalanceSet.add(customerRewardBalance1);


        CustomerRewardBalance customerRewardBalance2 = CustomerRewardBalanceBuilder.aCustomerRewardBalance()
                .withCrbMerchantNo(1L)
                .withCrbLoyaltyId("9999888877776661")
                .withCrbRewardCurrency(2L)
                .withCrbRewardBalance(120)
                .build();

        customerRewardBalanceSet.add(customerRewardBalance2);


        CustomerRewardBalance customerRewardBalance3 = CustomerRewardBalanceBuilder.aCustomerRewardBalance()
                .withCrbMerchantNo(1L)
                .withCrbLoyaltyId("9999888877776662")
                .withCrbRewardCurrency(1L)
                .withCrbRewardBalance(1090.90)
                .build();

        customerRewardBalanceSet.add(customerRewardBalance3);


        return customerRewardBalanceSet;

    }
}
