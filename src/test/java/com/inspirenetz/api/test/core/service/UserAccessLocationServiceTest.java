package com.inspirenetz.api.test.core.service;

import com.google.common.collect.Lists;
import com.inspirenetz.api.config.SecurityConfig;
import com.inspirenetz.api.core.domain.UserAccessLocation;
import com.inspirenetz.api.core.service.UserAccessLocationService;
import com.inspirenetz.api.rest.exception.InspireNetzException;
import com.inspirenetz.api.test.config.ApplicationTestConfig;
import com.inspirenetz.api.test.config.PersistenceTestConfig;
import com.inspirenetz.api.test.config.ServiceTestConfig;
import com.inspirenetz.api.test.core.fixture.UserAccessLocationFixture;
import com.inspirenetz.api.test.util.ControllerTestUtils;
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
import java.util.List;
import java.util.Set;

/**
 * Created by ameenci on 10/9/14.
 */
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes  = {ApplicationTestConfig.class, PersistenceTestConfig.class, ServiceTestConfig.class, SecurityConfig.class})
public class UserAccessLocationServiceTest {

    private static Logger log = LoggerFactory.getLogger(UserAccessLocationServiceTest.class);

    Set<UserAccessLocation> tempSet = new HashSet<>(0);

    @Autowired
    private UserAccessLocationService userAccessLocationService;

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


        UserAccessLocation userAccessLocation = userAccessLocationService.saveUserAccessLocation(UserAccessLocationFixture.standardUserAccessLocation());
        log.info(userAccessLocation.toString());
        Assert.assertNotNull(userAccessLocation.getUalId());

        // Add to tempSet
        tempSet.add(userAccessLocation);

    }

    @Test
    public void test2Update() throws InspireNetzException {

        UserAccessLocation userAccessLocation = UserAccessLocationFixture.standardUserAccessLocation();
        userAccessLocation = userAccessLocationService.validateAndSaveUserAccessLocation(userAccessLocation);
        log.info(" original data for save  " + userAccessLocation.toString());

        // Add to tempSet
        tempSet.add(userAccessLocation);

        UserAccessLocation updateUserAccessLocation = UserAccessLocationFixture.updatedStandUserAccessLocation(userAccessLocation);
        updateUserAccessLocation = userAccessLocationService.saveUserAccessLocation(userAccessLocation);
        log.info("Updated   userAccessLocation "+ updateUserAccessLocation.toString());

    }

    @Test
    public void test3findByUalId() {

        // Get the standard   
        UserAccessLocation userAccessLocation = UserAccessLocationFixture.standardUserAccessLocation();
        userAccessLocation = userAccessLocationService.saveUserAccessLocation(userAccessLocation);
        log.info(" original data for save  " + userAccessLocation.toString());

        // Add to tempSet
        tempSet.add(userAccessLocation);

        Assert.assertNotNull(userAccessLocation.getUalId());

        UserAccessLocation userAccessLocationById = userAccessLocationService.findByUalId(userAccessLocation.getUalId());
        Assert.assertNotNull(userAccessLocationById);
        log.info("Fetched   userAccessLocationById info" + userAccessLocationById.toString());


    }

    @Test
    public void test4findByUalUserId() {

        // Get the standard   
        UserAccessLocation userAccessLocation = UserAccessLocationFixture.standardUserAccessLocation();
        userAccessLocation = userAccessLocationService.saveUserAccessLocation(userAccessLocation);
        log.info(" original data for save  " + userAccessLocation.toString());

        // Add to tempSet
        tempSet.add(userAccessLocation);

        Assert.assertNotNull(userAccessLocation.getUalId());

        List<UserAccessLocation> userAccessLocationList = userAccessLocationService.findByUalUserId(userAccessLocation.getUalUserId());
        Assert.assertNotNull(userAccessLocationList);
        log.info("Fetched message function code  info" + userAccessLocationList.toString());


    }




    @Test
    public void test5deleteUserAccessLocation() {


        // Get the standard   
        UserAccessLocation userAccessLocation = UserAccessLocationFixture.standardUserAccessLocation();
        userAccessLocation = userAccessLocationService.saveUserAccessLocation(userAccessLocation);
        log.info(" original data for save  " + userAccessLocation.toString());
        tempSet.add(userAccessLocation);

        boolean isdeleted=userAccessLocationService.deleteUserAccessLocation(userAccessLocation.getUalId());
        Assert.assertTrue(isdeleted);
        log.info("Fetched message findByMsiNameLike" + isdeleted);


        UserAccessLocation searchUserLocation = userAccessLocationService.findByUalId(userAccessLocation.getUalId());
        Assert.assertNull(searchUserLocation);
        log.info("  userAccessLocation Deleted");

        // Remove from tempSet
        tempSet.remove(userAccessLocation);

    }




    @org.junit.After
    public void tearDown() {

        for ( UserAccessLocation userAccessLocation : tempSet ) {

            userAccessLocationService.deleteUserAccessLocation(userAccessLocation.getUalId());

        }
    }
}
