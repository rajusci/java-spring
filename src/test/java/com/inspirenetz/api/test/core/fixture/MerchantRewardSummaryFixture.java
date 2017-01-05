package com.inspirenetz.api.test.core.fixture;

import com.inspirenetz.api.core.domain.MerchantRewardSummary;
import com.inspirenetz.api.test.core.builder.MerchantRewardSummaryBuilder;
import com.inspirenetz.api.util.DBUtils;

import java.util.HashSet;
import java.util.Set;

/**
 * Created by sandheepgr on 21/5/14.
 */
public class MerchantRewardSummaryFixture {

    public static MerchantRewardSummary standardMerchantRewardSummary() {

        MerchantRewardSummary merchantRewardSummary = MerchantRewardSummaryBuilder.aMerchantRewardSummary()
                .withMrsMerchantNo(1L)
                .withMrsCurrencyId(1L)
                .withMrsBranch(1L)
                .withMrsDate(DBUtils.covertToSqlDate("2014-05-21"))
                .withMrsTotalRewarded(1000.0)
                .withMrsTotalRedeemed(500.0)
                .withMrsRewardExpired(200.0)
                .build();

        return merchantRewardSummary;

    }


    public static MerchantRewardSummary updatedStandardMerchantRewardSummary(MerchantRewardSummary merchantRewardSummary) {

        merchantRewardSummary.setMrsTotalRewarded(1200.0);
        merchantRewardSummary.setMrsTotalRedeemed(600.0);
        merchantRewardSummary.setMrsRewardExpired(220.0);

        return merchantRewardSummary;

    }


    public static Set<MerchantRewardSummary> standardMerchantRewardSummaries() {

        Set<MerchantRewardSummary> merchantRewardSummarySet = new HashSet<>(0);

        MerchantRewardSummary merchantRewardSummary1 = MerchantRewardSummaryBuilder.aMerchantRewardSummary()
                .withMrsMerchantNo(1L)
                .withMrsCurrencyId(1L)
                .withMrsBranch(1L)
                .withMrsDate(DBUtils.covertToSqlDate("2014-05-21"))
                .withMrsTotalRewarded(1000.0)
                .withMrsTotalRedeemed(500.0)
                .withMrsRewardExpired(200.0)
                .build();

        merchantRewardSummarySet.add(merchantRewardSummary1);


        MerchantRewardSummary merchantRewardSummary2 = MerchantRewardSummaryBuilder.aMerchantRewardSummary()
                .withMrsMerchantNo(1L)
                .withMrsCurrencyId(1L)
                .withMrsBranch(1L)
                .withMrsDate(DBUtils.covertToSqlDate("2014-05-21"))
                .withMrsTotalRewarded(1000.0)
                .withMrsTotalRedeemed(500.0)
                .withMrsRewardExpired(200.0)
                .build();

        merchantRewardSummarySet.add(merchantRewardSummary1);


        return merchantRewardSummarySet;

    }

}
