package com.inspirenetz.api.test.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.inspirenetz.api.core.dictionary.IndicatorStatus;
import com.inspirenetz.api.core.domain.MerchantRedemptionPartner;
import com.inspirenetz.api.core.service.MerchantRedemptionPartnerService;
import com.inspirenetz.api.test.config.*;
import com.inspirenetz.api.test.core.fixture.MerchantRedemptionPartnerFixture;
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
 * Created by ameen on 26/6/15.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration(classes  = {ApplicationTestConfig.class, PersistenceTestConfig.class, ServiceTestConfig.class, SecurityTestConfig.class, WebTestConfig.class, NotificationTestConfig.class})
public class MerchantRedemptionPartnerControllerTest {

    private static Logger log = LoggerFactory.getLogger(MerchantRedemptionPartnerControllerTest.class);


    private static MockMvc mockMvc;

    private static ObjectMapper mapper;

    @Autowired
    private MerchantRedemptionPartnerService merchantRedemptionPartnerService;


    @Autowired
    private WebApplicationContext wac;

    @Resource
    private FilterChainProxy springSecurityFilterChain;

    @Autowired
    @Qualifier("uds")
    protected UserDetailsService userDetailsService;

    // MerchantRedemptionPartner object
    private MerchantRedemptionPartner merchantRedemptionPartner;

    UsernamePasswordAuthenticationToken principal;

    MockHttpSession session;

    Set<MerchantRedemptionPartner> tempMerchantRedemptionPartner =new HashSet<>();

    @Before
    public void setUp()
    {
        try {

            // Set the principal
            principal = ControllerTestUtils.getPrincipal(ControllerTestUtils.TEST_MERCHANT_USER_LOGINID, userDetailsService);

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

        // Create the merchantRedemptionPartner
        merchantRedemptionPartner = MerchantRedemptionPartnerFixture.standardMerchantRedemptionPartner();

        tempMerchantRedemptionPartner.add(merchantRedemptionPartner);

    }



    @Test
    public void saveMerchantRedemptionPartner() throws Exception {


        MvcResult mvcResult = mockMvc.perform(post("/api/0.9/json/admin/merchantpartner")
                .principal(principal)
                .session(session)
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .param("mrpMerchantNo",merchantRedemptionPartner.getMrpMerchantNo().toString())
                .param("mrpRedemptionMerchant",merchantRedemptionPartner.getMrpRedemptionMerchant().toString())
                .param("mrpEnabled", merchantRedemptionPartner.getMrpEnabled().toString())
        )
                .andReturn();
        String response = mvcResult.getResponse().getContentAsString();
        Assert.assertNotNull(response);
        log.info("MerchantRedemptionPartnerResponse: " + response);

        // Convert json response to HashMap
        Assert.assertTrue(response.contains("success"));

        log.info("MerchantRedemptionPartner created");


    }


    @Test
    public void listMerchantRedemptionPartnersForAdmin() throws Exception  {

        // Place the delete request
        MvcResult mvcResult = mockMvc.perform(get("/api/0.9/json/admin/merchantredemptionpartner"+merchantRedemptionPartner.getMrpMerchantNo().toString()+"/0/0")
                .principal(principal)
                .session(session)
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)

        )
                .andReturn();

        String response = mvcResult.getResponse().getContentAsString();
        Assert.assertNotNull(response);
        log.info("MerchantRedemptionPartnerResponse: " + response);

        // Convert json response to HashMap
        Assert.assertTrue(response.contains("success"));


    }

    @Test
    public void listMerchantRedemptionPartnersMapForMerchant() throws Exception  {

        // Place the delete request
        MvcResult mvcResult = mockMvc.perform(get("/api/0.9/json/merchant/merchantredemptionpartner")
                .principal(principal)
                .session(session)
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)

        )
                .andReturn();

        String response = mvcResult.getResponse().getContentAsString();
        Assert.assertNotNull(response);
        log.info("MerchantRedemptionPartnerResponse: " + response);

        // Convert json response to HashMap
        Assert.assertTrue(response.contains("success"));


    }






    @After
    public void tearDown() throws Exception {

        for (MerchantRedemptionPartner merchantRedemptionPartner1:tempMerchantRedemptionPartner){

            merchantRedemptionPartnerService.deleteMerchantRedemptionPartner(merchantRedemptionPartner1);
        }
    }

}
