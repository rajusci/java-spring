package com.inspirenetz.api.test.core.repository;

import com.google.common.collect.Sets;
import com.inspirenetz.api.core.domain.LinkedRewardBalance;
import com.inspirenetz.api.core.repository.LinkedRewardBalanceRepository;
import com.inspirenetz.api.test.config.ApplicationTestConfig;
import com.inspirenetz.api.test.config.PersistenceTestConfig;
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

import java.util.List;
import java.util.Set;

/**
 * Created by sandheepgr on 17/2/14.
 */
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes  = {ApplicationTestConfig.class, PersistenceTestConfig.class})
public class LinkedRewardBalanceRepositoryTest {

    private static Logger log = LoggerFactory.getLogger(LinkedRewardBalanceRepositoryTest.class);

    @Autowired
    private LinkedRewardBalanceRepository linkedRewardBalanceRepository;


    @Before
    public void setup() {}

    @Test
    public void test1Create(){


        LinkedRewardBalance linkedRewardBalance = LinkedRewardBalanceFixture.standardLinkedRewardBalance();
        linkedRewardBalance = linkedRewardBalanceRepository.save(linkedRewardBalance);
        log.info(linkedRewardBalance.toString());
        Assert.assertNotNull(linkedRewardBalance.getLrbId());

    }



    @Test
    public void test2Update() {

        LinkedRewardBalance linkedRewardBalance = LinkedRewardBalanceFixture.standardLinkedRewardBalance();
        linkedRewardBalance = linkedRewardBalanceRepository.save(linkedRewardBalance);
        log.info("Original LinkedRewardBalance " + linkedRewardBalance.toString());

        LinkedRewardBalance updatedLinkedRewardBalance = LinkedRewardBalanceFixture.updatedStandardLinkedRewardBalance(linkedRewardBalance);
        updatedLinkedRewardBalance = linkedRewardBalanceRepository.save(updatedLinkedRewardBalance);
        log.info("Updated LinkedRewardBalance "+ updatedLinkedRewardBalance.toString());

    }


    @Test
    public void test6FindByLrbId() {

        // Get the standard linkedRewardBalance
        LinkedRewardBalance linkedRewardBalance = LinkedRewardBalanceFixture.standardLinkedRewardBalance();

        LinkedRewardBalance linkedRewardBalances = linkedRewardBalanceRepository.findByLrbId(1L);
        log.info("linkedRewardBalances by lrb id " + linkedRewardBalances.toString());


    }
    @Test
    public void test3FindByLoyaltyId() {

        // Get the standard linkedRewardBalance
        LinkedRewardBalance linkedRewardBalance = LinkedRewardBalanceFixture.standardLinkedRewardBalance();

        List<LinkedRewardBalance> linkedRewardBalances = linkedRewardBalanceRepository.findByLrbPrimaryLoyaltyId(linkedRewardBalance.getLrbPrimaryLoyaltyId());
        log.info("linkedRewardBalances by loyalty id " + linkedRewardBalances.toString());
        Set<LinkedRewardBalance> linkedRewardBalanceSet = Sets.newHashSet((Iterable<LinkedRewardBalance>)linkedRewardBalances);
        log.info("linkedRewardBalance list "+linkedRewardBalanceSet.toString());

    }

    @Test
    public void test4FindByLrbPrimaryLoyaltyIdAndLrbMerchantNo() {

        // Create the linkedRewardBalance
        LinkedRewardBalance linkedRewardBalance = LinkedRewardBalanceFixture.standardLinkedRewardBalance();
        linkedRewardBalanceRepository.save(linkedRewardBalance);
        Assert.assertNotNull(linkedRewardBalance.getLrbId());
        log.info("LinkedRewardBalance created");

        List<LinkedRewardBalance> fetchLinkedRewardBalance = linkedRewardBalanceRepository.findByLrbPrimaryLoyaltyIdAndLrbMerchantNo(linkedRewardBalance.getLrbPrimaryLoyaltyId(), linkedRewardBalance.getLrbMerchantNo());
        Assert.assertNotNull(fetchLinkedRewardBalance);
        log.info("Fetched linkedRewardBalance info" + linkedRewardBalance.toString());

    }


    @Test
    public void test5FindByLrbPrimaryLoyaltyIdAndLrbMerchantNoAndLrbRewardCurrency() {

        // Create the linkedRewardBalance
        LinkedRewardBalance linkedRewardBalance = LinkedRewardBalanceFixture.standardLinkedRewardBalance();
        linkedRewardBalanceRepository.save(linkedRewardBalance);
        Assert.assertNotNull(linkedRewardBalance.getLrbId());
        log.info("LinkedRewardBalance created");

        // Check the linkedRewardBalance name
        LinkedRewardBalance linkedRewardBalanceObj = linkedRewardBalanceRepository.findByLrbPrimaryLoyaltyIdAndLrbMerchantNoAndLrbRewardCurrency(linkedRewardBalance.getLrbPrimaryLoyaltyId(),linkedRewardBalance.getLrbMerchantNo(),linkedRewardBalance.getLrbRewardCurrency());
        Assert.assertNotNull(linkedRewardBalanceObj);

    }

    @After
    public void tearDown() {

        Set<LinkedRewardBalance> linkedRewardBalances = LinkedRewardBalanceFixture.standardLinkedRewardBalances();

        for(LinkedRewardBalance linkedRewardBalance: linkedRewardBalances) {

            LinkedRewardBalance delLinkedRewardBalance = linkedRewardBalanceRepository.findByLrbPrimaryLoyaltyIdAndLrbMerchantNoAndLrbRewardCurrency(linkedRewardBalance.getLrbPrimaryLoyaltyId(),linkedRewardBalance.getLrbMerchantNo(),linkedRewardBalance.getLrbRewardCurrency());

            if ( delLinkedRewardBalance != null ) {
                linkedRewardBalanceRepository.delete(delLinkedRewardBalance);
            }

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
