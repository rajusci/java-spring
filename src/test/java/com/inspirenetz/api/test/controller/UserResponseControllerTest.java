package com.inspirenetz.api.test.controller;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.inspirenetz.api.core.domain.*;
import com.inspirenetz.api.core.service.*;
import com.inspirenetz.api.rest.exception.InspireNetzException;
import com.inspirenetz.api.test.config.*;
import com.inspirenetz.api.test.core.fixture.*;
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
 * Created by alameen on 24/11/14.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration(classes  = {ApplicationTestConfig.class, PersistenceTestConfig.class, ServiceTestConfig.class, SecurityTestConfig.class, WebTestConfig.class, NotificationTestConfig.class})
public class UserResponseControllerTest {

    private static Logger log = LoggerFactory.getLogger(UserResponseControllerTest.class);

    private static MockMvc mockMvc;

    private static ObjectMapper mapper;

    Set<UserResponse> tempSet = new HashSet<>(0);


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

    @Autowired
    PromotionService promotionService;

    @Autowired
    UserResponseService userResponseService;

    private UserResponse userResponse;


    Set<UserResponse> tempSet5 = new HashSet<>(0);

    Set<Promotion> tempSet1 = new HashSet<>(0);

    Set<Customer> tempSet2 = new HashSet<>(0);
    // Map to hold the returned json
    Map<String,String> map = new HashMap<>();

    @Before
    public void setup() {



        try{
            // Set the principal
            principal = ControllerTestUtils.getPrincipal(ControllerTestUtils.TEST_MERCHANT_USER_LOGINID, userDetailsService);

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
       // userResponse = UserResponseFixture.standardUserResponse();


    }


    @Test
    public void listUserResponse()  throws Exception  {

        Promotion promotion = PromotionFixture.standardPromotion();
        promotion = promotionService.savePromotion(promotion);
        Assert.assertNotNull(promotion.getPrmId());
        log.info("Promotion created");

        tempSet1.add(promotion);

        // Create the customer
        Customer customer = CustomerFixture.standardCustomer();
        customerService.saveCustomer(customer);
        Assert.assertNotNull(customer.getCusCustomerNo());
        log.info("Customer created");

        tempSet2.add(customer);

        UserResponse userResponse = userResponseService.saveUserResponse(UserResponseFixture.standardUserResponse(promotion, customer));
        log.info(userResponse.toString());
        Assert.assertNotNull(userResponse.getUrpId());
        // Add to tempSet
        tempSet5.add(userResponse);


        // Place the delete request
        MvcResult mvcResult = mockMvc.perform(get("/api/0.9/json/merchant/promotionresponse/"+userResponse.getUrpResponseItemId()+"/"+userResponse.getUrpResponseType()+"/0"+"/0")
                .principal(principal)
                .session(session)
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)

        )
                .andReturn();

        String response = mvcResult.getResponse().getContentAsString();
        Assert.assertNotNull(response);
        log.info("UserResponse : " + response);

        // Convert json response to HashMap
        Assert.assertTrue(response.contains("success"));


    }

    @After
    public void tearDown() throws InspireNetzException {



        for ( UserResponse userResponse : tempSet ) {

            userResponseService.deleteUserResponse(userResponse);

        }

        for(Promotion promotion :tempSet1){

            promotionService.deletePromotion(promotion.getPrmId());
        }


//        Set<Customer> customers = CustomerFixture.standardCustomers();

        for(Customer customer: tempSet2) {

            customerService.deleteCustomer(customer.getCusCustomerNo());

        }

    }
}
