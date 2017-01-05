package com.inspirenetz.api.test.core.service;

import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import com.inspirenetz.api.core.dictionary.*;
import com.inspirenetz.api.core.domain.*;
import com.inspirenetz.api.core.loyaltyengine.CatalogueRedemptionVoucherType;
import com.inspirenetz.api.core.repository.RedemptionRepository;
import com.inspirenetz.api.core.service.*;
import com.inspirenetz.api.rest.exception.InspireNetzException;
import com.inspirenetz.api.rest.exception.InspireNetzRollBackException;
import com.inspirenetz.api.test.config.*;
import com.inspirenetz.api.test.core.fixture.*;
import com.inspirenetz.api.test.util.ControllerTestUtils;
import com.inspirenetz.api.util.DBUtils;
import com.inspirenetz.api.util.GeneralUtils;
import org.junit.*;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.*;

/**
 * Created by sandheepgr on 30/4/14.
 */
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes  = {ApplicationTestConfig.class, PersistenceTestConfig.class, ServiceTestConfig.class, SecurityTestConfig.class,IntegrationTestConfig.class})
public class RedemptionServiceTest {


    private static Logger log = LoggerFactory.getLogger(RedemptionServiceTest.class);

    @Autowired
    private RedemptionService redemptionService;

    @Autowired
    private MerchantSettingService merchantSettingService;

    @Autowired
    private CustomerRewardExpiryService customerRewardExpiryService;

    @Autowired
    private CustomerRewardBalanceService customerRewardBalanceService;

    @Autowired
    private CustomerService customerService;

    @Autowired
    private RewardCurrencyService rewardCurrencyService;

    @Autowired
    private RedemptionRepository redemptionRepository;

    @Autowired
    private CustomerSubscriptionService customerSubscriptionService;

    @Autowired
    private RedemptionMerchantService redemptionMerchantService;

    @Autowired
    private AccumulatedRewardBalanceService accumulatedRewardBalanceService;

    @Autowired
    private CatalogueService catalogueService;

    @Autowired
    UserMessagingService userMessagingService;

    @Autowired
    private RedemptionVoucherService redemptionVoucherService;

    @Autowired
    CustomerActivityService customerActivityService;

    @Autowired
    GeneralUtils generalUtils;

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

    UsernamePasswordAuthenticationToken principal;

    @Autowired
    @Qualifier("uds")
    protected UserDetailsService userDetailsService;


    @Before
    public void setUp() {

        // Set the principal
                principal = ControllerTestUtils.getPrincipal(ControllerTestUtils.TEST_MERCHANT_ADMIN_LOGINID, userDetailsService);

        // Set the context
        SecurityContextHolder.getContext().setAuthentication(principal);
    }

    @Test
    public void test1FindByRdmMerchantNoAndRdmUniqueBatchTrackingId() {

        // Get the standard redemption
        Redemption redemption = RedemptionFixture.standardCatalogueRedemption();
        redemptionService.saveRedemption(redemption);


        List<Redemption> redemptions = redemptionService.findByRdmMerchantNoAndRdmUniqueBatchTrackingId(redemption.getRdmMerchantNo(),redemption.getRdmUniqueBatchTrackingId());
        log.info("redemptions by tracking id " + redemptions.toString());
        Set<Redemption> redemptionSet = Sets.newHashSet((Iterable<Redemption>) redemptions);
        log.info("redemption list "+redemptionSet.toString());

    }

    @Test
    public void test2DeductPoints() {

        CustomerRewardBalance customerRewardBalance = CustomerRewardBalanceFixture.standardRewardBalance();
        customerRewardBalanceService.saveCustomerRewardBalance(customerRewardBalance);


        Set<CustomerRewardExpiry> customerRewardExpirySet = CustomerRewardExpiryFixture.standardCustomerRewardExpirys();
        customerRewardExpiryService.saveAll(Lists.newArrayList((Iterable<CustomerRewardExpiry>)customerRewardExpirySet));


        // Get the standard redemption
        Redemption redemption = RedemptionFixture.standardCatalogueRedemption();

        // Save the redemption
        redemption = redemptionService.saveRedemption(redemption);

        List<CustomerRewardExpiry> customerRewardExpiryList = customerRewardExpiryService.getFIFOCustomerExpiryList(redemption.getRdmMerchantNo(),redemption.getRdmLoyaltyId(),redemption.getRdmRewardCurrencyId());

        log.info("Customer Reward Expiry List" + customerRewardExpiryList.toString());

        boolean deduct = redemptionService.deductPoints(redemption,customerRewardExpiryList,100,10L);

        Assert.assertTrue(deduct);
        log.info("Point deduction successful");

    }

    @Test
    public void test3AddRedemptionTransactionEntry() {

        // create the redemption object
        Redemption redemption = RedemptionFixture.standardCatalogueRedemption();
        redemption = redemptionService.saveRedemption(redemption);

        boolean success = redemptionService.addRedemptionTransactionEntry(redemption,1L,100,20);
        Assert.assertTrue(success);
        log.info("Transaction saved");


    }

    @Test
    public void test4RedeemCatalogue() {

        // Get the customerrewardbalance
        CustomerRewardBalance customerRewardBalance = CustomerRewardBalanceFixture.standardRewardBalance();
        customerRewardBalanceService.saveCustomerRewardBalance(customerRewardBalance);


        Set<CustomerRewardExpiry> customerRewardExpirySet = CustomerRewardExpiryFixture.standardCustomerRewardExpirys();
        customerRewardExpiryService.saveAll(Lists.newArrayList((Iterable<CustomerRewardExpiry>)customerRewardExpirySet));


        // Get the standardRedemptionCatalogue
        RedemptionCatalogue redemptionCatalogue = RedemptionFixture.standardRedemptionCatalogue();

        // Create the redemption catalogue
        CatalogueRedemptionRequest catalogueRedemptionRequest = RedemptionFixture.standardCatalogueRedemptionRequest();

        // Get the response
        CatalogueRedemptionItemResponse response = redemptionService.redeemCatalogue(catalogueRedemptionRequest,redemptionCatalogue.getCatProductCode(), redemptionCatalogue.getCatQty());

        // Show the response
        log.info("Response : - " + response.toString());

        // Check the response
        Assert.assertTrue(response.getStatus().equals("success"));


    }

    @Test
    public void test5GetRedemptionCatalogues() {

        // Create the Map with data
        Map<String,String> params = new HashMap<>(0);

        params.put("cat_data[0][prd_no]","8");
        params.put("cat_data[0][prd_code]","PRD1001");
        params.put("cat_data[0][merchant_no]","1");
        params.put("cat_data[0][qty]","8");

        List<RedemptionCatalogue> redemptionCatalogueList = redemptionService.getRedemptionCatalogues(params);

        Assert.assertTrue(!redemptionCatalogueList.isEmpty());
        log.info("Redemptioncatalogues : "+redemptionCatalogueList.toString());


    }

    @Test
    public void test6DoCatalogueRedemption() throws InspireNetzException {

        Customer customer = CustomerFixture.standardCustomer();
        customerService.saveCustomer(customer);

        CustomerRewardBalance customerRewardBalance = CustomerRewardBalanceFixture.standardRewardBalance();
        customerRewardBalanceService.saveCustomerRewardBalance(customerRewardBalance);


        Set<CustomerRewardExpiry> customerRewardExpirySet = CustomerRewardExpiryFixture.standardCustomerRewardExpirys();
        customerRewardExpiryService.saveAll(Lists.newArrayList((Iterable<CustomerRewardExpiry>)customerRewardExpirySet));


        // Create the redemption catalogue
        CatalogueRedemptionRequest catalogueRedemptionRequest = RedemptionFixture.standardCatalogueRedemptionRequest();


        CatalogueRedemptionResponse responseObject = redemptionService.doCatalogueRedemption(catalogueRedemptionRequest);

        log.info("CatalogueRedemptionItemResponse: "+responseObject.toString());

    }

    @Test
    public void test6DoCashbackRedemption() throws InspireNetzException {

        Customer customer = CustomerFixture.standardCustomer();
        customerService.saveCustomer(customer);

        CustomerRewardBalance customerRewardBalance = CustomerRewardBalanceFixture.standardRewardBalance();
        customerRewardBalanceService.saveCustomerRewardBalance(customerRewardBalance);


        Set<CustomerRewardExpiry> customerRewardExpirySet = CustomerRewardExpiryFixture.standardCustomerRewardExpirys();
        customerRewardExpiryService.saveAll(Lists.newArrayList((Iterable<CustomerRewardExpiry>)customerRewardExpirySet));


        // Create the redemption catalogue
        CashBackRedemptionRequest cashbackRedemptionRequest = RedemptionFixture.standardCashbackRedemptionRequest();
        cashbackRedemptionRequest.setAuditDetails("8928#sandeepmenon");

        // Get the reward currency object
        RewardCurrency rewardCurrency = rewardCurrencyService.findByRwdCurrencyId(cashbackRedemptionRequest.getRewardCurrencyId());

        // Set the reward currency
        cashbackRedemptionRequest.setRewardCurrency(rewardCurrency);




        CashBackRedemptionResponse cashbackRedemptionResponse = redemptionService.doCashbackRedemption(cashbackRedemptionRequest);

        Assert.assertTrue(cashbackRedemptionResponse.getStatus().equals("success"));

        log.info("CashbackRedemptionResponse : "+ cashbackRedemptionResponse.toString());


    }

    @Test
    public void test7FindByRdmMerchantNoAndRdmLoyaltyIdAndRdmDateBetween() {

        Set<Redemption> redemptionSet = RedemptionFixture.getStandardRedemptions();
        redemptionRepository.save(redemptionSet);

        Redemption redemption = RedemptionFixture.standardCatalogueRedemption();


        Page<Redemption> redemptionPage = redemptionService.findByRdmMerchantNoAndRdmLoyaltyIdAndRdmDateBetween(redemption.getRdmMerchantNo(),redemption.getRdmLoyaltyId(), DBUtils.covertToSqlDate("2014-05-01"),DBUtils.covertToSqlDate("9999-12-31"),constructPageSpecification(0));
        Assert.assertNotNull(redemptionPage);
        List<Redemption> redemptionList = Lists.newArrayList((Iterable<Redemption>)redemptionPage);
        log.info("Redemption List "+redemptionList.toString());

    }

    @Test
    public void test8FindByRdmMerchantNoAndRdmTypeAndRdmDateBetween() {

        Set<Redemption> redemptionSet = RedemptionFixture.getStandardRedemptions();
        redemptionRepository.save(redemptionSet);

        Redemption redemption = RedemptionFixture.standardCatalogueRedemption();


        Page<Redemption> redemptionPage = redemptionService.findByRdmMerchantNoAndRdmTypeAndRdmDateBetween(redemption.getRdmMerchantNo(),redemption.getRdmType(), DBUtils.covertToSqlDate("2014-05-01"),DBUtils.covertToSqlDate("9999-12-31"),constructPageSpecification(0));
        Assert.assertNotNull(redemptionPage);
        Assert.assertTrue(redemptionPage.hasContent());
        List<Redemption> redemptionList = Lists.newArrayList((Iterable<Redemption>)redemptionPage);
        log.info("Redemption List "+redemptionList.toString());

    }

    @Test
    public void test9DeleteRedemption() throws InspireNetzException {

        // Create the redemption
        Redemption redemption = RedemptionFixture.standardCatalogueRedemption();
        redemption = redemptionService.saveRedemption(redemption);
        Assert.assertNotNull(redemption.getRdmId());
        log.info("Redemption created");

        // call the delete redemption
        redemptionService.deleteRedemption(redemption.getRdmId());

        // Try searching for the redemption
        Redemption checkRedemption  = redemptionService.findByRdmId(redemption.getRdmId());

        Assert.assertNull(checkRedemption);

        log.info("redemption deleted");

    }

    @Test
    public void test10ListRedemptionRequests() {

        Set<Redemption> redemptionSet = RedemptionFixture.getStandardRedemptions();
        redemptionRepository.save(redemptionSet);

        Redemption redemption = RedemptionFixture.standardCatalogueRedemption();

        Page<Redemption> redemptionPage = redemptionService.listRedemptionRequests(redemption.getRdmMerchantNo(), "loyaltyid",redemption.getRdmLoyaltyId(),0, constructPageSpecification(0));
        Assert.assertTrue(redemptionPage.hasContent());
        List<Redemption> redemptionList = Lists.newArrayList((Iterable<Redemption>)redemptionPage);
        log.info("Redemption List "+redemptionList.toString());


    }



    @Test
    public void test10findByRdmMerchantNoAndRdmLoyaltyIdAndRdmTypeAndRdmDateBetween() {

        Set<Redemption> redemptionSet = RedemptionFixture.getStandardRedemptions();
        redemptionRepository.save(redemptionSet);

        Redemption redemption = RedemptionFixture.standardCatalogueRedemption();


        Page<Redemption> redemptionPage = redemptionService.findByRdmMerchantNoAndRdmLoyaltyIdAndRdmTypeAndRdmDateBetween(redemption.getRdmMerchantNo(), redemption.getRdmLoyaltyId(), redemption.getRdmType(), DBUtils.covertToSqlDate("2014-05-01"), DBUtils.covertToSqlDate("9999-12-31"), constructPageSpecification(0));
        Assert.assertNotNull(redemptionPage);
        List<Redemption> redemptionList = Lists.newArrayList((Iterable<Redemption>)redemptionPage);
        log.info("Redemption List "+redemptionList.toString());

    }

    @Test
    public void test11CheckGeneralRulesValidity() throws InspireNetzException {

        //create customer object
        Customer customer = CustomerFixture.standardCustomer();
        customer.setCusLoyaltyId("1234565452");
        customer.setCusTier(1L);
        customer.setCusType(CustomerType.SUBSCRIBER);
        customer.setCusLocation(1L);

        //save the customer
        customer = customerService.saveCustomer(customer);
        customerSet.add(customer);

        if(customer !=null && customer.getCusCustomerNo() != null){

            log.info("Customer Object Saved"+customer);

        }
        log.info("Customer Object Saved"+customer);

        //create customer subscription object
        CustomerSubscription customerSubscription = CustomerSubscriptionFixture.standardCustomerSubscription();
        customerSubscription.setCsuCustomerNo(customer.getCusCustomerNo());
        customerSubscription.setCsuProductCode("PRD1000");

        //save customer subscription object
        customerSubscription = customerSubscriptionService.saveCustomerSubscription( customerSubscription);
        tempSet.add(customerSubscription);

        if(customerSubscription !=null && customerSubscription.getCsuId() != null){

            log.info("Customer Subscription Saved" + customerSubscription);

        }



        Catalogue catalogue  = CatalogueFixture.standardCatalogue();

        catalogue.setCatCustomerTier("1,2");
        catalogue.setCatCustomerType(0);
        catalogue.setCatStartDate((DBUtils.covertToSqlDate("2014-09-01")));
        catalogue.setCatEndDate((DBUtils.covertToSqlDate("2014-11-30")));
        catalogue.setCatLocationValues("3,2,1");

        catalogue.setCatProductValues("PRD1001,PRD1000");

        //saving catalogue object
        catalogue= catalogueService.saveCatalogue(catalogue);
        catalogueSet.add(catalogue);

        if(catalogue !=null && catalogue.getCatProductNo() != null){

            log.info("Catalogue Saved" + catalogue);

        }

        CatalogueRedemptionItemRequest catalogueRedemptionItemRequest = redemptionService.getRedemptionRequestObject(customer,catalogue);

        boolean isValid = redemptionService.checkGeneralRulesValidity(catalogueRedemptionItemRequest);
        Assert.assertTrue(isValid);
    }

    @Test
    public void test12RedeemCatalogueSingleItem() throws InspireNetzException {

        //Create Customer object and save
        Customer customer = CustomerFixture.standardCustomer();
        customer.setCusLoyaltyId("9742375875");
        customer = customerService.saveCustomer(customer);
        cusSet.add(customer);

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
        customerRewardExpiryService.saveCustomerRewardExpiry(customerRewardExpiry);
        creSet.add(customerRewardExpiry);

        RedemptionMerchant redemptionMerchant = RedemptionMerchantFixture.standardRedemptionMerchant();
        redemptionMerchant = redemptionMerchantService.saveRedemptionMerchant(redemptionMerchant);
        remSet.add(redemptionMerchant);

        Catalogue catalogue = CatalogueFixture.standardCatalogue();
        /*catalogue.setCatRedemptionMerchant(redemptionMerchant.getRemNo());*/
        catalogue.setCatRewardCurrencyId(rewardCurrency.getRwdCurrencyId());
        catalogue.setCatAvailableStock(0L);
        catalogue.setCatStartDate(DBUtils.covertToSqlDate("2014-01-01"));
        catalogue.setCatEndDate(DBUtils.covertToSqlDate("2014-12-01"));
        catalogueService.saveCatalogue(catalogue);
        catSet.add(catalogue);

        // Get the standardRedemptionCatalogue
        CatalogueRedemptionItemRequest redemptionCatalogue = new CatalogueRedemptionItemRequest();
        redemptionCatalogue.setUsrFName("Saneesh");
        redemptionCatalogue.setPrdCode("S");
        redemptionCatalogue.setPrdCode(catalogue.getCatProductCode());
        redemptionCatalogue.setUserLocation(customer.getCusLocation());
        redemptionCatalogue.setLoyaltyId(customer.getCusLoyaltyId());
        redemptionCatalogue.setMerchantNo(customer.getCusMerchantNo());
        redemptionCatalogue.setQty(1);

        // Get the response
        CatalogueRedemptionItemResponse response = redemptionService.redeemCatalogueItems(redemptionCatalogue);

        // Show the response
        log.info("Response : - " + response.toString());
/*

        // Check the response
        Assert.assertTrue(response.getStatus().equals("success"));
*/


    }

    @Test
    public void test13deductPoints() throws InspireNetzException, InspireNetzRollBackException {

        //Create Customer object and save
        Customer customer = CustomerFixture.standardCustomer();
        customer.setCusLoyaltyId("9742375875");
        customer = customerService.saveCustomer(customer);
        cusSet.add(customer);

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
        customerRewardExpiryService.saveCustomerRewardExpiry(customerRewardExpiry);
        creSet.add(customerRewardExpiry);

        RedemptionMerchant redemptionMerchant = RedemptionMerchantFixture.standardRedemptionMerchant();
        redemptionMerchant = redemptionMerchantService.saveRedemptionMerchant(redemptionMerchant);
        remSet.add(redemptionMerchant);

        Catalogue catalogue = CatalogueFixture.standardCatalogue();
        catalogue.setCatRedemptionMerchant(redemptionMerchant.getRemNo());
        catalogue.setCatRewardCurrencyId(rewardCurrency.getRwdCurrencyId());
        catalogueService.saveCatalogue(catalogue);
        catSet.add(catalogue);

        // Get the standardRedemptionCatalogue
        CatalogueRedemptionItemRequest redemptionCatalogue = new CatalogueRedemptionItemRequest();
        redemptionCatalogue.setUsrFName("Saneesh");
        redemptionCatalogue.setPrdCode("S");
        redemptionCatalogue.setPrdCode(catalogue.getCatProductCode());
        redemptionCatalogue.setUserLocation(customer.getCusLocation());
        redemptionCatalogue.setLoyaltyId(customer.getCusLoyaltyId());
        redemptionCatalogue.setMerchantNo(customer.getCusMerchantNo());
        redemptionCatalogue.setQty(1);

        CatalogueRedemptionVoucherType catalogueRedemptionVoucherType = new CatalogueRedemptionVoucherType(redemptionService,redemptionMerchantService,redemptionVoucherService,userMessagingService,catalogueService,generalUtils,merchantSettingService);

        CatalogueRedemptionItemRequest catalogueRedemptionItemRequest = redemptionService.getRedemptionRequestObject(customer,catalogue);

        catalogueRedemptionVoucherType.redeemPoints(catalogueRedemptionItemRequest);

        log.info("Successfully redeemed Points");

    }

    @Test
    public void test14isRequestValid() throws InspireNetzException {

        //create customer object
        Customer customer = CustomerFixture.standardCustomer();
        customer.setCusLoyaltyId("9742375895");
        customer.setCusTier(1L);
        customer.setCusType(CustomerType.SUBSCRIBER);
        customer.setCusLocation(1L);

        //save the customer
        customer = customerService.saveCustomer(customer);
        customerSet.add(customer);

        if(customer !=null && customer.getCusCustomerNo() != null){

            log.info("Customer Object Saved"+customer);

        }
        log.info("Customer Object Saved"+customer);

        //create customer subscription object
        CustomerSubscription customerSubscription = CustomerSubscriptionFixture.standardCustomerSubscription();
        customerSubscription.setCsuCustomerNo(customer.getCusCustomerNo());
        customerSubscription.setCsuProductCode("PRD1000");

        //save customer subscription object
        customerSubscription = customerSubscriptionService.saveCustomerSubscription( customerSubscription);
        tempSet.add(customerSubscription);

        if(customerSubscription !=null && customerSubscription.getCsuId() != null){

            log.info("Customer Subscription Saved" + customerSubscription);

        }



        Catalogue catalogue  = CatalogueFixture.standardCatalogue();

        catalogue.setCatCustomerTier("1,2");
        catalogue.setCatCustomerType(CustomerType.SUBSCRIBER);
        catalogue.setCatStartDate((DBUtils.covertToSqlDate("2014-09-01")));
        catalogue.setCatEndDate((DBUtils.covertToSqlDate("2014-09-30")));
        catalogue.setCatLocationValues("3,2,1");
        catalogue.setCatProductValues("PRD1001,PRD1000");

        //saving catalogue object
        catalogue= catalogueService.saveCatalogue(catalogue);
        catalogueSet.add(catalogue);

        if(catalogue !=null && catalogue.getCatProductNo() != null){

            log.info("Catalogue Saved" + catalogue);

        }

        CatalogueRedemptionVoucherType catalogueRedemptionVoucherType = new CatalogueRedemptionVoucherType(redemptionService,redemptionMerchantService,redemptionVoucherService,userMessagingService,catalogueService,generalUtils,merchantSettingService);

        CatalogueRedemptionItemRequest catalogueRedemptionItemRequest = redemptionService.getRedemptionRequestObject(customer,catalogue);

        boolean isValid = catalogueRedemptionVoucherType.isRequestValid(catalogueRedemptionItemRequest);

        Assert.assertTrue(isValid);
    }

    @Test
    public void test14redeemCatalogueItems() throws InspireNetzException {

        //Create Customer object and save
        Customer customer = CustomerFixture.standardCustomer();
        customer.setCusLoyaltyId("9742375890");
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
        customerRewardExpiryService.saveCustomerRewardExpiry(customerRewardExpiry);
        creSet.add(customerRewardExpiry);

        RedemptionMerchant redemptionMerchant = RedemptionMerchantFixture.standardRedemptionMerchant();
        redemptionMerchant = redemptionMerchantService.saveRedemptionMerchant(redemptionMerchant);
        remSet.add(redemptionMerchant);

        Catalogue catalogue = CatalogueFixture.standardCatalogue();
        catalogue.setCatRedemptionMerchant(redemptionMerchant.getRemNo());
        catalogue.setCatRewardCurrencyId(rewardCurrency.getRwdCurrencyId());
        catalogue.setCatRedemptionType(CatalogueRedemptionType.RAFFLES_TICKET);
        catalogue.setCatCustomerTier("1,2");
        catalogue.setCatCustomerType(CustomerType.SUBSCRIBER);
        catalogue.setCatStartDate((DBUtils.covertToSqlDate("2014-11-01")));
        catalogue.setCatEndDate((DBUtils.covertToSqlDate("2014-11-30")));
        catalogue.setCatLocationValues("");
        catalogue.setCatProductValues("");
        catalogue.setCatExtReference("10");
        catalogue.setCatAvailableStock(10L);
        catalogueService.saveCatalogue(catalogue);
        catSet.add(catalogue);

        // Get the standardRedemptionCatalogue
        CatalogueRedemptionItemRequest redemptionCatalogue = new CatalogueRedemptionItemRequest();
        redemptionCatalogue.setUsrFName("Saneesh");
        redemptionCatalogue.setPrdCode("S");
        redemptionCatalogue.setPrdCode(catalogue.getCatProductCode());
        redemptionCatalogue.setUserLocation(customer.getCusLocation());
        redemptionCatalogue.setLoyaltyId(customer.getCusLoyaltyId());
        redemptionCatalogue.setMerchantNo(customer.getCusMerchantNo());
        redemptionCatalogue.setQty(1);


        // Get the response
        CatalogueRedemptionItemResponse rewardBalance = redemptionService.redeemCatalogueItems(redemptionCatalogue);

        if(null != rewardBalance){

            // Show the response
            log.info("Response : - " + rewardBalance);
        }


        // Check the response
        Assert.assertNotNull(rewardBalance);


    }

    @Test
    public void test15redeemCatalogueItems() throws InspireNetzException {

        //Create Customer object and save
        Customer customer = CustomerFixture.standardCustomer();
        customer.setCusLoyaltyId("9742375890");
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
        customerRewardExpiryService.saveCustomerRewardExpiry(customerRewardExpiry);
        creSet.add(customerRewardExpiry);

        RedemptionMerchant redemptionMerchant = RedemptionMerchantFixture.standardRedemptionMerchant();
        redemptionMerchant = redemptionMerchantService.saveRedemptionMerchant(redemptionMerchant);
        remSet.add(redemptionMerchant);

        Catalogue catalogue = CatalogueFixture.standardCatalogue();
        catalogue.setCatRedemptionMerchant(redemptionMerchant.getRemNo());
        catalogue.setCatRewardCurrencyId(rewardCurrency.getRwdCurrencyId());
        catalogue.setCatRedemptionType(CatalogueRedemptionType.VOUCHER_BASED);
        catalogue.setCatCustomerTier("");
        catalogue.setCatCustomerType(CustomerType.SUBSCRIBER);
        catalogue.setCatStartDate((DBUtils.covertToSqlDate("2014-11-01")));
        catalogue.setCatEndDate((DBUtils.covertToSqlDate("2014-12-30")));
        catalogue.setCatLocationValues("");
        catalogue.setCatProductValues("");
        catalogue.setCatExtReference("10");
        catalogue.setCatAvailableStock(10L);
        catalogueService.saveCatalogue(catalogue);
        catSet.add(catalogue);

        // Get the standardRedemptionCatalogue
        CatalogueRedemptionItemRequest redemptionCatalogue = new CatalogueRedemptionItemRequest();
        redemptionCatalogue.setUsrFName("Saneesh");
        redemptionCatalogue.setPrdCode("S");
        redemptionCatalogue.setPrdCode(catalogue.getCatProductCode());
        redemptionCatalogue.setUserLocation(customer.getCusLocation());
        redemptionCatalogue.setLoyaltyId(customer.getCusLoyaltyId());
        redemptionCatalogue.setMerchantNo(customer.getCusMerchantNo());
        redemptionCatalogue.setQty(1);
        // Get the response
        CatalogueRedemptionItemResponse rewardBalance = redemptionService.redeemCatalogueItems(redemptionCatalogue);

        if(null != rewardBalance){

            // Show the response
            log.info("Response : - " + rewardBalance);
        }

        //get the customer activities
        Page<CustomerActivity> customerActivities = customerActivityService.searchCustomerActivities(customer.getCusLoyaltyId(), CustomerActivityType.REDEMPTION, java.sql.Date.valueOf("2014-01-01"), java.sql.Date.valueOf("2014-12-12"), customer.getCusMerchantNo(), constructPageSpecification(0));

        Assert.assertTrue(customerActivities.hasContent());
        // Check the response
        Assert.assertNotNull(rewardBalance);


    }



    @Test
    public void testBcodRedemption() throws InspireNetzException {

        // Get the standardRedemptionCatalogue
        CatalogueRedemptionItemRequest redemptionCatalogue = new CatalogueRedemptionItemRequest();
        redemptionCatalogue.setPrdCode("BCODE01");
        redemptionCatalogue.setLoyaltyId("9538828853");
        redemptionCatalogue.setMerchantNo(1L);
        redemptionCatalogue.setQty(1);
        redemptionCatalogue.setDestLoyaltyId("0");

        redemptionService.redeemCatalogueItems(redemptionCatalogue);

    }

    @Test
    public void testUregRedemption() throws InspireNetzException {

        CatalogueRedemptionItemRequest redemptionCatalogue = new CatalogueRedemptionItemRequest();
        redemptionCatalogue.setPrdCode("SURF");
        redemptionCatalogue.setLoyaltyId("9538828853");
        redemptionCatalogue.setMerchantNo(1L);
        redemptionCatalogue.setQty(1);
        redemptionCatalogue.setDestLoyaltyId("0");


        redemptionService.redeemCatalogueItems(redemptionCatalogue);

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



        Set<CustomerRewardBalance> customerRewardBalances = CustomerRewardBalanceFixture.standardCustomerRewardBalances();

        for(CustomerRewardBalance customerRewardBalance: customerRewardBalances) {

            customerRewardBalanceService.deleteCustomerRewardBalance(customerRewardBalance);;

        }



        Set<CustomerRewardExpiry> customerRewardExpirys = CustomerRewardExpiryFixture.standardCustomerRewardExpirys();

        for(CustomerRewardExpiry customerRewardExpiry: customerRewardExpirys) {

            customerRewardExpiryService.deleteCustomerRewardExpiry(customerRewardExpiry);

        }

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

    }

    /**
     * Returns a new object which specifies the the wanted result page.
     * @param pageIndex The index of the wanted result page
     * @return
     */
    private Pageable constructPageSpecification(int pageIndex) {
        Pageable pageSpecification = new PageRequest(pageIndex, 10);
        return pageSpecification;
    }

    /**
     * Returns a Sort object which sorts persons in ascending order by using the last name.
     * @return
     */
    private Sort sortByLastNameAsc() {
        return new Sort(Sort.Direction.DESC, "rdmId");
    }

}
