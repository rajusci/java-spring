package com.inspirenetz.api.core.domain;

import com.inspirenetz.api.core.dictionary.IndicatorStatus;

import javax.persistence.*;
import javax.validation.constraints.Size;

/**
 * Created by saneeshci on 25/9/14.
 */
@Entity
@Table(name = "REDEMPTION_MERCHANT_LOCATIONS")
public class RedemptionMerchantLocation extends AuditedEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "RML_ID")
    private Long rmlId;

    @Basic(fetch = FetchType.EAGER)
    @Column(name = "RML_MER_NO")
    private Long rmlMerNo;

    @Basic(fetch = FetchType.EAGER)
    @Column(name = "RML_LOCATION")
    private String rmlLocation;

    public Long getRmlId() {
        return rmlId;
    }

    public void setRmlId(Long rmlId) {
        this.rmlId = rmlId;
    }

    public Long getRmlMerNo() {
        return rmlMerNo;
    }

    public void setRmlMerNo(Long rmlMerNo) {
        this.rmlMerNo = rmlMerNo;
    }

    public String getRmlLocation() {
        return rmlLocation;
    }

    public void setRmlLocation(String rmlLocation) {
        this.rmlLocation = rmlLocation;
    }

    @Override
    public String toString() {
        return "RedemptionMerchantLocation{" +
                "rmlId=" + rmlId +
                ", rmlMerNo=" + rmlMerNo +
                ", rmlLocation='" + rmlLocation + '\'' +
                '}';
    }
}
