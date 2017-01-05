package com.inspirenetz.api.core.domain;

import com.inspirenetz.api.core.dictionary.CustomerRewardActivityStatus;
import com.inspirenetz.api.core.dictionary.CustomerRewardingType;
import com.inspirenetz.api.core.dictionary.LoyaltyRefferalRoles;
import com.inspirenetz.api.core.dictionary.RedemptionVoucherStatus;

import javax.persistence.*;
import java.sql.Timestamp;

/**
 * Created by saneeshci on 29/09/14.
 */
@Entity
@Table(name="CUSTOMER_REWARD_ACTIVITIES")
public class CustomerRewardActivity extends AuditedEntity {


    @Column(name = "CRA_ID",nullable = false)
    @Basic(fetch = FetchType.EAGER)
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long craId;

    @Column(name = "CRA_CUSTOMER_NO",nullable = false)
    @Basic(fetch = FetchType.EAGER)
    private Long craCustomerNo;

    @Column(name = "CRA_TYPE" ,nullable = false)
    @Basic(fetch = FetchType.EAGER)
    private Integer craType = CustomerRewardingType.EVENT_REGISTRATION;

    @Column(name = "CRA_ACTIVITY_REF",nullable = true)
    @Basic(fetch = FetchType.EAGER)
    private String craActivityRef;

    @Column(name = "CRA_STATUS",nullable = false)
    @Basic(fetch = FetchType.EAGER)
    private Integer craStatus = CustomerRewardActivityStatus.NEW;

    @Column(name = "CRA_ACTIVITY_TIMESTAMP",nullable =  false)
    @Basic(fetch = FetchType.EAGER)
    private Timestamp craActivityTimeStamp ;

    @Transient
    Integer customerRole = LoyaltyRefferalRoles.REFERREE;


    @PrePersist
    private void populateInsertFields() {

        // Set the registerTimestamp to current time
        craActivityTimeStamp = new Timestamp(System.currentTimeMillis());

    }

    public Long getCraId() {
        return craId;
    }

    public void setCraId(Long craId) {
        this.craId = craId;
    }

    public Long getCraCustomerNo() {
        return craCustomerNo;
    }

    public void setCraCustomerNo(Long craCustomerNo) {
        this.craCustomerNo = craCustomerNo;
    }

    public Integer getCraType() {
        return craType;
    }

    public void setCraType(Integer craType) {
        this.craType = craType;
    }

    public String getCraActivityRef() {
        return craActivityRef;
    }

    public void setCraActivityRef(String craActivityRef) {
        this.craActivityRef = craActivityRef;
    }

    public Integer getCraStatus() {
        return craStatus;
    }

    public void setCraStatus(Integer craStatus) {
        this.craStatus = craStatus;
    }

    public Timestamp getCraActivityTimeStamp() {
        return craActivityTimeStamp;
    }

    public void setCraActivityTimeStamp(Timestamp craActivityTimeStamp) {
        this.craActivityTimeStamp = craActivityTimeStamp;
    }

    public Integer getCustomerRole() {
        return customerRole;
    }

    public void setCustomerRole(Integer customerRole) {
        this.customerRole = customerRole;
    }

    @Override
    public String toString() {
        return "CustomerRewardActivity{" +
                "craId=" + craId +
                ", craCustomerNo=" + craCustomerNo +
                ", craType=" + craType +
                ", craActivityRef=" + craActivityRef +
                ", craStatus=" + craStatus +
                ", craActivityTimeStamp=" + craActivityTimeStamp +
                '}';
    }
}
