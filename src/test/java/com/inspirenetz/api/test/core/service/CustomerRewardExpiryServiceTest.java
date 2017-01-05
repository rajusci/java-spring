package com.inspirenetz.api.test.core.service;

import com.google.common.collect.Lists;
import com.inspirenetz.api.core.domain.AccumulatedRewardBalance;
import com.inspirenetz.api.core.domain.Customer;
import com.inspirenetz.api.core.domain.CustomerRewardBalance;
import com.inspirenetz.api.core.domain.CustomerRewardExpiry;
import com.inspirenetz.api.core.service.AccumulatedRewardBalanceService;
import com.inspirenetz.api.core.service.CustomerRewardBalanceService;
import com.inspirenetz.api.core.service.CustomerRewardExpiryService;
import com.inspirenetz.api.core.service.CustomerService;
import com.inspirenetz.api.rest.exception.InspireNetzException;
import com.inspirenetz.api.test.config.ApplicationTestConfig;
import com.inspirenetz.api.test.config.PersistenceTestConfig;
import com.inspirenetz.api.test.config.ServiceTestConfig;
import com.inspirenetz.api.test.core.fixture.AccumulatedRewardBalanceFixture;
import com.inspirenetz.api.test.core.fixture.CustomerFixture;
import com.inspirenetz.api.test.core.fixture.CustomerRewardBalanceFixture;
import com.inspirenetz.api.test.core.fixture.CustomerRewardExpiryFixture;
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

import java.sql.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Created by sandheepgr on 30/4/14.
 */
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes  = {ApplicationTestConfig.class, PersistenceTestConfig.class, ServiceTestConfig.class})
public class CustomerRewardExpiryServiceTest {


    private static Logger log = LoggerFactory.getLogger(CustomerRewardExpiryServiceTest.class);

    @Autowired
    private CustomerRewardExpiryService customerRewardExpiryService;

    Set<CustomerRewardExpiry> tempSet = new HashSet<>(0);

    Set<Customer> customerSet = new HashSet<>(0);

    Set<CustomerRewardBalance> customerRewardBalanceSet = new HashSet<>(0);

    Set<AccumulatedRewardBalance> accumulatedRewardBalanceSet = new HashSet<>(0);


    @Autowired
    private CustomerRewardBalanceService customerRewardBalanceService;

    @Autowired
    private CustomerService customerService;

    @Autowired
    private AccumulatedRewardBalanceService accumulatedRewardBalanceService;



    @Before
    public void setUp() {}



    @Test
    public void test1FindByCreLoyaltyId() {

        Set<CustomerRewardExpiry> customerRewardExpirySet = CustomerRewardExpiryFixture.standardCustomerRewardExpirys();
        List<CustomerRewardExpiry> customerRewardExpiries = Lists.newArrayList((Iterable<CustomerRewardExpiry>) customerRewardExpirySet);
        customerRewardExpiryService.saveAll(customerRewardExpiries);
        log.info("List saved");

        // Add to tempSet
        tempSet.addAll(customerRewardExpirySet);

        CustomerRewardExpiry customerRewardExpiry = CustomerRewardExpiryFixture.standardRewardExpiry();

        List<CustomerRewardExpiry> customerRewardExpiryList = customerRewardExpiryService.findByCreLoyaltyId(customerRewardExpiry.getCreLoyaltyId());
        log.info("customerrewardexpiry list" + customerRewardExpiryList.toString());

    }


    @Test
    public void test2FindByCreLoyaltyIdAndCreRewardCurrencyId() {

        Set<CustomerRewardExpiry> customerRewardExpirySet = CustomerRewardExpiryFixture.standardCustomerRewardExpirys();
        List<CustomerRewardExpiry> customerRewardExpiries = Lists.newArrayList((Iterable<CustomerRewardExpiry>) customerRewardExpirySet);
        customerRewardExpiryService.saveAll(customerRewardExpiries);
        log.info("List saved");

        // Add to tempSet
        tempSet.addAll(customerRewardExpirySet);

        CustomerRewardExpiry customerRewardExpiry = CustomerRewardExpiryFixture.standardRewardExpiry();

        List<CustomerRewardExpiry> customerRewardExpiryList = customerRewardExpiryService.findByCreLoyaltyIdAndCreRewardCurrencyId(customerRewardExpiry.getCreLoyaltyId(),customerRewardExpiry.getCreMerchantNo());
        log.info("customerrewardexpiry list" + customerRewardExpiryList.toString());

    }


    @Test
    public void test3GetFIFOCustomerExpiryList() {

        Set<CustomerRewardExpiry> customerRewardExpirySet = CustomerRewardExpiryFixture.standardCustomerRewardExpirys();
        List<CustomerRewardExpiry> customerRewardExpiries = Lists.newArrayList((Iterable<CustomerRewardExpiry>)customerRewardExpirySet);
        customerRewardExpiryService.saveAll(customerRewardExpiries);
        log.info("List saved");

        // Add to tempSet
        tempSet.addAll(customerRewardExpirySet);

        CustomerRewardExpiry customerRewardExpiry = CustomerRewardExpiryFixture.standardRewardExpiry();

        List<CustomerRewardExpiry> customerRewardExpiryList = customerRewardExpiryService.getFIFOCustomerExpiryList(customerRewardExpiry.getCreMerchantNo(),customerRewardExpiry.getCreLoyaltyId(),customerRewardExpiry.getCreRewardCurrencyId());
        log.info("customerrewardexpiry list" + customerRewardExpiryList.toString());

    }



    @Test
    public void test4FindByCreMerchantNoAndCreLoyaltyId() {

        Set<CustomerRewardExpiry> customerRewardExpirySet = CustomerRewardExpiryFixture.standardCustomerRewardExpirys();
        List<CustomerRewardExpiry> customerRewardExpiries = Lists.newArrayList((Iterable<CustomerRewardExpiry>) customerRewardExpirySet);
        customerRewardExpiryService.saveAll(customerRewardExpiries);
        log.info("List saved");

        // Add to tempSet
        tempSet.addAll(customerRewardExpirySet);

        CustomerRewardExpiry customerRewardExpiry = CustomerRewardExpiryFixture.standardRewardExpiry();

        List<CustomerRewardExpiry> customerRewardExpiryList = customerRewardExpiryService.findByCreMerchantNoAndCreLoyaltyId(customerRewardExpiry.getCreMerchantNo(),customerRewardExpiry.getCreLoyaltyId());
        log.info("customerrewardexpiry list" + customerRewardExpiryList.toString());

    }


    @Test
    public void test5FindByCreMerchantNoAndCreLoyaltyIdAndCreRewardCurrencyId() {

        Set<CustomerRewardExpiry> customerRewardExpirySet = CustomerRewardExpiryFixture.standardCustomerRewardExpirys();
        List<CustomerRewardExpiry> customerRewardExpiries = Lists.newArrayList((Iterable<CustomerRewardExpiry>) customerRewardExpirySet);
        customerRewardExpiryService.saveAll(customerRewardExpiries);
        log.info("List saved");

        // Add to tempSet
        tempSet.addAll(customerRewardExpirySet);

        CustomerRewardExpiry customerRewardExpiry = CustomerRewardExpiryFixture.standardRewardExpiry();

        List<CustomerRewardExpiry> customerRewardExpiryList = customerRewardExpiryService.findByCreMerchantNoAndCreLoyaltyIdAndCreRewardCurrencyId(customerRewardExpiry.getCreMerchantNo(),customerRewardExpiry.getCreLoyaltyId(),customerRewardExpiry.getCreRewardCurrencyId());
        log.info("customerrewardexpiry list" + customerRewardExpiryList.toString());

    }


    @Test
    public void test7FindByCreMerchantNoAndCreLoyaltyIdAndCreRewardCurrencyIdAndCreExpiryDt() {

        Set<CustomerRewardExpiry> customerRewardExpirySet = CustomerRewardExpiryFixture.standardCustomerRewardExpirys();
        List<CustomerRewardExpiry> customerRewardExpiries = Lists.newArrayList((Iterable<CustomerRewardExpiry>) customerRewardExpirySet);
        customerRewardExpiryService.saveAll(customerRewardExpiries);
        log.info("List saved");

        // Add to tempSet
        tempSet.addAll(customerRewardExpirySet);

        CustomerRewardExpiry customerRewardExpiry = CustomerRewardExpiryFixture.standardRewardExpiry();

        // Search CustomerRewardExpiry
        CustomerRewardExpiry searchCustomerRewardExpiry = customerRewardExpiryService.findByCreMerchantNoAndCreLoyaltyIdAndCreRewardCurrencyIdAndCreExpiryDt(customerRewardExpiry.getCreMerchantNo(),customerRewardExpiry.getCreLoyaltyId(),customerRewardExpiry.getCreRewardCurrencyId(),customerRewardExpiry.getCreExpiryDt());
        Assert.assertNotNull(searchCustomerRewardExpiry);
        log.info("CustomerRewardExpiry List " + customerRewardExpiry.toString());

    }


    @Test
    public void test8FindByCreId() {

        CustomerRewardExpiry customerRewardExpiry = customerRewardExpiryService.saveCustomerRewardExpiry(CustomerRewardExpiryFixture.standardRewardExpiry());
        log.info(customerRewardExpiry.toString());

        // Add to tempSet
        tempSet.add(customerRewardExpiry);

        CustomerRewardExpiry customerRewardExpiry1 = customerRewardExpiryService.findByCreId(customerRewardExpiry.getCreId());
        Assert.assertNotNull(customerRewardExpiry1);
        log.info("Customer Rewrd Expiry" + customerRewardExpiry1.toString());

    }


    @Test
    public void test9GetExpiredEntries() {


        CustomerRewardExpiry customerRewardExpiry = CustomerRewardExpiryFixture.standardRewardExpiry();
        customerRewardExpiry.setCreExpiryDt(new Date(new java.util.Date().getTime()));
        customerRewardExpiry = customerRewardExpiryService.saveCustomerRewardExpiry(customerRewardExpiry);
        Assert.assertNotNull(customerRewardExpiry);


        // Add to the tempSet
        tempSet.add(customerRewardExpiry);

        // Get the expired pages
        Page<CustomerRewardExpiry> customerRewardExpiryPage =  customerRewardExpiryService.getExpiredCustomerRewardExpiry(customerRewardExpiry.getCreMerchantNo(),constructPageSpecification(0));
        Assert.assertNotNull(customerRewardExpiryPage);
        log.info("CustomerRewardExpiry page : " + customerRewardExpiryPage.getContent());


    }


    @Test
    public void test10ExpireBalanceForCustomer() {

        // create the Customer
        Customer customer = CustomerFixture.standardCustomer();
        customer = customerService.saveCustomer(customer);

        customerSet.add(customer);


        // Add the reward balance
        CustomerRewardBalance customerRewardBalance = CustomerRewardBalanceFixture.standardRewardBalance();
        customerRewardBalance.setCrbLoyaltyId(customer.getCusLoyaltyId());
        customerRewardBalance = customerRewardBalanceService.saveCustomerRewardBalance(customerRewardBalance);

        customerRewardBalanceSet.add(customerRewardBalance);


        // Add the AccumulatedRewardBalance
        AccumulatedRewardBalance accumulatedRewardBalance = AccumulatedRewardBalanceFixture.standardAccumulatedRewardBalance();
        accumulatedRewardBalance.setArbLoyaltyId(customer.getCusLoyaltyId());
        accumulatedRewardBalance = accumulatedRewardBalanceService.saveAccumulatedRewardBalance(accumulatedRewardBalance);

        accumulatedRewardBalanceSet.add(accumulatedRewardBalance);

        // Add the customer reward expiry
        CustomerRewardExpiry customerRewardExpiry = CustomerRewardExpiryFixture.standardRewardExpiry();
        customerRewardExpiry.setCreLoyaltyId(customer.getCusLoyaltyId());
        customerRewardExpiry =  customerRewardExpiryService.saveCustomerRewardExpiry(customerRewardExpiry);

        tempSet.add(customerRewardExpiry);


        // Now clear the balance
        boolean isCleared = customerRewardExpiryService.expireBalanceForCustomer(customer);
        Assert.assertTrue(isCleared);


    }


    @After
    public void tearDown() throws InspireNetzException {

        for(CustomerRewardExpiry customerRewardExpiry: tempSet) {

            customerRewardExpiryService.deleteCustomerRewardExpiry(customerRewardExpiry);

        }


        for(CustomerRewardBalance customerRewardBalance : customerRewardBalanceSet ) {

            customerRewardBalanceService.deleteCustomerRewardBalance(customerRewardBalance);

        }


        for(Customer customer : customerSet ) {

            customerService.deleteCustomer(customer.getCusCustomerNo());

        }


        for (AccumulatedRewardBalance accumulatedRewardBalance : accumulatedRewardBalanceSet) {

            accumulatedRewardBalanceService.deleteAccumulatedRewardBalance(accumulatedRewardBalance.getArbId());

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
        return new Sort(Sort.Direction.ASC, "creLoyaltyId");
    }

}
