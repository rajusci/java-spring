package com.inspirenetz.api.core.domain;

import com.inspirenetz.api.core.dictionary.RecordStatus;

import javax.persistence.*;
import java.sql.Date;
import java.sql.Time;
import java.sql.Timestamp;

/**
 * Created by saneeshci on 04/12/14.
 */
@Entity
@Table(name = "CUSTOMER_ACTIVITIES")
public class CustomerActivity extends AuditedEntity {

    @Id
    @Column(name = "CUA_ID" ,nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long cuaId;

    @Column(name = "CUA_MERCHANT_NO",nullable = false)
    private Long cuaMerchantNo;

    @Column(name = "CUA_LOYALTY_ID" ,nullable = false)
    private String cuaLoyaltyId = "0";

    @Column(name = "CUA_ACTIVITY_TYPE",nullable = false)
    private Integer cuaActivityType = 0;

    @Column(name = "CUA_REMARKS" ,nullable=true)
    private String cuaRemarks ;

    @Column(name = "CUA_PARAMS" ,nullable=true)
    private String cuaParams ;

    @Column(name = "CUA_DATE")
    private Date cuaDate ;

    @Column(name ="CUA_TIME")
    private Time cuaTime;

    @Column(name = "CUA_RECORD_STATUS",nullable = true)
    @Basic(fetch = FetchType.EAGER)
    private Integer cuaRecordStatus  = RecordStatus.RECORD_STATUS_ACTIVE;

    @PrePersist
    private void populateInsertFields() {

        // Get the current timestamp
        Timestamp timestamp = new Timestamp(System.currentTimeMillis());

        // Set the registerTimestamp to current time
        cuaDate = new Date(timestamp.getTime());

        // Set the registerTimestamp to current time
        cuaTime = new Time(timestamp.getTime());

    }

    @PreUpdate
    private void populateUpdateFields() {

        Timestamp timestamp = new Timestamp(System.currentTimeMillis());

        // Set the registerTimestamp to current time
        cuaDate = new Date(timestamp.getTime());

        // Set the registerTimestamp to current time
        cuaTime = new Time(timestamp.getTime());

    }

    public Long getCuaId() {
        return cuaId;
    }

    public void setCuaId(Long cuaId) {
        this.cuaId = cuaId;
    }

    public Long getCuaMerchantNo() {
        return cuaMerchantNo;
    }

    public void setCuaMerchantNo(Long cuaMerchantNo) {
        this.cuaMerchantNo = cuaMerchantNo;
    }

    public String getCuaLoyaltyId() {
        return cuaLoyaltyId;
    }

    public void setCuaLoyaltyId(String cuaLoyaltyId) {
        this.cuaLoyaltyId = cuaLoyaltyId;
    }

    public Integer getCuaActivityType() {
        return cuaActivityType;
    }

    public void setCuaActivityType(Integer cuaActivityType) {
        this.cuaActivityType = cuaActivityType;
    }

    public Date getCuaDate() {
        return cuaDate;
    }

    public void setCuaDate(Date cuaDate) {
        this.cuaDate = cuaDate;
    }

    public Time getCuaTime() {
        return cuaTime;
    }

    public void setCuaTime(Time cuaTime) {
        this.cuaTime = cuaTime;
    }

    public String getCuaParams() {
        return cuaParams;
    }

    public void setCuaParams(String cuaParams) {
        this.cuaParams = cuaParams;
    }

    public String getCuaRemarks() {
        return cuaRemarks;
    }

    public void setCuaRemarks(String cuaRemarks) {
        this.cuaRemarks = cuaRemarks;
    }

    public Integer getCuaRecordStatus() {
        return cuaRecordStatus;
    }

    public void setCuaRecordStatus(Integer cuaRecordStatus) {
        this.cuaRecordStatus = cuaRecordStatus;
    }

    @Override
    public String toString() {
        return "CustomerActivity{" +
                "cuaId=" + cuaId +
                ", cuaMerchantNo=" + cuaMerchantNo +
                ", cuaLoyaltyId='" + cuaLoyaltyId + '\'' +
                ", cuaActivityType=" + cuaActivityType +
                ", cuaRemarks='" + cuaRemarks + '\'' +
                ", cuaParams='" + cuaParams + '\'' +
                ", cuaDate=" + cuaDate +
                ", cuaTime=" + cuaTime +
                '}';
    }
}
