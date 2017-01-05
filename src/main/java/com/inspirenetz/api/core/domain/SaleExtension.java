package com.inspirenetz.api.core.domain;

import com.inspirenetz.api.core.incustomization.attrext.ExtendedAttributeField;
import com.inspirenetz.api.core.incustomization.attrext.ExtendedAttributeIdField;
import com.inspirenetz.api.core.incustomization.attrext.ExtendedAttributeValueField;

import javax.persistence.*;

/**
 * Created by sandheepgr on 7/8/14.
 */
@Entity
@Table(name = "SALE_EXTENSION")
public class SaleExtension extends AuditedEntity {

    @Id
    @Column(name = "SAE_ID", nullable = false, insertable = true, updatable = true, length = 19, precision = 0)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long saeId;

    /**
     * IMPORTANT NOTE: Need to have this field as nullable as this entity is
     * updated on cascade with Sale entity.
     * Hibernate will first insert and the update the parent id here.
     * So if its not nullable, this will through data integrity exception for null
     * and prevent from updating it.
     */
    @Basic
    @Column(name = "SAE_SALE_ID")
    private Long saeSaleId;

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


    public Long getSaeId() {
        return saeId;
    }

    public void setSaeId(Long saeId) {
        this.saeId = saeId;
    }

    public Long getSaeSaleId() {
        return saeSaleId;
    }

    public void setSaeSaleId(Long saeSaleId) {
        this.saeSaleId = saeSaleId;
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
        return "SaleExtension{" +
                "saeId=" + saeId +
                ", saeSaleId=" + saeSaleId +
                ", attrId=" + attrId +
                ", attrValue='" + attrValue + '\'' +
                ", attribute=" + attribute +
                '}';
    }
}
