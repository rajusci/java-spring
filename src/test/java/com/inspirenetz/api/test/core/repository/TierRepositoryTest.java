package com.inspirenetz.api.test.core.repository;

import com.google.common.collect.Sets;
import com.inspirenetz.api.core.domain.Tier;
import com.inspirenetz.api.core.repository.TierRepository;
import com.inspirenetz.api.test.config.ApplicationTestConfig;
import com.inspirenetz.api.test.config.PersistenceTestConfig;
import com.inspirenetz.api.test.core.fixture.TierFixture;
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
 * Created by sandheepgr on 17/2/14.
 */
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes  = {ApplicationTestConfig.class, PersistenceTestConfig.class})
public class TierRepositoryTest {

    private static Logger log = LoggerFactory.getLogger(TierRepositoryTest.class);

    @Autowired
    private TierRepository tierRepository;

    // Set holding the temporary variable
    Set<Tier> tempSet = new HashSet<>(0);


    @Before
    public void setup() {}

    @Test
    public void test1Create(){


        Tier tier = tierRepository.save(TierFixture.standardTier());
        log.info(tier.toString());

        // Add the tier to tempSet
        tempSet.add(tier);

        Assert.assertNotNull(tier.getTieId());

    }

    @Test
    public void test2Update() {

        Tier tier = TierFixture.standardTier();
        tier = tierRepository.save(tier);
        log.info("Original Tier " + tier.toString());

        // Add the tier to tempSet
        tempSet.add(tier);

        Tier updatedTier = TierFixture.updatedStandardTier(tier);
        updatedTier = tierRepository.save(updatedTier);
        log.info("Updated Tier "+ updatedTier.toString());

    }


    @Test
    public void test3FindByTieId() {

        // Save the tier
        Tier tier = TierFixture.standardTier();
        tier = tierRepository.save(tier);

        // Add the tier to tempSet
        tempSet.add(tier);

        // GEt the tier now
        Tier searchTier = tierRepository.findByTieId(tier.getTieId());
        Assert.assertNotNull(searchTier);
        Assert.assertTrue(searchTier.getTieId() == tier.getTieId());
        log.info("Fetch tier : " + searchTier.toString());

    }


    @Test
    public void test4FindTyTierParentGroupAndTieName() {

        // Save the tier
        Tier tier = TierFixture.standardTier();
        tier = tierRepository.save(tier);

        // Add the tier to tempSet
        tempSet.add(tier);

        // Get the Tier
        Tier searchTier = tierRepository.findByTieParentGroupAndTieName(tier.getTieParentGroup(),tier.getTieName());
        Assert.assertNotNull(searchTier);
        Assert.assertTrue(searchTier.getTieId() == tier.getTieId());
        log.info("Fetch tier : " + searchTier.toString());

    }



    @Test
    public void test5FindByTieParentGroup() {

        // Save the tier
        Tier tier = TierFixture.standardTier();
        tier = tierRepository.save(tier);

        // Add the tier to tempSet
        tempSet.add(tier);

        // Get the Tier
        List<Tier> tierList = tierRepository.findByTieParentGroup(tier.getTieParentGroup());
        log.info("Fetch tier : " + tierList.toString());

    }



    @After
    public void tearDown() {



        for(Tier tier: tempSet) {

           tierRepository.delete(tier);

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
        return new Sort(Sort.Direction.ASC, "tieName");
    }


}
