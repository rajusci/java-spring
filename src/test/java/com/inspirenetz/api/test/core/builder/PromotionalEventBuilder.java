package com.inspirenetz.api.test.core.builder;

import com.inspirenetz.api.core.domain.PromotionalEvent;

import java.sql.Date;

/**
 * Created by saneesh-ci on 30/9/14.
 */
public class PromotionalEventBuilder {
    private Long preMerchantNo;
    private Long preId;
    private String preEventCode;
    private String preEventName;
    private String preDescription;
    private Long preLocation;
    private Date preStartDate;
    private Date preEndDate;

    private PromotionalEventBuilder() {
    }

    public static PromotionalEventBuilder aPromotionalEvent() {
        return new PromotionalEventBuilder();
    }

    public PromotionalEventBuilder withPreMerchantNo(Long preMerchantNo) {
        this.preMerchantNo = preMerchantNo;
        return this;
    }

    public PromotionalEventBuilder withPreId(Long preId) {
        this.preId = preId;
        return this;
    }

    public PromotionalEventBuilder withPreEventCode(String preEventCode) {
        this.preEventCode = preEventCode;
        return this;
    }

    public PromotionalEventBuilder withPreEventName(String preEventName) {
        this.preEventName = preEventName;
        return this;
    }

    public PromotionalEventBuilder withPreDescription(String preDescription) {
        this.preDescription = preDescription;
        return this;
    }

    public PromotionalEventBuilder withPreLocation(Long preLocation) {
        this.preLocation = preLocation;
        return this;
    }

    public PromotionalEventBuilder withPreStartDate(Date preStartDate) {
        this.preStartDate = preStartDate;
        return this;
    }

    public PromotionalEventBuilder withPreEndDate(Date preEndDate) {
        this.preEndDate = preEndDate;
        return this;
    }

    public PromotionalEvent build() {
        PromotionalEvent promotionalEvent = new PromotionalEvent();
        promotionalEvent.setPreMerchantNo(preMerchantNo);
        promotionalEvent.setPreId(preId);
        promotionalEvent.setPreEventCode(preEventCode);
        promotionalEvent.setPreEventName(preEventName);
        promotionalEvent.setPreDescription(preDescription);
        promotionalEvent.setPreLocation(preLocation);
        promotionalEvent.setPreStartDate(preStartDate);
        promotionalEvent.setPreEndDate(preEndDate);
        return promotionalEvent;
    }
}
