package com.inspirenetz.api.test.core.builder;

import com.inspirenetz.api.core.domain.PrimaryLoyalty;

/**
 * Created by saneesh-ci on 27/8/14.
 */
public class PrimaryLoyaltyBuilder {
    private Long pllLocation;
    private Integer pllStatus;
    private String pllLName;
    private String pllFName;
    private String pllLoyaltyId;
    private Long pllCustomerNo;
    private Long pllId;

    private PrimaryLoyaltyBuilder() {
    }

    public static PrimaryLoyaltyBuilder aPrimaryLoyalty() {
        return new PrimaryLoyaltyBuilder();
    }

    public PrimaryLoyaltyBuilder withPllLocation(Long pllLocation) {
        this.pllLocation = pllLocation;
        return this;
    }

    public PrimaryLoyaltyBuilder withPllStatus(Integer pllStatus) {
        this.pllStatus = pllStatus;
        return this;
    }

    public PrimaryLoyaltyBuilder withPllLName(String pllLName) {
        this.pllLName = pllLName;
        return this;
    }

    public PrimaryLoyaltyBuilder withPllFName(String pllFName) {
        this.pllFName = pllFName;
        return this;
    }

    public PrimaryLoyaltyBuilder withPllLoyaltyId(String pllLoyaltyId) {
        this.pllLoyaltyId = pllLoyaltyId;
        return this;
    }

    public PrimaryLoyaltyBuilder withPllCustomerNo(Long pllCustomerNo) {
        this.pllCustomerNo = pllCustomerNo;
        return this;
    }

    public PrimaryLoyaltyBuilder withPllId(Long pllId) {
        this.pllId = pllId;
        return this;
    }

    public PrimaryLoyalty build() {
        PrimaryLoyalty primaryLoyalty = new PrimaryLoyalty();
        primaryLoyalty.setPllLocation(pllLocation);
        primaryLoyalty.setPllStatus(pllStatus);
        primaryLoyalty.setPllLName(pllLName);
        primaryLoyalty.setPllFName(pllFName);
        primaryLoyalty.setPllLoyaltyId(pllLoyaltyId);
        primaryLoyalty.setPllCustomerNo(pllCustomerNo);
        primaryLoyalty.setPllId(pllId);
        return primaryLoyalty;
    }
}
