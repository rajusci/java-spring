package com.inspirenetz.api.test.core.service;

import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import com.inspirenetz.api.core.dictionary.NotificationStatus;
import com.inspirenetz.api.core.dictionary.UserType;
import com.inspirenetz.api.core.domain.Notification;
import com.inspirenetz.api.core.service.NotificationService;
import com.inspirenetz.api.test.config.ApplicationTestConfig;
import com.inspirenetz.api.test.config.PersistenceTestConfig;
import com.inspirenetz.api.test.config.ServiceTestConfig;
import com.inspirenetz.api.test.core.fixture.NotificationFixture;
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
 * Created by sandheepgr on 30/4/14.
 */
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes  = {ApplicationTestConfig.class, PersistenceTestConfig.class, ServiceTestConfig.class})
public class NotificationServiceTest {


    private static Logger log = LoggerFactory.getLogger(NotificationServiceTest.class);

    @Autowired
    private NotificationService notificationService;

    Set <Notification> notificationSet=new HashSet<>(0);

    @Before
    public void setUp() {}



    @Test
    public void test1Create(){


        Notification notification = notificationService.saveNotification(NotificationFixture.standardNotification());

        notificationSet.add(notification);

        log.info(notification.toString());
        Assert.assertNotNull(notification.getNtfNotificationId());

    }


    @Test
    public void testFindByNtfNotificationId() throws Exception {

        // Create the prodcut
        Notification notification = NotificationFixture.standardNotification();
        notification = notificationService.saveNotification(notification);

        notificationSet.add(notification);

        Notification searchNotification = notificationService.findByNtfNotificationId(notification.getNtfNotificationId());
        Assert.assertNotNull(searchNotification);
        log.info("Notification Information" + searchNotification.toString());
    }

    @Test
    public void testFindByNtfRecepientTypeAndNtfRecepient() throws Exception {

        Set<Notification> notifications = NotificationFixture.standardNotifications();
        List<Notification> notifications1 = Lists.newArrayList((Iterable<Notification>)notifications);
        notifications1=notificationService.saveAll(notifications1);

        notificationSet.addAll(notifications1);

        Notification notification = NotificationFixture.standardNotification();

        Page<Notification>  notificationPage = notificationService.findByNtfRecepientTypeAndNtfRecepient(notification.getNtfRecepientType(), notification.getNtfRecepient(), constructPageSpecification(0));
        Assert.assertTrue(notificationPage.hasContent());
        List<Notification> notificationList = Lists.newArrayList((Iterable<Notification>)notificationPage);
        log.info("Notification List" + notificationList);

    }

    @Test
    public void testFindByNtfRecepientTypeAndNtfRecepientAndNtfStatus() throws Exception {

        Set<Notification> notifications = NotificationFixture.standardNotifications();
        List<Notification> notifications1 = Lists.newArrayList((Iterable<Notification>)notifications);
        notificationService.saveAll(notifications1);

        notificationSet.addAll(notifications1);

        Notification notification = NotificationFixture.standardNotification();

        Page<Notification>  notificationPage = notificationService.findByNtfRecepientTypeAndNtfRecepientAndNtfStatus(notification.getNtfRecepientType(),notification.getNtfRecepient(),notification.getNtfStatus(),constructPageSpecification(0));
        Assert.assertTrue(notificationPage.hasContent());
        List<Notification> notificationList = Lists.newArrayList((Iterable<Notification>)notificationPage);
        log.info("Notification List" + notificationList);

    }
    @Test
    public void testgetNotifications() throws Exception {

        Set<Notification> notifications = NotificationFixture.standardNotifications();

        List<Notification> notifications1 = Lists.newArrayList((Iterable<Notification>)notifications);

        notificationService.saveAll(notifications1);

        notificationSet.addAll(notifications1);

        Notification notification = NotificationFixture.standardNotification();

        List<Notification>  notificationList = notificationService.getNotifications(UserType.CUSTOMER, notification.getNtfRecepient(),notification.getNtfType(),0L, NotificationStatus.NEW);

        Assert.assertNotNull(notificationList);

        log.info("Notification List " + notificationList);

    }
    @Test
    public void testgetNotificationsCount() throws Exception {

        Set<Notification> notifications = NotificationFixture.standardNotifications();

        List<Notification> notifications1 = Lists.newArrayList((Iterable<Notification>)notifications);

        notificationService.saveAll(notifications1);

        notificationSet.addAll(notifications1);

        Notification notification = NotificationFixture.standardNotification();

        Integer  notificationsCount = notificationService.getNotificationsCount(UserType.CUSTOMER, notification.getNtfRecepient(),notification.getNtfType(),0L, NotificationStatus.NEW);

        Assert.assertTrue(notificationsCount!=0);

        log.info("Notifications Count " + notificationsCount);

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
        return new Sort(Sort.Direction.ASC, "ntfNotificationId");
    }


    @After
    public void tearDown() throws Exception {

        for (Notification notification:notificationSet){

            notificationService.deleteNotification(notification.getNtfNotificationId());
        }
        
    }

}
