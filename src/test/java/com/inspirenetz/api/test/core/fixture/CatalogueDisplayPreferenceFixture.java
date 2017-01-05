package com.inspirenetz.api.test.core.fixture;

import com.inspirenetz.api.core.domain.CatalogueDisplayPreference;
import com.inspirenetz.api.test.core.builder.CatalogueDisplayPreferenceBuilder;

import java.util.HashSet;
import java.util.Set;

/**
 * Created by saneesh-ci on 9/9/14.
 */
public class CatalogueDisplayPreferenceFixture {




    public static CatalogueDisplayPreference standardCataloguDisplayPreference() {


        CatalogueDisplayPreference redemptionVoucher = CatalogueDisplayPreferenceBuilder.aCatalogueDisplayPreference()
                .withCdpMerchantNo(1L)
                .withCdpPreferences("FAV:CAT001")
                .build();


        return redemptionVoucher;


    }


    public static CatalogueDisplayPreference updatedStandardCataloguDisplayPreference(CatalogueDisplayPreference catalogueDisplayPreference) {

        catalogueDisplayPreference.setCdpPreferences("FAV");

        return catalogueDisplayPreference;

    }



}
