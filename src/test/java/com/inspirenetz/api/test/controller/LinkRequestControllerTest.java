package com.inspirenetz.api.test.controller;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.inspirenetz.api.core.domain.Customer;
import com.inspirenetz.api.core.domain.LinkRequest;
import com.inspirenetz.api.core.domain.LinkedLoyalty;
import com.inspirenetz.api.core.service.CustomerService;
import com.inspirenetz.api.core.service.LinkRequestService;
import com.inspirenetz.api.core.service.LinkedLoyaltyService;
import com.inspirenetz.api.rest.exception.InspireNetzException;
import com.inspirenetz.api.test.config.*;
import com.inspirenetz.api.test.core.fixture.CustomerFixture;
import com.inspirenetz.api.test.core.fixture.LinkRequestFixture;
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
public class LinkRequestControllerTest {


    private static Logger log = LoggerFactory.getLogger(LinkRequestControllerTest.class);

    private static MockMvc mockMvc;

    private static ObjectMapper mapper;

    @Autowired
    private LinkRequestService linkRequestService;

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


    // LinkRequest object
    private LinkRequest linkRequest;

    UsernamePasswordAuthenticationToken principal;

    MockHttpSession session;

    // Map to hold the returned json
    Map<String,String> map = new HashMap<>();

    Set<LinkRequest> tempSet = new HashSet<>(0);

    Set<Customer> customerSet = new HashSet<>(0);

    Set<LinkedLoyalty> linkLoyaltySet = new HashSet<>(0);



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

        // Create the linkRequest
        linkRequest = LinkRequestFixture.standardLinkRequest();


    }




    @Test
    public void saveLinkRequest() throws Exception {


        MvcResult mvcResult = mockMvc.perform(post("/api/0.9/json/merchant/linking/linkrequest")
                                                .principal(principal)
                                                .session(session)
                                                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                                                .param("lrqSourceCustomer",linkRequest.getLrqSourceCustomer().toString())
                                                .param("lrqParentCustomer",linkRequest.getLrqParentCustomer().toString())
                                                .param("lrqStatus", linkRequest.getLrqStatus().toString())
                                                .param("lrqRequestSource", linkRequest.getLrqRequestSource().toString())
                                                .param("lrqRequestDate", "2014-08-26")
                                              )
                                        .andReturn();
        String response = mvcResult.getResponse().getContentAsString();
        Assert.assertNotNull(response);
        log.info("LinkRequestResponse: " + response);

        // Convert json response to HashMap
        map = mapper.readValue(response, new TypeReference<Map<String, String>>() { });
        Assert.assertTrue(map.get("status").equals("success"));


        log.info("LinkRequest created");


    }

    @Test
    public void deleteLinkRequest() throws  Exception {

        // Create the linkRequest
        linkRequest = linkRequestService.saveLinkRequest(linkRequest);
        Assert.assertNotNull(linkRequest.getLrqId());
        log.info("LinkRequest created");

        tempSet.add(linkRequest);


        // Place the delete request
        MvcResult mvcResult = mockMvc.perform(post("/api/0.9/json/merchant/linking/linkrequest/delete/" + linkRequest.getLrqId())
                                                    .principal(principal)
                                                    .session(session)
                                                    .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                                            )
                                            .andReturn();
        String response = mvcResult.getResponse().getContentAsString();
        Assert.assertNotNull(response);
        log.info("LinkRequestResponse: " + response);

        // Convert json response to HashMap
        map = mapper.readValue(response, new TypeReference<Map<String, String>>() { });
        Assert.assertTrue(map.get("status").equals("success"));


        // Remove the requst from the tempset
        tempSet.remove(linkRequest);

        log.info("LinkRequest deleted");


    }

    @Test
    public void listLinkRequests() throws Exception  {

        //Add the data
        linkRequest = linkRequestService.saveLinkRequest(linkRequest);

        tempSet.add(linkRequest);

        // Place the delete request
        MvcResult mvcResult = mockMvc.perform(get("/api/0.9/json/merchant/linking/linkrequests/0/0")
                .principal(principal)
                .session(session)
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)

        )
                .andReturn();

        String response = mvcResult.getResponse().getContentAsString();
        Assert.assertNotNull(response);
        log.info("LinkRequestResponse: " + response);

        // Convert json response to HashMap
        Assert.assertTrue(response.contains("success"));


    }

    @Test
    public void getLinkRequestInfo()   throws Exception  {

        //Add the data
        linkRequest = linkRequestService.saveLinkRequest(linkRequest);

        tempSet.add(linkRequest);

        // Place the delete request
        MvcResult mvcResult = mockMvc.perform(get("/api/0.9/json/merchant/linking/linkrequest/"+linkRequest.getLrqId())
                .principal(principal)
                .session(session)
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)

        )
                .andReturn();

        String response = mvcResult.getResponse().getContentAsString();
        Assert.assertNotNull(response);
        log.info("LinkRequestResponse: " + response);

        // Convert json response to HashMap
        Assert.assertTrue(response.contains("success"));


    }


    @Test
    public void createlinkRequest() throws Exception {

        LinkedLoyalty linkedLoyalty;

        LinkRequest linkRequest;


        // Create primary
        Customer primary = CustomerFixture.standardCustomer();
        primary = customerService.saveCustomer(primary);
        customerSet.add(primary);

        // Create child
        Customer child = CustomerFixture.standardCustomer();
        child.setCusLoyaltyId("9999888877776662");
        child = customerService.saveCustomer(child);
        customerSet.add(child);


        MvcResult mvcResult = mockMvc.perform(post("/api/0.9/json/merchant/linking/createlinkrequest")
                .principal(principal)
                .session(session)
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .param("primaryLoyaltyId",primary.getCusLoyaltyId())
                .param("childLoyaltyId",child.getCusLoyaltyId())
                .param("lrqInitiator", primary.getCusLoyaltyId())
                .param("lrqSource", "1")
        )
                .andReturn();
        String response = mvcResult.getResponse().getContentAsString();
        Assert.assertNotNull(response);
        log.info("UnLinkRequestResponse: " + response);

        // Convert json response to HashMap
        map = mapper.readValue(response, new TypeReference<Map<String, String>>() { });
        Assert.assertTrue(map.get("status").equals("success"));


        // Get the id
        Long lrqId = Long.parseLong(map.get("data"));
        linkRequest= linkRequestService.findByLrqId(lrqId);
        tempSet.add(linkRequest);


        log.info("LinkRequest created");


    }

    @Test
    public void addUnlinkRequestForMerchant() throws Exception {

        LinkedLoyalty linkedLoyalty;

        LinkRequest linkRequest;


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

        linkLoyaltySet.add(linkedLoyalty);


        MvcResult mvcResult = mockMvc.perform(post("/api/0.9/json/merchant/bundling/unlink")
                .principal(principal)
                .session(session)
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .param("primaryLoyaltyId",primary.getCusLoyaltyId())
                .param("childLoyaltyId",child.getCusLoyaltyId())
                .param("lrqInitiator", primary.getCusLoyaltyId())
                .param("lrqSource", "1")
        )
                .andReturn();
        String response = mvcResult.getResponse().getContentAsString();
        Assert.assertNotNull(response);
        log.info("UnLinkRequestResponse: " + response);

        // Convert json response to HashMap
        map = mapper.readValue(response, new TypeReference<Map<String, String>>() { });
        Assert.assertTrue(map.get("status").equals("success"));


        // Get the id
        Long lrqId = Long.parseLong(map.get("data"));
        linkRequest= linkRequestService.findByLrqId(lrqId);
        tempSet.add(linkRequest);


        log.info("LinkRequest created");


    }

    @Test
    public void addUnlinkRequestCustomer() throws Exception {


        useCustomerSession();;


        LinkedLoyalty linkedLoyalty;

        LinkRequest linkRequest;


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

        linkLoyaltySet.add(linkedLoyalty);


        MvcResult mvcResult = mockMvc.perform(post("/api/0.9/json/customer/bundling/unlink")
                .principal(principal)
                .session(session)
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .param("primaryLoyaltyId",primary.getCusLoyaltyId())
                .param("childLoyaltyId",child.getCusLoyaltyId())
                .param("merchantNo", primary.getCusMerchantNo().toString())
                .param("lrqSource", "1")
        )
                .andReturn();
        String response = mvcResult.getResponse().getContentAsString();
        Assert.assertNotNull(response);
        log.info("UnLinkRequestResponse: " + response);

        // Convert json response to HashMap
        map = mapper.readValue(response, new TypeReference<Map<String, String>>() { });
        Assert.assertTrue(map.get("status").equals("success"));

        // Get the id
        Long lrqId = Long.parseLong(map.get("data"));
        linkRequest= linkRequestService.findByLrqId(lrqId);
        tempSet.add(linkRequest);


        log.info("LinkRequest created");


    }


    @After
    public void tearDown() throws InspireNetzException {

        for(LinkRequest linkRequest: tempSet) {

            linkRequestService.deleteLinkRequest(linkRequest.getLrqId());

        }


        for(LinkedLoyalty linkedLoyalty : linkLoyaltySet ) {

            linkedLoyaltyService.deleteLinkedLoyalty(linkedLoyalty.getLilId());

        }

        for(Customer customer : customerSet ) {

            customerService.deleteCustomer(customer.getCusCustomerNo());

        }


    }

}
