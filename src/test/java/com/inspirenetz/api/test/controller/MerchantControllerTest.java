package com.inspirenetz.api.test.controller;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.inspirenetz.api.config.SecurityConfig;
import com.inspirenetz.api.core.domain.Merchant;
import com.inspirenetz.api.core.domain.MerchantLocation;
import com.inspirenetz.api.core.service.MerchantService;
import com.inspirenetz.api.rest.exception.InspireNetzException;
import com.inspirenetz.api.test.config.*;
import com.inspirenetz.api.test.core.fixture.MerchantFixture;
import com.inspirenetz.api.test.core.fixture.MerchantLocationFixture;
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
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

/**
 * Created by sandheepgr on 30/4/14.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration(classes  = {ApplicationTestConfig.class, PersistenceTestConfig.class, ServiceTestConfig.class, SecurityTestConfig.class, WebTestConfig.class, NotificationTestConfig.class})
public class MerchantControllerTest {


    private static Logger log = LoggerFactory.getLogger(MerchantControllerTest.class);


    private static MockMvc mockMvc;

    private static ObjectMapper mapper;

    @Autowired
    private MerchantService merchantService;

    @Autowired
    private WebApplicationContext wac;

    @Resource
    private FilterChainProxy springSecurityFilterChain;

    @Autowired
    @Qualifier("uds")
    protected UserDetailsService userDetailsService;

    // Merchant object
    private Merchant merchant;

    UsernamePasswordAuthenticationToken principal;

    MockHttpSession session;

    // Map to hold the returned json
    Map<String,String> map = new HashMap<>();


    private void setupForAdmin() {

        try {

            // Set the principal
            principal = ControllerTestUtils.getPrincipal(ControllerTestUtils.TEST_ADMIN_USER_LOGINID,userDetailsService);

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


    private void setupForMerchantAdmin() {

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

        // Create the merchant
        merchant = MerchantFixture.standardMerchant();


    }



    @Test
    public void saveMerchant() throws Exception {

        setupForAdmin();

        MvcResult mvcResult = mockMvc.perform(post("/api/0.9/json/admin/merchant")
                .principal(principal)
                .session(session)
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .param("merMerchantName", merchant.getMerMerchantName())
                .param("merUrlName", merchant.getMerUrlName())
                .param("merAddress1", merchant.getMerAddress1())
                .param("merAddress2", merchant.getMerAddress2())
                .param("merAddress3", merchant.getMerAddress3())
                .param("merCity", merchant.getMerCity())
                .param("merState", merchant.getMerState())
                .param("merPostCode", merchant.getMerPostCode())
                .param("merContactName", merchant.getMerContactName())
                .param("merContactEmail", merchant.getMerContactEmail())
                .param("merPhoneNo", merchant.getMerPhoneNo())
                .param("merEmail", merchant.getMerEmail())
                .param("merMembershipName", merchant.getMerMembershipName())
                .param("merActivationDate", merchant.getMerActivationDate().toString())
        )
                                        .andReturn();
        String response = mvcResult.getResponse().getContentAsString();
        Assert.assertNotNull(response);
        log.info("MerchantResponse: " + response);

        // Convert json response to HashMap
        map = mapper.readValue(response, new TypeReference<Map<String, String>>() { });
        Assert.assertTrue(map.get("status").equals("success"));

        log.info("Merchant created");
    } 

    /*
    @Test
    public void deleteMerchant() throws  Exception {

        setupForAdmin();


        // Create the merchant
        merchant = merchantService.saveMerchant(merchant);
        Assert.assertNotNull(merchant.getMerMerchantNo());
        log.info("Merchant created");


        // Place the delete request
        MvcResult mvcResult = mockMvc.perform(post("/api/0.9/json/admin/merchant/delete/" + merchant.getMerMerchantNo())
                                                    .principal(principal)
                                                    .session(session)
                                                    .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                                            )
                                            .andReturn();
        String response = mvcResult.getResponse().getContentAsString();
        Assert.assertNotNull(response);
        log.info("MerchantResponse: " + response);

        // Convert json response to HashMap
        map = mapper.readValue(response, new TypeReference<Map<String, String>>() { });
        Assert.assertTrue(map.get("status").equals("success"));


    }

    */





    @Test
    public void listMerchants() throws Exception  {

        setupForAdmin();

        // Place the delete request
        MvcResult mvcResult = mockMvc.perform(get("/api/0.9/json/admin/merchants/0/00")
                .principal(principal)
                .session(session)
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)

        )
                .andReturn();

        String response = mvcResult.getResponse().getContentAsString();
        Assert.assertNotNull(response);
        log.info("MerchantResponse: " + response);

        // Convert json response to HashMap
        Assert.assertTrue(response.contains("success"));


    }



    @Test
    public void getMerchantInfo() throws Exception  {


        setupForAdmin();


        // Get the set of merchants
        merchant = merchantService.saveMerchant(merchant);


        // Place the delete request
        MvcResult mvcResult = mockMvc.perform(get("/api/0.9/json/admin/merchant/"+merchant.getMerMerchantNo())
                .principal(principal)
                .session(session)
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
        )
                .andReturn();

        String response = mvcResult.getResponse().getContentAsString();
        Assert.assertNotNull(response);
        log.info("MerchantResponse: " + response);

        // Convert json response to HashMap
        Assert.assertTrue(response.contains(merchant.getMerMerchantName()));


    }



    @Test
    public void saveMerchantProfile() throws Exception {


        setupForMerchantAdmin();


        merchant = merchantService.saveMerchant(merchant);

        Merchant merchant = MerchantFixture.standardMerchant();

        // Create the merchant locations
        MerchantLocation merchantLocation = MerchantLocationFixture.standardMerchantLocation();
        merchantLocation.setMelLocation("Location A");
        MerchantLocation merchantLocation2 = MerchantLocationFixture.standardMerchantLocation();
        merchantLocation2.setMelLocation("Location B");

        // The list
        Set<MerchantLocation> locationList = new HashSet<>(0);
        locationList.add(merchantLocation);
        locationList.add(merchantLocation2);

        merchant.setMerchantLocationSet(locationList);

        log.info(merchant+"merchant");
        ObjectMapper objectMapper = new ObjectMapper();
        String merchantData = objectMapper.writeValueAsString(merchant);
        log.info("JSON string : " + merchantData);

        MvcResult mvcResult = mockMvc.perform(post("/api/0.9/json/merchant/profile")
                .principal(principal)
                .session(session)
                .contentType(MediaType.valueOf(MediaType.APPLICATION_JSON_VALUE))
                .content(merchantData)
        )
                .andReturn();
        String response = mvcResult.getResponse().getContentAsString();
        Assert.assertNotNull(response);
        log.info("MerchantResponse: " + response);

        // Convert json response to HashMap
        map = mapper.readValue(response, new TypeReference<Map<String, String>>() { });
        Assert.assertTrue(map.get("status").equals("success"));

        log.info("Merchant created");
    }




    @Test
    public void getMerchantProfile() throws Exception  {

        setupForMerchantAdmin();

        Merchant merchant = MerchantFixture.standardMerchant();

        // Create the merchant locations
        MerchantLocation merchantLocation = MerchantLocationFixture.standardMerchantLocation();
        merchantLocation.setMelLocation("Location A");
        MerchantLocation merchantLocation2 = MerchantLocationFixture.standardMerchantLocation();
        merchantLocation2.setMelLocation("Location B");

        // The list
        Set<MerchantLocation> locationList = new HashSet<>(0);
        locationList.add(merchantLocation);
        locationList.add(merchantLocation2);

        merchant.setMerchantLocationSet(locationList);
        merchant.setMerMerchantNo(1L);
        merchant  = merchantService.saveMerchant(merchant);


        // Place the delete request
        MvcResult mvcResult = mockMvc.perform(get("/api/0.9/json/merchant/profile")
                .principal(principal)
                .session(session)
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
        )
                .andReturn();

        String response = mvcResult.getResponse().getContentAsString();
        Assert.assertNotNull(response);
        log.info("MerchantResponse: " + response);

        // Convert json response to HashMap
        Assert.assertTrue(response.contains("success"));


    }

    @Test
    public void getActiveMerchants() throws Exception  {


        setupForMerchantAdmin();

        // Place the  request
        MvcResult mvcResult = mockMvc.perform(get("/api/0.9/json/public/merchants")
                .principal(principal)
                .session(session)
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)


        )
                .andReturn();

        String response = mvcResult.getResponse().getContentAsString();
        Assert.assertNotNull(response);
        log.info("MerchantResponse: " + response);

        // Convert json response to HashMap
        Assert.assertTrue(response.contains("success"));

        Assert.assertNotNull(response);


    }

    @Test
    public void getMerchantDetailsByUrl() throws Exception  {

        setupForMerchantAdmin();

        // Place the  request
        MvcResult mvcResult = mockMvc.perform(get("/api/0.9/json/customer/merchant/profile/inspirenetz")
                .principal(principal)
                .session(session)
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)


        )
                .andReturn();

        String response = mvcResult.getResponse().getContentAsString();
        Assert.assertNotNull(response);
        log.info("MerchantResponse: " + response);

        // Convert json response to HashMap
        Assert.assertTrue(response.contains("success"));

        Assert.assertNotNull(response);


    }

    @After
    // NOTE : PLEASE USE WITH CARE
    // IF YOU ARE USING A USER ID FOR EXISTING MERCHANT, IT WILL DELETE THE MERCHANT AFTER
    // RUNNING THE TEST
    public void tearDown() throws InspireNetzException {

        Set<Merchant> merchants = MerchantFixture.standardMerchants();

        for(Merchant merchant: merchants) {

            Merchant delMerchant = merchantService.findByMerMerchantName(merchant.getMerMerchantName());

            if ( delMerchant != null ) {
                merchantService.deleteMerchant(delMerchant.getMerMerchantNo());
            }

        }
    }

}
