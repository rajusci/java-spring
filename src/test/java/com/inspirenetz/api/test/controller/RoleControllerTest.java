package com.inspirenetz.api.test.controller;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.inspirenetz.api.core.domain.Customer;
import com.inspirenetz.api.core.domain.Role;
import com.inspirenetz.api.core.domain.LinkedLoyalty;
import com.inspirenetz.api.core.service.CustomerService;
import com.inspirenetz.api.core.service.RoleService;
import com.inspirenetz.api.core.service.LinkedLoyaltyService;
import com.inspirenetz.api.test.config.*;
import com.inspirenetz.api.test.core.fixture.CustomerFixture;
import com.inspirenetz.api.test.core.fixture.RoleFixture;
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
 * Created by ameen on 30/4/14.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration(classes  = {ApplicationTestConfig.class, PersistenceTestConfig.class, ServiceTestConfig.class, SecurityTestConfig.class, WebTestConfig.class, NotificationTestConfig.class})
public class RoleControllerTest {


    private static Logger log = LoggerFactory.getLogger(RoleControllerTest.class);

    private static MockMvc mockMvc;

    private static ObjectMapper mapper;

    @Autowired
    private RoleService roleService;

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

    // Role object
    private Role role;

    UsernamePasswordAuthenticationToken principal;

    MockHttpSession session;

    // Map to hold the returned json
    Map<String,String> map = new HashMap<>();

    Set<Role> tempSet = new HashSet<>(0);

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

        // Create the role

        RoleFixture roleFixture=new RoleFixture();

        role = roleFixture.standardRole();


    }




    @Test
    public void saveRole() throws Exception {


        // Convert to JSON

        log.info(role+"role");
        ObjectMapper objectMapper = new ObjectMapper();
        String roleData = objectMapper.writeValueAsString(role);
        log.info("JSON string : " + roleData);

        MvcResult mvcResult = mockMvc.perform(post("/api/0.9/json/merchant/roles/role")
                .principal(principal)
                .session(session)
                .contentType(MediaType.valueOf(MediaType.APPLICATION_JSON_VALUE))
                .content(roleData)
        )
                .andReturn();
        String response = mvcResult.getResponse().getContentAsString();
        Assert.assertNotNull(response);
        log.info("RoleResponse: " + response);

        // Convert json response to HashMap
        map = mapper.readValue(response, new TypeReference<Map<String, String>>() { });
        Assert.assertTrue(map.get("status").equals("success"));

        role.setRolId(Long.parseLong(map.get("data")));
        tempSet.add(role);

        log.info("Role created");


    }

    @Test
    public void deleteRole() throws  Exception {

        // Create the role
        role = roleService.saveRole(role);
        Assert.assertNotNull(role.getRolId());
        log.info("Role created");

        tempSet.add(role);


        // Place the delete request
        MvcResult mvcResult = mockMvc.perform(post("/api/0.9/json/merchant/roles/role/delete/" + role.getRolId())
                .principal(principal)
                .session(session)
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
        )
                .andReturn();
        String response = mvcResult.getResponse().getContentAsString();
        Assert.assertNotNull(response);
        log.info("RoleResponse: " + response);

        // Convert json response to HashMap
        map = mapper.readValue(response, new TypeReference<Map<String, String>>() { });
        Assert.assertTrue(map.get("status").equals("success"));


        // Remove the requst from the tempset
        tempSet.remove(role);

        log.info("Role deleted");


    }

    @Test
    public void listRoles() throws Exception  {

        //Add the data
        role = roleService.saveRole(role);

        tempSet.add(role);

        // Place the delete request
        MvcResult mvcResult = mockMvc.perform(get("/api/0.9/json/merchant/roles/role/0/0")
                .principal(principal)
                .session(session)
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)

        )
                .andReturn();

        String response = mvcResult.getResponse().getContentAsString();
        Assert.assertNotNull(response);
        log.info("RoleResponse: " + response);

        // Convert json response to HashMap
        Assert.assertTrue(response.contains("success"));


    }

    @Test
    public void getRoleInfo()   throws Exception  {

        //Add the data
        role = roleService.saveRole(role);

        tempSet.add(role);

        // Place the delete request
        MvcResult mvcResult = mockMvc.perform(get("/api/0.9/json/merchant/roles/role/"+role.getRolId())
                .principal(principal)
                .session(session)
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)

        )
                .andReturn();

        String response = mvcResult.getResponse().getContentAsString();
        Assert.assertNotNull(response);
        log.info("RoleResponse: " + response);

        // Convert json response to HashMap
        Assert.assertTrue(response.contains("success"));


    }



    @After
    public void tearDown() {



        for(Role role: tempSet) {


            roleService.deleteRole(role.getRolId());

        }

    }

}