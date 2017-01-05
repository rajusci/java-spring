package com.inspirenetz.api.test.controller;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.inspirenetz.api.config.SecurityConfig;
import com.inspirenetz.api.core.dictionary.MessageSpielChannel;
import com.inspirenetz.api.core.dictionary.PromotionTargetOption;
import com.inspirenetz.api.core.domain.Promotion;
import com.inspirenetz.api.core.domain.UserResponse;
import com.inspirenetz.api.core.service.PromotionService;
import com.inspirenetz.api.core.service.UserResponseService;
import com.inspirenetz.api.test.config.*;
import com.inspirenetz.api.test.core.fixture.PromotionFixture;
import com.inspirenetz.api.test.core.fixture.UserResponseFixture;
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
public class PromotionControllerTest {


    private static Logger log = LoggerFactory.getLogger(PromotionControllerTest.class);


    private static MockMvc mockMvc;

    private static ObjectMapper mapper;

    @Autowired
    private PromotionService promotionService;

    @Autowired
    private WebApplicationContext wac;

    @Resource
    private FilterChainProxy springSecurityFilterChain;



    @Autowired
    private UserResponseService userResponseService;

    @Autowired
    @Qualifier("uds")
    protected UserDetailsService userDetailsService;

    // Promotion object
    private Promotion promotion;

    UsernamePasswordAuthenticationToken principal;

    Set<UserResponse> tempSet = new HashSet<>(0);

    Set<Promotion> tempSet1 = new HashSet<>(0);

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

        // Create the promotion
        promotion = PromotionFixture.standardPromotion();


    }


    @Test
    public void savePromotion() throws Exception {


        MvcResult mvcResult = mockMvc.perform(post("/api/0.9/json/merchant/promotion")
                                                .principal(principal)
                                                .session(session)
                                                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                                                .param("prmName", promotion.getPrmName())
                                                .param("prmShortDescription", promotion.getPrmShortDescription())
                                                .param("prmLongDescription", promotion.getPrmLongDescription())
                                                .param("prmExpiryOption", "2")
                                                .param("prmNumResponses","3")
                                                .param("prmTargetedOption",PromotionTargetOption.ALL_MEMBERS+"")
                                                .param("prmSegmentId","69")
                                                .param("prmBroadcastOption", MessageSpielChannel.SMS+":"+MessageSpielChannel.EMAIL)
                                                .param("prmSmsContent", "Testing")
                                                .param("prmEmailSubject", "Testing")
                                                .param("prmEmailContent", "Testing")

                                              )
                                        .andReturn();
        String response = mvcResult.getResponse().getContentAsString();
        Assert.assertNotNull(response);
        log.info("PromotionResponse: " + response);

        // Convert json response to HashMap
        map = mapper.readValue(response, new TypeReference<Map<String, String>>() { });
        Assert.assertTrue(map.get("status").equals("success"));


        log.info("Promotion created");


    }

    @Test
    public void deletePromotion() throws  Exception {

        // Create the promotion
        promotion = promotionService.savePromotion(promotion);
        Assert.assertNotNull(promotion.getPrmId());
        log.info("Promotion created");


        // Place the delete request
        MvcResult mvcResult = mockMvc.perform(post("/api/0.9/json/merchant/promotion/delete/" + promotion.getPrmId())
                                                    .principal(principal)
                                                    .session(session)
                                                    .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                                            )
                                            .andReturn();
        String response = mvcResult.getResponse().getContentAsString();
        Assert.assertNotNull(response);
        log.info("PromotionResponse: " + response);

        // Convert json response to HashMap
        map = mapper.readValue(response, new TypeReference<Map<String, String>>() { });
        Assert.assertTrue(map.get("status").equals("success"));


        log.info("Promotion deleted");


    }

    @Test
    public void listPromotions() throws Exception  {

        // Place the delete request
        MvcResult mvcResult = mockMvc.perform(get("/api/0.9/json/merchant/promotions/0/0")
                .principal(principal)
                .session(session)
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)

        )
                .andReturn();

        String response = mvcResult.getResponse().getContentAsString();
        Assert.assertNotNull(response);
        log.info("PromotionResponse: " + response);

        // Convert json response to HashMap
        Assert.assertTrue(response.contains("success"));


    }

    @Test
    public void listPromotionsForUser() throws Exception  {


        // NOTE: This test need to be run as a Customerlogin
        // Please replace the line in setup with the following
        // principal = ControllerTestUtils.getPrincipal(ControllerTestUtils.TEST_CUSTOMER_LOGINID,userDetailsService);


        // Place the delete request
        MvcResult mvcResult = mockMvc.perform(get("/api/0.9/json/user/promotions")
                .principal(principal)
                .session(session)
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)

        )
                .andReturn();

        String response = mvcResult.getResponse().getContentAsString();
        Assert.assertNotNull(response);
        log.info("PromotionResponse: " + response);

        // Convert json response to HashMap
        Assert.assertTrue(response.contains("success"));


    }

    @Test
    public void listPromotionInfo() throws  Exception {

        // Create the promotion
        promotion = promotionService.savePromotion(promotion);
        Assert.assertNotNull(promotion.getPrmId());
        log.info("Promotion created");


        // Place the delete request
        MvcResult mvcResult = mockMvc.perform(get("/api/0.9/json/merchant/promotion/" + promotion.getPrmId())
                .principal(principal)
                .session(session)
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
        )
                .andReturn();
        String response = mvcResult.getResponse().getContentAsString();
        Assert.assertNotNull(response);
        log.info("PromotionResponse: " + response);


    }

    @Test
    public void savePromotionView() throws Exception  {


        // Create the promotion
        promotion = promotionService.savePromotion(promotion);
        Assert.assertNotNull(promotion.getPrmId());
        log.info("Promotion created");

        tempSet1.add(promotion);

        // Convert to JSON
        ObjectMapper objectMapper = new ObjectMapper();
        String userData = objectMapper.writeValueAsString(UserResponseFixture.standardUserResponse(promotion));
        log.info("JSON string : " + userData);
        // Place the delete request
        MvcResult mvcResult = mockMvc.perform(post("/api/0.9/json/customer/promotionview")
                .principal(principal)
                .session(session)
                .contentType(MediaType.valueOf(MediaType.APPLICATION_JSON_VALUE))
                .content(userData)
        )
                .andReturn();

        String response = mvcResult.getResponse().getContentAsString();
        Assert.assertNotNull(response);
        log.info("UserResponse: " + response);

        // Convert json response to HashMap
        Assert.assertTrue(response.contains("success"));



    }

    @Test
    public void getPublicPromotion() throws Exception  {

        //Set Promotion Target Option
        promotion.setPrmTargetedOption(PromotionTargetOption.PUBLIC);

        // Create the promotion
        promotion = promotionService.savePromotion(promotion);
        Assert.assertNotNull(promotion.getPrmId());
        log.info("Promotion created");

        // Place the delete request
        MvcResult mvcResult = mockMvc.perform(get("/api/0.9/json/public/promotions")
                .principal(principal)
                .session(session)
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)


        )
                .andReturn();

        String response = mvcResult.getResponse().getContentAsString();
        Assert.assertNotNull(response);
        log.info("PromotionResponse: " + response);

        // Convert json response to HashMap
        Assert.assertTrue(response.contains("success"));


    }


    @After
    public void tearDown() throws Exception {

//        Set<Promotion> promotions = PromotionFixture.standardPromotions();
//
//        for(Promotion promotion: promotions) {
//
//            Promotion delPromotion = promotionService.findByPrmMerchantNoAndPrmName(promotion.getPrmMerchantNo(), promotion.getPrmName());
//
//            if ( delPromotion != null ) {
//                promotionService.deletePromotion(delPromotion.getPrmId());
//            }
//
//        }


        for(Promotion promotion1: tempSet1){

            promotionService.deletePromotion(promotion1.getPrmId());
        }

        for(UserResponse userResponse: tempSet){

            userResponseService.deleteUserResponse(userResponse);
        }
    }
    

}
