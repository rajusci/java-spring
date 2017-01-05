package com.inspirenetz.api.test.core.builder;

import com.inspirenetz.api.core.dictionary.*;
import com.inspirenetz.api.core.domain.Catalogue;
import com.inspirenetz.api.core.domain.Image;

import java.sql.Date;

/**
 * Created by saneesh-ci on 10/11/14.
 */
public class CatalogueBuilder {
    private Long catProductNo;
    private Long catMerchantNo;
    private String catProductCode = "";
    private String catDescription = "";
    private String catLongDescription = "";
    private Integer catCategory = 0;
    private Long catProductImage = ImagePrimaryId.PRIMARY_CATALOGUE_IMAGE;
    private Long catRewardCurrencyId = 0L;
    private Double catNumPoints = 0.0;
    private int catPartialCashPaymentRequired = IndicatorStatus.NO;
    private Double catPartialCash = 0.0;
    private String catTermsAndConditions = "";
    private int catDeliveryType = DeliveryType.INSTORE;
    private Image image;
    private Integer catRedemptionType = CatalogueRedemptionType.VOUCHER_BASED;
    private Long catRedemptionMerchant =0L;
    private String catExternalReference = "0.0";
    private String catMessageSpiel ;
    private Long catAvailableStock =0L;
    private String catLocationValues ;
    private String catProductValues ;
    private Integer catCustomerType = CustomerType.SUBSCRIBER;
    private String catCustomerTier ;
    private Date catStartDate ;
    private Date catEndDate ;
    private Double catProductCost ;
    private Long catAccountNo ;
    private String catExtReference ;
    private String catDtiNumber ;
    private String catChannelValues=RequestChannel.RDM_WEB+"";

    private CatalogueBuilder() {
    }

    public static CatalogueBuilder aCatalogue() {
        return new CatalogueBuilder();
    }

    public CatalogueBuilder withCatProductNo(Long catProductNo) {
        this.catProductNo = catProductNo;
        return this;
    }

    public CatalogueBuilder withCatMerchantNo(Long catMerchantNo) {
        this.catMerchantNo = catMerchantNo;
        return this;
    }

    public CatalogueBuilder withCatProductCode(String catProductCode) {
        this.catProductCode = catProductCode;
        return this;
    }

    public CatalogueBuilder withCatDescription(String catDescription) {
        this.catDescription = catDescription;
        return this;
    }

    public CatalogueBuilder withCatLongDescription(String catLongDescription) {
        this.catLongDescription = catLongDescription;
        return this;
    }

    public CatalogueBuilder withCatCategory(Integer catCategory) {
        this.catCategory = catCategory;
        return this;
    }

    public CatalogueBuilder withCatProductImage(Long catProductImage) {
        this.catProductImage = catProductImage;
        return this;
    }

    public CatalogueBuilder withCatRewardCurrencyId(Long catRewardCurrencyId) {
        this.catRewardCurrencyId = catRewardCurrencyId;
        return this;
    }

    public CatalogueBuilder withCatNumPoints(Double catNumPoints) {
        this.catNumPoints = catNumPoints;
        return this;
    }

    public CatalogueBuilder withCatPartialCashPaymentRequired(int catPartialCashPaymentRequired) {
        this.catPartialCashPaymentRequired = catPartialCashPaymentRequired;
        return this;
    }

    public CatalogueBuilder withCatPartialCash(Double catPartialCash) {
        this.catPartialCash = catPartialCash;
        return this;
    }

    public CatalogueBuilder withCatTermsAndConditions(String catTermsAndConditions) {
        this.catTermsAndConditions = catTermsAndConditions;
        return this;
    }

    public CatalogueBuilder withCatDeliveryType(int catDeliveryType) {
        this.catDeliveryType = catDeliveryType;
        return this;
    }

    public CatalogueBuilder withImage(Image image) {
        this.image = image;
        return this;
    }

    public CatalogueBuilder withCatRedemptionType(Integer catRedemptionType) {
        this.catRedemptionType = catRedemptionType;
        return this;
    }

    public CatalogueBuilder withCatRedemptionMerchant(Long catRedemptionMerchant) {
        this.catRedemptionMerchant = catRedemptionMerchant;
        return this;
    }

    public CatalogueBuilder withCatExternalReference(String catExternalReference) {
        this.catExternalReference = catExternalReference;
        return this;
    }

    public CatalogueBuilder withCatMessageSpiel(String catMessageSpiel) {
        this.catMessageSpiel = catMessageSpiel;
        return this;
    }

    public CatalogueBuilder withCatAvailableStock(Long catAvailableStock) {
        this.catAvailableStock = catAvailableStock;
        return this;
    }

    public CatalogueBuilder withCatLocationValues(String catLocationValues) {
        this.catLocationValues = catLocationValues;
        return this;
    }

    public CatalogueBuilder withCatProductValues(String catProductValues) {
        this.catProductValues = catProductValues;
        return this;
    }

    public CatalogueBuilder withCatCustomerType(Integer catCustomerType) {
        this.catCustomerType = catCustomerType;
        return this;
    }

    public CatalogueBuilder withCatCustomerTier(String catCustomerTier) {
        this.catCustomerTier = catCustomerTier;
        return this;
    }

    public CatalogueBuilder withCatStartDate(Date catStartDate) {
        this.catStartDate = catStartDate;
        return this;
    }

    public CatalogueBuilder withCatEndDate(Date catEndDate) {
        this.catEndDate = catEndDate;
        return this;
    }

    public CatalogueBuilder withCatProductCost(Double catProductCost) {
        this.catProductCost = catProductCost;
        return this;
    }

    public CatalogueBuilder withCatAccountNo(Long catAccountNo) {
        this.catAccountNo = catAccountNo;
        return this;
    }

    public CatalogueBuilder withCatExtReference(String catExtReference) {
        this.catExtReference = catExtReference;
        return this;
    }

    public CatalogueBuilder withCatDtiNumber(String catDtiNumber) {
        this.catDtiNumber = catDtiNumber;
        return this;
    }
    public CatalogueBuilder withCatChannelValues(String catChannelValues) {
        this.catChannelValues = catChannelValues;
        return this;
    }

    public CatalogueBuilder but() {
        return aCatalogue().withCatProductNo(catProductNo).withCatMerchantNo(catMerchantNo).withCatProductCode(catProductCode).withCatDescription(catDescription).withCatLongDescription(catLongDescription).withCatCategory(catCategory).withCatProductImage(catProductImage).withCatRewardCurrencyId(catRewardCurrencyId).withCatNumPoints(catNumPoints).withCatPartialCashPaymentRequired(catPartialCashPaymentRequired).withCatPartialCash(catPartialCash).withCatTermsAndConditions(catTermsAndConditions).withCatDeliveryType(catDeliveryType).withImage(image).withCatRedemptionType(catRedemptionType).withCatRedemptionMerchant(catRedemptionMerchant).withCatExternalReference(catExternalReference).withCatMessageSpiel(catMessageSpiel).withCatAvailableStock(catAvailableStock).withCatLocationValues(catLocationValues).withCatProductValues(catProductValues).withCatCustomerType(catCustomerType).withCatCustomerTier(catCustomerTier).withCatStartDate(catStartDate).withCatEndDate(catEndDate).withCatProductCost(catProductCost).withCatAccountNo(catAccountNo).withCatExtReference(catExtReference).withCatDtiNumber(catDtiNumber).withCatChannelValues(catChannelValues);
    }

    public Catalogue build() {
        Catalogue catalogue = new Catalogue();
        catalogue.setCatProductNo(catProductNo);
        catalogue.setCatMerchantNo(catMerchantNo);
        catalogue.setCatProductCode(catProductCode);
        catalogue.setCatDescription(catDescription);
        catalogue.setCatLongDescription(catLongDescription);
        catalogue.setCatCategory(catCategory);
        catalogue.setCatProductImage(catProductImage);
        catalogue.setCatRewardCurrencyId(catRewardCurrencyId);
        catalogue.setCatNumPoints(catNumPoints);
        catalogue.setCatPartialCashPaymentRequired(catPartialCashPaymentRequired);
        catalogue.setCatPartialCash(catPartialCash);
        catalogue.setCatTermsAndConditions(catTermsAndConditions);
        catalogue.setCatDeliveryType(catDeliveryType);
        catalogue.setImage(image);
        catalogue.setCatRedemptionType(catRedemptionType);
        catalogue.setCatRedemptionMerchant(catRedemptionMerchant);
        catalogue.setCatExternalReference(catExternalReference);
        catalogue.setCatMessageSpiel(catMessageSpiel);
        catalogue.setCatAvailableStock(catAvailableStock);
        catalogue.setCatLocationValues(catLocationValues);
        catalogue.setCatProductValues(catProductValues);
        catalogue.setCatCustomerType(catCustomerType);
        catalogue.setCatCustomerTier(catCustomerTier);
        catalogue.setCatStartDate(catStartDate);
        catalogue.setCatEndDate(catEndDate);
        catalogue.setCatProductCost(catProductCost);
        catalogue.setCatAccountNo(catAccountNo);
        catalogue.setCatExtReference(catExtReference);
        catalogue.setCatDtiNumber(catDtiNumber);
        catalogue.setCatChannelValues(catChannelValues);
        return catalogue;
    }
}
