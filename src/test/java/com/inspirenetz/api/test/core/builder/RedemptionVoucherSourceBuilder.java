package com.inspirenetz.api.test.core.builder;

import com.inspirenetz.api.core.domain.RedemptionVoucherSource;

/**
 * Created by saneesh-ci on 10/2/15.
 */
public class RedemptionVoucherSourceBuilder {
    private Long rvsId;
    private String rvsName;
    private Integer rvsType;
    private String rvsPrefix;
    private Long rvsCodeStart;
    private String rvsCode;
    private Long rvsCodeEnd;
    private Long rvsIndex;
    private Long rvsMerchantNo;
    private Integer rvsStatus;

    private RedemptionVoucherSourceBuilder() {
    }

    public static RedemptionVoucherSourceBuilder aRedemptionVoucherSource() {
        return new RedemptionVoucherSourceBuilder();
    }

    public RedemptionVoucherSourceBuilder withRvsId(Long rvsId) {
        this.rvsId = rvsId;
        return this;
    }

    public RedemptionVoucherSourceBuilder withRvsName(String rvsName) {
        this.rvsName = rvsName;
        return this;
    }

    public RedemptionVoucherSourceBuilder withRvsType(Integer rvsType) {
        this.rvsType = rvsType;
        return this;
    }

    public RedemptionVoucherSourceBuilder withRvsPrefix(String rvsPrefix) {
        this.rvsPrefix = rvsPrefix;
        return this;
    }

    public RedemptionVoucherSourceBuilder withRvsCodeStart(Long rvsCodeStart) {
        this.rvsCodeStart = rvsCodeStart;
        return this;
    }

    public RedemptionVoucherSourceBuilder withRvsCode(String rvsCode) {
        this.rvsCode = rvsCode;
        return this;
    }

    public RedemptionVoucherSourceBuilder withRvsCodeEnd(Long rvsCodeEnd) {
        this.rvsCodeEnd = rvsCodeEnd;
        return this;
    }

    public RedemptionVoucherSourceBuilder withRvsIndex(Long rvsIndex) {
        this.rvsIndex = rvsIndex;
        return this;
    }

    public RedemptionVoucherSourceBuilder withRvsMerchantNo(Long rvsMerchantNo) {
        this.rvsMerchantNo = rvsMerchantNo;
        return this;
    }

    public RedemptionVoucherSourceBuilder withRvsStatus(Integer rvsStatus) {
        this.rvsStatus = rvsStatus;
        return this;
    }

    public RedemptionVoucherSourceBuilder but() {
        return aRedemptionVoucherSource().withRvsId(rvsId).withRvsName(rvsName).withRvsType(rvsType).withRvsPrefix(rvsPrefix).withRvsCodeStart(rvsCodeStart).withRvsCode(rvsCode).withRvsCodeEnd(rvsCodeEnd).withRvsIndex(rvsIndex).withRvsMerchantNo(rvsMerchantNo);
    }

    public RedemptionVoucherSource build() {
        RedemptionVoucherSource redemptionVoucherSource = new RedemptionVoucherSource();
        redemptionVoucherSource.setRvsId(rvsId);
        redemptionVoucherSource.setRvsName(rvsName);
        redemptionVoucherSource.setRvsType(rvsType);
        redemptionVoucherSource.setRvsPrefix(rvsPrefix);
        redemptionVoucherSource.setRvsCodeStart(rvsCodeStart);
        redemptionVoucherSource.setRvsCode(rvsCode);
        redemptionVoucherSource.setRvsCodeEnd(rvsCodeEnd);
        redemptionVoucherSource.setRvsIndex(rvsIndex);
        redemptionVoucherSource.setRvsMerchantNo(rvsMerchantNo);
        redemptionVoucherSource.setRvsStatus(rvsStatus);
        return redemptionVoucherSource;
    }
}
