package com.inspirenetz.api.test.core.repository;

import com.google.common.collect.Lists;
import com.inspirenetz.api.core.domain.LinkRequest;
import com.inspirenetz.api.core.repository.LinkRequestRepository;
import com.inspirenetz.api.test.config.ApplicationTestConfig;
import com.inspirenetz.api.test.config.PersistenceTestConfig;
import com.inspirenetz.api.test.core.fixture.LinkRequestFixture;
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
 * Created by saneeshci on 24/08/2014
 */
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes  = {ApplicationTestConfig.class, PersistenceTestConfig.class})
public class LinkRequestRepositoryTest {

    private static Logger log = LoggerFactory.getLogger(LinkRequestRepositoryTest.class);

    @Autowired
    private LinkRequestRepository linkRequestRepository;

    Set<LinkRequest> tempSet = new HashSet<>(0);

    @Before
    public void setup() {}

    @Test
    public void test1Create(){


        LinkRequest linkRequest = linkRequestRepository.save(LinkRequestFixture.standardLinkRequest());
        log.info(linkRequest.toString());

        // Add the items
        tempSet.add(linkRequest);

        Assert.assertNotNull(linkRequest.getLrqId());

    }

    @Test
    public void test2Update() {

        LinkRequest linkRequest = LinkRequestFixture.standardLinkRequest();
        linkRequest = linkRequestRepository.save(linkRequest);
        log.info("Original LinkRequests " + linkRequest.toString());

        // Add the items
        tempSet.add(linkRequest);

        LinkRequest updatedLinkRequests = LinkRequestFixture.updatedStandardLinkRequests(linkRequest);
        updatedLinkRequests = linkRequestRepository.save(updatedLinkRequests);
        log.info("Updated LinkRequests "+ updatedLinkRequests.toString());

    }

    @Test
    public void test3FindByLrqId() {


        LinkRequest linkRequest = LinkRequestFixture.standardLinkRequest();
        linkRequest = linkRequestRepository.save(linkRequest);
        log.info("Original LinkRequests " + linkRequest.toString());

        // Add the items
        tempSet.add(linkRequest);

        LinkRequest searchRequest = linkRequestRepository.findByLrqId(linkRequest.getLrqId());
        Assert.assertNotNull(searchRequest);;
        Assert.assertTrue(linkRequest.getLrqId().longValue() == searchRequest.getLrqId().longValue());



    }


    @Test
    public void test4FindAll() {

        LinkRequest linkRequest = LinkRequestFixture.standardLinkRequest();
        linkRequest = linkRequestRepository.save(linkRequest);
        log.info("Original LinkRequests " + linkRequest.toString());

        // Add the items
        tempSet.add(linkRequest);

        Page<LinkRequest> linkRequestPage = linkRequestRepository.findAll(constructPageSpecification(0));
        Assert.assertNotNull(linkRequestPage);
        List<LinkRequest> linkRequestList = Lists.newArrayList((Iterable<LinkRequest>) linkRequestPage);
        log.info("Link Request List : "+ linkRequestList.toString());

    }


    @After
    public void tearDown() {

        for(LinkRequest linkRequest: tempSet) {

           linkRequestRepository.delete(linkRequest);

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
        return new Sort(Sort.Direction.ASC, "lrqId");
    }


}
