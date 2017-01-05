package com.inspirenetz.api.test.core.fixture;

import com.inspirenetz.api.core.domain.RedemptionMerchantLocation;
import com.inspirenetz.api.test.core.builder.RedemptionMerchantLocationBuilder;

import java.util.HashSet;
import java.util.Set;

/**
 * Created by saneesh-ci on 9/9/14.
 */
public class RedemptionMerchantLocationFixture {




    public static RedemptionMerchantLocation standardRedemptionMerchantLocation() {


        RedemptionMerchantLocation redemptionMerchantLocation = RedemptionMerchantLocationBuilder.aRedemptionMerchantLocation()
                .withRmlLocation("Kochi")
                .withRmlMerNo(1L)
                .build();


        return redemptionMerchantLocation;


    }


    public static RedemptionMerchantLocation updatedStandardRedemptionMerchantLocation(RedemptionMerchantLocation redemptionMerchantLocation) {

        RedemptionMerchantLocation redemptionMerchantLocation1 = RedemptionMerchantLocationFixture.standardRedemptionMerchantLocation();
        redemptionMerchantLocation1.setRmlLocation("Bangalore");
        return redemptionMerchantLocation;

    }


    public static Set<RedemptionMerchantLocation> standardRedemptionMerchantLocations() {

        Set<RedemptionMerchantLocation> redemptionMerchantLocations = new HashSet<RedemptionMerchantLocation>(0);

        RedemptionMerchantLocation kochi  = RedemptionMerchantLocationBuilder.aRedemptionMerchantLocation()
                .withRmlLocation("Kochi")
                .withRmlMerNo(1L)
                .build();

        redemptionMerchantLocations.add(kochi);



        RedemptionMerchantLocation delhi = RedemptionMerchantLocationBuilder.aRedemptionMerchantLocation()
                .withRmlLocation("Delhi")
                .withRmlMerNo(1L)
                .build();

        redemptionMerchantLocations.add(delhi);



        return redemptionMerchantLocations;



    }
}
