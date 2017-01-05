package com.inspirenetz.api.core.domain;

import javax.persistence.*;

/**
 * Created by sandheepgr on 7/8/14.
 */
@Entity
@Table(name = "ATTRIBUTES")
public class Attribute {

    @Id
    @Column(name = "ATR_ID", nullable = false, insertable = true, updatable = true, length = 10, precision = 0)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long atrId;

    @Basic
    @Column(name = "ATR_NAME", nullable = false, insertable = true, updatable = true, length = 200, precision = 0)
    private String atrName = "";

    @Basic
    @Column(name = "ATR_ENTITY", nullable = true, insertable = true, updatable = true, length = 10, precision = 0)
    private Integer atrEntity = 0;

    @Basic
    @Column(name = "ATR_DESC", nullable = true, insertable = true, updatable = true, length = 500, precision = 0)
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
        return "Attribute{" +
                "atrId=" + atrId +
                ", atrName='" + atrName + '\'' +
                ", atrEntity=" + atrEntity +
                ", atrDesc='" + atrDesc + '\'' +
                '}';
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Attribute attribute = (Attribute) o;

        if (!atrId.equals(attribute.atrId)) return false;
        if (!atrName.equals(attribute.atrName)) return false;

        return true;
    }


}
