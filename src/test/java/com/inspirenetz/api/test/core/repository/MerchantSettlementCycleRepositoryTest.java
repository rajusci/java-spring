package com.inspirenetz.api.test.core.repository;

import com.inspirenetz.api.core.domain.MerchantSettlementCycle;
import com.inspirenetz.api.core.repository.MerchantSettlementCycleRepository;
import com.inspirenetz.api.test.config.ApplicationTestConfig;
import com.inspirenetz.api.test.config.PersistenceTestConfig;
import com.inspirenetz.api.test.core.fixture.MerchantSettlementCycleFixture;
import org.junit.*;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.sql.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Created by sandheepgr on 17/2/14.
 */
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes  = {ApplicationTestConfig.class, PersistenceTestConfig.class})
public class MerchantSettlementCycleRepositoryTest {

    private static Logger log = LoggerFactory.getLogger(MerchantSettlementCycleRepositoryTest.class);

    @Autowired
    private MerchantSettlementCycleRepository merchantSettlementCycleRepository;

    Set<MerchantSettlementCycle> tempSet = new HashSet<>(0);

    @Before
    public void setup() {}

    @Test
    public void test1Create(){

        MerchantSettlementCycleFixture merchantSettlementCycleFixture=new MerchantSettlementCycleFixture();

        MerchantSettlementCycle merchantSettlementCycle = merchantSettlementCycleRepository.save(merchantSettlementCycleFixture.standardMerchantSettlementCycle());

        // Add to the tempSet
        tempSet.add(merchantSettlementCycle);

        log.info(merchantSettlementCycle.toString());
        Assert.assertNotNull(merchantSettlementCycle.getMscId());

    }

    @Test
    public void test2Update() {

        MerchantSettlementCycleFixture merchantSettlementCycleFixture=new MerchantSettlementCycleFixture();

        MerchantSettlementCycle merchantSettlementCycle = merchantSettlementCycleFixture.standardMerchantSettlementCycle();
        merchantSettlementCycle = merchantSettlementCycleRepository.save(merchantSettlementCycle);
        log.info("Original MerchantSettlementCycle " + merchantSettlementCycle.toString());

        // Add to the tempSet
        tempSet.add(merchantSettlementCycle);


        MerchantSettlementCycle updatedMerchantSettlementCycle = MerchantSettlementCycleFixture.updatedStandardMerchantSettlementCycle(merchantSettlementCycle);
        updatedMerchantSettlementCycle = merchantSettlementCycleRepository.save(updatedMerchantSettlementCycle);
        log.info("Updated MerchantSettlementCycle "+ updatedMerchantSettlementCycle.toString());

    }

    @Test
    public void test3FindByMscMerchantNoAndMscRedemptionMerchantAndMscMerchantLocationAndMscStartDateAfterAndMscEndDateBefore() {

        MerchantSettlementCycleFixture merchantSettlementCycleFixture=new MerchantSettlementCycleFixture();

        MerchantSettlementCycle merchantSettlementCycle = merchantSettlementCycleFixture.standardMerchantSettlementCycle();
        merchantSettlementCycle = merchantSettlementCycleRepository.save(merchantSettlementCycle);

        log.info("Original MerchantSettlementCycle " + merchantSettlementCycle.toString());


        // Add to the tempSet
        tempSet.add(merchantSettlementCycle);

        MerchantSettlementCycleFixture merchantSettlementCycleFixtureB=new MerchantSettlementCycleFixture();

        MerchantSettlementCycle merchantSettlementCycleB = merchantSettlementCycleFixture.standardMerchantSettlementCycle();
        merchantSettlementCycleB.setMscStartDate(Date.valueOf("2015-10-09"));
        merchantSettlementCycleB.setMscEndDate(Date.valueOf("2015-10-15"));
        merchantSettlementCycle = merchantSettlementCycleRepository.save(merchantSettlementCycleB);

        log.info("Original MerchantSettlementCycle " + merchantSettlementCycle.toString());
        // Add to the tempSet
        tempSet.add(merchantSettlementCycleB);


        // Get the data
        List<MerchantSettlementCycle> searchmerchantSettlementCycle = merchantSettlementCycleRepository.findByMscMerchantNoAndMscRedemptionMerchantAndMscMerchantLocationAndMscStartDateAfterAndMscEndDateBefore(merchantSettlementCycle.getMscMerchantNo(), merchantSettlementCycle.getMscRedemptionMerchant(), merchantSettlementCycle.getMscMerchantLocation(), Date.valueOf("2015-10-01"),Date.valueOf("2015-10-31"));
        Assert.assertNotNull(searchmerchantSettlementCycle);
        log.info("Searched MerchantSettlementCycle : " + searchmerchantSettlementCycle.toString());


    }



    @Test
    public void testFindLastGeneratedSettlementCycle(){

        MerchantSettlementCycleFixture merchantSettlementCycleFixture=new MerchantSettlementCycleFixture();

        MerchantSettlementCycle merchantSettlementCycle = merchantSettlementCycleFixture.standardMerchantSettlementCycle();
        merchantSettlementCycle = merchantSettlementCycleRepository.save(merchantSettlementCycle);

        log.info("Original MerchantSettlementCycle " + merchantSettlementCycle.toString());


        // Add to the tempSet
        tempSet.add(merchantSettlementCycle);

        MerchantSettlementCycleFixture merchantSettlementCycleFixtureB=new MerchantSettlementCycleFixture();

        MerchantSettlementCycle merchantSettlementCycleB = merchantSettlementCycleFixture.standardMerchantSettlementCycle();
        merchantSettlementCycleB.setMscStartDate(Date.valueOf("2015-10-09"));
        merchantSettlementCycleB.setMscEndDate(Date.valueOf("2015-10-15"));
        merchantSettlementCycle = merchantSettlementCycleRepository.save(merchantSettlementCycleB);

        log.info("Original MerchantSettlementCycle " + merchantSettlementCycle.toString());
        // Add to the tempSet
        tempSet.add(merchantSettlementCycleB);

        // Get the data
        Date lastGeneratedDate = merchantSettlementCycleRepository.findLastGeneratedSettlementCycle(merchantSettlementCycle.getMscMerchantNo(),merchantSettlementCycle.getMscRedemptionMerchant(),merchantSettlementCycle.getMscMerchantLocation());
        Assert.assertTrue(lastGeneratedDate != null);
        log.info("Retrieved last generated date : " + lastGeneratedDate);

    }





    @After
    public void tearDown() {

        for(MerchantSettlementCycle merchantSettlementCycle : tempSet ) {

            merchantSettlementCycleRepository.delete(merchantSettlementCycle);

        }

    }



}
