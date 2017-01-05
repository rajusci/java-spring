package com.inspirenetz.api.rest.resource;

import java.sql.Date;

/**
 * Created by sandheepgr on 15/4/14.
 */
public class LinkRequestResource extends BaseResource {


    private Long lrqId;

    private Long lrqMerchantNo;

    private Long lrqSourceCustomer;

    private Long lrqParentCustomer;

    private Long lrqStatus;

    private Integer lrqType;

    private String lrqRequestSourceRef;

    private Integer lrqRequestSource;

    private Integer lrqInitiator;

    private Date lrqRequestDate;

    private String lrqRemarks;



    private String cusFName;

    private String cusLName;

    private String cusLoyaltyId;

    private String cusEmail;

    private String cusMobile;




    public Long getLrqId() {
        return lrqId;
    }

    public void setLrqId(Long lrqId) {
        this.lrqId = lrqId;
    }

    public Long getLrqSourceCustomer() {
        return lrqSourceCustomer;
    }

    public void setLrqSourceCustomer(Long lrqSourceCustomer) {
        this.lrqSourceCustomer = lrqSourceCustomer;
    }

    public Long getLrqParentCustomer() {
        return lrqParentCustomer;
    }

    public void setLrqParentCustomer(Long lrqParentCustomer) {
        this.lrqParentCustomer = lrqParentCustomer;
    }

    public Long getLrqStatus() {
        return lrqStatus;
    }

    public void setLrqStatus(Long lrqStatus) {
        this.lrqStatus = lrqStatus;
    }

    public String getLrqRequestSourceRef() {
        return lrqRequestSourceRef;
    }

    public void setLrqRequestSourceRef(String lrqRequestSourceRef) {
        this.lrqRequestSourceRef = lrqRequestSourceRef;
    }

    public Date getLrqRequestDate() {
        return lrqRequestDate;
    }

    public void setLrqRequestDate(Date lrqRequestDate) {
        this.lrqRequestDate = lrqRequestDate;
    }

    public String getLrqRemarks() {
        return lrqRemarks;
    }

    public void setLrqRemarks(String lrqRemarks) {
        this.lrqRemarks = lrqRemarks;
    }

    public String getCusFName() {
        return cusFName;
    }

    public void setCusFName(String cusFName) {
        this.cusFName = cusFName;
    }

    public String getCusLName() {
        return cusLName;
    }

    public void setCusLName(String cusLName) {
        this.cusLName = cusLName;
    }

    public String getCusLoyaltyId() {
        return cusLoyaltyId;
    }

    public void setCusLoyaltyId(String cusLoyaltyId) {
        this.cusLoyaltyId = cusLoyaltyId;
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

    public Long getLrqMerchantNo() {
        return lrqMerchantNo;
    }

    public void setLrqMerchantNo(Long lrqMerchantNo) {
        this.lrqMerchantNo = lrqMerchantNo;
    }

    public Integer getLrqType() {
        return lrqType;
    }

    public void setLrqType(Integer lrqType) {
        this.lrqType = lrqType;
    }

    public void setLrqRequestSource(Integer lrqRequestSource) {
        this.lrqRequestSource = lrqRequestSource;
    }

    public Integer getLrqInitiator() {
        return lrqInitiator;
    }

    public void setLrqInitiator(Integer lrqInitiator) {
        this.lrqInitiator = lrqInitiator;
    }


    @Override
    public String toString() {
        return "LinkRequestResource{" +
                "lrqId=" + lrqId +
                ", lrqMerchantNo=" + lrqMerchantNo +
                ", lrqSourceCustomer=" + lrqSourceCustomer +
                ", lrqParentCustomer=" + lrqParentCustomer +
                ", lrqStatus=" + lrqStatus +
                ", lrqType=" + lrqType +
                ", lrqRequestSourceRef='" + lrqRequestSourceRef + '\'' +
                ", lrqRequestSource=" + lrqRequestSource +
                ", lrqInitiator=" + lrqInitiator +
                ", lrqRequestDate=" + lrqRequestDate +
                ", lrqRemarks='" + lrqRemarks + '\'' +
                ", cusFName='" + cusFName + '\'' +
                ", cusLName='" + cusLName + '\'' +
                ", cusLoyaltyId='" + cusLoyaltyId + '\'' +
                ", cusEmail='" + cusEmail + '\'' +
                ", cusMobile='" + cusMobile + '\'' +
                '}';
    }
}
