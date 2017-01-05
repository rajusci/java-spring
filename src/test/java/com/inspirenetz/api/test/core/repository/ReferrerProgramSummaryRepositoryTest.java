package com.inspirenetz.api.test.core.repository;

import com.google.common.collect.Sets;
import com.inspirenetz.api.core.domain.ReferrerProgramSummary;
import com.inspirenetz.api.core.repository.ReferrerProgramSummaryRepository;
import com.inspirenetz.api.test.config.ApplicationTestConfig;
import com.inspirenetz.api.test.config.PersistenceTestConfig;
import com.inspirenetz.api.test.core.fixture.ReferrerProgramSummaryFixture;
import org.junit.*;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.HashSet;
import java.util.Set;

/**
 * Created by ameen on 8/10/15.
 */
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes  = {ApplicationTestConfig.class, PersistenceTestConfig.class})
public class ReferrerProgramSummaryRepositoryTest {

    private static Logger log = LoggerFactory.getLogger(ReferrerProgramSummaryRepositoryTest.class);

    @Autowired
    private ReferrerProgramSummaryRepository referrerProgramSummaryRepository;

    Set<ReferrerProgramSummary> referrerProgramSummarySet =new HashSet<>();


    @Before
    public void setup() {}

    @Test
    public void test1Create(){


        ReferrerProgramSummary referrerProgramSummary = referrerProgramSummaryRepository.save(ReferrerProgramSummaryFixture.standardReferrerProgramSummary());
        log.info(referrerProgramSummary.toString());
        Assert.assertNotNull(referrerProgramSummary.getRpsId());

        referrerProgramSummarySet.add(referrerProgramSummary);

    }

    @Test
    public void test2Update() {

        ReferrerProgramSummary referrerProgramSummary = ReferrerProgramSummaryFixture.standardReferrerProgramSummary();
        referrerProgramSummary = referrerProgramSummaryRepository.save(referrerProgramSummary);
        log.info("Original ReferrerProgramSummary " + referrerProgramSummary.toString());

        ReferrerProgramSummary updatedReferrerProgramSummary = ReferrerProgramSummaryFixture.updatedStandardReferrerProgramSummary(referrerProgramSummary);
        updatedReferrerProgramSummary = referrerProgramSummaryRepository.save(updatedReferrerProgramSummary);
        log.info("Updated ReferrerProgramSummary "+ updatedReferrerProgramSummary.toString());

        referrerProgramSummarySet.add(referrerProgramSummary);
        referrerProgramSummarySet.add(updatedReferrerProgramSummary);

    }



    @Test
    public void findByRpsMerchantNoAndRpsRefereeLoyaltyId() {

        // Get the standard referrerProgramSummary
        ReferrerProgramSummary referrerProgramSummary = ReferrerProgramSummaryFixture.standardReferrerProgramSummary();

        referrerProgramSummary =referrerProgramSummaryRepository.save(referrerProgramSummary);

        referrerProgramSummary =referrerProgramSummaryRepository.findByRpsMerchantNoAndRpsRefereeLoyaltyId(referrerProgramSummary.getRpsMerchantNo(),referrerProgramSummary.getRpsRefereeLoyaltyId());

        Assert.assertNotNull(referrerProgramSummary);
        referrerProgramSummarySet.add(referrerProgramSummary);






    }

    @Test
    public void findByRpsMerchantNoAndRpsRefereeLoyaltyIdAndRpsProgramId() {

        ReferrerProgramSummary referrerProgramSummary = ReferrerProgramSummaryFixture.standardReferrerProgramSummary();

        referrerProgramSummary =referrerProgramSummaryRepository.save(referrerProgramSummary);

        referrerProgramSummary =referrerProgramSummaryRepository.findByRpsMerchantNoAndRpsRefereeLoyaltyIdAndRpsProgramId(referrerProgramSummary.getRpsMerchantNo(),referrerProgramSummary.getRpsRefereeLoyaltyId(),referrerProgramSummary.getRpsProgramId());

        Assert.assertNotNull(referrerProgramSummary);
        referrerProgramSummarySet.add(referrerProgramSummary);


    }

    @After
    public void tearDown() {

        for (ReferrerProgramSummary referrerProgramSummary :referrerProgramSummarySet){

            referrerProgramSummaryRepository.delete(referrerProgramSummary);
        }
    }
}
