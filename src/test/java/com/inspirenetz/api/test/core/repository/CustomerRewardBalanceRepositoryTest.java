package com.inspirenetz.api.test.core.repository;

import com.inspirenetz.api.core.domain.Customer;
import com.inspirenetz.api.core.domain.CustomerRewardBalance;
import com.inspirenetz.api.core.repository.CustomerRewardBalanceRepository;
import com.inspirenetz.api.test.config.ApplicationTestConfig;
import com.inspirenetz.api.test.config.PersistenceTestConfig;
import com.inspirenetz.api.test.core.fixture.CustomerFixture;
import com.inspirenetz.api.test.core.fixture.CustomerRewardBalanceFixture;
import org.junit.*;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Created by sandheepgr on 17/2/14.
 */
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes  = {ApplicationTestConfig.class, PersistenceTestConfig.class})
public class CustomerRewardBalanceRepositoryTest {

    private static Logger log = LoggerFactory.getLogger(CustomerRewardBalanceRepositoryTest.class);

    @Autowired
    private CustomerRewardBalanceRepository customerRewardBalanceRepository;

    Set<CustomerRewardBalance> tempSet = new HashSet<>(0);

    @Before
    public void setUp(){

    }

    @Test
    public void test1Save(){

        CustomerRewardBalance customerRewardBalance = customerRewardBalanceRepository.save(CustomerRewardBalanceFixture.standardRewardBalance());
        log.info(customerRewardBalance.toString());

        // Add to the set
        tempSet.add(customerRewardBalance);

        Assert.assertNotNull(customerRewardBalance.getCrbLoyaltyId());

    }

    @Test
    public void test2Update() {

        CustomerRewardBalance customerRewardBalance = customerRewardBalanceRepository.save(CustomerRewardBalanceFixture.standardRewardBalance());
        customerRewardBalanceRepository.save(customerRewardBalance);
        log.info("Original data" +customerRewardBalance.toString());

        // Add to the set
        tempSet.add(customerRewardBalance);


        CustomerRewardBalance updatedCustomerRewardBalance = CustomerRewardBalanceFixture.updatedStandardRewardBalance(customerRewardBalance);
        customerRewardBalanceRepository.save(customerRewardBalance);
        log.info("Updated data" + customerRewardBalance.toString());


    }


    @Test
    public void test3FindByCrbId() {

        CustomerRewardBalance customerRewardBalance = customerRewardBalanceRepository.save(CustomerRewardBalanceFixture.standardRewardBalance());
        customerRewardBalanceRepository.save(customerRewardBalance);
        log.info("Original data" +customerRewardBalance.toString());

        // Add to the set
        tempSet.add(customerRewardBalance);


        // Search the data
        CustomerRewardBalance searchBalance = customerRewardBalanceRepository.findByCrbId(customerRewardBalance.getCrbId());
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

        List<CustomerRewardBalance> customerRewardBalanceList = customerRewardBalanceRepository.findByCrbLoyaltyIdAndCrbMerchantNo(rewardBalance.getCrbLoyaltyId(), rewardBalance.getCrbMerchantNo());
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



    @After
    public void tearDown() {

        for (CustomerRewardBalance customerRewardBalance : tempSet ) {

            customerRewardBalanceRepository.delete(customerRewardBalance);

        }

    }



}
