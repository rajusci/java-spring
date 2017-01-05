package com.inspirenetz.api.test.core.service;

import com.inspirenetz.api.config.SecurityConfig;
import com.inspirenetz.api.core.domain.PasswordHistory;
import com.inspirenetz.api.core.domain.SecuritySetting;
import com.inspirenetz.api.core.domain.User;
import com.inspirenetz.api.core.service.PasswordHistoryService;
import com.inspirenetz.api.core.service.SecuritySettingService;
import com.inspirenetz.api.core.service.UserService;
import com.inspirenetz.api.rest.exception.InspireNetzException;
import com.inspirenetz.api.test.config.*;
import com.inspirenetz.api.test.core.fixture.PasswordHistoryFixture;
import com.inspirenetz.api.test.core.fixture.SecuritySettingFixture;
import com.inspirenetz.api.test.core.fixture.UserFixture;
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

import javax.validation.constraints.Null;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Created by ameenci on 10/9/14.
 */
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes  = {ApplicationTestConfig.class, PersistenceTestConfig.class, ServiceTestConfig.class, SecurityTestConfig.class, NotificationTestConfig.class})
public class SecuritySettingServiceTest {

    private static Logger log = LoggerFactory.getLogger(SecuritySettingServiceTest.class);

    Set<SecuritySetting> tempSet = new HashSet<>(0);

    @Autowired
    private SecuritySettingService securitySettingService;

    UsernamePasswordAuthenticationToken principal;
    @Autowired
    @Qualifier("uds")
    protected UserDetailsService userDetailsService;

    @Autowired
    PasswordHistoryService passwordHistoryService;

    @Autowired
    UserService userService;

    @Before
    public void setup() {

        // Set the principal
        principal = ControllerTestUtils.getPrincipal(ControllerTestUtils.TEST_MERCHANT_ADMIN_LOGINID, userDetailsService);

        // Set the context
        SecurityContextHolder.getContext().setAuthentication(principal);
    }

    @Test
    public void test1Create(){


        SecuritySetting securitySetting = securitySettingService.saveSecuritySetting(SecuritySettingFixture.standardSecuritySetting());
        log.info(securitySetting.toString());
        Assert.assertNotNull(securitySetting.getSecId());

        // Add to tempSet
        tempSet.add(securitySetting);

    }

    @Test
    public void test2Update() throws InspireNetzException {

        SecuritySetting securitySetting = SecuritySettingFixture.standardSecuritySetting();
        securitySetting = securitySettingService.saveSecuritySetting(securitySetting);
        log.info("Original role access right " + securitySetting.toString());

        // Add to tempSet
        tempSet.add(securitySetting);

        SecuritySetting updateSecuritySetting = SecuritySettingFixture.updatedStandSecuritySetting(securitySetting);
        updateSecuritySetting = securitySettingService.validateAndSaveSecuritySetting(updateSecuritySetting);
        log.info("Updated SecuritySetting "+ updateSecuritySetting.toString());

    }

    @Test
    public void test3findBySecId() {

        // Get the standard
        SecuritySetting securitySetting = SecuritySettingFixture.standardSecuritySetting();
        securitySetting = securitySettingService.saveSecuritySetting(securitySetting);
        log.info("Original role acces rights  " + securitySetting.toString());

        // Add to tempSet
        tempSet.add(securitySetting);

        Assert.assertNotNull(securitySetting.getSecId());

        SecuritySetting securitySettingById = securitySettingService.findBySecId(securitySetting.getSecId());
        Assert.assertNotNull(securitySettingById);
        log.info("Fetched SecuritySettingById info" + securitySettingById.toString());


    }

    @Test

    public void test4IsPasswordValid() throws InspireNetzException {

      //getting user information
      User user = UserFixture.standardUser();

      boolean passValid = securitySettingService.isPasswordValid(user,user.getUsrPassword());

      if(passValid == true){

            log.info("Password is valid with the security settings");
      }

      user = userService.saveUser(user);

      user.setUsrPassword(null);

      user.setUsrLName("Hari");

      user = userService.saveUser(user);

     /* PasswordHistory passwordHistory = PasswordHistoryFixture.standardPasswordHistory();
      passwordHistory.setPasHistoryUserNo(user.getUsrUserNo());
      passwordHistoryService.savePasswordHistory(passwordHistory.getPasHistoryUserNo(),passwordHistory.getPasHistoryPassword());

      //creating security settings
      SecuritySetting securitySetting = securitySettingService.saveSecuritySetting(SecuritySettingFixture.standardSecuritySetting());
      log.info(securitySetting.toString());
      Assert.assertNotNull(securitySetting.getSecId());
*/





    }


    @org.junit.After
    public void tearDown() {

        for ( SecuritySetting securitySetting : tempSet ) {

            securitySettingService.deleteSecuritySetting(securitySetting);

        }
    }
}
