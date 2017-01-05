package com.inspirenetz.api.test.core.repository;

import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import com.inspirenetz.api.core.domain.Setting;
import com.inspirenetz.api.core.repository.SettingRepository;
import com.inspirenetz.api.test.config.ApplicationTestConfig;
import com.inspirenetz.api.test.config.PersistenceTestConfig;
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
 * Created by sandheepgr on 17/5/14.
 */
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes  = {ApplicationTestConfig.class, PersistenceTestConfig.class})
public class SettingRepositoryTest {


    //  Create the logger
    private static Logger log = LoggerFactory.getLogger(SettingRepositoryTest.class);

    @Autowired
    private SettingRepository settingRepository;


    @Before
    public void setUp() throws Exception {


    }




    @Test
    public void test1Create(){


        Setting setting = settingRepository.save(SettingFixture.standardSetting());
        log.info(setting.toString());
        Assert.assertNotNull(setting.getSetId());

    }

    @Test
    public void test2Update() {

        Setting setting = SettingFixture.standardSetting();
        setting = settingRepository.save(setting);
        log.info("Original Setting " + setting.toString());

        Setting updatedSetting = SettingFixture.updatedStandardSetting(setting);
        updatedSetting = settingRepository.save(updatedSetting);
        log.info("Updated Setting "+ updatedSetting.toString());

    }


    @Test
    public void testFindBySetId() throws Exception {

        // Create the prodcut
        Setting setting = SettingFixture.standardSetting();
        setting = settingRepository.save(setting);

        Setting searchSetting = settingRepository.findBySetId(setting.getSetId());
        Assert.assertNotNull(searchSetting);
        log.info("Setting Information" + searchSetting.toString());
    }


    @Test
    public void testFindBySetName() throws Exception {

        // Create the prodcut
        Setting setting = SettingFixture.standardSetting();
        setting = settingRepository.save(setting);

        Setting searchSetting = settingRepository.findBySetName(setting.getSetName());
        Assert.assertNotNull(searchSetting);
        log.info("Setting Information" + searchSetting.toString());
    }



    @Test
    public void testFindBySetNameLike() {

        // Create the setting
        Setting setting = SettingFixture.standardSetting();
        settingRepository.save(setting);
        Assert.assertNotNull(setting.getSetId());
        log.info("Setting created");

        // Check the setting name
        Page<Setting> settings = settingRepository.findBySetNameLike("%new%", constructPageSpecification(0));
        Assert.assertTrue(settings.hasContent());
        Set<Setting> settingSet = Sets.newHashSet((Iterable<Setting>) settings);
        log.info("setting list "+settingSet.toString());


    }



    @Test
    public void testFindAll() {

        // Create the setting
        Setting setting = SettingFixture.standardSetting();
        settingRepository.save(setting);
        Assert.assertNotNull(setting.getSetId());
        log.info("Setting created");

        // Check the setting name
        Page<Setting> settings = settingRepository.findAll(constructPageSpecification(0));
        Assert.assertTrue(settings.hasContent());
        Set<Setting> settingSet = Sets.newHashSet((Iterable<Setting>) settings);
        log.info("setting list "+settingSet.toString());


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
        return new Sort(Sort.Direction.ASC, "setId");
    }

    @After
    public void tearDown() throws Exception {

        Set<Setting> settings = SettingFixture.standardSettings();

        for(Setting setting: settings) {

            Setting delSetting = settingRepository.findBySetName(setting.getSetName());

            if ( delSetting != null ) {
                settingRepository.delete(delSetting);
            }

        }
    }
}
