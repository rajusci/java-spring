package com.inspirenetz.api.test.controller;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.collect.Lists;
import com.inspirenetz.api.core.dictionary.PartyApprovalStatus;
import com.inspirenetz.api.core.domain.Customer;
import com.inspirenetz.api.core.domain.PartyApproval;
import com.inspirenetz.api.core.service.CustomerService;
import com.inspirenetz.api.core.service.PartyApprovalService;
import com.inspirenetz.api.rest.exception.InspireNetzException;
import com.inspirenetz.api.test.config.*;
import com.inspirenetz.api.test.core.fixture.CustomerFixture;
import com.inspirenetz.api.test.core.fixture.PartyApprovalFixture;
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
import java.sql.Timestamp;
import java.util.*;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

/**
 * Created by sandheepgr on 30/4/14.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration(classes  = {ApplicationTestConfig.class, PersistenceTestConfig.class, ServiceTestConfig.class, SecurityTestConfig.class, WebTestConfig.class, NotificationTestConfig.class})
public class PartyApprovalControllerTest {


    private static Logger log = LoggerFactory.getLogger(PartyApprovalControllerTest.class);

    private static MockMvc mockMvc;

    private static ObjectMapper mapper;

    @Autowired
    private PartyApprovalService partyApprovalService;

    @Autowired
    private WebApplicationContext wac;

    @Resource
    private FilterChainProxy springSecurityFilterChain;

    @Autowired
    @Qualifier("uds")
    protected UserDetailsService userDetailsService;

    @Autowired
    private CustomerService customerService;

    // PartyApproval object
    private PartyApproval partyApproval;

    // PartyApproval object
    private Set<Customer> customers;

    List<Customer> customerList;

    UsernamePasswordAuthenticationToken principal;

    MockHttpSession session;

    // Map to hold the returned json
    Map<String,String> map = new HashMap<>();

    Set<PartyApproval> tempSet = new HashSet<>(0);

    Set<Customer> tempCustomerSet = new HashSet<>(0);




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

        // Create the partyApproval
        partyApproval = PartyApprovalFixture.standardPartyApproval();

        customers = CustomerFixture.standardCustomers();

        customerList = Lists.newArrayList((Iterable<Customer>) customers);

    }




    @Test
    public void changePartyApprovalByMerchant() throws Exception {
/*
        customerService.saveAll(customerList);
        tempCustomerSet.addAll(customerList);

        // Create the partyApproval object
        PartyApproval partyApproval = PartyApprovalFixture.standardPartyApproval();
        partyApproval.setPapApprover(customerList.get(0).getCusCustomerNo());
        partyApproval.setPapRequestor(customerList.get(1).getCusCustomerNo());

        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.HOUR,-12);
        Timestamp timestamp = new Timestamp(calendar.getTimeInMillis());
        partyApproval.setPapSentDateTime(timestamp);

        partyApproval = partyApprovalService.savePartyApproval(partyApproval);
        Assert.assertNotNull(partyApproval.getPapId());

        log.info("PartyApproval created");
        log.info("created object"+partyApproval.toString());
        // Add the tempset
        tempSet.add(partyApproval);*/
        useCustomerSession();;

        MvcResult mvcResult = mockMvc.perform(post("/api/0.9/json/customer/partyapproval/status")
                .principal(principal)
                .session(session)
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .param("approver", "8620127820")
                .param("requestor", "9538828850")
                .param("requestType", "2")
                .param("merchantNo", "1")
                .param("prdCode", "CAT002")
                .param("status", PartyApprovalStatus.ACCEPTED + "")
                )
                .andReturn();
        String response = mvcResult.getResponse().getContentAsString();
        Assert.assertNotNull(response);
        log.info("PartyApprovalResponse: " + response);

        // Convert json response to HashMap
        map = mapper.readValue(response, new TypeReference<Map<String, String>>() { });
        Assert.assertTrue(map.get("status").equals("success"));


        log.info("PartyApproval created");


    }

    @Test
    public void changePartyApprovalByCustomer() throws Exception {

        customerList.get(0).setCusLoyaltyId(ControllerTestUtils.TEST_CUSTOMER_LOGINID);
        customerService.saveAll(customerList);
        tempCustomerSet.addAll(customerList);

        // Create the partyApproval object
        PartyApproval partyApproval = PartyApprovalFixture.standardPartyApproval();
        partyApproval.setPapApprover(customerList.get(0).getCusCustomerNo());
        partyApproval.setPapRequestor(customerList.get(1).getCusCustomerNo());

        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.HOUR,-12);
        Timestamp timestamp = new Timestamp(calendar.getTimeInMillis());
        partyApproval.setPapSentDateTime(timestamp);

        partyApproval = partyApprovalService.savePartyApproval(partyApproval);
        Assert.assertNotNull(partyApproval.getPapId());

        log.info("PartyApproval created");
        log.info("created object"+partyApproval.toString());
        // Add the tempset
        tempSet.add(partyApproval);

        MvcResult mvcResult = mockMvc.perform(post("/api/0.9/json/customer/partyapproval/status")
                .principal(principal)
                .session(session)
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .param("requestor", customerList.get(1).getCusLoyaltyId())
                .param("requestType", partyApproval.getPapType().toString())
                .param("status", PartyApprovalStatus.ACCEPTED+"")
                .param("merchantNo", "1")
        )
                .andReturn();
        String response = mvcResult.getResponse().getContentAsString();
        Assert.assertNotNull(response);
        log.info("PartyApprovalResponse: " + response);

        // Convert json response to HashMap
        map = mapper.readValue(response, new TypeReference<Map<String, String>>() { });
        Assert.assertTrue(map.get("status").equals("success"));


        log.info("PartyApproval created");


    }


    @Test
    public void test7GetPartyApprovals() throws Exception{


        // Create the customer
        Customer customerA = CustomerFixture.standardCustomer();
        customerA.setCusCustomerNo(968L);
        customerA.setCusLoyaltyId("9999888877776663");
        customerA.setCusUserNo(140L);
        customerA = customerService.saveCustomer(customerA);
        tempCustomerSet.add(customerA);
        Customer customerB = CustomerFixture.standardCustomer();
        customerB.setCusCustomerNo(969L);
        customerB.setCusLoyaltyId("9999888877776664");
        customerB = customerService.saveCustomer(customerB);
        tempCustomerSet.add(customerB);
        Customer customerC = CustomerFixture.standardCustomer();
        customerC.setCusCustomerNo(970L);
        customerC.setCusLoyaltyId("9999888877776665");
        customerC = customerService.saveCustomer(customerC);
        tempCustomerSet.add(customerC);


        // Create the PartyApprovals
        PartyApproval partyApproval1 = PartyApprovalFixture.standardPartyApproval();
        partyApproval1.setPapApprover(customerA.getCusCustomerNo());
        partyApproval1.setPapRequestor(customerB.getCusCustomerNo());
        partyApprovalService.savePartyApproval(partyApproval1);
        tempSet.add(partyApproval1);
        PartyApproval partyApproval2 = PartyApprovalFixture.standardPartyApproval();
        partyApproval2.setPapApprover(customerA.getCusCustomerNo());
        partyApproval2.setPapRequestor(customerC.getCusCustomerNo());
        partyApprovalService.savePartyApproval(partyApproval2);
        tempSet.add(partyApproval2);

        MvcResult mvcResult = mockMvc.perform(post("/api/0.9/json/customer/approvals/pending")
                .principal(principal)
                .session(session)
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .param("merchantNo", customerA.getCusMerchantNo().toString())

        )
                .andReturn();
        String response = mvcResult.getResponse().getContentAsString();
        Assert.assertNotNull(response);
        log.info("Get Approval Requests Response: " + response);

        // Convert json response to HashMap

        log.info("PartyApproval response generated");

        log.info("Fetched partyApproval info" + response.toString());

    }

    @After
    public void tearDown() throws InspireNetzException {

        for(PartyApproval partyApproval : tempSet) {


            if ( partyApproval != null ) {
                partyApprovalService.deletePartyApproval(partyApproval.getPapId());
            }

        }
        for(Customer  customer : tempCustomerSet) {


            if ( customer != null ) {
                customerService.deleteCustomer(customer.getCusCustomerNo());
            }

        }
    }

}
