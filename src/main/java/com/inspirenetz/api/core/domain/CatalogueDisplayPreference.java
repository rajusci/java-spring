package com.inspirenetz.api.core.domain;

import com.inspirenetz.api.core.dictionary.RedemptionVoucherStatus;

import javax.persistence.*;

/**
 * Created by saneeshci on 21/10/14.
 */
@Entity
@Table(name="CATALOGUE_DISPLAY_PREFERENCES")
public class CatalogueDisplayPreference extends AuditedEntity {


    @Column(name = "CDP_ID",nullable = false)
    @Basic(fetch = FetchType.EAGER)
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long cdpId;

    @Column(name = "CDP_PREFERENCE",nullable = false)
    @Basic(fetch = FetchType.EAGER)
    private String cdpPreferences;

    @Column(name = "CDP_MERCHANT_NO" ,nullable = false)
    @Basic(fetch = FetchType.EAGER)
    private Long cdpMerchantNo;

    public Long getCdpId() {
        return cdpId;
    }

    public void setCdpId(Long cdpId) {
        this.cdpId = cdpId;
    }

    public String getCdpPreferences() {
        return cdpPreferences;
    }

    public void setCdpPreferences(String cdpPreferences) {
        this.cdpPreferences = cdpPreferences;
    }

    public Long getCdpMerchantNo() {
        return cdpMerchantNo;
    }

    public void setCdpMerchantNo(Long cdpMerchantNo) {
        this.cdpMerchantNo = cdpMerchantNo;
    }

    @Override
    public String toString() {
        return "CatalogueDisplayPreference{" +
                "cdpId=" + cdpId +
                ", cdpPreferences='" + cdpPreferences + '\'' +
                ", cdpMerchantNo=" + cdpMerchantNo +
                '}';
    }
}
