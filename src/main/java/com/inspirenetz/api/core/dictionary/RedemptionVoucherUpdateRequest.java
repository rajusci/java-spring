package com.inspirenetz.api.core.dictionary;

import java.sql.Date;
import java.util.List;

/**
 * Created by ameen on 5/11/15.
 */
public class RedemptionVoucherUpdateRequest {

    public Long rvrReqId;
    public String rvrReqVoucherCode;
    public Date rvrReqExpiryDate;
    public Integer rvrNotification=IndicatorStatus.NO;
    public Integer rvrExpiryOption =IndicatorStatus.NO;
    public String amount;
    public String pin;
    public String rvrReqUniqueBatchId;
    public List<RedemptionVoucherUpdateRequest> redemptionVoucherUpdateRequestList;

    public Long getRvrReqId() {
        return rvrReqId;
    }

    public void setRvrReqId(Long rvrReqId) {
        this.rvrReqId = rvrReqId;
    }

    public String getRvrReqVoucherCode() {
        return rvrReqVoucherCode;
    }

    public void setRvrReqVoucherCode(String rvrReqVoucherCode) {
        this.rvrReqVoucherCode = rvrReqVoucherCode;
    }

    public Date getRvrReqExpiryDate() {
        return rvrReqExpiryDate;
    }

    public void setRvrReqExpiryDate(Date rvrReqExpiryDate) {
        this.rvrReqExpiryDate = rvrReqExpiryDate;
    }

    public Integer getRvrNotification() {
        return rvrNotification;
    }

    public void setRvrNotification(Integer rvrNotification) {
        this.rvrNotification = rvrNotification;
    }

    public Integer getRvrExpiryOption() {
        return rvrExpiryOption;
    }

    public void setRvrExpiryOption(Integer rvrExpiryOption) {
        this.rvrExpiryOption = rvrExpiryOption;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getPin() {
        return pin;
    }

    public void setPin(String pin) {
        this.pin = pin;
    }

    public String getRvrReqUniqueBatchId() {
        return rvrReqUniqueBatchId;
    }

    public void setRvrReqUniqueBatchId(String rvrReqUniqueBatchId) {
        this.rvrReqUniqueBatchId = rvrReqUniqueBatchId;
    }

    public List<RedemptionVoucherUpdateRequest> getRedemptionVoucherUpdateRequestList() {
        return redemptionVoucherUpdateRequestList;
    }

    public void setRedemptionVoucherUpdateRequestList(List<RedemptionVoucherUpdateRequest> redemptionVoucherUpdateRequestList) {
        this.redemptionVoucherUpdateRequestList = redemptionVoucherUpdateRequestList;
    }

    @Override
    public String toString() {
        return "RedemptionVoucherUpdateRequest{" +
                "rvrReqId=" + rvrReqId +
                ", rvrReqVoucherCode='" + rvrReqVoucherCode + '\'' +
                ", rvrReqExpiryDate=" + rvrReqExpiryDate +
                ", rvrNotification=" + rvrNotification +
                ", rvrExpiryOption=" + rvrExpiryOption +
                ", amount='" + amount + '\'' +
                ", pin='" + pin + '\'' +
                ", rvrReqUniqueBatchId='" + rvrReqUniqueBatchId + '\'' +
                ", redemptionVoucherUpdateRequestList=" + redemptionVoucherUpdateRequestList +
                '}';
    }
}
