package com.inspirenetz.api.test.controller;


import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.collect.Lists;
import com.inspirenetz.api.config.SecurityConfig;
import com.inspirenetz.api.core.dictionary.CatalogueRedemptionType;
import com.inspirenetz.api.core.dictionary.CustomerType;
import com.inspirenetz.api.core.dictionary.OTPType;
import com.inspirenetz.api.core.dictionary.RedemptionCatalogue;
import com.inspirenetz.api.core.domain.*;
import com.inspirenetz.api.core.service.*;
import com.inspirenetz.api.rest.exception.InspireNetzException;
import com.inspirenetz.api.test.config.*;
import com.inspirenetz.api.test.core.fixture.*;
import com.inspirenetz.api.test.util.ControllerTestUtils;
import com.inspirenetz.api.util.DBUtils;
import com.inspirenetz.api.util.ImageUtils;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.core.io.ClassPathResource;
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
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.*;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

/**
 * Created by sandheepgr on 30/4/14.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration(classes  = {ApplicationTestConfig.class, PersistenceTestConfig.class, ServiceTestConfig.class, SecurityTestConfig.class, WebTestConfig.class,IntegrationTestConfig.class})
public class RedemptionControllerTest {


    private static Logger log = LoggerFactory.getLogger(RedemptionControllerTest.class);


    private static MockMvc mockMvc;

    private static ObjectMapper mapper;

    @Autowired
    private RedemptionService redemptionService;

    @Autowired
    OneTimePasswordService oneTimePasswordService;

    String otp;

    Long merchantNo;

    @Autowired
    private CustomerRewardExpiryService customerRewardExpiryService;

    @Autowired
    private CustomerRewardBalanceService customerRewardBalanceService;

    @Autowired
    private CustomerService customerService;

    @Autowired
    private RewardCurrencyService rewardCurrencyService;

    @Autowired
    private WebApplicationContext wac;

    @Resource
    private FilterChainProxy springSecurityFilterChain;

    @Autowired
    @Qualifier("uds")
    protected UserDetailsService userDetailsService;

    @Autowired
    private CustomerSubscriptionService customerSubscriptionService;

    @Autowired
    private RedemptionMerchantService redemptionMerchantService;

    @Autowired
    private AccumulatedRewardBalanceService accumulatedRewardBalanceService;

    @Autowired
    private CatalogueService catalogueService;

    @Autowired
    private RedemptionVoucherService redemptionVoucherService;

    Set<Customer> tempSet1 =new HashSet<>();

    Set<CustomerRewardBalance> tempSet2 =new HashSet<>();

    Set<CustomerRewardExpiry> tempSet3 =new HashSet<>();

    // Redemption object
    private Redemption redemption;

    UsernamePasswordAuthenticationToken principal;

    MockHttpSession session;

    // Map to hold the returned json
    Map<String,String> map = new HashMap<>();

    Set<CustomerSubscription> tempSet = new HashSet<>(0);

    Set<Customer> customerSet = new HashSet<>(0);

    Set<Catalogue> catalogueSet = new HashSet<>(0);

    Set<Customer> cusSet = new HashSet<>(0);

    Set<RewardCurrency> rwdSet = new HashSet<>(0);

    Set<CustomerRewardExpiry> creSet = new HashSet<>(0);

    Set<Catalogue> catSet = new HashSet<>(0);

    Set<RedemptionMerchant> remSet = new HashSet<>(0);

    Set<CustomerRewardBalance> crbSet = new HashSet<>(0);

    Set<AccumulatedRewardBalance> arbSet = new HashSet<>(0);



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

            SecurityContextHolder.getContext().setAuthentication(principal);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    private void init() throws Exception{


        // Create the Mapper object
        mapper = new ObjectMapper();

        // Create the redemption
        redemption = RedemptionFixture.standardCatalogueRedemption();


    }



    @Test
    public void redeemCatalogue() throws Exception {

        RedemptionMerchant redemptionMerchant = RedemptionMerchantFixture.standardRedemptionMerchant();
        redemptionMerchant = redemptionMerchantService.saveRedemptionMerchant(redemptionMerchant);
        remSet.add(redemptionMerchant);

        Catalogue catalogue = CatalogueFixture.standardCatalogue();
        catalogue.setCatRedemptionMerchant(redemptionMerchant.getRemNo());
        catalogue.setCatRewardCurrencyId(1L);
        catalogue.setCatRedemptionType(CatalogueRedemptionType.VOUCHER_BASED);
        catalogue.setCatCustomerTier("");
        catalogue.setCatCustomerType(0);
        catalogue.setCatStartDate((DBUtils.covertToSqlDate("2014-11-01")));
        catalogue.setCatEndDate((DBUtils.covertToSqlDate("2014-12-30")));
        catalogue.setCatLocationValues("");
        catalogue.setCatProductValues("");
        catalogue.setCatExtReference("10");
        catalogue.setCatAvailableStock(10L);
        catalogueService.saveCatalogue(catalogue);
        catSet.add(catalogue);

        RedemptionCatalogue redemptionCatalogue = RedemptionFixture.standardRedemptionCatalogue();

        MvcResult mvcResult = mockMvc.perform(post("/api/0.9/json/customer/redemption/compatible")
                                                .principal(principal)
                                                .session(session)
                                                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                                                .param("cat_data[0][prd_no]", catalogue.getCatProductNo().toString())
                                                .param("cat_data[0][prd_code]", catalogue.getCatProductCode())
                                                .param("cat_data[0][merchant_no]", redemptionCatalogue.getCatMerchantNo().toString())
                                                .param("cat_data[0][qty]", "1")
                                                .param("merchant_no", "1")
                                                .param("contact", "8620127820")

                                              )
                                        .andReturn();
        String response = mvcResult.getResponse().getContentAsString();
        Assert.assertNotNull(response);
        log.info("RedemptionResponse: " + response);

        // Convert json response to HashMap
        map = mapper.readValue(response, new TypeReference<Map<String, String>>() { });
        Assert.assertTrue(map.get("status").equals("success"));


        log.info("Redemption created");


    }


    @Test
    public void redeemCatalogueMerchant() throws Exception {


        RedemptionCatalogue redemptionCatalogue = RedemptionFixture.standardRedemptionCatalogue();

        MvcResult mvcResult = mockMvc.perform(post("/api/0.9/json/merchant/redemption/checkout")
                .principal(principal)
                .session(session)
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .param("cat_data[0][prd_no]", redemptionCatalogue.getCatProductNo().toString())
                .param("cat_data[0][prd_code]", redemptionCatalogue.getCatProductCode())
                .param("cat_data[0][merchant_no]", redemptionCatalogue.getCatMerchantNo().toString())
                .param("cat_data[0][qty]", redemptionCatalogue.getCatQty().toString())
                .param("loyalty_id", "1100000036")
                .param("contact", "9538828853")

        )
                .andReturn();
        String response = mvcResult.getResponse().getContentAsString();
        Assert.assertNotNull(response);
        log.info("RedemptionResponse: " + response);

        // Convert json response to HashMap
        //map = mapper.readValue(response, new TypeReference<Map<String, String>>() { });
        //Assert.assertTrue(map.get("status").equals("success"));


        log.info("Redemption created");


    }

    @Test
    public void doCashbackCompatible() throws Exception {


        Customer customer = CustomerFixture.standardCustomer();
        customer.setCusLoyaltyId("8888888888");
        customer= customerService.saveCustomer(customer);
        cusSet.add(customer);


        CustomerRewardBalance customerRewardBalance = CustomerRewardBalanceFixture.standardRewardBalance();
        customerRewardBalance.setCrbLoyaltyId("8888888888");
        customerRewardBalance.setCrbRewardBalance(1000.00);
        customerRewardBalance=customerRewardBalanceService.saveCustomerRewardBalance(customerRewardBalance);
        crbSet.add(customerRewardBalance);

        CustomerRewardExpiry customerRewardExpirySet = CustomerRewardExpiryFixture.standardRewardExpiry();
        customerRewardExpirySet.setCreLoyaltyId("8888888888");
        customerRewardExpirySet.setCreRewardBalance(1000.00);

        customerRewardExpirySet =customerRewardExpiryService.saveCustomerRewardExpiry(customerRewardExpirySet);

        creSet.add(customerRewardExpirySet);

        otp=oneTimePasswordService.generateOTP(customer.getCusMerchantNo(),customer.getCusCustomerNo(), OTPType.CASH_BACK_REQUEST);

        merchantNo = customer.getCusMerchantNo();


        MvcResult mvcResult = mockMvc.perform(post("/api/0.9/json/transaction/cashback")
                .principal(principal)
                .session(session)
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .param("cardnumber",customer.getCusLoyaltyId())
                .param("rwd_currency_id",customerRewardBalance.getCrbRewardCurrency().toString())
                .param("purchase_amount","999")
                .param("txnref","0239")
                .param("otp_code","oooo")

        )
                .andReturn();
        String response = mvcResult.getResponse().getContentAsString();
        Assert.assertNotNull(response);
        log.info("RedemptionResponse: " + response);

        // Convert json response to HashMap
        map = mapper.readValue(response, new TypeReference<Map<String, String>>() { });
        Assert.assertTrue(map.get("status").equals("success"));


        log.info("Redemption created");


    }

    @Test
    public void listRedemptions() throws Exception {

        redemptionService.saveRedemption(redemption);


        MvcResult mvcResult = mockMvc.perform(get("/api/0.9/json/merchant/redemptions/loyaltyid/" + redemption.getRdmLoyaltyId())
                .principal(principal)
                .session(session)
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .param("page.sort.dir","desc")
                .param("page.sort","rdmId")

        )
                .andReturn();
        String response = mvcResult.getResponse().getContentAsString();
        Assert.assertNotNull(response);
        Assert.assertTrue(response.contains("success"));
        log.info("RedemptionResponse: " + response);

    }

    @Test
    public void listRedemptionRequests() throws Exception {

        redemptionService.saveRedemption(redemption);


        MvcResult mvcResult = mockMvc.perform(get("/api/0.9/json/merchant/redemptions/0/0?page.page=1&page.size=100")
                .principal(principal)
                .session(session)
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .param("status","0")

        )
                .andReturn();
        String response = mvcResult.getResponse().getContentAsString();
        Assert.assertNotNull(response);
        //Assert.assertTrue(response.contains("success"));
        log.info("RedemptionResponse: " + response);

    }

   /* @Test
    public void listRedemptionsForTrackingId() throws Exception {

        redemptionService.saveRedemption(redemption);


        MvcResult mvcResult = mockMvc.perform(get("/api/0.9/json/merchant/redemptions/list/trackingid/" + redemption.getRdmUniqueBatchTrackingId())
                .principal(principal)
                .session(session)
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)

        )
                .andReturn();
        String response = mvcResult.getResponse().getContentAsString();
        Assert.assertNotNull(response);
        Assert.assertTrue(response.contains("success"));
        log.info("RedemptionResponse: " + response);

    }
*/
    @Test
    public void updatePaymentStatus() throws Exception {

        redemptionService.saveRedemption(redemption);


        MvcResult mvcResult = mockMvc.perform(post("/api/0.9/json/merchant/redemption/paymentstatus/"+redemption.getRdmUniqueBatchTrackingId())
                .principal(principal)
                .session(session)
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .param("status","1")
        )
                .andReturn();
        String response = mvcResult.getResponse().getContentAsString();
        Assert.assertNotNull(response);
        Assert.assertTrue(response.contains("success"));
        log.info("RedemptionResponse: " + response);

    }

    @Test
    public void updateRedemptionStatus() throws Exception {

        redemptionService.saveRedemption(redemption);


        MvcResult mvcResult = mockMvc.perform(post("/api/0.9/json/merchant/redemption/status/"+redemption.getRdmUniqueBatchTrackingId())
                .principal(principal)
                .session(session)
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .param("status","20")
        )
                .andReturn();
        String response = mvcResult.getResponse().getContentAsString();
        Assert.assertNotNull(response);
        Assert.assertTrue(response.contains("success"));
        log.info("RedemptionResponse: " + response);

    }

    @Test
    public void redeemCatalogueSingleItemMerchant() throws Exception {


        //Create Customer object and save
        Customer customer = CustomerFixture.standardCustomer();
        customer.setCusLoyaltyId("9400651687");
        customer.setCusTier(1L);
        customer.setCusType(CustomerType.SUBSCRIBER);
        customer.setCusLocation(1L);
        customer = customerService.saveCustomer(customer);
        cusSet.add(customer);

        //create customer subscription object
        CustomerSubscription customerSubscription = CustomerSubscriptionFixture.standardCustomerSubscription();
        customerSubscription.setCsuCustomerNo(customer.getCusCustomerNo());
        customerSubscription.setCsuProductCode("PRD1000");

        //save customer subscription object
        customerSubscription = customerSubscriptionService.saveCustomerSubscription( customerSubscription);
        tempSet.add(customerSubscription);

        //Create Reward Currency object and save
        RewardCurrency rewardCurrency = RewardCurrencyFixture.standardRewardCurrency();
        rewardCurrency = rewardCurrencyService.saveRewardCurrency(rewardCurrency);
        rwdSet.add(rewardCurrency);

        //Create Reward Balance object save
        CustomerRewardBalance customerRewardBalance = CustomerRewardBalanceFixture.standardRewardBalance();
        customerRewardBalance.setCrbLoyaltyId(customer.getCusLoyaltyId());
        customerRewardBalance.setCrbRewardCurrency(rewardCurrency.getRwdCurrencyId());
        customerRewardBalance.setCrbRewardBalance(50000L);
        customerRewardBalanceService.saveCustomerRewardBalance(customerRewardBalance);
        crbSet.add(customerRewardBalance);

        //Create Accumulated Reward Balance object save
        AccumulatedRewardBalance accumulatedRewardBalance = AccumulatedRewardBalanceFixture.standardAccumulatedRewardBalance();
        accumulatedRewardBalance.setArbLoyaltyId(customer.getCusLoyaltyId());
        accumulatedRewardBalance.setArbRewardCurrency(rewardCurrency.getRwdCurrencyId());
        accumulatedRewardBalance.setArbRewardBalance(50000.0);
        accumulatedRewardBalanceService.saveAccumulatedRewardBalance(accumulatedRewardBalance);
        arbSet.add(accumulatedRewardBalance);

        //Create Customer Reward Expiry
        CustomerRewardExpiry customerRewardExpiry = CustomerRewardExpiryFixture.standardRewardExpiry();
        customerRewardExpiry.setCreLoyaltyId(customer.getCusLoyaltyId());
        customerRewardExpiry.setCreRewardBalance(50000.0);
        customerRewardExpiry.setCreRewardCurrencyId(rewardCurrency.getRwdCurrencyId());
        customerRewardExpiry.setCreExpiryDt(DBUtils.covertToSqlDate("2015-12-12"));
        customerRewardExpiryService.saveCustomerRewardExpiry(customerRewardExpiry);
        creSet.add(customerRewardExpiry);

        RedemptionMerchant redemptionMerchant = RedemptionMerchantFixture.standardRedemptionMerchant();
        redemptionMerchant = redemptionMerchantService.saveRedemptionMerchant(redemptionMerchant);
        remSet.add(redemptionMerchant);

        Catalogue catalogue = CatalogueFixture.standardCatalogue();
        catalogue.setCatRedemptionMerchant(redemptionMerchant.getRemNo());
        catalogue.setCatRewardCurrencyId(rewardCurrency.getRwdCurrencyId());
        catalogue.setCatRedemptionType(CatalogueRedemptionType.TELCO_PREPAID);
        catalogue.setCatCustomerTier("");
        catalogue.setCatCustomerType(CustomerType.SUBSCRIBER);
        catalogue.setCatStartDate((DBUtils.covertToSqlDate("2014-11-01")));
        catalogue.setCatEndDate((DBUtils.covertToSqlDate("2015-11-30")));
        catalogue.setCatLocationValues("");
        catalogue.setCatProductValues("");
        catalogue.setCatExtReference("123");
        catalogue.setCatAvailableStock(10L);
        catalogueService.saveCatalogue(catalogue);
        catSet.add(catalogue);

        MvcResult mvcResult = mockMvc.perform(post("/api/0.9/json/merchant/catalogueredemption")
                .principal(principal)
                .session(session)
                .param("loyaltyid",customer.getCusLoyaltyId())
                .param("prdcode",catalogue.getCatProductCode())
                .param("quantity","1")
                .param("destLoyaltyId","0")
                .param("channel","1")
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
        )
                .andReturn();
        String response = mvcResult.getResponse().getContentAsString();
        Assert.assertNotNull(response);
        log.info("RedemptionResponse: " + response);

        // Convert json response to HashMap
        map = mapper.readValue(response, new TypeReference<Map<String, String>>() { });
        Assert.assertTrue(map.get("status").equals("success"));


        log.info("Redemption created");


    }


    @Test
    public void testSpinnerTest() throws Exception {

        MvcResult mvcResult = mockMvc.perform(post("/api/0.9/json/merchant/catalogueredemption")
                .principal(principal)
                .session(session)
                .param("loyaltyid","9472294386")
                .param("prdcode","SPN1001")
                .param("quantity","1")
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
        )
                .andReturn();
        String response = mvcResult.getResponse().getContentAsString();
        Assert.assertNotNull(response);
        log.info("RedemptionResponse: " + response);

    }



    @After
    public void tearDown() throws InspireNetzException {

        Set<Customer> customers = CustomerFixture.standardCustomers();

        for(Customer customer: customers) {

            Customer delCustomer = customerService.findByCusLoyaltyIdAndCusMerchantNo(customer.getCusLoyaltyId(),customer.getCusMerchantNo());

            if ( delCustomer != null ) {
                customerService.deleteCustomer(delCustomer.getCusCustomerNo());
            }

        }



     /*   Set<CustomerRewardBalance> customerRewardBalances = CustomerRewardBalanceFixture.standardCustomerRewardBalances();

        for(CustomerRewardBalance customerRewardBalance: customerRewardBalances) {

            customerRewardBalanceService.deleteCustomerRewardBalance(customerRewardBalance);;

        }*/



       /* Set<CustomerRewardExpiry> customerRewardExpirys = CustomerRewardExpiryFixture.standardCustomerRewardExpirys();

        for(CustomerRewardExpiry customerRewardExpiry: customerRewardExpirys) {

            customerRewardExpiryService.deleteCustomerRewardExpiry(customerRewardExpiry);

        }*/

        for(Customer customer:customerSet){

            customerService.deleteCustomer(customer.getCusCustomerNo());

        }

        for(Catalogue catalogue:catalogueSet){

            catalogueService.deleteCatalogue(catalogue.getCatProductNo());

        }
        for(CustomerSubscription customerSubscription: tempSet){

            customerSubscriptionService.deleteCustomerSubscription(customerSubscription.getCsuId());

        }

        for(Customer customer: cusSet){

            customerService.deleteCustomer(customer.getCusCustomerNo());

        }
        /*for(RewardCurrency rewardCurrency: rwdSet){

            rewardCurrencyService.deleteRewardCurrency(rewardCurrency.getRwdCurrencyId());

        }*/
        for(CustomerRewardBalance rewardBalance: crbSet){

            customerRewardBalanceService.deleteCustomerRewardBalance(rewardBalance);

        }
        for(AccumulatedRewardBalance accumulatedRewardBalance: arbSet){

            accumulatedRewardBalanceService.deleteAccumulatedRewardBalance(accumulatedRewardBalance.getArbId());

        }
        for(CustomerRewardExpiry customerRewardExpiry: creSet){

            customerRewardExpiryService.deleteCustomerRewardExpiry(customerRewardExpiry);

        }
        for(RedemptionMerchant redemptionMerchant: remSet){

            redemptionMerchantService.deleteRedemptionMerchant(redemptionMerchant.getRemNo());

        }
        for(Catalogue catalogue: catSet){

            catalogueService.deleteCatalogue(catalogue.getCatProductNo());

        }

        //delete otp
        OneTimePassword oneTimePassword=oneTimePasswordService.findByOtpCode(merchantNo,otp);

        oneTimePasswordService.deleteOneTimePassword(oneTimePassword.getOtpId());


    }

}
