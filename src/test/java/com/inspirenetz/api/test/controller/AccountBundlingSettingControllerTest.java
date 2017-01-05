package com.inspirenetz.api.test.controller;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.inspirenetz.api.core.domain.AccountBundlingSetting;
import com.inspirenetz.api.core.service.AccountBundlingSettingService;
import com.inspirenetz.api.rest.exception.InspireNetzException;
import com.inspirenetz.api.test.config.*;
import com.inspirenetz.api.test.core.fixture.AccountBundlingSettingFixture;
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
public class AccountBundlingSettingControllerTest {


    private static Logger log = LoggerFactory.getLogger(AccountBundlingSettingControllerTest.class);

    private static MockMvc mockMvc;

    private static ObjectMapper mapper;

    @Autowired
    private AccountBundlingSettingService accountBundlingSettingService;

    @Autowired
    private WebApplicationContext wac;

    @Resource
    private FilterChainProxy springSecurityFilterChain;

    @Autowired
    @Qualifier("uds")
    protected UserDetailsService userDetailsService;

    // AccountBundlingSetting object
    private AccountBundlingSetting accountBundlingSetting;

    UsernamePasswordAuthenticationToken principal;

    MockHttpSession session;

    // Map to hold the returned json
    Map<String,String> map = new HashMap<>();


    Set<AccountBundlingSetting> tempSet= new HashSet<>(0);


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

        // Create the accountBundlingSetting
        accountBundlingSetting = AccountBundlingSettingFixture.standardAccountBundlingSetting();


    }




    @Test
    public void saveAccountBundlingSetting() throws Exception {


        MvcResult mvcResult = mockMvc.perform(post("/api/0.9/json/merchant/bundling/setting")
                                                .principal(principal)
                                                .session(session)
                                                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                                                .param("absLinkingType",accountBundlingSetting.getAbsLinkingType().toString())
                                                .param("absLinkingEligibility",accountBundlingSetting.getAbsLinkingEligibility().toString())
                                                .param("absPrimaryAccountEligibility", accountBundlingSetting.getAbsPrimaryAccountEligibility().toString())
                                              )
                                        .andReturn();
        String response = mvcResult.getResponse().getContentAsString();
        Assert.assertNotNull(response);
        log.info("AccountBundlingSettingResponse: " + response);

        // Convert json response to HashMap
        map = mapper.readValue(response, new TypeReference<Map<String, String>>() { });
        Assert.assertTrue(map.get("status").equals("success"));


        log.info("AccountBundlingSetting created");


    }

    @Test
    public void deleteAccountBundlingSetting() throws  Exception {


        // Create the accountBundlingSetting
        accountBundlingSetting = accountBundlingSettingService.saveAccountBundlingSetting(accountBundlingSetting);
        Assert.assertNotNull(accountBundlingSetting.getAbsId());
        log.info("AccountBundlingSetting created");

        tempSet.add(accountBundlingSetting);

        // Place the delete request
        MvcResult mvcResult = mockMvc.perform(post("/api/0.9/json/merchant/bundling/setting/delete/" + accountBundlingSetting.getAbsId())
                                                    .principal(principal)
                                                    .session(session)
                                                    .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                                            )
                                            .andReturn();
        String response = mvcResult.getResponse().getContentAsString();
        Assert.assertNotNull(response);
        log.info("AccountBundlingSettingResponse: " + response);

        // Convert json response to HashMap
        map = mapper.readValue(response, new TypeReference<Map<String, String>>() { });
        Assert.assertTrue(map.get("status").equals("success"));


        log.info("AccountBundlingSetting deleted");

        // Remove the item from the tempset as it was deleted successfully
        tempSet.remove(accountBundlingSetting);

    }

    @Test
    public void listAccountBundlingSettings() throws Exception  {

        //Add the data
        accountBundlingSetting = accountBundlingSettingService.saveAccountBundlingSetting(accountBundlingSetting);

        tempSet.add(accountBundlingSetting);

        // Place the delete request
        MvcResult mvcResult = mockMvc.perform(get("/api/0.9/json/merchant/bundling/settings/0/0")
                .principal(principal)
                .session(session)
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)

        )
                .andReturn();

        String response = mvcResult.getResponse().getContentAsString();
        Assert.assertNotNull(response);
        log.info("AccountBundlingSettingResponse: " + response);

        // Convert json response to HashMap
        Assert.assertTrue(response.contains("success"));


    }

    @Test
    public void getAccountBundlingSettingInfo()   throws Exception  {

        //Add the data
        accountBundlingSetting = accountBundlingSettingService.saveAccountBundlingSetting(accountBundlingSetting);

        tempSet.add(accountBundlingSetting);

        // Place the delete request
        MvcResult mvcResult = mockMvc.perform(get("/api/0.9/json/merchant/bundling/setting")
                .principal(principal)
                .session(session)
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)

        )
                .andReturn();

        String response = mvcResult.getResponse().getContentAsString();
        Assert.assertNotNull(response);
        log.info("AccountBundlingSettingResponse: " + response);

        // Convert json response to HashMap
        Assert.assertTrue(response.contains("success"));


    }





    @After
    public void tearDown() throws InspireNetzException {

        for(AccountBundlingSetting accountBundlingSetting: tempSet) {

            accountBundlingSettingService.deleteAccountBundlingSetting(accountBundlingSetting.getAbsId());

        }
    }
}
