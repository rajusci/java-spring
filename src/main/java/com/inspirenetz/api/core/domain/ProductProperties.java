package com.inspirenetz.api.core.domain;

import javax.persistence.*;

/**
 * Created by saneeshci on 27/09/14.
 */
@Entity
@Table(name="PRODUCT_PROPERTIES")
public class ProductProperties extends AuditedEntity {


    @Column(name = "PRP_ID",nullable = false)
    @Basic(fetch = FetchType.EAGER)
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long prpId;

    @Column(name = "PRP_NAME",nullable = false)
    @Basic(fetch = FetchType.EAGER)
    private String prpName;

    @Column(name = "PRP_VALUE" ,nullable = false)
    @Basic(fetch = FetchType.EAGER)
    private String prpValue;

    public Long getPrpId() {
        return prpId;
    }

    public void setPrpId(Long prpId) {
        this.prpId = prpId;
    }

    public String getPrpName() {
        return prpName;
    }

    public void setPrpName(String prpName) {
        this.prpName = prpName;
    }

    public String getPrpValue() {
        return prpValue;
    }

    public void setPrpValue(String prpValue) {
        this.prpValue = prpValue;
    }

    @Override
    public String toString() {
        return "ProductProperties{" +
                "prpId=" + prpId +
                ", prpName='" + prpName + '\'' +
                ", prpValue='" + prpValue + '\'' +
                '}';
    }
}
