package com.inspirenetz.api.test.core.service;

import com.google.common.collect.Sets;
import com.inspirenetz.api.core.domain.CustomerProfile;
import com.inspirenetz.api.core.repository.CustomerProfileRepository;
import com.inspirenetz.api.core.service.CustomerProfileService;
import com.inspirenetz.api.test.config.ApplicationTestConfig;
import com.inspirenetz.api.test.config.PersistenceTestConfig;
import com.inspirenetz.api.test.config.ServiceTestConfig;
import com.inspirenetz.api.test.core.fixture.CustomerProfileFixture;
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
import java.util.Set;

/**
 * Created by sandheepgr on 30/4/14.
 */
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes  = {ApplicationTestConfig.class, PersistenceTestConfig.class, ServiceTestConfig.class})
public class CustomerProfileServiceTest {


    private static Logger log = LoggerFactory.getLogger(CustomerProfileServiceTest.class);

    @Autowired
    private CustomerProfileService customerProfileService;

    @Autowired
    private CustomerProfileRepository customerProfileRepository;

    private Set<CustomerProfile> tempSet = new HashSet<>(0);

    @Before
    public void setUp() {}



    @Test
    public void test1FindByCspCustomerNo() {

        // Create the customerProfile
        CustomerProfile customerProfile = CustomerProfileFixture.standardCustomerProfile();
        customerProfileService.saveCustomerProfile(customerProfile);
        Assert.assertNotNull(customerProfile);
        log.info("CustomerProfile created");

        // Add to tempSEt
        tempSet.add(customerProfile);

        CustomerProfile fetchCustomerProfile = customerProfileService.findByCspCustomerNo(customerProfile.getCspCustomerNo());
        Assert.assertNotNull(fetchCustomerProfile);
        log.info("Fetched customerProfile info" + customerProfile.toString());

    }


    @Test
    public void test2SaveCustomerProfile() {


        // Create the customerProfile
        CustomerProfile customerProfile = CustomerProfileFixture.standardCustomerProfile();
        customerProfileService.saveCustomerProfile(customerProfile);


        // Add to tempSEt
        tempSet.add(customerProfile);

        Assert.assertNotNull(customerProfile);
        log.info("CustomerProfile created" + customerProfile.toString());


    }


    @Test
    public void test3DeleteCustomerProfile() {

        // Create the customerProfile
        CustomerProfile customerProfile = CustomerProfileFixture.standardCustomerProfile();
        customerProfileService.saveCustomerProfile(customerProfile);
        Assert.assertNotNull(customerProfile);
        log.info("CustomerProfile created" + customerProfile.toString());

        // Add to tempSEt
        tempSet.add(customerProfile);

        // Delete the customer profile entry
        customerProfileService.deleteCustomerProfile(customerProfile);

        // Find the customerProfile
        CustomerProfile searchProfile = customerProfileService.findByCspId(customerProfile.getCspId());
        Assert.assertNull(searchProfile);
        log.info("Customer Profile Deleted Successfully");



    }


    @Test
    public void test5FindByCspId() {

        // Create the customerProfile
        CustomerProfile customerProfile = CustomerProfileFixture.standardCustomerProfile();
        customerProfileService.saveCustomerProfile(customerProfile);
        Assert.assertNotNull(customerProfile);
        log.info("CustomerProfile created");

        // Add to tempSEt
        tempSet.add(customerProfile);

        CustomerProfile fetchCustomerProfile = customerProfileService.findByCspId(customerProfile.getCspId());
        Assert.assertNotNull(fetchCustomerProfile);
        log.info("Fetched customerProfile info" + customerProfile.toString());

    }




    @After
    public void tearDown() {


        for(CustomerProfile customerProfile: tempSet) {

            customerProfileService.deleteCustomerProfile(customerProfile);

        }

    }

}
