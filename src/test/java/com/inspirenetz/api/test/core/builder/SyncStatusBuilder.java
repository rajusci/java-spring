package com.inspirenetz.api.test.core.builder;

import com.inspirenetz.api.core.dictionary.SyncProcessStatus;
import com.inspirenetz.api.core.domain.SyncStatus;

import java.sql.Date;
import java.sql.Timestamp;

/**
 * Created by fayiz-ci on 9/15/15.
 */
public class SyncStatusBuilder {
    private Long sysId;
    private Long sysMerchantNo=0L;
    private Long sysLocation = 0L ;
    private Long sysBatch = 0L ;
    private String sysBatchRef = "";
    private Integer sysType ;
    private Integer sysStatus= SyncProcessStatus.FAILED;
    private Date sysDate;
    private Timestamp sysTimestamp;
    private java.util.Date createdAt;
    private String createdBy;
    private java.util.Date updatedAt;
    private String updatedBy;

    private SyncStatusBuilder() {
    }

    public static SyncStatusBuilder aSyncStatus() {
        return new SyncStatusBuilder();
    }

    public SyncStatusBuilder withSysId(Long sysId) {
        this.sysId = sysId;
        return this;
    }

    public SyncStatusBuilder withSysMerchantNo(Long sysMerchantNo) {
        this.sysMerchantNo = sysMerchantNo;
        return this;
    }

    public SyncStatusBuilder withSysLocation(Long sysLocation) {
        this.sysLocation = sysLocation;
        return this;
    }

    public SyncStatusBuilder withSysBatch(Long sysBatch) {
        this.sysBatch = sysBatch;
        return this;
    }

    public SyncStatusBuilder withSysBatchRef(String sysBatchRef) {
        this.sysBatchRef = sysBatchRef;
        return this;
    }

    public SyncStatusBuilder withSysType(Integer sysType) {
        this.sysType = sysType;
        return this;
    }

    public SyncStatusBuilder withSysStatus(Integer sysStatus) {
        this.sysStatus = sysStatus;
        return this;
    }

    public SyncStatusBuilder withSysDate(Date sysDate) {
        this.sysDate = sysDate;
        return this;
    }

    public SyncStatusBuilder withSysTimestamp(Timestamp sysTimestamp) {
        this.sysTimestamp = sysTimestamp;
        return this;
    }

    public SyncStatusBuilder withCreatedAt(java.util.Date createdAt) {
        this.createdAt = createdAt;
        return this;
    }

    public SyncStatusBuilder withCreatedBy(String createdBy) {
        this.createdBy = createdBy;
        return this;
    }

    public SyncStatusBuilder withUpdatedAt(java.util.Date updatedAt) {
        this.updatedAt = updatedAt;
        return this;
    }

    public SyncStatusBuilder withUpdatedBy(String updatedBy) {
        this.updatedBy = updatedBy;
        return this;
    }

    public SyncStatusBuilder but() {
        return aSyncStatus().withSysId(sysId).withSysMerchantNo(sysMerchantNo).withSysLocation(sysLocation).withSysBatch(sysBatch).withSysBatchRef(sysBatchRef).withSysType(sysType).withSysStatus(sysStatus).withSysDate(sysDate).withSysTimestamp(sysTimestamp).withCreatedAt(createdAt).withCreatedBy(createdBy).withUpdatedAt(updatedAt).withUpdatedBy(updatedBy);
    }

    public SyncStatus build() {
        SyncStatus syncStatus = new SyncStatus();
        syncStatus.setSysId(sysId);
        syncStatus.setSysMerchantNo(sysMerchantNo);
        syncStatus.setSysLocation(sysLocation);
        syncStatus.setSysBatch(sysBatch);
        syncStatus.setSysBatchRef(sysBatchRef);
        syncStatus.setSysType(sysType);
        syncStatus.setSysStatus(sysStatus);
        syncStatus.setSysDate(sysDate);
        syncStatus.setSysTimestamp(sysTimestamp);
        syncStatus.setCreatedAt(createdAt);
        syncStatus.setCreatedBy(createdBy);
        syncStatus.setUpdatedAt(updatedAt);
        syncStatus.setUpdatedBy(updatedBy);
        return syncStatus;
    }
}
