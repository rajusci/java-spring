package com.inspirenetz.api.test.controller;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.inspirenetz.api.config.SecurityConfig;
import com.inspirenetz.api.core.domain.RewardCurrency;
import com.inspirenetz.api.core.service.RewardCurrencyService;
import com.inspirenetz.api.rest.exception.InspireNetzException;
import com.inspirenetz.api.test.config.*;
import com.inspirenetz.api.test.core.fixture.RewardCurrencyFixture;
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
public class RewardCurrencyControllerTest {


    private static Logger log = LoggerFactory.getLogger(RewardCurrencyControllerTest.class);


    private static MockMvc mockMvc;

    private static ObjectMapper mapper;

    @Autowired
    private RewardCurrencyService rewardCurrencyService;

    @Autowired
    private WebApplicationContext wac;

    @Resource
    private FilterChainProxy springSecurityFilterChain;

    @Autowired
    @Qualifier("uds")
    protected UserDetailsService userDetailsService;

    // RewardCurrency object
    private RewardCurrency rewardCurrency;

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

        // Create the rewardCurrency
        rewardCurrency = RewardCurrencyFixture.standardRewardCurrency();


    }





    @Test
    public void saveRewardCurrency() throws Exception {


        MvcResult mvcResult = mockMvc.perform(post("/api/0.9/json/merchant/rewardcurrency")
                                                .principal(principal)
                                                .session(session)
                                                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                                                .param("rwdCurrencyName", rewardCurrency.getRwdCurrencyName())
                                                .param("rwdDescription",rewardCurrency.getRwdDescription())
                                                .param("rwdMerchantNo", rewardCurrency.getRwdMerchantNo().toString())
                                                .param("rwdRoundingMethod", rewardCurrency.getRwdRoundingMethod().toString())
                                              )
                                        .andReturn();
        String response = mvcResult.getResponse().getContentAsString();
        Assert.assertNotNull(response);
        log.info("RewardCurrencyResponse: " + response);

        // Convert json response to HashMap
        map = mapper.readValue(response, new TypeReference<Map<String, String>>() { });
        Assert.assertTrue(map.get("status").equals("success"));


        log.info("RewardCurrency created");


    } 
    
    @Test
    public void deleteRewardCurrency() throws  Exception {

        // Create the rewardCurrency
        rewardCurrency = rewardCurrencyService.saveRewardCurrency(rewardCurrency);
        Assert.assertNotNull(rewardCurrency.getRwdCurrencyId());
        log.info("RewardCurrency created");


        // Place the delete request
        MvcResult mvcResult = mockMvc.perform(post("/api/0.9/json/merchant/rewardcurrency/delete/" + rewardCurrency.getRwdCurrencyId().toString() + "")
                                                    .principal(principal)
                                                    .session(session)
                                                    .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                                            )
                                            .andReturn();
        String response = mvcResult.getResponse().getContentAsString();
        Assert.assertNotNull(response);
        log.info("RewardCurrencyResponse: " + response);

        // Convert json response to HashMap
        map = mapper.readValue(response, new TypeReference<Map<String, String>>() { });
        Assert.assertTrue(map.get("status").equals("success"));


        log.info("RewardCurrency deleted");


    }



    @Test
    public void getRewardCurrencyInfo() throws Exception  {

        // Save the reward currency
        rewardCurrency = rewardCurrencyService.saveRewardCurrency(rewardCurrency);

        // Place the delete request
        MvcResult mvcResult = mockMvc.perform(get("/api/0.9/json/merchant/rewardcurrency/"+rewardCurrency.getRwdCurrencyId())
                                                    .principal(principal)
                                                    .session(session)
                                                    .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                                            )
                                            .andReturn();

        String response = mvcResult.getResponse().getContentAsString();
        Assert.assertNotNull(response);
        log.info("RewardCurrencyResponse: " + response);

        // Convert json response to HashMap
        Assert.assertTrue(response.contains("success"));


    }






    @Test
    public void listRewardCurrencys() throws Exception  {

        // Place the delete request
        MvcResult mvcResult = mockMvc.perform(get("/api/0.9/json/merchant/rewardcurrencies/0/0")
                .principal(principal)
                .session(session)
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)

        )
                .andReturn();

        String response = mvcResult.getResponse().getContentAsString();
        Assert.assertNotNull(response);
        log.info("RewardCurrencyResponse: " + response);

        // Convert json response to HashMap
        Assert.assertTrue(response.contains("success"));


    }

    @Test
    public void listRewardCurrencyForCustomers() throws Exception  {

        // Place the  request
        MvcResult mvcResult = mockMvc.perform(get("/api/0.9/json/customer/rewardcurrencies/0/0")
                .principal(principal)
                .session(session)
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .param("merchantno","1")

        )
                .andReturn();

        String response = mvcResult.getResponse().getContentAsString();
        Assert.assertNotNull(response);
        log.info("RewardCurrencyResponse: " + response);

        // Convert json response to HashMap
        Assert.assertTrue(response.contains("success"));


    }

    @Test
    public void listRewardCurrencyForUsers() throws Exception  {



        // Place the  request
        MvcResult mvcResult = mockMvc.perform(get("/api/0.9/json/user/rewardcurrencies/0/0")
                .principal(principal)
                .session(session)
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)


        )
                .andReturn();

        String response = mvcResult.getResponse().getContentAsString();
        Assert.assertNotNull(response);
        log.info("RewardCurrencyResponse: " + response);

        // Convert json response to HashMap
        Assert.assertTrue(response.contains("success"));


    }


    @After
    public void tearDown() {

        Set<RewardCurrency> rewardCurrencys = RewardCurrencyFixture.standardRewardCurrencies();

        for(RewardCurrency rewardCurrency: rewardCurrencys) {

            RewardCurrency delRewardCurrency = rewardCurrencyService.findByRwdMerchantNoAndRwdCurrencyName(rewardCurrency.getRwdMerchantNo(),rewardCurrency.getRwdCurrencyName());

            if ( delRewardCurrency != null ) {
                try{
                    rewardCurrencyService.deleteRewardCurrency(delRewardCurrency.getRwdCurrencyId());

                }catch (InspireNetzException ex){
                    ex.printStackTrace();
                }
            }

        }
    }

}
