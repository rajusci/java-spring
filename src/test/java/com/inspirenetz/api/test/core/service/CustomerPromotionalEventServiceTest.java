package com.inspirenetz.api.test.core.service;

import com.inspirenetz.api.core.dictionary.DrawType;
import com.inspirenetz.api.core.domain.Customer;
import com.inspirenetz.api.core.domain.CustomerPromotionalEvent;
import com.inspirenetz.api.core.service.CustomerService;
import com.inspirenetz.api.core.service.CustomerPromotionalEventService;
import com.inspirenetz.api.rest.exception.InspireNetzException;
import com.inspirenetz.api.test.config.*;
import com.inspirenetz.api.test.core.fixture.CustomerFixture;
import com.inspirenetz.api.test.core.fixture.CustomerPromotionalEventFixture;
import com.inspirenetz.api.test.util.ControllerTestUtils;
import org.junit.*;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetailsService;
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
@ContextConfiguration(classes  = {ApplicationTestConfig.class, PersistenceTestConfig.class, ServiceTestConfig.class, SecurityTestConfig.class, NotificationTestConfig.class})
public class CustomerPromotionalEventServiceTest {


    private static Logger log = LoggerFactory.getLogger(CustomerPromotionalEventServiceTest.class);

    @Autowired
    private CustomerPromotionalEventService customerPromotionalEventService;

    @Autowired
    private CustomerService customerService;

    UsernamePasswordAuthenticationToken principal;

    @Autowired
    @Qualifier("uds")
    protected UserDetailsService userDetailsService;

    Set<CustomerPromotionalEvent> tempSet = new HashSet<>(0);

    Set<Customer> customerSet = new HashSet<>(0);


    @Before
    public void setUp() {

        // Set the principal
        principal = ControllerTestUtils.getPrincipal(ControllerTestUtils.TEST_MERCHANT_ADMIN_LOGINID, userDetailsService);

        // Set the context
        SecurityContextHolder.getContext().setAuthentication(principal);
    }


    @Test
    public void test1Create(){

        CustomerPromotionalEvent customerPromotionalEvent = customerPromotionalEventService.saveCustomerPromotionalEvent(CustomerPromotionalEventFixture.standardCustomerPromotionalEvent());
        log.info(customerPromotionalEvent.toString());

        // Add the items
        tempSet.add(customerPromotionalEvent);

        Assert.assertNotNull(customerPromotionalEvent.getCpeId());

    }

    @Test
    public void test2Update() throws InspireNetzException {

        CustomerPromotionalEvent customerPromotionalEvent = customerPromotionalEventService.saveCustomerPromotionalEvent(CustomerPromotionalEventFixture.standardCustomerPromotionalEvent());
        customerPromotionalEvent=customerPromotionalEventService.findByCpeId(customerPromotionalEvent.getCpeId());
        tempSet.add(customerPromotionalEvent);
        log.info("customerPromotionalEvent select data"+customerPromotionalEvent.getCpeId());

        CustomerPromotionalEvent updatedCustomerPromotionalEvent = CustomerPromotionalEventFixture.updatedStandardCustomerPromotionalEvent(customerPromotionalEvent);
        log.info("Updated CustomerPromotionalEvent "+ updatedCustomerPromotionalEvent.toString());
        updatedCustomerPromotionalEvent = customerPromotionalEventService.validateAndSaveCustomerPromotionalEvent(updatedCustomerPromotionalEvent);
//        tempSet.add(customerPromotionalEvent);
        log.info("Updated CustomerPromotionalEvent "+ updatedCustomerPromotionalEvent.toString());

    }

    @Test
    public void test3FindByCpeId() throws InspireNetzException {

        CustomerPromotionalEventFixture customerPromotionalEventFixture=new CustomerPromotionalEventFixture();

        CustomerPromotionalEvent customerPromotionalEvent = customerPromotionalEventFixture.standardCustomerPromotionalEvent();
        customerPromotionalEvent = customerPromotionalEventService.saveCustomerPromotionalEvent(customerPromotionalEvent);
        log.info("Original CustomerPromotionalEvents " + customerPromotionalEvent.toString());

        // Add the items
        tempSet.add(customerPromotionalEvent);

        CustomerPromotionalEvent searchRequest = customerPromotionalEventService.findByCpeId(customerPromotionalEvent.getCpeId());
        Assert.assertNotNull(searchRequest);
        Assert.assertTrue(customerPromotionalEvent.getCpeId().longValue() == searchRequest.getCpeId().longValue());



    }
    @Test
    public void test3FindByCpeLoyaltyIdAndCpeEventIdAndCpeMerchantNo() throws InspireNetzException {

        CustomerPromotionalEventFixture customerPromotionalEventFixture=new CustomerPromotionalEventFixture();

        CustomerPromotionalEvent customerPromotionalEvent = customerPromotionalEventFixture.standardCustomerPromotionalEvent();
        customerPromotionalEvent = customerPromotionalEventService.saveCustomerPromotionalEvent(customerPromotionalEvent);
        log.info("Original CustomerPromotionalEvents " + customerPromotionalEvent.toString());

        // Add the items
        tempSet.add(customerPromotionalEvent);

        CustomerPromotionalEvent searchRequest = customerPromotionalEventService.findByCpeLoyaltyIdAndCpeEventIdCpeMerchantNo(customerPromotionalEvent.getCpeLoyaltyId(), customerPromotionalEvent.getCpeEventId(), customerPromotionalEvent.getCpeMerchantNo());
        Assert.assertNotNull(searchRequest);


    }

    @Test
    public void test3FindByCpeLoyaltyIdAndCpeMerchantNo() throws InspireNetzException {

        CustomerPromotionalEventFixture customerPromotionalEventFixture=new CustomerPromotionalEventFixture();

        CustomerPromotionalEvent customerPromotionalEvent = customerPromotionalEventFixture.standardCustomerPromotionalEvent();
        customerPromotionalEvent = customerPromotionalEventService.saveCustomerPromotionalEvent(customerPromotionalEvent);
        log.info("Original CustomerPromotionalEvents " + customerPromotionalEvent.toString());

        // Add the items
        tempSet.add(customerPromotionalEvent);

        List<CustomerPromotionalEvent> searchResults = customerPromotionalEventService.findByCpeLoyaltyIdAndCpeMerchantNo(customerPromotionalEvent.getCpeLoyaltyId(), customerPromotionalEvent.getCpeMerchantNo());
        Assert.assertNotNull(searchResults);
        Assert.assertTrue(searchResults.size()>0);

    }

    @Test
    public void test5DeleteCustomerPromotionalEvents() throws InspireNetzException {

        // Create the customerPromotionalEvent

        CustomerPromotionalEventFixture customerPromotionalEventFixture=new CustomerPromotionalEventFixture();

        CustomerPromotionalEvent  customerPromotionalEvent = customerPromotionalEventFixture.standardCustomerPromotionalEvent();
        customerPromotionalEvent = customerPromotionalEventService.saveCustomerPromotionalEvent(customerPromotionalEvent);
        Assert.assertNotNull(customerPromotionalEvent.getCpeId());
        log.info("CustomerPromotionalEvents created");

        // call the delete customerPromotionalEvent
        customerPromotionalEventService.deleteCustomerPromotionalEvent(customerPromotionalEvent.getCpeId());

        // Get the link request again
        CustomerPromotionalEvent customerPromotionalEvent1 = customerPromotionalEventService.findByCpeId(customerPromotionalEvent.getCpeId());
        Assert.assertNull(customerPromotionalEvent1);;


    }


    @Test
    public void test6SaveAndValidateCustomerPromotionalEvent() throws InspireNetzException {

        CustomerPromotionalEventFixture customerPromotionalEventFixture=new CustomerPromotionalEventFixture();

        CustomerPromotionalEvent customerPromotionalEvent = customerPromotionalEventService.validateAndSaveCustomerPromotionalEvent(customerPromotionalEventFixture.standardCustomerPromotionalEvent());
        log.info(customerPromotionalEvent.toString());

        // Add the items
        tempSet.add(customerPromotionalEvent);

        Assert.assertNotNull(customerPromotionalEvent.getCpeId());

    }



    @After
    public void tearDown() throws InspireNetzException {
        for(CustomerPromotionalEvent customerPromotionalEvent: tempSet) {

            customerPromotionalEventService.deleteCustomerPromotionalEvent(customerPromotionalEvent.getCpeId());

        }

        for(Customer customer:customerSet){


            customerService.deleteCustomer(customer.getCusCustomerNo());
        }


    }


}
