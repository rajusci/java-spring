package com.inspirenetz.api.test.core.fixture;

import com.inspirenetz.api.core.domain.RedemptionVoucherSource;
import com.inspirenetz.api.test.core.builder.RedemptionVoucherSourceBuilder;

import java.util.HashSet;
import java.util.Set;

/**
 * Created by saneesh-ci on 27/9/14.
 */
public class RedemptionVoucherSourceFixture {




    public static RedemptionVoucherSource standardRedemptionVoucherSource() {


        RedemptionVoucherSource redemptionVoucherSource = RedemptionVoucherSourceBuilder.aRedemptionVoucherSource()
                .withRvsName("Test Voucher Source")
                .withRvsType(2)
                .withRvsPrefix("ABC")
                .withRvsCodeStart(1236L)
                .withRvsCodeEnd(5236L)
                .withRvsMerchantNo(1l)
                .withRvsStatus(1)
                .build();


        return redemptionVoucherSource;


    }


    public static RedemptionVoucherSource updatedStandardRedemptionVoucherSource(RedemptionVoucherSource redemptionVoucherSource) {

        redemptionVoucherSource.setRvsName("Test 2");

        return redemptionVoucherSource;

    }


    public static Set<RedemptionVoucherSource> standardRedemptionVoucherSources() {

        Set<RedemptionVoucherSource> redemptionVoucherSources = new HashSet<RedemptionVoucherSource>(0);

        RedemptionVoucherSource redemptionVoucherSourceA  = RedemptionVoucherSourceBuilder.aRedemptionVoucherSource()
                .withRvsName("Test Voucher Source")
                .withRvsType(2)
                .withRvsCode("ABC001")
                .build();


        redemptionVoucherSources.add(redemptionVoucherSourceA);



        RedemptionVoucherSource redemptionVoucherSourceB = RedemptionVoucherSourceBuilder.aRedemptionVoucherSource()
                .withRvsName("Test 2")
                .withRvsType(1)
                .withRvsCode("ABC002")
                .build();



        redemptionVoucherSources.add(redemptionVoucherSourceB);



        return redemptionVoucherSources;



    }
}
