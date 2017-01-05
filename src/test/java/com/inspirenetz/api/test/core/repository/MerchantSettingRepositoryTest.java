package com.inspirenetz.api.test.core.repository;

import com.google.common.collect.Sets;
import com.inspirenetz.api.core.domain.MerchantSetting;
import com.inspirenetz.api.core.repository.MerchantSettingRepository;
import com.inspirenetz.api.test.config.ApplicationTestConfig;
import com.inspirenetz.api.test.config.PersistenceTestConfig;
import com.inspirenetz.api.test.core.fixture.MerchantSettingFixture;
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

import java.util.List;
import java.util.Set;

/**
 * Created by sandheepgr on 17/2/14.
 */
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes  = {ApplicationTestConfig.class, PersistenceTestConfig.class})
public class MerchantSettingRepositoryTest {

    private static Logger log = LoggerFactory.getLogger(MerchantSettingRepositoryTest.class);

    @Autowired
    private MerchantSettingRepository merchantSettingRepository;


    @Before
    public void setup() {}

    @Test
    public void test1Create(){


        MerchantSetting merchantSetting = merchantSettingRepository.save(MerchantSettingFixture.standardMerchantSetting());
        log.info(merchantSetting.toString());

    }

    @Test
    public void test2Update() {

        MerchantSetting merchantSetting = MerchantSettingFixture.standardMerchantSetting();
        merchantSetting = merchantSettingRepository.save(merchantSetting);
        log.info("Original MerchantSetting " + merchantSetting.toString());

        MerchantSetting updatedMerchantSetting = MerchantSettingFixture.updatedStandardMerchantSetting(merchantSetting);
        updatedMerchantSetting = merchantSettingRepository.save(updatedMerchantSetting);
        log.info("Updated MerchantSetting "+ updatedMerchantSetting.toString());

    }

    @Test
    public void test4FindByMesMerchantNoAndMesCode() {

        // Create the merchantSetting
        MerchantSetting merchantSetting = MerchantSettingFixture.standardMerchantSetting();
        merchantSettingRepository.save(merchantSetting);
        Assert.assertNotNull(merchantSetting);
        log.info("MerchantSetting created");

        MerchantSetting fetchMerchantSetting = merchantSettingRepository.findByMesMerchantNoAndMesSettingId(merchantSetting.getMesMerchantNo(), merchantSetting.getMesSettingId());
        Assert.assertNotNull(fetchMerchantSetting);
        log.info("Fetched merchantSetting info" + merchantSetting.toString());

    }

    @Test
    public void test5FindByMesMerchantNo() {

        // Create the merchantSetting
        MerchantSetting merchantSetting = MerchantSettingFixture.standardMerchantSetting();
        merchantSettingRepository.save(merchantSetting);
        log.info("MerchantSetting created");

        // Check the merchantSetting name
        List<MerchantSetting> merchantSettingList = merchantSettingRepository.findByMesMerchantNo(merchantSetting.getMesMerchantNo());
        Assert.assertNotNull(merchantSettingList);
        log.info("merchantSetting list "+merchantSettingList.toString());


    }


    @After
    public void tearDown() {

        Set<MerchantSetting> merchantSettings = MerchantSettingFixture.standardMerchantSettings();

        for(MerchantSetting merchantSetting: merchantSettings) {

            MerchantSetting delMerchantSetting = merchantSettingRepository.findByMesMerchantNoAndMesSettingId(merchantSetting.getMesMerchantNo(), merchantSetting.getMesSettingId());

            if ( delMerchantSetting != null ) {
                merchantSettingRepository.delete(delMerchantSetting);
            }

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
        return new Sort(Sort.Direction.ASC, "brnName");
    }


}
