package com.inspirenetz.api.test.core.repository;

import com.google.common.collect.Sets;
import com.inspirenetz.api.core.domain.PartyApproval;
import com.inspirenetz.api.core.repository.PartyApprovalRepository;
import com.inspirenetz.api.test.config.ApplicationTestConfig;
import com.inspirenetz.api.test.config.PersistenceTestConfig;
import com.inspirenetz.api.test.core.fixture.PartyApprovalFixture;
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

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Created by saneeshci on 30/08/14.
 */
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes  = {ApplicationTestConfig.class, PersistenceTestConfig.class})
public class PartyApprovalRepositoryTest {

    private static Logger log = LoggerFactory.getLogger(PartyApprovalRepositoryTest.class);

    @Autowired
    private PartyApprovalRepository partyApprovalRepository;

    Set<PartyApproval> tempSet = new HashSet<>(0);

    @Before
    public void setup() {}

    @Test
    public void test1Create(){


        PartyApproval partyApproval = partyApprovalRepository.save(PartyApprovalFixture.standardPartyApproval());
        log.info(partyApproval.toString());
        // Add the tempset
        tempSet.add(partyApproval);
        Assert.assertNotNull(partyApproval.getPapId());

    }

    @Test
    public void test2Update() {

        PartyApproval partyApproval = PartyApprovalFixture.standardPartyApproval();
        partyApproval = partyApprovalRepository.save(partyApproval);
        log.info("Original PartyApproval " + partyApproval.toString());

        PartyApproval updatedPartyApproval = PartyApprovalFixture.updatedStandardPartyApproval(partyApproval);
        updatedPartyApproval = partyApprovalRepository.save(updatedPartyApproval);
        log.info("Updated PartyApproval "+ updatedPartyApproval.toString());
        tempSet.add(partyApproval);

    }
    @Test
    public void test3FindByPapApproverAndPapRequestorAndPapType() {

        // Get the standard partyApproval
        PartyApproval partyApproval = PartyApprovalFixture.standardPartyApproval();
        partyApproval = partyApprovalRepository.save(partyApproval);
        tempSet.add(partyApproval);

        PartyApproval partyApprovalObj = partyApprovalRepository.findByPapApproverAndPapRequestorAndPapType(partyApproval.getPapApprover(), partyApproval.getPapRequest(),partyApproval.getPapType());
        Assert.assertNotNull(partyApprovalObj.getPapId());

        log.info("partyApprovals by approver and requestor and type " + partyApprovalObj.toString());

    }


  /*  @Test
    public void test3FindByLiaCustomerAndLiaLinkRequest() {

        // Get the standard partyApproval
        PartyApproval partyApproval = PartyApprovalFixture.standardPartyApproval();
        partyApproval = partyApprovalRepository.save(partyApproval);

        PartyApproval partyApprovalObj = partyApprovalRepository.findByLiaCustomerAndLiaLinkRequest(partyApproval.getPapApprover(), partyApproval.getPapRequest());
        Assert.assertNotNull(partyApprovalObj.getPapId());

        log.info("partyApprovals by customerno and linking request " + partyApprovalObj.toString());
        List<PartyApproval> partyApprovals = new ArrayList<PartyApproval>();
        partyApprovals.add(partyApprovalObj);
        Set<PartyApproval> partyApprovalSet = Sets.newHashSet((Iterable<PartyApproval>) partyApprovals);
        log.info("partyApproval list "+ partyApprovalSet.toString());

    }*/


    @Test
    public void test5FindByPapApproverAndType() {

        // Create the partyApproval
        PartyApproval partyApproval = PartyApprovalFixture.standardPartyApproval();
        partyApprovalRepository.save(partyApproval);
        tempSet.add(partyApproval);

        Assert.assertNotNull(partyApproval.getPapId());
        log.info("PartyApproval created");

        // Check the partyApproval name
        List<PartyApproval> partyApprovals = partyApprovalRepository.findByPapApproverAndPapType(partyApproval.getPapApprover(), partyApproval.getPapType());
        Assert.assertTrue(partyApprovals.size()>0);
        Set<PartyApproval> partyApprovalSet = Sets.newHashSet((Iterable<PartyApproval>) partyApprovals);
        log.info("partyApproval list "+ partyApprovalSet.toString());


    }


    @Test
    public void test6FindByPapApprover() {


        // Create the partyApproval
        PartyApproval partyApproval = PartyApprovalFixture.standardPartyApproval();
        partyApproval.setPapStatus(2);
        partyApprovalRepository.save(partyApproval);
        tempSet.add(partyApproval);

        Assert.assertNotNull(partyApproval.getPapId());
        log.info("PartyApproval created");

        // Check the partyApproval name
        List<PartyApproval> partyApprovals = partyApprovalRepository.findByPapApprover(partyApproval.getPapApprover());
        Assert.assertTrue(partyApprovals.size()>0);
        Set<PartyApproval> partyApprovalSet = Sets.newHashSet((Iterable<PartyApproval>) partyApprovals);
        log.info("partyApproval list "+ partyApprovalSet.toString());


    }

    @Test
    public void test6FindByPapRequestor() {


        // Create the partyApproval
        PartyApproval partyApproval = PartyApprovalFixture.standardPartyApproval();
        partyApproval.setPapStatus(2);
        partyApprovalRepository.save(partyApproval);
        tempSet.add(partyApproval);

        Assert.assertNotNull(partyApproval.getPapId());
        log.info("PartyApproval created");

        // Check the partyApproval name
        List<PartyApproval> partyApprovals = partyApprovalRepository.findByPapRequestor(partyApproval.getPapRequestor());
        Assert.assertTrue(partyApprovals.size()>0);
        Set<PartyApproval> partyApprovalSet = Sets.newHashSet((Iterable<PartyApproval>) partyApprovals);
        log.info("partyApproval list "+ partyApprovalSet.toString());


    }


    @After
    public void tearDown() {

        for(PartyApproval partyApproval : tempSet) {


            if ( partyApproval != null ) {
                partyApprovalRepository.delete(partyApproval);
            }

        }
    }


}
