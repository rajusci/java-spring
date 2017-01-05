package com.inspirenetz.api.core.domain;

import javax.persistence.*;
import java.sql.Date;

/**
 * Created by sandheepgr on 21/5/14.
 */
@Entity
@Table(name = "MERCHANT_REWARD_SUMMARY")
public class MerchantRewardSummary extends AuditedEntity {

    @Id
    @Column(name = "MRS_ID", nullable = false, insertable = true, updatable = true, length = 10, precision = 0)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long mrsId;

    @Column(name = "MRS_MERCHANT_NO", nullable = false, insertable = true, updatable = true, length = 10, precision = 0)
    private Long mrsMerchantNo;

    @Column(name = "MRS_CURRENCY_ID", nullable = false, insertable = true, updatable = true, length = 10, precision = 0)
    private Long mrsCurrencyId;

    @Column(name = "MRS_BRANCH", nullable = false, insertable = true, updatable = true, length = 10, precision = 0)
    private Long mrsBranch;

    @Column(name = "MRS_DATE", nullable = false, insertable = true, updatable = true, length = 10, precision = 0)
    private Date mrsDate;

    @Basic
    @Column(name = "MRS_TOTAL_REWARDED", nullable = false, insertable = true, updatable = true, length = 10, precision = 2)
    private Double mrsTotalRewarded = 0.0;

    @Basic
    @Column(name = "MRS_TOTAL_REDEEMED", nullable = false, insertable = true, updatable = true, length = 10, precision = 2)
    private Double mrsTotalRedeemed = 0.0;

    @Basic
    @Column(name = "MRS_REWARD_EXPIRED", nullable = false, insertable = true, updatable = true, length = 10, precision = 2)
    private Double mrsRewardExpired = 0.0;



    public Long getMrsId() {
        return mrsId;
    }

    public void setMrsId(Long mrsId) {
        this.mrsId = mrsId;
    }

    public Long getMrsMerchantNo() {
        return mrsMerchantNo;
    }

    public void setMrsMerchantNo(Long mrsMerchantNo) {
        this.mrsMerchantNo = mrsMerchantNo;
    }

    public Long getMrsCurrencyId() {
        return mrsCurrencyId;
    }

    public void setMrsCurrencyId(Long mrsCurrencyId) {
        this.mrsCurrencyId = mrsCurrencyId;
    }

    public Long getMrsBranch() {
        return mrsBranch;
    }

    public void setMrsBranch(Long mrsBranch) {
        this.mrsBranch = mrsBranch;
    }

    public Date getMrsDate() {
        return mrsDate;
    }

    public void setMrsDate(Date mrsDate) {
        this.mrsDate = mrsDate;
    }

    public Double getMrsTotalRewarded() {
        return mrsTotalRewarded;
    }

    public void setMrsTotalRewarded(Double mrsTotalRewarded) {
        this.mrsTotalRewarded = mrsTotalRewarded;
    }

    public Double getMrsTotalRedeemed() {
        return mrsTotalRedeemed;
    }

    public void setMrsTotalRedeemed(Double mrsTotalRedeemed) {
        this.mrsTotalRedeemed = mrsTotalRedeemed;
    }

    public Double getMrsRewardExpired() {
        return mrsRewardExpired;
    }

    public void setMrsRewardExpired(Double mrsRewardExpired) {
        this.mrsRewardExpired = mrsRewardExpired;
    }


    @Override
    public String toString() {
        return "MerchantRewardSummary{" +
                "mrsId=" + mrsId +
                ", mrsMerchantNo=" + mrsMerchantNo +
                ", mrsCurrencyId=" + mrsCurrencyId +
                ", mrsBranch=" + mrsBranch +
                ", mrsDate=" + mrsDate +
                ", mrsTotalRewarded=" + mrsTotalRewarded +
                ", mrsTotalRedeemed=" + mrsTotalRedeemed +
                ", mrsRewardExpired=" + mrsRewardExpired +
                '}';
    }
}
