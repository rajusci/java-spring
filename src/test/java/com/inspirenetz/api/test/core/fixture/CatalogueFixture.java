package com.inspirenetz.api.test.core.fixture;

import com.inspirenetz.api.core.dictionary.DeliveryType;
import com.inspirenetz.api.core.dictionary.ImagePrimaryId;
import com.inspirenetz.api.core.dictionary.IndicatorStatus;
import com.inspirenetz.api.core.dictionary.RequestChannel;
import com.inspirenetz.api.core.domain.Catalogue;
import com.inspirenetz.api.test.core.builder.CatalogueBuilder;

import java.util.HashSet;
import java.util.Set;

/**
 * Created by sandheepgr on 17/2/14.
 */
public class CatalogueFixture {

    public static Catalogue standardCatalogue() {

        Catalogue catalogue = CatalogueBuilder.aCatalogue()
                .withCatProductCode("TEST001")
                .withCatDescription("TEST CATALOGUE")
                .withCatCategory(1)
                .withCatRewardCurrencyId(1L)
                .withCatNumPoints(10.0)
                .withCatMerchantNo(1L)
                .withCatDeliveryType(DeliveryType.INSTORE)
                .withCatProductImage(ImagePrimaryId.PRIMARY_CATALOGUE_IMAGE)
                .withCatPartialCashPaymentRequired(IndicatorStatus.NO)
                .withCatChannelValues(RequestChannel.RDM_WEB+"")
                .build();

        return catalogue;
    }


    public static Catalogue updatedStandardCatalogue(Catalogue catalogue) {

        catalogue.setCatDescription("New description");
        catalogue.setCatLongDescription("New long description");
        return catalogue;

    }




    public static Set<Catalogue> standardCatalogues() {

        Set<Catalogue> catalogues = new HashSet<>();

        Catalogue catalogue1 = CatalogueBuilder.aCatalogue()
                .withCatProductCode("PRDTEST1001")
                .withCatDescription("Test item 1")
                .withCatCategory(1)
                .withCatRewardCurrencyId(1L)
                .withCatNumPoints(10.0)
                .withCatMerchantNo(1L)
                .withCatDeliveryType(DeliveryType.INSTORE)
                .withCatProductImage(ImagePrimaryId.PRIMARY_CATALOGUE_IMAGE)
                .withCatPartialCashPaymentRequired(IndicatorStatus.NO)
                .withCatChannelValues(RequestChannel.RDM_WEB+"")
                .build();

        catalogues.add(catalogue1);


        Catalogue catalogue2 = CatalogueBuilder.aCatalogue()
                .withCatProductCode("PRDTEST1002")
                .withCatDescription("An item")
                .withCatCategory(1)
                .withCatRewardCurrencyId(1L)
                .withCatNumPoints(10.0)
                .withCatMerchantNo(1L)
                .withCatDeliveryType(DeliveryType.INSTORE)
                .withCatProductImage(ImagePrimaryId.PRIMARY_CATALOGUE_IMAGE)
                .withCatPartialCashPaymentRequired(IndicatorStatus.NO)
                .withCatChannelValues(RequestChannel.RDM_WEB+"")
                .build();

        catalogues.add(catalogue2);


        return catalogues;
    }
}
