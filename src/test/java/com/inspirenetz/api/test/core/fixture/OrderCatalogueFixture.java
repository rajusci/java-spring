package com.inspirenetz.api.test.core.fixture;

import com.inspirenetz.api.core.dictionary.DeliveryType;
import com.inspirenetz.api.core.dictionary.ImagePrimaryId;
import com.inspirenetz.api.core.domain.OrderCatalogue;
import com.inspirenetz.api.test.core.builder.OrderCatalogueBuilder;

import java.util.HashSet;
import java.util.Set;

/**
 * Created by sandheepgr on 30/7/14.
 */
public class OrderCatalogueFixture {

    public static OrderCatalogue standardOrderCatalogue() {

        OrderCatalogue orderCatalogue = OrderCatalogueBuilder.anOrderCatalogue()
                .withOrcMerchantNo(1L)
                .withOrcProductCode("PRDTEST1001")
                .withOrcDescription("TEST ORC CATALOGUE1")
                .withOrcCategory(1)
                .withOrcPrice(10.0)
                .withOrcTaxPerc(1.0)
                .withOrcAvailableLocation(1L)
                .withOrcProductImage(ImagePrimaryId.PRIMARY_CATALOGUE_IMAGE)
                .withOrcDeliveryType(DeliveryType.BOTH)
                .build();


        return orderCatalogue;

    }


    public static OrderCatalogue updatedStandardOrderCatalogue(OrderCatalogue catalogue) {

        catalogue.setOrcDescription("New description");
        catalogue.setOrcLongDescription("New long description");
        return catalogue;

    }




    public static Set<OrderCatalogue> standardOrderCatalogues() {

        Set<OrderCatalogue> catalogues = new HashSet<>();

        OrderCatalogue catalogue1 = OrderCatalogueBuilder.anOrderCatalogue()
                .withOrcMerchantNo(1L)
                .withOrcProductCode("PRDTEST1001")
                .withOrcDescription("TEST ORC CATALOGUE1")
                .withOrcCategory(1)
                .withOrcPrice(10.0)
                .withOrcTaxPerc(1.0)
                .withOrcAvailableLocation(1L)
                .withOrcProductImage(ImagePrimaryId.PRIMARY_CATALOGUE_IMAGE)
                .withOrcDeliveryType(DeliveryType.BOTH)
                .build();

        catalogues.add(catalogue1);


        OrderCatalogue catalogue2 = OrderCatalogueBuilder.anOrderCatalogue()
                .withOrcMerchantNo(1L)
                .withOrcProductCode("PRDTEST1002")
                .withOrcDescription("TEST ORC CATALOGUE2")
                .withOrcCategory(1)
                .withOrcPrice(20.0)
                .withOrcTaxPerc(1.0)
                .withOrcAvailableLocation(1L)
                .withOrcProductImage(ImagePrimaryId.PRIMARY_CATALOGUE_IMAGE)
                .withOrcDeliveryType(DeliveryType.BOTH)
                .build();

        catalogues.add(catalogue2);


        return catalogues;
    }
}


