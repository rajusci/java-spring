package com.inspirenetz.api.test.core.service;

import com.inspirenetz.api.core.dictionary.IndicatorStatus;
import com.inspirenetz.api.core.dictionary.TierEvaluationPeriod;
import com.inspirenetz.api.core.dictionary.TierEvaluationPeriodType;
import com.inspirenetz.api.core.dictionary.TierEvaluationResult;
import com.inspirenetz.api.core.domain.*;
import com.inspirenetz.api.core.repository.TierRepository;
import com.inspirenetz.api.core.service.*;
import com.inspirenetz.api.rest.exception.InspireNetzException;
import com.inspirenetz.api.test.config.ApplicationTestConfig;
import com.inspirenetz.api.test.config.PersistenceTestConfig;
import com.inspirenetz.api.test.config.ServiceTestConfig;
import com.inspirenetz.api.test.core.fixture.*;
import com.inspirenetz.api.util.DBUtils;
import org.junit.*;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Created by sandheepgr on 30/4/14.
 */
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes  = {ApplicationTestConfig.class, PersistenceTestConfig.class, ServiceTestConfig.class})
public class TierServiceTest {


    private static Logger log = LoggerFactory.getLogger(TierServiceTest.class);

    @Autowired
    private TierService tierService;

    @Autowired
    private TierRepository tierRepository;

    @Autowired
    private TierGroupService tierGroupService;

    @Autowired
    private ProductService productService;

    @Autowired
    private CustomerService customerService;

    @Autowired
    private AccumulatedRewardBalanceService accumulatedRewardBalanceService;

    @Autowired
    private CustomerSubscriptionService customerSubscriptionService;

    @Autowired
    private Environment environment;



    @Before
    public void setUp() {}



    // Set holding the temporary variable
    Set<Tier> tempSet = new HashSet<>(0);

    // The set holding the tierGroups
    Set<TierGroup> tempTierGroupSet = new HashSet<>(0);

    // Set holding the CustomerSubscription
    Set<CustomerSubscription> customerSubscriptionSet = new HashSet<>(0);

    // Set holding the Proudct
    Set<Product>  productSet = new HashSet<>(0);

    // Set holding the Customer
    Set<Customer> customerSet = new HashSet<>(0);

    // Set holding the accumuldatedreward balance
    Set<AccumulatedRewardBalance> accumulatedRewardBalanceSet = new HashSet<>(0);



    @Before
    public void setup() {}

    @Test
    public void test1Create() throws InspireNetzException {


        Tier tier = tierService.saveTier(TierFixture.standardTier());
        log.info(tier.toString());

        // Add the tier to tempSet
        tempSet.add(tier);

        Assert.assertNotNull(tier.getTieId());

    }

    @Test
    public void test2Update() throws InspireNetzException {

        Tier tier = TierFixture.standardTier();
        tier = tierService.saveTier(tier);
        log.info("Original Tier " + tier.toString());

        // Add the tier to tempSet
        tempSet.add(tier);

        Tier updatedTier = TierFixture.updatedStandardTier(tier);
        updatedTier = tierService.saveTier(updatedTier);
        log.info("Updated Tier "+ updatedTier.toString());

    }

    @Test
    public void test3FindByTieId() throws InspireNetzException {

        // Save the tier
        Tier tier = TierFixture.standardTier();
        tier = tierService.saveTier(tier);

        // Add the tier to tempSet
        tempSet.add(tier);

        // GEt the tier now
        Tier searchTier = tierService.findByTieId(tier.getTieId());
        Assert.assertNotNull(searchTier);
        Assert.assertTrue(searchTier.getTieId() == tier.getTieId());
        log.info("Fetch tier : " + searchTier.toString());

    }

    @Test
    public void test4FindTyTierParentGroupAndTieName() throws InspireNetzException {

        // Save the tier
        Tier tier = TierFixture.standardTier();
        tier = tierService.saveTier(tier);

        // Add the tier to tempSet
        tempSet.add(tier);

        // Get the Tier
        Tier searchTier = tierService.findByTieParentGroupAndTieName(tier.getTieParentGroup(),tier.getTieName());
        Assert.assertNotNull(searchTier);
        Assert.assertTrue(searchTier.getTieId() == tier.getTieId());
        log.info("Fetch tier : " + searchTier.toString());

    }

    @Test
    public void test5FindByTieParentGroup() throws InspireNetzException {

        // Save the tier
        Tier tier = TierFixture.standardTier();
        tier = tierService.saveTier(tier);

        // Add the tier to tempSet
        tempSet.add(tier);

        // Get the Tier
        List<Tier> tierList = tierService.findByTieParentGroup(tier.getTieParentGroup());
        log.info("Fetch tier : " + tierList.toString());

    }

    @Test
    public void test6IsTierCodeExisting() throws InspireNetzException {

        // Create the tier
        Tier tier = TierFixture.standardTier();
        tier = tierService.saveTier(tier);
        Assert.assertNotNull(tier.getTieId());
        log.info("Tier created");

        // Add the tier to tempSet
        tempSet.add(tier);

        // Create a new tier
        Tier newTier = TierFixture.standardTier();
        boolean exists = tierService.isTierCodeDuplicateExisting(newTier);
        Assert.assertTrue(exists);
        log.info("Tier exists");


    }

    @Test
    public void test7DeleteTier() throws InspireNetzException {

        // Create the tier
        Tier tier = TierFixture.standardTier();
        tier = tierService.saveTier(tier);
        Assert.assertNotNull(tier.getTieId());
        log.info("Tier created");


        // Add the tier to tempSet
        tempSet.add(tier);

        // call the delete tier
        tierService.deleteTier(tier.getTieId());

        // Try searching for the tier
        Tier checkTier  = tierService.findByTieId(tier.getTieId());

        Assert.assertNull(checkTier);

        log.info("tier deleted");

    }

    @Test
    public void test8IsTierListValid() {

        // Create the firstTier
        Tier tier1 = TierFixture.standardTier();


        // Create the secondTier
        Tier tier2 = TierFixture.standardTier();



        // The list
        List<Tier> tierList = new ArrayList<>(0);

        tierList.add(tier1);
        tierList.add(tier2);




        // Check if its valid
        boolean isValid = tierService.isTierListValid(tierList);
        Assert.assertTrue(isValid);
        log.info("Tier list is valid");
    }

    @Test
    public void test9saveTierList() throws InspireNetzException {

        // Create the firstTier
        Tier tier1 = TierFixture.standardTier();

        // Create the secondTier
        Tier tier2 = TierFixture.standardTier();

        tier2.setTieName("tier name");

        // The list
        List<Tier> tierList = new ArrayList<>(0);

        tierList.add(tier1);
        tierList.add(tier2);

        // Add the list to the tempSet for deleting
        tempSet.addAll(tierList);


        // Save the tierList
        boolean saved = tierService.saveTierList(tierList, 1L);
        Assert.assertTrue(saved);
        log.info("Tierlist saved");


    }

    @Test
    public void test10IsByCalendarTimeValid() {

        boolean isValid = tierService.isByCalendarTimeValid(TierEvaluationPeriod.DAILY);
        Assert.assertTrue(isValid);
        log.info("isValid" + isValid);

    }

    @Test
    public void test11IsTierValidByApplicationGroup()throws InspireNetzException {

        // Create the Product
        Product product = ProductFixture.standardProduct();
        product = productService.saveProduct(product);


        // Add the product to set
        productSet.add(product);


        // Create the customer object
        Customer customer = CustomerFixture.standardCustomer();

        // Create the CustomerSubscription
        CustomerSubscription customerSubscription = CustomerSubscriptionFixture.standardCustomerSubscription();
        customerSubscription.setCsuProductCode(product.getPrdCode());
        customerSubscription = customerSubscriptionService.saveCustomerSubscription(customerSubscription);

        // Add the customerSubscription to set
        customerSubscriptionSet.add(customerSubscription);

        // Set the customer number to the customer number in the CustomerSubscription
        customer.setCusCustomerNo(customerSubscription.getCsuCustomerNo());;


        // Check if its valid
        boolean isValid = tierService.isTierValidByApplicableGroup(customer,product.getPrdCategory1());
        Assert.assertTrue(isValid);
        log.info("Group is valid");

    }

    @Test
    public void test12OrderTierList() {

        // Create the First tier
        Tier tier1 = TierFixture.standardTier();
        tier1.setTiePointValue1(500.0);

        // Create Second Tier
        Tier tier2 = TierFixture.standardTier();
        tier2.setTiePointValue1(1000.0);

        // Create third Tier
        Tier tier3 = TierFixture.standardTier();
        tier3.setTiePointValue1(1500.0);

        // Create fourth Tier
        Tier tier4 = TierFixture.standardTier();
        tier4.setTiePointValue1(2000.0);


        // ADd the tiers to list
        List<Tier> tierList = new ArrayList<>(0);
        tierList.add(tier2);
        tierList.add(tier4);
        tierList.add(tier3);
        tierList.add(tier1);

        // Sort the list
        tierList = tierService.orderTierList(tierList);
        log.info("Resultant list" + tierList.toString());
        Assert.assertTrue(tierList.get(0) == tier1);


    }

    @Test
    public void test13getTierEvaluationResult() {


        // Create the First tier
        Tier tier1 = TierFixture.standardTier();
        tier1.setTieId(1L);
        tier1.setTiePointValue1(500.0);

        // Create Second Tier
        Tier tier2 = TierFixture.standardTier();
        tier2.setTieId(2L);
        tier2.setTiePointValue1(1000.0);

        // Create third Tier
        Tier tier3 = TierFixture.standardTier();
        tier3.setTieId(3L);
        tier3.setTiePointValue1(1500.0);

        // Create fourth Tier
        Tier tier4 = TierFixture.standardTier();
        tier4.setTieId(4L);
        tier4.setTiePointValue1(2000.0);


        // ADd the tiers to list
        List<Tier> tierList = new ArrayList<>(0);
        tierList.add(tier2);
        tierList.add(tier4);
        tierList.add(tier3);
        tierList.add(tier1);

        // Call the operation for the tier1 as current tier and tier 4 as calcTier
        int result = tierService.getTierEvaluationResult(tier1,tier4,tierList);
        log.info("Result is : " + result);
        Assert.assertTrue(result == TierEvaluationResult.UPGRADE);



    }

    @Test
    public void test14IsTierValidByRewardBalance() {

        // Create the customer
        Customer customer = CustomerFixture.standardCustomer();

        // Create the AccumulatedRewardBalance
        AccumulatedRewardBalance accumulatedRewardBalance = AccumulatedRewardBalanceFixture.standardAccumulatedRewardBalance();
        accumulatedRewardBalance = accumulatedRewardBalanceService.saveAccumulatedRewardBalance(accumulatedRewardBalance);

        // Add the balance to set
        accumulatedRewardBalanceSet.add(accumulatedRewardBalance);

        // Create the TierGroup
        TierGroup tierGroup = TierGroupFixture.standardTierGroup();

        // Create the Tier
        Tier tier = TierFixture.standardTier();

        tier.setTiePointValue1(10.0);


        boolean isValid = tierService.isTierValidByRewardBalance(customer,tierGroup,tier);
        Assert.assertTrue(isValid);

    }

    @Test
    public void test15GetCurrentTierForCustomer() throws InspireNetzException {

        // Create the First tier
        Tier tier1 = TierFixture.standardTier();
        tier1.setTieId(1L);
        tier1.setTiePointValue1(500.0);

        // Create Second Tier
        Tier tier2 = TierFixture.standardTier();
        tier2.setTieId(2L);
        tier2.setTiePointValue1(1000.0);

        // Create third Tier
        Tier tier3 = TierFixture.standardTier();
        tier3.setTieId(3L);
        tier3.setTiePointValue1(1500.0);

        // Create fourth Tier
        Tier tier4 = TierFixture.standardTier();
        tier4.setTieId(4L);
        tier4.setTiePointValue1(2000.0);


        // ADd the tiers to list
        List<Tier> tierList = new ArrayList<>(0);
        tierList.add(tier2);
        tierList.add(tier4);
        tierList.add(tier3);
        tierList.add(tier1);


        // Create the customer
        Customer customer = CustomerFixture.standardCustomer();
        customer.setCusTier(tier1.getTieId());


        // Check if the customer tier is the same
        Tier retTier = tierService.getCurrentTierForCustomer(customer,tierList);
        Assert.assertNotNull(retTier);
        Assert.assertTrue(retTier == tier1);


    }

    @Test
    public void test16GetCalculatedTierForCustomer() {

        // Create the Tier Group
        TierGroup tierGroup = TierGroupFixture.standardTierGroup();

        // Create the REwarDbalance


        // Create the First tier
        Tier tier1 = TierFixture.standardTier();
        tier1.setTieId(1L);
        tier1.setTiePointValue1(500.0);

        // Create Second Tier
        Tier tier2 = TierFixture.standardTier();
        tier2.setTieId(2L);
        tier2.setTiePointValue1(1000.0);

        // Create third Tier
        Tier tier3 = TierFixture.standardTier();
        tier3.setTieId(3L);
        tier3.setTiePointValue1(1500.0);

        // Create fourth Tier
        Tier tier4 = TierFixture.standardTier();
        tier4.setTieId(4L);
        tier4.setTiePointValue1(2000.0);


        // ADd the tiers to list
        List<Tier> tierList = new ArrayList<>(0);
        tierList.add(tier2);
        tierList.add(tier4);
        tierList.add(tier3);
        tierList.add(tier1);

        // Create the Customer object
        Customer customer = CustomerFixture.standardCustomer();

        // Create the AccumulatedRewardBalance
        AccumulatedRewardBalance accumulatedRewardBalance = AccumulatedRewardBalanceFixture.standardAccumulatedRewardBalance();
        accumulatedRewardBalance.setArbRewardBalance(1100.0);
        accumulatedRewardBalance = accumulatedRewardBalanceService.saveAccumulatedRewardBalance(accumulatedRewardBalance);

        // Add the balance to set
        accumulatedRewardBalanceSet.add(accumulatedRewardBalance);

        // Get the tier
        Tier tier = tierService.getCalculatedTierForCustomer(customer,tierList,tierGroup);
        Assert.assertNotNull(tier);
        log.info("Returned tier : " + tier.toString());
        Assert.assertTrue(tier == tier2);


    }

    @Test
    public void test17GetApplicableTierGroupForCustomer() throws InspireNetzException {

        // Create the Product
        Product product = ProductFixture.standardProduct();
        product = productService.saveProduct(product);

        // Add the product to set
        productSet.add(product);

        // Create the customer object
        Customer customer = CustomerFixture.standardCustomer();


        // Create the CustomerSubscription
        CustomerSubscription customerSubscription = CustomerSubscriptionFixture.standardCustomerSubscription();
        customerSubscription.setCsuProductCode(product.getPrdCode());
        customerSubscription = customerSubscriptionService.saveCustomerSubscription(customerSubscription);

        // Add the customerSubscription to set
        customerSubscriptionSet.add(customerSubscription);

        // Set the customer number to the customer number in the CustomerSubscription
        customer.setCusCustomerNo(customerSubscription.getCsuCustomerNo());



        // Create the tierGroup
        TierGroup tierGroup1 = TierGroupFixture.standardTierGroup();
        tierGroup1.setTigApplicableGroup(product.getPrdCategory1());
        tierGroup1.setTigLocation(customer.getCusLocation());


        // Create the second group
        TierGroup tierGroup2 = TierGroupFixture.standardTierGroup();
        tierGroup2.setTigLocation(100L);

        // Create the List
        List<TierGroup> tierGroupList = new ArrayList<>(0);
        tierGroupList.add(tierGroup1);
        tierGroupList.add(tierGroup2);






        // Create the AccumulatedRewardBalance
        AccumulatedRewardBalance accumulatedRewardBalance = AccumulatedRewardBalanceFixture.standardAccumulatedRewardBalance();
        accumulatedRewardBalance.setArbRewardBalance(1100.0);
        accumulatedRewardBalance = accumulatedRewardBalanceService.saveAccumulatedRewardBalance(accumulatedRewardBalance);

        // Add the balance to set
        accumulatedRewardBalanceSet.add(accumulatedRewardBalance);


        // Get the customer insformation
        TierGroup tierGroup = tierService.getApplicableTierGroupForCustomer(customer,tierGroupList);
        Assert.assertNotNull(tierGroup);
        log.info("Assigned tier group " +tierGroup.toString());
        Assert.assertTrue(tierGroup == tierGroup1);





    }

    @Test
    public void test18EvaluateTierForCustomer() throws InspireNetzException {


        // Get the prepaid category
        String prepaidCategory = environment.getProperty("sku.category.prepaid-category-code");


        // Create the Product
        Product product = ProductFixture.standardProduct();
        product.setPrdCategory1(prepaidCategory);
        product = productService.saveProduct(product);

        // Add the product to set
        productSet.add(product);

        // Create the customer object
        Customer customer = CustomerFixture.standardCustomer();
        customer.setCusTier(3L);
        customer = customerService.saveCustomer(customer);

        // Add cusotmer to deleteSet
        customerSet.add(customer);

        // Create the CustomerSubscription
        CustomerSubscription customerSubscription = CustomerSubscriptionFixture.standardCustomerSubscription();
        customerSubscription.setCsuProductCode(product.getPrdCode());
        customerSubscription.setCsuCustomerNo(customer.getCusCustomerNo());
        customerSubscription = customerSubscriptionService.saveCustomerSubscription(customerSubscription);

        // Add the customerSubscription to set
        customerSubscriptionSet.add(customerSubscription);


        // Create the First tier
        Tier tier1 = TierFixture.standardTier();
        tier1.setTieId(1L);
        tier1.setTiePointInd(IndicatorStatus.YES);
        tier1.setTieAmountInd(IndicatorStatus.NO);
        tier1.setTiePointValue1(500.0);

        // Create Second Tier
        Tier tier2 = TierFixture.standardTier();
        tier2.setTieId(2L);
        tier1.setTiePointInd(IndicatorStatus.YES);
        tier1.setTieAmountInd(IndicatorStatus.NO);
        tier2.setTiePointValue1(1000.0);

        // Create third Tier
        Tier tier3 = TierFixture.standardTier();
        tier3.setTieId(3L);
        tier1.setTiePointInd(IndicatorStatus.YES);
        tier1.setTieAmountInd(IndicatorStatus.NO);
        tier3.setTiePointValue1(1500.0);

        // Create fourth Tier
        Tier tier4 = TierFixture.standardTier();
        tier4.setTieId(4L);
        tier1.setTiePointInd(IndicatorStatus.YES);
        tier1.setTieAmountInd(IndicatorStatus.NO);
        tier4.setTiePointValue1(2000.0);


        // ADd the tiers to list
        Set<Tier> tierSet = new HashSet<>(0);
        tierSet.add(tier2);
        tierSet.add(tier4);
        tierSet.add(tier3);
        tierSet.add(tier1);


        // Create the tierGroup
        TierGroup tierGroup1 = TierGroupFixture.standardTierGroup();
        tierGroup1.setTigApplicableGroup(product.getPrdCategory1());
        tierGroup1.setTigLocation(customer.getCusLocation());
        tierGroup1.setTigEvaluationPeriodCompType(TierEvaluationPeriodType.BY_CALENDAR);
        tierGroup1.setTierSet(tierSet);

        // Create the second group
        TierGroup tierGroup2 = TierGroupFixture.standardTierGroup();
        tierGroup2.setTigLocation(100L);

        // Create the List
        List<TierGroup> tierGroupList = new ArrayList<>(0);
        tierGroupList.add(tierGroup1);
        tierGroupList.add(tierGroup2);



        // Create the AccumulatedRewardBalance
        AccumulatedRewardBalance accumulatedRewardBalance = AccumulatedRewardBalanceFixture.standardAccumulatedRewardBalance();
        accumulatedRewardBalance.setArbLoyaltyId(customer.getCusLoyaltyId());
        accumulatedRewardBalance.setArbRewardBalance(2000.0);
        accumulatedRewardBalance = accumulatedRewardBalanceService.saveAccumulatedRewardBalance(accumulatedRewardBalance);

        // Add the balance to set
        accumulatedRewardBalanceSet.add(accumulatedRewardBalance);

        // Call the function
        tierService.evaluateTierForCustomer(customer,tierGroupList);

        // Get the customer information
        Customer searchCustomer = customerService.findByCusCustomerNo(customer.getCusCustomerNo());
        Assert.assertTrue(searchCustomer.getCusTier().longValue() == tier3.getTieId().longValue());

    }

    @Test
    public void test19ListTiersForGroup() {


        // Create the First tier
        Tier tier1 = TierFixture.standardTier();
        tier1.setTieId(1L);
        tier1.setTiePointValue1(500.0);

        // Create Second Tier
        Tier tier2 = TierFixture.standardTier();
        tier2.setTieId(2L);
        tier2.setTiePointValue1(1000.0);

        // Create third Tier
        Tier tier3 = TierFixture.standardTier();
        tier3.setTieId(3L);
        tier3.setTiePointValue1(1500.0);

        // Create fourth Tier
        Tier tier4 = TierFixture.standardTier();
        tier4.setTieId(4L);
        tier4.setTiePointValue1(2000.0);


        // ADd the tiers to list
        Set<Tier> tierSet = new HashSet<>(0);
        tierSet.add(tier2);
        tierSet.add(tier4);
        tierSet.add(tier3);
        tierSet.add(tier1);


        // Create the tierGroup
        TierGroup tierGroup1 = TierGroupFixture.standardTierGroup();
        tierGroup1.setTierSet(tierSet);

        // Create the second group
        TierGroup tierGroup2 = TierGroupFixture.standardTierGroup();
        tierGroup2.setTigName("POSTPAID");
        tierGroup2.setTigLocation(100L);

        // Create the List
        List<TierGroup> tierGroupList = new ArrayList<>(0);
        tierGroupList.add(tierGroup1);
        tierGroupList.add(tierGroup2);

        // SAve the tierGroup
        tierGroupService.saveAll(tierGroupList);

        // Add the list to the tempSEt
        tempTierGroupSet.addAll(tierGroupList);


        // Get the datea
        List<Tier> tierList = tierService.listTiersForGroup(tierGroup1.getTigMerchantNo());
        Assert.assertNotNull(tierList);
        Assert.assertTrue(!tierList.isEmpty());
        log.info("List is  " + tierList.toString());

    }

    @Test
    public void test20IsByCustomerSpecificDateValid() {

        // Create the Customer
        Customer customer = CustomerFixture.standardCustomer();
        customer.setCusTierLastEvaluated(DBUtils.covertToSqlDate("2014-09-18"));
        customer = customerService.saveCustomer(customer);

        // Add to customer set
        customerSet.add(customer);

        // Check if the period is valid
        boolean isValid = tierService.isByCustomerSpecificDateValid(customer,TierEvaluationPeriod.WEEKLY);
        Assert.assertTrue(isValid);
        log.info("Tier period is valid");


    }

    @Test
    public void getNextLowerTier() {

        // Create the Tier Group
        TierGroup tierGroup = TierGroupFixture.standardTierGroup();

        // Create the REwarDbalance


        // Create the First tier
        Tier tier1 = TierFixture.standardTier();
        tier1.setTieId(1L);
        tier1.setTiePointValue1(500.0);

        // Create Second Tier
        Tier tier2 = TierFixture.standardTier();
        tier2.setTieId(2L);
        tier2.setTiePointValue1(1000.0);

        // Create third Tier
        Tier tier3 = TierFixture.standardTier();
        tier3.setTieId(3L);
        tier3.setTiePointValue1(1500.0);

        // Create fourth Tier
        Tier tier4 = TierFixture.standardTier();
        tier4.setTieId(4L);
        tier4.setTiePointValue1(2000.0);


        // ADd the tiers to list
        List<Tier> tierList = new ArrayList<>(0);
        tierList.add(tier2);
        tierList.add(tier4);
        tierList.add(tier3);
        tierList.add(tier1);


        // Set the currentTier as tier3
        Tier currentTier = tier3;

        // Get the next lower tier
        Tier nextLowerTier = tierService.getNextLowerTier(currentTier,tierList);
        Assert.assertTrue(nextLowerTier.getTieId().longValue() == tier2.getTieId().longValue());
        log.info("Next lower tier is : " +nextLowerTier);

    }


    @After
    public void tearDown() throws InspireNetzException {

        for(Customer customer :customerSet ) {

            customerService.deleteCustomer(customer.getCusCustomerNo());

        }

        for(AccumulatedRewardBalance accumulatedRewardBalance : accumulatedRewardBalanceSet ) {

            accumulatedRewardBalanceService.deleteAccumulatedRewardBalance(accumulatedRewardBalance.getArbId());

        }

        for(Product product: productSet ) {

            productService.deleteProduct(product.getPrdId());
        }


        for(CustomerSubscription customerSubscription  : customerSubscriptionSet ) {

            customerSubscriptionService.deleteCustomerSubscription(customerSubscription.getCsuId());
        }


        for(Tier tier: tempSet) {

            tierRepository.delete(tier);

        }
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
        return new Sort(Sort.Direction.ASC, "tieName");
    }

}
