package com.inspirenetz.api.core.domain;

import com.inspirenetz.api.core.dictionary.ImagePrimaryId;
import com.inspirenetz.api.util.DBUtils;
import org.hibernate.validator.constraints.NotEmpty;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.sql.Timestamp;

/**
 * Created by sandheepgr on 30/7/14.
 */
@Entity
@Table(name = "ORDER_CATALOGUES")
public class OrderCatalogue extends AuditedEntity {

    @Id
    @Column(name = "ORC_PRODUCT_NO", nullable = false, insertable = true, updatable = true, length = 10, precision = 0)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long orcProductNo;

    @Basic
    @Column(name = "ORC_MERCHANT_NO", nullable = false, insertable = true, updatable = true, length = 10, precision = 0)
    private Long orcMerchantNo;

    @Basic
    @Column(name = "ORC_PRODUCT_CODE", nullable = false, insertable = true, updatable = true, length = 30, precision = 0)
    @NotNull(message ="{catalogue.catproductcode.notnull}")
    @NotEmpty(message ="{catalogue.catproductcode.notempty}")
    @Size(min =1,max = 20,message ="{catalogue.catproductcode.size}")
    private String orcProductCode = "";

    @Basic
    @Column(name = "ORC_DESCRIPTION", nullable = true, insertable = true, updatable = true, length = 100, precision = 0)
    @NotNull(message ="{catalogue.catdescription.notnull}")
    @NotEmpty(message ="{catalogue.catdescription.notempty}")
    @Size(max = 100,message ="{catalogue.catdescription.size}")
    private String orcDescription = "";

    @Basic
    @Column(name = "ORC_LONG_DESCRIPTION", nullable = true, insertable = true, updatable = true, length = 1000, precision = 0)
    @Size(max = 200,message ="{catalogue.catlongdescription.size}")
    private String orcLongDescription = "";

    @Basic
    @Column(name = "ORC_CATEGORY", nullable = false, insertable = true, updatable = true, length = 10, precision = 0)
    private Integer orcCategory = 0;

    @Basic
    @Column(name = "ORC_SUB_CATEGORY1", nullable = false, insertable = true, updatable = true, length = 10, precision = 0)
    private Integer orcSubCategory1 = 0;

    @Basic
    @Column(name = "ORC_SUB_CATEGORY2", nullable = false, insertable = true, updatable = true, length = 10, precision = 0)
    private Integer orcSubCategory2 = 0;

    @Basic
    @Column(name = "ORC_SUB_CATEGORY3", nullable = false, insertable = true, updatable = true, length = 10, precision = 0)
    private Integer orcSubCategory3 = 0;

    @Basic
    @Column(name = "ORC_SUB_CATEGORY4", nullable = false, insertable = true, updatable = true, length = 10, precision = 0)
    private Integer orcSubCategory4 = 0;

    @Basic
    @Column(name = "ORC_VENDOR", nullable = false, insertable = true, updatable = true, length = 10, precision = 0)
    private Long orcVendor = 0L;

    @Basic
    @Column(name = "ORC_PRODUCT_IMAGE", nullable = false, insertable = true, updatable = true, length = 10, precision = 0)
    private Long orcProductImage =ImagePrimaryId.PRIMARY_CATALOGUE_IMAGE;

    @Basic
    @Column(name = "ORC_AVAILABLE_LOCATION", nullable = false, insertable = true, updatable = true, length = 10, precision = 0)
    private Long orcAvailableLocation = 0L;

    @Basic
    @Column(name = "ORC_SECONDARY_PRODUCT_IMAGE", nullable = false, insertable = true, updatable = true, length = 10, precision = 0)
    private Long orcSecondaryProductImage = ImagePrimaryId.PRIMARY_CATALOGUE_IMAGE;

    @Basic
    @Column(name = "ORC_PRICE", nullable = false, insertable = true, updatable = true, length = 10, precision = 2)
    private Double orcPrice = 0.0;

    @Basic
    @Column(name = "ORC_TAX_PERC", nullable = false, insertable = true, updatable = true, length = 6, precision = 2)
    private Double orcTaxPerc = 0.0;

    @Basic
    @Column(name = "ORC_SERVICE_TAX_PERC", nullable = false, insertable = true, updatable = true, length = 5, precision = 2)
    private Double orcServiceTaxPerc = 0.0;

    @Basic
    @Column(name = "ORC_EXPIRY_TIME", nullable = true, insertable = true, updatable = true, length = 19, precision = 0)
    private Timestamp orcExpiryTime;

    @Basic
    @Column(name = "ORC_STOCK_QUANTITY", nullable = false, insertable = true, updatable = true, length = 10, precision = 2)
    private Double orcStockQuantity = 0.0;

    @Basic
    @Column(name = "ORC_TERMS_AND_CONDITIONS", nullable = true, insertable = true, updatable = true, length = 200, precision = 0)
    @Size(max = 200,message ="{catalogue.cattermsandconditions.size}")
    private String orcTermsAndConditions = "";

    @Basic
    @Column(name = "ORC_DELIVERY_TYPE", nullable = false, insertable = true, updatable = true, length = 10, precision = 0)
    private Integer orcDeliveryType = 0;


    @ManyToOne(fetch = FetchType.EAGER,optional = true)
    @JoinColumn(name = "ORC_PRODUCT_IMAGE",insertable = false,updatable = false)
    private Image imgPrimaryImage;


    @PreUpdate
    @PrePersist
    private void updateFields() {

        // Check if the expiryTime is null
        if ( orcExpiryTime == null ) {

            orcExpiryTime = DBUtils.convertToSqlTimestamp("0000-00-00 00:00:00");

        }
    }

    public Long getOrcProductNo() {
        return orcProductNo;
    }

    public void setOrcProductNo(Long orcProductNo) {
        this.orcProductNo = orcProductNo;
    }

    public Long getOrcMerchantNo() {
        return orcMerchantNo;
    }

    public void setOrcMerchantNo(Long orcMerchantNo) {
        this.orcMerchantNo = orcMerchantNo;
    }

    public String getOrcProductCode() {
        return orcProductCode;
    }

    public void setOrcProductCode(String orcProductCode) {
        this.orcProductCode = orcProductCode;
    }

    public String getOrcDescription() {
        return orcDescription;
    }

    public void setOrcDescription(String orcDescription) {
        this.orcDescription = orcDescription;
    }

    public String getOrcLongDescription() {
        return orcLongDescription;
    }

    public void setOrcLongDescription(String orcLongDescription) {
        this.orcLongDescription = orcLongDescription;
    }

    public Integer getOrcCategory() {
        return orcCategory;
    }

    public void setOrcCategory(Integer orcCategory) {
        this.orcCategory = orcCategory;
    }

    public Integer getOrcSubCategory1() {
        return orcSubCategory1;
    }

    public void setOrcSubCategory1(Integer orcSubCategory1) {
        this.orcSubCategory1 = orcSubCategory1;
    }

    public Integer getOrcSubCategory2() {
        return orcSubCategory2;
    }

    public void setOrcSubCategory2(Integer orcSubCategory2) {
        this.orcSubCategory2 = orcSubCategory2;
    }

    public Integer getOrcSubCategory3() {
        return orcSubCategory3;
    }

    public void setOrcSubCategory3(Integer orcSubCategory3) {
        this.orcSubCategory3 = orcSubCategory3;
    }

    public Integer getOrcSubCategory4() {
        return orcSubCategory4;
    }

    public void setOrcSubCategory4(Integer orcSubCategory4) {
        this.orcSubCategory4 = orcSubCategory4;
    }

    public Long getOrcVendor() {
        return orcVendor;
    }

    public void setOrcVendor(Long orcVendor) {
        this.orcVendor = orcVendor;
    }

    public Long getOrcProductImage() {
        return orcProductImage;
    }

    public void setOrcProductImage(Long orcProductImage) {
        this.orcProductImage = orcProductImage;
    }

    public Long getOrcAvailableLocation() {
        return orcAvailableLocation;
    }

    public void setOrcAvailableLocation(Long orcAvailableLocation) {
        this.orcAvailableLocation = orcAvailableLocation;
    }

    public Long getOrcSecondaryProductImage() {
        return orcSecondaryProductImage;
    }

    public void setOrcSecondaryProductImage(Long orcSecondaryProductImage) {
        this.orcSecondaryProductImage = orcSecondaryProductImage;
    }

    public Double getOrcPrice() {
        return orcPrice;
    }

    public void setOrcPrice(Double orcPrice) {
        this.orcPrice = orcPrice;
    }

    public Double getOrcTaxPerc() {
        return orcTaxPerc;
    }

    public void setOrcTaxPerc(Double orcTaxPerc) {
        this.orcTaxPerc = orcTaxPerc;
    }

    public Double getOrcServiceTaxPerc() {
        return orcServiceTaxPerc;
    }

    public void setOrcServiceTaxPerc(Double orcServiceTaxPerc) {
        this.orcServiceTaxPerc = orcServiceTaxPerc;
    }

    public Timestamp getOrcExpiryTime() {
        return orcExpiryTime;
    }

    public void setOrcExpiryTime(Timestamp orcExpiryTime) {
        this.orcExpiryTime = orcExpiryTime;
    }

    public Double getOrcStockQuantity() {
        return orcStockQuantity;
    }

    public void setOrcStockQuantity(Double orcStockQuantity) {
        this.orcStockQuantity = orcStockQuantity;
    }

    public String getOrcTermsAndConditions() {
        return orcTermsAndConditions;
    }

    public void setOrcTermsAndConditions(String orcTermsAndConditions) {
        this.orcTermsAndConditions = orcTermsAndConditions;
    }

    public Integer getOrcDeliveryType() {
        return orcDeliveryType;
    }

    public void setOrcDeliveryType(Integer orcDeliveryType) {
        this.orcDeliveryType = orcDeliveryType;
    }

    public Image getImgPrimaryImage() {
        return imgPrimaryImage;
    }

    public void setImgPrimaryImage(Image imgPrimaryImage) {
        this.imgPrimaryImage = imgPrimaryImage;
    }

    @Override
    public String toString() {
        return "OrderCatalogue{" +
                "orcProductNo=" + orcProductNo +
                ", orcMerchantNo=" + orcMerchantNo +
                ", orcProductCode='" + orcProductCode + '\'' +
                ", orcDescription='" + orcDescription + '\'' +
                ", orcLongDescription='" + orcLongDescription + '\'' +
                ", orcCategory=" + orcCategory +
                ", orcSubCategory1=" + orcSubCategory1 +
                ", orcSubCategory2=" + orcSubCategory2 +
                ", orcSubCategory3=" + orcSubCategory3 +
                ", orcSubCategory4=" + orcSubCategory4 +
                ", orcVendor=" + orcVendor +
                ", orcProductImage=" + orcProductImage +
                ", orcAvailableLocation=" + orcAvailableLocation +
                ", orcSecondaryProductImage=" + orcSecondaryProductImage +
                ", orcPrice=" + orcPrice +
                ", orcTaxPerc=" + orcTaxPerc +
                ", orcServiceTaxPerc=" + orcServiceTaxPerc +
                ", orcExpiryTime=" + orcExpiryTime +
                ", orcStockQuantity=" + orcStockQuantity +
                ", orcTermsAndConditions='" + orcTermsAndConditions + '\'' +
                ", orcDeliveryType=" + orcDeliveryType +
                '}';
    }
}
