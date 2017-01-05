package com.inspirenetz.api.test.core.repository;

import com.google.common.collect.Lists;
import com.inspirenetz.api.core.dictionary.UserType;
import com.inspirenetz.api.core.domain.*;
import com.inspirenetz.api.core.repository.UserAccessLocationRepository;
import com.inspirenetz.api.core.repository.UserAccessRightRepository;
import com.inspirenetz.api.core.repository.UserRepository;
import com.inspirenetz.api.core.repository.UserRoleRepository;
import com.inspirenetz.api.test.config.ApplicationTestConfig;
import com.inspirenetz.api.test.config.PersistenceTestConfig;
import com.inspirenetz.api.test.core.fixture.UserFixture;
import org.junit.*;
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
 * Created by sandheepgr on 17/2/14.
 */
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes  = {ApplicationTestConfig.class, PersistenceTestConfig.class})
public class UserRepositoryTest {

    private static Logger log = LoggerFactory.getLogger(UserRepositoryTest.class);

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserRoleRepository userRoleRepository;

    @Autowired
    private UserAccessLocationRepository userAccessLocationRepository;

    @Autowired
    private UserAccessRightRepository userAccessRightRepository;


    Set<UserRole> roleSet = new HashSet<>(0);

    Set<User> tempSet = new HashSet<>(0);

    Set<UserAccessLocation> locationSet = new HashSet<>(0);

    Set<UserAccessRight> accessRightsSet = new HashSet<>(0);

    @Before
    public void setup() {}

    @Test
    public void test1Create(){

        // For setting user role data
        Set<UserRole> userRoles=new HashSet<>();

        UserRole userRole=new UserRole();
        userRole.setUerRole(10L);
        userRoles.add(userRole);
        roleSet.add(userRole);

        UserRole userRole1=new UserRole();
        userRole1.setUerRole(11L);
        userRoles.add(userRole1);
        roleSet.add(userRole1);
        // For setting user access location data
        Set<UserAccessLocation> userAccessLocations=new HashSet<>();

        UserAccessLocation userAccessLocation=new UserAccessLocation();
        userAccessLocation.setUalLocation(20L);
        userAccessLocations.add(userAccessLocation);
        locationSet.add(userAccessLocation);

        UserAccessLocation userAccessLocation1=new UserAccessLocation();
        userAccessLocation1.setUalLocation(21L);
        userAccessLocations.add(userAccessLocation1);
        locationSet.add(userAccessLocation1);

        // For setting user access location data
        Set<UserAccessRight> userAccessRights=new HashSet<>();

        UserAccessRight userAccessRight=new UserAccessRight();
        userAccessRight.setUarFunctionCode(30L);
        userAccessRights.add(userAccessRight);
        accessRightsSet.add(userAccessRight);

        UserAccessRight userAccessRight1=new UserAccessRight();
        userAccessRight1.setUarFunctionCode(31L);
        userAccessRights.add(userAccessRight1);
        accessRightsSet.add(userAccessRight1);

        User user = UserFixture.standardUser();
        user.setUserAccessLocationSet(userAccessLocations);
        user.setUserAccessRightsSet(userAccessRights);
        user.setUserRoleSet(userRoles);

        user = userRepository.save(user);
        tempSet.add(user);
        log.info(user.toString());
        Assert.assertNotNull(user.getUsrUserNo());

    }

    @Test
    public void test2Update() {

        // For setting user role data
        Set<UserRole> userRoles=new HashSet<>();

        UserRole userRole=new UserRole();
        userRole.setUerRole(10L);
        userRoles.add(userRole);
        roleSet.add(userRole);

        UserRole userRole1=new UserRole();
        userRole1.setUerRole(11L);
        userRoles.add(userRole1);
        roleSet.add(userRole1);
        // For setting user access location data
        Set<UserAccessLocation> userAccessLocations=new HashSet<>();

        UserAccessLocation userAccessLocation=new UserAccessLocation();
        userAccessLocation.setUalLocation(20L);
        userAccessLocations.add(userAccessLocation);
        locationSet.add(userAccessLocation);

        UserAccessLocation userAccessLocation1=new UserAccessLocation();
        userAccessLocation1.setUalLocation(21L);
        userAccessLocations.add(userAccessLocation1);
        locationSet.add(userAccessLocation1);

        // For setting user access location data
        Set<UserAccessRight> userAccessRights=new HashSet<>();

        UserAccessRight userAccessRight=new UserAccessRight();
        userAccessRight.setUarFunctionCode(30L);
        userAccessRights.add(userAccessRight);
        accessRightsSet.add(userAccessRight);

        UserAccessRight userAccessRight1=new UserAccessRight();
        userAccessRight1.setUarFunctionCode(31L);
        userAccessRights.add(userAccessRight1);
        accessRightsSet.add(userAccessRight1);

        User user = UserFixture.standardUser();
        user.setUserAccessLocationSet(userAccessLocations);
        user.setUserAccessRightsSet(userAccessRights);
        user.setUserRoleSet(userRoles);

        user = userRepository.save(user);

        user = userRepository.findByUsrLoginIdAndUsrUserType(user.getUsrLoginId(),user.getUsrUserType());
        userRoles = user.getUserRoleSet();
        userAccessLocations = user.getUserAccessLocationSet();
        userAccessRights = user.getUserAccessRightsSet();

        for(UserRole userRole2 : userRoles){
            if(userRole2.getUerRole() == userRole.getUerRole()){

                userRoles.remove(userRole2);

            }
        }

        UserRole userRole3 = new UserRole();
        userRole3.setUerRole(111L);
        userRoles.add(userRole3);

        user.setUserRoleSet(userRoles);

    }



    @Test
    public void test3FindByUsrUserNo() {

        // Get the standard user
        User user = UserFixture.standardUser();
        user = userRepository.save(user);


        User searchUser = userRepository.findByUsrUserNo(user.getUsrUserNo());
        Assert.assertNotNull(searchUser);
        log.info("User information " + user.toString());

    }


    @Test
    public void test3FindByUsrLoginId() {

        // Get the standard user
        User user = UserFixture.standardUser();
        user = userRepository.save(user);


        User searchUser = userRepository.findByUsrLoginId(user.getUsrLoginId());
        Assert.assertNotNull(searchUser);
        log.info("User information " + user.toString());

    }

    @Test
    public void test4FindByUsrMerchantNo() {

        Set<User> userSet = UserFixture.standardUsers();
        userRepository.save(userSet);

        // Get the standard user
        User user = UserFixture.standardUser();

        // Get the page
        Page<User> userPage = userRepository.findByUsrMerchantNo(user.getUsrMerchantNo(), 0, constructPageSpecification(0));
        Assert.assertNotNull(userPage);
        Assert.assertTrue(userPage.hasContent());

        List<User>  userList = Lists.newArrayList((Iterable<User>) userPage);
        log.info("User information " + userList.toString());

    }



    @Test
    public void test5FindByUsrLoginIdAndUsrUserType() {

        // Get the standard user
        User user = UserFixture.standardUser();
        user = userRepository.save(user);


        User searchUser = userRepository.findByUsrLoginIdAndUsrUserType(user.getUsrLoginId(), user.getUsrUserType());
        Assert.assertNotNull(searchUser);
        log.info("User information " + user.toString());

    }


    @Test
    public void test6FindByUsrMerchantNoAndUsrFNameLike() {

        Set<User> userSet = UserFixture.standardUsers();
        userRepository.save(userSet); 
        // Get the standard User
        User user = UserFixture.standardUser();

        // Get the page
        Page<User> userPage = userRepository.findByUsrMerchantNoAndUsrFNameLike(user.getUsrMerchantNo(), 0, "%demo%",constructPageSpecification(0));
        Assert.assertNotNull(userPage);
        Assert.assertTrue(userPage.hasContent());

        List<User>  userList = Lists.newArrayList((Iterable<User>) userPage);
        log.info("User information " + userList.toString());


    }


    @Test
    public void test7FindByUsrMerchantNoAndUsrLoginIdLike() {

        Set<User> userSet = UserFixture.standardUsers();
        userRepository.save(userSet);
        // Get the standard User
        User user = UserFixture.standardUser();

        // Get the page
        Page<User> userPage = userRepository.findByUsrMerchantNoAndUsrLoginIdLike(user.getUsrMerchantNo(), 0, "%test%", constructPageSpecification(0));
        Assert.assertNotNull(userPage);
        Assert.assertTrue(userPage.hasContent());

        List<User>  userList = Lists.newArrayList((Iterable<User>) userPage);
        log.info("User information " + userList.toString());


    }

    @Test
    public void test8FindByUsrMerchantNoTypeAndUsrLoginIdLike() {

        User user = UserFixture.standardUser();
        user.setUsrUserType(UserType.REDEMPTION_MERCHANT_USER);
        userRepository.save(user);
        // Get the standard User

        // Get the page
        Page<User> userPage = userRepository.findByUsrMerchantNoAndUsrUserTypeAndUsrLoginIdLike(user.getUsrMerchantNo(), 6, "%test%", constructPageSpecification(0));
        Assert.assertNotNull(userPage);
        Assert.assertTrue(userPage.hasContent());

        List<User>  userList = Lists.newArrayList((Iterable<User>) userPage);
        log.info("User information " + userList.toString());


    }

    @Test
    public void test9findByUsrUserTypeAndUsrThirdPartyVendorNo() {

        // Get the standard user
        User user = UserFixture.standardUser();
        user = userRepository.save(user);



        List<User> userList = userRepository.findByUsrUserTypeAndUsrThirdPartyVendorNo(UserType.REDEMPTION_MERCHANT_USER,user.getUsrThirdPartyVendorNo());
        Assert.assertNotNull(userList);
        log.info("User information " + userList.toString());

    }

    @Test
    public void test10FindByUsrUserCode() {

        // Get the standard user
        User user = UserFixture.standardUser();
        user.setUsrUserCode("CXY0ut");
        user = userRepository.save(user);


        User searchUser = userRepository.findByUsrUserCode(user.getUsrUserCode());
        Assert.assertNotNull(searchUser);
        log.info("User information " + user.toString());

    }


    @After
    public void tearDown() {

        Set<User> users = UserFixture.standardUsers();

        for(User user: users) {

            User delUser = userRepository.findByUsrLoginId(user.getUsrLoginId());

            if ( delUser != null ) {
                userRepository.delete(delUser);
            }

        }

        for(User user : tempSet ) {

            userRepository.delete(user);

        }
        for(UserRole userRole : roleSet ) {

            userRoleRepository.delete(userRole);

        }
        for(UserAccessLocation userAccessLocation : locationSet ) {

            userAccessLocationRepository.delete(userAccessLocation);

        }
        for(UserAccessRight userAccessRight : accessRightsSet ) {

            userAccessRightRepository.delete(userAccessRight);

        }
    }



    /**
     * Returns a new object which specifies the the wanted result page.
     * @param pageIndex The index of the wanted result page
     * @return
     */
    private Pageable constructPageSpecification(int pageIndex) {
        Pageable pageSpecification = new PageRequest(pageIndex, 10, sortByLastNameAsc());
        return pageSpecification;
    }

    /**
     * Returns a Sort object which sorts persons in ascending order by using the last name.
     * @return
     */
    private Sort sortByLastNameAsc() {
        return new Sort(Sort.Direction.ASC, "usrLoginId");
    }

}
