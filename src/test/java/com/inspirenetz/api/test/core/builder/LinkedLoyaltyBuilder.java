package com.inspirenetz.api.test.core.builder;

import com.inspirenetz.api.core.domain.LinkedLoyalty;

/**
 * Created by saneesh-ci on 22/8/14.
 */
public class LinkedLoyaltyBuilder {
    private Long lilId;
    private Long lilParentCustomerNo;
    private Long lilChildCustomerNo;
    private Integer lilStatus;
    private Long lilLocation;

    private LinkedLoyaltyBuilder() {
    }

    public static LinkedLoyaltyBuilder aLinkedLoyalty() {
        return new LinkedLoyaltyBuilder();
    }

    public LinkedLoyaltyBuilder withLilId(Long lilId) {
        this.lilId = lilId;
        return this;
    }

    public LinkedLoyaltyBuilder withLilParentCustomerNo(Long lilParentCustomerNo) {
        this.lilParentCustomerNo = lilParentCustomerNo;
        return this;
    }

    public LinkedLoyaltyBuilder withLilChildCustomerNo(Long lilChildCustomerNo) {
        this.lilChildCustomerNo = lilChildCustomerNo;
        return this;
    }

    public LinkedLoyaltyBuilder withLilStatus(Integer lilStatus) {
        this.lilStatus = lilStatus;
        return this;
    }

    public LinkedLoyaltyBuilder withLilLocation(Long lilLocation) {
        this.lilLocation = lilLocation;
        return this;
    }

    public LinkedLoyalty build() {
        LinkedLoyalty linkedLoyalty = new LinkedLoyalty();
        linkedLoyalty.setLilId(lilId);
        linkedLoyalty.setLilParentCustomerNo(lilParentCustomerNo);
        linkedLoyalty.setLilChildCustomerNo(lilChildCustomerNo);
        linkedLoyalty.setLilStatus(lilStatus);
        linkedLoyalty.setLilLocation(lilLocation);
        return linkedLoyalty;
    }
}
