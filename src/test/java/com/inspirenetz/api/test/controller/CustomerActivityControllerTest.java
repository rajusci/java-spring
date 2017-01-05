package com.inspirenetz.api.test.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.inspirenetz.api.core.domain.Customer;
import com.inspirenetz.api.core.domain.CustomerActivity;
import com.inspirenetz.api.core.service.CustomerService;
import com.inspirenetz.api.core.service.CustomerActivityService;
import com.inspirenetz.api.rest.exception.InspireNetzException;
import com.inspirenetz.api.test.config.*;
import com.inspirenetz.api.test.core.fixture.CustomerActivityFixture;
import com.inspirenetz.api.test.util.ControllerTestUtils;
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

/**
 * Created by ameen on 30/4/14.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration(classes  = {ApplicationTestConfig.class, PersistenceTestConfig.class, ServiceTestConfig.class, SecurityTestConfig.class, WebTestConfig.class, NotificationTestConfig.class})
public class CustomerActivityControllerTest {


    private static Logger log = LoggerFactory.getLogger(CustomerActivityControllerTest.class);

    private static MockMvc mockMvc;

    private static ObjectMapper mapper;

    @Autowired
    private CustomerActivityService customerActivityService;

    @Autowired
    private WebApplicationContext wac;

    @Resource
    private FilterChainProxy springSecurityFilterChain;

    @Autowired
    @Qualifier("uds")
    protected UserDetailsService userDetailsService;

    @Autowired
    private CustomerService customerService;

    // CustomerActivity object
    private CustomerActivity customerActivity;

    UsernamePasswordAuthenticationToken principal;

    MockHttpSession session;

    // Map to hold the returned json
    Map<String,String> map = new HashMap<>();

    Set<CustomerActivity> tempSet = new HashSet<>(0);

    Set<Customer> customerSet = new HashSet<>(0);

    @Before
    public void setUp()
    {
        try {

            // Set the principal
            principal = ControllerTestUtils.getPrincipal(ControllerTestUtils.TEST_MERCHANT_ADMIN_LOGINID,userDetailsService);

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


    private void useCustomerSession() {

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

        // Create the customerActivity

        CustomerActivityFixture customerActivityFixture=new CustomerActivityFixture();

        customerActivity = customerActivityFixture.standardCustomerActivity();


    }

    @Test
    public void searchCustomerActivity() throws Exception  {

        //Add the data
        customerActivity = customerActivityService.saveCustomerActivity(customerActivity);

        tempSet.add(customerActivity);

        // Place the delete request
        MvcResult mvcResult = mockMvc.perform(get("/api/0.9/json/merchant/customer/activities")
                .principal(principal)
                .session(session)
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .param("loyaltyId",customerActivity.getCuaLoyaltyId())
                .param("type",customerActivity.getCuaActivityType().toString())
                .param("fromDate",  DBUtils.covertToSqlDate("1970-01-01").toString())
                .param("endDate", DBUtils.covertToSqlDate("2015-01-01").toString())
        )
                .andReturn();

        String response = mvcResult.getResponse().getContentAsString();
        Assert.assertNotNull(response);
        log.info("CustomerActivityResponse: " + response);

        // Convert json response to HashMap
        Assert.assertTrue(response.contains("success"));


    }

    @Test
    public void getRecentActivities() throws Exception  {

        useCustomerSession();

        // Place the delete request
        MvcResult mvcResult = mockMvc.perform(get("/api/0.9/json/merchant/customer/activities/compatible")
                .principal(principal)
                .session(session)
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .param("merchant_no","1"))
                .andReturn();

        String response = mvcResult.getResponse().getContentAsString();
        Assert.assertNotNull(response);
        log.info("CustomerActivityResponse: " + response);

        // Convert json response to HashMap
        Assert.assertTrue(response.contains("success"));


    }

    @Test
    public void getUserActivity() throws Exception  {

        useCustomerSession();

        //Add the data
        customerActivity.setCuaLoyaltyId(ControllerTestUtils.TEST_CUSTOMER_LOGINID);

        customerActivity = customerActivityService.saveCustomerActivity(customerActivity);

        tempSet.add(customerActivity);

        // Place the delete request
        MvcResult mvcResult = mockMvc.perform(get("/api/0.9/json/user/activities")
                .principal(principal)
                .session(session)
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .param("merchantNo","0"))
                .andReturn();

        String response = mvcResult.getResponse().getContentAsString();
        Assert.assertNotNull(response);
        log.info("CustomerActivityResponse: " + response);

        // Convert json response to HashMap
        Assert.assertTrue(response.contains("success"));


    }


    @After
    public void tearDown() throws InspireNetzException {



        for(CustomerActivity customerActivity: tempSet) {


            customerActivityService.deleteCustomerActivity(customerActivity.getCuaId());

        }

    }

}