package com.inspirenetz.api.rest.resource;

import com.inspirenetz.api.core.dictionary.CouponAcceptType;
import com.inspirenetz.api.core.dictionary.CouponCodeType;
import com.inspirenetz.api.core.dictionary.CouponValueType;
import com.inspirenetz.api.core.dictionary.ImagePrimaryId;
import com.inspirenetz.api.util.DBUtils;

import java.sql.Date;

/**
 * Created by sandheepgr on 18/6/14.
 */
public class CouponResource extends BaseResource {

    private Long cpnCouponId;
    private Long cpnMerchantNo;
    private String cpnCouponName = "";
    private String cpnPromoName = "";
    private String cpnCouponText = "";
    private Integer cpnCouponCodeType = CouponCodeType.FIXED;
    private String cpnCouponCode = "";
    private String cpnCouponCodeFrom = "";
    private String cpnCouponCodeTo ="";
    private Integer cpnCurrency = 356;
    private Integer cpnValueType = CouponValueType.AMOUNT;
    private Double cpnValue = 0.0;
    private Double cpnCapAmount = 0.0;
    private Double cpnBulkOrderQuantity = 0.0;
    private Double cpnBulkOrderFreeUnits = 0.0;
    private Double cpnMinTxnAmount = 0.0;
    private Date cpnExpiryDt;
    private Integer cpnAcceptType = CouponAcceptType.NO_LIMIT;
    private Integer cpnAcceptLimit = 0;
    private Integer cpnAcceptCount = 0;
    private Integer cpnMaxCouponsPerCustomer = 0;
    private Integer cpnMaxCouponsPerTransaction = 0;
    private Long cpnImage = ImagePrimaryId.PRIMARY_COUPON_IMAGE;
    private String cpnImagePath = "";


    public Long getCpnCouponId() {
        return cpnCouponId;
    }

    public void setCpnCouponId(Long cpnCouponId) {
        this.cpnCouponId = cpnCouponId;
    }

    public Long getCpnMerchantNo() {
        return cpnMerchantNo;
    }

    public void setCpnMerchantNo(Long cpnMerchantNo) {
        this.cpnMerchantNo = cpnMerchantNo;
    }

    public String getCpnCouponName() {
        return cpnCouponName;
    }

    public void setCpnCouponName(String cpnCouponName) {
        this.cpnCouponName = cpnCouponName;
    }

    public String getCpnPromoName() {
        return cpnPromoName;
    }

    public void setCpnPromoName(String cpnPromoName) {
        this.cpnPromoName = cpnPromoName;
    }

    public String getCpnCouponText() {
        return cpnCouponText;
    }

    public void setCpnCouponText(String cpnCouponText) {
        this.cpnCouponText = cpnCouponText;
    }

    public Integer getCpnCouponCodeType() {
        return cpnCouponCodeType;
    }

    public void setCpnCouponCodeType(Integer cpnCouponCodeType) {
        this.cpnCouponCodeType = cpnCouponCodeType;
    }

    public String getCpnCouponCode() {
        return cpnCouponCode;
    }

    public void setCpnCouponCode(String cpnCouponCode) {
        this.cpnCouponCode = cpnCouponCode;
    }

    public String getCpnCouponCodeFrom() {
        return cpnCouponCodeFrom;
    }

    public void setCpnCouponCodeFrom(String cpnCouponCodeFrom) {
        this.cpnCouponCodeFrom = cpnCouponCodeFrom;
    }

    public String getCpnCouponCodeTo() {
        return cpnCouponCodeTo;
    }

    public void setCpnCouponCodeTo(String cpnCouponCodeTo) {
        this.cpnCouponCodeTo = cpnCouponCodeTo;
    }

    public Integer getCpnCurrency() {
        return cpnCurrency;
    }

    public void setCpnCurrency(Integer cpnCurrency) {
        this.cpnCurrency = cpnCurrency;
    }

    public Integer getCpnValueType() {
        return cpnValueType;
    }

    public void setCpnValueType(Integer cpnValueType) {
        this.cpnValueType = cpnValueType;
    }

    public Double getCpnValue() {
        return cpnValue;
    }

    public void setCpnValue(Double cpnValue) {
        this.cpnValue = cpnValue;
    }

    public Double getCpnCapAmount() {
        return cpnCapAmount;
    }

    public void setCpnCapAmount(Double cpnCapAmount) {
        this.cpnCapAmount = cpnCapAmount;
    }

    public Double getCpnBulkOrderQuantity() {
        return cpnBulkOrderQuantity;
    }

    public void setCpnBulkOrderQuantity(Double cpnBulkOrderQuantity) {
        this.cpnBulkOrderQuantity = cpnBulkOrderQuantity;
    }

    public Double getCpnBulkOrderFreeUnits() {
        return cpnBulkOrderFreeUnits;
    }

    public void setCpnBulkOrderFreeUnits(Double cpnBulkOrderFreeUnits) {
        this.cpnBulkOrderFreeUnits = cpnBulkOrderFreeUnits;
    }

    public Double getCpnMinTxnAmount() {
        return cpnMinTxnAmount;
    }

    public void setCpnMinTxnAmount(Double cpnMinTxnAmount) {
        this.cpnMinTxnAmount = cpnMinTxnAmount;
    }

    public Date getCpnExpiryDt() {
        return cpnExpiryDt;
    }

    public void setCpnExpiryDt(Date cpnExpiryDt) {
        this.cpnExpiryDt = cpnExpiryDt;
    }

    public Integer getCpnAcceptType() {
        return cpnAcceptType;
    }

    public void setCpnAcceptType(Integer cpnAcceptType) {
        this.cpnAcceptType = cpnAcceptType;
    }

    public Integer getCpnAcceptLimit() {
        return cpnAcceptLimit;
    }

    public void setCpnAcceptLimit(Integer cpnAcceptLimit) {
        this.cpnAcceptLimit = cpnAcceptLimit;
    }

    public Integer getCpnAcceptCount() {
        return cpnAcceptCount;
    }

    public void setCpnAcceptCount(Integer cpnAcceptCount) {
        this.cpnAcceptCount = cpnAcceptCount;
    }

    public Integer getCpnMaxCouponsPerCustomer() {
        return cpnMaxCouponsPerCustomer;
    }

    public void setCpnMaxCouponsPerCustomer(Integer cpnMaxCouponsPerCustomer) {
        this.cpnMaxCouponsPerCustomer = cpnMaxCouponsPerCustomer;
    }

    public Integer getCpnMaxCouponsPerTransaction() {
        return cpnMaxCouponsPerTransaction;
    }

    public void setCpnMaxCouponsPerTransaction(Integer cpnMaxCouponsPerTransaction) {
        this.cpnMaxCouponsPerTransaction = cpnMaxCouponsPerTransaction;
    }

    public Long getCpnImage() {
        return cpnImage;
    }

    public void setCpnImage(Long cpnImage) {
        this.cpnImage = cpnImage;
    }

    public String getCpnImagePath() {
        return cpnImagePath;
    }

    public void setCpnImagePath(String cpnImagePath) {
        this.cpnImagePath = cpnImagePath;
    }


    @Override
    public String toString() {
        return "CouponResource{" +
                "cpnCouponId=" + cpnCouponId +
                ", cpnMerchantNo=" + cpnMerchantNo +
                ", cpnCouponName='" + cpnCouponName + '\'' +
                ", cpnPromoName='" + cpnPromoName + '\'' +
                ", cpnCouponText='" + cpnCouponText + '\'' +
                ", cpnCouponCodeType=" + cpnCouponCodeType +
                ", cpnCouponCode='" + cpnCouponCode + '\'' +
                ", cpnCouponCodeFrom='" + cpnCouponCodeFrom + '\'' +
                ", cpnCouponCodeTo='" + cpnCouponCodeTo + '\'' +
                ", cpnCurrency=" + cpnCurrency +
                ", cpnValueType=" + cpnValueType +
                ", cpnValue=" + cpnValue +
                ", cpnCapAmount=" + cpnCapAmount +
                ", cpnBulkOrderQuantity=" + cpnBulkOrderQuantity +
                ", cpnBulkOrderFreeUnits=" + cpnBulkOrderFreeUnits +
                ", cpnMinTxnAmount=" + cpnMinTxnAmount +
                ", cpnExpiryDt=" + cpnExpiryDt +
                ", cpnAcceptType=" + cpnAcceptType +
                ", cpnAcceptLimit=" + cpnAcceptLimit +
                ", cpnAcceptCount=" + cpnAcceptCount +
                ", cpnMaxCouponsPerCustomer=" + cpnMaxCouponsPerCustomer +
                ", cpnMaxCouponsPerTransaction=" + cpnMaxCouponsPerTransaction +
                ", cpnImage=" + cpnImage +
                ", cpnImagePath='" + cpnImagePath + '\'' +
                '}';
    }
}
