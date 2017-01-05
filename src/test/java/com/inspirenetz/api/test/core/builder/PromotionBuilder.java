package com.inspirenetz.api.test.core.builder;

import com.inspirenetz.api.core.dictionary.ImagePrimaryId;
import com.inspirenetz.api.core.dictionary.PromotionExpiryOption;
import com.inspirenetz.api.core.dictionary.PromotionTargetOption;
import com.inspirenetz.api.core.domain.Promotion;

import java.sql.Date;

/**
 * Created by sandheepgr on 17/6/14.
 */
public class PromotionBuilder {
    private Long prmId;
    private Long prmMerchantNo;
    private String prmName = "";
    private String prmShortDescription = "";
    private String prmLongDescription = "";
    private String prmMoreDetails = "";
    private Long prmImage = ImagePrimaryId.PRIMARY_PROMOTION_IMAGE;
    private Integer prmTargetedOption = PromotionTargetOption.ALL_MEMBERS;
    private Long prmSegmentId = 0L;
    private Long prmCoalitionId = 0L;
    private Long prmRewardCurrency = 0L;
    private Double prmRewardUnits = 0.0;
    private Integer prmExpiryOption = PromotionExpiryOption.EXPIRY_DATE;
    private Date prmExpiryDate;
    private Integer prmClaimExpiryDays = 0;
    private Integer prmMaxResponses = 0;
    private String prmUserAction ="";
    private String prmBroadcastOption = "";
    private Integer prmNumPromotes = 0;
    private Integer prmNumResponses = 0;
    private Integer prmNumViews = 0;

    private String prmSmsContent="";
    private String prmEmailSubject="";
    private String prmEmailContent="";
    private java.util.Date createdAt;
    private String createdBy;
    private java.util.Date updatedAt;
    private String updatedBy;

    private PromotionBuilder() {
    }

    public static PromotionBuilder aPromotion() {
        return new PromotionBuilder();
    }

    public PromotionBuilder withPrmId(Long prmId) {
        this.prmId = prmId;
        return this;
    }

    public PromotionBuilder withPrmMerchantNo(Long prmMerchantNo) {
        this.prmMerchantNo = prmMerchantNo;
        return this;
    }

    public PromotionBuilder withPrmName(String prmName) {
        this.prmName = prmName;
        return this;
    }

    public PromotionBuilder withPrmShortDescription(String prmShortDescription) {
        this.prmShortDescription = prmShortDescription;
        return this;
    }

    public PromotionBuilder withPrmLongDescription(String prmLongDescription) {
        this.prmLongDescription = prmLongDescription;
        return this;
    }

    public PromotionBuilder withPrmMoreDetails(String prmMoreDetails) {
        this.prmMoreDetails = prmMoreDetails;
        return this;
    }

    public PromotionBuilder withPrmImage(Long prmImage) {
        this.prmImage = prmImage;
        return this;
    }

    public PromotionBuilder withPrmTargetedOption(Integer prmTargetedOption) {
        this.prmTargetedOption = prmTargetedOption;
        return this;
    }

    public PromotionBuilder withPrmSegmentId(Long prmSegmentId) {
        this.prmSegmentId = prmSegmentId;
        return this;
    }

    public PromotionBuilder withPrmCoalitionId(Long prmCoalitionId) {
        this.prmCoalitionId = prmCoalitionId;
        return this;
    }

    public PromotionBuilder withPrmRewardCurrency(Long prmRewardCurrency) {
        this.prmRewardCurrency = prmRewardCurrency;
        return this;
    }

    public PromotionBuilder withPrmRewardUnits(Double prmRewardUnits) {
        this.prmRewardUnits = prmRewardUnits;
        return this;
    }

    public PromotionBuilder withPrmExpiryOption(Integer prmExpiryOption) {
        this.prmExpiryOption = prmExpiryOption;
        return this;
    }

    public PromotionBuilder withPrmExpiryDate(Date prmExpiryDate) {
        this.prmExpiryDate = prmExpiryDate;
        return this;
    }

    public PromotionBuilder withPrmClaimExpiryDays(Integer prmClaimExpiryDays) {
        this.prmClaimExpiryDays = prmClaimExpiryDays;
        return this;
    }

    public PromotionBuilder withPrmMaxResponses(Integer prmMaxResponses) {
        this.prmMaxResponses = prmMaxResponses;
        return this;
    }

    public PromotionBuilder withPrmUserAction(String prmUserAction) {
        this.prmUserAction = prmUserAction;
        return this;
    }

    public PromotionBuilder withPrmBroadcastOption(String prmBroadcastOption) {
        this.prmBroadcastOption = prmBroadcastOption;
        return this;
    }

    public PromotionBuilder withPrmNumPromotes(Integer prmNumPromotes) {
        this.prmNumPromotes = prmNumPromotes;
        return this;
    }

    public PromotionBuilder withPrmNumResponses(Integer prmNumResponses) {
        this.prmNumResponses = prmNumResponses;
        return this;
    }

    public PromotionBuilder withPrmNumViews(Integer prmNumViews) {
        this.prmNumViews = prmNumViews;
        return this;
    }

    public PromotionBuilder withPrmSmsContent(String prmSmsContent) {
        this.prmSmsContent = prmSmsContent;
        return this;
    }

    public PromotionBuilder withPrmEmailSubject(String prmEmailSubject) {
        this.prmEmailSubject = prmEmailSubject;
        return this;
    }

    public PromotionBuilder withPrmEmailContent(String prmEmailContent) {
        this.prmEmailContent = prmEmailContent;
        return this;
    }

    public PromotionBuilder withCreatedAt(java.util.Date createdAt) {
        this.createdAt = createdAt;
        return this;
    }

    public PromotionBuilder withCreatedBy(String createdBy) {
        this.createdBy = createdBy;
        return this;
    }

    public PromotionBuilder withUpdatedAt(java.util.Date updatedAt) {
        this.updatedAt = updatedAt;
        return this;
    }

    public PromotionBuilder withUpdatedBy(String updatedBy) {
        this.updatedBy = updatedBy;
        return this;
    }

    public Promotion build() {
        Promotion promotion = new Promotion();
        promotion.setPrmId(prmId);
        promotion.setPrmMerchantNo(prmMerchantNo);
        promotion.setPrmName(prmName);
        promotion.setPrmShortDescription(prmShortDescription);
        promotion.setPrmLongDescription(prmLongDescription);
        promotion.setPrmMoreDetails(prmMoreDetails);
        promotion.setPrmImage(prmImage);
        promotion.setPrmTargetedOption(prmTargetedOption);
        promotion.setPrmSegmentId(prmSegmentId);
        promotion.setPrmCoalitionId(prmCoalitionId);
        promotion.setPrmRewardCurrency(prmRewardCurrency);
        promotion.setPrmRewardUnits(prmRewardUnits);
        promotion.setPrmExpiryOption(prmExpiryOption);
        promotion.setPrmExpiryDate(prmExpiryDate);
        promotion.setPrmClaimExpiryDays(prmClaimExpiryDays);
        promotion.setPrmMaxResponses(prmMaxResponses);
        promotion.setPrmUserAction(prmUserAction);
        promotion.setPrmBroadcastOption(prmBroadcastOption);
        promotion.setPrmNumPromotes(prmNumPromotes);
        promotion.setPrmNumResponses(prmNumResponses);
        promotion.setPrmNumViews(prmNumViews);
        promotion.setPrmSmsContent(prmSmsContent);
        promotion.setPrmEmailSubject(prmEmailSubject);
        promotion.setPrmEmailContent(prmEmailContent);
        promotion.setCreatedAt(createdAt);
        promotion.setCreatedBy(createdBy);
        promotion.setUpdatedAt(updatedAt);
        promotion.setUpdatedBy(updatedBy);
        return promotion;
    }
}
