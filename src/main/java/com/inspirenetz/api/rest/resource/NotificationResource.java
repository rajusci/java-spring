package com.inspirenetz.api.rest.resource;

import com.inspirenetz.api.core.dictionary.ImagePrimaryId;
import com.inspirenetz.api.core.dictionary.NotificationRecepientType;
import com.inspirenetz.api.core.dictionary.NotificationStatus;
import com.inspirenetz.api.core.dictionary.NotificationType;

import java.sql.Date;
import java.sql.Time;
import java.sql.Timestamp;

/**
 * Created by sandheepgr on 15/4/14.
 */
public class NotificationResource extends BaseResource {


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

    public Long getNtfRecepient() {
        return ntfRecepient;
    }

    public void setNtfRecepient(Long ntfRecepient) {
        this.ntfRecepient = ntfRecepient;
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

    @Override
    public String toString() {
        return "NotificationResource{" +
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
