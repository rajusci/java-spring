package com.inspirenetz.api.test.core.repository;

import com.google.common.collect.Sets;
import com.inspirenetz.api.core.domain.MerchantLocation;
import com.inspirenetz.api.core.repository.MerchantLocationRepository;
import com.inspirenetz.api.test.config.ApplicationTestConfig;
import com.inspirenetz.api.test.config.PersistenceTestConfig;
import com.inspirenetz.api.test.core.fixture.MerchantLocationFixture;
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

import java.util.List;
import java.util.Set;

/**
 * Created by sandheepgr on 17/2/14.
 */
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes  = {ApplicationTestConfig.class, PersistenceTestConfig.class})
public class MerchantLocationRepositoryTest {

    private static Logger log = LoggerFactory.getLogger(MerchantLocationRepositoryTest.class);

    @Autowired
    private MerchantLocationRepository merchantLocationRepository;


    @Before
    public void setup() {}

    @Test
    public void test1Create(){


        MerchantLocation merchantLocation = merchantLocationRepository.save(MerchantLocationFixture.standardMerchantLocation());
        log.info(merchantLocation.toString());
        Assert.assertNotNull(merchantLocation.getMelId());

    }

    @Test
    public void test2Update() {

        MerchantLocation merchantLocation = MerchantLocationFixture.standardMerchantLocation();
        merchantLocation = merchantLocationRepository.save(merchantLocation);
        log.info("Original MerchantLocation " + merchantLocation.toString());

        MerchantLocation updatedMerchantLocation = MerchantLocationFixture.updatedStandardMerchantLocation(merchantLocation);
        updatedMerchantLocation = merchantLocationRepository.save(updatedMerchantLocation);
        log.info("Updated MerchantLocation "+ updatedMerchantLocation.toString());

    }



    @Test
    public void test3FindByMelMerchantNo() {

        // Get the standard merchantLocation
        MerchantLocation merchantLocation = MerchantLocationFixture.standardMerchantLocation();

        Page<MerchantLocation> merchantLocations = merchantLocationRepository.findByMelMerchantNo(merchantLocation.getMelMerchantNo(),constructPageSpecification(0));
        log.info("merchantLocations by merchant no " + merchantLocations.toString());
        Set<MerchantLocation> merchantLocationSet = Sets.newHashSet((Iterable<MerchantLocation>)merchantLocations);
        log.info("merchantLocation list "+merchantLocationSet.toString());

    }

    @Test
    public void test4FindByMelMerchantNoAndMelLocation() {

        // Create the merchantLocation
        MerchantLocation merchantLocation = MerchantLocationFixture.standardMerchantLocation();
        merchantLocationRepository.save(merchantLocation);
        Assert.assertNotNull(merchantLocation.getMelId());
        log.info("MerchantLocation created");

        MerchantLocation fetchMerchantLocation = merchantLocationRepository.findByMelMerchantNoAndMelLocation(merchantLocation.getMelMerchantNo(),merchantLocation.getMelLocation());
        Assert.assertNotNull(fetchMerchantLocation);
        log.info("Fetched merchantLocation info" + merchantLocation.toString());

    }


    @Test
    public void test5FindByMelMerchantNoAndMelLocationLike() {

        // Create the merchantLocation
        MerchantLocation merchantLocation = MerchantLocationFixture.standardMerchantLocation();
        merchantLocationRepository.save(merchantLocation);
        Assert.assertNotNull(merchantLocation.getMelId());
        log.info("MerchantLocation created");

        // Check the merchantLocation name
        Page<MerchantLocation> merchantLocations = merchantLocationRepository.findByMelMerchantNoAndMelLocationLike(merchantLocation.getMelMerchantNo(),"%gu%",constructPageSpecification(0));
        Assert.assertTrue(merchantLocations.hasContent());
        Set<MerchantLocation> merchantLocationSet = Sets.newHashSet((Iterable<MerchantLocation>)merchantLocations);
        log.info("merchantLocation list "+merchantLocationSet.toString());


    }


    @Test
    public void test6FindByMelMerchantNo() {

        // Get the standard merchantLocation
        MerchantLocation merchantLocation = MerchantLocationFixture.standardMerchantLocation();

        List<MerchantLocation> merchantLocationList = merchantLocationRepository.findByMelMerchantNo(merchantLocation.getMelMerchantNo());
        Assert.assertNotNull(merchantLocationList);;
        Assert.assertTrue(!merchantLocationList.isEmpty());
        log.info("merchantLocation list "+merchantLocationList.toString());

    }

    @After
    public void tearDown() {

        Set<MerchantLocation> merchantLocations = MerchantLocationFixture.standardMerchantLocations();

        for(MerchantLocation merchantLocation: merchantLocations) {

            MerchantLocation delMerchantLocation = merchantLocationRepository.findByMelMerchantNoAndMelLocation(merchantLocation.getMelMerchantNo(),merchantLocation.getMelLocation());

            if ( delMerchantLocation != null ) {
                merchantLocationRepository.delete(delMerchantLocation);
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
        return new Sort(Sort.Direction.ASC, "melLocation");
    }


}
