package com.inspirenetz.api.test.controller;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.inspirenetz.api.core.domain.Customer;
import com.inspirenetz.api.core.domain.LoyaltyExtension;
import com.inspirenetz.api.core.domain.LinkedLoyalty;
import com.inspirenetz.api.core.service.CustomerService;
import com.inspirenetz.api.core.service.LoyaltyExtensionService;
import com.inspirenetz.api.core.service.LinkedLoyaltyService;
import com.inspirenetz.api.test.config.*;
import com.inspirenetz.api.test.core.fixture.CustomerFixture;
import com.inspirenetz.api.test.core.fixture.LoyaltyExtensionFixture;
import com.inspirenetz.api.test.core.fixture.LinkedLoyaltyFixture;
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
public class LoyaltyExtensionControllerTest {


    private static Logger log = LoggerFactory.getLogger(LoyaltyExtensionControllerTest.class);

    private static MockMvc mockMvc;

    private static ObjectMapper mapper;

    @Autowired
    private LoyaltyExtensionService loyaltyExtensionService;

    @Autowired
    private WebApplicationContext wac;

    @Resource
    private FilterChainProxy springSecurityFilterChain;

    @Autowired
    @Qualifier("uds")
    protected UserDetailsService userDetailsService;

    @Autowired
    private CustomerService customerService;


    // LoyaltyExtension object
    private LoyaltyExtension loyaltyExtension;

    UsernamePasswordAuthenticationToken principal;

    MockHttpSession session;

    // Map to hold the returned json
    Map<String,String> map = new HashMap<>();

    Set<LoyaltyExtension> tempSet = new HashSet<>(0);


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

        // Create the loyaltyExtension
        loyaltyExtension = LoyaltyExtensionFixture.standardLoyaltyExtension();


    }




    @Test
    public void saveLoyaltyExtension() throws Exception {


        MvcResult mvcResult = mockMvc.perform(post("/api/0.9/json/merchant/loyalty/extension")
                .principal(principal)
                .session(session)
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .param("lexName", loyaltyExtension.getLexName())
                .param("lexDescription", loyaltyExtension.getLexDescription())
                .param("lexFile", loyaltyExtension.getLexFile().toString())
        )
                .andReturn();
        String response = mvcResult.getResponse().getContentAsString();
        Assert.assertNotNull(response);
        log.info("LoyaltyExtensionResponse: " + response);

        // Convert json response to HashMap
        map = mapper.readValue(response, new TypeReference<Map<String, String>>() { });
        Assert.assertTrue(map.get("status").equals("success"));

        loyaltyExtension.setLexId(Long.parseLong(map.get("data")));
        tempSet.add(loyaltyExtension);

        log.info("LoyaltyExtension created");


    }

    @Test
    public void deleteLoyaltyExtension() throws  Exception {

        // Create the loyaltyExtension
        loyaltyExtension = loyaltyExtensionService.saveLoyaltyExtension(loyaltyExtension);
        Assert.assertNotNull(loyaltyExtension.getLexId());
        log.info("LoyaltyExtension created");

        tempSet.add(loyaltyExtension);


        // Place the delete request
        MvcResult mvcResult = mockMvc.perform(post("/api/0.9/json/merchant/loyalty/extension/delete/" + loyaltyExtension.getLexId())
                .principal(principal)
                .session(session)
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
        )
                .andReturn();
        String response = mvcResult.getResponse().getContentAsString();
        Assert.assertNotNull(response);
        log.info("LoyaltyExtensionResponse: " + response);

        // Convert json response to HashMap
        map = mapper.readValue(response, new TypeReference<Map<String, String>>() { });
        Assert.assertTrue(map.get("status").equals("success"));


        // Remove the requst from the tempset
        tempSet.remove(loyaltyExtension);

        log.info("LoyaltyExtension deleted");


    }

    @Test
    public void listLoyaltyExtensions() throws Exception  {

        //Add the data
        loyaltyExtension = loyaltyExtensionService.saveLoyaltyExtension(loyaltyExtension);

        tempSet.add(loyaltyExtension);

        // Place the delete request
        MvcResult mvcResult = mockMvc.perform(get("/api/0.9/json/merchant/loyalty/extensions/0/0")
                .principal(principal)
                .session(session)
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)

        )
                .andReturn();

        String response = mvcResult.getResponse().getContentAsString();
        Assert.assertNotNull(response);
        log.info("LoyaltyExtensionResponse: " + response);

        // Convert json response to HashMap
        Assert.assertTrue(response.contains("success"));


    }

    @Test
    public void getLoyaltyExtensionInfo()   throws Exception  {

        //Add the data
        loyaltyExtension = loyaltyExtensionService.saveLoyaltyExtension(loyaltyExtension);

        tempSet.add(loyaltyExtension);

        // Place the delete request
        MvcResult mvcResult = mockMvc.perform(get("/api/0.9/json/merchant/loyalty/extension/"+loyaltyExtension.getLexId())
                .principal(principal)
                .session(session)
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)

        )
                .andReturn();

        String response = mvcResult.getResponse().getContentAsString();
        Assert.assertNotNull(response);
        log.info("LoyaltyExtensionResponse: " + response);

        // Convert json response to HashMap
        Assert.assertTrue(response.contains("success"));


    }
    @After
    public void tearDown() {



        for(LoyaltyExtension loyaltyExtension: tempSet) {


            loyaltyExtensionService.deleteLoyaltyExtension(loyaltyExtension.getLexId());

        }


    }

}