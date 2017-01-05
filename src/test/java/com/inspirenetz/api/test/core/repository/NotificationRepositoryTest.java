package com.inspirenetz.api.test.core.repository;

import com.google.common.collect.Lists;
import com.inspirenetz.api.core.dictionary.NotificationRecepientType;
import com.inspirenetz.api.core.dictionary.NotificationStatus;
import com.inspirenetz.api.core.dictionary.NotificationType;
import com.inspirenetz.api.core.domain.Notification;
import com.inspirenetz.api.core.repository.NotificationRepository;
import com.inspirenetz.api.test.config.ApplicationTestConfig;
import com.inspirenetz.api.test.config.PersistenceTestConfig;
import com.inspirenetz.api.test.core.fixture.NotificationFixture;
import com.inspirenetz.api.util.integration.IntegrationUtils;
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
 * Created by sandheepgr on 17/5/14.
 */
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes  = {ApplicationTestConfig.class, PersistenceTestConfig.class})
public class NotificationRepositoryTest {


    //  Create the logger
    private static Logger log = LoggerFactory.getLogger(NotificationRepositoryTest.class);

    @Autowired
    private NotificationRepository notificationRepository;


    @Before
    public void setUp() throws Exception {


    }




    @Test
    public void test1Create(){


        Notification notification = notificationRepository.save(NotificationFixture.standardNotification());
        log.info(notification.toString());
        Assert.assertNotNull(notification.getNtfNotificationId());

    }


    @Test
    public void testFindByNtfNotificationId() throws Exception {

        // Create the prodcut
        Notification notification = NotificationFixture.standardNotification();
        notification = notificationRepository.save(notification);

        Notification searchNotification = notificationRepository.findByNtfNotificationId(notification.getNtfNotificationId());
        Assert.assertNotNull(searchNotification);
        log.info("Notification Information" + searchNotification.toString());
    }

    @Test
    public void testFindByNtfRecepientTypeAndNtfRecepient() throws Exception {

        Set<Notification> notifications = NotificationFixture.standardNotifications();
        notificationRepository.save(notifications);

        Notification notification = NotificationFixture.standardNotification();

        Page<Notification>  notificationPage = notificationRepository.findByNtfRecepientTypeAndNtfRecepient(notification.getNtfRecepientType(), notification.getNtfRecepient(), constructPageSpecification(0));
        Assert.assertTrue(notificationPage.hasContent());
        List<Notification> notificationList = Lists.newArrayList((Iterable<Notification>)notificationPage);
        log.info("Notification List" + notificationList);

    }

    @Test
    public void testFindByNtfRecepientTypeAndNtfRecepientAndNtfStatus() throws Exception {

        // Create the prodcut
        Notification notification = NotificationFixture.standardNotification();
        notificationRepository.save(notification);

        Page<Notification>  notificationPage = notificationRepository.findByNtfRecepientTypeAndNtfRecepientAndNtfStatus(notification.getNtfRecepientType(),notification.getNtfRecepient(),notification.getNtfStatus(),constructPageSpecification(0));
        Assert.assertTrue(notificationPage.hasContent());
        List<Notification> notificationList = Lists.newArrayList((Iterable<Notification>)notificationPage);
        log.info("Notification List" + notificationList);

    }

    @Test
    public void testgetNotificationsForUser() throws Exception {

        // Create the nptification list

        List<Integer> ntfTypes=Lists.newArrayList(NotificationType.USER_ACTIVITY,NotificationType.CUSTOMER_ACTIVITY);
        List<Integer> ntfRecipientTypes=Lists.newArrayList(NotificationRecepientType.MERCHANT);

        List<Notification> notificationList = notificationRepository.getNotificationsForUser( ntfRecipientTypes,ntfTypes,NotificationRecepientType.USER,1l, NotificationStatus.NEW);

        Assert.assertNotNull(notificationList);
        log.info("Notification List" + notificationList);

    }

    @Test
    public void testgetNotificationsForUserAndMerchant() throws Exception {

        // Create the nptification list

        List<Integer> ntfTypes=Lists.newArrayList(NotificationType.USER_ACTIVITY,NotificationType.CUSTOMER_ACTIVITY);
        List<Integer> ntfRecipientTypes=Lists.newArrayList(NotificationRecepientType.USER);

        List<Notification> notificationList = notificationRepository.getNotificationsForUserAndMerchant( ntfRecipientTypes,ntfTypes,NotificationRecepientType.USER,1l,NotificationRecepientType.MERCHANT,1l, NotificationStatus.NEW);

        Assert.assertNotNull(notificationList);
        log.info("Notification List" + notificationList);

    }

    @After
    public void tearDown() throws Exception {

//        notificationRepository.deleteAll();

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
}
