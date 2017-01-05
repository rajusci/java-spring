package com.inspirenetz.api.rest.resource;

import org.springframework.hateoas.ResourceSupport;

/**
 * Created by ameen on 23/4/15.
 */
public class MerchantSettingResource extends ResourceSupport {

    private Long mesId;
    private Long mesMerchantNo = 0L;
    private Long mesSettingId = 0L;
    private String mesValue = "";
    private String setName;
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
}
