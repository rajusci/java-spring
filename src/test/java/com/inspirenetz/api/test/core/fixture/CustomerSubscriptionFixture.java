package com.inspirenetz.api.test.core.fixture;

import com.inspirenetz.api.core.domain.CustomerSubscription;
import com.inspirenetz.api.test.core.builder.CustomerSubscriptionBuilder;

import java.util.HashSet;
import java.util.Set;

/**
 * Created by sandheepgr on 22/8/14.
 */
public class CustomerSubscriptionFixture {

    public static CustomerSubscription standardCustomerSubscription() {

        CustomerSubscription customerSubscription = CustomerSubscriptionBuilder.aCustomerSubscription()
                .withCsuCustomerNo(1L)
                .withCsuMerchantNo(1L)
                .withCsuProductCode("PRD1001")
                .withCsuPoints(10.0)
                .withCsuLocation(1L)
                .withCsuServiceNo("10001")
                .build();

        return customerSubscription;

    }


    public static CustomerSubscription standardUpdatedCustomerSubscription(CustomerSubscription customerSubscription ) {

        // Set the service
        customerSubscription.setCsuServiceNo("10003");
        return customerSubscription;

    }



    public Set<CustomerSubscription> standardCustomerSubscriptions() {

        Set<CustomerSubscription> customerSubscriptionSet = new HashSet<>(0);


        CustomerSubscription customerSubscription1 = CustomerSubscriptionBuilder.aCustomerSubscription()
                .withCsuCustomerNo(1L)
                .withCsuMerchantNo(1L)
                .withCsuProductCode("PRD1001")
                .withCsuPoints(10.0)
                .withCsuLocation(1L)
                .withCsuServiceNo("10001")
                .build();

        customerSubscriptionSet.add(customerSubscription1);


        CustomerSubscription customerSubscription2 = CustomerSubscriptionBuilder.aCustomerSubscription()
                .withCsuCustomerNo(1L)
                .withCsuMerchantNo(1L)
                .withCsuProductCode("PRD1002")
                .withCsuPoints(10.0)
                .withCsuLocation(1L)
                .withCsuServiceNo("10002")
                .build();

        customerSubscriptionSet.add(customerSubscription2);


        return customerSubscriptionSet;

    }
}
