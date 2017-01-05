package com.inspirenetz.api.core.domain;

import org.hibernate.validator.constraints.NotEmpty;

import javax.persistence.*;

/**
 * Created by alameen on 10/2/15.
 */
@Entity
@Table(name = "VOUCHER_CODES")
public class VoucherCode extends AuditedEntity{


    @Column(name = "VOC_ID",nullable = false)
    @Basic(fetch = FetchType.EAGER)
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long vocId;

    @Column(name = "VOC_MERCHANT_NO",nullable = false)
    @Basic(fetch = FetchType.EAGER)
    private Long vocMerchantNo;

    @Column(name = "VOC_VOUCHER_SOURCE")
    @Basic(fetch = FetchType.EAGER)
    private Long vocVoucherSource;

    @Column(name = "VOC_INDEX")
    @Basic(fetch = FetchType.EAGER)
    private Long vocIndex;

    @Column(name = "VOC_VOUCHER_CODE")
    @Basic(fetch = FetchType.EAGER)
    @NotEmpty(message = "{vouchercode.vocvouchercode.notempty}")
    private String vocVoucherCode;

    public Long getVocId() {
        return vocId;
    }

    public void setVocId(Long vocId) {
        this.vocId = vocId;
    }

    public Long getVocMerchantNo() {
        return vocMerchantNo;
    }

    public void setVocMerchantNo(Long vocMerchantNo) {
        this.vocMerchantNo = vocMerchantNo;
    }

    public Long getVocVoucherSource() {
        return vocVoucherSource;
    }

    public void setVocVoucherSource(Long vocVoucherSource) {
        this.vocVoucherSource = vocVoucherSource;
    }

    public Long getVocIndex() {
        return vocIndex;
    }

    public void setVocIndex(Long vocIndex) {
        this.vocIndex = vocIndex;
    }

    public String getVocVoucherCode() {
        return vocVoucherCode;
    }

    public void setVocVoucherCode(String vocVoucherCode) {
        this.vocVoucherCode = vocVoucherCode;
    }

    @Override
    public String toString() {
        return "VoucherCode{" +
                "vocId=" + vocId +
                ", vocMerchantNo=" + vocMerchantNo +
                ", vocVoucherSource=" + vocVoucherSource +
                ", vocIndex=" + vocIndex +
                ", vocVoucherCode='" + vocVoucherCode + '\'' +
                '}';
    }
}
