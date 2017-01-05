package com.inspirenetz.api.core.domain;

import javax.persistence.*;

/**
 * Created by sandheepgr on 26/6/14.
 */
@Entity
@Table(name = "MERCHANT_SETTINGS")
public class MerchantSetting extends  AuditedEntity {

    @Column(name = "MES_ID")
    @Basic(fetch = FetchType.EAGER)
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long mesId;

    @Column(name = "MES_MERCHANT_NO", nullable = false, insertable = true, updatable = true, length = 10, precision = 0)
    private Long mesMerchantNo = 0L;

    @Column(name = "MES_SETTING_ID", nullable = false, insertable = true, updatable = true, length = 10, precision = 0)
    private Long mesSettingId = 0L;

    @Basic
    @Column(name = "MES_VALUE", nullable = false, insertable = true, updatable = true, length = 200, precision = 0)
    private String mesValue = "";

    @Transient
    private String setName;

    @Transient
    private Integer setDataType;


    public Long getMesId() {
        return mesId;
    }

    public void setMesId(Long mesId) {
        this.mesId = mesId;
    }

    public Long getMesMerchantNo() {
        return mesMerchantNo;
    }

    public void setMesMerchantNo(Long mesMerchantNo) {
        this.mesMerchantNo = mesMerchantNo;
    }

    public Long getMesSettingId() {
        return mesSettingId;
    }

    public void setMesSettingId(Long mesSettingId) {
        this.mesSettingId = mesSettingId;
    }

    public String getMesValue() {
        return mesValue;
    }

    public void setMesValue(String mesValue) {
        this.mesValue = mesValue;
    }


    public String getSetName() {
        return setName;
    }

    public void setSetName(String setName) {
        this.setName = setName;
    }


    public Integer getSetDataType() {
        return setDataType;
    }

    public void setSetDataType(Integer setDataType) {
        this.setDataType = setDataType;
    }

    @Override
    public String toString() {
        return "MerchantSetting{" +
                "mesId=" + mesId +
                ", mesMerchantNo=" + mesMerchantNo +
                ", mesSettingId=" + mesSettingId +
                ", mesValue='" + mesValue + '\'' +
                ", setName='" + setName + '\'' +
                ", setDataType=" + setDataType +
                '}';
    }
}
