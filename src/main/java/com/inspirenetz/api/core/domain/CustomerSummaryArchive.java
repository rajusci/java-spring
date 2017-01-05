package com.inspirenetz.api.core.domain;

import javax.persistence.*;

/**
 * Created by sandheepgr on 29/3/14.
 */
@Entity
@Table(name = "CUSTOMER_SUMMARY_ARCHIVE")
public class CustomerSummaryArchive extends AuditedEntity {

    @Id
    @Column(name = "CSA_ID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long csaId;

    @Column(name = "CSA_MERCHANT_NO")
    private Long csaMerchantNo;

    @Column(name = "CSA_LOYALTY_ID")
    private String csaLoyaltyId = "0";

    @Column(name = "CSA_LOCATION")
    private Long csaLocation = 0L;

    @Column(name = "CSA_PERIOD_YYYY")
    private int csaPeriodYyyy = 0 ;

    @Column(name = "CSA_PERIOD_QQ")
    private int csaPeriodQq = 0;

    @Column(name = "CSA_PERIOD_MM")
    private int csaPeriodMm = 0;

    @Basic(fetch = FetchType.EAGER)
    @Column(name = "CSA_VISIT_COUNT")
    private int csaVisitCount = 0;

    @Basic(fetch = FetchType.EAGER)
    @Column(name = "CSA_QUANTITY")
    private int csaQuantity = 0;

    @Basic(fetch = FetchType.EAGER)
    @Column(name = "CSA_TXN_AMOUNT")
    private double csaTxnAmount = 0;


    public Long getCsaId() {
        return csaId;
    }

    public void setCsaId(Long csaId) {
        this.csaId = csaId;
    }

    public Long getCsaMerchantNo() {
        return csaMerchantNo;
    }

    public void setCsaMerchantNo(Long csaMerchantNo) {
        this.csaMerchantNo = csaMerchantNo;
    }

    public String getCsaLoyaltyId() {
        return csaLoyaltyId;
    }

    public void setCsaLoyaltyId(String csaLoyaltyId) {
        this.csaLoyaltyId = csaLoyaltyId;
    }

    public Long getCsaLocation() {
        return csaLocation;
    }

    public void setCsaLocation(Long csaLocation) {
        this.csaLocation = csaLocation;
    }

    public int getCsaPeriodYyyy() {
        return csaPeriodYyyy;
    }

    public void setCsaPeriodYyyy(int csaPeriodYyyy) {
        this.csaPeriodYyyy = csaPeriodYyyy;
    }

    public int getCsaPeriodQq() {
        return csaPeriodQq;
    }

    public void setCsaPeriodQq(int csaPeriodQq) {
        this.csaPeriodQq = csaPeriodQq;
    }

    public int getCsaPeriodMm() {
        return csaPeriodMm;
    }

    public void setCsaPeriodMm(int csaPeriodMm) {
        this.csaPeriodMm = csaPeriodMm;
    }

    public int getCsaVisitCount() {
        return csaVisitCount;
    }

    public void setCsaVisitCount(int csaVisitCount) {
        this.csaVisitCount = csaVisitCount;
    }

    public int getCsaQuantity() {
        return csaQuantity;
    }

    public void setCsaQuantity(int csaQuantity) {
        this.csaQuantity = csaQuantity;
    }

    public double getCsaTxnAmount() {
        return csaTxnAmount;
    }

    public void setCsaTxnAmount(double csaTxnAmount) {
        this.csaTxnAmount = csaTxnAmount;
    }


    @Override
    public String toString() {
        return "CustomerSummaryArchive{" +
                "csaId=" + csaId +
                ", csaMerchantNo=" + csaMerchantNo +
                ", csaLoyaltyId='" + csaLoyaltyId + '\'' +
                ", csaLocation=" + csaLocation +
                ", csaPeriodYyyy=" + csaPeriodYyyy +
                ", csaPeriodQq=" + csaPeriodQq +
                ", csaPeriodMm=" + csaPeriodMm +
                ", csaVisitCount=" + csaVisitCount +
                ", csaQuantity=" + csaQuantity +
                ", csaTxnAmount=" + csaTxnAmount +
                '}';
    }
}
