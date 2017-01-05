package com.inspirenetz.api.test.core.fixture;

import com.inspirenetz.api.core.domain.AccountArchive;
import com.inspirenetz.api.core.domain.AccountArchiveBuilder;

/**
 * Created by sandheepgr on 8/9/14.
 */
public class AccountArchiveFixture {

    public static AccountArchive standardAccountArchive() {

        AccountArchive accountArchive = AccountArchiveBuilder.anAccountArchive()
                .withAarMerchantNo(1L)
                .withAarOldLoyaltyId("9999888877776661")
                .withAarNewLoyaltyId("9999888877776662")
                .build();

        return accountArchive;

    }

}
