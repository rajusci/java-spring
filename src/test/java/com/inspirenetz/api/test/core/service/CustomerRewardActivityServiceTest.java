package com.inspirenetz.api.test.core.service;

import com.google.common.collect.Lists;
import com.inspirenetz.api.core.dictionary.CustomerActivityType;
import com.inspirenetz.api.core.dictionary.IndicatorStatus;
import com.inspirenetz.api.core.domain.*;
import com.inspirenetz.api.core.service.CustomerActivityService;
import com.inspirenetz.api.core.service.CustomerService;
import com.inspirenetz.api.core.service.CustomerRewardActivityService;
import com.inspirenetz.api.core.service.LinkedLoyaltyService;
import com.inspirenetz.api.rest.exception.InspireNetzException;
import com.inspirenetz.api.test.config.*;
import com.inspirenetz.api.test.core.fixture.*;
import com.inspirenetz.api.test.util.ControllerTestUtils;
import org.junit.*;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Created by saneeshci on 30/9/14.
 */
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes  = {ApplicationTestConfig.class, PersistenceTestConfig.class, ServiceTestConfig.class, SecurityTestConfig.class, NotificationTestConfig.class})
public class CustomerRewardActivityServiceTest {


    private static Logger log = LoggerFactory.getLogger(CustomerRewardActivityServiceTest.class);

    @Autowired
    private CustomerRewardActivityService customerRewardActivityService;

    UsernamePasswordAuthenticationToken principal;

    @Autowired
    @Qualifier("uds")
    protected UserDetailsService userDetailsService;

    @Autowired
    private CustomerService customerService;

    @Autowired
    CustomerActivityService customerActivityService;


    @Autowired
    private LinkedLoyaltyService linkedLoyaltyService;

    Set<CustomerRewardActivity> tempSet = new HashSet<>(0);

    Set<Customer> tempSet1 = new HashSet<>(0);


    @Before
    public void setUp() {

        // Set the principal
        principal = ControllerTestUtils.getPrincipal(ControllerTestUtils.TEST_MERCHANT_USER_LOGINID, userDetailsService);

        // Set the context
        SecurityContextHolder.getContext().setAuthentication(principal);
    }


    @Test
    public void test1Create() throws InspireNetzException {


        CustomerRewardActivity customerRewardActivity = customerRewardActivityService.saveCustomerRewardActivity(CustomerRewardActivityFixture.standardCustomerRewardActivity());
        log.info(customerRewardActivity.toString());

        // Add the items
        tempSet.add(customerRewardActivity);

        Assert.assertNotNull(customerRewardActivity.getCraId());

    }

    @Test
    public void test2Update() throws InspireNetzException {

        CustomerRewardActivity customerRewardActivity = CustomerRewardActivityFixture.standardCustomerRewardActivity();
        customerRewardActivity = customerRewardActivityService.saveCustomerRewardActivity(customerRewardActivity);
        log.info("Original CustomerRewardActivitys " + customerRewardActivity.toString());

        // Add the items
        tempSet.add(customerRewardActivity);

        CustomerRewardActivity updatedCustomerRewardActivitys = CustomerRewardActivityFixture.updatedStandCustomerRewardActivity(customerRewardActivity);
        updatedCustomerRewardActivitys = customerRewardActivityService.saveCustomerRewardActivity(updatedCustomerRewardActivitys);
        log.info("Updated CustomerRewardActivitys "+ updatedCustomerRewardActivitys.toString());

    }



    @Test
    public void test3FindByCraId() throws InspireNetzException {


        CustomerRewardActivity customerRewardActivity = CustomerRewardActivityFixture.standardCustomerRewardActivity();
        customerRewardActivity = customerRewardActivityService.saveCustomerRewardActivity(customerRewardActivity);
        log.info("Original CustomerRewardActivitys " + customerRewardActivity.toString());

        // Add the items
        tempSet.add(customerRewardActivity);

        CustomerRewardActivity searchRequest = customerRewardActivityService.findByCraId(customerRewardActivity.getCraId());
        Assert.assertNotNull(searchRequest);;
        Assert.assertTrue(customerRewardActivity.getCraId().longValue() == searchRequest.getCraId().longValue());



    }

    @Test
    public void test5FindByCraCustomerNo() throws InspireNetzException {


        CustomerRewardActivity customerRewardActivity = CustomerRewardActivityFixture.standardCustomerRewardActivity();
        customerRewardActivity = customerRewardActivityService.saveCustomerRewardActivity(customerRewardActivity);
        log.info("Original CustomerRewardActivitys " + customerRewardActivity.toString());

        // Add the items
        tempSet.add(customerRewardActivity);

        Page<CustomerRewardActivity> searchRequest = customerRewardActivityService.findByCraCustomerNo(customerRewardActivity.getCraCustomerNo(),constructPageSpecification(0));
        Assert.assertNotNull(searchRequest);;

    }

    @Test
    public void test6FindByCraCustomerNoAndCraType() throws InspireNetzException {


        CustomerRewardActivity customerRewardActivity = CustomerRewardActivityFixture.standardCustomerRewardActivity();
        customerRewardActivity = customerRewardActivityService.saveCustomerRewardActivity(customerRewardActivity);
        log.info("Original CustomerRewardActivitys " + customerRewardActivity.toString());

        // Add the items
        tempSet.add(customerRewardActivity);

        Page<CustomerRewardActivity> searchRequest = customerRewardActivityService.findByCraCustomerNoAndCraType(customerRewardActivity.getCraCustomerNo(), customerRewardActivity.getCraType(), constructPageSpecification(0));
        Assert.assertNotNull(searchRequest);;

    }



    @Test
    public void test5DeleteCustomerRewardActivitys() throws InspireNetzException {

        // Create the customerRewardActivity
        CustomerRewardActivity  customerRewardActivity = CustomerRewardActivityFixture.standardCustomerRewardActivity();
        customerRewardActivity = customerRewardActivityService.saveCustomerRewardActivity(customerRewardActivity);
        Assert.assertNotNull(customerRewardActivity.getCraId());
        log.info("CustomerRewardActivitys created");

        // call the delete customerRewardActivity
        customerRewardActivityService.deleteCustomerRewardActivity(customerRewardActivity);

        // Get the link request again
        CustomerRewardActivity customerRewardActivity1 = customerRewardActivityService.findByCraId(customerRewardActivity.getCraId());
        Assert.assertNull(customerRewardActivity1);;


    }


    @Test
    public void test6SaveAndValidateCustomerRewardActivity() throws InspireNetzException {

        CustomerRewardActivity customerRewardActivity = CustomerRewardActivityFixture.standardCustomerRewardActivity();
        customerRewardActivity = customerRewardActivityService.validateAndRegisterCustomerRewardActivity(customerRewardActivity.getCraCustomerNo(),customerRewardActivity.getCraType(),customerRewardActivity.getCraActivityRef());
        log.info(customerRewardActivity.toString());

        // Add the items
        tempSet.add(customerRewardActivity);

        Assert.assertNotNull(customerRewardActivity.getCraId());

    }


    @Test
    public void test7EventRegistrationLogging() throws InspireNetzException {

        // Create the customers
        Set<Customer> customers = CustomerFixture.standardCustomers();
        List<Customer> customerList = Lists.newArrayList((Iterable<Customer>)customers);


        // Source customer object
        Customer sourceCustomer = customerList.get(0);
        sourceCustomer = customerService.saveCustomer(sourceCustomer);


        CustomerRewardActivity customerRewardActivity = CustomerRewardActivityFixture.standardCustomerRewardActivity();
        customerRewardActivity.setCraCustomerNo(sourceCustomer.getCusCustomerNo());
        customerRewardActivity = customerRewardActivityService.validateAndRegisterCustomerRewardActivity(customerRewardActivity.getCraCustomerNo(),customerRewardActivity.getCraType(),customerRewardActivity.getCraActivityRef());
        log.info(customerRewardActivity.toString());

        //get the customer activities
        Page<CustomerActivity> customerActivities = customerActivityService.searchCustomerActivities(sourceCustomer.getCusLoyaltyId(), CustomerActivityType.TRANSFER_POINT, java.sql.Date.valueOf("2014-01-01"), java.sql.Date.valueOf("2014-12-12"), sourceCustomer.getCusMerchantNo(), constructPageSpecification(0));

        Assert.assertTrue(customerActivities.hasContent());
        // Add the items
        tempSet.add(customerRewardActivity);

        Assert.assertNotNull(customerRewardActivity.getCraId());

    }
    @Test
    public void test8RegisterByLoyalty() throws InspireNetzException {

        Customer customer = CustomerFixture.standardCustomer();
        customerService.saveCustomer(customer);
        Assert.assertNotNull(customer.getCusCustomerNo());
        log.info("Customer created");

        tempSet1.add(customer);

        CustomerRewardActivity customerRewardActivity =  CustomerRewardActivityFixture.standardCustomerRewardActivity();

        customerRewardActivity.setCraCustomerNo(customer.getCusCustomerNo());

        customerRewardActivity.setCraActivityRef("400");

        customerRewardActivity.setCraType(1);


        log.info("Original CustomerRewardActivitys " + customerRewardActivity.toString());

        // Add the items
        tempSet.add(customerRewardActivity);

        CustomerRewardActivity customerRewardActivity1=customerRewardActivityService.saveCustomerRewardActivityByLoyaltyId(customer.getCusLoyaltyId(),customerRewardActivity.getCraType(),customerRewardActivity.getCraActivityRef(),1L);

        Assert.assertNotNull(customerRewardActivity1);

    }


    @After
    public void tearDown() throws InspireNetzException {

        for(CustomerRewardActivity customerRewardActivity: tempSet) {

            customerRewardActivityService.deleteCustomerRewardActivity(customerRewardActivity);

        }

        for(Customer customer:tempSet1){

            customerService.deleteCustomer(customer.getCusCustomerNo());
        }


    }



    /**
     * Returns a new object which specifies the the wanted result page.
     * @param pageIndex The index of the wanted result page
     * @return
     */
    private Pageable constructPageSpecification(int pageIndex) {
        Pageable pageSpecification = new PageRequest(pageIndex, 10);
        return pageSpecification;
    }

    /**
     * Returns a Sort object which sorts persons in ascending order by using the last name.
     * @return
     */
    private Sort sortByLastNameAsc() {
        return new Sort(Sort.Direction.ASC, "craId");
    }

}
