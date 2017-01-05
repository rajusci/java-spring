package com.inspirenetz.api.core.domain;

import com.inspirenetz.api.core.dictionary.LinkRequestInitiator;
import com.inspirenetz.api.core.dictionary.LinkRequestType;
import org.hibernate.validator.constraints.NotEmpty;

import javax.persistence.*;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.sql.Date;

/**
 * Created by saneesh-ci on 24/8/14.
 */
@Entity
@Table(name="LINK_REQUESTS")
public class LinkRequest extends AuditedEntity implements Serializable {

    @Column(name = "LRQ_ID",nullable = false)
    @Basic(fetch = FetchType.EAGER)
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long lrqId;

    @Column(name = "LRQ_MERCHANT_NO",nullable = false)
    @Basic(fetch = FetchType.EAGER)
    private Long lrqMerchantNo;

    @Column(name = "LRQ_SOURCE_CUSTOMER",nullable = false)
    @Basic(fetch = FetchType.EAGER)
    private Long lrqSourceCustomer;

    @Column(name = "LRQ_PARENT_CUSTOMER",nullable = false)
    @Basic(fetch = FetchType.EAGER)
    private Long lrqParentCustomer;

    @Column(name = "LRQ_STATUS",nullable = false)
    @Basic(fetch = FetchType.EAGER)
    private Integer lrqStatus;

    @Column(name = "LRQ_TYPE",nullable = false)
    @Basic(fetch = FetchType.EAGER)
    private Integer lrqType = LinkRequestType.LINK;

    @Column(name = "LRQ_REQUEST_SOURCE",nullable = true)
    private Integer lrqRequestSource;

    @Column(name = "LRQ_REQUEST_SOURCE_REF",nullable = false)
    @Basic(fetch = FetchType.EAGER)
    private String lrqRequestSourceRef;

    @Column(name = "LRQ_INITIATOR",nullable = false)
    @Basic(fetch = FetchType.EAGER)
    private Integer lrqInitiator = LinkRequestInitiator.PRIMARY;

    @Column(name = "LRQ_REQUEST_DATE",nullable = true)
    @Basic(fetch = FetchType.EAGER)
    private Date lrqRequestDate;

    @Column(name = "LRQ_REMARKS",nullable = true)
    @Basic(fetch = FetchType.EAGER)
    private String lrqRemarks;

    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "LRQ_SOURCE_CUSTOMER",insertable = false,updatable = false)
    private Customer customer;

    @Transient
    private boolean isUnregisterRequest = false;


    public Long getLrqId() {
        return lrqId;
    }

    public void setLrqId(Long lrqId) {
        this.lrqId = lrqId;
    }

    public Long getLrqMerchantNo() {
        return lrqMerchantNo;
    }

    public void setLrqMerchantNo(Long lrqMerchantNo) {
        this.lrqMerchantNo = lrqMerchantNo;
    }

    public Integer getLrqInitiator() {
        return lrqInitiator;
    }

    public void setLrqInitiator(Integer lrqInitiator) {
        this.lrqInitiator = lrqInitiator;
    }

    public Long getLrqSourceCustomer() {
        return lrqSourceCustomer;
    }

    public void setLrqSourceCustomer(Long lrqSourceCustomer) {
        this.lrqSourceCustomer = lrqSourceCustomer;
    }

    public Long getLrqParentCustomer() {
        return lrqParentCustomer;
    }

    public void setLrqParentCustomer(Long lrqParentCustomer) {
        this.lrqParentCustomer = lrqParentCustomer;
    }

    public Integer getLrqStatus() {
        return lrqStatus;
    }

    public void setLrqStatus(Integer lrqStatus) {
        this.lrqStatus = lrqStatus;
    }

    public Integer getLrqType() {
        return lrqType;
    }

    public void setLrqType(Integer lrqType) {
        this.lrqType = lrqType;
    }

    public Integer getLrqRequestSource() {
        return lrqRequestSource;
    }

    public void setLrqRequestSource(Integer lrqRequestSource) {
        this.lrqRequestSource = lrqRequestSource;
    }

    public String getLrqRequestSourceRef() {
        return lrqRequestSourceRef;
    }

    public void setLrqRequestSourceRef(String lrqRequestSourceRef) {
        this.lrqRequestSourceRef = lrqRequestSourceRef;
    }

    public Date getLrqRequestDate() {
        return lrqRequestDate;
    }

    public void setLrqRequestDate(Date lrqRequestDate) {
        this.lrqRequestDate = lrqRequestDate;
    }

    public String getLrqRemarks() {
        return lrqRemarks;
    }

    public void setLrqRemarks(String lrqRemarks) {
        this.lrqRemarks = lrqRemarks;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public boolean isUnregisterRequest() {
        return isUnregisterRequest;
    }

    public void setUnregisterRequest(boolean isUnregisterRequest) {
        this.isUnregisterRequest = isUnregisterRequest;
    }

    @Override
    public String toString() {
        return "LinkRequest{" +
                "lrqId=" + lrqId +
                ", lrqSourceCustomer=" + lrqSourceCustomer +
                ", lrqParentCustomer=" + lrqParentCustomer +
                ", lrqStatus=" + lrqStatus +
                ", lrqRequestSource=" + lrqRequestSource +
                ", lrqRequestSourceRef='" + lrqRequestSourceRef + '\'' +
                ", lrqRequestDate=" + lrqRequestDate +
                ", lrqRemarks='" + lrqRemarks + '\'' +
                '}';
    }
}
