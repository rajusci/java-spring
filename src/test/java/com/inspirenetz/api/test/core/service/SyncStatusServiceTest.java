package com.inspirenetz.api.test.core.service;

import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import com.inspirenetz.api.core.dictionary.SyncType;
import com.inspirenetz.api.core.domain.Merchant;
import com.inspirenetz.api.core.domain.MerchantLocation;
import com.inspirenetz.api.core.domain.SyncStatus;
import com.inspirenetz.api.core.service.MerchantLocationService;
import com.inspirenetz.api.core.service.MerchantService;
import com.inspirenetz.api.core.service.SyncStatusService;
import com.inspirenetz.api.rest.exception.InspireNetzException;
import com.inspirenetz.api.test.config.*;
import com.inspirenetz.api.test.core.fixture.MerchantFixture;
import com.inspirenetz.api.test.core.fixture.MerchantLocationFixture;
import com.inspirenetz.api.test.core.fixture.SyncStatusFixture;
import org.junit.*;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Created by fayizkci on 15/9/15.
 */
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes  = {ApplicationTestConfig.class, PersistenceTestConfig.class, ServiceTestConfig.class,ServiceTestConfig.class, SecurityTestConfig.class, NotificationTestConfig.class})
public class SyncStatusServiceTest {


    private static Logger log = LoggerFactory.getLogger(SyncStatusServiceTest.class);

    @Autowired
    private SyncStatusService syncStatusService;

    @Autowired
    private MerchantService merchantService;

    @Autowired
    private MerchantLocationService merchantLocationService;

    HashSet<SyncStatus> syncStatusHashSet=new HashSet<SyncStatus>();

    HashSet<Merchant> merchantHashSet=new HashSet<Merchant>();

    HashSet<MerchantLocation> merchantLocationsHashSet=new HashSet<MerchantLocation>();

    @Before
    public void setUp() {}


    @Test
    public void testGetLastBatchIndex() throws InspireNetzException {

        Merchant merchant= MerchantFixture.standardMerchant();
        merchant.setMerMerchantNo(100L);
        merchant=merchantService.saveMerchant(merchant);
        merchantHashSet.add(merchant);

        MerchantLocation merchantLocation= MerchantLocationFixture.standardMerchantLocation();
        merchantLocation.setMelMerchantNo(merchant.getMerMerchantNo());
        merchantLocation=merchantLocationService.saveMerchantLocation(merchantLocation);
        merchantLocationsHashSet.add(merchantLocation);

        SyncStatus syncStatus =SyncStatusFixture.standardSyncStatus();
        syncStatus.setSysMerchantNo(merchant.getMerMerchantNo());
        syncStatus.setSysLocation(merchantLocation.getMelId());
        syncStatus = syncStatusService.saveSyncStatus(syncStatus);
        syncStatusHashSet.add(syncStatus);

        Long lastBatchIndex = syncStatusService.getLastBatchIndex(syncStatus.getSysMerchantNo(),syncStatus.getSysLocation(),syncStatus.getSysType(),syncStatus.getSysDate());
        Assert.assertNotNull(lastBatchIndex);
        log.info("Last Batch Index - " + lastBatchIndex.toString());
    }


    @Test
    public void testGetRecentSyncStatusByType() throws Exception {

        Merchant merchant= MerchantFixture.standardMerchant();
        merchant.setMerMerchantNo(100L);
        merchant=merchantService.saveMerchant(merchant);
        merchantHashSet.add(merchant);

        MerchantLocation merchantLocation= MerchantLocationFixture.standardMerchantLocation();
        merchantLocation.setMelMerchantNo(merchant.getMerMerchantNo());
        merchantLocation=merchantLocationService.saveMerchantLocation(merchantLocation);
        merchantLocationsHashSet.add(merchantLocation);

        SyncStatus syncStatus =SyncStatusFixture.standardSyncStatus();
        syncStatus.setSysMerchantNo(merchant.getMerMerchantNo());
        syncStatus.setSysLocation(merchantLocation.getMelId());
        syncStatus = syncStatusService.saveSyncStatus(syncStatus);
        syncStatusHashSet.add(syncStatus);

        SyncStatus searchSyncStatus= syncStatusService.getRecentSyncStatusByType(syncStatus.getSysMerchantNo(), syncStatus.getSysLocation(), syncStatus.getSysType(), syncStatus.getSysDate());
        Assert.assertNotNull(searchSyncStatus);
        log.info("Sync Status Information - " + searchSyncStatus.toString());

    }

    @Test
    public void testFindBySysMerchantNoAndSysLocationAndSysDateAndSysType() throws Exception {

        Merchant merchant= MerchantFixture.standardMerchant();
        merchant.setMerMerchantNo(100L);
        merchant=merchantService.saveMerchant(merchant);
        merchantHashSet.add(merchant);

        MerchantLocation merchantLocation= MerchantLocationFixture.standardMerchantLocation();
        merchantLocation.setMelMerchantNo(merchant.getMerMerchantNo());
        merchantLocation=merchantLocationService.saveMerchantLocation(merchantLocation);
        merchantLocationsHashSet.add(merchantLocation);

        SyncStatus syncStatus =SyncStatusFixture.standardSyncStatus();
        syncStatus.setSysMerchantNo(merchant.getMerMerchantNo());
        syncStatus.setSysLocation(merchantLocation.getMelId());
        syncStatus = syncStatusService.saveSyncStatus(syncStatus);
        syncStatusHashSet.add(syncStatus);

        List<SyncStatus> searchSyncStatus= syncStatusService.findBySysMerchantNoAndSysLocationAndSysTypeAndSysDate(syncStatus.getSysMerchantNo(), syncStatus.getSysLocation(), syncStatus.getSysType(), syncStatus.getSysDate());
        Assert.assertFalse(searchSyncStatus.isEmpty());
        log.info("Sync Status Information - " + searchSyncStatus.toString());

    }



    @Test
    public void testListSyncStatus() throws InspireNetzException {

        Merchant merchant= MerchantFixture.standardMerchant();
        merchant.setMerMerchantNo(100L);
        merchant=merchantService.saveMerchant(merchant);
        merchantHashSet.add(merchant);

        MerchantLocation merchantLocation= MerchantLocationFixture.standardMerchantLocation();
        merchantLocation.setMelMerchantNo(merchant.getMerMerchantNo());
        merchantLocation=merchantLocationService.saveMerchantLocation(merchantLocation);
        merchantLocationsHashSet.add(merchantLocation);

        SyncStatus syncStatus1 =SyncStatusFixture.standardSyncStatus();
        syncStatus1.setSysType(SyncType.ITEMS);
        syncStatus1.setSysMerchantNo(merchant.getMerMerchantNo());
        syncStatus1.setSysLocation(merchantLocation.getMelId());
        syncStatus1 = syncStatusService.saveSyncStatus(syncStatus1);
        syncStatusHashSet.add(syncStatus1);

        SyncStatus syncStatus2 =SyncStatusFixture.standardSyncStatus();
        syncStatus2.setSysType(SyncType.SALES);
        syncStatus2.setSysMerchantNo(merchant.getMerMerchantNo());
        syncStatus2.setSysLocation(merchantLocation.getMelId());
        syncStatus2 = syncStatusService.saveSyncStatus(syncStatus2);
        syncStatusHashSet.add(syncStatus2);

        SyncStatus syncStatus3 =SyncStatusFixture.standardSyncStatus();
        syncStatus3.setSysType(SyncType.CUSTOMERS);
        syncStatus3.setSysMerchantNo(merchant.getMerMerchantNo());
        syncStatus3.setSysLocation(merchantLocation.getMelId());
        syncStatus3 = syncStatusService.saveSyncStatus(syncStatus3);
        syncStatusHashSet.add(syncStatus3);


        List<Map<String, Object>> searchSyncStatus = syncStatusService.listSyncStatus(syncStatus1.getSysMerchantNo(), syncStatus1.getSysLocation(), syncStatus1.getSysDate());
        Assert.assertTrue(!searchSyncStatus.isEmpty());
        log.info("Sync Status list "+searchSyncStatus.toString());

    }




    /**
     * Returns a new object which specifies the the wanted result page.
     * @param pageIndex The index of the wanted result page
     * @return
     */
    private Pageable constructPageSpecification(int pageIndex) {
        Pageable pageSpecifisetion = new PageRequest(pageIndex, 10, sortByLastNameAsc());
        return pageSpecifisetion;
    }

    /**
     * Returns a Sort object which sorts persons in ascending order by using the last name.
     * @return
     */
    private Sort sortByLastNameAsc() {
        return new Sort(Sort.Direction.ASC, "sysId");
    }


    @After
    public void tearDown() throws Exception {

        Set<SyncStatus> syncStatuses = SyncStatusFixture.standardSyncStatuses();

        for(SyncStatus syncStatus: syncStatusHashSet) {

            SyncStatus delSyncStatus = syncStatusService.findBySysId(syncStatus.getSysId());

            if ( delSyncStatus != null ) {
                syncStatusService.deleteSyncStatus(delSyncStatus);
            }

        }

        for(MerchantLocation merchantLocation:merchantLocationsHashSet){

           merchantLocationService.deleteMerchantLocationSet(merchantLocationsHashSet);
        }

        for(Merchant merchant:merchantHashSet){

            merchantService.deleteMerchant(merchant.getMerMerchantNo());
        }
    }

}
