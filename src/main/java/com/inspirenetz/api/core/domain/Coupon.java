package com.inspirenetz.api.core.domain;

import com.inspirenetz.api.core.dictionary.CouponAcceptType;
import com.inspirenetz.api.core.dictionary.CouponCodeType;
import com.inspirenetz.api.core.dictionary.CouponValueType;
import com.inspirenetz.api.core.dictionary.ImagePrimaryId;
import org.hibernate.validator.constraints.NotEmpty;

import javax.persistence.*;
import javax.validation.constraints.Size;
import java.sql.Date;

/**
 * Created by sandheepgr on 29/3/14.
 */
@Entity
@Table(name = "COUPONS")
public class Coupon extends AuditedEntity {

    @Id
    @Column(name = "CPN_COUPON_ID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long cpnCouponId;

    @Basic(fetch = FetchType.EAGER)
    @Column(name = "CPN_MERCHANT_NO")
    private Long cpnMerchantNo;

    @Basic(fetch = FetchType.EAGER)
    @Column(name = "CPN_COUPON_NAME")
    @NotEmpty(message = "{coupon.cpncouponname.notempty}")
    @Size(min=1,max=200,message = "{coupon.cpncouponname.size}")
    private String cpnCouponName = "";

    @Basic(fetch = FetchType.EAGER)
    @Column(name = "CPN_PROMO_NAME")
    @NotEmpty(message = "{coupon.cpnpromoname.notempty}")
    @Size(min=1,max=200,message = "{coupon.cpnpromoname.size}")
    private String cpnPromoName = "";

    @Basic(fetch = FetchType.EAGER)
    @Column(name = "CPN_COUPON_TEXT")
    @Size(min=1,max=500,message = "{coupon.cpncoupontext.size}")
    private String cpnCouponText = "";

    @Basic(fetch = FetchType.EAGER)
    @Column(name = "CPN_COUPON_CODE_TYPE")
    private Integer cpnCouponCodeType = CouponCodeType.FIXED;

    @Basic(fetch = FetchType.EAGER)
    @Column(name = "CPN_COUPON_CODE")
    @Size(max=100,message = "{coupon.cpncouponcode.size}")
    private String cpnCouponCode = "";

    @Basic(fetch = FetchType.EAGER)
    @Column(name = "CPN_COUPON_CODE_FROM")
    @Size(max=100,message = "{coupon.cpncouponcodefrom.size}")
    private String cpnCouponCodeFrom = "";

    @Basic(fetch = FetchType.EAGER)
    @Column(name = "CPN_COUPON_CODE_TO")
    @Size(max=100,message = "{coupon.cpncouponcodeto.size}")
    private String cpnCouponCodeTo ="";

    @Basic(fetch = FetchType.EAGER)
    @Column(name = "CPN_CURRENCY")
    private Integer cpnCurrency = 356;

    @Basic(fetch = FetchType.EAGER)
    @Column(name = "CPN_VALUE_TYPE")
    private Integer cpnValueType = CouponValueType.AMOUNT;

    @Basic(fetch = FetchType.EAGER)
    @Column(name = "CPN_VALUE")
    private Double cpnValue = 0.0;

    @Basic(fetch = FetchType.EAGER)
    @Column(name = "CPN_CAP_AMOUNT")
    private Double cpnCapAmount = 0.0;

    @Basic(fetch = FetchType.EAGER)
    @Column(name = "CPN_BULK_ORDER_QUANTITY")
    private Double cpnBulkOrderQuantity = 0.0;

    @Basic(fetch = FetchType.EAGER)
    @Column(name = "CPN_BULK_ORDER_FREE_UNITS")
    private Double cpnBulkOrderFreeUnits = 0.0;

    @Basic(fetch = FetchType.EAGER)
    @Column(name = "CPN_MIN_TXN_AMOUNT")
    private Double cpnMinTxnAmount = 0.0;

    @Basic(fetch = FetchType.EAGER)
    @Column(name = "CPN_EXPIRY_DT")
    private Date cpnExpiryDt;

    @Basic(fetch = FetchType.EAGER)
    @Column(name = "CPN_ACCEPT_TYPE")
    private Integer cpnAcceptType = CouponAcceptType.NO_LIMIT;

    @Basic(fetch = FetchType.EAGER)
    @Column(name = "CPN_ACCEPT_LIMIT")
    private Integer cpnAcceptLimit = 0;

    @Basic(fetch = FetchType.EAGER)
    @Column(name = "CPN_ACCEPT_COUNT")
    private Integer cpnAcceptCount = 0;

    @Basic(fetch = FetchType.EAGER)
    @Column(name = "CPN_MAX_COUPONS_PER_CUSTOMER")
    private Integer cpnMaxCouponsPerCustomer = 1;

    @Basic(fetch = FetchType.EAGER)
    @Column(name = "CPN_MAX_COUPONS_PER_TRANSACTION")
    private Integer cpnMaxCouponsPerTransaction = 1;

    @Basic(fetch = FetchType.EAGER)
    @Column(name = "CPN_IMAGE")
    private Long cpnImage = ImagePrimaryId.PRIMARY_COUPON_IMAGE;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "CPN_IMAGE",insertable = false,updatable = false)
    private Image image;



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

    public Image getImage() {
        return image;
    }

    public void setImage(Image image) {
        this.image = image;
    }

    @Override
    public String toString() {
        return "Coupon{" +
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
                '}';
    }
}
