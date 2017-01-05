package com.inspirenetz.api.test.core.builder;

import com.inspirenetz.api.core.domain.DrawChance;

/**
 * Created by saneesh-ci on 7/11/14.
 */
public class DrawChanceBuilder {
    private Long drcId;
    private Long drcCustomerNo;
    private Long drcChances;
    private Integer drcType;
    private Integer drcStatus;

    private DrawChanceBuilder() {
    }

    public static DrawChanceBuilder aDrawChance() {
        return new DrawChanceBuilder();
    }

    public DrawChanceBuilder withDrcId(Long drcId) {
        this.drcId = drcId;
        return this;
    }

    public DrawChanceBuilder withDrcCustomerNo(Long drcCustomerNo) {
        this.drcCustomerNo = drcCustomerNo;
        return this;
    }

    public DrawChanceBuilder withDrcChances(Long drcChances) {
        this.drcChances = drcChances;
        return this;
    }

    public DrawChanceBuilder withDrcType(Integer drcType) {
        this.drcType = drcType;
        return this;
    }

    public DrawChanceBuilder withDrcStatus(Integer drcStatus) {
        this.drcStatus = drcStatus;
        return this;
    }

    public DrawChanceBuilder but() {
        return aDrawChance().withDrcId(drcId).withDrcCustomerNo(drcCustomerNo).withDrcChances(drcChances).withDrcType(drcType).withDrcStatus(drcStatus);
    }

    public DrawChance build() {
        DrawChance drawChance = new DrawChance();
        drawChance.setDrcId(drcId);
        drawChance.setDrcCustomerNo(drcCustomerNo);
        drawChance.setDrcChances(drcChances);
        drawChance.setDrcType(drcType);
        drawChance.setDrcStatus(drcStatus);
        return drawChance;
    }
}
