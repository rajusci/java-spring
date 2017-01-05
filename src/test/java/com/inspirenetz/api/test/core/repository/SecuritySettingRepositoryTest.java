package com.inspirenetz.api.test.core.repository;

import com.inspirenetz.api.core.domain.SecuritySetting;
import com.inspirenetz.api.core.repository.SecuritySettingRepository;
import com.inspirenetz.api.test.config.ApplicationTestConfig;
import com.inspirenetz.api.test.config.PersistenceTestConfig;
import com.inspirenetz.api.test.core.fixture.SecuritySettingFixture;
import org.junit.Assert;
import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Created by saneeshci on 29/9/14.
 */
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes  = {ApplicationTestConfig.class, PersistenceTestConfig.class})
public class SecuritySettingRepositoryTest {

    private static Logger log = LoggerFactory.getLogger(SecuritySettingRepositoryTest.class);

    Set<SecuritySetting> tempSet = new HashSet<>(0);

    @Autowired
    private SecuritySettingRepository securitySettingRepository;

    @Before
    public void setup() {}

    @Test
    public void test1Create(){


        SecuritySetting securitySetting = securitySettingRepository.save(SecuritySettingFixture.standardSecuritySetting());
        log.info(securitySetting.toString());
        Assert.assertNotNull(securitySetting.getSecId());

        // Add to tempSet
        tempSet.add(securitySetting);

    }

    @Test
    public void test2Update() {

        SecuritySetting securitySetting = SecuritySettingFixture.standardSecuritySetting();
        securitySetting = securitySettingRepository.save(securitySetting);
        log.info("Original role access right " + securitySetting.toString());

        // Add to tempSet
        tempSet.add(securitySetting);

        SecuritySetting updateSecuritySetting = SecuritySettingFixture.updatedStandSecuritySetting(securitySetting);
        updateSecuritySetting = securitySettingRepository.save(updateSecuritySetting);
        log.info("Updated SecuritySetting "+ updateSecuritySetting.toString());

    }

    @Test
    public void test3findBySecId() {

        // Get the standard
        SecuritySetting securitySetting = SecuritySettingFixture.standardSecuritySetting();
        securitySetting = securitySettingRepository.save(securitySetting);
        log.info("Original Security Settings  " + securitySetting.toString());

        // Add to tempSet
        tempSet.add(securitySetting);

        Assert.assertNotNull(securitySetting.getSecId());

        SecuritySetting securitySettingById = securitySettingRepository.findBySecId(securitySetting.getSecId());
        Assert.assertNotNull(securitySettingById);
        log.info("Fetched SecuritySettingById info" + securitySettingById.toString());


    }

    @org.junit.After
    public void tearDown() {

        for ( SecuritySetting securitySetting : tempSet ) {

            securitySettingRepository.delete(securitySetting);

        }
    }
}
