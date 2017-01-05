package com.inspirenetz.api.test.core.builder;

import com.inspirenetz.api.core.dictionary.IndicatorStatus;
import com.inspirenetz.api.core.dictionary.TransferPointSettingCompatibility;
import com.inspirenetz.api.core.dictionary.TransferPointSettingLinkedEligibity;
import com.inspirenetz.api.core.domain.TransferPointSetting;

import java.util.Date;

/**
 * Created by sandheepgr on 2/9/14.
 */
public class TransferPointSettingBuilder {
    private Long tpsId;
    private Long tpsMerchantNo = 0L;
    private Long tpsLocation = 0L;
    private Integer tpsTransferCompatibility = TransferPointSettingCompatibility.SAME_LOCATION;
    private Double tpsTransferCharge = 0.0;
    private Integer tpsIsTierAffected = IndicatorStatus.NO;
    private Integer tpsTransferredPointValidity = 30;
    private Integer tpsMaxTransfers = 5;
    private Date createdAt;
    private String createdBy;
    private Date updatedAt;
    private String updatedBy;
    private Integer tpsLinkedEligibilty = TransferPointSettingLinkedEligibity.NO_AUTHORIZATION;

    private TransferPointSettingBuilder() {
    }

    public static TransferPointSettingBuilder aTransferPointSetting() {
        return new TransferPointSettingBuilder();
    }

    public TransferPointSettingBuilder withTpsId(Long tpsId) {
        this.tpsId = tpsId;
        return this;
    }

    public TransferPointSettingBuilder withTpsMerchantNo(Long tpsMerchantNo) {
        this.tpsMerchantNo = tpsMerchantNo;
        return this;
    }

    public TransferPointSettingBuilder withTpsLocation(Long tpsLocation) {
        this.tpsLocation = tpsLocation;
        return this;
    }

    public TransferPointSettingBuilder withTpsTransferCompatibility(Integer tpsTransferCompatibility) {
        this.tpsTransferCompatibility = tpsTransferCompatibility;
        return this;
    }

    public TransferPointSettingBuilder withTpsTransferCharge(Double tpsTransferCharge) {
        this.tpsTransferCharge = tpsTransferCharge;
        return this;
    }

    public TransferPointSettingBuilder withTpsIsTierAffected(Integer tpsIsTierAffected) {
        this.tpsIsTierAffected = tpsIsTierAffected;
        return this;
    }

    public TransferPointSettingBuilder withTpsTransferredPointValidity(Integer tpsTransferredPointValidity) {
        this.tpsTransferredPointValidity = tpsTransferredPointValidity;
        return this;
    }

    public TransferPointSettingBuilder withTpsMaxTransfers(Integer tpsMaxTransfers) {
        this.tpsMaxTransfers = tpsMaxTransfers;
        return this;
    }

    public TransferPointSettingBuilder withTpsLinkedEligibility(Integer tpsLinkedEligibilty) {
        this.tpsLinkedEligibilty = tpsLinkedEligibilty;
        return this;
    }

    public TransferPointSettingBuilder withCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
        return this;
    }

    public TransferPointSettingBuilder withCreatedBy(String createdBy) {
        this.createdBy = createdBy;
        return this;
    }

    public TransferPointSettingBuilder withUpdatedAt(Date updatedAt) {
        this.updatedAt = updatedAt;
        return this;
    }

    public TransferPointSettingBuilder withUpdatedBy(String updatedBy) {
        this.updatedBy = updatedBy;
        return this;
    }

    public TransferPointSetting build() {
        TransferPointSetting transferPointSetting = new TransferPointSetting();
        transferPointSetting.setTpsId(tpsId);
        transferPointSetting.setTpsMerchantNo(tpsMerchantNo);
        transferPointSetting.setTpsLocation(tpsLocation);
        transferPointSetting.setTpsTransferCompatibility(tpsTransferCompatibility);
        transferPointSetting.setTpsTransferCharge(tpsTransferCharge);
        transferPointSetting.setTpsIsTierAffected(tpsIsTierAffected);
        transferPointSetting.setTpsTransferredPointValidity(tpsTransferredPointValidity);
        transferPointSetting.setTpsMaxTransfers(tpsMaxTransfers);
        transferPointSetting.setCreatedAt(createdAt);
        transferPointSetting.setCreatedBy(createdBy);
        transferPointSetting.setUpdatedAt(updatedAt);
        transferPointSetting.setUpdatedBy(updatedBy);
        transferPointSetting.setTpsLinkedEligibilty(tpsLinkedEligibilty);
        return transferPointSetting;
    }
}
