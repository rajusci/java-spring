package com.inspirenetz.api.test.core.service;

import com.google.common.collect.Sets;
import com.inspirenetz.api.core.domain.Customer;
import com.inspirenetz.api.core.domain.CustomerRewardBalance;
import com.inspirenetz.api.core.domain.LinkedRewardBalance;
import com.inspirenetz.api.core.repository.LinkedRewardBalanceRepository;
import com.inspirenetz.api.core.service.CustomerRewardBalanceService;
import com.inspirenetz.api.core.service.CustomerService;
import com.inspirenetz.api.core.service.LinkedRewardBalanceService;
import com.inspirenetz.api.rest.exception.InspireNetzException;
import com.inspirenetz.api.test.config.ApplicationTestConfig;
import com.inspirenetz.api.test.config.PersistenceTestConfig;
import com.inspirenetz.api.test.config.ServiceTestConfig;
import com.inspirenetz.api.test.core.fixture.CustomerFixture;
import com.inspirenetz.api.test.core.fixture.CustomerRewardBalanceFixture;
import com.inspirenetz.api.test.core.fixture.LinkedRewardBalanceFixture;
import org.junit.*;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
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
public class LinkedRewardBalanceServiceTest {


    private static Logger log = LoggerFactory.getLogger(LinkedRewardBalanceServiceTest.class);

    @Autowired
    private LinkedRewardBalanceService linkedRewardBalanceService;

    @Autowired
    private LinkedRewardBalanceRepository linkedRewardBalanceRepository;

    @Autowired
    private CustomerRewardBalanceService customerRewardBalanceService;

    @Autowired
    private CustomerService customerService;


    Set<Customer> customerSet = new HashSet<>(0);

    Set<CustomerRewardBalance> customerRewardBalanceSet = new HashSet<>(0);

    @Before
    public void setUp() {}



    @Test
    public void test1FindByLrbPrimaryLoyaltyId() {

        // Get the standard linkedRewardBalance
        LinkedRewardBalance linkedRewardBalance = LinkedRewardBalanceFixture.standardLinkedRewardBalance();

        List<LinkedRewardBalance> linkedRewardBalances = linkedRewardBalanceService.findByLrbPrimaryLoyaltyId(linkedRewardBalance.getLrbPrimaryLoyaltyId());
        log.info("linkedRewardBalances by merchant no " + linkedRewardBalances.toString());
        Set<LinkedRewardBalance> linkedRewardBalanceSet = Sets.newHashSet((Iterable<LinkedRewardBalance>) linkedRewardBalances);
        log.info("linkedRewardBalance list "+linkedRewardBalanceSet.toString());

    }



    @Test
    public void test2FindByLrbPrimaryLoyaltyIdAndLrbMerchantNo() {

        // Create the linkedRewardBalance
        LinkedRewardBalance linkedRewardBalance = LinkedRewardBalanceFixture.standardLinkedRewardBalance();
        linkedRewardBalanceService.saveLinkedRewardBalance(linkedRewardBalance);
        Assert.assertNotNull(linkedRewardBalance.getLrbId());
        log.info("LinkedRewardBalance created");

        List<LinkedRewardBalance> fetchLinkedRewardBalance = linkedRewardBalanceService.findByLrbPrimaryLoyaltyIdAndLrbMerchantNo(linkedRewardBalance.getLrbPrimaryLoyaltyId(), linkedRewardBalance.getLrbMerchantNo());
        Assert.assertNotNull(fetchLinkedRewardBalance);
        log.info("Fetched linkedRewardBalance info" + linkedRewardBalance.toString());

    }



    @Test
    public void test3FindByLrbPrimaryLoyaltyIdAndLrbMerchantNoAndLrbRewardCurrency() {

        // Create the linkedRewardBalance
        LinkedRewardBalance linkedRewardBalance = LinkedRewardBalanceFixture.standardLinkedRewardBalance();
        linkedRewardBalanceService.saveLinkedRewardBalance(linkedRewardBalance);
        Assert.assertNotNull(linkedRewardBalance.getLrbId());
        log.info("LinkedRewardBalance created");

        // Check the linkedRewardBalance name
        LinkedRewardBalance linkedRewardBalances = linkedRewardBalanceService.findByLrbPrimaryLoyaltyIdAndLrbMerchantNoAndLrbRewardCurrency(linkedRewardBalance.getLrbPrimaryLoyaltyId(),linkedRewardBalance.getLrbMerchantNo(),linkedRewardBalance.getLrbRewardCurrency());
        Assert.assertNotNull(linkedRewardBalances);

    } 
    @Test
    public void test5DeleteLinkedRewardBalance() {

        // Create the linkedRewardBalance
        LinkedRewardBalance linkedRewardBalance = LinkedRewardBalanceFixture.standardLinkedRewardBalance();
        linkedRewardBalance = linkedRewardBalanceService.saveLinkedRewardBalance(linkedRewardBalance);
        Assert.assertNotNull(linkedRewardBalance.getLrbId());
        log.info("LinkedRewardBalance created");

        // call the delete linkedRewardBalance
        linkedRewardBalanceService.deleteLinkedRewardBalance(linkedRewardBalance.getLrbId());

        // Try searching for the linkedRewardBalance
        LinkedRewardBalance checkLinkedRewardBalance  = linkedRewardBalanceService.findByLrbPrimaryLoyaltyIdAndLrbMerchantNoAndLrbRewardCurrency(linkedRewardBalance.getLrbPrimaryLoyaltyId(), linkedRewardBalance.getLrbMerchantNo(), linkedRewardBalance.getLrbRewardCurrency());

        Assert.assertNull(checkLinkedRewardBalance);

        log.info("linkedRewardBalance deleted");

    }


    @Test
    public void test6ExportPrimaryBalanceToLinkedRewardBalance() {

        // Create the customer
        Customer customer = CustomerFixture.standardCustomer();
        customer = customerService.saveCustomer(customer);
        Assert.assertNotNull(customer);

        // Add to the set for delete
        customerSet.add(customer);

        // Add some reward balance
        CustomerRewardBalance  customerRewardBalance = CustomerRewardBalanceFixture.standardRewardBalance();
        customerRewardBalance = customerRewardBalanceService.saveCustomerRewardBalance(customerRewardBalance);
        Assert.assertNotNull(customerRewardBalance);



        // Export to the rewardbalance
        boolean isAdded = linkedRewardBalanceService.exportPrimaryBalanceToLinkedRewardBalance(customer);
        Assert.assertTrue(isAdded);

        // Get the LInkedRewardBalnace
        List<LinkedRewardBalance> linkedRewardBalanceList = linkedRewardBalanceService.findByLrbPrimaryLoyaltyIdAndLrbMerchantNo(customer.getCusLoyaltyId(),customer.getCusMerchantNo());
        Assert.assertNotNull(linkedRewardBalanceList);
        log.info("LinkedRewardBalanceList : " + linkedRewardBalanceList.toString());

    }




    @After
    public void tearDown() throws InspireNetzException {

        Set<LinkedRewardBalance> linkedRewardBalances = LinkedRewardBalanceFixture.standardLinkedRewardBalances();

        for(LinkedRewardBalance linkedRewardBalance: linkedRewardBalances) {

            LinkedRewardBalance delLinkedRewardBalance = linkedRewardBalanceRepository.findByLrbPrimaryLoyaltyIdAndLrbMerchantNoAndLrbRewardCurrency(linkedRewardBalance.getLrbPrimaryLoyaltyId(), linkedRewardBalance.getLrbMerchantNo(), linkedRewardBalance.getLrbRewardCurrency());

            if ( delLinkedRewardBalance != null ) {
                linkedRewardBalanceRepository.delete(delLinkedRewardBalance);
            }

        }


        for(Customer customer : customerSet ) {

            List<CustomerRewardBalance> customerRewardBalanceList =  customerRewardBalanceService.findByCrbLoyaltyIdAndCrbMerchantNo(customer.getCusLoyaltyId(),customer.getCusMerchantNo());

            for(CustomerRewardBalance customerRewardBalance : customerRewardBalanceList ) {

                customerRewardBalanceService.deleteCustomerRewardBalance(customerRewardBalance);

            }



            List<LinkedRewardBalance> linkedRewardBalanceList = linkedRewardBalanceService.findByLrbPrimaryLoyaltyIdAndLrbMerchantNo(customer.getCusLoyaltyId(),customer.getCusMerchantNo());

            for ( LinkedRewardBalance linkedRewardBalance : linkedRewardBalanceList ) {

                linkedRewardBalanceService.deleteLinkedRewardBalance(linkedRewardBalance.getLrbId());

            }


            // Delete the customer
            customerService.deleteCustomer(customer.getCusCustomerNo());

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
        return new Sort(Sort.Direction.ASC, "brnName");
    }

}
