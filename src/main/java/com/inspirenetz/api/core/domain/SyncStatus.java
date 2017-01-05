package com.inspirenetz.api.core.domain;

import com.inspirenetz.api.core.dictionary.*;

import javax.persistence.*;
import javax.validation.constraints.Size;
import java.sql.Date;
import java.sql.Timestamp;

/**
 * Created by fayizkci on 11/9/15.
 */
@Entity
@Table(name = "SYNC_STATUS")
public class SyncStatus extends AuditedEntity {

    @Column(name = "SYS_ID",nullable = false)
    @Basic(fetch = FetchType.EAGER)
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long sysId;


    @Column(name = "SYS_MERCHANT_NO",nullable = false)
    @Basic(fetch = FetchType.EAGER)
    private Long sysMerchantNo=0L;


    @Column(name = "SYS_LOCATION",nullable = false)
    @Basic(fetch = FetchType.EAGER)
    private Long sysLocation = 0L ;

    @Column(name = "SYS_BATCH",nullable = false)
    @Basic(fetch = FetchType.EAGER)
    private Long sysBatch = 0L ;


    @Basic(fetch = FetchType.EAGER)
    @Column(name = "SYS_BATCH_REF")
    @Size(max=200,message ="{syncstatus.sysBatchRef.size}")
    private String sysBatchRef = "";

    @Column(name = "SYS_TYPE",nullable = false)
    @Basic(fetch = FetchType.EAGER)
    private Integer sysType ;

    @Column(name = "SYS_STATUS",nullable = false)
    @Basic(fetch = FetchType.EAGER)
    private Integer sysStatus= SyncProcessStatus.FAILED;

    @Column(name = "SYS_DATE",nullable = false,updatable = false)
    @Basic(fetch = FetchType.EAGER)
    private Date sysDate;

    @Column(name = "SYS_TIMESTAMP",nullable = false,updatable = false)
    @Basic(fetch = FetchType.EAGER)
    private Timestamp sysTimestamp;

    public SyncStatus() {
    }

    @PrePersist
    private void populateInsertFields() {

        // Get the current timestamp
        Timestamp timestamp = new Timestamp(System.currentTimeMillis());

        // Set the registerTimestamp to current time
        sysTimestamp = new Timestamp(timestamp.getTime());

        sysDate=new Date(timestamp.getTime());

    }


    public Long getSysId() {
        return sysId;
    }

    public void setSysId(Long sysId) {
        this.sysId = sysId;
    }

    public Long getSysMerchantNo() {
        return sysMerchantNo;
    }

    public void setSysMerchantNo(Long sysMerchantNo) {
        this.sysMerchantNo = sysMerchantNo;
    }

    public Long getSysLocation() {
        return sysLocation;
    }

    public void setSysLocation(Long sysLocation) {
        this.sysLocation = sysLocation;
    }

    public Long getSysBatch() {
        return sysBatch;
    }

    public void setSysBatch(Long sysBatch) {
        this.sysBatch = sysBatch;
    }

    public String getSysBatchRef() {
        return sysBatchRef;
    }

    public void setSysBatchRef(String sysBatchRef) {
        this.sysBatchRef = sysBatchRef;
    }

    public Integer getSysType() {
        return sysType;
    }

    public void setSysType(Integer sysType) {
        this.sysType = sysType;
    }

    public Integer getSysStatus() {
        return sysStatus;
    }

    public void setSysStatus(Integer sysStatus) {
        this.sysStatus = sysStatus;
    }

    public Date getSysDate() {
        return sysDate;
    }

    public void setSysDate(Date sysDate) {
        this.sysDate = sysDate;
    }

    public Timestamp getSysTimestamp() {
        return sysTimestamp;
    }

    public void setSysTimestamp(Timestamp sysTimestamp) {
        this.sysTimestamp = sysTimestamp;
    }

    @Override
    public String toString() {
        return "SyncStatus{" +
                "sysId=" + sysId +
                ", sysMerchantNo=" + sysMerchantNo +
                ", sysLocation=" + sysLocation +
                ", sysBatch=" + sysBatch +
                ", sysBatchRef='" + sysBatchRef + '\'' +
                ", sysType=" + sysType +
                ", sysStatus=" + sysStatus +
                ", sysDate=" + sysDate +
                ", sysTimestamp=" + sysTimestamp +
                '}';
    }
}
