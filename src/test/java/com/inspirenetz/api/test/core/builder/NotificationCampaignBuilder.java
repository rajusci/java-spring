package com.inspirenetz.api.test.core.builder;

import com.inspirenetz.api.core.domain.NotificationCampaign;

/**
 * Created by saneesh on 21/8/15.
 */
public class NotificationCampaignBuilder {
    private String ntcSmsContent;
    private Long ntcMerchantNo;
    private String ntcName;
    private String ntcDescription;
    private String ntcTargetChannels;
    private Integer ntcTargetListeners;
    private String ntcTargetSegments;
    private String ntcTargetCustomers;
    private String ntcTargetMobile;
    private String ntcTargetEmail;
    private String ntcEmailContent;
    private String ntcEmailSubject;
    private Long ntcId;

    private NotificationCampaignBuilder() {
    }

    public static NotificationCampaignBuilder aNotificationCampaign() {
        return new NotificationCampaignBuilder();
    }

    public NotificationCampaignBuilder withNtcSmsContent(String ntcSmsContent) {
        this.ntcSmsContent = ntcSmsContent;
        return this;
    }

    public NotificationCampaignBuilder withNtcMerchantNo(Long ntcMerchantNo) {
        this.ntcMerchantNo = ntcMerchantNo;
        return this;
    }

    public NotificationCampaignBuilder withNtcName(String ntcName) {
        this.ntcName = ntcName;
        return this;
    }

    public NotificationCampaignBuilder withNtcDescription(String ntcDescription) {
        this.ntcDescription = ntcDescription;
        return this;
    }

    public NotificationCampaignBuilder withNtcTargetChannels(String ntcTargetChannels) {
        this.ntcTargetChannels = ntcTargetChannels;
        return this;
    }

    public NotificationCampaignBuilder withNtcTargetListeners(Integer ntcTargetListeners) {
        this.ntcTargetListeners = ntcTargetListeners;
        return this;
    }

    public NotificationCampaignBuilder withNtcTargetSegments(String ntcTargetSegments) {
        this.ntcTargetSegments = ntcTargetSegments;
        return this;
    }

    public NotificationCampaignBuilder withNtcTargetCustomers(String ntcTargetCustomers) {
        this.ntcTargetCustomers = ntcTargetCustomers;
        return this;
    }

    public NotificationCampaignBuilder withNtcTargetMobile(String ntcTargetMobile) {
        this.ntcTargetMobile = ntcTargetMobile;
        return this;
    }

    public NotificationCampaignBuilder withNtcTargetEmail(String ntcTargetEmail) {
        this.ntcTargetEmail = ntcTargetEmail;
        return this;
    }

    public NotificationCampaignBuilder withNtcEmailContent(String ntcEmailContent) {
        this.ntcEmailContent = ntcEmailContent;
        return this;
    }

    public NotificationCampaignBuilder withNtcEmailSubject(String ntcEmailSubject) {
        this.ntcEmailSubject = ntcEmailSubject;
        return this;
    }

    public NotificationCampaignBuilder withNtcId(Long ntcId) {
        this.ntcId = ntcId;
        return this;
    }

    public NotificationCampaignBuilder but() {
        return aNotificationCampaign().withNtcSmsContent(ntcSmsContent).withNtcMerchantNo(ntcMerchantNo).withNtcName(ntcName).withNtcDescription(ntcDescription).withNtcTargetChannels(ntcTargetChannels).withNtcTargetListeners(ntcTargetListeners).withNtcTargetSegments(ntcTargetSegments).withNtcTargetCustomers(ntcTargetCustomers).withNtcTargetMobile(ntcTargetMobile).withNtcTargetEmail(ntcTargetEmail).withNtcEmailContent(ntcEmailContent).withNtcEmailSubject(ntcEmailSubject).withNtcId(ntcId);
    }

    public NotificationCampaign build() {
        NotificationCampaign notificationCampaign = new NotificationCampaign();
        notificationCampaign.setNtcSmsContent(ntcSmsContent);
        notificationCampaign.setNtcMerchantNo(ntcMerchantNo);
        notificationCampaign.setNtcName(ntcName);
        notificationCampaign.setNtcDescription(ntcDescription);
        notificationCampaign.setNtcTargetChannels(ntcTargetChannels);
        notificationCampaign.setNtcTargetListeners(ntcTargetListeners);
        notificationCampaign.setNtcTargetSegments(ntcTargetSegments);
        notificationCampaign.setNtcTargetCustomers(ntcTargetCustomers);
        notificationCampaign.setNtcTargetMobile(ntcTargetMobile);
        notificationCampaign.setNtcTargetEmail(ntcTargetEmail);
        notificationCampaign.setNtcEmailContent(ntcEmailContent);
        notificationCampaign.setNtcEmailSubject(ntcEmailSubject);
        notificationCampaign.setNtcId(ntcId);
        return notificationCampaign;
    }
}
