package com.inspirenetz.api.core.domain;

import com.inspirenetz.api.core.dictionary.IndicatorStatus;
import com.inspirenetz.api.core.dictionary.MerchantSettlementType;

import javax.persistence.*;
import java.sql.Date;

/**
 * Created by saneeshci on 27/09/14.
 */
@Entity
@Table(name="MERCHANT_SETTLEMENT")
public class MerchantSettlement extends AuditedEntity {


    @Column(name = "MES_ID",nullable = false)
    @Basic(fetch = FetchType.EAGER)
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long mesId;

    @Column(name = "MES_INTERNAL_REF",nullable = false)
    @Basic(fetch = FetchType.EAGER)
    private String mesInternalRef;

    @Column(name = "MES_LOYALTY_ID" ,nullable = false)
    @Basic(fetch = FetchType.EAGER)
    private String mesLoyaltyId;

    @Column(name = "MES_VENDOR_NO" ,nullable = false)
    @Basic(fetch = FetchType.EAGER)
    private Long mesVendorNo;

    @Column(name = "MES_IS_SETTLED" ,nullable = false)
    @Basic(fetch = FetchType.EAGER)
    private Integer mesIsSettled = IndicatorStatus.YES;

    @Column(name = "MES_SETTLEMENT_TYPE" ,nullable = false)
    @Basic(fetch = FetchType.EAGER)
    private Integer mesSettlementType = MerchantSettlementType.LOAD_WALLET;

    @Column(name = "MES_SETTLEMENT_REF" ,nullable = false)
    @Basic(fetch = FetchType.EAGER)
    private String mesSettlementRef;

    @Column(name = "MES_MERCHANT_NO" ,nullable = false)
    @Basic(fetch = FetchType.EAGER)
    private Long mesMerchantNo;

    @Column(name = "MES_LOCATION" ,nullable = false)
    @Basic(fetch = FetchType.EAGER)
    private Long mesLocation;

    @Column(name = "MES_DATE" ,nullable = false)
    @Basic(fetch = FetchType.EAGER)
    private Date mesDate;

    @Column(name = "MES_AMOUNT" ,nullable = false)
    @Basic(fetch = FetchType.EAGER)
    private double mesAmount;

    @Column(name = "MES_POINTS" ,nullable = false)
    @Basic(fetch = FetchType.EAGER)
    private double mesPoints;


    public Long getMesId() {
        return mesId;
    }

    public void setMesId(Long mesId) {
        this.mesId = mesId;
    }

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

    @Override
    public String toString() {
        return "MerchantSettlement{" +
                "mesId=" + mesId +
                ", mesInternalRef='" + mesInternalRef + '\'' +
                ", mesLoyaltyId='" + mesLoyaltyId + '\'' +
                ", mesVendorNo=" + mesVendorNo +
                ", mesIsSettled=" + mesIsSettled +
                ", mesSettlementType=" + mesSettlementType +
                ", mesSettlementRef='" + mesSettlementRef + '\'' +
                ", mesMerchantNo=" + mesMerchantNo +
                ", mesLocation=" + mesLocation +
                ", mesDate=" + mesDate +
                ", mesAmount=" + mesAmount +
                ", mesPoints=" + mesPoints +
                '}';
    }
}
