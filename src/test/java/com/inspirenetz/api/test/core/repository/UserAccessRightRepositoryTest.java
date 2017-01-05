package com.inspirenetz.api.test.core.repository;

import com.google.common.collect.Sets;
import com.inspirenetz.api.core.domain.UserAccessRight;
import com.inspirenetz.api.core.repository.UserAccessRightRepository;
import com.inspirenetz.api.test.config.ApplicationTestConfig;
import com.inspirenetz.api.test.config.PersistenceTestConfig;
import com.inspirenetz.api.test.core.fixture.UserAccessRightFixture;
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

import java.util.List;
import java.util.Set;

/**
 * Created by sandheepgr on 17/2/14.
 */
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes  = {ApplicationTestConfig.class, PersistenceTestConfig.class})
public class UserAccessRightRepositoryTest {

    private static Logger log = LoggerFactory.getLogger(UserAccessRightRepositoryTest.class);

    @Autowired
    private UserAccessRightRepository userAccessRightRepository;


    @Before
    public void setup() {}

    @Test
    public void test1Create(){


        UserAccessRight userAccessRight = userAccessRightRepository.save(UserAccessRightFixture.standardUserAccessRight());
        log.info(userAccessRight.toString());
        Assert.assertNotNull(userAccessRight.getUarUarId());

    }

    @Test
    public void test2Update() {

        UserAccessRight userAccessRight = UserAccessRightFixture.standardUserAccessRight();
        userAccessRight = userAccessRightRepository.save(userAccessRight);
        log.info("Original UserAccessRight " + userAccessRight.toString());

        UserAccessRight updatedUserAccessRight = UserAccessRightFixture.updatedStandardUserAccessRight(userAccessRight);
        updatedUserAccessRight = userAccessRightRepository.save(updatedUserAccessRight);
        log.info("Updated UserAccessRight "+ updatedUserAccessRight.toString());

    }



    @Test
    public void test3FindByUarUserNo() {

        // Get the standard userAccessRight
        UserAccessRight userAccessRight = UserAccessRightFixture.standardUserAccessRight();
        userAccessRight = userAccessRightRepository.save(userAccessRight);

        List<UserAccessRight> userAccessRightList = userAccessRightRepository.findByUarUserNo(userAccessRight.getUarUserNo());
        Assert.assertNotNull(userAccessRightList);
        log.info("userAccessRight list "+userAccessRightList.toString());

    }



    @Test
    public void test4FindByUarUarId() {

        // Create the userAccessRight
        UserAccessRight userAccessRight = UserAccessRightFixture.standardUserAccessRight();
        userAccessRightRepository.save(userAccessRight);
        Assert.assertNotNull(userAccessRight.getUarUarId());
        log.info("UserAccessRight created");

        UserAccessRight fetchUserAccessRight = userAccessRightRepository.findByUarUarId(userAccessRight.getUarUarId());
        Assert.assertNotNull(fetchUserAccessRight);
        log.info("Fetched userAccessRight info" + userAccessRight.toString());

    }



    @Test
    public void test4FindByUarUserNoAndUarFunctionCode() {

        // Create the userAccessRight
        UserAccessRight userAccessRight = UserAccessRightFixture.standardUserAccessRight();
        userAccessRightRepository.save(userAccessRight);
        Assert.assertNotNull(userAccessRight.getUarUarId());
        log.info("UserAccessRight created");

        UserAccessRight fetchUserAccessRight = userAccessRightRepository.findByUarUserNoAndUarFunctionCode(userAccessRight.getUarUserNo(),userAccessRight.getUarFunctionCode());
        Assert.assertNotNull(fetchUserAccessRight);
        log.info("Fetched userAccessRight info" + userAccessRight.toString());

    }


    @After
    public void tearDown() {

        Set<UserAccessRight> userAccessRights = UserAccessRightFixture.standardUserAccessRights();

        for(UserAccessRight userAccessRight: userAccessRights) {

            UserAccessRight delUserAccessRight = userAccessRightRepository.findByUarUserNoAndUarFunctionCode(userAccessRight.getUarUserNo(),userAccessRight.getUarFunctionCode());

            if ( delUserAccessRight != null ) {
                userAccessRightRepository.delete(delUserAccessRight);
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
