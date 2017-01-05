package com.inspirenetz.api.test.core.repository;

import com.inspirenetz.api.core.domain.RoleAccessRight;
import com.inspirenetz.api.core.repository.RoleAccessRightRepository;
import com.inspirenetz.api.test.config.ApplicationTestConfig;
import com.inspirenetz.api.test.config.PersistenceTestConfig;
import com.inspirenetz.api.test.core.fixture.RoleAccessRightFixture;
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
public class RoleAccessRightRepositoryTest {

    private static Logger log = LoggerFactory.getLogger(RoleAccessRightRepositoryTest.class);

    Set<RoleAccessRight> tempSet = new HashSet<>(0);

    @Autowired
    private RoleAccessRightRepository roleAccessRightRepository;

    @Before
    public void setup() {}

    @Test
    public void test1Create(){


        RoleAccessRight roleAccessRight = roleAccessRightRepository.save(RoleAccessRightFixture.standardRoleAccessRight());
        log.info(roleAccessRight.toString());
        Assert.assertNotNull(roleAccessRight.getRarId());

        // Add to tempSet
        tempSet.add(roleAccessRight);

    }

    @Test
    public void test2Update() {

        RoleAccessRight roleAccessRight = RoleAccessRightFixture.standardRoleAccessRight();
        roleAccessRight = roleAccessRightRepository.save(roleAccessRight);
        log.info("Original role access right " + roleAccessRight.toString());

        // Add to tempSet
        tempSet.add(roleAccessRight);

        RoleAccessRight updateRoleAccessRight = RoleAccessRightFixture.updatedStandRoleAccessRight(roleAccessRight);
        updateRoleAccessRight = roleAccessRightRepository.save(updateRoleAccessRight);
        log.info("Updated RoleAccessRight "+ updateRoleAccessRight.toString());

    }

    @Test
    public void test3findByRarId() {

        // Get the standard
        RoleAccessRight roleAccessRight = RoleAccessRightFixture.standardRoleAccessRight();
        roleAccessRight = roleAccessRightRepository.save(roleAccessRight);
        log.info("Original role acces rights  " + roleAccessRight.toString());

        // Add to tempSet
        tempSet.add(roleAccessRight);

        Assert.assertNotNull(roleAccessRight.getRarId());

        RoleAccessRight roleAccessRightById = roleAccessRightRepository.findByRarId(roleAccessRight.getRarId());
        Assert.assertNotNull(roleAccessRightById);
        log.info("Fetched RoleAccessRightById info" + roleAccessRightById.toString());


    }

    @Test
    public void test4findByRarRole() {

        // Get the standard
        RoleAccessRight roleAccessRight = RoleAccessRightFixture.standardRoleAccessRight();
        roleAccessRight = roleAccessRightRepository.save(roleAccessRight);
        log.info("Original role access right data " + roleAccessRight.toString());

        Assert.assertNotNull(roleAccessRight.getRarId());

        // Add to tempSet
        tempSet.add(roleAccessRight);

        List<RoleAccessRight> roleAccessRightFindByRole = roleAccessRightRepository.findByRarRole(roleAccessRight.getRarRole());
        Assert.assertNotNull(roleAccessRightFindByRole);
        log.info("message for find by role" + roleAccessRightFindByRole.toString());


    }


    @Test
    public void test5findByRarRoleAndRarFunctionCode() {

        // Get the standard brand
        RoleAccessRight roleAccessRight = RoleAccessRightFixture.standardRoleAccessRight();
        roleAccessRight = roleAccessRightRepository.save(roleAccessRight);
        log.info("Original role access right data " + roleAccessRight.toString());

        Assert.assertNotNull(roleAccessRight.getRarId());

        // Add to tempSet
        tempSet.add(roleAccessRight);


        List<RoleAccessRight> roleAccessRightByRarFunctionCode = roleAccessRightRepository.findByRarRoleAndRarFunctionCode(roleAccessRight.getRarRole(),roleAccessRight.getRarFunctionCode());
        Assert.assertNotNull(roleAccessRightByRarFunctionCode);
        log.info("Fetched findByRarRoleAndRarFunctionCode info" + roleAccessRightByRarFunctionCode.toString());


    }


    @org.junit.After
    public void tearDown() {

        for ( RoleAccessRight roleAccessRight : tempSet ) {

            roleAccessRightRepository.delete(roleAccessRight);

        }
    }
}
