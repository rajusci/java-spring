package com.inspirenetz.api.rest.resource;

import com.fasterxml.jackson.annotation.JsonFormat;
import org.springframework.hateoas.ResourceSupport;

import java.sql.Date;
import java.sql.Time;
import java.sql.Timestamp;

/**
 * Created by ameen on 14/9/15.
 */
public class BulkUploadBatchInfoResource extends ResourceSupport {


    private Long blkBatchIndex;

    private String blkBatchName;

    @JsonFormat(shape=JsonFormat.Shape.STRING, pattern="yyyy-MM-dd")
    private Date blkBatchDate;

    @JsonFormat(shape=JsonFormat.Shape.STRING, pattern="hh:mm:ss")
    private Time blkTime;

    private Long blkMerchantNo;

    private Integer blkProcessingStatus;

    public Long getBlkBatchIndex() {
        return blkBatchIndex;
    }

    public void setBlkBatchIndex(Long blkBatchIndex) {
        this.blkBatchIndex = blkBatchIndex;
    }

    public String getBlkBatchName() {
        return blkBatchName;
    }

    public void setBlkBatchName(String blkBatchName) {
        this.blkBatchName = blkBatchName;
    }

    public Long getBlkMerchantNo() {
        return blkMerchantNo;
    }

    public void setBlkMerchantNo(Long blkMerchantNo) {
        this.blkMerchantNo = blkMerchantNo;
    }

    public Integer getBlkProcessingStatus() {
        return blkProcessingStatus;
    }

    public void setBlkProcessingStatus(Integer blkProcessingStatus) {
        this.blkProcessingStatus = blkProcessingStatus;
    }

    public Date getBlkBatchDate() {
        return blkBatchDate;
    }

    public void setBlkBatchDate(Date blkBatchDate) {
        this.blkBatchDate = blkBatchDate;
    }

    public Time getBlkTime() {
        return blkTime;
    }

    public void setBlkTime(Time blkTime) {
        this.blkTime = blkTime;
    }
}


