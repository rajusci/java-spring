package com.inspirenetz.api.core.dictionary;

import com.inspirenetz.api.core.domain.Customer;
import com.inspirenetz.api.core.domain.Redemption;

/**
 * Created by sandheepgr on 25/7/14.
 */
public class ValidationRequest {

    String cashBackType = "";

    String loyaltyId = "";

    String ref = "";

    Double amount =0.0;

    String merchantIdentifier = "";

    Integer merchantIdentifierType = MerchantIdentifierType.IDENTIFIER_TYPE_CODE;

    String pin = "";

    Customer customer;

    Redemption redemption;

    Integer channel = RequestChannel.RDM_CHANNEL_SMS;

    Long merchantNo = 1l;

    long rwdCurrencyId = 1l;

    boolean isCustomerPrimary = true;

    long approverCustomerNo = 0l;

    int eligibilityStatus = RequestEligibilityStatus.ELIGIBLE;

    String debitLoyaltyId = "";

    Long debitCustomerNo = 0L;

    boolean isActionAllowed = true;

    int approvalType = PartyApprovalType.PARTY_APPROVAL_CASHBACK;

    Long actionId = 0L;

    Double redeemQty = 0.0;

    String otpCode;

    boolean isApprovalRequest = false;

    Long redemptionMerchantNo;

    public String getLoyaltyId() {
        return loyaltyId;
    }

    public void setLoyaltyId(String loyaltyId) {
        this.loyaltyId = loyaltyId;
    }

    public String getRef() {
        return ref;
    }

    public void setRef(String ref) {
        this.ref = ref;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public String getMerchantIdentifier() {
        return merchantIdentifier;
    }

    public void setMerchantIdentifier(String merchantIdentifier) {
        this.merchantIdentifier = merchantIdentifier;
    }

    public String getPin() {
        return pin;
    }

    public void setPin(String pin) {
        this.pin = pin;
    }

    public Integer getChannel() {
        return channel;
    }

    public void setChannel(Integer channel) {
        this.channel = channel;
    }

    public String getCashBackType() {

        return cashBackType;
    }

    public void setCashBackType(String cashBackType) {
        this.cashBackType = cashBackType;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public Redemption getRedemption() {
        return redemption;
    }

    public void setRedemption(Redemption redemption) {
        this.redemption = redemption;
    }

    public Long getMerchantNo() {
        return merchantNo;
    }

    public void setMerchantNo(Long merchantNo) {
        this.merchantNo = merchantNo;
    }

    public long getRwdCurrencyId() {
        return rwdCurrencyId;
    }

    public void setRwdCurrencyId(long rwdCurrencyId) {
        this.rwdCurrencyId = rwdCurrencyId;
    }

    public int getEligibilityStatus() {
        return eligibilityStatus;
    }

    public void setEligibilityStatus(int eligibilityStatus) {
        this.eligibilityStatus = eligibilityStatus;
    }

    public boolean isCustomerPrimary() {
        return isCustomerPrimary;
    }

    public void setCustomerPrimary(boolean isCustomerPrimary) {
        this.isCustomerPrimary = isCustomerPrimary;
    }

    public long getApproverCustomerNo() {
        return approverCustomerNo;
    }

    public void setApproverCustomerNo(long approverCustomerNo) {
        this.approverCustomerNo = approverCustomerNo;
    }

    public Long getDebitCustomerNo() {
        return debitCustomerNo;
    }

    public void setDebitCustomerNo(Long debitCustomerNo) {
        this.debitCustomerNo = debitCustomerNo;
    }

    public String getDebitLoyaltyId() {
        return debitLoyaltyId;
    }

    public void setDebitLoyaltyId(String debitLoyaltyId) {
        this.debitLoyaltyId = debitLoyaltyId;
    }

    public boolean isActionAllowed() {
        return isActionAllowed;
    }

    public void setActionAllowed(boolean isActionAllowed) {
        this.isActionAllowed = isActionAllowed;
    }

    public Long getActionId() {
        return actionId;
    }

    public void setActionId(Long actionId) {
        this.actionId = actionId;
    }

    public int getApprovalType() {
        return approvalType;
    }

    public void setApprovalType(int approvalType) {
        this.approvalType = approvalType;
    }

    public Double getRedeemQty() {
        return redeemQty;
    }

    public void setRedeemQty(Double redeemQty) {
        this.redeemQty = redeemQty;
    }

    public Integer getMerchantIdentifierType() {
        return merchantIdentifierType;
    }

    public void setMerchantIdentifierType(Integer merchantIdentifierType) {
        this.merchantIdentifierType = merchantIdentifierType;
    }

    public boolean isApprovalRequest() {
        return isApprovalRequest;
    }

    public void setApprovalRequest(boolean isApprovalRequest) {
        this.isApprovalRequest = isApprovalRequest;
    }

    public String getOtpCode() {
        return otpCode;
    }

    public void setOtpCode(String otpCode) {
        this.otpCode = otpCode;
    }

    public Long getRedemptionMerchantNo() {
        return redemptionMerchantNo;
    }

    public void setRedemptionMerchantNo(Long redemptionMerchantNo) {
        this.redemptionMerchantNo = redemptionMerchantNo;
    }

    @Override
    public String toString() {
        return "ValidationRequest{" +
                "cashBackType='" + cashBackType + '\'' +
                ", loyaltyId='" + loyaltyId + '\'' +
                ", ref='" + ref + '\'' +
                ", amount=" + amount +
                ", merchantIdentifier='" + merchantIdentifier + '\'' +
                ", merchantIdentifierType=" + merchantIdentifierType +
                ", pin='" + pin + '\'' +
                ", customer=" + customer +
                ", redemption=" + redemption +
                ", channel=" + channel +
                ", merchantNo=" + merchantNo +
                ", rwdCurrencyId=" + rwdCurrencyId +
                ", isCustomerPrimary=" + isCustomerPrimary +
                ", approverCustomerNo=" + approverCustomerNo +
                ", eligibilityStatus=" + eligibilityStatus +
                ", debitLoyaltyId='" + debitLoyaltyId + '\'' +
                ", debitCustomerNo=" + debitCustomerNo +
                ", isActionAllowed=" + isActionAllowed +
                ", approvalType=" + approvalType +
                ", actionId=" + actionId +
                ", redeemQty=" + redeemQty +
                ", otpCode='" + otpCode + '\'' +
                ", isApprovalRequest=" + isApprovalRequest +
                '}';
    }
}
