package com.inspirenetz.api.rest.resource;

import com.inspirenetz.api.core.dictionary.SyncProcessStatus;

import java.sql.Timestamp;
import java.util.Date;

/**
 * Created by sandheepgr on 15/4/14.
 */
public class SyncStatusResource extends BaseResource {

    private Long sysId;
    private Long sysMerchantNo=0L;
    private Long sysLocation = 0L ;
    private Long sysBatch = 0L ;
    private String sysBatchRef = "";
    private Integer sysType ;
    private Integer sysStatus= SyncProcessStatus.FAILED;
    private Date sysDate;
    private Timestamp sysTimestamp;
    private String batchFilePath="";

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

    public String getBatchFilePath() {
        return batchFilePath;
    }

    public void setBatchFilePath(String batchFilePath) {
        this.batchFilePath = batchFilePath;
    }
}
