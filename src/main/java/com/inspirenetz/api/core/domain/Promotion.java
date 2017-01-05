package com.inspirenetz.api.core.domain;

import com.inspirenetz.api.core.dictionary.ImagePrimaryId;
import com.inspirenetz.api.core.dictionary.PromotionExpiryOption;
import com.inspirenetz.api.core.dictionary.PromotionTargetOption;
import org.hibernate.validator.constraints.NotEmpty;

import javax.persistence.*;
import javax.validation.constraints.Size;
import java.sql.Date;

/**
 * Created by sandheepgr on 17/6/14.
 */
@Entity
@Table(name = "PROMOTIONS")
public class Promotion  extends  AuditedEntity{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "PRM_ID", nullable = false, insertable = true, updatable = true, length = 10, precision = 0)
    private Long prmId;

    @Basic
    @Column(name = "PRM_MERCHANT_NO", nullable = false, insertable = true, updatable = true, length = 10, precision = 0)
    private Long prmMerchantNo;

    @Basic
    @Column(name = "PRM_NAME", nullable = false, insertable = true, updatable = true, length = 50, precision = 0)
    @NotEmpty(message = "{promotion.prmname.notempty}")
    @Size(min=1,max=50,message = "{promotion.prmname.size}")
    private String prmName = "";

    @Basic
    @Column(name = "PRM_SHORT_DESCRIPTION", nullable = false, insertable = true, updatable = true, length = 100, precision = 0)
    @NotEmpty(message = "{promotion.prmshortdescription.notempty}")
    @Size(min=1,max=100,message = "{promotion.prmshortdescription.size}")
    private String prmShortDescription = "";

    @Basic
    @Column(name = "PRM_LONG_DESCRIPTION", nullable = true, insertable = true, updatable = true, length = 500, precision = 0)
    @Size(min=1,max=500,message = "{promotion.prmlongdescription.size}")
    private String prmLongDescription = "";

    @Basic
    @Column(name = "PRM_MORE_DETAILS", nullable = true, insertable = true, updatable = true, length = 255, precision = 0)
    private String prmMoreDetails = "";

    @Basic
    @Column(name = "PRM_IMAGE", nullable = true, insertable = true, updatable = true, length = 10, precision = 0)
    private Long prmImage = ImagePrimaryId.PRIMARY_PROMOTION_IMAGE;

    @Basic
    @Column(name = "PRM_TARGETED_OPTION", nullable = false, insertable = true, updatable = true, length = 10, precision = 0)
    private Integer prmTargetedOption = PromotionTargetOption.ALL_MEMBERS;

    @Basic
    @Column(name = "PRM_SEGMENT_ID", nullable = true, insertable = true, updatable = true, length = 10, precision = 0)
    private Long prmSegmentId = 0L;

    @Basic
    @Column(name = "PRM_COALITION_ID", nullable = true, insertable = true, updatable = true, length = 10, precision = 0)
    private Long prmCoalitionId = 0L;

    @Basic
    @Column(name = "PRM_REWARD_CURRENCY", nullable = false, insertable = true, updatable = true, length = 10, precision = 0)
    private Long prmRewardCurrency = 0L;

    @Basic
    @Column(name = "PRM_REWARD_UNITS", nullable = false, insertable = true, updatable = true, length = 10, precision = 0)
    private Double prmRewardUnits = 0.0;

    @Basic
    @Column(name = "PRM_EXPIRY_OPTION", nullable = false, insertable = true, updatable = true, length = 10, precision = 0)
    private Integer prmExpiryOption = PromotionExpiryOption.EXPIRY_DATE;

    @Basic
    @Column(name = "PRM_EXPIRY_DATE", nullable = true, insertable = true, updatable = true, length = 10, precision = 0)
    private Date prmExpiryDate;

    @Basic
    @Column(name = "PRM_CLAIM_EXPIRY_DAYS", nullable = false, insertable = true, updatable = true, length = 10, precision = 0)
    private Integer prmClaimExpiryDays = 0;

    @Basic
    @Column(name = "PRM_MAX_RESPONSES", nullable = true, insertable = true, updatable = true, length = 10, precision = 0)
    private Integer prmMaxResponses = 0;

    @Basic
    @Column(name = "PRM_USER_ACTION", nullable = false, insertable = true, updatable = true, length = 8, precision = 0)
    private String prmUserAction ="";

    @Basic
    @Column(name = "PRM_BROADCAST_OPTION", nullable = false, insertable = true, updatable = true, length = 10, precision = 0)
    private String prmBroadcastOption = "";

    @Basic
    @Column(name = "PRM_NUM_PROMOTES", nullable = true, insertable = true, updatable = true, length = 10, precision = 0)
    private Integer prmNumPromotes = 0;

    @Basic
    @Column(name = "PRM_NUM_RESPONSES", nullable = true, insertable = true, updatable = true, length = 10, precision = 0)
    private Integer prmNumResponses = 0;

    @Basic
    @Column(name = "PRM_NUM_VIEWS", nullable = false, insertable = true, updatable = true, length = 10, precision = 0)
    private Integer prmNumViews = 0;

    @Basic
    @Column(name = "PRM_SMS_CONTENT", nullable = true)
    private String prmSmsContent = "";

    @Basic
    @Column(name = "PRM_EMAIL_SUBJECT", nullable = true)
    private String prmEmailSubject = "";

    @Basic
    @Column(name = "PRM_EMAIL_CONTENT", nullable = true)
    private String prmEmailContent = "";

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name="PRM_IMAGE",insertable = false,updatable = false)
    private Image image;

    @Transient
    private String merMerchantName ;


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

    public Image getImage() {
        return image;
    }

    public void setImage(Image image) {
        this.image = image;
    }

    public String getMerMerchantName() {
        return merMerchantName;
    }

    public void setMerMerchantName(String merMerchantName) {
        this.merMerchantName = merMerchantName;
    }

    @Override
    public String toString() {
        return "Promotion{" +
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
                ", image=" + image +
                ", merMerchantName='" + merMerchantName + '\'' +
                '}';
    }
}
