package com.inspirenetz.api.test.core.service;

import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import com.inspirenetz.api.core.dictionary.*;
import com.inspirenetz.api.core.domain.*;
import com.inspirenetz.api.core.repository.CustomerRepository;
import com.inspirenetz.api.core.repository.PartyApprovalRepository;
import com.inspirenetz.api.core.service.*;
import com.inspirenetz.api.rest.exception.InspireNetzException;
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
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Created by saneeshci on 29/8/14.
 */
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes  = {ApplicationTestConfig.class, PersistenceTestConfig.class, ServiceTestConfig.class, SecurityTestConfig.class, NotificationTestConfig.class})
public class PartyApprovalServiceTest {


    private static Logger log = LoggerFactory.getLogger(PartyApprovalServiceTest.class);

    @Autowired
    private PartyApprovalService partyApprovalService;

    @Autowired
    private CustomerService customerService;


    @Autowired
    private LinkRequestService linkRequestService;

    UsernamePasswordAuthenticationToken principal;

    @Autowired
    @Qualifier("uds")
    protected UserDetailsService userDetailsService;

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private AccountBundlingSettingService accountBundlingSettingService;

    @Autowired
    private PartyApprovalRepository partyApprovalRepository;

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
    CustomerActivityService customerActivityService;

    @Autowired
    PointTransferRequestService pointTransferRequestService;

    @Autowired
    LinkedRewardBalanceService linkedRewardBalanceService;

    Set<PartyApproval> tempSet = new HashSet<>(0);

    Set<Customer> tempCustomerSet = new HashSet<>(0);

    Set<LinkRequest> linkRequestSet = new HashSet<>(0);

    Set<Customer> customerSet = new HashSet<>(0);

    Set<RewardCurrency> rewardCurrencySet = new HashSet<>(0);

    Set<Tier> tierSet = new HashSet<>(0);

    Set<TransferPointSetting> transferPointSettingSet = new HashSet<>(0);

    Set<TierGroup> tierGroupSet = new HashSet<>(0);

    Set<LinkedLoyalty> linkedLoyaltySet = new HashSet<>(0);

    Set<LinkedRewardBalance> linkedRewardBalances = new HashSet<>(0);


    @Before
    public void setUp() {

        // Set the principal
        principal = ControllerTestUtils.getPrincipal(ControllerTestUtils.TEST_MERCHANT_USER_LOGINID, userDetailsService);

        // Set the context
        SecurityContextHolder.getContext().setAuthentication(principal);
    }



    @Test
    public void test1FindByPapId() {

        // Get the standard partyApproval
        PartyApproval partyApproval = PartyApprovalFixture.standardPartyApproval();
        partyApproval = partyApprovalService.savePartyApproval(partyApproval);

        // Add the tempset
        tempSet.add(partyApproval);

        PartyApproval partyApprovalObj = partyApprovalService.findByPapId(partyApproval.getPapId());
        log.info("partyApprovals by liaId " + partyApprovalObj.toString());

        List<PartyApproval> partyApprovals = new ArrayList<PartyApproval>();
        partyApprovals.add(partyApprovalObj);
        Set<PartyApproval> partyApprovalSet = Sets.newHashSet((Iterable<PartyApproval>) partyApprovals);
        log.info("partyApproval list "+ partyApprovalSet.toString());

    }



    @Test
    public void test2FindByPapApproverAndPapRequestorAndPapTypeAndPapLinkRequest() {

        Set<Customer> customers = CustomerFixture.standardCustomers();
        List<Customer> customerList = Lists.newArrayList((Iterable<Customer>)customers);
        customerService.saveAll(customerList);
        tempCustomerSet.addAll(customerList);

        // Create the partyApproval
        PartyApproval partyApproval = PartyApprovalFixture.standardPartyApproval();
        partyApproval.setPapApprover(customerList.get(0).getCusCustomerNo());
        partyApproval.setPapRequestor(customerList.get(1).getCusCustomerNo());

        partyApprovalService.savePartyApproval(partyApproval);
        Assert.assertNotNull(partyApproval.getPapId());
        log.info("PartyApproval created");

        // Add the tempset
        tempSet.add(partyApproval);

        PartyApproval fetchPartyApproval = partyApprovalService.getPartyApprovalForLinkRequest(partyApproval.getPapApprover(), partyApproval.getPapRequestor(), partyApproval.getPapType());
        Assert.assertNotNull(fetchPartyApproval);
        log.info("Fetched partyApproval info" + partyApproval.toString());

    }



    @Test
    public void test3FindByPapApproverAndPapType() {

        // Create the partyApproval
        PartyApproval partyApproval = PartyApprovalFixture.standardPartyApproval();
        partyApprovalService.savePartyApproval(partyApproval);
        Assert.assertNotNull(partyApproval.getPapId());
        log.info("PartyApproval created");

        // Add the tempset
        tempSet.add(partyApproval);

        // Check the partyApproval name
        List<PartyApproval> partyApprovals = partyApprovalService.findByPapApproverAndPapType(partyApproval.getPapApprover(), partyApproval.getPapType());
        Assert.assertTrue(partyApprovals.size()>0);
        Set<PartyApproval> partyApprovalSet = Sets.newHashSet((Iterable<PartyApproval>) partyApprovals);
        log.info("partyApproval list "+ partyApprovalSet.toString());


    }

    @Test
    public void test5DeletePartyApproval() {

        Set<Customer> customers = CustomerFixture.standardCustomers();
        List<Customer> customerList = Lists.newArrayList((Iterable<Customer>)customers);
        customerService.saveAll(customerList);
        tempCustomerSet.addAll(customerList);

        // Create the partyApproval
        PartyApproval partyApproval = PartyApprovalFixture.standardPartyApproval();
        partyApproval.setPapApprover(customerList.get(0).getCusCustomerNo());
        partyApproval.setPapRequestor(customerList.get(1).getCusCustomerNo());

        partyApproval = partyApprovalService.savePartyApproval(partyApproval);
        Assert.assertNotNull(partyApproval.getPapId());
        log.info("PartyApproval created");

        // Add the tempset
        tempSet.add(partyApproval);

        // call the delete partyApproval
        partyApprovalService.deletePartyApproval(partyApproval.getPapId());

        // Try searching for the partyApproval
        PartyApproval checkPartyApproval = partyApprovalService.getPartyApprovalForLinkRequest(partyApproval.getPapApprover(),partyApproval.getPapRequestor(),partyApproval.getPapType());

        Assert.assertNull(checkPartyApproval);

        log.info("partyApproval deleted");

        tempSet.remove(partyApproval);

    }

    @Test
    public void test7isExpiredPartyApproval() {

        // Create the partyApproval
        PartyApproval partyApproval = PartyApprovalFixture.standardPartyApproval();
        partyApproval = partyApprovalService.savePartyApproval(partyApproval);
        Assert.assertNotNull(partyApproval.getPapId());
        log.info("PartyApproval created");
        // Add the tempset
        tempSet.add(partyApproval);

        // call the delete partyApproval
        boolean isExpired = partyApprovalService.isPartyApprovalRequestExpired(partyApproval);
        log.info("expiry sratus"+isExpired);
        Assert.assertFalse(isExpired);

        tempSet.remove(partyApproval);

    }

    @Test
    public void test7changeExpiryStatus() throws InspireNetzException {

        Set<Customer> customers = CustomerFixture.standardCustomers();
        List<Customer> customerList = Lists.newArrayList((Iterable<Customer>)customers);
        customerService.saveAll(customerList);
        tempCustomerSet.addAll(customerList);

        // Create the partyApproval
        PartyApproval partyApproval = PartyApprovalFixture.standardPartyApproval();
        partyApproval.setPapApprover(customerList.get(0).getCusCustomerNo());
        partyApproval.setPapRequestor(customerList.get(1).getCusCustomerNo());

        partyApproval = partyApprovalService.savePartyApproval(partyApproval);
        Assert.assertNotNull(partyApproval.getPapId());

        log.info("PartyApproval created");
        log.info("created object"+partyApproval.toString());
        // Add the tempset
        tempSet.add(partyApproval);

        log.info("parameters"+partyApproval.toString());
        // call the delete partyApproval
        boolean isChanged = partyApprovalService.changePartyApprovalForRequest(1L, customerList.get(0).getCusLoyaltyId(), customerList.get(1).getCusLoyaltyId(), partyApproval.getPapType(), PartyApprovalStatus.ACCEPTED);
        log.info("Changed Status"+ isChanged);
        Assert.assertTrue(isChanged);

        tempSet.remove(partyApproval);

    }

    @Test
    public void test7GetPartyApprovals() {


        // Create the customer
        Customer customerA = CustomerFixture.standardCustomer();
        customerA.setCusCustomerNo(968L);
        customerA.setCusLoyaltyId("9999888877776663");
        customerA.setCusUserNo(140L);
        customerA = customerService.saveCustomer(customerA);
        tempCustomerSet.add(customerA);
        Customer customerB = CustomerFixture.standardCustomer();
        customerB.setCusCustomerNo(969L);
        customerB.setCusLoyaltyId("9999888877776664");
        customerB = customerService.saveCustomer(customerB);
        tempCustomerSet.add(customerB);
        Customer customerC = CustomerFixture.standardCustomer();
        customerC.setCusCustomerNo(970L);
        customerC.setCusLoyaltyId("9999888877776665");
        customerC = customerService.saveCustomer(customerC);
        tempCustomerSet.add(customerC);


        // Create the PartyApprovals
        PartyApproval partyApproval1 = PartyApprovalFixture.standardPartyApproval();
        partyApproval1.setPapApprover(customerA.getCusCustomerNo());
        partyApproval1.setPapRequestor(customerB.getCusCustomerNo());
        partyApproval1.setPapStatus(2);
        partyApprovalService.savePartyApproval(partyApproval1);
        tempSet.add(partyApproval1);
        PartyApproval partyApproval2 = PartyApprovalFixture.standardPartyApproval();
        partyApproval2.setPapApprover(customerA.getCusCustomerNo());
        partyApproval2.setPapRequestor(customerC.getCusCustomerNo());
        partyApprovalService.savePartyApproval(partyApproval2);
        tempSet.add(partyApproval2);

        List<PartyApproval> partyApprovals = partyApprovalService.findByPapApprover(customerA.getCusMerchantNo(),customerA.getCusLoyaltyId());
        Assert.assertTrue(partyApprovals.size()>0);
        log.info("Fetched partyApproval info" + partyApprovals.toString());

    }
    @Test
    public void expiringLinkRequest() throws InspireNetzException{


        // Create the customer
        Customer customerA = CustomerFixture.standardCustomer();
        customerA.setCusCustomerNo(968L);
        customerA.setCusLoyaltyId("99911100118337888771");
        customerA.setCusUserNo(140L);
        customerA = customerService.saveCustomer(customerA);
        tempCustomerSet.add(customerA);
        Customer customerB = CustomerFixture.standardCustomer();
        customerB.setCusCustomerNo(969L);
        customerB.setCusLoyaltyId("9911111000009987388");
        customerB = customerService.saveCustomer(customerB);
        tempCustomerSet.add(customerB);
        Customer customerC = CustomerFixture.standardCustomer();
        customerC.setCusCustomerNo(970L);
        customerC.setCusLoyaltyId("99000001111854138837");
        customerC = customerService.saveCustomer(customerC);
        tempCustomerSet.add(customerC);


        // Create the PartyApprovals
        PartyApproval partyApproval1 = PartyApprovalFixture.standardPartyApproval();
        partyApproval1.setPapApprover(customerA.getCusCustomerNo());
        partyApproval1.setPapRequestor(customerB.getCusCustomerNo());
        partyApproval1.setPapStatus(2);
        partyApprovalService.savePartyApproval(partyApproval1);
        tempSet.add(partyApproval1);

        //create link request
        LinkRequest linkRequest = linkRequestService.saveLinkRequest(LinkRequestFixture.standardLinkRequest());

        Long customerNo = customerA.getCusCustomerNo();
        linkRequest.setLrqSourceCustomer(customerNo);

        Long customerNo1 =customerB.getCusCustomerNo();
        linkRequest.setLrqParentCustomer(customerNo1);



        linkRequestService.saveLinkRequest(linkRequest);

        AccountBundlingSetting accountBundlingSetting =new AccountBundlingSetting();

        accountBundlingSetting = accountBundlingSettingService.getDefaultAccountBundlingSetting(1L);
        accountBundlingSetting.setAbsConfirmationExpiryLimit(0);

        accountBundlingSettingService.saveAccountBundlingSetting(accountBundlingSetting);

        log.info(linkRequest.toString());

        // Add the items
        linkRequestSet.add(linkRequest);

        Assert.assertNotNull(linkRequest.getLrqId());


        partyApprovalService.expiringLinkRequest(customerA.getCusLoyaltyId(),1L, ApprovalExpiryFilterType.APPROVER);

        log.info("successfully expired");



    }

    @Test
    public void testChangePartyApprovalForTransfer() throws InspireNetzException {

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
        PointTransferRequest pointTransferRequest = new PointTransferRequest();

        pointTransferRequest.setPtrSource(primary.getCusLoyaltyId());
        pointTransferRequest.setPtrDestination(destCustomer.getCusLoyaltyId());
        pointTransferRequest.setPtrSourceCurrency(rewardCurrency.getRwdCurrencyId());
        pointTransferRequest.setPtrDestCurrency(rewardCurrency.getRwdCurrencyId());
        pointTransferRequest.setPtrRewardQty(20.0);
        pointTransferRequest.setPtrSourceCusNo(primary.getCusCustomerNo());
        pointTransferRequest.setPtrApproverCusNo(customerList.get(0).getCusCustomerNo());
        pointTransferRequest.setPtrMerchantNo(1L);
        pointTransferRequest.setPtrApprover(customerList.get(0).getCusLoyaltyId());
        pointTransferRequest.setPtrStatus(TransferRequestStatus.NEW);

        pointTransferRequestService.savePointTransferRequest(pointTransferRequest);

        PartyApproval partyApproval = PartyApprovalFixture.standardPartyApproval();
        partyApproval.setPapApprover(customerList.get(0).getCusCustomerNo());
        partyApproval.setPapReference(customerList.get(1).getCusLoyaltyId());
        partyApproval.setPapRequest(pointTransferRequest.getPtrId());
        partyApproval.setPapRequestor(primary.getCusCustomerNo());
        partyApproval.setPapStatus(PartyApprovalStatus.QUEUED);
        partyApproval.setPapType(PartyApprovalType.PARTY_APPROVAL_TRANSFER_POINT_REQUEST);

        partyApprovalService.savePartyApproval(partyApproval);

        tempSet.add(partyApproval);
        // make the call
        partyApprovalService.changePartyApprovalForTransferPointRequest(pointTransferRequest.getPtrMerchantNo(),pointTransferRequest.getPtrApprover(),pointTransferRequest.getPtrSource(),PartyApprovalType.PARTY_APPROVAL_TRANSFER_POINT_REQUEST,pointTransferRequest.getPtrDestination(),TransferRequestStatus.REJECTED);

        // Now get the rewardbalance for the secodary
        CustomerRewardBalance secRewardBalance = customerRewardBalanceService.findByCrbLoyaltyIdAndCrbMerchantNoAndCrbRewardCurrency(destCustomer.getCusLoyaltyId(), destCustomer.getCusMerchantNo(), rewardCurrency.getRwdCurrencyId());
        Assert.assertNotNull(secRewardBalance);


    }

    @After
    public void tearDown() throws InspireNetzException{



        for(PartyApproval partyApproval : tempSet) {


            if ( partyApproval != null ) {
                partyApprovalRepository.delete(partyApproval);
            }

        }
        for(Customer  customer : tempCustomerSet) {


            if ( customer != null ) {
                customerRepository.delete(customer);
            }

        }

        for(LinkRequest linkRequest: linkRequestSet) {

            linkRequestService.deleteLinkRequest(linkRequest.getLrqId());

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
            for(LinkedRewardBalance linkedRewardBalance : linkedRewardBalances){

                linkedRewardBalanceService.deleteLinkedRewardBalance(linkedRewardBalance.getLrbId());
            }

        }
    }


}
