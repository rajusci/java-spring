package com.inspirenetz.api.test.core.fixture;

import com.inspirenetz.api.core.domain.CustomerRewardExpiry;
import com.inspirenetz.api.test.core.builder.*;
import com.inspirenetz.api.util.DBUtils;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by sandheepgr on 17/2/14.
 */
public class CustomerRewardExpiryFixture {


    public static CustomerRewardExpiry standardRewardExpiry() {

        CustomerRewardExpiry customerRewardExpiry = CustomerRewardExpiryBuilder.aCustomerRewardExpiry()
                .withCreMerchantNo(1L)
                .withCreLoyaltyId("9999888877776661")
                .withCreRewardCurrencyId(1L)
                .withCreRewardBalance(100)
                .withCreExpiryDt(DBUtils.covertToSqlDate("2014-12-20"))
                .build();

        return customerRewardExpiry;


    }


    public static CustomerRewardExpiry updatedStandardRewardExpiry(CustomerRewardExpiry customerRewardExpiry) {

        customerRewardExpiry.setCreRewardBalance(200.13);
        return customerRewardExpiry;
    }


    public static Set<CustomerRewardExpiry> standardCustomerRewardExpirys() {

        Set<CustomerRewardExpiry> customerRewardExpirySet = new HashSet<>();


        CustomerRewardExpiry customerRewardExpiry1 = CustomerRewardExpiryBuilder.aCustomerRewardExpiry()
                .withCreMerchantNo(1L)
                .withCreLoyaltyId("9999888877776661")
                .withCreRewardCurrencyId(1L)
                .withCreRewardBalance(100)
                .withCreExpiryDt(DBUtils.covertToSqlDate("2014-03-20"))
                .build();

        customerRewardExpirySet.add(customerRewardExpiry1);


        CustomerRewardExpiry customerRewardExpiry2 = CustomerRewardExpiryBuilder.aCustomerRewardExpiry()
                .withCreMerchantNo(1L)
                .withCreLoyaltyId("9999888877776661")
                .withCreExpiryDt(DBUtils.covertToSqlDate("9999-12-31"))
                .withCreRewardCurrencyId(2L)
                .withCreRewardBalance(120)
                .build();

        customerRewardExpirySet.add(customerRewardExpiry2);


        CustomerRewardExpiry customerRewardExpiry3 = CustomerRewardExpiryBuilder.aCustomerRewardExpiry()
                .withCreMerchantNo(1L)
                .withCreLoyaltyId("9999888877776662")
                .withCreExpiryDt(DBUtils.covertToSqlDate("2014-05-20"))
                .withCreRewardCurrencyId(1L)
                .withCreRewardBalance(1090.90)
                .build();

        customerRewardExpirySet.add(customerRewardExpiry3);


        return customerRewardExpirySet;

    }
}