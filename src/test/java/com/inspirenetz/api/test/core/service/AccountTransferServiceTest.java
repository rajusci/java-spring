package com.inspirenetz.api.test.core.service;

import com.google.common.collect.Lists;
import com.inspirenetz.api.core.dictionary.*;
import com.inspirenetz.api.core.domain.*;
import com.inspirenetz.api.core.service.*;
import com.inspirenetz.api.rest.exception.InspireNetzException;
import com.inspirenetz.api.test.config.ApplicationTestConfig;
import com.inspirenetz.api.test.config.IntegrationTestConfig;
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
@ContextConfiguration(classes  = {ApplicationTestConfig.class, PersistenceTestConfig.class, ServiceTestConfig.class, IntegrationTestConfig.class})
public class AccountTransferServiceTest {


    private static Logger log = LoggerFactory.getLogger(AccountTransferServiceTest.class);


    @Autowired
    private LinkRequestService linkRequestService;

    @Autowired
    private CustomerService customerService;


    @Autowired
    private AccountTransferService accountTransferService;

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
    private PrimaryLoyaltyService primaryLoyaltyService;

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
    public void testTransferAccount() throws InspireNetzException {

        // create the Customer
        Set<Customer> customers = CustomerFixture.standardCustomers();
        List<Customer> customerList = Lists.newArrayList((Iterable<Customer>)customers);
        customerService.saveAll(customerList);

        // Get the sourceCustomer
        Customer sourceCustomer = customerList.get(0);


        // Get the destCustomer
        Customer destCustomer = customerList.get(1);
        destCustomer.setCusLoyaltyId("9298390192");
        destCustomer.setCusStatus(CustomerStatus.INACTIVE);
        destCustomer = customerService.saveCustomer(destCustomer);

        // ADd to the set for delete
        customerSet.addAll(customerList);


        // Add the AccumulatedRewardBalance for the primary
        AccumulatedRewardBalance accumulatedRewardBalance1 = AccumulatedRewardBalanceFixture.standardAccumulatedRewardBalance();
        accumulatedRewardBalance1.setArbLoyaltyId(sourceCustomer.getCusLoyaltyId());
        accumulatedRewardBalance1.setArbRewardBalance(20.0);
        accumulatedRewardBalance1 =  accumulatedRewardBalanceService.saveAccumulatedRewardBalance(accumulatedRewardBalance1);

        // Add the LinkedRewardBalance for the primary
        LinkedRewardBalance linkedRewardBalance = LinkedRewardBalanceFixture.standardLinkedRewardBalance();
        linkedRewardBalance.setLrbPrimaryLoyaltyId(sourceCustomer.getCusLoyaltyId());
        linkedRewardBalance.setLrbRewardBalance(120.0);
        linkedRewardBalance =  linkedRewardBalanceService.saveLinkedRewardBalance(linkedRewardBalance);


        // Add the CustomerrewardBalance for primary
        CustomerRewardBalance customerRewardBalance1 = CustomerRewardBalanceFixture.standardRewardBalance();
        customerRewardBalance1.setCrbLoyaltyId(sourceCustomer.getCusLoyaltyId());
        customerRewardBalance1 =  customerRewardBalanceService.saveCustomerRewardBalance(customerRewardBalance1);


        // Add the CustomerRewardExpiry for primary
        CustomerRewardExpiry customerRewardExpiry1 = CustomerRewardExpiryFixture.standardRewardExpiry();
        customerRewardExpiry1.setCreLoyaltyId(sourceCustomer.getCusLoyaltyId());
        customerRewardExpiry1 = customerRewardExpiryService.saveCustomerRewardExpiry(customerRewardExpiry1);


        // Create a PrimaryLoyalty entry
        PrimaryLoyalty primaryLoyalty = PrimaryLoyaltyFixture.standardPrimaryLoyalty();
        primaryLoyalty.setPllLoyaltyId(sourceCustomer.getCusLoyaltyId());
        primaryLoyalty.setPllCustomerNo(sourceCustomer.getCusCustomerNo());
        primaryLoyalty = primaryLoyaltyService.savePrimaryLoyalty(primaryLoyalty);


        // Create the LinkedLoyalty
        LinkedLoyalty linkedLoyalty = LinkedLoyaltyFixture.standardLinkedLoyalty();
        linkedLoyalty.setLilLocation(sourceCustomer.getCusLocation());
        linkedLoyalty.setLilChildCustomerNo(destCustomer.getCusCustomerNo());
        linkedLoyalty.setLilParentCustomerNo(sourceCustomer.getCusCustomerNo());
        linkedLoyalty.setLilStatus(LinkedLoyaltyStatus.ACTIVE);
        linkedLoyalty = linkedLoyaltyService.saveLinkedLoyalty(linkedLoyalty);


        boolean isTransferred = accountTransferService.transferAccount(sourceCustomer.getCusLoyaltyId(),destCustomer.getCusLoyaltyId(),sourceCustomer.getCusMerchantNo());
        Assert.assertTrue(isTransferred);


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


            PrimaryLoyalty  primaryLoyalty =  primaryLoyaltyService.findByPllLoyaltyId(customer.getCusLoyaltyId());

            if ( primaryLoyalty !=  null )
                primaryLoyaltyService.deletePrimaryLoyalty(primaryLoyalty.getPllId());


            customerService.deleteCustomer(customer.getCusCustomerNo());

        }


        for(PartyApproval partyApproval : partyApprovalSet ) {

            partyApprovalService.deletePartyApproval(partyApproval.getPapId());

        }



    }

}
