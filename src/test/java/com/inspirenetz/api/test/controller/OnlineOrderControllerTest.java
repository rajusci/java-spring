package com.inspirenetz.api.test.controller;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.inspirenetz.api.core.dictionary.CashPaymentStatus;
import com.inspirenetz.api.core.dictionary.OnlineOrderStatus;
import com.inspirenetz.api.core.domain.OnlineOrder;
import com.inspirenetz.api.core.service.OnlineOrderService;
import com.inspirenetz.api.rest.exception.InspireNetzException;
import com.inspirenetz.api.test.config.*;
import com.inspirenetz.api.test.core.fixture.OnlineOrderFixture;
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
import java.util.List;
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
public class OnlineOrderControllerTest {


    private static Logger log = LoggerFactory.getLogger(OnlineOrderControllerTest.class);

    private static MockMvc mockMvc;

    private static ObjectMapper mapper;

    @Autowired
    private OnlineOrderService onlineOrderService;

    @Autowired
    private WebApplicationContext wac;

    @Resource
    private FilterChainProxy springSecurityFilterChain;

    @Autowired
    @Qualifier("uds")
    protected UserDetailsService userDetailsService;

    // OnlineOrder object
    private OnlineOrder onlineOrder;

    UsernamePasswordAuthenticationToken principal;

    MockHttpSession session;

    // Map to hold the returned json
    Map<String,String> map = new HashMap<>();




    @Before
    public void setUp()
    {
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


    private void init() throws Exception{


        // Create the Mapper object
        mapper = new ObjectMapper();

        // Create the onlineOrder
        onlineOrder = OnlineOrderFixture.standardOnlineOrder();


    }




    @Test
    public void saveOnlineOrder() throws Exception {


        MvcResult mvcResult = mockMvc.perform(post("/api/0.9/json/merchant/order")
                                                .principal(principal)
                                                .session(session)
                                                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                                                .param("ordProductCode", onlineOrder.getOrdProductCode())
                                                .param("ordLoyaltyId", onlineOrder.getOrdLoyaltyId())
                                                .param("ordOrderLocation", onlineOrder.getOrdOrderLocation().toString())
                                                .param("ordOrderSlot", onlineOrder.getOrdOrderSlot().toString())
                                                .param("ordUniqueBatchTrackingId", onlineOrder.getOrdUniqueBatchTrackingId().toString())
                                                .param("ordTimestamp",onlineOrder.getOrdTimestamp().toString())
                                              )
                                        .andReturn();
        String response = mvcResult.getResponse().getContentAsString();
        Assert.assertNotNull(response);
        log.info("OnlineOrderResponse: " + response);

        // Convert json response to HashMap
        map = mapper.readValue(response, new TypeReference<Map<String, String>>() { });
        Assert.assertTrue(map.get("status").equals("success"));


        log.info("OnlineOrder created");


    }

    @Test
    public void deleteOnlineOrder() throws  Exception {

        // Create the onlineOrder
        onlineOrder = onlineOrderService.saveOnlineOrder(onlineOrder);
        Assert.assertNotNull(onlineOrder.getOrdId());
        log.info("OnlineOrder created");


        // Place the delete request
        MvcResult mvcResult = mockMvc.perform(post("/api/0.9/json/merchant/order/delete/" + onlineOrder.getOrdId())
                                                    .principal(principal)
                                                    .session(session)
                                                    .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                                            )
                                            .andReturn();
        String response = mvcResult.getResponse().getContentAsString();
        Assert.assertNotNull(response);
        log.info("OnlineOrderResponse: " + response);

        // Convert json response to HashMap
        map = mapper.readValue(response, new TypeReference<Map<String, String>>() { });
        Assert.assertTrue(map.get("status").equals("success"));


        log.info("OnlineOrder deleted");


    }

    @Test
    public void listOnlineOrders() throws Exception  {

        //Add the data
        onlineOrder = onlineOrderService.saveOnlineOrder(onlineOrder);

        // Place the delete request
        MvcResult mvcResult = mockMvc.perform(get("/api/0.9/json/merchant/orders/0/0")
                .principal(principal)
                .session(session)
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)

        )
                .andReturn();

        String response = mvcResult.getResponse().getContentAsString();
        Assert.assertNotNull(response);
        log.info("OnlineOrderResponse: " + response);

        // Convert json response to HashMap
        Assert.assertTrue(response.contains("success"));


    }

    @Test
    public void getOnlineOrderInfo()   throws Exception  {

        //Add the data
        onlineOrder = onlineOrderService.saveOnlineOrder(onlineOrder);

        // Place the delete request
        MvcResult mvcResult = mockMvc.perform(get("/api/0.9/json/merchant/order/"+onlineOrder.getOrdId())
                .principal(principal)
                .session(session)
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)

        )
                .andReturn();

        String response = mvcResult.getResponse().getContentAsString();
        Assert.assertNotNull(response);
        log.info("OnlineOrderResponse: " + response);

        // Convert json response to HashMap
        Assert.assertTrue(response.contains("success"));


    }

    @Test
    public void getOnlineOrderInfoForTrackingId()   throws Exception  {

        //Add the data
        onlineOrder = onlineOrderService.saveOnlineOrder(onlineOrder);

        // Place the delete request
        MvcResult mvcResult = mockMvc.perform(get("/api/0.9/json/merchant/order/trackingid/"+onlineOrder.getOrdUniqueBatchTrackingId())
                .principal(principal)
                .session(session)
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)

        )
                .andReturn();

        String response = mvcResult.getResponse().getContentAsString();
        Assert.assertNotNull(response);
        log.info("OnlineOrderResponse: " + response);

        // Convert json response to HashMap
        Assert.assertTrue(response.contains("success"));


    }

    @Test
    public void updatePaymentStatus()   throws Exception  {

        //Add the data
        onlineOrder = onlineOrderService.saveOnlineOrder(onlineOrder);

        // Place the delete request
        MvcResult mvcResult = mockMvc.perform(post("/api/0.9/json/merchant/order/paymentstatus/"+onlineOrder.getOrdUniqueBatchTrackingId())
                .principal(principal)
                .session(session)
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .param("status", Integer.toString(CashPaymentStatus.PAID))

        )
                .andReturn();

        String response = mvcResult.getResponse().getContentAsString();
        Assert.assertNotNull(response);
        log.info("OnlineOrderResponse: " + response);

        // Convert json response to HashMap
        Assert.assertTrue(response.contains("success"));


    }

    @Test
    public void updateOnlineOrderStatus()   throws Exception  {

        //Add the data
        onlineOrder = onlineOrderService.saveOnlineOrder(onlineOrder);

        // Place the delete request
        MvcResult mvcResult = mockMvc.perform(post("/api/0.9/json/merchant/order/status/"+onlineOrder.getOrdUniqueBatchTrackingId())
                .principal(principal)
                .session(session)
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .param("status", Integer.toString(OnlineOrderStatus.FULFILLED))

        )
                .andReturn();

        String response = mvcResult.getResponse().getContentAsString();
        Assert.assertNotNull(response);
        log.info("OnlineOrderResponse: " + response);

        // Convert json response to HashMap
        Assert.assertTrue(response.contains("success"));


    }



    @After
    public void tearDown() throws InspireNetzException {

        Set<OnlineOrder> onlineOrders = OnlineOrderFixture.standardOnlineOrders();

        for(OnlineOrder onlineOrder: onlineOrders) {

            List<OnlineOrder> onlineOrderList = onlineOrderService.findByOrdMerchantNoAndOrdUniqueBatchTrackingId(onlineOrder.getOrdMerchantNo(), onlineOrder.getOrdUniqueBatchTrackingId());

            for(OnlineOrder onlineOrder1 : onlineOrderList ) {

                onlineOrderService.deleteOnlineOrder(onlineOrder1.getOrdId());
            }

        }
    }


}
