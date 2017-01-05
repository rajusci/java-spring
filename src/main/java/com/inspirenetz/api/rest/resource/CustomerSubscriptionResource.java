package com.inspirenetz.api.rest.resource;

/**
 * Created by sandheepgr on 27/8/14.
 */
public class CustomerSubscriptionResource extends BaseResource {

    private Long csuId;

    private Long csuCustomerNo = 0L;

    private Long csuMerchantNo =0L;

    private String csuProductCode = "";

    private Double csuPoints = 0.0;

    private Long csuLocation = 0L;

    private String csuServiceNo = "";

    private String prdName = "";

    private String cusLoyaltyId = "";

    private String cusFName = "";

    private String cusEmail = "";

    private String cusMobile = "";


    public Long getCsuId() {
        return csuId;
    }

    public void setCsuId(Long csuId) {
        this.csuId = csuId;
    }

    public Long getCsuCustomerNo() {
        return csuCustomerNo;
    }

    public void setCsuCustomerNo(Long csuCustomerNo) {
        this.csuCustomerNo = csuCustomerNo;
    }

    public Long getCsuMerchantNo() {
        return csuMerchantNo;
    }

    public void setCsuMerchantNo(Long csuMerchantNo) {
        this.csuMerchantNo = csuMerchantNo;
    }

    public String getCsuProductCode() {
        return csuProductCode;
    }

    public void setCsuProductCode(String csuProductCode) {
        this.csuProductCode = csuProductCode;
    }

    public Double getCsuPoints() {
        return csuPoints;
    }

    public void setCsuPoints(Double csuPoints) {
        this.csuPoints = csuPoints;
    }

    public Long getCsuLocation() {
        return csuLocation;
    }

    public void setCsuLocation(Long csuLocation) {
        this.csuLocation = csuLocation;
    }

    public String getCsuServiceNo() {
        return csuServiceNo;
    }

    public void setCsuServiceNo(String csuServiceNo) {
        this.csuServiceNo = csuServiceNo;
    }

    public String getPrdName() {
        return prdName;
    }

    public void setPrdName(String prdName) {
        this.prdName = prdName;
    }

    public String getCusLoyaltyId() {
        return cusLoyaltyId;
    }

    public void setCusLoyaltyId(String cusLoyaltyId) {
        this.cusLoyaltyId = cusLoyaltyId;
    }

    public String getCusFName() {
        return cusFName;
    }

    public void setCusFName(String cusFName) {
        this.cusFName = cusFName;
    }

    public String getCusEmail() {
        return cusEmail;
    }

    public void setCusEmail(String cusEmail) {
        this.cusEmail = cusEmail;
    }

    public String getCusMobile() {
        return cusMobile;
    }

    public void setCusMobile(String cusMobile) {
        this.cusMobile = cusMobile;
    }


    @Override
    public String toString() {
        return "CustomerSubscriptionResource{" +
                "csuId=" + csuId +
                ", csuCustomerNo=" + csuCustomerNo +
                ", csuMerchantNo=" + csuMerchantNo +
                ", csuProductCode='" + csuProductCode + '\'' +
                ", csuPoints=" + csuPoints +
                ", csuLocation=" + csuLocation +
                ", csuServiceNo='" + csuServiceNo + '\'' +
                ", prdName='" + prdName + '\'' +
                ", cusLoyaltyId='" + cusLoyaltyId + '\'' +
                ", cusFName='" + cusFName + '\'' +
                ", cusEmail='" + cusEmail + '\'' +
                ", cusMobile='" + cusMobile + '\'' +
                '}';
    }
}
