package com.inspirenetz.api.test.core.fixture;

import com.inspirenetz.api.core.domain.Brand;
import com.inspirenetz.api.core.domain.ProductCategory;
import com.inspirenetz.api.test.core.builder.BrandBuilder;
import com.inspirenetz.api.test.core.builder.ProductCategoryBuilder;

import java.util.HashSet;
import java.util.Set;

/**
 * Created by sandheepgr on 30/4/14.
 */
public class ProductCategoryFixture {

    public static ProductCategory standardProductCategory() {

        ProductCategory productCategory = ProductCategoryBuilder.aProductCategory()
                .withPcyMerchantNo(1L)
                .withPcyCode("PCY1000143")
                .withPcyName("Grocery")
                .withPcyDescription("Grocery items")
                .build();

        return productCategory;

    }


    public static ProductCategory updatedStandardProductCategory(ProductCategory productCategory) {

        productCategory.setPcyName("Grocery Items");
        productCategory.setPcyDescription("Any grocery items");

        return productCategory;

    }



    public static Set<ProductCategory> standardProductCategories() {

        Set<ProductCategory> productCategories = new HashSet<>();

        ProductCategory grocery = ProductCategoryBuilder.aProductCategory()
                .withPcyMerchantNo(1L)
                .withPcyCode("PCY1000143")
                .withPcyName("Grocery")
                .withPcyDescription("Grocery items")
                .build();

        productCategories.add(grocery);


        ProductCategory foodItems = ProductCategoryBuilder.aProductCategory()
                .withPcyMerchantNo(1L)
                .withPcyCode("PCY1000145")
                .withPcyName("Food Items")
                .withPcyDescription("Food items")
                .build();

        productCategories.add(foodItems);


        return productCategories;

    }

}





