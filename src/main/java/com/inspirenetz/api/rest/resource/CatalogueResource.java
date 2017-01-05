package com.inspirenetz.api.rest.resource;

import com.inspirenetz.api.core.dictionary.CatalogueType;
import com.inspirenetz.api.core.dictionary.DeliveryType;
import com.inspirenetz.api.core.dictionary.ImagePrimaryId;
import com.inspirenetz.api.core.dictionary.IndicatorStatus;

import java.sql.Date;

/**
 * Created by sandheepgr on 19/5/14.
 */
public class CatalogueResource extends BaseResource {


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

    private String catProductImagePath = "";

    private Integer catRedemptionType ;

    private Long catRedemptionMerchant;

    private String catApplicableUnits ;

    private String catMessageSpiel ;

    private Long catAvailableStock ;

    private String catProductValues ;

    private String catLocationValues ;

    private Integer catCustomerType ;

    private String catCustomerTier ;

    private Date catStartDate ;

    private Date catEndDate ;

    private Double catProductCost ;

    private Long catAccountNo ;

    private String catExtReference ;

    private String catDtiNumber ;

    private Integer catPasaRewardsEnabled;


    private String catChannelValues="";

    private Integer catRedemptionVoucherExpiry;

    private Date catRedemptionVoucherExpiryDate;

    private Integer catRedemptionVoucherExpiryDateAfter;

    private Long catVoucherSource;

    private String rwdCurrencyName;

    private String merMerchantName = "";

    private String catCategoryName = "";

    private String catVoucherSpiel;

    private Integer catType = CatalogueType.MERCHANT_CATALOGUE;

    private Long catPartnerProduct;

    public Long getCatProductNo() {
        return catProductNo;
    }

    public void setCatProductNo(Long catProductNo) {
        this.catProductNo = catProductNo;
    }

    public Long getCatMerchantNo() {
        return catMerchantNo;
    }

    public void setCatMerchantNo(Long catMerchantNo) {
        this.catMerchantNo = catMerchantNo;
    }

    public String getCatProductCode() {
        return catProductCode;
    }

    public void setCatProductCode(String catProductCode) {
        this.catProductCode = catProductCode;
    }

    public String getCatDescription() {
        return catDescription;
    }

    public void setCatDescription(String catDescription) {
        this.catDescription = catDescription;
    }

    public String getCatLongDescription() {
        return catLongDescription;
    }

    public void setCatLongDescription(String catLongDescription) {
        this.catLongDescription = catLongDescription;
    }

    public Integer getCatCategory() {
        return catCategory;
    }

    public void setCatCategory(Integer catCategory) {
        this.catCategory = catCategory;
    }

    public Long getCatProductImage() {
        return catProductImage;
    }

    public void setCatProductImage(Long catProductImage) {
        this.catProductImage = catProductImage;
    }

    public Long getCatRewardCurrencyId() {
        return catRewardCurrencyId;
    }

    public void setCatRewardCurrencyId(Long catRewardCurrencyId) {
        this.catRewardCurrencyId = catRewardCurrencyId;
    }

    public Double getCatNumPoints() {
        return catNumPoints;
    }

    public void setCatNumPoints(Double catNumPoints) {
        this.catNumPoints = catNumPoints;
    }

    public int getCatPartialCashPaymentRequired() {
        return catPartialCashPaymentRequired;
    }

    public void setCatPartialCashPaymentRequired(int catPartialCashPaymentRequired) {
        this.catPartialCashPaymentRequired = catPartialCashPaymentRequired;
    }

    public Double getCatPartialCash() {
        return catPartialCash;
    }

    public void setCatPartialCash(Double catPartialCash) {
        this.catPartialCash = catPartialCash;
    }

    public String getCatTermsAndConditions() {
        return catTermsAndConditions;
    }

    public void setCatTermsAndConditions(String catTermsAndConditions) {
        this.catTermsAndConditions = catTermsAndConditions;
    }

    public int getCatDeliveryType() {
        return catDeliveryType;
    }

    public void setCatDeliveryType(int catDeliveryType) {
        this.catDeliveryType = catDeliveryType;
    }

    public String getCatProductImagePath() {
        return catProductImagePath;
    }

    public void setCatProductImagePath(String catProductImagePath) {
        this.catProductImagePath = catProductImagePath;
    }

    public Integer getCatRedemptionType() {
        return catRedemptionType;
    }

    public void setCatRedemptionType(Integer catRedemptionType) {
        this.catRedemptionType = catRedemptionType;
    }

    public Long getCatRedemptionMerchant() {
        return catRedemptionMerchant;
    }

    public void setCatRedemptionMerchant(Long catRedemptionMerchant) {
        this.catRedemptionMerchant = catRedemptionMerchant;
    }

    public String getCatApplicableUnits() {
        return catApplicableUnits;
    }

    public void setCatApplicableUnits(String catApplicableUnits) {
        this.catApplicableUnits = catApplicableUnits;
    }

    public String getCatMessageSpiel() {
        return catMessageSpiel;
    }

    public void setCatMessageSpiel(String catMessageSpiel) {
        this.catMessageSpiel = catMessageSpiel;
    }

    public Long getCatAvailableStock() {
        return catAvailableStock;
    }

    public void setCatAvailableStock(Long catAvailableStock) {
        this.catAvailableStock = catAvailableStock;
    }

    public String getCatProductValues() {
        return catProductValues;
    }

    public void setCatProductValues(String catProductValues) {
        this.catProductValues = catProductValues;
    }

    public String getCatLocationValues() {
        return catLocationValues;
    }

    public void setCatLocationValues(String catLocationValues) {
        this.catLocationValues = catLocationValues;
    }

    public Integer getCatCustomerType() {
        return catCustomerType;
    }

    public void setCatCustomerType(Integer catCustomerType) {
        this.catCustomerType = catCustomerType;
    }

    public String getCatCustomerTier() {
        return catCustomerTier;
    }

    public void setCatCustomerTier(String catCustomerTier) {
        this.catCustomerTier = catCustomerTier;
    }

    public Date getCatStartDate() {
        return catStartDate;
    }

    public void setCatStartDate(Date catStartDate) {
        this.catStartDate = catStartDate;
    }

    public Date getCatEndDate() {
        return catEndDate;
    }

    public void setCatEndDate(Date catEndDate) {
        this.catEndDate = catEndDate;
    }

    public Double getCatProductCost() {
        return catProductCost;
    }

    public void setCatProductCost(Double catProductCost) {
        this.catProductCost = catProductCost;
    }

    public Long getCatAccountNo() {
        return catAccountNo;
    }

    public void setCatAccountNo(Long catAccountNo) {
        this.catAccountNo = catAccountNo;
    }

    public String getCatExtReference() {
        return catExtReference;
    }

    public void setCatExtReference(String catExtReference) {
        this.catExtReference = catExtReference;
    }

    public String getCatDtiNumber() {
        return catDtiNumber;
    }

    public void setCatDtiNumber(String catDtiNumber) {
        this.catDtiNumber = catDtiNumber;
    }

    public Integer getCatPasaRewardsEnabled() {
        return catPasaRewardsEnabled;
    }

    public void setCatPasaRewardsEnabled(Integer catPasaRewardsEnabled) {
        this.catPasaRewardsEnabled = catPasaRewardsEnabled;
    }

    public String getCatChannelValues() {
        return catChannelValues;
    }

    public void setCatChannelValues(String catChannelValues) {
        this.catChannelValues = catChannelValues;
    }

    public Integer getCatRedemptionVoucherExpiry() {
        return catRedemptionVoucherExpiry;
    }

    public void setCatRedemptionVoucherExpiry(Integer catRedemptionVoucherExpiry) {
        this.catRedemptionVoucherExpiry = catRedemptionVoucherExpiry;
    }

    public Date getCatRedemptionVoucherExpiryDate() {
        return catRedemptionVoucherExpiryDate;
    }

    public void setCatRedemptionVoucherExpiryDate(Date catRedemptionVoucherExpiryDate) {
        this.catRedemptionVoucherExpiryDate = catRedemptionVoucherExpiryDate;
    }

    public Integer getCatRedemptionVoucherExpiryDateAfter() {
        return catRedemptionVoucherExpiryDateAfter;
    }

    public void setCatRedemptionVoucherExpiryDateAfter(Integer catRedemptionVoucherExpiryDateAfter) {
        this.catRedemptionVoucherExpiryDateAfter = catRedemptionVoucherExpiryDateAfter;
    }

    public Long getCatVoucherSource() {
        return catVoucherSource;
    }

    public void setCatVoucherSource(Long catVoucherSource) {
        this.catVoucherSource = catVoucherSource;
    }

    public String getRwdCurrencyName() {
        return rwdCurrencyName;
    }

    public void setRwdCurrencyName(String rwdCurrencyName) {
        this.rwdCurrencyName = rwdCurrencyName;
    }

    public String getMerMerchantName() {
        return merMerchantName;
    }

    public void setMerMerchantName(String merMerchantName) {
        this.merMerchantName = merMerchantName;
    }

    public String getCatCategoryName() {
        return catCategoryName;
    }

    public void setCatCategoryName(String catCategoryName) {
        this.catCategoryName = catCategoryName;
    }

    public String getCatVoucherSpiel() {
        return catVoucherSpiel;
    }

    public void setCatVoucherSpiel(String catVoucherSpiel) {
        this.catVoucherSpiel = catVoucherSpiel;
    }

    public Integer getCatType() {
        return catType;
    }

    public void setCatType(Integer catType) {
        this.catType = catType;
    }

    public Long getCatPartnerProduct() {
        return catPartnerProduct;
    }

    public void setCatPartnerProduct(Long catPartnerProduct) {
        this.catPartnerProduct = catPartnerProduct;
    }
}
