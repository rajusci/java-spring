package com.inspirenetz.api.test.core.service;

import com.google.common.collect.Sets;
import com.inspirenetz.api.core.domain.MerchantLocation;
import com.inspirenetz.api.core.domain.Tier;
import com.inspirenetz.api.core.repository.MerchantLocationRepository;
import com.inspirenetz.api.core.service.MerchantLocationService;
import com.inspirenetz.api.rest.exception.InspireNetzException;
import com.inspirenetz.api.test.config.ApplicationTestConfig;
import com.inspirenetz.api.test.config.PersistenceTestConfig;
import com.inspirenetz.api.test.config.ServiceTestConfig;
import com.inspirenetz.api.test.core.fixture.MerchantLocationFixture;
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

import java.util.*;

/**
 * Created by sandheepgr on 30/4/14.
 */
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes  = {ApplicationTestConfig.class, PersistenceTestConfig.class, ServiceTestConfig.class})
public class MerchantLocationServiceTest {


    private static Logger log = LoggerFactory.getLogger(MerchantLocationServiceTest.class);

    @Autowired
    private MerchantLocationService merchantLocationService;

    @Autowired
    private MerchantLocationRepository merchantLocationRepository;

    // Set holding the temporary variable
    Set<MerchantLocation> tempSet = new HashSet<>(0);


    @Before
    public void setUp() {}



    @Test
    public void test3FindByMelMerchantNo() {

        // Get the standard merchantLocation
        MerchantLocation merchantLocation = MerchantLocationFixture.standardMerchantLocation();

        Page<MerchantLocation> merchantLocations = merchantLocationService.findByMelMerchantNo(merchantLocation.getMelMerchantNo(),constructPageSpecification(0));
        log.info("merchantLocations by merchant no " + merchantLocations.toString());
        Set<MerchantLocation> merchantLocationSet = Sets.newHashSet((Iterable<MerchantLocation>)merchantLocations);
        log.info("merchantLocation list "+merchantLocationSet.toString());

    }

    @Test
    public void test4FindByMelMerchantNoAndMelLocation() throws InspireNetzException {

        // Create the merchantLocation
        MerchantLocation merchantLocation = MerchantLocationFixture.standardMerchantLocation();
        merchantLocationService.saveMerchantLocation(merchantLocation);
        Assert.assertNotNull(merchantLocation.getMelId());
        log.info("MerchantLocation created");

        MerchantLocation fetchMerchantLocation = merchantLocationService.findByMelMerchantNoAndMelLocation(merchantLocation.getMelMerchantNo(),merchantLocation.getMelLocation());
        Assert.assertNotNull(fetchMerchantLocation);
        log.info("Fetched merchantLocation info" + merchantLocation.toString());

    }


    @Test
    public void test5FindByMelMerchantNoAndMelLocationLike() throws InspireNetzException {

        // Create the merchantLocation
        MerchantLocation merchantLocation = MerchantLocationFixture.standardMerchantLocation();
        merchantLocationService.saveMerchantLocation(merchantLocation);
        Assert.assertNotNull(merchantLocation.getMelId());
        log.info("MerchantLocation created");

        // Check the merchantLocation name
        Page<MerchantLocation> merchantLocations = merchantLocationService.findByMelMerchantNoAndMelLocationLike(merchantLocation.getMelMerchantNo(),"%gu%",constructPageSpecification(0));
        Assert.assertTrue(merchantLocations.hasContent());
        Set<MerchantLocation> merchantLocationSet = Sets.newHashSet((Iterable<MerchantLocation>)merchantLocations);
        log.info("merchantLocation list "+merchantLocationSet.toString());


    }


    @Test
    public void test6IsMerchantLocationExisting() throws InspireNetzException {

        // Create the merchantLocation
        MerchantLocation merchantLocation = MerchantLocationFixture.standardMerchantLocation();
        merchantLocation = merchantLocationService.saveMerchantLocation(merchantLocation);
        Assert.assertNotNull(merchantLocation.getMelId());
        log.info("MerchantLocation created");

        // Create a new merchantLocation
        MerchantLocation newMerchantLocation = MerchantLocationFixture.standardMerchantLocation();
        boolean exists = merchantLocationService.isMerchantLocationDuplicateExisting(newMerchantLocation);
        Assert.assertTrue(exists);
        log.info("MerchantLocation exists");


    }


    @Test
    public void test5DeleteMerchantLocation() throws InspireNetzException {

        // Create the merchantLocation
        MerchantLocation merchantLocation = MerchantLocationFixture.standardMerchantLocation();
        merchantLocation = merchantLocationService.saveMerchantLocation(merchantLocation);
        Assert.assertNotNull(merchantLocation.getMelId());
        log.info("MerchantLocation created");

        // call the delete merchantLocation
        merchantLocationService.deleteMerchantLocation(merchantLocation.getMelId());

        // Try searching for the merchantLocation
        MerchantLocation checkMerchantLocation  = merchantLocationService.findByMelMerchantNoAndMelLocation(merchantLocation.getMelMerchantNo(),merchantLocation.getMelLocation());

        Assert.assertNull(checkMerchantLocation);

        log.info("merchantLocation deleted");

    }



    @Test
    public void test6GetMerchantLocationAsMap() throws InspireNetzException {

        // Create the merchantLocation
        MerchantLocation merchantLocation = MerchantLocationFixture.standardMerchantLocation();
        merchantLocationService.saveMerchantLocation(merchantLocation);
        Assert.assertNotNull(merchantLocation.getMelId());
        log.info("MerchantLocation created");


        // Get the Map
        HashMap<Long,MerchantLocation> merchantLocationHashMap = merchantLocationService.getMerchantLocationAsMap(merchantLocation.getMelMerchantNo());
        Assert.assertNotNull(merchantLocationHashMap);;
        log.info("Location Map" + merchantLocationHashMap.toString());

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
