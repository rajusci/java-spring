package com.inspirenetz.api.test.core.builder;

import com.inspirenetz.api.core.dictionary.IndicatorStatus;
import com.inspirenetz.api.core.dictionary.MerchantSettlementType;
import com.inspirenetz.api.core.domain.MerchantSettlement;

/**
 * Created by saneesh on 22/9/15.
 */
public class MerchantSettlementBuilder {
    private Long mesId;
    private String mesInternalRef;
    private String mesLoyaltyId;
    private Long mesVendorNo;
    private Integer mesIsSettled = IndicatorStatus.YES;
    private Integer mesSettlementType = MerchantSettlementType.LOAD_WALLET;

    private MerchantSettlementBuilder() {
    }

    public static MerchantSettlementBuilder aMerchantSettlement() {
        return new MerchantSettlementBuilder();
    }

    public MerchantSettlementBuilder withMesId(Long mesId) {
        this.mesId = mesId;
        return this;
    }

    public MerchantSettlementBuilder withMesInternalRef(String mesInternalRef) {
        this.mesInternalRef = mesInternalRef;
        return this;
    }

    public MerchantSettlementBuilder withMesLoyaltyId(String mesLoyaltyId) {
        this.mesLoyaltyId = mesLoyaltyId;
        return this;
    }

    public MerchantSettlementBuilder withMesVendorNo(Long mesVendorNo) {
        this.mesVendorNo = mesVendorNo;
        return this;
    }

    public MerchantSettlementBuilder withMesIsSettled(Integer mesIsSettled) {
        this.mesIsSettled = mesIsSettled;
        return this;
    }

    public MerchantSettlementBuilder withMesSettlementType(Integer mesSettlementType) {
        this.mesSettlementType = mesSettlementType;
        return this;
    }

    public MerchantSettlementBuilder but() {
        return aMerchantSettlement().withMesId(mesId).withMesInternalRef(mesInternalRef).withMesLoyaltyId(mesLoyaltyId).withMesVendorNo(mesVendorNo).withMesIsSettled(mesIsSettled).withMesSettlementType(mesSettlementType);
    }

    public MerchantSettlement build() {
        MerchantSettlement merchantSettlement = new MerchantSettlement();
        merchantSettlement.setMesId(mesId);
        merchantSettlement.setMesInternalRef(mesInternalRef);
        merchantSettlement.setMesLoyaltyId(mesLoyaltyId);
        merchantSettlement.setMesVendorNo(mesVendorNo);
        merchantSettlement.setMesIsSettled(mesIsSettled);
        merchantSettlement.setMesSettlementType(mesSettlementType);
        return merchantSettlement;
    }
}
