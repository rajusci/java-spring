package com.inspirenetz.api.core.domain;

import javax.persistence.*;
import java.sql.Time;
import java.sql.Date;

/**
 * Created by ameen on 20/10/15.
 */
@Entity
@Table(name = "CARD_NUMBER_BATCH_INFO")
public class CardNumberBatchInfo extends AuditedEntity{

    @Column(name = "CNB_ID",nullable = false)
    @Basic(fetch = FetchType.EAGER)
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long cnbId;


    @Column(name = "CNB_MERCHANT_NO",nullable = false)
    @Basic(fetch = FetchType.EAGER)
    private Long cnbMerchantNo;

    @Column(name = "CNB_NAME",nullable = false)
    @Basic(fetch = FetchType.EAGER)
    private String cnbName;

    @Column(name = "CNB_DATE",nullable = false)
    @Basic(fetch = FetchType.EAGER)
    private Date cnbDate;

    @Column(name = "CNB_TIME")
    @Basic(fetch = FetchType.EAGER)
    private Time cnbTime;

    @Column(name = "CNB_CARD_TYPE",nullable = false)
    @Basic(fetch = FetchType.EAGER)
    private Long cnbCardType;

    @Column(name = "CNB_PROCESS_STATUS",nullable = false)
    @Basic(fetch = FetchType.EAGER)
    private Integer cnbProcessStatus;


    public Long getCnbId() {
        return cnbId;
    }

    public void setCnbId(Long cnbId) {
        this.cnbId = cnbId;
    }

    public Long getCnbMerchantNo() {
        return cnbMerchantNo;
    }

    public void setCnbMerchantNo(Long cnbMerchantNo) {
        this.cnbMerchantNo = cnbMerchantNo;
    }

    public String getCnbName() {
        return cnbName;
    }

    public void setCnbName(String cnbName) {
        this.cnbName = cnbName;
    }

    public Date getCnbDate() {
        return cnbDate;
    }

    public void setCnbDate(Date cnbDate) {
        this.cnbDate = cnbDate;
    }

    public Time getCnbTime() {
        return cnbTime;
    }

    public void setCnbTime(Time cnbTime) {
        this.cnbTime = cnbTime;
    }

    public Integer getCnbProcessStatus() {
        return cnbProcessStatus;
    }

    public void setCnbProcessStatus(Integer cnbProcessStatus) {
        this.cnbProcessStatus = cnbProcessStatus;
    }

    public Long getCnbCardType() {
        return cnbCardType;
    }

    public void setCnbCardType(Long cnbCardType) {
        this.cnbCardType = cnbCardType;
    }

    @Override
    public String toString() {
        return "CardNumberBatchInfo{" +
                "cnbId=" + cnbId +
                ", cnbMerchantNo=" + cnbMerchantNo +
                ", cnbName='" + cnbName + '\'' +
                ", cnbDate=" + cnbDate +
                ", cnbTime=" + cnbTime +
                ", cnbCardType=" + cnbCardType +
                ", cnbProcessStatus=" + cnbProcessStatus +
                '}';
    }
}
