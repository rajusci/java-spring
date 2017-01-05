package com.inspirenetz.api.core.domain;

import com.inspirenetz.api.core.dictionary.IndicatorStatus;

import javax.persistence.*;
import javax.validation.constraints.Size;

/**
 * Created by sandheepgr on 29/3/14.
 */
@Entity
@Table(name = "MERCHANT_LOCATIONS")
public class MerchantLocation extends AuditedEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "MEL_ID")
    private Long melId;

    @Basic(fetch = FetchType.EAGER)
    @Column(name = "MEL_MERCHANT_NO" ,nullable = true)
    private Long melMerchantNo;

    @Basic(fetch = FetchType.EAGER)
    @Column(name = "MEL_LOCATION")
    @Size(max=100, message = "{merchantlocations.mellocation.size}")
    private String melLocation;

    @Basic(fetch = FetchType.EAGER)
    @Column(name = "MEL_LATITUDE",nullable = true)
    private String melLatitude;

    @Basic(fetch = FetchType.EAGER)
    @Column(name = "MEL_LONGITUDE",nullable = true)
    private String melLongitude;

    @Basic(fetch = FetchType.EAGER)
    @Column(name = "MEL_LOCATION_IN_USE")
    private int melLocationInUse = IndicatorStatus.YES;


    public Long getMelId() {
        return melId;
    }

    public void setMelId(Long melId) {
        this.melId = melId;
    }

    public Long getMelMerchantNo() {
        return melMerchantNo;
    }

    public void setMelMerchantNo(Long melMerchantNo) {
        this.melMerchantNo = melMerchantNo;
    }

    public String getMelLocation() {
        return melLocation;
    }

    public void setMelLocation(String melLocation) {
        this.melLocation = melLocation;
    }

    public String getMelLatitude() {
        return melLatitude;
    }

    public void setMelLatitude(String melLatitude) {
        this.melLatitude = melLatitude;
    }

    public String getMelLongitude() {
        return melLongitude;
    }

    public void setMelLongitude(String melLongitude) {
        this.melLongitude = melLongitude;
    }

    public int getMelLocationInUse() {
        return melLocationInUse;
    }

    public void setMelLocationInUse(int melLocationInUse) {
        this.melLocationInUse = melLocationInUse;
    }


    @Override
    public String toString() {
        return "MerchantLocation{" +
                "melId=" + melId +
                ", melMerchantNo=" + melMerchantNo +
                ", melLocation='" + melLocation + '\'' +
                ", melLatitude='" + melLatitude + '\'' +
                ", melLongitude='" + melLongitude + '\'' +
                ", melLocationInUse=" + melLocationInUse +
                '}';
    }
}
