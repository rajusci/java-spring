package com.inspirenetz.api.test.core.service;

/**
 * Created by alameen on 25/10/14.
 */

import com.google.common.collect.Sets;
import com.inspirenetz.api.core.domain.Customer;
import com.inspirenetz.api.core.domain.User;
import com.inspirenetz.api.core.domain.UserProfile;
import com.inspirenetz.api.core.repository.UserProfileRepository;
import com.inspirenetz.api.core.service.CustomerService;
import com.inspirenetz.api.core.service.UserProfileService;
import com.inspirenetz.api.core.service.UserService;
import com.inspirenetz.api.rest.exception.InspireNetzException;
import com.inspirenetz.api.test.config.*;
import com.inspirenetz.api.test.core.fixture.CustomerFixture;
import com.inspirenetz.api.test.core.fixture.UserFixture;
import com.inspirenetz.api.test.core.fixture.UserProfileFixture;
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
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.HashSet;
import java.util.Set;


@FixMethodOrder(MethodSorters.NAME_ASCENDING)
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes  = {ApplicationTestConfig.class, PersistenceTestConfig.class, ServiceTestConfig.class,ServiceTestConfig.class, SecurityTestConfig.class, NotificationTestConfig.class})
public class UserProfileServiceTest {

    private static Logger log = LoggerFactory.getLogger(UserProfileServiceTest.class);

    @Autowired
    private UserProfileService userProfileService;

    @Autowired
    private UserProfileRepository userProfileRepository;

    @Autowired
    private UserService userService;

    @Autowired
    private CustomerService customerService;

    UsernamePasswordAuthenticationToken principal;

    @Autowired
    @Qualifier("uds")
    protected UserDetailsService userDetailsService;


    Set<UserProfile> tempSet = new HashSet<>(0);

    Set<UserProfile> tempSet1 = new HashSet<>(0);

    Set<UserProfile> tempSet2 = new HashSet<>(0);

    @Before
    public void setUp() {

        // Set the principal
        principal = ControllerTestUtils.getPrincipal(ControllerTestUtils.TEST_CUSTOMER_LOGINID, userDetailsService);

        // Set the context
        SecurityContextHolder.getContext().setAuthentication(principal);

    }

    @Test
    public void test1saveUserProfile() throws InspireNetzException {

        //save user
        User user = UserFixture.standardUser();
        user = userService.saveUser(user);

        Customer customer = CustomerFixture.standardCustomer(user);
        customerService.saveCustomer(customer);
        Assert.assertNotNull(customer.getCusCustomerNo());
        log.info("Customer created");
        // Get the standard userProfile
        UserProfile userProfile = UserProfileFixture.standardUserProfile(user);
        userProfile =userProfileService.saveUserProfile(userProfile);
        Assert.assertNotNull(userProfile.getUspId());

        log.info("userProfile "+userProfile);

    }



    @Test
    public void test2findByUspUserNo() throws InspireNetzException {

//        // Create the userProfile
        UserProfile userProfile = UserProfileFixture.standardUserProfile();
        userProfile = userProfileService.saveUserProfile(userProfile);
        Assert.assertNotNull(userProfile.getUspId());
        log.info("UserProfile created");

        tempSet.add(userProfile);

        UserProfile fetchUserProfile = userProfileService.findByUspUserNo(userProfile.getUspUserNo());

        log.info("Fetched userProfile info" + fetchUserProfile.toString());

    }

    @org.junit.After
    public void tearDown() throws InspireNetzException {

        for ( UserProfile userProfile : tempSet ) {

            userProfileService.delete(userProfile);

        }


    }
}
