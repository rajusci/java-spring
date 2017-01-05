package com.inspirenetz.api.rest.resource;

import java.sql.Date;
import java.sql.Timestamp;

/**
 * Created by sandheepgr on 15/4/14.
 */
public class CustomerPromotionalEventResource extends BaseResource {



    private Long cpeId;
    private Long cpeMerchantNo = 1L;
    private String cpeLoyaltyId ;
    private Long cpeEventId ;
    private Timestamp cpeTimeStamp;
    private String cpeReference;
    private String cpeProduct;
    private Date cpeDate;
    private String productName="";

    public Long getCpeId() {
        return cpeId;
    }

    public void setCpeId(Long cpeId) {
        this.cpeId = cpeId;
    }

    public Long getCpeMerchantNo() {
        return cpeMerchantNo;
    }

    public void setCpeMerchantNo(Long cpeMerchantNo) {
        this.cpeMerchantNo = cpeMerchantNo;
    }

    public String getCpeLoyaltyId() {
        return cpeLoyaltyId;
    }

    public void setCpeLoyaltyId(String cpeLoyaltyId) {
        this.cpeLoyaltyId = cpeLoyaltyId;
    }

    public Long getCpeEventId() {
        return cpeEventId;
    }

    public void setCpeEventId(Long cpeEventId) {
        this.cpeEventId = cpeEventId;
    }

    public Timestamp getCpeTimeStamp() {
        return cpeTimeStamp;
    }

    public void setCpeTimeStamp(Timestamp cpeTimeStamp) {
        this.cpeTimeStamp = cpeTimeStamp;
    }

    public String getCpeReference() {
        return cpeReference;
    }

    public void setCpeReference(String cpeReference) {
        this.cpeReference = cpeReference;
    }

    public Date getCpeDate() {
        return cpeDate;
    }

    public void setCpeDate(Date cpeDate) {
        this.cpeDate = cpeDate;
    }

    public String getCpeProduct() {

        return cpeProduct;
    }

    public void setCpeProduct(String cpeProduct) {
        this.cpeProduct = cpeProduct;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    @Override
    public String toString() {
        return "CustomerPromotionalEventResource{" +
                "cpeId=" + cpeId +
                ", cpeMerchantNo=" + cpeMerchantNo +
                ", cpeLoyaltyId='" + cpeLoyaltyId + '\'' +
                ", cpeEventId=" + cpeEventId +
                ", cpeTimeStamp=" + cpeTimeStamp +
                ", cpeReference='" + cpeReference + '\'' +
                ", cpeProduct='" + cpeProduct + '\'' +
                ", cpeDate=" + cpeDate +
                ", productName='" + productName + '\'' +
                '}';
    }
}
