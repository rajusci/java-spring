package com.inspirenetz.api.test.core.repository;

import com.inspirenetz.api.core.domain.Redemption;
import com.inspirenetz.api.core.domain.RedemptionMerchant;
import com.inspirenetz.api.core.domain.RedemptionMerchantLocation;
import com.inspirenetz.api.core.repository.RedemptionMerchantRepository;
import com.inspirenetz.api.test.config.ApplicationTestConfig;
import com.inspirenetz.api.test.config.PersistenceTestConfig;
import com.inspirenetz.api.test.core.fixture.RedemptionMerchantFixture;
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
public class RedemptionMerchantRepositoryTest {

    private static Logger log = LoggerFactory.getLogger(RedemptionMerchantRepositoryTest.class);

    @Autowired
    private RedemptionMerchantRepository redemptionMerchantRepository;

    Set<RedemptionMerchant> tempSet = new HashSet<>(0);

    @Before
    public void setup() {}

    @Test
    public void test1Create(){

        RedemptionMerchantFixture redemptionMerchantFixture=new RedemptionMerchantFixture();

        RedemptionMerchant redemptionMerchant = redemptionMerchantRepository.save(redemptionMerchantFixture.standardRedemptionMerchant());

        // Add to the tempSet
        tempSet.add(redemptionMerchant);

        log.info(redemptionMerchant.toString());
        Assert.assertNotNull(redemptionMerchant.getRemNo());

    }


    @Test
    public void test2FindByMerchantName(){

        RedemptionMerchant redemptionMerchant=RedemptionMerchantFixture.standardRedemptionMerchant();

        redemptionMerchant.setRemName("AJITH1QS");

        RedemptionMerchant redemptionMerchant1 = redemptionMerchantRepository.save(redemptionMerchant);

        RedemptionMerchant redemptionMerchant2 =redemptionMerchantRepository.findByRemName(redemptionMerchant1.getRemName());

        // Add to the tempSet
        tempSet.add(redemptionMerchant);

        log.info(redemptionMerchant.toString());
        Assert.assertNotNull(redemptionMerchant.getRemNo());

    }


    @Test
    public void test2Update() {

        RedemptionMerchantFixture redemptionMerchantFixture=new RedemptionMerchantFixture();

        RedemptionMerchant redemptionMerchant = redemptionMerchantFixture.standardRedemptionMerchant();
        redemptionMerchant = redemptionMerchantRepository.save(redemptionMerchant);
        log.info("Original RedemptionMerchant " + redemptionMerchant.toString());

        // Add to the tempSet
        tempSet.add(redemptionMerchant);

        long rmlId = 0L;
        Set<RedemptionMerchantLocation> redemptionLocationSet = redemptionMerchant.getRedemptionMerchantLocations();
        for(RedemptionMerchantLocation redemptionMerchantLocation: redemptionLocationSet){

            rmlId = redemptionMerchantLocation.getRmlId();

        }
        RedemptionMerchantLocation redemptionMerchantLocation=new RedemptionMerchantLocation();
        redemptionMerchantLocation.setRmlMerNo(redemptionMerchant.getRemNo());
        redemptionMerchantLocation.setRmlId(rmlId);
        redemptionMerchantLocation.setRmlLocation("Bangalore");

        RedemptionMerchantLocation redemptionMerchantLocation1=new RedemptionMerchantLocation();
        redemptionMerchantLocation1.setRmlLocation("Kochi");


        Set<RedemptionMerchantLocation> redemptionMerchantLocations =new HashSet<>();
        redemptionMerchantLocations.add(redemptionMerchantLocation);

        redemptionMerchantLocations.add(redemptionMerchantLocation1);
        redemptionMerchant.setRedemptionMerchantLocations(redemptionMerchantLocations);

        RedemptionMerchant updatedRedemptionMerchant = new RedemptionMerchant();
        updatedRedemptionMerchant = redemptionMerchantRepository.save(redemptionMerchant);
        log.info("Updated RedemptionMerchant "+ updatedRedemptionMerchant.toString());

    }

    @Test
    public void test3FindByRemNo() {

        RedemptionMerchantFixture redemptionMerchantFixture=new RedemptionMerchantFixture();

        RedemptionMerchant redemptionMerchant = redemptionMerchantFixture.standardRedemptionMerchant();
        redemptionMerchant = redemptionMerchantRepository.save(redemptionMerchant);
        log.info("Original RedemptionMerchant " + redemptionMerchant.toString());


        // Add to the tempSet
        tempSet.add(redemptionMerchant);

        // Get the data
        RedemptionMerchant searchredemptionMerchant = redemptionMerchantRepository.findByRemNo(redemptionMerchant.getRemNo());
        Assert.assertNotNull(searchredemptionMerchant);
        Assert.assertTrue(redemptionMerchant.getRemNo().longValue() ==  searchredemptionMerchant.getRemNo().longValue());;
        log.info("Searched RedemptionMerchant : " + searchredemptionMerchant.toString());


    }

    @After
    public void tearDown() {

        for(RedemptionMerchant redemptionMerchant : tempSet ) {

            redemptionMerchantRepository.delete(redemptionMerchant);

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
        return new Sort(Sort.Direction.ASC, "remName");
    }

}
