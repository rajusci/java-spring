package com.inspirenetz.api.test.controller;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.inspirenetz.api.config.SecurityConfig;
import com.inspirenetz.api.core.dictionary.CouponDistributionStatus;
import com.inspirenetz.api.core.dictionary.CouponDistributionType;
import com.inspirenetz.api.core.domain.CouponDistribution;
import com.inspirenetz.api.core.service.CouponDistributionService;
import com.inspirenetz.api.test.config.*;
import com.inspirenetz.api.test.core.fixture.CouponDistributionFixture;
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
public class CouponDistributionControllerTest {


    private static Logger log = LoggerFactory.getLogger(CouponDistributionControllerTest.class);


    private static MockMvc mockMvc;

    private static ObjectMapper mapper;

    @Autowired
    private CouponDistributionService couponDistributionService;

    @Autowired
    private WebApplicationContext wac;

    @Resource
    private FilterChainProxy springSecurityFilterChain;

    @Autowired
    @Qualifier("uds")
    protected UserDetailsService userDetailsService;

    // CouponDistribution object
    private CouponDistribution couponDistribution;

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

        // Create the couponDistribution
        couponDistribution = CouponDistributionFixture.standardCouponDistribution();


    }



    @Test
    public void saveCouponDistribution() throws Exception {

        couponDistribution.setCodDistributionType(CouponDistributionType.CUSTOMER_SEGMENTS);

        couponDistribution.setCodCustomerSegments("69");
        couponDistribution.setCodCustomerIds("8867987369");
        couponDistribution.setCodSmsContent("Your Coupon Code is <coupon>");
        couponDistribution.setCodEmailSubject("Coupon Code");
        couponDistribution.setCodEmailContent("<b>Your</b> Coupon Code is <coupon>");

        MvcResult mvcResult = mockMvc.perform(post("/api/0.9/json/merchant/coupondistribution")
                .principal(principal)
                .session(session)
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .param("codCouponCode", couponDistribution.getCodCouponCode())
                .param("codDistributionType", couponDistribution.getCodDistributionType().toString())
                .param("codBroadCastType", couponDistribution.getCodBroadCastType().toString())
                .param("codCustomerSegments", couponDistribution.getCodCustomerSegments().toString())
                .param("codCustomerIds", couponDistribution.getCodCustomerIds().toString())
                .param("codSmsContent", couponDistribution.getCodSmsContent().toString())
                .param("codEmailSubject", couponDistribution.getCodEmailSubject().toString())
                .param("codEmailContent", couponDistribution.getCodEmailContent().toString())

        )
                .andReturn();
        String response = mvcResult.getResponse().getContentAsString();
        Assert.assertNotNull(response);
        log.info("CouponDistributionResponse: " + response);

        // Convert json response to HashMap
        map = mapper.readValue(response, new TypeReference<Map<String, String>>() { });
        Assert.assertTrue(map.get("status").equals("success"));


        log.info("CouponDistribution created");


    } 
    
    @Test
    public void deleteCouponDistribution() throws  Exception {

        // Create the couponDistribution
        couponDistribution = couponDistributionService.saveCouponDistribution(couponDistribution);
        Assert.assertNotNull(couponDistribution.getCodId());
        log.info("CouponDistribution created");


        // Place the delete request
        MvcResult mvcResult = mockMvc.perform(post("/api/0.9/json/merchant/coupondistribution/delete/" + couponDistribution.getCodId())
                                                    .principal(principal)
                                                    .session(session)
                                                    .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                                            )
                                            .andReturn();
        String response = mvcResult.getResponse().getContentAsString();
        Assert.assertNotNull(response);
        log.info("CouponDistributionResponse: " + response);

        // Convert json response to HashMap
        map = mapper.readValue(response, new TypeReference<Map<String, String>>() { });
        Assert.assertTrue(map.get("status").equals("success"));


        log.info("CouponDistribution deleted");


    }


    @Test
    public void listCouponDistributions() throws Exception  {

        // Get the set of couponDistributions
        Set<CouponDistribution> couponDistributionSet = CouponDistributionFixture.standardCouponDistributions();
        List<CouponDistribution>  couponDistributionList = new ArrayList<CouponDistribution>();
        couponDistributionList.addAll(couponDistributionSet);
        couponDistributionService.saveAll(couponDistributionList);

        // Place the delete request
        MvcResult mvcResult = mockMvc.perform(get("/api/0.9/json/merchant/coupondistributions/0/0")
                .principal(principal)
                .session(session)
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)

        )
                .andReturn();

        String response = mvcResult.getResponse().getContentAsString();
        Assert.assertNotNull(response);
        log.info("CouponDistributionResponse: " + response);

        // Convert json response to HashMap
        Assert.assertTrue(response.contains("success"));


    }


    @Test
    public void updateCouponDistributionStatus() throws Exception  {

        // Create the couponDistribution
        couponDistribution = couponDistributionService.saveCouponDistribution(couponDistribution);
        Assert.assertNotNull(couponDistribution.getCodId());
        log.info("CouponDistribution created");


        // Place the delete request
        MvcResult mvcResult = mockMvc.perform(post("/api/0.9/json/merchant/coupondistribution/status/"+couponDistribution.getCodId())
                .principal(principal)
                .session(session)
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .param("codStatus",Integer.toString(CouponDistributionStatus.SUSPENDED))
        )
                .andReturn();

        String response = mvcResult.getResponse().getContentAsString();
        Assert.assertNotNull(response);
        log.info("CouponDistributionResponse: " + response);

        // Convert json response to HashMap
        Assert.assertTrue(response.contains("success"));


    }




    @After
    public void tearDown() throws Exception {

        Set<CouponDistribution> couponDistributions = CouponDistributionFixture.standardCouponDistributions();

        for(CouponDistribution couponDistribution: couponDistributions) {

            CouponDistribution delCouponDistribution = couponDistributionService.findByCodMerchantNoAndCodCouponCode(couponDistribution.getCodMerchantNo(), couponDistribution.getCodCouponCode());

            if ( delCouponDistribution != null ) {
                couponDistributionService.deleteCouponDistribution(delCouponDistribution.getCodId());
            }

        }
    }

}
