package com.inspirenetz.api.test.core.fixture;


import com.inspirenetz.api.core.dictionary.IndicatorStatus;
import com.inspirenetz.api.core.domain.MerchantModule;
import com.inspirenetz.api.test.core.builder.MerchantModuleBuilder;

import java.util.HashSet;
import java.util.Set;

/**
 * Created by sandheepgr on 26/6/14.
 */
public class MerchantModuleFixture {

    public static MerchantModule standardMerchantModule() {

        MerchantModule merchantModule = MerchantModuleBuilder.aMerchantModule()
                .withMemMerchantNo(1L)
                .withMemModuleId(9999L)
                .withMemEnabledInd(IndicatorStatus.YES)
                .build();


        return merchantModule;


    }



    public static MerchantModule updatedStandardMerchantModule(MerchantModule merchantModule) {

        merchantModule.setMemEnabledInd(IndicatorStatus.NO);
        return merchantModule;

    }



    public static  Set<MerchantModule> standardMerchantModules() {

        Set<MerchantModule>  merchantModuleSet = new HashSet<>(0);

        MerchantModule merchantModule1 = MerchantModuleBuilder.aMerchantModule()
                .withMemMerchantNo(1L)
                .withMemModuleId(9999L)
                .withMemEnabledInd(IndicatorStatus.YES)
                .build();

        merchantModuleSet.add(merchantModule1);


        MerchantModule merchantModule2 = MerchantModuleBuilder.aMerchantModule()
                .withMemMerchantNo(1L)
                .withMemModuleId(8888L)
                .withMemEnabledInd(IndicatorStatus.YES)
                .build();

        merchantModuleSet.add(merchantModule2);


        return merchantModuleSet;


    }
}
