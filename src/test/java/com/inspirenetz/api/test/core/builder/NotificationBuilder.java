package com.inspirenetz.api.test.core.builder;

import com.inspirenetz.api.core.dictionary.ImagePrimaryId;
import com.inspirenetz.api.core.dictionary.NotificationRecepientType;
import com.inspirenetz.api.core.dictionary.NotificationStatus;
import com.inspirenetz.api.core.dictionary.NotificationType;
import com.inspirenetz.api.core.domain.Notification;

import java.sql.Timestamp;

/**
 * Created by sandheepgr on 27/5/14.
 */
public class NotificationBuilder {

    private Long ntfNotificationId;
    private Timestamp ntfTimestamp;
    private int ntfType = NotificationType.USER_ACTIVITY;
    private Long ntfFunctionId = 0l;
    private Long ntfActivityUserNo = 0l;
    private String ntfSourceName = "";
    private String ntfText = "";
    private int ntfRecepientType = NotificationRecepientType.ALL_USERS;
    private Long ntfRecepient = 0l;
    private Long ntfSourceImageId = ImagePrimaryId.PRIMARY_DEFAULT_IMAGE;
    private int ntfStatus = NotificationStatus.NEW;

    private NotificationBuilder() {
    }

    public static NotificationBuilder aNotification() {
        return new NotificationBuilder();
    }

    public NotificationBuilder withNtfNotificationId(Long ntfNotificationId) {
        this.ntfNotificationId = ntfNotificationId;
        return this;
    }

    public NotificationBuilder withNtfTimestamp(Timestamp ntfTimestamp) {
        this.ntfTimestamp = ntfTimestamp;
        return this;
    }

    public NotificationBuilder withNtfType(int ntfType) {
        this.ntfType = ntfType;
        return this;
    }

    public NotificationBuilder withNtfFunctionId(Long ntfFunctionId) {
        this.ntfFunctionId = ntfFunctionId;
        return this;
    }

    public NotificationBuilder withNtfActivityUserNo(Long ntfActivityUserNo) {
        this.ntfActivityUserNo = ntfActivityUserNo;
        return this;
    }

    public NotificationBuilder withNtfSourceName(String ntfSourceName) {
        this.ntfSourceName = ntfSourceName;
        return this;
    }

    public NotificationBuilder withNtfText(String ntfText) {
        this.ntfText = ntfText;
        return this;
    }

    public NotificationBuilder withNtfRecepientType(int ntfRecepientType) {
        this.ntfRecepientType = ntfRecepientType;
        return this;
    }
    public NotificationBuilder withNtfRecepient(Long ntfRecepient) {
        this.ntfRecepient = ntfRecepient;
        return this;
    }

    public NotificationBuilder withNtfSourceImageId(Long ntfSourceImageId) {
        this.ntfSourceImageId = ntfSourceImageId;
        return this;
    }

    public NotificationBuilder withNtfStatus(int ntfStatus) {
        this.ntfStatus = ntfStatus;
        return this;
    }

    public Notification build() {
        Notification notification = new Notification();
        notification.setNtfNotificationId(ntfNotificationId);
        notification.setNtfTimestamp(ntfTimestamp);
        notification.setNtfType(ntfType);
        notification.setNtfFunctionId(ntfFunctionId);
        notification.setNtfActivityUserNo(ntfActivityUserNo);
        notification.setNtfSourceName(ntfSourceName);
        notification.setNtfText(ntfText);
        notification.setNtfRecepientType(ntfRecepientType);
        notification.setNtfRecepient(ntfRecepient);
        notification.setNtfSourceImageId(ntfSourceImageId);
        notification.setNtfStatus(ntfStatus);

        return notification;
    }
}
