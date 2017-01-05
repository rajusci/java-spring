package com.inspirenetz.api.test.core.service;

import com.google.common.collect.Lists;
import com.inspirenetz.api.core.dictionary.*;
import com.inspirenetz.api.core.domain.*;
import com.inspirenetz.api.core.incustomization.attrext.AttributeExtendedEntityMap;
import com.inspirenetz.api.core.loyaltyengine.LoyaltyComputation;
import com.inspirenetz.api.core.loyaltyengine.LoyaltyComputationTransactionAmount;
import com.inspirenetz.api.test.config.*;
import com.inspirenetz.api.test.util.ControllerTestUtils;
import com.inspirenetz.api.util.*;
import com.inspirenetz.api.core.repository.LoyaltyProgramRepository;
import com.inspirenetz.api.core.service.*;
import com.inspirenetz.api.rest.exception.InspireNetzException;
import com.inspirenetz.api.test.core.fixture.*;
import org.drools.KnowledgeBase;
import org.drools.KnowledgeBaseFactory;
import org.drools.builder.KnowledgeBuilder;
import org.drools.builder.KnowledgeBuilderFactory;
import org.drools.builder.ResourceType;
import org.drools.io.ResourceFactory;
import org.drools.runtime.StatefulKnowledgeSession;
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

import java.io.File;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Created by sandheepgr on 30/4/14.
 */
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes  = {ApplicationTestConfig.class, PersistenceTestConfig.class, ServiceTestConfig.class, SecurityTestConfig.class, NotificationTestConfig.class})
public class LoyaltyEngineServiceTest {


    private static Logger log = LoggerFactory.getLogger(LoyaltyEngineServiceTest.class);

    @Autowired
    private CustomerRewardBalanceService customerRewardBalanceService;

    @Autowired
    private CustomerRewardExpiryService customerRewardExpiryService;

    @Autowired
    private MerchantRewardSummaryService merchantRewardSummaryService;

    @Autowired
    private RewardCurrencyService rewardCurrencyService;

    @Autowired
    private CustomerService customerService;

    @Autowired
    private CustomerSubscriptionService customerSubscriptionService;

    @Autowired
    private CustomerProfileService customerProfileService;

    @Autowired
    private LoyaltyEngineService loyaltyEngineService;

    @Autowired
    private MerchantProgramSummaryService  merchantProgramSummaryService;

    @Autowired
    private CustomerProgramSummaryService customerProgramSummaryService;

    @Autowired
    private CustomerSummaryArchiveService customerSummaryArchiveService;

    @Autowired
    private LoyaltyProgramService loyaltyProgramService;

    @Autowired
    private LoyaltyProgramSkuService loyaltyProgramSkuService;

    @Autowired
    private ProductService productService;

    @Autowired
    private LoyaltyProgramUtil loyaltyProgramUtil;

    @Autowired
    private LinkedRewardBalanceService linkedRewardBalanceService;

    @Autowired
    private AccumulatedRewardBalanceService accumulatedRewardBalanceService;

    @Autowired
    private LinkedLoyaltyService linkedLoyaltyService;

    @Autowired
    private LoyaltyEngineUtils loyaltyEngineUtils;

    @Autowired
    private GeneralUtils generalUtils;

    @Autowired
    private PrimaryLoyaltyService primaryLoyaltyService;

    @Autowired
    private AccountBundlingSettingService accountBundlingSettingService;

    @Autowired
    private SaleService saleService;

    @Autowired
    private SaleSKUService saleSKUService;

    @Autowired
    private AttributeService attributeService;

    @Autowired
    private TierGroupService tierGroupService;

    @Autowired
    private TierService tierService;

    @Autowired
    private LoyaltyExtensionService loyaltyExtensionService;

    @Autowired
    private CustomerRewardActivityService customerRewardActivityService;

    @Autowired
    private PromotionalEventService promotionalEventService;

    @Autowired
    CustomerPromotionalEventService customerPromotionalEventService;

    @Autowired
    private MerchantService merchantService;

    @Autowired
    MerchantSettingService merchantSettingService;

    @Autowired
    private AuthSessionUtils authSessionUtils;

    @Autowired
    CustomerReferralService customerReferralService;

    Set<Customer> customerSet = new HashSet<>(0);

    Set<CustomerProfile> customerProfileSet = new HashSet<>(0);

    Set<Sale> saleSet   = new HashSet<>(0);

    Set<AccountBundlingSetting> accountBundlingSettingSet =  new HashSet<>(0);

    Set<RewardCurrency> rewardCurrencySet = new HashSet<>(0);

    Set<TierGroup> tierGroupSet  = new HashSet<>(0);

    Set<Tier> tierSet = new HashSet<>(0);

    Set<LoyaltyExtension> loyaltyExtensionSet = new HashSet<>(0);

    Set<CustomerRewardActivity> customerRewardActivitySet = new HashSet<>(0);

    Set<PromotionalEvent> promotionalEvents = new HashSet<>(0);

    Set<CustomerPromotionalEvent> customerPromotionalSet = new HashSet<>(0);

    Set<CustomerReferral> customerReferralSet = new HashSet<>(0);

    Set<Product> productSet = new HashSet<>(0);

    Set<Merchant> merchantSet = new HashSet<>(0);

    Set<MerchantSetting > merchantSettings = new HashSet<>(0);


    // TODO - add the repository method to service ( in tear down) and remove the repository
    @Autowired
    private LoyaltyProgramRepository loyaltyProgramRepository;

    Set<LoyaltyProgram> loyaltyProgramSet = new HashSet<>(0);


    private Sale sale;

    private Customer customer;

    UsernamePasswordAuthenticationToken principal;

    @Autowired
    @Qualifier("uds")
    protected UserDetailsService userDetailsService;

    @Before
    public void setUp() throws InspireNetzException {

        sale = SaleFixture.standardSale();

        customer = CustomerFixture.standardCustomer();


        // Set the principal
        principal = ControllerTestUtils.getPrincipal(ControllerTestUtils.TEST_MERCHANT_ADMIN_LOGINID, userDetailsService);

        // Set the context
        SecurityContextHolder.getContext().setAuthentication(principal);


    }


    @Test
    public void testGetPointRewardDataObjectForTransaction() {

        // Get the PointRewardData
        PointRewardData pointRewardData =  loyaltyEngineService.getPointRewardDataObjectForTransaction(sale);
        Assert.assertNotNull(pointRewardData);
        log.info("PointRewardData : " + pointRewardData.toString());

    }

    @Test
    public void testUpdateMerchantProgramSummary() {

        MerchantProgramSummary merchantProgramSummary = MerchantProgramSummaryFixture.standardMerchantProgramSummary();

        // Get the PointRewardData
        PointRewardData pointRewardData =  loyaltyEngineService.getPointRewardDataObjectForTransaction(sale);
        pointRewardData.setProgramId(merchantProgramSummary.getMpsProgramId());
        Assert.assertNotNull(pointRewardData);
        log.info("PointRewardData : " + pointRewardData.toString());



        boolean updated = loyaltyEngineService.updateMerchantProgramSummary(pointRewardData);
        Assert.assertTrue(updated);
        log.info("Merchant Program Summary Updated");

    }

    @Test
    public void testUpdateCustomerRewardBalance() {

        CustomerRewardBalance customerRewardBalance = CustomerRewardBalanceFixture.standardRewardBalance();

        // Get the PointRewardData
        PointRewardData pointRewardData =  loyaltyEngineService.getPointRewardDataObjectForTransaction(sale);
        pointRewardData.setRwdCurrencyId(customerRewardBalance.getCrbRewardCurrency());
        Assert.assertNotNull(pointRewardData);
        log.info("PointRewardData : " + pointRewardData.toString());

        boolean updated = loyaltyEngineService.updateCustomerRewardBalance(pointRewardData);
        Assert.assertTrue(updated);
        log.info("CustomerRewardbalance Updated");

    }

    @Test
    public void testUpdateCustomerRewardExpiry() {

        CustomerRewardExpiry customerRewardExpiry = CustomerRewardExpiryFixture.standardRewardExpiry();

        // Get the PointRewardData
        PointRewardData pointRewardData =  loyaltyEngineService.getPointRewardDataObjectForTransaction(sale);
        pointRewardData.setRwdCurrencyId(customerRewardExpiry.getCreRewardCurrencyId());
        Assert.assertNotNull(pointRewardData);
        log.info("PointRewardData : " + pointRewardData.toString());

        boolean updated = loyaltyEngineService.updateCustomerRewardExpiry(pointRewardData);
        Assert.assertTrue(updated);
        log.info("CustomerRewardExpiry Updated");

    }

    @Test
    public void testUpdateMerchantRewardSummary() {


        MerchantRewardSummary merchantRewardSummary = MerchantRewardSummaryFixture.standardMerchantRewardSummary();

        // Get the PointRewardData
        PointRewardData pointRewardData =  loyaltyEngineService.getPointRewardDataObjectForTransaction(sale);
        pointRewardData.setRwdCurrencyId(merchantRewardSummary.getMrsCurrencyId());
        pointRewardData.setTxnLocation(merchantRewardSummary.getMrsBranch());
        pointRewardData.setTxnDate(merchantRewardSummary.getMrsDate());

        Assert.assertNotNull(pointRewardData);
        log.info("PointRewardData : " + pointRewardData.toString());

        boolean updated = loyaltyEngineService.updateMerchantRewardSummary(pointRewardData);
        Assert.assertTrue(updated);
        log.info("Merchant REward Summary Updated");

    }

    @Test
    public void testUpdateCustomerProgramSummary() {


        CustomerProgramSummary customerProgramSummary = CustomerProgramSummaryFixture.standardCustomerProgramSummary();

        // Get the PointRewardData
        PointRewardData pointRewardData =  loyaltyEngineService.getPointRewardDataObjectForTransaction(sale);
        pointRewardData.setProgramId(customerProgramSummary.getCpsProgramId());

        Assert.assertNotNull(pointRewardData);
        log.info("PointRewardData : " + pointRewardData.toString());

        boolean updated = loyaltyEngineService.updateCustomerProgramSummary(pointRewardData);
        Assert.assertTrue(updated);
        log.info("Customer Program Summary Updated");

    }

    @Test
    public void testUpdateCustomerSummaryArchive() {


        CustomerSummaryArchive customerSummaryArchive = CustomerSummaryArchiveFixture.standardCustomerSummaryArchive();

        // Get the PointRewardData
        PointRewardData pointRewardData =  loyaltyEngineService.getPointRewardDataObjectForTransaction(sale);
        pointRewardData.setTxnLocation(customerSummaryArchive.getCsaLocation());

        Assert.assertNotNull(pointRewardData);
        log.info("PointRewardData : " + pointRewardData.toString());

        boolean updated = loyaltyEngineService.updateCustomerSummaryArchive(pointRewardData);
        Assert.assertTrue(updated);
        log.info("Customer Summary Archive Updated");

    }

    @Test
    public void testUpdateRewardTables() {

        MerchantProgramSummary merchantProgramSummary = MerchantProgramSummaryFixture.standardMerchantProgramSummary();
        CustomerSummaryArchive customerSummaryArchive = CustomerSummaryArchiveFixture.standardCustomerSummaryArchive();
        CustomerRewardBalance customerRewardBalance = CustomerRewardBalanceFixture.standardRewardBalance();
        MerchantRewardSummary merchantRewardSummary = MerchantRewardSummaryFixture.standardMerchantRewardSummary();

        // Get the PointRewardData
        PointRewardData pointRewardData =  loyaltyEngineService.getPointRewardDataObjectForTransaction(sale);
        pointRewardData.setTxnLocation(customerSummaryArchive.getCsaLocation());
        pointRewardData.setProgramId(merchantProgramSummary.getMpsProgramId());
        pointRewardData.setRwdCurrencyId(customerRewardBalance.getCrbRewardCurrency());
        pointRewardData.setTxnDate(merchantRewardSummary.getMrsDate());

        Assert.assertNotNull(pointRewardData);
        log.info("PointRewardData : " + pointRewardData.toString());

        boolean updated = loyaltyEngineService.updateRewardTables(pointRewardData);
        Assert.assertTrue(updated);
        log.info("Customer Summary Archive Updated");

    }

    @Test
    public void testAddTransaction() {

        Transaction transaction = TransactionFixture.standardTransaction();
        boolean saved = loyaltyEngineService.addTransaction(transaction);
        Assert.assertTrue(saved);
        log.info("Transaction added succesfully");

    }

    @Test
    public void testAddNotifications() {

        // Save the PointRewardData
        PointRewardData pointRewardData =  loyaltyEngineService.getPointRewardDataObjectForTransaction(sale);
        Assert.assertNotNull(pointRewardData);
        log.info("PointRewardData : " + pointRewardData.toString());

        pointRewardData.setUserNo(12L);
        pointRewardData.setUsrFName("Sandeep");
        pointRewardData.setUsrLName("Menon");
        pointRewardData.setUsrProfilePic(ImagePrimaryId.PRIMARY_DEFAULT_IMAGE);

        loyaltyEngineService.addNotifications(pointRewardData);


    }

    @Test
    public void testIsProgramGeneralRulesValid() throws InspireNetzException {

        // Create the customer
        customer.setCusTier(10L);
        customer = customerService.saveCustomer(customer);

        // Add to the customerSet
        customerSet.add(customer);



        // Get the default LoyaltyProgram
        LoyaltyProgram loyaltyProgram = LoyaltyProgramFixture.standardLoyaltyProgram();
        loyaltyProgram.setPrgEligibleCustType(LoyaltyProgramEligibleCustomerType.SPECIFIC_TIER);
        loyaltyProgram.setPrgEligibleCusTier(10L);
        boolean isValid = loyaltyEngineService.isProgramGeneralRulesValid(loyaltyProgram, sale);
        Assert.assertTrue(isValid);
        log.info("Program is valid");

    }

    @Test
    public void testProcessProgramRewarding() throws InspireNetzException {

        // Save the PointRewardData
        PointRewardData pointRewardData =  loyaltyEngineService.getPointRewardDataObjectForTransaction(sale);
        Assert.assertNotNull(pointRewardData);
        log.info("PointRewardData : " + pointRewardData.toString());

        // Get the standard loyalty program
        LoyaltyProgram loyaltyProgram = LoyaltyProgramFixture.standardLoyaltyProgram();
        loyaltyProgram = loyaltyProgramService.saveLoyaltyProgram(loyaltyProgram);

        // Check the value
        boolean success = loyaltyEngineService.processProgramRewarding(loyaltyProgram, sale, pointRewardData);
        Assert.assertTrue(success);


        log.info("processing completed");

    }

    @Test
    public void testProcessTransaction() throws InspireNetzException {

        // Get the standard loyalty program
        LoyaltyProgram loyaltyProgram = LoyaltyProgramFixture.standardLoyaltyProgram();
        loyaltyProgram = loyaltyProgramService.saveLoyaltyProgram(loyaltyProgram);

        boolean isSuccess = loyaltyEngineService.processTransaction(sale);
        Assert.assertTrue(isSuccess);
        log.info("Processing completed for the sale");

    }

    @Test
    public void testProductBasedTransaction() throws InspireNetzException {

        TierGroup tierGroup = TierGroupFixture.standardTierGroup();
        tierGroup.setTigName("groupTest3");
        tierGroup.setTigMerchantNo(3L);
        tierGroup = tierGroupService.saveTierGroup(tierGroup);



        // Add to the tierGroupSet
        tierGroupSet.add(tierGroup);

        Tier tier = TierFixture.standardTier();
        tier.setTieName("Smart-Prepaid-Gold4");
        tier.setTieParentGroup(tierGroup.getTigId());

        tier = tierService.saveTier(tier);

        // Add to the tierSEt
        tierSet.add(tier);



        // Create the customer
        Customer customer = CustomerFixture.standardCustomer();
        customer.setCusMerchantNo(3L);
        customer.setCusTier(tier.getTieId());
        customer = customerService.saveCustomer(customer);

        // Add to customerset
        customerSet.add(customer);


        // SEt the sale to sku type
        sale.setSalType(SaleType.ITEM_BASED_PURCHASE);
        sale.setSalPaymentReference(new Date().getTime() + "");
        sale.setSalLoyaltyId(customer.getCusLoyaltyId());
        sale.setSalMerchantNo(3L);
        sale = saleService.saveSale(sale);



        // Add to saleSet
        saleSet.add(sale);

        // GEt the product
        Product product = ProductFixture.standardProduct();
        product.setPrdName("product45");
        product.setPrdMerchantNo(3L);
        product = productService.saveProduct(product);
        Assert.assertNotNull(product);;
        log.info("Product Information SAved");

        productSet.add(product);


        // Create a customer subscription
        CustomerSubscription customerSubscription = CustomerSubscriptionFixture.standardCustomerSubscription();
        customerSubscription.setCsuCustomerNo(customer.getCusCustomerNo());
        customerSubscription.setCsuProductCode(product.getPrdCode());
        customerSubscription.setCsuMerchantNo(3L);
        customerSubscription = customerSubscriptionService.saveCustomerSubscription(customerSubscription);


        // Get the standardSaleSKU
        SaleSKU saleSKU = SaleSKUFixture.standardSaleSku();
        saleSKU.setSsuSaleId(sale.getSalId());
        saleSKU.setSsuPrice(1530.0);
        saleSKU.setSsuProductCode(product.getPrdCode());
        saleSKU = saleSKUService.saveSaleSku(saleSKU);
        Assert.assertNotNull(saleSKU);
        log.info("Sale sku information saved");


        LoyaltyProgram  loyaltyProgram =  LoyaltyProgramFixture.standardLoyaltyProgram();
        loyaltyProgram.setPrgProgramName("Referrassl Test Program");
        loyaltyProgram.setPrgProgramDriver(LoyaltyProgramDriver.PRODUCT_BASED);
        loyaltyProgram.setPrgRole(LoyaltyRefferalRoles.REFFERRER);
        loyaltyProgram.setPrgMerchantNo(3L);



        //  Create the LoyaltyProgramSku with custom date
        LoyaltyProgramSku loyaltyProgramSku = LoyaltyProgramSkuFixture.standardLoyaltyProgramSku();
        loyaltyProgramSku.setLpuItemCode(product.getPrdCode());
        loyaltyProgramSku.setLpuTier(tier.getTieId());
        loyaltyProgramSku.setLpuItemType(LoyaltyProgramSkuType.PRODUCT);
        loyaltyProgramSku.setLpuPrgRatioNum(20);
        loyaltyProgramSku.setLpuPrgRatioDeno(100);

        // Add to a set
        Set<LoyaltyProgramSku> loyaltyProgramSkuSet = new HashSet<>(0);
        loyaltyProgramSkuSet.add(loyaltyProgramSku);

        // Add to the LoyaltyProgram object
        loyaltyProgram.setLoyaltyProgramSkuSet(loyaltyProgramSkuSet);


        // SAve the loyaltyProgram
        loyaltyProgram = loyaltyProgramService.saveLoyaltyProgram(loyaltyProgram);
        Assert.assertNotNull(loyaltyProgram.getPrgProgramNo());



        log.info("Loyalty Program SAved" + loyaltyProgram.toString());

        // Save the PointRewardData
        PointRewardData pointRewardData =  loyaltyEngineService.getPointRewardDataObjectForTransaction(sale);
        pointRewardData.setMerchantNo(3L);
        Assert.assertNotNull(pointRewardData);
        log.info("PointRewardData : " + pointRewardData.toString());

        //add referrer customer
        Customer customerReferrer = CustomerFixture.standardCustomer();
        customerReferrer.setCusMerchantNo(3L);
        customerReferrer.setCusLoyaltyId("123321");
        customerReferrer.setCusFName("testdt");
        customerReferrer.setCusTier(tier.getTieId());
        customerReferrer = customerService.saveCustomer(customerReferrer);

        customerSet.add(customerReferrer);


        //add customer referral information
        CustomerReferral customerReferral =CustomerReferralFixture.standardCustomerReferral();
        customerReferral.setCsrMerchantNo(3L);
        customerReferral.setCsrRefMobile(sale.getSalLoyaltyId());
        customerReferral.setCsrLoyaltyId(customerReferrer.getCusLoyaltyId());
        customerReferral.setCsrFName("testdt");
        customerReferralService.saveCustomerReferral(customerReferral);

        customerReferralSet.add(customerReferral);

        // Check the value
        boolean success = loyaltyEngineService.processProgramRewarding(loyaltyProgram,sale,pointRewardData);
        Assert.assertTrue(success);

    }

    @Test
    public void testProductDiscountBasedTransaction() throws InspireNetzException {

        // SEt the sale to sku type
        sale.setSalType(SaleType.ITEM_BASED_PURCHASE);
        sale = saleService.saveSale(sale);

        // GEt the product
        Product product = ProductFixture.standardProduct();
        product = productService.saveProduct(product);
        Assert.assertNotNull(product);;
        log.info("Product Information SAved");


        // Get the standardSaleSKU
        SaleSKU saleSKU = SaleSKUFixture.standardSaleSku();
        saleSKU.setSsuSaleId(sale.getSalId());
        saleSKU.setSsuProductCode(product.getPrdCode());
        saleSKU = saleSKUService.saveSaleSku(saleSKU);
        Assert.assertNotNull(saleSKU);
        log.info("Sale sku information saved");


        // Create the LoyaltyProgram
        LoyaltyProgram  loyaltyProgram =  LoyaltyProgramFixture.standardLoyaltyProgram();
        loyaltyProgram.setPrgProgramDriver(LoyaltyProgramDriver.PRODUCT_DISCOUNT_BASED);
        loyaltyProgram.setPrgRuleType(LoyaltyProgramRuleType.TIERED_RATIO);
        loyaltyProgram.setPrgTier1Num(1.0);
        loyaltyProgram.setPrgTier1Deno(100.0);
        loyaltyProgram.setPrgTier1LimitFrom(0.0);
        loyaltyProgram.setPrgTier1LimitTo(10.0);
        loyaltyProgram.setPrgTier2Num(2.0);
        loyaltyProgram.setPrgTier2Deno(100.0);
        loyaltyProgram.setPrgTier2LimitFrom(10.0);
        loyaltyProgram.setPrgTier2LimitTo(20.0);
        loyaltyProgram = loyaltyProgramService.saveLoyaltyProgram(loyaltyProgram);
        Assert.assertNotNull(loyaltyProgram);
        log.info("Loyalty Program SAved" + loyaltyProgram.toString());


        // Save the PointRewardData
        PointRewardData pointRewardData =  loyaltyEngineService.getPointRewardDataObjectForTransaction(sale);
        Assert.assertNotNull(pointRewardData);
        log.info("PointRewardData : " + pointRewardData.toString());



        // Check the value
        boolean success = loyaltyEngineService.processProgramRewarding(loyaltyProgram,sale,pointRewardData);
        Assert.assertTrue(success);


    }

    @Test
    public void testLinkedAwardPoints() throws InspireNetzException {

        // create the Customer
        Set<Customer> customers = CustomerFixture.standardCustomers();
        List<Customer> customerList = Lists.newArrayList((Iterable<Customer>) customers);
        customerService.saveAll(customerList);

        // ADd to the set for delete
        customerSet.addAll(customerList);



        // Create the LinkedLoyalty
        LinkedLoyalty linkedLoyalty = LinkedLoyaltyFixture.standardLinkedLoyalty();
        linkedLoyalty.setLilLocation(customerList.get(0).getCusLocation());
        linkedLoyalty.setLilChildCustomerNo(customerList.get(1).getCusCustomerNo());
        linkedLoyalty.setLilParentCustomerNo(customerList.get(0).getCusCustomerNo());
        linkedLoyalty.setLilStatus(LinkedLoyaltyStatus.ACTIVE);
        linkedLoyalty = linkedLoyaltyService.saveLinkedLoyalty(linkedLoyalty);


        // Create the AccountBundlingSetting
        AccountBundlingSetting accountBundlingSetting = AccountBundlingSettingFixture.standardAccountBundlingSetting();
        accountBundlingSetting.setAbsLocation(0L);
        accountBundlingSetting.setAbsLinkingType(AccountBundlingLinkingType.CONSOLIDATE_TO_PRIMARY);
        accountBundlingSetting = accountBundlingSettingService.saveAccountBundlingSetting(accountBundlingSetting);
        Assert.assertNotNull(accountBundlingSetting.getAbsId());

        // Add to the set
        accountBundlingSettingSet.add(accountBundlingSetting);



        // Add the AccumulatedRewardBalance for the secondary
        AccumulatedRewardBalance accumulatedRewardBalance = AccumulatedRewardBalanceFixture.standardAccumulatedRewardBalance();
        accumulatedRewardBalance.setArbLoyaltyId(customerList.get(1).getCusLoyaltyId());
        accumulatedRewardBalance =  accumulatedRewardBalanceService.saveAccumulatedRewardBalance(accumulatedRewardBalance);


        // Add the AccumulatedRewardBalance for the primary
        AccumulatedRewardBalance accumulatedRewardBalance1 = AccumulatedRewardBalanceFixture.standardAccumulatedRewardBalance();
        accumulatedRewardBalance1.setArbLoyaltyId(customerList.get(0).getCusLoyaltyId());
        accumulatedRewardBalance1.setArbRewardBalance(120.0);
        accumulatedRewardBalance1 =  accumulatedRewardBalanceService.saveAccumulatedRewardBalance(accumulatedRewardBalance1);


        // Add the LinkedRewardBalance for the primary
        LinkedRewardBalance linkedRewardBalance = LinkedRewardBalanceFixture.standardLinkedRewardBalance();
        linkedRewardBalance.setLrbPrimaryLoyaltyId(customerList.get(0).getCusLoyaltyId());
        linkedRewardBalance.setLrbRewardBalance(120.0);
        linkedRewardBalance =  linkedRewardBalanceService.saveLinkedRewardBalance(linkedRewardBalance);


        // Add the CustomerrewardBalance for secondary
        CustomerRewardBalance customerRewardBalance = CustomerRewardBalanceFixture.standardRewardBalance();
        customerRewardBalance.setCrbLoyaltyId(customerList.get(1).getCusLoyaltyId());
        customerRewardBalance =  customerRewardBalanceService.saveCustomerRewardBalance(customerRewardBalance);


        // Create a PrimaryLoyalty entry
        PrimaryLoyalty primaryLoyalty = PrimaryLoyaltyFixture.standardPrimaryLoyalty();
        primaryLoyalty.setPllLoyaltyId(customerList.get(0).getCusLoyaltyId());
        primaryLoyalty.setPllCustomerNo(customerList.get(0).getCusCustomerNo());
        primaryLoyalty = primaryLoyaltyService.savePrimaryLoyalty(primaryLoyalty);


        // Create the point rewardData for the secondary
        PointRewardData pointRewardData = new PointRewardData();
        pointRewardData.setTxnLocation(customerList.get(1).getCusLocation());
        pointRewardData.setLoyaltyId(customerList.get(1).getCusLoyaltyId());
        pointRewardData.setAddToAccumulatedBalance(true);
        pointRewardData.setAuditDetails("59");
        pointRewardData.setMerchantNo(customerList.get(1).getCusMerchantNo());
        pointRewardData.setRewardQty(20.0);
        pointRewardData.setRwdCurrencyId(1L);
        pointRewardData.setTxnDate(DBUtils.covertToSqlDate("2014-05-20"));


        // Create Transaction
        Transaction transaction = loyaltyEngineUtils.getTransactionForPointRewardData(pointRewardData,100.0,"test");



        // Call the awardPoint
        boolean isAwardPoints = loyaltyEngineService.awardPoints(pointRewardData,transaction);
        Assert.assertTrue(isAwardPoints);;


    }

    @Test
    public void testDeductPoints() throws InspireNetzException {

        // create the Customer
        Set<Customer> customers = CustomerFixture.standardCustomers();
        List<Customer> customerList = Lists.newArrayList((Iterable<Customer>) customers);
        customerService.saveAll(customerList);

        // ADd to the set for delete
        customerSet.addAll(customerList);



        // Create the LinkedLoyalty
        LinkedLoyalty linkedLoyalty = LinkedLoyaltyFixture.standardLinkedLoyalty();
        linkedLoyalty.setLilLocation(customerList.get(0).getCusLocation());
        linkedLoyalty.setLilChildCustomerNo(customerList.get(1).getCusCustomerNo());
        linkedLoyalty.setLilParentCustomerNo(customerList.get(0).getCusCustomerNo());
        linkedLoyalty.setLilStatus(LinkedLoyaltyStatus.ACTIVE);
        linkedLoyalty = linkedLoyaltyService.saveLinkedLoyalty(linkedLoyalty);


        // Add the LinkedRewardBalance for the primary
        LinkedRewardBalance linkedRewardBalance = LinkedRewardBalanceFixture.standardLinkedRewardBalance();
        linkedRewardBalance.setLrbPrimaryLoyaltyId(customerList.get(0).getCusLoyaltyId());
        linkedRewardBalance.setLrbRewardBalance(120.0);
        linkedRewardBalance =  linkedRewardBalanceService.saveLinkedRewardBalance(linkedRewardBalance);


        // Add the CustomerrewardBalance for secondary
        CustomerRewardBalance customerRewardBalance = CustomerRewardBalanceFixture.standardRewardBalance();
        customerRewardBalance.setCrbLoyaltyId(customerList.get(1).getCusLoyaltyId());
        customerRewardBalance =  customerRewardBalanceService.saveCustomerRewardBalance(customerRewardBalance);


        // Add the CustomerrewardBalance for primary
        CustomerRewardBalance customerRewardBalance1 = CustomerRewardBalanceFixture.standardRewardBalance();
        customerRewardBalance1.setCrbLoyaltyId(customerList.get(0).getCusLoyaltyId());
        customerRewardBalance1 =  customerRewardBalanceService.saveCustomerRewardBalance(customerRewardBalance1);



        // Add the CustomerRewardExpiry for secondary
        CustomerRewardExpiry customerRewardExpiry = CustomerRewardExpiryFixture.standardRewardExpiry();
        customerRewardExpiry.setCreLoyaltyId(customerList.get(1).getCusLoyaltyId());
        customerRewardExpiry.setCreExpiryDt(DBUtils.covertToSqlDate("2014-08-31"));
        customerRewardExpiry = customerRewardExpiryService.saveCustomerRewardExpiry(customerRewardExpiry);


        // Add the CustomerRewardExpiry for primary
        CustomerRewardExpiry customerRewardExpiry1 = CustomerRewardExpiryFixture.standardRewardExpiry();
        customerRewardExpiry1.setCreLoyaltyId(customerList.get(0).getCusLoyaltyId());
        customerRewardExpiry1.setCreExpiryDt(DBUtils.covertToSqlDate("2014-09-10"));
        customerRewardExpiry1 = customerRewardExpiryService.saveCustomerRewardExpiry(customerRewardExpiry1);




        // Create a PrimaryLoyalty entry
        PrimaryLoyalty primaryLoyalty = PrimaryLoyaltyFixture.standardPrimaryLoyalty();
        primaryLoyalty.setPllLoyaltyId(customerList.get(0).getCusLoyaltyId());
        primaryLoyalty.setPllCustomerNo(customerList.get(0).getCusCustomerNo());
        primaryLoyalty = primaryLoyaltyService.savePrimaryLoyalty(primaryLoyalty);



        // Create the PointDeductData
        PointDeductData pointDeductData = new PointDeductData();
        pointDeductData.setTxnLocation(customerList.get(1).getCusLocation());
        pointDeductData.setLoyaltyId(customerList.get(1).getCusLoyaltyId());
        pointDeductData.setAuditDetails("59");
        pointDeductData.setMerchantNo(customerList.get(1).getCusMerchantNo());
        pointDeductData.setRedeemQty(120.0);
        pointDeductData.setRwdCurrencyId(1L);
        pointDeductData.setTxnDate(DBUtils.covertToSqlDate("2014-08-31"));




        // Call the deduct points
        boolean isDeductPoints = loyaltyEngineService.deductPoints(pointDeductData);
        Assert.assertTrue(isDeductPoints);

    }

    @Test
    public void testDrools() {

        // Get a standard loyalty program
        LoyaltyProgram loyaltyProgram1 = LoyaltyProgramFixture.standardLoyaltyProgram();

        // Get another standard loyaltyProgram
        LoyaltyProgram loyaltyProgram2 =  LoyaltyProgramFixture.standardLoyaltyProgram();

        // GEt a Standard Purchae
        Sale sale = SaleFixture.standardSale();



        loyaltyProgram1.setPrgProgramDriver(2);



        //Create KnowledgeBase...
        KnowledgeBase knowledgeBase = createKnowledgeBase();
        //Create a statefull session
        StatefulKnowledgeSession session = knowledgeBase.newStatefulKnowledgeSession();


        /*
        //Create Facts - two ban accounts
        Account account = new Account();
        account.setBalance(10);
        account.setId("N1");

        Account account2 = new Account();
        account2.setBalance(120);
        account2.setId("N2");

        //Insert the bank account facts into a State full session
        session.insert(account);
        session.insert(account2);

        //Note that at this point, afetr fact insertation the Agenda has one activation ready to be fired.
        //Account1 is less than 100.

        //Only now we will fire the rules which are already in the agenda
        session.fireAllRules();


        // Create bonus Fact
        Bonus bonus = new Bonus();
        bonus.setAmount(20);

        session.insert(bonus);*/




        // Add the loyalty program to the session
        session.insert(loyaltyProgram1);
        session.insert(loyaltyProgram2);

        // Set the LoyaltyProgramUtil global
        session.setGlobal("loyaltyProgramUtil",loyaltyProgramUtil);

        // Add the sale
        session.insert(sale);

        // fire all the rules
        session.fireAllRules();

        //A message of N1 is less than 100 will be printed to stdout.

    }

    @Test
    public void testDroolsTransactionBased() {

        // Get a standard loyalty program
        LoyaltyProgram loyaltyProgram1 = LoyaltyProgramFixture.standardLoyaltyProgram();

        // Get another standard loyaltyProgram
        LoyaltyProgram loyaltyProgram2 =  LoyaltyProgramFixture.standardLoyaltyProgram();

        Sale sale = SaleFixture.standardSale();
        Attribute attribute = attributeService.findByAtrId(13L);
        saleService.setExtFieldValue(sale,attribute,"100");


        SaleSKU saleSKU = SaleSKUFixture.standardSaleSku();
        saleSKU.setSsuSaleId(sale.getSalId());
        Attribute attribute1 = attributeService.findByAtrId(14L);
        saleSKUService.setExtFieldValue(saleSKU,attribute1,"2000");


        PointRewardData pointRewardData = new PointRewardData();






        // Get the map
        AttributeExtendedEntityMap saleMap = saleService.toAttributeExtensionMap(sale,AttributeExtensionMapType.ALL);

        // Set the AttributeExtendedEntityMap
        sale.setFieldMap(saleMap);



        // Get the map for the sku
        AttributeExtendedEntityMap saleSkuMap = saleSKUService.toAttributeExtensionMap(saleSKU,AttributeExtensionMapType.ALL);

        // Set the AttributeExtenedEntityMap
        saleSKU.setFieldMap(saleSkuMap);

        loyaltyProgram1.setPrgProgramDriver(2);



        //Create KnowledgeBase...
        KnowledgeBase knowledgeBase = createKnowledgeBase();
        //Create a statefull session
        StatefulKnowledgeSession session = knowledgeBase.newStatefulKnowledgeSession();


        /*
        //Create Facts - two ban accounts
        Account account = new Account();
        account.setBalance(10);
        account.setId("N1");

        Account account2 = new Account();
        account2.setBalance(120);
        account2.setId("N2");

        //Insert the bank account facts into a State full session
        session.insert(account);
        session.insert(account2);

        //Note that at this point, afetr fact insertation the Agenda has one activation ready to be fired.
        //Account1 is less than 100.

        //Only now we will fire the rules which are already in the agenda
        session.fireAllRules();


        // Create bonus Fact
        Bonus bonus = new Bonus();
        bonus.setAmount(20);

        session.insert(bonus);*/




        // Add the loyalty program to the session
        session.insert(loyaltyProgram1);
        session.insert(loyaltyProgram2);

        // Set the LoyaltyProgramUtil global
        session.setGlobal("loyaltyProgramUtil",loyaltyProgramUtil);

        // Set the pointRewardData
        session.insert(pointRewardData);


        // Add the sale
        session.insert(sale);
        session.insert(saleSKU);

        // fire all the rules
        session.fireAllRules();

        //A message of N1 is less than 100 will be printed to stdout.
        log.info("point qty" + pointRewardData.getRewardQty());

    }

    @Test
    public void testDroolsLoyaltyProgram() throws InspireNetzException {


        TierGroup tierGroup = TierGroupFixture.standardTierGroup();
        tierGroup = tierGroupService.saveTierGroup(tierGroup);

        // Add to the tierGroupSet
        tierGroupSet.add(tierGroup);


        Tier tier = TierFixture.standardTier();
        tier.setTieName("Smart-Prepaid-Gold");
        tier.setTieParentGroup(tierGroup.getTigId());
        tier = tierService.saveTier(tier);

        // Add to the tierSEt
        tierSet.add(tier);



        // Create the customer
        Customer customer = CustomerFixture.standardCustomer();
        customer.setCusTier(tier.getTieId());
        customer = customerService.saveCustomer(customer);

        // Add to customerset
        customerSet.add(customer);


        // Create a SaleSKU
        SaleSKU saleSKU = SaleSKUFixture.standardSaleSku();
        saleSKU.setSsuMsfValue(200.0);
        saleSKU.setSsuPrice(150.0);
        saleSKU.setSsuSaleId(null);
        saleSKU.setSsuProductCode("BUDDY");

        // Add to set
        Set<SaleSKU> saleSKUSet = new HashSet<>(0);
        saleSKUSet.add(saleSKU);

        // Create a standard sales
        Sale sale = SaleFixture.standardSale();
        sale.setSalType(SaleType.ITEM_BASED_PURCHASE);
        sale.setSalLoyaltyId(customer.getCusLoyaltyId());
        sale.setSaleSKUSet(saleSKUSet);
        sale = saleService.saveSale(sale);


        LoyaltyExtension loyaltyExtension = LoyaltyExtensionFixture.standardLoyaltyExtension();
        loyaltyExtension.setLexFile("msfbasedprogram.drl");
        loyaltyExtension = loyaltyExtensionService.saveLoyaltyExtension(loyaltyExtension);

        // Add the extension
        loyaltyExtensionSet.add(loyaltyExtension);


        // Create the LoyaltyProgram
        LoyaltyProgram loyaltyProgram = LoyaltyProgramFixture.standardLoyaltyProgram();
        loyaltyProgram.setPrgComputationSource(LoyaltyComputationSource.LOYALTY_EXTENSION);
        loyaltyProgram.setPrgLoyaltyExtension(loyaltyExtension.getLexId());
        loyaltyProgram =loyaltyProgramService.saveLoyaltyProgram(loyaltyProgram);


        // Call the processing
        boolean isProcessed = loyaltyEngineService.processTransaction(sale);
        Assert.assertTrue(isProcessed);
        log.info("data processed");


    }

    @Test
    public void testScheduledProcessingCustomDate() throws InspireNetzException {

        TierGroup tierGroup = TierGroupFixture.standardTierGroup();
        tierGroup = tierGroupService.saveTierGroup(tierGroup);

        // Add to the tierGroupSet
        tierGroupSet.add(tierGroup);


        Tier tier = TierFixture.standardTier();
        tier.setTieName("Smart-Prepaid-Gold");
        tier.setTieParentGroup(tierGroup.getTigId());
        tier = tierService.saveTier(tier);

        // Add to the tierSEt
        tierSet.add(tier);


        // Create a reward Currency
        RewardCurrency rewardCurrency = RewardCurrencyFixture.standardRewardCurrency();
        rewardCurrency.setRwdExpiryDate(DBUtils.covertToSqlDate("2014-10-01"));
        rewardCurrency = rewardCurrencyService.saveRewardCurrency(rewardCurrency);
        Assert.assertNotNull(rewardCurrency.getRwdCurrencyId());

        // Add to the set
        rewardCurrencySet.add(rewardCurrency);

        // Create the LoyaltyProgram
        LoyaltyProgram loyaltyProgram = LoyaltyProgramFixture.standardLoyaltyProgram();
        loyaltyProgram.setPrgProgramDriver(LoyaltyProgramDriver.DATE_TRIGGERED);

        //  Create the LoyaltyProgramSku with custom date
        LoyaltyProgramSku loyaltyProgramSku = LoyaltyProgramSkuFixture.standardLoyaltyProgramSku();
        loyaltyProgramSku.setLpuItemCode(generalUtils.convertToISOFormat(new Date()));
        loyaltyProgramSku.setLpuTier(tier.getTieId());
        loyaltyProgramSku.setLpuItemType(LoyaltyProgramSkuType.DT_CUSTOM_DATE);
        loyaltyProgramSku.setLpuPrgRatioNum(100);

        // Add to a set
        Set<LoyaltyProgramSku> loyaltyProgramSkuSet = new HashSet<>(0);
        loyaltyProgramSkuSet.add(loyaltyProgramSku);

        // Add to the LoyaltyProgram object
        loyaltyProgram.setLoyaltyProgramSkuSet(loyaltyProgramSkuSet);


        // SAve the loyaltyProgram
        loyaltyProgram = loyaltyProgramService.saveLoyaltyProgram(loyaltyProgram);
        Assert.assertNotNull(loyaltyProgram.getPrgProgramNo());



        // Create a customer
        Customer customer = CustomerFixture.standardCustomer();
        customer.setCusTier(tier.getTieId());
        customer = customerService.saveCustomer(customer);

        // Add to tempSet for customer
        customerSet.add(customer);


        // Start the processing
        boolean processed = loyaltyEngineService.processDTLoyaltyProgramsForCustomer(loyaltyProgram, customer);
        Assert.assertTrue(processed);
        log.info("Completed successfully");

        log.info("Completed successfully");





    }

    @Test
    public void testScheduledProcessingCustomerBirthday() throws InspireNetzException {

        TierGroup tierGroup = TierGroupFixture.standardTierGroup();
        tierGroup = tierGroupService.saveTierGroup(tierGroup);

        // Add to the tierGroupSet
        tierGroupSet.add(tierGroup);


        Tier tier = TierFixture.standardTier();
        tier.setTieName("Smart-Prepaid-Gold");
        tier.setTieParentGroup(tierGroup.getTigId());
        tier = tierService.saveTier(tier);

        // Add to the tierSEt
        tierSet.add(tier);


        // Create a reward Currency
        RewardCurrency rewardCurrency = RewardCurrencyFixture.standardRewardCurrency();
        rewardCurrency.setRwdExpiryDate(DBUtils.covertToSqlDate("2014-10-01"));
        rewardCurrency = rewardCurrencyService.saveRewardCurrency(rewardCurrency);
        Assert.assertNotNull(rewardCurrency.getRwdCurrencyId());

        // Add to the set
        rewardCurrencySet.add(rewardCurrency);

        // Create the LoyaltyProgram
        LoyaltyProgram loyaltyProgram = LoyaltyProgramFixture.standardLoyaltyProgram();
        loyaltyProgram.setPrgProgramDriver(LoyaltyProgramDriver.DATE_TRIGGERED);

        //  Create the LoyaltyProgramSku with custom date
        LoyaltyProgramSku loyaltyProgramSku = LoyaltyProgramSkuFixture.standardLoyaltyProgramSku();
        loyaltyProgramSku.setLpuItemCode(generalUtils.convertToISOFormat(new Date()));
        loyaltyProgramSku.setLpuTier(tier.getTieId());
        loyaltyProgramSku.setLpuItemType(LoyaltyProgramSkuType.DT_BIRTHDAY);
        loyaltyProgramSku.setLpuPrgRatioNum(20);

        //  Create the LoyaltyProgramSku with custom date
        LoyaltyProgramSku loyaltyProgramSku2 = LoyaltyProgramSkuFixture.standardLoyaltyProgramSku();
        loyaltyProgramSku2.setLpuItemCode(generalUtils.convertToISOFormat(new Date()));
        loyaltyProgramSku2.setLpuTier(tier.getTieId());
        loyaltyProgramSku2.setLpuItemType(LoyaltyProgramSkuType.DT_ANNIVERSARY);
        loyaltyProgramSku2.setLpuPrgRatioNum(30);


        // Add to a set
        Set<LoyaltyProgramSku> loyaltyProgramSkuSet = new HashSet<>(0);
        loyaltyProgramSkuSet.add(loyaltyProgramSku);
        loyaltyProgramSkuSet.add(loyaltyProgramSku2);


        // Add to the LoyaltyProgram object
        loyaltyProgram.setLoyaltyProgramSkuSet(loyaltyProgramSkuSet);


        // SAve the loyaltyProgram
        loyaltyProgram = loyaltyProgramService.saveLoyaltyProgram(loyaltyProgram);
        Assert.assertNotNull(loyaltyProgram.getPrgProgramNo());



        // Create a customer
        Customer customer = CustomerFixture.standardCustomer();
        customer.setCusTier(tier.getTieId());
        customer = customerService.saveCustomer(customer);


        // Create the CustomerProfile
        CustomerProfile customerProfile = CustomerProfileFixture.standardCustomerProfile();
        customerProfile.setCspBirthDayLastAwarded(DBUtils.covertToSqlDate("2014-12-17"));
        customerProfile.setCspAnniversaryLastAwarded(DBUtils.covertToSqlDate("2014-12-17"));
        customerProfile.setCspCustomerBirthday(DBUtils.covertToSqlDate("1989-12-18"));
        customerProfile.setCspCustomerAnniversary(DBUtils.covertToSqlDate("2014-12-18"));
        customerProfile.setCspCustomerNo(customer.getCusCustomerNo());
        customerProfile = customerProfileService.saveCustomerProfile(customerProfile);

        // Add to customerprofileSEt
        customerProfileSet.add(customerProfile);

        // Add to tempSet for customer
        customerSet.add(customer);


        // Start the processing
        //loyaltyEngineService.runScheduledProcessing();
        boolean processed = loyaltyEngineService.processDTLoyaltyProgramsForCustomer(loyaltyProgram, customer);
        Assert.assertTrue(processed);
        log.info("Completed successfully");

        customerProfile = customerProfileService.findByCspCustomerNo(customer.getCusCustomerNo());
        log.info("CustomerProfile : " + customerProfile);




    }


    @Test
    public void testCustomerRewardActivityProcessing() throws InspireNetzException {



        TierGroup tierGroup = TierGroupFixture.standardTierGroup();
        tierGroup = tierGroupService.saveTierGroup(tierGroup);

        // Add to the tierGroupSet
        tierGroupSet.add(tierGroup);


        Tier tier = TierFixture.standardTier();
        tier.setTieName("Smart-Prepaid-Gold");
        tier.setTieParentGroup(tierGroup.getTigId());
        tier = tierService.saveTier(tier);

        // Add to the tierSEt
        tierSet.add(tier);

        // Create a reward Currency
        RewardCurrency rewardCurrency = RewardCurrencyFixture.standardRewardCurrency();
        rewardCurrency.setRwdExpiryDate(DBUtils.covertToSqlDate("2014-10-01"));
        rewardCurrency = rewardCurrencyService.saveRewardCurrency(rewardCurrency);
        Assert.assertNotNull(rewardCurrency.getRwdCurrencyId());

        // Add to the set
        rewardCurrencySet.add(rewardCurrency);

        // Create the LoyaltyProgram
        LoyaltyProgram loyaltyProgram = LoyaltyProgramFixture.standardLoyaltyProgram();
        loyaltyProgram.setPrgCurrencyId(rewardCurrency.getRwdCurrencyId());
        loyaltyProgram.setPrgProgramDriver(LoyaltyProgramDriver.ACTIVITY_TRIGGERED);

        //  Create the LoyaltyProgramSku with custom date
        LoyaltyProgramSku loyaltyProgramSku = LoyaltyProgramSkuFixture.standardLoyaltyProgramSku();
        loyaltyProgramSku.setLpuItemCode("PRD1001");
        loyaltyProgramSku.setLpuTier(tier.getTieId());
        loyaltyProgramSku.setLpuItemType(LoyaltyProgramSkuType.PRODUCT_SUBSCRIPTION);
        loyaltyProgramSku.setLpuPrgRatioNum(20);

        // Add to a set
        Set<LoyaltyProgramSku> loyaltyProgramSkuSet = new HashSet<>(0);
        loyaltyProgramSkuSet.add(loyaltyProgramSku);

        // Add to the LoyaltyProgram object
        loyaltyProgram.setLoyaltyProgramSkuSet(loyaltyProgramSkuSet);


        // SAve the loyaltyProgram
        loyaltyProgram = loyaltyProgramService.saveLoyaltyProgram(loyaltyProgram);
        Assert.assertNotNull(loyaltyProgram.getPrgProgramNo());




        // Create a customer
        Customer customer = CustomerFixture.standardCustomer();
        customer.setCusTier(tier.getTieId());
        customer = customerService.saveCustomer(customer);

        // Add to tempSet for customer
        customerSet.add(customer);

        // Create The CustomerRewardActivity
        CustomerRewardActivity customerRewardActivity = CustomerRewardActivityFixture.standardCustomerRewardActivity();
        customerRewardActivity.setCraType(CustomerRewardingType.PRODUCT_SUBSCRIPTION);
        customerRewardActivity.setCraActivityRef("PRD1001");
        customerRewardActivity.setCraCustomerNo(customer.getCusCustomerNo());
        customerRewardActivity.setCraStatus(CustomerRewardActivityStatus.NEW);

        // sAve the customerRewardActivity
        customerRewardActivity = customerRewardActivityService.saveCustomerRewardActivity(customerRewardActivity);

        // Add to the set
        customerRewardActivitySet.add(customerRewardActivity);


        // Call the processing
        loyaltyEngineService.doCustomerRewardActivityProcessing(customerRewardActivity);
        log.info("Customer Reward Activity awarding completed");


        // Get the CustomerRewardBalance for the customer
        CustomerRewardBalance customerRewardBalance = customerRewardBalanceService.findByCrbLoyaltyIdAndCrbMerchantNoAndCrbRewardCurrency(customer.getCusLoyaltyId(),customer.getCusMerchantNo(),rewardCurrency.getRwdCurrencyId());
        Assert.assertNotNull(customerRewardBalance);
        Assert.assertTrue(customerRewardBalance.getCrbRewardBalance() == loyaltyProgramSku.getLpuPrgRatioNum());
        log.info("Completed successfully");

    }

    @Test
    public void testEventBasedRewardActivityProcessing() throws InspireNetzException {



        TierGroup tierGroup = TierGroupFixture.standardTierGroup();
        tierGroup = tierGroupService.saveTierGroup(tierGroup);

        // Add to the tierGroupSet
        tierGroupSet.add(tierGroup);


        Tier tier = TierFixture.standardTier();
        tier.setTieName("Test tier");
        tier.setTieParentGroup(tierGroup.getTigId());
        tier = tierService.saveTier(tier);

        // Add to the tierSEt
        tierSet.add(tier);

        MerchantSetting merchantSetting = MerchantSettingFixture.standardMerchantSetting();
        merchantSetting.setMesSettingId(57L);
        merchantSetting.setMesValue(MerchantReferralSetting.CUSTOMER_BASED+"");
        merchantSetting.setMesMerchantNo(1L);
        merchantSetting.setSetName(AdminSettingsConfiguration.MER_REFERRAL_BASIS);

        merchantSetting = merchantSettingService.saveMerchantSetting(merchantSetting);

        merchantSettings.add(merchantSetting);

        // Create a reward Currency
        RewardCurrency rewardCurrency = RewardCurrencyFixture.standardRewardCurrency();
        rewardCurrency.setRwdExpiryDate(DBUtils.covertToSqlDate("2016-10-01"));
        rewardCurrency = rewardCurrencyService.saveRewardCurrency(rewardCurrency);
        Assert.assertNotNull(rewardCurrency.getRwdCurrencyId());

        PromotionalEvent promotionalEvent = PromotionalEventFixture.standardPromotionalEvent();
        promotionalEvent.setPreDescription("Referral milestone");
        promotionalEvent.setPreEventCode("REF01");
        promotionalEvent.setPreMerchantNo(1L);

        promotionalEvent = promotionalEventService.savePromotionalEvent(promotionalEvent);
        promotionalEvents.add(promotionalEvent);
        // Add to the set
        rewardCurrencySet.add(rewardCurrency);

        // Add to a set
        Set<LoyaltyProgramSku> loyaltyProgramSkuSet = new HashSet<>(0);


        // Create the LoyaltyProgram
        LoyaltyProgram loyaltyProgram = LoyaltyProgramFixture.standardLoyaltyProgram();
        loyaltyProgram.setPrgCurrencyId(rewardCurrency.getRwdCurrencyId());
        loyaltyProgram.setPrgProgramDriver(LoyaltyProgramDriver.EVENT_BASED);

        //  Create the LoyaltyProgramSku with custom date
        LoyaltyProgramSku loyaltyProgramSku = LoyaltyProgramSkuFixture.standardLoyaltyProgramSku();
        loyaltyProgramSku.setLpuItemCode(promotionalEvent.getPreEventCode());
        loyaltyProgramSku.setLpuTier(0L);
        loyaltyProgramSku.setLpuItemType(LoyaltyProgramSkuType.MERCHANT_EVENT);
        loyaltyProgramSku.setLpuReference("PRD001");
        loyaltyProgramSku.setLpuPrgRatioNum(20);
        loyaltyProgramSku.setLpuRole(LoyaltyRefferalRoles.REFERREE);
        loyaltyProgramSku.setLpuProgramId(null);
        log.info("SKU 1"+loyaltyProgramSku.hashCode());
        loyaltyProgramSkuSet.add(loyaltyProgramSku);

        //  Create the LoyaltyProgramSku with custom date
        LoyaltyProgramSku loyaltyProgramSku1 = LoyaltyProgramSkuFixture.standardLoyaltyProgramSku();
        loyaltyProgramSku1.setLpuItemCode(promotionalEvent.getPreEventCode());
        loyaltyProgramSku1.setLpuTier(0L);
        loyaltyProgramSku1.setLpuItemType(LoyaltyProgramSkuType.MERCHANT_EVENT);
        loyaltyProgramSku1.setLpuReference("PRD001");
        loyaltyProgramSku1.setLpuPrgRatioNum(10);
        loyaltyProgramSku1.setLpuProgramId(null);
        loyaltyProgramSku1.setLpuRole(LoyaltyRefferalRoles.REFFERRER);
        log.info("SKU 2"+loyaltyProgramSku1.hashCode());
        loyaltyProgramSkuSet.add(loyaltyProgramSku1);

        loyaltyProgram.setPrgMerchantNo(1L);
        // Add to the LoyaltyProgram object
        loyaltyProgram.setLoyaltyProgramSkuSet(loyaltyProgramSkuSet);


        // SAve the loyaltyProgram
        loyaltyProgram = loyaltyProgramService.saveLoyaltyProgram(loyaltyProgram);
        Assert.assertNotNull(loyaltyProgram.getPrgProgramNo());


        // Create a customer
        Customer customer = CustomerFixture.standardCustomer();
        customer.setCusTier(tier.getTieId());
        customer.setCusLoyaltyId("9298390192");
        customer = customerService.saveCustomer(customer);

        // Create a customer
        Customer referrer = CustomerFixture.standardCustomer();
        referrer.setCusTier(tier.getTieId());
        referrer.setCusLoyaltyId("9744273894");
        referrer = customerService.saveCustomer(referrer);

        // Add to tempSet for customer
        customerSet.add(customer);

        customerSet.add(referrer);

        CustomerPromotionalEvent customerPromotionalEvent = CustomerPromotionalEventFixture.standardCustomerPromotionalEvent();
        customerPromotionalEvent.setCpeProduct("PRD001");
        customerPromotionalEvent.setCpeEventId(promotionalEvent.getPreId());
        customerPromotionalEvent.setCpeLoyaltyId(customer.getCusLoyaltyId());
        customerPromotionalEvent.setCpeMerchantNo(customer.getCusMerchantNo());
        customerPromotionalEvent = customerPromotionalEventService.saveCustomerPromotionalEvent(customerPromotionalEvent);
        customerPromotionalSet.add(customerPromotionalEvent);

        // Create The CustomerRewardActivity
        CustomerRewardActivity customerRewardActivity = CustomerRewardActivityFixture.standardCustomerRewardActivity();
        customerRewardActivity.setCraType(CustomerRewardingType.MERCHANT_EVENT);
        customerRewardActivity.setCraActivityRef(customerPromotionalEvent.getCpeId()+"");
        customerRewardActivity.setCraCustomerNo(customer.getCusCustomerNo());
        customerRewardActivity.setCraStatus(CustomerRewardActivityStatus.NEW);

        // save the customerRewardActivity
        customerRewardActivity = customerRewardActivityService.saveCustomerRewardActivity(customerRewardActivity);

        customerRewardActivitySet.add(customerRewardActivity);


        CustomerReferral customerReferral = CustomerReferralFixture.standardCustomerReferral();
        customerReferral.setCsrLoyaltyId(referrer.getCusLoyaltyId());
        customerReferral.setCsrRefMobile(customer.getCusLoyaltyId());
        customerReferral.setCsrRefStatus(CustomerReferralStatus.NEW);
        customerReferral.setCsrFName("Test referral");
        customerReferral.setCsrProduct("PRD001");
        customerReferral = customerReferralService.saveCustomerReferral(customerReferral);
        customerReferralSet.add(customerReferral);

        log.info("Starting event based rewarding");

        // Call the processing
        loyaltyEngineService.doEventBasedAwarding(customerRewardActivity);



        // Get the CustomerRewardBalance for the customer
        CustomerRewardBalance customerRewardBalance = customerRewardBalanceService.findByCrbLoyaltyIdAndCrbMerchantNoAndCrbRewardCurrency(customer.getCusLoyaltyId(),customer.getCusMerchantNo(),rewardCurrency.getRwdCurrencyId());
        Assert.assertNotNull(customerRewardBalance);

    }

    @Test
    public void testRewardExpiryProcessing() throws InspireNetzException {

        // create the Customer
        Set<Customer> customers = CustomerFixture.standardCustomers();
        List<Customer> customerList = Lists.newArrayList((Iterable<Customer>) customers);
        customerService.saveAll(customerList);

        // ADd to the set for delete
        customerSet.addAll(customerList);



        // Create the LinkedLoyalty
        LinkedLoyalty linkedLoyalty = LinkedLoyaltyFixture.standardLinkedLoyalty();
        linkedLoyalty.setLilLocation(customerList.get(0).getCusLocation());
        linkedLoyalty.setLilChildCustomerNo(customerList.get(1).getCusCustomerNo());
        linkedLoyalty.setLilParentCustomerNo(customerList.get(0).getCusCustomerNo());
        linkedLoyalty.setLilStatus(LinkedLoyaltyStatus.ACTIVE);
        linkedLoyalty = linkedLoyaltyService.saveLinkedLoyalty(linkedLoyalty);


        // Add the LinkedRewardBalance for the primary
        LinkedRewardBalance linkedRewardBalance = LinkedRewardBalanceFixture.standardLinkedRewardBalance();
        linkedRewardBalance.setLrbPrimaryLoyaltyId(customerList.get(0).getCusLoyaltyId());
        linkedRewardBalance.setLrbRewardBalance(120.0);
        linkedRewardBalance =  linkedRewardBalanceService.saveLinkedRewardBalance(linkedRewardBalance);


        // Add the CustomerrewardBalance for secondary
        CustomerRewardBalance customerRewardBalance = CustomerRewardBalanceFixture.standardRewardBalance();
        customerRewardBalance.setCrbLoyaltyId(customerList.get(1).getCusLoyaltyId());
        customerRewardBalance =  customerRewardBalanceService.saveCustomerRewardBalance(customerRewardBalance);


        // Add the CustomerrewardBalance for primary
        CustomerRewardBalance customerRewardBalance1 = CustomerRewardBalanceFixture.standardRewardBalance();
        customerRewardBalance1.setCrbLoyaltyId(customerList.get(0).getCusLoyaltyId());
        customerRewardBalance1 =  customerRewardBalanceService.saveCustomerRewardBalance(customerRewardBalance1);



        // Add the CustomerRewardExpiry for secondary
        CustomerRewardExpiry customerRewardExpiry = CustomerRewardExpiryFixture.standardRewardExpiry();
        customerRewardExpiry.setCreLoyaltyId(customerList.get(0).getCusLoyaltyId());
        customerRewardExpiry.setCreExpiryDt(DBUtils.covertToSqlDate("2014-08-31"));
        customerRewardExpiry = customerRewardExpiryService.saveCustomerRewardExpiry(customerRewardExpiry);


        // Add the CustomerRewardExpiry for primary
        CustomerRewardExpiry customerRewardExpiry1 = CustomerRewardExpiryFixture.standardRewardExpiry();
        customerRewardExpiry1.setCreLoyaltyId(customerList.get(1).getCusLoyaltyId());
        customerRewardExpiry1.setCreExpiryDt(DBUtils.covertToSqlDate("2014-10-10"));
        customerRewardExpiry1 = customerRewardExpiryService.saveCustomerRewardExpiry(customerRewardExpiry1);




        // Create a PrimaryLoyalty entry
        PrimaryLoyalty primaryLoyalty = PrimaryLoyaltyFixture.standardPrimaryLoyalty();
        primaryLoyalty.setPllLoyaltyId(customerList.get(0).getCusLoyaltyId());
        primaryLoyalty.setPllCustomerNo(customerList.get(0).getCusCustomerNo());
        primaryLoyalty = primaryLoyaltyService.savePrimaryLoyalty(primaryLoyalty);



        // Call the clear expired balance



        // Call the deduct points
        loyaltyEngineService.runRewardExpiryProcessing();

        // Refetch the Expired customer reward expiry and make sure that the entry is 0
        CustomerRewardExpiry checkBalance = customerRewardExpiryService.findByCreId(customerRewardExpiry.getCreId());
        Assert.assertTrue(checkBalance.getCreRewardBalance() == 0);

    }

    private KnowledgeBase createKnowledgeBase() {

        KnowledgeBuilder builder = KnowledgeBuilderFactory.newKnowledgeBuilder();
        //Add drl file into builder
        File accountRules = new File("/home/sandheepgr/IdeaProjects/inspirepro-api/rules/transasctionbasedprogram.drl"); //Where the account rule is.
        builder.add(ResourceFactory.newFileResource(accountRules), ResourceType.DRL);
        if (builder.hasErrors()) {
            throw new RuntimeException(builder.getErrors().toString());
        }

        KnowledgeBase knowledgeBase = KnowledgeBaseFactory.newKnowledgeBase();

        //Add to Knowledge Base packages from the builder which are actually the rules from the drl file.
        knowledgeBase.addKnowledgePackages(builder.getKnowledgePackages());

        return knowledgeBase;
    }

    @Test
    public void testRewardExpiryLogging(){

        loyaltyEngineService.runRewardExpiryProcessing();

    }

    @Test
    public void testModValues() {

        double ratioNum = 10;
        double ratioDeno = 100;

        double amount = 130;

        double mod = amount % ratioDeno;

        double actualAmount = amount - mod;
        log.info("Mod is : "+mod);
        log.info("Actual amount is " + actualAmount);

    }

    @Test
    public void testDroolsReferrerFrequencyBased() throws InspireNetzException {

        // Set the stage for the drools by loading the file
        KnowledgeBuilder builder = KnowledgeBuilderFactory.newKnowledgeBuilder();
        File accountRules = new File("/home/sandheepgr/IdeaProjects/inspirenetz-api/rules/aparna_referrer_rules.drl"); //Where the account rule is.
        builder.add(ResourceFactory.newFileResource(accountRules), ResourceType.DRL);
        if (builder.hasErrors()) {
            throw new RuntimeException(builder.getErrors().toString());
        }
        KnowledgeBase knowledgeBase = KnowledgeBaseFactory.newKnowledgeBase();
        knowledgeBase.addKnowledgePackages(builder.getKnowledgePackages());

        // Create a merchant
        Long merchantNo = authSessionUtils.getMerchantNo();

        // Create the referee customer
        Customer referee = CustomerFixture.standardCustomer();
        referee.setCusMerchantNo(merchantNo);
        referee.setCusMobile("8123591676");
        referee = customerService.saveCustomer(referee);
        customerSet.add(referee);

        // Create the referrer customer
        Customer referrer = CustomerFixture.standardCustomer();
        referrer.setCusMerchantNo(merchantNo);
        referrer.setCusMobile("9538828853");
        referrer.setCusLoyaltyId("9181818181818");
        referrer = customerService.saveCustomer(referrer);
        customerSet.add(referrer);


        // Add the referral entry
        CustomerReferral customerReferral = CustomerReferralFixture.standardCustomerReferral();
        customerReferral.setCsrMerchantNo(merchantNo);
        customerReferral.setCsrLoyaltyId(referrer.getCusLoyaltyId());
        customerReferral.setCsrRefMobile(referee.getCusMobile());
        customerReferral.setCsrRefStatus(CustomerReferralStatus.PROCESSED);
        customerReferral.setCsrFName("Test referral");
        customerReferral.setCsrProduct("PRD001");
        customerReferral = customerReferralService.saveCustomerReferral(customerReferral);
        customerReferralSet.add(customerReferral);


        // Create the loyalty extension
        LoyaltyExtension loyaltyExtension = LoyaltyExtensionFixture.standardLoyaltyExtension();
        loyaltyExtension.setLexMerchantNo(merchantNo);
        loyaltyExtension.setLexFile("aparna_referrer_rules.drl");
        loyaltyExtension = loyaltyExtensionService.saveLoyaltyExtension(loyaltyExtension);
        loyaltyExtensionSet.add(loyaltyExtension);

        // Create the Sale
        Sale sale = SaleFixture.standardSale();
        sale.setSalMerchantNo(merchantNo);
        sale.setSalAmount(2550000.0);
        sale.setSalType(SaleType.ITEM_BASED_PURCHASE);
        sale.setSalLoyaltyId(referee.getCusLoyaltyId());
        sale = saleService.saveSale(sale);
        saleSet.add(sale);


        // Create the LoyaltyProgra
        LoyaltyProgram loyaltyProgram = LoyaltyProgramFixture.standardLoyaltyProgram();
        loyaltyProgram.setPrgMerchantNo(merchantNo);
        loyaltyProgram.setPrgRole(LoyaltyRefferalRoles.REFFERRER);
        loyaltyProgram.setPrgStatus(LoyaltyProgramStatus.ACTIVE);
        loyaltyProgram.setPrgComputationSource(LoyaltyComputationSource.LOYALTY_EXTENSION);
        loyaltyProgram.setPrgLoyaltyExtension(loyaltyExtension.getLexId());
        loyaltyProgram =loyaltyProgramService.saveLoyaltyProgram(loyaltyProgram);
        loyaltyProgramSet.add(loyaltyProgram);

        // Call the processing
        boolean isProcessed = loyaltyEngineService.processTransaction(sale);
        Assert.assertTrue(isProcessed);
        log.info("data processed");

    }

    @Test
    public void testIsProgramValidForTransaction() throws InspireNetzException {

        TierGroup tierGroup = TierGroupFixture.standardTierGroup();
        tierGroup.setTigName("groupTest3");
        tierGroup.setTigMerchantNo(3L);
        tierGroup = tierGroupService.saveTierGroup(tierGroup);



        // Add to the tierGroupSet
        tierGroupSet.add(tierGroup);

        Tier tier = TierFixture.standardTier();
        tier.setTieName("Smart-Prepaid-Gold4");
        tier.setTieParentGroup(tierGroup.getTigId());

        tier = tierService.saveTier(tier);

        // Add to the tierSEt
        tierSet.add(tier);



        // Create the customer
        Customer customer = CustomerFixture.standardCustomer();
        customer.setCusMerchantNo(3L);
        customer.setCusTier(tier.getTieId());
        customer = customerService.saveCustomer(customer);

        // Add to customerset
        customerSet.add(customer);


        // SEt the sale to sku type
        sale.setSalType(SaleType.ITEM_BASED_PURCHASE);
        sale.setSalPaymentReference(new Date().getTime() + "");
        sale.setSalLoyaltyId(customer.getCusLoyaltyId());
        sale.setSalMerchantNo(3L);
        sale = saleService.saveSale(sale);



        // Add to saleSet
        saleSet.add(sale);

        LoyaltyProgram  loyaltyProgram =  LoyaltyProgramFixture.standardLoyaltyProgram();
        loyaltyProgram.setPrgProgramName("Test_Loyalty");
        loyaltyProgram.setPrgProgramDriver(LoyaltyProgramDriver.TRANSACTION_AMOUNT);
        loyaltyProgram.setPrgMerchantNo(3L);
        loyaltyProgram.setPrgTxnSource(LoyaltyTransactionSource.SALES_TRANSACTION);

        // SAve the loyaltyProgram
        loyaltyProgram = loyaltyProgramService.saveLoyaltyProgram(loyaltyProgram);
        Assert.assertNotNull(loyaltyProgram.getPrgProgramNo());

        log.info("Loyalty Program Saved" + loyaltyProgram.toString());

        // Save the PointRewardData
        PointRewardData pointRewardData =  loyaltyEngineService.getPointRewardDataObjectForTransaction(sale);
        pointRewardData.setMerchantNo(3L);
        Assert.assertNotNull(pointRewardData);
        log.info("PointRewardData : " + pointRewardData.toString());

        // Get the LoyaltyComputation object for the loyalty program
        LoyaltyComputation loyaltyComputation = loyaltyProgramService.getLoyaltyComputation(loyaltyProgram);

        boolean isValid = loyaltyComputation.isProgramValidForTransaction(loyaltyProgram, sale);


        Assert.assertTrue(isValid);

    }

    /**
     * Returns a new object which specifies the the wanted result page.
     * @param pageIndex The index of the wanted result page
     * @return
     */
    private Pageable constructPageSpecification(int pageIndex) {
        Pageable pageSpecification = new PageRequest(pageIndex, 10, sortByLastNameAsc());
        return pageSpecification;
    }

    /**
     * Returns a Sort object which sorts persons in ascending order by using the last name.
     * @return
     */
    private Sort sortByLastNameAsc() {
        return new Sort(Sort.Direction.ASC, "salId");
    }




    @After
    public void tearDown() throws Exception {



        for(Merchant merchant : merchantSet ) {

            merchantService.deleteMerchant(merchant.getMerMerchantNo());

        }


        for(AccountBundlingSetting accountBundlingSetting : accountBundlingSettingSet ) {

            accountBundlingSettingService.deleteAccountBundlingSetting(accountBundlingSetting.getAbsId());

        }



        for(Customer customer : customerSet ) {

            List<AccumulatedRewardBalance> accumulatedRewardBalanceList = accumulatedRewardBalanceService.findByArbMerchantNoAndArbLoyaltyId(customer.getCusMerchantNo(),customer.getCusLoyaltyId());

            for(AccumulatedRewardBalance accumulatedRewardBalance : accumulatedRewardBalanceList ) {

                accumulatedRewardBalanceService.deleteAccumulatedRewardBalance(accumulatedRewardBalance.getArbId());

            }


            List<LinkedRewardBalance> linkedRewardBalanceList = linkedRewardBalanceService.findByLrbPrimaryLoyaltyIdAndLrbMerchantNo(customer.getCusLoyaltyId(), customer.getCusMerchantNo());

            for(LinkedRewardBalance linkedRewardBalance : linkedRewardBalanceList ) {

                linkedRewardBalanceService.deleteLinkedRewardBalance(linkedRewardBalance.getLrbId());

            }


            List<CustomerRewardBalance> customerRewardBalanceList = customerRewardBalanceService.findByCrbLoyaltyIdAndCrbMerchantNo(customer.getCusLoyaltyId(),customer.getCusMerchantNo());

            for(CustomerRewardBalance customerRewardBalance : customerRewardBalanceList ) {

                customerRewardBalanceService.deleteCustomerRewardBalance(customerRewardBalance);

            }


            List<LinkedLoyalty> linkedLoyaltyList = linkedLoyaltyService.findByLilParentCustomerNo(customer.getCusCustomerNo());

            for ( LinkedLoyalty linkedLoyalty : linkedLoyaltyList ) {

                linkedLoyaltyService.deleteLinkedLoyalty(linkedLoyalty.getLilId());

            }


            List<CustomerProgramSummary> customerProgramSummaryList = customerProgramSummaryService.findByCpsMerchantNoAndCpsLoyaltyId(customer.getCusMerchantNo(),customer.getCusLoyaltyId());

            for ( CustomerProgramSummary customerProgramSummary : customerProgramSummaryList ) {

                customerProgramSummaryService.deleteCustomerProgramSummary(customerProgramSummary);

            }


            // Create the set
            List<CustomerSummaryArchive> customerSummaryArchiveList = customerSummaryArchiveService.findByCsaMerchantNoAndCsaLoyaltyId(customer.getCusMerchantNo(),customer.getCusLoyaltyId());


            for( CustomerSummaryArchive customerSummaryArchive : customerSummaryArchiveList) {

                customerSummaryArchiveService.deleteCustomerSummaryArchive(customerSummaryArchive);

            }


            Page<Sale> salePage = saleService.findBySalMerchantNoAndSalLoyaltyId(customer.getCusMerchantNo(),customer.getCusLoyaltyId(),constructPageSpecification(0));
            List<Sale> saleList = Lists.newArrayList((Iterable<Sale>) salePage);

            for ( Sale sale1 : saleList ) {

                saleService.deleteSale(sale1.getSalId());

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


        // Create the set
        Set<MerchantProgramSummary> merchantProgramSummarySet = MerchantProgramSummaryFixture.standardMerchantProgramSummaries();

        for( MerchantProgramSummary merchantProgramSummary : merchantProgramSummarySet) {

            merchantProgramSummaryService.deleteMerchantProgramSummary(merchantProgramSummary);

        }

        // Create the set
        Set<MerchantRewardSummary> merchantRewardSummarySet = MerchantRewardSummaryFixture.standardMerchantRewardSummaries();

        for( MerchantRewardSummary merchantRewardSummary : merchantRewardSummarySet) {

            merchantRewardSummaryService.deleteMerchantRewardSummary(merchantRewardSummary);

        }


        for(LoyaltyProgram loyaltyProgram: loyaltyProgramSet) {

            loyaltyProgramService.deleteLoyaltyProgram(loyaltyProgram.getPrgProgramNo());

        }

       /* for(RewardCurrency rewardCurrency :rewardCurrencySet ) {

            rewardCurrencyService.deleteRewardCurrency(rewardCurrency.getRwdCurrencyId());

        }*/


        for(CustomerProfile customerProfile : customerProfileSet ) {

            customerProfileService.deleteCustomerProfile(customerProfile);

        }

        for(Tier tier : tierSet ) {

            tierService.deleteTier(tier.getTieId());

        }

        for(TierGroup tierGroup : tierGroupSet ) {

            tierGroupService.deleteTierGroup(tierGroup.getTigId());

        }

        for(MerchantSetting merchantSetting : merchantSettings){


            merchantSettingService.deleteMerchantSetting(merchantSetting);

        }

        Set<Product> products = ProductFixture.standardProducts();

        for(Product product: products) {

            Product delProduct = productService.findByPrdMerchantNoAndPrdCode(product.getPrdMerchantNo(), product.getPrdCode());

            if ( delProduct != null ) {
                productService.deleteProduct(delProduct.getPrdId());
            }

        }

        for(Product product:productSet ) {

            productService.deleteProduct(product.getPrdId());

        }

        for(PromotionalEvent promotionalEvent : promotionalEvents){

            promotionalEventService.deletePromotionalEvent(promotionalEvent);
        }

        for(CustomerPromotionalEvent customerPromotionalEvent : customerPromotionalSet){

            customerPromotionalEventService.deleteCustomerPromotionalEvent(customerPromotionalEvent.getCpeId());
        }
        for(CustomerReferral customerReferral : customerReferralSet){

            customerReferralService.deleteCustomerReferral(customerReferral.getCsrId());

        }
    }


}
