package com.inspirenetz.api.test.core.fixture;

import com.inspirenetz.api.core.dictionary.CustomerSegmentComparisonType;
import com.inspirenetz.api.core.dictionary.IndicatorStatus;
import com.inspirenetz.api.core.dictionary.RuleApplicationType;
import com.inspirenetz.api.core.domain.Tier;
import com.inspirenetz.api.test.core.builder.TierBuilder;

import java.util.HashSet;
import java.util.Set;

/**
 * Created by sandheepgr on 21/8/14.
 */
public class TierFixture {

    public static Tier standardTier() {

        Tier tier = TierBuilder.aTier()
                .withTieParentGroup(10L)
                .withTieName("TEST TIER1")
                .withTiePointInd(IndicatorStatus.YES)
                .withTiePointCompType(CustomerSegmentComparisonType.MORE_THAN)
                .withTiePointValue1(1000.0)
                .withTieRuleApplicationType(RuleApplicationType.EITHER)
                .build();


        return tier;

    }



    public static Tier updatedStandardTier(Tier tier) {

        tier.setTiePointValue1(1200.0);
        return tier;

    }



    public static Set<Tier> standardTiers() {

        Set<Tier> tierSet = new HashSet<>(0);

        Tier tier1 = TierBuilder.aTier()
                .withTieParentGroup(10L)
                .withTieName("TEST TIER1")
                .withTiePointInd(IndicatorStatus.YES)
                .withTiePointCompType(CustomerSegmentComparisonType.MORE_THAN)
                .withTiePointValue1(1000.0)
                .withTieRuleApplicationType(RuleApplicationType.ALL)
                .build();

        tierSet.add(tier1);



        Tier tier2 = TierBuilder.aTier()
                .withTieParentGroup(10L)
                .withTieName("TEST TIER2")
                .withTiePointInd(IndicatorStatus.YES)
                .withTiePointCompType(CustomerSegmentComparisonType.MORE_THAN)
                .withTiePointValue1(2000.0)
                .withTieRuleApplicationType(RuleApplicationType.ALL)
                .build();


        tierSet.add(tier2);


        return tierSet;

    }
}
