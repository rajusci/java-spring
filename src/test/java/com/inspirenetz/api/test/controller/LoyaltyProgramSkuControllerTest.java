package com.inspirenetz.api.test.controller;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.inspirenetz.api.config.SecurityConfig;
import com.inspirenetz.api.core.domain.LoyaltyProgramSku;
import com.inspirenetz.api.core.service.LoyaltyProgramSkuService;
import com.inspirenetz.api.test.config.*;
import com.inspirenetz.api.test.core.fixture.LoyaltyProgramSkuFixture;
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
public class LoyaltyProgramSkuControllerTest {


    private static Logger log = LoggerFactory.getLogger(LoyaltyProgramSkuControllerTest.class);


    private static MockMvc mockMvc;

    private static ObjectMapper mapper;

    @Autowired
    private LoyaltyProgramSkuService loyaltyProgramSkuService;

    @Autowired
    private WebApplicationContext wac;

    @Resource
    private FilterChainProxy springSecurityFilterChain;

    @Autowired
    @Qualifier("uds")
    protected UserDetailsService userDetailsService;

    // LoyaltyProgramSku object
    private LoyaltyProgramSku loyaltyProgramSku;

    UsernamePasswordAuthenticationToken principal;

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

        // Create the loyaltyProgramSku
        loyaltyProgramSku = LoyaltyProgramSkuFixture.standardLoyaltyProgramSku();


    }



    @Test
    public void saveLoyaltyProgramSku() throws Exception {


        MvcResult mvcResult = mockMvc.perform(post("/api/0.9/json/merchant/loyaltyprogram/sku")
                                                .principal(principal)
                                                .session(session)
                                                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                                                .param("lpuProgramId", loyaltyProgramSku.getLpuProgramId().toString())
                                                .param("lpuItemType",Integer.toString(loyaltyProgramSku.getLpuItemType()))
                                                .param("lpuItemCode", loyaltyProgramSku.getLpuItemCode())
                                                .param("lpuPrgRatioNum", Double.toString(loyaltyProgramSku.getLpuPrgRatioNum()))
                                                .param("lpuPrgRatioDeno", Double.toString(loyaltyProgramSku.getLpuPrgRatioDeno()))
                                              )
                                        .andReturn();
        String response = mvcResult.getResponse().getContentAsString();
        Assert.assertNotNull(response);
        log.info("LoyaltyProgramSkuResponse: " + response);

        // Convert json response to HashMap
        map = mapper.readValue(response, new TypeReference<Map<String, String>>() { });
        Assert.assertTrue(map.get("status").equals("success"));


        log.info("LoyaltyProgramSku created");


    } 
    
    @Test
    public void deleteLoyaltyProgramSku() throws  Exception {

        // Create the loyaltyProgramSku
        loyaltyProgramSku = loyaltyProgramSkuService.saveLoyaltyProgramSku(loyaltyProgramSku);
        Assert.assertNotNull(loyaltyProgramSku.getLpuId());
        log.info("LoyaltyProgramSku created");


        // Place the delete request
        MvcResult mvcResult = mockMvc.perform(post("/api/0.9/json/merchant/loyaltyprogram/sku/delete/"+loyaltyProgramSku.getLpuId().toString())
                                                    .principal(principal)
                                                    .session(session)
                                                    .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                                            )
                                            .andReturn();
        String response = mvcResult.getResponse().getContentAsString();
        Assert.assertNotNull(response);
        log.info("LoyaltyProgramSkuResponse: " + response);

        // Convert json response to HashMap
        map = mapper.readValue(response, new TypeReference<Map<String, String>>() { });
        Assert.assertTrue(map.get("status").equals("success"));


        log.info("LoyaltyProgramSku deleted");


    }



    @Test
    public void getLoyaltyProgramSkusByProgramId() throws Exception  {

        // Get the set of loyaltyProgramSkus
        Set<LoyaltyProgramSku> loyaltyProgramSkuSet = LoyaltyProgramSkuFixture.standardLoyaltyProgramSkus();
        List<LoyaltyProgramSku>  loyaltyProgramSkuList = new ArrayList<LoyaltyProgramSku>();
        loyaltyProgramSkuList.addAll(loyaltyProgramSkuSet);
        loyaltyProgramSkuService.saveAll(loyaltyProgramSkuList); 

        // Place the delete request
        MvcResult mvcResult = mockMvc.perform(get("/api/0.9/json/merchant/loyaltyprogram/sku/program/"+loyaltyProgramSku.getLpuProgramId().toString())
                .principal(principal)
                .session(session)
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                )
                .andReturn();

        String response = mvcResult.getResponse().getContentAsString();
        Assert.assertNotNull(response);
        log.info("LoyaltyProgramSkuResponse: " + response);

        // Convert json response to HashMap
        Assert.assertTrue(response.contains("success"));


    }





    @Test
    public void listLoyaltyProgramSkus() throws Exception  {

        // Place the delete request
        MvcResult mvcResult = mockMvc.perform(get("/api/0.9/json/merchant/loyaltyProgramSkus")
                .principal(principal)
                .session(session)
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)

        )
                .andReturn();

        String response = mvcResult.getResponse().getContentAsString();
        Assert.assertNotNull(response);
        log.info("LoyaltyProgramSkuResponse: " + response);

        // Convert json response to HashMap
        Assert.assertTrue(response.contains("success"));


    }



    @After
    public void tearDown() throws Exception {

        Set<LoyaltyProgramSku> loyaltyProgramSkus = LoyaltyProgramSkuFixture.standardLoyaltyProgramSkus();

        for(LoyaltyProgramSku loyaltyProgramSku: loyaltyProgramSkus) {

            LoyaltyProgramSku delLoyaltyProgramSku = loyaltyProgramSkuService.findByLpuProgramIdAndLpuItemTypeAndLpuItemCode(loyaltyProgramSku.getLpuProgramId(), loyaltyProgramSku.getLpuItemType(), loyaltyProgramSku.getLpuItemCode());

            if ( delLoyaltyProgramSku != null ) {
                loyaltyProgramSkuService.deleteLoyaltyProgramSku(delLoyaltyProgramSku.getLpuId());
            }

        }
    }

}
