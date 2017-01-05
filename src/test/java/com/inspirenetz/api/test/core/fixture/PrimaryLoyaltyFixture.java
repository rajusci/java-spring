package com.inspirenetz.api.test.core.fixture;

import com.inspirenetz.api.core.dictionary.PrimaryLoyaltyStatus;
import com.inspirenetz.api.core.domain.PrimaryLoyalty;
import com.inspirenetz.api.test.core.builder.PrimaryLoyaltyBuilder;

import java.util.HashSet;
import java.util.Set;

/**
 * Created by saneeshci on 30/4/14.
 */
public class PrimaryLoyaltyFixture {

    public static PrimaryLoyalty standardPrimaryLoyalty() {

        PrimaryLoyalty primaryLoyalty = PrimaryLoyaltyBuilder.aPrimaryLoyalty()
                .withPllCustomerNo(1L)
                .withPllLoyaltyId("ABC1000231")
                .withPllFName("Pepsi")
                .withPllLocation(1L)
                .withPllStatus(PrimaryLoyaltyStatus.ACTIVE)
                .build();


        return primaryLoyalty;


    }


    public static PrimaryLoyalty updatedStandardPrimaryLoyalty(PrimaryLoyalty primaryLoyalty) {

        primaryLoyalty.setPllFName("CocaCola");
        primaryLoyalty.setPllLName("Another Soft Drink");

        return primaryLoyalty;

    }


    public static Set<PrimaryLoyalty> standardPrimaryLoyaltys() {

        Set<PrimaryLoyalty> primaryLoyaltys = new HashSet<PrimaryLoyalty>(0);

        PrimaryLoyalty pepsi  = PrimaryLoyaltyBuilder.aPrimaryLoyalty()
                .withPllCustomerNo(1L)
                .withPllLoyaltyId("ABC1000231")
                .withPllFName("Pepsi")
                .withPllLocation(1L)
                .withPllStatus(PrimaryLoyaltyStatus.ACTIVE)
                .withPllLName("d")
                .build();

        primaryLoyaltys.add(pepsi);



        PrimaryLoyalty coke = PrimaryLoyaltyBuilder.aPrimaryLoyalty()
                .withPllCustomerNo(2L)
                .withPllLoyaltyId("ABC1000241")
                .withPllFName("Cola")
                .withPllLocation(1L)
                .withPllStatus(PrimaryLoyaltyStatus.ACTIVE)
                .withPllLName("d")
                .build();

        primaryLoyaltys.add(coke);



        return primaryLoyaltys;



    }
}
