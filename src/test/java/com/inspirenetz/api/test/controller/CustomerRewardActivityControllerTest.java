package com.inspirenetz.api.test.controller;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.inspirenetz.api.core.domain.Customer;
import com.inspirenetz.api.core.domain.CustomerRewardActivity;
import com.inspirenetz.api.core.domain.LinkedLoyalty;
import com.inspirenetz.api.core.service.CustomerService;
import com.inspirenetz.api.core.service.CustomerRewardActivityService;
import com.inspirenetz.api.core.service.LinkedLoyaltyService;
import com.inspirenetz.api.rest.exception.InspireNetzException;
import com.inspirenetz.api.test.config.*;
import com.inspirenetz.api.test.core.fixture.CustomerFixture;
import com.inspirenetz.api.test.core.fixture.CustomerRewardActivityFixture;
import com.inspirenetz.api.test.core.fixture.LinkedLoyaltyFixture;
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
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

/**
 * Created by sandheepgr on 30/4/14.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration(classes  = {ApplicationTestConfig.class, PersistenceTestConfig.class, ServiceTestConfig.class, SecurityTestConfig.class, WebTestConfig.class, NotificationTestConfig.class})
public class CustomerRewardActivityControllerTest {


    private static Logger log = LoggerFactory.getLogger(CustomerRewardActivityControllerTest.class);

    private static MockMvc mockMvc;

    private static ObjectMapper mapper;

    @Autowired
    private CustomerRewardActivityService customerRewardActivityService;

    @Autowired
    private WebApplicationContext wac;

    @Resource
    private FilterChainProxy springSecurityFilterChain;

    @Autowired
    CustomerService customerService;

    @Autowired
    @Qualifier("uds")
    protected UserDetailsService userDetailsService;


    // CustomerRewardActivity object
    private CustomerRewardActivity customerRewardActivity;

    UsernamePasswordAuthenticationToken principal;

    MockHttpSession session;

    // Map to hold the returned json
    Map<String,String> map = new HashMap<>();

    Set<CustomerRewardActivity> tempSet = new HashSet<>(0);

    Set<Customer> customerSet = new HashSet<>(0);

    Set<LinkedLoyalty> linkLoyaltySet = new HashSet<>(0);



    @Before
    public void setUp()
    {
        useMerchantSession();
    }


    protected void useMerchantSession() {

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


    protected void useCustomerSession() {

        try {

            // Set the principal
            principal = ControllerTestUtils.getPrincipal(ControllerTestUtils.TEST_CUSTOMER_LOGINID,userDetailsService);

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

        // Create the customerRewardActivity
        customerRewardActivity = CustomerRewardActivityFixture.standardCustomerRewardActivity();


    }




    @Test
    public void registerCustomerRewardActivity() throws Exception {


        MvcResult mvcResult = mockMvc.perform(post("/api/0.9/json/merchant/customerrewards/register")
                                                .principal(principal)
                                                .session(session)
                                                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                                                .param("craCustomerNo",customerRewardActivity.getCraCustomerNo().toString())
                                                .param("craType",customerRewardActivity.getCraType().toString())
                                                .param("craActivityRef", customerRewardActivity.getCraActivityRef().toString())
                                              )
                                        .andReturn();
        String response = mvcResult.getResponse().getContentAsString();
        Assert.assertNotNull(response);
        log.info("CustomerRewardActivityResponse: " + response);

        // Convert json response to HashMap
        map = mapper.readValue(response, new TypeReference<Map<String, String>>() { });
        Assert.assertTrue(map.get("status").equals("success"));


        log.info("CustomerRewardActivity created");


    }


    @Test
    public void getCustomerRewardActivityByCustomerNo() throws Exception  {

        //Add the data
        customerRewardActivity = customerRewardActivityService.saveCustomerRewardActivity(customerRewardActivity);

        tempSet.add(customerRewardActivity);

        // Place the delete request
        MvcResult mvcResult = mockMvc.perform(get("/api/0.9/json/merchant/customerrewards/"+customerRewardActivity.getCraCustomerNo())
                .principal(principal)
                .session(session)
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)

        )
                .andReturn();

        String response = mvcResult.getResponse().getContentAsString();
        Assert.assertNotNull(response);
        log.info("CustomerRewardActivityResponse: " + response);

        // Convert json response to HashMap
        Assert.assertTrue(response.contains("success"));


    }

    @Test
    public void getCustomerRewardActivityByCustomerNoAndType() throws Exception  {

        //Add the data
        customerRewardActivity = customerRewardActivityService.saveCustomerRewardActivity(customerRewardActivity);

        tempSet.add(customerRewardActivity);

        // Place the delete request
        MvcResult mvcResult = mockMvc.perform(get("/api/0.9/json/merchant/customerrewards/"+customerRewardActivity.getCraCustomerNo()+"/"+customerRewardActivity.getCraType())
                .principal(principal)
                .session(session)
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)

        )
                .andReturn();

        String response = mvcResult.getResponse().getContentAsString();
        Assert.assertNotNull(response);
        log.info("CustomerRewardActivityResponse: " + response);

        // Convert json response to HashMap
        Assert.assertTrue(response.contains("success"));


    }


    @Test
    public void getCustomerRewardActivityInfo()   throws Exception  {

        //Add the data
        customerRewardActivity = customerRewardActivityService.saveCustomerRewardActivity(customerRewardActivity);

        tempSet.add(customerRewardActivity);

        // Place the delete request
        MvcResult mvcResult = mockMvc.perform(get("/api/0.9/json/merchant/customerrewards/info/"+customerRewardActivity.getCraId())
                .principal(principal)
                .session(session)
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)

        )
                .andReturn();

        String response = mvcResult.getResponse().getContentAsString();
        Assert.assertNotNull(response);
        log.info("CustomerRewardActivityResponse: " + response);

        // Convert json response to HashMap
        Assert.assertTrue(response.contains("success"));


    }
    @Test
    public void registerCustomerRewardActivityLoyaltyId() throws Exception {


        Customer customer = CustomerFixture.standardCustomer();
        customerService.saveCustomer(customer);
        Assert.assertNotNull(customer.getCusCustomerNo());
        log.info("Customer created");

        customerSet.add(customer);

        tempSet.add(customerRewardActivity);

        MvcResult mvcResult = mockMvc.perform(post("/api/0.9/json/merchant/customerreward/register")
                .principal(principal)
                .session(session)
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .param("craLoyaltyId", customer.getCusLoyaltyId())
                .param("craType", customerRewardActivity.getCraType().toString())
                .param("craActivityRef", customerRewardActivity.getCraActivityRef().toString())
        )
                .andReturn();
        String response = mvcResult.getResponse().getContentAsString();
        Assert.assertNotNull(response);
        log.info("CustomerRewardActivityResponse: " + response);

        // Convert json response to HashMap
        map = mapper.readValue(response, new TypeReference<Map<String, String>>() { });
        Assert.assertTrue(map.get("status").equals("success"));


        log.info("CustomerRewardActivity created");




    }




    @After
    public void tearDown() throws InspireNetzException {

        for(CustomerRewardActivity customerRewardActivity: tempSet) {

            customerRewardActivityService.deleteCustomerRewardActivity(customerRewardActivity);

        }

        for(Customer customer:customerSet){

            customerService.deleteCustomer(customer.getCusCustomerNo());
        }



    }

}
