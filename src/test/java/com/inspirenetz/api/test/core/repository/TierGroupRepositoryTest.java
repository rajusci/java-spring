package com.inspirenetz.api.test.core.repository;

import com.google.common.collect.Sets;
import com.inspirenetz.api.core.domain.TierGroup;
import com.inspirenetz.api.core.repository.TierGroupRepository;
import com.inspirenetz.api.test.config.ApplicationTestConfig;
import com.inspirenetz.api.test.config.PersistenceTestConfig;
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
 * Created by saneeshci on 20/8/14.
 */
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes  = {ApplicationTestConfig.class, PersistenceTestConfig.class})
public class TierGroupRepositoryTest {

    private static Logger log = LoggerFactory.getLogger(TierGroupRepositoryTest.class);

    @Autowired
    private TierGroupRepository tierGroupRepository;

    private Set< TierGroup> tierGroupsSet=new HashSet<TierGroup>();

    @Before
    public void setup() {}

    @Test
    public void test1Create(){

        log.info(TierGroupFixture.standardTierGroup().toString());
        TierGroup tierGroup = tierGroupRepository.save(TierGroupFixture.standardTierGroup());
        log.info(tierGroup.toString());
        Assert.assertNotNull(tierGroup.getTigId());
        tierGroupsSet.add(tierGroup);

    }

    @Test
    public void test2Update() {

        TierGroup tierGroup = TierGroupFixture.standardTierGroup();
        tierGroup = tierGroupRepository.save(tierGroup);
        log.info("Original TierGroup " + tierGroup.toString());

        TierGroup updatedTierGroup = TierGroupFixture.updatedStandardTierGroup(tierGroup);
        updatedTierGroup = tierGroupRepository.save(updatedTierGroup);
        log.info("Updated TierGroup "+ updatedTierGroup.toString());
        tierGroupsSet.add(tierGroup);
    }



    @Test
    public void test3SearchTierGroups() {

        // Get the standard tierGroup
        TierGroup tierGroup = TierGroupFixture.standardTierGroup();

        Page<TierGroup> tierGroups = tierGroupRepository.searchTierGroups(tierGroup.getTigMerchantNo(),tierGroup.getTigLocation(),constructPageSpecification(1));
        log.info("tierGroups by merchant no and location" + tierGroups.toString());
        Set<TierGroup> tierGroupSet = Sets.newHashSet((Iterable<TierGroup>)tierGroups);
        log.info("tierGroup list "+tierGroupSet.toString());

    }

    @Test
    public void test4searchTierGroupByTigName() {

        // Create the tierGroup
        TierGroup tierGroup = TierGroupFixture.standardTierGroup();
        tierGroupRepository.save(tierGroup);
        Assert.assertNotNull(tierGroup.getTigId());
        log.info("TierGroup created");
        tierGroupsSet.add(tierGroup);

        TierGroup fetchTierGroup = tierGroupRepository.findByTigMerchantNoAndTigName(tierGroup.getTigMerchantNo(),tierGroup.getTigName());
        Assert.assertNotNull(fetchTierGroup);
        log.info("Fetched tierGroup info" + tierGroup.toString());

    }


    @Test
    public void test5FindByTigMerchantNoAndTigNameLike() {

        // Create the tierGroup
        TierGroup tierGroup = TierGroupFixture.standardTierGroup();
        tierGroupRepository.save(tierGroup);
        Assert.assertNotNull(tierGroup.getTigId());
        log.info("TierGroup created");
        tierGroupsSet.add(tierGroup);

        // Check the tierGroup name
        Page<TierGroup> tierGroups = tierGroupRepository.searchTierGroupsByName(tierGroup.getTigMerchantNo(),tierGroup.getTigLocation(),"%PRE%",constructPageSpecification(0));
        Assert.assertTrue(tierGroups.hasContent());
        Set<TierGroup> tierGroupSet = Sets.newHashSet((Iterable<TierGroup>)tierGroups);
        log.info("tierGroup list "+tierGroupSet.toString());


    }

    @Test
    public void test6FindByTigMerchantNo() {

        // Create the tierGroup
        TierGroup tierGroup = TierGroupFixture.standardTierGroup();
        tierGroupRepository.save(tierGroup);
        Assert.assertNotNull(tierGroup.getTigId());
        log.info("TierGroup created");
        tierGroupsSet.add(tierGroup);

        // Get the List of TierGroup
        List<TierGroup> tierGroupList = tierGroupRepository.findByTigMerchantNo(tierGroup.getTigMerchantNo());
        Assert.assertNotNull(tierGroupList);
        Assert.assertTrue(!tierGroupList.isEmpty());
        log.info("Tier Group List"+tierGroupList.toString());
    }

    @After
    public void tearDown() {

        Set<TierGroup> tierGroups = TierGroupFixture.standardTierGroups();

        for(TierGroup tierGroup: tierGroups) {

            TierGroup delTierGroup = tierGroupRepository.findByTigMerchantNoAndTigName(tierGroup.getTigMerchantNo(), tierGroup.getTigName());

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
