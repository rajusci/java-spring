package com.inspirenetz.api.test.core.fixture;

import com.inspirenetz.api.core.dictionary.IndicatorStatus;
import com.inspirenetz.api.core.dictionary.RewardCurrencyExpiryOption;
import com.inspirenetz.api.core.dictionary.RewardCurrencyUnitType;
import com.inspirenetz.api.core.dictionary.RoundingMethod;
import com.inspirenetz.api.core.domain.RewardCurrency;
import com.inspirenetz.api.test.core.builder.RewardCurrencyBuilder;
import com.inspirenetz.api.util.DBUtils;
import org.antlr.grammar.v3.ANTLRv3Parser;

import java.util.HashSet;
import java.util.Set;

/**
 * Created by sandheepgr on 6/5/14.
 */
public class RewardCurrencyFixture {

    public static RewardCurrency standardRewardCurrency() {

        RewardCurrency rewardCurrency = RewardCurrencyBuilder.aRewardCurrency()
                .withRwdCurrencyName("Super Points")
                .withRwdMerchantNo(1L)
                .withRwdRewardUnitType(RewardCurrencyUnitType.POINTS)
                .withRwdDescription("Super market points")
                .withRwdCashbackIndicator(IndicatorStatus.YES)
                .withRwdCashbackRatioDeno(1.0)
                .withRwdExpiryOption(RewardCurrencyExpiryOption.EXPIRY_MONTHS)
                .withRwdExpiryDays(20)
                .withRwdRoundingMethod(RoundingMethod.ROUND)
                .build();

        return rewardCurrency;

    }



    public static RewardCurrency updatedStandardRewardCurrency(RewardCurrency rewardCurrency) {

        rewardCurrency.setRwdCashbackRatioDeno(2.0);
        rewardCurrency.setRwdCurrencyName("Duper Points");

        return rewardCurrency;

    }


    public static Set<RewardCurrency> standardRewardCurrencies() {

        Set<RewardCurrency> rewardCurrencies = new HashSet<>(0);


        RewardCurrency rewardCurrency1 = RewardCurrencyBuilder.aRewardCurrency()
                .withRwdCurrencyName("Super Points")
                .withRwdMerchantNo(1L)
                .withRwdRewardUnitType(RewardCurrencyUnitType.POINTS)
                .withRwdDescription("Super market points")
                .withRwdCashbackIndicator(IndicatorStatus.YES)
                .withRwdCashbackRatioDeno(1.0)
                .withRwdRoundingMethod(RoundingMethod.ROUND)
                .build();

        rewardCurrencies.add(rewardCurrency1);



        RewardCurrency rewardCurrency2 = RewardCurrencyBuilder.aRewardCurrency()
                .withRwdCurrencyName("Fashion Points")
                .withRwdMerchantNo(1L)
                .withRwdRewardUnitType(RewardCurrencyUnitType.POINTS)
                .withRwdDescription("Textiles points")
                .withRwdCashbackIndicator(IndicatorStatus.NO)
                .withRwdCashbackRatioDeno(1.0)
                .withRwdRoundingMethod(RoundingMethod.ROUND)
                .build();

        rewardCurrencies.add(rewardCurrency2);



        RewardCurrency rewardCurrency3 = RewardCurrencyBuilder.aRewardCurrency()
                .withRwdCurrencyName("Fashion Points")
                .withRwdMerchantNo(2L)
                .withRwdRewardUnitType(RewardCurrencyUnitType.POINTS)
                .withRwdDescription("Textiles points")
                .withRwdCashbackIndicator(IndicatorStatus.NO)
                .withRwdCashbackRatioDeno(1.0)
                .withRwdRoundingMethod(RoundingMethod.ROUND)
                .build();

        rewardCurrencies.add(rewardCurrency3);


        return rewardCurrencies;

    }
}
