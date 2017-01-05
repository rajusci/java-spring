package com.inspirenetz.api.core.domain;

import com.inspirenetz.api.core.dictionary.*;
import org.hibernate.validator.constraints.NotEmpty;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.sql.Date;

/**
 * Created by sandheepgr on 17/2/14.
 */
@Entity
@Table(name="CATALOGUES")
public class Catalogue extends  AuditedEntity {

    @Column(name = "CAT_PRODUCT_NO",nullable = false)
    @Basic(fetch = FetchType.EAGER)
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long catProductNo;

    @Column(name = "CAT_MERCHANT_NO",nullable = false)
    @Basic(fetch = FetchType.EAGER)
    private Long catMerchantNo;

    @Column(name = "CAT_PRODUCT_CODE",nullable = false)
    @Basic(fetch = FetchType.EAGER)
    @NotNull(message ="{catalogue.catproductcode.notnull}")
    @NotEmpty(message ="{catalogue.catproductcode.notempty}")
    @Size(min =1,max = 20,message ="{catalogue.catproductcode.size}")
    private String catProductCode = "";

    @Column(name = "CAT_DESCRIPTION",nullable = false)
    @Basic(fetch = FetchType.EAGER)
    @NotNull(message ="{catalogue.catdescription.notnull}")
    @NotEmpty(message ="{catalogue.catdescription.notempty}")
    @Size(max = 100,message ="{catalogue.catdescription.size}")
    private String catDescription = "";

    @Column(name = "CAT_LONG_DESCRIPTION",nullable = true)
    @Basic(fetch = FetchType.EAGER)
    @Size(max = 200,message ="{catalogue.catlongdescription.size}")
    private String catLongDescription = "";

    @Column(name = "CAT_CATEGORY",nullable = true)
    @Basic(fetch = FetchType.EAGER)
    private Integer catCategory = 0;

    @Column(name = "CAT_PRODUCT_IMAGE",nullable = true)
    @Basic(fetch = FetchType.EAGER)
    private Long catProductImage = ImagePrimaryId.PRIMARY_CATALOGUE_IMAGE;

    @Column(name = "CAT_REWARD_CURRENCY_ID",nullable = true)
    @Basic(fetch = FetchType.EAGER)
    private Long catRewardCurrencyId = 0L;

    @Column(name = "CAT_NUM_POINTS",nullable = false)
    @Basic(fetch = FetchType.EAGER)
    private Double catNumPoints = 0.0;

    @Column(name = "CAT_PARTIAL_CASH_PAYMENT_REQUIRED",nullable = false)
    @Basic(fetch = FetchType.EAGER)
    private int catPartialCashPaymentRequired = IndicatorStatus.NO;

    @Column(name = "CAT_PARTIAL_CASH",nullable = true)
    @Basic(fetch = FetchType.EAGER)
    private Double catPartialCash = 0.0;

    @Column(name = "CAT_TERMS_AND_CONDITIONS",nullable = true)
    @Basic(fetch = FetchType.EAGER)
    @Size(max = 200,message ="{catalogue.cattermsandconditions.size}")
    private String catTermsAndConditions = "";

    @Column(name = "CAT_DELIVERY_TYPE",nullable = false)
    @Basic(fetch = FetchType.EAGER)
    private int catDeliveryType = DeliveryType.INSTORE;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "CAT_PRODUCT_IMAGE",insertable = false,updatable = false)
    private Image image;

    @Column(name = "CAT_REDEMPTION_TYPE",nullable = true)
    @Basic(fetch = FetchType.EAGER)
    private Integer catRedemptionType = CatalogueRedemptionType.PRODUCT;

    @Column(name = "CAT_REDEMPTION_MERCHANT",nullable = true)
    @Basic(fetch = FetchType.EAGER)
    private Long catRedemptionMerchant =0L;

    @Column(name = "CAT_EXTERNAL_REFERENCE",nullable = true)
    @Basic(fetch = FetchType.EAGER)
    private String catExternalReference = "0.0";

    @Column(name = "CAT_MESSAGE_SPIEL",nullable = true)
    @Basic(fetch = FetchType.EAGER)
    private String catMessageSpiel ;

    @Column(name = "CAT_AVAILABLE_STOCK",nullable = true)
    @Basic(fetch = FetchType.EAGER)
    private Long catAvailableStock =0L;

    @Column(name = "CAT_LOCATION_VALUES",nullable = true)
    @Basic(fetch = FetchType.EAGER)
    private String catLocationValues ;

    @Column(name = "CAT_PRODUCT_VALUES",nullable = true)
    @Basic(fetch = FetchType.EAGER)
    private String catProductValues ;

    @Column(name = "CAT_CUSTOMER_TYPE",nullable = true)
    @Basic(fetch = FetchType.EAGER)
    private Integer catCustomerType = CustomerType.SUBSCRIBER;

    @Column(name = "CAT_CUSTOMER_TIER",nullable = true)
    @Basic(fetch = FetchType.EAGER)
    private String catCustomerTier ;

    @Column(name = "CAT_START_DATE",nullable = true)
    @Basic(fetch = FetchType.EAGER)
    private Date catStartDate ;


    @Column(name = "CAT_END_DATE",nullable = true)
    @Basic(fetch = FetchType.EAGER)
    private Date catEndDate ;

    @Column(name = "CAT_PRODUCT_COST",nullable = true)
    @Basic(fetch = FetchType.EAGER)
    private Double catProductCost ;

    @Column(name = "CAT_ACCOUNT_NO",nullable = true)
    @Basic(fetch = FetchType.EAGER)
    private Long catAccountNo ;

    @Column(name = "CAT_EXT_REFERENCE",nullable = true)
    @Basic(fetch = FetchType.EAGER)
    private String catExtReference ;

    @Column(name = "CAT_DTI_NUMBER",nullable = true)
    @Basic(fetch = FetchType.EAGER)
    private String catDtiNumber ;

    @Transient
    Integer rdmChannel = RequestChannel.RDM_CHANNEL_SMS;

    @Column(name = "CAT_PASA_REWARDS_ENABLED",nullable = true)
    @Basic(fetch = FetchType.EAGER)
    private Integer catPasaRewardsEnabled = PasaRewardsEnabledFlag.NO;

    @Column(name ="CAT_CHANNEL_VALUES" ,nullable = true)
    @Basic(fetch = FetchType.EAGER)
    private String catChannelValues="";

    @Column(name = "CAT_REDEMPTION_VOUCHER_EXPIRY",nullable = true)
    @Basic(fetch = FetchType.EAGER)
    private Integer catRedemptionVoucherExpiry;

    @Column(name = "CAT_REDEMPTION_VOUCHER_EXPIRY_DATE",nullable = true)
    @Basic(fetch = FetchType.EAGER)
    private Date catRedemptionVoucherExpiryDate;

    @Column(name = "CAT_REDEMPTION_VOUCHER_EXPIRY_AFTER",nullable = true)
    @Basic(fetch = FetchType.EAGER)
    private Integer catRedemptionVoucherExpiryDateAfter;

    @Column(name = "CAT_VOUCHER_SOURCE",nullable = true)
    @Basic(fetch = FetchType.EAGER)
    private Long catVoucherSource;

    @Column(name = "CAT_VOUCHER_SPIEL",nullable = true)
    @Basic(fetch = FetchType.EAGER)
    private String catVoucherSpiel ;

    @Column(name = "CAT_PARTNER_PRODUCT",nullable = true)
    @Basic(fetch = FetchType.EAGER)
    private Long catPartnerProduct;

    @Column(name = "CAT_TYPE",nullable = true)
    @Basic(fetch = FetchType.EAGER)
    private Integer catType = CatalogueType.MERCHANT_CATALOGUE;


    @Transient
    private String rwdCurrencyName ;

    @Transient
    private String merMerchantName ;

    @Transient
    private String catCategoryName ;


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

    public Image getImage() {
        return image;
    }

    public void setImage(Image image) {
        this.image = image;
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

    public String getCatExternalReference() {
        return catExternalReference;
    }

    public void setCatExternalReference(String catExternalReference) {
        this.catExternalReference = catExternalReference;
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

    public String getCatLocationValues() {
        return catLocationValues;
    }

    public void setCatLocationValues(String catLocationValues) {
        this.catLocationValues = catLocationValues;
    }

    public String getCatProductValues() {
        return catProductValues;
    }

    public void setCatProductValues(String catProductValues) {
        this.catProductValues = catProductValues;
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

    public Integer getRdmChannel() {
        return rdmChannel;
    }

    public void setRdmChannel(Integer rdmChannel) {
        this.rdmChannel = rdmChannel;
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

    public Long getCatPartnerProduct() {
        return catPartnerProduct;
    }

    public void setCatPartnerProduct(Long catPartnerProduct) {
        this.catPartnerProduct = catPartnerProduct;
    }

    public Integer getCatType() {
        return catType;
    }

    public void setCatType(Integer catType) {
        this.catType = catType;
    }

    @Override
    public String toString() {
        return "Catalogue{" +
                "catProductNo=" + catProductNo +
                ", catMerchantNo=" + catMerchantNo +
                ", catProductCode='" + catProductCode + '\'' +
                ", catDescription='" + catDescription + '\'' +
                ", catLongDescription='" + catLongDescription + '\'' +
                ", catCategory=" + catCategory +
                ", catProductImage=" + catProductImage +
                ", catRewardCurrencyId=" + catRewardCurrencyId +
                ", catNumPoints=" + catNumPoints +
                ", catPartialCashPaymentRequired=" + catPartialCashPaymentRequired +
                ", catPartialCash=" + catPartialCash +
                ", catTermsAndConditions='" + catTermsAndConditions + '\'' +
                ", catDeliveryType=" + catDeliveryType +
                ", image=" + image +
                ", catRedemptionType=" + catRedemptionType +
                ", catRedemptionMerchant=" + catRedemptionMerchant +
                ", catExternalReference='" + catExternalReference + '\'' +
                ", catMessageSpiel='" + catMessageSpiel + '\'' +
                ", catAvailableStock=" + catAvailableStock +
                ", catLocationValues='" + catLocationValues + '\'' +
                ", catProductValues='" + catProductValues + '\'' +
                ", catCustomerType=" + catCustomerType +
                ", catCustomerTier='" + catCustomerTier + '\'' +
                ", catStartDate=" + catStartDate +
                ", catEndDate=" + catEndDate +
                ", catProductCost=" + catProductCost +
                ", catAccountNo=" + catAccountNo +
                ", catExtReference='" + catExtReference + '\'' +
                ", catDtiNumber='" + catDtiNumber + '\'' +
                ", rdmChannel=" + rdmChannel +
                ", catPasaRewardsEnabled=" + catPasaRewardsEnabled +
                ", catChannelValues='" + catChannelValues + '\'' +
                ", catRedemptionVoucherExpiry=" + catRedemptionVoucherExpiry +
                ", catRedemptionVoucherExpiryDate=" + catRedemptionVoucherExpiryDate +
                ", catRedemptionVoucherExpiryDateAfter=" + catRedemptionVoucherExpiryDateAfter +
                ", catVoucherSource=" + catVoucherSource +
                ", catVoucherSpiel='" + catVoucherSpiel + '\'' +
                ", rwdCurrencyName='" + rwdCurrencyName + '\'' +
                ", merMerchantName='" + merMerchantName + '\'' +
                ", catCategoryName='" + catCategoryName + '\'' +
                '}';
    }
}
