package com.inspirenetz.api.core.domain;

import com.inspirenetz.api.core.dictionary.EVoucherPaymentStatus;
import com.inspirenetz.api.core.dictionary.EVoucherStatus;

import javax.persistence.*;
import java.math.BigDecimal;
import java.sql.Date;
import java.sql.Timestamp;

/**
 * Created by sandheepgr on 29/3/14.
 */
@Entity
@Table(name = "EVOUCHERS")
public class Evoucher {

    @Id
    @Column(name = "EVO_ID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long evoId;
    
    @Basic(fetch = FetchType.EAGER)
    @Column(name = "EVO_MERCHANT_NO")
    private Long evoMerchantNo;
    
    @Basic(fetch = FetchType.EAGER)
    @Column(name = "EVO_USER_NO")
    private int evoUserNo;
    
    @Basic(fetch = FetchType.EAGER)
    @Column(name = "EVO_FROM_NAME")
    private String evoFromName;
    
    @Basic(fetch = FetchType.EAGER)
    @Column(name = "EVO_TO_NAME")
    private String evoToName;
    
    @Basic(fetch = FetchType.EAGER)
    @Column(name = "EVO_TO_EMAIL")
    private String evoToEmail;
    
    @Basic(fetch = FetchType.EAGER)
    @Column(name = "EVO_TO_MOBILE")
    private String evoToMobile;
    
    @Basic(fetch = FetchType.EAGER)
    @Column(name = "EVO_MESSAGE")
    private String evoMessage;
    
    @Basic(fetch = FetchType.EAGER)
    @Column(name = "EVO_STATUS")
    private EVoucherStatus evoStatus;
    
    @Basic(fetch = FetchType.EAGER)
    @Column(name = "EVO_CURRENCY")
    private int evoCurrency;
    
    @Basic(fetch = FetchType.EAGER)
    @Column(name = "EVO_AMOUNT")
    private BigDecimal evoAmount;
    
    @Basic(fetch = FetchType.EAGER)
    @Column(name = "EVO_VOUCHER_NUMBER")
    private String evoVoucherNumber;
    
    @Basic(fetch = FetchType.EAGER)
    @Column(name = "EVO_PAYMENT_MODE")
    private int evoPaymentMode;
    
    @Basic(fetch = FetchType.EAGER)
    @Column(name = "EVO_PAYMENT_STATUS")
    private EVoucherPaymentStatus evoPaymentStatus;
    
    @Basic(fetch = FetchType.EAGER)
    @Column(name = "EVO_DATE")
    private Date evoDate;
    
    @Basic(fetch = FetchType.EAGER)
    @Column(name = "EVO_UPDATE_TIMESTAMP")
    private Timestamp evoUpdateTimestamp;


    public Long getEvoId() {
        return evoId;
    }

    public void setEvoId(Long evoId) {
        this.evoId = evoId;
    }

    public Long getEvoMerchantNo() {
        return evoMerchantNo;
    }

    public void setEvoMerchantNo(Long evoMerchantNo) {
        this.evoMerchantNo = evoMerchantNo;
    }

    public int getEvoUserNo() {
        return evoUserNo;
    }

    public void setEvoUserNo(int evoUserNo) {
        this.evoUserNo = evoUserNo;
    }

    public String getEvoFromName() {
        return evoFromName;
    }

    public void setEvoFromName(String evoFromName) {
        this.evoFromName = evoFromName;
    }

    public String getEvoToName() {
        return evoToName;
    }

    public void setEvoToName(String evoToName) {
        this.evoToName = evoToName;
    }

    public String getEvoToEmail() {
        return evoToEmail;
    }

    public void setEvoToEmail(String evoToEmail) {
        this.evoToEmail = evoToEmail;
    }

    public String getEvoToMobile() {
        return evoToMobile;
    }

    public void setEvoToMobile(String evoToMobile) {
        this.evoToMobile = evoToMobile;
    }

    public String getEvoMessage() {
        return evoMessage;
    }

    public void setEvoMessage(String evoMessage) {
        this.evoMessage = evoMessage;
    }

    public EVoucherStatus getEvoStatus() {
        return evoStatus;
    }

    public void setEvoStatus(EVoucherStatus evoStatus) {
        this.evoStatus = evoStatus;
    }

    public int getEvoCurrency() {
        return evoCurrency;
    }

    public void setEvoCurrency(int evoCurrency) {
        this.evoCurrency = evoCurrency;
    }

    public BigDecimal getEvoAmount() {
        return evoAmount;
    }

    public void setEvoAmount(BigDecimal evoAmount) {
        this.evoAmount = evoAmount;
    }

    public String getEvoVoucherNumber() {
        return evoVoucherNumber;
    }

    public void setEvoVoucherNumber(String evoVoucherNumber) {
        this.evoVoucherNumber = evoVoucherNumber;
    }

    public int getEvoPaymentMode() {
        return evoPaymentMode;
    }

    public void setEvoPaymentMode(int evoPaymentMode) {
        this.evoPaymentMode = evoPaymentMode;
    }

    public EVoucherPaymentStatus getEvoPaymentStatus() {
        return evoPaymentStatus;
    }

    public void setEvoPaymentStatus(EVoucherPaymentStatus evoPaymentStatus) {
        this.evoPaymentStatus = evoPaymentStatus;
    }

    public Date getEvoDate() {
        return evoDate;
    }

    public void setEvoDate(Date evoDate) {
        this.evoDate = evoDate;
    }

    public Timestamp getEvoUpdateTimestamp() {
        return evoUpdateTimestamp;
    }

    public void setEvoUpdateTimestamp(Timestamp evoUpdateTimestamp) {
        this.evoUpdateTimestamp = evoUpdateTimestamp;
    }


    @Override
    public String toString() {
        return "Evoucher{" +
                "evoId=" + evoId +
                ", evoMerchantNo=" + evoMerchantNo +
                ", evoUserNo=" + evoUserNo +
                ", evoFromName='" + evoFromName + '\'' +
                ", evoToName='" + evoToName + '\'' +
                ", evoToEmail='" + evoToEmail + '\'' +
                ", evoToMobile='" + evoToMobile + '\'' +
                ", evoMessage='" + evoMessage + '\'' +
                ", evoStatus=" + evoStatus +
                ", evoCurrency=" + evoCurrency +
                ", evoAmount=" + evoAmount +
                ", evoVoucherNumber='" + evoVoucherNumber + '\'' +
                ", evoPaymentMode=" + evoPaymentMode +
                ", evoPaymentStatus=" + evoPaymentStatus +
                ", evoDate=" + evoDate +
                ", evoUpdateTimestamp=" + evoUpdateTimestamp +
                '}';
    }
}
