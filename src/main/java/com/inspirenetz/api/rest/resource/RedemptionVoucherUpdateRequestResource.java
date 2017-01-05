package com.inspirenetz.api.rest.resource;

import com.inspirenetz.api.core.dictionary.IndicatorStatus;
import com.inspirenetz.api.core.dictionary.RedemptionVoucherUpdateRequest;

import java.util.List;

/**
 * Created by ameen on 5/11/15.
 */
public class RedemptionVoucherUpdateRequestResource extends BaseResource {

    public Long rvrReqId;
    public String rvrReqVoucherCode;
    public String rvrReqExpiryDate;
    public Integer rvrNotification= IndicatorStatus.NO;
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

    public String getRvrReqExpiryDate() {
        return rvrReqExpiryDate;
    }

    public void setRvrReqExpiryDate(String rvrReqExpiryDate) {
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
}
