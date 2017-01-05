package com.inspirenetz.api.test.core.builder;

import com.inspirenetz.api.core.dictionary.TransferRequestStatus;
import com.inspirenetz.api.core.domain.PointTransferRequest;

/**
 * Created by saneesh-ci on 18/2/15.
 */
public class PointTransferRequestBuilder {
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

    private PointTransferRequestBuilder() {
    }

    public static PointTransferRequestBuilder aPointTransferRequest() {
        return new PointTransferRequestBuilder();
    }

    public PointTransferRequestBuilder withPtrId(Long ptrId) {
        this.ptrId = ptrId;
        return this;
    }

    public PointTransferRequestBuilder withPtrSource(String ptrSource) {
        this.ptrSource = ptrSource;
        return this;
    }

    public PointTransferRequestBuilder withPtrDestination(String ptrDestination) {
        this.ptrDestination = ptrDestination;
        return this;
    }

    public PointTransferRequestBuilder withPtrApprover(String ptrApprover) {
        this.ptrApprover = ptrApprover;
        return this;
    }

    public PointTransferRequestBuilder withPtrSourceCusNo(Long ptrSourceCusNo) {
        this.ptrSourceCusNo = ptrSourceCusNo;
        return this;
    }

    public PointTransferRequestBuilder withPtrApproverCusNo(Long ptrApproverCusNo) {
        this.ptrApproverCusNo = ptrApproverCusNo;
        return this;
    }

    public PointTransferRequestBuilder withPtrRewardQty(Double ptrRewardQty) {
        this.ptrRewardQty = ptrRewardQty;
        return this;
    }

    public PointTransferRequestBuilder withPtrStatus(Integer ptrStatus) {
        this.ptrStatus = ptrStatus;
        return this;
    }

    public PointTransferRequestBuilder withPtrSourceCurrency(Long ptrSourceCurrency) {
        this.ptrSourceCurrency = ptrSourceCurrency;
        return this;
    }

    public PointTransferRequestBuilder withPtrDestCurrency(Long ptrDestCurrency) {
        this.ptrDestCurrency = ptrDestCurrency;
        return this;
    }

    public PointTransferRequestBuilder withPtrMerchantNo(Long ptrMerchantNo) {
        this.ptrMerchantNo = ptrMerchantNo;
        return this;
    }

    public PointTransferRequestBuilder but() {
        return aPointTransferRequest().withPtrId(ptrId).withPtrSource(ptrSource).withPtrDestination(ptrDestination).withPtrApprover(ptrApprover).withPtrSourceCusNo(ptrSourceCusNo).withPtrApproverCusNo(ptrApproverCusNo).withPtrRewardQty(ptrRewardQty).withPtrStatus(ptrStatus).withPtrSourceCurrency(ptrSourceCurrency).withPtrDestCurrency(ptrDestCurrency).withPtrMerchantNo(ptrMerchantNo);
    }

    public PointTransferRequest build() {
        PointTransferRequest pointTransferRequest = new PointTransferRequest();
        pointTransferRequest.setPtrId(ptrId);
        pointTransferRequest.setPtrSource(ptrSource);
        pointTransferRequest.setPtrDestination(ptrDestination);
        pointTransferRequest.setPtrApprover(ptrApprover);
        pointTransferRequest.setPtrSourceCusNo(ptrSourceCusNo);
        pointTransferRequest.setPtrApproverCusNo(ptrApproverCusNo);
        pointTransferRequest.setPtrRewardQty(ptrRewardQty);
        pointTransferRequest.setPtrStatus(ptrStatus);
        pointTransferRequest.setPtrSourceCurrency(ptrSourceCurrency);
        pointTransferRequest.setPtrDestCurrency(ptrDestCurrency);
        pointTransferRequest.setPtrMerchantNo(ptrMerchantNo);
        return pointTransferRequest;
    }
}
