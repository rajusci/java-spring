package com.inspirenetz.api.test.core.builder;

import com.inspirenetz.api.core.dictionary.IndicatorStatus;
import com.inspirenetz.api.core.dictionary.SettingDataType;
import com.inspirenetz.api.core.domain.Setting;

import java.util.Date;

/**
 * Created by sandheepgr on 26/6/14.
 */
public class SettingBuilder {
    private Long setId;
    private String setName = "";
    private String setDescription = "";
    private Integer setDataType= SettingDataType.STRING;
    private String setDefaultValue = "";
    private Integer setSessionStoreInd = IndicatorStatus.NO;
    private Long setModuleId = 0L;
    private Date createdAt;
    private String createdBy;
    private Date updatedAt;
    private String updatedBy;

    private SettingBuilder() {
    }

    public static SettingBuilder aSetting() {
        return new SettingBuilder();
    }

    public SettingBuilder withSetId(Long setId) {
        this.setId = setId;
        return this;
    }

    public SettingBuilder withSetName(String setName) {
        this.setName = setName;
        return this;
    }

    public SettingBuilder withSetDescription(String setDescription) {
        this.setDescription = setDescription;
        return this;
    }

    public SettingBuilder withSetDataType(Integer setDataType) {
        this.setDataType = setDataType;
        return this;
    }

    public SettingBuilder withSetDefaultValue(String setDefaultValue) {
        this.setDefaultValue = setDefaultValue;
        return this;
    }

    public SettingBuilder withSetSessionStoreInd(Integer setSessionStoreInd) {
        this.setSessionStoreInd = setSessionStoreInd;
        return this;
    }

    public SettingBuilder withSetModuleId(Long setModuleId) {
        this.setModuleId = setModuleId;
        return this;
    }

    public SettingBuilder withCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
        return this;
    }

    public SettingBuilder withCreatedBy(String createdBy) {
        this.createdBy = createdBy;
        return this;
    }

    public SettingBuilder withUpdatedAt(Date updatedAt) {
        this.updatedAt = updatedAt;
        return this;
    }

    public SettingBuilder withUpdatedBy(String updatedBy) {
        this.updatedBy = updatedBy;
        return this;
    }

    public Setting build() {
        Setting setting = new Setting();
        setting.setSetId(setId);
        setting.setSetName(setName);
        setting.setSetDescription(setDescription);
        setting.setSetDataType(setDataType);
        setting.setSetDefaultValue(setDefaultValue);
        setting.setSetSessionStoreInd(setSessionStoreInd);
        setting.setSetModuleId(setModuleId);
        setting.setCreatedAt(createdAt);
        setting.setCreatedBy(createdBy);
        setting.setUpdatedAt(updatedAt);
        setting.setUpdatedBy(updatedBy);
        return setting;
    }
}
