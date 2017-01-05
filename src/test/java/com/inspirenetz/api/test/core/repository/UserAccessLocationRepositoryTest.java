package com.inspirenetz.api.test.core.repository;

import com.inspirenetz.api.core.domain.UserAccessLocation;
import com.inspirenetz.api.core.repository.UserAccessLocationRepository;
import com.inspirenetz.api.test.config.ApplicationTestConfig;
import com.inspirenetz.api.test.config.PersistenceTestConfig;
import com.inspirenetz.api.test.core.fixture.UserAccessLocationFixture;
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
 * Created by ameenci on 10/9/14.
 */
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes  = {ApplicationTestConfig.class, PersistenceTestConfig.class})
public class UserAccessLocationRepositoryTest {

    private static Logger log = LoggerFactory.getLogger(UserAccessLocationRepositoryTest.class);

    Set<UserAccessLocation> tempSet = new HashSet<>(0);

    @Autowired
    private UserAccessLocationRepository userAccessLocationRepository;

    @Before
    public void setup() {}

    @Test
    public void test1Create(){


        UserAccessLocation userAccessLocation = userAccessLocationRepository.save(UserAccessLocationFixture.standardUserAccessLocation());
        log.info(userAccessLocation.toString());
        Assert.assertNotNull(userAccessLocation.getUalId());

        // Add to tempSet
        tempSet.add(userAccessLocation);

    }

    @Test
    public void test2Update() {

        UserAccessLocation userAccessLocation = UserAccessLocationFixture.standardUserAccessLocation();
        userAccessLocation = userAccessLocationRepository.save(userAccessLocation);
        log.info("Original User Location " + userAccessLocation.toString());

        // Add to tempSet
        tempSet.add(userAccessLocation);

        UserAccessLocation updateuserAccessLocation = UserAccessLocationFixture.updatedStandUserAccessLocation(userAccessLocation);
        updateuserAccessLocation = userAccessLocationRepository.save(updateuserAccessLocation);
        log.info("Updated User Location "+ updateuserAccessLocation.toString());

    }

    @Test
    public void test3finByUalId() {

        // Get the standard  
        UserAccessLocation userAccessLocation = UserAccessLocationFixture.standardUserAccessLocation();
        userAccessLocation = userAccessLocationRepository.save(userAccessLocation);
        log.info("Original User Location " + userAccessLocation.toString());
        // Add to tempSet
        tempSet.add(userAccessLocation);

        Assert.assertNotNull(userAccessLocation.getUalId());

        UserAccessLocation userAccessLocationById = userAccessLocationRepository.findByUalId(userAccessLocation.getUalId());
        Assert.assertNotNull(userAccessLocationById);
        log.info("Fetched userAccessLocationById info" + userAccessLocationById.toString());


    }

    @Test
    public void test4findByUalUserId() {

        // Get the standard  
        UserAccessLocation userAccessLocation = UserAccessLocationFixture.standardUserAccessLocation();
        userAccessLocation = userAccessLocationRepository.save(userAccessLocation);
        log.info("Original  userAccessLocation" + userAccessLocation.toString());
        Assert.assertNotNull(userAccessLocation.getUalId());
        // Add to tempSet
        tempSet.add(userAccessLocation);


        List<UserAccessLocation> userAccessLocationListByUserId = userAccessLocationRepository.findByUalUserId(userAccessLocation.getUalUserId());
        Assert.assertNotNull(userAccessLocationListByUserId);
        log.info("Fetched UserAccessLocation function code  info" + userAccessLocationListByUserId.toString());


    }




    @org.junit.After
    public void tearDown() {

        for ( UserAccessLocation userAccessLocation : tempSet ) {

            userAccessLocationRepository.delete(userAccessLocation);

        }
    }
}
