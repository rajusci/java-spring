package com.inspirenetz.api.test.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.inspirenetz.api.core.dictionary.*;
import com.inspirenetz.api.core.domain.*;
import com.inspirenetz.api.core.service.*;
import com.inspirenetz.api.rest.exception.InspireNetzException;
import com.inspirenetz.api.test.config.*;
import com.inspirenetz.api.test.core.fixture.*;
import com.inspirenetz.api.test.util.ControllerTestUtils;
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
import java.sql.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

/**
 * Created by ameen on 30/4/14.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration(classes  = {ApplicationTestConfig.class, PersistenceTestConfig.class, ServiceTestConfig.class, SecurityTestConfig.class, WebTestConfig.class, NotificationTestConfig.class,IntegrationTestConfig.class})
public class CashbackControllerTest {


    private static Logger log = LoggerFactory.getLogger(CashbackControllerTest.class);

    private static MockMvc mockMvc;

    private static ObjectMapper mapper;

    @Autowired
    private DrawChanceService drawChanceService;

    @Autowired
    private WebApplicationContext wac;

    @Resource
    private FilterChainProxy springSecurityFilterChain;

    @Autowired
    @Qualifier("uds")
    protected UserDetailsService userDetailsService;

    @Autowired
    RedemptionMerchantService redemptionMerchantService;

    @Autowired
    CustomerService customerService;

    @Autowired
    UserService userService;

    @Autowired
    CustomerRewardBalanceService customerRewardBalanceService;

    @Autowired
    CustomerRewardExpiryService customerRewardExpiryService;

    @Autowired
    private CashBackService cashBackService;

    @Autowired
    OneTimePasswordService oneTimePasswordService;

    Set<RedemptionMerchant> merchantSet = new HashSet<>(0);
    Set<User> userSet = new HashSet<>(0);
    Set<CustomerRewardBalance> balanceSet = new HashSet<>(0);
    Set<CustomerRewardExpiry> expirySet = new HashSet<>(0);

    // DrawChance object
    private DrawChance drawChance;

    UsernamePasswordAuthenticationToken principal;

    MockHttpSession session;

    // Map to hold the returned json
    Map<String,String> map = new HashMap<>();

    Set<DrawChance> tempSet = new HashSet<>(0);

    Set<Customer> customerSet = new HashSet<>(0);

    @Before
    public void setUp()
    {
        try {

            // Set the principal
            principal = ControllerTestUtils.getPrincipal(ControllerTestUtils.TEST_REDEMPTION_MERCHANT,userDetailsService);

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

        // Create the drawChance

        DrawChanceFixture drawChanceFixture=new DrawChanceFixture();

        drawChance = drawChanceFixture.standardDrawChance();


    }

    @Test
    public void doCashbackForPartner() throws Exception  {

        Customer customer = CustomerFixture.standardCustomer();
        customer.setCusLoyaltyId("9538052636");
        customer.setCusStatus(CustomerStatus.ACTIVE);
        customer.setCusMobile("9538052636");
        customer = customerService.saveCustomer(customer);

        customerSet.add(customer);


        User customerUser = UserFixture.standardUser();
        customerUser.setUsrLoginId(customer.getCusLoyaltyId());
        customerUser.setUsrStatus(UserStatus.ACTIVE);
        customerUser = userService.saveUser(customerUser);
        userSet.add(customerUser);

        CustomerRewardBalance customerRewardBalance = CustomerRewardBalanceFixture.standardRewardBalance();

        customerRewardBalance.setCrbLoyaltyId(customer.getCusLoyaltyId());
        customerRewardBalance.setCrbRewardCurrency(1L);
        customerRewardBalance.setCrbMerchantNo(1L);
        customerRewardBalance = customerRewardBalanceService.saveCustomerRewardBalance(customerRewardBalance);

        balanceSet.add(customerRewardBalance);

        CustomerRewardExpiry customerRewardExpiry = CustomerRewardExpiryFixture.standardRewardExpiry();
        customerRewardExpiry.setCreLoyaltyId(customer.getCusLoyaltyId());
        customerRewardExpiry.setCreRewardCurrencyId(1L);
        customerRewardExpiry.setCreExpiryDt(Date.valueOf("2016-12-31"));
        customerRewardExpiry = customerRewardExpiryService.saveCustomerRewardExpiry(customerRewardExpiry);

        expirySet.add(customerRewardExpiry);

        boolean isGenerated=oneTimePasswordService.generateOTPCompatible(customer.getCusMerchantNo(),customer.getCusLoyaltyId(),OTPType.PAY_POINTS);

        Assert.assertTrue(isGenerated);

        log.info("generateOTPCompatible Response : " +isGenerated);

        //get the generated one time password

        OneTimePassword oneTimePassword = oneTimePasswordService.findByOtpMerchantNoAndOtpRefTypeAndOtpReferenceAndOtpTypeOrderByOtpCreateTimestampDesc(customer.getCusMerchantNo(),OTPRefType.CUSTOMER,customer.getCusCustomerNo().toString(),OTPType.PAY_POINTS);


        // Place the delete request
        MvcResult mvcResult = mockMvc.perform(post("/api/0.9/json/merchant/partner/cashback")
                .principal(principal)
                .session(session)
                .param("mobile", customer.getCusMobile())
                .param("merchantNo", customer.getCusMerchantNo() + "")
                .param("amount", "10")
                .param("channel", RequestChannel.STORE + "")
                .param("reference", "10001")
                .param("otpCode", oneTimePassword.getOtpCode())
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)

        )
                .andReturn();

        String response = mvcResult.getResponse().getContentAsString();



    }


    @After
    public void tearDown() throws InspireNetzException {



        for(RedemptionMerchant merchant : merchantSet){

            redemptionMerchantService.deleteRedemptionMerchant(merchant.getRemNo());
        }

        for(User user : userSet){

            userService.deleteUser(user);

        }

        for(Customer customer :customerSet){

            customerService.deleteCustomer(customer.getCusCustomerNo());
        }

        for(CustomerRewardBalance customerRewardBalance :balanceSet){

            customerRewardBalanceService.deleteCustomerRewardBalance(customerRewardBalance);
        }

        for(CustomerRewardExpiry customerRewardExpiry :expirySet){

            customerRewardExpiryService.deleteCustomerRewardExpiry(customerRewardExpiry);
        }

    }

}

