package com.inspirenetz.api.core.dictionary;

import com.inspirenetz.api.core.domain.RedemptionMerchant;

/**
 * Created by sandheepgr on 25/7/14.
 */
public class ValidationResponse {

    boolean isValid = false;

    String validationRemarks = "";

    APIErrorCode apiErrorCode;

    int eligibilityStatus = RequestEligibilityStatus.ELIGIBLE;

    String debitLoyaltyId = "";

    Long debitCustomerNo = 0L;

    Long approverCustomerNo = 0L;

    String spielName;

    RedemptionMerchant redemptionMerchant;

    public APIErrorCode getApiErrorCode() {
        return apiErrorCode;
    }

    public void setApiErrorCode(APIErrorCode apiErrorCode) {
        this.apiErrorCode = apiErrorCode;
    }

    public String getValidationRemarks() {
        return validationRemarks;
    }

    public void setValidationRemarks(String validationRemarks) {
        this.validationRemarks = validationRemarks;
    }

    public boolean isValid() {

        return isValid;
    }

    public void setValid(boolean isValid) {
        this.isValid = isValid;
    }

    public int getEligibilityStatus() {
        return eligibilityStatus;
    }

    public void setEligibilityStatus(int eligibilityStatus) {
        this.eligibilityStatus = eligibilityStatus;
    }

    public String getDebitLoyaltyId() {
        return debitLoyaltyId;
    }

    public void setDebitLoyaltyId(String debitLoyaltyId) {
        this.debitLoyaltyId = debitLoyaltyId;
    }

    public Long getDebitCustomerNo() {
        return debitCustomerNo;
    }

    public void setDebitCustomerNo(Long debitCustomerNo) {
        this.debitCustomerNo = debitCustomerNo;
    }

    public Long getApproverCustomerNo() {
        return approverCustomerNo;
    }

    public void setApproverCustomerNo(Long approverCustomerNo) {
        this.approverCustomerNo = approverCustomerNo;
    }

    public RedemptionMerchant getRedemptionMerchant() {
        return redemptionMerchant;
    }

    public void setRedemptionMerchant(RedemptionMerchant redemptionMerchant) {
        this.redemptionMerchant = redemptionMerchant;
    }

    public String getSpielName() {
        return spielName;
    }

    public void setSpielName(String spielName) {
        this.spielName = spielName;
    }
}
