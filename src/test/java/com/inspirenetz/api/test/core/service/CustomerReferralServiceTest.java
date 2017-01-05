package com.inspirenetz.api.test.core.service;

import com.inspirenetz.api.core.dictionary.IndicatorStatus;
import com.inspirenetz.api.core.domain.Customer;
import com.inspirenetz.api.core.domain.CustomerReferral;
import com.inspirenetz.api.core.domain.User;
import com.inspirenetz.api.core.service.CustomerReferralService;
import com.inspirenetz.api.rest.exception.InspireNetzException;
import com.inspirenetz.api.test.config.*;
import com.inspirenetz.api.test.core.fixture.CustomerReferralFixture;
import com.inspirenetz.api.test.core.fixture.UserFixture;
import com.inspirenetz.api.test.util.ControllerTestUtils;
import com.inspirenetz.api.util.DBUtils;
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
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.*;

/**
 * Created by saneesh-ci on 27/9/14.
 */
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes  = {ApplicationTestConfig.class, PersistenceTestConfig.class, ServiceTestConfig.class, SecurityTestConfig.class, NotificationTestConfig.class})
public class CustomerReferralServiceTest {


    private static Logger log = LoggerFactory.getLogger(CustomerReferralServiceTest.class);

    @Autowired
    private CustomerReferralService customerReferralService;

    UsernamePasswordAuthenticationToken principal;

    @Autowired
    @Qualifier("uds")
    protected UserDetailsService userDetailsService;

    Set<CustomerReferral> tempSet = new HashSet<>(0);

    Set<Customer> customerSet = new HashSet<>(0);


    @Before
    public void setUp() {

        // Set the principal
        principal = ControllerTestUtils.getPrincipal(ControllerTestUtils.TEST_MERCHANT_ADMIN_LOGINID, userDetailsService);

        // Set the context
        SecurityContextHolder.getContext().setAuthentication(principal);
    }


    @Test
    public void test1Create() throws InspireNetzException {

        CustomerReferral customerReferral = customerReferralService.validateAndSaveCustomerReferral(CustomerReferralFixture.standardCustomerReferral());

        log.info(customerReferral.toString());

        // Add the items
        tempSet.add(customerReferral);

        Assert.assertNotNull(customerReferral.getCsrId());

    }

    @Test
    public void test2Update() throws InspireNetzException {

        CustomerReferral customerReferral = customerReferralService.validateAndSaveCustomerReferral(CustomerReferralFixture.standardCustomerReferral());

        Assert.assertNotNull(customerReferral);

        customerReferral=customerReferralService.findByCsrId(customerReferral.getCsrId());

        tempSet.add(customerReferral);

        log.info("Customer referral select data"+customerReferral.toString());

        CustomerReferral updatedCustomerReferral = CustomerReferralFixture.updatedStandardCustomerReferral(customerReferral);

        log.info("Updated Customer referral data "+ updatedCustomerReferral.toString());

        updatedCustomerReferral = customerReferralService.validateAndSaveCustomerReferral(updatedCustomerReferral);

        log.info("Updated Customer referral details "+ updatedCustomerReferral.toString());

    }

    @Test
    public void test3FindByCsrId() throws InspireNetzException {

        CustomerReferralFixture customerReferralFixture=new CustomerReferralFixture();

        CustomerReferral customerReferral = customerReferralFixture.standardCustomerReferral();

        customerReferral = customerReferralService.validateAndSaveCustomerReferral(customerReferral);

        log.info("Original Customer referral details " + customerReferral.toString());

        Assert.assertNotNull(customerReferral);

        // Add the items
        tempSet.add(customerReferral);

        CustomerReferral searchedCustomerReferral = customerReferralService.findByCsrId(customerReferral.getCsrId());

        Assert.assertNotNull(searchedCustomerReferral);

        Assert.assertTrue(customerReferral.getCsrId().longValue() == searchedCustomerReferral.getCsrId().longValue());



    }
    @Test
    public void test3IsDuplicateReferralExisting() throws InspireNetzException {

        CustomerReferralFixture customerReferralFixture=new CustomerReferralFixture();

        CustomerReferral customerReferral = customerReferralFixture.standardCustomerReferral();

        customerReferral = customerReferralService.validateAndSaveCustomerReferral(customerReferral);

        log.info("Original Customer referral details " + customerReferral.toString());

        Assert.assertNotNull(customerReferral);

        // Add the items
        tempSet.add(customerReferral);

        Assert.assertTrue(customerReferralService.isDuplicateReferralExisting(customerReferral));


    }

    @Test
    public void test4FindByCsrMerchantNoAndCsrLoyaltyIdOrderByCsrRefTimeStampDesc() throws InspireNetzException {

        // Create the customer
        CustomerReferral customerReferral = CustomerReferralFixture.standardCustomerReferral();

        customerReferralService.validateAndSaveCustomerReferral(customerReferral);

        Assert.assertNotNull(customerReferral.getCsrId());

        log.info("Customer referral created");

        // Add to the tempSet
        tempSet.add(customerReferral);

        Page<CustomerReferral> fetchedCustomerReferrals = customerReferralService.findByCsrMerchantNoAndCsrLoyaltyIdOrderByCsrRefTimeStampDesc(customerReferral.getCsrMerchantNo(), customerReferral.getCsrLoyaltyId(),constructPageSpecification(1));

        Assert.assertNotNull(fetchedCustomerReferrals);

        log.info("Fetched customer referrals" + fetchedCustomerReferrals.toString());


    }

    @Test
    public void test5FindByCsrMerchantNoAndCsrRefMobileAndCsrRefStatusOrderByCsrRefTimeStampAsc() throws InspireNetzException {

        // Create the customer
        CustomerReferral customerReferral = CustomerReferralFixture.standardCustomerReferral();

        customerReferralService.validateAndSaveCustomerReferral(customerReferral);

        Assert.assertNotNull(customerReferral.getCsrId());

        log.info("Customer referral created");

        // Add to the tempSet
        tempSet.add(customerReferral);

        List<CustomerReferral> fetchedCustomerReferral = customerReferralService.findByCsrMerchantNoAndCsrRefMobileAndCsrRefStatusOrderByCsrRefTimeStampAsc(customerReferral.getCsrMerchantNo(), customerReferral.getCsrRefMobile(), IndicatorStatus.NO);

        Assert.assertNotNull(fetchedCustomerReferral);

        log.info("Fetched customer referral" + fetchedCustomerReferral.toString());


    }

    @Test
    public void findByCsrMerchantNoAndCsrLoyaltyIdAndCsrRefTimeStampBetween() throws InspireNetzException {

        // Create the customer
        Set<CustomerReferral> customerReferral = CustomerReferralFixture.standardCustomerReferrals();

        List<CustomerReferral> customerReferralList =new ArrayList<>(customerReferral);

        customerReferralService.saveAll(customerReferralList);


        log.info("Customer referral created");

        for (CustomerReferral customerReferral1 :customerReferral){

            // Add to the tempSet
            tempSet.add(customerReferral1);
        }


        List<CustomerReferral> customerReferralList2 =customerReferralService.findByCsrMerchantNoAndCsrLoyaltyIdAndCsrRefTimeStampBetween(1L, "9400651677", DBUtils.covertToSqlDate("2015-10-02"), DBUtils.covertToSqlDate("2015-10-24"));

        Assert.assertNotNull(customerReferralList2);

    }

    @Test
    public void validateCustomerReferral() throws InspireNetzException {

        // Create the customer
        Set<CustomerReferral> customerReferral = CustomerReferralFixture.standardCustomerReferrals();

        List<CustomerReferral> customerReferralList =new ArrayList<>(customerReferral);

        customerReferralList.get(0).setCsrLoyaltyId("8867987369");
        customerReferralList.get(0).setCsrRefMobile("9495177881");
        customerReferralList.get(0).setCsrMerchantNo(1L);



        User user = UserFixture.standardUser();
        user.setUsrUserNo(11L);
        customerReferralService.validateCustomerReferral(customerReferralList.get(0), user);

    }

    @Test
    public void testCheckReferralValidity() throws InspireNetzException {

        //get the customer referral
        CustomerReferral customerReferral = CustomerReferralFixture.standardCustomerReferral();

        //set customerReferral name
        customerReferral.setCsrFName("devasu");
        customerReferral.setCsrRefMobile("9999999993");
        customerReferral.setCsrRefName("soum");
        customerReferral.setCsrLoyaltyId("9538831228");
        customerReferral.setCsrMerchantNo(3l);

        //get the user object
        User user = UserFixture.standardUser();
        user.setUsrMerchantNo(3l);


        boolean isFuncAllowed = customerReferralService.checkReferralValidity(customerReferral,user);

        Assert.assertTrue(isFuncAllowed);

    }




    @After
    public void tearDown(){

        for(CustomerReferral customerReferral: tempSet) {

            CustomerReferral delCustomerReferral = customerReferralService.findByCsrId(customerReferral.getCsrId());

            if ( delCustomerReferral != null ) {

                try {
                    customerReferralService.deleteCustomerReferral(delCustomerReferral.getCsrId());
                } catch (InspireNetzException e) {
                    e.printStackTrace();
                }

            }

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

}
