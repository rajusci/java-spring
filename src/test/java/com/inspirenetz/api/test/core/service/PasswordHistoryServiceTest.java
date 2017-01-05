package com.inspirenetz.api.test.core.service;

import com.google.common.collect.Lists;
import com.inspirenetz.api.core.domain.*;
import com.inspirenetz.api.core.service.*;
import com.inspirenetz.api.rest.exception.InspireNetzException;
import com.inspirenetz.api.test.config.*;
import com.inspirenetz.api.test.core.fixture.CustomerFixture;
import com.inspirenetz.api.test.core.fixture.PasswordHistoryFixture;
import com.inspirenetz.api.test.core.fixture.LinkedLoyaltyFixture;
import com.inspirenetz.api.test.core.fixture.UserFixture;
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

import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Created by sandheepgr on 30/4/14.
 */
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes  = {ApplicationTestConfig.class, PersistenceTestConfig.class, ServiceTestConfig.class, SecurityTestConfig.class, NotificationTestConfig.class})
public class PasswordHistoryServiceTest {


    private static Logger log = LoggerFactory.getLogger(PasswordHistoryServiceTest.class);

    @Autowired
    private PasswordHistoryService passwordHistoryService;

    @Autowired
    private SecuritySettingService securitySettingService;

    @Autowired
    private UserService userService;

    UsernamePasswordAuthenticationToken principal;

    @Autowired
    @Qualifier("uds")
    protected UserDetailsService userDetailsService;



    Set<PasswordHistory> tempSet = new HashSet<>(0);


    @Before
    public void setUp() {

        // Set the principal
        principal = ControllerTestUtils.getPrincipal(ControllerTestUtils.TEST_MERCHANT_USER_LOGINID, userDetailsService);

        // Set the context
        SecurityContextHolder.getContext().setAuthentication(principal);
    }


    @Test
    public void test1Create(){


        PasswordHistory passwordHistory = PasswordHistoryFixture.standardPasswordHistory();
        passwordHistory = passwordHistoryService.savePasswordHistory(passwordHistory.getPasHistoryUserNo(), passwordHistory.getPasHistoryPassword());
        // Add the items
        tempSet.add(passwordHistory);

        Assert.assertNotNull(passwordHistory.getPasHistoryId());

    }

    @Test
    public void test2findByPasHistoryUserNo(){

        PasswordHistory passwordHistory = PasswordHistoryFixture.standardPasswordHistory();
        passwordHistory = passwordHistoryService.savePasswordHistory(passwordHistory.getPasHistoryUserNo(), passwordHistory.getPasHistoryPassword());

        List<PasswordHistory> passwordHistoryList=passwordHistoryService.findByPasHistoryUserNo(passwordHistory.getPasHistoryUserNo());

        Assert.assertNotNull(passwordHistoryList);

    }


    @Test
    public void test3findByMaxPasChangedAt(){

        PasswordHistory passwordHistory = PasswordHistoryFixture.standardPasswordHistory();
        passwordHistory = passwordHistoryService.savePasswordHistory(passwordHistory.getPasHistoryUserNo(), passwordHistory.getPasHistoryPassword());

        Date date=passwordHistoryService.findByMaxPasChangedAt(passwordHistory.getPasHistoryUserNo());

        Assert.assertNotNull(date);

    }


    @Test
    public void test4findByLastChangedAtDate() throws InspireNetzException {

        User user = UserFixture.standardUser();
        user = userService.saveUser(user);

        User user1 = UserFixture.updatedStandardUser(user);

        SecuritySetting securitySetting=securitySettingService.getSecuritySetting();

        boolean passHistory = passwordHistoryService.matchedPreviousHistory(securitySetting.getSecPwdPasHistory(),user1.getUsrPassword(),user1.getUsrUserNo());

        if(passHistory == true){

            log.info("Pass History checking"+passHistory);
        }



    }


    @After
    public void tearDown() {

            for(PasswordHistory passwordHistory:tempSet){

                passwordHistoryService.delete(passwordHistory.getPasHistoryId());
            }



    }


}
