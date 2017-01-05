package com.inspirenetz.api.test.core.fixture;

import com.inspirenetz.api.core.dictionary.DrawType;
import com.inspirenetz.api.core.domain.PartnerCatalogue;
import com.inspirenetz.api.test.core.builder.PartnerCatalogueBuilder;

import java.util.HashSet;
import java.util.Set;

/**
 * Created by saneesh-ci on 27/9/14.
 */
public class PartnerCatalogueFixture {




    public static PartnerCatalogue standardPartnerCatalogue() {


        PartnerCatalogue partnerCatalogue = PartnerCatalogueBuilder.aPartnerCatalogue()
                .withPacId(100L)
                .withPacCategory(2)
                .withPacCode("TEST123")
                .withPacCost(200.0)
                .withPacDescription("Tester")
                .withPacName("Olive")
                .withPacStatus(1)
                .withPacPartnerNo(1L)
                .withPacStock(10.0)
                .build();


        return partnerCatalogue;


    }


    public static PartnerCatalogue updatedStandardPartnerCatalogue(PartnerCatalogue partnerCatalogue) {

        partnerCatalogue.setPacCost(10.00);

        return partnerCatalogue;

    }


    public static Set<PartnerCatalogue> standardPartnerCatalogues() {

        Set<PartnerCatalogue> partnerCatalogues = new HashSet<PartnerCatalogue>(0);

        PartnerCatalogue partnerCatalogueA  = PartnerCatalogueBuilder.aPartnerCatalogue()
                .withPacId(100L)
                .withPacCategory(2)
                .withPacCode("TEST123")
                .withPacCost(200.0)
                .withPacDescription("Tester")
                .withPacName("Olive")
                .withPacStatus(1)
                .withPacPartnerNo(1L)
                .withPacStock(10.0)
                .build();

        partnerCatalogues.add(partnerCatalogueA);


        PartnerCatalogue partnerCatalogueB  = PartnerCatalogueBuilder.aPartnerCatalogue()
                .withPacId(100L)
                .withPacCategory(2)
                .withPacCode("TEST123")
                .withPacCost(200.0)
                .withPacDescription("Tester")
                .withPacName("Olive")
                .withPacStatus(1)
                .withPacPartnerNo(1L)
                .withPacStock(10.0)
                .build();


        partnerCatalogues.add(partnerCatalogueB);

        return partnerCatalogues;



    }
}
