package com.inspirenetz.api.test.core.fixture;

import com.inspirenetz.api.core.domain.MerchantProgramSummary;
import com.inspirenetz.api.test.core.builder.MerchantProgramSummaryBuilder;

import java.util.HashSet;
import java.util.Set;

/**
 * Created by sandheepgr on 21/5/14.
 */
public class MerchantProgramSummaryFixture {

    public static MerchantProgramSummary standardMerchantProgramSummary() {

        MerchantProgramSummary merchantProgramSummary = MerchantProgramSummaryBuilder.aMerchantProgramSummary()
                .withMpsMerchantNo(1L)
                .withMpsBranch(1L)
                .withMpsProgramId(1L)
                .withMpsTransactionAmount(100.0)
                .withMpsRewardCount(10.0)
                .withMpsTransactionCount(10)
                .build();

        return merchantProgramSummary;

    }


    public static MerchantProgramSummary updatedStandardMerchantProgramSummary(MerchantProgramSummary merchantProgramSummary) {

        merchantProgramSummary.setMpsRewardCount(12.0);
        merchantProgramSummary.setMpsTransactionAmount(110.0);

        return merchantProgramSummary;

    }


    public static Set<MerchantProgramSummary> standardMerchantProgramSummaries() {

        Set<MerchantProgramSummary> merchantProgramSummaries = new HashSet<>(0);

        MerchantProgramSummary merchantProgramSummary1 = MerchantProgramSummaryBuilder.aMerchantProgramSummary()
                .withMpsMerchantNo(1L)
                .withMpsBranch(1L)
                .withMpsProgramId(1L)
                .withMpsTransactionAmount(100.0)
                .withMpsRewardCount(10.0)
                .withMpsTransactionCount(10)
                .build();

        merchantProgramSummaries.add(merchantProgramSummary1);



        MerchantProgramSummary merchantProgramSummary2 = MerchantProgramSummaryBuilder.aMerchantProgramSummary()
                .withMpsMerchantNo(1L)
                .withMpsBranch(1L)
                .withMpsProgramId(2L)
                .withMpsTransactionAmount(100.0)
                .withMpsRewardCount(10.0)
                .withMpsTransactionCount(10)
                .build();

        merchantProgramSummaries.add(merchantProgramSummary2);


        // Return the set
        return merchantProgramSummaries;

    }

}
