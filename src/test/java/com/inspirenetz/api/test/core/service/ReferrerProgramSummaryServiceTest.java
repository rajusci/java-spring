package com.inspirenetz.api.test.core.service;

import com.inspirenetz.api.core.domain.ReferrerProgramSummary;
import com.inspirenetz.api.core.repository.ReferrerProgramSummaryRepository;
import com.inspirenetz.api.core.service.ReferrerProgramSummaryService;
import com.inspirenetz.api.test.config.*;
import com.inspirenetz.api.test.core.fixture.ReferrerProgramSummaryFixture;
import org.junit.*;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.HashSet;
import java.util.Set;

/**
 * Created by ameen on 8/10/15.
 */
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes  = {ApplicationTestConfig.class, PersistenceTestConfig.class, ServiceTestConfig.class,ServiceTestConfig.class, SecurityTestConfig.class, NotificationTestConfig.class})
public class ReferrerProgramSummaryServiceTest {

    private static Logger log = LoggerFactory.getLogger(ReferrerProgramSummaryServiceTest.class);

    @Autowired
    private ReferrerProgramSummaryService referrerProgramSummaryService;

    Set<ReferrerProgramSummary> referrerProgramSummarySet =new HashSet<>();


    @Before
    public void setup() {}

    @Test
    public void test1Create(){


        ReferrerProgramSummary referrerProgramSummary = referrerProgramSummaryService.saveReferrerProgramSummary(ReferrerProgramSummaryFixture.standardReferrerProgramSummary());
        log.info(referrerProgramSummary.toString());
        Assert.assertNotNull(referrerProgramSummary.getRpsId());

        referrerProgramSummarySet.add(referrerProgramSummary);

    }

    @Test
    public void test2Update() {

        ReferrerProgramSummary referrerProgramSummary = ReferrerProgramSummaryFixture.standardReferrerProgramSummary();
        referrerProgramSummary = referrerProgramSummaryService.saveReferrerProgramSummary(referrerProgramSummary);
        log.info("Original ReferrerProgramSummary " + referrerProgramSummary.toString());

        ReferrerProgramSummary updatedReferrerProgramSummary = ReferrerProgramSummaryFixture.updatedStandardReferrerProgramSummary(referrerProgramSummary);
        updatedReferrerProgramSummary = referrerProgramSummaryService.saveReferrerProgramSummary(updatedReferrerProgramSummary);
        log.info("Updated ReferrerProgramSummary "+ updatedReferrerProgramSummary.toString());

        referrerProgramSummarySet.add(referrerProgramSummary);
        referrerProgramSummarySet.add(updatedReferrerProgramSummary);

    }



    @Test
    public void findByRpsMerchantNoAndRpsRefereeLoyaltyId() {

        // Get the standard referrerProgramSummary
        ReferrerProgramSummary referrerProgramSummary = ReferrerProgramSummaryFixture.standardReferrerProgramSummary();

        referrerProgramSummary =referrerProgramSummaryService.saveReferrerProgramSummary(referrerProgramSummary);

        referrerProgramSummary =referrerProgramSummaryService.findByRpsMerchantNoAndRpsRefereeLoyaltyId(referrerProgramSummary.getRpsMerchantNo(),referrerProgramSummary.getRpsRefereeLoyaltyId());

        Assert.assertNotNull(referrerProgramSummary);
        referrerProgramSummarySet.add(referrerProgramSummary);






    }

    @Test
    public void findByRpsMerchantNoAndRpsRefereeLoyaltyIdAndRpsProgramId() {

        ReferrerProgramSummary referrerProgramSummary = ReferrerProgramSummaryFixture.standardReferrerProgramSummary();

        referrerProgramSummary =referrerProgramSummaryService.saveReferrerProgramSummary(referrerProgramSummary);

        referrerProgramSummary =referrerProgramSummaryService.findByRpsMerchantNoAndRpsRefereeLoyaltyIdAndRpsProgramId(referrerProgramSummary.getRpsMerchantNo(),referrerProgramSummary.getRpsRefereeLoyaltyId(),referrerProgramSummary.getRpsProgramId());

        Assert.assertNotNull(referrerProgramSummary);
        referrerProgramSummarySet.add(referrerProgramSummary);


    }

    @After
    public void tearDown() {

        for (ReferrerProgramSummary referrerProgramSummary :referrerProgramSummarySet){

            referrerProgramSummaryService.delete(referrerProgramSummary);
        }
    }
}
