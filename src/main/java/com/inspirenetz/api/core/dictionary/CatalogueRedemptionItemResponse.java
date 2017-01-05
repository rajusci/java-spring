package com.inspirenetz.api.core.dictionary;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by sandheepgr on 29/4/14.
 */
public class CatalogueRedemptionItemResponse {

    String prd_no;

    String status;

    String errorcode = "";

    String tracking_id = "";

    Long rdmId;

    double crbRewardBalance;

    Integer rdmStatus = CatalogueRedemptionStatus.CAT_RDM_INSUFFICIENT_BALANCE;

    String voucherCode;

    List<String > voucherCodeList = new ArrayList<String>();


    public String getPrd_no() {
        return prd_no;
    }

    public void setPrd_no(String prd_no) {
        this.prd_no = prd_no;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getErrorcode() {
        return errorcode;
    }

    public void setErrorcode(String errorcode) {
        this.errorcode = errorcode;
    }

    public String getTracking_id() {
        return tracking_id;
    }

    public void setTracking_id(String tracking_id) {
        this.tracking_id = tracking_id;
    }

    public Long getRdmId() {
        return rdmId;
    }

    public void setRdmId(Long rdmId) {
        this.rdmId = rdmId;
    }

    public Integer getRdmStatus() {
        return rdmStatus;
    }

    public void setRdmStatus(Integer rdmStatus) {
        this.rdmStatus = rdmStatus;
    }

    public double getCrbRewardBalance() {
        return crbRewardBalance;
    }

    public void setCrbRewardBalance(double crbRewardBalance) {
        this.crbRewardBalance = crbRewardBalance;
    }

    public String getVoucherCode() {
        return voucherCode;
    }

    public void setVoucherCode(String voucherCode) {
        this.voucherCode = voucherCode;
    }

    public List<String> getVoucherCodeList() {
        return voucherCodeList;
    }

    public void setVoucherCodeList(List<String> voucherCodeList) {
        this.voucherCodeList = voucherCodeList;
    }

    @Override
    public String toString() {
        return "CatalogueRedemptionItemResponse{" +
                "prd_no='" + prd_no + '\'' +
                ", status='" + status + '\'' +
                ", errorcode='" + errorcode + '\'' +
                ", tracking_id='" + tracking_id + '\'' +
                '}';
    }
}
