package com.inspirenetz.api.core.domain;

import com.inspirenetz.api.core.dictionary.CouponDistributionStatus;
import com.inspirenetz.api.core.dictionary.CouponDistributionType;

import javax.persistence.*;
import javax.validation.constraints.Size;
import java.sql.Timestamp;
import java.util.Date;

/**
 * Created by sandheepgr on 29/3/14.
 */
@Entity
@Table(name = "COUPON_DISTRIBUTIONS")
public class CouponDistribution extends AuditedEntity {


    @Id
    @Column(name = "COD_ID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long codId;

    @Basic(fetch = FetchType.EAGER)
    @Column(name = "COD_MERCHANT_NO")
    private Long codMerchantNo;

    @Basic(fetch = FetchType.EAGER)
    @Column(name = "COD_COUPON_CODE")
    @Size(max=30,message = "{coupondistribution.codcouponcode.size}")
    private String codCouponCode = "";

    @Basic(fetch = FetchType.EAGER)
    @Column(name = "COD_DISTRIBUTION_TYPE")
    private Integer codDistributionType = CouponDistributionType.MEMBERS;

    @Basic(fetch = FetchType.EAGER)
    @Column(name = "COD_CUSTOMER_SEGMENTS")
    private String codCustomerSegments = "";

    @Basic(fetch = FetchType.EAGER)
    @Column(name = "COD_CUSTOMER_IDS")
    private String codCustomerIds = "";

    @Basic(fetch = FetchType.EAGER)
    @Column(name = "COD_COALITION_IDS")
    private String codCoalitionIds = "";

    @Basic(fetch = FetchType.EAGER)
    @Column(name = "COD_STATUS")
    private Integer codStatus = CouponDistributionStatus.ACTIVE;

    @Basic(fetch = FetchType.EAGER)
    @Column(name = "COD_BROADCAST_TYPE")
    private String  codBroadCastType;

    @Basic
    @Column(name = "COD_SMS_CONTENT", nullable = true)
    private String codSmsContent = "";

    @Basic
    @Column(name = "COD_EMAIL_SUBJECT", nullable = true)
    private String codEmailSubject = "";

    @Basic
    @Column(name = "COD_EMAIL_CONTENT", nullable = true)
    private String codEmailContent = "";


    public Long getCodId() {
        return codId;
    }

    public void setCodId(Long codId) {
        this.codId = codId;
    }

    public Long getCodMerchantNo() {
        return codMerchantNo;
    }

    public void setCodMerchantNo(Long codMerchantNo) {
        this.codMerchantNo = codMerchantNo;
    }

    public String getCodCouponCode() {
        return codCouponCode;
    }

    public void setCodCouponCode(String codCouponCode) {
        this.codCouponCode = codCouponCode;
    }

    public Integer getCodDistributionType() {
        return codDistributionType;
    }

    public void setCodDistributionType(Integer codDistributionType) {
        this.codDistributionType = codDistributionType;
    }

    public Integer getCodStatus() {
        return codStatus;
    }

    public void setCodStatus(Integer codStatus) {
        this.codStatus = codStatus;
    }

    public String getCodCustomerSegments() {
        return codCustomerSegments;
    }

    public void setCodCustomerSegments(String codCustomerSegments) {
        this.codCustomerSegments = codCustomerSegments;
    }

    public String getCodCustomerIds() {
        return codCustomerIds;
    }

    public void setCodCustomerIds(String codCustomerIds) {
        this.codCustomerIds = codCustomerIds;
    }

    public String getCodCoalitionIds() {
        return codCoalitionIds;
    }

    public void setCodCoalitionIds(String codCoalitionIds) {
        this.codCoalitionIds = codCoalitionIds;
    }

    public String getCodBroadCastType() {
        return codBroadCastType;
    }

    public void setCodBroadCastType(String codBroadCastType) {
        this.codBroadCastType = codBroadCastType;
    }

    public String getCodSmsContent() {
        return codSmsContent;
    }

    public void setCodSmsContent(String codSmsContent) {
        this.codSmsContent = codSmsContent;
    }

    public String getCodEmailSubject() {
        return codEmailSubject;
    }

    public void setCodEmailSubject(String codEmailSubject) {
        this.codEmailSubject = codEmailSubject;
    }

    public String getCodEmailContent() {
        return codEmailContent;
    }

    public void setCodEmailContent(String codEmailContent) {
        this.codEmailContent = codEmailContent;
    }

    @Override
    public String toString() {
        return "CouponDistribution{" +
                "codId=" + codId +
                ", codMerchantNo=" + codMerchantNo +
                ", codCouponCode='" + codCouponCode + '\'' +
                ", codDistributionType=" + codDistributionType +
                ", codCustomerSegments='" + codCustomerSegments + '\'' +
                ", codCustomerIds='" + codCustomerIds + '\'' +
                ", codCoalitionIds='" + codCoalitionIds + '\'' +
                ", codStatus=" + codStatus +
                ", codBroadCastType='" + codBroadCastType + '\'' +
                ", codSmsContent='" + codSmsContent + '\'' +
                ", codEmailSubject='" + codEmailSubject + '\'' +
                ", codEmailContent='" + codEmailContent + '\'' +
                '}';
    }
}
