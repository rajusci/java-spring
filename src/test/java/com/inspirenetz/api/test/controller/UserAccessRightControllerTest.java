package com.inspirenetz.api.test.controller;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.inspirenetz.api.config.SecurityConfig;
import com.inspirenetz.api.core.domain.UserAccessRight;
import com.inspirenetz.api.core.service.UserAccessRightService;
import com.inspirenetz.api.test.config.*;
import com.inspirenetz.api.test.core.fixture.UserAccessRightFixture;
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
@ContextConfiguration(classes  = {ApplicationTestConfig.class, PersistenceTestConfig.class, ServiceTestConfig.class, SecurityConfig.class, WebTestConfig.class, NotificationTestConfig.class})
public class UserAccessRightControllerTest {


    private static Logger log = LoggerFactory.getLogger(UserAccessRightControllerTest.class);


    private static MockMvc mockMvc;

    private static ObjectMapper mapper;

    @Autowired
    private UserAccessRightService userAccessRightService;

    @Autowired
    private WebApplicationContext wac;

    @Resource
    private FilterChainProxy springSecurityFilterChain;

    @Autowired
    @Qualifier("uds")
    protected UserDetailsService userDetailsService;

    // UserAccessRight object
    private UserAccessRight userAccessRight;

    UsernamePasswordAuthenticationToken principal;

    MockHttpSession session;

    // Map to hold the returned json
    Map<String,String> map = new HashMap<>();




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

        // Create the userAccessRight
        userAccessRight = UserAccessRightFixture.standardUserAccessRight();


    }

    @Test
    public void saveUserAccessRight() throws Exception {


        MvcResult mvcResult = mockMvc.perform(post("/api/0.9/json/admin/uar/save")
                                                .principal(principal)
                                                .session(session)
                                                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                                                .param("uarUserNo",userAccessRight.getUarUserNo().toString())
                                                .param("uarFunctionCode",userAccessRight.getUarFunctionCode().toString())
                                                .param("uarAccessEnableFlag", userAccessRight.getUarAccessEnableFlag())
                                              )
                                        .andReturn();
        String response = mvcResult.getResponse().getContentAsString();
        Assert.assertNotNull(response);
        log.info("UserAccessRightResponse: " + response);

        // Convert json response to HashMap
        map = mapper.readValue(response, new TypeReference<Map<String, String>>() { });
        Assert.assertTrue(map.get("status").equals("success"));


        log.info("UserAccessRight created");


    } 

    @Test
    public void listUserAccessRightsMap() throws Exception  {

        /*userAccessRight = userAccessRightService.saveUserAccessRight(userAccessRight);*/

        // Place the delete request
        MvcResult mvcResult = mockMvc.perform(get("/api/0.9/json/user/uars/map")
                .principal(principal)
                .session(session)
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)

        )
                .andReturn();

        String response = mvcResult.getResponse().getContentAsString();
        Assert.assertNotNull(response);
        log.info("UserAccessRightResponse: " + response);

        // Convert json response to HashMap
        Assert.assertTrue(response.contains("success"));


    }



    @After
    public void tearDown() {

        Set<UserAccessRight> userAccessRights = UserAccessRightFixture.standardUserAccessRights();

        for(UserAccessRight userAccessRight: userAccessRights) {

            UserAccessRight delUserAccessRight = userAccessRightService.findByUarUserNoAndUarFunctionCode(userAccessRight.getUarUserNo(), userAccessRight.getUarFunctionCode());

            if ( delUserAccessRight != null ) {
                userAccessRightService.deleteUserAccessRight(delUserAccessRight.getUarUarId());
            }

        }
    }

}
