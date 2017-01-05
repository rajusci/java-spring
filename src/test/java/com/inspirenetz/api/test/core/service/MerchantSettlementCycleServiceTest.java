package com.inspirenetz.api.test.core.service;

import com.inspirenetz.api.core.domain.Customer;
import com.inspirenetz.api.core.domain.MerchantSettlementCycle;
import com.inspirenetz.api.core.domain.RedemptionMerchant;
import com.inspirenetz.api.core.service.CustomerService;
import com.inspirenetz.api.core.service.MerchantSettlementCycleService;
import com.inspirenetz.api.core.service.RedemptionMerchantService;
import com.inspirenetz.api.rest.exception.InspireNetzException;
import com.inspirenetz.api.test.config.ApplicationTestConfig;
import com.inspirenetz.api.test.config.PersistenceTestConfig;
import com.inspirenetz.api.test.config.SecurityTestConfig;
import com.inspirenetz.api.test.config.ServiceTestConfig;
import com.inspirenetz.api.test.core.fixture.MerchantSettlementCycleFixture;
import com.inspirenetz.api.test.util.ControllerTestUtils;
import org.junit.*;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.sql.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Created by saneesh on 21/10/15.
 */
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes  = {ApplicationTestConfig.class, PersistenceTestConfig.class, ServiceTestConfig.class, SecurityTestConfig.class})
public class MerchantSettlementCycleServiceTest {


    private static Logger log = LoggerFactory.getLogger(MerchantSettlementCycleServiceTest.class);

    @Autowired
    private MerchantSettlementCycleService merchantSettlementCycleService;

    @Autowired
    private CustomerService customerService;

    UsernamePasswordAuthenticationToken principal;

    @Autowired
    RedemptionMerchantService redemptionMerchantService;

    @Autowired
    @Qualifier("uds")
    protected UserDetailsService userDetailsService;

    Set<MerchantSettlementCycle> tempSet = new HashSet<>(0);

    Set<Customer> customerSet = new HashSet<>(0);


    @Before
    public void setUp() {

        // Set the principal
        principal = ControllerTestUtils.getPrincipal(ControllerTestUtils.TEST_MERCHANT_ADMIN_LOGINID, userDetailsService);

        // Set the context
        SecurityContextHolder.getContext().setAuthentication(principal);
    }


    @Test
    public void test1Create(){

        MerchantSettlementCycle merchantSettlementCycle = merchantSettlementCycleService.saveMerchantSettlementCycle(MerchantSettlementCycleFixture.standardMerchantSettlementCycle());
        log.info(merchantSettlementCycle.toString());

        // Add the items
        tempSet.add(merchantSettlementCycle);

        Assert.assertNotNull(merchantSettlementCycle.getMscId());

    }

    @Test
    public void test2Update() throws InspireNetzException {

        MerchantSettlementCycle merchantSettlementCycle = merchantSettlementCycleService.saveMerchantSettlementCycle(MerchantSettlementCycleFixture.standardMerchantSettlementCycle());
        merchantSettlementCycle=merchantSettlementCycleService.findByMscId(merchantSettlementCycle.getMscId());
        tempSet.add(merchantSettlementCycle);
        log.info("merchantSettlementCycle select data"+merchantSettlementCycle.getMscId());

        MerchantSettlementCycle updatedMerchantSettlementCycle = MerchantSettlementCycleFixture.updatedStandardMerchantSettlementCycle(merchantSettlementCycle);
        log.info("Updated MerchantSettlementCycle "+ updatedMerchantSettlementCycle.toString());
        updatedMerchantSettlementCycle = merchantSettlementCycleService.validateAndSaveMerchantSettlementCycle(updatedMerchantSettlementCycle);
//        tempSet.add(merchantSettlementCycle);
        log.info("Updated MerchantSettlementCycle "+ updatedMerchantSettlementCycle.toString());

    }

    @Test
    public void test3FindByMscId() throws InspireNetzException {

        MerchantSettlementCycleFixture merchantSettlementCycleFixture=new MerchantSettlementCycleFixture();

        MerchantSettlementCycle merchantSettlementCycle = merchantSettlementCycleFixture.standardMerchantSettlementCycle();
        merchantSettlementCycle = merchantSettlementCycleService.saveMerchantSettlementCycle(merchantSettlementCycle);
        log.info("Original MerchantSettlementCycles " + merchantSettlementCycle.toString());

        // Add the items
        tempSet.add(merchantSettlementCycle);

        MerchantSettlementCycle searchRequest = merchantSettlementCycleService.findByMscId(merchantSettlementCycle.getMscId());
        Assert.assertNotNull(searchRequest);
        Assert.assertTrue(merchantSettlementCycle.getMscId().longValue() == searchRequest.getMscId().longValue());



    }
    @Test
    public void test3SearchMerchantSettlementCycle() throws InspireNetzException {

        MerchantSettlementCycleFixture merchantSettlementCycleFixture=new MerchantSettlementCycleFixture();

        MerchantSettlementCycle merchantSettlementCycle = merchantSettlementCycleFixture.standardMerchantSettlementCycle();
        merchantSettlementCycle = merchantSettlementCycleService.saveMerchantSettlementCycle(merchantSettlementCycle);
        log.info("Original MerchantSettlementCycles " + merchantSettlementCycle.toString());

        // Add the items
        tempSet.add(merchantSettlementCycle);

        List<MerchantSettlementCycle> searchRequest = merchantSettlementCycleService.searchMerchantSettlementCycle(merchantSettlementCycle.getMscMerchantNo(), merchantSettlementCycle.getMscRedemptionMerchant(), merchantSettlementCycle.getMscMerchantLocation(), Date.valueOf("2015-10-02"),Date.valueOf("2015-10-09"));
        Assert.assertNotNull(searchRequest);


    }

    @Test
    public void generateCycles() throws InspireNetzException {

        RedemptionMerchant redemptionMerchant = redemptionMerchantService.findByRemNo(1012l);

        merchantSettlementCycleService.generateMerchantSettlementCycle(redemptionMerchant,1l);
    }




    @After
    public void tearDown() throws InspireNetzException {
        for(MerchantSettlementCycle merchantSettlementCycle: tempSet) {

            merchantSettlementCycleService.deleteMerchantSettlementCycle(merchantSettlementCycle.getMscId());

        }

        for(Customer customer:customerSet){


            customerService.deleteCustomer(customer.getCusCustomerNo());
        }


    }


}
