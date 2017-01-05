package com.inspirenetz.api.rest.resource;

import org.springframework.hateoas.ResourceSupport;

import java.util.Date;

/**
 * Created by alameen on 8/11/14.
 */
public class UserResponseResource extends ResourceSupport {


    private Long urpId;

    private Long urpUserNo;

    private Long urpResponseItemType;

    private Long urpResponseItemId;

    private Long urpResponseType;

    private String urpResponseValue;

    private Date urpTimeStamp;

    private String cusLoyaltyId;

    private String cusFName;

    private String cusLName;

    public Long getUrpId() {
        return urpId;
    }

    public void setUrpId(Long urpId) {
        this.urpId = urpId;
    }

    public Long getUrpUserNo() {
        return urpUserNo;
    }

    public void setUrpUserNo(Long urpUserNo) {
        this.urpUserNo = urpUserNo;
    }

    public Long getUrpResponseItemType() {
        return urpResponseItemType;
    }

    public void setUrpResponseItemType(Long urpResponseItemType) {
        this.urpResponseItemType = urpResponseItemType;
    }

    public Long getUrpResponseItemId() {
        return urpResponseItemId;
    }

    public void setUrpResponseItemId(Long urpResponseItemId) {
        this.urpResponseItemId = urpResponseItemId;
    }

    public Long getUrpResponseType() {
        return urpResponseType;
    }

    public void setUrpResponseType(Long urpResponseType) {
        this.urpResponseType = urpResponseType;
    }

    public String getUrpResponseValue() {
        return urpResponseValue;
    }

    public void setUrpResponseValue(String urpResponseValue) {
        this.urpResponseValue = urpResponseValue;
    }

    public Date getUrpTimeStamp() {
        return urpTimeStamp;
    }

    public void setUrpTimeStamp(Date urpTimeStamp) {
        this.urpTimeStamp = urpTimeStamp;
    }

    public String getCusLoyaltyId() {
        return cusLoyaltyId;
    }

    public void setCusLoyaltyId(String cusLoyaltyId) {
        this.cusLoyaltyId = cusLoyaltyId;
    }

    public String getCusFName() {
        return cusFName;
    }

    public void setCusFName(String cusFName) {
        this.cusFName = cusFName;
    }

    public String getCusLName() {
        return cusLName;
    }

    public void setCusLName(String cusLName) {
        this.cusLName = cusLName;
    }
}
