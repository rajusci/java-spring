package com.inspirenetz.api.test.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.collect.Lists;
import com.inspirenetz.api.core.dictionary.NotificationStatus;
import com.inspirenetz.api.core.dictionary.UserType;
import com.inspirenetz.api.core.domain.Customer;
import com.inspirenetz.api.core.domain.CustomerActivity;
import com.inspirenetz.api.core.domain.Notification;
import com.inspirenetz.api.core.service.CustomerActivityService;
import com.inspirenetz.api.core.service.CustomerService;
import com.inspirenetz.api.core.service.NotificationService;
import com.inspirenetz.api.rest.exception.InspireNetzException;
import com.inspirenetz.api.test.config.*;
import com.inspirenetz.api.test.core.fixture.CustomerActivityFixture;
import com.inspirenetz.api.test.core.fixture.NotificationFixture;
import com.inspirenetz.api.test.util.ControllerTestUtils;
import com.inspirenetz.api.util.DBUtils;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.FilterChainProxy;
import org.springframework.security.web.context.HttpSessionSecurityContextRepository;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import javax.annotation.Resource;
import java.util.*;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

/**
 * Created by fayizkci on 17/3/15.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration(classes  = {ApplicationTestConfig.class, PersistenceTestConfig.class, ServiceTestConfig.class, SecurityTestConfig.class, WebTestConfig.class, NotificationTestConfig.class})
public class NotificationControllerTest {


    private static Logger log = LoggerFactory.getLogger(NotificationControllerTest.class);

    private static MockMvc mockMvc;

    private static ObjectMapper mapper;

    @Autowired
    private NotificationService notificationService;

    @Autowired
    private WebApplicationContext wac;

    @Resource
    private FilterChainProxy springSecurityFilterChain;

    @Autowired
    @Qualifier("uds")
    protected UserDetailsService userDetailsService;



    UsernamePasswordAuthenticationToken principal;

    MockHttpSession session;

    // Map to hold the returned json
    Map<String,String> map = new HashMap<>();

    Set<Notification> notificationSet = new HashSet<>(0);

    @Before
    public void setUp()
    {
        try {

            // Set the principal
            principal = ControllerTestUtils.getPrincipal(ControllerTestUtils.TEST_MERCHANT_ADMIN_LOGINID,userDetailsService);

            // Create the Session
            session = new MockHttpSession();


            session.setAttribute(
                    HttpSessionSecurityContextRepository.SPRING_SECURITY_CONTEXT_KEY,
                    new ControllerTestUtils.MockSecurityContext(principal));

            //mockMvc = webAppContextSetup(this.wac).build();
            this.mockMvc = MockMvcBuilders
                    .webAppContextSetup(this.wac)
                    .addFilters(this.springSecurityFilterChain)
                    .build();

            init();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    private void useCustomerSession() {

        try {

            // Set the principal
            principal = ControllerTestUtils.getPrincipal(ControllerTestUtils.TEST_CUSTOMER_LOGINID,userDetailsService);

            // Create the Session
            session = new MockHttpSession();


            session.setAttribute(
                    HttpSessionSecurityContextRepository.SPRING_SECURITY_CONTEXT_KEY,
                    new ControllerTestUtils.MockSecurityContext(principal));

            //mockMvc = webAppContextSetup(this.wac).build();
            this.mockMvc = MockMvcBuilders
                    .webAppContextSetup(this.wac)
                    .addFilters(this.springSecurityFilterChain)
                    .build();

            init();

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    private void init() throws Exception{


        // Create the Mapper object
        mapper = new ObjectMapper();


    }

    @Test
    public void getNotifications() throws Exception  {

        useCustomerSession();

        //Add the data
        Set<Notification> notifications = NotificationFixture.standardNotifications();

        List<Notification> notifications1 = Lists.newArrayList((Iterable<Notification>) notifications);

        notificationService.saveAll(notifications1);

        notificationSet.addAll(notifications1);

        Notification notification = NotificationFixture.standardNotification();

        // Place the get notifications request
        MvcResult mvcResult = mockMvc.perform(get("/api/0.9/json/merchant/customer/notifications")
                .principal(principal)
                .session(session)
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .param("ntfType",  notification.getNtfType()+"")
                .param("merchantNo", "0")
                .param("ntfStatus", NotificationStatus.NEW+"")
        )
                .andReturn();

        String response = mvcResult.getResponse().getContentAsString();

        Assert.assertNotNull(response);

        log.info("getNotifications Response: " + response);

        // Convert json response to HashMap
        Assert.assertTrue(response.contains("success"));


    }

    @Test
    public void getNotificationCount() throws Exception  {

        useCustomerSession();

        //Add the data
        Set<Notification> notifications = NotificationFixture.standardNotifications();

        List<Notification> notifications1 = Lists.newArrayList((Iterable<Notification>) notifications);

        notificationService.saveAll(notifications1);

        notificationSet.addAll(notifications1);

        Notification notification = NotificationFixture.standardNotification();

        // Place the notification count request
        MvcResult mvcResult = mockMvc.perform(get("/api/0.9/json/merchant/customer/notifications/count")
                .principal(principal)
                .session(session)
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .param("userType", UserType.CUSTOMER + "")
                .param("userNo", notification.getNtfRecepient().toString())
                .param("ntfType", notification.getNtfType() + "")
                .param("merchantNo", "0")
                .param("ntfStatus", NotificationStatus.NEW+"")
        )
                .andReturn();

        String response = mvcResult.getResponse().getContentAsString();

        Assert.assertNotNull(response);

        log.info("getNotifications Response: " + response);

        // Convert json response to HashMap
        Assert.assertTrue(response.contains("success"));


    }


    @After
    public void tearDown() throws InspireNetzException {



        for(Notification notification: notificationSet) {


            notificationService.deleteNotification(notification.getNtfNotificationId());

        }

    }

}