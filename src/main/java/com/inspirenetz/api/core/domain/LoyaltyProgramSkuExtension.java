package com.inspirenetz.api.core.domain;

import com.inspirenetz.api.core.incustomization.attrext.ExtendedAttributeField;
import com.inspirenetz.api.core.incustomization.attrext.ExtendedAttributeIdField;
import com.inspirenetz.api.core.incustomization.attrext.ExtendedAttributeValueField;

import javax.persistence.*;

/**
 * Created by sandheepgr on 7/8/14.
 */
@Entity
@Table(name = "LOYALTY_PROGRAM_SKU_EXTENSION")
public class LoyaltyProgramSkuExtension extends AuditedEntity {

    @Id
    @Column(name = "LUE_ID", nullable = false, insertable = true, updatable = true, length = 19, precision = 0)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long lueId;

    /**
     * IMPORTANT NOTE: Need to have this field as nullable as this entity is
     * updated on cascade with LoyaltyProgramSku entity.
     * Hibernate will first insert and the update the parent id here.
     * So if its not nullable, this will through data integrity exception for null
     * and prevent from updating it.
     */
    @Basic
    @Column(name = "LUE_LOYALTY_PROGRAM_SKU_ID")
    private Long lueLoyaltyProgramSkuId;

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


    public Long getLueId() {
        return lueId;
    }

    public void setLueId(Long lueId) {
        this.lueId = lueId;
    }

    public Long getLueLoyaltyProgramSkuId() {
        return lueLoyaltyProgramSkuId;
    }

    public void setLueLoyaltyProgramSkuId(Long lueLoyaltyProgramSkuId) {
        this.lueLoyaltyProgramSkuId = lueLoyaltyProgramSkuId;
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
        return "LoyaltyProgramSkuExtension{" +
                "lueId=" + lueId +
                ", lueLoyaltyProgramSkuId=" + lueLoyaltyProgramSkuId +
                ", attrId=" + attrId +
                ", attrValue='" + attrValue + '\'' +
                ", attribute=" + attribute +
                '}';
    }
}
