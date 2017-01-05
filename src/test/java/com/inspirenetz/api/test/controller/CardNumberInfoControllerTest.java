package com.inspirenetz.api.test.controller;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.inspirenetz.api.core.domain.CardNumberInfo;
import com.inspirenetz.api.core.domain.CardType;
import com.inspirenetz.api.core.service.CardNumberInfoService;
import com.inspirenetz.api.core.service.CardTypeService;
import com.inspirenetz.api.rest.exception.InspireNetzException;
import com.inspirenetz.api.test.config.*;
import com.inspirenetz.api.test.core.fixture.CardNumberInfoFixture;
import com.inspirenetz.api.test.core.fixture.CardTypeFixture;
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
 * Created by ameen on 21/10/15.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration(classes  = {ApplicationTestConfig.class, PersistenceTestConfig.class, ServiceTestConfig.class, SecurityTestConfig.class, WebTestConfig.class, NotificationTestConfig.class})
public class CardNumberInfoControllerTest {

    private static Logger log = LoggerFactory.getLogger(CardNumberInfoControllerTest.class);

    private static MockMvc mockMvc;

    private static ObjectMapper mapper;

    @Autowired
    private WebApplicationContext wac;

    @Autowired
    CardTypeService cardTypeService;

    @Autowired
    CardNumberInfoService cardNumberInfoService;

    @Resource
    private FilterChainProxy springSecurityFilterChain;

    @Autowired
    @Qualifier("uds")
    protected UserDetailsService userDetailsService;

    UsernamePasswordAuthenticationToken principal;

    MockHttpSession session;

    // Map to hold the returned json
    Map<String,String> map = new HashMap<>();

    Set<CardType> cardTypeSet =new HashSet<>();

    Set<CardNumberInfo> cardNumberInfoSet =new HashSet<>();



    @Before
    public void setUp()
    {
        try {

            // Set the principal
            principal = ControllerTestUtils.getPrincipal(ControllerTestUtils.TEST_MERCHANT_USER_LOGINID, userDetailsService);

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

    }

    @Test
    public void processFile() throws Exception {


        // Get the CardType
        CardType cardType = CardTypeFixture.standardCardType();
        cardType.setCrtMerchantNo(3L);
        cardType = cardTypeService.save(cardType);
        cardTypeSet.add(cardType);

        //upload file

        MvcResult mvcResult = mockMvc.perform(post("/api/0.9/json/merchant/card/cardnumberinfobatch")
                .principal(principal)
                .session(session)
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .param("cniCardType",cardType.getCrtId().toString())
                .param("filePath","/vouchersource/test1.csv")
                .param("cniBatchName","test3")
               )
                .andReturn();
        String response = mvcResult.getResponse().getContentAsString();
        Assert.assertNotNull(response);
        log.info("processFile: " + response);

        // Convert json response to HashMap
        map = mapper.readValue(response, new TypeReference<Map<String, String>>() { });
        Assert.assertTrue(map.get("status").equals("success"));


        log.info("processFile created");


    }

    @Test
    public void getValidatedCardDetails() throws Exception {


        // Get the CardType
        CardType cardType = CardTypeFixture.standardCardType();
        cardType.setCrtMerchantNo(1L);
        cardType = cardTypeService.save(cardType);
        cardTypeSet.add(cardType);

        CardNumberInfo cardNumberInfo= CardNumberInfoFixture.standardCardNumberInfo();
        cardNumberInfo.setCniMerchantNo(1L);
        cardNumberInfo.setCniCardType(cardType.getCrtId());
        cardNumberInfo.setCniCardNumber("3333444455556667");
        cardNumberInfo=cardNumberInfoService.saveCardNumberInfo(cardNumberInfo);
        cardNumberInfoSet.add(cardNumberInfo);

        //upload file

        MvcResult mvcResult = mockMvc.perform(get("/api/0.9/json/merchant/cardnumber/info")
                .principal(principal)
                .session(session)
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .param("cardNumber",cardNumberInfo.getCniCardNumber())
        )
                .andReturn();
        String response = mvcResult.getResponse().getContentAsString();
        Assert.assertNotNull(response);
        log.info("getValidatedCardDetails: " + response);



    }

    @Test
    public void getValidatedCardDetailsForPublic() throws Exception {


        // Get the CardType
        CardType cardType = CardTypeFixture.standardCardType();
        cardType.setCrtMerchantNo(1L);
        cardType = cardTypeService.save(cardType);
        Assert.assertNotNull(cardType);
        cardTypeSet.add(cardType);

        CardNumberInfo cardNumberInfo= CardNumberInfoFixture.standardCardNumberInfo();
        cardNumberInfo.setCniMerchantNo(1L);
        cardNumberInfo.setCniCardType(cardType.getCrtId());
        cardNumberInfo.setCniCardNumber("1111111111111111");
        cardNumberInfo.setCniPin("1111");
        cardNumberInfo=cardNumberInfoService.saveCardNumberInfo(cardNumberInfo);
        Assert.assertNotNull(cardNumberInfo);
        cardNumberInfoSet.add(cardNumberInfo);

        //upload file

        MvcResult mvcResult = mockMvc.perform(get("/api/0.9/json/public/cardnumber/validate")
                .principal(principal)
                .session(session)
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .param("cardNumber",cardNumberInfo.getCniCardNumber())
                .param("mobile","8867987369")
                .param("pin",cardNumberInfo.getCniPin())
                .param("merchantNo","0")
        )
                .andReturn();
        String response = mvcResult.getResponse().getContentAsString();
        Assert.assertNotNull(response);
        log.info("getValidatedCardDetailsForPublic: " + response);



    }



    @After
    public  void tearDown() throws InspireNetzException {

        for (CardNumberInfo cardNumberInfo:cardNumberInfoSet){

            cardNumberInfoService.delete(cardNumberInfo);
        }

        for (CardType cardType :cardTypeSet){

            cardTypeService.deleteCardType(cardType.getCrtId(),cardType.getCrtMerchantNo());
        }
    }



}
