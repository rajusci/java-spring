package com.inspirenetz.api.test.core.builder;

import com.inspirenetz.api.core.domain.LoyaltyExtension;

/**
 * Created by saneesh-ci on 10/9/14.
 */
public class LoyaltyExtensionBuilder {
    private Long lexMerchantNo;
    private Long lexId;
    private String lexName;
    private String lexDescription;
    private String lexFile;

    private LoyaltyExtensionBuilder() {
    }

    public static LoyaltyExtensionBuilder aLoyaltyExtension() {
        return new LoyaltyExtensionBuilder();
    }

    public LoyaltyExtensionBuilder withLexMerchantNo(Long lexMerchantNo) {
        this.lexMerchantNo = lexMerchantNo;
        return this;
    }

    public LoyaltyExtensionBuilder withLexId(Long lexId) {
        this.lexId = lexId;
        return this;
    }

    public LoyaltyExtensionBuilder withLexName(String lexName) {
        this.lexName = lexName;
        return this;
    }

    public LoyaltyExtensionBuilder withLexDescription(String lexDescription) {
        this.lexDescription = lexDescription;
        return this;
    }

    public LoyaltyExtensionBuilder withLexFile(String lexFile) {
        this.lexFile = lexFile;
        return this;
    }

    public LoyaltyExtension build() {
        LoyaltyExtension loyaltyExtension = new LoyaltyExtension();
        loyaltyExtension.setLexMerchantNo(lexMerchantNo);
        loyaltyExtension.setLexId(lexId);
        loyaltyExtension.setLexName(lexName);
        loyaltyExtension.setLexDescription(lexDescription);
        loyaltyExtension.setLexFile(lexFile);
        return loyaltyExtension;
    }
}
