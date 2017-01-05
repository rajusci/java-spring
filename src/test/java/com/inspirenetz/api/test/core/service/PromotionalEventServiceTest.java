package com.inspirenetz.api.test.core.service;

import com.inspirenetz.api.core.domain.PromotionalEvent;
import com.inspirenetz.api.core.domain.PromotionalEvent;
import com.inspirenetz.api.core.service.PromotionalEventService;
import com.inspirenetz.api.rest.exception.InspireNetzException;
import com.inspirenetz.api.test.config.*;
import com.inspirenetz.api.test.core.fixture.PromotionalEventFixture;
import com.inspirenetz.api.test.core.fixture.PromotionalEventFixture;
import com.inspirenetz.api.test.util.ControllerTestUtils;
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
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.HashSet;
import java.util.Set;

/**
 * Created by ameenci on 10/9/14.
 */
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes  = {ApplicationTestConfig.class, PersistenceTestConfig.class, ServiceTestConfig.class, SecurityTestConfig.class, NotificationTestConfig.class})
public class PromotionalEventServiceTest {

    private static Logger log = LoggerFactory.getLogger(PromotionalEventServiceTest.class);

    Set<PromotionalEvent> tempSet = new HashSet<>(0);

    @Autowired
    private PromotionalEventService promotionalEventService;

    UsernamePasswordAuthenticationToken principal;
    @Autowired
    @Qualifier("uds")
    protected UserDetailsService userDetailsService;

    @Before
    public void setup() {

        // Set the principal
        principal = ControllerTestUtils.getPrincipal(ControllerTestUtils.TEST_MERCHANT_ADMIN_LOGINID, userDetailsService);

        // Set the context
        SecurityContextHolder.getContext().setAuthentication(principal);
    }

    @Test
    public void test1Create(){


        PromotionalEvent promotionalEvent = promotionalEventService.savePromotionalEvent(PromotionalEventFixture.standardPromotionalEvent());
        log.info(promotionalEvent.toString());
        Assert.assertNotNull(promotionalEvent.getPreId());

        // Add to tempSet
        tempSet.add(promotionalEvent);

    }

    @Test
    public void test2Update() throws InspireNetzException {

        PromotionalEvent promotionalEvent = PromotionalEventFixture.standardPromotionalEvent();
        promotionalEvent = promotionalEventService.savePromotionalEvent(promotionalEvent);
        log.info("Original role access right " + promotionalEvent.toString());

        // Add to tempSet
        tempSet.add(promotionalEvent);

        PromotionalEvent updatePromotionalEvent = PromotionalEventFixture.updatedStandPromotionalEvent(promotionalEvent);
        updatePromotionalEvent = promotionalEventService.validateAndSavePromotionalEvent(updatePromotionalEvent);
        log.info("Updated PromotionalEvent "+ updatePromotionalEvent.toString());

    }

    @Test
    public void test3findByPreId() throws InspireNetzException {

        // Get the standard
        PromotionalEvent promotionalEvent = PromotionalEventFixture.standardPromotionalEvent();
        promotionalEvent = promotionalEventService.validateAndSavePromotionalEvent(promotionalEvent);
        log.info("Original Security Settings  " + promotionalEvent.toString());

        // Add to tempSet
        tempSet.add(promotionalEvent);

        Assert.assertNotNull(promotionalEvent.getPreId());

        PromotionalEvent promotionalEventById = promotionalEventService.findByPreId(promotionalEvent.getPreId());
        Assert.assertNotNull(promotionalEventById);
        log.info("Fetched PromotionalEventById info" + promotionalEventById.toString());


    }

    @Test
    public void test5findByPreMerchantNo() throws InspireNetzException {

        // Get the standard
        PromotionalEvent promotionalEvent = PromotionalEventFixture.standardPromotionalEvent();
        promotionalEvent = promotionalEventService.validateAndSavePromotionalEvent(promotionalEvent);
        log.info("Original Security Settings  " + promotionalEvent.toString());

        // Add to tempSet
        tempSet.add(promotionalEvent);

        Assert.assertNotNull(promotionalEvent.getPreId());

        Page<PromotionalEvent> promotionalEventById = promotionalEventService.findByPreMerchantNo(promotionalEvent.getPreMerchantNo(),constructPageSpecification(0));
        Assert.assertNotNull(promotionalEventById);
        log.info("Fetched PromotionalEventById info" + promotionalEventById.toString());


    }
    @Test
    public void test4searchEvents() throws InspireNetzException {

        // Get the standard
        PromotionalEvent promotionalEvent = PromotionalEventFixture.standardPromotionalEvent();
        promotionalEvent = promotionalEventService.validateAndSavePromotionalEvent(promotionalEvent);
        log.info("Original Security Settings  " + promotionalEvent.toString());

        // Add to tempSet
        tempSet.add(promotionalEvent);

        Assert.assertNotNull(promotionalEvent.getPreId());

        Page<PromotionalEvent> promotionalEventById = promotionalEventService.searchPromotionalEvents("0","0",1l, constructPageSpecification(0));
        Assert.assertNotNull(promotionalEventById);
        log.info("Fetched PromotionalEventById info" + promotionalEventById.toString());


    }

    @Test
    public void test7GetValidEvents() throws InspireNetzException {

        Set<PromotionalEvent> promotionalEvents = PromotionalEventFixture.standardPromotionalEvents();

        for(PromotionalEvent promotionalEvent  : promotionalEvents){

            promotionalEvent = promotionalEventService.validateAndSavePromotionalEvent(promotionalEvent);

        }

        //Add to tempset
        tempSet.addAll(promotionalEvents);

        Page<PromotionalEvent> promotionalEventById = promotionalEventService.getValidEventsByDate(1L, DBUtils.covertToSqlDate("2014-09-30"), constructPageSpecification(0));
        Assert.assertNotNull(promotionalEventById);
        log.info("Fetched PromotionalEventById info" + promotionalEventById.toString());


    }

    @Test
    public void test7isDuplicatePromotionalEventExisting() {

        // Get the standard brand
        PromotionalEvent promotionalEvent = PromotionalEventFixture.standardPromotionalEvent();
        promotionalEvent = promotionalEventService.savePromotionalEvent(promotionalEvent);
        log.info("Original messgae spiel " + promotionalEvent.toString());

        // Add to tempSet
        tempSet.add(promotionalEvent);

        PromotionalEvent promotionalEvent1 = PromotionalEventFixture.standardPromotionalEvent();

        boolean isexisting=promotionalEventService.isDuplicateEventExisting(promotionalEvent1);
        Assert.assertTrue(isexisting);
        log.info("Fetched message promotionalEventService" + isexisting);




    }


    @org.junit.After
    public void tearDown() throws InspireNetzException {

        for ( PromotionalEvent promotionalEvent : tempSet ) {

            promotionalEventService.deletePromotionalEvent(promotionalEvent);

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
