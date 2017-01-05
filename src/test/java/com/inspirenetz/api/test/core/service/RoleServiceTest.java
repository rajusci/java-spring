package com.inspirenetz.api.test.core.service;

import com.google.common.collect.Lists;
import com.inspirenetz.api.core.domain.Customer;
import com.inspirenetz.api.core.domain.Role;
import com.inspirenetz.api.core.domain.LinkedLoyalty;
import com.inspirenetz.api.core.service.CustomerService;
import com.inspirenetz.api.core.service.RoleService;
import com.inspirenetz.api.rest.exception.InspireNetzException;
import com.inspirenetz.api.test.config.*;
import com.inspirenetz.api.test.core.fixture.RoleFixture;
import com.inspirenetz.api.test.util.ControllerTestUtils;
import org.junit.*;
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
 * Created by saneesh-ci on 9/9/14.
 */
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes  = {ApplicationTestConfig.class, PersistenceTestConfig.class, ServiceTestConfig.class, SecurityTestConfig.class, NotificationTestConfig.class})
public class RoleServiceTest {


    private static Logger log = LoggerFactory.getLogger(RoleServiceTest.class);

    @Autowired
    private RoleService roleService;

    UsernamePasswordAuthenticationToken principal;

    @Autowired
    @Qualifier("uds")
    protected UserDetailsService userDetailsService;

    @Autowired
    private CustomerService customerService;


    Set<Role> tempSet = new HashSet<>(0);

    Set<Customer> customerSet = new HashSet<>(0);

    Set<LinkedLoyalty> linkLoyaltySet = new HashSet<>(0);

    @Before
    public void setUp() {

        // Set the principal
        principal = ControllerTestUtils.getPrincipal(ControllerTestUtils.TEST_MERCHANT_ADMIN_LOGINID, userDetailsService);

        // Set the context
        SecurityContextHolder.getContext().setAuthentication(principal);
    }


    @Test
    public void test1Create(){

        RoleFixture roleFixture=new RoleFixture();

        Role role = roleService.saveRole(roleFixture.standardRole());
        log.info(role.toString());

        // Add the items
        tempSet.add(role);

        Assert.assertNotNull(role.getRolId());

    }

    @Test
    public void test2Update() throws InspireNetzException {

//        RoleFixture roleFixture=new RoleFixture();
//
//        Role role = roleFixture.standardRole();
//        role = roleService.saveRole(role);
//        log.info("Original Roles " + role.toString());
//
//
//
//        // Add the items
//
        // for getting dummy data from database for updating the test 42L is role id from roles table its must be specified explicitly
        Role role=roleService.findByRolId(42L);
        log.info("role select data"+role.getRolId());
        Role updatedRole = RoleFixture.updatedStandardRole(role);
        log.info("Updated Role "+ updatedRole.toString());
        updatedRole = roleService.validateAndSaveRole(updatedRole);
//        tempSet.add(role);
        log.info("Updated Role "+ updatedRole.toString());

    }

    @Test
    public void test3FindByRolId() throws InspireNetzException {

        RoleFixture roleFixture=new RoleFixture();

        Role role = roleFixture.standardRole();
        role = roleService.saveRole(role);
        log.info("Original Roles " + role.toString());

        // Add the items
        tempSet.add(role);

        Role searchRequest = roleService.findByRolId(role.getRolId());
        Assert.assertNotNull(searchRequest);
        Assert.assertTrue(role.getRolId().longValue() == searchRequest.getRolId().longValue());



    }


    @Test
    public void test4SearchRoles() {

        RoleFixture roleFixture=new RoleFixture();

        Role role = roleFixture.standardRole();
        role = roleService.saveRole(role);
        log.info("Original Roles " + role.toString());

        // Add the items
        tempSet.add(role);

        Page<Role> rolePage = roleService.searchRoles("0","0",constructPageSpecification(0));
        Assert.assertNotNull(rolePage);
        List<Role> roleList = Lists.newArrayList((Iterable<Role>) rolePage);
        log.info("Role List : "+ roleList.toString());

    }

    @Test
    public void test5DeleteRoles() throws InspireNetzException {

        // Create the role

        RoleFixture roleFixture=new RoleFixture();

        Role  role = roleFixture.standardRole();
        role = roleService.saveRole(role);
        Assert.assertNotNull(role.getRolId());
        log.info("Roles created");

        // call the delete role
        roleService.deleteRole(role.getRolId());

        // Get the link request again
        Role role1 = roleService.findByRolId(role.getRolId());
        Assert.assertNull(role1);;


    }


    @Test
    public void test6SaveAndValidateRole() throws InspireNetzException {

        RoleFixture roleFixture=new RoleFixture();

        Role role = roleService.validateAndSaveRole(roleFixture.standardRole());
        log.info(role.toString());

        // Add the items
        tempSet.add(role);

        Assert.assertNotNull(role.getRolId());

    }



    @Test
    public void test7DeleteRoles() throws InspireNetzException {

        // Create the role
        RoleFixture roleFixture=new RoleFixture();

        Role  role = roleFixture.standardRole();
        role = roleService.saveRole(role);
        Assert.assertNotNull(role.getRolId());
        log.info("Roles created");

        // call the delete role
        roleService.validateAndDeleteRole(role.getRolId());

        // Get the link request again
        Role role1 = roleService.findByRolId(role.getRolId());
        Assert.assertNull(role1);;


    }

    @Test
    public void test8FindByUserType() throws InspireNetzException {


        RoleFixture roleFixture=new RoleFixture();

        Role role = roleFixture.standardRole();
        role = roleService.saveRole(role);
        log.info("Original Roles " + role.toString());

        // Add the items
        tempSet.add(role);

        List<Role> roleList = roleService.getRolesByUserType(role.getRolUserType());
        Assert.assertTrue(roleList.size() > 0);

    }

    @After
    public void tearDown() {
        for(Role role: tempSet) {

            roleService.deleteRole(role.getRolId());

        }

}


    /**
     * Returns a new object which specifies the the wanted result page.
     * @param pageIndex The index of the wanted result page
     * @return
     */
    private Pageable constructPageSpecification(int pageIndex) {
        Pageable pageSpecification = new PageRequest(pageIndex, 100, sortByLastNameAsc());
        return pageSpecification;
    }

    /**
     * Returns a Sort object which sorts persons in ascending order by using the last name.
     * @return
     */
    private Sort sortByLastNameAsc() {
        return new Sort(Sort.Direction.ASC, "rolName");
    }

}
