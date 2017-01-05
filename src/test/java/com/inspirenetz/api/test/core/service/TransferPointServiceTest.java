package com.inspirenetz.api.test.core.service;

import com.google.common.collect.Lists;
import com.inspirenetz.api.core.dictionary.CustomerActivityType;
import com.inspirenetz.api.core.dictionary.IndicatorStatus;
import com.inspirenetz.api.core.dictionary.LinkedLoyaltyStatus;
import com.inspirenetz.api.core.dictionary.TransferPointRequest;
import com.inspirenetz.api.core.domain.*;
import com.inspirenetz.api.core.service.*;
import com.inspirenetz.api.rest.exception.InspireNetzException;
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
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Created by sandheepgr on 30/4/14.
 */
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes  = {ApplicationTestConfig.class, PersistenceTestConfig.class, ServiceTestConfig.class, IntegrationTestConfig.class,SecurityTestConfig.class, NotificationTestConfig.class})
public class TransferPointServiceTest {


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
    CustomerActivityService customerActivityService;

    UsernamePasswordAuthenticationToken principal;

    @Autowired
    @Qualifier("uds")
    protected UserDetailsService userDetailsService;


    Set<Customer> customerSet = new HashSet<>(0);

    Set<RewardCurrency> rewardCurrencySet = new HashSet<>(0);

    Set<Tier> tierSet = new HashSet<>(0);

    Set<TransferPointSetting> transferPointSettingSet = new HashSet<>(0);

    Set<TierGroup> tierGroupSet = new HashSet<>(0);

    Set<LinkedLoyalty> linkedLoyaltySet = new HashSet<>(0);

    Set<LinkedRewardBalance> linkedRewardBalances = new HashSet<>(0);

    @Autowired
    LinkedRewardBalanceService linkedRewardBalanceService;


    private static Logger log = LoggerFactory.getLogger(TransferPointServiceTest.class);

    @Before
    public void setUp() {

        // Set the principal
        principal = ControllerTestUtils.getPrincipal(ControllerTestUtils.TEST_MERCHANT_USER_LOGINID, userDetailsService);

        // Set the context
        SecurityContextHolder.getContext().setAuthentication(principal);

    }
    @Test
    public void testGetDateForFirstDayOfCurrentMOnth() {

        Date date = generalUtils.getFirstDateForCurrentMonth();
        log.info("date" + date);

    }

    @Test
    public void testGetLastDateForCurrentMonth(){

        Date date = generalUtils.getLastDateForCurrentMonth();
        log.info("date" + date);

    }


    @Test
    public void testAddDaysToToday() {

        Date date = generalUtils.addDaysToToday(30);
        log.info("date : " + date);

    }


    @Test
    public void testTransferPoints() throws InspireNetzException {

        // Create the customers
        Set<Customer> customers = CustomerFixture.standardCustomers();
        List<Customer> customerList = Lists.newArrayList((Iterable<Customer>)customers);
        customerService.saveAll(customerList);

        Customer primary = CustomerFixture.standardCustomer();
        primary.setCusLoyaltyId("9000090000");
        primary = customerService.saveCustomer(primary);

        // Add to the set
        customerSet.addAll(customers);
        customerSet.add(primary);

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
        primary.setCusTier(tier.getTieId());
        primary = customerService.saveCustomer(primary);

        // Destination customer object
        Customer destCustomer = customerList.get(1);


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



      /*  // Create the TransferPointSetting object
        TransferPointSetting transferPointSetting = TransferPointSettingFixture.standardTransferPointSetting();
        transferPointSetting = transferPointSettingService.saveTransferPointSetting(transferPointSetting);
        Assert.assertNotNull(transferPointSetting);

        // Add to the TransferPointSettingset
        transferPointSettingSet.add(transferPointSetting);*/


        LinkedLoyalty linkedLoyalty = LinkedLoyaltyFixture.standardLinkedLoyalty();
        linkedLoyalty.setLilChildCustomerNo(customerList.get(0).getCusCustomerNo());
        linkedLoyalty.setLilParentCustomerNo(primary.getCusCustomerNo());
        linkedLoyalty.setLilStatus(LinkedLoyaltyStatus.ACTIVE);

        linkedLoyaltyService.saveLinkedLoyalty(linkedLoyalty);

        linkedLoyaltySet.add(linkedLoyalty);

        LinkedRewardBalance linkedRewardBalance = LinkedRewardBalanceFixture.standardLinkedRewardBalance();

        linkedRewardBalance.setLrbPrimaryLoyaltyId(customerList.get(1).getCusLoyaltyId());
        linkedRewardBalance.setLrbRewardBalance(1000.0);
        linkedRewardBalance.setLrbRewardCurrency(rewardCurrency.getRwdCurrencyId());
        linkedRewardBalances.add(linkedRewardBalance);
        // Create the TransferPointRequest object
        TransferPointRequest transferPointRequest = new TransferPointRequest();

        transferPointRequest.setFromLoyaltyId(primary.getCusLoyaltyId());
        transferPointRequest.setToLoyaltyId(destCustomer.getCusLoyaltyId());
        transferPointRequest.setFromRewardCurrency(rewardCurrency.getRwdCurrencyId());
        transferPointRequest.setToRewardCurrency(rewardCurrency.getRwdCurrencyId());
        transferPointRequest.setRewardQty(20.0);

        // make the call
        boolean isTranferred = transferPointService.transferPoints(transferPointRequest);
        Assert.assertTrue(isTranferred);



        // Now get the rewardbalance for the secodary
        CustomerRewardBalance secRewardBalance = customerRewardBalanceService.findByCrbLoyaltyIdAndCrbMerchantNoAndCrbRewardCurrency(destCustomer.getCusLoyaltyId(), destCustomer.getCusMerchantNo(), rewardCurrency.getRwdCurrencyId());
        Assert.assertNotNull(secRewardBalance);
        Assert.assertTrue(secRewardBalance.getCrbRewardBalance() == transferPointRequest.getRewardQty());



    }

    @Test
    public void testTransferPointsLogging() throws InspireNetzException {

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
        customerRewardExpiry.setCreExpiryDt(DBUtils.covertToSqlDate("2014-12-31"));
        customerRewardExpiry = customerRewardExpiryService.saveCustomerRewardExpiry(customerRewardExpiry);



      /*  // Create the TransferPointSetting object
        TransferPointSetting transferPointSetting = TransferPointSettingFixture.standardTransferPointSetting();
        transferPointSetting = transferPointSettingService.saveTransferPointSetting(transferPointSetting);
        Assert.assertNotNull(transferPointSetting);

        // Add to the TransferPointSettingset
        transferPointSettingSet.add(transferPointSetting);*/


        // Create the TransferPointRequest object
        TransferPointRequest transferPointRequest = new TransferPointRequest();

        transferPointRequest.setFromLoyaltyId(sourceCustomer.getCusLoyaltyId());
        transferPointRequest.setToLoyaltyId(destCustomer.getCusLoyaltyId());
        transferPointRequest.setFromRewardCurrency(121212L);
        transferPointRequest.setToRewardCurrency(rewardCurrency.getRwdCurrencyId());
        transferPointRequest.setRewardQty(20.0);

        // make the call
        boolean isTranferred = transferPointService.transferPoints(transferPointRequest);
        Assert.assertTrue(isTranferred);



        // Now get the rewardbalance for the secodary
        CustomerRewardBalance secRewardBalance = customerRewardBalanceService.findByCrbLoyaltyIdAndCrbMerchantNoAndCrbRewardCurrency(destCustomer.getCusLoyaltyId(), destCustomer.getCusMerchantNo(), rewardCurrency.getRwdCurrencyId());
        Assert.assertNotNull(secRewardBalance);
        Assert.assertTrue(secRewardBalance.getCrbRewardBalance() == transferPointRequest.getRewardQty());

        //get the customer activities
        Page<CustomerActivity> customerActivities = customerActivityService.searchCustomerActivities(sourceCustomer.getCusLoyaltyId(), CustomerActivityType.TRANSFER_POINT, java.sql.Date.valueOf("2014-01-01"), java.sql.Date.valueOf("2014-12-14"), sourceCustomer.getCusMerchantNo(), constructPageSpecification(0));

        Assert.assertTrue(customerActivities.hasContent());

    }

    @After
    public void tearDown() throws InspireNetzException {


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
        for(LinkedRewardBalance linkedRewardBalance : linkedRewardBalances){

            linkedRewardBalanceService.deleteLinkedRewardBalance(linkedRewardBalance.getLrbId());
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
