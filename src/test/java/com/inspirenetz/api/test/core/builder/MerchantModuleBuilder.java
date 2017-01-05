package com.inspirenetz.api.test.core.builder;

import com.inspirenetz.api.core.dictionary.IndicatorStatus;
import com.inspirenetz.api.core.domain.MerchantModule;

import java.util.Date;

/**
 * Created by sandheepgr on 10/7/14.
 */
public class MerchantModuleBuilder {
    private Long memId;
    private Long memMerchantNo;
    private Long memModuleId = 0L;
    private Integer memEnabledInd = IndicatorStatus.NO;
    private Date createdAt;
    private String createdBy;
    private Date updatedAt;
    private String updatedBy;

    private MerchantModuleBuilder() {
    }

    public static MerchantModuleBuilder aMerchantModule() {
        return new MerchantModuleBuilder();
    }

    public MerchantModuleBuilder withMemId(Long memId) {
        this.memId = memId;
        return this;
    }

    public MerchantModuleBuilder withMemMerchantNo(Long memMerchantNo) {
        this.memMerchantNo = memMerchantNo;
        return this;
    }

    public MerchantModuleBuilder withMemModuleId(Long memModuleId) {
        this.memModuleId = memModuleId;
        return this;
    }

    public MerchantModuleBuilder withMemEnabledInd(Integer memEnabledInd) {
        this.memEnabledInd = memEnabledInd;
        return this;
    }

    public MerchantModuleBuilder withCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
        return this;
    }

    public MerchantModuleBuilder withCreatedBy(String createdBy) {
        this.createdBy = createdBy;
        return this;
    }

    public MerchantModuleBuilder withUpdatedAt(Date updatedAt) {
        this.updatedAt = updatedAt;
        return this;
    }

    public MerchantModuleBuilder withUpdatedBy(String updatedBy) {
        this.updatedBy = updatedBy;
        return this;
    }

    public MerchantModule build() {
        MerchantModule merchantModule = new MerchantModule();
        merchantModule.setMemId(memId);
        merchantModule.setMemMerchantNo(memMerchantNo);
        merchantModule.setMemModuleId(memModuleId);
        merchantModule.setMemEnabledInd(memEnabledInd);
        merchantModule.setCreatedAt(createdAt);
        merchantModule.setCreatedBy(createdBy);
        merchantModule.setUpdatedAt(updatedAt);
        merchantModule.setUpdatedBy(updatedBy);
        return merchantModule;
    }
}
