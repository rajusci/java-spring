package com.inspirenetz.api.core.domain;

import javax.persistence.*;

/**
 * Created by saneeshci on 27/09/14.
 */
@Entity
@Table(name="DRAW_CHANCES")
public class BrandReport extends AuditedEntity {


    @Column(name = "DRC_ID",nullable = false)
    @Basic(fetch = FetchType.EAGER)
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long drcId;

    @Column(name = "DRC_CUSTOMER_NO",nullable = false)
    @Basic(fetch = FetchType.EAGER)
    private Long drcCustomerNo;

    @Column(name = "DRC_CHANCES" ,nullable = false)
    @Basic(fetch = FetchType.EAGER)
    private Long drcChances;

    @Column(name = "DRC_TYPE" ,nullable = false)
    @Basic(fetch = FetchType.EAGER)
    private Integer drcType;

    @Column(name = "DRC_STATUS" ,nullable = false)
    @Basic(fetch = FetchType.EAGER)
    private Integer drcStatus;


    public Long getDrcId() {
        return drcId;
    }

    public void setDrcId(Long drcId) {
        this.drcId = drcId;
    }

    public Long getDrcCustomerNo() {
        return drcCustomerNo;
    }

    public void setDrcCustomerNo(Long drcCustomerNo) {
        this.drcCustomerNo = drcCustomerNo;
    }

    public Long getDrcChances() {
        return drcChances;
    }

    public void setDrcChances(Long drcChances) {
        this.drcChances = drcChances;
    }

    public Integer getDrcType() {
        return drcType;
    }

    public void setDrcType(Integer drcType) {
        this.drcType = drcType;
    }

    public Integer getDrcStatus() {
        return drcStatus;
    }

    public void setDrcStatus(Integer drcStatus) {
        this.drcStatus = drcStatus;
    }

    @Override
    public String toString() {
        return "DrawChance{" +
                "drcId=" + drcId +
                ", drcCustomerNo=" + drcCustomerNo +
                ", drcChances=" + drcChances +
                ", drcType=" + drcType +
                ", drcStatus=" + drcStatus +
                '}';
    }
}
