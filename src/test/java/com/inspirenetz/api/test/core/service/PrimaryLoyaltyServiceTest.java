package com.inspirenetz.api.test.core.service;

import com.google.common.collect.Sets;
import com.inspirenetz.api.core.domain.Customer;
import com.inspirenetz.api.core.domain.CustomerRewardBalance;
import com.inspirenetz.api.core.domain.PrimaryLoyalty;
import com.inspirenetz.api.core.repository.PrimaryLoyaltyRepository;
import com.inspirenetz.api.core.service.CustomerRewardBalanceService;
import com.inspirenetz.api.core.service.CustomerService;
import com.inspirenetz.api.core.service.PrimaryLoyaltyService;
import com.inspirenetz.api.rest.exception.InspireNetzException;
import com.inspirenetz.api.test.config.ApplicationTestConfig;
import com.inspirenetz.api.test.config.PersistenceTestConfig;
import com.inspirenetz.api.test.config.ServiceTestConfig;
import com.inspirenetz.api.test.core.fixture.CustomerFixture;
import com.inspirenetz.api.test.core.fixture.CustomerRewardBalanceFixture;
import com.inspirenetz.api.test.core.fixture.PrimaryLoyaltyFixture;
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
 * Created by sandheepgr on 28/8/14.
 */
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes  = {ApplicationTestConfig.class, PersistenceTestConfig.class, ServiceTestConfig.class})
public class PrimaryLoyaltyServiceTest {


    private static Logger log = LoggerFactory.getLogger(PrimaryLoyaltyServiceTest.class);

    @Autowired
    private PrimaryLoyaltyService primaryLoyaltyService;

    @Autowired
    private CustomerService customerService;

    @Autowired
    private PrimaryLoyaltyRepository primaryLoyaltyRepository;

    @Autowired
    private CustomerRewardBalanceService customerRewardBalanceService;


    Set<Customer> customerSet = new HashSet<>(0);

    Set<PrimaryLoyalty> tempSet = new HashSet<>(0);


    @Before
    public void setUp() {}



    @Test
    public void test1FindByPllId() {

        // Get the standard primaryLoyalty
        PrimaryLoyalty primaryLoyalty = primaryLoyaltyService.savePrimaryLoyalty(PrimaryLoyaltyFixture.standardPrimaryLoyalty());

        // Add to tempSEt
        tempSet.add(primaryLoyalty);

        PrimaryLoyalty prLoyalty = primaryLoyaltyService.findByPllId(primaryLoyalty.getPllId());
        Assert.assertNotNull(prLoyalty.getPllId());
        log.info("primaryLoyalty by pll id " + prLoyalty.toString());

       }

    @Test
    public void test2FindByPllCustomerNo() {

        // Get the standard primaryLoyalty
        PrimaryLoyalty primaryLoyalty = primaryLoyaltyService.savePrimaryLoyalty(PrimaryLoyaltyFixture.standardPrimaryLoyalty());

        // Add to tempSEt
        tempSet.add(primaryLoyalty);

        PrimaryLoyalty prLoyalty = primaryLoyaltyService.findByPllCustomerNo(primaryLoyalty.getPllCustomerNo());
        Assert.assertNotNull(prLoyalty.getPllId());
        log.info("primaryLoyalty by pll customer no " + prLoyalty.toString());

    }

    @Test
    public void test3FindByPllLoyaltyId() {

        // Get the standard primaryLoyalty
        PrimaryLoyalty primaryLoyalty = primaryLoyaltyService.savePrimaryLoyalty(PrimaryLoyaltyFixture.standardPrimaryLoyalty());

        // Add to tempSEt
        tempSet.add(primaryLoyalty);

        PrimaryLoyalty prLoyalty = primaryLoyaltyService.findByPllLoyaltyId(primaryLoyalty.getPllLoyaltyId());
        Assert.assertNotNull(prLoyalty.getPllId());
        log.info("primaryLoyalty by pll loyalty id " + prLoyalty.toString());

    }

    @Test
    public void test4deletePrimaryLoyalty() {

        // Get the standard primaryLoyalty
        PrimaryLoyalty primaryLoyalty = primaryLoyaltyService.savePrimaryLoyalty(PrimaryLoyaltyFixture.standardPrimaryLoyalty());

        // Add to tempSEt
        tempSet.add(primaryLoyalty);

        Boolean prLoyalty = primaryLoyaltyService.deletePrimaryLoyalty(primaryLoyalty.getPllId());
        Assert.assertTrue(prLoyalty);
        log.info("primaryLoyalty deleted " + prLoyalty.toString());


        // Remove from the tempSet
        tempSet.remove(primaryLoyalty);

    }

    @Test
    public void test5isPrimaryLoyaltyExisting() {

        // Get the standard primaryLoyalty
        PrimaryLoyalty primaryLoyalty = primaryLoyaltyService.savePrimaryLoyalty(PrimaryLoyaltyFixture.standardPrimaryLoyalty());
        Assert.assertNotNull(primaryLoyalty.getPllId());
        log.info("primary loyalty created"+primaryLoyalty.toString());

        // Add to tempSEt
        tempSet.add(primaryLoyalty);

        PrimaryLoyalty newLoyalty = PrimaryLoyaltyFixture.standardPrimaryLoyalty();
        Boolean prLoyalty = primaryLoyaltyService.isPrimaryLoyaltyExisting(newLoyalty);
        Assert.assertTrue(prLoyalty);

        prLoyalty = primaryLoyaltyService.deletePrimaryLoyalty(primaryLoyalty.getPllId());
        prLoyalty = primaryLoyaltyService.isPrimaryLoyaltyExisting(primaryLoyalty);
        Assert.assertFalse(prLoyalty);

        log.info("primaryLoyalty tested " + prLoyalty.toString());


        tempSet.remove(primaryLoyalty);
    }



    @Test
    public void test6AddcustomerAsPrimary() {

        // Create customer
        Customer customer = CustomerFixture.standardCustomer();
        customer = customerService.saveCustomer(customer);
        Assert.assertNotNull(customer);

        // Add the data
        customerSet.add(customer);


        // Add the CustomerRewardBalance
        CustomerRewardBalance customerRewardBalance = CustomerRewardBalanceFixture.standardRewardBalance();
        customerRewardBalance = customerRewardBalanceService.saveCustomerRewardBalance(customerRewardBalance);
        Assert.assertNotNull(customerRewardBalance);



        // Boolean isAded
        boolean isAdded = primaryLoyaltyService.addCustomerAsPrimary(customer);
        Assert.assertTrue(isAdded);

        // Get the PrimaryLoyalty
        PrimaryLoyalty primaryLoyalty = primaryLoyaltyService.findByPllCustomerNo(customer.getCusCustomerNo());
        Assert.assertNotNull(primaryLoyalty);
        Assert.assertTrue(primaryLoyalty.getPllCustomerNo().longValue() ==  customer.getCusCustomerNo().longValue());

        // Add to the set for deletion
        tempSet.add(primaryLoyalty);

    }



    @After
    public void tearDown() throws InspireNetzException {

       for ( Customer customer :customerSet ) {

           customerService.deleteCustomer(customer.getCusCustomerNo());

           List<CustomerRewardBalance> customerRewardBalanceList = customerRewardBalanceService.findByCrbLoyaltyIdAndCrbMerchantNo(customer.getCusLoyaltyId(),customer.getCusMerchantNo());

           for ( CustomerRewardBalance customerRewardBalance : customerRewardBalanceList ) {

               customerRewardBalanceService.deleteCustomerRewardBalance(customerRewardBalance);


           }
       }


       for(PrimaryLoyalty primaryLoyalty : tempSet ) {

           primaryLoyaltyService.deletePrimaryLoyalty(primaryLoyalty.getPllId());

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
        return new Sort(Sort.Direction.ASC, "pllLoyaltyId");
    }

}
