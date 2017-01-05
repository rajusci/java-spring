package com.inspirenetz.api.core.dictionary;

import java.sql.Date;

/**
 * Created by sandheepgr on 28/4/14.
 */
public class CatalogueRedemptionItemRequest {



    private Long merchantNo =0l;

    private String loyaltyId ="";

    private int deliveryInd = 0;

    private String address1 ="";

    private String address2 ="";

    private String address3= "";

    private String city ="";

    private String state ="";

    private String country = "";

    private String pincode ="";

    private String contactNumber = "";

    private Long userNo = 0l;

    private Long userLocation = new Long(0);

    private String prdCode;

    private Integer qty;

    private String usrFName;

    private String usrLName;

    private Long usrNo;

    private String txnLocation;

    private String auditDetails;

    private String externalRef ;

    private Integer channel = RequestChannel.RDM_CHANNEL_SMS;

    private String destLoyaltyId = "0";

    private String catExtReference;

    private Long cusCustomerNo;

    private String catProductCode;

    private Long catRedemptionMerchant;

    private boolean isPasaRewards = false;

    private String catDescription;

    private String catDtiNumber;

    private String catMessageSpiel;

    private Double catNumPoints;

    private Double catProductCost;

    private String creditLoyaltyId;

    private Long creditCustomerNo;

    private double totalRwdQty = 0;

    private double totalCashAmount = 0;

    private String trackingId;

    private Long catRewardCurrencyId = 0L;

    private Long rdmId;

    private Long debitCustomerNo;

    private String debitLoyaltyId;

    private Integer eligibilityStatus;

    private boolean isCustomerPrimary = false;

    private Long approverCustomerNo;

    private boolean isRedemptionAllowed = false;

    private boolean redemptionApprovalStatus = true;

    private Long catProductNo;

    private Integer catRedemptionVoucherExpiry;

    private Date catRedemptionVoucherExpiryDate;

    private Integer catRedemptionVoucherExpiryDateAfter;

    private Long catPartnerProduct = 0l;

    private Integer catType = 0;

    private int catDeliveryType ;

    private String catContactNo;

    public String getCatContactNo() {
        return catContactNo;
    }

    public void setCatContactNo(String catContactNo) {
        this.catContactNo = catContactNo;
    }

    public int getCatDeliveryType() {
        return catDeliveryType;
    }

    public void setCatDeliveryType(int catDeliveryType) {
        this.catDeliveryType = catDeliveryType;
    }

    public String getAuditDetails() {
        return auditDetails;
    }

    public void setAuditDetails(String auditDetails) {
        this.auditDetails = auditDetails;
    }

    public Long getUserLocation() {
        return userLocation;
    }

    public void setUserLocation(Long userLocation) {
        this.userLocation = userLocation;
    }

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

    public int getDeliveryInd() {
        return deliveryInd;
    }

    public void setDeliveryInd(int deliveryInd) {
        this.deliveryInd = deliveryInd;
    }

    public String getAddress1() {
        return address1;
    }

    public void setAddress1(String address1) {
        this.address1 = address1;
    }

    public String getAddress2() {
        return address2;
    }

    public void setAddress2(String address2) {
        this.address2 = address2;
    }

    public String getAddress3() {
        return address3;
    }

    public void setAddress3(String address3) {
        this.address3 = address3;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getPincode() {
        return pincode;
    }

    public void setPincode(String pincode) {
        this.pincode = pincode;
    }

    public String getContactNumber() {
        return contactNumber;
    }

    public void setContactNumber(String contactNumber) {
        this.contactNumber = contactNumber;
    }

    public Long getUserNo() {
        return userNo;
    }

    public void setUserNo(Long userNo) {
        this.userNo = userNo;
    }

    public String getPrdCode() {
        return prdCode;
    }

    public void setPrdCode(String prdCode) {
        this.prdCode = prdCode;
    }

    public Integer getQty() {
        return qty;
    }

    public void setQty(Integer qty) {
        this.qty = qty;
    }

    public String getUsrFName() {
        return usrFName;
    }

    public void setUsrFName(String usrFName) {
        this.usrFName = usrFName;
    }

    public String getUsrLName() {
        return usrLName;
    }

    public void setUsrLName(String usrLName) {
        this.usrLName = usrLName;
    }

    public Long getUsrNo() {
        return usrNo;
    }

    public void setUsrNo(Long usrNo) {
        this.usrNo = usrNo;
    }

    public String getTxnLocation() {
        return txnLocation;
    }

    public void setTxnLocation(String txnLocation) {
        this.txnLocation = txnLocation;
    }

    public String getExternalRef() {
        return externalRef;
    }

    public void setExternalRef(String externalRef) {
        this.externalRef = externalRef;
    }

    public Integer getChannel() {
        return channel;
    }

    public void setChannel(Integer channel) {
        this.channel = channel;
    }

    public String getDestLoyaltyId() {
        return destLoyaltyId;
    }

    public void setDestLoyaltyId(String destLoyaltyId) {
        this.destLoyaltyId = destLoyaltyId;
    }

    public String getCatExtReference() {
        return catExtReference;
    }

    public void setCatExtReference(String catExtReference) {
        this.catExtReference = catExtReference;
    }

    public Long getCusCustomerNo() {
        return cusCustomerNo;
    }

    public void setCusCustomerNo(Long cusCustomerNo) {
        this.cusCustomerNo = cusCustomerNo;
    }

    public String getCatProductCode() {
        return catProductCode;
    }

    public Long getCatRedemptionMerchant() {
        return catRedemptionMerchant;
    }

    public void setCatRedemptionMerchant(Long catRedemptionMerchant) {
        this.catRedemptionMerchant = catRedemptionMerchant;
    }

    public void setCatProductCode(String catProductCode) {
        this.catProductCode = catProductCode;
    }

    public boolean isPasaRewards() {
        return isPasaRewards;
    }

    public void setPasaRewards(boolean isPasaRewards) {
        this.isPasaRewards = isPasaRewards;
    }

    public String getCatDescription() {
        return catDescription;
    }

    public void setCatDescription(String catDescription) {
        this.catDescription = catDescription;
    }

    public String getCatDtiNumber() {
        return catDtiNumber;
    }

    public void setCatDtiNumber(String catDtiNumber) {
        this.catDtiNumber = catDtiNumber;
    }

    public String getCatMessageSpiel() {
        return catMessageSpiel;
    }

    public void setCatMessageSpiel(String catMessageSpiel) {
        this.catMessageSpiel = catMessageSpiel;
    }

    public Double getCatNumPoints() {
        return catNumPoints;
    }

    public void setCatNumPoints(Double catNumPoints) {
        this.catNumPoints = catNumPoints;
    }

    public Double getCatProductCost() {
        return catProductCost;
    }

    public void setCatProductCost(Double catProductCost) {
        this.catProductCost = catProductCost;
    }

    public String getCreditLoyaltyId() {
        return creditLoyaltyId;
    }

    public void setCreditLoyaltyId(String creditLoyaltyId) {
        this.creditLoyaltyId = creditLoyaltyId;
    }

    public Long getCreditCustomerNo() {
        return creditCustomerNo;
    }

    public void setCreditCustomerNo(Long creditCustomerNo) {
        this.creditCustomerNo = creditCustomerNo;
    }

    public double getTotalRwdQty() {
        return totalRwdQty;
    }

    public void setTotalRwdQty(double totalRwdQty) {
        this.totalRwdQty = totalRwdQty;
    }

    public double getTotalCashAmount() {
        return totalCashAmount;
    }

    public void setTotalCashAmount(double totalCashAmount) {
        this.totalCashAmount = totalCashAmount;
    }

    public String getTrackingId() {
        return trackingId;
    }

    public void setTrackingId(String trackingId) {
        this.trackingId = trackingId;
    }

    public Long getCatRewardCurrencyId() {
        return catRewardCurrencyId;
    }

    public void setCatRewardCurrencyId(Long catRewardCurrencyId) {
        this.catRewardCurrencyId = catRewardCurrencyId;
    }

    public Long getRdmId() {
        return rdmId;
    }

    public void setRdmId(Long rdmId) {
        this.rdmId = rdmId;
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

    public Integer getEligibilityStatus() {
        return eligibilityStatus;
    }

    public void setEligibilityStatus(Integer eligibilityStatus) {
        this.eligibilityStatus = eligibilityStatus;
    }

    public boolean isCustomerPrimary() {
        return isCustomerPrimary;
    }

    public void setCustomerPrimary(boolean isCustomerPrimary) {
        this.isCustomerPrimary = isCustomerPrimary;
    }

    public Long getApproverCustomerNo() {
        return approverCustomerNo;
    }

    public void setApproverCustomerNo(Long approverCustomerNo) {
        this.approverCustomerNo = approverCustomerNo;
    }

    public boolean isRedemptionAllowed() {
        return isRedemptionAllowed;
    }

    public void setRedemptionAllowed(boolean isRedemptionAllowed) {
        this.isRedemptionAllowed = isRedemptionAllowed;
    }

    public boolean isRedemptionApprovalStatus() {
        return redemptionApprovalStatus;
    }

    public void setRedemptionApprovalStatus(boolean redemptionApprovalStatus) {
        this.redemptionApprovalStatus = redemptionApprovalStatus;
    }

    public Long getCatProductNo() {
        return catProductNo;
    }

    public void setCatProductNo(Long catProductNo) {
        this.catProductNo = catProductNo;
    }

    public Integer getCatRedemptionVoucherExpiry() {
        return catRedemptionVoucherExpiry;
    }

    public void setCatRedemptionVoucherExpiry(Integer catRedemptionVoucherExpiry) {
        this.catRedemptionVoucherExpiry = catRedemptionVoucherExpiry;
    }

    public Date getCatRedemptionVoucherExpiryDate() {
        return catRedemptionVoucherExpiryDate;
    }

    public void setCatRedemptionVoucherExpiryDate(Date catRedemptionVoucherExpiryDate) {
        this.catRedemptionVoucherExpiryDate = catRedemptionVoucherExpiryDate;
    }

    public Integer getCatRedemptionVoucherExpiryDateAfter() {
        return catRedemptionVoucherExpiryDateAfter;
    }

    public void setCatRedemptionVoucherExpiryDateAfter(Integer catRedemptionVoucherExpiryDateAfter) {
        this.catRedemptionVoucherExpiryDateAfter = catRedemptionVoucherExpiryDateAfter;
    }

    public Long getCatPartnerProduct() {
        return catPartnerProduct;
    }

    public void setCatPartnerProduct(Long catPartnerProduct) {
        this.catPartnerProduct = catPartnerProduct;
    }

    public Integer getCatType() {
        return catType;
    }

    public void setCatType(Integer catType) {
        this.catType = catType;
    }
}