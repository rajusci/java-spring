package com.inspirenetz.api.rest.resource;

import com.inspirenetz.api.core.dictionary.TransferRequestStatus;
import org.springframework.hateoas.ResourceSupport;

import java.sql.Date;

/**
 * Created by ameenci on 16/9/14.
 */
public class PointTransferRequestResource extends ResourceSupport {


    private Long ptrId;
    private String ptrSource;
    private String ptrDestination;
    private String ptrApprover;
    private Long ptrSourceCusNo;
    private Long ptrApproverCusNo;
    private Double ptrRewardQty;
    private Integer ptrStatus = TransferRequestStatus.NEW;
    private Long ptrSourceCurrency;
    private Long ptrDestCurrency;
    private Long ptrMerchantNo;
    private Date ptrRequestDate;

    public Date getPtrRequestDate() {
        return ptrRequestDate;
    }

    public void setPtrRequestDate(Date ptrRequestDate) {
        this.ptrRequestDate = ptrRequestDate;
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

    @Override
    public String toString() {
        return "PointTransferRequestResource{" +
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
                ", ptrRequestDate=" + ptrRequestDate +
                '}';
    }
}
