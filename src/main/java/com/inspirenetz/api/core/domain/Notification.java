package com.inspirenetz.api.core.domain;

import com.inspirenetz.api.core.dictionary.ImagePrimaryId;
import com.inspirenetz.api.core.dictionary.NotificationRecepientType;
import com.inspirenetz.api.core.dictionary.NotificationStatus;
import com.inspirenetz.api.core.dictionary.NotificationType;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Date;

/**
 * Created by sandheepgr on 27/5/14.
 */
@Entity
@Table(name = "NOTIFICATIONS")
public class Notification {

    @Id
    @Column(name = "NTF_NOTIFICATION_ID", nullable = false, insertable = true, updatable = true, length = 10, precision = 0)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long ntfNotificationId;

    @Basic
    @Column(name = "NTF_TIMESTAMP", nullable = false, insertable = true, updatable = true, length = 19, precision = 0)
    private Timestamp ntfTimestamp;

    @Basic
    @Column(name = "NTF_TYPE", nullable = false, insertable = true, updatable = true, length = 10, precision = 0)
    private int ntfType = NotificationType.USER_ACTIVITY;

    @Basic
    @Column(name = "NTF_FUNCTION_ID", nullable = false, insertable = true, updatable = true, length = 10, precision = 0)
    private Long ntfFunctionId = 0l;

    @Basic
    @Column(name = "NTF_ACTIVITY_USER_NO", nullable = false, insertable = true, updatable = true, length = 10, precision = 0)
    private Long ntfActivityUserNo = 0l;

    @Basic
    @Column(name = "NTF_SOURCE_NAME", nullable = false, insertable = true, updatable = true, length = 100, precision = 0)
    private String ntfSourceName = "";

    @Basic
    @Column(name = "NTF_TEXT", nullable = false, insertable = true, updatable = true, length = 250, precision = 0)
    private String ntfText = "";

    @Basic
    @Column(name = "NTF_RECEPIENT_TYPE", nullable = false, insertable = true, updatable = true, length = 10, precision = 0)
    private int ntfRecepientType = NotificationRecepientType.ALL_USERS;

    @Basic
    @Column(name = "NTF_RECEPIENT", nullable = true, insertable = true, updatable = true, length = 10, precision = 0)
    private Long ntfRecepient = 0L;

    @Basic
    @Column(name = "NTF_SOURCE_IMAGE_ID", nullable = false, insertable = true, updatable = true, length = 10, precision = 0)
    private Long ntfSourceImageId = ImagePrimaryId.PRIMARY_DEFAULT_IMAGE;

    @Basic
    @Column(name = "NTF_STATUS", nullable = false, insertable = true, updatable = true, length = 10, precision = 0)
    private int ntfStatus = NotificationStatus.NEW;



    @PrePersist
    private void prePersist() {

        // Get the date
        Date date = new Date();

        // Get the Timestamp
        Timestamp timestamp = new Timestamp(date.getTime());

        // Set the timestamp
        this.ntfTimestamp = timestamp;

    }


    public Long getNtfNotificationId() {
        return ntfNotificationId;
    }

    public void setNtfNotificationId(Long ntfNotificationId) {
        this.ntfNotificationId = ntfNotificationId;
    }

    public Timestamp getNtfTimestamp() {
        return ntfTimestamp;
    }

    public void setNtfTimestamp(Timestamp ntfTimestamp) {
        this.ntfTimestamp = ntfTimestamp;
    }

    public int getNtfType() {
        return ntfType;
    }

    public void setNtfType(int ntfType) {
        this.ntfType = ntfType;
    }

    public Long getNtfFunctionId() {
        return ntfFunctionId;
    }

    public void setNtfFunctionId(Long ntfFunctionId) {
        this.ntfFunctionId = ntfFunctionId;
    }

    public Long getNtfActivityUserNo() {
        return ntfActivityUserNo;
    }

    public void setNtfActivityUserNo(Long ntfActivityUserNo) {
        this.ntfActivityUserNo = ntfActivityUserNo;
    }

    public String getNtfSourceName() {
        return ntfSourceName;
    }

    public void setNtfSourceName(String ntfSourceName) {
        this.ntfSourceName = ntfSourceName;
    }

    public String getNtfText() {
        return ntfText;
    }

    public void setNtfText(String ntfText) {
        this.ntfText = ntfText;
    }

    public int getNtfRecepientType() {
        return ntfRecepientType;
    }

    public void setNtfRecepientType(int ntfRecepientType) {
        this.ntfRecepientType = ntfRecepientType;
    }

    public Long getNtfSourceImageId() {
        return ntfSourceImageId;
    }

    public void setNtfSourceImageId(Long ntfSourceImageId) {
        this.ntfSourceImageId = ntfSourceImageId;
    }

    public int getNtfStatus() {
        return ntfStatus;
    }

    public void setNtfStatus(int ntfStatus) {
        this.ntfStatus = ntfStatus;
    }

    public Long getNtfRecepient() {
        return ntfRecepient;
    }

    public void setNtfRecepient(Long ntfRecepient) {
        this.ntfRecepient = ntfRecepient;
    }

    @Override
    public String toString() {
        return "Notification{" +
                "ntfNotificationId=" + ntfNotificationId +
                ", ntfTimestamp=" + ntfTimestamp +
                ", ntfType=" + ntfType +
                ", ntfFunctionId=" + ntfFunctionId +
                ", ntfActivityUserNo=" + ntfActivityUserNo +
                ", ntfSourceName='" + ntfSourceName + '\'' +
                ", ntfText='" + ntfText + '\'' +
                ", ntfRecepientType=" + ntfRecepientType +
                ", ntfRecepient=" + ntfRecepient +
                ", ntfSourceImageId=" + ntfSourceImageId +
                ", ntfStatus=" + ntfStatus +
                '}';
    }
}
