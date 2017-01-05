package com.inspirenetz.api.test.core.service;

import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import com.inspirenetz.api.core.domain.CustomerSummaryArchive;
import com.inspirenetz.api.core.repository.CustomerSummaryArchiveRepository;
import com.inspirenetz.api.core.service.CustomerSummaryArchiveService;
import com.inspirenetz.api.test.config.ApplicationTestConfig;
import com.inspirenetz.api.test.config.PersistenceTestConfig;
import com.inspirenetz.api.test.config.ServiceTestConfig;
import com.inspirenetz.api.test.core.fixture.CustomerSummaryArchiveFixture;
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
 * Created by sandheepgr on 30/4/14.
 */
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes  = {ApplicationTestConfig.class, PersistenceTestConfig.class, ServiceTestConfig.class})
public class CustomerSummaryArchiveServiceTest {


    private static Logger log = LoggerFactory.getLogger(CustomerSummaryArchiveServiceTest.class);

    @Autowired
    private CustomerSummaryArchiveService customerSummaryArchiveService;

    private Set<CustomerSummaryArchive> tempSet = new HashSet<>(0);



    @Test
    public void testCreate() {

        CustomerSummaryArchive customerSummaryArchive = CustomerSummaryArchiveFixture.standardCustomerSummaryArchive();
        customerSummaryArchive = customerSummaryArchiveService.saveCustomerSummaryArchive(customerSummaryArchive);

        // Add to tempSet
        tempSet.add(customerSummaryArchive);

        Assert.assertNotNull(customerSummaryArchive);
    }



    @Test
    public void testUpdate() {

        CustomerSummaryArchive customerSummaryArchive = CustomerSummaryArchiveFixture.standardCustomerSummaryArchive();
        customerSummaryArchive = customerSummaryArchiveService.saveCustomerSummaryArchive(customerSummaryArchive);
        Assert.assertNotNull(customerSummaryArchive);
        log.info("CustomerSummaryArchive Original:" + customerSummaryArchive.toString());

        // Add to tempSet
        tempSet.add(customerSummaryArchive);

        // Update the details
        CustomerSummaryArchive updatedDetails = CustomerSummaryArchiveFixture.updatedStandardCustomerSummaryArchive(customerSummaryArchive);
        updatedDetails = customerSummaryArchiveService.saveCustomerSummaryArchive(updatedDetails);
        Assert.assertNotNull(updatedDetails);
        log.info("CustomerSummaryArchive Updated: " + updatedDetails.toString() );


    }

    @Test
    public void testFindByCsaMerchantNoAndCsaLoyaltyId() throws Exception {

        // Get the standardCustomerSummaryArchive
        Set<CustomerSummaryArchive> customerSummaryArchiveSet = CustomerSummaryArchiveFixture.standardCustomerSummaryArchives();
        List<CustomerSummaryArchive> customerSummaryArchives = Lists.newArrayList((Iterable<CustomerSummaryArchive>) customerSummaryArchiveSet);
        customerSummaryArchiveService.saveAll(customerSummaryArchives);

        // Add to tempSet
        tempSet.addAll(customerSummaryArchiveSet);

        // Get the standardCustomerSummaryArchive
        CustomerSummaryArchive  customerSummaryArchive = CustomerSummaryArchiveFixture.standardCustomerSummaryArchive();


        List<CustomerSummaryArchive> customerSummaryArchiveList = customerSummaryArchiveService.findByCsaMerchantNoAndCsaLoyaltyId(customerSummaryArchive.getCsaMerchantNo(),customerSummaryArchive.getCsaLoyaltyId());
        Assert.assertTrue(!customerSummaryArchiveList.isEmpty());
        log.info("CustomerSummaryArchive List: " + customerSummaryArchiveList.toString());

    }

    @Test
    public void testFindByCsaMerchantNoAndCsaLoyaltyIdAndCsaLocation() throws Exception {

        // Get the standardCustomerSummaryArchive
        Set<CustomerSummaryArchive> customerSummaryArchiveSet = CustomerSummaryArchiveFixture.standardCustomerSummaryArchives();
        List<CustomerSummaryArchive> customerSummaryArchives = Lists.newArrayList((Iterable<CustomerSummaryArchive>) customerSummaryArchiveSet);
        customerSummaryArchiveService.saveAll(customerSummaryArchives);

        // Add to tempSet
        tempSet.addAll(customerSummaryArchiveSet);

        // Get the standardCustomerSummaryArchive
        CustomerSummaryArchive  customerSummaryArchive = CustomerSummaryArchiveFixture.standardCustomerSummaryArchive();


        List<CustomerSummaryArchive> customerSummaryArchiveList = customerSummaryArchiveService.findByCsaMerchantNoAndCsaLoyaltyIdAndCsaLocation(customerSummaryArchive.getCsaMerchantNo(), customerSummaryArchive.getCsaLoyaltyId(), customerSummaryArchive.getCsaLocation());
        Assert.assertTrue(!customerSummaryArchiveList.isEmpty());
        log.info("CustomerSummaryArchive List: " + customerSummaryArchiveList.toString());

    }

    @Test
    public void testFindByCsaMerchantNoAndCsaLoyaltyIdAndCsaLocationAndCsaPeriodYyyy() throws Exception {

        // Get the standardCustomerSummaryArchive
        Set<CustomerSummaryArchive> customerSummaryArchiveSet = CustomerSummaryArchiveFixture.standardCustomerSummaryArchives();
        List<CustomerSummaryArchive> customerSummaryArchives = Lists.newArrayList((Iterable<CustomerSummaryArchive>) customerSummaryArchiveSet);
        customerSummaryArchiveService.saveAll(customerSummaryArchives);

        // Add to tempSet
        tempSet.addAll(customerSummaryArchiveSet);

        // Get the standardCustomerSummaryArchive
        CustomerSummaryArchive  customerSummaryArchive = CustomerSummaryArchiveFixture.standardCustomerSummaryArchive();


        List<CustomerSummaryArchive> customerSummaryArchiveList = customerSummaryArchiveService.findByCsaMerchantNoAndCsaLoyaltyIdAndCsaLocationAndCsaPeriodYyyy(customerSummaryArchive.getCsaMerchantNo(), customerSummaryArchive.getCsaLoyaltyId(), customerSummaryArchive.getCsaLocation(), customerSummaryArchive.getCsaPeriodYyyy());
        Assert.assertTrue(!customerSummaryArchiveList.isEmpty());
        log.info("CustomerSummaryArchive List: " + customerSummaryArchiveList.toString());

    }

    @Test
    public void testFindByCsaMerchantNoAndCsaLoyaltyIdAndCsaLocationAndCsaPeriodYyyyAndCsaPeriodQq() throws Exception {

        // Get the standardCustomerSummaryArchive
        Set<CustomerSummaryArchive> customerSummaryArchiveSet = CustomerSummaryArchiveFixture.standardCustomerSummaryArchives();
        List<CustomerSummaryArchive> customerSummaryArchives = Lists.newArrayList((Iterable<CustomerSummaryArchive>) customerSummaryArchiveSet);
        customerSummaryArchiveService.saveAll(customerSummaryArchives);

        // Add to tempSet
        tempSet.addAll(customerSummaryArchiveSet);

        // Get the standardCustomerSummaryArchive
        CustomerSummaryArchive  customerSummaryArchive = CustomerSummaryArchiveFixture.standardCustomerSummaryArchive();


        List<CustomerSummaryArchive> customerSummaryArchiveList = customerSummaryArchiveService.findByCsaMerchantNoAndCsaLoyaltyIdAndCsaLocationAndCsaPeriodYyyyAndCsaPeriodQq(customerSummaryArchive.getCsaMerchantNo(), customerSummaryArchive.getCsaLoyaltyId(), customerSummaryArchive.getCsaLocation(), customerSummaryArchive.getCsaPeriodYyyy(), customerSummaryArchive.getCsaPeriodQq());
        Assert.assertTrue(!customerSummaryArchiveList.isEmpty());
        log.info("CustomerSummaryArchive List: " + customerSummaryArchiveList.toString());

    }

    @Test
    public void testFindByCsaMerchantNoAndCsaLoyaltyIdAndCsaLocationAndCsaPeriodYyyyAndCsaPeriodQqAndCsaPeriodMm() throws Exception {

        // Get the standardCustomerSummaryArchive
        Set<CustomerSummaryArchive> customerSummaryArchiveSet = CustomerSummaryArchiveFixture.standardCustomerSummaryArchives();
        List<CustomerSummaryArchive> customerSummaryArchives = Lists.newArrayList((Iterable<CustomerSummaryArchive>) customerSummaryArchiveSet);
        customerSummaryArchiveService.saveAll(customerSummaryArchives);

        // Add to tempSet
        tempSet.addAll(customerSummaryArchiveSet);

        // Get the standardCustomerSummaryArchive
        CustomerSummaryArchive  customerSummaryArchive = CustomerSummaryArchiveFixture.standardCustomerSummaryArchive();


        CustomerSummaryArchive searchCustomerSummaryArchive = customerSummaryArchiveService.findByCsaMerchantNoAndCsaLoyaltyIdAndCsaLocationAndCsaPeriodYyyyAndCsaPeriodQqAndCsaPeriodMm(customerSummaryArchive.getCsaMerchantNo(), customerSummaryArchive.getCsaLoyaltyId(), customerSummaryArchive.getCsaLocation(), customerSummaryArchive.getCsaPeriodYyyy(),customerSummaryArchive.getCsaPeriodQq(),customerSummaryArchive.getCsaPeriodMm());
        Assert.assertNotNull(searchCustomerSummaryArchive);
        log.info("CustomerSummaryArchive : " + searchCustomerSummaryArchive.toString());

    }



    @After
    public void tearDown() throws Exception {



        for( CustomerSummaryArchive customerSummaryArchive : tempSet) {

            customerSummaryArchiveService.deleteCustomerSummaryArchive(customerSummaryArchive);

        }
    }
    
}
