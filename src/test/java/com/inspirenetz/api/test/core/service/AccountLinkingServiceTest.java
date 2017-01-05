package com.inspirenetz.api.test.core.service;

import com.google.common.collect.Lists;
import com.inspirenetz.api.core.dictionary.*;
import com.inspirenetz.api.core.domain.*;
import com.inspirenetz.api.core.service.*;
import com.inspirenetz.api.rest.exception.InspireNetzException;
import com.inspirenetz.api.test.config.ApplicationTestConfig;
import com.inspirenetz.api.test.config.PersistenceTestConfig;
import com.inspirenetz.api.test.config.ServiceTestConfig;
import com.inspirenetz.api.test.core.fixture.*;
import org.junit.*;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
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
@ContextConfiguration(classes  = {ApplicationTestConfig.class, PersistenceTestConfig.class, ServiceTestConfig.class})
public class AccountLinkingServiceTest {


    private static Logger log = LoggerFactory.getLogger(AccountLinkingServiceTest.class);


    @Autowired
    private LinkRequestService linkRequestService;

    @Autowired
    private CustomerService customerService;

    @Autowired
    private AccountLinkingService accountLinkingService;

    @Autowired
    private AccountBundlingSettingService accountBundlingSettingService;

    @Autowired
    private PartyApprovalService partyApprovalService;

    @Autowired
    private AccumulatedRewardBalanceService accumulatedRewardBalanceService;

    @Autowired
    private LinkedRewardBalanceService linkedRewardBalanceService;

    @Autowired
    private CustomerRewardBalanceService customerRewardBalanceService;

    @Autowired
    private CustomerRewardExpiryService customerRewardExpiryService;

    @Autowired
    private LinkedLoyaltyService linkedLoyaltyService;

    @Autowired
    private TransactionService transactionService;



    private Set<LinkRequest> linkRequestSet = new HashSet<>(0);

    private Set<Customer> customerSet = new HashSet<>(0);

    private Set<AccountBundlingSetting> accountBundlingSettingSet = new HashSet<>(0);

    private Set<PartyApproval> partyApprovalSet = new HashSet<>(0);





    @Before
    public void setUp() {


    }



    @Test
    public void testProcessLinkRequest() throws InspireNetzException {

        // create the Customer
        Set<Customer> customers = CustomerFixture.standardCustomers();
        List<Customer> customerList = Lists.newArrayList((Iterable<Customer>)customers);
        customerService.saveAll(customerList);

        // ADd to the set for delete
        customerSet.addAll(customerList);



        // Create the AccountBundlingSetting
        AccountBundlingSetting accountBundlingSetting = AccountBundlingSettingFixture.standardAccountBundlingSetting();
        accountBundlingSetting.setAbsLocation(0L);
        accountBundlingSetting.setAbsLinkingType(AccountBundlingLinkingType.CONSOLIDATE_TO_PRIMARY);
        accountBundlingSetting = accountBundlingSettingService.saveAccountBundlingSetting(accountBundlingSetting);
        Assert.assertNotNull(accountBundlingSetting.getAbsId());

        // Add to the set
        accountBundlingSettingSet.add(accountBundlingSetting);



        // Create the linkRequest
        LinkRequest linkRequest = LinkRequestFixture.standardLinkRequest();
        linkRequest.setLrqStatus(LinkRequestStatus.PENDING);
        linkRequest.setLrqParentCustomer(customerList.get(0).getCusCustomerNo());
        linkRequest.setLrqSourceCustomer(customerList.get(1).getCusCustomerNo());
        linkRequest = linkRequestService.saveLinkRequest(linkRequest);
        Assert.assertNotNull(linkRequest.getLrqId());
        log.info("LinkREQuest : " + linkRequest);

        // Add to the list for deletion
        linkRequestSet.add(linkRequest);




        // Create the PartyApproval
        PartyApproval partyApproval = PartyApprovalFixture.standardPartyApproval();
        partyApproval.setPapApprover(customerList.get(1).getCusCustomerNo());
        partyApproval.setPapRequestor(customerList.get(0).getCusCustomerNo());
        partyApproval.setPapStatus(PartyApprovalStatus.ACCEPTED);
        partyApproval.setPapRequest(linkRequest.getLrqId());
        partyApproval = partyApprovalService.savePartyApproval(partyApproval);


        // Add to the tempset
        partyApprovalSet.add(partyApproval);


        // Add the AccumulatedRewardBalance for the secondary
        AccumulatedRewardBalance accumulatedRewardBalance = AccumulatedRewardBalanceFixture.standardAccumulatedRewardBalance();
        accumulatedRewardBalance.setArbLoyaltyId(customerList.get(1).getCusLoyaltyId());
        accumulatedRewardBalance =  accumulatedRewardBalanceService.saveAccumulatedRewardBalance(accumulatedRewardBalance);


        // Add the AccumulatedRewardBalance for the primary
        AccumulatedRewardBalance accumulatedRewardBalance1 = AccumulatedRewardBalanceFixture.standardAccumulatedRewardBalance();
        accumulatedRewardBalance1.setArbLoyaltyId(customerList.get(0).getCusLoyaltyId());
        accumulatedRewardBalance1.setArbRewardBalance(20.0);
        accumulatedRewardBalance1 =  accumulatedRewardBalanceService.saveAccumulatedRewardBalance(accumulatedRewardBalance1);


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
        customerRewardExpiry = customerRewardExpiryService.saveCustomerRewardExpiry(customerRewardExpiry);


        // Add the CustomerRewardExpiry for primary
        CustomerRewardExpiry customerRewardExpiry1 = CustomerRewardExpiryFixture.standardRewardExpiry();
        customerRewardExpiry1.setCreLoyaltyId(customerList.get(0).getCusLoyaltyId());
        customerRewardExpiry1 = customerRewardExpiryService.saveCustomerRewardExpiry(customerRewardExpiry1);





        boolean isLinked = accountLinkingService.processLinkRequest(linkRequest, customerList.get(0).getCusLoyaltyId());
        Assert.assertTrue(isLinked);


    }




    @Test
    public void testProcessUnLinkRequest() throws InspireNetzException {

        // create the Customer
        Set<Customer> customers = CustomerFixture.standardCustomers();
        List<Customer> customerList = Lists.newArrayList((Iterable<Customer>)customers);
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



        // Create the linkRequest
        LinkRequest linkRequest = LinkRequestFixture.standardLinkRequest();
        linkRequest.setLrqStatus(LinkRequestStatus.PENDING);
        linkRequest.setLrqType(LinkRequestType.UNLINK);
        linkRequest.setLrqParentCustomer(customerList.get(0).getCusCustomerNo());
        linkRequest.setLrqSourceCustomer(customerList.get(1).getCusCustomerNo());
        linkRequest = linkRequestService.saveLinkRequest(linkRequest);
        Assert.assertNotNull(linkRequest.getLrqId());
        log.info("LinkREQuest : " + linkRequest);

        // Add to the list for deletion
        linkRequestSet.add(linkRequest);




        // Create the PartyApproval
        PartyApproval partyApproval = PartyApprovalFixture.standardPartyApproval();
        partyApproval.setPapApprover(customerList.get(1).getCusCustomerNo());
        partyApproval.setPapRequestor(customerList.get(0).getCusCustomerNo());
        partyApproval.setPapStatus(PartyApprovalStatus.ACCEPTED);
        partyApproval.setPapRequest(linkRequest.getLrqId());
        partyApproval = partyApprovalService.savePartyApproval(partyApproval);


        // Add to the tempset
        partyApprovalSet.add(partyApproval);


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



        // Add the CustomerRewardExpiry for secondary
        CustomerRewardExpiry customerRewardExpiry = CustomerRewardExpiryFixture.standardRewardExpiry();
        customerRewardExpiry.setCreLoyaltyId(customerList.get(1).getCusLoyaltyId());
        customerRewardExpiry = customerRewardExpiryService.saveCustomerRewardExpiry(customerRewardExpiry);


        // Add the CustomerRewardExpiry for primary
        CustomerRewardExpiry customerRewardExpiry1 = CustomerRewardExpiryFixture.standardRewardExpiry();
        customerRewardExpiry1.setCreLoyaltyId(customerList.get(0).getCusLoyaltyId());
        customerRewardExpiry1 = customerRewardExpiryService.saveCustomerRewardExpiry(customerRewardExpiry1);



        boolean isUnlinked = accountLinkingService.processLinkRequest(linkRequest,customerList.get(0).getCusLoyaltyId() );
        Assert.assertTrue(isUnlinked);


    }




    @After
    public void tearDown() throws InspireNetzException {

        for(LinkRequest linkRequest : linkRequestSet ) {

            linkRequestService.deleteLinkRequest(linkRequest.getLrqId());

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


            List<CustomerRewardExpiry> customerRewardExpiryList = customerRewardExpiryService.findByCreMerchantNoAndCreLoyaltyId(customer.getCusMerchantNo(),customer.getCusLoyaltyId());

            for (CustomerRewardExpiry customerRewardExpiry : customerRewardExpiryList ) {

                customerRewardExpiryService.deleteCustomerRewardExpiry(customerRewardExpiry);

            }


            List<LinkedLoyalty> linkedLoyaltyList = linkedLoyaltyService.findByLilParentCustomerNo(customer.getCusCustomerNo());

            for ( LinkedLoyalty linkedLoyalty : linkedLoyaltyList ) {

                linkedLoyaltyService.deleteLinkedLoyalty(linkedLoyalty.getLilId());

            }


            customerService.deleteCustomer(customer.getCusCustomerNo());

        }


        for(PartyApproval partyApproval : partyApprovalSet ) {

            partyApprovalService.deletePartyApproval(partyApproval.getPapId());

        }



    }

}
