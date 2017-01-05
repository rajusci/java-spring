package com.inspirenetz.api.test.core.fixture;

import com.inspirenetz.api.core.dictionary.ImagePrimaryId;
import com.inspirenetz.api.core.dictionary.PointRewardData;
import com.inspirenetz.api.test.core.builder.PointRewardDataBuilder;

/**
 * Created by sandheepgr on 27/5/14.
 */
public class PointRewardDataFixture {

    public static PointRewardData standardPointRewardData() {

        PointRewardData pointRewardData = PointRewardDataBuilder.aPointRewardData()
                .withMerchantNo(1L)
                .withMerchantName("Standard Merchant")
                .withMerchantLogo(ImagePrimaryId.PRIMARY_MERCHANT_LOGO)
                .withLoyaltyId("9999888877776661")
                .withUserNo(2L)
                .withUsrFName("Sandeep")
                .withUsrLName("Menon")
                .withUsrProfilePic(ImagePrimaryId.PRIMARY_DEFAULT_IMAGE)
                .withRwdCurrencyId(100L)
                .withRwdCurrencyName("Standard Currency")
                .withRewardQty(100.0)
                .withTotalRewardQty(100)
                .build();

        return pointRewardData;
    }
}
