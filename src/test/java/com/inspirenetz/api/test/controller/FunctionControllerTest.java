package com.inspirenetz.api.test.controller;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.inspirenetz.api.config.SecurityConfig;
import com.inspirenetz.api.core.domain.Function;
import com.inspirenetz.api.core.service.FunctionService;
import com.inspirenetz.api.test.config.*;
import com.inspirenetz.api.test.core.fixture.FunctionFixture;
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
public class FunctionControllerTest {


    private static Logger log = LoggerFactory.getLogger(FunctionControllerTest.class);


    private static MockMvc mockMvc;

    private static ObjectMapper mapper;

    @Autowired
    private FunctionService functionService;

    @Autowired
    private WebApplicationContext wac;

    @Resource
    private FilterChainProxy springSecurityFilterChain;

    @Autowired
    @Qualifier("uds")
    protected UserDetailsService userDetailsService;

    // Function object
    private Function function;

    UsernamePasswordAuthenticationToken principal;

    MockHttpSession session;

    // Map to hold the returned json
    Map<String,String> map = new HashMap<>();




    @Before
    public void setUp()
    {
        useMerchantAdminSession();
    }


    protected void useMerchantAdminSession() {

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


    protected void useSuperAdminSession() {

        try {

            // Set the principal
            principal = ControllerTestUtils.getPrincipal(ControllerTestUtils.TEST_SUPER_ADMIN_USER_LOGINID,userDetailsService);

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

        // Create the function
        function = FunctionFixture.standardFunction();


    }



    @Test
    public void saveFunction() throws Exception {


        MvcResult mvcResult = mockMvc.perform(post("/api/0.9/json/admin/function")
                .principal(principal)
                .session(session)
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .param("fncName", function.getFncFunctionName())
                .param("fncDescription",function.getFncDescription())
                .param("fncAdminEnabled",function.getFncAdminEnabled())
                .param("fncMerchantAdminEnabled",function.getFncMerchantAdminEnabled())
                .param("fncMerchantUserEnabled",function.getFncMerchantUserEnabled())
                .param("fncCustomerEnabled",function.getFncCustomerEnabled())
                .param("fncVendorUserEnabled",function.getFncCustomerEnabled())
                .param("fncNotifySa",function.getFncNotifySa())
                .param("fncNotifyAdmin",function.getFncNotifyAdmin())
                .param("fncNotifyMa",function.getFncNotifyMa())
                .param("fncNotifyMu",function.getFncNotifyMu())
                .param("fncNotifyUser",function.getFncNotifyUser())
                .param("fncType",function.getFncType().toString())
        )
                .andReturn();
        String response = mvcResult.getResponse().getContentAsString();
        Assert.assertNotNull(response);
        log.info("FunctionResponse: " + response);

        // Convert json response to HashMap
        map = mapper.readValue(response, new TypeReference<Map<String, String>>() { });
        Assert.assertTrue(map.get("status").equals("success"));


        log.info("Function created");


    }

    @Test
    public void deleteFunction() throws  Exception {

        // Create the function
        function = functionService.saveFunction(function);
        Assert.assertNotNull(function.getFncFunctionCode());
        log.info("Function created");


        // Place the delete request
        MvcResult mvcResult = mockMvc.perform(post("/api/0.9/json/admin/function/delete/" + function.getFncFunctionCode())
                .principal(principal)
                .session(session)
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
        )
                .andReturn();
        String response = mvcResult.getResponse().getContentAsString();
        Assert.assertNotNull(response);
        log.info("FunctionResponse: " + response);

        // Convert json response to HashMap
        map = mapper.readValue(response, new TypeReference<Map<String, String>>() { });
        Assert.assertTrue(map.get("status").equals("success"));


        log.info("Function deleted");


    }



    @Test
    public void getFunctionsByName() throws Exception  {

        // Get the set of functions
        Set<Function> functionSet = FunctionFixture.standardFunctions();
        List<Function>  functionList = new ArrayList<Function>();
        functionList.addAll(functionSet);
        functionService.saveAll(functionList);


        // Place the delete request
        MvcResult mvcResult = mockMvc.perform(get("/api/0.9/json/admin/function/name/TEST")
                .principal(principal)
                .session(session)
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
        )
                .andReturn();

        String response = mvcResult.getResponse().getContentAsString();
        Assert.assertNotNull(response);
        log.info("FunctionResponse: " + response);

        // Convert json response to HashMap
        Assert.assertTrue(response.contains("success"));


    }





    @Test
    public void listFunctions() throws Exception  {

        // Place the delete request
        MvcResult mvcResult = mockMvc.perform(get("/api/0.9/json/admin/functions")
                .principal(principal)
                .session(session)
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)

        )
                .andReturn();

        String response = mvcResult.getResponse().getContentAsString();
        Assert.assertNotNull(response);
        log.info("FunctionResponse: " + response);

        // Convert json response to HashMap
        Assert.assertTrue(response.contains("success"));


    }


    @Test
    public void getFunctionsByUserType() throws Exception  {

        // Create the function
        Function function = FunctionFixture.standardFunction();
        functionService.saveFunction(function);
        Assert.assertNotNull(function.getFncFunctionCode());
        log.info("Function created");

        // Place the api call
        MvcResult mvcResult = mockMvc.perform(get("/api/0.9/json/functions/usertype/2")
                .principal(principal)
                .session(session)
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)

        )
                .andReturn();

        String response = mvcResult.getResponse().getContentAsString();
        Assert.assertNotNull(response);
        log.info("FunctionResponse: " + response);

    }


    @Test
    public void getFunctionsByUserTypeForSuperAdmin() throws Exception  {

        useSuperAdminSession();
        /*
        // Create the function
        Function function = FunctionFixture.standardFunction();
        functionService.saveFunction(function);
        Assert.assertNotNull(function.getFncFunctionCode());
        log.info("Function created");
        */

        // Place the api call
        MvcResult mvcResult = mockMvc.perform(get("/api/0.9/json/functions/usertype/3")
                .principal(principal)
                .session(session)
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)

        )
                .andReturn();

        String response = mvcResult.getResponse().getContentAsString();
        Assert.assertNotNull(response);
        log.info("FunctionResponse: " + response);

    }


    @After
    public void tearDown() throws Exception {

        Set<Function> functions = FunctionFixture.standardFunctions();

        for(Function function: functions) {

            Function delFunction = functionService.findByFncFunctionName(function.getFncFunctionName());

            if ( delFunction != null ) {
                functionService.deleteFunction(delFunction.getFncFunctionCode());
            }

        }
    }

}