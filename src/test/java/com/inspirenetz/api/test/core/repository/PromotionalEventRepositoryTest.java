package com.inspirenetz.api.test.core.repository;

import com.inspirenetz.api.core.domain.PromotionalEvent;
import com.inspirenetz.api.core.repository.PromotionalEventRepository;
import com.inspirenetz.api.test.config.ApplicationTestConfig;
import com.inspirenetz.api.test.config.PersistenceTestConfig;
import com.inspirenetz.api.test.core.fixture.PromotionalEventFixture;
import com.inspirenetz.api.util.DBUtils;
import org.junit.Assert;
import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Test;
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
 * Created by saneeshci on 29/9/14.
 */
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes  = {ApplicationTestConfig.class, PersistenceTestConfig.class})
public class PromotionalEventRepositoryTest {

    private static Logger log = LoggerFactory.getLogger(PromotionalEventRepositoryTest.class);

    Set<PromotionalEvent> tempSet = new HashSet<>(0);

    @Autowired
    private PromotionalEventRepository promotionalEventRepository;

    @Before
    public void setup() {}

    @Test
    public void test1Create(){


        PromotionalEvent promotionalEvent = promotionalEventRepository.save(PromotionalEventFixture.standardPromotionalEvent());
        log.info(promotionalEvent.toString());
        Assert.assertNotNull(promotionalEvent.getPreId());

        // Add to tempSet
        tempSet.add(promotionalEvent);

    }

    @Test
    public void test2Update() {

        PromotionalEvent promotionalEvent = PromotionalEventFixture.standardPromotionalEvent();
        promotionalEvent = promotionalEventRepository.save(promotionalEvent);
        log.info("Original role access right " + promotionalEvent.toString());

        // Add to tempSet
        tempSet.add(promotionalEvent);

        PromotionalEvent updatePromotionalEvent = PromotionalEventFixture.updatedStandPromotionalEvent(promotionalEvent);
        updatePromotionalEvent = promotionalEventRepository.save(updatePromotionalEvent);
        log.info("Updated PromotionalEvent "+ updatePromotionalEvent.toString());

    }

    @Test
    public void test3findByPreId() {

        // Get the standard
        PromotionalEvent promotionalEvent = PromotionalEventFixture.standardPromotionalEvent();
        promotionalEvent = promotionalEventRepository.save(promotionalEvent);
        log.info("Original Security Settings  " + promotionalEvent.toString());

        // Add to tempSet
        tempSet.add(promotionalEvent);

        Assert.assertNotNull(promotionalEvent.getPreId());

        PromotionalEvent promotionalEventById = promotionalEventRepository.findByPreId(promotionalEvent.getPreId());
        Assert.assertNotNull(promotionalEventById);
        log.info("Fetched PromotionalEventById info" + promotionalEventById.toString());


    }

    @Test
    public void test5findByPreMerchantNo() {

        // Get the standard
        PromotionalEvent promotionalEvent = PromotionalEventFixture.standardPromotionalEvent();
        promotionalEvent = promotionalEventRepository.save(promotionalEvent);
        log.info("Original Security Settings  " + promotionalEvent.toString());

        // Add to tempSet
        tempSet.add(promotionalEvent);

        Assert.assertNotNull(promotionalEvent.getPreId());

        Page<PromotionalEvent> promotionalEventById = promotionalEventRepository.findByPreMerchantNo(promotionalEvent.getPreMerchantNo(),constructPageSpecification(0));
        Assert.assertNotNull(promotionalEventById);
        log.info("Fetched PromotionalEventById info" + promotionalEventById.toString());


    }
    @Test
    public void test4findByPreMerchantNoAndPreEventNameLike() {

        // Get the standard
        PromotionalEvent promotionalEvent = PromotionalEventFixture.standardPromotionalEvent();
        promotionalEvent = promotionalEventRepository.save(promotionalEvent);
        log.info("Original Security Settings  " + promotionalEvent.toString());

        // Add to tempSet
        tempSet.add(promotionalEvent);

        Assert.assertNotNull(promotionalEvent.getPreId());

        Page<PromotionalEvent> promotionalEventById = promotionalEventRepository.findByPreMerchantNoAndPreEventNameLike(promotionalEvent.getPreMerchantNo(), "%EVE%",constructPageSpecification(0));
        Assert.assertNotNull(promotionalEventById);
        log.info("Fetched PromotionalEventById info" + promotionalEventById.toString());


    }

    @Test
    public void test7GetValidEvents() {

        Set<PromotionalEvent> promotionalEvents = PromotionalEventFixture.standardPromotionalEvents();

        for(PromotionalEvent promotionalEvent  : promotionalEvents){

            promotionalEvent = promotionalEventRepository.save(promotionalEvent);

        }

        //Add to tempset
        tempSet.addAll(promotionalEvents);

        Page<PromotionalEvent> promotionalEventById = promotionalEventRepository.getValidEventsByMerchantNoAndDate(1L, DBUtils.covertToSqlDate("2014-09-30"),constructPageSpecification(0));
        Assert.assertNotNull(promotionalEventById);
        log.info("Fetched PromotionalEventById info" + promotionalEventById.toString());


    }

    @org.junit.After
    public void tearDown() {

        for ( PromotionalEvent promotionalEvent : tempSet ) {

            promotionalEventRepository.delete(promotionalEvent);

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
        return new Sort(Sort.Direction.ASC, "preEventName");
    }

}
