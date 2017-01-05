package com.inspirenetz.api.test.core.fixture;

import com.inspirenetz.api.core.dictionary.IndicatorStatus;
import com.inspirenetz.api.core.dictionary.TransferPointSettingLinkedEligibity;
import com.inspirenetz.api.core.domain.TransferPointSetting;
import com.inspirenetz.api.test.core.builder.TransferPointSettingBuilder;

import java.util.HashSet;
import java.util.Set;

/**
 * Created by sandheepgr on 24/8/14.
 */
public class TransferPointSettingFixture {

    public static TransferPointSetting standardTransferPointSetting() {

        TransferPointSetting transferPointSetting = TransferPointSettingBuilder.aTransferPointSetting()
                .withTpsMerchantNo(1L)
                .withTpsLocation(0L)
                .withTpsIsTierAffected(IndicatorStatus.NO)
                .withTpsMaxTransfers(2)
                .withTpsTransferCharge(1.0)
                .withTpsTransferredPointValidity(30)
                .withTpsLinkedEligibility(TransferPointSettingLinkedEligibity.NO_AUTHORIZATION)
                .build();


        return transferPointSetting;

    }




    public static TransferPointSetting updatedStandardTransferPointSetting(TransferPointSetting transferPointSetting) {

        transferPointSetting.setTpsTransferCharge(2.0);
        return transferPointSetting;
    }


    public static Set<TransferPointSetting> standardTransferPointSettings() {

        Set<TransferPointSetting> transferPointSettingSet = new HashSet<>(0);

        TransferPointSetting transferPointSetting = standardTransferPointSetting();

        transferPointSettingSet.add(transferPointSetting);

        return transferPointSettingSet;


    }
}
