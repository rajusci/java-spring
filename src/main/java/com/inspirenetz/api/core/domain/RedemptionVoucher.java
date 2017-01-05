package com.inspirenetz.api.core.domain;

import com.inspirenetz.api.core.dictionary.RedemptionVoucherStatus;
import com.inspirenetz.api.core.dictionary.VoucherUpdateStatus;

import javax.persistence.*;
import java.sql.Date;

/**
 * Created by saneeshci on 25/09/14.
 */
@Entity
@Table(name="REDEMPTION_VOUCHERS")
public class RedemptionVoucher extends AuditedEntity {


    @Column(name = "RVR_ID",nullable = false)
    @Basic(fetch = FetchType.EAGER)
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long rvrId;

    @Column(name = "RVR_VOUCHER_CODE",nullable = false)
    @Basic(fetch = FetchType.EAGER)
    private String rvrVoucherCode;

    @Column(name = "RVR_CUSTOMER_NO" ,nullable = false)
    @Basic(fetch = FetchType.EAGER)
    private Long rvrCustomerNo;

    @Column(name = "RVR_MERCHANT",nullable = false)
    @Basic(fetch = FetchType.EAGER)
    private Long rvrMerchant;

    @Column(name = "RVR_PRODUCT_CODE",nullable = false)
    @Basic(fetch = FetchType.EAGER)
    private String rvrProductCode;

    @Column(name = "RVR_STATUS",nullable = false)
    @Basic(fetch = FetchType.EAGER)
    private Integer rvrStatus = RedemptionVoucherStatus.NEW;

    @Column(name = "RVR_LOYALTY_ID",nullable = false)
    @Basic(fetch = FetchType.EAGER)
    private String rvrLoyaltyId;

    @Column(name = "RVR_CREATE_DATE",nullable = true)
    @Basic(fetch = FetchType.EAGER)
    private Date rvrCreateDate;

    @Column(name = "RVR_VOUCHER_TYPE",nullable = true)
    @Basic(fetch = FetchType.EAGER)
    private Integer rvrVoucherType;

    @Column(name = "RVR_CLAIMED_LOCATION",nullable = true)
    @Basic(fetch = FetchType.EAGER)
    private Long rvrClaimedLocation;

    @Column(name = "RVR_EXPIRY_DATE",nullable = true)
    @Basic(fetch = FetchType.EAGER)
    private Date rvrExpiryDate;

    @Column(name = "RVR_MERCHANT_NO",nullable = true)
    @Basic(fetch = FetchType.EAGER)
    private Long rvrMerchantNo;

    @Column(name = "RVR_ASSIGNED_STATUS",nullable = false)
    @Basic(fetch = FetchType.EAGER)
    private Integer rvrAssignedStatus = VoucherUpdateStatus.NOT_APPLICABLE;

    @Column(name = "RVR_UNIQUE_BATCH_ID",nullable = false)
    @Basic(fetch = FetchType.EAGER)
    private String rvrUniqueBatchId;



    @Transient
    private String rvrMerchantName;


    public Long getRvrId() {
        return rvrId;
    }

    public void setRvrId(Long rvrId) {
        this.rvrId = rvrId;
    }

    public String getRvrVoucherCode() {
        return rvrVoucherCode;
    }

    public void setRvrVoucherCode(String rvrVoucherCode) {
        this.rvrVoucherCode = rvrVoucherCode;
    }

    public Long getRvrCustomerNo() {
        return rvrCustomerNo;
    }

    public void setRvrCustomerNo(Long rvrCustomerNo) {
        this.rvrCustomerNo = rvrCustomerNo;
    }

    public Long getRvrMerchant() {
        return rvrMerchant;
    }

    public void setRvrMerchant(Long rvrMerchant) {
        this.rvrMerchant = rvrMerchant;
    }

    public String getRvrProductCode() {
        return rvrProductCode;
    }

    public void setRvrProductCode(String rvrProductCode) {
        this.rvrProductCode = rvrProductCode;
    }

    public Integer getRvrStatus() {
        return rvrStatus;
    }

    public void setRvrStatus(Integer rvrStatus) {
        this.rvrStatus = rvrStatus;
    }

    public String getRvrLoyaltyId() {
        return rvrLoyaltyId;
    }

    public void setRvrLoyaltyId(String rvrLoyaltyId) {
        this.rvrLoyaltyId = rvrLoyaltyId;
    }

    public Date getRvrCreateDate() {
        return rvrCreateDate;
    }

    public void setRvrCreateDate(Date rvrCreateDate) {
        this.rvrCreateDate = rvrCreateDate;
    }

    public Integer getRvrVoucherType() {
        return rvrVoucherType;
    }

    public void setRvrVoucherType(Integer rvrVoucherType) {
        this.rvrVoucherType = rvrVoucherType;
    }

    public String getRvrMerchantName() {
        return rvrMerchantName;
    }

    public void setRvrMerchantName(String rvrMerchantName) {
        this.rvrMerchantName = rvrMerchantName;
    }

    public Long getRvrClaimedLocation() {
        return rvrClaimedLocation;
    }

    public void setRvrClaimedLocation(Long rvrClaimedLocation) {
        this.rvrClaimedLocation = rvrClaimedLocation;
    }

    public Date getRvrExpiryDate() {
        return rvrExpiryDate;
    }

    public void setRvrExpiryDate(Date rvrExpiryDate) {
        this.rvrExpiryDate = rvrExpiryDate;
    }

    public Long getRvrMerchantNo() {
        return rvrMerchantNo;
    }

    public void setRvrMerchantNo(Long rvrMerchantNo) {
        this.rvrMerchantNo = rvrMerchantNo;
    }

    public Integer getRvrAssignedStatus() {
        return rvrAssignedStatus;
    }

    public void setRvrAssignedStatus(Integer rvrAssignedStatus) {
        this.rvrAssignedStatus = rvrAssignedStatus;
    }

    public String getRvrUniqueBatchId() {
        return rvrUniqueBatchId;
    }

    public void setRvrUniqueBatchId(String rvrUniqueBatchId) {
        this.rvrUniqueBatchId = rvrUniqueBatchId;
    }

    @Override
    public String toString() {
        return "RedemptionVoucher{" +
                "rvrId=" + rvrId +
                ", rvrVoucherCode='" + rvrVoucherCode + '\'' +
                ", rvrCustomerNo=" + rvrCustomerNo +
                ", rvrMerchant=" + rvrMerchant +
                ", rvrProductCode='" + rvrProductCode + '\'' +
                ", rvrStatus=" + rvrStatus +
                ", rvrLoyaltyId='" + rvrLoyaltyId + '\'' +
                ", rvrCreateDate=" + rvrCreateDate +
                ", rvrVoucherType=" + rvrVoucherType +
                ", rvrClaimedLocation=" + rvrClaimedLocation +
                ", rvrExpiryDate=" + rvrExpiryDate +
                ", rvrMerchantNo=" + rvrMerchantNo +
                ", rvrAssignedStatus=" + rvrAssignedStatus +
                ", rvrUniqueBatchId='" + rvrUniqueBatchId + '\'' +
                ", rvrMerchantName='" + rvrMerchantName + '\'' +
                '}';
    }
}
