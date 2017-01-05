package com.inspirenetz.api.test.core.fixture;

import com.inspirenetz.api.core.dictionary.CustomerRewardingType;
import com.inspirenetz.api.core.domain.CustomerRewardActivity;
import com.inspirenetz.api.test.core.builder.CustomerRewardActivityBuilder;

import java.sql.Timestamp;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by ameenci on 10/9/14.
 */
public class CustomerRewardActivityFixture {

    public static CustomerRewardActivity standardCustomerRewardActivity(){

        // Get the current timestamp
        Timestamp timestamp = new Timestamp(System.currentTimeMillis());



        CustomerRewardActivity customerRewardActivity = CustomerRewardActivityBuilder.aCustomerRewardActivity()

                .withCraCustomerNo(1000L)
                .withCraType(CustomerRewardingType.EVENT_REGISTRATION)
                .withCraActivityRef("100F")
                .withCraStatus(1)
                .build();


        return customerRewardActivity;


    }


    public static CustomerRewardActivity updatedStandCustomerRewardActivity(CustomerRewardActivity customerRewardActivity) {

        customerRewardActivity.setCraType(CustomerRewardingType.E_STATEMENT_ENROLEMENT);


        return customerRewardActivity;

    }


    public static Set<CustomerRewardActivity> standardCustomerRewardActivitys() {

        // Get the current timestamp
        Timestamp timestamp = new Timestamp(System.currentTimeMillis());

        Set<CustomerRewardActivity> customerRewardActivitys = new HashSet<CustomerRewardActivity>(0);

        CustomerRewardActivity customerRewardActivity  = CustomerRewardActivityBuilder.aCustomerRewardActivity()
                .withCraCustomerNo(1000L)
                .withCraType(CustomerRewardingType.EVENT_REGISTRATION)
                .withCraActivityRef("100F")
                .withCraStatus(1)
                .withCraActivityTimeStamp(new Timestamp(timestamp.getTime()))
                .build();

        customerRewardActivitys.add(customerRewardActivity);

        CustomerRewardActivity customerRewardActivity1 = CustomerRewardActivityBuilder.aCustomerRewardActivity()
                .withCraCustomerNo(1000L)
                .withCraType(CustomerRewardingType.EVENT_REGISTRATION)
                .withCraActivityRef("100F")
                .withCraStatus(1)
                .withCraActivityTimeStamp(new Timestamp(timestamp.getTime()))
                .build();

        customerRewardActivitys.add(customerRewardActivity1);



        return customerRewardActivitys;



    }
}
