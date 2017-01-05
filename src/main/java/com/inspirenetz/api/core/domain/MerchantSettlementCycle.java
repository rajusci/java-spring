package com.inspirenetz.api.core.domain;

import javax.persistence.*;
import java.sql.Date;

/**
 * Created by saneeshci on 20/10/15.
 */
@Entity
@Table(name="MERCHANT_SETTLEMENT_CYCLE")
public class MerchantSettlementCycle extends AuditedEntity {


    @Column(name = "MSC_ID",nullable = false)
    @Basic(fetch = FetchType.EAGER)
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long mscId;

    @Column(name = "MSC_MERCHANT_NO",nullable = false)
    @Basic(fetch = FetchType.EAGER)
    private Long mscMerchantNo;

    @Column(name = "MSC_REDEMPTION_MERCHANT",nullable = false)
    @Basic(fetch = FetchType.EAGER)
    private Long mscRedemptionMerchant;

    @Column(name = "MSC_MERCHANT_LOCATION",nullable = false)
    @Basic(fetch = FetchType.EAGER)
    private Long mscMerchantLocation;

    @Column(name = "MSC_STATUS" ,nullable = false)
    @Basic(fetch = FetchType.EAGER)
    private Integer mscStatus;


    @Column(name = "MSC_AMOUNT" ,nullable = true)
    @Basic(fetch = FetchType.EAGER)
    private Double mscAmount;

    @Column(name = "MSC_REFERENCE" ,nullable = true)
    @Basic(fetch = FetchType.EAGER)
    private String mscReference;

    @Column(name = "MSC_START_DATE" ,nullable = false)
    @Basic(fetch = FetchType.EAGER)
    private Date mscStartDate;

    @Column(name = "MSC_END_DATE" ,nullable = false)
    @Basic(fetch = FetchType.EAGER)
    private Date mscEndDate;

    @Column(name = "MSC_SETTLED_BY" ,nullable = true)
    @Basic(fetch = FetchType.EAGER)
    private String mscSettledBy;

    @Column(name = "MSC_SETTLED_DATE" ,nullable = true)
    @Basic(fetch = FetchType.EAGER)
    private Date mscSettledDate;

    public Long getMscId() {
        return mscId;
    }

    public void setMscId(Long mscId) {
        this.mscId = mscId;
    }

    public Long getMscMerchantNo() {
        return mscMerchantNo;
    }

    public void setMscMerchantNo(Long mscMerchantNo) {
        this.mscMerchantNo = mscMerchantNo;
    }

    public Long getMscRedemptionMerchant() {
        return mscRedemptionMerchant;
    }

    public void setMscRedemptionMerchant(Long mscRedemptionMerchant) {
        this.mscRedemptionMerchant = mscRedemptionMerchant;
    }

    public Long getMscMerchantLocation() {
        return mscMerchantLocation;
    }

    public void setMscMerchantLocation(Long mscMerchantLocation) {
        this.mscMerchantLocation = mscMerchantLocation;
    }

    public Integer getMscStatus() {
        return mscStatus;
    }

    public void setMscStatus(Integer mscStatus) {
        this.mscStatus = mscStatus;
    }

    public String getMscReference() {
        return mscReference;
    }

    public void setMscReference(String mscReference) {
        this.mscReference = mscReference;
    }

    public Date getMscStartDate() {
        return mscStartDate;
    }

    public void setMscStartDate(Date mscStartDate) {
        this.mscStartDate = mscStartDate;
    }

    public Date getMscEndDate() {
        return mscEndDate;
    }

    public void setMscEndDate(Date mscEndDate) {
        this.mscEndDate = mscEndDate;
    }

    public String getMscSettledBy() {
        return mscSettledBy;
    }

    public void setMscSettledBy(String mscSettledBy) {
        this.mscSettledBy = mscSettledBy;
    }

    public Date getMscSettledDate() {
        return mscSettledDate;
    }

    public void setMscSettledDate(Date mscSettledDate) {
        this.mscSettledDate = mscSettledDate;
    }

    public Double getMscAmount() {
        return mscAmount;
    }

    public void setMscAmount(Double mscAmount) {
        this.mscAmount = mscAmount;
    }

    @Override
    public String toString() {
        return "MerchantSettlementCycle{" +
                "mscId=" + mscId +
                ", mscMerchantNo=" + mscMerchantNo +
                ", mscRedemptionMerchant=" + mscRedemptionMerchant +
                ", mscMerchantLocation=" + mscMerchantLocation +
                ", mscStatus=" + mscStatus +
                ", mscAmount='" + mscAmount + '\'' +
                ", mscReference='" + mscReference + '\'' +
                ", mscStartDate=" + mscStartDate +
                ", mscEndDate=" + mscEndDate +
                ", mscSettledBy='" + mscSettledBy + '\'' +
                ", mscSettledDate=" + mscSettledDate +
                '}';
    }
}
