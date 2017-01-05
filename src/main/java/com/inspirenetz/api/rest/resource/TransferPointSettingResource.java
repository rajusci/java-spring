package com.inspirenetz.api.rest.resource;

import com.inspirenetz.api.core.dictionary.IndicatorStatus;
import com.inspirenetz.api.core.dictionary.TransferPointSettingCompatibility;
import com.inspirenetz.api.core.dictionary.TransferPointSettingLinkedEligibity;

/**
 * Created by sandheepgr on 4/9/14.
 */
public class TransferPointSettingResource extends BaseResource {

    private Long tpsId;

    private Long tpsMerchantNo = 0L;

    private Long tpsLocation = 0L;

    private Integer tpsTransferCompatibility = TransferPointSettingCompatibility.SAME_LOCATION;

    private Double tpsTransferCharge = 0.0;

    private Integer tpsIsTierAffected = IndicatorStatus.NO;

    private Integer tpsTransferredPointValidity = 30;

    private Integer tpsMaxTransfers = 5;

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
        return "TransferPointSettingResource{" +
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
