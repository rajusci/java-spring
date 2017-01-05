package com.inspirenetz.api.test.controller;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.inspirenetz.api.core.domain.Customer;
import com.inspirenetz.api.core.domain.User;
import com.inspirenetz.api.core.domain.UserProfile;
import com.inspirenetz.api.core.service.CustomerService;
import com.inspirenetz.api.core.service.UserProfileService;
import com.inspirenetz.api.core.service.UserService;
import com.inspirenetz.api.rest.exception.InspireNetzException;
import com.inspirenetz.api.test.config.*;
import com.inspirenetz.api.test.core.fixture.CustomerFixture;
import com.inspirenetz.api.test.core.fixture.UserFixture;
import com.inspirenetz.api.test.core.fixture.UserProfileFixture;
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
 * Created by alameen on 25/10/14.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration(classes  = {ApplicationTestConfig.class, PersistenceTestConfig.class, ServiceTestConfig.class, SecurityTestConfig.class, WebTestConfig.class, NotificationTestConfig.class})
public class UserProfileControllerTest {

    private static Logger log = LoggerFactory.getLogger(UserProfileControllerTest.class);

    private static MockMvc mockMvc;

    private static ObjectMapper mapper;

    Set<UserProfile> tempSet = new HashSet<>(0);


    @Autowired
    private UserProfileService userProfileService;

    @Autowired
    private WebApplicationContext wac;

    @Resource
    private FilterChainProxy springSecurityFilterChain;

    @Autowired
    @Qualifier("uds")
    protected UserDetailsService userDetailsService;

    @Autowired
    UserService userService;

    @Autowired
    CustomerService customerService;

    UsernamePasswordAuthenticationToken principal;

    MockHttpSession session;

    private UserProfile userProfile;


    Set<UserProfile> tempSet5 = new HashSet<>(0);

    Set<User> tempSet1 = new HashSet<>(0);

    Set<Customer> tempSet2 = new HashSet<>(0);
    // Map to hold the returned json
    Map<String,String> map = new HashMap<>();

    @Before
    public void setup() {



        try{
            // Set the principal
            principal = ControllerTestUtils.getPrincipal(ControllerTestUtils.TEST_CUSTOMER_LOGINID, userDetailsService);

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
        userProfile = UserProfileFixture.standardUserProfile();


    }

    @Test
    public void saveUserProfile() throws Exception {


        MvcResult mvcResult = mockMvc.perform(post("/api/0.9/json/customer/profile")
                .principal(principal)
                .session(session)
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .param("uspAddress1",userProfile.getUspAddress1().toString())
                .param("uspAddress2",userProfile.getUspAddress2().toString())
                .param("uspAddress3", userProfile.getUspAddress3().toString())
                .param("uspUserNo", userProfile.getUspUserNo().toString())
                .param("uspBirthday",userProfile.getUspBirthday().toString())
                .param("usrFName",userProfile.getUsrFName())
                .param("usrLName",userProfile.getUsrLName())
                .param("usrProfilePic",userProfile.getUsrProfilePic().toString())
        )
                .andReturn();
        String response = mvcResult.getResponse().getContentAsString();
        Assert.assertNotNull(response);
        log.info("saveUserProfile: " + response);

        // Convert json response to HashMap
        map = mapper.readValue(response, new TypeReference<Map<String, String>>() { });
        Assert.assertTrue(map.get("status").equals("success"));

        log.info("saveUserProfile created");


    }


    @Test
    public void getUserProfileByUspUserNo()   throws Exception  {

        //Add the data

       //save user
        User user = UserFixture.standardUser();
        user = userService.saveUser(user);
        tempSet1.add(user);
        Customer customer = CustomerFixture.standardCustomer(user);
        customerService.saveCustomer(customer);
        Assert.assertNotNull(customer.getCusCustomerNo());
        log.info("Customer created");
        tempSet2.add(customer);

        // Get the standard userProfile
        UserProfile userProfile = UserProfileFixture.standardUserProfile(user);
        userProfile =userProfileService.saveUserProfile(userProfile);
        Assert.assertNotNull(userProfile.getUspId());

        tempSet5.add(userProfile);


        // Place the delete request
        MvcResult mvcResult = mockMvc.perform(get("/api/0.9/json/customer/profile/"+customer.getCusCustomerNo())
                .principal(principal)
                .session(session)
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)

        )
                .andReturn();

        String response = mvcResult.getResponse().getContentAsString();
        Assert.assertNotNull(response);
        log.info("UserProfile : " + response);

        // Convert json response to HashMap
        Assert.assertTrue(response.contains("success"));


    }

    @After
    public void tearDown() throws InspireNetzException {


        for ( UserProfile userProfile : tempSet5 ) {

            userProfileService.delete(userProfile);

        }


        for ( Customer customer : tempSet2 ) {

            customerService.deleteCustomer(customer.getCusCustomerNo());

        }

        for ( User user : tempSet1 ) {

            userService.deleteUser(user);

        }


    }



}
