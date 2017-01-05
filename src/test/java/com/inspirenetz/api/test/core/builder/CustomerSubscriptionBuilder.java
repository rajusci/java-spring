package com.inspirenetz.api.test.core.builder;

import com.inspirenetz.api.core.domain.CustomerSubscription;

import java.util.Date;

/**
 * Created by sandheepgr on 22/8/14.
 */
public class CustomerSubscriptionBuilder {
    private Long csuId;
    private Long csuCustomerNo = 0L;
    private Long csuMerchantNo =0L;
    private String csuProductCode = "";
    private Double csuPoints = 0.0;
    private Long csuLocation = 0L;
    private String csuServiceNo = "";
    private Date createdAt;
    private String createdBy;
    private Date updatedAt;
    private String updatedBy;

    private CustomerSubscriptionBuilder() {
    }

    public static CustomerSubscriptionBuilder aCustomerSubscription() {
        return new CustomerSubscriptionBuilder();
    }

    public CustomerSubscriptionBuilder withCsuId(Long csuId) {
        this.csuId = csuId;
        return this;
    }

    public CustomerSubscriptionBuilder withCsuCustomerNo(Long csuCustomerNo) {
        this.csuCustomerNo = csuCustomerNo;
        return this;
    }

    public CustomerSubscriptionBuilder withCsuMerchantNo(Long csuMerchantNo) {
        this.csuMerchantNo = csuMerchantNo;
        return this;
    }

    public CustomerSubscriptionBuilder withCsuProductCode(String csuProductCode) {
        this.csuProductCode = csuProductCode;
        return this;
    }

    public CustomerSubscriptionBuilder withCsuPoints(Double csuPoints) {
        this.csuPoints = csuPoints;
        return this;
    }

    public CustomerSubscriptionBuilder withCsuLocation(Long csuLocation) {
        this.csuLocation = csuLocation;
        return this;
    }

    public CustomerSubscriptionBuilder withCsuServiceNo(String csuServiceNo) {
        this.csuServiceNo = csuServiceNo;
        return this;
    }

    public CustomerSubscriptionBuilder withCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
        return this;
    }

    public CustomerSubscriptionBuilder withCreatedBy(String createdBy) {
        this.createdBy = createdBy;
        return this;
    }

    public CustomerSubscriptionBuilder withUpdatedAt(Date updatedAt) {
        this.updatedAt = updatedAt;
        return this;
    }

    public CustomerSubscriptionBuilder withUpdatedBy(String updatedBy) {
        this.updatedBy = updatedBy;
        return this;
    }

    public CustomerSubscription build() {
        CustomerSubscription customerSubscription = new CustomerSubscription();
        customerSubscription.setCsuId(csuId);
        customerSubscription.setCsuCustomerNo(csuCustomerNo);
        customerSubscription.setCsuMerchantNo(csuMerchantNo);
        customerSubscription.setCsuProductCode(csuProductCode);
        customerSubscription.setCsuPoints(csuPoints);
        customerSubscription.setCsuLocation(csuLocation);
        customerSubscription.setCsuServiceNo(csuServiceNo);
        customerSubscription.setCreatedAt(createdAt);
        customerSubscription.setCreatedBy(createdBy);
        customerSubscription.setUpdatedAt(updatedAt);
        customerSubscription.setUpdatedBy(updatedBy);
        return customerSubscription;
    }
}
