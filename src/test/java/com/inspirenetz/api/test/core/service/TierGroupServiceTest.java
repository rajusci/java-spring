package com.inspirenetz.api.test.core.service;

import com.google.common.collect.Sets;
import com.inspirenetz.api.core.domain.Tier;
import com.inspirenetz.api.core.domain.TierGroup;
import com.inspirenetz.api.core.repository.TierGroupRepository;
import com.inspirenetz.api.core.service.TierGroupService;
import com.inspirenetz.api.core.service.TierService;
import com.inspirenetz.api.rest.exception.InspireNetzException;
import com.inspirenetz.api.test.config.ApplicationTestConfig;
import com.inspirenetz.api.test.config.PersistenceTestConfig;
import com.inspirenetz.api.test.config.ServiceTestConfig;
import com.inspirenetz.api.test.core.fixture.TierFixture;
import com.inspirenetz.api.test.core.fixture.TierGroupFixture;
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
 * Created by sandheepgr on 30/4/14.
 */
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes  = {ApplicationTestConfig.class, PersistenceTestConfig.class, ServiceTestConfig.class})
public class TierGroupServiceTest {


    private static Logger log = LoggerFactory.getLogger(TierGroupServiceTest.class);

    @Autowired
    private TierGroupService tierGroupService;

    @Autowired
    private TierService tierService;

    @Autowired
    private TierGroupRepository tierGroupRepository;

    // Set holding the temporary variable
    Set<Tier> tempSet = new HashSet<>(0);

    @Before
    public void setUp() {}



    @Test
    public void test1FindByMerchantNo() {

        // Get the standard tierGroup
        TierGroup tierGroup = TierGroupFixture.standardTierGroup();

        Page<TierGroup> tierGroups = tierGroupService.searchTierGroups(tierGroup.getTigMerchantNo(),1L,3,"0", "0", constructPageSpecification(1));
        log.info("tierGroups by merchant no " + tierGroups.toString());
        Set<TierGroup> tierGroupSet = Sets.newHashSet((Iterable<TierGroup>) tierGroups);
        log.info("tierGroup list "+tierGroupSet.toString());

    }



    @Test
    public void test3searchTierGroupByName() throws InspireNetzException {

        // Create the tierGroup
        TierGroup tierGroup = TierGroupFixture.standardTierGroup();
        tierGroupService.saveTierGroup(tierGroup);
        Assert.assertNotNull(tierGroup.getTigName());
        log.info("TierGroup created");

        // Check the tierGroup name
        Page<TierGroup> tierGroups = tierGroupService.searchTierGroups(tierGroup.getTigMerchantNo(),0L,3,"name", "%pre%", constructPageSpecification(0));
        Assert.assertTrue(tierGroups.hasContent());
        Set<TierGroup> tierGroupSet = Sets.newHashSet((Iterable<TierGroup>)tierGroups);
        log.info("tierGroup list "+tierGroupSet.toString());


    }


    @Test
    public void test4IsTierGroupNameExisting() throws InspireNetzException {

        // Create the tierGroup
        TierGroup tierGroup = TierGroupFixture.standardTierGroup();
        tierGroup = tierGroupService.saveTierGroup(tierGroup);
        Assert.assertNotNull(tierGroup.getTigId());
        log.info("TierGroup created");

        // Create a new tierGroup
        TierGroup newTierGroup = TierGroupFixture.standardTierGroup();
        boolean exists = tierGroupService.isTierGroupNameDuplicateExisting(newTierGroup);
        Assert.assertTrue(exists);
        log.info("TierGroup exists");


    }

    @Test
    public void test7SaveTierGroup() throws InspireNetzException {

        // Create the tierGroup
        TierGroup tierGroup = TierGroupFixture.standardTierGroup();
        tierGroup = tierGroupService.saveTierGroup(tierGroup);
        Assert.assertNotNull(tierGroup.getTigId());
        log.info("TierGroup created");


    }


    @Test
    public void test5DeleteTierGroup() throws InspireNetzException {

        // Create the tierGroup
        TierGroup tierGroup = TierGroupFixture.standardTierGroup();
        tierGroup = tierGroupService.saveTierGroup(tierGroup);
        Assert.assertNotNull(tierGroup.getTigId());
        log.info("TierGroup created");

        // call the delete tierGroup
        tierGroupService.deleteTierGroup(tierGroup.getTigId());

        // Try searching for the tierGroup
        TierGroup checkTierGroup  = tierGroupService.findByTigId(tierGroup.getTigId());

        Assert.assertNull(checkTierGroup);

        log.info("tierGroup deleted");

    }



    @Test
    public void test6FindByTigMerchantNo() throws InspireNetzException {

        // Create the tierGroup
        TierGroup tierGroup = TierGroupFixture.standardTierGroup();
        tierGroupService.saveTierGroup(tierGroup);
        Assert.assertNotNull(tierGroup.getTigId());
        log.info("TierGroup created");


        // Get the List of TierGroup
        List<TierGroup> tierGroupList = tierGroupService.findByTigMerchantNo(tierGroup.getTigMerchantNo());
        Assert.assertNotNull(tierGroupList);
        Assert.assertTrue(!tierGroupList.isEmpty());
        log.info("Tier Group List"+tierGroupList.toString());
    }



    @Test
    public void testLazyLoading() throws InspireNetzException {

        // Get the tier
        Tier tier = TierFixture.standardTier();

        // Create the tierGroup
        TierGroup tierGroup = TierGroupFixture.standardTierGroup();


        tierGroup = tierGroupService.saveTierGroup(tierGroup);
        Assert.assertNotNull(tierGroup.getTigId());
        log.info("TierGroup created");


        tier.setTieParentGroup(tierGroup.getTigId());
        tierService.saveTier(tier);

        // Add the tier to tempSet
        tempSet.add(tier);

        // Get the tierGroup
        TierGroup tierGroup1 = tierGroupService.findByTigId(tierGroup.getTigId());
        Set<Tier> tierSet = tierGroup1.getTierSet();
        Assert.assertNotNull(tierSet);
        log.info("Tiers " + tierSet.toString());
    }


    @After
    public void tearDown() throws InspireNetzException {


        for(Tier tier: tempSet) {

            tierService.deleteTier(tier.getTieId());

        }


        Set<TierGroup> tierGroups = TierGroupFixture.standardTierGroups();

        for(TierGroup tierGroup: tierGroups) {

            TierGroup delTierGroup = tierGroupRepository.findByTigMerchantNoAndTigName(tierGroup.getTigMerchantNo(),tierGroup.getTigName());

            if ( delTierGroup != null ) {
                tierGroupRepository.delete(delTierGroup);
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
        return new Sort(Sort.Direction.ASC, "tigName");
    }

}
