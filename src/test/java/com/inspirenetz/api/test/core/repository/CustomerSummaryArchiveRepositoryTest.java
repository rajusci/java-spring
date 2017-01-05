package com.inspirenetz.api.test.core.repository;

import com.inspirenetz.api.core.domain.CustomerSummaryArchive;
import com.inspirenetz.api.core.repository.CustomerSummaryArchiveRepository;
import com.inspirenetz.api.test.config.ApplicationTestConfig;
import com.inspirenetz.api.test.config.PersistenceTestConfig;
import com.inspirenetz.api.test.core.fixture.CustomerRewardExpiryFixture;
import com.inspirenetz.api.test.core.fixture.CustomerSummaryArchiveFixture;
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
 * Created by sandheepgr on 20/5/14.
 */
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes  = {ApplicationTestConfig.class, PersistenceTestConfig.class})
public class CustomerSummaryArchiveRepositoryTest {


    // Create the logger
    private static Logger log = LoggerFactory.getLogger(CustomerSummaryArchiveRepositoryTest.class);

    @Autowired
    private CustomerSummaryArchiveRepository customerSummaryArchiveRepository;

    private Set<CustomerSummaryArchive> tempSet = new HashSet<>(0);




    @Test
    public void testCreate() {

        CustomerSummaryArchive customerSummaryArchive = CustomerSummaryArchiveFixture.standardCustomerSummaryArchive();
        customerSummaryArchive = customerSummaryArchiveRepository.save(customerSummaryArchive);

        // Add to tempSet
        tempSet.add(customerSummaryArchive);

        Assert.assertNotNull(customerSummaryArchive);
    }



    @Test
    public void testUpdate() {

        CustomerSummaryArchive customerSummaryArchive = CustomerSummaryArchiveFixture.standardCustomerSummaryArchive();
        customerSummaryArchive = customerSummaryArchiveRepository.save(customerSummaryArchive);
        Assert.assertNotNull(customerSummaryArchive);
        log.info("CustomerSummaryArchive Original:" + customerSummaryArchive.toString());

        // Add to tempSet
        tempSet.add(customerSummaryArchive);


        // Update the details
        CustomerSummaryArchive updatedDetails = CustomerSummaryArchiveFixture.updatedStandardCustomerSummaryArchive(customerSummaryArchive);
        updatedDetails = customerSummaryArchiveRepository.save(updatedDetails);
        Assert.assertNotNull(updatedDetails);
        log.info("CustomerSummaryArchive Updated: " + updatedDetails.toString() );


    }

    @Test
    public void testFindByCsaMerchantNoAndCsaLoyaltyId() throws Exception {

        // Get the standardCustomerSummaryArchive
        Set<CustomerSummaryArchive> customerSummaryArchiveSet = CustomerSummaryArchiveFixture.standardCustomerSummaryArchives();
        customerSummaryArchiveRepository.save(customerSummaryArchiveSet);

        // Add to tempSet
        tempSet.addAll(customerSummaryArchiveSet);


        // Get the standardCustomerSummaryArchive
        CustomerSummaryArchive  customerSummaryArchive = CustomerSummaryArchiveFixture.standardCustomerSummaryArchive();


        List<CustomerSummaryArchive> customerSummaryArchiveList = customerSummaryArchiveRepository.findByCsaMerchantNoAndCsaLoyaltyId(customerSummaryArchive.getCsaMerchantNo(),customerSummaryArchive.getCsaLoyaltyId());
        Assert.assertTrue(!customerSummaryArchiveList.isEmpty());
        log.info("CustomerSummaryArchive List: " + customerSummaryArchiveList.toString());

    }

    @Test
    public void testFindByCsaMerchantNoAndCsaLoyaltyIdAndCsaLocation() throws Exception {

        // Get the standardCustomerSummaryArchive
        Set<CustomerSummaryArchive> customerSummaryArchiveSet = CustomerSummaryArchiveFixture.standardCustomerSummaryArchives();
        customerSummaryArchiveRepository.save(customerSummaryArchiveSet);

        // Add to tempSet
        tempSet.addAll(customerSummaryArchiveSet);

        // Get the standardCustomerSummaryArchive
        CustomerSummaryArchive  customerSummaryArchive = CustomerSummaryArchiveFixture.standardCustomerSummaryArchive();


        List<CustomerSummaryArchive> customerSummaryArchiveList = customerSummaryArchiveRepository.findByCsaMerchantNoAndCsaLoyaltyIdAndCsaLocation(customerSummaryArchive.getCsaMerchantNo(), customerSummaryArchive.getCsaLoyaltyId(), customerSummaryArchive.getCsaLocation());
        Assert.assertTrue(!customerSummaryArchiveList.isEmpty());
        log.info("CustomerSummaryArchive List: " + customerSummaryArchiveList.toString());

    }

    @Test
    public void testFindByCsaMerchantNoAndCsaLoyaltyIdAndCsaLocationAndCsaPeriodYyyy() throws Exception {

        // Get the standardCustomerSummaryArchive
        Set<CustomerSummaryArchive> customerSummaryArchiveSet = CustomerSummaryArchiveFixture.standardCustomerSummaryArchives();
        customerSummaryArchiveRepository.save(customerSummaryArchiveSet);

        // Add to tempSet
        tempSet.addAll(customerSummaryArchiveSet);

        // Get the standardCustomerSummaryArchive
        CustomerSummaryArchive  customerSummaryArchive = CustomerSummaryArchiveFixture.standardCustomerSummaryArchive();


        List<CustomerSummaryArchive> customerSummaryArchiveList = customerSummaryArchiveRepository.findByCsaMerchantNoAndCsaLoyaltyIdAndCsaLocationAndCsaPeriodYyyy(customerSummaryArchive.getCsaMerchantNo(), customerSummaryArchive.getCsaLoyaltyId(), customerSummaryArchive.getCsaLocation(), customerSummaryArchive.getCsaPeriodYyyy());
        Assert.assertTrue(!customerSummaryArchiveList.isEmpty());
        log.info("CustomerSummaryArchive List: " + customerSummaryArchiveList.toString());

    }

    @Test
    public void testFindByCsaMerchantNoAndCsaLoyaltyIdAndCsaLocationAndCsaPeriodYyyyAndCsaPeriodQq() throws Exception {

        // Get the standardCustomerSummaryArchive
        Set<CustomerSummaryArchive> customerSummaryArchiveSet = CustomerSummaryArchiveFixture.standardCustomerSummaryArchives();
        customerSummaryArchiveRepository.save(customerSummaryArchiveSet);

        // Add to tempSet
        tempSet.addAll(customerSummaryArchiveSet);

        // Get the standardCustomerSummaryArchive
        CustomerSummaryArchive  customerSummaryArchive = CustomerSummaryArchiveFixture.standardCustomerSummaryArchive();


        List<CustomerSummaryArchive> customerSummaryArchiveList = customerSummaryArchiveRepository.findByCsaMerchantNoAndCsaLoyaltyIdAndCsaLocationAndCsaPeriodYyyyAndCsaPeriodQq(customerSummaryArchive.getCsaMerchantNo(), customerSummaryArchive.getCsaLoyaltyId(), customerSummaryArchive.getCsaLocation(), customerSummaryArchive.getCsaPeriodYyyy(), customerSummaryArchive.getCsaPeriodQq());
        Assert.assertTrue(!customerSummaryArchiveList.isEmpty());
        log.info("CustomerSummaryArchive List: " + customerSummaryArchiveList.toString());

    }

    @Test
    public void testFindByCsaMerchantNoAndCsaLoyaltyIdAndCsaLocationAndCsaPeriodYyyyAndCsaPeriodQqAndCsaPeriodMm() throws Exception {

        // Get the standardCustomerSummaryArchive
        Set<CustomerSummaryArchive> customerSummaryArchiveSet = CustomerSummaryArchiveFixture.standardCustomerSummaryArchives();
        customerSummaryArchiveRepository.save(customerSummaryArchiveSet);


        // Add to tempSet
        tempSet.addAll(customerSummaryArchiveSet);

        // Get the standardCustomerSummaryArchive
        CustomerSummaryArchive  customerSummaryArchive = CustomerSummaryArchiveFixture.standardCustomerSummaryArchive();


        CustomerSummaryArchive searchCustomerSummaryArchive = customerSummaryArchiveRepository.findByCsaMerchantNoAndCsaLoyaltyIdAndCsaLocationAndCsaPeriodYyyyAndCsaPeriodQqAndCsaPeriodMm(customerSummaryArchive.getCsaMerchantNo(), customerSummaryArchive.getCsaLoyaltyId(), customerSummaryArchive.getCsaLocation(), customerSummaryArchive.getCsaPeriodYyyy(),customerSummaryArchive.getCsaPeriodQq(),customerSummaryArchive.getCsaPeriodMm());
        Assert.assertNotNull(searchCustomerSummaryArchive);
        log.info("CustomerSummaryArchive : " + searchCustomerSummaryArchive.toString());

    }



    @After
    public void tearDown() throws Exception {



        for( CustomerSummaryArchive customerSummaryArchive : tempSet) {

            customerSummaryArchiveRepository.delete(customerSummaryArchive);

        }
    }
}
