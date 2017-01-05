package com.inspirenetz.api.core.domain;


import com.inspirenetz.api.config.IntegrationConfig;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Date;


/**
 * Created by abhi on 13/7/16.
 */

@Entity
@Table(name= "MERCHANT_PARTNER_TRANSACTION")
public class MerchantPartnerTransaction extends AuditedEntity {

    @Id
    @Column(name = "MPT_ID")
    @GeneratedValue(strategy =  GenerationType.IDENTITY)
    private Long mptId;

    @Basic(fetch = FetchType.EAGER)
    @Column( name = "MPT_PRODUCT_NUMBER")
    private Long mptProductNo;

    @Basic(fetch = FetchType.EAGER)
    @Column( name = "MPT_MERCHANT_NO")
    private Long mptMerchantNo;

    @Basic(fetch = FetchType.EAGER)
    @Column( name = "MPT_PARTNER_NO")
    private Long mptPartnerNo;

    @Basic(fetch = FetchType.EAGER)
    @Column( name = "MPT_QUANTITY")
    private Integer mptQuantity;

    @Basic(fetch = FetchType.EAGER)
    @Column( name = "MPT_PRICE")
    private Double mptPrice;

    @Basic( fetch = FetchType.EAGER)
    @Column( name = "MPT_TXN_DATE" )
    private Date mptTxnDate;


    public Long getMptId() {
        return mptId;
    }

    public void setMptId(Long mptId) {
        this.mptId = mptId;
    }

    public Long getMptProductNo() {
        return mptProductNo;
    }

    public void setMptProductNo(Long mptProductNo) {
        this.mptProductNo = mptProductNo;
    }

    public Long getMptMerchantNo() {
        return mptMerchantNo;
    }

    public void setMptMerchantNo(Long mptMerchantNo) {
        this.mptMerchantNo = mptMerchantNo;
    }

    public Long getMptPartnerNo() {
        return mptPartnerNo;
    }

    public void setMptPartnerNo(Long mptPartnerNo) {
        this.mptPartnerNo = mptPartnerNo;
    }

    public Integer getMptQuantity() {
        return mptQuantity;
    }

    public void setMptQuantity(Integer mptQuantity) {
        this.mptQuantity = mptQuantity;
    }

    public Double getMptPrice() {
        return mptPrice;
    }

    public void setMptPrice(Double mptPrice) {
        this.mptPrice = mptPrice;
    }

    public Date getMptTxnDate() {
        return mptTxnDate;
    }

    public void setMptTxnDate(Date mptTxnDate) {
        this.mptTxnDate = mptTxnDate;
    }

    @Override
    public String toString() {
        return "MerchantPartnerTransaction{" +
                "mptId=" + mptId +
                ", mptProductNo=" + mptProductNo +
                ", mptMerchantNo=" + mptMerchantNo +
                ", mptPartnerNo=" + mptPartnerNo +
                ", mptQuantity=" + mptQuantity +
                ", mptPrice=" + mptPrice +
                ", mptTxnDate=" + mptTxnDate +
                '}';
    }
}


