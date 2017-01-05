package com.inspirenetz.api.test.core.builder;

import com.inspirenetz.api.core.domain.UserResponse;

import java.util.Date;

/**
 * Created by alameen on 24/11/14.
 */
public class UserResponseBuilder {
    private Long urpId;
    private Long urpUserNo;
    private Long urpResponseItemType;
    private Long urpResponseItemId;
    private Integer urpResponseType;
    private String urpResponseValue;
    private Date urpTimeStamp;
    private String cusFName;
    private String cusLName;
    private String cusLoyaltyId;

    private UserResponseBuilder() {
    }

    public static UserResponseBuilder anUserResponse() {
        return new UserResponseBuilder();
    }

    public UserResponseBuilder withUrpId(Long urpId) {
        this.urpId = urpId;
        return this;
    }

    public UserResponseBuilder withUrpUserNo(Long urpUserNo) {
        this.urpUserNo = urpUserNo;
        return this;
    }

    public UserResponseBuilder withUrpResponseItemType(Long urpResponseItemType) {
        this.urpResponseItemType = urpResponseItemType;
        return this;
    }

    public UserResponseBuilder withUrpResponseItemId(Long urpResponseItemId) {
        this.urpResponseItemId = urpResponseItemId;
        return this;
    }

    public UserResponseBuilder withUrpResponseType(Integer urpResponseType) {
        this.urpResponseType = urpResponseType;
        return this;
    }

    public UserResponseBuilder withUrpResponseValue(String urpResponseValue) {
        this.urpResponseValue = urpResponseValue;
        return this;
    }

    public UserResponseBuilder withUrpTimeStamp(Date urpTimeStamp) {
        this.urpTimeStamp = urpTimeStamp;
        return this;
    }

    public UserResponseBuilder withCusFName(String cusFName) {
        this.cusFName = cusFName;
        return this;
    }

    public UserResponseBuilder withCusLName(String cusLName) {
        this.cusLName = cusLName;
        return this;
    }

    public UserResponseBuilder withCusLoyaltyId(String cusLoyaltyId) {
        this.cusLoyaltyId = cusLoyaltyId;
        return this;
    }

    public UserResponseBuilder but() {
        return anUserResponse().withUrpId(urpId).withUrpUserNo(urpUserNo).withUrpResponseItemType(urpResponseItemType).withUrpResponseItemId(urpResponseItemId).withUrpResponseType(urpResponseType).withUrpResponseValue(urpResponseValue).withUrpTimeStamp(urpTimeStamp).withCusFName(cusFName).withCusLName(cusLName).withCusLoyaltyId(cusLoyaltyId);
    }

    public UserResponse build() {
        UserResponse userResponse = new UserResponse();
        userResponse.setUrpId(urpId);
        userResponse.setUrpUserNo(urpUserNo);
        userResponse.setUrpResponseItemType(urpResponseItemType);
        userResponse.setUrpResponseItemId(urpResponseItemId);
        userResponse.setUrpResponseType(urpResponseType);
        userResponse.setUrpResponseValue(urpResponseValue);
        userResponse.setUrpTimeStamp(urpTimeStamp);
        userResponse.setCusFName(cusFName);
        userResponse.setCusLName(cusLName);
        userResponse.setCusLoyaltyId(cusLoyaltyId);
        return userResponse;
    }
}
