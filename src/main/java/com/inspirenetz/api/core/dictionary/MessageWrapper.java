package com.inspirenetz.api.core.dictionary;

import java.util.HashMap;

/**
 * Created by sandheepgr on 29/3/14.
 */
public class MessageWrapper {

    String loyaltyId;

    String spielName;

    String mobile;

    String transId ="";

    Long merchantNo;

    Integer requestChannel = RequestChannel.RDM_CHANNEL_SMS;



    Integer channel = 0;

    Integer isCustomer=IndicatorStatus.NO;

    String emailId;

    String emailSubject;

    String message;

    HashMap<String,String> params=new HashMap<String, String>(0);


    public String getLoyaltyId() {
        return loyaltyId;
    }

    public void setLoyaltyId(String loyaltyId) {
        this.loyaltyId = loyaltyId;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getSpielName() {
        return spielName;
    }

    public void setSpielName(String spielName) {
        this.spielName = spielName;
    }


    public String getTransId() {
        return transId;
    }

    public void setTransId(String transId) {
        this.transId = transId;
    }

    public Long getMerchantNo() {
        return merchantNo;
    }

    public void setMerchantNo(Long merchantNo) {
        this.merchantNo = merchantNo;
    }

    public Integer getChannel() {
        return channel;
    }

    public void setChannel(Integer channel) {
        this.channel = channel;
    }

    public Integer getRequestChannel() {
        return requestChannel;
    }

    public void setRequestChannel(Integer requestChannel) {
        this.requestChannel = requestChannel;
    }

    public Integer getIsCustomer() {
        return isCustomer;
    }

    public void setIsCustomer(Integer isCustomer) {
        this.isCustomer = isCustomer;
    }

    public String getEmailId() {
        return emailId;
    }

    public void setEmailId(String emailId) {
        this.emailId = emailId;
    }

    public String getEmailSubject() {
        return emailSubject;
    }

    public void setEmailSubject(String emailSubject) {
        this.emailSubject = emailSubject;
    }

    public HashMap<String, String> getParams() {
        return params;
    }

    public void setParams(HashMap<String, String> params) {
        this.params = params;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
