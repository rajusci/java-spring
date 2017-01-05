package com.inspirenetz.api.core.domain;

import javax.persistence.*;

/**
 * Created by sandheepgr on 29/3/14.
 */
@Entity
@Table(name = "MERCHANT_LOYALTY_IDS")
public class MerchantLoyaltyId {

    @Id
    @Column(name = "MLI_MERCHANT_NO")
    private Long mliMerchantNo;

    @Basic(fetch = FetchType.EAGER)
    @Column(name = "MLI_LOYALTY_ID_INDEX")
    private long mliLoyaltyIdIndex;

    @Basic(fetch = FetchType.EAGER)
    @Column(name = "MLI_LOYALTY_ID_FROM")
    private long mliLoyaltyIdFrom;

    @Basic(fetch = FetchType.EAGER)
    @Column(name = "MLI_LOYALTY_ID_TO")
    private long mliLoyaltyIdTo;


    public Long getMliMerchantNo() {
        return mliMerchantNo;
    }

    public void setMliMerchantNo(Long mliMerchantNo) {
        this.mliMerchantNo = mliMerchantNo;
    }

    public long getMliLoyaltyIdIndex() {
        return mliLoyaltyIdIndex;
    }

    public void setMliLoyaltyIdIndex(long mliLoyaltyIdIndex) {
        this.mliLoyaltyIdIndex = mliLoyaltyIdIndex;
    }

    public long getMliLoyaltyIdFrom() {
        return mliLoyaltyIdFrom;
    }

    public void setMliLoyaltyIdFrom(long mliLoyaltyIdFrom) {
        this.mliLoyaltyIdFrom = mliLoyaltyIdFrom;
    }

    public long getMliLoyaltyIdTo() {
        return mliLoyaltyIdTo;
    }

    public void setMliLoyaltyIdTo(long mliLoyaltyIdTo) {
        this.mliLoyaltyIdTo = mliLoyaltyIdTo;
    }


    @Override
    public String toString() {
        return "MerchantLoyaltyId{" +
                "mliMerchantNo=" + mliMerchantNo +
                ", mliLoyaltyIdIndex=" + mliLoyaltyIdIndex +
                ", mliLoyaltyIdFrom=" + mliLoyaltyIdFrom +
                ", mliLoyaltyIdTo=" + mliLoyaltyIdTo +
                '}';
    }
}
