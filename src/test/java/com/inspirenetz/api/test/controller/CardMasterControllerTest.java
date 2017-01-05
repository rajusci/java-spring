package com.inspirenetz.api.test.controller;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.inspirenetz.api.core.dictionary.CardMasterStatus;
import com.inspirenetz.api.core.dictionary.CardTypeType;
import com.inspirenetz.api.core.dictionary.OTPRefType;
import com.inspirenetz.api.core.dictionary.OTPType;
import com.inspirenetz.api.core.domain.*;
import com.inspirenetz.api.core.service.*;
import com.inspirenetz.api.rest.exception.InspireNetzException;
import com.inspirenetz.api.rest.resource.CardMasterResource;
import com.inspirenetz.api.test.config.*;
import com.inspirenetz.api.test.core.fixture.CardMasterFixture;
import com.inspirenetz.api.test.core.fixture.CardNumberInfoFixture;
import com.inspirenetz.api.test.core.fixture.CardTypeFixture;
import com.inspirenetz.api.test.core.fixture.CustomerFixture;
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
@ContextConfiguration(classes  = {ApplicationTestConfig.class, PersistenceTestConfig.class, ServiceTestConfig.class, SecurityTestConfig.class, WebTestConfig.class,IntegrationTestConfig.class,NotificationTestConfig.class})
public class CardMasterControllerTest {


    private static Logger log = LoggerFactory.getLogger(CardMasterControllerTest.class);

    private static MockMvc mockMvc;

    private static ObjectMapper mapper;

    @Autowired
    private CardMasterService cardMasterService;

    @Autowired
    private CustomerService customerService;

    @Autowired
    private CardTypeService cardTypeService;

    @Autowired
    private WebApplicationContext wac;

    @Autowired
    private OneTimePasswordService oneTimePasswordService;

    @Autowired
    private CardNumberInfoService cardNumberInfoService;

    @Autowired
    private CardTransactionService cardTransactionService;

    @Resource
    private FilterChainProxy springSecurityFilterChain;

    Set<CardType> tempSet1=new HashSet<>();

    Set<CardMaster> tempSet2 =new HashSet<>();

    Set<OneTimePassword> tempSet3 =new HashSet<>();

    Set<Customer> tempSet4 =new HashSet<>();

    Set<CardNumberInfo> cardNumberInfoSet =new HashSet<>();

    String otp;
    Long merchantNo;

    @Autowired
    @Qualifier("uds")
    protected UserDetailsService userDetailsService;

    // CardMaster object
    private CardMaster cardMaster;

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

        // Create the cardMaster
        cardMaster = CardMasterFixture.standardCardMaster();


    }




    @Test
    public void saveCardMaster() throws Exception {

        // Get the cardmaster object
        CardMaster cardMaster = CardMasterFixture.standardCardMaster();
        cardMaster.setCrmCardNo("");
        cardMaster.setCrmMerchantNo(1L);
        cardMaster.setCrmType(86L);


        /*// Get the CardType
        CardType cardType = CardTypeFixture.standardCardType();
        cardType = cardTypeService.saveCardType(cardType);
        cardMaster.setCrmType(cardType.getCrtId());
        tempSet1.add(cardType);*/

        MvcResult mvcResult = mockMvc.perform(post("/api/0.9/json/merchant/storedvaluecard/cardmaster")
                                                .principal(principal)
                                                .session(session)
                                                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                                                .param("crmCardNo",cardMaster.getCrmCardNo())
                                                .param("crmCardStatus",cardMaster.getCrmCardStatus().toString())
                                                .param("crmCardBalance", cardMaster.getCrmCardBalance().toString())
                                                .param("crmMobile", cardMaster.getCrmMobile().toString())
                                                .param("crmCardHolderName", cardMaster.getCrmCardHolderName().toString())
                                                .param("crmType",cardMaster.getCrmType().toString())
                                                .param("activationPin","1234")
                                                .param("crmPin","9845")
                                              )
                                        .andReturn();
        String response = mvcResult.getResponse().getContentAsString();
        Assert.assertNotNull(response);
        tempSet2.add(cardMaster);
        log.info("CardMasterResponse: " + response);

        // Convert json response to HashMap
        map = mapper.readValue(response, new TypeReference<Map<String, String>>() { });
        Assert.assertTrue(map.get("status").equals("success"));


        log.info("CardMaster created");


    }

    @Test
    public void deleteCardMaster() throws  Exception {

        // Create the cardMaster
        cardMaster = cardMasterService.saveCardMaster(cardMaster);
        Assert.assertNotNull(cardMaster.getCrmId());
        log.info("CardMaster created");


        // Place the delete request
        MvcResult mvcResult = mockMvc.perform(post("/api/0.9/json/merchant/storedvaluecard/cardmaster/delete/" + cardMaster.getCrmId())
                                                    .principal(principal)
                                                    .session(session)
                                                    .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                                            )
                                            .andReturn();
        String response = mvcResult.getResponse().getContentAsString();
        Assert.assertNotNull(response);
        log.info("CardMasterResponse: " + response);

        // Convert json response to HashMap
        map = mapper.readValue(response, new TypeReference<Map<String, String>>() { });
        Assert.assertTrue(map.get("status").equals("success"));


        log.info("CardMaster deleted");


    }

    @Test
    public void listCardMasters() throws Exception  {

        //Add the data
        cardMaster = cardMasterService.saveCardMaster(cardMaster);

        // Place the delete request
        MvcResult mvcResult = mockMvc.perform(get("/api/0.9/json/merchant/storedvaluecard/cardmasters/0/0")
                .principal(principal)
                .session(session)
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)

        )
                .andReturn();

        String response = mvcResult.getResponse().getContentAsString();
        Assert.assertNotNull(response);
        log.info("CardMasterResponse: " + response);

        // Convert json response to HashMap
        Assert.assertTrue(response.contains("success"));


    }

    @Test
    public void getCardMasterInfo()   throws Exception  {

        //Add the data
        cardMaster = cardMasterService.saveCardMaster(cardMaster);

        // Place the delete request
        MvcResult mvcResult = mockMvc.perform(get("/api/0.9/json/merchant/storedvaluecard/cardmaster/"+cardMaster.getCrmId())
                .principal(principal)
                .session(session)
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)

        )
                .andReturn();

        String response = mvcResult.getResponse().getContentAsString();
        Assert.assertNotNull(response);
        log.info("CardMasterResponse: " + response);

        // Convert json response to HashMap
        Assert.assertTrue(response.contains("success"));


    }

    @Test
    public void getCompatibleCardBalance()   throws Exception  {

        //Add the data

        cardMaster.setCrmCardNo("3333444455556666");

       // cardMaster = cardMasterService.saveCardMaster(cardMaster);

        //tempSet2.add(cardMaster);

        // Place the delete request
        MvcResult mvcResult = mockMvc.perform(get("/api/0.9/json/transaction/cardbalance")
                .principal(principal)
                .session(session)
                .contentType(MediaType.APPLICATION_JSON)
                .param("cardnumber",cardMaster.getCrmCardNo())

        )
                .andReturn();

        String response = mvcResult.getResponse().getContentAsString();
        Assert.assertNotNull(response);
        log.info("CardMasterResponse: " + response);

        // Convert json response to HashMap
        Assert.assertTrue(response.contains("success"));


    }

    @Test
    public void changeCardLockStatus()   throws Exception  {

        //Add the data
        cardMaster.setCrmCardStatus(CardMasterStatus.LOCKED);
        cardMaster = cardMasterService.saveCardMaster(cardMaster);

        // Place the delete request
        MvcResult mvcResult = mockMvc.perform(post("/api/0.9/json/merchant/storedvaluecard/cardmaster/security/active/" + cardMaster.getCrmCardNo())
                .principal(principal)
                .session(session)
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)

        )
                .andReturn();

        String response = mvcResult.getResponse().getContentAsString();
        Assert.assertNotNull(response);
        log.info("CardMasterResponse: " + response);

        // Convert json response to HashMap
        Assert.assertTrue(response.contains("success"));


    }

    @Test
    public void changeCardPin()   throws Exception  {

        //Add the da
        cardMaster = cardMasterService.saveCardMaster(cardMaster);

        // Place the delete request
        MvcResult mvcResult = mockMvc.perform(post("/api/0.9/json/merchant/storedvaluecard/cardmaster/pinchange/"+cardMaster.getCrmCardNo())
                .principal(principal)
                .session(session)
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .param("pin","2213")

        )
                .andReturn();

        String response = mvcResult.getResponse().getContentAsString();
        Assert.assertNotNull(response);
        log.info("CardMasterResponse: " + response);

        // Convert json response to HashMap
        Assert.assertTrue(response.contains("success"));


    }

    @Test
    public void topupCard()   throws Exception  {

        //Add the da
        // Get the cardType
        CardType cardType = CardTypeFixture.standardCardType();
        cardType.setCrtType(CardTypeType.RECHARGEBLE);
        cardType.setCrtMaxValue(1000.0);
        cardType.setCrtMinTopupValue(10);
        cardType = cardTypeService.saveCardType(cardType);

        tempSet1.add(cardType);

        // Set the cardtype for the cardmaster
        // Get the Cardmaster
        CardMaster cardMaster = CardMasterFixture.standardCardMaster();
        cardMaster.setCrmType(cardType.getCrtId());
        cardMaster = cardMasterService.saveCardMaster(cardMaster);

        tempSet2.add(cardMaster);

        // Place the delete request
        MvcResult mvcResult = mockMvc.perform(post("/api/0.9/json/merchant/storedvaluecard/cardmaster/topup/"+cardMaster.getCrmCardNo())
                .principal(principal)
                .session(session)
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .param("amount","200")

        )
                .andReturn();

        String response = mvcResult.getResponse().getContentAsString();
        Assert.assertNotNull(response);
        log.info("CardMasterResponse: " + response);

        // Convert json response to HashMap
        Assert.assertTrue(response.contains("success"));


    }

    @Test
    public void topupCardCompatible()   throws Exception  {

        /*//Add the da
        // Get the cardType
        CardType cardType = CardTypeFixture.standardCardType();
        cardType.setCrtType(CardTypeType.RECHARGEBLE);
        cardType.setCrtMaxValue(1000.0);
        cardType.setCrtMinTopupValue(10);
        cardType = cardTypeService.save(cardType);

        tempSet1.add(cardType);

        // Set the cardtype for the cardmaster
        // Get the Cardmaster
        CardMaster cardMaster = CardMasterFixture.standardCardMaster();
        cardMaster.setCrmType(cardType.getCrtId());
        cardMaster.setCrmCardNo("00091000");
        cardMaster = cardMasterService.saveCardMaster(cardMaster);

        tempSet2.add(cardMaster);*/

        // Place the delete request
        MvcResult mvcResult = mockMvc.perform(post("/api/0.9/json/transaction/cardtopup")
                .principal(principal)
                .session(session)
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .param("purchase_amount","200")
                .param("cardnumber","3333444455556674")
                .param("location","Smart")

        )
                .andReturn();

        String response = mvcResult.getResponse().getContentAsString();
        Assert.assertNotNull(response);
        log.info("CardMasterResponse: " + response);

        // Convert json response to HashMap
        Assert.assertTrue(response.contains("success"));


    }

    @Test
    public void refundCard()   throws Exception  {

        //Add the da
        // Get the cardType
        CardType cardType = CardTypeFixture.standardCardType();
        cardType.setCrtType(CardTypeType.RECHARGEBLE);
        cardType.setCrtMaxValue(1000.0);
        cardType.setCrtMinTopupValue(10);
        cardType = cardTypeService.saveCardType(cardType);


        // Set the cardtype for the cardmaster
        // Get the Cardmaster
        CardMaster cardMaster = CardMasterFixture.standardCardMaster();
        cardMaster.setCrmType(cardType.getCrtId());
        cardMaster = cardMasterService.saveCardMaster(cardMaster);

        // Place the delete request
        MvcResult mvcResult = mockMvc.perform(post("/api/0.9/json/merchant/storedvaluecard/cardmaster/refund/"+cardMaster.getCrmCardNo())
                .principal(principal)
                .session(session)
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .param("reference","c/2323")

        )
                .andReturn();

        String response = mvcResult.getResponse().getContentAsString();
        Assert.assertNotNull(response);
        log.info("CardMasterResponse: " + response);

        // Convert json response to HashMap
        Assert.assertTrue(response.contains("success"));


    }

    @Test
    public void transferCard()   throws Exception  {

        // Get the cardType
        CardType cardType = CardTypeFixture.standardCardType();
        cardType.setCrtType(CardTypeType.RECHARGEBLE);
        cardType.setCrtMaxValue(1000.0);
        cardType.setCrtMinTopupValue(10);
        cardType = cardTypeService.saveCardType(cardType);


        // Set the cardtype for the cardmaster
        // Get the  source Cardmaster
        CardMaster srcCardMaster = CardMasterFixture.standardCardMaster();
        srcCardMaster.setCrmCardNo("1001");
        srcCardMaster.setCrmType(cardType.getCrtId());
        srcCardMaster = cardMasterService.saveCardMaster(srcCardMaster);


        // Get the  source Cardmaster
        CardMaster destCardMaster = CardMasterFixture.standardCardMaster();
        destCardMaster.setCrmType(cardType.getCrtId());
        destCardMaster.setCrmCardNo("1002");
        destCardMaster = cardMasterService.saveCardMaster(destCardMaster);


        // Place the delete request
        MvcResult mvcResult = mockMvc.perform(post("/api/0.9/json/merchant/storedvaluecard/cardmaster/transfer")
                .principal(principal)
                .session(session)
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .param("sourceCardNo",srcCardMaster.getCrmCardNo())
                .param("destCardNo",destCardMaster.getCrmCardNo())

        )
                .andReturn();

        String response = mvcResult.getResponse().getContentAsString();
        Assert.assertNotNull(response);
        log.info("CardMasterResponse: " + response);

        // Convert json response to HashMap
        Assert.assertTrue(response.contains("success"));


    }

    @Test
    public void debitCard()   throws Exception  {

        // Get the cardType
        CardType cardType = CardTypeFixture.standardCardType();
        cardType.setCrtType(CardTypeType.RECHARGEBLE);
        cardType.setCrtMaxValue(1000.0);
        cardType.setCrtMinTopupValue(10);
        cardType = cardTypeService.saveCardType(cardType);


        // Set the cardtype for the cardmaster
        // Get the  source Cardmaster
        cardMaster.setCrmCardNo("1001");
        cardMaster.setCrmType(cardType.getCrtId());
        cardMaster = cardMasterService.saveCardMaster(cardMaster);


        // Place the delete request
        MvcResult mvcResult = mockMvc.perform(post("/api/0.9/json/merchant/storedvaluecard/cardmaster/debit/"+cardMaster.getCrmCardNo())
                .principal(principal)
                .session(session)
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .param("reference","1000")
                .param("amount","20")

        )
                .andReturn();

        String response = mvcResult.getResponse().getContentAsString();
        Assert.assertNotNull(response);
        log.info("CardMasterResponse: " + response);

        // Convert json response to HashMap
        Assert.assertTrue(response.contains("success"));


    }

    @Test
    public void debitCardCompatible()   throws Exception  {

        // Get the cardType
        CardType cardType = CardTypeFixture.standardCardType();
        cardType.setCrtName("topop1231");
        cardType.setCrtType(CardTypeType.RECHARGEBLE);
        cardType.setCrtMaxValue(1000.0);
        cardType.setCrtMinTopupValue(10);
        cardType = cardTypeService.save(cardType);

        tempSet1.add(cardType);

        // Save customer details
        Customer customer = CustomerFixture.standardCustomer();

        customer.setCusLoyaltyId("1234512342");

        customer =customerService.saveCustomer(customer);

        tempSet4.add(customer);

        // Get the  source Cardmaster
        cardMaster.setCrmCardNo("10011");
        cardMaster.setCrmType(cardType.getCrtId());
        cardMaster.setCrmLoyaltyId(customer.getCusLoyaltyId());
        cardMaster = cardMasterService.saveCardMaster(cardMaster);
        tempSet2.add(cardMaster);

        //generate otp
        // Get the standard customer
        otp=oneTimePasswordService.generateOTP(cardMaster.getCrmMerchantNo(),customer.getCusCustomerNo(), OTPType.CHARGE_CARD_PAYMENT);

        merchantNo =cardMaster.getCrmMerchantNo();
        // Place the delete request
        MvcResult mvcResult = mockMvc.perform(post("/api/0.9/json/transaction/chargecard")
                .principal(principal)
                .session(session)
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .param("cardnumber",cardMaster.getCrmCardNo())
                .param("purchase_amount","30")
                .param("txnref", "testAmeen")
                .param("otp_code",otp)


        )
                .andReturn();

        String response = mvcResult.getResponse().getContentAsString();
        Assert.assertNotNull(response);
        log.info("CardMasterResponse: " + response);

        // Convert json response to HashMap
        Assert.assertTrue(response.contains("success"));


    }


    @Test
    public void issueCardFromPublic()   throws Exception  {

        // Get the cardType
        CardType cardType = CardTypeFixture.standardCardType();
        cardType.setCrtName("ISSUE_CARD_TEST_TYPE");
        cardType.setCrtType(CardTypeType.FIXED_VALUE);
        cardType.setCrtFixedValue(1000.0);
        cardType = cardTypeService.save(cardType);

        Assert.assertNotNull(cardType);
        tempSet1.add(cardType);

        CardNumberInfo cardNumberInfo = CardNumberInfoFixture.standardCardNumberInfo();
        cardNumberInfo.setCniCardNumber("1111111111111111");
        cardNumberInfo.setCniPin("1111");
        cardNumberInfo.setCniCardType(cardType.getCrtId());
        cardNumberInfo=cardNumberInfoService.saveCardNumberInfo(cardNumberInfo);

        Assert.assertNotNull(cardNumberInfo);
        cardNumberInfoSet.add(cardNumberInfo);

        otp=oneTimePasswordService.generateOTP(cardNumberInfo.getCniMerchantNo(),"8867987369", OTPRefType.PUBLIC, OTPType.PUBLIC_CARD_REGISTER);

        // Get the cardmaster object
        CardMaster cardMaster = CardMasterFixture.standardCardMaster();
        cardMaster.setCrmCardNo("1111111111111111");
        cardMaster.setCrmMerchantNo(0L);
        cardMaster.setCrmMobile("8867987369");
        cardMaster.setCrmType(cardType.getCrtId());

        merchantNo =cardNumberInfo.getCniMerchantNo();
        // Place the delete request
        MvcResult mvcResult = mockMvc.perform(post("/api/0.9/json/public/card/issue")
                .principal(principal)
                .session(session)
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .param("cardNumber",cardMaster.getCrmCardNo())
                .param("pin",cardNumberInfo.getCniPin())
                .param("mobile",cardMaster.getCrmMobile())
                .param("cardHolderName",cardMaster.getCrmCardHolderName())
                .param("email",cardMaster.getCrmEmailId())
                .param("dob", cardMaster.getCrmDob()==null?"":cardMaster.getCrmDob().toString())
                .param("merchantNo","0")
                .param("otpCode",otp)


        )
                .andReturn();

        String response = mvcResult.getResponse().getContentAsString();
        Assert.assertNotNull(response);
        log.info("CardMasterResponse: " + response);

        cardMaster.setCrmMerchantNo(cardNumberInfo.getCniMerchantNo());

        tempSet2.add(cardMaster);

        // Convert json response to HashMap
        Assert.assertTrue(response.contains("success"));


    }

    @Test
    public void getCardBalanceOTP()   throws Exception  {

        // Get the cardType
        CardType cardType = CardTypeFixture.standardCardType();
        cardType.setCrtName("ISSUE_CARD_TEST_TYPE");
        cardType.setCrtType(CardTypeType.FIXED_VALUE);
        cardType.setCrtFixedValue(1000.0);
        cardType = cardTypeService.save(cardType);

        Assert.assertNotNull(cardType);
        tempSet1.add(cardType);

        CardNumberInfo cardNumberInfo = CardNumberInfoFixture.standardCardNumberInfo();
        cardNumberInfo.setCniCardNumber("1111111111111111");
        cardNumberInfo.setCniPin("1111");
        cardNumberInfo.setCniCardType(cardType.getCrtId());
        cardNumberInfo=cardNumberInfoService.saveCardNumberInfo(cardNumberInfo);
        cardNumberInfoSet.add(cardNumberInfo);

        // Get the cardmaster object
        CardMaster cardMaster = CardMasterFixture.standardCardMaster();
        cardMaster.setCrmCardNo("1111111111111111");
        cardMaster.setCrmMobile("8867987369");
        cardMaster.setCrmType(cardType.getCrtId());
        cardMaster.setCrmMerchantNo(cardNumberInfo.getCniMerchantNo());

        cardMaster=cardMasterService.saveCardMaster(cardMaster);

        tempSet2.add(cardMaster);

        // Place the delete request
        MvcResult mvcResult = mockMvc.perform(post("/api/0.9/json/public/card/getbalanceotp")
                .principal(principal)
                .session(session)
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .param("cardNumber",cardMaster.getCrmCardNo())
                .param("merchantNo","0")


        )
                .andReturn();

        String response = mvcResult.getResponse().getContentAsString();
        Assert.assertNotNull(response);
        log.info("CardMasterResponse: " + response);



        // Convert json response to HashMap
        Assert.assertTrue(response.contains("success"));


    }

    @Test
    public void getCardBalancePublic()   throws Exception  {

        // Get the cardType
        CardType cardType = CardTypeFixture.standardCardType();
        cardType.setCrtName("ISSUE_CARD_TEST_TYPE");
        cardType.setCrtType(CardTypeType.FIXED_VALUE);
        cardType.setCrtFixedValue(1000.0);
        cardType = cardTypeService.save(cardType);

        Assert.assertNotNull(cardType);
        tempSet1.add(cardType);

        CardNumberInfo cardNumberInfo = CardNumberInfoFixture.standardCardNumberInfo();
        cardNumberInfo.setCniCardNumber("1111111111111111");
        cardNumberInfo.setCniPin("1111");
        cardNumberInfo.setCniCardType(cardType.getCrtId());
        cardNumberInfo=cardNumberInfoService.saveCardNumberInfo(cardNumberInfo);

        cardNumberInfoSet.add(cardNumberInfo);

        // Get the cardmaster object
        CardMaster cardMaster = CardMasterFixture.standardCardMaster();
        cardMaster.setCrmCardNo("1111111111111111");
        cardMaster.setCrmMobile("8867987369");
        cardMaster.setCrmType(cardType.getCrtId());
        cardMaster.setCrmMerchantNo(cardNumberInfo.getCniMerchantNo());

        cardMaster=cardMasterService.saveCardMaster(cardMaster);

        tempSet2.add(cardMaster);

        otp=oneTimePasswordService.generateOTP(1L,cardMaster.getCrmId().toString(), OTPRefType.CARDMASTER, OTPType.CHARGE_CARD_BALANCE_OTP);

        merchantNo=1L;

        // Place the delete request
        MvcResult mvcResult = mockMvc.perform(post("/api/0.9/json/public/card/getbalance")
                .principal(principal)
                .session(session)
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .param("cardNumber",cardMaster.getCrmCardNo())
                .param("merchantNo","0")
                .param("otpCode",otp)

        )
                .andReturn();

        String response = mvcResult.getResponse().getContentAsString();
        Assert.assertNotNull(response);
        log.info("CardMasterResponse: " + response);



        // Convert json response to HashMap
        Assert.assertTrue(response.contains("success"));


    }

    @After
    public void tearDown() throws InspireNetzException {


//        Set<CardMaster> cardMasters = CardMasterFixture.standardCardMasters();
//
//        for(CardMaster cardMaster: cardMasters) {
//
//            CardMaster delCardMaster = cardMasterService.findByCrmMerchantNoAndCrmCardNo(cardMaster.getCrmMerchantNo(),cardMaster.getCrmCardNo());
//
//            if ( delCardMaster != null ) {
//                cardMasterService.deleteCardMaster(delCardMaster.getCrmId(),delCardMaster.getCrmMerchantNo());
//            }
//
//        }
//
//
//
//        Set<CardType> cardTypes = CardTypeFixture.standardCardTypes();
//
//        for(CardType cardType: cardTypes) {
//
//            CardType delCardType = cardTypeService.findByCrtMerchantNoAndCrtName(cardType.getCrtMerchantNo(), cardType.getCrtName());
//
//            if ( delCardType != null ) {
//
//                cardTypeService.deleteCardType(delCardType.getCrtId(),delCardType.getCrtMerchantNo());
//
//            }
//
//        }

        for(Customer customer:tempSet4){

            customerService.deleteCustomer(customer.getCusCustomerNo());

        }
        for (CardMaster cardMaster:tempSet2){

            List<CardTransaction> cardTransactions=cardTransactionService.getCardTransactionCompatible(cardMaster.getCrmCardNo(), cardMaster.getCrmMerchantNo(), 100);
            for(CardTransaction cardTransaction:cardTransactions){

                cardTransactionService.deleteCardTransaction(cardTransaction.getCtxTxnNo());

            }
            cardMaster=cardMasterService.findByCrmMerchantNoAndCrmCardNo(cardMaster.getCrmMerchantNo(),cardMaster.getCrmCardNo());
            if(cardMaster!=null){
                cardMasterService.deleteCardMaster(cardMaster.getCrmId(),cardMaster.getCrmMerchantNo());
            }
        }

        for (CardNumberInfo cardNumberInfo:cardNumberInfoSet){

            cardNumberInfoService.delete(cardNumberInfo);
        }

        for (CardType cardType:tempSet1){


            cardTypeService.deleteCardType(cardType.getCrtId(),cardType.getCrtMerchantNo());
        }

        //delete otp
        OneTimePassword oneTimePassword=oneTimePasswordService.findByOtpCode(merchantNo,otp);

        if(oneTimePassword!=null){
        oneTimePasswordService.deleteOneTimePassword(oneTimePassword.getOtpId());
        }




    }

}
