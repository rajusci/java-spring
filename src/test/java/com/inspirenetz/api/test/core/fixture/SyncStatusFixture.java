package com.inspirenetz.api.test.core.fixture;


import com.inspirenetz.api.core.dictionary.SyncProcessStatus;
import com.inspirenetz.api.core.dictionary.SyncType;
import com.inspirenetz.api.core.domain.SyncStatus;
import com.inspirenetz.api.test.core.builder.SyncStatusBuilder;


import java.util.HashSet;
import java.util.Set;

/**
 * Created by fayizkci on 15/9/15.
 */
public class SyncStatusFixture {

    public static SyncStatus standardSyncStatus() {

        SyncStatus syncStatus = SyncStatusBuilder.aSyncStatus()
                .withSysMerchantNo(1L)
                .withSysLocation(1L)
                .withSysBatch(12L)
                .withSysBatchRef("")
                .withSysType(SyncType.SALES)
                .withSysStatus(SyncProcessStatus.ONGOING)
                .build();


        return syncStatus;

    }

    public static SyncStatus updatedStandardSyncStatus(SyncStatus syncStatus) {



        syncStatus.setSysType(SyncType.SALES);

        return syncStatus;

    }


    public static Set<SyncStatus> standardSyncStatuses() {

        Set<SyncStatus> syncStatusSet = new HashSet<>(0);

        SyncStatus syncStatus = SyncStatusBuilder.aSyncStatus()
                .withSysMerchantNo(1L)
                .withSysLocation(1L)
                .withSysBatch(12L)
                .withSysBatchRef("")
                .withSysType(SyncType.SALES)
                .withSysStatus(SyncProcessStatus.ONGOING)
                .build();

        syncStatusSet.add(syncStatus);



        SyncStatus syncStatus2 = SyncStatusBuilder.aSyncStatus()
                .withSysMerchantNo(1L)
                .withSysLocation(1L)
                .withSysBatch(12L)
                .withSysBatchRef("")
                .withSysType(SyncType.ITEMS)
                .withSysStatus(SyncProcessStatus.ONGOING)
                .build();

        syncStatusSet.add(syncStatus2);


        return syncStatusSet;


    }
}
