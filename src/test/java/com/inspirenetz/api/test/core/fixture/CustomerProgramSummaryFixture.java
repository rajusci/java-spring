package com.inspirenetz.api.test.core.fixture;

import com.inspirenetz.api.core.domain.CustomerProgramSummary;
import com.inspirenetz.api.test.core.builder.CustomerProgramSummaryBuilder;

import java.util.HashSet;
import java.util.Set;

/**
 * Created by sandheepgr on 20/5/14.
 */
public class CustomerProgramSummaryFixture {

    public static CustomerProgramSummary standardCustomerProgramSummary() {

        CustomerProgramSummary customerProgramSummary = CustomerProgramSummaryBuilder.aCustomerProgramSummary()
                .withCpsMerchantNo(1L)
                .withCpsLoyaltyId("9999888877776661")
                .withCpsProgramId(1L)
                .withCpsAwardCount(100)
                .withCpsProgramAmount(1200)
                .withCpsProgramQuantity(2)
                .withCpsProgramVisit(4)
                .build();

        return customerProgramSummary;

    }


    public static CustomerProgramSummary updatedStandardCustomerProgramSummary(CustomerProgramSummary customerProgramSummary) {

        customerProgramSummary.setCpsAwardCount(110);
        customerProgramSummary.setCpsProgramAmount(100);

        return customerProgramSummary;
    }



    public static Set<CustomerProgramSummary> standardCustomerProgramSummaries() {

        //  Create the Set
        Set<CustomerProgramSummary> customerProgramSummarySet = new HashSet<>();


        CustomerProgramSummary customerProgramSummary1 = CustomerProgramSummaryBuilder.aCustomerProgramSummary()
                .withCpsMerchantNo(1L)
                .withCpsLoyaltyId("9999888877776661")
                .withCpsProgramId(1L)
                .withCpsAwardCount(100)
                .withCpsProgramAmount(1200)
                .withCpsProgramQuantity(2)
                .withCpsProgramVisit(4)
                .build();

        customerProgramSummarySet.add(customerProgramSummary1);


        CustomerProgramSummary customerProgramSummary2 = CustomerProgramSummaryBuilder.aCustomerProgramSummary()
                .withCpsMerchantNo(1L)
                .withCpsLoyaltyId("9999888877776662")
                .withCpsProgramId(1L)
                .withCpsAwardCount(100)
                .withCpsProgramAmount(1200)
                .withCpsProgramQuantity(2)
                .withCpsProgramVisit(4)
                .build();

        customerProgramSummarySet.add(customerProgramSummary2);


        return customerProgramSummarySet;

    }

}
