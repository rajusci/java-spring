package com.inspirenetz.api.test.core.builder;

import com.inspirenetz.api.core.dictionary.ImagePrimaryId;
import com.inspirenetz.api.core.domain.OrderCatalogue;

import java.sql.Timestamp;
import java.util.Date;

/**
 * Created by sandheepgr on 30/7/14.
 */
public class OrderCatalogueBuilder {
    private Long orcProductNo;
    private Long orcMerchantNo;
    private String orcProductCode = "";
    private String orcDescription = "";
    private String orcLongDescription = "";
    private Integer orcCategory = 0;
    private Integer orcSubCategory1 = 0;
    private Integer orcSubCategory2 = 0;
    private Integer orcSubCategory3 = 0;
    private Integer orcSubCategory4 = 0;
    private Long orcVendor = 0L;
    private Long orcProductImage =ImagePrimaryId.PRIMARY_CATALOGUE_IMAGE;
    private Long orcAvailableLocation = 0L;
    private Long orcSecondaryProductImage = ImagePrimaryId.PRIMARY_CATALOGUE_IMAGE;
    private Double orcPrice = 0.0;
    private Double orcTaxPerc = 0.0;
    private Double orcServiceTaxPerc = 0.0;
    private Timestamp orcExpiryTime;
    private Double orcStockQuantity = 0.0;
    private String orcTermsAndConditions = "";
    private Integer orcDeliveryType = 0;
    private Date createdAt;
    private String createdBy;
    private Date updatedAt;
    private String updatedBy;

    private OrderCatalogueBuilder() {
    }

    public static OrderCatalogueBuilder anOrderCatalogue() {
        return new OrderCatalogueBuilder();
    }

    public OrderCatalogueBuilder withOrcProductNo(Long orcProductNo) {
        this.orcProductNo = orcProductNo;
        return this;
    }

    public OrderCatalogueBuilder withOrcMerchantNo(Long orcMerchantNo) {
        this.orcMerchantNo = orcMerchantNo;
        return this;
    }

    public OrderCatalogueBuilder withOrcProductCode(String orcProductCode) {
        this.orcProductCode = orcProductCode;
        return this;
    }

    public OrderCatalogueBuilder withOrcDescription(String orcDescription) {
        this.orcDescription = orcDescription;
        return this;
    }

    public OrderCatalogueBuilder withOrcLongDescription(String orcLongDescription) {
        this.orcLongDescription = orcLongDescription;
        return this;
    }

    public OrderCatalogueBuilder withOrcCategory(Integer orcCategory) {
        this.orcCategory = orcCategory;
        return this;
    }

    public OrderCatalogueBuilder withOrcSubCategory1(Integer orcSubCategory1) {
        this.orcSubCategory1 = orcSubCategory1;
        return this;
    }

    public OrderCatalogueBuilder withOrcSubCategory2(Integer orcSubCategory2) {
        this.orcSubCategory2 = orcSubCategory2;
        return this;
    }

    public OrderCatalogueBuilder withOrcSubCategory3(Integer orcSubCategory3) {
        this.orcSubCategory3 = orcSubCategory3;
        return this;
    }

    public OrderCatalogueBuilder withOrcSubCategory4(Integer orcSubCategory4) {
        this.orcSubCategory4 = orcSubCategory4;
        return this;
    }

    public OrderCatalogueBuilder withOrcVendor(Long orcVendor) {
        this.orcVendor = orcVendor;
        return this;
    }

    public OrderCatalogueBuilder withOrcProductImage(Long orcProductImage) {
        this.orcProductImage = orcProductImage;
        return this;
    }

    public OrderCatalogueBuilder withOrcAvailableLocation(Long orcAvailableLocation) {
        this.orcAvailableLocation = orcAvailableLocation;
        return this;
    }

    public OrderCatalogueBuilder withOrcSecondaryProductImage(Long orcSecondaryProductImage) {
        this.orcSecondaryProductImage = orcSecondaryProductImage;
        return this;
    }

    public OrderCatalogueBuilder withOrcPrice(Double orcPrice) {
        this.orcPrice = orcPrice;
        return this;
    }

    public OrderCatalogueBuilder withOrcTaxPerc(Double orcTaxPerc) {
        this.orcTaxPerc = orcTaxPerc;
        return this;
    }

    public OrderCatalogueBuilder withOrcServiceTaxPerc(Double orcServiceTaxPerc) {
        this.orcServiceTaxPerc = orcServiceTaxPerc;
        return this;
    }

    public OrderCatalogueBuilder withOrcExpiryTime(Timestamp orcExpiryTime) {
        this.orcExpiryTime = orcExpiryTime;
        return this;
    }

    public OrderCatalogueBuilder withOrcStockQuantity(Double orcStockQuantity) {
        this.orcStockQuantity = orcStockQuantity;
        return this;
    }

    public OrderCatalogueBuilder withOrcTermsAndConditions(String orcTermsAndConditions) {
        this.orcTermsAndConditions = orcTermsAndConditions;
        return this;
    }

    public OrderCatalogueBuilder withOrcDeliveryType(Integer orcDeliveryType) {
        this.orcDeliveryType = orcDeliveryType;
        return this;
    }

    public OrderCatalogueBuilder withCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
        return this;
    }

    public OrderCatalogueBuilder withCreatedBy(String createdBy) {
        this.createdBy = createdBy;
        return this;
    }

    public OrderCatalogueBuilder withUpdatedAt(Date updatedAt) {
        this.updatedAt = updatedAt;
        return this;
    }

    public OrderCatalogueBuilder withUpdatedBy(String updatedBy) {
        this.updatedBy = updatedBy;
        return this;
    }

    public OrderCatalogue build() {
        OrderCatalogue orderCatalogue = new OrderCatalogue();
        orderCatalogue.setOrcProductNo(orcProductNo);
        orderCatalogue.setOrcMerchantNo(orcMerchantNo);
        orderCatalogue.setOrcProductCode(orcProductCode);
        orderCatalogue.setOrcDescription(orcDescription);
        orderCatalogue.setOrcLongDescription(orcLongDescription);
        orderCatalogue.setOrcCategory(orcCategory);
        orderCatalogue.setOrcSubCategory1(orcSubCategory1);
        orderCatalogue.setOrcSubCategory2(orcSubCategory2);
        orderCatalogue.setOrcSubCategory3(orcSubCategory3);
        orderCatalogue.setOrcSubCategory4(orcSubCategory4);
        orderCatalogue.setOrcVendor(orcVendor);
        orderCatalogue.setOrcProductImage(orcProductImage);
        orderCatalogue.setOrcAvailableLocation(orcAvailableLocation);
        orderCatalogue.setOrcSecondaryProductImage(orcSecondaryProductImage);
        orderCatalogue.setOrcPrice(orcPrice);
        orderCatalogue.setOrcTaxPerc(orcTaxPerc);
        orderCatalogue.setOrcServiceTaxPerc(orcServiceTaxPerc);
        orderCatalogue.setOrcExpiryTime(orcExpiryTime);
        orderCatalogue.setOrcStockQuantity(orcStockQuantity);
        orderCatalogue.setOrcTermsAndConditions(orcTermsAndConditions);
        orderCatalogue.setOrcDeliveryType(orcDeliveryType);
        orderCatalogue.setCreatedAt(createdAt);
        orderCatalogue.setCreatedBy(createdBy);
        orderCatalogue.setUpdatedAt(updatedAt);
        orderCatalogue.setUpdatedBy(updatedBy);
        return orderCatalogue;
    }
}
