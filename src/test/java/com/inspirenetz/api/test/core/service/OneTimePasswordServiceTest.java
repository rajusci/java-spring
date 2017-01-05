package com.inspirenetz.api.test.core.service;

import com.inspirenetz.api.core.dictionary.OTPRefType;
import com.inspirenetz.api.core.dictionary.OTPType;
import com.inspirenetz.api.core.domain.*;
import com.inspirenetz.api.core.service.*;
import com.inspirenetz.api.rest.exception.InspireNetzException;
import com.inspirenetz.api.test.config.*;
import com.inspirenetz.api.test.core.fixture.*;
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
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Created by sandheepgr on 30/4/14.
 */
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes  = {ApplicationTestConfig.class, PersistenceTestConfig.class, ServiceTestConfig.class, SecurityTestConfig.class, NotificationTestConfig.class})
public class OneTimePasswordServiceTest {


    private static Logger log = LoggerFactory.getLogger(OneTimePasswordServiceTest.class);

    @Autowired
    private OneTimePasswordService oneTimePasswordService;

    @Autowired
    private CustomerService customerService;

    Set<Customer> customerSet = new HashSet<>(0);
    Set<OneTimePassword> oneTimePasswordSet = new HashSet<>(0);

    @Test
    public void test1generateOTPCompatible() throws InspireNetzException {

        //Create Customer object and save
        Customer customer = CustomerFixture.standardCustomer();
        customer.setCusLoyaltyId("9742375875");
        customer = customerService.saveCustomer(customer);
        customerSet.add(customer);

        //Create OneTimePassword and save
        OneTimePassword oneTimePassword =OneTimePasswordFixture.standardOneTimePassword();
        oneTimePassword.setOtpMerchantNo(customer.getCusMerchantNo());
        oneTimePassword.setOtpCustomerNo(customer.getCusCustomerNo());
        oneTimePassword= oneTimePasswordService.saveOneTimePassword(oneTimePassword);
        oneTimePasswordSet.add(oneTimePassword);

        boolean isGenerated=oneTimePasswordService.generateOTPCompatible(customer.getCusMerchantNo(),customer.getCusLoyaltyId(),oneTimePassword.getOtpType());

        Assert.assertTrue(isGenerated);

        log.info("generateOTPCompatible Response : " +isGenerated);

    }

    @Test
    public void test1generateOTPGeneric() throws InspireNetzException {

        //Create Customer object and save
        Customer customer = CustomerFixture.standardCustomer();
        customer.setCusLoyaltyId("9742375875");
        customer = customerService.saveCustomer(customer);
        customerSet.add(customer);

        //Create OneTimePassword and save
        OneTimePassword oneTimePassword =OneTimePasswordFixture.standardOneTimePassword();
        oneTimePassword.setOtpMerchantNo(customer.getCusMerchantNo());
        oneTimePassword.setOtpCustomerNo(customer.getCusCustomerNo());
        oneTimePassword= oneTimePasswordService.saveOneTimePassword(oneTimePassword);
        oneTimePasswordSet.add(oneTimePassword);

        boolean isGenerated=oneTimePasswordService.generateOTPGeneric(customer.getCusMerchantNo(), OTPRefType.CUSTOMER,customer.getCusCustomerNo().toString(), oneTimePassword.getOtpType());

        Assert.assertTrue(isGenerated);

        log.info("generateOTPGeneric Response : " +isGenerated);

    }

    @Test
    public void testValidateOTPGeneric() throws InspireNetzException {

        //Create Customer object and save
        Customer customer = CustomerFixture.standardCustomer();
        customer.setCusLoyaltyId("9742375875");
        customer = customerService.saveCustomer(customer);
        customerSet.add(customer);

        //Create OneTimePassword and save
        OneTimePassword oneTimePassword =OneTimePasswordFixture.standardOneTimePassword();
        oneTimePassword.setOtpMerchantNo(customer.getCusMerchantNo());
        oneTimePassword.setOtpCustomerNo(customer.getCusCustomerNo());
        oneTimePassword= oneTimePasswordService.saveOneTimePassword(oneTimePassword);
        oneTimePasswordSet.add(oneTimePassword);

        boolean isGenerated=oneTimePasswordService.generateOTPGeneric(customer.getCusMerchantNo(), OTPRefType.CUSTOMER,customer.getCusCustomerNo().toString(), oneTimePassword.getOtpType());

        oneTimePassword=oneTimePasswordService.findByOtpMerchantNoAndOtpRefTypeAndOtpReferenceAndOtpTypeOrderByOtpCreateTimestampDesc(customer.getCusMerchantNo(), OTPRefType.CUSTOMER,customer.getCusCustomerNo().toString(), oneTimePassword.getOtpType());

        Integer result=oneTimePasswordService.validateOTPGeneric(customer.getCusMerchantNo(),OTPRefType.CUSTOMER,customer.getCusCustomerNo().toString(),oneTimePassword.getOtpType(),oneTimePassword.getOtpCode());

        Assert.assertTrue(isGenerated);

        log.info("ValidateOTPGeneric Response : " +result);

    }

    @Test
    public void testOTPValidation() throws InspireNetzException {

        //Create Customer object and save
        Customer customer = CustomerFixture.standardCustomer();
        customer.setCusLoyaltyId("9742375875");
        customer.setCusMobile("9742375875");
        customer.setCusMerchantNo(3L);
        customer = customerService.saveCustomer(customer);
        customerSet.add(customer);


        boolean isGenerated=oneTimePasswordService.generateOTPForPartnerRequest(customer.getCusMerchantNo(),customer.getCusLoyaltyId(), OTPType.PAY_POINTS);

        Assert.assertTrue(isGenerated);
    }

        @After
    public void tearDown() throws InspireNetzException {

        //tear down oneTimePassword
        for(OneTimePassword oneTimePassword : oneTimePasswordSet ) {

            oneTimePasswordService.deleteOneTimePassword(oneTimePassword.getOtpId());

        }

        //tear down customer
        for(Customer customer : customerSet ) {

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



}
