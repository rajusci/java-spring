package com.inspirenetz.api.test.core.service;

import com.inspirenetz.api.config.SecurityConfig;
import com.inspirenetz.api.core.domain.RoleAccessRight;
import com.inspirenetz.api.core.repository.RoleAccessRightRepository;
import com.inspirenetz.api.core.service.RoleAccessRightService;
import com.inspirenetz.api.test.config.ApplicationTestConfig;
import com.inspirenetz.api.test.config.PersistenceTestConfig;
import com.inspirenetz.api.test.config.ServiceTestConfig;
import com.inspirenetz.api.test.core.fixture.RoleAccessRightFixture;
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
public class RoleAccessRightServiceTest {

    private static Logger log = LoggerFactory.getLogger(RoleAccessRightServiceTest.class);

    Set<RoleAccessRight> tempSet = new HashSet<>(0);

    @Autowired
    private RoleAccessRightService roleAccessRightService;

    UsernamePasswordAuthenticationToken principal;
    @Autowired
    @Qualifier("uds")
    protected UserDetailsService userDetailsService;

    @Before
    public void setup() {

        // Set the principal
        principal = ControllerTestUtils.getPrincipal(ControllerTestUtils.TEST_MERCHANT_USER_LOGINID, userDetailsService);

        // Set the context
        SecurityContextHolder.getContext().setAuthentication(principal);
    }

    @Test
    public void test1Create(){


        RoleAccessRight roleAccessRight = roleAccessRightService.saveRoleAccessRight(RoleAccessRightFixture.standardRoleAccessRight());
        log.info(roleAccessRight.toString());
        Assert.assertNotNull(roleAccessRight.getRarId());

        // Add to tempSet
        tempSet.add(roleAccessRight);

    }

    @Test
    public void test2Update() {

        RoleAccessRight roleAccessRight = RoleAccessRightFixture.standardRoleAccessRight();
        roleAccessRight = roleAccessRightService.saveRoleAccessRight(roleAccessRight);
        log.info("Original role access right " + roleAccessRight.toString());

        // Add to tempSet
        tempSet.add(roleAccessRight);

        RoleAccessRight updateRoleAccessRight = RoleAccessRightFixture.updatedStandRoleAccessRight(roleAccessRight);
        updateRoleAccessRight = roleAccessRightService.saveRoleAccessRight(updateRoleAccessRight);
        log.info("Updated RoleAccessRight "+ updateRoleAccessRight.toString());

    }

    @Test
    public void test3findByRarId() {

        // Get the standard
        RoleAccessRight roleAccessRight = RoleAccessRightFixture.standardRoleAccessRight();
        roleAccessRight = roleAccessRightService.saveRoleAccessRight(roleAccessRight);
        log.info("Original role acces rights  " + roleAccessRight.toString());

        // Add to tempSet
        tempSet.add(roleAccessRight);

        Assert.assertNotNull(roleAccessRight.getRarId());

        RoleAccessRight roleAccessRightById = roleAccessRightService.findByRarId(roleAccessRight.getRarId());
        Assert.assertNotNull(roleAccessRightById);
        log.info("Fetched RoleAccessRightById info" + roleAccessRightById.toString());


    }

    @Test
    public void test4findByRarRole() {

        // Get the standard
        RoleAccessRight roleAccessRight = RoleAccessRightFixture.standardRoleAccessRight();
        roleAccessRight = roleAccessRightService.saveRoleAccessRight(roleAccessRight);
        log.info("Original role access right data " + roleAccessRight.toString());

        Assert.assertNotNull(roleAccessRight.getRarId());

        // Add to tempSet
        tempSet.add(roleAccessRight);

        List<RoleAccessRight> roleAccessRightFindByRole = roleAccessRightService.findByRarRole(roleAccessRight.getRarRole());
        Assert.assertNotNull(roleAccessRightFindByRole);
        log.info("message for find by role" + roleAccessRightFindByRole.toString());


    }


    @Test
    public void test5findByRarRoleAndRarFunctionCode() {

        // Get the standard brand
        RoleAccessRight roleAccessRight = RoleAccessRightFixture.standardRoleAccessRight();
        roleAccessRight = roleAccessRightService.saveRoleAccessRight(roleAccessRight);
        log.info("Original role access right data " + roleAccessRight.toString());

        Assert.assertNotNull(roleAccessRight.getRarId());

        // Add to tempSet
        tempSet.add(roleAccessRight);


        List<RoleAccessRight> roleAccessRightByRarFunctionCode = roleAccessRightService.findByRarRoleAndRarFunctionCode(roleAccessRight.getRarRole(),roleAccessRight.getRarFunctionCode());
        Assert.assertNotNull(roleAccessRightByRarFunctionCode);
        log.info("Fetched findByRarRoleAndRarFunctionCode info" + roleAccessRightByRarFunctionCode.toString());


    }


    @org.junit.After
    public void tearDown() {

        for ( RoleAccessRight roleAccessRight : tempSet ) {

            roleAccessRightService.deleteRoleAccessRight(roleAccessRight.getRarId());

        }
    }
}
