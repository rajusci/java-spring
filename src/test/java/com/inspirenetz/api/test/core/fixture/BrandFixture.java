package com.inspirenetz.api.test.core.fixture;

import com.inspirenetz.api.core.domain.Brand;
import com.inspirenetz.api.test.core.builder.*;

import java.util.HashSet;
import java.util.Set;

/**
 * Created by sandheepgr on 30/4/14.
 */
public class BrandFixture {

    public static Brand standardBrand() {

        Brand brand = BrandBuilder.aBrand()
                .withBrnMerchantNo(1L)
                .withBrnCode("BRN1000231")
                .withBrnName("Pepsi")
                .withBrnDescription("Pepsi Soft Drinks")
                .build();


        return brand;


    }


    public static Brand updatedStandardBrand(Brand brand) {

        brand.setBrnName("CocaCola");
        brand.setBrnDescription("Another Soft Drink");

        return brand;

    }


    public static Set<Brand> standardBrands() {

        Set<Brand> brands = new HashSet<Brand>(0);

        Brand pepsi  = BrandBuilder.aBrand()
                .withBrnMerchantNo(1L)
                .withBrnCode("BRN1000231")
                .withBrnName("Pepsi")
                .withBrnDescription("Pepsi Soft drinks")
                .build();

        brands.add(pepsi);



        Brand coke = BrandBuilder.aBrand()
                .withBrnMerchantNo(1L)
                .withBrnCode("BRN1002343")
                .withBrnName("Coke")
                .withBrnDescription("Cocacola soft drink")
                .build();

        brands.add(coke);



        return brands;



    }
}
