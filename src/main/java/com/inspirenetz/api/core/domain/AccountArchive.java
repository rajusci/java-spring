package com.inspirenetz.api.core.domain;

import javax.persistence.*;

/**
 * Created by sandheepgr on 8/9/14.
 */
@Entity
@Table(name = "ACCOUNT_ARCHIVE")
public class AccountArchive {


    @Id
    @Column(name = "AAR_ID", nullable = false, insertable = true, updatable = true, length = 19, precision = 0)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long aarId;

    @Basic
    @Column(name = "AAR_MERCHANT_NO", nullable = false, insertable = true, updatable = true, length = 19, precision = 0)
    private Long aarMerchantNo;

    @Basic
    @Column(name = "AAR_OLD_LOYALTY_ID", nullable = false, insertable = true, updatable = true, length = 45, precision = 0)
    private String aarOldLoyaltyId;

    @Basic
    @Column(name = "AAR_NEW_LOYALTY_ID", nullable = false, insertable = true, updatable = true, length = 45, precision = 0)
    private String aarNewLoyaltyId;




    public Long getAarId() {
        return aarId;
    }

    public void setAarId(Long aarId) {
        this.aarId = aarId;
    }

    public Long getAarMerchantNo() {
        return aarMerchantNo;
    }

    public void setAarMerchantNo(Long aarMerchantNo) {
        this.aarMerchantNo = aarMerchantNo;
    }

    public String getAarOldLoyaltyId() {
        return aarOldLoyaltyId;
    }

    public void setAarOldLoyaltyId(String aarOldLoyaltyId) {
        this.aarOldLoyaltyId = aarOldLoyaltyId;
    }

    public String getAarNewLoyaltyId() {
        return aarNewLoyaltyId;
    }

    public void setAarNewLoyaltyId(String aarNewLoyaltyId) {
        this.aarNewLoyaltyId = aarNewLoyaltyId;
    }



    @Override
    public String toString() {
        return "AccountArchive{" +
                "aarId=" + aarId +
                ", aarMerchantNo=" + aarMerchantNo +
                ", aarOldLoyaltyId='" + aarOldLoyaltyId + '\'' +
                ", aarNewLoyaltyId='" + aarNewLoyaltyId + '\'' +
                '}';
    }
}
