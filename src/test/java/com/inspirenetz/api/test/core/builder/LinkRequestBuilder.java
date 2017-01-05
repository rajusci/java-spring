package com.inspirenetz.api.test.core.builder;

import com.inspirenetz.api.core.dictionary.LinkRequestInitiator;
import com.inspirenetz.api.core.dictionary.LinkRequestType;
import com.inspirenetz.api.core.domain.Customer;
import com.inspirenetz.api.core.domain.LinkRequest;

import java.sql.Date;

/**
 * Created by sandheepgr on 29/8/14.
 */
public class LinkRequestBuilder {
    private Long lrqId;
    private Long lrqMerchantNo;
    private Long lrqSourceCustomer;
    private Long lrqParentCustomer;
    private Integer lrqStatus;
    private Integer lrqType = LinkRequestType.LINK;
    private Integer lrqRequestSource;
    private String lrqRequestSourceRef;
    private Integer lrqInitiator = LinkRequestInitiator.PRIMARY;
    private Date lrqRequestDate;
    private String lrqRemarks;
    private Customer customer;
    private java.util.Date createdAt;
    private String createdBy;
    private java.util.Date updatedAt;
    private String updatedBy;

    private LinkRequestBuilder() {
    }

    public static LinkRequestBuilder aLinkRequest() {
        return new LinkRequestBuilder();
    }

    public LinkRequestBuilder withLrqId(Long lrqId) {
        this.lrqId = lrqId;
        return this;
    }

    public LinkRequestBuilder withLrqMerchantNo(Long lrqMerchantNo) {
        this.lrqMerchantNo = lrqMerchantNo;
        return this;
    }

    public LinkRequestBuilder withLrqSourceCustomer(Long lrqSourceCustomer) {
        this.lrqSourceCustomer = lrqSourceCustomer;
        return this;
    }

    public LinkRequestBuilder withLrqParentCustomer(Long lrqParentCustomer) {
        this.lrqParentCustomer = lrqParentCustomer;
        return this;
    }

    public LinkRequestBuilder withLrqStatus(Integer lrqStatus) {
        this.lrqStatus = lrqStatus;
        return this;
    }

    public LinkRequestBuilder withLrqType(Integer lrqType) {
        this.lrqType = lrqType;
        return this;
    }

    public LinkRequestBuilder withLrqRequestSource(Integer lrqRequestSource) {
        this.lrqRequestSource = lrqRequestSource;
        return this;
    }

    public LinkRequestBuilder withLrqRequestSourceRef(String lrqRequestSourceRef) {
        this.lrqRequestSourceRef = lrqRequestSourceRef;
        return this;
    }

    public LinkRequestBuilder withLrqInitiator(Integer lrqInitiator) {
        this.lrqInitiator = lrqInitiator;
        return this;
    }

    public LinkRequestBuilder withLrqRequestDate(Date lrqRequestDate) {
        this.lrqRequestDate = lrqRequestDate;
        return this;
    }

    public LinkRequestBuilder withLrqRemarks(String lrqRemarks) {
        this.lrqRemarks = lrqRemarks;
        return this;
    }

    public LinkRequestBuilder withCustomer(Customer customer) {
        this.customer = customer;
        return this;
    }

    public LinkRequestBuilder withCreatedAt(java.util.Date createdAt) {
        this.createdAt = createdAt;
        return this;
    }

    public LinkRequestBuilder withCreatedBy(String createdBy) {
        this.createdBy = createdBy;
        return this;
    }

    public LinkRequestBuilder withUpdatedAt(java.util.Date updatedAt) {
        this.updatedAt = updatedAt;
        return this;
    }

    public LinkRequestBuilder withUpdatedBy(String updatedBy) {
        this.updatedBy = updatedBy;
        return this;
    }

    public LinkRequest build() {
        LinkRequest linkRequest = new LinkRequest();
        linkRequest.setLrqId(lrqId);
        linkRequest.setLrqMerchantNo(lrqMerchantNo);
        linkRequest.setLrqSourceCustomer(lrqSourceCustomer);
        linkRequest.setLrqParentCustomer(lrqParentCustomer);
        linkRequest.setLrqStatus(lrqStatus);
        linkRequest.setLrqType(lrqType);
        linkRequest.setLrqRequestSource(lrqRequestSource);
        linkRequest.setLrqRequestSourceRef(lrqRequestSourceRef);
        linkRequest.setLrqInitiator(lrqInitiator);
        linkRequest.setLrqRequestDate(lrqRequestDate);
        linkRequest.setLrqRemarks(lrqRemarks);
        linkRequest.setCustomer(customer);
        linkRequest.setCreatedAt(createdAt);
        linkRequest.setCreatedBy(createdBy);
        linkRequest.setUpdatedAt(updatedAt);
        linkRequest.setUpdatedBy(updatedBy);
        return linkRequest;
    }
}
