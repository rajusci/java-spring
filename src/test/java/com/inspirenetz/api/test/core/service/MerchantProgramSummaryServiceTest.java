package com.inspirenetz.api.test.core.service;

import com.google.common.collect.Lists;
import com.inspirenetz.api.core.domain.MerchantProgramSummary;
import com.inspirenetz.api.core.service.MerchantProgramSummaryService;
import com.inspirenetz.api.test.config.ApplicationTestConfig;
import com.inspirenetz.api.test.config.PersistenceTestConfig;
import com.inspirenetz.api.test.config.ServiceTestConfig;
import com.inspirenetz.api.test.core.fixture.MerchantProgramSummaryFixture;
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
public class MerchantProgramSummaryServiceTest {


    private static Logger log = LoggerFactory.getLogger(MerchantProgramSummaryServiceTest.class);

    @Autowired
    private MerchantProgramSummaryService merchantProgramSummaryService;

    private Set<MerchantProgramSummary> tempSet = new HashSet<>(0);


    @Test
    public void testCreate() {

        MerchantProgramSummary merchantProgramSummary = MerchantProgramSummaryFixture.standardMerchantProgramSummary();
        merchantProgramSummary = merchantProgramSummaryService.saveMerchantProgramSummary(merchantProgramSummary);

        // Add to the tempSet
        tempSet.add(merchantProgramSummary);

        Assert.assertNotNull(merchantProgramSummary);
    }



    @Test
    public void testUpdate() {

        MerchantProgramSummary merchantProgramSummary = MerchantProgramSummaryFixture.standardMerchantProgramSummary();
        merchantProgramSummary = merchantProgramSummaryService.saveMerchantProgramSummary(merchantProgramSummary);
        Assert.assertNotNull(merchantProgramSummary);
        log.info("MerchantProgramSummary Original:" + merchantProgramSummary.toString());

        // Add to the tempSet
        tempSet.add(merchantProgramSummary);

        // Update the details
        MerchantProgramSummary updatedDetails = MerchantProgramSummaryFixture.updatedStandardMerchantProgramSummary(merchantProgramSummary);
        updatedDetails = merchantProgramSummaryService.saveMerchantProgramSummary(updatedDetails);
        Assert.assertNotNull(updatedDetails);
        log.info("MerchantProgramSummary Updated: " + updatedDetails.toString() );


    }


    @Test
    public void testFindByMpsMerchantNo() throws Exception {

        // Get the standardMerchantProgramSummary
        Set<MerchantProgramSummary> merchantProgramSummarySet = MerchantProgramSummaryFixture.standardMerchantProgramSummaries();
        List<MerchantProgramSummary> merchantProgramSummaries = Lists.newArrayList((Iterable<MerchantProgramSummary>)merchantProgramSummarySet);
        merchantProgramSummaryService.saveAll(merchantProgramSummaries);

        // Add to the tempSet
        tempSet.addAll(merchantProgramSummarySet);

        // Get the standardMerchantProgramSummary
        MerchantProgramSummary  merchantProgramSummary = MerchantProgramSummaryFixture.standardMerchantProgramSummary();


        List<MerchantProgramSummary> merchantProgramSummaryList = merchantProgramSummaryService.findByMpsMerchantNo(merchantProgramSummary.getMpsMerchantNo());
        Assert.assertTrue(!merchantProgramSummaryList.isEmpty());
        log.info("MerchantProgramSummary List: " + merchantProgramSummaryList.toString());

    }

    @Test
    public void testFindByMpsMerchantNoAndMpsBranch() throws Exception {

        // Get the standardMerchantProgramSummary
        Set<MerchantProgramSummary> merchantProgramSummarySet = MerchantProgramSummaryFixture.standardMerchantProgramSummaries();
        List<MerchantProgramSummary> merchantProgramSummaries = Lists.newArrayList((Iterable<MerchantProgramSummary>)merchantProgramSummarySet);
        merchantProgramSummaryService.saveAll(merchantProgramSummaries);

        // Add to the tempSet
        tempSet.addAll(merchantProgramSummarySet);

        // Get the standardMerchantProgramSummary
        MerchantProgramSummary  merchantProgramSummary = MerchantProgramSummaryFixture.standardMerchantProgramSummary();


        List<MerchantProgramSummary> merchantProgramSummaryList = merchantProgramSummaryService.findByMpsMerchantNoAndMpsBranch(merchantProgramSummary.getMpsMerchantNo(),merchantProgramSummary.getMpsBranch());
        Assert.assertTrue(!merchantProgramSummaryList.isEmpty());
        log.info("MerchantProgramSummary List: " + merchantProgramSummaryList.toString());

    }

    @Test
    public void testFindByMpsMerchantNoAndMpsBranchAndMpsProgramId() throws Exception {

        // Get the standardMerchantProgramSummary
        Set<MerchantProgramSummary> merchantProgramSummarySet = MerchantProgramSummaryFixture.standardMerchantProgramSummaries();
        List<MerchantProgramSummary> merchantProgramSummaries = Lists.newArrayList((Iterable<MerchantProgramSummary>)merchantProgramSummarySet);
        merchantProgramSummaryService.saveAll(merchantProgramSummaries);

        // Add to the tempSet
        tempSet.addAll(merchantProgramSummarySet);

        // Get the standard MerchantProgramSummary object
        MerchantProgramSummary merchantProgramSummary = MerchantProgramSummaryFixture.standardMerchantProgramSummary();

        // Get the standardMerchantProgramSummary
        MerchantProgramSummary  searchMerchantProgramSummary = merchantProgramSummaryService.findByMpsMerchantNoAndMpsBranchAndMpsProgramId(merchantProgramSummary.getMpsMerchantNo(),merchantProgramSummary.getMpsBranch(),merchantProgramSummary.getMpsProgramId());
        Assert.assertNotNull(searchMerchantProgramSummary);
        log.info("MerchantProgramSummary Information :" + searchMerchantProgramSummary.toString());

    }


    @Test
    public void testFindByMpsId() {

        MerchantProgramSummary merchantProgramSummary = MerchantProgramSummaryFixture.standardMerchantProgramSummary();
        merchantProgramSummary = merchantProgramSummaryService.saveMerchantProgramSummary(merchantProgramSummary);

        // Add to the tempSet
        tempSet.add(merchantProgramSummary);


        MerchantProgramSummary merchantProgramSummary1 = merchantProgramSummaryService.findByMpsId(merchantProgramSummary.getMpsId());
        Assert.assertNotNull(merchantProgramSummary1);;
        log.info("Merchnt program summary" + merchantProgramSummary1);

    }

    @After
    public void tearDown() throws Exception {

        for( MerchantProgramSummary merchantProgramSummary : tempSet) {

            merchantProgramSummaryService.deleteMerchantProgramSummary(merchantProgramSummary);

        }
    }
    
}
