package com.inspirenetz.api.test.controller;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.inspirenetz.api.config.SecurityConfig;
import com.inspirenetz.api.core.domain.MerchantModule;
import com.inspirenetz.api.core.domain.MerchantSetting;
import com.inspirenetz.api.core.domain.Module;
import com.inspirenetz.api.core.service.MerchantSettingService;
import com.inspirenetz.api.test.config.*;
import com.inspirenetz.api.test.core.fixture.MerchantSettingFixture;
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
public class MerchantSettingControllerTest {


    private static Logger log = LoggerFactory.getLogger(MerchantSettingControllerTest.class);


    private static MockMvc mockMvc;

    private static ObjectMapper mapper;

    @Autowired
    private MerchantSettingService merchantSettingService;

    @Autowired
    private WebApplicationContext wac;

    @Resource
    private FilterChainProxy springSecurityFilterChain;

    @Autowired
    @Qualifier("uds")
    protected UserDetailsService userDetailsService;

    // MerchantSetting object
    private MerchantSetting merchantSetting;

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

        // Create the merchantSetting
        merchantSetting = MerchantSettingFixture.standardMerchantSetting();


    }



    @Test
    public void saveMerchantSetting() throws Exception {


        MvcResult mvcResult = mockMvc.perform(post("/api/0.9/json/admin/merchantsetting")
                                                .principal(principal)
                                                .session(session)
                                                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                                                .param("mesMerchantNo",merchantSetting.getMesMerchantNo().toString())
                                                .param("mesSettingId",merchantSetting.getMesSettingId().toString())
                                                .param("mesValue", merchantSetting.getMesValue())
                                              )
                                        .andReturn();
        String response = mvcResult.getResponse().getContentAsString();
        Assert.assertNotNull(response);
        log.info("MerchantSettingResponse: " + response);

        // Convert json response to HashMap
        Assert.assertTrue(response.contains("success"));

        log.info("MerchantSetting created");


    } 
    
    @Test
    public void deleteMerchantSetting() throws  Exception {

        // Create the merchantSetting
        merchantSetting = merchantSettingService.saveMerchantSetting(merchantSetting);
        log.info("MerchantSetting created");


        // Place the delete request
        MvcResult mvcResult = mockMvc.perform(post("/api/0.9/json/admin/merchantsetting/delete")
                                                    .principal(principal)
                                                    .session(session)
                                                    .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                                                    .param("mesMerchantNo", merchantSetting.getMesMerchantNo().toString())
                                                    .param("mesSettingId",merchantSetting.getMesSettingId().toString())
                                                    .param("mesValue", merchantSetting.getMesValue())
                                            )
                                            .andReturn();
        String response = mvcResult.getResponse().getContentAsString();
        Assert.assertNotNull(response);
        log.info("MerchantSettingResponse: " + response);

        // Convert json response to HashMap
        Assert.assertTrue(response.contains("success"));


        log.info("MerchantSetting deleted");


    }





    @Test
    public void listMerchantSettingsForAdmin() throws Exception  {

        // Place the delete request
        MvcResult mvcResult = mockMvc.perform(get("/api/0.9/json/admin/merchantsettings/"+merchantSetting.getMesMerchantNo().toString())
                .principal(principal)
                .session(session)
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)

        )
                .andReturn();

        String response = mvcResult.getResponse().getContentAsString();
        Assert.assertNotNull(response);
        log.info("MerchantSettingResponse: " + response);

        // Convert json response to HashMap
        Assert.assertTrue(response.contains("success"));


    }


    @Test
    public void listMerchantSettingsMapForMerchant() throws Exception  {

        // Place the delete request
        MvcResult mvcResult = mockMvc.perform(get("/api/0.9/json/merchant/merchantsettings/map")
                .principal(principal)
                .session(session)
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)

        )
                .andReturn();

        String response = mvcResult.getResponse().getContentAsString();

        log.info("MerchantSettingResponse: " + response);



    }


    @After
    public void tearDown() throws Exception {

        Set<MerchantSetting> merchantSettings = MerchantSettingFixture.standardMerchantSettings();

        for(MerchantSetting merchantSetting: merchantSettings) {

            MerchantSetting delMerchantSetting = merchantSettingService.findByMesMerchantNoAndMesSettingId(merchantSetting.getMesMerchantNo(),merchantSetting.getMesSettingId());

            if ( delMerchantSetting != null ) {
                merchantSettingService.deleteMerchantSetting(delMerchantSetting);
            }

        }
    }

}
