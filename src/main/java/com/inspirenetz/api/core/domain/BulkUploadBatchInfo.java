package com.inspirenetz.api.core.domain;

import org.hibernate.validator.constraints.NotEmpty;

import javax.persistence.*;
import java.io.Serializable;
import java.sql.Date;
import java.sql.Time;
import java.sql.Timestamp;

/**
 * Created by ameen on 12/9/15.
 */
@Entity
@Table(name="BULK_UPLOAD_BATCH_INFO")
public class BulkUploadBatchInfo extends AuditedEntity implements Serializable {

    @Column(name = "BLK_BATCH_INDEX",nullable = false)
    @Basic(fetch = FetchType.EAGER)
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long blkBatchIndex;

    @Column(name = "BLK_BATCH_NAME",nullable = false)
    @Basic(fetch = FetchType.EAGER)
    private String blkBatchName;

    @Column(name = "BLK_BATCH_DATE",nullable = false)
    @Basic(fetch = FetchType.EAGER)
    private Date blkBatchDate;

    @Column(name = "BLK_BATCH_TIME",nullable = false)
    @Basic(fetch = FetchType.EAGER)
    private Time blkTime;

    @Column(name = "BLK_MERCHANT_NO",nullable = false)
    @Basic(fetch = FetchType.EAGER)
    private Long blkMerchantNo;

    @Column(name = "BLK_PROCESS_STATUS",nullable = false)
    @Basic(fetch = FetchType.EAGER)
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

    @Override
    public String toString() {
        return "BulkUploadBatchInfo{" +
                "blkBatchIndex=" + blkBatchIndex +
                ", blkBatchName='" + blkBatchName + '\'' +
                ", blkBatchDate=" + blkBatchDate +
                ", blkTime=" + blkTime +
                ", blkMerchantNo=" + blkMerchantNo +
                ", blkProcessingStatus=" + blkProcessingStatus +
                '}';
    }
}
