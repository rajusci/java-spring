package com.inspirenetz.api.test.core.repository;

import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import com.inspirenetz.api.core.domain.CustomerRewardExpiry;
import com.inspirenetz.api.core.repository.CustomerRewardExpiryRepository;
import com.inspirenetz.api.test.config.ApplicationTestConfig;
import com.inspirenetz.api.test.config.PersistenceTestConfig;
import com.inspirenetz.api.test.core.fixture.CustomerRewardExpiryFixture;
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
public class CustomerRewardExpiryRepositoryTest {

    private static Logger log = LoggerFactory.getLogger(CustomerRewardExpiryRepositoryTest.class);

    @Autowired
    CustomerRewardExpiryRepository customerRewardExpiryRepository;

    Set<CustomerRewardExpiry> tempSet = new HashSet<>(0);

    @Before
    public void setUp(){

    }

    @Test
    public void test1Save(){

        CustomerRewardExpiry customerRewardExpiry = customerRewardExpiryRepository.save(CustomerRewardExpiryFixture.standardRewardExpiry());
        log.info(customerRewardExpiry.toString());

        // Add to tempSet
        tempSet.add(customerRewardExpiry);

        Assert.assertNotNull(customerRewardExpiry.getCreLoyaltyId());

    }

    @Test
    public void test2Update() {

        CustomerRewardExpiry customerRewardExpiry = customerRewardExpiryRepository.save(CustomerRewardExpiryFixture.standardRewardExpiry());
        customerRewardExpiryRepository.save(customerRewardExpiry);
        log.info("Original data" +customerRewardExpiry.toString());

        // Add to tempSet
        tempSet.add(customerRewardExpiry);

        CustomerRewardExpiry updatedCustomerRewardExpiry = CustomerRewardExpiryFixture.updatedStandardRewardExpiry(customerRewardExpiry);
        customerRewardExpiryRepository.save(customerRewardExpiry);
        log.info("Updated data" + customerRewardExpiry.toString());


    }


    @Test
    public void test3FindByCreLoyaltyId() {

        Set<CustomerRewardExpiry> customerRewardExpirySet = CustomerRewardExpiryFixture.standardCustomerRewardExpirys();
        customerRewardExpiryRepository.save(customerRewardExpirySet);
        log.info("List saved");

        // Add to tempSet
        tempSet.addAll(customerRewardExpirySet);

        CustomerRewardExpiry customerRewardExpiry = CustomerRewardExpiryFixture.standardRewardExpiry();

        List<CustomerRewardExpiry> customerRewardExpiryList = customerRewardExpiryRepository.findByCreLoyaltyId(customerRewardExpiry.getCreLoyaltyId());
        log.info("customerrewardexpiry list" + customerRewardExpiryList.toString());

    }



    @Test
    public void test4FindByCreLoyaltyIdAndCreRewardCurrencyId() {

        Set<CustomerRewardExpiry> customerRewardExpirySet = CustomerRewardExpiryFixture.standardCustomerRewardExpirys();
        customerRewardExpiryRepository.save(customerRewardExpirySet);
        log.info("List saved");

        // Add to tempSet
        tempSet.addAll(customerRewardExpirySet);

        CustomerRewardExpiry customerRewardExpiry = CustomerRewardExpiryFixture.standardRewardExpiry();

        List<CustomerRewardExpiry> customerRewardExpiryList = customerRewardExpiryRepository.findByCreLoyaltyIdAndCreRewardCurrencyId(customerRewardExpiry.getCreLoyaltyId(),customerRewardExpiry.getCreMerchantNo());
        log.info("customerrewardexpiry list" + customerRewardExpiryList.toString());

    }



    @Test
    public void test5FindByCreMerchantNoAndCreLoyaltyIdAndCreRewardCurrencyId() {

        Set<CustomerRewardExpiry> customerRewardExpirySet = CustomerRewardExpiryFixture.standardCustomerRewardExpirys();
        customerRewardExpiryRepository.save(customerRewardExpirySet);
        log.info("List saved");

        // Add to tempSet
        tempSet.addAll(customerRewardExpirySet);

        CustomerRewardExpiry customerRewardExpiry = CustomerRewardExpiryFixture.standardRewardExpiry();

        List<CustomerRewardExpiry> customerRewardExpiryList = customerRewardExpiryRepository.findByCreMerchantNoAndCreLoyaltyIdAndCreRewardCurrencyId(customerRewardExpiry.getCreMerchantNo(),customerRewardExpiry.getCreLoyaltyId(),customerRewardExpiry.getCreRewardCurrencyId());
        log.info("customerrewardexpiry list" + customerRewardExpiryList.toString());

    }



    @Test
    public void test6FindByCreMerchantNoAndCreLoyaltyId() {

        Set<CustomerRewardExpiry> customerRewardExpirySet = CustomerRewardExpiryFixture.standardCustomerRewardExpirys();
        customerRewardExpiryRepository.save(customerRewardExpirySet);
        log.info("List saved");


        // Add to tempSet
        tempSet.addAll(customerRewardExpirySet);

        CustomerRewardExpiry customerRewardExpiry = CustomerRewardExpiryFixture.standardRewardExpiry();

        List<CustomerRewardExpiry> customerRewardExpiryList = customerRewardExpiryRepository.findByCreMerchantNoAndCreLoyaltyId(customerRewardExpiry.getCreMerchantNo(),customerRewardExpiry.getCreLoyaltyId());
        log.info("customerrewardexpiry list" + customerRewardExpiryList.toString());

    }


    @Test
    public void test7FindByCreMerchantNoAndCreLoyaltyIdAndCreRewardCurrencyIdAndCreExpiryDt() {

        Set<CustomerRewardExpiry> customerRewardExpirySet = CustomerRewardExpiryFixture.standardCustomerRewardExpirys();
        customerRewardExpiryRepository.save(customerRewardExpirySet);
        log.info("List saved");


        // Add to tempSet
        tempSet.addAll(customerRewardExpirySet);

        CustomerRewardExpiry customerRewardExpiry = CustomerRewardExpiryFixture.standardRewardExpiry();

        // Search CustomerRewardExpiry
        CustomerRewardExpiry searchCustomerRewardExpiry = customerRewardExpiryRepository.findByCreMerchantNoAndCreLoyaltyIdAndCreRewardCurrencyIdAndCreExpiryDt(customerRewardExpiry.getCreMerchantNo(),customerRewardExpiry.getCreLoyaltyId(),customerRewardExpiry.getCreRewardCurrencyId(),customerRewardExpiry.getCreExpiryDt());
        Assert.assertNotNull(searchCustomerRewardExpiry);
        log.info("CustomerRewardExpiry List " + customerRewardExpiry.toString());

    }


    @Test
    public void test8FindByCreId(){

        CustomerRewardExpiry customerRewardExpiry = customerRewardExpiryRepository.save(CustomerRewardExpiryFixture.standardRewardExpiry());
        log.info(customerRewardExpiry.toString());

        // Add to tempSet
        tempSet.add(customerRewardExpiry);

        CustomerRewardExpiry customerRewardExpiry1 = customerRewardExpiryRepository.findByCreId(customerRewardExpiry.getCreId());
        Assert.assertNotNull(customerRewardExpiry1);
        log.info("Customer Rewrd Expiry" + customerRewardExpiry1.toString());

    }


    @After
    public void tearDown() {


        for(CustomerRewardExpiry customerRewardExpiry: tempSet) {

           customerRewardExpiryRepository.delete(customerRewardExpiry);

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
        return new Sort(Sort.Direction.ASC, "creLoyaltyId");
    }


}
