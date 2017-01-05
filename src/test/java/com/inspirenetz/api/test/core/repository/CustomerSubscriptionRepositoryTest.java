package com.inspirenetz.api.test.core.repository;

import com.inspirenetz.api.core.domain.CustomerSubscription;
import com.inspirenetz.api.core.repository.CustomerSubscriptionRepository;
import com.inspirenetz.api.test.config.ApplicationTestConfig;
import com.inspirenetz.api.test.config.PersistenceTestConfig;
import com.inspirenetz.api.test.core.fixture.CustomerSubscriptionFixture;
import org.junit.*;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
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
public class CustomerSubscriptionRepositoryTest {

    private static Logger log = LoggerFactory.getLogger(CustomerSubscriptionRepositoryTest.class);

    @Autowired
    private CustomerSubscriptionRepository customerSubscriptionRepository;

    Set<CustomerSubscription> tempSet = new HashSet<>(0);

    @Before
    public void setup() {}


    @Test
    public void test1Create(){


        CustomerSubscription customerSubscription = customerSubscriptionRepository.save(CustomerSubscriptionFixture.standardCustomerSubscription());
        log.info(customerSubscription.toString());

        // Add the customerSubscriptionSet to tempSet
        tempSet.add(customerSubscription);

        Assert.assertNotNull(customerSubscription.getCsuId());

    }

    @Test
    public void test2Update() {

        CustomerSubscription customerSubscription = CustomerSubscriptionFixture.standardCustomerSubscription();
        customerSubscription = customerSubscriptionRepository.save(customerSubscription);
        log.info("Original CustomerSubscription " + customerSubscription.toString());

        // Add the customerSubscriptionSet to tempSet
        tempSet.add(customerSubscription);

        CustomerSubscription updatedCustomerSubscription = CustomerSubscriptionFixture.standardUpdatedCustomerSubscription(customerSubscription);
        updatedCustomerSubscription = customerSubscriptionRepository.save(updatedCustomerSubscription);
        log.info("Updated CustomerSubscription "+ updatedCustomerSubscription.toString());

    }


    @Test
    public void test3FindByCsuId() {

        CustomerSubscription customerSubscription = CustomerSubscriptionFixture.standardCustomerSubscription();
        customerSubscription = customerSubscriptionRepository.save(customerSubscription);
        log.info("Original CustomerSubscription " + customerSubscription.toString());

        // Add the customerSubscriptionSet to tempSet
        tempSet.add(customerSubscription);

        // GEt the CustomerSubscription
        CustomerSubscription searchCustomerSubscription = customerSubscriptionRepository.findByCsuId(customerSubscription.getCsuId());
        Assert.assertNotNull(searchCustomerSubscription);
        log.info("Customer Subscription " + searchCustomerSubscription.toString());

    }


    @Test
    public void test4findByCsuCustomerNo() {

        CustomerSubscription customerSubscription = CustomerSubscriptionFixture.standardCustomerSubscription();
        customerSubscription = customerSubscriptionRepository.save(customerSubscription);
        log.info("Original CustomerSubscription " + customerSubscription.toString());

        // Add the customerSubscriptionSet to tempSet
        tempSet.add(customerSubscription);

        // Get the list of items
        List<CustomerSubscription> customerSubscriptionList = customerSubscriptionRepository.findByCsuCustomerNo(customerSubscription.getCsuCustomerNo());
        Assert.assertNotNull(customerSubscriptionList);
        Assert.assertTrue(!customerSubscriptionList.isEmpty());
        log.info("CustomerSubscription List " + customerSubscriptionList.toString());


    }


    @Test
    public void test5findByCsuCustomerNoAndCsuProductCode() {

        CustomerSubscription customerSubscription = CustomerSubscriptionFixture.standardCustomerSubscription();
        customerSubscription = customerSubscriptionRepository.save(customerSubscription);
        log.info("Original CustomerSubscription " + customerSubscription.toString());

        // Add the customerSubscriptionSet to tempSet
        tempSet.add(customerSubscription);

        // GEt the CustomerSubscription
        CustomerSubscription searchCustomerSubscription = customerSubscriptionRepository.findByCsuCustomerNoAndCsuProductCode(customerSubscription.getCsuCustomerNo(), customerSubscription.getCsuProductCode());
        Assert.assertNotNull(searchCustomerSubscription);
        log.info("Customer Subscription " + searchCustomerSubscription.toString());

    }


    @After
    public void tearDown() {

        for(CustomerSubscription customerSubscription : tempSet ) {

            customerSubscriptionRepository.delete(customerSubscription);

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
        return new Sort(Sort.Direction.ASC, "csuName");
    }


}
