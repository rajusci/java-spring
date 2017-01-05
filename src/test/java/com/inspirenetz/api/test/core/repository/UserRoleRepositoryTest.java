package com.inspirenetz.api.test.core.repository;

import com.inspirenetz.api.core.domain.UserRole;
import com.inspirenetz.api.core.repository.UserRoleRepository;
import com.inspirenetz.api.test.config.ApplicationTestConfig;
import com.inspirenetz.api.test.config.PersistenceTestConfig;
import com.inspirenetz.api.test.core.fixture.UserRoleFixture;
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
import java.util.List;
import java.util.Set;

/**
 * Created by ameenci on 10/9/14.
 */
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes  = {ApplicationTestConfig.class, PersistenceTestConfig.class})
public class UserRoleRepositoryTest {

    private static Logger log = LoggerFactory.getLogger(UserRoleRepositoryTest.class);

    Set<UserRole> tempSet = new HashSet<>(0);

    @Autowired
    private UserRoleRepository userRoleRepository;

    @Before
    public void setup() {}

    @Test
    public void test1Create(){


        UserRole userRole = userRoleRepository.save(UserRoleFixture.standardUserRole());
        log.info(userRole.toString());
        Assert.assertNotNull(userRole.getUerId());

        // Add to tempSet
        tempSet.add(userRole);

    }

    @Test
    public void test2Update() {

        UserRole userRole = UserRoleFixture.standardUserRole();
        userRole = userRoleRepository.save(userRole);
        log.info("Original userRole " + userRole.toString());

        // Add to tempSet
        tempSet.add(userRole);

        UserRole updatedStandardUserRole = UserRoleFixture.updatedStandardUserRole(userRole);
        updatedStandardUserRole = userRoleRepository.save(updatedStandardUserRole);
        log.info("Updated user role "+ updatedStandardUserRole.toString());

    }

    @Test
    public void test3findByUerId() {

        // Get the standard
        UserRole userRole = UserRoleFixture.standardUserRole();
        userRole = userRoleRepository.save(userRole);
        log.info("Original user role  " + userRole.toString());

        // Add to tempSet
        tempSet.add(userRole);

        Assert.assertNotNull(userRole.getUerId());

        UserRole userRoleById = userRoleRepository.findByUerId(userRole.getUerId());
        Assert.assertNotNull(userRoleById);
        log.info("Fetched userRoleById info" + userRoleById.toString());


    }

    @Test
    public void test4findByUerUserId() {

        // Get the standard
        UserRole userRole = UserRoleFixture.standardUserRole();
        userRole = userRoleRepository.save(userRole);
        log.info("Original role  right data " + userRole.toString());

        Assert.assertNotNull(userRole.getUerId());

        // Add to tempSet
        tempSet.add(userRole);

        List<UserRole> roleAccessRightFindByRole = userRoleRepository.findByUerUserId(userRole.getUerUserId());
        Assert.assertNotNull(roleAccessRightFindByRole);
        log.info("message for find by user role" + roleAccessRightFindByRole.toString());


    }




    @org.junit.After
    public void tearDown() {

        for ( UserRole userRole : tempSet ) {

            userRoleRepository.delete(userRole);

        }
    }
}
