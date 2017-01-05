package com.inspirenetz.api.test.core.fixture;

import com.inspirenetz.api.core.domain.MerchantLocation;
import com.inspirenetz.api.test.core.builder.MerchantLocationBuilder;

import java.util.HashSet;
import java.util.Set;

/**
 * Created by sandheepgr on 14/5/14.
 */
public class MerchantLocationFixture {

    public static MerchantLocation standardMerchantLocation() {

        MerchantLocation merchantLocation = MerchantLocationBuilder.aMerchantLocation()
                .withMelLocation("Guwahati")
                .withMelMerchantNo(1L)
                .withMelLatitude("12.1212")
                .withMelLongitude("12.0239")
                .build();


        return merchantLocation;

    }



    public static MerchantLocation updatedStandardMerchantLocation(MerchantLocation merchantLocation) {

        merchantLocation.setMelLocation("Hyderabad");
        merchantLocation.setMelLatitude("13.01002");

        return merchantLocation;

    }



    public static Set<MerchantLocation> standardMerchantLocations() {

        Set<MerchantLocation> merchantLocationSet = new HashSet<>();

        MerchantLocation merchantLocation1 = MerchantLocationBuilder.aMerchantLocation()
                .withMelLocation("Guwahati")
                .withMelMerchantNo(1L)
                .withMelLatitude("12.1212")
                .withMelLongitude("12.0239")
                .build();

        merchantLocationSet.add(merchantLocation1);

        MerchantLocation merchantLocation2 = MerchantLocationBuilder.aMerchantLocation()
                .withMelLocation("Hyderabad")
                .withMelMerchantNo(2L)
                .withMelLatitude("13.99387")
                .withMelLongitude("12.390")
                .build();

        merchantLocationSet.add(merchantLocation2);


        return merchantLocationSet;

    }
}
