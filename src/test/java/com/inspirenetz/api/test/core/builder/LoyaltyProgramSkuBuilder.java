package com.inspirenetz.api.test.core.builder;

import com.inspirenetz.api.core.dictionary.LoyaltyProgramSkuType;
import com.inspirenetz.api.core.domain.LoyaltyProgramSku;
import com.inspirenetz.api.core.domain.LoyaltyProgramSkuExtension;
import com.inspirenetz.api.core.incustomization.attrext.AttributeExtendedEntityMap;

import java.util.Date;
import java.util.Set;

/**
 * Created by sandheepgr on 13/9/14.
 */
public class LoyaltyProgramSkuBuilder {
    private Long lpuId;
    private Long lpuProgramId ;
    private int lpuItemType = LoyaltyProgramSkuType.PRODUCT_CATEGORY;
    private String lpuItemCode = "";
    private Long lpuTier = 0L;
    private Integer lpuTransactionType = 0;
    private double lpuPrgRatioNum = 1 ;
    private double lpuPrgRatioDeno = 1;
    private Set<LoyaltyProgramSkuExtension> loyaltyProgramSkuExtensionSet;
    private AttributeExtendedEntityMap fieldMap;
    private Date createdAt;
    private String createdBy;
    private Date updatedAt;
    private String updatedBy;

    private LoyaltyProgramSkuBuilder() {
    }

    public static LoyaltyProgramSkuBuilder aLoyaltyProgramSku() {
        return new LoyaltyProgramSkuBuilder();
    }

    public LoyaltyProgramSkuBuilder withLpuId(Long lpuId) {
        this.lpuId = lpuId;
        return this;
    }

    public LoyaltyProgramSkuBuilder withLpuProgramId(Long lpuProgramId) {
        this.lpuProgramId = lpuProgramId;
        return this;
    }

    public LoyaltyProgramSkuBuilder withLpuItemType(int lpuItemType) {
        this.lpuItemType = lpuItemType;
        return this;
    }

    public LoyaltyProgramSkuBuilder withLpuItemCode(String lpuItemCode) {
        this.lpuItemCode = lpuItemCode;
        return this;
    }

    public LoyaltyProgramSkuBuilder withLpuTransactionType(Integer lpuTransactionType) {
        this.lpuTransactionType = lpuTransactionType;
        return this;
    }

    public LoyaltyProgramSkuBuilder withLpuTier(Long lpuTier) {
        this.lpuTier = lpuTier;
        return this;
    }

    public LoyaltyProgramSkuBuilder withLpuPrgRatioNum(double lpuPrgRatioNum) {
        this.lpuPrgRatioNum = lpuPrgRatioNum;
        return this;
    }

    public LoyaltyProgramSkuBuilder withLpuPrgRatioDeno(double lpuPrgRatioDeno) {
        this.lpuPrgRatioDeno = lpuPrgRatioDeno;
        return this;
    }

    public LoyaltyProgramSkuBuilder withLoyaltyProgramSkuExtensionSet(Set<LoyaltyProgramSkuExtension> loyaltyProgramSkuExtensionSet) {
        this.loyaltyProgramSkuExtensionSet = loyaltyProgramSkuExtensionSet;
        return this;
    }

    public LoyaltyProgramSkuBuilder withFieldMap(AttributeExtendedEntityMap fieldMap) {
        this.fieldMap = fieldMap;
        return this;
    }

    public LoyaltyProgramSkuBuilder withCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
        return this;
    }

    public LoyaltyProgramSkuBuilder withCreatedBy(String createdBy) {
        this.createdBy = createdBy;
        return this;
    }

    public LoyaltyProgramSkuBuilder withUpdatedAt(Date updatedAt) {
        this.updatedAt = updatedAt;
        return this;
    }

    public LoyaltyProgramSkuBuilder withUpdatedBy(String updatedBy) {
        this.updatedBy = updatedBy;
        return this;
    }

    public LoyaltyProgramSku build() {
        LoyaltyProgramSku loyaltyProgramSku = new LoyaltyProgramSku();
        loyaltyProgramSku.setLpuId(lpuId);
        loyaltyProgramSku.setLpuProgramId(lpuProgramId);
        loyaltyProgramSku.setLpuItemType(lpuItemType);
        loyaltyProgramSku.setLpuItemCode(lpuItemCode);
        loyaltyProgramSku.setLpuTransactionType(lpuTransactionType);
        loyaltyProgramSku.setLpuTier(lpuTier);
        loyaltyProgramSku.setLpuPrgRatioNum(lpuPrgRatioNum);
        loyaltyProgramSku.setLpuPrgRatioDeno(lpuPrgRatioDeno);
        loyaltyProgramSku.setLoyaltyProgramSkuExtensionSet(loyaltyProgramSkuExtensionSet);
        loyaltyProgramSku.setFieldMap(fieldMap);
        loyaltyProgramSku.setCreatedAt(createdAt);
        loyaltyProgramSku.setCreatedBy(createdBy);
        loyaltyProgramSku.setUpdatedAt(updatedAt);
        loyaltyProgramSku.setUpdatedBy(updatedBy);
        return loyaltyProgramSku;
    }
}
