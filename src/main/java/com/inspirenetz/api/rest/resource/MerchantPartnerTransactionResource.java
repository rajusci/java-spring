package com.inspirenetz.api.rest.resource;

import com.inspirenetz.api.core.repository.BaseRepository;

import java.sql.Date;

/**
 * Created by abhi on 13/7/16.
 */
public class MerchantPartnerTransactionResource extends BaseResource {

    private Long mptId;

    private Long mptProductNo;

    private Long mptMerchantNo;

    private Long mptPartnerNo;

    private Integer mptQuantity;

    private Double mptPrice;

    private Date mptTxnDate;

    private Integer mptSearchType;

    public Long getMptId() {
        return mptId;
    }

    public void setMptId(Long mptId) {
        this.mptId = mptId;
    }

    public Long getMptProductNo() {
        return mptProductNo;
    }

    public void setMptProductNo(Long mptProductNo) {
        this.mptProductNo = mptProductNo;
    }

    public Long getMptMerchantNo() {
        return mptMerchantNo;
    }

    public void setMptMerchantNo(Long mptMerchantNo) {
        this.mptMerchantNo = mptMerchantNo;
    }

    public Long getMptPartnerNo() {
        return mptPartnerNo;
    }

    public void setMptPartnerNo(Long mptPartnerNo) {
        this.mptPartnerNo = mptPartnerNo;
    }

    public Integer getMptQuantity() {
        return mptQuantity;
    }

    public void setMptQuantity(Integer mptQuantity) {
        this.mptQuantity = mptQuantity;
    }

    public Double getMptPrice() {
        return mptPrice;
    }

    public void setMptPrice(Double mptPrice) {
        this.mptPrice = mptPrice;
    }

    public Date getMptTxnDate() {
        return mptTxnDate;
    }

    public void setMptTxnDate(Date mptTxnDate) {
        this.mptTxnDate = mptTxnDate;
    }

    public Integer getMptSearchType() {
        return mptSearchType;
    }

    public void setMptSearchType(Integer mptSearchType) {
        this.mptSearchType = mptSearchType;
    }

    @Override
    public String toString() {
        return "MerchantPartnerTransactionResource{" +
                "mptId=" + mptId +
                ", mptProductNo=" + mptProductNo +
                ", mptMerchantNo=" + mptMerchantNo +
                ", mptPartnerNo=" + mptPartnerNo +
                ", mptQuantity=" + mptQuantity +
                ", mptPrice=" + mptPrice +
                ", mptTxnDate=" + mptTxnDate +
                ", mptSearchType=" + mptSearchType +
                '}';
    }
}
