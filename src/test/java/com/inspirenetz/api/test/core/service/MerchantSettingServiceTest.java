package com.inspirenetz.api.test.core.service;

import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import com.inspirenetz.api.core.domain.MerchantSetting;
import com.inspirenetz.api.core.domain.Setting;
import com.inspirenetz.api.core.service.MerchantSettingService;
import com.inspirenetz.api.core.service.SettingService;
import com.inspirenetz.api.rest.exception.InspireNetzException;
import com.inspirenetz.api.test.config.ApplicationTestConfig;
import com.inspirenetz.api.test.config.PersistenceTestConfig;
import com.inspirenetz.api.test.config.ServiceTestConfig;
import com.inspirenetz.api.test.core.fixture.MerchantSettingFixture;
import com.inspirenetz.api.test.core.fixture.SettingFixture;
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

import java.util.HashMap;
import java.util.List;
import java.util.Set;

/**
 * Created by sandheepgr on 30/4/14.
 */
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes  = {ApplicationTestConfig.class, PersistenceTestConfig.class, ServiceTestConfig.class})
public class MerchantSettingServiceTest {


    private static Logger log = LoggerFactory.getLogger(MerchantSettingServiceTest.class);

    @Autowired
    private MerchantSettingService merchantSettingService;

    @Autowired
    private SettingService settingService;

    @Before
    public void setUp() {}

    @Test
    public void test1Create()throws InspireNetzException {


        MerchantSetting merchantSetting = merchantSettingService.saveMerchantSetting(MerchantSettingFixture.standardMerchantSetting());
        log.info(merchantSetting.toString());

    }

    @Test
    public void test2Update() throws InspireNetzException {

        MerchantSetting merchantSetting = MerchantSettingFixture.standardMerchantSetting();
        merchantSetting = merchantSettingService.saveMerchantSetting(merchantSetting);
        log.info("Original MerchantSetting " + merchantSetting.toString());

        MerchantSetting updatedMerchantSetting = MerchantSettingFixture.updatedStandardMerchantSetting(merchantSetting);
        updatedMerchantSetting = merchantSettingService.saveMerchantSetting(updatedMerchantSetting);
        log.info("Updated MerchantSetting "+ updatedMerchantSetting.toString());

    }

    @Test
    public void test4FindByMesMerchantNoAndMesCode() throws InspireNetzException {

        // Create the merchantSetting
        MerchantSetting merchantSetting = MerchantSettingFixture.standardMerchantSetting();
        merchantSettingService.saveMerchantSetting(merchantSetting);
        Assert.assertNotNull(merchantSetting);
        log.info("MerchantSetting created");

        MerchantSetting fetchMerchantSetting = merchantSettingService.findByMesMerchantNoAndMesSettingId(merchantSetting.getMesMerchantNo(), merchantSetting.getMesSettingId());
        Assert.assertNotNull(fetchMerchantSetting);
        log.info("Fetched merchantSetting info" + merchantSetting.toString());

    }

    @Test
    public void test5FindByMesMerchantNo() throws InspireNetzException {

        // Create the merchantSetting
        MerchantSetting merchantSetting = MerchantSettingFixture.standardMerchantSetting();
        merchantSettingService.saveMerchantSetting(merchantSetting);
        log.info("MerchantSetting created");

        // Check the merchantSetting name
        List<MerchantSetting> merchantSettingList = merchantSettingService.findByMesMerchantNo(merchantSetting.getMesMerchantNo());
        Assert.assertNotNull(merchantSettingList);
        log.info("merchantSetting list "+merchantSettingList.toString());


    }

    @Test
    public void test6GetSettingAsMap() throws InspireNetzException {

       // Create the Setting
        Setting setting = SettingFixture.standardSetting();
        setting = settingService.saveSetting(setting);

        // Save the MerchantSetting
        // Create the merchantSetting
        MerchantSetting merchantSetting = MerchantSettingFixture.standardMerchantSetting();
        merchantSetting.setMesSettingId(setting.getSetId());
        merchantSettingService.saveMerchantSetting(merchantSetting);
        log.info("MerchantSetting created");


        // Get the HashMap
        HashMap<Long,String> uarMap =  merchantSettingService.getSettingAsMap(merchantSetting.getMesMerchantNo());
        Assert.assertTrue(!uarMap.isEmpty());
        log.info("MerchantSEtting Map " + uarMap.toString());

    }

    /**
     * Returns a new object which specifies the the wanted result page.
     * @param pageIndex The index of the wanted result page
     * @return
     */
    private Pageable constructPageSpecifimesion(int pageIndex) {
        Pageable pageSpecifimesion = new PageRequest(pageIndex, 10, sortByLastNameAsc());
        return pageSpecifimesion;
    }

    /**
     * Returns a Sort object which sorts persons in ascending order by using the last name.
     * @return
     */
    private Sort sortByLastNameAsc() {
        return new Sort(Sort.Direction.ASC, "mesProductNo");
    }


    @After
    public void tearDown() throws Exception {

        Set<MerchantSetting> merchantSettings = MerchantSettingFixture.standardMerchantSettings();

        for(MerchantSetting merchantSetting: merchantSettings) {

            MerchantSetting delMerchantSetting = merchantSettingService.findByMesMerchantNoAndMesSettingId(merchantSetting.getMesMerchantNo(), merchantSetting.getMesSettingId());

            if ( delMerchantSetting != null ) {
                merchantSettingService.deleteMerchantSetting(delMerchantSetting);
            }

        }
    }

}
