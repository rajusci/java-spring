package com.inspirenetz.api.core.domain;

import javax.persistence.*;
import java.sql.Date;
import java.sql.Timestamp;

/**
 * Created by saneesh-ci on 25/6/15.
 */
@Entity
@Table(name = "CUSTOMER_PROMOTIONAL_EVENT")
public class CustomerPromotionalEvent extends AuditedEntity {


    @Id
    @Column(name = "CPE_ID", nullable = false, insertable = true, updatable = false, length = 10, precision = 0)
    @GeneratedValue  ( strategy = GenerationType.IDENTITY)
    private Long cpeId;

    @Basic
    @Column(name = "CPE_MERCHANT_NO", nullable = false, insertable = true, updatable = false, length = 10, precision = 0)
    private Long cpeMerchantNo = 1L;

    @Basic
    @Column(name = "CPE_LOYALTY_ID", nullable = false, insertable = true, updatable = false)
    private String cpeLoyaltyId ;

    @Basic
    @Column(name = "CPE_EVENT_ID", nullable = false, insertable = true, updatable = false)
    private Long cpeEventId ;

    @Basic
    @Column(name = "CPE_DATE", nullable = true, insertable = true, updatable = false)
    private Date cpeDate ;

    @Basic
    @Column(name = "CPE_TIMESTAMP", nullable = false, insertable = true, updatable = false)
    private Timestamp cpeTimeStamp;

    @Basic
    @Column(name = "CPE_PRODUCT", nullable = true, insertable = true, updatable = false)
    private String cpeProduct;

    @Basic
    @Column(name = "CPE_REFERENCE", nullable = true, insertable = true, updatable = false)
    private String cpeReference;




    @PrePersist
    private void populateInsertFields() {

        // Get the current timestamp
        Timestamp timestamp = new Timestamp(System.currentTimeMillis());

        // Set the registerTimestamp to current time
        cpeTimeStamp = new Timestamp(timestamp.getTime());

    }

    @PreUpdate
    private void populateUpdateFields() {

        // Get the current timestamp
        Timestamp timestamp = new Timestamp(System.currentTimeMillis());

        // Set the registerTimestamp to current time
        cpeTimeStamp = new Timestamp(timestamp.getTime());
    }


    public Long getCpeId() {
        return cpeId;
    }

    public void setCpeId(Long cpeId) {
        this.cpeId = cpeId;
    }

    public Long getCpeMerchantNo() {
        return cpeMerchantNo;
    }

    public void setCpeMerchantNo(Long cpeMerchantNo) {
        this.cpeMerchantNo = cpeMerchantNo;
    }

    public String getCpeLoyaltyId() {
        return cpeLoyaltyId;
    }

    public void setCpeLoyaltyId(String cpeLoyaltyId) {
        this.cpeLoyaltyId = cpeLoyaltyId;
    }

    public Long getCpeEventId() {
        return cpeEventId;
    }

    public void setCpeEventId(Long cpeEventId) {
        this.cpeEventId = cpeEventId;
    }

    public Timestamp getCpeTimeStamp() {
        return cpeTimeStamp;
    }

    public void setCpeTimeStamp(Timestamp cpeTimeStamp) {
        this.cpeTimeStamp = cpeTimeStamp;
    }

    public String getCpeProduct() {
        return cpeProduct;
    }

    public void setCpeProduct(String cpeProduct) {
        this.cpeProduct = cpeProduct;
    }

    public Date getCpeDate() {
        return cpeDate;
    }

    public void setCpeDate(Date cpeDate) {
        this.cpeDate = cpeDate;
    }

    public String getCpeReference() {
        return cpeReference;
    }

    public void setCpeReference(String cpeReference) {
        this.cpeReference = cpeReference;
    }

    @Override
    public String toString() {
        return "CustomerPromotionalEvent{" +
                "cpeId=" + cpeId +
                ", cpeMerchantNo=" + cpeMerchantNo +
                ", cpeLoyaltyId='" + cpeLoyaltyId + '\'' +
                ", cpeEventId='" + cpeEventId + '\'' +
                ", cpeTimeStamp=" + cpeTimeStamp +
                ", cpeProduct=" + cpeProduct +
                '}';
    }
}
