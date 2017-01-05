package com.inspirenetz.api.test.core.builder;

import com.inspirenetz.api.core.dictionary.ImagePrimaryId;
import com.inspirenetz.api.core.dictionary.IndicatorStatus;
import com.inspirenetz.api.core.domain.CatalogueCategory;

import java.util.Date;

/**
 * Created by sandheepgr on 1/8/14.
 */
public class CatalogueCategoryBuilder {
    private Long cacId;
    private String cacName = "";
    private String cacDescription = "'";
    private Integer cacParentGroup = 0;
    private Integer cacFirstLevelInd = IndicatorStatus.NO;
    private Long cacImage = ImagePrimaryId.PRIMARY_CATALOGUE_CATEGORY_IMAGE;
    private Date createdAt;
    private String createdBy;
    private Date updatedAt;
    private String updatedBy;

    private CatalogueCategoryBuilder() {
    }

    public static CatalogueCategoryBuilder aCatalogueCategory() {
        return new CatalogueCategoryBuilder();
    }

    public CatalogueCategoryBuilder withCacId(Long cacId) {
        this.cacId = cacId;
        return this;
    }

    public CatalogueCategoryBuilder withCacName(String cacName) {
        this.cacName = cacName;
        return this;
    }

    public CatalogueCategoryBuilder withCacDescription(String cacDescription) {
        this.cacDescription = cacDescription;
        return this;
    }

    public CatalogueCategoryBuilder withCacParentGroup(Integer cacParentGroup) {
        this.cacParentGroup = cacParentGroup;
        return this;
    }

    public CatalogueCategoryBuilder withCacFirstLevelInd(Integer cacFirstLevelInd) {
        this.cacFirstLevelInd = cacFirstLevelInd;
        return this;
    }

    public CatalogueCategoryBuilder withCacImage(Long cacImage) {
        this.cacImage = cacImage;
        return this;
    }

    public CatalogueCategoryBuilder withCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
        return this;
    }

    public CatalogueCategoryBuilder withCreatedBy(String createdBy) {
        this.createdBy = createdBy;
        return this;
    }

    public CatalogueCategoryBuilder withUpdatedAt(Date updatedAt) {
        this.updatedAt = updatedAt;
        return this;
    }

    public CatalogueCategoryBuilder withUpdatedBy(String updatedBy) {
        this.updatedBy = updatedBy;
        return this;
    }

    public CatalogueCategory build() {
        CatalogueCategory catalogueCategory = new CatalogueCategory();
        catalogueCategory.setCacId(cacId);
        catalogueCategory.setCacName(cacName);
        catalogueCategory.setCacDescription(cacDescription);
        catalogueCategory.setCacParentGroup(cacParentGroup);
        catalogueCategory.setCacFirstLevelInd(cacFirstLevelInd);
        catalogueCategory.setCacImage(cacImage);
        catalogueCategory.setCreatedAt(createdAt);
        catalogueCategory.setCreatedBy(createdBy);
        catalogueCategory.setUpdatedAt(updatedAt);
        catalogueCategory.setUpdatedBy(updatedBy);
        return catalogueCategory;
    }
}
