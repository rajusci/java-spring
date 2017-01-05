package com.inspirenetz.api.test.core.service;

import com.google.common.collect.Sets;
import com.inspirenetz.api.core.auth.AuthSession;
import com.inspirenetz.api.core.domain.Function;
import com.inspirenetz.api.core.domain.UserAccessRight;
import com.inspirenetz.api.core.repository.UserAccessRightRepository;
import com.inspirenetz.api.core.service.FunctionService;
import com.inspirenetz.api.core.service.UserAccessRightService;
import com.inspirenetz.api.rest.exception.InspireNetzException;
import com.inspirenetz.api.test.config.*;
import com.inspirenetz.api.test.core.fixture.FunctionFixture;
import com.inspirenetz.api.test.core.fixture.UserAccessRightFixture;
import com.inspirenetz.api.test.util.ControllerTestUtils;
import com.inspirenetz.api.util.AuthSessionUtils;
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

import java.util.HashMap;
import java.util.List;
import java.util.Set;

/**
 * Created by sandheepgr on 30/4/14.
 */
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes  = {ApplicationTestConfig.class, PersistenceTestConfig.class, ServiceTestConfig.class, SecurityTestConfig.class, NotificationTestConfig.class})
public class UserAccessRightServiceTest {


    private static Logger log = LoggerFactory.getLogger(UserAccessRightServiceTest.class);

    @Autowired
    private UserAccessRightService userAccessRightService;

    @Autowired
    private FunctionService functionService;

    UsernamePasswordAuthenticationToken principal;

    @Autowired
    AuthSessionUtils authSessionUtils;

    @Autowired
    @Qualifier("uds")
    protected UserDetailsService userDetailsService;

    @Before
    public void setUp() {

        // Set the principal
        principal = ControllerTestUtils.getPrincipal(ControllerTestUtils.TEST_MERCHANT_USER_LOGINID, userDetailsService);

        // Set the context
        SecurityContextHolder.getContext().setAuthentication(principal);
    }


    @Test
    public void test1Create(){


        UserAccessRight userAccessRight = userAccessRightService.saveUserAccessRight(UserAccessRightFixture.standardUserAccessRight());
        log.info(userAccessRight.toString());
        Assert.assertNotNull(userAccessRight.getUarUarId());

    }

    @Test
    public void test2Update() {

        UserAccessRight userAccessRight = UserAccessRightFixture.standardUserAccessRight();
        userAccessRight = userAccessRightService.saveUserAccessRight(userAccessRight);
        log.info("Original UserAccessRight " + userAccessRight.toString());

        UserAccessRight updatedUserAccessRight = UserAccessRightFixture.updatedStandardUserAccessRight(userAccessRight);
        updatedUserAccessRight = userAccessRightService.saveUserAccessRight(updatedUserAccessRight);
        log.info("Updated UserAccessRight "+ updatedUserAccessRight.toString());

    }



    @Test
    public void test3FindByUarUserNo() {

        // Get the standard userAccessRight
        UserAccessRight userAccessRight = UserAccessRightFixture.standardUserAccessRight();
        userAccessRight = userAccessRightService.saveUserAccessRight(userAccessRight);

        List<UserAccessRight> userAccessRightList = userAccessRightService.findByUarUserNo(userAccessRight.getUarUserNo());
        Assert.assertNotNull(userAccessRightList);
        log.info("userAccessRight list "+userAccessRightList.toString());

    }



    @Test
    public void test4FindByUarUarId() {

        // Create the userAccessRight
        UserAccessRight userAccessRight = UserAccessRightFixture.standardUserAccessRight();
        userAccessRightService.saveUserAccessRight(userAccessRight);
        Assert.assertNotNull(userAccessRight.getUarUarId());
        log.info("UserAccessRight created");

        UserAccessRight fetchUserAccessRight = userAccessRightService.findByUarUarId(userAccessRight.getUarUarId());
        Assert.assertNotNull(fetchUserAccessRight);
        log.info("Fetched userAccessRight info" + userAccessRight.toString());

    }



    @Test
    public void test4FindByUarUserNoAndUarFunctionCode() {

        // Create the userAccessRight
        UserAccessRight userAccessRight = UserAccessRightFixture.standardUserAccessRight();
        userAccessRightService.saveUserAccessRight(userAccessRight);
        Assert.assertNotNull(userAccessRight.getUarUarId());
        log.info("UserAccessRight created");

        UserAccessRight fetchUserAccessRight = userAccessRightService.findByUarUserNoAndUarFunctionCode(userAccessRight.getUarUserNo(),userAccessRight.getUarFunctionCode());
        Assert.assertNotNull(fetchUserAccessRight);
        log.info("Fetched userAccessRight info" + userAccessRight.toString());

    }



    @Test
    public void test4IsUserAccessRightCodeExisting() {

        // Create the userAccessRight
        UserAccessRight userAccessRight = UserAccessRightFixture.standardUserAccessRight();
        userAccessRight = userAccessRightService.saveUserAccessRight(userAccessRight);
        Assert.assertNotNull(userAccessRight.getUarUarId());
        log.info("UserAccessRight created");

        // Create a new userAccessRight
        UserAccessRight newUserAccessRight = UserAccessRightFixture.standardUserAccessRight();
        boolean exists = userAccessRightService.isDuplicateUserAccessRightExisting(newUserAccessRight);
        Assert.assertTrue(exists);
        log.info("UserAccessRight exists");


    }


    @Test
    public void test5DeleteUserAccessRight() {

        // Create the userAccessRight
        UserAccessRight userAccessRight = UserAccessRightFixture.standardUserAccessRight();
        userAccessRight = userAccessRightService.saveUserAccessRight(userAccessRight);
        Assert.assertNotNull(userAccessRight.getUarUarId());
        log.info("UserAccessRight created");

        // call the delete userAccessRight
        userAccessRightService.deleteUserAccessRight(userAccessRight.getUarUarId());

        // Try searching for the userAccessRight
        UserAccessRight checkUserAccessRight  = userAccessRightService.findByUarUserNoAndUarFunctionCode(userAccessRight.getUarUserNo(),userAccessRight.getUarFunctionCode());

        Assert.assertNull(checkUserAccessRight);

        log.info("userAccessRight deleted");

    }


    @Test
    public void test6GetUarAsMap() throws InspireNetzException {

        // Create the function
        Function function = FunctionFixture.standardFunction();
        function = functionService.saveFunction(function);

        // Create the userAccessRight
        UserAccessRight userAccessRight = UserAccessRightFixture.standardUserAccessRight();
        userAccessRight.setUarFunctionCode(function.getFncFunctionCode());
        userAccessRight = userAccessRightService.saveUserAccessRight(userAccessRight);
        Assert.assertNotNull(userAccessRight.getUarUarId());
        log.info("UserAccessRight created");


        // Get the HashMap
        HashMap<Long,String> uarMap =  userAccessRightService.getUarAsMap(userAccessRight.getUarUserNo());
        Assert.assertTrue(!uarMap.isEmpty());
        log.info("UAR Map " + uarMap.toString());

    }

    @Test
    public void testIsFunctionAccessAllowed() throws InspireNetzException {

        boolean isAccessAllowed = authSessionUtils.isFunctionAccessAllowed(47L);

        Assert.assertTrue(isAccessAllowed);

    }




    @After
    public void tearDown() {

        Set<Function> functions = FunctionFixture.standardFunctions();

        for(Function function: functions) {

            Function delFunction = functionService.findByFncFunctionName(function.getFncFunctionName());

            if ( delFunction != null ) {
                functionService.deleteFunction(delFunction.getFncFunctionCode());
            }

        }


        Set<UserAccessRight> userAccessRights = UserAccessRightFixture.standardUserAccessRights();

        for(UserAccessRight userAccessRight: userAccessRights) {

            UserAccessRight delUserAccessRight = userAccessRightService.findByUarUserNoAndUarFunctionCode(userAccessRight.getUarUserNo(),userAccessRight.getUarFunctionCode());

            if ( delUserAccessRight != null ) {
                userAccessRightService.deleteUserAccessRight(delUserAccessRight.getUarUarId());
            }

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
        return new Sort(Sort.Direction.ASC, "uarName");
    }

}
