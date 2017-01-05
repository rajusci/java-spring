package com.inspirenetz.api.test.core.repository;

import com.inspirenetz.api.core.domain.AccountArchive;
import com.inspirenetz.api.core.repository.AccountArchiveRepository;
import com.inspirenetz.api.test.config.ApplicationTestConfig;
import com.inspirenetz.api.test.config.PersistenceTestConfig;
import com.inspirenetz.api.test.core.fixture.AccountArchiveFixture;
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
public class AccountArchiveRepositoryTest {

    private static Logger log = LoggerFactory.getLogger(AccountArchiveRepositoryTest.class);

    @Autowired
    private AccountArchiveRepository accountArchiveRepository;

    Set<AccountArchive> tempSet = new HashSet<>(0);


    @Before
    public void setup() {}

    @Test
    public void test1Create(){


        AccountArchive accountArchive = accountArchiveRepository.save(AccountArchiveFixture.standardAccountArchive());
        log.info(accountArchive.toString());

        // Add to the tempSet
        tempSet.add(accountArchive);

        Assert.assertNotNull(accountArchive.getAarId());

    }



    @Test
    public void test2FindByAarId() {

        // Create the accountArchive
        AccountArchive accountArchive = AccountArchiveFixture.standardAccountArchive();
        accountArchiveRepository.save(accountArchive);
        Assert.assertNotNull(accountArchive.getAarId());
        log.info("AccountArchive created");

        // Add to the tempSet
        tempSet.add(accountArchive);

        // Get the setting
        AccountArchive accountArchive1 = accountArchiveRepository.findByAarId(accountArchive.getAarId());
        Assert.assertNotNull(accountArchive1);
        log.info("Account Bundling Setting " + accountArchive);


    }

    @Test
    public void test3FindByAarMerchantNoAndAarOldLoyaltyIdAndAarNewLoyaltyId() {

        // Create the accountArchive
        AccountArchive accountArchive = AccountArchiveFixture.standardAccountArchive();
        accountArchiveRepository.save(accountArchive);
        Assert.assertNotNull(accountArchive.getAarId());
        log.info("AccountArchive created");

        // Add to the tempSet
        tempSet.add(accountArchive);

        AccountArchive fetchAccountArchive = accountArchiveRepository.findByAarMerchantNoAndAarOldLoyaltyIdAndAarNewLoyaltyId(accountArchive.getAarMerchantNo(), accountArchive.getAarOldLoyaltyId(),accountArchive.getAarNewLoyaltyId());
        Assert.assertNotNull(fetchAccountArchive);
        log.info("Fetched accountArchive info" + accountArchive.toString());

    }




    @After
    public void tearDown() {

        for(AccountArchive accountArchive: tempSet) {

            accountArchiveRepository.delete(accountArchive);

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
        return new Sort(Sort.Direction.ASC, "aarName");
    }


}
