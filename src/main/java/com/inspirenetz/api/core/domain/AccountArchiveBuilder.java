package com.inspirenetz.api.core.domain;

/**
 * Created by sandheepgr on 8/9/14.
 */
public class AccountArchiveBuilder {
    private Long aarId;
    private Long aarMerchantNo;
    private String aarOldLoyaltyId;
    private String aarNewLoyaltyId;

    private AccountArchiveBuilder() {
    }

    public static AccountArchiveBuilder anAccountArchive() {
        return new AccountArchiveBuilder();
    }

    public AccountArchiveBuilder withAarId(Long aarId) {
        this.aarId = aarId;
        return this;
    }

    public AccountArchiveBuilder withAarMerchantNo(Long aarMerchantNo) {
        this.aarMerchantNo = aarMerchantNo;
        return this;
    }

    public AccountArchiveBuilder withAarOldLoyaltyId(String aarOldLoyaltyId) {
        this.aarOldLoyaltyId = aarOldLoyaltyId;
        return this;
    }

    public AccountArchiveBuilder withAarNewLoyaltyId(String aarNewLoyaltyId) {
        this.aarNewLoyaltyId = aarNewLoyaltyId;
        return this;
    }

    public AccountArchive build() {
        AccountArchive accountArchive = new AccountArchive();
        accountArchive.setAarId(aarId);
        accountArchive.setAarMerchantNo(aarMerchantNo);
        accountArchive.setAarOldLoyaltyId(aarOldLoyaltyId);
        accountArchive.setAarNewLoyaltyId(aarNewLoyaltyId);
        return accountArchive;
    }
}
