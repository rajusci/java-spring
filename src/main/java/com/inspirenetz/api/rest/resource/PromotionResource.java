package com.inspirenetz.api.rest.resource;

import com.inspirenetz.api.core.dictionary.ImagePrimaryId;
import com.inspirenetz.api.core.dictionary.PromotionExpiryOption;
import com.inspirenetz.api.core.dictionary.PromotionTargetOption;

import java.sql.Date;

/**
 * Created by sandheepgr on 17/6/14.
 */
public class PromotionResource extends BaseResource {

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
    private String prmImagePath = "";
    private String merMerchantName = "";



    public Long getPrmId() {
        return prmId;
    }

    public void setPrmId(Long prmId) {
        this.prmId = prmId;
    }

    public Long getPrmMerchantNo() {
        return prmMerchantNo;
    }

    public void setPrmMerchantNo(Long prmMerchantNo) {
        this.prmMerchantNo = prmMerchantNo;
    }

    public String getPrmName() {
        return prmName;
    }

    public void setPrmName(String prmName) {
        this.prmName = prmName;
    }

    public String getPrmShortDescription() {
        return prmShortDescription;
    }

    public void setPrmShortDescription(String prmShortDescription) {
        this.prmShortDescription = prmShortDescription;
    }

    public String getPrmLongDescription() {
        return prmLongDescription;
    }

    public void setPrmLongDescription(String prmLongDescription) {
        this.prmLongDescription = prmLongDescription;
    }

    public String getPrmMoreDetails() {
        return prmMoreDetails;
    }

    public void setPrmMoreDetails(String prmMoreDetails) {
        this.prmMoreDetails = prmMoreDetails;
    }

    public Long getPrmImage() {
        return prmImage;
    }

    public void setPrmImage(Long prmImage) {
        this.prmImage = prmImage;
    }

    public Integer getPrmTargetedOption() {
        return prmTargetedOption;
    }

    public void setPrmTargetedOption(Integer prmTargetedOption) {
        this.prmTargetedOption = prmTargetedOption;
    }

    public Long getPrmSegmentId() {
        return prmSegmentId;
    }

    public void setPrmSegmentId(Long prmSegmentId) {
        this.prmSegmentId = prmSegmentId;
    }

    public Long getPrmCoalitionId() {
        return prmCoalitionId;
    }

    public void setPrmCoalitionId(Long prmCoalitionId) {
        this.prmCoalitionId = prmCoalitionId;
    }

    public Long getPrmRewardCurrency() {
        return prmRewardCurrency;
    }

    public void setPrmRewardCurrency(Long prmRewardCurrency) {
        this.prmRewardCurrency = prmRewardCurrency;
    }

    public Double getPrmRewardUnits() {
        return prmRewardUnits;
    }

    public void setPrmRewardUnits(Double prmRewardUnits) {
        this.prmRewardUnits = prmRewardUnits;
    }

    public Integer getPrmExpiryOption() {
        return prmExpiryOption;
    }

    public void setPrmExpiryOption(Integer prmExpiryOption) {
        this.prmExpiryOption = prmExpiryOption;
    }

    public Date getPrmExpiryDate() {
        return prmExpiryDate;
    }

    public void setPrmExpiryDate(Date prmExpiryDate) {
        this.prmExpiryDate = prmExpiryDate;
    }

    public Integer getPrmClaimExpiryDays() {
        return prmClaimExpiryDays;
    }

    public void setPrmClaimExpiryDays(Integer prmClaimExpiryDays) {
        this.prmClaimExpiryDays = prmClaimExpiryDays;
    }

    public Integer getPrmMaxResponses() {
        return prmMaxResponses;
    }

    public void setPrmMaxResponses(Integer prmMaxResponses) {
        this.prmMaxResponses = prmMaxResponses;
    }

    public String getPrmUserAction() {
        return prmUserAction;
    }

    public void setPrmUserAction(String prmUserAction) {
        this.prmUserAction = prmUserAction;
    }

    public String getPrmBroadcastOption() {
        return prmBroadcastOption;
    }

    public void setPrmBroadcastOption(String prmBroadcastOption) {
        this.prmBroadcastOption = prmBroadcastOption;
    }

    public Integer getPrmNumPromotes() {
        return prmNumPromotes;
    }

    public void setPrmNumPromotes(Integer prmNumPromotes) {
        this.prmNumPromotes = prmNumPromotes;
    }

    public Integer getPrmNumResponses() {
        return prmNumResponses;
    }

    public void setPrmNumResponses(Integer prmNumResponses) {
        this.prmNumResponses = prmNumResponses;
    }

    public Integer getPrmNumViews() {
        return prmNumViews;
    }

    public void setPrmNumViews(Integer prmNumViews) {
        this.prmNumViews = prmNumViews;
    }

    public String getPrmSmsContent() {
        return prmSmsContent;
    }

    public void setPrmSmsContent(String prmSmsContent) {
        this.prmSmsContent = prmSmsContent;
    }

    public String getPrmEmailSubject() {
        return prmEmailSubject;
    }

    public void setPrmEmailSubject(String prmEmailSubject) {
        this.prmEmailSubject = prmEmailSubject;
    }

    public String getPrmEmailContent() {
        return prmEmailContent;
    }

    public void setPrmEmailContent(String prmEmailContent) {
        this.prmEmailContent = prmEmailContent;
    }

    public String getPrmImagePath() {
        return prmImagePath;
    }

    public void setPrmImagePath(String prmImagePath) {
        this.prmImagePath = prmImagePath;
    }

    public String getMerMerchantName() {
        return merMerchantName;
    }

    public void setMerMerchantName(String merMerchantName) {
        this.merMerchantName = merMerchantName;
    }

    @Override
    public String toString() {
        return "PromotionResource{" +
                "prmId=" + prmId +
                ", prmMerchantNo=" + prmMerchantNo +
                ", prmName='" + prmName + '\'' +
                ", prmShortDescription='" + prmShortDescription + '\'' +
                ", prmLongDescription='" + prmLongDescription + '\'' +
                ", prmMoreDetails='" + prmMoreDetails + '\'' +
                ", prmImage=" + prmImage +
                ", prmTargetedOption=" + prmTargetedOption +
                ", prmSegmentId=" + prmSegmentId +
                ", prmCoalitionId=" + prmCoalitionId +
                ", prmRewardCurrency=" + prmRewardCurrency +
                ", prmRewardUnits=" + prmRewardUnits +
                ", prmExpiryOption=" + prmExpiryOption +
                ", prmExpiryDate=" + prmExpiryDate +
                ", prmClaimExpiryDays=" + prmClaimExpiryDays +
                ", prmMaxResponses=" + prmMaxResponses +
                ", prmUserAction='" + prmUserAction + '\'' +
                ", prmBroadcastOption='" + prmBroadcastOption + '\'' +
                ", prmNumPromotes=" + prmNumPromotes +
                ", prmNumResponses=" + prmNumResponses +
                ", prmNumViews=" + prmNumViews +
                ", prmSmsContent='" + prmSmsContent + '\'' +
                ", prmEmailSubject='" + prmEmailSubject + '\'' +
                ", prmEmailContent='" + prmEmailContent + '\'' +
                ", prmImagePath='" + prmImagePath + '\'' +
                ", merMerchantName='" + merMerchantName + '\'' +
                '}';
    }
}
