package com.inspirenetz.api.core.domain;

import javax.persistence.*;
import java.util.Date;

/**
 * Created by alameen on 8/11/14.
 */
@Entity
@Table(name = "USER_RESPONSES")
public class UserResponse {


    @Column(name = "URP_ID",nullable = false)
    @Basic(fetch = FetchType.EAGER)
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long urpId;

    @Column(name = "URP_USER_NO",nullable = false)
    @Basic(fetch = FetchType.EAGER)
    private Long urpUserNo;

    @Column(name = "URP_RESPONSE_ITEM_TYPE",nullable = false)
    @Basic(fetch = FetchType.EAGER)
    private Long urpResponseItemType;


    @Column(name = "URP_RESPONSE_ITEM_ID",nullable = false)
    @Basic(fetch = FetchType.EAGER)
    private Long urpResponseItemId;

    @Column(name = "URP_RESPONSE_TYPE",nullable = false)
    @Basic(fetch = FetchType.EAGER)
    private Integer urpResponseType;

    @Column(name = "URP_RESPONSE_VALUE",nullable = true)
    @Basic(fetch = FetchType.EAGER)
    private String urpResponseValue;

    @Column(name = "URP_TIMESTAMP")
    @Temporal(TemporalType.TIMESTAMP)
    private Date urpTimeStamp;

    @Transient
    private String cusFName;

    @Transient
    private String cusLName;

    @Transient
    private String cusLoyaltyId;

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

    public Integer getUrpResponseType() {
        return urpResponseType;
    }

    public void setUrpResponseType(Integer urpResponseType) {
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

    public String getCusLoyaltyId() {
        return cusLoyaltyId;
    }

    public void setCusLoyaltyId(String cusLoyaltyId) {
        this.cusLoyaltyId = cusLoyaltyId;
    }


    @Override
    public String toString() {
        return "UserResponse{" +
                "urpId=" + urpId +
                ", urpUserNo=" + urpUserNo +
                ", urpResponseItemType=" + urpResponseItemType +
                ", urpResponseItemId=" + urpResponseItemId +
                ", urpResponseType=" + urpResponseType +
                ", urpResponseValue='" + urpResponseValue + '\'' +
                ", urpTimeStamp=" + urpTimeStamp +
                ", cusFName='" + cusFName + '\'' +
                ", cusLName='" + cusLName + '\'' +
                ", cusLoyaltyId='" + cusLoyaltyId + '\'' +
                '}';
    }
}
