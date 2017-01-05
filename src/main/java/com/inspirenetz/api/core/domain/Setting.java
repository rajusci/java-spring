package com.inspirenetz.api.core.domain;

import com.inspirenetz.api.core.dictionary.IndicatorStatus;
import com.inspirenetz.api.core.dictionary.SettingDataType;
import com.inspirenetz.api.core.dictionary.YesNoInd;

import javax.persistence.*;

/**
 * Created by sandheepgr on 26/6/14.
 */

@Entity
@Table(name="SETTINGS")
public class Setting  extends  AuditedEntity {

    @Id
    @Column(name = "SET_ID", nullable = false, insertable = true, updatable = true, length = 10, precision = 0)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long setId;

    @Basic
    @Column(name = "SET_NAME", nullable = false, insertable = true, updatable = true, length = 200, precision = 0)
    private String setName = "";

    @Basic
    @Column(name = "SET_DESCRIPTION", nullable = true, insertable = true, updatable = true, length = 500, precision = 0)
    private String setDescription = "";

    @Basic
    @Column(name = "SET_DATA_TYPE", nullable = false, insertable = true, updatable = true, length = 10, precision = 0)
    private Integer setDataType= SettingDataType.STRING;

    @Basic
    @Column(name = "SET_DEFAULT_VALUE", nullable = false, insertable = true, updatable = true, length = 200, precision = 0)
    private String setDefaultValue = "";

    @Basic
    @Column(name = "SET_SESSION_STORE_IND", nullable = false, insertable = true, updatable = true, length = 10, precision = 0)
    private Integer setSessionStoreInd = IndicatorStatus.NO;

    @Basic
    @Column(name = "SET_MODULE_ID", nullable = true, insertable = true, updatable = true, length = 10, precision = 0)
    private Long setModuleId = 0L;




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


    @Override
    public String toString() {
        return "Setting{" +
                "setId=" + setId +
                ", setName='" + setName + '\'' +
                ", setDescription='" + setDescription + '\'' +
                ", setDataType=" + setDataType +
                ", setDefaultValue='" + setDefaultValue + '\'' +
                ", setSessionStoreInd=" + setSessionStoreInd +
                ", setModuleId=" + setModuleId +
                '}';
    }
}
