package com.inspirenetz.api.test.controller;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.inspirenetz.api.core.dictionary.IndicatorStatus;
import com.inspirenetz.api.core.dictionary.RedemptionVoucherStatus;
import com.inspirenetz.api.core.dictionary.RedemptionVoucherUpdateRequest;
import com.inspirenetz.api.core.dictionary.UserType;
import com.inspirenetz.api.core.domain.*;
import com.inspirenetz.api.core.service.*;
import com.inspirenetz.api.rest.exception.InspireNetzException;
import com.inspirenetz.api.test.config.*;
import com.inspirenetz.api.test.core.fixture.CustomerFixture;
import com.inspirenetz.api.test.core.fixture.RedemptionMerchantFixture;
import com.inspirenetz.api.test.core.fixture.RedemptionVoucherFixture;
import com.inspirenetz.api.test.core.fixture.UserFixture;
import com.inspirenetz.api.test.util.ControllerTestUtils;
import com.inspirenetz.api.util.AuthSessionUtils;
import com.inspirenetz.api.util.DBUtils;
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
@ContextConfiguration(classes  = {ApplicationTestConfig.class, PersistenceTestConfig.class, ServiceTestConfig.class, SecurityTestConfig.class, WebTestConfig.class, NotificationTestConfig.class})
public class RedemptionVoucherControllerTest {


    private static Logger log = LoggerFactory.getLogger(RedemptionVoucherControllerTest.class);

    private static MockMvc mockMvc;

    private static ObjectMapper mapper;

    @Autowired
    private RedemptionVoucherService redemptionVoucherService;

    @Autowired
    private WebApplicationContext wac;

    @Resource
    private FilterChainProxy springSecurityFilterChain;

    @Autowired
    @Qualifier("uds")
    protected UserDetailsService userDetailsService;

    @Autowired
    private CustomerService customerService;

    @Autowired
    private UserService userService;

    @Autowired
    private AuthSessionUtils authSessionUtils;

    @Autowired
    private RedemptionMerchantService redemptionMerchantService;

    // RedemptionVoucher object
    private RedemptionVoucher redemptionVoucher;

    UsernamePasswordAuthenticationToken principal;

    MockHttpSession session;

    // Map to hold the returned json
    Map<String,String> map = new HashMap<>();

    Set<RedemptionVoucher> tempSet = new HashSet<>(0);

    Set<RedemptionMerchant> tempSet1 = new HashSet<>(0);

    Set<Customer> customerSet = new HashSet<>(0);

    Set<User> userSet = new HashSet<>(0);

    @Before
    public void setUp()
    {
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

        // Create the redemptionVoucher

        RedemptionVoucherFixture redemptionVoucherFixture=new RedemptionVoucherFixture();

        redemptionVoucher = redemptionVoucherFixture.standardRedemptionVoucher();


    }

    @Test
    public void getPendingRedemptionVouchers() throws Exception  {

        //Add the data
        redemptionVoucher = redemptionVoucherService.saveRedemptionVoucher(redemptionVoucher);

        tempSet.add(redemptionVoucher);

        // Place the delete request
        MvcResult mvcResult = mockMvc.perform(get("/api/0.9/json/merchant/redemptionvoucher/pending/9538828853?&channel=2")
                .principal(principal)
                .session(session)
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)

        )
                .andReturn();

        String response = mvcResult.getResponse().getContentAsString();
        Assert.assertNotNull(response);
        log.info("RedemptionVoucherResponse: " + response);

        // Convert json response to HashMap
        Assert.assertTrue(response.contains("success"));


    }

    @Test
    public void checkRedemptionVoucherExists()   throws Exception  {

        // Create a temporary redemptionm merchant user
        User user = UserFixture.standardUser();
        user.setUsrLoginId("8123591676");
        user.setUsrUserType(UserType.REDEMPTION_MERCHANT_USER);
        user.setUsrMerchantNo(1000L);
        user = userService.saveUser(user);

        // Add the user to the set
        userSet.add(user);


        // Create the RedemptionVoucherMerchant
        Customer customer = CustomerFixture.standardCustomer();
        customer = customerService.saveCustomer(customer);

        RedemptionVoucherFixture redemptionVoucherFixture=new RedemptionVoucherFixture();
        RedemptionVoucher redemptionVoucher = redemptionVoucherFixture.standardRedemptionVoucher();
        redemptionVoucher.setRvrCustomerNo(customer.getCusCustomerNo());
        redemptionVoucher.setRvrMerchant(user.getUsrMerchantNo());
        redemptionVoucher = redemptionVoucherService.saveRedemptionVoucher(redemptionVoucher);
        log.info("Original RedemptionVouchers " + redemptionVoucher.toString());

        // Add the items
        tempSet.add(redemptionVoucher);
        customerSet.add(customer);

        // Place the delete request
        MvcResult mvcResult = mockMvc.perform(post("/api/0.9/json/merchant/redemptionvoucher/claim")
                .principal(principal)
                .session(session)
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .param("loyaltyid",customer.getCusLoyaltyId())
                .param("merchantloginid",user.getUsrLoginId())
                .param("vouchercode",redemptionVoucher.getRvrVoucherCode())

        )
                .andReturn();

        String response = mvcResult.getResponse().getContentAsString();
        Assert.assertNotNull(response);
        log.info("RedemptionVoucherResponse: " + response);

        // Convert json response to HashMap
        Assert.assertTrue(response.contains("success"));


    }

    @Test
    public void getRedemptionVoucherList()  throws Exception  {

        Customer customer = CustomerFixture.standardCustomer();
        customer.setCusLoyaltyId("1234456321");
        customer = customerService.saveCustomer(customer);

        RedemptionVoucherFixture redemptionVoucherFixture=new RedemptionVoucherFixture();
        RedemptionVoucher redemptionVoucher = redemptionVoucherFixture.standardRedemptionVoucher();
        redemptionVoucher.setRvrCustomerNo(customer.getCusCustomerNo());
        redemptionVoucher.setRvrLoyaltyId(customer.getCusLoyaltyId());
        redemptionVoucher = redemptionVoucherService.saveRedemptionVoucher(redemptionVoucher);

        tempSet.add(redemptionVoucher);

        // Place the delete request
        MvcResult mvcResult = mockMvc.perform(get("/api/0.9/json/merchant/redemptionvoucher/"+"voucherCode"+"/"+redemptionVoucher.getRvrVoucherCode())
                .principal(principal)
                .session(session)
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)

        )
                .andReturn();

        String response = mvcResult.getResponse().getContentAsString();
        Assert.assertNotNull(response);
        log.info("RedemptionVoucherResponse: " + response);

        // Convert json response to HashMap
        Assert.assertTrue(response.contains("success"));


    }

    @Test
    public void searchRedemptionVoucher()  throws Exception  {


        Long userNo = authSessionUtils.getUserNo();

        User user =userService.findByUsrUserNo(userNo);

        Long redemptionMerchantNo = user.getUsrThirdPartyVendorNo();

        RedemptionVoucherFixture redemptionVoucherFixture=new RedemptionVoucherFixture();
        RedemptionVoucher redemptionVoucher = redemptionVoucherFixture.standardRedemptionVoucher();
        redemptionVoucher.setRvrMerchant(redemptionMerchantNo);
        redemptionVoucher.setRvrLoyaltyId(user.getUsrLoginId());

        redemptionVoucher = redemptionVoucherService.saveRedemptionVoucher(redemptionVoucher);

        tempSet.add(redemptionVoucher);

        // Place the delete request
        MvcResult mvcResult = mockMvc.perform(get("/api/0.9/json/merchant/searchredemptionvoucher/0/0/" + DBUtils.covertToSqlDate("1970-01-01")+ "/"+DBUtils.covertToSqlDate("9999-01-01"))
                .principal(principal)
                .session(session)
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)

        )
                .andReturn();

        String response = mvcResult.getResponse().getContentAsString();
        Assert.assertNotNull(response);
        log.info("RedemptionVoucherResponse: " + response);

        // Convert json response to HashMap
        Assert.assertTrue(response.contains("success"));


    }

    @Test
    public void redemptionVoucherIsValid()  throws Exception  {



        RedemptionVoucher redemptionVoucher = RedemptionVoucherFixture.standardRedemptionVoucher();
        redemptionVoucher.setRvrVoucherCode("EXT100011114211");
        redemptionVoucher.setRvrExpiryDate(DBUtils.covertToSqlDate("2015-02-08"));
        redemptionVoucher.setRvrStatus(1);
        redemptionVoucher = redemptionVoucherService.saveRedemptionVoucher(redemptionVoucher);

       tempSet.add(redemptionVoucher);

        // Place the delete request
        MvcResult mvcResult = mockMvc.perform(post("/api/0.9/json/merchant/redemptionvoucher/validate" )
                .principal(principal)
                .session(session)
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .param("voucherCode",redemptionVoucher.getRvrVoucherCode())


        )
                .andReturn();

        String response = mvcResult.getResponse().getContentAsString();
        Assert.assertNotNull(response);
        log.info("RedemptionVoucherResponse: " + response);

        // Convert json response to HashMap
       Assert.assertTrue(response.contains("success"));


    }

    @Test
    public void voucherClaimForMerchantUser()  throws Exception  {



        RedemptionMerchantFixture redemptionMerchantFixture=new RedemptionMerchantFixture();

        RedemptionMerchant redemptionMerchant = redemptionMerchantService.saveRedemptionMerchant(redemptionMerchantFixture.standardRedemptionMerchant());

        log.info(redemptionMerchant.toString());

        // Add the items
        tempSet1.add(redemptionMerchant);

        Assert.assertNotNull(redemptionMerchant.getRemNo());

        RedemptionVoucher redemptionVoucher = RedemptionVoucherFixture.standardRedemptionVoucher();

        redemptionVoucher.setRvrVoucherCode("EXT1011111");

        redemptionVoucher.setRvrExpiryDate(DBUtils.covertToSqlDate("2015-01-30"));

        redemptionVoucher.setRvrMerchant(redemptionMerchant.getRemNo());

        redemptionVoucher.setRvrStatus(RedemptionVoucherStatus.NEW);

        RedemptionVoucher redemptionVoucher1=redemptionVoucherService.saveRedemptionVoucher(redemptionVoucher);

        Set<RedemptionMerchantLocation> location =new HashSet<>(0);

        location =redemptionMerchant.getRedemptionMerchantLocations();

        String merchantLocation="";

        for(RedemptionMerchantLocation redemptionMerchantLocation:location){


            merchantLocation =redemptionMerchantLocation.getRmlLocation();

            break;
        }

        // Place the delete request
        MvcResult mvcResult = mockMvc.perform(post("/api/0.9/json/merchantuser/redemptionvoucher/claim" )
                .principal(principal)
                .session(session)
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .param("voucherCode",redemptionVoucher.getRvrVoucherCode())
                .param("merchantLocation",merchantLocation)


        )
                .andReturn();

        String response = mvcResult.getResponse().getContentAsString();
        Assert.assertNotNull(response);
        log.info("RedemptionVoucherResponse: " + response);

        // Convert json response to HashMap
        Assert.assertTrue(response.contains("success"));


    }

    @Test
    public void updateRedemptionVoucher() throws Exception {


        RedemptionVoucher redemptionVoucher = RedemptionVoucherFixture.standardRedemptionVoucher();
        redemptionVoucher.setRvrVoucherCode("EXT100011113991");
        redemptionVoucher.setRvrExpiryDate(DBUtils.covertToSqlDate("2015-02-06"));
        RedemptionVoucher redemptionVoucher1=redemptionVoucherService.saveRedemptionVoucher(redemptionVoucher);

        tempSet.add(redemptionVoucher);

        tempSet.add(redemptionVoucher1);

        RedemptionVoucherUpdateRequest redemptionVoucherUpdateRequest =new RedemptionVoucherUpdateRequest();

        redemptionVoucherUpdateRequest.setRvrReqId(redemptionVoucher1.getRvrId());
        redemptionVoucherUpdateRequest.setRvrExpiryOption(IndicatorStatus.NO);
        redemptionVoucherUpdateRequest.setRvrReqVoucherCode("Asghhh");


        // Place the delete request
        MvcResult mvcResult = mockMvc.perform(post("/api/0.9/json/merchant/updatevoucher" )
                .principal(principal)
                .session(session)
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .param("rvrReqId",redemptionVoucherUpdateRequest.getRvrReqId().toString())
                .param("rvrReqVoucherCode",redemptionVoucherUpdateRequest.getRvrReqVoucherCode().toString())


        )
                .andReturn();

        String response = mvcResult.getResponse().getContentAsString();
        Assert.assertNotNull(response);
        log.info("updateRedemptionVoucher: " + response);

        // Convert json response to HashMap
        Assert.assertTrue(response.contains("success"));



    }


    @After
    public void tearDown() throws InspireNetzException {



        for(RedemptionVoucher redemptionVoucher: tempSet) {


            redemptionVoucherService.deleteRedemptionVoucher(redemptionVoucher.getRvrId());

        }
        for(Customer customer: customerSet) {


            customerService.deleteCustomer(customer.getCusCustomerNo());

        }

        for(RedemptionMerchant redemptionMerchant: tempSet1) {

            redemptionMerchantService.deleteRedemptionMerchant(redemptionMerchant.getRemNo());

        }

    }

}