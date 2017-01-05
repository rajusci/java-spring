package com.inspirenetz.api.test.core.builder;

import com.inspirenetz.api.core.domain.CardNumberBatchInfo;

import java.sql.Time;
import java.sql.Date;

/**
 * Created by ameen on 20/10/15.
 */
public class CardNumberBatchInfoBuilder {
    private Long cnbId;
    private Long cnbMerchantNo;
    private String cnbName;
    private Date cnbDate;
    private Time cnbTime;
    private Integer cnbProcessStatus;
    private Date createdAt;
    private String createdBy;
    private Date updatedAt;
    private String updatedBy;

    private CardNumberBatchInfoBuilder() {
    }

    public static CardNumberBatchInfoBuilder aCardNumberBatchInfo() {
        return new CardNumberBatchInfoBuilder();
    }

    public CardNumberBatchInfoBuilder withCnbId(Long cnbId) {
        this.cnbId = cnbId;
        return this;
    }

    public CardNumberBatchInfoBuilder withCnbMerchantNo(Long cnbMerchantNo) {
        this.cnbMerchantNo = cnbMerchantNo;
        return this;
    }

    public CardNumberBatchInfoBuilder withCnbName(String cnbName) {
        this.cnbName = cnbName;
        return this;
    }

    public CardNumberBatchInfoBuilder withCnbDate(Date cnbDate) {
        this.cnbDate = cnbDate;
        return this;
    }

    public CardNumberBatchInfoBuilder withCnbTime(Time cnbTime) {
        this.cnbTime = cnbTime;
        return this;
    }

    public CardNumberBatchInfoBuilder withCnbProcessStatus(Integer cnbProcessStatus) {
        this.cnbProcessStatus = cnbProcessStatus;
        return this;
    }

    public CardNumberBatchInfoBuilder withCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
        return this;
    }

    public CardNumberBatchInfoBuilder withCreatedBy(String createdBy) {
        this.createdBy = createdBy;
        return this;
    }

    public CardNumberBatchInfoBuilder withUpdatedAt(Date updatedAt) {
        this.updatedAt = updatedAt;
        return this;
    }

    public CardNumberBatchInfoBuilder withUpdatedBy(String updatedBy) {
        this.updatedBy = updatedBy;
        return this;
    }

    public CardNumberBatchInfoBuilder but() {
        return aCardNumberBatchInfo().withCnbId(cnbId).withCnbMerchantNo(cnbMerchantNo).withCnbName(cnbName).withCnbDate(cnbDate).withCnbTime(cnbTime).withCnbProcessStatus(cnbProcessStatus).withCreatedAt(createdAt).withCreatedBy(createdBy).withUpdatedAt(updatedAt).withUpdatedBy(updatedBy);
    }

    public CardNumberBatchInfo build() {
        CardNumberBatchInfo cardNumberBatchInfo = new CardNumberBatchInfo();
        cardNumberBatchInfo.setCnbId(cnbId);
        cardNumberBatchInfo.setCnbMerchantNo(cnbMerchantNo);
        cardNumberBatchInfo.setCnbName(cnbName);
        cardNumberBatchInfo.setCnbDate(cnbDate);
        cardNumberBatchInfo.setCnbTime(cnbTime);
        cardNumberBatchInfo.setCnbProcessStatus(cnbProcessStatus);
        cardNumberBatchInfo.setCreatedAt(createdAt);
        cardNumberBatchInfo.setCreatedBy(createdBy);
        cardNumberBatchInfo.setUpdatedAt(updatedAt);
        cardNumberBatchInfo.setUpdatedBy(updatedBy);
        return cardNumberBatchInfo;
    }
}
