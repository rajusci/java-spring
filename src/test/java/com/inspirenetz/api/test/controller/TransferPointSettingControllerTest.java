package com.inspirenetz.api.test.controller;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.inspirenetz.api.core.domain.TransferPointSetting;
import com.inspirenetz.api.core.service.TransferPointSettingService;
import com.inspirenetz.api.rest.exception.InspireNetzException;
import com.inspirenetz.api.test.config.*;
import com.inspirenetz.api.test.core.fixture.TransferPointSettingFixture;
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
import org.springframework.security.core.context.SecurityContextHolder;
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
public class TransferPointSettingControllerTest {


    private static Logger log = LoggerFactory.getLogger(TransferPointSettingControllerTest.class);

    private static MockMvc mockMvc;

    private static ObjectMapper mapper;

    @Autowired
    private TransferPointSettingService transferPointSettingService;

    @Autowired
    private WebApplicationContext wac;

    @Resource
    private FilterChainProxy springSecurityFilterChain;

    @Autowired
    @Qualifier("uds")
    protected UserDetailsService userDetailsService;

    // TransferPointSetting object
    private TransferPointSetting transferPointSetting;

    UsernamePasswordAuthenticationToken principal;

    MockHttpSession session;

    // Map to hold the returned json
    Map<String,String> map = new HashMap<>();


    Set<TransferPointSetting> tempSet= new HashSet<>(0);


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

            // Set the context
            SecurityContextHolder.getContext().setAuthentication(principal);


        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    private void init() throws Exception{


        // Create the Mapper object
        mapper = new ObjectMapper();

        // Create the transferPointSetting
        transferPointSetting = TransferPointSettingFixture.standardTransferPointSetting();


    }




    @Test
    public void saveTransferPointSetting() throws Exception {


        MvcResult mvcResult = mockMvc.perform(post("/api/0.9/json/merchant/customer/rewardbalance/transferpoint/setting")
                                                .principal(principal)
                                                .session(session)
                                                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                                                .param("tpsTransferCompatibility",transferPointSetting.getTpsTransferCompatibility().toString())
                                                .param("tpsTransferCharge",transferPointSetting.getTpsTransferCharge().toString())
                                                .param("tpsTransferredPointValidity", transferPointSetting.getTpsTransferredPointValidity().toString())
                                              )
                                        .andReturn();
        String response = mvcResult.getResponse().getContentAsString();
        Assert.assertNotNull(response);
        log.info("TransferPointSettingResponse: " + response);

        // Convert json response to HashMap
        map = mapper.readValue(response, new TypeReference<Map<String, String>>() { });
        Assert.assertTrue(map.get("status").equals("success"));


        log.info("TransferPointSetting created");


    }

    @Test
    public void deleteTransferPointSetting() throws  Exception {


        // Create the transferPointSetting
        transferPointSetting = transferPointSettingService.saveTransferPointSetting(transferPointSetting);
        Assert.assertNotNull(transferPointSetting.getTpsId());
        log.info("TransferPointSetting created");

        tempSet.add(transferPointSetting);

        // Place the delete request
        MvcResult mvcResult = mockMvc.perform(post("/api/0.9/json/merchant/customer/rewardbalance/transferpoint/setting/delete/" + transferPointSetting.getTpsId())
                                                    .principal(principal)
                                                    .session(session)
                                                    .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                                            )
                                            .andReturn();
        String response = mvcResult.getResponse().getContentAsString();
        Assert.assertNotNull(response);
        log.info("TransferPointSettingResponse: " + response);

        // Convert json response to HashMap
        map = mapper.readValue(response, new TypeReference<Map<String, String>>() { });
        Assert.assertTrue(map.get("status").equals("success"));


        log.info("TransferPointSetting deleted");

        // Remove the item from the tempset as it was deleted successfully
        tempSet.remove(transferPointSetting);

    }

    @Test
    public void listTransferPointSettings() throws Exception  {

        //Add the data
        transferPointSetting = transferPointSettingService.saveTransferPointSetting(transferPointSetting);

        tempSet.add(transferPointSetting);

        // Place the delete request
        MvcResult mvcResult = mockMvc.perform(get("/api/0.9/json/merchant/customer/rewardbalance/transferpoint/settings/0/0")
                .principal(principal)
                .session(session)
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)

        )
                .andReturn();

        String response = mvcResult.getResponse().getContentAsString();
        Assert.assertNotNull(response);
        log.info("TransferPointSettingResponse: " + response);

        // Convert json response to HashMap
        Assert.assertTrue(response.contains("success"));


    }

    @Test
    public void getTransferPointSettingInfo()   throws Exception  {

        //Add the data
        transferPointSetting = transferPointSettingService.saveTransferPointSetting(transferPointSetting);

        tempSet.add(transferPointSetting);

        // Place the delete request
        MvcResult mvcResult = mockMvc.perform(get("/api/0.9/json/merchant/customer/rewardbalance/transferpoint/setting")
                .principal(principal)
                .session(session)
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)

        )
                .andReturn();

        String response = mvcResult.getResponse().getContentAsString();
        Assert.assertNotNull(response);
        log.info("TransferPointSettingResponse: " + response);

        // Convert json response to HashMap
        Assert.assertTrue(response.contains("success"));


    }





    @After
    public void tearDown() throws InspireNetzException {

        for(TransferPointSetting transferPointSetting: tempSet) {

            transferPointSettingService.deleteTransferPointSetting(transferPointSetting.getTpsId());

        }
    }
}
