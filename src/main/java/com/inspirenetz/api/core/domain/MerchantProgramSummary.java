package com.inspirenetz.api.core.domain;

import javax.persistence.*;

/**
 * Created by sandheepgr on 21/5/14.
 */
@Entity
@Table(name = "MERCHANT_PROGRAM_SUMMARY")
public class MerchantProgramSummary extends AuditedEntity {

    @Id
    @Column(name = "MPS_ID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long mpsId;

    @Column(name = "MPS_MERCHANT_NO", nullable = false, insertable = true, updatable = true, length = 10, precision = 0)
    private Long mpsMerchantNo;

    @Column(name = "MPS_BRANCH", nullable = false, insertable = true, updatable = true, length = 10, precision = 0)
    private Long mpsBranch;

    @Column(name = "MPS_PROGRAM_ID", nullable = false, insertable = true, updatable = true, length = 10, precision = 0)
    private Long mpsProgramId;

    @Basic
    @Column(name = "MPS_TRANSACTION_AMOUNT", nullable = false, insertable = true, updatable = true, length = 10, precision = 2)
    private Double mpsTransactionAmount = 0.0;

    @Basic
    @Column(name = "MPS_REWARD_COUNT", nullable = false, insertable = true, updatable = true, length = 10, precision = 2)
    private Double mpsRewardCount = 0.0;

    @Basic
    @Column(name = "MPS_TRANSACTION_COUNT", nullable = false, insertable = true, updatable = true, length = 10, precision = 0)
    private int mpsTransactionCount = 0;


    public Long getMpsId() {
        return mpsId;
    }

    public void setMpsId(Long mpsId) {
        this.mpsId = mpsId;
    }

    public Long getMpsMerchantNo() {
        return mpsMerchantNo;
    }

    public void setMpsMerchantNo(Long mpsMerchantNo) {
        this.mpsMerchantNo = mpsMerchantNo;
    }

    public Long getMpsBranch() {
        return mpsBranch;
    }

    public void setMpsBranch(Long mpsBranch) {
        this.mpsBranch = mpsBranch;
    }

    public Long getMpsProgramId() {
        return mpsProgramId;
    }

    public void setMpsProgramId(Long mpsProgramId) {
        this.mpsProgramId = mpsProgramId;
    }

    public Double getMpsTransactionAmount() {
        return mpsTransactionAmount;
    }

    public void setMpsTransactionAmount(Double mpsTransactionAmount) {
        this.mpsTransactionAmount = mpsTransactionAmount;
    }

    public Double getMpsRewardCount() {
        return mpsRewardCount;
    }

    public void setMpsRewardCount(Double mpsRewardCount) {
        this.mpsRewardCount = mpsRewardCount;
    }

    public int getMpsTransactionCount() {
        return mpsTransactionCount;
    }

    public void setMpsTransactionCount(int mpsTransactionCount) {
        this.mpsTransactionCount = mpsTransactionCount;
    }


    @Override
    public String toString() {
        return "MerchantProgramSummary{" +
                "mpsId=" + mpsId +
                ", mpsMerchantNo=" + mpsMerchantNo +
                ", mpsBranch=" + mpsBranch +
                ", mpsProgramId=" + mpsProgramId +
                ", mpsTransactionAmount=" + mpsTransactionAmount +
                ", mpsRewardCount=" + mpsRewardCount +
                ", mpsTransactionCount=" + mpsTransactionCount +
                '}';
    }
}
