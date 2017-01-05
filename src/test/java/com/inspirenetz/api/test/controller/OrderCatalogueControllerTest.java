package com.inspirenetz.api.test.controller;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.inspirenetz.api.core.domain.OrderCatalogue;
import com.inspirenetz.api.core.service.OrderCatalogueService;
import com.inspirenetz.api.rest.exception.InspireNetzException;
import com.inspirenetz.api.test.config.*;
import com.inspirenetz.api.test.core.fixture.OrderCatalogueFixture;
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
public class OrderCatalogueControllerTest {


    private static Logger log = LoggerFactory.getLogger(OrderCatalogueControllerTest.class);

    private static MockMvc mockMvc;

    private static ObjectMapper mapper;

    @Autowired
    private OrderCatalogueService orderCatalogueService;

    @Autowired
    private WebApplicationContext wac;

    @Resource
    private FilterChainProxy springSecurityFilterChain;

    @Autowired
    @Qualifier("uds")
    protected UserDetailsService userDetailsService;

    // OrderCatalogue object
    private OrderCatalogue orderCatalogue;

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

        // Create the orderCatalogue
        orderCatalogue = OrderCatalogueFixture.standardOrderCatalogue();


    }




    @Test
    public void saveOrderCatalogue() throws Exception {


        MvcResult mvcResult = mockMvc.perform(post("/api/0.9/json/merchant/order/catalogue")
                                                .principal(principal)
                                                .session(session)
                                                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                                                .param("orcProductCode",orderCatalogue.getOrcProductCode())
                                                .param("orcDescription",orderCatalogue.getOrcDescription())
                                                .param("orcCategory", orderCatalogue.getOrcCategory().toString())
                                                .param("orcPrice", orderCatalogue.getOrcPrice().toString())
                                                .param("orcTaxPerc", orderCatalogue.getOrcTaxPerc().toString())
                                                .param("orcAvailableLocation", orderCatalogue.getOrcAvailableLocation().toString())
                                                .param("orcDeliveryType", orderCatalogue.getOrcDeliveryType().toString())
                                              )
                                        .andReturn();
        String response = mvcResult.getResponse().getContentAsString();
        Assert.assertNotNull(response);
        log.info("OrderCatalogueResponse: " + response);

        // Convert json response to HashMap
        map = mapper.readValue(response, new TypeReference<Map<String, String>>() { });
        Assert.assertTrue(map.get("status").equals("success"));


        log.info("OrderCatalogue created");


    }

    @Test
    public void deleteOrderCatalogue() throws  Exception {

        // Create the orderCatalogue
        orderCatalogue = orderCatalogueService.saveOrderCatalogue(orderCatalogue);
        Assert.assertNotNull(orderCatalogue.getOrcProductNo());
        log.info("OrderCatalogue created");


        // Place the delete request
        MvcResult mvcResult = mockMvc.perform(post("/api/0.9/json/merchant/order/catalogue/delete/" + orderCatalogue.getOrcProductNo())
                                                    .principal(principal)
                                                    .session(session)
                                                    .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                                            )
                                            .andReturn();
        String response = mvcResult.getResponse().getContentAsString();
        Assert.assertNotNull(response);
        log.info("OrderCatalogueResponse: " + response);

        // Convert json response to HashMap
        map = mapper.readValue(response, new TypeReference<Map<String, String>>() { });
        Assert.assertTrue(map.get("status").equals("success"));


        log.info("OrderCatalogue deleted");


    }

    @Test
    public void listOrderCatalogues() throws Exception  {

        //Add the data
        orderCatalogue = orderCatalogueService.saveOrderCatalogue(orderCatalogue);

        // Place the delete request
        MvcResult mvcResult = mockMvc.perform(get("/api/0.9/json/merchant/order/catalogues/0/0")
                .principal(principal)
                .session(session)
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)

        )
                .andReturn();

        String response = mvcResult.getResponse().getContentAsString();
        Assert.assertNotNull(response);
        log.info("OrderCatalogueResponse: " + response);

        // Convert json response to HashMap
        Assert.assertTrue(response.contains("success"));


    }

    @Test
    public void getOrderCatalogueInfo()   throws Exception  {

        //Add the data
        orderCatalogue = orderCatalogueService.saveOrderCatalogue(orderCatalogue);

        // Place the delete request
        MvcResult mvcResult = mockMvc.perform(get("/api/0.9/json/merchant/order/catalogue/"+orderCatalogue.getOrcProductNo())
                .principal(principal)
                .session(session)
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)

        )
                .andReturn();

        String response = mvcResult.getResponse().getContentAsString();
        Assert.assertNotNull(response);
        log.info("OrderCatalogueResponse: " + response);

        // Convert json response to HashMap
        Assert.assertTrue(response.contains("success"));


    }





    @After
    public void tearDown() throws InspireNetzException {

        Set<OrderCatalogue> orderCatalogues = OrderCatalogueFixture.standardOrderCatalogues();

        for(OrderCatalogue orderCatalogue: orderCatalogues) {

            OrderCatalogue delOrderCatalogue = orderCatalogueService.findByOrcMerchantNoAndOrcProductCodeAndOrcAvailableLocation(orderCatalogue.getOrcMerchantNo(), orderCatalogue.getOrcProductCode(), orderCatalogue.getOrcAvailableLocation());

            if ( delOrderCatalogue != null ) {
                orderCatalogueService.deleteOrderCatalogue(delOrderCatalogue.getOrcProductNo());
            }

        }
    }

}
