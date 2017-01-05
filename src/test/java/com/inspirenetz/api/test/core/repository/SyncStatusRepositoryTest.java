package com.inspirenetz.api.test.core.repository;

import com.google.common.collect.Sets;
import com.inspirenetz.api.core.domain.Module;
import com.inspirenetz.api.core.domain.SyncStatus;
import com.inspirenetz.api.core.repository.ModuleRepository;
import com.inspirenetz.api.core.repository.SyncStatusRepository;
import com.inspirenetz.api.test.config.ApplicationTestConfig;
import com.inspirenetz.api.test.config.PersistenceTestConfig;
import com.inspirenetz.api.test.core.fixture.ModuleFixture;
import com.inspirenetz.api.test.core.fixture.SyncStatusFixture;
import org.junit.*;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Created by fayizkci on 15/9/15.
 */
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes  = {ApplicationTestConfig.class, PersistenceTestConfig.class})
public class SyncStatusRepositoryTest {


    //  Create the logger
    private static Logger log = LoggerFactory.getLogger(SyncStatusRepositoryTest.class);

    @Autowired
    private SyncStatusRepository syncStatusRepository;

    HashSet<SyncStatus> syncStatusHashSet=new HashSet<SyncStatus>();

    @Before
    public void setUp() throws Exception {


    }

    @After
    public void tearDown() throws Exception {

        Set<SyncStatus> syncStatuses = SyncStatusFixture.standardSyncStatuses();

        for(SyncStatus syncStatus: syncStatusHashSet) {

            SyncStatus delSyncStatus = syncStatusRepository.findBySysId(syncStatus.getSysId());

            if ( delSyncStatus != null ) {
                syncStatusRepository.delete(delSyncStatus);
            }

        }
    }


    @Test
    public void test1Create(){


        SyncStatus syncStatus = syncStatusRepository.save(SyncStatusFixture.standardSyncStatus());
        log.info(syncStatus.toString());
        Assert.assertNotNull(syncStatus.getSysId());
        syncStatusHashSet.add(syncStatus);
    }

    @Test
    public void test2Update() {

        SyncStatus syncStatus =SyncStatusFixture.standardSyncStatus();
        syncStatus = syncStatusRepository.save(syncStatus);
        log.info("Original Sync Status " + syncStatus.toString());
        syncStatusHashSet.add(syncStatus);

        SyncStatus updatedSyncStatus = SyncStatusFixture.updatedStandardSyncStatus(syncStatus);
        updatedSyncStatus = syncStatusRepository.save(updatedSyncStatus);
        log.info("Updated Sync Status "+ updatedSyncStatus.toString());

    }

    @Test
    public void testFindBySysId() {

        SyncStatus syncStatus =SyncStatusFixture.standardSyncStatus();
        syncStatus = syncStatusRepository.save(syncStatus);
        syncStatusHashSet.add(syncStatus);

        SyncStatus searchSyncStatus = syncStatusRepository.findBySysId(syncStatus.getSysId());
        Assert.assertNotNull(searchSyncStatus);
        log.info("Sync Status Information" + searchSyncStatus.toString());
    }


    @Test
    public void testFindBySysMerchantNo()  {

        SyncStatus syncStatus =SyncStatusFixture.standardSyncStatus();
        syncStatus = syncStatusRepository.save(syncStatus);
        syncStatusHashSet.add(syncStatus);

        List<SyncStatus> searchSyncStatus = syncStatusRepository.findBySysMerchantNo(syncStatus.getSysMerchantNo());
        Assert.assertTrue(!searchSyncStatus.isEmpty());
        log.info("Sync Status list "+searchSyncStatus.toString());
    }

    @Test
    public void testFindBySysMerchantNoAndSysLocation()  {

        SyncStatus syncStatus =SyncStatusFixture.standardSyncStatus();
        syncStatus = syncStatusRepository.save(syncStatus);
        syncStatusHashSet.add(syncStatus);

        List<SyncStatus> searchSyncStatus = syncStatusRepository.findBySysMerchantNoAndSysLocation(syncStatus.getSysMerchantNo(), syncStatus.getSysLocation());
        Assert.assertTrue(!searchSyncStatus.isEmpty());
        log.info("Sync Status list "+searchSyncStatus.toString());
    }

    @Test
    public void testFindBySysMerchantNoAndSysLocationAndSysTypeAndSysDateOrderBySysBatchDesc()  {

        SyncStatus syncStatus =SyncStatusFixture.standardSyncStatus();
        syncStatus = syncStatusRepository.save(syncStatus);
        syncStatusHashSet.add(syncStatus);

        List<SyncStatus> searchSyncStatus = syncStatusRepository.findBySysMerchantNoAndSysLocationAndSysTypeAndSysDateOrderBySysBatchDesc(syncStatus.getSysMerchantNo(), syncStatus.getSysLocation(), syncStatus.getSysType(), syncStatus.getSysDate());
        Assert.assertTrue(!searchSyncStatus.isEmpty());
        log.info("Sync Status list "+searchSyncStatus.toString());
    }






    /**
     * Returns a new object which specifies the the wanted result page.
     * @param pageIndex The index of the wanted result page
     * @return
     */
    private Pageable constructPageSpecification(int pageIndex) {
        Pageable pageSpecification = new PageRequest(pageIndex, 10, sortByLastNameAsc());
        return pageSpecification;
    }

    /**
     * Returns a Sort object which sorts persons in ascending order by using the last name.
     * @return
     */
    private Sort sortByLastNameAsc() {
        return new Sort(Sort.Direction.ASC, "sysId");
    }
}
