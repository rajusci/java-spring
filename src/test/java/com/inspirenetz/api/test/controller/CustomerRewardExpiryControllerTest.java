package com.inspirenetz.api.test.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.inspirenetz.api.config.SecurityConfig;
import com.inspirenetz.api.core.domain.CustomerRewardExpiry;
import com.inspirenetz.api.core.service.CustomerRewardExpiryService;
import com.inspirenetz.api.test.config.*;
import com.inspirenetz.api.test.core.fixture.CustomerRewardExpiryFixture;
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
import java.util.*;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

/**
 * Created by sandheepgr on 30/4/14.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration(classes  = {ApplicationTestConfig.class, PersistenceTestConfig.class, ServiceTestConfig.class, SecurityTestConfig.class, WebTestConfig.class, NotificationTestConfig.class})
public class CustomerRewardExpiryControllerTest {


    private static Logger log = LoggerFactory.getLogger(CustomerRewardExpiryControllerTest.class);


    private static MockMvc mockMvc;

    private static ObjectMapper mapper;

    @Autowired
    private CustomerRewardExpiryService customerRewardExpiryService;

    Set<CustomerRewardExpiry> tempSet = new HashSet<>(0);

    @Autowired
    private WebApplicationContext wac;

    @Resource
    private FilterChainProxy springSecurityFilterChain;

    @Autowired
    @Qualifier("uds")
    protected UserDetailsService userDetailsService;

    // CustomerRewardExpiry object
    private CustomerRewardExpiry customerRewardExpiry;

    UsernamePasswordAuthenticationToken principal;

    MockHttpSession session;

    // Map to hold the returned json
    Map<String,String> map = new HashMap<>();




    @Before
    public void setUp()
    {
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

        // Create the customerRewardExpiry
        customerRewardExpiry = CustomerRewardExpiryFixture.standardRewardExpiry();


    }




    @Test
    public void getCustomerRewardExpiry() throws Exception  {

        // Get the set of customerRewardExpirys
        Set<CustomerRewardExpiry> customerRewardExpirySet = CustomerRewardExpiryFixture.standardCustomerRewardExpirys();
        List<CustomerRewardExpiry>  customerRewardExpiryList = new ArrayList<CustomerRewardExpiry>();
        customerRewardExpiryList.addAll(customerRewardExpirySet);
        customerRewardExpiryService.saveAll(customerRewardExpiryList);

        tempSet.addAll(customerRewardExpiryList);

        // Place the delete request
        MvcResult mvcResult = mockMvc.perform(get("/api/0.9/json/merchant/customer/rewardexpiry/"+customerRewardExpiry.getCreLoyaltyId()+"/"+customerRewardExpiry.getCreRewardCurrencyId())
                .principal(principal)
                .session(session)
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
        )
                .andReturn();

        String response = mvcResult.getResponse().getContentAsString();
        Assert.assertNotNull(response);
        log.info("CustomerRewardExpiryResponse: " + response);

        // Convert json response to HashMap
        Assert.assertTrue(response.contains("success"));


    }

    @Test
    public void getRewardExpiryForCustomer() throws Exception  {

    /*    // Get the set of customerRewardExpirys
        Set<CustomerRewardExpiry> customerRewardExpirySet = CustomerRewardExpiryFixture.standardCustomerRewardExpirys();
        List<CustomerRewardExpiry>  customerRewardExpiryList = new ArrayList<CustomerRewardExpiry>();
        customerRewardExpiryList.addAll(customerRewardExpirySet);
        customerRewardExpiryService.saveAll(customerRewardExpiryList);

        tempSet.addAll(customerRewardExpiryList);*/

        // Place the delete request
        MvcResult mvcResult = mockMvc.perform(get("/api/0.9/json/customer/rewardexpiry/1/0")
                .principal(principal)
                .session(session)
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
        )
                .andReturn();

        String response = mvcResult.getResponse().getContentAsString();
        Assert.assertNotNull(response);
        log.info("CustomerRewardExpiryResponse: " + response);

        // Convert json response to HashMap
        Assert.assertTrue(response.contains("success"));


    }




    @After
    public void tearDown() {



        for(CustomerRewardExpiry customerRewardExpiry: tempSet) {

            customerRewardExpiryService.deleteCustomerRewardExpiry(customerRewardExpiry);

        }
    }

}
