package com.inspirenetz.api.test.core.service;

import com.inspirenetz.api.core.domain.Customer;
import com.inspirenetz.api.core.domain.CustomerSubscription;
import com.inspirenetz.api.core.repository.CustomerSubscriptionRepository;
import com.inspirenetz.api.core.service.CustomerService;
import com.inspirenetz.api.core.service.CustomerSubscriptionService;
import com.inspirenetz.api.rest.exception.InspireNetzException;
import com.inspirenetz.api.test.config.*;
import com.inspirenetz.api.test.core.fixture.CustomerFixture;
import com.inspirenetz.api.test.core.fixture.CustomerSubscriptionFixture;
import com.inspirenetz.api.test.util.ControllerTestUtils;
import com.inspirenetz.api.util.GeneralUtils;
import org.junit.*;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Created by sandheepgr on 30/4/14.
 */
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes  = {ApplicationTestConfig.class, PersistenceTestConfig.class, ServiceTestConfig.class, SecurityTestConfig.class, NotificationTestConfig.class})
public class CustomerSubscriptionServiceTest {


    private static Logger log = LoggerFactory.getLogger(CustomerSubscriptionServiceTest.class);

    @Autowired
    private CustomerSubscriptionService customerSubscriptionService;

    @Autowired
    private CustomerSubscriptionRepository customerSubscriptionRepository;

    @Autowired
    private CustomerService customerService;

    @Autowired
    private GeneralUtils generalUtils;

    @Autowired
    @Qualifier("uds")
    protected UserDetailsService userDetailsService;

    UsernamePasswordAuthenticationToken principal;

    Set<CustomerSubscription> tempSet = new HashSet<>(0);

    Set<Customer> customerSet = new HashSet<>(0);


    @Before
    public void setUp() {

        // Set the principal
        principal = ControllerTestUtils.getPrincipal(ControllerTestUtils.TEST_MERCHANT_USER_LOGINID, userDetailsService);

        // Set the context
        SecurityContextHolder.getContext().setAuthentication(principal);

    }




    @Test
    public void test1Create() throws InspireNetzException {






        CustomerSubscription customerSubscription = CustomerSubscriptionFixture.standardCustomerSubscription();
        customerSubscription = customerSubscriptionService.saveCustomerSubscription(customerSubscription);
        log.info(customerSubscription.toString());

        // Add the customerSubscriptionSet to tempSet
        tempSet.add(customerSubscription);

        Assert.assertNotNull(customerSubscription.getCsuId());

    }

    @Test
    public void test2Update() throws InspireNetzException {

        CustomerSubscription customerSubscription = CustomerSubscriptionFixture.standardCustomerSubscription();
        customerSubscription = customerSubscriptionService.saveCustomerSubscription(customerSubscription);
        log.info("Original CustomerSubscription " + customerSubscription.toString());

        // Add the customerSubscriptionSet to tempSet
        tempSet.add(customerSubscription);

        CustomerSubscription updatedCustomerSubscription = CustomerSubscriptionFixture.standardUpdatedCustomerSubscription(customerSubscription);
        updatedCustomerSubscription = customerSubscriptionService.saveCustomerSubscription(updatedCustomerSubscription);
        log.info("Updated CustomerSubscription "+ updatedCustomerSubscription.toString());

    }


    @Test
    public void test3GetCustomerSubscription() throws InspireNetzException {

        CustomerSubscription customerSubscription = CustomerSubscriptionFixture.standardCustomerSubscription();
        customerSubscription = customerSubscriptionService.saveCustomerSubscription(customerSubscription);
        log.info("Original CustomerSubscription " + customerSubscription.toString());

        // Add the customerSubscriptionSet to tempSet
        tempSet.add(customerSubscription);

        // GEt the CustomerSubscription
        CustomerSubscription searchCustomerSubscription = customerSubscriptionService.getCustomerSubscription(customerSubscription.getCsuId());
        Assert.assertNotNull(searchCustomerSubscription);
        log.info("Customer Subscription " + searchCustomerSubscription.toString());

    }


    @Test
    public void test4FindByCsuCustomerNo() throws InspireNetzException {

        CustomerSubscription customerSubscription = CustomerSubscriptionFixture.standardCustomerSubscription();
        customerSubscription = customerSubscriptionService.saveCustomerSubscription(customerSubscription);
        log.info("Original CustomerSubscription " + customerSubscription.toString());

        // Add the customerSubscriptionSet to tempSet
        tempSet.add(customerSubscription);

        // Get the list of items
        List<CustomerSubscription> customerSubscriptionList = customerSubscriptionService.findByCsuCustomerNo(customerSubscription.getCsuCustomerNo());
        Assert.assertNotNull(customerSubscriptionList);
        Assert.assertTrue(!customerSubscriptionList.isEmpty());
        log.info("CustomerSubscription List " + customerSubscriptionList.toString());


    }


    @Test
    public void test5FindByCsuCustomerNoAndCsuProductCode() throws InspireNetzException {

        CustomerSubscription customerSubscription = CustomerSubscriptionFixture.standardCustomerSubscription();
        customerSubscription = customerSubscriptionService.saveCustomerSubscription(customerSubscription);
        log.info("Original CustomerSubscription " + customerSubscription.toString());

        // Add the customerSubscriptionSet to tempSet
        tempSet.add(customerSubscription);

        // GEt the CustomerSubscription
        CustomerSubscription searchCustomerSubscription = customerSubscriptionService.findByCsuCustomerNoAndCsuProductCode(customerSubscription.getCsuCustomerNo(), customerSubscription.getCsuProductCode());
        Assert.assertNotNull(searchCustomerSubscription);
        log.info("Customer Subscription " + searchCustomerSubscription.toString());

    }



    @Test
    public void test6IsCustomerSubscriptionCodeExisting() throws InspireNetzException {

        // Create the customerSubscription
        CustomerSubscription customerSubscription = CustomerSubscriptionFixture.standardCustomerSubscription();
        customerSubscription = customerSubscriptionService.saveCustomerSubscription(customerSubscription);
        Assert.assertNotNull(customerSubscription.getCsuId());
        log.info("CustomerSubscription created");

        // Create a new customerSubscription
        CustomerSubscription newCustomerSubscription = CustomerSubscriptionFixture.standardCustomerSubscription();
        boolean exists = customerSubscriptionService.isDuplicateProductCodeExistingForCustomer(newCustomerSubscription);
        Assert.assertTrue(exists);
        log.info("CustomerSubscription exists");


    }


    @Test
    public void test7DeleteCustomerSubscription() throws InspireNetzException {

        // Create the customerSubscription
        CustomerSubscription customerSubscription = CustomerSubscriptionFixture.standardCustomerSubscription();
        customerSubscription = customerSubscriptionService.saveCustomerSubscription(customerSubscription);
        Assert.assertNotNull(customerSubscription.getCsuId());
        log.info("CustomerSubscription created");

        // call the delete customerSubscription
        customerSubscriptionService.deleteCustomerSubscription(customerSubscription.getCsuId());

        // Try searching for the customerSubscription
        CustomerSubscription checkCustomerSubscription  = customerSubscriptionService.getCustomerSubscription(customerSubscription.getCsuId());

        Assert.assertNull(checkCustomerSubscription);

        log.info("customerSubscription deleted");

    }


    @Test
    public void test8AddCustomerSubscription() throws InspireNetzException {

        CustomerSubscription customerSubscription = CustomerSubscriptionFixture.standardCustomerSubscription();
        customerSubscription = customerSubscriptionService.addCustomerSubscription(customerSubscription);
        log.info(customerSubscription.toString());

        // Add the customerSubscriptionSet to tempSet
        tempSet.add(customerSubscription);

        Assert.assertNotNull(customerSubscription.getCsuId());

    }


    @Test
    public void test9RemoveCustomerSubscription() throws InspireNetzException {

        // Create the customerSubscription
        CustomerSubscription customerSubscription = CustomerSubscriptionFixture.standardCustomerSubscription();
        customerSubscription = customerSubscriptionService.saveCustomerSubscription(customerSubscription);
        Assert.assertNotNull(customerSubscription.getCsuId());
        log.info("CustomerSubscription created");

        // call the delete customerSubscription
        customerSubscriptionService.removeCustomerSubscription(customerSubscription.getCsuId());

        // Try searching for the customerSubscription
        CustomerSubscription checkCustomerSubscription  = customerSubscriptionService.getCustomerSubscription(customerSubscription.getCsuId());

        Assert.assertNull(checkCustomerSubscription);

        log.info("customerSubscription deleted");

    }


    @Test
    public void test10listSubscriptionsForCustomer() throws InspireNetzException {

        CustomerSubscription customerSubscription = CustomerSubscriptionFixture.standardCustomerSubscription();
        customerSubscription = customerSubscriptionService.saveCustomerSubscription(customerSubscription);
        log.info("Original CustomerSubscription " + customerSubscription.toString());

        // Add the customerSubscriptionSet to tempSet
        tempSet.add(customerSubscription);

        // Get the list of items
        List<CustomerSubscription> customerSubscriptionList = customerSubscriptionService.listSubscriptionsForCustomer(customerSubscription.getCsuCustomerNo());
        Assert.assertNotNull(customerSubscriptionList);
        Assert.assertTrue(!customerSubscriptionList.isEmpty());
        log.info("CustomerSubscription List " + customerSubscriptionList.toString());


    }


    @Test
    public void test11GetSubscriptionProductCode() throws InspireNetzException {

        // Create the customer
        Customer customer = CustomerFixture.standardCustomer();
        customer = customerService.saveCustomer(customer);
        Assert.assertNotNull(customer.getCusCustomerNo());
        customerSet.add(customer);

        CustomerSubscription customerSubscription = CustomerSubscriptionFixture.standardCustomerSubscription();
        customerSubscription.setCsuCustomerNo(customer.getCusCustomerNo());
        customerSubscription = customerSubscriptionService.saveCustomerSubscription(customerSubscription);
        Assert.assertNotNull(customerSubscription.getCsuId());
        tempSet.add(customerSubscription);
        log.info("CustomerSubscription created");


        // Get the productcode
        String productCode = customerSubscriptionService.getCustomerSubscriptionProductCode(customer);
        Assert.assertTrue(productCode.equals(customerSubscription.getCsuProductCode()));
        log.info("Product code retrieved : " + productCode);

    }


    @Test
    public void testCheckDayofMonth() {

        String strDate = generalUtils.convertToDayMonthFormat(new Date());
        log.info("Formatted date : " + strDate);
    }





    @After
    public void tearDown() throws InspireNetzException {

        for(CustomerSubscription customerSubscription : tempSet ) {

            customerSubscriptionRepository.delete(customerSubscription);

        }


        for(Customer customer : customerSet) {

            customerService.deleteCustomer(customer.getCusCustomerNo());

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
