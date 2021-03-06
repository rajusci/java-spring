package com.inspirenetz.api.test.core.service;

import com.google.common.collect.Lists;
import com.inspirenetz.api.core.domain.CustomerProgramSummary;
import com.inspirenetz.api.core.domain.CustomerProgramSummary;
import com.inspirenetz.api.core.service.CustomerProgramSummaryService;
import com.inspirenetz.api.test.config.ApplicationTestConfig;
import com.inspirenetz.api.test.config.PersistenceTestConfig;
import com.inspirenetz.api.test.config.ServiceTestConfig;
import com.inspirenetz.api.test.core.fixture.CustomerProgramSummaryFixture;
import com.inspirenetz.api.test.core.fixture.CustomerProgramSummaryFixture;
import org.junit.After;
import org.junit.Assert;
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
 * Created by sandheepgr on 30/4/14.
 */
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes  = {ApplicationTestConfig.class, PersistenceTestConfig.class, ServiceTestConfig.class})
public class CustomerProgramSummaryServiceTest {


    private static Logger log = LoggerFactory.getLogger(CustomerProgramSummaryServiceTest.class);

    @Autowired
    private CustomerProgramSummaryService customerProgramSummaryService;

    Set<CustomerProgramSummary> tempSet  = new HashSet<>(0);


    @Test
    public void testCreate() {

        CustomerProgramSummary customerProgramSummary = CustomerProgramSummaryFixture.standardCustomerProgramSummary();
        customerProgramSummary = customerProgramSummaryService.saveCustomerProgramSummary(customerProgramSummary);

        // Add the item to the tempSEt
        tempSet.add(customerProgramSummary);

        Assert.assertNotNull(customerProgramSummary);
    }



    @Test
    public void testUpdate() {

        CustomerProgramSummary customerProgramSummary = CustomerProgramSummaryFixture.standardCustomerProgramSummary();
        customerProgramSummary = customerProgramSummaryService.saveCustomerProgramSummary(customerProgramSummary);
        Assert.assertNotNull(customerProgramSummary);
        log.info("CustomerProgramSummary Original:" + customerProgramSummary.toString());

        // Add the item to the tempSEt
        tempSet.add(customerProgramSummary);

        // Update the details
        CustomerProgramSummary updatedDetails = CustomerProgramSummaryFixture.updatedStandardCustomerProgramSummary(customerProgramSummary);
        updatedDetails = customerProgramSummaryService.saveCustomerProgramSummary(updatedDetails);
        Assert.assertNotNull(updatedDetails);
        log.info("CustomerProgramSummary Updated: " + updatedDetails.toString() );


    }

    @Test
    public void testFindByCpsMerchantNoAndCpsLoyaltyId() throws Exception {

        // Get the standardCustomerProgramSummary
        Set<CustomerProgramSummary> customerProgramSummarySet = CustomerProgramSummaryFixture.standardCustomerProgramSummaries();
        List<CustomerProgramSummary> customerProgramSummaries = Lists.newArrayList((Iterable<CustomerProgramSummary>)customerProgramSummarySet);
        customerProgramSummaryService.saveAll(customerProgramSummaries);

        // Add the item to the tempSEt
        tempSet.addAll(customerProgramSummarySet);

        // Get the standardCustomerProgramSummary
        CustomerProgramSummary  customerProgramSummary = CustomerProgramSummaryFixture.standardCustomerProgramSummary();


        List<CustomerProgramSummary> customerProgramSummaryList = customerProgramSummaryService.findByCpsMerchantNoAndCpsLoyaltyId(customerProgramSummary.getCpsMerchantNo(),customerProgramSummary.getCpsLoyaltyId());
        Assert.assertTrue(!customerProgramSummaryList.isEmpty());
        log.info("CustomerProgramSummary List: " + customerProgramSummaryList.toString());

    }

    @Test
    public void testFindByCpsMerchantNoAndCpsLoyaltyIdAndCpsProgramId() throws Exception {

        // Get the standardCustomerProgramSummary
        Set<CustomerProgramSummary> customerProgramSummarySet = CustomerProgramSummaryFixture.standardCustomerProgramSummaries();
        List<CustomerProgramSummary> customerProgramSummaries = Lists.newArrayList((Iterable<CustomerProgramSummary>)customerProgramSummarySet);
        customerProgramSummaryService.saveAll(customerProgramSummaries);

        // Add the item to the tempSEt
        tempSet.addAll(customerProgramSummarySet);

        // Get the standard CustomerProgramSummary object
        CustomerProgramSummary customerProgramSummary = CustomerProgramSummaryFixture.standardCustomerProgramSummary();

        // Get the standardCustomerProgramSummary
        CustomerProgramSummary  searchCustomerProgramSummary = customerProgramSummaryService.findByCpsMerchantNoAndCpsLoyaltyIdAndCpsProgramId(customerProgramSummary.getCpsMerchantNo(),customerProgramSummary.getCpsLoyaltyId(),customerProgramSummary.getCpsProgramId());
        Assert.assertNotNull(searchCustomerProgramSummary);
        log.info("CustomerProgramSummary Information :" + searchCustomerProgramSummary.toString());


    }



    @Test
    public void testFindByCpsId() {

        CustomerProgramSummary customerProgramSummary = CustomerProgramSummaryFixture.standardCustomerProgramSummary();
        customerProgramSummary = customerProgramSummaryService.saveCustomerProgramSummary(customerProgramSummary);

        // Add the item to the tempSEt
        tempSet.add(customerProgramSummary);

        // Get the CustomerProgramSummary
        CustomerProgramSummary customerProgramSummary1 = customerProgramSummaryService.findByCpsId(customerProgramSummary.getCpsId());
        Assert.assertNotNull(customerProgramSummary1);
        log.info("Customer Program Summary" + customerProgramSummary1.toString());

    }


    @After
    public void tearDown() throws Exception {

        for( CustomerProgramSummary customerProgramSummary : tempSet) {

            customerProgramSummaryService.deleteCustomerProgramSummary(customerProgramSummary);

        }
    }
    
}
