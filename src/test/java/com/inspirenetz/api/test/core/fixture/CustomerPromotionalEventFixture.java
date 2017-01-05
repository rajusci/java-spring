package com.inspirenetz.api.test.core.fixture;

import com.inspirenetz.api.core.domain.CustomerPromotionalEvent;
import com.inspirenetz.api.test.core.builder.CustomerPromotionalEventBuilder;

import java.sql.Date;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by saneesh-ci on 25/6/15.
 */
public class CustomerPromotionalEventFixture {




    public static CustomerPromotionalEvent standardCustomerPromotionalEvent() {


        CustomerPromotionalEvent customerPromotionalEvent = CustomerPromotionalEventBuilder.aCustomerPromotionalEvent()
                .withCpeEventId(100L)
                .withCpeId(100L)
                .withCpeLoyaltyId("8792684047")
                .withCpeReference("PRODUCT A")
                .withCpeDate(Date.valueOf("2015-06-16"))
                .build();


        return customerPromotionalEvent;


    }


    public static CustomerPromotionalEvent updatedStandardCustomerPromotionalEvent(CustomerPromotionalEvent customerPromotionalEvent) {

        customerPromotionalEvent.setCpeProduct("PRODUCT B");

        return customerPromotionalEvent;

    }


    public static Set<CustomerPromotionalEvent> standardCustomerPromotionalEvents() {

        Set<CustomerPromotionalEvent> customerPromotionalEvents = new HashSet<CustomerPromotionalEvent>(0);

        CustomerPromotionalEvent customerPromotionalEventA  = CustomerPromotionalEventBuilder.aCustomerPromotionalEvent()
                .withCpeEventId(100L)
                .withCpeId(100L)
                .withCpeLoyaltyId("8792684047")
                .withCpeReference("PRODUCT A")
                .build();


        customerPromotionalEvents.add(customerPromotionalEventA);



        CustomerPromotionalEvent customerPromotionalEventB = CustomerPromotionalEventBuilder.aCustomerPromotionalEvent()
                .withCpeEventId(100L)
                .withCpeId(100L)
                .withCpeLoyaltyId("8792684047")
                .withCpeReference("PRODUCT B")
                .build();


        customerPromotionalEvents.add(customerPromotionalEventB);



        return customerPromotionalEvents;



    }
}
