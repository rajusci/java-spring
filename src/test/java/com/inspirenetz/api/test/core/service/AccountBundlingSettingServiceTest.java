package com.inspirenetz.api.test.core.service;

import com.google.common.collect.Sets;
import com.inspirenetz.api.core.domain.AccountBundlingSetting;
import com.inspirenetz.api.core.domain.Customer;
import com.inspirenetz.api.core.domain.LinkedLoyalty;
import com.inspirenetz.api.core.repository.AccountBundlingSettingRepository;
import com.inspirenetz.api.core.service.AccountBundlingSettingService;
import com.inspirenetz.api.core.service.CustomerService;
import com.inspirenetz.api.core.service.LinkedLoyaltyService;
import com.inspirenetz.api.rest.exception.InspireNetzException;
import com.inspirenetz.api.test.config.*;
import com.inspirenetz.api.test.core.fixture.AccountBundlingSettingFixture;
import com.inspirenetz.api.test.core.fixture.CustomerFixture;
import com.inspirenetz.api.test.core.fixture.LinkedLoyaltyFixture;
import com.inspirenetz.api.test.util.ControllerTestUtils;
import org.junit.*;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.Page;
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
public class AccountBundlingSettingServiceTest {


    private static Logger log = LoggerFactory.getLogger(AccountBundlingSettingServiceTest.class);

    @Autowired
    private AccountBundlingSettingService accountBundlingSettingService;

    UsernamePasswordAuthenticationToken principal;

    @Autowired
    CustomerService customerService;

    @Autowired
    LinkedLoyaltyService linkedLoyaltyService;

    @Autowired
    @Qualifier("uds")
    protected UserDetailsService userDetailsService;

    Set<AccountBundlingSetting> tempSet = new HashSet<>(0);

    Set<Customer> tempSet1 = new HashSet<>(0);

    Set<LinkedLoyalty> tempSet3 = new HashSet<>(0);

    @Before
    public void setup() {

        // Set the principal
        principal = ControllerTestUtils.getPrincipal(ControllerTestUtils.TEST_MERCHANT_USER_LOGINID, userDetailsService);

        // Set the context
        SecurityContextHolder.getContext().setAuthentication(principal);

    }

    @Test
    public void test1Create(){


        AccountBundlingSetting accountBundlingSetting = accountBundlingSettingService.saveAccountBundlingSetting(AccountBundlingSettingFixture.standardAccountBundlingSetting());
        log.info(accountBundlingSetting.toString());

        // Add to the tempSet
        tempSet.add(accountBundlingSetting);

        Assert.assertNotNull(accountBundlingSetting.getAbsId());

    }

    @Test
    public void test2Update() {

        AccountBundlingSetting accountBundlingSetting = AccountBundlingSettingFixture.standardAccountBundlingSetting();
        accountBundlingSetting = accountBundlingSettingService.saveAccountBundlingSetting(accountBundlingSetting);
        log.info("Original AccountBundlingSetting " + accountBundlingSetting.toString());

        // Add to the tempSet
        tempSet.add(accountBundlingSetting);

        AccountBundlingSetting updatedAccountBundlingSetting = AccountBundlingSettingFixture.updatedStandardAccountBundlingSetting(accountBundlingSetting);
        updatedAccountBundlingSetting = accountBundlingSettingService.saveAccountBundlingSetting(updatedAccountBundlingSetting);
        log.info("Updated AccountBundlingSetting " + updatedAccountBundlingSetting.toString());

    }



    @Test
    public void test3FindByAbsId() {

        // Create the accountBundlingSetting
        AccountBundlingSetting accountBundlingSetting = AccountBundlingSettingFixture.standardAccountBundlingSetting();
        accountBundlingSettingService.saveAccountBundlingSetting(accountBundlingSetting);
        Assert.assertNotNull(accountBundlingSetting.getAbsId());
        log.info("AccountBundlingSetting created");

        // Add to the tempSet
        tempSet.add(accountBundlingSetting);

        // Get the setting
        AccountBundlingSetting accountBundlingSetting1 = accountBundlingSettingService.findByAbsId(accountBundlingSetting.getAbsId());
        Assert.assertNotNull(accountBundlingSetting1);;
        log.info("Account Bundling Setting " + accountBundlingSetting);


    }

    @Test
    public void test4FindByAbsMerchantNoAndAbsLocation() {

        // Create the accountBundlingSetting
        AccountBundlingSetting accountBundlingSetting = AccountBundlingSettingFixture.standardAccountBundlingSetting();
        accountBundlingSettingService.saveAccountBundlingSetting(accountBundlingSetting);
        Assert.assertNotNull(accountBundlingSetting.getAbsId());
        log.info("AccountBundlingSetting created");

        // Add to the tempSet
        tempSet.add(accountBundlingSetting);

        AccountBundlingSetting fetchAccountBundlingSetting = accountBundlingSettingService.findByAbsMerchantNoAndAbsLocation(accountBundlingSetting.getAbsMerchantNo(), accountBundlingSetting.getAbsLocation());
        Assert.assertNotNull(fetchAccountBundlingSetting);
        log.info("Fetched accountBundlingSetting info" + accountBundlingSetting.toString());

    }



    @Test
    public void test5findByAbsMerchantNo() {

        // Create the accountBundlingSetting
        AccountBundlingSetting accountBundlingSetting = AccountBundlingSettingFixture.standardAccountBundlingSetting();
        accountBundlingSettingService.saveAccountBundlingSetting(accountBundlingSetting);
        Assert.assertNotNull(accountBundlingSetting.getAbsId());
        log.info("AccountBundlingSetting created");

        // Add to the tempSet
        tempSet.add(accountBundlingSetting);

        List<AccountBundlingSetting> fetchAccountBundlingSetting = accountBundlingSettingService.findByAbsMerchantNo(accountBundlingSetting.getAbsMerchantNo());
        Assert.assertNotNull(fetchAccountBundlingSetting);
        log.info("Fetched accountBundlingSetting info" + accountBundlingSetting.toString());

    }


    @Test
    public void test6SaveAccountBundlingSettingForUser() throws InspireNetzException {


        AccountBundlingSetting accountBundlingSetting = accountBundlingSettingService.saveAccountBundlingSettingForUser(AccountBundlingSettingFixture.standardAccountBundlingSetting());
        log.info(accountBundlingSetting.toString());

        // Add to the tempSet
        tempSet.add(accountBundlingSetting);

        Assert.assertNotNull(accountBundlingSetting.getAbsId());

    }


    @Test
    public void test7GetDefaultAccountBundlingSetting() {

        // Create the accountBundlingSetting
        AccountBundlingSetting accountBundlingSetting = AccountBundlingSettingFixture.standardAccountBundlingSetting();
        accountBundlingSetting.setAbsLocation(0L);
        accountBundlingSettingService.saveAccountBundlingSetting(accountBundlingSetting);
        Assert.assertNotNull(accountBundlingSetting.getAbsId());
        log.info("AccountBundlingSetting created");

        // Add to the tempSet
        tempSet.add(accountBundlingSetting);

        AccountBundlingSetting fetchAccountBundlingSetting = accountBundlingSettingService.getDefaultAccountBundlingSetting(accountBundlingSetting.getAbsMerchantNo());
        Assert.assertNotNull(fetchAccountBundlingSetting);
        log.info("Fetched accountBundlingSetting info" + accountBundlingSetting.toString());

    }


    @Test
    public void test7checkPrimaryAccountIsReachedLinkedLimit() throws InspireNetzException {


        //save the customer
        Customer customer = CustomerFixture.standardCustomer();
        customerService.saveCustomer(customer);
        Assert.assertNotNull(customer.getCusCustomerNo());
        log.info("Customer created");

        tempSet1.add(customer);

        // Create the accountBundlingSetting
        AccountBundlingSetting accountBundlingSetting = AccountBundlingSettingFixture.standardAccountBundlingSetting();
        accountBundlingSetting.setAbsLocation(0L);
        accountBundlingSettingService.saveAccountBundlingSetting(accountBundlingSetting);
        Assert.assertNotNull(accountBundlingSetting.getAbsId());
        log.info("AccountBundlingSetting created");

        //add linking configuration
        LinkedLoyalty linkedLoyalty = linkedLoyaltyService.saveLinkedLoyalty(LinkedLoyaltyFixture.standardLinkedLoyalty(customer));

        tempSet3.add(linkedLoyalty);

        log.info(linkedLoyalty.toString());
        Assert.assertNotNull(linkedLoyalty.getLilId());

        // Add to the tempSet
        tempSet.add(accountBundlingSetting);

        boolean checkPrimaryReachedLimit = accountBundlingSettingService.checkPrimaryAccountIsReachedLinkedLimit(customer.getCusLoyaltyId(),customer.getCusMerchantNo());

        log.info("Fetched accountBundlingSetting info" + checkPrimaryReachedLimit);

    }


    @After
    public void tearDown() throws InspireNetzException {

        for(AccountBundlingSetting accountBundlingSetting: tempSet) {

            accountBundlingSettingService.deleteAccountBundlingSetting(accountBundlingSetting.getAbsId());

        }

        for(Customer customer: tempSet1) {

            customerService.deleteCustomer(customer.getCusCustomerNo());

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
