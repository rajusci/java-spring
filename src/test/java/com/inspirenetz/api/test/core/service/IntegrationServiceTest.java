package com.inspirenetz.api.test.core.service;

import com.inspirenetz.api.core.domain.User;
import com.inspirenetz.api.core.repository.BrandRepository;
import com.inspirenetz.api.core.service.IntegrationService;
import com.inspirenetz.api.core.service.UserService;
import com.inspirenetz.api.test.config.ApplicationTestConfig;
import com.inspirenetz.api.test.config.IntegrationTestConfig;
import com.inspirenetz.api.test.config.PersistenceTestConfig;
import com.inspirenetz.api.test.config.ServiceTestConfig;
import com.inspirenetz.api.test.core.fixture.UserFixture;
import org.junit.*;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.io.IOException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * Created by sandheepgr on 30/4/14.
 */
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes  = {ApplicationTestConfig.class, PersistenceTestConfig.class, ServiceTestConfig.class,IntegrationTestConfig.class})
public class IntegrationServiceTest {



    private static Logger log = LoggerFactory.getLogger(IntegrationServiceTest.class);

    @Autowired
    private IntegrationService integrationService;

    @Autowired
    private BrandRepository brandRepository;

    @Autowired
    private Environment env;

    @Autowired
    UserService userService;

    Set<User> tempUserSet =new HashSet<>();


    @Before
    public void setUp() {}


    @Test
    public void testRestGet() throws IOException {

        String url = env.getProperty("integration.customer.validateaccount");
        Map<String,String> pathVariables = new HashMap<>(0);
        pathVariables.put("loyaltyid","100010");
        Map response = integrationService.placeRestGetAPICall(url, pathVariables);
        log.info("Response : "+response);



    }


    @Test
    public void testRestPost() {

        String url = env.getProperty("integration.messaging.sendsms");
        Map<String,String> postParams = new HashMap<>(0);
        postParams.put("mobile","9538828853");
        postParams.put("message","this is a test");
        Map response = integrationService.placeRestPostAPICall(url,postParams);
        log.info("REsponse : " + response);

    }

    @Test
    public void  jasperUser(){

        User user = UserFixture.standardUser();
        user.setUsrMerchantNo(3L);
        user.setUsrLoginId("nagesh1");
        userService.saveUser(user);
        tempUserSet.add(user);
        integrationService.integrateJasperServerUserCreation(user,3L);
    }

    @After
    public void tearDown() {


    }

}
