package com.inspirenetz.api.test.core.repository;

import com.google.common.collect.Sets;
import com.inspirenetz.api.core.domain.AccountBundlingSetting;
import com.inspirenetz.api.core.repository.AccountBundlingSettingRepository;
import com.inspirenetz.api.test.config.ApplicationTestConfig;
import com.inspirenetz.api.test.config.PersistenceTestConfig;
import com.inspirenetz.api.test.core.fixture.AccountBundlingSettingFixture;
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
 * Created by sandheepgr on 17/2/14.
 */
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes  = {ApplicationTestConfig.class, PersistenceTestConfig.class})
public class AccountBundlingSettingRepositoryTest {

    private static Logger log = LoggerFactory.getLogger(AccountBundlingSettingRepositoryTest.class);

    @Autowired
    private AccountBundlingSettingRepository accountBundlingSettingRepository;

    Set<AccountBundlingSetting> tempSet = new HashSet<>(0);


    @Before
    public void setup() {}

    @Test
    public void test1Create(){


        AccountBundlingSetting accountBundlingSetting = accountBundlingSettingRepository.save(AccountBundlingSettingFixture.standardAccountBundlingSetting());
        log.info(accountBundlingSetting.toString());

        // Add to the tempSet
        tempSet.add(accountBundlingSetting);

        Assert.assertNotNull(accountBundlingSetting.getAbsId());

    }

    @Test
    public void test2Update() {

        AccountBundlingSetting accountBundlingSetting = AccountBundlingSettingFixture.standardAccountBundlingSetting();
        accountBundlingSetting = accountBundlingSettingRepository.save(accountBundlingSetting);
        log.info("Original AccountBundlingSetting " + accountBundlingSetting.toString());

        // Add to the tempSet
        tempSet.add(accountBundlingSetting);

        AccountBundlingSetting updatedAccountBundlingSetting = AccountBundlingSettingFixture.updatedStandardAccountBundlingSetting(accountBundlingSetting);
        updatedAccountBundlingSetting = accountBundlingSettingRepository.save(updatedAccountBundlingSetting);
        log.info("Updated AccountBundlingSetting "+ updatedAccountBundlingSetting.toString());

    }



    @Test
    public void test3FindByAbsId() {

        // Create the accountBundlingSetting
        AccountBundlingSetting accountBundlingSetting = AccountBundlingSettingFixture.standardAccountBundlingSetting();
        accountBundlingSettingRepository.save(accountBundlingSetting);
        Assert.assertNotNull(accountBundlingSetting.getAbsId());
        log.info("AccountBundlingSetting created");

        // Add to the tempSet
        tempSet.add(accountBundlingSetting);

        // Get the setting
        AccountBundlingSetting accountBundlingSetting1 = accountBundlingSettingRepository.findByAbsId(accountBundlingSetting.getAbsId());
        Assert.assertNotNull(accountBundlingSetting1);;
        log.info("Account Bundling Setting " + accountBundlingSetting);


    }

    @Test
    public void test4FindByAbsMerchantNoAndAbsLocation() {

        // Create the accountBundlingSetting
        AccountBundlingSetting accountBundlingSetting = AccountBundlingSettingFixture.standardAccountBundlingSetting();
        accountBundlingSettingRepository.save(accountBundlingSetting);
        Assert.assertNotNull(accountBundlingSetting.getAbsId());
        log.info("AccountBundlingSetting created");

        // Add to the tempSet
        tempSet.add(accountBundlingSetting);

        AccountBundlingSetting fetchAccountBundlingSetting = accountBundlingSettingRepository.findByAbsMerchantNoAndAbsLocation(accountBundlingSetting.getAbsMerchantNo(), accountBundlingSetting.getAbsLocation());
        Assert.assertNotNull(fetchAccountBundlingSetting);
        log.info("Fetched accountBundlingSetting info" + accountBundlingSetting.toString());

    }


    @Test
    public void test5findByAbsMerchantNo() {

        // Create the accountBundlingSetting
        AccountBundlingSetting accountBundlingSetting = AccountBundlingSettingFixture.standardAccountBundlingSetting();
        accountBundlingSettingRepository.save(accountBundlingSetting);
        Assert.assertNotNull(accountBundlingSetting.getAbsId());
        log.info("AccountBundlingSetting created");

        // Add to the tempSet
        tempSet.add(accountBundlingSetting);

        List<AccountBundlingSetting> fetchAccountBundlingSetting = accountBundlingSettingRepository.findByAbsMerchantNo(accountBundlingSetting.getAbsMerchantNo());
        Assert.assertNotNull(fetchAccountBundlingSetting);
        log.info("Fetched accountBundlingSetting info" + accountBundlingSetting.toString());

    }



    @After
    public void tearDown() {

        for(AccountBundlingSetting accountBundlingSetting: tempSet) {

            accountBundlingSettingRepository.delete(accountBundlingSetting);

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
        return new Sort(Sort.Direction.ASC, "absName");
    }


}
