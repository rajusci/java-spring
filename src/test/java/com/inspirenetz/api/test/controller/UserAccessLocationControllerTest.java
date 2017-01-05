package com.inspirenetz.api.test.controller;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.inspirenetz.api.core.domain.UserAccessLocation;
import com.inspirenetz.api.core.service.UserAccessLocationService;
import com.inspirenetz.api.test.config.*;
import com.inspirenetz.api.test.core.fixture.UserAccessLocationFixture;
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
 * Created by ameenci on 11/9/14.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration(classes  = {ApplicationTestConfig.class, PersistenceTestConfig.class, ServiceTestConfig.class, SecurityTestConfig.class, WebTestConfig.class, NotificationTestConfig.class})
public class UserAccessLocationControllerTest {

    private static Logger log = LoggerFactory.getLogger(UserAccessLocationControllerTest.class);

    private static MockMvc mockMvc;

    private static ObjectMapper mapper;

    Set<UserAccessLocation> tempSet = new HashSet<>(0);

    @Autowired
    private UserAccessLocationService userAccessLocationService;

    UsernamePasswordAuthenticationToken principal;

    @Autowired
    private WebApplicationContext wac;

    @Resource
    private FilterChainProxy springSecurityFilterChain;

    @Autowired
    @Qualifier("uds")
    protected UserDetailsService userDetailsService;

    MockHttpSession session;

    private UserAccessLocation userAccessLocation;

    // Map to hold the returned json
    Map<String,String> map = new HashMap<>();

    @Before
    public void setup() {



        try{
            // Set the principal
            principal = ControllerTestUtils.getPrincipal(ControllerTestUtils.TEST_MERCHANT_ADMIN_LOGINID, userDetailsService);

            // Set the context
            SecurityContextHolder.getContext().setAuthentication(principal);

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
        }catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void init() throws Exception{


        // Create the Mapper object
        mapper = new ObjectMapper();

        // Create the linkRequest
        userAccessLocation = UserAccessLocationFixture.standardUserAccessLocation();


    }

    @Test
    public void saveUserAccessLocation() throws Exception {


        MvcResult mvcResult = mockMvc.perform(post("/api/0.9/json/admin/useraccesslocation")
                .principal(principal)
                .session(session)
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .param("ualUserId",userAccessLocation.getUalUserId().toString())
                .param("ualLocation",userAccessLocation.getUalLocation().toString())
        )
                .andReturn();
        String response = mvcResult.getResponse().getContentAsString();
        Assert.assertNotNull(response);
        log.info("saveUserAccessLocation: " + response);

        // Convert json response to HashMap
        map = mapper.readValue(response, new TypeReference<Map<String, String>>() { });
        Assert.assertTrue(map.get("status").equals("success"));

        // Get the id
        Long ualId = Long.parseLong(map.get("data"));
        userAccessLocation = userAccessLocationService.findByUalId(ualId);

        // Add to tempSet
        tempSet.add(userAccessLocation);

        log.info(" userAccessLocation is created");


    }

    @Test
    public void listUserAccessLocation() throws Exception  {

        //Add the data
        userAccessLocation = userAccessLocationService.saveUserAccessLocation(userAccessLocation);

        tempSet.add(userAccessLocation);

        // Place the delete request
        MvcResult mvcResult = mockMvc.perform(get("/api/0.9/json/admin/useraccesslocation/"+userAccessLocation.getUalUserId())
                .principal(principal)
                .session(session)
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)

        )
                .andReturn();

        String response = mvcResult.getResponse().getContentAsString();
        Assert.assertNotNull(response);
        log.info("userAccessLocation: " + response);

        // Convert json response to HashMap
        Assert.assertTrue(response.contains("success"));

    }


    @Test
    public void deleteUserAccessLocation() throws  Exception {

        // Create the user Access Location
        userAccessLocation = userAccessLocationService.saveUserAccessLocation(userAccessLocation);

        Assert.assertNotNull(userAccessLocation.getUalId());

        log.info("user Access Location created");

        tempSet.add(userAccessLocation);


        // Place the delete request
        MvcResult mvcResult = mockMvc.perform(post("/api/0.9/json/admin/useraccesslocation/delete/" + userAccessLocation.getUalId())
                .principal(principal)
                .session(session)
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
        )
                .andReturn();
        String response = mvcResult.getResponse().getContentAsString();
        Assert.assertNotNull(response);
        log.info("user Access Location: " + response);

        // Convert json response to HashMap
        map = mapper.readValue(response, new TypeReference<Map<String, String>>() { });
        Assert.assertTrue(map.get("status").equals("success"));


        // Remove the requst from the tempset
        tempSet.remove(userAccessLocation);

        log.info("userAccessLocation is removed");


    }


    @After
    public void tearDown() {


        for ( UserAccessLocation userAccessLocation : tempSet ) {

            userAccessLocationService.deleteUserAccessLocation(userAccessLocation.getUalId());

        }

    }

}
