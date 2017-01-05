package com.inspirenetz.api.core.domain;

import com.inspirenetz.api.core.dictionary.TransferRequestStatus;

import javax.persistence.*;
import java.sql.Timestamp;
import java.sql.Date;

/**
 * Created by saneeshci on 27/09/14.
 */
@Entity
@Table(name="POINT_TRANSFER_REQUESTS")
public class PointTransferRequest extends AuditedEntity {


    @Column(name = "PTR_ID",nullable = false)
    @Basic(fetch = FetchType.EAGER)
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long ptrId;

    @Column(name = "PTR_SOURCE",nullable = false)
    @Basic(fetch = FetchType.EAGER)
    private String ptrSource;

    @Column(name = "PTR_DESTINATION" ,nullable = false)
    @Basic(fetch = FetchType.EAGER)
    private String ptrDestination;



    @Column(name = "PTR_APPROVER" ,nullable = false)
    @Basic(fetch = FetchType.EAGER)
    private String ptrApprover;

    @Column(name = "PTR_SOURCE_CUS_NO" ,nullable = false)
    @Basic(fetch = FetchType.EAGER)
    private Long ptrSourceCusNo;

    @Column(name = "PTR_APPROVER_CUS_NO" ,nullable = false)
    @Basic(fetch = FetchType.EAGER)
    private Long ptrApproverCusNo;

    @Column(name = "PTR_REWARD_QTY" ,nullable = false)
    @Basic(fetch = FetchType.EAGER)
    private Double ptrRewardQty;

    @Column(name = "PTR_STATUS" ,nullable = false)
    @Basic(fetch = FetchType.EAGER)
    private Integer ptrStatus = TransferRequestStatus.NEW;

    @Column(name = "PTR_SOURCE_CURRENCY" ,nullable = false)
    @Basic(fetch = FetchType.EAGER)
    private Long ptrSourceCurrency;

    @Column(name = "PTR_DEST_CURRENCY" ,nullable = false)
    @Basic(fetch = FetchType.EAGER)
    private Long ptrDestCurrency;

    @Column(name = "PTR_MERCHANT_NO" ,nullable = false)
    @Basic(fetch = FetchType.EAGER)
    private Long ptrMerchantNo;

    @Column(name = "PTR_REQUEST_DATE" ,nullable = true,updatable = false)
    @Basic(fetch = FetchType.EAGER)
    private Date ptrRequestDate;

    @PrePersist
    private void populateInsertFields() {

        ptrRequestDate = new Date(System.currentTimeMillis());

    }


    public Long getPtrId() {
        return ptrId;
    }

    public void setPtrId(Long ptrId) {
        this.ptrId = ptrId;
    }

    public String getPtrSource() {
        return ptrSource;
    }

    public void setPtrSource(String ptrSource) {
        this.ptrSource = ptrSource;
    }

    public String getPtrDestination() {
        return ptrDestination;
    }

    public void setPtrDestination(String ptrDestination) {
        this.ptrDestination = ptrDestination;
    }

    public String getPtrApprover() {
        return ptrApprover;
    }

    public void setPtrApprover(String ptrApprover) {
        this.ptrApprover = ptrApprover;
    }

    public Double getPtrRewardQty() {
        return ptrRewardQty;
    }

    public void setPtrRewardQty(Double ptrRewardQty) {
        this.ptrRewardQty = ptrRewardQty;
    }

    public Integer getPtrStatus() {
        return ptrStatus;
    }

    public void setPtrStatus(Integer ptrStatus) {
        this.ptrStatus = ptrStatus;
    }

    public Long getPtrSourceCurrency() {
        return ptrSourceCurrency;
    }

    public void setPtrSourceCurrency(Long ptrSourceCurrency) {
        this.ptrSourceCurrency = ptrSourceCurrency;
    }

    public Long getPtrDestCurrency() {
        return ptrDestCurrency;
    }

    public void setPtrDestCurrency(Long ptrDestCurrency) {
        this.ptrDestCurrency = ptrDestCurrency;
    }

    public Long getPtrMerchantNo() {
        return ptrMerchantNo;
    }

    public void setPtrMerchantNo(Long ptrMerchantNo) {
        this.ptrMerchantNo = ptrMerchantNo;
    }

    public Long getPtrSourceCusNo() {
        return ptrSourceCusNo;
    }

    public void setPtrSourceCusNo(Long ptrSourceCusNo) {
        this.ptrSourceCusNo = ptrSourceCusNo;
    }

    public Long getPtrApproverCusNo() {
        return ptrApproverCusNo;
    }

    public void setPtrApproverCusNo(Long ptrApproverCusNo) {
        this.ptrApproverCusNo = ptrApproverCusNo;
    }

    public Date getPtrRequestDate() {
        return ptrRequestDate;
    }

    public void setPtrRequestDate(Date ptrRequestDate) {
        this.ptrRequestDate = ptrRequestDate;
    }

    @Override
    public String toString() {
        return "PointTransferRequest{" +
                "ptrId=" + ptrId +
                ", ptrSource='" + ptrSource + '\'' +
                ", ptrDestination='" + ptrDestination + '\'' +
                ", ptrApprover='" + ptrApprover + '\'' +
                ", ptrSourceCusNo=" + ptrSourceCusNo +
                ", ptrApproverCusNo=" + ptrApproverCusNo +
                ", ptrRewardQty=" + ptrRewardQty +
                ", ptrStatus=" + ptrStatus +
                ", ptrSourceCurrency=" + ptrSourceCurrency +
                ", ptrDestCurrency=" + ptrDestCurrency +
                ", ptrMerchantNo=" + ptrMerchantNo +
                '}';
    }
}
