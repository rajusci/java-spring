package com.inspirenetz.api.test.core.repository;

import com.google.common.collect.Sets;
import com.inspirenetz.api.core.domain.PrimaryLoyalty;
import com.inspirenetz.api.core.repository.PrimaryLoyaltyRepository;
import com.inspirenetz.api.test.config.ApplicationTestConfig;
import com.inspirenetz.api.test.config.PersistenceTestConfig;
import com.inspirenetz.api.test.core.fixture.PrimaryLoyaltyFixture;
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

import java.util.Set;

/**
 * Created by saneeshci on 27/8/2014.
 */
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes  = {ApplicationTestConfig.class, PersistenceTestConfig.class})
public class PrimaryLoyaltyRepositoryTest {

    private static Logger log = LoggerFactory.getLogger(PrimaryLoyaltyRepositoryTest.class);

    @Autowired
    private PrimaryLoyaltyRepository primaryLoyaltyRepository;


    @Before
    public void setup() {}

    @Test
    public void test1Create(){


        PrimaryLoyalty primaryLoyalty = primaryLoyaltyRepository.save(PrimaryLoyaltyFixture.standardPrimaryLoyalty());
        log.info(primaryLoyalty.toString());
        Assert.assertNotNull(primaryLoyalty.getPllId());

    }

    @Test
    public void test2Update() {

        PrimaryLoyalty primaryLoyalty = PrimaryLoyaltyFixture.standardPrimaryLoyalty();
        primaryLoyalty = primaryLoyaltyRepository.save(primaryLoyalty);
        log.info("Original PrimaryLoyalty " + primaryLoyalty.toString());

        PrimaryLoyalty updatedPrimaryLoyalty = PrimaryLoyaltyFixture.updatedStandardPrimaryLoyalty(primaryLoyalty);
        updatedPrimaryLoyalty = primaryLoyaltyRepository.save(updatedPrimaryLoyalty);
        log.info("Updated PrimaryLoyalty "+ updatedPrimaryLoyalty.toString());

    }



    @Test
    public void test3FindByPllCustomerNo() {

        // Get the standard primaryLoyalty
        PrimaryLoyalty primaryLoyalty = primaryLoyaltyRepository.save(PrimaryLoyaltyFixture.standardPrimaryLoyalty());
        PrimaryLoyalty primaryLoyaltyObj = primaryLoyaltyRepository.findByPllCustomerNo(primaryLoyalty.getPllCustomerNo());
        log.info("primaryLoyalty by customer no " + primaryLoyaltyObj.toString());

    }

    @Test
    public void test3FindByPllLoyaltyId() {

        // Get the standard primaryLoyalty
        PrimaryLoyalty primaryLoyalty = primaryLoyaltyRepository.save(PrimaryLoyaltyFixture.standardPrimaryLoyalty());

        PrimaryLoyalty primaryLoyaltyObj = primaryLoyaltyRepository.findByPllLoyaltyId(primaryLoyalty.getPllLoyaltyId());
        log.info("primaryLoyalty by customer no " + primaryLoyaltyObj.toString());

    }


    @After
    public void tearDown() {

       PrimaryLoyalty primaryLoyaltys = PrimaryLoyaltyFixture.standardPrimaryLoyalty();



            PrimaryLoyalty delPrimaryLoyalty = primaryLoyaltyRepository.findByPllCustomerNo(primaryLoyaltys.getPllCustomerNo());

            if ( delPrimaryLoyalty != null ) {
                primaryLoyaltyRepository.delete(delPrimaryLoyalty);
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
        return new Sort(Sort.Direction.ASC, "pllCustomerNo");
    }


}
