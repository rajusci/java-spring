package com.inspirenetz.api.test.core.builder;

import com.inspirenetz.api.core.dictionary.CustomerRewardingType;
import com.inspirenetz.api.core.domain.CustomerRewardActivity;

import java.sql.Timestamp;

/**
 * Created by saneesh-ci on 30/9/14.
 */
public class CustomerRewardActivityBuilder {
    private Timestamp craActivityTimeStamp ;
    private Long craId;
    private Long craCustomerNo;
    private Integer craType = CustomerRewardingType.EVENT_REGISTRATION;
    private String craActivityRef;
    private Integer craStatus;

    private CustomerRewardActivityBuilder() {
    }

    public static CustomerRewardActivityBuilder aCustomerRewardActivity() {
        return new CustomerRewardActivityBuilder();
    }

    public CustomerRewardActivityBuilder withCraActivityTimeStamp(Timestamp craActivityTimeStamp) {
        this.craActivityTimeStamp = craActivityTimeStamp;
        return this;
    }

    public CustomerRewardActivityBuilder withCraId(Long craId) {
        this.craId = craId;
        return this;
    }

    public CustomerRewardActivityBuilder withCraCustomerNo(Long craCustomerNo) {
        this.craCustomerNo = craCustomerNo;
        return this;
    }

    public CustomerRewardActivityBuilder withCraType(Integer craType) {
        this.craType = craType;
        return this;
    }

    public CustomerRewardActivityBuilder withCraActivityRef(String craActivityRef) {
        this.craActivityRef = craActivityRef;
        return this;
    }

    public CustomerRewardActivityBuilder withCraStatus(Integer craStatus) {
        this.craStatus = craStatus;
        return this;
    }

    public CustomerRewardActivity build() {
        CustomerRewardActivity customerRewardActivity = new CustomerRewardActivity();
        customerRewardActivity.setCraActivityTimeStamp(craActivityTimeStamp);
        customerRewardActivity.setCraId(craId);
        customerRewardActivity.setCraCustomerNo(craCustomerNo);
        customerRewardActivity.setCraType(craType);
        customerRewardActivity.setCraActivityRef(craActivityRef);
        customerRewardActivity.setCraStatus(craStatus);
        return customerRewardActivity;
    }
}
