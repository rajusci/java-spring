package com.inspirenetz.api.test.core.fixture;

import com.inspirenetz.api.core.dictionary.TierEvaluationPeriod;
import com.inspirenetz.api.core.dictionary.TierEvaluationPeriodType;
import com.inspirenetz.api.core.domain.TierGroup;
import com.inspirenetz.api.test.core.builder.TierGroupBuilder;

import java.util.HashSet;
import java.util.Set;

/**
 * Created by sandheepgr on 30/4/14.
 */
public class TierGroupFixture {

    public static TierGroup standardTierGroup() {

        TierGroup tierGroup = TierGroupBuilder.aTierGroup()
                .withTigMerchantNo(1L)
                .withTigName("PREPAID")
                .withTigLocation(2L)
                .withTigRewardCurrency(1L)
                .withTigTransactionCurrency(102L)
                .withTigApplicableGroup("PCY1001")
                .withTigEvaluationPeriodCompType(TierEvaluationPeriodType.BY_CALENDAR)
                .withTigUpgradeCheckPeriod(TierEvaluationPeriod.DAILY)
                .withTigDowngradeCheckPeriod(TierEvaluationPeriod.DAILY)
                .build();


        return tierGroup;


    }


    public static TierGroup updatedStandardTierGroup(TierGroup tierGroup) {

        tierGroup.setTigName("BROADBAND");
        tierGroup.setTigMerchantNo(1L);
        tierGroup.setTigLocation(100L);

        return tierGroup;

    }


    public static Set<TierGroup> standardTierGroups() {

        Set<TierGroup> tierGroups = new HashSet<TierGroup>(0);

        TierGroup prepaidGroup  = TierGroupBuilder.aTierGroup()
                .withTigMerchantNo(1L)
                .withTigName("PREPAID")
                .withTigLocation(2L)
                .withTigTransactionCurrency(102L)
                .withTigRewardCurrency(107L)
                .withTigApplicableGroup("PCY1001")
                .withTigEvaluationPeriodCompType(TierEvaluationPeriodType.BY_CALENDAR)
                .withTigUpgradeCheckPeriod(TierEvaluationPeriod.DAILY)
                .withTigDowngradeCheckPeriod(TierEvaluationPeriod.QUARTERLY)
                .build();

        tierGroups.add(prepaidGroup);



        TierGroup postPaidGroup = TierGroupBuilder.aTierGroup()
                .withTigMerchantNo(1L)
                .withTigName("POSTPAID")
                .withTigLocation(2L)
                .withTigTransactionCurrency(102L)
                .withTigRewardCurrency(107L)
                .withTigApplicableGroup("PCY1002")
                .withTigEvaluationPeriodCompType(TierEvaluationPeriodType.BY_CALENDAR)
                .withTigUpgradeCheckPeriod(TierEvaluationPeriod.DAILY)
                .withTigDowngradeCheckPeriod(TierEvaluationPeriod.QUARTERLY)
                .build();

        tierGroups.add(postPaidGroup);



        return tierGroups;



    }
}
