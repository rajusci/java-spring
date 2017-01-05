package com.inspirenetz.api.test.core.service;

import com.inspirenetz.api.core.domain.AccountArchive;
import com.inspirenetz.api.core.service.AccountArchiveService;
import com.inspirenetz.api.rest.exception.InspireNetzException;
import com.inspirenetz.api.test.config.*;
import com.inspirenetz.api.test.core.fixture.AccountArchiveFixture;
import com.inspirenetz.api.test.util.ControllerTestUtils;
import org.junit.*;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Created by sandheepgr on 30/4/14.
 */
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes  = {ApplicationTestConfig.class, PersistenceTestConfig.class, ServiceTestConfig.class, SecurityTestConfig.class, NotificationTestConfig.class})
public class AccountArchiveServiceTest {


    private static Logger log = LoggerFactory.getLogger(AccountArchiveServiceTest.class);

    @Autowired
    private AccountArchiveService accountArchiveService;

    UsernamePasswordAuthenticationToken principal;

    @Autowired
    @Qualifier("uds")
    protected UserDetailsService userDetailsService;

    Set<AccountArchive> tempSet = new HashSet<>(0);


    @Before
    public void setup() {

        // Set the principal
        principal = ControllerTestUtils.getPrincipal(ControllerTestUtils.TEST_MERCHANT_USER_LOGINID, userDetailsService);

        // Set the context
        SecurityContextHolder.getContext().setAuthentication(principal);

    }

    @Test
    public void test1Create(){


        AccountArchive accountArchive = accountArchiveService.saveAccountArchive(AccountArchiveFixture.standardAccountArchive());
        log.info(accountArchive.toString());

        // Add to the tempSet
        tempSet.add(accountArchive);

        Assert.assertNotNull(accountArchive.getAarId());

    }

   


    @Test
    public void test2FindByAarId() {

        // Create the accountArchive
        AccountArchive accountArchive = AccountArchiveFixture.standardAccountArchive();
        accountArchiveService.saveAccountArchive(accountArchive);
        Assert.assertNotNull(accountArchive.getAarId());
        log.info("AccountArchive created");

        // Add to the tempSet
        tempSet.add(accountArchive);

        // Get the setting
        AccountArchive accountArchive1 = accountArchiveService.findByAarId(accountArchive.getAarId());
        Assert.assertNotNull(accountArchive1);;
        log.info("Account Bundling Setting " + accountArchive);


    }


    @Test
    public void test3IsDuplicateAccountArchiveExisting() {

        // Create the accountArchive
        AccountArchive accountArchive = AccountArchiveFixture.standardAccountArchive();
        accountArchiveService.saveAccountArchive(accountArchive);
        Assert.assertNotNull(accountArchive.getAarId());
        log.info("AccountArchive created");

        // Add to the tempSet
        tempSet.add(accountArchive);


        // Get the setting
        AccountArchive accountArchive1 = AccountArchiveFixture.standardAccountArchive();

        // Check if duplicate is existing
        boolean isExists = accountArchiveService.isDuplicateAccountArchiveExisting(accountArchive1);
        Assert.assertTrue(isExists);

        // Log the information
        log.info("Duplicate entry exists");


    }


    @Test
    public void test4DeleteAccountArchive() {

        // Create the accountArchive
        AccountArchive accountArchive = AccountArchiveFixture.standardAccountArchive();
        accountArchiveService.saveAccountArchive(accountArchive);
        Assert.assertNotNull(accountArchive.getAarId());
        log.info("AccountArchive created");

        // Add to the tempSEt
        tempSet.add(accountArchive);

        // Delete the id
        accountArchiveService.deleteAccountArchive(accountArchive.getAarId());

        // Now get the information
        AccountArchive searchAccountArchive = accountArchiveService.findByAarId(accountArchive.getAarId());
        Assert.assertNull(searchAccountArchive);


        // If the deletiong was successful, then remove from the
        tempSet.remove(accountArchive);


    }



    @After
    public void tearDown() throws InspireNetzException {

        for(AccountArchive accountArchive: tempSet) {

            accountArchiveService.deleteAccountArchive(accountArchive.getAarId());

        }
    }


    /**
     * Returns a new object which specifies the the wanted result page.
     * @param pageIndex The index of the wanted result page
     * @return
     */
    private Pageable constructPageSpecification(int pageIndex) {
        Pageable pageSpecification = new PageRequest(pageIndex, 10);
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
