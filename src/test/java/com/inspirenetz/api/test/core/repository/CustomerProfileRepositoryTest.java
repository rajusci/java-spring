package com.inspirenetz.api.test.core.repository;

import com.google.common.collect.Sets;
import com.inspirenetz.api.core.domain.CustomerProfile;
import com.inspirenetz.api.core.domain.CustomerProfilePK;
import com.inspirenetz.api.core.repository.CustomerProfileRepository;
import com.inspirenetz.api.test.config.ApplicationTestConfig;
import com.inspirenetz.api.test.config.PersistenceTestConfig;
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
 * Created by sandheepgr on 17/2/14.
 */
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes  = {ApplicationTestConfig.class, PersistenceTestConfig.class})
public class CustomerProfileRepositoryTest {

    private static Logger log = LoggerFactory.getLogger(CustomerProfileRepositoryTest.class);

    @Autowired
    private CustomerProfileRepository customerProfileRepository;

    private Set<CustomerProfile> tempSet = new HashSet<>(0);


    @Before
    public void setup() {}

    @Test
    public void test1Create(){

        CustomerProfile customerProfile = customerProfileRepository.save(CustomerProfileFixture.standardCustomerProfile());

        // Add to tempSEt
        tempSet.add(customerProfile);

        log.info(customerProfile.toString());
        Assert.assertNotNull(customerProfile);

    }


    @Test
    public void test2Update() {

        CustomerProfile customerProfile = CustomerProfileFixture.standardCustomerProfile();
        customerProfile = customerProfileRepository.save(customerProfile);
        log.info("Original CustomerProfile " + customerProfile.toString());

        // Add to tempSEt
        tempSet.add(customerProfile);

        CustomerProfile updatedCustomerProfile = CustomerProfileFixture.updatedStandardCustomerProfile(customerProfile);
        updatedCustomerProfile = customerProfileRepository.save(updatedCustomerProfile);
        log.info("Updated CustomerProfile "+ updatedCustomerProfile.toString());

    }


    @Test
    public void test3FindByCspId() {


        CustomerProfile customerProfile = CustomerProfileFixture.standardCustomerProfile();
        customerProfile = customerProfileRepository.save(customerProfile);
        log.info("Original CustomerProfile " + customerProfile.toString());

        // Add to tempSEt
        tempSet.add(customerProfile);

        CustomerProfile searchProfile = customerProfileRepository.findByCspId(customerProfile.getCspId());
        Assert.assertNotNull(searchProfile);
        log.info("Searched customer " + searchProfile.toString());

    }

    @Test
    public void test4FindByCspCustomerNo() {

        CustomerProfile customerProfile = CustomerProfileFixture.standardCustomerProfile();
        customerProfile = customerProfileRepository.save(customerProfile);
        log.info("Original CustomerProfile " + customerProfile.toString());

        // Add to tempSEt
        tempSet.add(customerProfile);

        CustomerProfile searchProfile = customerProfileRepository.findByCspCustomerNo(customerProfile.getCspCustomerNo());
        Assert.assertNotNull(searchProfile);
        log.info("Searched customer " + searchProfile.toString());

    }


    @After
    public void tearDown() {

        for(CustomerProfile customerProfile: tempSet) {

           customerProfileRepository.delete(customerProfile);

        }
    }
}

