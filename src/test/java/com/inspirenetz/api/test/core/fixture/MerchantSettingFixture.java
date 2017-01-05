package com.inspirenetz.api.test.core.fixture;

import com.inspirenetz.api.core.domain.MerchantSetting;
import com.inspirenetz.api.test.core.builder.MerchantSettingBuilder;

import java.util.HashSet;
import java.util.Set;

/**
 * Created by sandheepgr on 26/6/14.
 */
public class MerchantSettingFixture {

    public static MerchantSetting standardMerchantSetting() {

        MerchantSetting merchantSetting = MerchantSettingBuilder.aMerchantSetting()
                .withMesMerchantNo(1L)
                .withMesSettingId(9999L)
                .withMesValue("OVAL")
                .build();


        return merchantSetting;


    }



    public static MerchantSetting updatedStandardMerchantSetting(MerchantSetting merchantSetting) {

        merchantSetting.setMesValue("CIRCLE");
        return merchantSetting;

    }



    public static  Set<MerchantSetting> standardMerchantSettings() {

        Set<MerchantSetting>  merchantSettingSet = new HashSet<>(0);

        MerchantSetting merchantSetting1 = MerchantSettingBuilder.aMerchantSetting()
                .withMesMerchantNo(1L)
                .withMesSettingId(9999L)
                .withMesValue("OVAL")
                .build();

        merchantSettingSet.add(merchantSetting1);


        MerchantSetting merchantSetting2 = MerchantSettingBuilder.aMerchantSetting()
                .withMesMerchantNo(1L)
                .withMesSettingId(8888L)
                .withMesValue("CIRCLE")
                .build();

        merchantSettingSet.add(merchantSetting2);


        return merchantSettingSet;


    }
}
