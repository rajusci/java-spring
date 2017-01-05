package com.inspirenetz.api.core.domain;

import com.inspirenetz.api.core.dictionary.PurchaseStatus;
import com.inspirenetz.api.core.dictionary.SaleType;
import org.hibernate.validator.constraints.NotEmpty;
import org.hibernate.validator.constraints.Range;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.sql.Date;
import java.sql.Time;
import java.sql.Timestamp;
import java.util.Calendar;

/**
 * Created by sandheepgr on 28/3/14.
 */
@Entity
@Table(name="PURCHASES")
public class Purchase extends AuditedEntity {

    @Column(name = "PRC_ID",nullable = false)
    @Basic(fetch = FetchType.EAGER)
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long prcId;


    @Column(name = "PRC_MERCHANT_NO",nullable = false)
    @Basic(fetch = FetchType.EAGER)
    private Long prcMerchantNo;

    @Column(name = "PRC_TYPE",nullable = false)
    @Basic(fetch = FetchType.EAGER)
    private int prcType = SaleType.STANDARD_PURCHASE;

    @Column(name = "PRC_DATE",nullable = false)
    @Basic(fetch = FetchType.EAGER)
    private Date prcDate;

    @Column(name = "PRC_TIME",nullable = false)
    @Basic(fetch = FetchType.EAGER)
    private Time prcTime;

    @Column(name = "PRC_LOYALTY_ID",nullable = false)
    @Basic(fetch = FetchType.EAGER)
    @NotEmpty(message="{purchase.prcloyaltyid.notempty}")
    @NotNull(message="{purchase.prcloyaltyid.notnull}")
    private String prcLoyaltyId;

    @Column(name = "PRC_AMOUNT",nullable = false)
    @Basic(fetch = FetchType.EAGER)
    @Range(min=1, message="{purchase.prcamount.range}")
    private Double prcAmount = new Double(0);

    @Column(name = "PRC_CURRENCY",nullable = false)
    @Basic(fetch = FetchType.EAGER)
    private int prcCurrency = 356;

    @Column(name = "PRC_PAYMENT_MODE",nullable = false)
    @Basic(fetch = FetchType.EAGER)
    private int prcPaymentMode = 1;

    @Column(name = "PRC_LOCATION",nullable = false)
    @Basic(fetch = FetchType.EAGER)
    private Long prcLocation = 0L;

    @Column(name = "PRC_TXN_CHANNEL",nullable = true)
    @Basic(fetch = FetchType.EAGER)
    private int prcTxnChannel = 1;

    @Column(name = "PRC_PAYMENT_REFERENCE",nullable = true)
    @Basic(fetch = FetchType.EAGER)
    @Size(max=50 , message = "{purchase.prcpaymentreference.size}")
    private String prcPaymentReference;

    @Column(name = "PRC_STATUS",nullable = false)
    @Basic(fetch = FetchType.EAGER)
    private int prcStatus = PurchaseStatus.NEW;

    @Column(name = "PRC_QUANTITY",nullable = false)
    @Basic(fetch = FetchType.EAGER)
    private Double prcQuantity = new Double(0);

    @Column(name = "PRC_DAY_OF_WEEK",nullable = false)
    @Basic(fetch = FetchType.EAGER)
    private int prcDayOfWeek = 0;

    @Column(name = "PRC_TIMESTAMP",nullable = false)
    @Basic(fetch = FetchType.EAGER)
    private Timestamp prcTimestamp;

    @Column(name = "PRC_USER_NO",nullable = false)
    @Basic(fetch = FetchType.EAGER)
    private Long prcUserNo = 0L;


    public Long getPrcId() {
        return prcId;
    }

    public void setPrcId(Long prcId) {
        this.prcId = prcId;
    }

    public Long getPrcMerchantNo() {
        return prcMerchantNo;
    }

    public void setPrcMerchantNo(Long prcMerchantNo) {
        this.prcMerchantNo = prcMerchantNo;
    }

    public int getPrcType() {
        return prcType;
    }

    public void setPrcType(int prcType) {
        this.prcType = prcType;
    }

    public void setPrcQuantity(Double prcQuantity) {
        this.prcQuantity = prcQuantity;
    }

    public Date getPrcDate() {
        return prcDate;
    }

    public void setPrcDate(Date prcDate) {
        this.prcDate = prcDate;
    }

    public Time getPrcTime() {
        return prcTime;
    }

    public void setPrcTime(Time prcTime) {
        this.prcTime = prcTime;
    }

    public String getPrcLoyaltyId() {
        return prcLoyaltyId;
    }

    public void setPrcLoyaltyId(String prcLoyaltyId) {
        this.prcLoyaltyId = prcLoyaltyId;
    }

    public double getPrcAmount() {
        return prcAmount;
    }

    public void setPrcAmount(double prcAmount) {
        this.prcAmount = prcAmount;
    }

    public int getPrcCurrency() {
        return prcCurrency;
    }

    public void setPrcCurrency(int prcCurrency) {
        this.prcCurrency = prcCurrency;
    }

    public int getPrcPaymentMode() {
        return prcPaymentMode;
    }

    public void setPrcPaymentMode(int prcPaymentMode) {
        this.prcPaymentMode = prcPaymentMode;
    }


    public Long getPrcLocation() {
        return prcLocation;
    }

    public void setPrcLocation(Long prcLocation) {
        this.prcLocation = prcLocation;
    }

    public int getPrcTxnChannel() {
        return prcTxnChannel;
    }

    public void setPrcTxnChannel(int prcTxnChannel) {
        this.prcTxnChannel = prcTxnChannel;
    }

    public String getPrcPaymentReference() {
        return prcPaymentReference;
    }

    public void setPrcPaymentReference(String prcPaymentReference) {
        this.prcPaymentReference = prcPaymentReference;
    }

    public void setPrcAmount(Double prcAmount) {
        this.prcAmount = prcAmount;
    }

    public int getPrcStatus() {
        return prcStatus;
    }

    public void setPrcStatus(int prcStatus) {
        this.prcStatus = prcStatus;
    }

    public double getPrcQuantity() {
        return prcQuantity;
    }

    public void setPrcQuantity(double prcQuantity) {
        this.prcQuantity = prcQuantity;
    }

    public int getPrcDayOfWeek() {
        return prcDayOfWeek;
    }

    public void setPrcDayOfWeek(int prcDayOfWeek) {
        this.prcDayOfWeek = prcDayOfWeek;
    }

    public Timestamp getPrcTimestamp() {
        return prcTimestamp;
    }

    public void setPrcTimestamp(Timestamp prcTimestamp) {
        this.prcTimestamp = prcTimestamp;
    }

    public Long getPrcUserNo() {
        return prcUserNo;
    }

    public void setPrcUserNo(Long prcUserNo) {
        this.prcUserNo = prcUserNo;
    }

    @Override
    public String toString() {
        return "Purchase{" +
                "prcId=" + prcId +
                ", prcMerchantNo=" + prcMerchantNo +
                ", prcType=" + prcType +
                ", prcDate=" + prcDate +
                ", prcTime=" + prcTime +
                ", prcLoyaltyId='" + prcLoyaltyId + '\'' +
                ", prcAmount=" + prcAmount +
                ", prcCurrency=" + prcCurrency +
                ", prcPaymentMode=" + prcPaymentMode +
                ", prcLocation=" + prcLocation +
                ", prcTxnChannel=" + prcTxnChannel +
                ", prcPaymentReference=" + prcPaymentReference +
                ", prcStatus=" + prcStatus +
                ", prcQuantity=" + prcQuantity +
                ", prcDayOfWeek=" + prcDayOfWeek +
                ", prcTimestamp=" + prcTimestamp +
                ", prcUserNo=" + prcUserNo +
                '}';
    }


    @PrePersist
    protected void populateFields() {

        // Create the Calendar object
        Calendar c = Calendar.getInstance();

        // Set the time
        c.setTime(prcDate);

        // Set the day of week
        prcDayOfWeek = c.get(Calendar.DAY_OF_WEEK);


        // Set the timestamp
        if ( prcTimestamp == null ){

            prcTimestamp = new Timestamp(System.currentTimeMillis());

        }


        // Set the time if its null
        if ( prcTime == null ) {

            prcTime = new Time(prcTimestamp.getTime());

        }

    }
}
