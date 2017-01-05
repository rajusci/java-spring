package com.inspirenetz.api.rest.resource;

import java.sql.Date;

/**
 * Created by sandheepgr on 15/4/14.
 */
public class MerchantSettlementResource extends BaseResource {



    private Long mesId;
    private String mesInternalRef;
    private String mesLoyaltyId;
    private Long mesVendorNo;
    private Integer mesIsSettled;
    private Integer mesSettlementType;
    private String mesSettlementRef;
    private Long mesMerchantNo;
    private Long mesLocation;
    private Date mesDate;
    private double mesAmount;
    private double mesPoints;
    private Integer isSettled = 0;

    public String getMesInternalRef() {
        return mesInternalRef;
    }

    public void setMesInternalRef(String mesInternalRef) {
        this.mesInternalRef = mesInternalRef;
    }

    public String getMesLoyaltyId() {
        return mesLoyaltyId;
    }

    public void setMesLoyaltyId(String mesLoyaltyId) {
        this.mesLoyaltyId = mesLoyaltyId;
    }

    public Long getMesVendorNo() {
        return mesVendorNo;
    }

    public void setMesVendorNo(Long mesVendorNo) {
        this.mesVendorNo = mesVendorNo;
    }

    public Integer getMesIsSettled() {
        return mesIsSettled;
    }

    public void setMesIsSettled(Integer mesIsSettled) {
        this.mesIsSettled = mesIsSettled;
    }

    public Integer getMesSettlementType() {
        return mesSettlementType;
    }

    public void setMesSettlementType(Integer mesSettlementType) {
        this.mesSettlementType = mesSettlementType;
    }

    public String getMesSettlementRef() {
        return mesSettlementRef;
    }

    public void setMesSettlementRef(String mesSettlementRef) {
        this.mesSettlementRef = mesSettlementRef;
    }

    public Long getMesMerchantNo() {
        return mesMerchantNo;
    }

    public void setMesMerchantNo(Long mesMerchantNo) {
        this.mesMerchantNo = mesMerchantNo;
    }

    public Long getMesLocation() {
        return mesLocation;
    }

    public void setMesLocation(Long mesLocation) {
        this.mesLocation = mesLocation;
    }

    public Date getMesDate() {
        return mesDate;
    }

    public void setMesDate(Date mesDate) {
        this.mesDate = mesDate;
    }

    public double getMesAmount() {
        return mesAmount;
    }

    public void setMesAmount(double mesAmount) {
        this.mesAmount = mesAmount;
    }

    public double getMesPoints() {
        return mesPoints;
    }

    public void setMesPoints(double mesPoints) {
        this.mesPoints = mesPoints;
    }

    public Long getMesId() {

        return mesId;
    }

    public void setMesId(Long mesId) {
        this.mesId = mesId;
    }

    public Integer getIsSettled() {
        return isSettled;
    }

    public void setIsSettled(Integer isSettled) {
        this.isSettled = isSettled;
    }
}
