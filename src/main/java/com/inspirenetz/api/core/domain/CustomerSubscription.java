package com.inspirenetz.api.core.domain;

import javax.persistence.*;

/**
 * Created by sandheepgr on 22/8/14.
 */
@Entity
@Table(name = "CUSTOMER_SUBSCRIPTIONS")
public class CustomerSubscription extends AuditedEntity {

    @Id
    @Column(name = "CSU_ID", nullable = false, insertable = true, updatable = true, length = 10, precision = 0)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long csuId;

    @Basic
    @Column(name = "CSU_CUSTOMER_NO", nullable = false, insertable = true, updatable = true, length = 10, precision = 0)
    private Long csuCustomerNo = 0L;

    @Basic
    @Column(name = "CSU_MERCHANT_NO", nullable = false, insertable = true, updatable = true, length = 10, precision = 0)
    private Long csuMerchantNo =0L;

    @Basic
    @Column(name = "CSU_PRODUCT_CODE", nullable = false, insertable = true, updatable = true, length = 20, precision = 0)
    private String csuProductCode = "";

    @Basic
    @Column(name = "CSU_POINTS", nullable = false, insertable = true, updatable = true, length = 10, precision = 2)
    private Double csuPoints = 0.0;

    @Basic
    @Column(name = "CSU_LOCATION", nullable = false, insertable = true, updatable = true, length = 10, precision = 0)
    private Long csuLocation = 0L;

    @Basic
    @Column(name = "CSU_SERVICE_NO", nullable = false, insertable = true, updatable = true, length = 20, precision = 0)
    private String csuServiceNo = "";

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "CSU_CUSTOMER_NO", insertable = false, updatable =  false)
    private Customer customer;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumns({
            @JoinColumn(name = "CSU_PRODUCT_CODE",referencedColumnName = "PRD_CODE",insertable = false,updatable = false),
            @JoinColumn(name = "CSU_MERCHANT_NO",referencedColumnName = "PRD_MERCHANT_NO",insertable = false,updatable = false)
    })
    private Product product;




    public Long getCsuId() {
        return csuId;
    }

    public void setCsuId(Long csuId) {
        this.csuId = csuId;
    }

    public Long getCsuCustomerNo() {
        return csuCustomerNo;
    }

    public void setCsuCustomerNo(Long csuCustomerNo) {
        this.csuCustomerNo = csuCustomerNo;
    }

    public Long getCsuMerchantNo() {
        return csuMerchantNo;
    }

    public void setCsuMerchantNo(Long csuMerchantNo) {
        this.csuMerchantNo = csuMerchantNo;
    }

    public String getCsuProductCode() {
        return csuProductCode;
    }

    public void setCsuProductCode(String csuProductCode) {
        this.csuProductCode = csuProductCode;
    }

    public Double getCsuPoints() {
        return csuPoints;
    }

    public void setCsuPoints(Double csuPoints) {
        this.csuPoints = csuPoints;
    }

    public Long getCsuLocation() {
        return csuLocation;
    }

    public void setCsuLocation(Long csuLocation) {
        this.csuLocation = csuLocation;
    }

    public String getCsuServiceNo() {
        return csuServiceNo;
    }

    public void setCsuServiceNo(String csuServiceNo) {
        this.csuServiceNo = csuServiceNo;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    @Override
    public String toString() {
        return "CustomerSubscription{" +
                "csuId=" + csuId +
                ", csuCustomerNo=" + csuCustomerNo +
                ", csuMerchantNo=" + csuMerchantNo +
                ", csuProductCode='" + csuProductCode + '\'' +
                ", csuPoints=" + csuPoints +
                ", csuLocation=" + csuLocation +
                ", csuServiceNo='" + csuServiceNo + '\'' +
                '}';
    }
}
