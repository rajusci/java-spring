package com.inspirenetz.api.test.controller;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.inspirenetz.api.core.domain.Customer;
import com.inspirenetz.api.core.domain.LinkedLoyalty;
import com.inspirenetz.api.core.domain.RedemptionMerchantLocation;
import com.inspirenetz.api.core.service.CustomerService;
import com.inspirenetz.api.core.service.LinkedLoyaltyService;
import com.inspirenetz.api.core.service.RedemptionMerchantLocationService;
import com.inspirenetz.api.test.config.*;
import com.inspirenetz.api.test.core.fixture.RedemptionMerchantLocationFixture;
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
 * Created by ameen on 30/4/14.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration(classes  = {ApplicationTestConfig.class, PersistenceTestConfig.class, ServiceTestConfig.class, SecurityTestConfig.class, WebTestConfig.class, NotificationTestConfig.class})
public class RedemptionMerchantLocationControllerTest {


    private static Logger log = LoggerFactory.getLogger(RedemptionMerchantLocationControllerTest.class);

    private static MockMvc mockMvc;

    private static ObjectMapper mapper;

    @Autowired
    private RedemptionMerchantLocationService redemptionMerchantLocationService;

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
    private LinkedLoyaltyService linkedLoyaltyService;

    // RedemptionMerchantLocation object
    private RedemptionMerchantLocation redemptionMerchantLocation;

    UsernamePasswordAuthenticationToken principal;

    MockHttpSession session;

    // Map to hold the returned json
    Map<String,String> map = new HashMap<>();

    Set<RedemptionMerchantLocation> tempSet = new HashSet<>(0);

    Set<Customer> customerSet = new HashSet<>(0);

    Set<LinkedLoyalty> linkLoyaltySet = new HashSet<>(0);


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


    private void init() throws Exception{


        // Create the Mapper object
        mapper = new ObjectMapper();

        // Create the redemptionMerchantLocation

        RedemptionMerchantLocationFixture redemptionMerchantLocationFixture=new RedemptionMerchantLocationFixture();

        redemptionMerchantLocation = redemptionMerchantLocationFixture.standardRedemptionMerchantLocation();


    }


    @Test
    public void listRedemptionMerchantLocations() throws Exception  {

        //Add the data
        redemptionMerchantLocation = redemptionMerchantLocationService.saveRedemptionMerchantLocation(redemptionMerchantLocation);

        tempSet.add(redemptionMerchantLocation);

        // Place the delete request
        MvcResult mvcResult = mockMvc.perform(get("/api/0.9/json/merchant/redemptionmerchantlocations/"+redemptionMerchantLocation.getRmlMerNo())
                .principal(principal)
                .session(session)
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)

        )
                .andReturn();

        String response = mvcResult.getResponse().getContentAsString();
        Assert.assertNotNull(response);
        log.info("RedemptionMerchantLocationResponse: " + response);

        // Convert json response to HashMap
        Assert.assertTrue(response.contains("success"));


    }



    @After
    public void tearDown() {



        for(RedemptionMerchantLocation redemptionMerchantLocation: tempSet) {


            redemptionMerchantLocationService.deleteRedemptionMerchantLocation(redemptionMerchantLocation.getRmlId());

        }

    }

}