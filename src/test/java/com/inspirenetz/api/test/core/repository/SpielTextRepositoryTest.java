package com.inspirenetz.api.test.core.repository;

import com.inspirenetz.api.core.domain.MessageSpiel;
import com.inspirenetz.api.core.domain.SpielText;
import com.inspirenetz.api.core.repository.SpielTextRepository;
import com.inspirenetz.api.core.service.MessageSpielService;
import com.inspirenetz.api.test.config.ApplicationTestConfig;
import com.inspirenetz.api.test.config.PersistenceTestConfig;
import com.inspirenetz.api.test.core.fixture.MessageSpielFixture;
import com.inspirenetz.api.test.core.fixture.SpielTextFixture;
import org.apache.log4j.spi.LoggerFactory;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Before;
import org.junit.Assert;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.HashSet;
import java.util.List;
import java.util.Set;


/**
 * Created by alameen on 3/2/15.
 */
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes  = {ApplicationTestConfig.class, PersistenceTestConfig.class})
public class SpielTextRepositoryTest {

    //  Create the logger
    private static Logger log = org.slf4j.LoggerFactory.getLogger(SpielTextRepositoryTest.class);

    @Autowired
    private SpielTextRepository spielTextRepository;



    Set<SpielText> tempSet=new HashSet<>(0);

    @org.junit.Before
    public void setUp() throws Exception {


    }

    
    @Test
    public void test1Create(){


        SpielText spielText = spielTextRepository.save(SpielTextFixture.standardSpielText());
        log.info(spielText.toString());

        // Add the spielText to tempSet
        tempSet.add(spielText);

        Assert.assertNotNull(spielText.getSptId());

    }

    @Test
    public void test2Update() {

        SpielText spielText = SpielTextFixture.standardSpielText();
        spielText = spielTextRepository.save(spielText);
        log.info("Original SpielText " + spielText.toString());

        // Add the spielText to tempSet
        tempSet.add(spielText);

        SpielText updatedSpielText = SpielTextFixture.updatedStandardSpielText(spielText);
        updatedSpielText = spielTextRepository.save(updatedSpielText);
        log.info("Updated SpielText "+ updatedSpielText.toString());

    }


    @Test
    public void test3FindBySptRef() {

        SpielText spielText = SpielTextFixture.standardSpielText();

        spielText.setSptRef(11L);
        spielText = spielTextRepository.save(spielText);


        // Add the spielText to tempSet
        tempSet.add(spielText);

        // GEt the spielText now
        List searchSpielText = spielTextRepository.findBySptRef(spielText.getSptRef());

        Assert.assertNotNull(searchSpielText);

        log.info("Fetch spielText : " + searchSpielText.toString());

    }


    @Test
    public void findBySptRefAndSptChannelAndSptLocation() {

        // Save the spielText
        SpielText spielText = SpielTextFixture.standardSpielText();

        spielText.setSptRef(2L);

        spielText.setSptDescription("llll");


        spielText = spielTextRepository.save(spielText);

        // Add the spielText to tempSet
        tempSet.add(spielText);

        // Get the SpielText
        SpielText spielText1 = spielTextRepository.findBySptRefAndSptChannelAndSptLocation(spielText.getSptRef(), spielText.getSptChannel(), spielText.getSptLocation());

        Assert.assertNotNull(spielText1.getSptDescription());

        log.info("Fetch spielText : " + spielText1.toString());

    }



    @org.junit.After
    public void tearDown() {



        for(SpielText spielText: tempSet) {

            spielTextRepository.delete(spielText);

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
