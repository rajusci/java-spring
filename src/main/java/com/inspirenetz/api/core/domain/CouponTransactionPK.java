package com.inspirenetz.api.core.domain;

import javax.persistence.Column;
import javax.persistence.Id;
import java.io.Serializable;

/**
 * Created by sandheepgr on 29/3/14.
 */
public class CouponTransactionPK implements Serializable {

    @Column(name = "CPT_MERCHANT_NO")
    @Id
    private Long cptMerchantNo;

    @Column(name = "CPT_COUPON_CODE")
    @Id
    private String cptCouponCode;

    @Column(name = "CPT_LOYALTY_ID")
    @Id
    private String cptLoyaltyId;

    @Column(name = "CPT_PURCHASE_ID")
    @Id
    private int cptPurchaseId;


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

    public int getCptPurchaseId() {
        return cptPurchaseId;
    }

    public void setCptPurchaseId(int cptPurchaseId) {
        this.cptPurchaseId = cptPurchaseId;
    }
}
