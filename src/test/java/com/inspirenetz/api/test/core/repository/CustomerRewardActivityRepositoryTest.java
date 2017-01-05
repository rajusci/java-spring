package com.inspirenetz.api.test.core.repository;

import com.inspirenetz.api.core.domain.CustomerRewardActivity;
import com.inspirenetz.api.core.repository.CustomerRewardActivityRepository;
import com.inspirenetz.api.test.config.ApplicationTestConfig;
import com.inspirenetz.api.test.config.PersistenceTestConfig;
import com.inspirenetz.api.test.core.fixture.CustomerRewardActivityFixture;
import org.junit.Assert;
import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Test;
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
import java.util.Set;

/**
 * Created by saneeshci on 29/9/14.
 */
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes  = {ApplicationTestConfig.class, PersistenceTestConfig.class})
public class CustomerRewardActivityRepositoryTest {

    private static Logger log = LoggerFactory.getLogger(CustomerRewardActivityRepositoryTest.class);

    Set<CustomerRewardActivity> tempSet = new HashSet<>(0);

    @Autowired
    private CustomerRewardActivityRepository customerRewardActivityRepository;

    @Before
    public void setup() {}

    @Test
    public void test1Create(){


        CustomerRewardActivity customerRewardActivity = customerRewardActivityRepository.save(CustomerRewardActivityFixture.standardCustomerRewardActivity());
        log.info(customerRewardActivity.toString());
        Assert.assertNotNull(customerRewardActivity.getCraId());

        // Add to tempSet
        tempSet.add(customerRewardActivity);

    }

    @Test
    public void test2Update() {

        CustomerRewardActivity customerRewardActivity = CustomerRewardActivityFixture.standardCustomerRewardActivity();
        customerRewardActivity = customerRewardActivityRepository.save(customerRewardActivity);
        log.info("Original role access right " + customerRewardActivity.toString());

        // Add to tempSet
        tempSet.add(customerRewardActivity);

        CustomerRewardActivity updateCustomerRewardActivity = CustomerRewardActivityFixture.updatedStandCustomerRewardActivity(customerRewardActivity);
        updateCustomerRewardActivity = customerRewardActivityRepository.save(updateCustomerRewardActivity);
        log.info("Updated CustomerRewardActivity "+ updateCustomerRewardActivity.toString());

    }

    @Test
    public void test3findByCraId() {

        // Get the standard
        CustomerRewardActivity customerRewardActivity = CustomerRewardActivityFixture.standardCustomerRewardActivity();
        customerRewardActivity = customerRewardActivityRepository.save(customerRewardActivity);
        log.info("Original Security Settings  " + customerRewardActivity.toString());

        // Add to tempSet
        tempSet.add(customerRewardActivity);

        Assert.assertNotNull(customerRewardActivity.getCraId());

        CustomerRewardActivity customerRewardActivityById = customerRewardActivityRepository.findByCraId(customerRewardActivity.getCraId());
        Assert.assertNotNull(customerRewardActivityById);
        log.info("Fetched CustomerRewardActivityById info" + customerRewardActivityById.toString());


    }

    @Test
    public void test4findByCraCustomerNo() {

        // Get the standard
        CustomerRewardActivity customerRewardActivity = CustomerRewardActivityFixture.standardCustomerRewardActivity();
        customerRewardActivity = customerRewardActivityRepository.save(customerRewardActivity);
        log.info("Original Security Settings  " + customerRewardActivity.toString());

        // Add to tempSet
        tempSet.add(customerRewardActivity);

        Assert.assertNotNull(customerRewardActivity.getCraId());

        Page<CustomerRewardActivity> customerRewardActivityPage = customerRewardActivityRepository.findByCraCustomerNo(customerRewardActivity.getCraCustomerNo(),constructPageSpecification(0));
        Assert.assertNotNull(customerRewardActivityPage);
        log.info("Fetched CustomerRewardActivityById info" + customerRewardActivityPage.toString());


    }

    @Test
    public void test5findByCraCustomerNoAndCraType() {

        // Get the standard
        CustomerRewardActivity customerRewardActivity = CustomerRewardActivityFixture.standardCustomerRewardActivity();
        customerRewardActivity = customerRewardActivityRepository.save(customerRewardActivity);
        log.info("Original Security Settings  " + customerRewardActivity.toString());

        // Add to tempSet
        tempSet.add(customerRewardActivity);

        Assert.assertNotNull(customerRewardActivity.getCraId());

        Page<CustomerRewardActivity> customerRewardActivityPage = customerRewardActivityRepository.findByCraCustomerNoAndCraType(customerRewardActivity.getCraCustomerNo(),customerRewardActivity.getCraType(),constructPageSpecification(0));
        Assert.assertNotNull(customerRewardActivityPage);
        log.info("Fetched CustomerRewardActivityById info" + customerRewardActivityPage.toString());


    }

    @org.junit.After
    public void tearDown() {

        for ( CustomerRewardActivity customerRewardActivity : tempSet ) {

            customerRewardActivityRepository.delete(customerRewardActivity);

        }
    }
    private Pageable constructPageSpecification(int pageIndex) {
        Pageable pageSpecification = new PageRequest(pageIndex, 10, sortByLastNameAsc());
        return pageSpecification;
    }

    /**
     * Returns a Sort object which sorts persons in ascending order by using the last name.
     * @return
     */
    private Sort sortByLastNameAsc() {
        return new Sort(Sort.Direction.ASC, "craType");
    }
}
