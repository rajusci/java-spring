package com.inspirenetz.api.core.domain;

import com.inspirenetz.api.core.incustomization.attrext.ExtendedAttributeField;
import com.inspirenetz.api.core.incustomization.attrext.ExtendedAttributeIdField;
import com.inspirenetz.api.core.incustomization.attrext.ExtendedAttributeValueField;

import javax.persistence.*;

/**
 * Created by sandheepgr on 7/8/14.
 */
@Entity
@Table(name = "LOYALTY_PROGRAM_EXTENSION")
public class LoyaltyProgramExtension extends AuditedEntity {

    @Id
    @Column(name = "LPE_ID", nullable = false, insertable = true, updatable = true, length = 19, precision = 0)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long lpeId;

    /**
     * IMPORTANT NOTE: Need to have this field as nullable as this entity is
     * updated on cascade with LoyaltyProgram entity.
     * Hibernate will first insert and the update the parent id here.
     * So if its not nullable, this will through data integrity exception for null
     * and prevent from updating it.
     */
    @Basic
    @Column(name = "LPE_LOYALTY_PROGRAM_ID")
    private Long lpeLoyaltyProgramId;

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


    public Long getLpeId() {
        return lpeId;
    }

    public void setLpeId(Long lpeId) {
        this.lpeId = lpeId;
    }

    public Long getLpeLoyaltyProgramId() {
        return lpeLoyaltyProgramId;
    }

    public void setLpeLoyaltyProgramId(Long lpeLoyaltyProgramId) {
        this.lpeLoyaltyProgramId = lpeLoyaltyProgramId;
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
        return "LoyaltyProgramExtension{" +
                "lpeId=" + lpeId +
                ", lpeLoyaltyProgramId=" + lpeLoyaltyProgramId +
                ", attrId=" + attrId +
                ", attrValue='" + attrValue + '\'' +
                ", attribute=" + attribute +
                '}';
    }
}
