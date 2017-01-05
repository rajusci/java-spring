package com.inspirenetz.api.rest.resource;

import org.springframework.hateoas.ResourceSupport;

/**
 * Created by ameenci on 16/9/14.
 */
public class AttributeResource extends ResourceSupport {


    private Long atrId;

    private String atrName = "";

    private Integer atrEntity = 0;

    private String atrDesc = "";

    public Long getAtrId() {
        return atrId;
    }

    public void setAtrId(Long atrId) {
        this.atrId = atrId;
    }

    public String getAtrName() {
        return atrName;
    }

    public void setAtrName(String atrName) {
        this.atrName = atrName;
    }

    public Integer getAtrEntity() {
        return atrEntity;
    }

    public void setAtrEntity(Integer atrEntity) {
        this.atrEntity = atrEntity;
    }

    public String getAtrDesc() {
        return atrDesc;
    }

    public void setAtrDesc(String atrDesc) {
        this.atrDesc = atrDesc;
    }


    @Override
    public String toString() {
        return "AttributeResource{" +
                "atrId=" + atrId +
                ", atrName='" + atrName + '\'' +
                ", atrEntity=" + atrEntity +
                ", atrDesc='" + atrDesc + '\'' +
                '}';
    }
}
