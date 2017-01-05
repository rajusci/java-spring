package com.inspirenetz.api.rest.resource;

import com.inspirenetz.api.core.dictionary.VoucherUpdateStatus;

import java.sql.Date;

/**
 * Created by sandheepgr on 15/4/14.
 */
public class RedemptionVoucherResource extends BaseResource {



    private Long rvrId;
    private Long rvrMerchant;
    private Long rvrCustomerNo;
    private String rvrProductCode;
    private String rvrVoucherCode;
    private Integer rvrStatus = 1;
    private String rvrLoyaltyId;
    private Date createdAt;
    private Long rvrMerchantNo;
    private Date rvrCreateDate;
    private Integer rvrVoucherType;
    private Date rvrExpiryDate;
    private Long rvrClaimedLocation;
    private Integer rvrAssignedStatus = VoucherUpdateStatus.NOT_APPLICABLE;
    private String rvrUniqueBatchId;


    public Long getRvrId() {
        return rvrId;
    }

    public void setRvrId(Long rvrId) {
        this.rvrId = rvrId;
    }

    public Long getRvrMerchant() {
        return rvrMerchant;
    }

    public void setRvrMerchant(Long rvrMerchant) {
        this.rvrMerchant = rvrMerchant;
    }

    public Long getRvrCustomerNo() {
        return rvrCustomerNo;
    }

    public void setRvrCustomerNo(Long rvrCustomerNo) {
        this.rvrCustomerNo = rvrCustomerNo;
    }

    public String getRvrProductCode() {
        return rvrProductCode;
    }

    public void setRvrProductCode(String rvrProductCode) {
        this.rvrProductCode = rvrProductCode;
    }

    public String getRvrVoucherCode() {
        return rvrVoucherCode;
    }

    public void setRvrVoucherCode(String rvrVoucherCode) {
        this.rvrVoucherCode = rvrVoucherCode;
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

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public Long getRvrMerchantNo() {
        return rvrMerchantNo;
    }

    public void setRvrMerchantNo(Long rvrMerchantNo) {
        this.rvrMerchantNo = rvrMerchantNo;
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

    public Date getRvrExpiryDate() {
        return rvrExpiryDate;
    }

    public void setRvrExpiryDate(Date rvrExpiryDate) {
        this.rvrExpiryDate = rvrExpiryDate;
    }

    public Long getRvrClaimedLocation() {
        return rvrClaimedLocation;
    }

    public void setRvrClaimedLocation(Long rvrClaimedLocation) {
        this.rvrClaimedLocation = rvrClaimedLocation;
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


}
