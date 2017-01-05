package com.inspirenetz.api.test.core.service;

import com.inspirenetz.api.core.dictionary.CustomerActivityType;
import com.inspirenetz.api.core.dictionary.DrawType;
import com.inspirenetz.api.core.dictionary.OTPType;
import com.inspirenetz.api.core.domain.Customer;
import com.inspirenetz.api.core.domain.CustomerActivity;
import com.inspirenetz.api.core.domain.OneTimePassword;
import com.inspirenetz.api.core.service.CustomerActivityService;
import com.inspirenetz.api.core.service.CustomerService;
import com.inspirenetz.api.core.service.OneTimePasswordService;
import com.inspirenetz.api.rest.exception.InspireNetzException;
import com.inspirenetz.api.test.config.*;
import com.inspirenetz.api.test.core.fixture.CustomerActivityFixture;
import com.inspirenetz.api.test.core.fixture.CustomerFixture;
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

import java.sql.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Created by saneesh-ci on 27/9/14.
 */
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes  = {ApplicationTestConfig.class, PersistenceTestConfig.class, ServiceTestConfig.class, SecurityTestConfig.class, NotificationTestConfig.class})
public class CustomerActivityServiceTest {


    private static Logger log = LoggerFactory.getLogger(CustomerActivityServiceTest.class);

    @Autowired
    private CustomerActivityService customerActivityService;

    @Autowired
    CustomerService customerService;

    @Autowired
    OneTimePasswordService oneTimePasswordService;

    UsernamePasswordAuthenticationToken principal;

    @Autowired
    @Qualifier("uds")
    protected UserDetailsService userDetailsService;

    Set<CustomerActivity> tempSet = new HashSet<>(0);

    Set<Customer> customerSet = new HashSet<>(0);


    @Before
    public void setUp() {

        // Set the principal
        principal = ControllerTestUtils.getPrincipal(ControllerTestUtils.TEST_CUSTOMER_LOGINID, userDetailsService);

        // Set the context
        SecurityContextHolder.getContext().setAuthentication(principal);
    }


    @Test
    public void test1Create(){

        CustomerActivity customerActivity = customerActivityService.saveCustomerActivity(CustomerActivityFixture.standardCustomerActivity());
        log.info(customerActivity.toString());

        // Add the items
        tempSet.add(customerActivity);

        Assert.assertNotNull(customerActivity.getCuaId());

    }

    @Test
    public void test2Update() throws InspireNetzException {

        CustomerActivity customerActivity = customerActivityService.saveCustomerActivity(CustomerActivityFixture.standardCustomerActivity());
        customerActivity=customerActivityService.findByCuaId(customerActivity.getCuaId());
        tempSet.add(customerActivity);
        log.info("customerActivity select data"+customerActivity.getCuaId());

        CustomerActivity updatedCustomerActivity = CustomerActivityFixture.updatedStandardCustomerActivity(customerActivity);
        log.info("Updated CustomerActivity "+ updatedCustomerActivity.toString());
        updatedCustomerActivity = customerActivityService.saveCustomerActivity(updatedCustomerActivity);
//        tempSet.add(customerActivity);
        log.info("Updated CustomerActivity "+ updatedCustomerActivity.toString());

    }

    @Test
    public void test3FindByCuaId() throws InspireNetzException {

        CustomerActivityFixture customerActivityFixture=new CustomerActivityFixture();

        CustomerActivity customerActivity = customerActivityFixture.standardCustomerActivity();
        customerActivity = customerActivityService.saveCustomerActivity(customerActivity);
        log.info("Original CustomerActivitys " + customerActivity.toString());

        // Add the items
        tempSet.add(customerActivity);

        CustomerActivity searchRequest = customerActivityService.findByCuaId(customerActivity.getCuaId());
        Assert.assertNotNull(searchRequest);
        Assert.assertTrue(customerActivity.getCuaId().longValue() == searchRequest.getCuaId().longValue());



    }
    @Test
    public void test4SearchActivities() throws InspireNetzException {

        CustomerActivityFixture customerActivityFixture=new CustomerActivityFixture();

        CustomerActivity customerActivity = customerActivityFixture.standardCustomerActivity();
        customerActivity = customerActivityService.saveCustomerActivity(customerActivity);
        log.info("Original CustomerActivitys " + customerActivity.toString());

        // Add the items
        tempSet.add(customerActivity);

        Page<CustomerActivity> searchRequest = customerActivityService.searchCustomerActivities("0", 0, Date.valueOf("2014-02-13"),Date.valueOf("2015-02-13"), customerActivity.getCuaMerchantNo(),constructPageSpecification(0) );
        Assert.assertNotNull(searchRequest);
        Assert.assertTrue(searchRequest.hasContent());

    }


    @Test
    public void test5DeleteCustomerActivitys() throws InspireNetzException {

        // Create the customerActivity

        CustomerActivityFixture customerActivityFixture=new CustomerActivityFixture();

        CustomerActivity  customerActivity = customerActivityFixture.standardCustomerActivity();
        customerActivity = customerActivityService.saveCustomerActivity(customerActivity);
        Assert.assertNotNull(customerActivity.getCuaId());
        log.info("CustomerActivitys created");

        // call the delete customerActivity
        customerActivityService.deleteCustomerActivity(customerActivity.getCuaId());

        // Get the link request again
        CustomerActivity customerActivity1 = customerActivityService.findByCuaId(customerActivity.getCuaId());
        Assert.assertNull(customerActivity1);;


    }

    @Test
    public void test6logActivity() throws InspireNetzException {

        CustomerActivity customerActivity = CustomerActivityFixture.standardCustomerActivity();
        customerActivity = customerActivityService.logActivity(customerActivity.getCuaLoyaltyId(),customerActivity.getCuaActivityType(),customerActivity.getCuaRemarks(),customerActivity.getCuaMerchantNo(),customerActivity.getCuaParams());
        log.info(customerActivity.toString());

        // Add the items
        tempSet.add(customerActivity);

        Assert.assertNotNull(customerActivity.getCuaId());

    }

    @Test
    public void test7CustomerRegistrationLogging() throws InspireNetzException {

        Customer customer = CustomerFixture.standardCustomer();
        customer.setCusLoyaltyId("9476656224");

        // Call the register customer
        boolean isValid = customerService.registerCustomer(customer.getCusLoyaltyId(),"welcome1234","Sandeep","Menon");

        customer = customerService.findByCusLoyaltyIdAndCusMerchantNo(customer.getCusLoyaltyId(),customer.getCusMerchantNo());

        //get the otp list for the customer
        List<OneTimePassword> oneTimePasswords = oneTimePasswordService.getOTPListForType(customer.getCusMerchantNo(), customer.getCusCustomerNo(), OTPType.REGISTER_CUSTOMER);

        //get the current otp
        OneTimePassword oneTimePassword = oneTimePasswords.get(0);

        //get the otp code
        String otpCode = oneTimePassword.getOtpCode();

        //confirm the customer registratiion
        customerService.confirmCustomerRegistration(customer.getCusLoyaltyId(),otpCode,1L);

        customerSet.add(customer);

        //get the customer activities
        Page<CustomerActivity> customerActivities = customerActivityService.searchCustomerActivities(customer.getCusLoyaltyId(), CustomerActivityType.REGISTER, Date.valueOf("2014-01-01"), Date.valueOf("2014-12-12"), customer.getCusMerchantNo(), constructPageSpecification(0));

        Assert.assertTrue(customerActivities.hasContent());
    }

    @Test
    public void test8RedemptionEnquiryLogging() throws InspireNetzException {

        Customer customer = CustomerFixture.standardCustomer();
        customer.setCusLoyaltyId("9476656224");

        // Call the register customer
        boolean isValid = customerService.registerCustomer(customer.getCusLoyaltyId(),"welcome1234","Sandeep","Menon");

        customer = customerService.findByCusLoyaltyIdAndCusMerchantNo(customer.getCusLoyaltyId(),customer.getCusMerchantNo());

        //get the otp list for the customer
        List<OneTimePassword> oneTimePasswords = oneTimePasswordService.getOTPListForType(customer.getCusMerchantNo(), customer.getCusCustomerNo(), OTPType.REGISTER_CUSTOMER);

        //get the current otp
        OneTimePassword oneTimePassword = oneTimePasswords.get(0);

        //get the otp code
        String otpCode = oneTimePassword.getOtpCode();

        //confirm the customer registratiion
        customerService.confirmCustomerRegistration(customer.getCusLoyaltyId(),otpCode,1L);

        customerSet.add(customer);

        //get the customer activities
        Page<CustomerActivity> customerActivities = customerActivityService.searchCustomerActivities(customer.getCusLoyaltyId(), CustomerActivityType.REGISTER, Date.valueOf("2014-01-01"), Date.valueOf("2014-12-12"), customer.getCusMerchantNo(), constructPageSpecification(0));

        Assert.assertTrue(customerActivities.hasContent());
    }

    @Test
    public void test8GetCustomerRecentActivities() throws InspireNetzException {



        //get the customer activities
        List<Map<String, String >> customerActivities = customerActivityService.getCustomerRecentActivities("4444444444", 0L);

        Assert.assertFalse(customerActivities.isEmpty());
    }

    @After
    public void tearDown() throws InspireNetzException {
        for(CustomerActivity customerActivity: tempSet) {

            customerActivityService.deleteCustomerActivity(customerActivity.getCuaId());

        }

        for(Customer customer: customerSet) {

            customerService.deleteCustomer(customer.getCusCustomerNo());

        }


    }

    /**
     *
     * Returns a new object which specifies the the wanted result page.
     * @param pageIndex The index of the wanted result page
     * @return
     */
    private Pageable constructPageSpecification(int pageIndex) {
        Pageable pageSpecification = new PageRequest(pageIndex, 10);
        return pageSpecification;
    }

}
