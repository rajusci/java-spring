package com.inspirenetz.api.core.dictionary;

import com.inspirenetz.api.core.domain.Customer;
import com.inspirenetz.api.core.domain.RewardCurrency;
import com.inspirenetz.api.core.domain.TransferPointSetting;

/**
 * Created by sandheepgr on 2/9/14.
 */
public class TransferPointRequest {




    private String fromLoyaltyId;

    private String toLoyaltyId;


    private Long fromRewardCurrency;

    private Long toRewardCurrency;


    private Double rewardQty = 0.0;




    private Long merchantNo  = 1L;

    private Customer fromCustomer ;

    private Customer toCustomer ;

    private RewardCurrency fromRewardCurrencyObj;

    private RewardCurrency toRewardCurrencyObj;

    private TransferPointSetting transferPointSetting;

    private boolean isCustomerPrimary = false;

    private Long parentCustomerNo;

    private Long childCustomerNo;

    private Long approverCustomerNo;

    private Long requestorCustomerNo;

    private boolean isTransferAllowed = false;

    private boolean isApproved = true;

    private boolean isAccountLinked;

    private int eligibilityStatus = RequestEligibilityStatus.ELIGIBLE;

    private Long ptrId;

    public Long getPtrId() {
        return ptrId;
    }

    public void setPtrId(Long ptrId) {
        this.ptrId = ptrId;
    }

    public Long getChildCustomerNo() {
        return childCustomerNo;
    }

    public void setChildCustomerNo(Long childCustomerNo) {
        this.childCustomerNo = childCustomerNo;
    }

    public Long getParentCustomerNo() {
        return parentCustomerNo;
    }

    public void setParentCustomerNo(Long parentCustomerNo) {
        this.parentCustomerNo = parentCustomerNo;
    }

    public int getEligibilityStatus() {
        return eligibilityStatus;
    }

    public void setEligibilityStatus(int eligibilityStatus) {
        this.eligibilityStatus = eligibilityStatus;
    }

    public boolean isApproved() {
        return isApproved;
    }

    public void setApproved(boolean isApproved) {
        this.isApproved = isApproved;
    }

    public boolean isTransferAllowed() {

        return isTransferAllowed;
    }

    public void setTransferAllowed(boolean isTransferAllowed) {
        this.isTransferAllowed = isTransferAllowed;
    }

    public Long getRequestorCustomerNo() {
        return requestorCustomerNo;
    }

    public void setRequestorCustomerNo(Long requestorCustomerNo) {
        this.requestorCustomerNo = requestorCustomerNo;
    }

    public boolean isAccountLinked() {
        return isAccountLinked;
    }

    public void setAccountLinked(boolean isAccountLinked) {
        this.isAccountLinked = isAccountLinked;
    }

    public Long getApproverCustomerNo() {
        return approverCustomerNo;
    }

    public void setApproverCustomerNo(Long approverCustomerNo) {
        this.approverCustomerNo = approverCustomerNo;
    }

    public String getFromLoyaltyId() {
        return fromLoyaltyId;
    }

    public void setFromLoyaltyId(String fromLoyaltyId) {
        this.fromLoyaltyId = fromLoyaltyId;
    }

    public String getToLoyaltyId() {
        return toLoyaltyId;
    }

    public void setToLoyaltyId(String toLoyaltyId) {
        this.toLoyaltyId = toLoyaltyId;
    }

    public Long getFromRewardCurrency() {
        return fromRewardCurrency;
    }

    public void setFromRewardCurrency(Long fromRewardCurrency) {
        this.fromRewardCurrency = fromRewardCurrency;
    }

    public Long getToRewardCurrency() {
        return toRewardCurrency;
    }

    public void setToRewardCurrency(Long toRewardCurrency) {
        this.toRewardCurrency = toRewardCurrency;
    }

    public Double getRewardQty() {
        return rewardQty;
    }

    public void setRewardQty(Double rewardQty) {
        this.rewardQty = rewardQty;
    }

    public Long getMerchantNo() {
        return merchantNo;
    }

    public void setMerchantNo(Long merchantNo) {
        this.merchantNo = merchantNo;
    }

    public Customer getFromCustomer() {
        return fromCustomer;
    }

    public void setFromCustomer(Customer fromCustomer) {
        this.fromCustomer = fromCustomer;
    }

    public Customer getToCustomer() {
        return toCustomer;
    }

    public void setToCustomer(Customer toCustomer) {
        this.toCustomer = toCustomer;
    }

    public RewardCurrency getFromRewardCurrencyObj() {
        return fromRewardCurrencyObj;
    }

    public void setFromRewardCurrencyObj(RewardCurrency fromRewardCurrencyObj) {
        this.fromRewardCurrencyObj = fromRewardCurrencyObj;
    }

    public RewardCurrency getToRewardCurrencyObj() {
        return toRewardCurrencyObj;
    }

    public void setToRewardCurrencyObj(RewardCurrency toRewardCurrencyObj) {
        this.toRewardCurrencyObj = toRewardCurrencyObj;
    }

    public TransferPointSetting getTransferPointSetting() {
        return transferPointSetting;
    }

    public void setTransferPointSetting(TransferPointSetting transferPointSetting) {
        this.transferPointSetting = transferPointSetting;
    }

    public boolean isCustomerPrimary() {
        return isCustomerPrimary;
    }

    public void setCustomerPrimary(boolean isCustomerPrimary) {
        this.isCustomerPrimary = isCustomerPrimary;
    }

    @Override
    public String toString() {
        return "TransferPointRequest{" +
                "fromLoyaltyId='" + fromLoyaltyId + '\'' +
                ", toLoyaltyId='" + toLoyaltyId + '\'' +
                ", fromRewardCurrency=" + fromRewardCurrency +
                ", toRewardCurrency=" + toRewardCurrency +
                ", rewardQty=" + rewardQty +
                ", merchantNo=" + merchantNo +
                '}';
    }
}

