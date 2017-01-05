package com.inspirenetz.api.test.core.builder;

import com.inspirenetz.api.core.domain.MerchantSetting;

import java.util.Date;

/**
 * Created by sandheepgr on 10/7/14.
 */
public class MerchantSettingBuilder {
    private Long mesId;
    private Long mesMerchantNo = 0L;
    private Long mesSettingId = 0L;
    private String mesValue = "";
    private Date createdAt;
    private String createdBy;
    private Date updatedAt;
    private String updatedBy;

    private MerchantSettingBuilder() {
    }

    public static MerchantSettingBuilder aMerchantSetting() {
        return new MerchantSettingBuilder();
    }

    public MerchantSettingBuilder withMesId(Long mesId) {
        this.mesId = mesId;
        return this;
    }

    public MerchantSettingBuilder withMesMerchantNo(Long mesMerchantNo) {
        this.mesMerchantNo = mesMerchantNo;
        return this;
    }

    public MerchantSettingBuilder withMesSettingId(Long mesSettingId) {
        this.mesSettingId = mesSettingId;
        return this;
    }

    public MerchantSettingBuilder withMesValue(String mesValue) {
        this.mesValue = mesValue;
        return this;
    }

    public MerchantSettingBuilder withCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
        return this;
    }

    public MerchantSettingBuilder withCreatedBy(String createdBy) {
        this.createdBy = createdBy;
        return this;
    }

    public MerchantSettingBuilder withUpdatedAt(Date updatedAt) {
        this.updatedAt = updatedAt;
        return this;
    }

    public MerchantSettingBuilder withUpdatedBy(String updatedBy) {
        this.updatedBy = updatedBy;
        return this;
    }

    public MerchantSetting build() {
        MerchantSetting merchantSetting = new MerchantSetting();
        merchantSetting.setMesId(mesId);
        merchantSetting.setMesMerchantNo(mesMerchantNo);
        merchantSetting.setMesSettingId(mesSettingId);
        merchantSetting.setMesValue(mesValue);
        merchantSetting.setCreatedAt(createdAt);
        merchantSetting.setCreatedBy(createdBy);
        merchantSetting.setUpdatedAt(updatedAt);
        merchantSetting.setUpdatedBy(updatedBy);
        return merchantSetting;
    }
}
