package com.inspirenetz.api.test.controller;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.inspirenetz.api.core.domain.Purchase;
import com.inspirenetz.api.core.repository.PurchaseRepository;
import com.inspirenetz.api.core.service.PurchaseService;
import com.inspirenetz.api.test.config.*;
import com.inspirenetz.api.test.core.fixture.PurchaseFixture;
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
public class PurchaseControllerTest {


    private static Logger log = LoggerFactory.getLogger(PurchaseControllerTest.class);


    private static MockMvc mockMvc;

    private static ObjectMapper mapper;

    @Autowired
    private PurchaseService purchaseService;

    // Only used for the teardown of test data.
    @Autowired
    private PurchaseRepository purchaseRepository;

    @Autowired
    private WebApplicationContext wac;

    @Resource
    private FilterChainProxy springSecurityFilterChain;

    @Autowired
    @Qualifier("uds")
    protected UserDetailsService userDetailsService;

    // Purchase object
    private Purchase purchase;

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

        // Create the purchase
        purchase = PurchaseFixture.standardPurchase();


    }



    @Test
    public void savePurchase() throws Exception {


        MvcResult mvcResult = mockMvc.perform(post("/api/0.9/json/transaction/purchase")
                                                .principal(principal)
                                                .session(session)
                                                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                                                .param("prcLoyaltyId", purchase.getPrcLoyaltyId())
                                                .param("prcAmount", Double.toString(purchase.getPrcAmount()))
                                              )
                                        .andReturn();
        String response = mvcResult.getResponse().getContentAsString();
        Assert.assertNotNull(response);
        log.info("PurchaseResponse: " + response);

        // Convert json response to HashMap
        map = mapper.readValue(response, new TypeReference<Map<String, String>>() { });
        Assert.assertTrue(map.get("status").equals("success"));


        log.info("Purchase created");


    }



    @Test
    public void savePurchaseCompatible() throws Exception {



        MvcResult mvcResult = mockMvc.perform(post("/api/0.9/json/transaction/addpurchase")
                .principal(principal)
                .session(session)
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .param("cardnumber", purchase.getPrcLoyaltyId())
                .param("purchase_amount", Double.toString(purchase.getPrcAmount()))
                .param("date", purchase.getPrcDate().toString())
                .param("txnref", purchase.getPrcPaymentReference())
        )
                .andReturn();
        String response = mvcResult.getResponse().getContentAsString();
        Assert.assertNotNull(response);
        log.info("PurchaseResponse: " + response);

        // Convert json response to HashMap
        map = mapper.readValue(response, new TypeReference<Map<String, String>>() { });
        Assert.assertTrue(map.get("status").equals("success"));


        log.info("Purchase created");


    }

    @Test
    public void deletePurchase() throws  Exception {

        // Create the purchase
        purchase = purchaseService.savePurchase(purchase);
        Assert.assertNotNull(purchase.getPrcId());
        log.info("Purchase created");


        // Place the delete request
        MvcResult mvcResult = mockMvc.perform(post("/api/0.9/json/transaction/purchase/delete")
                                                    .principal(principal)
                                                    .session(session)
                                                    .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                                                    .param("prcId", purchase.getPrcId().toString())
                                            )
                                            .andReturn();
        String response = mvcResult.getResponse().getContentAsString();
        Assert.assertNotNull(response);
        log.info("PurchaseResponse: " + response);

        // Convert json response to HashMap
        map = mapper.readValue(response, new TypeReference<Map<String, String>>() { });
        Assert.assertTrue(map.get("status").equals("success"));


        log.info("Purchase deleted");


    }



    @Test
    public void searchPurchase() throws Exception  {

        // Get the set of purchases
        Set<Purchase> purchaseSet = PurchaseFixture.standardPurchases();
        List<Purchase>  purchaseList = new ArrayList<Purchase>();
        purchaseList.addAll(purchaseSet);
        purchaseService.saveAll(purchaseList);


        // Place the delete request
        MvcResult mvcResult = mockMvc.perform(get("/api/0.9/json/merchant/sales/loyaltyid/" + purchase.getPrcLoyaltyId())
                                                    .principal(principal)
                                                    .session(session)
                                                    .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                                            )
                                            .andReturn();

        String response = mvcResult.getResponse().getContentAsString();
        Assert.assertNotNull(response);
        log.info("PurchaseResponse: " + response);

        // Convert json response to HashMap
        Assert.assertTrue(response.contains("success"));


    }



    @Test
    public void getPurchaseInfo() throws Exception  {

        // Get the set of purchases
        purchase = purchaseService.savePurchase(purchase);


        // Place the delete request
        MvcResult mvcResult = mockMvc.perform(get("/api/0.9/json/merchant/sale/4499")
                .principal(principal)
                .session(session)
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
        )
                .andReturn();

        String response = mvcResult.getResponse().getContentAsString();
        Assert.assertNotNull(response);
        log.info("PurchaseResponse: " + response);

        // Convert json response to HashMap
        Assert.assertTrue(response.contains("success"));


    }







    @After
    public void tearDown() {

        Set<Purchase> purchases = PurchaseFixture.standardPurchases();

        for(Purchase purchase: purchases) {

            Purchase delPurchase = purchaseRepository.findByPrcMerchantNoAndPrcLoyaltyIdAndPrcDateAndPrcAmountAndPrcPaymentReference(purchase.getPrcMerchantNo(), purchase.getPrcLoyaltyId(), purchase.getPrcDate(), purchase.getPrcAmount(), purchase.getPrcPaymentReference());

            if ( delPurchase != null ) {
                purchaseRepository.delete(delPurchase);
            }

        }
    }
}
