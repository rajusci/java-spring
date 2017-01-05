package com.inspirenetz.api.core.domain;

import javax.persistence.*;
import java.util.HashMap;

/**
 * Created by saneeshci on 27/09/14.
 */
@Entity
@Table(name="NOTIFICATION_CAMPAIGN")
public class NotificationCampaign extends AuditedEntity {


    @Column(name = "NTC_ID",nullable = false)
    @Basic(fetch = FetchType.EAGER)
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long ntcId;

    @Column(name = "NTC_MERCHANT_NO",nullable = false)
    @Basic(fetch = FetchType.EAGER)
    private Long ntcMerchantNo;

    @Column(name = "NTC_NAME" ,nullable = false)
    @Basic(fetch = FetchType.EAGER)
    private String ntcName;

    @Column(name = "NTC_DESCRIPTION" ,nullable = true)
    @Basic(fetch = FetchType.EAGER)
    private String ntcDescription;

    @Column(name = "NTC_TARGET_CHANNELS",nullable = true)
    @Basic(fetch = FetchType.EAGER)
    private String ntcTargetChannels;

    @Column(name = "NTC_TARGET_LISTENERS" ,nullable = true)
    @Basic(fetch = FetchType.EAGER)
    private Integer ntcTargetListeners;


    @Column(name = "NTC_TARGET_SEGMENTS" ,nullable = true)
    @Basic(fetch = FetchType.EAGER)
    private String ntcTargetSegments;

    @Column(name = "NTC_TARGET_CUSTOMERS" ,nullable = true)
    @Basic(fetch = FetchType.EAGER)
    private String ntcTargetCustomers;

    @Column(name = "NTC_TARGET_MOBILE" ,nullable = true)
    @Basic(fetch = FetchType.EAGER)
    private String ntcTargetMobile;

    @Column(name = "NTC_TARGET_EMAIL" ,nullable = true)
    @Basic(fetch = FetchType.EAGER)
    private String ntcTargetEmail;

    @Column(name = "NTC_EMAIL_CONTENT" ,nullable = true)
    @Basic(fetch = FetchType.EAGER)
    private String ntcEmailContent;

    @Column(name = "NTC_EMAIL_SUBJECT" ,nullable = true)
    @Basic(fetch = FetchType.EAGER)
    private String ntcEmailSubject;

    @Column(name = "NTC_SMS_CONTENT" ,nullable = true)
    @Basic(fetch = FetchType.EAGER)
    private String ntcSmsContent;

    @Transient
    private HashMap<String,String> params=new HashMap<String, String>(0);

    public Long getNtcId() {
        return ntcId;
    }

    public void setNtcId(Long ntcId) {
        this.ntcId = ntcId;
    }

    public Long getNtcMerchantNo() {
        return ntcMerchantNo;
    }

    public void setNtcMerchantNo(Long ntcMerchantNo) {
        this.ntcMerchantNo = ntcMerchantNo;
    }

    public String getNtcName() {
        return ntcName;
    }

    public void setNtcName(String ntcName) {
        this.ntcName = ntcName;
    }

    public String getNtcDescription() {
        return ntcDescription;
    }

    public void setNtcDescription(String ntcDescription) {
        this.ntcDescription = ntcDescription;
    }

    public String getNtcTargetChannels() {
        return ntcTargetChannels;
    }

    public void setNtcTargetChannels(String ntcTargetChannels) {
        this.ntcTargetChannels = ntcTargetChannels;
    }

    public Integer getNtcTargetListeners() {
        return ntcTargetListeners;
    }

    public void setNtcTargetListeners(Integer ntcTargetListeners) {
        this.ntcTargetListeners = ntcTargetListeners;
    }

    public String getNtcTargetSegments() {
        return ntcTargetSegments;
    }

    public void setNtcTargetSegments(String ntcTargetSegments) {
        this.ntcTargetSegments = ntcTargetSegments;
    }

    public String getNtcTargetCustomers() {
        return ntcTargetCustomers;
    }

    public void setNtcTargetCustomers(String ntcTargetCustomers) {
        this.ntcTargetCustomers = ntcTargetCustomers;
    }

    public String getNtcTargetMobile() {
        return ntcTargetMobile;
    }

    public void setNtcTargetMobile(String ntcTargetMobile) {
        this.ntcTargetMobile = ntcTargetMobile;
    }

    public String getNtcTargetEmail() {
        return ntcTargetEmail;
    }

    public void setNtcTargetEmail(String ntcTargetEmail) {
        this.ntcTargetEmail = ntcTargetEmail;
    }

    public String getNtcEmailContent() {
        return ntcEmailContent;
    }

    public void setNtcEmailContent(String ntcEmailContent) {
        this.ntcEmailContent = ntcEmailContent;
    }

    public String getNtcEmailSubject() {
        return ntcEmailSubject;
    }

    public void setNtcEmailSubject(String ntcEmailSubject) {
        this.ntcEmailSubject = ntcEmailSubject;
    }

    public String getNtcSmsContent() {
        return ntcSmsContent;
    }

    public void setNtcSmsContent(String ntcSmsContent) {
        this.ntcSmsContent = ntcSmsContent;
    }

    public HashMap<String, String> getParams() {
        return params;
    }

    public void setParams(HashMap<String, String> params) {
        this.params = params;
    }

    @Override
    public String toString() {
        return "NotificationCampaign{" +
                "ntcId=" + ntcId +
                ", ntcMerchantNo=" + ntcMerchantNo +
                ", ntcName='" + ntcName + '\'' +
                ", ntcDescription='" + ntcDescription + '\'' +
                ", ntcTargetChannels='" + ntcTargetChannels + '\'' +
                ", ntcTargetListeners=" + ntcTargetListeners +
                ", ntcTargetSegments='" + ntcTargetSegments + '\'' +
                ", ntcTargetCustomers='" + ntcTargetCustomers + '\'' +
                ", ntcTargetMobile='" + ntcTargetMobile + '\'' +
                ", ntcTargetEmail='" + ntcTargetEmail + '\'' +
                ", ntcEmailContent='" + ntcEmailContent + '\'' +
                ", ntcEmailSubject='" + ntcEmailSubject + '\'' +
                ", ntcSmsContent='" + ntcSmsContent + '\'' +
                '}';
    }
}
