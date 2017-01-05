package com.inspirenetz.api.test.core.fixture;

import com.inspirenetz.api.core.dictionary.CouponDistributionStatus;
import com.inspirenetz.api.core.domain.CouponDistribution;
import com.inspirenetz.api.test.core.builder.CouponDistributionBuilder;

import java.util.HashSet;
import java.util.Set;

/**
 * Created by sandheepgr on 18/6/14.
 */
public class CouponDistributionFixture {

    public static CouponDistribution standardCouponDistribution() {

        CouponDistribution couponDistribution = CouponDistributionBuilder.aCouponDistribution()
                .withCodMerchantNo(1L)
                .withCodCouponCode("CPN10001")
                .withCodBroadCastType("1:2:3")
                .build();

        return couponDistribution;

    }



    public static CouponDistribution updatedStandardCouponDistribution(CouponDistribution couponDistribution) {

        couponDistribution.setCodStatus(CouponDistributionStatus.SUSPENDED);

        return couponDistribution;

    }



    public static Set<CouponDistribution> standardCouponDistributions() {

        Set<CouponDistribution> couponDistributionSet = new HashSet<>(0);


        CouponDistribution couponDistribution1 = CouponDistributionBuilder.aCouponDistribution()
                .withCodMerchantNo(1L)
                .withCodCouponCode("CPN10001")
                .build();

        couponDistributionSet.add(couponDistribution1);


        return couponDistributionSet;

    }

}
