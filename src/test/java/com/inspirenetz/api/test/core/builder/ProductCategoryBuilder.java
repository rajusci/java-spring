package com.inspirenetz.api.test.core.builder;

import com.inspirenetz.api.core.domain.ProductCategory;

/**
 * Created by sandheepgr on 2/5/14.
 */
public class ProductCategoryBuilder {
    private Long pcyId;
    private String pcyCode;
    private Long pcyMerchantNo;
    private String pcyName;
    private String pcyDescription;

    private ProductCategoryBuilder() {
    }

    public static ProductCategoryBuilder aProductCategory() {
        return new ProductCategoryBuilder();
    }

    public ProductCategoryBuilder withPcyId(Long pcyId) {
        this.pcyId = pcyId;
        return this;
    }

    public ProductCategoryBuilder withPcyCode(String pcyCode) {
        this.pcyCode = pcyCode;
        return this;
    }

    public ProductCategoryBuilder withPcyMerchantNo(Long pcyMerchantNo) {
        this.pcyMerchantNo = pcyMerchantNo;
        return this;
    }

    public ProductCategoryBuilder withPcyName(String pcyName) {
        this.pcyName = pcyName;
        return this;
    }

    public ProductCategoryBuilder withPcyDescription(String pcyDescription) {
        this.pcyDescription = pcyDescription;
        return this;
    }

    public ProductCategory build() {
        ProductCategory productCategory = new ProductCategory();
        productCategory.setPcyId(pcyId);
        productCategory.setPcyCode(pcyCode);
        productCategory.setPcyMerchantNo(pcyMerchantNo);
        productCategory.setPcyName(pcyName);
        productCategory.setPcyDescription(pcyDescription);
        return productCategory;
    }
}
