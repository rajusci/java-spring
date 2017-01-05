package com.inspirenetz.api.core.domain;

import com.inspirenetz.api.core.dictionary.*;
import com.inspirenetz.api.util.DBUtils;
import org.hibernate.validator.constraints.NotEmpty;
import org.hibernate.validator.constraints.Range;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.sql.Date;
import java.text.ParseException;

/**
 * Created by sandheepgr on 28/4/14.
 */
@Entity
@Table(name = "REWARD_CURRENCY")
public class RewardCurrency extends AuditedEntity {

    @Id
    @Column(name = "RWD_CURRENCY_ID", nullable = false, insertable = true, updatable = true, length = 10, precision = 0)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long rwdCurrencyId;

    @Column(name = "RWD_MERCHANT_NO", nullable = false, insertable = true, updatable = true, length = 10, precision = 0)
    private Long rwdMerchantNo;

    @Column(name = "RWD_REWARD_UNIT_TYPE", nullable = false, insertable = true, updatable = true, length = 10, precision = 0)
    private int rwdRewardUnitType = RewardCurrencyUnitType.POINTS;

    @Basic
    @Column(name = "RWD_CURRENCY_NAME", nullable = false, insertable = true, updatable = true, length = 100, precision = 0)
    @NotEmpty(message="{rewardcurrency.rwdcurrencyname.notempty}")
    @NotNull(message="{rewardcurrency.rwdcurrencyname.notnull}")
    @Size(min=3,max=100 ,message = "{rewardcurrency.rwdcurrencyname.size}")
    private String rwdCurrencyName = "";

    @Basic
    @Column(name = "RWD_DESCRIPTION", nullable = false, insertable = true, updatable = true)
    @Size(max=50 , message ="{rewardcurrency.rwddescription.size}")
    private String rwdDescription = "";

    @Basic
    @Column(name = "RWD_STATUS", nullable = false, insertable = true, updatable = true, length = 10, precision = 0)
    private int rwdStatus = RewardCurrencyStatus.ACTIVE;

    @Basic
    @Column(name = "RWD_EXPIRY_OPTION", nullable = false, insertable = true, updatable = true, length = 10, precision = 0)
    private int rwdExpiryOption = RewardCurrencyExpiryOption.NO_EXPIRY;

    @Basic
    @Column(name = "RWD_EXPIRY_DATE", nullable = true, insertable = true, updatable = true, length = 10, precision = 0)
    private Date rwdExpiryDate;

    @Basic
    @Column(name = "RWD_EXPIRY_DAYS", nullable = true, insertable = true, updatable = true, length = 10, precision = 0)
    private Integer rwdExpiryDays = 0;

    @Basic
    @Column(name = "RWD_EXPIRY_PERIOD", nullable = true, insertable = true, updatable = true, length = 10, precision = 0)
    private Integer rwdExpiryPeriod = RewardCurrencyExpiryPeriod.END_OF_DAY;

    @Basic
    @Column(name = "RWD_CASHBACK_INDICATOR", nullable = false, insertable = true, updatable = true, length = 10, precision = 0)
    private int rwdCashbackIndicator = IndicatorStatus.NO;

    @Basic
    @Column(name = "RWD_CASHBACK_RATIO_DENO", nullable = false, insertable = true, updatable = true, length = 8, precision = 16,scale=2)
    @Range(min = 1L, message = "{rewardcurrency.rwdcashbackratiodeno.range}")
    private Double rwdCashbackRatioDeno = new Double(1);

    @Basic
    @Column(name = "RWD_EXCLUSIVE_INDICATOR", nullable = false, insertable = true, updatable = true, length = 10, precision = 0)
    private int rwdExclusiveIndicator = IndicatorStatus.NO;

    @Basic
    @Column(name = "RWD_REDEMPTION_MIN_POINTS", nullable = false, insertable = true, updatable = true, length = 10, precision = 0)
    private Integer rwdRedemptionMinPoints = 1;

    @Basic
    @Column(name = "RWD_IMAGE", nullable = false, insertable = true, updatable = true, length = 10, precision = 0)
    private Long rwdImage = 1L;

    @Basic
    @Column(name = "RWD_ALLOW_DECIMAL", nullable = true, insertable = true, updatable = true, length = 10, precision = 0)
    private Integer rwdAllowDecimal = IndicatorStatus.NO;

    @Basic
    @Column(name="RWD_ROUNDING_METHOD",nullable = true,insertable = true,updatable = true)
    private Integer rwdRoundingMethod=RoundingMethod.ROUND;

    @PrePersist
    protected void prePersist() throws ParseException{

        // Set the default date if the expiry date is null
        if ( rwdExpiryDate == null ) {

            rwdExpiryDate = DBUtils.getDefaultDate();

        }

    }


    @PreUpdate
    protected void preUpdate() throws ParseException{

        // Set the default date if the expiry date is null
        if ( rwdExpiryDate == null ) {

            rwdExpiryDate = DBUtils.getDefaultDate();

        }

    }

    public Long getRwdCurrencyId() {
        return rwdCurrencyId;
    }

    public void setRwdCurrencyId(Long rwdCurrencyId) {
        this.rwdCurrencyId = rwdCurrencyId;
    }

    public Long getRwdMerchantNo() {
        return rwdMerchantNo;
    }

    public void setRwdMerchantNo(Long rwdMerchantNo) {
        this.rwdMerchantNo = rwdMerchantNo;
    }

    public int getRwdRewardUnitType() {
        return rwdRewardUnitType;
    }

    public void setRwdRewardUnitType(int rwdRewardUnitType) {
        this.rwdRewardUnitType = rwdRewardUnitType;
    }

    public String getRwdCurrencyName() {
        return rwdCurrencyName;
    }

    public void setRwdCurrencyName(String rwdCurrencyName) {
        this.rwdCurrencyName = rwdCurrencyName;
    }

    public String getRwdDescription() {
        return rwdDescription;
    }

    public void setRwdDescription(String rwdDescription) {
        this.rwdDescription = rwdDescription;
    }

    public int getRwdStatus() {
        return rwdStatus;
    }

    public void setRwdStatus(int rwdStatus) {
        this.rwdStatus = rwdStatus;
    }

    public int getRwdExpiryOption() {
        return rwdExpiryOption;
    }

    public void setRwdExpiryOption(int rwdExpiryOption) {
        this.rwdExpiryOption = rwdExpiryOption;
    }

    public Date getRwdExpiryDate() {
        return rwdExpiryDate;
    }

    public void setRwdExpiryDate(Date rwdExpiryDate) {
        this.rwdExpiryDate = rwdExpiryDate;
    }

    public Integer getRwdExpiryDays() {
        return rwdExpiryDays;
    }

    public void setRwdExpiryDays(Integer rwdExpiryDays) {
        this.rwdExpiryDays = rwdExpiryDays;
    }

    public int getRwdCashbackIndicator() {
        return rwdCashbackIndicator;
    }

    public void setRwdCashbackIndicator(int rwdCashbackIndicator) {
        this.rwdCashbackIndicator = rwdCashbackIndicator;
    }

    public Double getRwdCashbackRatioDeno() {
        return rwdCashbackRatioDeno;
    }

    public void setRwdCashbackRatioDeno(Double rwdCashbackRatioDeno) {
        this.rwdCashbackRatioDeno = rwdCashbackRatioDeno;
    }

    public int getRwdExclusiveIndicator() {
        return rwdExclusiveIndicator;
    }

    public void setRwdExclusiveIndicator(int rwdExclusiveIndicator) {
        this.rwdExclusiveIndicator = rwdExclusiveIndicator;
    }

    public Integer getRwdRedemptionMinPoints() {
        return rwdRedemptionMinPoints;
    }

    public void setRwdRedemptionMinPoints(Integer rwdRedemptionMinPoints) {
        this.rwdRedemptionMinPoints = rwdRedemptionMinPoints;
    }

    public Long getRwdImage() {
        return rwdImage;
    }

    public void setRwdImage(Long rwdImage) {
        this.rwdImage = rwdImage;
    }

    public Integer getRwdAllowDecimal() {
        return rwdAllowDecimal;
    }

    public void setRwdAllowDecimal(Integer rwdAllowDecimal) {
        this.rwdAllowDecimal = rwdAllowDecimal;
    }

    public Integer getRwdExpiryPeriod() {
        return rwdExpiryPeriod;
    }

    public void setRwdExpiryPeriod(Integer rwdExpiryPeriod) {
        this.rwdExpiryPeriod = rwdExpiryPeriod;
    }

    public Integer getRwdRoundingMethod() {
        return rwdRoundingMethod;
    }

    public void setRwdRoundingMethod(Integer rwdRoundingMethod) {
        this.rwdRoundingMethod = rwdRoundingMethod;
    }

    @Override
    public String toString() {
        return "RewardCurrency{" +
                "rwdCurrencyId=" + rwdCurrencyId +
                ", rwdMerchantNo=" + rwdMerchantNo +
                ", rwdRewardUnitType=" + rwdRewardUnitType +
                ", rwdCurrencyName='" + rwdCurrencyName + '\'' +
                ", rwdDescription='" + rwdDescription + '\'' +
                ", rwdStatus=" + rwdStatus +
                ", rwdExpiryOption=" + rwdExpiryOption +
                ", rwdExpiryDate=" + rwdExpiryDate +
                ", rwdExpiryDays=" + rwdExpiryDays +
                ", rwdExpiryPeriod=" + rwdExpiryPeriod +
                ", rwdCashbackIndicator=" + rwdCashbackIndicator +
                ", rwdCashbackRatioDeno=" + rwdCashbackRatioDeno +
                ", rwdExclusiveIndicator=" + rwdExclusiveIndicator +
                ", rwdRedemptionMinPoints=" + rwdRedemptionMinPoints +
                ", rwdImage=" + rwdImage +
                ", rwdAllowDecimal=" + rwdAllowDecimal +
                ", rwdRoundingMethod=" + rwdRoundingMethod +
                '}';
    }
}
