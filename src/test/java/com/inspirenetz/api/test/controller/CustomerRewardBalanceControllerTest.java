package com.inspirenetz.api.test.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.collect.Lists;
import com.inspirenetz.api.core.dictionary.IndicatorStatus;
import com.inspirenetz.api.core.dictionary.TransactionType;
import com.inspirenetz.api.core.dictionary.TransferPointRequest;
import com.inspirenetz.api.core.domain.*;
import com.inspirenetz.api.core.repository.CustomerRewardBalanceRepository;
import com.inspirenetz.api.core.service.*;
import com.inspirenetz.api.rest.exception.InspireNetzException;
import com.inspirenetz.api.test.config.*;
import com.inspirenetz.api.test.core.fixture.*;
import com.inspirenetz.api.test.util.ControllerTestUtils;
import com.inspirenetz.api.util.AuthSessionUtils;
import com.inspirenetz.api.util.DBUtils;
import com.inspirenetz.api.util.GeneralUtils;
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
public class CustomerRewardBalanceControllerTest {


    private static Logger log = LoggerFactory.getLogger(CustomerRewardBalanceControllerTest.class);


    private static MockMvc mockMvc;

    private static ObjectMapper mapper;

    @Autowired
    AuthSessionUtils authSessionUtils;


    @Autowired
    private WebApplicationContext wac;

    @Resource
    private FilterChainProxy springSecurityFilterChain;

    @Autowired
    @Qualifier("uds")
    protected UserDetailsService userDetailsService;

    @Autowired
    DrawChanceService drawChanceService;



    Set<CustomerRewardBalance> tempSet = new HashSet<>(0);

    Set<Customer> customerDrawSet = new HashSet<>(0);

    Set<DrawChance> drawChancesSet = new HashSet<>(0);




    @Autowired
    private GeneralUtils generalUtils;

    @Autowired
    private CustomerService customerService;

    @Autowired
    private RewardCurrencyService rewardCurrencyService;

    @Autowired
    private CustomerRewardBalanceService customerRewardBalanceService;

    @Autowired
    private CustomerRewardExpiryService customerRewardExpiryService;

    @Autowired
    private TransferPointService transferPointService;

    @Autowired
    private TierService tierService;

    @Autowired
    private TransferPointSettingService transferPointSettingService;

    @Autowired
    private TierGroupService tierGroupService;

    @Autowired
    private LinkedLoyaltyService linkedLoyaltyService;

    @Autowired
    private PrimaryLoyaltyService primaryLoyaltyService;

    @Autowired
    private AccumulatedRewardBalanceService accumulatedRewardBalanceService;

    @Autowired
    CustomerSubscriptionService customerSubscriptionService;

    Set<Tier> tierSet = new HashSet<>(0);

    Set<TransferPointSetting> transferPointSettingSet = new HashSet<>(0);

    Set<TierGroup> tierGroupSet = new HashSet<>(0);

    Set<RewardCurrency> rewardCurrencySet = new HashSet<>(0);

    Set<Customer> customerSet = new HashSet<>(0);

    Set<RewardCurrency> rwdSet = new HashSet<>(0);

    Set<CustomerRewardBalance> crbSet = new HashSet<>(0);

    Set<AccumulatedRewardBalance> arbSet = new HashSet<>(0);

    Set<CustomerRewardExpiry> creSet = new HashSet<>(0);

    Set<CustomerSubscription> cusSubSet = new HashSet<>(0);

    Set<DrawChance> drawChances =new HashSet<>(0);


    // CustomerRewardBalance object
    private CustomerRewardBalance customerRewardBalance;

    UsernamePasswordAuthenticationToken principal;

    MockHttpSession session;

    // Map to hold the returned json
    Map<String,String> map = new HashMap<>();




    @Before
    public void setUp()
    {
        useMerchantUser();
    }


    public void useMerchantUser() {

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


    public void useCustomerUser() {

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

        // Create the customerRewardBalance
        customerRewardBalance = CustomerRewardBalanceFixture.standardRewardBalance();


    }




    @Test
    public void getCustomerRewardBalancesCompatible() throws Exception  {

        // Get the set of customerRewardBalances
        Set<CustomerRewardBalance> customerRewardBalanceSet = CustomerRewardBalanceFixture.standardCustomerRewardBalances();
        List<CustomerRewardBalance>  customerRewardBalanceList = new ArrayList<CustomerRewardBalance>();
        customerRewardBalanceList.addAll(customerRewardBalanceSet);
        customerRewardBalanceService.saveAll(customerRewardBalanceList);


        tempSet.addAll(customerRewardBalanceList);

        // Place the delete request
        MvcResult mvcResult = mockMvc.perform(get("/api/0.9/json/loyalty/rewardbalance")
                                                    .principal(principal)
                                                    .session(session)
                                                    .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                                                    .param("loyalty_id", customerRewardBalance.getCrbLoyaltyId())
                                            )
                                            .andReturn();

        String response = mvcResult.getResponse().getContentAsString();
        Assert.assertNotNull(response);
        log.info("CustomerRewardBalanceResponse: " + response);

        // Convert json response to HashMap
        Assert.assertTrue(response.contains("success"));


    }



    @Test
    public void getCustomerRewardBalanceForCustomer() throws Exception  {

        useCustomerUser();

        // Create a reward currency
        RewardCurrency rewardCurrency = RewardCurrencyFixture.standardRewardCurrency();
        rewardCurrency = rewardCurrencyService.saveRewardCurrency(rewardCurrency);
        rewardCurrencySet.add(rewardCurrency);

        // Get the set of customerRewardBalances
        CustomerRewardBalance customerRewardBalance = CustomerRewardBalanceFixture.standardRewardBalance();
        customerRewardBalance.setCrbRewardCurrency(rewardCurrency.getRwdCurrencyId());
        tempSet.add(customerRewardBalance);


        // Place the delete request
        MvcResult mvcResult = mockMvc.perform(get("/api/0.9/json/customer/rewardbalance/"+customerRewardBalance.getCrbMerchantNo()+"/"+customerRewardBalance.getCrbRewardCurrency())
                .principal(principal)
                .session(session)
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
        )
                .andReturn();

        String response = mvcResult.getResponse().getContentAsString();
        Assert.assertNotNull(response);
        log.info("CustomerRewardBalanceResponse: " + response);

        // Convert json response to HashMap
        Assert.assertTrue(response.contains("success"));


    }



    @Test
    public void getCustomerRewardBalanceForMerchantUser() throws Exception  {


        useMerchantUser();

        // Create a reward currency
        RewardCurrency rewardCurrency = RewardCurrencyFixture.standardRewardCurrency();
        rewardCurrency = rewardCurrencyService.saveRewardCurrency(rewardCurrency);
        rewardCurrencySet.add(rewardCurrency);

        //save customer
        Customer customer = CustomerFixture.standardCustomer();

        customer.setCusLoyaltyId("4560127910");

        customerService.saveCustomer(customer);

        customerDrawSet.add(customer);

        // Get the set of customerRewardBalances
        CustomerRewardBalance customerRewardBalance = CustomerRewardBalanceFixture.standardRewardBalance();
        customerRewardBalance.setCrbRewardCurrency(rewardCurrency.getRwdCurrencyId());
        customerRewardBalance.setCrbLoyaltyId(customer.getCusLoyaltyId());

        customerRewardBalance = customerRewardBalanceService.saveCustomerRewardBalance(customerRewardBalance);


        tempSet.add(customerRewardBalance);

        //save Draw chances
        DrawChance drawChance =DrawChanceFixture.standardDrawChance();

        drawChance.setDrcCustomerNo(customer.getCusCustomerNo());

        drawChanceService.saveDrawChance(drawChance);

        drawChancesSet.add(drawChance);



        // Place the delete request
        MvcResult mvcResult = mockMvc.perform(get("/api/0.9/json/merchant/customer/rewardbalance/"+customerRewardBalance.getCrbLoyaltyId()+"/"+"0")
                .principal(principal)
                .session(session)
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
        )
                .andReturn();

        String response = mvcResult.getResponse().getContentAsString();
        Assert.assertNotNull(response);
        log.info("CustomerRewardBalanceResponse: " + response);

        // Convert json response to HashMap
        Assert.assertTrue(response.contains("success"));


    }



    @Test
    public void sendCustomerRewardBalanceSMS() throws Exception  {


        useMerchantUser();

        // Create a reward currency
        RewardCurrency rewardCurrency = RewardCurrencyFixture.standardRewardCurrency();
        rewardCurrency = rewardCurrencyService.saveRewardCurrency(rewardCurrency);
        rewardCurrencySet.add(rewardCurrency);

        // Get the set of customerRewardBalances
        CustomerRewardBalance customerRewardBalance = CustomerRewardBalanceFixture.standardRewardBalance();
        customerRewardBalance.setCrbRewardCurrency(rewardCurrency.getRwdCurrencyId());
        customerRewardBalance = customerRewardBalanceService.saveCustomerRewardBalance(customerRewardBalance);
        tempSet.add(customerRewardBalance);


        // Place the delete request
        MvcResult mvcResult = mockMvc.perform(get("/api/0.9/json/merchant/customer/rewardbalance/sms/"+customerRewardBalance.getCrbLoyaltyId()+"/"+customerRewardBalance.getCrbRewardCurrency())
                .principal(principal)
                .session(session)
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
        )
                .andReturn();

        String response = mvcResult.getResponse().getContentAsString();
        Assert.assertNotNull(response);
        log.info("CustomerRewardBalanceResponse: " + response);

        // Convert json response to HashMap
        Assert.assertTrue(response.contains("success"));


    }



    @Test
    public void transferPointsForCustomer() throws Exception  {


        useCustomerUser();



        // Add TierGropu
        TierGroup tierGroup = TierGroupFixture.standardTierGroup();
        tierGroup = tierGroupService.saveTierGroup(tierGroup);
        Assert.assertNotNull(tierGroup);

        //Add to the tierGroupSet
        tierGroupSet.add(tierGroup);

        // Create a tier
        Tier tier = TierFixture.standardTier();
        tier.setTieParentGroup(tierGroup.getTigId());
        tier.setTieIsTransferPointsAllowedInd(IndicatorStatus.YES);
        tier = tierService.saveTier(tier);


        // Add tier to tierSet
        tierSet.add(tier);

        // Source customer object
        Customer sourceCustomer = CustomerFixture.standardCustomer();
        sourceCustomer.setCusTier(tier.getTieId());
        sourceCustomer = customerService.saveCustomer(sourceCustomer);

        // Add to customerset
        customerSet.add(sourceCustomer);

        // Destination customer object
        Customer destCustomer = CustomerFixture.standardCustomer();
        destCustomer.setCusLoyaltyId("9999888877776662");
        destCustomer = customerService.saveCustomer(destCustomer);

        // Add to customerset
        customerSet.add(destCustomer);

        /*
        // Create the LinkedLoyalty
        LinkedLoyalty linkedLoyalty = LinkedLoyaltyFixture.standardLinkedLoyalty();
        linkedLoyalty.setLilLocation(sourceCustomer.getCusLocation());
        linkedLoyalty.setLilChildCustomerNo(destCustomer.getCusCustomerNo());
        linkedLoyalty.setLilParentCustomerNo(sourceCustomer.getCusCustomerNo());
        linkedLoyalty.setLilStatus(LinkedLoyaltyStatus.ACTIVE);
        linkedLoyalty = linkedLoyaltyService.saveLinkedLoyalty(linkedLoyalty);


        // Create a PrimaryLoyalty entry
        PrimaryLoyalty primaryLoyalty = PrimaryLoyaltyFixture.standardPrimaryLoyalty();
        primaryLoyalty.setPllLoyaltyId(sourceCustomer.getCusLoyaltyId());
        primaryLoyalty.setPllCustomerNo(sourceCustomer.getCusCustomerNo());
        primaryLoyalty = primaryLoyaltyService.savePrimaryLoyalty(primaryLoyalty);
        */


        // Add the reward currency
        RewardCurrency rewardCurrency = RewardCurrencyFixture.standardRewardCurrency();
        rewardCurrency = rewardCurrencyService.saveRewardCurrency(rewardCurrency);
        Assert.assertNotNull(rewardCurrency);


        // Add the customer reward balance for the source customer
        // Add the CustomerrewardBalance for secondary
        CustomerRewardBalance customerRewardBalance = CustomerRewardBalanceFixture.standardRewardBalance();
        customerRewardBalance.setCrbLoyaltyId(sourceCustomer.getCusLoyaltyId());
        customerRewardBalance.setCrbRewardCurrency(rewardCurrency.getRwdCurrencyId());
        customerRewardBalance =  customerRewardBalanceService.saveCustomerRewardBalance(customerRewardBalance);


        // Add the CustomerRewardExpiry for secondary
        CustomerRewardExpiry customerRewardExpiry = CustomerRewardExpiryFixture.standardRewardExpiry();
        customerRewardExpiry.setCreLoyaltyId(sourceCustomer.getCusLoyaltyId());
        customerRewardExpiry.setCreRewardCurrencyId(rewardCurrency.getRwdCurrencyId());
        customerRewardExpiry.setCreExpiryDt(DBUtils.covertToSqlDate("2014-08-31"));
        customerRewardExpiry = customerRewardExpiryService.saveCustomerRewardExpiry(customerRewardExpiry);



        // Create the TransferPointSetting object
        TransferPointSetting transferPointSetting = TransferPointSettingFixture.standardTransferPointSetting();
        transferPointSetting = transferPointSettingService.saveTransferPointSetting(transferPointSetting);
        Assert.assertNotNull(transferPointSetting);

        // Add to the TransferPointSettingset
        transferPointSettingSet.add(transferPointSetting);


        // Create the TransferPointRequest object
        TransferPointRequest transferPointRequest = new TransferPointRequest();

        transferPointRequest.setFromLoyaltyId(sourceCustomer.getCusLoyaltyId());
        transferPointRequest.setToLoyaltyId(destCustomer.getCusLoyaltyId());
        transferPointRequest.setFromRewardCurrency(rewardCurrency.getRwdCurrencyId());
        transferPointRequest.setToRewardCurrency(rewardCurrency.getRwdCurrencyId());
        transferPointRequest.setRewardQty(20.0);

        // make the call
        boolean isTranferred = transferPointService.transferPoints(transferPointRequest);
        Assert.assertTrue(isTranferred);




        // Place the delete request
        MvcResult mvcResult = mockMvc.perform(post("/api/0.9/json/customer/rewardbalance/transferpoints")
                .principal(principal)
                .session(session)
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .param("merchantNo",sourceCustomer.getCusMerchantNo().toString())
                .param("destLoyaltyId",destCustomer.getCusLoyaltyId())
                .param("srcCurrencyId",rewardCurrency.getRwdCurrencyId().toString())
                .param("destCurrencyId",rewardCurrency.getRwdCurrencyId().toString())
                .param("rwdQty","20.0")
        )
                .andReturn();

        String response = mvcResult.getResponse().getContentAsString();
        Assert.assertNotNull(response);
        log.info("Transfer points response: " + response);

        // Convert json response to HashMap
        Assert.assertTrue(response.contains("success"));


    }



    @Test
    public void transferPointsForMerchant() throws Exception  {


        useMerchantUser();


/*

        // Create the customers
        Set<Customer> customers = CustomerFixture.standardCustomers();
        List<Customer> customerList = Lists.newArrayList((Iterable<Customer>)customers);
        customerService.saveAll(customerList);

        // Add to the set
        customerSet.addAll(customers);


        // Add TierGropu
        TierGroup tierGroup = TierGroupFixture.standardTierGroup();
        tierGroup = tierGroupService.saveTierGroup(tierGroup);
        Assert.assertNotNull(tierGroup);

        //Add to the tierGroupSet
        tierGroupSet.add(tierGroup);

        // Create a tier
        Tier tier = TierFixture.standardTier();
        tier.setTieParentGroup(tierGroup.getTigId());
        tier.setTieIsTransferPointsAllowedInd(IndicatorStatus.YES);
        tier = tierService.saveTier(tier);


        // Add tier to tierSet
        tierSet.add(tier);

        // Source customer object
        Customer sourceCustomer = customerList.get(0);
        sourceCustomer.setCusTier(tier.getTieId());
        sourceCustomer = customerService.saveCustomer(sourceCustomer);

        // Destination customer object
        Customer destCustomer = customerList.get(1);

        */
/*
        // Create the LinkedLoyalty
        LinkedLoyalty linkedLoyalty = LinkedLoyaltyFixture.standardLinkedLoyalty();
        linkedLoyalty.setLilLocation(sourceCustomer.getCusLocation());
        linkedLoyalty.setLilChildCustomerNo(destCustomer.getCusCustomerNo());
        linkedLoyalty.setLilParentCustomerNo(sourceCustomer.getCusCustomerNo());
        linkedLoyalty.setLilStatus(LinkedLoyaltyStatus.ACTIVE);
        linkedLoyalty = linkedLoyaltyService.saveLinkedLoyalty(linkedLoyalty);


        // Create a PrimaryLoyalty entry
        PrimaryLoyalty primaryLoyalty = PrimaryLoyaltyFixture.standardPrimaryLoyalty();
        primaryLoyalty.setPllLoyaltyId(sourceCustomer.getCusLoyaltyId());
        primaryLoyalty.setPllCustomerNo(sourceCustomer.getCusCustomerNo());
        primaryLoyalty = primaryLoyaltyService.savePrimaryLoyalty(primaryLoyalty);
        *//*



        // Add the reward currency
        RewardCurrency rewardCurrency = RewardCurrencyFixture.standardRewardCurrency();
        rewardCurrency = rewardCurrencyService.saveRewardCurrency(rewardCurrency);
        Assert.assertNotNull(rewardCurrency);


        // Add the customer reward balance for the source customer
        // Add the CustomerrewardBalance for secondary
        CustomerRewardBalance customerRewardBalance = CustomerRewardBalanceFixture.standardRewardBalance();
        customerRewardBalance.setCrbLoyaltyId(sourceCustomer.getCusLoyaltyId());
        customerRewardBalance.setCrbRewardCurrency(rewardCurrency.getRwdCurrencyId());
        customerRewardBalance =  customerRewardBalanceService.saveCustomerRewardBalance(customerRewardBalance);


        // Add the CustomerRewardExpiry for secondary
        CustomerRewardExpiry customerRewardExpiry = CustomerRewardExpiryFixture.standardRewardExpiry();
        customerRewardExpiry.setCreLoyaltyId(sourceCustomer.getCusLoyaltyId());
        customerRewardExpiry.setCreRewardCurrencyId(rewardCurrency.getRwdCurrencyId());
        customerRewardExpiry.setCreExpiryDt(DBUtils.covertToSqlDate("2014-08-31"));
        customerRewardExpiry = customerRewardExpiryService.saveCustomerRewardExpiry(customerRewardExpiry);



        // Create the TransferPointSetting object
        TransferPointSetting transferPointSetting = TransferPointSettingFixture.standardTransferPointSetting();
        transferPointSetting = transferPointSettingService.saveTransferPointSetting(transferPointSetting);
        Assert.assertNotNull(transferPointSetting);

        // Add to the TransferPointSettingset
        transferPointSettingSet.add(transferPointSetting);


        // Create the TransferPointRequest object
        TransferPointRequest transferPointRequest = new TransferPointRequest();

        transferPointRequest.setFromLoyaltyId(sourceCustomer.getCusLoyaltyId());
        transferPointRequest.setToLoyaltyId(destCustomer.getCusLoyaltyId());
        transferPointRequest.setFromRewardCurrency(rewardCurrency.getRwdCurrencyId());
        transferPointRequest.setToRewardCurrency(rewardCurrency.getRwdCurrencyId());
        transferPointRequest.setRewardQty(20.0);

        // make the call
        boolean isTranferred = transferPointService.transferPoints(transferPointRequest);
        Assert.assertTrue(isTranferred);

*/



        // Place the delete request
        MvcResult mvcResult = mockMvc.perform(post("/api/0.9/json/merchant/customer/rewardbalance/transferpoints")
                .principal(principal)
                .session(session)
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .param("srcLoyaltyId","8620127820")
                .param("destLoyaltyId","9538828853")
                .param("srcCurrencyId","1")
                .param("destCurrencyId","1")
                .param("rwdQty","1.0")
        )
                .andReturn();

        String response = mvcResult.getResponse().getContentAsString();
        Assert.assertNotNull(response);
        log.info("Transfer points response: " + response);

        // Convert json response to HashMap
        Assert.assertTrue(response.contains("success"));


    }

    @Test
    public void buyPointsForMerchant() throws Exception  {

        useMerchantUser();
        //Create Customer object and save
        Customer customer = CustomerFixture.standardCustomer();
        customer.setCusLoyaltyId("9742375875");
        customer = customerService.saveCustomer(customer);
        customerSet.add(customer);

        //Create Customer object and save
        CustomerSubscription customerSubscription = CustomerSubscriptionFixture.standardCustomerSubscription();
        customerSubscription.setCsuCustomerNo(customer.getCusCustomerNo());
        customerSubscription.setCsuProductCode("PRD1001");
        customerSubscription = customerSubscriptionService.saveCustomerSubscription(customerSubscription);
        cusSubSet.add(customerSubscription);

        //Create Reward Currency object and save
        RewardCurrency rewardCurrency = RewardCurrencyFixture.standardRewardCurrency();
        rewardCurrency = rewardCurrencyService.saveRewardCurrency(rewardCurrency);
        rwdSet.add(rewardCurrency);

        //Create Reward Balance object save
        CustomerRewardBalance customerRewardBalance = CustomerRewardBalanceFixture.standardRewardBalance();
        customerRewardBalance.setCrbLoyaltyId(customer.getCusLoyaltyId());
        customerRewardBalance.setCrbRewardCurrency(rewardCurrency.getRwdCurrencyId());
        customerRewardBalance.setCrbRewardBalance(500L);
        customerRewardBalanceService.saveCustomerRewardBalance(customerRewardBalance);
        crbSet.add(customerRewardBalance);

        //Create Accumulated Reward Balance object save
        AccumulatedRewardBalance accumulatedRewardBalance = AccumulatedRewardBalanceFixture.standardAccumulatedRewardBalance();
        accumulatedRewardBalance.setArbLoyaltyId(customer.getCusLoyaltyId());
        accumulatedRewardBalance.setArbRewardCurrency(rewardCurrency.getRwdCurrencyId());
        accumulatedRewardBalance.setArbRewardBalance(500.0);
        accumulatedRewardBalanceService.saveAccumulatedRewardBalance(accumulatedRewardBalance);
        arbSet.add(accumulatedRewardBalance);

        //Create Customer Reward Expiry
        CustomerRewardExpiry customerRewardExpiry = CustomerRewardExpiryFixture.standardRewardExpiry();
        customerRewardExpiry.setCreLoyaltyId(customer.getCusLoyaltyId());
        customerRewardExpiry.setCreRewardBalance(500.0);
        customerRewardExpiry.setCreRewardCurrencyId(rewardCurrency.getRwdCurrencyId());
        customerRewardExpiry.setCreExpiryDt(DBUtils.covertToSqlDate("9999-12-31"));
        customerRewardExpiry = customerRewardExpiryService.saveCustomerRewardExpiry(customerRewardExpiry);
        creSet.add(customerRewardExpiry);

        // Place the delete request
        MvcResult mvcResult = mockMvc.perform(post("/api/0.9/json/merchant/rewardbalance/buypoints")
                .principal(principal)
                .session(session)
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .param("loyaltyid",customer.getCusLoyaltyId())
                .param("crbRwdCurrencyId",rewardCurrency.getRwdCurrencyId().toString())
                .param("crbNumPoints","1000")
        )
                .andReturn();

        String response = mvcResult.getResponse().getContentAsString();
        Assert.assertNotNull(response);
        log.info("Transfer points response: " + response);

        // Convert json response to HashMap
        Assert.assertTrue(response.contains("success"));

    }

    @Test
    public void buyPointsForCustomer() throws Exception  {

        useCustomerUser();
        //Create Customer object and save
        Customer customer = CustomerFixture.standardCustomer();
        customer.setCusLoyaltyId("9742375875");
        customer.setCusUserNo(208L);
        customer = customerService.saveCustomer(customer);
        customerSet.add(customer);

        //Create Customer object and save
        CustomerSubscription customerSubscription = CustomerSubscriptionFixture.standardCustomerSubscription();
        customerSubscription.setCsuCustomerNo(customer.getCusCustomerNo());
        customerSubscription.setCsuProductCode("PRD1001");
        customerSubscription = customerSubscriptionService.saveCustomerSubscription(customerSubscription);
        cusSubSet.add(customerSubscription);

        //Create Reward Currency object and save
        RewardCurrency rewardCurrency = RewardCurrencyFixture.standardRewardCurrency();
        rewardCurrency = rewardCurrencyService.saveRewardCurrency(rewardCurrency);
        rwdSet.add(rewardCurrency);

        //Create Reward Balance object save
        CustomerRewardBalance customerRewardBalance = CustomerRewardBalanceFixture.standardRewardBalance();
        customerRewardBalance.setCrbLoyaltyId(customer.getCusLoyaltyId());
        customerRewardBalance.setCrbRewardCurrency(rewardCurrency.getRwdCurrencyId());
        customerRewardBalance.setCrbRewardBalance(500L);
        customerRewardBalanceService.saveCustomerRewardBalance(customerRewardBalance);
        crbSet.add(customerRewardBalance);

        //Create Accumulated Reward Balance object save
        AccumulatedRewardBalance accumulatedRewardBalance = AccumulatedRewardBalanceFixture.standardAccumulatedRewardBalance();
        accumulatedRewardBalance.setArbLoyaltyId(customer.getCusLoyaltyId());
        accumulatedRewardBalance.setArbRewardCurrency(rewardCurrency.getRwdCurrencyId());
        accumulatedRewardBalance.setArbRewardBalance(500.0);
        accumulatedRewardBalanceService.saveAccumulatedRewardBalance(accumulatedRewardBalance);
        arbSet.add(accumulatedRewardBalance);

        //Create Customer Reward Expiry
        CustomerRewardExpiry customerRewardExpiry = CustomerRewardExpiryFixture.standardRewardExpiry();
        customerRewardExpiry.setCreLoyaltyId(customer.getCusLoyaltyId());
        customerRewardExpiry.setCreRewardBalance(500.0);
        customerRewardExpiry.setCreRewardCurrencyId(rewardCurrency.getRwdCurrencyId());
        customerRewardExpiry.setCreExpiryDt(DBUtils.covertToSqlDate("9999-12-31"));
        customerRewardExpiry = customerRewardExpiryService.saveCustomerRewardExpiry(customerRewardExpiry);
        creSet.add(customerRewardExpiry);

        // Place the delete request
        MvcResult mvcResult = mockMvc.perform(post("/api/0.9/json/customer/rewardbalance/buypoints")
                .principal(principal)
                .session(session)
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .param("merchantno","1")
                .param("crbRwdCurrencyId",rewardCurrency.getRwdCurrencyId().toString())
                .param("crbNumPoints","1000")
        )
                .andReturn();

        String response = mvcResult.getResponse().getContentAsString();
        Assert.assertNotNull(response);
        log.info("Transfer points response: " + response);

        // Convert json response to HashMap
        Assert.assertTrue(response.contains("success"));

    }

    @Test
    public void test8DoRewardAdjustments() throws Exception {

        //Create Customer object and save
        Customer customer = CustomerFixture.standardCustomer();
        customer.setCusLoyaltyId("9742375875");
        customer = customerService.saveCustomer(customer);
        customerSet.add(customer);

        //Create Reward Currency object and save
        RewardCurrency rewardCurrency = RewardCurrencyFixture.standardRewardCurrency();
        rewardCurrency = rewardCurrencyService.saveRewardCurrency(rewardCurrency);
        rwdSet.add(rewardCurrency);

        //Create Reward Balance object save
        CustomerRewardBalance customerRewardBalance = CustomerRewardBalanceFixture.standardRewardBalance();
        customerRewardBalance.setCrbLoyaltyId(customer.getCusLoyaltyId());
        customerRewardBalance.setCrbRewardCurrency(rewardCurrency.getRwdCurrencyId());
        customerRewardBalance.setCrbRewardBalance(500L);
        customerRewardBalanceService.saveCustomerRewardBalance(customerRewardBalance);
        crbSet.add(customerRewardBalance);

        //Create Accumulated Reward Balance object save
        AccumulatedRewardBalance accumulatedRewardBalance = AccumulatedRewardBalanceFixture.standardAccumulatedRewardBalance();
        accumulatedRewardBalance.setArbLoyaltyId(customer.getCusLoyaltyId());
        accumulatedRewardBalance.setArbRewardCurrency(rewardCurrency.getRwdCurrencyId());
        accumulatedRewardBalance.setArbRewardBalance(500.0);
        accumulatedRewardBalanceService.saveAccumulatedRewardBalance(accumulatedRewardBalance);
        arbSet.add(accumulatedRewardBalance);

        //Create Customer Reward Expiry
        CustomerRewardExpiry customerRewardExpiry = CustomerRewardExpiryFixture.standardRewardExpiry();
        customerRewardExpiry.setCreLoyaltyId(customer.getCusLoyaltyId());
        customerRewardExpiry.setCreRewardBalance(500.0);
        customerRewardExpiry.setCreRewardCurrencyId(rewardCurrency.getRwdCurrencyId());
        customerRewardExpiry.setCreExpiryDt(DBUtils.covertToSqlDate("9999-12-31"));
        customerRewardExpiry = customerRewardExpiryService.saveCustomerRewardExpiry(customerRewardExpiry);
        creSet.add(customerRewardExpiry);

        // Place the delete request
        MvcResult mvcResult = mockMvc.perform(post("/api/0.9/json/customer/rewardbalance/adjustment")
                .principal(principal)
                .session(session)
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .param("adjPoints","100")
                .param("adjLoyaltyId",customer.getCusLoyaltyId())
                .param("adjCurrencyId",rewardCurrency.getRwdCurrencyId().toString())
                .param("adjProgramNo","0")
                .param("adjType",TransactionType.REWARD_ADJUSTMENT_DEDUCTING+"")
                .param("adjReference","")
                .param("isTierAffected",false+"")
        )
                .andReturn();

        String response = mvcResult.getResponse().getContentAsString();
        Assert.assertNotNull(response);
        log.info("Transfer points response: " + response);

        // Convert json response to HashMap
        Assert.assertTrue(response.contains("success"));
    }

    @Test
    public void test8GetRewardBalanceForCustomer() throws Exception {

//        //Create Customer object and save
//        Customer customer = CustomerFixture.standardCustomer();
//        customer.setCusLoyaltyId("8281046944");
//        customer = customerService.saveCustomer(customer);
//        customerSet.add(customer);
//
//        //Create Reward Currency object and save
//        RewardCurrency rewardCurrency = RewardCurrencyFixture.standardRewardCurrency();
//        rewardCurrency = rewardCurrencyService.saveRewardCurrency(rewardCurrency);
//        rwdSet.add(rewardCurrency);
//
//        //Create Reward Balance object save
//        CustomerRewardBalance customerRewardBalance = CustomerRewardBalanceFixture.standardRewardBalance();
//        customerRewardBalance.setCrbLoyaltyId(customer.getCusLoyaltyId());
//        customerRewardBalance.setCrbRewardCurrency(rewardCurrency.getRwdCurrencyId());
//        customerRewardBalance.setCrbRewardBalance(500L);
//        customerRewardBalanceService.saveCustomerRewardBalance(customerRewardBalance);
//        crbSet.add(customerRewardBalance);
//
//        //Create Accumulated Reward Balance object save
//        AccumulatedRewardBalance accumulatedRewardBalance = AccumulatedRewardBalanceFixture.standardAccumulatedRewardBalance();
//        accumulatedRewardBalance.setArbLoyaltyId(customer.getCusLoyaltyId());
//        accumulatedRewardBalance.setArbRewardCurrency(rewardCurrency.getRwdCurrencyId());
//        accumulatedRewardBalance.setArbRewardBalance(500.0);
//        accumulatedRewardBalanceService.saveAccumulatedRewardBalance(accumulatedRewardBalance);
//        arbSet.add(accumulatedRewardBalance);
//
//        //Create Customer Reward Expiry
//        CustomerRewardExpiry customerRewardExpiry = CustomerRewardExpiryFixture.standardRewardExpiry();
//        customerRewardExpiry.setCreLoyaltyId(customer.getCusLoyaltyId());
//        customerRewardExpiry.setCreRewardBalance(500.0);
//        customerRewardExpiry.setCreRewardCurrencyId(rewardCurrency.getRwdCurrencyId());
//        customerRewardExpiry.setCreExpiryDt(DBUtils.covertToSqlDate("9999-12-31"));
//        customerRewardExpiry = customerRewardExpiryService.saveCustomerRewardExpiry(customerRewardExpiry);
//        creSet.add(customerRewardExpiry);

//        CustomerRewardBalance customerRewardBalance = CustomerRewardBalanceFixture.standardRewardBalance();
//        customerRewardBalanceService.saveCustomerRewardBalance(customerRewardBalance);



        // Place the delete request
        MvcResult mvcResult = mockMvc.perform(get("/api/0.9/json/customer/compatible/rewardbalance?merchant_no=1")
                .principal(principal)
                .session(session)
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
        )
                .andReturn();

        String response = mvcResult.getResponse().getContentAsString();
        Assert.assertNotNull(response);
        log.info("Transfer points response: " + response);

        // Convert json response to HashMap
        Assert.assertTrue(response.contains("success"));
    }

    @Test
    public void test8GetRewardBalanceForUser() throws Exception {

//        //Create Customer object and save
//        Customer customer = CustomerFixture.standardCustomer();
//        customer.setCusLoyaltyId("8281046944");
//        customer = customerService.saveCustomer(customer);
//        customerSet.add(customer);
//
//        //Create Reward Currency object and save
//        RewardCurrency rewardCurrency = RewardCurrencyFixture.standardRewardCurrency();
//        rewardCurrency = rewardCurrencyService.saveRewardCurrency(rewardCurrency);
//        rwdSet.add(rewardCurrency);
//
//        //Create Reward Balance object save
//        CustomerRewardBalance customerRewardBalance = CustomerRewardBalanceFixture.standardRewardBalance();
//        customerRewardBalance.setCrbLoyaltyId(customer.getCusLoyaltyId());
//        customerRewardBalance.setCrbRewardCurrency(rewardCurrency.getRwdCurrencyId());
//        customerRewardBalance.setCrbRewardBalance(500L);
//        customerRewardBalanceService.saveCustomerRewardBalance(customerRewardBalance);
//        crbSet.add(customerRewardBalance);
//
//        //Create Accumulated Reward Balance object save
//        AccumulatedRewardBalance accumulatedRewardBalance = AccumulatedRewardBalanceFixture.standardAccumulatedRewardBalance();
//        accumulatedRewardBalance.setArbLoyaltyId(customer.getCusLoyaltyId());
//        accumulatedRewardBalance.setArbRewardCurrency(rewardCurrency.getRwdCurrencyId());
//        accumulatedRewardBalance.setArbRewardBalance(500.0);
//        accumulatedRewardBalanceService.saveAccumulatedRewardBalance(accumulatedRewardBalance);
//        arbSet.add(accumulatedRewardBalance);
//
//        //Create Customer Reward Expiry
//        CustomerRewardExpiry customerRewardExpiry = CustomerRewardExpiryFixture.standardRewardExpiry();
//        customerRewardExpiry.setCreLoyaltyId(customer.getCusLoyaltyId());
//        customerRewardExpiry.setCreRewardBalance(500.0);
//        customerRewardExpiry.setCreRewardCurrencyId(rewardCurrency.getRwdCurrencyId());
//        customerRewardExpiry.setCreExpiryDt(DBUtils.covertToSqlDate("9999-12-31"));
//        customerRewardExpiry = customerRewardExpiryService.saveCustomerRewardExpiry(customerRewardExpiry);
//        creSet.add(customerRewardExpiry);

//        CustomerRewardBalance customerRewardBalance = CustomerRewardBalanceFixture.standardRewardBalance();
//        customerRewardBalanceService.saveCustomerRewardBalance(customerRewardBalance);

        useCustomerUser();

        // Place the delete request
        MvcResult mvcResult = mockMvc.perform(get("/api/0.9/json/portal/rewardbalance/1/0")
                .principal(principal)
                .session(session)
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
        )
                .andReturn();

        String response = mvcResult.getResponse().getContentAsString();
        Assert.assertNotNull(response);
        log.info("Transfer points response: " + response);

        // Convert json response to HashMap
        Assert.assertTrue(response.contains("success"));
    }


    @Test
    public void sendRewardBalanceSMSCompatible() throws Exception  {


        useMerchantUser();

        // Create a reward currency
        RewardCurrency rewardCurrency = RewardCurrencyFixture.standardRewardCurrency();

        rewardCurrency = rewardCurrencyService.saveRewardCurrency(rewardCurrency);

        rewardCurrencySet.add(rewardCurrency);

        // Get the set of customerRewardBalances
        CustomerRewardBalance customerRewardBalance = CustomerRewardBalanceFixture.standardRewardBalance();

        customerRewardBalance.setCrbRewardCurrency(rewardCurrency.getRwdCurrencyId());

        customerRewardBalance = customerRewardBalanceService.saveCustomerRewardBalance(customerRewardBalance);

        tempSet.add(customerRewardBalance);


        // Place the delete request
        MvcResult mvcResult = mockMvc.perform(post("/api/0.9/json/loyalty/rewardbalancesms")
                .principal(principal)
                .session(session)
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .param("loyalty_id", customerRewardBalance.getCrbLoyaltyId())
                .param("rwd_currency_id", customerRewardBalance.getCrbRewardCurrency().toString())
        )
                .andReturn();

        String response = mvcResult.getResponse().getContentAsString();

        Assert.assertNotNull(response);

        log.info("sendRewardBalanceSMSCompatibleResponse: " + response);

        // Convert json response to HashMap
        Assert.assertTrue(response.contains("success"));


    }

    @After
    public void tearDown() throws InspireNetzException {


        for (CustomerRewardBalance customerRewardBalance : tempSet ) {

            customerRewardBalanceService.deleteCustomerRewardBalance(customerRewardBalance);

        }

        for(RewardCurrency rewardCurrency : rewardCurrencySet ) {

            rewardCurrencyService.deleteRewardCurrency(rewardCurrency.getRwdCurrencyId());

        }





        for(Tier tier : tierSet ) {

            tierService.deleteTier(tier.getTieId());

        }


        for(TierGroup tierGroup : tierGroupSet ) {

            tierGroupService.deleteTierGroup(tierGroup.getTigId());

        }


        for(TransferPointSetting transferPointSetting : transferPointSettingSet ) {

            transferPointSettingService.deleteTransferPointSetting(transferPointSetting.getTpsId());

        }


        for(Customer customer : customerSet ) {


            List<LinkedLoyalty> linkedLoyaltyList = linkedLoyaltyService.findByLilParentCustomerNo(customer.getCusCustomerNo());

            for ( LinkedLoyalty linkedLoyalty : linkedLoyaltyList ) {

                linkedLoyaltyService.deleteLinkedLoyalty(linkedLoyalty.getLilId());

            }



            List<CustomerRewardBalance> customerRewardBalanceList = customerRewardBalanceService.findByCrbLoyaltyIdAndCrbMerchantNo(customer.getCusLoyaltyId(),customer.getCusMerchantNo());

            for(CustomerRewardBalance customerRewardBalance : customerRewardBalanceList ) {

                customerRewardBalanceService.deleteCustomerRewardBalance(customerRewardBalance);

            }



            List<CustomerRewardExpiry> customerRewardExpiryList = customerRewardExpiryService.findByCreLoyaltyId(customer.getCusLoyaltyId());

            for ( CustomerRewardExpiry customerRewardExpiry : customerRewardExpiryList ) {

                customerRewardExpiryService.deleteCustomerRewardExpiry(customerRewardExpiry);

            }



            PrimaryLoyalty  primaryLoyalty =  primaryLoyaltyService.findByPllLoyaltyId(customer.getCusLoyaltyId());

            if ( primaryLoyalty !=  null )
                primaryLoyaltyService.deletePrimaryLoyalty(primaryLoyalty.getPllId());


            customerService.deleteCustomer(customer.getCusCustomerNo());

        }



        Set<CustomerRewardBalance> customerRewardBalances = CustomerRewardBalanceFixture.standardCustomerRewardBalances();

        for(CustomerRewardBalance customerRewardBalance: customerRewardBalances) {

            customerRewardBalanceService.deleteCustomerRewardBalance(customerRewardBalance);;

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
        for(CustomerSubscription customerSubscription: cusSubSet){

            customerSubscriptionService.deleteCustomerSubscription(customerSubscription.getCsuId());

        }

        for(DrawChance drawChance:drawChancesSet){


            drawChanceService.deleteDrawChance(drawChance.getDrcId());
        }

        for(Customer  customer:customerDrawSet){


            customerService.deleteCustomer(customer.getCusCustomerNo());

        }








    }

}
