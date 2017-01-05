package com.inspirenetz.api.core.domain;

import com.inspirenetz.api.core.incustomization.attrext.ExtendedAttributeField;
import com.inspirenetz.api.core.incustomization.attrext.ExtendedAttributeIdField;
import com.inspirenetz.api.core.incustomization.attrext.ExtendedAttributeValueField;

import javax.persistence.*;

/**
 * Created by sandheepgr on 7/8/14.
 */
@Entity
@Table(name = "SALE_SKU_EXTENSION")
public class SaleSKUExtension extends AuditedEntity {

    @Id
    @Column(name = "SSE_ID", nullable = false, insertable = true, updatable = true, length = 19, precision = 0)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long sseId;

    /**
     * IMPORTANT NOTE: Need to have this field as nullable as this entity is
     * updated on cascade with Sale entity.
     * Hibernate will first insert and the update the parent id here.
     * So if its not nullable, this will through data integrity exception for null
     * and prevent from updating it.
     */
    @Basic
    @Column(name = "SSE_SALE_SKU_ID")
    private Long sseSaleSkuId;

    @Basic
    @Column(name = "ATTR_ID")
    @ExtendedAttributeIdField
    private Long attrId;

    @Basic
    @Column(name = "ATTR_VALUE")
    @ExtendedAttributeValueField
    private String attrValue;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "ATTR_ID",insertable = false,updatable = false)
    @ExtendedAttributeField
    private Attribute attribute;


    public Long getSseId() {
        return sseId;
    }

    public void setSseId(Long sseId) {
        this.sseId = sseId;
    }

    public Long getSseSaleSkuId() {
        return sseSaleSkuId;
    }

    public void setSseSaleSkuId(Long sseSaleSkuId) {
        this.sseSaleSkuId = sseSaleSkuId;
    }

    public Long getAttrId() {
        return attrId;
    }

    public void setAttrId(Long attrId) {
        this.attrId = attrId;
    }

    public String getAttrValue() {
        return attrValue;
    }

    public void setAttrValue(String attrValue) {
        this.attrValue = attrValue;
    }

    public Attribute getAttribute() {
        return attribute;
    }

    public void setAttribute(Attribute attribute) {
        this.attribute = attribute;
    }


    @Override
    public String toString() {
        return "SaleSKUExtension{" +
                "sseId=" + sseId +
                ", sseSaleSkuId=" + sseSaleSkuId +
                ", attrId=" + attrId +
                ", attrValue='" + attrValue + '\'' +
                ", attribute=" + attribute +
                '}';
    }
}
