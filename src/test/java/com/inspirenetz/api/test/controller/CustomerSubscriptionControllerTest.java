package com.inspirenetz.api.test.controller;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.inspirenetz.api.core.domain.Customer;
import com.inspirenetz.api.core.domain.CustomerSubscription;
import com.inspirenetz.api.core.service.CustomerSubscriptionService;
import com.inspirenetz.api.test.config.*;
import com.inspirenetz.api.test.core.fixture.CustomerSubscriptionFixture;
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
public class CustomerSubscriptionControllerTest {


    private static Logger log = LoggerFactory.getLogger(CustomerSubscriptionControllerTest.class);

    private static MockMvc mockMvc;

    private static ObjectMapper mapper;

    @Autowired
    private CustomerSubscriptionService customerSubscriptionService;

    @Autowired
    private WebApplicationContext wac;

    @Resource
    private FilterChainProxy springSecurityFilterChain;

    @Autowired
    @Qualifier("uds")
    protected UserDetailsService userDetailsService;

    // CustomerSubscription object
    private CustomerSubscription customerSubscription;

    UsernamePasswordAuthenticationToken principal;

    // Create the tempset
    Set<CustomerSubscription> tempSet = new HashSet<>(0);


    MockHttpSession session;

    // Map to hold the returned json
    Map<String,String> map = new HashMap<>();




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

        // Create the customerSubscription
        customerSubscription = CustomerSubscriptionFixture.standardCustomerSubscription();


    }




    @Test
    public void saveCustomerSubscription() throws Exception {


        MvcResult mvcResult = mockMvc.perform(post("/api/0.9/json/merchant/customer/subscription")
                                                .principal(principal)
                                                .session(session)
                                                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                                                .param("csuCustomerNo",customerSubscription.getCsuCustomerNo().toString())
                                                .param("csuProductCode",customerSubscription.getCsuProductCode().toString())
                                                .param("csuPoints", customerSubscription.getCsuPoints().toString())
                                                .param("csuServiceNo", customerSubscription.getCsuServiceNo().toString())
                                              )
                                        .andReturn();
        String response = mvcResult.getResponse().getContentAsString();
        Assert.assertNotNull(response);
        log.info("CustomerSubscriptionResponse: " + response);

        // Convert json response to HashMap
        map = mapper.readValue(response, new TypeReference<Map<String, String>>() { });
        Assert.assertTrue(map.get("status").equals("success"));


        log.info("CustomerSubscription created");


    }

    @Test
    public void deleteCustomerSubscription() throws  Exception {

        // Create the customerSubscription
        customerSubscription = customerSubscriptionService.saveCustomerSubscription(customerSubscription);
        Assert.assertNotNull(customerSubscription.getCsuId());
        log.info("CustomerSubscription created");


        // Add the tempSet
        tempSet.add(customerSubscription);


        // Place the delete request
        MvcResult mvcResult = mockMvc.perform(post("/api/0.9/json/merchant/customer/subscription/delete/" + customerSubscription.getCsuId())
                                                    .principal(principal)
                                                    .session(session)
                                                    .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                                            )
                                            .andReturn();
        String response = mvcResult.getResponse().getContentAsString();
        Assert.assertNotNull(response);
        log.info("CustomerSubscriptionResponse: " + response);

        // Convert json response to HashMap
        map = mapper.readValue(response, new TypeReference<Map<String, String>>() { });
        Assert.assertTrue(map.get("status").equals("success"));


        log.info("CustomerSubscription deleted");


        // Remove from the tempSet as it deleted successfully
        tempSet.remove(customerSubscription);


    }

    @Test
    public void listCustomerSubscriptions() throws Exception  {

        //Add the data
        customerSubscription = customerSubscriptionService.saveCustomerSubscription(customerSubscription);

        // Add to the tempSet
        tempSet.add(customerSubscription);

        // Place the delete request
        MvcResult mvcResult = mockMvc.perform(get("/api/0.9/json/customer/subscriptions/"+customerSubscription.getCsuCustomerNo())
                .principal(principal)
                .session(session)
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)

        )
                .andReturn();

        String response = mvcResult.getResponse().getContentAsString();
        Assert.assertNotNull(response);
        log.info("CustomerSubscriptionResponse: " + response);

        // Convert json response to HashMap
        Assert.assertTrue(response.contains("success"));


    }

    @Test
    public void getCustomerSubscriptionInfo()   throws Exception  {

        //Add the data
        customerSubscription = customerSubscriptionService.saveCustomerSubscription(customerSubscription);

        // Add to the tempSet
        // Place the delete request
        MvcResult mvcResult = mockMvc.perform(get("/api/0.9/json/customer/subscription/"+customerSubscription.getCsuId())
                .principal(principal)
                .session(session)
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)

        )
                .andReturn();

        String response = mvcResult.getResponse().getContentAsString();
        Assert.assertNotNull(response);
        log.info("CustomerSubscriptionResponse: " + response);

        // Convert json response to HashMap
        Assert.assertTrue(response.contains("success"));


    }




    @After
    public void tearDown() {

        for(CustomerSubscription customerSubscription: tempSet) {

            customerSubscriptionService.deleteCustomerSubscription(customerSubscription.getCsuId());

        }
    }

}
