package com.inspirenetz.api.test.controller;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.collect.Lists;
import com.inspirenetz.api.core.domain.Customer;
import com.inspirenetz.api.core.domain.LinkedLoyalty;
import com.inspirenetz.api.core.domain.PrimaryLoyalty;
import com.inspirenetz.api.core.service.CustomerService;
import com.inspirenetz.api.core.service.LinkedLoyaltyService;
import com.inspirenetz.api.core.service.PrimaryLoyaltyService;
import com.inspirenetz.api.rest.exception.InspireNetzException;
import com.inspirenetz.api.test.config.*;
import com.inspirenetz.api.test.core.fixture.CustomerFixture;
import com.inspirenetz.api.test.core.fixture.LinkedLoyaltyFixture;
import com.inspirenetz.api.test.core.fixture.PrimaryLoyaltyFixture;
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
 * Created by saneeshci on 01/09/14.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration(classes  = {ApplicationTestConfig.class, PersistenceTestConfig.class, ServiceTestConfig.class, SecurityTestConfig.class, WebTestConfig.class, NotificationTestConfig.class})
public class LinkedLoyaltyControllerTest {


    private static Logger log = LoggerFactory.getLogger(LinkedLoyaltyControllerTest.class);

    private static MockMvc mockMvc;

    private static ObjectMapper mapper;

    @Autowired
    private LinkedLoyaltyService linkedLoyaltyService;

    @Autowired
    private CustomerService customerService;

    @Autowired
    private PrimaryLoyaltyService primaryLoyaltyService;

    @Autowired
    private WebApplicationContext wac;

    @Resource
    private FilterChainProxy springSecurityFilterChain;

    @Autowired
    @Qualifier("uds")
    protected UserDetailsService userDetailsService;

    // LinkedLoyalty object
    private LinkedLoyalty linkedLoyalty;

    UsernamePasswordAuthenticationToken principal;

    MockHttpSession session;

    // Map to hold the returned json
    Map<String,String> map = new HashMap<>();


    Set<LinkedLoyalty> tempSet = new HashSet<>(0);

    Set<Customer> customerSet = new HashSet<>(0);

    Set<PrimaryLoyalty> pLoyaltySet = new HashSet<>(0);


    @Before
    public void setUp()
    {
        useMerchantSession();
    }


    protected void useMerchantSession() {

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

        } catch (Exception e) {
            e.printStackTrace();
        }

    }


    protected void useCustomerSession() {

        try {

            // Set the principal
            principal = ControllerTestUtils.getPrincipal(ControllerTestUtils.TEST_CUSTOMER_LOGINID,userDetailsService);

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

        // Create the linkedLoyalty
        linkedLoyalty = LinkedLoyaltyFixture.standardLinkedLoyalty();


    }




    @Test
    public void listLinkedLoyaltysForMerchant() throws Exception  {

        // Create the merchant session
        useMerchantSession();

        // Create primary
        Customer primary = CustomerFixture.standardCustomer();
        primary = customerService.saveCustomer(primary);
        customerSet.add(primary);

        // Create child
        Customer child = CustomerFixture.standardCustomer();
        child.setCusLoyaltyId("9999888877776662");
        child = customerService.saveCustomer(child);
        customerSet.add(child);


        // Get the customerset
        linkedLoyalty = LinkedLoyaltyFixture.standardLinkedLoyalty();
        linkedLoyalty.setLilParentCustomerNo(primary.getCusCustomerNo());
        linkedLoyalty.setLilChildCustomerNo(child.getCusCustomerNo());
        linkedLoyaltyService.saveLinkedLoyalty(linkedLoyalty);

        tempSet.add(linkedLoyalty);




        PrimaryLoyalty primaryLoyalty = PrimaryLoyaltyFixture.standardPrimaryLoyalty();
        primaryLoyalty.setPllCustomerNo(primary.getCusCustomerNo());
        primaryLoyalty.setPllLoyaltyId(primary.getCusLoyaltyId());
        primaryLoyaltyService.savePrimaryLoyalty(primaryLoyalty);

        pLoyaltySet.add(primaryLoyalty);



        // Place the delete request
        MvcResult mvcResult = mockMvc.perform(get("/api/0.9/json/merchant/bundling/linkedloyalty/"+child.getCusLoyaltyId()+"")
                .principal(principal)
                .session(session)
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)

        )
                .andReturn();

        String response = mvcResult.getResponse().getContentAsString();
        Assert.assertNotNull(response);
        log.info("LinkedLoyaltyResponse: " + response);

        // Convert json response to HashMap
        Assert.assertTrue(response.contains("success"));


    }

    @Test
    public void listLinkedLoyaltyForCustomer() throws Exception  {

        // Use the customer session
        useCustomerSession();

        // Create primary
        Customer primary = CustomerFixture.standardCustomer();
        primary = customerService.saveCustomer(primary);
        customerSet.add(primary);

        // Create child
        Customer child = CustomerFixture.standardCustomer();
        child.setCusLoyaltyId("9999888877776662");
        child = customerService.saveCustomer(child);
        customerSet.add(child);


        // Get the customerset
        linkedLoyalty = LinkedLoyaltyFixture.standardLinkedLoyalty();
        linkedLoyalty.setLilParentCustomerNo(primary.getCusCustomerNo());
        linkedLoyalty.setLilChildCustomerNo(child.getCusCustomerNo());
        linkedLoyaltyService.saveLinkedLoyalty(linkedLoyalty);

        tempSet.add(linkedLoyalty);




        PrimaryLoyalty primaryLoyalty = PrimaryLoyaltyFixture.standardPrimaryLoyalty();
        primaryLoyalty.setPllCustomerNo(primary.getCusCustomerNo());
        primaryLoyalty.setPllLoyaltyId(primary.getCusLoyaltyId());
        primaryLoyaltyService.savePrimaryLoyalty(primaryLoyalty);

        pLoyaltySet.add(primaryLoyalty);




        Long merchantNo = 1L;


        // Place the delete request
        MvcResult mvcResult = mockMvc.perform(get("/api/0.9/json/customer/bundling/linkedloyalty/"+merchantNo+"")
                .principal(principal)
                .session(session)
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)

        )
                .andReturn();

        String response = mvcResult.getResponse().getContentAsString();
        Assert.assertNotNull(response);
        log.info("LinkedLoyaltyResponse: " + response);

        // Convert json response to HashMap
        Assert.assertTrue(response.contains("success"));


    }

    @After
    public void tearDown() throws InspireNetzException {

        for(LinkedLoyalty linkedLoyalty : tempSet ) {

            linkedLoyaltyService.deleteLinkedLoyalty(linkedLoyalty.getLilId());

        }


        for(Customer customer : customerSet ) {

            customerService.deleteCustomer(customer.getCusCustomerNo());

        }

        for(PrimaryLoyalty primaryLoyalty : pLoyaltySet ) {

            primaryLoyaltyService.deletePrimaryLoyalty(primaryLoyalty.getPllId());

        }


    }

}