package com.inspirenetz.api.rest.resource;

/**
 * Created by sandheepgr on 15/4/14.
 */
public class NotificationCampaignResource extends BaseResource {



    private Long ntcId;
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
    private String ntcSmsContent;

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

    @Override
    public String toString() {
        return "NotificationCampaignResource{" +
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
