package com.inspirenetz.api.test.core.fixture;

import com.inspirenetz.api.core.domain.ReferrerProgramSummary;
import com.inspirenetz.api.test.core.builder.ReferrerProgramSummaryBuilder;

import java.util.HashSet;
import java.util.Set;

/**
 * Created by ameen on 8/10/15.
 */
public class ReferrerProgramSummaryFixture {

    public static ReferrerProgramSummary standardReferrerProgramSummary() {

        ReferrerProgramSummary referrerProgramSummary = ReferrerProgramSummaryBuilder.aReferrerProgramSummary()
                .withRpsProgramId(1L)
                .withRpsMerchantNo(1L)
                .withRpsLoyaltyId("12345678")
                .withRpsRefereeLoyaltyId("888888")
                .build();


        return referrerProgramSummary;


    }


    public static ReferrerProgramSummary updatedStandardReferrerProgramSummary(ReferrerProgramSummary referrerProgramSummary) {

        referrerProgramSummary.setRpsProgramVisit(1);


        return referrerProgramSummary;

    }


    public static Set<ReferrerProgramSummary> standardReferrerProgramSummarys() {

        Set<ReferrerProgramSummary> referrerProgramSummarys = new HashSet<ReferrerProgramSummary>(0);

        ReferrerProgramSummary summary1  = ReferrerProgramSummaryBuilder.aReferrerProgramSummary()
                .withRpsProgramId(1L)
                .withRpsMerchantNo(1L)
                .withRpsLoyaltyId("12345678")
                .withRpsRefereeLoyaltyId("888888")
                .build();


        referrerProgramSummarys.add(summary1);



        ReferrerProgramSummary summary2 = ReferrerProgramSummaryBuilder.aReferrerProgramSummary()
                .withRpsProgramId(1L)
                .withRpsMerchantNo(1L)
                .withRpsLoyaltyId("123456781")
                .withRpsRefereeLoyaltyId("8888881")
                .build();


        referrerProgramSummarys.add(summary2);



        return referrerProgramSummarys;



    }
}
