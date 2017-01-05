package com.inspirenetz.api.test.core.builder;

import com.inspirenetz.api.core.domain.VoucherCode;

import java.util.Date;

/**
 * Created by ameen on 17/2/15.
 */
public class VoucherCodeBuilder {
    private Long vocId;
    private Long vocMerchantNo;
    private Long vocVoucherSource;
    private Long vocIndex;
    private String vocVoucherCode;
    private Date createdAt;
    private String createdBy;
    private Date updatedAt;
    private String updatedBy;

    private VoucherCodeBuilder() {
    }

    public static VoucherCodeBuilder aVoucherCode() {
        return new VoucherCodeBuilder();
    }

    public VoucherCodeBuilder withVocId(Long vocId) {
        this.vocId = vocId;
        return this;
    }

    public VoucherCodeBuilder withVocMerchantNo(Long vocMerchantNo) {
        this.vocMerchantNo = vocMerchantNo;
        return this;
    }

    public VoucherCodeBuilder withVocVoucherSource(Long vocVoucherSource) {
        this.vocVoucherSource = vocVoucherSource;
        return this;
    }

    public VoucherCodeBuilder withVocIndex(Long vocIndex) {
        this.vocIndex = vocIndex;
        return this;
    }

    public VoucherCodeBuilder withVocVoucherCode(String vocVoucherCode) {
        this.vocVoucherCode = vocVoucherCode;
        return this;
    }

    public VoucherCodeBuilder withCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
        return this;
    }

    public VoucherCodeBuilder withCreatedBy(String createdBy) {
        this.createdBy = createdBy;
        return this;
    }

    public VoucherCodeBuilder withUpdatedAt(Date updatedAt) {
        this.updatedAt = updatedAt;
        return this;
    }

    public VoucherCodeBuilder withUpdatedBy(String updatedBy) {
        this.updatedBy = updatedBy;
        return this;
    }

    public VoucherCodeBuilder but() {
        return aVoucherCode().withVocId(vocId).withVocMerchantNo(vocMerchantNo).withVocVoucherSource(vocVoucherSource).withVocIndex(vocIndex).withVocVoucherCode(vocVoucherCode).withCreatedAt(createdAt).withCreatedBy(createdBy).withUpdatedAt(updatedAt).withUpdatedBy(updatedBy);
    }

    public VoucherCode build() {
        VoucherCode voucherCode = new VoucherCode();
        voucherCode.setVocId(vocId);
        voucherCode.setVocMerchantNo(vocMerchantNo);
        voucherCode.setVocVoucherSource(vocVoucherSource);
        voucherCode.setVocIndex(vocIndex);
        voucherCode.setVocVoucherCode(vocVoucherCode);
        voucherCode.setCreatedAt(createdAt);
        voucherCode.setCreatedBy(createdBy);
        voucherCode.setUpdatedAt(updatedAt);
        voucherCode.setUpdatedBy(updatedBy);
        return voucherCode;
    }
}
