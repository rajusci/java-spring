package com.inspirenetz.api.test.core.service;

import com.inspirenetz.api.config.SecurityConfig;
import com.inspirenetz.api.core.domain.UserRole;
import com.inspirenetz.api.core.service.UserRoleService;
import com.inspirenetz.api.rest.exception.InspireNetzException;
import com.inspirenetz.api.test.config.ApplicationTestConfig;
import com.inspirenetz.api.test.config.PersistenceTestConfig;
import com.inspirenetz.api.test.config.ServiceTestConfig;
import com.inspirenetz.api.test.core.fixture.UserRoleFixture;
import com.inspirenetz.api.test.util.ControllerTestUtils;
import com.inspirenetz.api.util.AuthSessionUtils;
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
public class UserRoleServiceTest {

    private static Logger log = LoggerFactory.getLogger(UserRoleServiceTest.class);

    Set<UserRole> tempSet = new HashSet<>(0);

    @Autowired
    private UserRoleService userRoleService;

    @Autowired
    private AuthSessionUtils authSessionUtils;


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
    public void test1Create() throws InspireNetzException {


        UserRole userRole = userRoleService.validateAndSaveUserRole(UserRoleFixture.standardUserRole());
        log.info(userRole.toString());
        Assert.assertNotNull(userRole.getUerId());

        // Add to tempSet
        tempSet.add(userRole);

    }

    @Test
    public void test2Update() {

        UserRole userRole = UserRoleFixture.standardUserRole();
        userRole = userRoleService.saveUserRole(userRole);
        log.info("Original userRole " + userRole.toString());

        // Add to tempSet
        tempSet.add(userRole);

        UserRole updatedStandardUserRole = UserRoleFixture.updatedStandardUserRole(userRole);
        updatedStandardUserRole = userRoleService.saveUserRole(updatedStandardUserRole);
        log.info("Updated user role "+ updatedStandardUserRole.toString());

    }

    @Test
    public void test3findByUerId() {

        // Get the standard
        UserRole userRole = UserRoleFixture.standardUserRole();
        userRole = userRoleService.saveUserRole(userRole);
        log.info("Original user role  " + userRole.toString());

        // Add to tempSet
        tempSet.add(userRole);

        Assert.assertNotNull(userRole.getUerId());

        UserRole userRoleById = userRoleService.findByUerId(userRole.getUerId());
        Assert.assertNotNull(userRoleById);
        log.info("Fetched userRoleById info" + userRoleById.toString());


    }

    @Test
    public void test4findByUerUserId() {

        // Get the standard
        UserRole userRole = UserRoleFixture.standardUserRole();
        userRole = userRoleService.saveUserRole(userRole);
        log.info("Original role  right data " + userRole.toString());

        Assert.assertNotNull(userRole.getUerId());

        // Add to tempSet
        tempSet.add(userRole);

        List<UserRole> roleAccessRightFindByRole = userRoleService.findByUerUserId(userRole.getUerId());
        Assert.assertNotNull(roleAccessRightFindByRole);
        log.info("message for find by user role" + roleAccessRightFindByRole.toString());


    }

    @Test
    public void test7deleteMessageSpiel() {

        // Get the standard
        UserRole userRole = UserRoleFixture.standardUserRole();
        userRole = userRoleService.saveUserRole(userRole);
        log.info("Original role  right data " + userRole.toString());


        // Add to tempSet
        tempSet.add(userRole);
        boolean isDeleted=userRoleService.deleteUserRole(userRole.getUerId());
        Assert.assertTrue(isDeleted);
        log.info("Fetched message test7deleteMessageSpiel" + isDeleted);


        UserRole searchDataIsDeleted = userRoleService.findByUerId(userRole.getUerId());
        Assert.assertNull(searchDataIsDeleted);
        log.info("User Role is  Deleted");

        // Remove from tempSet
        tempSet.remove(userRole);

    }


    @Test
    public void test8ValidateUserRoleAssigned() {

        // Find the current user
        Long userNo = authSessionUtils.getUserNo();

        // Create a userROle
        // Get the standard
        UserRole userRole = UserRoleFixture.standardUserRole();
        userRole.setUerUserId(userNo);
        userRole.setUerRole(20L);
        userRole = userRoleService.saveUserRole(userRole);

        tempSet.add(userRole);

        log.info("Original role  right data " + userRole.toString());

        boolean isAssigned = authSessionUtils.isRoleAssigned(20L);
        Assert.assertTrue(isAssigned);

    }


    @org.junit.After
    public void tearDown() {

        for ( UserRole userRole : tempSet ) {

            userRoleService.deleteUserRole(userRole.getUerId());

        }
    }

}
