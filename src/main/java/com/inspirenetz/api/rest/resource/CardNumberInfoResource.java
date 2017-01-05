package com.inspirenetz.api.rest.resource;

import com.inspirenetz.api.core.dictionary.CardTypeExpiryOption;
import com.inspirenetz.api.core.dictionary.CardTypeType;

import java.sql.Date;

/**
 * Created by ameen on 21/10/15.
 */
public class CardNumberInfoResource extends BaseResource {


    private Long cniId;
    private Long cniMerchantNo;
    private String cniCardNumber;
    private Long cniCardType;
    private Long cniBatchId;
    private Integer cniCardStatus;
    private Integer cniPinEnabled;

    private String crtName = "";

    private int crtType = CardTypeType.FIXED_VALUE;

    private double crtFixedValue = 0.0;

    private double crtMinTopupValue = 0.0;

    private double crtMaxValue = 0.0;

    private Integer crtExpiryOption = CardTypeExpiryOption.EXPIRY_DATE;

    private Date crtExpiryDate;

    private Integer crtExpiryDays = 0;

    private Integer crtMaxNumTxns= 0;

    public Long getCniId() {
        return cniId;
    }

    public void setCniId(Long cniId) {
        this.cniId = cniId;
    }

    public Long getCniMerchantNo() {
        return cniMerchantNo;
    }

    public void setCniMerchantNo(Long cniMerchantNo) {
        this.cniMerchantNo = cniMerchantNo;
    }

    public String getCniCardNumber() {
        return cniCardNumber;
    }

    public void setCniCardNumber(String cniCardNumber) {
        this.cniCardNumber = cniCardNumber;
    }

    public Long getCniCardType() {
        return cniCardType;
    }

    public void setCniCardType(Long cniCardType) {
        this.cniCardType = cniCardType;
    }

    public Long getCniBatchId() {
        return cniBatchId;
    }

    public void setCniBatchId(Long cniBatchId) {
        this.cniBatchId = cniBatchId;
    }

    public Integer getCniCardStatus() {
        return cniCardStatus;
    }

    public void setCniCardStatus(Integer cniCardStatus) {
        this.cniCardStatus = cniCardStatus;
    }

    public String getCrtName() {
        return crtName;
    }

    public void setCrtName(String crtName) {
        this.crtName = crtName;
    }

    public int getCrtType() {
        return crtType;
    }

    public void setCrtType(int crtType) {
        this.crtType = crtType;
    }

    public double getCrtFixedValue() {
        return crtFixedValue;
    }

    public void setCrtFixedValue(double crtFixedValue) {
        this.crtFixedValue = crtFixedValue;
    }

    public double getCrtMinTopupValue() {
        return crtMinTopupValue;
    }

    public void setCrtMinTopupValue(double crtMinTopupValue) {
        this.crtMinTopupValue = crtMinTopupValue;
    }

    public double getCrtMaxValue() {
        return crtMaxValue;
    }

    public void setCrtMaxValue(double crtMaxValue) {
        this.crtMaxValue = crtMaxValue;
    }

    public Integer getCrtExpiryOption() {
        return crtExpiryOption;
    }

    public void setCrtExpiryOption(Integer crtExpiryOption) {
        this.crtExpiryOption = crtExpiryOption;
    }

    public Date getCrtExpiryDate() {
        return crtExpiryDate;
    }

    public void setCrtExpiryDate(Date crtExpiryDate) {
        this.crtExpiryDate = crtExpiryDate;
    }

    public Integer getCrtExpiryDays() {
        return crtExpiryDays;
    }

    public void setCrtExpiryDays(Integer crtExpiryDays) {
        this.crtExpiryDays = crtExpiryDays;
    }

    public Integer getCrtMaxNumTxns() {
        return crtMaxNumTxns;
    }

    public void setCrtMaxNumTxns(Integer crtMaxNumTxns) {
        this.crtMaxNumTxns = crtMaxNumTxns;
    }

    public Integer getCniPinEnabled() {
        return cniPinEnabled;
    }

    public void setCniPinEnabled(Integer cniPinEnabled) {
        this.cniPinEnabled = cniPinEnabled;
    }

    @Override
    public String toString() {
        return "CardNumberInfoResource{" +
                "cniId=" + cniId +
                ", cniMerchantNo=" + cniMerchantNo +
                ", cniCardNumber='" + cniCardNumber + '\'' +
                ", cniCardType=" + cniCardType +
                ", cniBatchId=" + cniBatchId +
                ", cniCardStatus=" + cniCardStatus +
                ", cniPinEnabled=" + cniPinEnabled +
                ", crtName='" + crtName + '\'' +
                ", crtType=" + crtType +
                ", crtFixedValue=" + crtFixedValue +
                ", crtMinTopupValue=" + crtMinTopupValue +
                ", crtMaxValue=" + crtMaxValue +
                ", crtExpiryOption=" + crtExpiryOption +
                ", crtExpiryDate=" + crtExpiryDate +
                ", crtExpiryDays=" + crtExpiryDays +
                ", crtMaxNumTxns=" + crtMaxNumTxns +
                '}';
    }
}
