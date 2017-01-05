package com.inspirenetz.api.test.core.repository;

import com.inspirenetz.api.core.domain.LinkedLoyalty;
import com.inspirenetz.api.core.repository.LinkedLoyaltyRepository;
import com.inspirenetz.api.test.config.ApplicationTestConfig;
import com.inspirenetz.api.test.config.PersistenceTestConfig;
import com.inspirenetz.api.test.core.fixture.LinkedLoyaltyFixture;
import org.junit.*;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
 * Created by sandheepgr on 17/2/14.
 */
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes  = {ApplicationTestConfig.class, PersistenceTestConfig.class})
public class LinkedLoyaltyRepositoryTest {

    private static Logger log = LoggerFactory.getLogger(LinkedLoyaltyRepositoryTest.class);

    @Autowired
    private LinkedLoyaltyRepository linkedLoyaltyRepository;


    Set<LinkedLoyalty> tempSet = new HashSet<>(0);


    @Before
    public void setup() {}



    @Test
    public void test1Create(){


        LinkedLoyalty linkedLoyalty = linkedLoyaltyRepository.save(LinkedLoyaltyFixture.standardLinkedLoyalty());

        // Add to the tempSet
        tempSet.add(linkedLoyalty);

        log.info(linkedLoyalty.toString());
        Assert.assertNotNull(linkedLoyalty.getLilId());

    }

    @Test
    public void test2Update() {

        LinkedLoyalty linkedLoyalty = LinkedLoyaltyFixture.standardLinkedLoyalty();
        linkedLoyalty = linkedLoyaltyRepository.save(linkedLoyalty);
        log.info("Original LinkedLoyalty " + linkedLoyalty.toString());

        // Add to the tempSet
        tempSet.add(linkedLoyalty);

        LinkedLoyalty updatedLinkedLoyalty = LinkedLoyaltyFixture.updatedStandardLinkedLoyalty(linkedLoyalty);
        updatedLinkedLoyalty = linkedLoyaltyRepository.save(updatedLinkedLoyalty);
        log.info("Updated LinkedLoyalty "+ updatedLinkedLoyalty.toString());

    }


    public void test3FindByLilId() {

        LinkedLoyalty linkedLoyalty = LinkedLoyaltyFixture.standardLinkedLoyalty();
        linkedLoyalty = linkedLoyaltyRepository.save(linkedLoyalty);
        log.info("Original LinkedLoyalty " + linkedLoyalty.toString());


        // Add to the tempSet
        tempSet.add(linkedLoyalty);

        // Get the data
        LinkedLoyalty searchLoyalty = linkedLoyaltyRepository.findByLilId(linkedLoyalty.getLilId());
        Assert.assertNotNull(searchLoyalty);
        Assert.assertTrue(linkedLoyalty.getLilId().longValue() ==  searchLoyalty.getLilId().longValue());;
        log.info("Searched LinkedLoyalty : " + searchLoyalty.toString());


    }

    @Test
    public void test4FindByLilChildCustomerNo(){

        LinkedLoyalty linkedLoyalty = LinkedLoyaltyFixture.standardLinkedLoyalty();
        linkedLoyalty = linkedLoyaltyRepository.save(linkedLoyalty);
        log.info("Original LinkedLoyalty " + linkedLoyalty.toString());


        // Add to the tempSet
        tempSet.add(linkedLoyalty);

        // Get the data
        LinkedLoyalty searchLoyalty = linkedLoyaltyRepository.findByLilChildCustomerNo(linkedLoyalty.getLilChildCustomerNo());
        Assert.assertNotNull(searchLoyalty);
        Assert.assertTrue(linkedLoyalty.getLilId().longValue() ==  searchLoyalty.getLilId().longValue());;
        log.info("Searched LinkedLoyalty : " + searchLoyalty.toString());

    }



    @Test
    public void test5FindByLilParentCustomerNo(){


        LinkedLoyalty linkedLoyalty = LinkedLoyaltyFixture.standardLinkedLoyalty();
        linkedLoyalty = linkedLoyaltyRepository.save(linkedLoyalty);
        log.info("Original LinkedLoyalty " + linkedLoyalty.toString());


        // Add to the tempSet
        tempSet.add(linkedLoyalty);

        // Get the data
        List<LinkedLoyalty> linkedLoyaltyList = linkedLoyaltyRepository.findByLilParentCustomerNo(linkedLoyalty.getLilParentCustomerNo());
        Assert.assertNotNull(linkedLoyaltyList);
        log.info("Searched LinkedLoyalty : " + linkedLoyaltyList.toString());




    }




    @After
    public void tearDown() {

        for(LinkedLoyalty linkedLoyalty : tempSet ) {

            linkedLoyaltyRepository.delete(linkedLoyalty);

        }

    }

}
