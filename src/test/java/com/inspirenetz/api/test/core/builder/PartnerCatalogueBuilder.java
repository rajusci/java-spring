package com.inspirenetz.api.test.core.builder;

import com.inspirenetz.api.core.dictionary.FulfilmentType;
import com.inspirenetz.api.core.dictionary.ImagePrimaryId;
import com.inspirenetz.api.core.dictionary.PartnerCatalogueStatus;
import com.inspirenetz.api.core.domain.Image;
import com.inspirenetz.api.core.domain.PartnerCatalogue;

/**
 * Created by saneeshci on 7/14/16.
 */
public final class PartnerCatalogueBuilder {
    private Image image;
    private Long pacId;
    private String pacName;
    private String pacCode;
    private String pacDescription;
    private Double pacCost;
    private Double pacStock;
    private Long pacImage =  ImagePrimaryId.PRIMARY_CATALOGUE_IMAGE;
    private Long pacPartnerNo;
    private int pacCategory;
    private int pacFulfilmentType = FulfilmentType.MERCHANT_FULFILLED;
    private int pacStatus = PartnerCatalogueStatus.ACTIVE;

    private PartnerCatalogueBuilder() {
    }

    public static PartnerCatalogueBuilder aPartnerCatalogue() {
        return new PartnerCatalogueBuilder();
    }

    public PartnerCatalogueBuilder withImage(Image image) {
        this.image = image;
        return this;
    }

    public PartnerCatalogueBuilder withPacId(Long pacId) {
        this.pacId = pacId;
        return this;
    }

    public PartnerCatalogueBuilder withPacName(String pacName) {
        this.pacName = pacName;
        return this;
    }

    public PartnerCatalogueBuilder withPacCode(String pacCode) {
        this.pacCode = pacCode;
        return this;
    }

    public PartnerCatalogueBuilder withPacDescription(String pacDescription) {
        this.pacDescription = pacDescription;
        return this;
    }

    public PartnerCatalogueBuilder withPacCost(Double pacCost) {
        this.pacCost = pacCost;
        return this;
    }

    public PartnerCatalogueBuilder withPacStock(Double pacStock) {
        this.pacStock = pacStock;
        return this;
    }

    public PartnerCatalogueBuilder withPacImage(Long pacImage) {
        this.pacImage = pacImage;
        return this;
    }

    public PartnerCatalogueBuilder withPacPartnerNo(Long pacPartnerNo) {
        this.pacPartnerNo = pacPartnerNo;
        return this;
    }

    public PartnerCatalogueBuilder withPacCategory(int pacCategory) {
        this.pacCategory = pacCategory;
        return this;
    }

    public PartnerCatalogueBuilder withPacFulfilmentType(int pacFulfilmentType) {
        this.pacFulfilmentType = pacFulfilmentType;
        return this;
    }

    public PartnerCatalogueBuilder withPacStatus(int pacStatus) {
        this.pacStatus = pacStatus;
        return this;
    }

    public PartnerCatalogue build() {
        PartnerCatalogue partnerCatalogue = new PartnerCatalogue();
        partnerCatalogue.setImage(image);
        partnerCatalogue.setPacId(pacId);
        partnerCatalogue.setPacName(pacName);
        partnerCatalogue.setPacCode(pacCode);
        partnerCatalogue.setPacDescription(pacDescription);
        partnerCatalogue.setPacCost(pacCost);
        partnerCatalogue.setPacStock(pacStock);
        partnerCatalogue.setPacImage(pacImage);
        partnerCatalogue.setPacPartnerNo(pacPartnerNo);
        partnerCatalogue.setPacCategory(pacCategory);
        partnerCatalogue.setPacFulfilmentType(pacFulfilmentType);
        partnerCatalogue.setPacStatus(pacStatus);
        return partnerCatalogue;
    }
}
