package com.inspirenetz.api.rest.resource;

import com.inspirenetz.api.core.dictionary.IndicatorStatus;
import org.springframework.hateoas.ResourceSupport;

import java.sql.Date;
import java.sql.Timestamp;

/**
 * Created by fayiz on 27/4/15.
 */
public class CustomerReferralResource extends ResourceSupport {


    private Long csrId;
    private Long csrMerchantNo = 0L;
    private String csrLoyaltyId ;
    private String csrFName ;
    private String csrRefName ;
    private String csrRefMobile ;
    private String csrRefEmail="";
    private Integer csrRefStatus= IndicatorStatus.NO;
    private String csrRefAddress ="";
    private Long csrUserNo;
    private String csrProduct;
    private Long csrLocation;
    private String  productName;
    private String csrRefNo="0";
    private String csrUserFName;
    private String csrLocationName;
    private String csrRefMobileCountryCode  = "";

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public Long getCsrId() {
        return csrId;
    }

    public void setCsrId(Long csrId) {
        this.csrId = csrId;
    }

    public Long getCsrMerchantNo() {
        return csrMerchantNo;
    }

    public void setCsrMerchantNo(Long csrMerchantNo) {
        this.csrMerchantNo = csrMerchantNo;
    }

    public String getCsrLoyaltyId() {
        return csrLoyaltyId;
    }

    public void setCsrLoyaltyId(String csrLoyaltyId) {
        this.csrLoyaltyId = csrLoyaltyId;
    }

    public String getCsrRefName() {
        return csrRefName;
    }

    public void setCsrRefName(String csrRefName) {
        this.csrRefName = csrRefName;
    }

    public String getCsrRefMobile() {
        return csrRefMobile;
    }

    public void setCsrRefMobile(String csrRefMobile) {
        this.csrRefMobile = csrRefMobile;
    }

    public String getCsrRefEmail() {
        return csrRefEmail;
    }

    public void setCsrRefEmail(String csrRefEmail) {
        this.csrRefEmail = csrRefEmail;
    }

    public Integer getCsrRefStatus() {
        return csrRefStatus;
    }

    public void setCsrRefStatus(Integer csrRefStatus) {
        this.csrRefStatus = csrRefStatus;
    }

    public String getCsrRefAddress() {
        return csrRefAddress;
    }

    public void setCsrRefAddress(String csrRefAddress) {
        this.csrRefAddress = csrRefAddress;
    }

    public Long getCsrUserNo() {
        return csrUserNo;
    }

    public void setCsrUserNo(Long csrUserNo) {
        this.csrUserNo = csrUserNo;
    }

    public String getCsrProduct() {
        return csrProduct;
    }

    public void setCsrProduct(String csrProduct) {
        this.csrProduct = csrProduct;
    }

    public String getCsrFName() {
        return csrFName;
    }

    public void setCsrFName(String csrFName) {
        this.csrFName = csrFName;
    }

    public Long getCsrLocation() {
        return csrLocation;
    }

    public void setCsrLocation(Long csrLocation) {
        this.csrLocation = csrLocation;
    }

    public String getCsrRefNo() {
        return csrRefNo;
    }

    public void setCsrRefNo(String csrRefNo) {
        this.csrRefNo = csrRefNo;
    }

    public String getCsrUserFName() {
        return csrUserFName;
    }

    public void setCsrUserFName(String csrUserFName) {
        this.csrUserFName = csrUserFName;
    }

    public String getCsrLocationName() {
        return csrLocationName;
    }

    public void setCsrLocationName(String csrLocationName) {
        this.csrLocationName = csrLocationName;
    }

    public String getCsrRefMobileCountryCode() {
        return csrRefMobileCountryCode;
    }

    public void setCsrRefMobileCountryCode(String csrRefMobileCountryCode) {
        this.csrRefMobileCountryCode = csrRefMobileCountryCode;
    }
}
