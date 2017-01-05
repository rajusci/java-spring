package com.inspirenetz.api.rest.resource;

/**
 * Created by sandheepgr on 26/6/14.
 */
public class SettingResource extends BaseResource {

    private Long setId;

    private String setName;

    private String setDescription;

    private Integer setDataType;

    private String setDefaultValue;

    private Integer setSessionStoreInd;

    private Long setModuleId;


    public Long getSetId() {
        return setId;
    }

    public void setSetId(Long setId) {
        this.setId = setId;
    }

    public String getSetName() {
        return setName;
    }

    public void setSetName(String setName) {
        this.setName = setName;
    }

    public String getSetDescription() {
        return setDescription;
    }

    public void setSetDescription(String setDescription) {
        this.setDescription = setDescription;
    }

    public Integer getSetDataType() {
        return setDataType;
    }

    public void setSetDataType(Integer setDataType) {
        this.setDataType = setDataType;
    }

    public String getSetDefaultValue() {
        return setDefaultValue;
    }

    public void setSetDefaultValue(String setDefaultValue) {
        this.setDefaultValue = setDefaultValue;
    }

    public Integer getSetSessionStoreInd() {
        return setSessionStoreInd;
    }

    public void setSetSessionStoreInd(Integer setSessionStoreInd) {
        this.setSessionStoreInd = setSessionStoreInd;
    }

    public Long getSetModuleId() {
        return setModuleId;
    }

    public void setSetModuleId(Long setModuleId) {
        this.setModuleId = setModuleId;
    }
}
