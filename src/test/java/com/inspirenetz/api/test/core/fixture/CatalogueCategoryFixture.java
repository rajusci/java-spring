package com.inspirenetz.api.test.core.fixture;

import com.inspirenetz.api.core.dictionary.IndicatorStatus;
import com.inspirenetz.api.core.domain.CatalogueCategory;
import com.inspirenetz.api.test.core.builder.CatalogueCategoryBuilder;

import java.util.HashSet;
import java.util.Set;

/**
 * Created by sandheepgr on 1/8/14.
 */
public class CatalogueCategoryFixture {

    public static CatalogueCategory standardCatalogueCategory() {

        CatalogueCategory   catalogueCategory = CatalogueCategoryBuilder.aCatalogueCategory()
                .withCacName("TEST CATALOGUE CATEGORY1")
                .withCacDescription("TEST CATALOGUE DESC1")
                .withCacParentGroup(1)
                .withCacFirstLevelInd(IndicatorStatus.YES)
                .build();


        return catalogueCategory;


    }


    public static CatalogueCategory updatedStandardCatalogueCategory(CatalogueCategory  catalogueCategory) {

        catalogueCategory.setCacDescription("UPDATED TEST CATALOGUE DESC");
        return catalogueCategory;

    }


    public static Set<CatalogueCategory> standardCatalogueCategories() {

        Set<CatalogueCategory> catalogueCategorySet = new HashSet<>(0);

        CatalogueCategory   catalogueCategory1 = CatalogueCategoryBuilder.aCatalogueCategory()
                .withCacName("TEST CATALOGUE CATEGORY1")
                .withCacDescription("TEST CATALOGUE DESC1")
                .withCacParentGroup(1)
                .build();

        catalogueCategorySet.add(catalogueCategory1);


        CatalogueCategory   catalogueCategory2 = CatalogueCategoryBuilder.aCatalogueCategory()
                .withCacName("TEST CATALOGUE CATEGORY2")
                .withCacDescription("TEST CATALOGUE DESC2")
                .withCacParentGroup(1)
                .build();

        catalogueCategorySet.add(catalogueCategory2);


        return catalogueCategorySet;


    }

}
