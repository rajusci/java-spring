package com.inspirenetz.api.test.core.repository;

import com.inspirenetz.api.core.domain.TransferPointSetting;
import com.inspirenetz.api.core.repository.TransferPointSettingRepository;
import com.inspirenetz.api.test.config.ApplicationTestConfig;
import com.inspirenetz.api.test.config.PersistenceTestConfig;
import com.inspirenetz.api.test.core.fixture.TransferPointSettingFixture;
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
import java.util.Set;

/**
 * Created by sandheepgr on 17/2/14.
 */
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes  = {ApplicationTestConfig.class, PersistenceTestConfig.class})
public class TransferPointSettingRepositoryTest {

    private static Logger log = LoggerFactory.getLogger(TransferPointSettingRepositoryTest.class);

    @Autowired
    private TransferPointSettingRepository transferPointSettingRepository;

    Set<TransferPointSetting> tempSet = new HashSet<>(0);


    @Before
    public void setup() {}

    @Test
    public void test1Create(){


        TransferPointSetting transferPointSetting = transferPointSettingRepository.save(TransferPointSettingFixture.standardTransferPointSetting());
        log.info(transferPointSetting.toString());

        // Add to the tempSet
        tempSet.add(transferPointSetting);

        Assert.assertNotNull(transferPointSetting.getTpsId());

    }

    @Test
    public void test2Update() {

        TransferPointSetting transferPointSetting = TransferPointSettingFixture.standardTransferPointSetting();
        transferPointSetting = transferPointSettingRepository.save(transferPointSetting);
        log.info("Original TransferPointSetting " + transferPointSetting.toString());

        // Add to the tempSet
        tempSet.add(transferPointSetting);

        TransferPointSetting updatedTransferPointSetting = TransferPointSettingFixture.updatedStandardTransferPointSetting(transferPointSetting);
        updatedTransferPointSetting = transferPointSettingRepository.save(updatedTransferPointSetting);
        log.info("Updated TransferPointSetting "+ updatedTransferPointSetting.toString());

    }



    @Test
    public void test3FindByTpsId() {

        // Create the transferPointSetting
        TransferPointSetting transferPointSetting = TransferPointSettingFixture.standardTransferPointSetting();
        transferPointSettingRepository.save(transferPointSetting);
        Assert.assertNotNull(transferPointSetting.getTpsId());
        log.info("TransferPointSetting created");

        // Add to the tempSet
        tempSet.add(transferPointSetting);

        // Get the setting
        TransferPointSetting transferPointSetting1 = transferPointSettingRepository.findByTpsId(transferPointSetting.getTpsId());
        Assert.assertNotNull(transferPointSetting1);;
        log.info("Account Bundling Setting " + transferPointSetting);


    }

    @Test
    public void test4FindByTpsMerchantNoAndTpsLocation() {

        // Create the transferPointSetting
        TransferPointSetting transferPointSetting = TransferPointSettingFixture.standardTransferPointSetting();
        transferPointSettingRepository.save(transferPointSetting);
        Assert.assertNotNull(transferPointSetting.getTpsId());
        log.info("TransferPointSetting created");

        // Add to the tempSet
        tempSet.add(transferPointSetting);

        TransferPointSetting fetchTransferPointSetting = transferPointSettingRepository.findByTpsMerchantNoAndTpsLocation(transferPointSetting.getTpsMerchantNo(), transferPointSetting.getTpsLocation());
        Assert.assertNotNull(fetchTransferPointSetting);
        log.info("Fetched transferPointSetting info" + transferPointSetting.toString());

    }


    @Test
    public void test5findByTpsMerchantNo() {

        // Create the transferPointSetting
        TransferPointSetting transferPointSetting = TransferPointSettingFixture.standardTransferPointSetting();
        transferPointSettingRepository.save(transferPointSetting);
        Assert.assertNotNull(transferPointSetting.getTpsId());
        log.info("TransferPointSetting created");

        // Add to the tempSet
        tempSet.add(transferPointSetting);

        List<TransferPointSetting> fetchTransferPointSetting = transferPointSettingRepository.findByTpsMerchantNo(transferPointSetting.getTpsMerchantNo());
        Assert.assertNotNull(fetchTransferPointSetting);
        log.info("Fetched transferPointSetting info" + transferPointSetting.toString());

    }



    @After
    public void tearDown() {

        for(TransferPointSetting transferPointSetting: tempSet) {

            transferPointSettingRepository.delete(transferPointSetting);

        }
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
        return new Sort(Sort.Direction.ASC, "tpsName");
    }


}
