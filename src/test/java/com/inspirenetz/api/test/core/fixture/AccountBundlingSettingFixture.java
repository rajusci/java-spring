package com.inspirenetz.api.test.core.fixture;

import com.inspirenetz.api.core.domain.AccountBundlingSetting;
import com.inspirenetz.api.test.core.builder.AccountBundlingSettingBuilder;

import java.util.HashSet;
import java.util.Set;

/**
 * Created by sandheepgr on 24/8/14.
 */
public class AccountBundlingSettingFixture {

    public static AccountBundlingSetting standardAccountBundlingSetting() {

        AccountBundlingSetting accountBundlingSetting = AccountBundlingSettingBuilder.anAccountBundlingSetting()
                .withAbsMerchantNo(1L)
                .withAbsLocation(1L)
                .withAbsLinkingType(1)
                .withAbsLinkingEligibility(1)
                .withAbsPrimaryAccountEligibility(1)
                .withAbsBundlingActionInitiation(1)
                .withAbsPrimaryAccountCategory("PCY1001")
                .withAbsBundlingConfirmationType(1)
                .withAbsBundlingRedemption(1)
                .withAbsLinkedAccountLimit(1)
                .withAbsConfirmationExpiryLimit(1)
                .withAbsTierBehaviour(1)
                .build();


        return accountBundlingSetting;

    }




    public static AccountBundlingSetting updatedStandardAccountBundlingSetting(AccountBundlingSetting accountBundlingSetting) {

        accountBundlingSetting.setAbsLinkedAccountLimit(2);
        return accountBundlingSetting;
    }


    public static Set<AccountBundlingSetting> standardAccountBundlingSettings() {

        Set<AccountBundlingSetting> accountBundlingSettingSet = new HashSet<>(0);

        AccountBundlingSetting accountBundlingSetting = standardAccountBundlingSetting();

        accountBundlingSettingSet.add(accountBundlingSetting);

        return accountBundlingSettingSet;


    }
}
