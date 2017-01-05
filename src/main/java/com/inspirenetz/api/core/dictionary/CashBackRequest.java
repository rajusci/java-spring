package com.inspirenetz.api.core.dictionary;

import com.inspirenetz.api.core.domain.RedemptionMerchant;

/**
 * Created by saneeshci on 15/09/15..
 */
public class CashBackRequest {

    String cashBackType = "";

    String loyaltyId = "";

    String ref = "";

    Double amount =0.0;

    String merchantIdentifier = "";

  /*  Integer merchantIdentifierType = MerchantIdentifierType.IDENTIFIER_TYPE_CODE;

  */  String pin = "";

    Integer channel = RequestChannel.RDM_CHANNEL_SMS;

    Long merchantNo = 1l;

    Long rwdCurrencyId = 1l;

    Double redeemQty = 0.0;

    boolean isValid = true;

    String data = "";

    Long rdmId = 0L;

    boolean isCashBackAllowed = false;

    private boolean redemptionApprovalStatus = true;

    Long debitCustomerNo = 0L;

    String debitLoyaltyId = "";

    RedemptionMerchant redemptionMerchant;

    String remName;

    String rwdCurrencyName;

    Integer settlementType = MerchantSettlementType.LOAD_WALLET;

    String trackingId = "";

    boolean isApprovalRequest = false;

    String reference = "";

    String otpCode;

    public Long getMerchantNo() {
        return merchantNo;
    }

    public void setMerchantNo(Long merchantNo) {
        this.merchantNo = merchantNo;
    }

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

    public Long getRwdCurrencyId() {
        return rwdCurrencyId;
    }

    public void setRwdCurrencyId(Long rwdCurrencyId) {
        this.rwdCurrencyId = rwdCurrencyId;
    }

    public Double getRedeemQty() {
        return redeemQty;
    }

    public void setRedeemQty(Double redeemQty) {
        this.redeemQty = redeemQty;
    }

    public boolean isValid() {
        return isValid;
    }

    public void setValid(boolean isValid) {
        this.isValid = isValid;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public Long getRdmId() {
        return rdmId;
    }

    public void setRdmId(Long rdmId) {
        this.rdmId = rdmId;
    }

    public boolean isCashBackAllowed() {
        return isCashBackAllowed;
    }

    public void setCashBackAllowed(boolean isCashBackAllowed) {
        this.isCashBackAllowed = isCashBackAllowed;
    }

    public boolean isRedemptionApprovalStatus() {
        return redemptionApprovalStatus;
    }

    public void setRedemptionApprovalStatus(boolean redemptionApprovalStatus) {
        this.redemptionApprovalStatus = redemptionApprovalStatus;
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

    public String getOtpCode() {
        return otpCode;
    }

    public void setOtpCode(String otpCode) {
        this.otpCode = otpCode;
    }

    /*public Integer getMerchantIdentifierType() {
            return merchantIdentifierType;
        }

        public void setMerchantIdentifierType(Integer merchantIdentifierType) {
            this.merchantIdentifierType = merchantIdentifierType;
        }
    */
    public RedemptionMerchant getRedemptionMerchant() {
        return redemptionMerchant;
    }

    public void setRedemptionMerchant(RedemptionMerchant redemptionMerchant) {
        this.redemptionMerchant = redemptionMerchant;
    }

    public Integer getSettlementType() {
        return settlementType;
    }

    public void setSettlementType(Integer settlementType) {
        this.settlementType = settlementType;
    }

    public String getTrackingId() {
        return trackingId;
    }

    public void setTrackingId(String trackingId) {
        this.trackingId = trackingId;
    }

    public boolean isApprovalRequest() {
        return isApprovalRequest;
    }

    public void setApprovalRequest(boolean isApprovalRequest) {
        this.isApprovalRequest = isApprovalRequest;
    }

    public String getReference() {
        return reference;
    }

    public void setReference(String reference) {
        this.reference = reference;
    }

    public String getRemName() {
        return remName;
    }

    public void setRemName(String remName) {
        this.remName = remName;
    }

    public String getRwdCurrencyName() {
        return rwdCurrencyName;
    }

    public void setRwdCurrencyName(String rwdCurrencyName) {
        this.rwdCurrencyName = rwdCurrencyName;
    }

    @Override
    public String toString() {
        return "CashBackRequest{" +
                "cashBackType='" + cashBackType + '\'' +
                ", loyaltyId='" + loyaltyId + '\'' +
                ", ref='" + ref + '\'' +
                ", amount=" + amount +
                ", merchantIdentifier='" + merchantIdentifier + '\'' +
                ", pin='" + pin + '\'' +
                ", channel=" + channel +
                ", merchantNo=" + merchantNo +
                ", rwdCurrencyId=" + rwdCurrencyId +
                ", redeemQty=" + redeemQty +
                ", isValid=" + isValid +
                ", data='" + data + '\'' +
                ", rdmId=" + rdmId +
                ", isCashBackAllowed=" + isCashBackAllowed +
                ", redemptionApprovalStatus=" + redemptionApprovalStatus +
                ", debitCustomerNo=" + debitCustomerNo +
                ", debitLoyaltyId='" + debitLoyaltyId + '\'' +
                ", redemptionMerchant=" + redemptionMerchant +
                ", remName='" + remName + '\'' +
                ", rwdCurrencyName='" + rwdCurrencyName + '\'' +
                ", settlementType=" + settlementType +
                ", trackingId='" + trackingId + '\'' +
                ", isApprovalRequest=" + isApprovalRequest +
                ", reference='" + reference + '\'' +
                ", otpCode='" + otpCode + '\'' +
                '}';
    }
}
