package com.inspirenetz.api.test.core.fixture;

import com.inspirenetz.api.core.dictionary.LinkedLoyaltyStatus;
import com.inspirenetz.api.core.domain.Customer;
import com.inspirenetz.api.core.domain.LinkedLoyalty;
import com.inspirenetz.api.test.core.builder.LinkedLoyaltyBuilder;

import java.util.HashSet;
import java.util.Set;

/**
 * Created by saneesh-ci on 22/8/14.
 */
public class LinkedLoyaltyFixture {

    public static LinkedLoyalty standardLinkedLoyalty() {

        LinkedLoyalty linkedLoyalty = LinkedLoyaltyBuilder.aLinkedLoyalty()
                .withLilParentCustomerNo(1L)
                .withLilChildCustomerNo(2L)
                .withLilStatus(LinkedLoyaltyStatus.ACTIVE)
                .withLilLocation(1L)
                .build();


        return linkedLoyalty;


    }

    public static LinkedLoyalty standardLinkedLoyalty(Customer customer) {

        LinkedLoyalty linkedLoyalty = LinkedLoyaltyBuilder.aLinkedLoyalty()
                .withLilParentCustomerNo(customer.getCusCustomerNo())
                .withLilChildCustomerNo(2L)
                .withLilStatus(LinkedLoyaltyStatus.ACTIVE)
                .withLilLocation(1L)
                .build();


        return linkedLoyalty;

    }




    public static LinkedLoyalty updatedStandardLinkedLoyalty(LinkedLoyalty linkedLoyalty) {

        linkedLoyalty.setLilStatus(LinkedLoyaltyStatus.DISABLED);
        linkedLoyalty.setLilLocation(2L);

        return linkedLoyalty;

    }


    public static Set<LinkedLoyalty> standardLinkedLoyaltys() {

        Set<LinkedLoyalty> linkedLoyalties = new HashSet<LinkedLoyalty>(0);

        LinkedLoyalty loyalty1  = LinkedLoyaltyBuilder.aLinkedLoyalty()
                .withLilParentCustomerNo(1L)
                .withLilChildCustomerNo(2L)
                .withLilStatus(LinkedLoyaltyStatus.ACTIVE)
                .withLilLocation(1L)
                .build();


        linkedLoyalties.add(loyalty1);



        LinkedLoyalty loyalty2 = LinkedLoyaltyBuilder.aLinkedLoyalty()
                .withLilParentCustomerNo(1L)
                .withLilChildCustomerNo(3L)
                .withLilStatus(LinkedLoyaltyStatus.ACTIVE)
                .withLilLocation(1L)
                .build();

        linkedLoyalties.add(loyalty2);



        return linkedLoyalties;



    }
}
