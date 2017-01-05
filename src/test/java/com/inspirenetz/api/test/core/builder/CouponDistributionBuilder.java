package com.inspirenetz.api.test.core.builder;

import com.inspirenetz.api.core.dictionary.CouponDistributionStatus;
import com.inspirenetz.api.core.dictionary.CouponDistributionType;
import com.inspirenetz.api.core.domain.CouponDistribution;

import java.sql.Timestamp;
import java.util.Date;

/**
 * Created by sandheepgr on 18/6/14.
 */
public class CouponDistributionBuilder {
    private Long codId;
    private Long codMerchantNo;
    private String codCouponCode = "";
    private Integer codDistributionType = CouponDistributionType.MEMBERS;
    private String codCustomerSegments = "";
    private String codCustomerIds = "";
    private String codCoalitionIds = "";
    private Integer codStatus = CouponDistributionStatus.ACTIVE;

    private String codBroadCastType;
    private String codSmsContent = "";
    private String codEmailSubject = "";
    private String codEmailContent = "";
    private Date createdAt;
    private String createdBy;
    private Date updatedAt;
    private String updatedBy;

    private CouponDistributionBuilder() {
    }

    public static CouponDistributionBuilder aCouponDistribution() {
        return new CouponDistributionBuilder();
    }

    public CouponDistributionBuilder withCodId(Long codId) {
        this.codId = codId;
        return this;
    }

    public CouponDistributionBuilder withCodMerchantNo(Long codMerchantNo) {
        this.codMerchantNo = codMerchantNo;
        return this;
    }

    public CouponDistributionBuilder withCodBroadCastType(String  codBroadCastType) {

        this.codBroadCastType = codBroadCastType;
        return this;
    }

    public CouponDistributionBuilder withCodCouponCode(String codCouponCode) {
        this.codCouponCode = codCouponCode;
        return this;
    }

    public CouponDistributionBuilder withCodDistributionType(Integer codDistributionType) {
        this.codDistributionType = codDistributionType;
        return this;
    }

    public CouponDistributionBuilder withCodCustomerSegments(String codCustomerSegments) {
        this.codCustomerSegments = codCustomerSegments;
        return this;
    }

    public CouponDistributionBuilder withCodCustomerIds(String codCustomerIds) {
        this.codCustomerIds = codCustomerIds;
        return this;
    }

    public CouponDistributionBuilder withCodCoalitionIds(String codCoalitionIds) {
        this.codCoalitionIds = codCoalitionIds;
        return this;
    }

    public CouponDistributionBuilder withCodStatus(Integer codStatus) {
        this.codStatus = codStatus;
        return this;
    }

    public CouponDistributionBuilder withCodSmsContent(String codSmsContent) {
        this.codSmsContent = codSmsContent;
        return this;
    }

    public CouponDistributionBuilder withCodEmailSubject(String codEmailSubject) {
        this.codEmailSubject = codEmailSubject;
        return this;
    }

    public CouponDistributionBuilder withCodEmailContent(String codEmailContent) {
        this.codEmailContent = codEmailContent;
        return this;
    }


    public CouponDistributionBuilder withCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
        return this;
    }

    public CouponDistributionBuilder withCreatedBy(String createdBy) {
        this.createdBy = createdBy;
        return this;
    }

    public CouponDistributionBuilder withUpdatedAt(Date updatedAt) {
        this.updatedAt = updatedAt;
        return this;
    }

    public CouponDistributionBuilder withUpdatedBy(String updatedBy) {
        this.updatedBy = updatedBy;
        return this;
    }

    public CouponDistribution build() {
        CouponDistribution couponDistribution = new CouponDistribution();
        couponDistribution.setCodId(codId);
        couponDistribution.setCodMerchantNo(codMerchantNo);
        couponDistribution.setCodCouponCode(codCouponCode);
        couponDistribution.setCodDistributionType(codDistributionType);
        couponDistribution.setCodCustomerSegments(codCustomerSegments);
        couponDistribution.setCodCustomerIds(codCustomerIds);
        couponDistribution.setCodCoalitionIds(codCoalitionIds);
        couponDistribution.setCodBroadCastType(codBroadCastType);
        couponDistribution.setCodStatus(codStatus);
        couponDistribution.setCodSmsContent(codSmsContent);
        couponDistribution.setCodEmailSubject(codEmailSubject);
        couponDistribution.setCodEmailContent(codEmailContent);
        couponDistribution.setCreatedAt(createdAt);
        couponDistribution.setCreatedBy(createdBy);
        couponDistribution.setUpdatedAt(updatedAt);
        couponDistribution.setUpdatedBy(updatedBy);
        return couponDistribution;
    }
}
