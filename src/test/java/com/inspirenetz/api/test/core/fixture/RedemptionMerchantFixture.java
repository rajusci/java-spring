package com.inspirenetz.api.test.core.fixture;

import com.inspirenetz.api.core.domain.RedemptionMerchant;
import com.inspirenetz.api.core.domain.RedemptionMerchantLocation;
import com.inspirenetz.api.test.core.builder.RedemptionMerchantBuilder;

import java.util.HashSet;
import java.util.Set;

/**
 * Created by saneesh-ci on 9/9/14.
 */
public class RedemptionMerchantFixture {




    public static RedemptionMerchant standardRedemptionMerchant() {

        // For setting dummy data to collection
        Set<RedemptionMerchantLocation> redemptionMerchantLocationSet=new HashSet<>(0);

        RedemptionMerchantLocation redemptionMerchantLocation=new RedemptionMerchantLocation();
        redemptionMerchantLocation.setRmlLocation("Bangalore");


        redemptionMerchantLocationSet.add(redemptionMerchantLocation);

        RedemptionMerchantLocation redemptionMerchantLocation1=new RedemptionMerchantLocation();
        redemptionMerchantLocation1.setRmlLocation("Delhi");

        redemptionMerchantLocationSet.add(redemptionMerchantLocation1);

        RedemptionMerchant redemptionMerchant = RedemptionMerchantBuilder.aRedemptionMerchant()
                .withRemName("PizzaHut")
                .withRemCategory(1L)
                .withRemAddress("Address")
                .withRemVoucherPrefix("ABC")
                .withRemContactMobile("9400651688")
                .withRedemptionMerchantLocations(redemptionMerchantLocationSet)
                .build();


        return redemptionMerchant;


    }

    public static Set<RedemptionMerchant> standardRoles() {

        Set<RedemptionMerchantLocation> redemptionMerchantLocationSet=new HashSet<>(0);

        RedemptionMerchantLocation redemptionMerchantLocation=new RedemptionMerchantLocation();
        redemptionMerchantLocation.setRmlLocation("Bangalore");


        redemptionMerchantLocationSet.add(redemptionMerchantLocation);

        RedemptionMerchantLocation redemptionMerchantLocation1=new RedemptionMerchantLocation();
        redemptionMerchantLocation1.setRmlLocation("Delhi");

        redemptionMerchantLocationSet.add(redemptionMerchantLocation1);

        Set<RedemptionMerchant> redemptionMerchants = new HashSet<RedemptionMerchant>(0);

        RedemptionMerchant pepsi  = RedemptionMerchantBuilder.aRedemptionMerchant()
                .withRemName("PizzaHut")
                .withRemCategory(1L)
                .withRemAddress("Address")
                .withRemVoucherPrefix("ABC")
                .withRedemptionMerchantLocations(redemptionMerchantLocationSet)
                .build();

        redemptionMerchants.add(pepsi);



        RedemptionMerchant coke = RedemptionMerchantBuilder.aRedemptionMerchant()
                .withRemName("Dominos")
                .withRemCategory(1L)
                .withRemAddress("Address")
                .withRemVoucherPrefix("DEF")
                .withRedemptionMerchantLocations(redemptionMerchantLocationSet)
                .build();

        redemptionMerchants.add(coke);



        return redemptionMerchants;



    }
}
