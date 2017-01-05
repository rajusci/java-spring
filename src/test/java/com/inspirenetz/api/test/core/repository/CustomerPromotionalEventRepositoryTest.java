package com.inspirenetz.api.test.core.repository;

import com.inspirenetz.api.core.dictionary.DrawType;
import com.inspirenetz.api.core.domain.CustomerPromotionalEvent;
import com.inspirenetz.api.core.repository.CustomerPromotionalEventRepository;
import com.inspirenetz.api.test.config.ApplicationTestConfig;
import com.inspirenetz.api.test.config.PersistenceTestConfig;
import com.inspirenetz.api.test.core.fixture.CustomerPromotionalEventFixture;
import org.junit.*;
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
 * Created by saneesh-ci on 25/6/15.
 */
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes  = {ApplicationTestConfig.class, PersistenceTestConfig.class})
public class CustomerPromotionalEventRepositoryTest {

    private static Logger log = LoggerFactory.getLogger(CustomerPromotionalEventRepositoryTest.class);

    @Autowired
    private CustomerPromotionalEventRepository CustomerPromotionalEventRepository;

    Set<CustomerPromotionalEvent> tempSet = new HashSet<>(0);

    @Before
    public void setup() {}

    @Test
    public void test1Create(){

        CustomerPromotionalEventFixture CustomerPromotionalEventFixture=new CustomerPromotionalEventFixture();

        CustomerPromotionalEvent CustomerPromotionalEvent = CustomerPromotionalEventRepository.save(CustomerPromotionalEventFixture.standardCustomerPromotionalEvent());

        // Add to the tempSet
        tempSet.add(CustomerPromotionalEvent);

        log.info(CustomerPromotionalEvent.toString());
        Assert.assertNotNull(CustomerPromotionalEvent.getCpeId());

    }

    @Test
    public void test2Update() {

        CustomerPromotionalEventFixture CustomerPromotionalEventFixture=new CustomerPromotionalEventFixture();

        CustomerPromotionalEvent CustomerPromotionalEvent = CustomerPromotionalEventFixture.standardCustomerPromotionalEvent();
        CustomerPromotionalEvent = CustomerPromotionalEventRepository.save(CustomerPromotionalEvent);
        log.info("Original CustomerPromotionalEvent " + CustomerPromotionalEvent.toString());

        // Add to the tempSet
        tempSet.add(CustomerPromotionalEvent);


        CustomerPromotionalEvent updatedCustomerPromotionalEvent = CustomerPromotionalEventFixture.updatedStandardCustomerPromotionalEvent(CustomerPromotionalEvent);
        updatedCustomerPromotionalEvent = CustomerPromotionalEventRepository.save(updatedCustomerPromotionalEvent);
        log.info("Updated CustomerPromotionalEvent "+ updatedCustomerPromotionalEvent.toString());

    }

    @Test
    public void test3FindByCpeId() {

        CustomerPromotionalEventFixture CustomerPromotionalEventFixture=new CustomerPromotionalEventFixture();

        CustomerPromotionalEvent CustomerPromotionalEvent = CustomerPromotionalEventFixture.standardCustomerPromotionalEvent();
        CustomerPromotionalEvent = CustomerPromotionalEventRepository.save(CustomerPromotionalEvent);
        log.info("Original CustomerPromotionalEvent " + CustomerPromotionalEvent.toString());


        // Add to the tempSet
        tempSet.add(CustomerPromotionalEvent);

        // Get the data
        CustomerPromotionalEvent searchCustomerPromotionalEvent = CustomerPromotionalEventRepository.findByCpeId(CustomerPromotionalEvent.getCpeId());
        Assert.assertNotNull(searchCustomerPromotionalEvent);
        Assert.assertTrue(CustomerPromotionalEvent.getCpeId().longValue() ==  searchCustomerPromotionalEvent.getCpeId().longValue());;
        log.info("Searched CustomerPromotionalEvent : " + searchCustomerPromotionalEvent.toString());


    }

    @Test
    public void test4FindByCpeLoyaltyIdAndCpeMerchantNo(){

        CustomerPromotionalEventFixture CustomerPromotionalEventFixture=new CustomerPromotionalEventFixture();

        CustomerPromotionalEvent CustomerPromotionalEvent = CustomerPromotionalEventFixture.standardCustomerPromotionalEvent();
        CustomerPromotionalEvent = CustomerPromotionalEventRepository.save(CustomerPromotionalEvent);
        log.info("Original CustomerPromotionalEvent " + CustomerPromotionalEvent.toString());


        // Add to the tempSet
        tempSet.add(CustomerPromotionalEvent);

        // Get the data
        List<CustomerPromotionalEvent> searchCustomerPromotionalEvent = CustomerPromotionalEventRepository.findByCpeLoyaltyIdAndCpeMerchantNo(CustomerPromotionalEvent.getCpeLoyaltyId(),CustomerPromotionalEvent.getCpeMerchantNo());
        Assert.assertNotNull(searchCustomerPromotionalEvent);
        log.info("Searched CustomerPromotionalEvent : " + searchCustomerPromotionalEvent.toString());

    }



    @After
    public void tearDown() {

        for(CustomerPromotionalEvent CustomerPromotionalEvent : tempSet ) {

            CustomerPromotionalEventRepository.delete(CustomerPromotionalEvent);

        }

    }



}
