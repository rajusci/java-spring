package com.inspirenetz.api.rest.resource;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.sql.Timestamp;

/**
 * Created by sandheepgr on 30/7/14.
 */
public class OrderCatalogueResource extends BaseResource {



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

    private Long orcProductImage =0L;

    private Long orcAvailableLocation = 0L;

    private Long orcSecondaryProductImage = 0L;

    private Double orcPrice = 0.0;

    private Double orcTaxPerc = 0.0;

    private Double orcServiceTaxPerc = 0.0;

    @JsonFormat(shape=JsonFormat.Shape.STRING, pattern="yyyy-MM-dd hh:mm:ss")
    private Timestamp orcExpiryTime;

    private Double orcStockQuantity = 0.0;

    private String orcTermsAndConditions = "";

    private Integer orcDeliveryType = 0;

    private String imgPrimaryPath = "";

    private String imgSecondaryPath = "";



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

    public String getImgPrimaryPath() {
        return imgPrimaryPath;
    }

    public void setImgPrimaryPath(String imgPrimaryPath) {
        this.imgPrimaryPath = imgPrimaryPath;
    }

    public String getImgSecondaryPath() {
        return imgSecondaryPath;
    }

    public void setImgSecondaryPath(String imgSecondaryPath) {
        this.imgSecondaryPath = imgSecondaryPath;
    }

    @Override
    public String toString() {
        return "OrderCatalogueResource{" +
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
