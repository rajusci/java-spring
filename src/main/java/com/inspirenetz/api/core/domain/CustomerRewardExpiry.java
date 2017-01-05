package com.inspirenetz.api.core.domain;

import javax.persistence.*;
import java.sql.Date;

/**
 * Created by sandheepgr on 17/2/14.
 */
@Entity
@Table(name = "CUSTOMER_REWARD_EXPIRY")
public class CustomerRewardExpiry extends AuditedEntity  {

    @Column(name = "CRE_ID",nullable = false)
    @Basic(fetch = FetchType.EAGER)
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long creId;

    @Column(name = "CRE_MERCHANT_NO",nullable = false)
    @Basic(fetch = FetchType.EAGER)
    private Long creMerchantNo;

    @Column(name = "CRE_LOYALTY_ID",nullable = false)
    @Basic(fetch = FetchType.EAGER)
    private String creLoyaltyId;

    @Column(name = "CRE_EXPIRY_DT",nullable = false)
    @Basic(fetch = FetchType.EAGER)
    private Date creExpiryDt;

    @Column(name = "CRE_REWARD_CURRENCY_ID",nullable = false)
    @Basic(fetch = FetchType.EAGER)
    private Long creRewardCurrencyId;

    @Column(name = "CRE_REWARD_BALANCE",nullable = false)
    @Basic(fetch = FetchType.EAGER)
    private double creRewardBalance;

    @Transient
    private boolean isAccountDeactivation = false;

    @Column(name = "version")
    @Version
    private Long version;


    public Long getCreId() {
        return creId;
    }

    public void setCreId(Long creId) {
        this.creId = creId;
    }

    public Long getCreMerchantNo() {
        return creMerchantNo;
    }

    public void setCreMerchantNo(Long creMerchantNo) {
        this.creMerchantNo = creMerchantNo;
    }

    public Long getCreRewardCurrencyId() {
        return creRewardCurrencyId;
    }

    public void setCreRewardCurrencyId(Long creRewardCurrencyId) {
        this.creRewardCurrencyId = creRewardCurrencyId;
    }

    public String getCreLoyaltyId() {
        return creLoyaltyId;
    }

    public void setCreLoyaltyId(String creLoyaltyId) {
        this.creLoyaltyId = creLoyaltyId;
    }

    public Date getCreExpiryDt() {
        return creExpiryDt;
    }

    public void setCreExpiryDt(Date creExpiryDt) {
        this.creExpiryDt = creExpiryDt;
    }

    public double getCreRewardBalance() {
        return creRewardBalance;
    }

    public void setCreRewardBalance(double creRewardBalance) {
        this.creRewardBalance = creRewardBalance;
    }

    public boolean isAccountDeactivation() {
        return isAccountDeactivation;
    }

    public void setAccountDeactivation(boolean isAccountDeactivation) {
        this.isAccountDeactivation = isAccountDeactivation;
    }


    public Long getVersion() {
        return version;
    }

    public void setVersion(Long version) {
        this.version = version;
    }

    public String toString() {
        return "CustomerRewardExpiry{" +
                "creId=" + creId +
                ", creMerchantNo=" + creMerchantNo +
                ", creLoyaltyId='" + creLoyaltyId + '\'' +
                ", creExpiryDt=" + creExpiryDt +
                ", creRewardCurrencyId=" + creRewardCurrencyId +
                ", creRewardBalance=" + creRewardBalance +
                ", isAccountDeactivation=" + isAccountDeactivation +
                ", version=" + version +
                '}';
    }
}
