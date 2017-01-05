package com.inspirenetz.api.test.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.inspirenetz.api.core.dictionary.IndicatorStatus;
import com.inspirenetz.api.core.dictionary.OTPType;
import com.inspirenetz.api.core.dictionary.TransactionType;
import com.inspirenetz.api.core.dictionary.TransferPointRequest;
import com.inspirenetz.api.core.domain.*;
import com.inspirenetz.api.core.service.*;
import com.inspirenetz.api.rest.exception.InspireNetzException;
import com.inspirenetz.api.test.config.*;
import com.inspirenetz.api.test.core.fixture.*;
import com.inspirenetz.api.test.util.ControllerTestUtils;
import com.inspirenetz.api.util.AuthSessionUtils;
import com.inspirenetz.api.util.DBUtils;
import com.inspirenetz.api.util.GeneralUtils;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.FilterChainProxy;
import org.springframework.security.web.context.HttpSessionSecurityContextRepository;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import javax.annotation.Resource;
import java.util.*;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

/**
 * Created by sandheepgr on 30/4/14.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration(classes  = {ApplicationTestConfig.class, PersistenceTestConfig.class, ServiceTestConfig.class, SecurityTestConfig.class, WebTestConfig.class, NotificationTestConfig.class})
public class OneTimePasswordControllerTest {


    private static Logger log = LoggerFactory.getLogger(OneTimePasswordControllerTest.class);


    private static MockMvc mockMvc;

    private static ObjectMapper mapper;

    @Autowired
    AuthSessionUtils authSessionUtils;


    @Autowired
    private WebApplicationContext wac;

    @Resource
    private FilterChainProxy springSecurityFilterChain;

    @Autowired
    @Qualifier("uds")
    protected UserDetailsService userDetailsService;

    @Autowired
    DrawChanceService drawChanceService;



    Set<CustomerRewardBalance> tempSet = new HashSet<>(0);

    Set<Customer> customerDrawSet = new HashSet<>(0);

    Set<DrawChance> drawChancesSet = new HashSet<>(0);




    @Autowired
    private GeneralUtils generalUtils;

    @Autowired
    private OneTimePasswordService oneTimePasswordService;

    @Autowired
    private CustomerService customerService;

    Set<Customer> customerSet = new HashSet<>(0);

    Set<OneTimePassword> oneTimePasswordSet = new HashSet<>(0);


    // CustomerRewardBalance object
    private CustomerRewardBalance customerRewardBalance;

    UsernamePasswordAuthenticationToken principal;

    MockHttpSession session;

    // Map to hold the returned json
    Map<String,String> map = new HashMap<>();




    @Before
    public void setUp()
    {
        useMerchantUser();
    }


    public void useMerchantUser() {

        try {

            // Set the principal
            principal = ControllerTestUtils.getPrincipal(ControllerTestUtils.TEST_MERCHANT_USER_LOGINID,userDetailsService);

            // Create the Session
            session = new MockHttpSession();


            session.setAttribute(
                    HttpSessionSecurityContextRepository.SPRING_SECURITY_CONTEXT_KEY,
                    new ControllerTestUtils.MockSecurityContext(principal));

            //mockMvc = webAppContextSetup(this.wac).build();
            this.mockMvc = MockMvcBuilders
                    .webAppContextSetup(this.wac)
                    .addFilters(this.springSecurityFilterChain)
                    .build();

            init();

        } catch (Exception e) {
            e.printStackTrace();
        }

    }


    public void useCustomerUser() {

        try {

            // Set the principal
            principal = ControllerTestUtils.getPrincipal(ControllerTestUtils.TEST_MERCHANT_USER_LOGINID,userDetailsService);

            // Create the Session
            session = new MockHttpSession();


            session.setAttribute(
                    HttpSessionSecurityContextRepository.SPRING_SECURITY_CONTEXT_KEY,
                    new ControllerTestUtils.MockSecurityContext(principal));

            //mockMvc = webAppContextSetup(this.wac).build();
            this.mockMvc = MockMvcBuilders
                    .webAppContextSetup(this.wac)
                    .addFilters(this.springSecurityFilterChain)
                    .build();

            init();

        } catch (Exception e) {
            e.printStackTrace();
        }

    }


    private void init() throws Exception{


        // Create the Mapper object
        mapper = new ObjectMapper();

        // Create the customerRewardBalance
        customerRewardBalance = CustomerRewardBalanceFixture.standardRewardBalance();


    }




    @Test
    public void generateOTPCompatible() throws Exception  {

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

        // Place the delete request
        MvcResult mvcResult = mockMvc.perform(get("/api/0.9/json/merchant/otp")
                                                    .principal(principal)
                                                    .session(session)
                                                    .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                                                    .param("card_number", customer.getCusLoyaltyId())
                                                    .param("otp_type", oneTimePassword.getOtpType().toString())
                                            )
                                            .andReturn();

        String response = mvcResult.getResponse().getContentAsString();

        Assert.assertNotNull(response);

        log.info("generateOTPCompatible Response: " + response);

        Assert.assertTrue(response.contains("success"));


    }

    @Test
    public void generateOTPGeneric() throws Exception  {

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

        // Place the delete request
        MvcResult mvcResult = mockMvc.perform(get("/api/0.9/json/merchant/otp")
                .principal(principal)
                .session(session)
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .param("card_number", customer.getCusLoyaltyId())
                .param("otp_type", oneTimePassword.getOtpType().toString())
        )
                .andReturn();

        String response = mvcResult.getResponse().getContentAsString();

        Assert.assertNotNull(response);

        log.info("generateOTPCompatible Response: " + response);

        Assert.assertTrue(response.contains("success"));


    }

    @Test
    public void generateOtpForPartnerRequest() throws Exception  {

        //Create Customer object and save
        Customer customer = CustomerFixture.standardCustomer();
        customer.setCusLoyaltyId("9742375875");
        customer.setCusMobile("9742375875");
        customer = customerService.saveCustomer(customer);
        customerSet.add(customer);


        // Place the delete request
        MvcResult mvcResult = mockMvc.perform(get("/api/0.9/json/merchant/pay/otp")
                .principal(principal)
                .session(session)
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .param("mobile", customer.getCusMobile())
                .param("merchantNo",customer.getCusMerchantNo()+"")
                .param("otpType", OTPType.PAY_POINTS+"")
        )
                .andReturn();

        String response = mvcResult.getResponse().getContentAsString();

        Assert.assertNotNull(response);

        log.info("generateOTPCompatible Response: " + response);

        Assert.assertTrue(response.contains("success"));


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

}
