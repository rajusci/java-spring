package com.inspirenetz.api.test.core.builder;

import com.inspirenetz.api.core.domain.MerchantPartnerTransaction;

import java.util.Date;

/**
 * Created by abhi on 14/7/16.
 */
public final class MerchantPartnerTransactionBuilder {
    private Long mptId;
    private Long mptProductNo;
    private Long mptMerchantNo;
    private Long mptPartnerNo;
    private Integer mptQuantity;
    private Double mptPrice;
    private Integer mptSearchType;
    private Date createdAt;
    private String createdBy;
    private Date updatedAt;
    private String updatedBy;
    private Date mptTxnDate;


    private MerchantPartnerTransactionBuilder() {
    }

    public static MerchantPartnerTransactionBuilder aMerchantPartnerTransaction() {
        return new MerchantPartnerTransactionBuilder();
    }

    public MerchantPartnerTransactionBuilder withMptId(Long mptId) {
        this.mptId = mptId;
        return this;
    }

    public MerchantPartnerTransactionBuilder withMptProductNo(Long mptProductNo) {
        this.mptProductNo = mptProductNo;
        return this;
    }

    public MerchantPartnerTransactionBuilder withMptMerchantNo(Long mptMerchantNo) {
        this.mptMerchantNo = mptMerchantNo;
        return this;
    }

    public MerchantPartnerTransactionBuilder withMptPartnerNo(Long mptPartnerNo) {
        this.mptPartnerNo = mptPartnerNo;
        return this;
    }

    public MerchantPartnerTransactionBuilder withMptQuantity(Integer mptQuantity) {
        this.mptQuantity = mptQuantity;
        return this;
    }

    public MerchantPartnerTransactionBuilder withMptPrice(Double mptPrice) {
        this.mptPrice = mptPrice;
        return this;
    }

    public MerchantPartnerTransactionBuilder withMptTxnDate(Date mptTxnDate) {
        this.mptTxnDate = mptTxnDate;
        return this;
    }

    public MerchantPartnerTransactionBuilder withMptSearchType(Integer mptSearchType) {
        this.mptSearchType = mptSearchType;
        return this;
    }

    public MerchantPartnerTransactionBuilder withCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
        return this;
    }

    public MerchantPartnerTransactionBuilder withCreatedBy(String createdBy) {
        this.createdBy = createdBy;
        return this;
    }

    public MerchantPartnerTransactionBuilder withUpdatedAt(Date updatedAt) {
        this.updatedAt = updatedAt;
        return this;
    }

    public MerchantPartnerTransactionBuilder withUpdatedBy(String updatedBy) {
        this.updatedBy = updatedBy;
        return this;
    }

    public MerchantPartnerTransaction build() {
        MerchantPartnerTransaction merchantPartnerTransaction = new MerchantPartnerTransaction();
        merchantPartnerTransaction.setMptId(mptId);
        merchantPartnerTransaction.setMptProductNo(mptProductNo);
        merchantPartnerTransaction.setMptMerchantNo(mptMerchantNo);
        merchantPartnerTransaction.setMptPartnerNo(mptPartnerNo);
        merchantPartnerTransaction.setMptQuantity(mptQuantity);
        merchantPartnerTransaction.setMptPrice(mptPrice);
        merchantPartnerTransaction.setMptTxnDate(mptTxnDate);
        merchantPartnerTransaction.setCreatedAt(createdAt);
        merchantPartnerTransaction.setCreatedBy(createdBy);
        merchantPartnerTransaction.setUpdatedAt(updatedAt);
        merchantPartnerTransaction.setUpdatedBy(updatedBy);
        return merchantPartnerTransaction;
    }


}
