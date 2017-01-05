package com.inspirenetz.api.core.dictionary;

import java.util.List;

/**
 * Created by sandheepgr on 28/4/14.
 */
public class CatalogueRedemptionRequest {



    private Long merchantNo =0l;

    private String loyaltyId ="";

    private List<RedemptionCatalogue> redemptionCatalogues;

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

    private String auditDetails;

    private Long rdmPartnerNo = 0l;

    public String getAuditDetails() {
        return auditDetails;
    }

    public void setAuditDetails(String auditDetails) {
        this.auditDetails = auditDetails;
    }

    public Long getUserLocation() {
        return userLocation;
    }

    public Long getRdmPartnerNo() {
        return rdmPartnerNo;
    }

    public void setRdmPartnerNo(Long rdmPartnerNo) {
        this.rdmPartnerNo = rdmPartnerNo;
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

    public List<RedemptionCatalogue> getRedemptionCatalogues() {
        return redemptionCatalogues;
    }

    public void setRedemptionCatalogues(List<RedemptionCatalogue> redemptionCatalogues) {
        this.redemptionCatalogues = redemptionCatalogues;
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
}
