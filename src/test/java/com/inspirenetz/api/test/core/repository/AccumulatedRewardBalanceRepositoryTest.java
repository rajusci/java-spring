package com.inspirenetz.api.test.core.repository;

import com.google.common.collect.Sets;
import com.inspirenetz.api.core.domain.AccumulatedRewardBalance;
import com.inspirenetz.api.core.repository.AccumulatedRewardBalanceRepository;
import com.inspirenetz.api.test.config.ApplicationTestConfig;
import com.inspirenetz.api.test.config.PersistenceTestConfig;
import com.inspirenetz.api.test.core.fixture.AccumulatedRewardBalanceFixture;
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
 * Created by sandheepgr on 17/2/14.
 */
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes  = {ApplicationTestConfig.class, PersistenceTestConfig.class})
public class AccumulatedRewardBalanceRepositoryTest {

    private static Logger log = LoggerFactory.getLogger(AccumulatedRewardBalanceRepositoryTest.class);

    @Autowired
    private AccumulatedRewardBalanceRepository accumulatedRewardBalanceRepository;

    Set<AccumulatedRewardBalance> tempSet = new HashSet<>(0);


    @Before
    public void setup() {}


    @Test
    public void test1Create(){


        AccumulatedRewardBalance accumulatedRewardBalance = accumulatedRewardBalanceRepository.save(AccumulatedRewardBalanceFixture.standardAccumulatedRewardBalance());
        log.info(accumulatedRewardBalance.toString());

        // Add to tempSet
        tempSet.add(accumulatedRewardBalance);

        Assert.assertNotNull(accumulatedRewardBalance.getArbId());

    }

    @Test
    public void test2Update() {

        AccumulatedRewardBalance accumulatedRewardBalance = AccumulatedRewardBalanceFixture.standardAccumulatedRewardBalance();
        accumulatedRewardBalance = accumulatedRewardBalanceRepository.save(accumulatedRewardBalance);
        log.info("Original AccumulatedRewardBalance " + accumulatedRewardBalance.toString());

        // Add to tempSet
        tempSet.add(accumulatedRewardBalance);

        AccumulatedRewardBalance updatedAccumulatedRewardBalance = AccumulatedRewardBalanceFixture.updatedStandardAccumulatedRewardBalance(accumulatedRewardBalance);
        updatedAccumulatedRewardBalance = accumulatedRewardBalanceRepository.save(updatedAccumulatedRewardBalance);
        log.info("Updated AccumulatedRewardBalance "+ updatedAccumulatedRewardBalance.toString());

    }



    @Test
    public void test3FindByArbId() {

        // Get the standard accumulatedRewardBalance
        AccumulatedRewardBalance accumulatedRewardBalance = AccumulatedRewardBalanceFixture.standardAccumulatedRewardBalance();
        accumulatedRewardBalance = accumulatedRewardBalanceRepository.save(accumulatedRewardBalance);

        // Add to tempSet
        tempSet.add(accumulatedRewardBalance);

        // Get the saved reward balance
        AccumulatedRewardBalance searchRewardBalance = accumulatedRewardBalanceRepository.findByArbId(accumulatedRewardBalance.getArbId());
        Assert.assertNotNull(searchRewardBalance);
        log.info("Accumulated Reward Balance :  " + accumulatedRewardBalance);

    }

    @Test
    public void test4FindByArbMerchantNoAndArbLoyaltyIdAndArbRewardCurrency() {

        // Create the accumulatedRewardBalance
        AccumulatedRewardBalance accumulatedRewardBalance = AccumulatedRewardBalanceFixture.standardAccumulatedRewardBalance();
        accumulatedRewardBalanceRepository.save(accumulatedRewardBalance);
        Assert.assertNotNull(accumulatedRewardBalance.getArbId());
        log.info("AccumulatedRewardBalance created");

        // Add to tempSet
        tempSet.add(accumulatedRewardBalance);

        AccumulatedRewardBalance fetchAccumulatedRewardBalance = accumulatedRewardBalanceRepository.findByArbMerchantNoAndArbLoyaltyIdAndArbRewardCurrency(accumulatedRewardBalance.getArbMerchantNo(), accumulatedRewardBalance.getArbLoyaltyId(), accumulatedRewardBalance.getArbRewardCurrency());
        Assert.assertNotNull(fetchAccumulatedRewardBalance);
        log.info("Fetched accumulatedRewardBalance info" + accumulatedRewardBalance.toString());

    }


    @Test
    public void test5FindByArbMerchantNoAndArbLoyaltyId() {

        // Create the accumulatedRewardBalance
        AccumulatedRewardBalance accumulatedRewardBalance = AccumulatedRewardBalanceFixture.standardAccumulatedRewardBalance();
        accumulatedRewardBalanceRepository.save(accumulatedRewardBalance);

        // Add to tempSet
        tempSet.add(accumulatedRewardBalance);

        List<AccumulatedRewardBalance> accumulatedRewardBalanceList = accumulatedRewardBalanceRepository.findByArbMerchantNoAndArbLoyaltyId(accumulatedRewardBalance.getArbMerchantNo(),accumulatedRewardBalance.getArbLoyaltyId());
        Assert.assertNotNull(accumulatedRewardBalanceList);
        log.info("AccumulatedRewardBalance list " + accumulatedRewardBalanceList.toString());

    }


    @After
    public void tearDown() {

        for(AccumulatedRewardBalance accumulatedRewardBalance : tempSet ) {

            accumulatedRewardBalanceRepository.delete(accumulatedRewardBalance);

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
        return new Sort(Sort.Direction.ASC, "arbName");
    }


}
