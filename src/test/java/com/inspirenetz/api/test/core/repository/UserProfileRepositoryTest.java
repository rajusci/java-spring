package com.inspirenetz.api.test.core.repository;

import com.inspirenetz.api.core.domain.UserProfile;
import com.inspirenetz.api.core.repository.UserProfileRepository;
import com.inspirenetz.api.test.config.ApplicationTestConfig;
import com.inspirenetz.api.test.config.PersistenceTestConfig;
import com.inspirenetz.api.test.core.fixture.UserProfileFixture;
import org.junit.Assert;
import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.HashSet;
import java.util.Set;

/**
 * Created by alameen on 24/10/14.
 */
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes  = {ApplicationTestConfig.class, PersistenceTestConfig.class})
public class UserProfileRepositoryTest {

    private static Logger log = LoggerFactory.getLogger(UserProfileRepositoryTest.class);

    Set<UserProfile> tempSet = new HashSet<>(0);

    @Autowired
    private UserProfileRepository userProfileRepository;

    @Before
    public void setup() {}

    @Test
    public void test1Create(){


        UserProfile userProfile = userProfileRepository.save(UserProfileFixture.standardUserProfile());
        log.info(userProfile.toString());
        Assert.assertNotNull(userProfile.getUspId());

        // Add to tempSet
        tempSet.add(userProfile);

    }

    @Test
    public void test2Update() {

        UserProfile userProfile = UserProfileFixture.standardUserProfile();
        userProfile = userProfileRepository.save(userProfile);
        log.info("Original userProfile " + userProfile.toString());

        // Add to tempSet
        tempSet.add(userProfile);

        UserProfile updateUserProfile = UserProfileFixture.updatedStandUserProfile(userProfile);
        updateUserProfile = userProfileRepository.save(updateUserProfile);
        log.info("Updated userProfile "+ updateUserProfile.toString());

    }

    @Test
    public void test3findByUspUserNo() {

        UserProfile userProfile = UserProfileFixture.standardUserProfile();
        userProfile = userProfileRepository.save(userProfile);
        log.info("Original userProfile " + userProfile.toString());

        // Add to tempSet
        tempSet.add(userProfile);

        userProfile = userProfileRepository.findByUspUserNo(userProfile.getUspUserNo());
        log.info("Updated userProfile "+ userProfile.toString());

    }

    @org.junit.After
    public void tearDown() {

        for ( UserProfile userProfile : tempSet ) {

            userProfileRepository.delete(userProfile);

        }
    }

}
