package com.inspirenetz.api.test.core.service;

import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import com.inspirenetz.api.core.dictionary.AccountBundlingLinkingType;
import com.inspirenetz.api.core.dictionary.CustomerActivityType;
import com.inspirenetz.api.core.dictionary.LinkedLoyaltyStatus;
import com.inspirenetz.api.core.dictionary.TransactionType;
import com.inspirenetz.api.core.domain.*;
import com.inspirenetz.api.core.repository.CustomerRewardBalanceRepository;
import com.inspirenetz.api.core.service.*;
import com.inspirenetz.api.rest.exception.InspireNetzException;
import com.inspirenetz.api.rest.resource.RewardCurrencyResource;
import com.inspirenetz.api.test.config.*;
import com.inspirenetz.api.test.core.fixture.*;
import com.inspirenetz.api.test.util.ControllerTestUtils;
import com.inspirenetz.api.util.DBUtils;
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

import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Created by sandheepgr on 30/4/14.
 */
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes  = {ApplicationTestConfig.class, PersistenceTestConfig.class, ServiceTestConfig.class, SecurityTestConfig.class, NotificationTestConfig.class})
public class CustomerRewardBalanceServiceTest {


    private static Logger log = LoggerFactory.getLogger(CustomerRewardBalanceServiceTest.class);

    @Autowired
    private LinkedRewardBalanceService linkedRewardBalanceService;

    @Autowired
    private AccountBundlingSettingService accountBundlingSettingService;

    @Autowired
    private PrimaryLoyaltyService primaryLoyaltyService;

    @Autowired
    private LinkedLoyaltyService linkedLoyaltyService;

    @Autowired
    private RewardCurrencyService rewardCurrencyService;

    @Autowired
    private CustomerRewardBalanceService customerRewardBalanceService;

    @Autowired
    private CustomerService customerService;

    @Autowired
    private AccumulatedRewardBalanceService accumulatedRewardBalanceService;

    @Autowired
    CustomerRewardExpiryService customerRewardExpiryService;

    @Autowired
    CustomerSubscriptionService customerSubscriptionService;

    @Autowired
    CustomerActivityService customerActivityService;

    @Autowired
    private CustomerRewardBalanceRepository customerRewardBalanceRepository;

    @Autowired
    ProductService productService;

    Set<CustomerRewardBalance> tempSet = new HashSet<>(0);

    Set<AccountBundlingSetting> accountBundlingSettingSet = new HashSet<>(0);

    Set<RewardCurrency> rewardCurrencySet = new HashSet<>(0);

    Set<Customer> customerSet = new HashSet<>(0);

    Set<RewardCurrency> rwdSet = new HashSet<>(0);

    Set<CustomerRewardBalance> crbSet = new HashSet<>(0);

    Set<AccumulatedRewardBalance> arbSet = new HashSet<>(0);

    Set<CustomerRewardExpiry> creSet = new HashSet<>(0);

    Set<CustomerSubscription> cusSubSet = new HashSet<>(0);


    @Autowired
    @Qualifier("uds")
    protected UserDetailsService userDetailsService;

    UsernamePasswordAuthenticationToken principal;

    @Before
    public void setUp() {

        // Set the principal
        principal = ControllerTestUtils.getPrincipal(ControllerTestUtils.TEST_MERCHANT_USER_LOGINID, userDetailsService);

        // Set the context
        SecurityContextHolder.getContext().setAuthentication(principal);

    }


    @Test
    public void test1Save(){

        CustomerRewardBalance customerRewardBalance = customerRewardBalanceService.saveCustomerRewardBalance(CustomerRewardBalanceFixture.standardRewardBalance());
        log.info(customerRewardBalance.toString());

        // Add to the set
        tempSet.add(customerRewardBalance);

        Assert.assertNotNull(customerRewardBalance.getCrbLoyaltyId());

    }


    @Test
    public void test2Update() {

        CustomerRewardBalance customerRewardBalance = CustomerRewardBalanceFixture.standardRewardBalance();
        customerRewardBalanceService.saveCustomerRewardBalance(customerRewardBalance);
        log.info("Original data" +customerRewardBalance.toString());

        // Add to the set
        tempSet.add(customerRewardBalance);


        CustomerRewardBalance updatedCustomerRewardBalance = CustomerRewardBalanceFixture.updatedStandardRewardBalance(customerRewardBalance);
        customerRewardBalanceService.saveCustomerRewardBalance(customerRewardBalance);
        log.info("Updated data" + customerRewardBalance.toString());


    }


    @Test
    public void test3FindByCrbId() {

        CustomerRewardBalance customerRewardBalance =  CustomerRewardBalanceFixture.standardRewardBalance();
        customerRewardBalanceService.saveCustomerRewardBalance(customerRewardBalance);
        log.info("Original data" +customerRewardBalance.toString());

        // Add to the set
        tempSet.add(customerRewardBalance);


        // Search the data
        CustomerRewardBalance searchBalance = customerRewardBalanceService.findByCrbId(customerRewardBalance.getCrbId());
        Assert.assertNotNull(searchBalance);
        log.info("Search Balance "+searchBalance);

    }


    @Test
    public void test4FindByCrbLoyaltyIdAndCrbMerchantNo() {

        Set<CustomerRewardBalance> customerRewardBalances = CustomerRewardBalanceFixture.standardCustomerRewardBalances();
        customerRewardBalanceRepository.save(customerRewardBalances);

        // Add to the set
        tempSet.addAll(customerRewardBalances);

        CustomerRewardBalance rewardBalance = CustomerRewardBalanceFixture.standardRewardBalance();

        List<CustomerRewardBalance> customerRewardBalanceList = customerRewardBalanceService.findByCrbLoyaltyIdAndCrbMerchantNo(rewardBalance.getCrbLoyaltyId(), rewardBalance.getCrbMerchantNo());
        Assert.assertTrue(!customerRewardBalanceList.isEmpty());
        log.info("CustomerRewardBalance List " + customerRewardBalanceList.toString());

    }




    @Test
    public void test5FindByCrbLoyaltyIdAndCrbMerchantNoAndCrbRewardCurrency() {

        Set<CustomerRewardBalance> customerRewardBalances = CustomerRewardBalanceFixture.standardCustomerRewardBalances();
        customerRewardBalanceRepository.save(customerRewardBalances);

        // Add to the set
        tempSet.addAll(customerRewardBalances);

        CustomerRewardBalance rewardBalance = CustomerRewardBalanceFixture.standardRewardBalance();

        CustomerRewardBalance searchRewardBalance = customerRewardBalanceRepository.findByCrbLoyaltyIdAndCrbMerchantNoAndCrbRewardCurrency(rewardBalance.getCrbLoyaltyId(),rewardBalance.getCrbMerchantNo(),rewardBalance.getCrbRewardCurrency());
        Assert.assertNotNull(searchRewardBalance);;
        log.info("CustomerRewardBalance data " + rewardBalance.toString());

    }



    @Test
    public void test6SearchBalances() throws InspireNetzException {

        // create the Customer
        Set<Customer> customers = CustomerFixture.standardCustomers();
        List<Customer> customerList = Lists.newArrayList((Iterable<Customer>) customers);
        customerService.saveAll(customerList);

        // ADd to the set for delete
        customerSet.addAll(customerList);

        // Create a reward currency
        RewardCurrency rewardCurrency = RewardCurrencyFixture.standardRewardCurrency();
        rewardCurrency = rewardCurrencyService.saveRewardCurrency(rewardCurrency);

        rewardCurrencySet.add(rewardCurrency);

        // Create the LinkedLoyalty
        LinkedLoyalty linkedLoyalty = LinkedLoyaltyFixture.standardLinkedLoyalty();
        linkedLoyalty.setLilLocation(customerList.get(0).getCusLocation());
        linkedLoyalty.setLilChildCustomerNo(customerList.get(1).getCusCustomerNo());
        linkedLoyalty.setLilParentCustomerNo(customerList.get(0).getCusCustomerNo());
        linkedLoyalty.setLilStatus(LinkedLoyaltyStatus.ACTIVE);
        linkedLoyalty = linkedLoyaltyService.saveLinkedLoyalty(linkedLoyalty);


        // Create a PrimaryLoyalty entry
        PrimaryLoyalty primaryLoyalty = PrimaryLoyaltyFixture.standardPrimaryLoyalty();
        primaryLoyalty.setPllLoyaltyId(customerList.get(0).getCusLoyaltyId());
        primaryLoyalty.setPllCustomerNo(customerList.get(0).getCusCustomerNo());
        primaryLoyalty = primaryLoyaltyService.savePrimaryLoyalty(primaryLoyalty);


        // Create the AccountBundlingSetting
        AccountBundlingSetting accountBundlingSetting = AccountBundlingSettingFixture.standardAccountBundlingSetting();
        accountBundlingSetting.setAbsLocation(0L);
        accountBundlingSetting.setAbsLinkingType(AccountBundlingLinkingType.CONSOLIDATE_TO_PRIMARY);
        accountBundlingSetting = accountBundlingSettingService.saveAccountBundlingSetting(accountBundlingSetting);
        Assert.assertNotNull(accountBundlingSetting.getAbsId());

        // Add to the set
        accountBundlingSettingSet.add(accountBundlingSetting);


        // Add the LinkedRewardBalance for the primary
        LinkedRewardBalance linkedRewardBalance = LinkedRewardBalanceFixture.standardLinkedRewardBalance();
        linkedRewardBalance.setLrbRewardCurrency(rewardCurrency.getRwdCurrencyId());
        linkedRewardBalance.setLrbPrimaryLoyaltyId(customerList.get(0).getCusLoyaltyId());
        linkedRewardBalance.setLrbRewardBalance(120.0);
        linkedRewardBalance =  linkedRewardBalanceService.saveLinkedRewardBalance(linkedRewardBalance);


        // Add the CustomerrewardBalance for secondary
        CustomerRewardBalance customerRewardBalance = CustomerRewardBalanceFixture.standardRewardBalance();
        customerRewardBalance.setCrbRewardCurrency(rewardCurrency.getRwdCurrencyId());
        customerRewardBalance.setCrbLoyaltyId(customerList.get(1).getCusLoyaltyId());
        customerRewardBalance =  customerRewardBalanceService.saveCustomerRewardBalance(customerRewardBalance);


        // Get the balance for dta
        List<CustomerRewardBalance> customerRewardBalanceList = customerRewardBalanceService.searchBalances(null,customerList.get(1).getCusLoyaltyId(),rewardCurrency.getRwdCurrencyId());
        Assert.assertNotNull(customerRewardBalanceList);
        log.info("CustomerRewardBalanceList : " +customerRewardBalanceList.toString());

    }

    @Test
    public void test7buyPoints() throws InspireNetzException {

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

        customerRewardBalance = customerRewardBalanceService.buyPoints(customer.getCusLoyaltyId(),1L,rewardCurrency.getRwdCurrencyId(),1000L);

        Assert.assertNotNull(customerRewardBalance);
    }

    @Test
    public void test11buyPointsLogging() throws InspireNetzException {

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

        Product product = ProductFixture.standardProduct();
        product.setPrdCode("PRD1001");
        product.setPrdMerchantNo(1L);
        product.setPrdCategory1("11");

        productService.saveProduct(product);


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

        customerRewardBalance = customerRewardBalanceService.buyPoints(customer.getCusLoyaltyId(),1L,rewardCurrency.getRwdCurrencyId(),1000L);

        Assert.assertNotNull(customerRewardBalance);
    }



    @Test
    public void test8DoRewardAdjustments() throws InspireNetzException {

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

        customerRewardBalance = customerRewardBalanceService.doRewardAdjustment(customer.getCusMerchantNo(),customer.getCusLoyaltyId(), TransactionType.REWARD_ADUJUSTMENT_AWARDING,rewardCurrency.getRwdCurrencyId(),100.0,false,0L,"123");

        Assert.assertNotNull(customerRewardBalance);
    }

    @Test
    public void test9SendRewardBalanceSMS() throws InspireNetzException {

        customerRewardBalanceService.sendRewardBalanceSMS(null,"9538828853",1L);

    }

    @Test
    public void test10SendRewardBalanceLogging() throws InspireNetzException {

        customerRewardBalanceService.sendRewardBalanceSMS(1L,"9538828853",1L);

        //get the customer activities
        Page<CustomerActivity> customerActivities = customerActivityService.searchCustomerActivities("9538828853", CustomerActivityType.POINT_ENQUIRY, java.sql.Date.valueOf("2014-01-01"), java.sql.Date.valueOf("2014-12-12"), 1L, constructPageSpecification(0));




    }

    @After
    public void tearDown() throws InspireNetzException {

        for(AccountBundlingSetting accountBundlingSetting : accountBundlingSettingSet ) {

            accountBundlingSettingService.deleteAccountBundlingSetting(accountBundlingSetting.getAbsId());

        }


        for(Customer customer : customerSet ) {


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


            PrimaryLoyalty  primaryLoyalty =  primaryLoyaltyService.findByPllLoyaltyId(customer.getCusLoyaltyId());

            if ( primaryLoyalty !=  null )
                primaryLoyaltyService.deletePrimaryLoyalty(primaryLoyalty.getPllId());



            customerService.deleteCustomer(customer.getCusCustomerNo());

        }



        for ( RewardCurrency rewardCurrency : rewardCurrencySet ) {

            rewardCurrencyService.deleteRewardCurrency(rewardCurrency.getRwdCurrencyId());

        }

        Set<CustomerRewardBalance> customerRewardBalances = CustomerRewardBalanceFixture.standardCustomerRewardBalances();

        for(CustomerRewardBalance customerRewardBalance: customerRewardBalances) {

            customerRewardBalanceService.deleteCustomerRewardBalance(customerRewardBalance);;

        }



        Set<CustomerRewardExpiry> customerRewardExpirys = CustomerRewardExpiryFixture.standardCustomerRewardExpirys();

        for(CustomerRewardExpiry customerRewardExpiry: customerRewardExpirys) {

            customerRewardExpiryService.deleteCustomerRewardExpiry(customerRewardExpiry);

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



}
