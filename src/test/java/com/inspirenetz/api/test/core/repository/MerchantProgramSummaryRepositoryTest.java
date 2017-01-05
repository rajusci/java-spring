package com.inspirenetz.api.test.core.repository;

import com.inspirenetz.api.core.domain.MerchantProgramSummary;
import com.inspirenetz.api.core.repository.MerchantProgramSummaryRepository;
import com.inspirenetz.api.test.config.ApplicationTestConfig;
import com.inspirenetz.api.test.config.PersistenceTestConfig;
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
 * Created by sandheepgr on 20/5/14.
 */
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes  = {ApplicationTestConfig.class, PersistenceTestConfig.class})
public class MerchantProgramSummaryRepositoryTest {


    // Create the logger
    private static Logger log = LoggerFactory.getLogger(MerchantProgramSummaryRepositoryTest.class);

    @Autowired
    private MerchantProgramSummaryRepository merchantProgramSummaryRepository;

    private Set<MerchantProgramSummary> tempSet = new HashSet<>(0);



    @Test
    public void testCreate() {

        MerchantProgramSummary merchantProgramSummary = MerchantProgramSummaryFixture.standardMerchantProgramSummary();
        merchantProgramSummary = merchantProgramSummaryRepository.save(merchantProgramSummary);

        // Add to the tempSet
        tempSet.add(merchantProgramSummary);


        Assert.assertNotNull(merchantProgramSummary);
    }



    @Test
    public void testUpdate() {

        MerchantProgramSummary merchantProgramSummary = MerchantProgramSummaryFixture.standardMerchantProgramSummary();
        merchantProgramSummary = merchantProgramSummaryRepository.save(merchantProgramSummary);
        Assert.assertNotNull(merchantProgramSummary);
        log.info("MerchantProgramSummary Original:" + merchantProgramSummary.toString());

        // Add to the tempSet
        tempSet.add(merchantProgramSummary);


        // Update the details
        MerchantProgramSummary updatedDetails = MerchantProgramSummaryFixture.updatedStandardMerchantProgramSummary(merchantProgramSummary);
        updatedDetails = merchantProgramSummaryRepository.save(updatedDetails);
        Assert.assertNotNull(updatedDetails);
        log.info("MerchantProgramSummary Updated: " + updatedDetails.toString() );


    }


    @Test
    public void testFindByMpsMerchantNo() throws Exception {

        // Get the standardMerchantProgramSummary
        Set<MerchantProgramSummary> merchantProgramSummarySet = MerchantProgramSummaryFixture.standardMerchantProgramSummaries();
        merchantProgramSummaryRepository.save(merchantProgramSummarySet);

        // Add to the tempSet
        tempSet.addAll(merchantProgramSummarySet);


        // Get the standardMerchantProgramSummary
        MerchantProgramSummary  merchantProgramSummary = MerchantProgramSummaryFixture.standardMerchantProgramSummary();


        List<MerchantProgramSummary> merchantProgramSummaryList = merchantProgramSummaryRepository.findByMpsMerchantNo(merchantProgramSummary.getMpsMerchantNo());
        Assert.assertTrue(!merchantProgramSummaryList.isEmpty());
        log.info("MerchantProgramSummary List: " + merchantProgramSummaryList.toString());

    }

    @Test
    public void testFindByMpsMerchantNoAndMpsBranch() throws Exception {

        // Get the standardMerchantProgramSummary
        Set<MerchantProgramSummary> merchantProgramSummarySet = MerchantProgramSummaryFixture.standardMerchantProgramSummaries();
        merchantProgramSummaryRepository.save(merchantProgramSummarySet);

        // Add to the tempSet
        tempSet.addAll(merchantProgramSummarySet);

        // Get the standardMerchantProgramSummary
        MerchantProgramSummary  merchantProgramSummary = MerchantProgramSummaryFixture.standardMerchantProgramSummary();


        List<MerchantProgramSummary> merchantProgramSummaryList = merchantProgramSummaryRepository.findByMpsMerchantNoAndMpsBranch(merchantProgramSummary.getMpsMerchantNo(),merchantProgramSummary.getMpsBranch());
        Assert.assertTrue(!merchantProgramSummaryList.isEmpty());
        log.info("MerchantProgramSummary List: " + merchantProgramSummaryList.toString());

    }

    @Test
    public void testFindByMpsMerchantNoAndMpsBranchAndMpsProgramId() throws Exception {

        // Get the standardMerchantProgramSummary
        Set<MerchantProgramSummary> merchantProgramSummarySet = MerchantProgramSummaryFixture.standardMerchantProgramSummaries();
        merchantProgramSummaryRepository.save(merchantProgramSummarySet);

        // Add to the tempSet
        tempSet.addAll(merchantProgramSummarySet);

        // Get the standard MerchantProgramSummary object
        MerchantProgramSummary merchantProgramSummary = MerchantProgramSummaryFixture.standardMerchantProgramSummary();

        // Get the standardMerchantProgramSummary
        MerchantProgramSummary  searchMerchantProgramSummary = merchantProgramSummaryRepository.findByMpsMerchantNoAndMpsBranchAndMpsProgramId(merchantProgramSummary.getMpsMerchantNo(),merchantProgramSummary.getMpsBranch(),merchantProgramSummary.getMpsProgramId());
        Assert.assertNotNull(searchMerchantProgramSummary);
        log.info("MerchantProgramSummary Information :" + searchMerchantProgramSummary.toString());

    }


    @Test
    public void testFindByMpsId() {

        MerchantProgramSummary merchantProgramSummary = MerchantProgramSummaryFixture.standardMerchantProgramSummary();
        merchantProgramSummary = merchantProgramSummaryRepository.save(merchantProgramSummary);

        // Add to the tempSet
        tempSet.add(merchantProgramSummary);


        MerchantProgramSummary merchantProgramSummary1 = merchantProgramSummaryRepository.findByMpsId(merchantProgramSummary.getMpsId());
        Assert.assertNotNull(merchantProgramSummary1);;
        log.info("Merchnt program summary" + merchantProgramSummary1);

    }

    @After
    public void tearDown() throws Exception {

        for( MerchantProgramSummary merchantProgramSummary : tempSet) {

            merchantProgramSummaryRepository.delete(merchantProgramSummary);

        }
    }
}
