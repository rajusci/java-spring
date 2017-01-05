package com.inspirenetz.api.core.domain;

import com.inspirenetz.api.core.dictionary.CashPaymentStatus;
import com.inspirenetz.api.core.dictionary.IndicatorStatus;
import com.inspirenetz.api.core.dictionary.OnlineOrderStatus;
import org.hibernate.validator.constraints.NotEmpty;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.sql.Date;
import java.sql.Time;
import java.sql.Timestamp;

/**
 * Created by sandheepgr on 30/7/14.
 */
@Entity
@Table(name = "ONLINE_ORDERS")
public class OnlineOrder  extends AuditedEntity{
    
    @Id
    @Column(name = "ORD_ID", nullable = false, insertable = true, updatable = true, length = 10, precision = 0)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long ordId;

    @Basic
    @Column(name = "ORD_DATE", nullable = false, insertable = true, updatable = true, length = 10, precision = 0)
    private Date ordDate;

    @Basic
    @Column(name = "ORD_TIME", nullable = false, insertable = true, updatable = true, length = 8, precision = 0)
    private Time ordTime;

    @Basic
    @Column(name = "ORD_MERCHANT_NO", nullable = false, insertable = true, updatable = true, length = 10, precision = 0)
    private Long ordMerchantNo = 0L;

    @Basic
    @Column(name = "ORD_STATUS", nullable = false, insertable = true, updatable = true, length = 10, precision = 0)
    private Integer ordStatus = OnlineOrderStatus.NEW;

    @Basic
    @Column(name = "ORD_PRODUCT_CODE", nullable = false, insertable = true, updatable = true, length = 30, precision = 0)
    @NotNull(message = "{onlineorder.ordproductcode.notnull}")
    @NotEmpty(message = "{onlineorder.ordproductcode.notempty}")
    @Size(min=1,max=20,message = "{onlineorder.ordproductcode.size}")
    private String ordProductCode = "";

    @Basic
    @Column(name = "ORD_QTY", nullable = false, insertable = true, updatable = true, length = 8, precision = 2)
    private Double ordQty =  1.0;

    @Basic
    @Column(name = "ORD_LOYALTY_ID", nullable = false, insertable = true, updatable = true, length = 20, precision = 0)
    private String ordLoyaltyId = "";

    @Basic
    @Column(name = "ORD_ORDER_SLOT", nullable = false, insertable = true, updatable = true, length = 10, precision = 0)
    private Long ordOrderSlot = 0L;

    @Basic
    @Column(name = "ORD_ORDER_LOCATION", nullable = false, insertable = true, updatable = true, length = 10, precision = 0)
    private Long ordOrderLocation = 0L;

    @Basic
    @Column(name = "ORD_DELIVERY_IND", nullable = false, insertable = true, updatable = true, length = 10, precision = 0)
    private Integer ordDeliveryInd = IndicatorStatus.NO;

    @Basic
    @Column(name = "ORD_DELIVERY_ADDR1", nullable = true, insertable = true, updatable = true, length = 25, precision = 0)
    @Size(max=50,message = "{onlineorder.orddeliveryaddr1.size}")
    private String ordDeliveryAddr1 = "";

    @Basic
    @Column(name = "ORD_DELIVERY_ADDR2", nullable = true, insertable = true, updatable = true, length = 25, precision = 0)
    @Size(max=50,message = "{onlineorder.orddeliveryaddr2.size}")
    private String ordDeliveryAddr2 = "";

    @Basic
    @Column(name = "ORD_DELIVERY_ADDR3", nullable = true, insertable = true, updatable = true, length = 25, precision = 0)
    @Size(max=50,message = "{onlineorder.orddeliveryaddr3.size}")
    private String ordDeliveryAddr3 = "";

    @Basic
    @Column(name = "ORD_DELIVERY_CITY", nullable = true, insertable = true, updatable = true, length = 25, precision = 0)
    @Size(max=50,message = "{onlineorder.orddeliverycity.size}")
    private String ordDeliveryCity ="";

    @Basic
    @Column(name = "ORD_DELIVERY_STATE", nullable = true, insertable = true, updatable = true, length = 25, precision = 0)
    @Size(max=50,message = "{onlineorder.orddeliverystate.size}")
    private String ordDeliveryState = "";
    
    @Basic
    @Column(name = "ORD_DELIVERY_COUNTRY", nullable = true, insertable = true, updatable = true, length = 100, precision = 0)
    @Size(max=100,message = "{onlineorder.orddeliverycountry.size}")
    private String ordDeliveryCountry ="";

    @Basic
    @Column(name = "ORD_DELIVERY_POSTCODE", nullable = true, insertable = true, updatable = true, length = 100, precision = 0)
    @Size(max=10,message = "{onlineorder.orddeliverypostcode.size}")
    private String ordDeliveryPostcode = "";

    @Basic
    @Column(name = "ORD_CONTACT_NUMBER", nullable = false, insertable = true, updatable = true, length = 25, precision = 0)
    @Size(max=12,message = "{onlineorder.ordcontactnumber.size}")
    private String ordContactNumber = "";

    @Basic
    @Column(name = "ORD_AMOUNT", nullable = false, insertable = true, updatable = true, length = 12, precision = 2)
    private Double ordAmount = 0.0;

    @Basic
    @Column(name = "ORD_ADDITIONAL_CHARGES", nullable = false, insertable = true, updatable = true, length = 10, precision = 2)
    private Double ordAdditionalCharges = 0.0;

    @Basic
    @Column(name = "ORD_CASH_PAYMENT_STATUS", nullable = false, insertable = true, updatable = true, length = 10, precision = 0)
    private Integer ordCashPaymentStatus = CashPaymentStatus.NOT_PAID;

    @Basic
    @Column(name = "ORD_REF", nullable = true, insertable = true, updatable = true, length = 50, precision = 0)
    @Size(max=50,message = "{onlineorder.ordref.size}")
    private String ordRef = "";

    @Basic
    @Column(name = "ORD_TIMESTAMP", nullable = false, insertable = true, updatable = true, length = 19, precision = 0)
    private Timestamp ordTimestamp;

    @Basic
    @Column(name = "ORD_USER_NO", nullable = false, insertable = true, updatable = true, length = 10, precision = 0)
    private Long ordUserNo = 0L;

    @Basic
    @Column(name = "ORD_UNIQUE_BATCH_TRACKING_ID", nullable = false, insertable = true, updatable = true, length = 10, precision = 0)
    private Integer ordUniqueBatchTrackingId = 0;

    @Basic
    @Column(name = "ORD_DELIVERY_COURIER_INFO", nullable = true, insertable = true, updatable = true, length = 200, precision = 0)
    private String ordDeliveryCourierInfo = "";

    @Basic
    @Column(name = "ORD_DELIVERY_COURIER_TRACKING", nullable = true, insertable = true, updatable = true, length = 50, precision = 0)
    private String ordDeliveryCourierTracking = "";


    


    @PrePersist
    @PreUpdate
    private void populateFields() {


        // If the date field is not set, set the date to current date
        if ( ordDate == null ) {

            // Set the date to current date
            ordDate = new Date(new java.util.Date().getTime());

        }


        // If the time field is not set, then set the time to current time
        if ( ordTime == null ) {

            // Set the time to current time.
            ordTime = new Time(new java.util.Date().getTime());

        }
    }


    public Long getOrdId() {
        return ordId;
    }

    public void setOrdId(Long ordId) {
        this.ordId = ordId;
    }

    public Date getOrdDate() {
        return ordDate;
    }

    public void setOrdDate(Date ordDate) {
        this.ordDate = ordDate;
    }

    public Time getOrdTime() {
        return ordTime;
    }

    public void setOrdTime(Time ordTime) {
        this.ordTime = ordTime;
    }

    public Long getOrdMerchantNo() {
        return ordMerchantNo;
    }

    public void setOrdMerchantNo(Long ordMerchantNo) {
        this.ordMerchantNo = ordMerchantNo;
    }

    public Integer getOrdStatus() {
        return ordStatus;
    }

    public void setOrdStatus(Integer ordStatus) {
        this.ordStatus = ordStatus;
    }

    public String getOrdProductCode() {
        return ordProductCode;
    }

    public void setOrdProductCode(String ordProductCode) {
        this.ordProductCode = ordProductCode;
    }

    public Double getOrdQty() {
        return ordQty;
    }

    public void setOrdQty(Double ordQty) {
        this.ordQty = ordQty;
    }

    public String getOrdLoyaltyId() {
        return ordLoyaltyId;
    }

    public void setOrdLoyaltyId(String ordLoyaltyId) {
        this.ordLoyaltyId = ordLoyaltyId;
    }

    public Long getOrdOrderSlot() {
        return ordOrderSlot;
    }

    public void setOrdOrderSlot(Long ordOrderSlot) {
        this.ordOrderSlot = ordOrderSlot;
    }

    public Long getOrdOrderLocation() {
        return ordOrderLocation;
    }

    public void setOrdOrderLocation(Long ordOrderLocation) {
        this.ordOrderLocation = ordOrderLocation;
    }

    public Integer getOrdDeliveryInd() {
        return ordDeliveryInd;
    }

    public void setOrdDeliveryInd(Integer ordDeliveryInd) {
        this.ordDeliveryInd = ordDeliveryInd;
    }

    public String getOrdDeliveryAddr1() {
        return ordDeliveryAddr1;
    }

    public void setOrdDeliveryAddr1(String ordDeliveryAddr1) {
        this.ordDeliveryAddr1 = ordDeliveryAddr1;
    }

    public String getOrdDeliveryAddr2() {
        return ordDeliveryAddr2;
    }

    public void setOrdDeliveryAddr2(String ordDeliveryAddr2) {
        this.ordDeliveryAddr2 = ordDeliveryAddr2;
    }

    public String getOrdDeliveryAddr3() {
        return ordDeliveryAddr3;
    }

    public void setOrdDeliveryAddr3(String ordDeliveryAddr3) {
        this.ordDeliveryAddr3 = ordDeliveryAddr3;
    }

    public String getOrdDeliveryCity() {
        return ordDeliveryCity;
    }

    public void setOrdDeliveryCity(String ordDeliveryCity) {
        this.ordDeliveryCity = ordDeliveryCity;
    }

    public String getOrdDeliveryState() {
        return ordDeliveryState;
    }

    public void setOrdDeliveryState(String ordDeliveryState) {
        this.ordDeliveryState = ordDeliveryState;
    }

    public String getOrdDeliveryCountry() {
        return ordDeliveryCountry;
    }

    public void setOrdDeliveryCountry(String ordDeliveryCountry) {
        this.ordDeliveryCountry = ordDeliveryCountry;
    }

    public String getOrdDeliveryPostcode() {
        return ordDeliveryPostcode;
    }

    public void setOrdDeliveryPostcode(String ordDeliveryPostcode) {
        this.ordDeliveryPostcode = ordDeliveryPostcode;
    }

    public String getOrdContactNumber() {
        return ordContactNumber;
    }

    public void setOrdContactNumber(String ordContactNumber) {
        this.ordContactNumber = ordContactNumber;
    }

    public Double getOrdAmount() {
        return ordAmount;
    }

    public void setOrdAmount(Double ordAmount) {
        this.ordAmount = ordAmount;
    }

    public Double getOrdAdditionalCharges() {
        return ordAdditionalCharges;
    }

    public void setOrdAdditionalCharges(Double ordAdditionalCharges) {
        this.ordAdditionalCharges = ordAdditionalCharges;
    }

    public Integer getOrdCashPaymentStatus() {
        return ordCashPaymentStatus;
    }

    public void setOrdCashPaymentStatus(Integer ordCashPaymentStatus) {
        this.ordCashPaymentStatus = ordCashPaymentStatus;
    }

    public String getOrdRef() {
        return ordRef;
    }

    public void setOrdRef(String ordRef) {
        this.ordRef = ordRef;
    }

    public Timestamp getOrdTimestamp() {
        return ordTimestamp;
    }

    public void setOrdTimestamp(Timestamp ordTimestamp) {
        this.ordTimestamp = ordTimestamp;
    }

    public Long getOrdUserNo() {
        return ordUserNo;
    }

    public void setOrdUserNo(Long ordUserNo) {
        this.ordUserNo = ordUserNo;
    }

    public Integer getOrdUniqueBatchTrackingId() {
        return ordUniqueBatchTrackingId;
    }

    public void setOrdUniqueBatchTrackingId(Integer ordUniqueBatchTrackingId) {
        this.ordUniqueBatchTrackingId = ordUniqueBatchTrackingId;
    }

    public String getOrdDeliveryCourierInfo() {
        return ordDeliveryCourierInfo;
    }

    public void setOrdDeliveryCourierInfo(String ordDeliveryCourierInfo) {
        this.ordDeliveryCourierInfo = ordDeliveryCourierInfo;
    }

    public String getOrdDeliveryCourierTracking() {
        return ordDeliveryCourierTracking;
    }

    public void setOrdDeliveryCourierTracking(String ordDeliveryCourierTracking) {
        this.ordDeliveryCourierTracking = ordDeliveryCourierTracking;
    }


    @Override
    public String toString() {
        return "OnlineOrder{" +
                "ordId=" + ordId +
                ", ordDate=" + ordDate +
                ", ordTime=" + ordTime +
                ", ordMerchantNo=" + ordMerchantNo +
                ", ordStatus=" + ordStatus +
                ", ordProductCode='" + ordProductCode + '\'' +
                ", ordQty=" + ordQty +
                ", ordLoyaltyId='" + ordLoyaltyId + '\'' +
                ", ordOrderSlot=" + ordOrderSlot +
                ", ordOrderLocation=" + ordOrderLocation +
                ", ordDeliveryInd=" + ordDeliveryInd +
                ", ordDeliveryAddr1='" + ordDeliveryAddr1 + '\'' +
                ", ordDeliveryAddr2='" + ordDeliveryAddr2 + '\'' +
                ", ordDeliveryAddr3='" + ordDeliveryAddr3 + '\'' +
                ", ordDeliveryCity='" + ordDeliveryCity + '\'' +
                ", ordDeliveryState='" + ordDeliveryState + '\'' +
                ", ordDeliveryCountry='" + ordDeliveryCountry + '\'' +
                ", ordDeliveryPostcode='" + ordDeliveryPostcode + '\'' +
                ", ordContactNumber='" + ordContactNumber + '\'' +
                ", ordAmount=" + ordAmount +
                ", ordAdditionalCharges=" + ordAdditionalCharges +
                ", ordCashPaymentStatus=" + ordCashPaymentStatus +
                ", ordRef='" + ordRef + '\'' +
                ", ordTimestamp=" + ordTimestamp +
                ", ordUserNo=" + ordUserNo +
                ", ordUniqueBatchTrackingId=" + ordUniqueBatchTrackingId +
                ", ordDeliveryCourierInfo='" + ordDeliveryCourierInfo + '\'' +
                ", ordDeliveryCourierTracking='" + ordDeliveryCourierTracking + '\'' +
                '}';
    }
}
