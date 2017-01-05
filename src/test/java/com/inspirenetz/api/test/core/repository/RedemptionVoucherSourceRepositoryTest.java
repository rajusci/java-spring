package com.inspirenetz.api.test.core.repository;

import com.inspirenetz.api.core.domain.RedemptionVoucherSource;
import com.inspirenetz.api.core.repository.RedemptionVoucherSourceRepository;
import com.inspirenetz.api.test.config.ApplicationTestConfig;
import com.inspirenetz.api.test.config.PersistenceTestConfig;
import com.inspirenetz.api.test.core.fixture.RedemptionVoucherSourceFixture;
import org.junit.*;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.HashSet;
import java.util.Set;

/**
 * Created by sandheepgr on 17/2/14.
 */
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes  = {ApplicationTestConfig.class, PersistenceTestConfig.class})
public class RedemptionVoucherSourceRepositoryTest {

    private static Logger log = LoggerFactory.getLogger(RedemptionVoucherSourceRepositoryTest.class);

    @Autowired
    private RedemptionVoucherSourceRepository redemptionVoucherSourceRepository;

    Set<RedemptionVoucherSource> tempSet = new HashSet<>(0);

    @Before
    public void setup() {}

    @Test
    public void test1Create(){

        RedemptionVoucherSourceFixture redemptionVoucherSourceFixture=new RedemptionVoucherSourceFixture();

        RedemptionVoucherSource redemptionVoucherSource = redemptionVoucherSourceRepository.save(redemptionVoucherSourceFixture.standardRedemptionVoucherSource());

        // Add to the tempSet
        tempSet.add(redemptionVoucherSource);

        log.info(redemptionVoucherSource.toString());
        Assert.assertNotNull(redemptionVoucherSource.getRvsId());

    }

    @Test
    public void test2Update() {

        RedemptionVoucherSourceFixture redemptionVoucherSourceFixture=new RedemptionVoucherSourceFixture();

        RedemptionVoucherSource redemptionVoucherSource = redemptionVoucherSourceFixture.standardRedemptionVoucherSource();
        redemptionVoucherSource = redemptionVoucherSourceRepository.save(redemptionVoucherSource);
        log.info("Original RedemptionVoucherSource " + redemptionVoucherSource.toString());

        // Add to the tempSet
        tempSet.add(redemptionVoucherSource);


        RedemptionVoucherSource updatedRedemptionVoucherSource = RedemptionVoucherSourceFixture.updatedStandardRedemptionVoucherSource(redemptionVoucherSource);
        updatedRedemptionVoucherSource = redemptionVoucherSourceRepository.save(updatedRedemptionVoucherSource);
        log.info("Updated RedemptionVoucherSource "+ updatedRedemptionVoucherSource.toString());

    }

    @Test
    public void test3FindByRvsId() {

        RedemptionVoucherSourceFixture redemptionVoucherSourceFixture=new RedemptionVoucherSourceFixture();

        RedemptionVoucherSource redemptionVoucherSource = redemptionVoucherSourceFixture.standardRedemptionVoucherSource();
        redemptionVoucherSource = redemptionVoucherSourceRepository.save(redemptionVoucherSource);
        log.info("Original RedemptionVoucherSource " + redemptionVoucherSource.toString());


        // Add to the tempSet
        tempSet.add(redemptionVoucherSource);

        // Get the data
        RedemptionVoucherSource searchredemptionVoucherSource = redemptionVoucherSourceRepository.findByRvsId(redemptionVoucherSource.getRvsId());
        Assert.assertNotNull(searchredemptionVoucherSource);
        Assert.assertTrue(redemptionVoucherSource.getRvsId().longValue() ==  searchredemptionVoucherSource.getRvsId().longValue());;
        log.info("Searched RedemptionVoucherSource : " + searchredemptionVoucherSource.toString());


    }

    @Test
    public void test4FindByRvsMerchantNoAndRvsName(){

        RedemptionVoucherSourceFixture redemptionVoucherSourceFixture=new RedemptionVoucherSourceFixture();

        RedemptionVoucherSource redemptionVoucherSource = redemptionVoucherSourceFixture.standardRedemptionVoucherSource();
        redemptionVoucherSource = redemptionVoucherSourceRepository.save(redemptionVoucherSource);
        log.info("Original RedemptionVoucherSource " + redemptionVoucherSource.toString());


        // Add to the tempSet
        tempSet.add(redemptionVoucherSource);

        // Get the data
        Page<RedemptionVoucherSource> searchRedemptionVoucherSource = redemptionVoucherSourceRepository.findByRvsMerchantNoAndRvsNameLike(redemptionVoucherSource.getRvsMerchantNo(), redemptionVoucherSource.getRvsName(),constructPageSpecification(0));
        Assert.assertTrue(searchRedemptionVoucherSource.hasContent());
        log.info("Searched RedemptionVoucherSource : " + searchRedemptionVoucherSource.toString());

    }




    @After
    public void tearDown() {

        for(RedemptionVoucherSource redemptionVoucherSource : tempSet ) {

            redemptionVoucherSourceRepository.delete(redemptionVoucherSource);

        }

    }

    /**
     * Returns a new object which specifies the the wanted result page.
     * @param pageIndex The index of the wanted result page
     * @return
     */
    private Pageable constructPageSpecification(int pageIndex) {
        Pageable pageSpecification = new PageRequest(pageIndex, 10);
        return pageSpecification;
    }



}
