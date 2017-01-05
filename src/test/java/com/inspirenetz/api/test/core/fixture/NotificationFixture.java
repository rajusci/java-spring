package com.inspirenetz.api.test.core.fixture;

import com.inspirenetz.api.core.dictionary.NotificationRecepientType;
import com.inspirenetz.api.core.dictionary.NotificationType;
import com.inspirenetz.api.core.domain.Notification;
import com.inspirenetz.api.test.core.builder.NotificationBuilder;

import java.util.HashSet;
import java.util.Set;

/**
 * Created by sandheepgr on 27/5/14.
 */
public class NotificationFixture {

    public static Notification standardNotification() {

        Notification notification = NotificationBuilder.aNotification()
                .withNtfType(NotificationType.CUSTOMER_ACTIVITY)
                .withNtfActivityUserNo(10L)
                .withNtfSourceName("Sandeep")
                .withNtfText("Sandeep has logged into the system")
                .withNtfRecepientType(NotificationRecepientType.USER)
                .withNtfRecepient(12L)
                .build();


        return notification;

    }



    public static Set<Notification> standardNotifications() {

        Set<Notification> notificationSet = new HashSet<>(0);

        Notification notification = NotificationBuilder.aNotification()
                .withNtfType(NotificationType.CUSTOMER_ACTIVITY)
                .withNtfActivityUserNo(10L)
                .withNtfSourceName("Sandeep")
                .withNtfText("Sandeep has logged into the system")
                .withNtfRecepientType(NotificationRecepientType.USER)
                .withNtfRecepient(12L)
                .build();

        notificationSet.add(notification);


        Notification notification2 = NotificationBuilder.aNotification()
                .withNtfType(NotificationType.CUSTOMER_ACTIVITY)
                .withNtfActivityUserNo(12L)
                .withNtfSourceName("John")
                .withNtfText("John has logged into the system")
                .withNtfRecepientType(NotificationRecepientType.USER)
                .withNtfRecepient(11L)
                .build();
        notificationSet.add(notification2);

        return notificationSet;
    }


}
