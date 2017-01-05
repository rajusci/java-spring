package com.inspirenetz.api.test.controller;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.inspirenetz.api.core.domain.Customer;
import com.inspirenetz.api.core.domain.RedemptionVoucherSource;
import com.inspirenetz.api.core.domain.LinkedLoyalty;
import com.inspirenetz.api.core.service.CustomerService;
import com.inspirenetz.api.core.service.RedemptionVoucherSourceService;
import com.inspirenetz.api.core.service.LinkedLoyaltyService;
import com.inspirenetz.api.rest.exception.InspireNetzException;
import com.inspirenetz.api.test.config.*;
import com.inspirenetz.api.test.core.fixture.CustomerFixture;
import com.inspirenetz.api.test.core.fixture.RedemptionVoucherSourceFixture;
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
public class RedemptionVoucherSourceControllerTest {


    private static Logger log = LoggerFactory.getLogger(RedemptionVoucherSourceControllerTest.class);

    private static MockMvc mockMvc;

    private static ObjectMapper mapper;

    @Autowired
    private RedemptionVoucherSourceService redemptionVoucherSourceService;

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


    // RedemptionVoucherSource object
    private RedemptionVoucherSource redemptionVoucherSource;

    UsernamePasswordAuthenticationToken principal;

    MockHttpSession session;

    // Map to hold the returned json
    Map<String,String> map = new HashMap<>();

    Set<RedemptionVoucherSource> tempSet = new HashSet<>(0);

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

        // Create the redemptionVoucherSource
        redemptionVoucherSource = RedemptionVoucherSourceFixture.standardRedemptionVoucherSource();


    }




    @Test
    public void saveRedemptionVoucherSource() throws Exception {


        MvcResult mvcResult = mockMvc.perform(post("/api/0.9/json/merchant/redemption/vouchersource")
                                                .principal(principal)
                                                .session(session)
                                                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                                                .param("rvsType",redemptionVoucherSource.getRvsType().toString())
                                                .param("rvsName",redemptionVoucherSource.getRvsName().toString())
                                                .param("rvsCodeStart", redemptionVoucherSource.getRvsCodeStart().toString())
                                                .param("rvsCodeEnd", redemptionVoucherSource.getRvsCodeEnd().toString())
                                                .param("rvsPrefix", redemptionVoucherSource.getRvsPrefix())
                                              )
                                        .andReturn();
        String response = mvcResult.getResponse().getContentAsString();
        Assert.assertNotNull(response);
        log.info("RedemptionVoucherSourceResponse: " + response);

        // Convert json response to HashMap
        map = mapper.readValue(response, new TypeReference<Map<String, String>>() { });
        Assert.assertTrue(map.get("status").equals("success"));


        log.info("RedemptionVoucherSource created");


    }

    @Test
    public void deleteRedemptionVoucherSource() throws  Exception {

        // Create the redemptionVoucherSource
        redemptionVoucherSource = redemptionVoucherSourceService.saveRedemptionVoucherSource(redemptionVoucherSource);
        Assert.assertNotNull(redemptionVoucherSource.getRvsId());
        log.info("RedemptionVoucherSource created");

        tempSet.add(redemptionVoucherSource);


        // Place the delete request
        MvcResult mvcResult = mockMvc.perform(post("/api/0.9/json/merchant/redemption/vouchersource/delete/" + redemptionVoucherSource.getRvsId())
                                                    .principal(principal)
                                                    .session(session)
                                                    .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                                            )
                                            .andReturn();
        String response = mvcResult.getResponse().getContentAsString();
        Assert.assertNotNull(response);
        log.info("RedemptionVoucherSourceResponse: " + response);

        // Convert json response to HashMap
        map = mapper.readValue(response, new TypeReference<Map<String, String>>() { });
        Assert.assertTrue(map.get("status").equals("success"));


        // Remove the requst from the tempset
        tempSet.remove(redemptionVoucherSource);

        log.info("RedemptionVoucherSource deleted");


    }

    @Test
    public void listRedemptionVoucherSources() throws Exception  {

        //Add the data
        redemptionVoucherSource = redemptionVoucherSourceService.saveRedemptionVoucherSource(redemptionVoucherSource);

        tempSet.add(redemptionVoucherSource);

        // Place the delete request
        MvcResult mvcResult = mockMvc.perform(get("/api/0.9/json/merchant/redemption/vouchersources//0/0")
                .principal(principal)
                .session(session)
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)

        )
                .andReturn();

        String response = mvcResult.getResponse().getContentAsString();
        Assert.assertNotNull(response);
        log.info("RedemptionVoucherSourceResponse: " + response);

        // Convert json response to HashMap
        Assert.assertTrue(response.contains("success"));


    }

    @Test
    public void getActiveVouchers() throws Exception  {

        //Add the data
        redemptionVoucherSource = redemptionVoucherSourceService.saveRedemptionVoucherSource(redemptionVoucherSource);

        tempSet.add(redemptionVoucherSource);

        // Place the delete request
        MvcResult mvcResult = mockMvc.perform(get("/api/0.9/json/merchant/redemption/vouchersources/active")
                .principal(principal)
                .session(session)
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)

        )
                .andReturn();

        String response = mvcResult.getResponse().getContentAsString();
        Assert.assertNotNull(response);
        log.info("RedemptionVoucherSourceResponse: " + response);

        // Convert json response to HashMap
        Assert.assertTrue(response.contains("success"));


    }

    @Test
    public void getRedemptionVoucherSourceInfo()   throws Exception  {

        //Add the data
        redemptionVoucherSource = redemptionVoucherSourceService.saveRedemptionVoucherSource(redemptionVoucherSource);

        tempSet.add(redemptionVoucherSource);

        // Place the delete request
        MvcResult mvcResult = mockMvc.perform(get("/api/0.9/json/merchant/redemption/vouchersource/"+redemptionVoucherSource.getRvsId())
                .principal(principal)
                .session(session)
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)

        )
                .andReturn();

        String response = mvcResult.getResponse().getContentAsString();
        Assert.assertNotNull(response);
        log.info("RedemptionVoucherSourceResponse: " + response);

        // Convert json response to HashMap
        Assert.assertTrue(response.contains("success"));


    }



    @After
    public void tearDown() throws InspireNetzException {

        for(RedemptionVoucherSource redemptionVoucherSource: tempSet) {

            redemptionVoucherSourceService.deleteRedemptionVoucherSource(redemptionVoucherSource.getRvsId());

        }

    }

}
