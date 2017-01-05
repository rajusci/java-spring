package com.inspirenetz.api.test.core.fixture;

import com.inspirenetz.api.core.domain.Vendor;
import com.inspirenetz.api.test.core.builder.VendorBuilder;

import java.util.HashSet;
import java.util.Set;

/**
 * Created by sandheepgr on 1/8/14.
 */
public class VendorFixture {


    public static Vendor standardVendor() {

        Vendor vendor = VendorBuilder.aVendor()
                .withVenMerchantNo(1L)
                .withVenName("TEST VENDOR1")
                .withVenDescription("TEST VENDOR DESC")
                .withVenCategory(1L)
                .build();


        return vendor;

    }


    public  static Vendor updatedStandardVendor(Vendor vendor ) {

        vendor.setVenDescription("TEST VENDOR DESC UPDATED");
        return vendor;

    }


    public static Set<Vendor> standardVendors() {

        Set<Vendor> vendorSet = new HashSet<>(0);

        Vendor vendor1 = VendorBuilder.aVendor()
                .withVenMerchantNo(1L)
                .withVenName("TEST VENDOR1")
                .withVenDescription("TEST VENDOR DESC")
                .withVenCategory(1L)
                .build();

        vendorSet.add(vendor1);



        Vendor vendor2 = VendorBuilder.aVendor()
                .withVenMerchantNo(1L)
                .withVenName("TEST VENDOR1")
                .withVenDescription("TEST VENDOR DESC")
                .withVenCategory(1L)
                .build();

        vendorSet.add(vendor2);



        return vendorSet;
    }
}
