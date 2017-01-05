package com.inspirenetz.api.core.domain;

import javax.persistence.*;

/**
 * Created by sandheepgr on 29/3/14.
 */
@Entity
@Table(name = "COUPON_TRANSACTIONS")
public class CouponTransaction extends AuditedEntity {

    @Id
    @Basic(fetch = FetchType.EAGER)
    @Column(name = "CPT_ID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long cptId;

    @Basic(fetch = FetchType.EAGER)
    @Column(name = "CPT_MERCHANT_NO")
    private Long cptMerchantNo;

    @Basic(fetch = FetchType.EAGER)
    @Column(name = "CPT_COUPON_CODE")
    private String cptCouponCode = "";

    @Basic(fetch = FetchType.EAGER)
    @Column(name = "CPT_LOYALTY_ID")
    private String cptLoyaltyId = "0";

    @Basic(fetch = FetchType.EAGER)
    @Column(name = "CPT_PURCHASE_ID")
    private Long cptPurchaseId = 0L;

    @Basic(fetch = FetchType.EAGER)
    @Column(name = "CPT_COUPON_COUNT")
    private int cptCouponCount = 0;


    public Long getCptId() {
        return cptId;
    }

    public void setCptId(Long cptId) {
        this.cptId = cptId;
    }

    public Long getCptMerchantNo() {
        return cptMerchantNo;
    }

    public void setCptMerchantNo(Long cptMerchantNo) {
        this.cptMerchantNo = cptMerchantNo;
    }

    public String getCptCouponCode() {
        return cptCouponCode;
    }

    public void setCptCouponCode(String cptCouponCode) {
        this.cptCouponCode = cptCouponCode;
    }

    public String getCptLoyaltyId() {
        return cptLoyaltyId;
    }

    public void setCptLoyaltyId(String cptLoyaltyId) {
        this.cptLoyaltyId = cptLoyaltyId;
    }

    public Long getCptPurchaseId() {
        return cptPurchaseId;
    }

    public void setCptPurchaseId(Long cptPurchaseId) {
        this.cptPurchaseId = cptPurchaseId;
    }

    public int getCptCouponCount() {
        return cptCouponCount;
    }

    public void setCptCouponCount(int cptCouponCount) {
        this.cptCouponCount = cptCouponCount;
    }


    @Override
    public String toString() {
        return "CouponTransaction{" +
                "cptMerchantNo=" + cptMerchantNo +
                ", cptCouponCode='" + cptCouponCode + '\'' +
                ", cptLoyaltyId='" + cptLoyaltyId + '\'' +
                ", cptPurchaseId=" + cptPurchaseId +
                ", cptCouponCount=" + cptCouponCount +
                '}';
    }
}
