package com.inspirenetz.api.test.core.service;

import com.google.common.collect.Lists;
import com.inspirenetz.api.core.domain.MerchantRewardSummary;
import com.inspirenetz.api.core.service.MerchantRewardSummaryService;
import com.inspirenetz.api.test.config.ApplicationTestConfig;
import com.inspirenetz.api.test.config.PersistenceTestConfig;
import com.inspirenetz.api.test.config.ServiceTestConfig;
import com.inspirenetz.api.test.core.fixture.MerchantRewardSummaryFixture;
import org.junit.After;
import org.junit.Assert;
import org.junit.FixMethodOrder;
import org.junit.Test;
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
public class MerchantRewardSummaryServiceTest {


    private static Logger log = LoggerFactory.getLogger(MerchantRewardSummaryServiceTest.class);

    @Autowired
    private MerchantRewardSummaryService merchantRewardSummaryService;


    Set<MerchantRewardSummary> tempSet = new HashSet<>(0);


    @Test
    public void testCreate() {

        MerchantRewardSummary merchantRewardSummary = MerchantRewardSummaryFixture.standardMerchantRewardSummary();
        merchantRewardSummary = merchantRewardSummaryService.saveMerchantRewardSummary(merchantRewardSummary);

        // Add to tempSet
        tempSet.add(merchantRewardSummary);

        Assert.assertNotNull(merchantRewardSummary);

    }



    @Test
    public void testUpdate() {

        MerchantRewardSummary merchantRewardSummary = MerchantRewardSummaryFixture.standardMerchantRewardSummary();
        merchantRewardSummary = merchantRewardSummaryService.saveMerchantRewardSummary(merchantRewardSummary);
        Assert.assertNotNull(merchantRewardSummary);
        log.info("MerchantRewardSummary Original:" + merchantRewardSummary.toString());

        // Add to tempSet
        tempSet.add(merchantRewardSummary);

        // Update the details
        MerchantRewardSummary updatedDetails = MerchantRewardSummaryFixture.updatedStandardMerchantRewardSummary(merchantRewardSummary);
        updatedDetails = merchantRewardSummaryService.saveMerchantRewardSummary(updatedDetails);
        Assert.assertNotNull(updatedDetails);
        log.info("MerchantRewardSummary Updated: " + updatedDetails.toString() );


    }

    @Test
    public void testFindByMrsMerchantNo() throws Exception {

        // Get the standardMerchantRewardSummary
        Set<MerchantRewardSummary> merchantRewardSummarySet = MerchantRewardSummaryFixture.standardMerchantRewardSummaries();
        List<MerchantRewardSummary> merchantRewardSummaries = Lists.newArrayList((Iterable<MerchantRewardSummary>)merchantRewardSummarySet);
        merchantRewardSummaryService.saveAll(merchantRewardSummaries);

        // Add to tempSet
        tempSet.addAll(merchantRewardSummarySet);

        // Get the standardMerchantRewardSummary
        MerchantRewardSummary  merchantRewardSummary = MerchantRewardSummaryFixture.standardMerchantRewardSummary();


        List<MerchantRewardSummary> merchantRewardSummaryList = merchantRewardSummaryService.findByMrsMerchantNo(merchantRewardSummary.getMrsMerchantNo());
        Assert.assertTrue(!merchantRewardSummaryList.isEmpty());
        log.info("MerchantRewardSummary List: " + merchantRewardSummaryList.toString());

    }

    @Test
    public void testFindByMrsMerchantNoAndMrsCurrencyId() throws Exception {

        // Get the standardMerchantRewardSummary
        Set<MerchantRewardSummary> merchantRewardSummarySet = MerchantRewardSummaryFixture.standardMerchantRewardSummaries();
        List<MerchantRewardSummary> merchantRewardSummaries = Lists.newArrayList((Iterable<MerchantRewardSummary>)merchantRewardSummarySet);
        merchantRewardSummaryService.saveAll(merchantRewardSummaries);

        // Add to tempSet
        tempSet.addAll(merchantRewardSummarySet);

        // Get the standard MerchantRewardSummary object
        MerchantRewardSummary merchantRewardSummary = MerchantRewardSummaryFixture.standardMerchantRewardSummary();

        // Get the standardMerchantRewardSummary
        List<MerchantRewardSummary> merchantRewardSummaryList  = merchantRewardSummaryService.findByMrsMerchantNoAndMrsCurrencyId(merchantRewardSummary.getMrsMerchantNo(),merchantRewardSummary.getMrsCurrencyId());
        Assert.assertTrue(!merchantRewardSummaryList.isEmpty());
        log.info("MerchantRewardSummary List: " + merchantRewardSummaryList.toString());

    }


    @Test
    public void testFindByMrsMerchantNoAndMrsCurrencyIdAndMrsBranch() throws Exception {

        // Get the standardMerchantRewardSummary
        Set<MerchantRewardSummary> merchantRewardSummarySet = MerchantRewardSummaryFixture.standardMerchantRewardSummaries();
        List<MerchantRewardSummary> merchantRewardSummaries = Lists.newArrayList((Iterable<MerchantRewardSummary>)merchantRewardSummarySet);
        merchantRewardSummaryService.saveAll(merchantRewardSummaries);

        // Add to tempSet
        tempSet.addAll(merchantRewardSummarySet);

        // Get the standard MerchantRewardSummary object
        MerchantRewardSummary merchantRewardSummary = MerchantRewardSummaryFixture.standardMerchantRewardSummary();

        // Get the standardMerchantRewardSummary
        List<MerchantRewardSummary> merchantRewardSummaryList  = merchantRewardSummaryService.findByMrsMerchantNoAndMrsCurrencyIdAndMrsBranch(merchantRewardSummary.getMrsMerchantNo(),merchantRewardSummary.getMrsCurrencyId(),merchantRewardSummary.getMrsBranch());
        Assert.assertTrue(!merchantRewardSummaryList.isEmpty());
        log.info("MerchantRewardSummary List: " + merchantRewardSummaryList.toString());

    }



    @Test
    public void testFindByMrsMerchantNoAndMrsCurrencyIdAndMrsBranchAndMrsDate() throws Exception {

        // Get the standardMerchantRewardSummary
        Set<MerchantRewardSummary> merchantRewardSummarySet = MerchantRewardSummaryFixture.standardMerchantRewardSummaries();
        List<MerchantRewardSummary> merchantRewardSummaries = Lists.newArrayList((Iterable<MerchantRewardSummary>)merchantRewardSummarySet);
        merchantRewardSummaryService.saveAll(merchantRewardSummaries);

        // Add to tempSet
        tempSet.addAll(merchantRewardSummarySet);

        // Get the standard MerchantRewardSummary object
        MerchantRewardSummary merchantRewardSummary = MerchantRewardSummaryFixture.standardMerchantRewardSummary();

        // Get the standardMerchantRewardSummary
        MerchantRewardSummary searchMerchantRewardSummary  = merchantRewardSummaryService.findByMrsMerchantNoAndMrsCurrencyIdAndMrsBranchAndMrsDate(merchantRewardSummary.getMrsMerchantNo(),merchantRewardSummary.getMrsCurrencyId(),merchantRewardSummary.getMrsBranch(),merchantRewardSummary.getMrsDate());
        Assert.assertNotNull(searchMerchantRewardSummary);
        log.info("MerchantRewardSummary: " + searchMerchantRewardSummary.toString());

    }


    @Test
    public void testFindByMrsId() {

        MerchantRewardSummary merchantRewardSummary = MerchantRewardSummaryFixture.standardMerchantRewardSummary();
        merchantRewardSummary = merchantRewardSummaryService.saveMerchantRewardSummary(merchantRewardSummary);

        // Add to tempSet
        tempSet.add(merchantRewardSummary);


        // Read the MerchantRewardSummary
        MerchantRewardSummary merchantRewardSummary1 = merchantRewardSummaryService.findByMrsId(merchantRewardSummary.getMrsId());
        Assert.assertNotNull(merchantRewardSummary1);
        log.info("Merchant Reward Summary " + merchantRewardSummary1.toString());


    }


    @After
    public void tearDown() throws Exception {

        for( MerchantRewardSummary merchantRewardSummary : tempSet) {

            merchantRewardSummaryService.deleteMerchantRewardSummary(merchantRewardSummary);

        }
    }
}
