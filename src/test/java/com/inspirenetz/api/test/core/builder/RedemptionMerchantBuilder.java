package com.inspirenetz.api.test.core.builder;

import com.inspirenetz.api.core.dictionary.RedemptionMerchantStatus;
import com.inspirenetz.api.core.domain.RedemptionMerchant;
import com.inspirenetz.api.core.domain.RedemptionMerchantLocation;

import java.util.Set;

/**
 * Created by saneesh-ci on 26/9/14.
 */
public class RedemptionMerchantBuilder {
    public Set<RedemptionMerchantLocation> redemptionMerchantLocations;
    private Long remNo;
    private String remName;
    private Long remCategory;
    private String remAddress;
    private String remContactPerson = "";
    private String remContactEmail;
    private String remContactMobile;
    private String remVoucherPrefix;
    private int remStatus = RedemptionMerchantStatus.ACCOUNT_ACTIVE;

    private RedemptionMerchantBuilder() {
    }

    public static RedemptionMerchantBuilder aRedemptionMerchant() {
        return new RedemptionMerchantBuilder();
    }

    public RedemptionMerchantBuilder withRedemptionMerchantLocations(Set<RedemptionMerchantLocation> redemptionMerchantLocations) {
        this.redemptionMerchantLocations = redemptionMerchantLocations;
        return this;
    }

    public RedemptionMerchantBuilder withRemNo(Long remNo) {
        this.remNo = remNo;
        return this;
    }

    public RedemptionMerchantBuilder withRemName(String remName) {
        this.remName = remName;
        return this;
    }

    public RedemptionMerchantBuilder withRemCategory(Long remCategory) {
        this.remCategory = remCategory;
        return this;
    }

    public RedemptionMerchantBuilder withRemAddress(String remAddress) {
        this.remAddress = remAddress;
        return this;
    }

    public RedemptionMerchantBuilder withRemContactPerson(String remContactPerson) {
        this.remContactPerson = remContactPerson;
        return this;
    }

    public RedemptionMerchantBuilder withRemContactEmail(String remContactEmail) {
        this.remContactEmail = remContactEmail;
        return this;
    }

    public RedemptionMerchantBuilder withRemContactMobile(String remContactMobile) {
        this.remContactMobile = remContactMobile;
        return this;
    }

    public RedemptionMerchantBuilder withRemVoucherPrefix(String remVoucherPrefix) {
        this.remVoucherPrefix = remVoucherPrefix;
        return this;
    }

    public RedemptionMerchantBuilder withRemStatus(int remStatus) {
        this.remStatus = remStatus;
        return this;
    }

    public RedemptionMerchant build() {
        RedemptionMerchant redemptionMerchant = new RedemptionMerchant();
        redemptionMerchant.setRedemptionMerchantLocations(redemptionMerchantLocations);
        redemptionMerchant.setRemNo(remNo);
        redemptionMerchant.setRemName(remName);
        redemptionMerchant.setRemCategory(remCategory);
        redemptionMerchant.setRemAddress(remAddress);
        redemptionMerchant.setRemContactPerson(remContactPerson);
        redemptionMerchant.setRemContactEmail(remContactEmail);
        redemptionMerchant.setRemContactMobile(remContactMobile);
        redemptionMerchant.setRemVoucherPrefix(remVoucherPrefix);
        redemptionMerchant.setRemStatus(remStatus);
        return redemptionMerchant;
    }
}
