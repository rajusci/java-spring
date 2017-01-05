package com.inspirenetz.api.test.core.builder;

import com.inspirenetz.api.core.dictionary.IndicatorStatus;
import com.inspirenetz.api.core.domain.CardNumberInfo;

import java.util.Date;

/**
 * Created by ameen on 21/10/15.
 */
public class CardNumberInfoBuilder {
    private Long cniId;
    private Long cniMerchantNo;
    private String cniCardNumber;
    private Long cniCardType;
    private Long cniBatchId;
    private String cniPin;
    private Integer cniCardStatus= IndicatorStatus.NO;
    private Date createdAt;
    private String createdBy;
    private Date updatedAt;
    private String updatedBy;

    private CardNumberInfoBuilder() {
    }

    public static CardNumberInfoBuilder aCardNumberInfo() {
        return new CardNumberInfoBuilder();
    }

    public CardNumberInfoBuilder withCniId(Long cniId) {
        this.cniId = cniId;
        return this;
    }

    public CardNumberInfoBuilder withCniMerchantNo(Long cniMerchantNo) {
        this.cniMerchantNo = cniMerchantNo;
        return this;
    }

    public CardNumberInfoBuilder withCniCardNumber(String cniCardNumber) {
        this.cniCardNumber = cniCardNumber;
        return this;
    }

    public CardNumberInfoBuilder withCniCardType(Long cniCardType) {
        this.cniCardType = cniCardType;
        return this;
    }

    public CardNumberInfoBuilder withCniBatchId(Long cniBatchId) {
        this.cniBatchId = cniBatchId;
        return this;
    }

    public CardNumberInfoBuilder withCniPin(String cniPin) {
        this.cniPin = cniPin;
        return this;
    }

    public CardNumberInfoBuilder withCniCardStatus(Integer cniCardStatus) {
        this.cniCardStatus = cniCardStatus;
        return this;
    }

    public CardNumberInfoBuilder withCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
        return this;
    }

    public CardNumberInfoBuilder withCreatedBy(String createdBy) {
        this.createdBy = createdBy;
        return this;
    }

    public CardNumberInfoBuilder withUpdatedAt(Date updatedAt) {
        this.updatedAt = updatedAt;
        return this;
    }

    public CardNumberInfoBuilder withUpdatedBy(String updatedBy) {
        this.updatedBy = updatedBy;
        return this;
    }

    public CardNumberInfoBuilder but() {
        return aCardNumberInfo().withCniId(cniId).withCniMerchantNo(cniMerchantNo).withCniCardNumber(cniCardNumber).withCniCardType(cniCardType).withCniBatchId(cniBatchId).withCniPin(cniPin).withCniCardStatus(cniCardStatus).withCreatedAt(createdAt).withCreatedBy(createdBy).withUpdatedAt(updatedAt).withUpdatedBy(updatedBy);
    }

    public CardNumberInfo build() {
        CardNumberInfo cardNumberInfo = new CardNumberInfo();
        cardNumberInfo.setCniId(cniId);
        cardNumberInfo.setCniMerchantNo(cniMerchantNo);
        cardNumberInfo.setCniCardNumber(cniCardNumber);
        cardNumberInfo.setCniCardType(cniCardType);
        cardNumberInfo.setCniBatchId(cniBatchId);
        cardNumberInfo.setCniPin(cniPin);
        cardNumberInfo.setCniCardStatus(cniCardStatus);
        cardNumberInfo.setCreatedAt(createdAt);
        cardNumberInfo.setCreatedBy(createdBy);
        cardNumberInfo.setUpdatedAt(updatedAt);
        cardNumberInfo.setUpdatedBy(updatedBy);
        return cardNumberInfo;
    }
}
