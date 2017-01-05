package com.inspirenetz.api.test.controller;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.inspirenetz.api.core.domain.Customer;
import com.inspirenetz.api.core.domain.LinkedLoyalty;
import com.inspirenetz.api.core.domain.SecuritySetting;
import com.inspirenetz.api.core.service.CustomerService;
import com.inspirenetz.api.core.service.LinkedLoyaltyService;
import com.inspirenetz.api.core.service.SecuritySettingService;
import com.inspirenetz.api.test.config.*;
import com.inspirenetz.api.test.core.fixture.SecuritySettingFixture;
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
 * Created by ameen on 30/4/14.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration(classes  = {ApplicationTestConfig.class, PersistenceTestConfig.class, ServiceTestConfig.class, SecurityTestConfig.class, WebTestConfig.class, NotificationTestConfig.class})
public class SecuritySettingControllerTest {


    private static Logger log = LoggerFactory.getLogger(SecuritySettingControllerTest.class);

    private static MockMvc mockMvc;

    private static ObjectMapper mapper;

    @Autowired
    private SecuritySettingService securitySettingService;

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

    // SecuritySetting object
    private SecuritySetting securitySetting;

    UsernamePasswordAuthenticationToken principal;

    MockHttpSession session;

    // Map to hold the returned json
    Map<String,String> map = new HashMap<>();

    Set<SecuritySetting> tempSet = new HashSet<>(0);

    Set<Customer> customerSet = new HashSet<>(0);

    Set<LinkedLoyalty> linkLoyaltySet = new HashSet<>(0);


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

        // Create the securitySetting

        SecuritySettingFixture securitySettingFixture=new SecuritySettingFixture();

        securitySetting = securitySettingFixture.standardSecuritySetting();


    }




    @Test
    public void saveSecuritySetting() throws Exception {


        // Convert to JSON

        log.info(securitySetting+"securitySetting");
        ObjectMapper objectMapper = new ObjectMapper();
        String securitySettingData = objectMapper.writeValueAsString(securitySetting);
        log.info("JSON string : " + securitySettingData);

        MvcResult mvcResult = mockMvc.perform(post("/api/0.9/json/merchant/securitysettings/securitysetting")
                .principal(principal)
                .session(session)
                .contentType(MediaType.valueOf(MediaType.APPLICATION_FORM_URLENCODED_VALUE))
                .content(securitySettingData)
        )
                .andReturn();
        String response = mvcResult.getResponse().getContentAsString();
        Assert.assertNotNull(response);
        log.info("SecuritySettingResponse: " + response);

        // Convert json response to HashMap
        map = mapper.readValue(response, new TypeReference<Map<String, String>>() { });
        Assert.assertTrue(map.get("status").equals("success"));

        securitySetting.setSecId(Long.parseLong(map.get("data")));
        tempSet.add(securitySetting);

        log.info("SecuritySetting created");


    }



    @Test
    public void getSecuritySettingInfo()   throws Exception  {

        //Add the data
        securitySetting = securitySettingService.saveSecuritySetting(securitySetting);

        tempSet.add(securitySetting);

        // Place the delete request
        MvcResult mvcResult = mockMvc.perform(get("/api/0.9/json/merchant/securitysettings/securitysetting")
                .principal(principal)
                .session(session)
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)

        )
                .andReturn();

        String response = mvcResult.getResponse().getContentAsString();
        Assert.assertNotNull(response);
        log.info("SecuritySettingResponse: " + response);

        // Convert json response to HashMap
        Assert.assertTrue(response.contains("success"));


    }



    @After
    public void tearDown() {



        for(SecuritySetting securitySetting: tempSet) {


            securitySettingService.deleteSecuritySetting(securitySetting);

        }

    }

}