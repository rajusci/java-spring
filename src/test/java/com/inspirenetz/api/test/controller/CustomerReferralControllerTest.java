package com.inspirenetz.api.test.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.inspirenetz.api.core.domain.CustomerReferral;
import com.inspirenetz.api.core.domain.PointTransferRequest;
import com.inspirenetz.api.core.service.CustomerReferralService;
import com.inspirenetz.api.core.service.PointTransferRequestService;
import com.inspirenetz.api.rest.exception.InspireNetzException;
import com.inspirenetz.api.test.config.*;
import com.inspirenetz.api.test.core.fixture.CustomerReferralFixture;
import com.inspirenetz.api.test.core.fixture.PointTransferRequestFixture;
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
 * Created by fayiz on 27/4/15.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration(classes  = {ApplicationTestConfig.class, PersistenceTestConfig.class, ServiceTestConfig.class, SecurityTestConfig.class, WebTestConfig.class, NotificationTestConfig.class})
public class CustomerReferralControllerTest {
    private static Logger log = LoggerFactory.getLogger(MessageSpielControllerTest.class);

    private static MockMvc mockMvc;

    private static ObjectMapper mapper;

    Set<CustomerReferral> tempSet = new HashSet<>(0);

    @Autowired
    private CustomerReferralService customerReferralService;

    UsernamePasswordAuthenticationToken principal;

    @Autowired
    private WebApplicationContext wac;

    @Resource
    private FilterChainProxy springSecurityFilterChain;

    @Autowired
    @Qualifier("uds")
    protected UserDetailsService userDetailsService;

    MockHttpSession session;

    private CustomerReferral customerReferral;

    // Map to hold the returned json
    Map<String,String> map = new HashMap<>();

    @Before
    public void setup() {



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

            SecurityContextHolder.getContext().setAuthentication(principal);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void init() throws Exception{


        // Create the Mapper object
        mapper = new ObjectMapper();

        // Create the linkRequest
        customerReferral = CustomerReferralFixture.standardCustomerReferral();


    }

    @Test
    public void saveCustomerReferral() throws Exception {


        // Place the save request
        MvcResult mvcResult = mockMvc.perform(post("/api/0.9/json/customer/referral/request")
                .principal(principal)
                .session(session)
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .param("referrerMin",customerReferral.getCsrLoyaltyId())
                .param("refereeMin",customerReferral.getCsrRefMobile())

        )
                .andReturn();

        String response = mvcResult.getResponse().getContentAsString();

        Assert.assertNotNull(response);

        log.info("saveCustomerReferral: " + response);

        // Convert json response to HashMap
        Assert.assertTrue(response.contains("success"));

    }



    @After
    public void tearDown() throws InspireNetzException {


        Set<CustomerReferral> customerReferrals = CustomerReferralFixture.standardCustomerReferrals();

        for(CustomerReferral customerReferral: customerReferrals) {

            CustomerReferral delcuCustomerReferral = customerReferralService.findByCsrMerchantNoAndCsrLoyaltyIdAndCsrRefMobile(customerReferral.getCsrMerchantNo(), customerReferral.getCsrLoyaltyId(), customerReferral.getCsrRefMobile());

            if ( delcuCustomerReferral != null ) {
                customerReferralService.deleteCustomerReferral(delcuCustomerReferral.getCsrId());
            }

        }

    }
}
