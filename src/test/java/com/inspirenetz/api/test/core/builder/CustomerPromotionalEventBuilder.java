package com.inspirenetz.api.test.core.builder;

import com.inspirenetz.api.core.domain.CustomerPromotionalEvent;

import java.sql.Date;
import java.sql.Timestamp;

/**
 * Created by saneesh on 25/6/15.
 */
public class CustomerPromotionalEventBuilder {
    private Long cpeId;
    private Long cpeMerchantNo = 1L;
    private String cpeLoyaltyId ;
    private Long cpeEventId ;
    private Timestamp cpeTimeStamp;
    private String cpeReference;
    private Date cpeDate;
    private String cpeProduct;

    private CustomerPromotionalEventBuilder() {
    }

    public static CustomerPromotionalEventBuilder aCustomerPromotionalEvent() {
        return new CustomerPromotionalEventBuilder();
    }

    public CustomerPromotionalEventBuilder withCpeId(Long cpeId) {
        this.cpeId = cpeId;
        return this;
    }

    public CustomerPromotionalEventBuilder withCpeMerchantNo(Long cpeMerchantNo) {
        this.cpeMerchantNo = cpeMerchantNo;
        return this;
    }

    public CustomerPromotionalEventBuilder withCpeLoyaltyId(String cpeLoyaltyId) {
        this.cpeLoyaltyId = cpeLoyaltyId;
        return this;
    }

    public CustomerPromotionalEventBuilder withCpeEventId(Long cpeEventId) {
        this.cpeEventId = cpeEventId;
        return this;
    }

    public CustomerPromotionalEventBuilder withCpeTimeStamp(Timestamp cpeTimeStamp) {
        this.cpeTimeStamp = cpeTimeStamp;
        return this;
    }

    public CustomerPromotionalEventBuilder withCpeReference(String cpeReference) {
        this.cpeReference = cpeReference;
        return this;
    }
    public CustomerPromotionalEventBuilder withCpeDate(Date cpeDate) {
        this.cpeDate = cpeDate;
        return this;
    }

    public CustomerPromotionalEventBuilder but() {
        return aCustomerPromotionalEvent().withCpeId(cpeId).withCpeMerchantNo(cpeMerchantNo).withCpeLoyaltyId(cpeLoyaltyId).withCpeEventId(cpeEventId).withCpeTimeStamp(cpeTimeStamp).withCpeReference(cpeReference);
    }

    public CustomerPromotionalEvent build() {
        CustomerPromotionalEvent customerPromotionalEvent = new CustomerPromotionalEvent();
        customerPromotionalEvent.setCpeId(cpeId);
        customerPromotionalEvent.setCpeMerchantNo(cpeMerchantNo);
        customerPromotionalEvent.setCpeLoyaltyId(cpeLoyaltyId);
        customerPromotionalEvent.setCpeEventId(cpeEventId);
        customerPromotionalEvent.setCpeTimeStamp(cpeTimeStamp);
        customerPromotionalEvent.setCpeProduct(cpeProduct);
        customerPromotionalEvent.setCpeReference(cpeReference);
        customerPromotionalEvent.setCpeDate(cpeDate);
        return customerPromotionalEvent;
    }
}
