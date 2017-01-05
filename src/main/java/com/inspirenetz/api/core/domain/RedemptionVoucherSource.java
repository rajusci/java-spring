package com.inspirenetz.api.core.domain;

import com.inspirenetz.api.core.dictionary.VoucherStatus;

import javax.persistence.*;

/**
 * Created by saneeshci on 10/02/15.
 */
@Entity
@Table(name="REDEMPTION_VOUCHER_SOURCES")
public class RedemptionVoucherSource extends AuditedEntity {


    @Column(name = "RVS_ID",nullable = false)
    @Basic(fetch = FetchType.EAGER)
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long rvsId;

    @Column(name = "RVS_NAME",nullable = false)
    @Basic(fetch = FetchType.EAGER)
    private String rvsName;

    @Column(name = "RVS_TYPE" ,nullable = false)
    @Basic(fetch = FetchType.EAGER)
    private Integer rvsType;

    @Column(name = "RVS_PREFIX",nullable = true)
    @Basic(fetch = FetchType.EAGER)
    private String rvsPrefix;

    @Column(name = "RVS_CODE_START" ,nullable = true)
    @Basic(fetch = FetchType.EAGER)
    private Long rvsCodeStart;

    @Column(name = "RVS_CODE" ,nullable = true)
    @Basic(fetch = FetchType.EAGER)
    private String rvsCode;

    @Column(name = "RVS_CODE_END" ,nullable = true)
    @Basic(fetch = FetchType.EAGER)
    private Long rvsCodeEnd;

    @Column(name = "RVS_INDEX" ,nullable = true)
    @Basic(fetch = FetchType.EAGER)
    private Long rvsIndex;

    @Column(name = "RVS_MERCHANT_NO" ,nullable = false)
    @Basic(fetch = FetchType.EAGER)
    private Long rvsMerchantNo;

    @Column(name = "RVS_STATUS" ,nullable = false)
    @Basic(fetch = FetchType.EAGER)
    private Integer rvsStatus = VoucherStatus.ACTIVE;

    @Transient
    String filePath ="";

    public Long getRvsId() {
        return rvsId;
    }

    public void setRvsId(Long rvsId) {
        this.rvsId = rvsId;
    }

    public String getRvsName() {
        return rvsName;
    }

    public void setRvsName(String rvsName) {
        this.rvsName = rvsName;
    }

    public Integer getRvsType() {
        return rvsType;
    }

    public void setRvsType(Integer rvsType) {
        this.rvsType = rvsType;
    }

    public String getRvsPrefix() {
        return rvsPrefix;
    }

    public void setRvsPrefix(String rvsPrefix) {
        this.rvsPrefix = rvsPrefix;
    }

    public String getRvsCode() {
        return rvsCode;
    }

    public void setRvsCode(String rvsCode) {
        this.rvsCode = rvsCode;
    }

    public Long getRvsIndex() {
        return rvsIndex;
    }

    public void setRvsIndex(Long rvsIndex) {
        this.rvsIndex = rvsIndex;
    }

    public Long getRvsMerchantNo() {
        return rvsMerchantNo;
    }

    public void setRvsMerchantNo(Long rvsMerchantNo) {
        this.rvsMerchantNo = rvsMerchantNo;
    }

    public Long getRvsCodeStart() {
        return rvsCodeStart;
    }

    public void setRvsCodeStart(Long rvsCodeStart) {
        this.rvsCodeStart = rvsCodeStart;
    }

    public Long getRvsCodeEnd() {
        return rvsCodeEnd;
    }

    public void setRvsCodeEnd(Long rvsCodeEnd) {
        this.rvsCodeEnd = rvsCodeEnd;
    }

    public Integer getRvsStatus() {
        return rvsStatus;
    }

    public void setRvsStatus(Integer rvsStatus) {
        this.rvsStatus = rvsStatus;
    }

    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    @Override
    public String toString() {
        return "RedemptionVoucherSource{" +
                "rvsId=" + rvsId +
                ", rvsName='" + rvsName + '\'' +
                ", rvsType=" + rvsType +
                ", rvsPrefix='" + rvsPrefix + '\'' +
                ", rvsCodeStart=" + rvsCodeStart +
                ", rvsCode='" + rvsCode + '\'' +
                ", rvsCodeEnd=" + rvsCodeEnd +
                ", rvsIndex=" + rvsIndex +
                ", rvsMerchantNo=" + rvsMerchantNo +
                '}';
    }
}
