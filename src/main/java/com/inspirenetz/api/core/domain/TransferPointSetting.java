package com.inspirenetz.api.core.domain;

import com.inspirenetz.api.core.dictionary.IndicatorStatus;
import com.inspirenetz.api.core.dictionary.TransferPointSettingCompatibility;
import com.inspirenetz.api.core.dictionary.TransferPointSettingLinkedEligibity;

import javax.persistence.*;

/**
 * Created by sandheepgr on 24/8/14.
 */
@Entity
@Table(name = "TRANSFER_POINT_SETTING")
public class TransferPointSetting extends AuditedEntity {


    @Id
    @Column(name = "TPS_ID", nullable = false, insertable = true, updatable = true, length = 10, precision = 0)
    @GeneratedValue  ( strategy = GenerationType.IDENTITY)
    private Long tpsId;

    @Basic
    @Column(name = "TPS_MERCHANT_NO", nullable = false, insertable = true, updatable = true, length = 10, precision = 0)
    private Long tpsMerchantNo = 0L;

    @Basic
    @Column(name = "TPS_LOCATION", nullable = false, insertable = true, updatable = true, length = 10, precision = 0)
    private Long tpsLocation = 0L;

    @Basic
    @Column(name = "TPS_TRANSFER_COMPATIBILITY", nullable = true, insertable = true, updatable = true, length = 10, precision = 0)
    private Integer tpsTransferCompatibility = TransferPointSettingCompatibility.SAME_LOCATION;

    @Basic
    @Column(name = "TPS_TRANSFER_CHARGE", nullable = true, insertable = true, updatable = true, length = 10, precision = 0)
    private Double tpsTransferCharge = 0.0;

    @Basic
    @Column(name = "TPS_IS_TIER_AFFECTED", nullable = true, insertable = true, updatable = true, length = 10, precision = 0)
    private Integer tpsIsTierAffected = IndicatorStatus.NO;

    @Basic
    @Column(name = "TPS_TRANSFERRED_POINT_VALIDITY", nullable = true, insertable = true, updatable = true, length = 10, precision = 0)
    private Integer tpsTransferredPointValidity = 30;

    @Basic
    @Column(name = "TPS_MAX_TRANSFERS", nullable = true, insertable = true, updatable = true, length = 100, precision = 0)
    private Integer tpsMaxTransfers = 5;

    @Basic
    @Column(name = "TPS_LINKED_ELIGIBILITY", nullable = true, insertable = true, updatable = true, length = 10, precision = 0)
    private Integer tpsLinkedEligibilty = TransferPointSettingLinkedEligibity.NO_AUTHORIZATION;


    public Long getTpsId() {
        return tpsId;
    }

    public void setTpsId(Long tpsId) {
        this.tpsId = tpsId;
    }

    public Long getTpsMerchantNo() {
        return tpsMerchantNo;
    }

    public void setTpsMerchantNo(Long tpsMerchantNo) {
        this.tpsMerchantNo = tpsMerchantNo;
    }

    public Long getTpsLocation() {
        return tpsLocation;
    }

    public void setTpsLocation(Long tpsLocation) {
        this.tpsLocation = tpsLocation;
    }

    public Integer getTpsTransferCompatibility() {
        return tpsTransferCompatibility;
    }

    public void setTpsTransferCompatibility(Integer tpsTransferCompatibility) {
        this.tpsTransferCompatibility = tpsTransferCompatibility;
    }

    public Double getTpsTransferCharge() {
        return tpsTransferCharge;
    }

    public void setTpsTransferCharge(Double tpsTransferCharge) {
        this.tpsTransferCharge = tpsTransferCharge;
    }

    public Integer getTpsIsTierAffected() {
        return tpsIsTierAffected;
    }

    public void setTpsIsTierAffected(Integer tpsIsTierAffected) {
        this.tpsIsTierAffected = tpsIsTierAffected;
    }

    public Integer getTpsTransferredPointValidity() {
        return tpsTransferredPointValidity;
    }

    public void setTpsTransferredPointValidity(Integer tpsTransferredPointValidity) {
        this.tpsTransferredPointValidity = tpsTransferredPointValidity;
    }

    public Integer getTpsMaxTransfers() {
        return tpsMaxTransfers;
    }

    public void setTpsMaxTransfers(Integer tpsMaxTransfers) {
        this.tpsMaxTransfers = tpsMaxTransfers;
    }

    public Integer getTpsLinkedEligibilty() {
        return tpsLinkedEligibilty;
    }

    public void setTpsLinkedEligibilty(Integer tpsLinkedEligibilty) {
        this.tpsLinkedEligibilty = tpsLinkedEligibilty;
    }

    @Override
    public String toString() {
        return "TransferPointSetting{" +
                "tpsId=" + tpsId +
                ", tpsMerchantNo=" + tpsMerchantNo +
                ", tpsLocation=" + tpsLocation +
                ", tpsTransferCompatibility=" + tpsTransferCompatibility +
                ", tpsTransferCharge=" + tpsTransferCharge +
                ", tpsIsTierAffected=" + tpsIsTierAffected +
                ", tpsTransferredPointValidity=" + tpsTransferredPointValidity +
                ", tpsMaxTransfers=" + tpsMaxTransfers +
                ", tpsLinkedEligibilty=" + tpsLinkedEligibilty +
                '}';
    }
}
