package com.inspirenetz.api.test.core.builder;

import com.inspirenetz.api.core.domain.Brand;

/**
 * Created by sandheepgr on 30/4/14.
 */
public class BrandBuilder {
    private Long brnId;
    private String brnCode;
    private Long brnMerchantNo;
    private String brnName;
    private String brnDescription;

    private BrandBuilder() {
    }

    public static BrandBuilder aBrand() {
        return new BrandBuilder();
    }

    public BrandBuilder withBrnId(Long brnId) {
        this.brnId = brnId;
        return this;
    }

    public BrandBuilder withBrnCode(String brnCode) {
        this.brnCode = brnCode;
        return this;
    }

    public BrandBuilder withBrnMerchantNo(Long brnMerchantNo) {
        this.brnMerchantNo = brnMerchantNo;
        return this;
    }

    public BrandBuilder withBrnName(String brnName) {
        this.brnName = brnName;
        return this;
    }

    public BrandBuilder withBrnDescription(String brnDescription) {
        this.brnDescription = brnDescription;
        return this;
    }

    public Brand build() {
        Brand brand = new Brand();
        brand.setBrnId(brnId);
        brand.setBrnCode(brnCode);
        brand.setBrnMerchantNo(brnMerchantNo);
        brand.setBrnName(brnName);
        brand.setBrnDescription(brnDescription);
        return brand;
    }
}
