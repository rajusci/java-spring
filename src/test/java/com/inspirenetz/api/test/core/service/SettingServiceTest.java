package com.inspirenetz.api.test.core.service;

import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import com.inspirenetz.api.core.domain.Setting;
import com.inspirenetz.api.core.service.SettingService;
import com.inspirenetz.api.rest.exception.InspireNetzException;
import com.inspirenetz.api.test.config.ApplicationTestConfig;
import com.inspirenetz.api.test.config.ApplicationTestConfig;
import com.inspirenetz.api.test.config.PersistenceTestConfig;
import com.inspirenetz.api.test.config.ServiceTestConfig;
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

import java.util.List;
import java.util.Set;

/**
 * Created by sandheepgr on 30/4/14.
 */
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes  = {ApplicationTestConfig.class, PersistenceTestConfig.class, ServiceTestConfig.class})
public class SettingServiceTest {


    private static Logger log = LoggerFactory.getLogger(SettingServiceTest.class);

    @Autowired
    private SettingService settingService;

    @Before
    public void setUp() {}



    @Test
    public void testFindBySetId() throws Exception {

        // Create the prodcut
        Setting setting = SettingFixture.standardSetting();
        setting = settingService.saveSetting(setting);

        Setting searchSetting = settingService.findBySetId(setting.getSetId());
        Assert.assertNotNull(searchSetting);
        log.info("Setting Information" + searchSetting.toString());
    }


    @Test
    public void testFindBySetName() throws Exception {

        // Create the prodcut
        Setting setting = SettingFixture.standardSetting();
        setting = settingService.saveSetting(setting);

        Setting searchSetting = settingService.findBySetName(setting.getSetName());
        Assert.assertNotNull(searchSetting);
        log.info("Setting Information" + searchSetting.toString());
    }



    @Test
    public void testFindBySetNameLike() throws InspireNetzException {

        // Create the setting
        Setting setting = SettingFixture.standardSetting();
        settingService.saveSetting(setting);
        Assert.assertNotNull(setting.getSetId());
        log.info("Setting created");

        // Check the setting name
        Page<Setting> settings = settingService.findBySetNameLike("%new%",constructPageSpecification(0));
        Assert.assertTrue(settings.hasContent());
        Set<Setting> settingSet = Sets.newHashSet((Iterable<Setting>) settings);
        log.info("setting list "+settingSet.toString());


    }



    @Test
    public void testFindAll() throws InspireNetzException {

        // Create the setting
        Setting setting = SettingFixture.standardSetting();
        settingService.saveSetting(setting);
        Assert.assertNotNull(setting.getSetId());
        log.info("Setting created");

        // Check the setting name
        Page<Setting> settings = settingService.listSettings(constructPageSpecification(0));
        Assert.assertTrue(settings.hasContent());
        Set<Setting> settingSet = Sets.newHashSet((Iterable<Setting>) settings);
        log.info("setting list "+settingSet.toString());


    }



    @Test
    public void testIsDuplicateSettingExisting() throws InspireNetzException {

        // Save the setting
        Setting setting = SettingFixture.standardSetting();
        settingService.saveSetting(setting);

        // New Setting
        Setting newSetting = SettingFixture.standardSetting();
        boolean exists = settingService.isDuplicateSettingExisting(newSetting);
        Assert.assertTrue(exists);
        log.info("setting exists");

    }


    @Test
    public void searchSettings() throws InspireNetzException {

        // Save the setting
        Setting setting = SettingFixture.standardSetting();
        settingService.saveSetting(setting);

        // Get the settings
        Page<Setting> settingPage =  settingService.searchSettings("0","0",constructPageSpecification(0));
        Assert.assertNotNull(settingPage);
        Assert.assertTrue(settingPage.hasContent());
        List<Setting> settingList = Lists.newArrayList((Iterable<Setting>) settingPage);
        log.info("Setting details "+settingList.toString());


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
        return new Sort(Sort.Direction.ASC, "setId");
    }


    @After
    public void tearDown() throws Exception {

        Set<Setting> settings = SettingFixture.standardSettings();

        for(Setting setting: settings) {

            Setting delSetting = settingService.findBySetName(setting.getSetName());

            if ( delSetting != null ) {
                settingService.deleteSetting(delSetting.getSetId());
            }

        }
    }

}
