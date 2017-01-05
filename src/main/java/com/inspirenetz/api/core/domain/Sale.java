package com.inspirenetz.api.core.domain;

import com.inspirenetz.api.core.dictionary.PurchaseStatus;
import com.inspirenetz.api.core.dictionary.SaleType;
import com.inspirenetz.api.core.incustomization.attrext.AttributeExtendedEntityMap;
import com.inspirenetz.api.core.incustomization.attrext.AttributeExtension;
import org.hibernate.validator.constraints.Range;

import javax.persistence.*;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.constraints.Size;
import java.sql.Date;
import java.sql.Time;
import java.sql.Timestamp;
import java.util.Calendar;
import java.util.Set;

/**
 * Created by sandheepgr on 7/8/14.
 */
@Entity
@Table(name = "SALES")
public class Sale extends AuditedEntity implements AttributeExtension  {

    @Id
    @Column(name = "SAL_ID", nullable = false, insertable = true, updatable = true, length = 10, precision = 0)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long salId;

    @Basic
    @Column(name = "SAL_MERCHANT_NO", nullable = false, insertable = true, updatable = true, length = 10, precision = 0)
    private Long salMerchantNo = 0L;

    @Basic
    @Column(name = "SAL_LOYALTY_ID", nullable = false, insertable = true, updatable = true, length = 20, precision = 0)
    private String salLoyaltyId = "";

    @Basic
    @Column(name = "SAL_LOCATION", nullable = false, insertable = true, updatable = true, length = 10, precision = 0)
    private Long salLocation =0L;

    @Basic
    @Column(name = "SAL_DATE", nullable = false, insertable = true, updatable = true, length = 10, precision = 0)
    private Date salDate;

    @Basic
    @Column(name = "SAL_TIME", nullable = false, insertable = true, updatable = true, length = 8, precision = 0)
    private Time salTime;

    @Column(name = "SAL_TYPE",nullable = false)
    @Basic(fetch = FetchType.EAGER)
    private Integer salType = SaleType.ITEM_BASED_PURCHASE;

    @Column(name = "SAL_AMOUNT",nullable = false)
    @Basic(fetch = FetchType.EAGER)
    private Double salAmount = new Double(0);

    @Column(name = "SAL_CURRENCY",nullable = false)
    @Basic(fetch = FetchType.EAGER)
    private Integer salCurrency = 356;

    @Column(name = "SAL_PAYMENT_MODE",nullable = false)
    @Basic(fetch = FetchType.EAGER)
    private Integer salPaymentMode = 1;

    @Column(name = "SAL_TXN_CHANNEL",nullable = true)
    @Basic(fetch = FetchType.EAGER)
    private Integer salTxnChannel = 1;

    @Column(name = "SAL_QUANTITY",nullable = false)
    @Basic(fetch = FetchType.EAGER)
    private Double salQuantity = 0.0;

    @Column(name = "SAL_PAYMENT_REFERENCE",nullable = true)
    @Basic(fetch = FetchType.EAGER)
    @Size(max=50 , message = "{sale.salpaymentreference.size}")
    private String salPaymentReference;

    @Column(name = "SAL_STATUS",nullable = false)
    @Basic(fetch = FetchType.EAGER)
    private Integer salStatus = PurchaseStatus.NEW;

    @Column(name = "SAL_TIMESTAMP",nullable = false)
    @Basic(fetch = FetchType.EAGER)
    private Timestamp salTimestamp;

    @Column(name = "SAL_USER_NO",nullable = false)
    @Basic(fetch = FetchType.EAGER)
    private Long salUserNo = 0L;

    @OneToMany(fetch = FetchType.EAGER,cascade = javax.persistence.CascadeType.ALL)
    @JoinColumn(name="SAE_SALE_ID",referencedColumnName = "SAL_ID" )
    private Set<SaleExtension> saleExtensionSet;

    @OneToMany(fetch = FetchType.EAGER,cascade = CascadeType.ALL)
    @JoinColumn(name="SSU_SALE_ID",referencedColumnName = "SAL_ID")
    private Set<SaleSKU> saleSKUSet;


    @Transient
    private AttributeExtendedEntityMap fieldMap;


    public Sale() {}

    public Sale(Long salMerchantNo, String salLoyaltyId, Long salLocation, Date salDate,Time salTime,Timestamp salTimestamp, Integer salType, Double salAmount, Integer salTxnChannel, Double salQuantity, String salPaymentReference,Double qty,Set<SaleSKU> saleSKUSet) {
        this.salMerchantNo = salMerchantNo;
        this.salLoyaltyId = salLoyaltyId;
        this.salLocation = salLocation;
        this.salDate = salDate;
        this.salTime= salTime;
        this.salTimestamp = salTimestamp;
        this.salType = salType;
        this.salAmount = salAmount;
        this.salTxnChannel = salTxnChannel;
        this.salQuantity = salQuantity;
        this.salPaymentReference = salPaymentReference;
        this.saleSKUSet = saleSKUSet;
        this.salQuantity = qty;
    }

    @PrePersist
    protected void populateFields() {

        // Create the Calendar object
        Calendar c = Calendar.getInstance();

        // Set the time
        c.setTime(salDate);


        // Set the timestamp
        if ( salTimestamp == null ){

            salTimestamp = new Timestamp(System.currentTimeMillis());

        }


        // Set the time if its null
        if ( salTime == null ) {

            salTime = new Time(salTimestamp.getTime());

        }

    }


    public Long getSalId() {
        return salId;
    }

    public void setSalId(Long salId) {
        this.salId = salId;
    }

    public Long getSalMerchantNo() {
        return salMerchantNo;
    }

    public void setSalMerchantNo(Long salMerchantNo) {
        this.salMerchantNo = salMerchantNo;
    }

    public String getSalLoyaltyId() {
        return salLoyaltyId;
    }

    public void setSalLoyaltyId(String salLoyaltyId) {
        this.salLoyaltyId = salLoyaltyId;
    }

    public Long getSalLocation() {
        return salLocation;
    }

    public void setSalLocation(Long salLocation) {
        this.salLocation = salLocation;
    }

    public Date getSalDate() {
        return salDate;
    }

    public void setSalDate(Date salDate) {
        this.salDate = salDate;
    }

    public Time getSalTime() {
        return salTime;
    }

    public void setSalTime(Time salTime) {
        this.salTime = salTime;
    }

    public Integer getSalType() {
        return salType;
    }

    public void setSalType(Integer salType) {
        this.salType = salType;
    }

    public Double getSalAmount() {
        return salAmount;
    }

    public void setSalAmount(Double salAmount) {
        this.salAmount = salAmount;
    }

    public Integer getSalCurrency() {
        return salCurrency;
    }

    public void setSalCurrency(Integer salCurrency) {
        this.salCurrency = salCurrency;
    }

    public Integer getSalPaymentMode() {
        return salPaymentMode;
    }

    public void setSalPaymentMode(Integer salPaymentMode) {
        this.salPaymentMode = salPaymentMode;
    }

    public Integer getSalTxnChannel() {
        return salTxnChannel;
    }

    public void setSalTxnChannel(Integer salTxnChannel) {
        this.salTxnChannel = salTxnChannel;
    }

    public Double getSalQuantity() {
        return salQuantity;
    }

    public void setSalQuantity(Double salQuantity) {
        this.salQuantity = salQuantity;
    }

    public String getSalPaymentReference() {
        return salPaymentReference;
    }

    public void setSalPaymentReference(String salPaymentReference) {
        this.salPaymentReference = salPaymentReference;
    }

    public Integer getSalStatus() {
        return salStatus;
    }

    public void setSalStatus(Integer salStatus) {
        this.salStatus = salStatus;
    }

    public Timestamp getSalTimestamp() {
        return salTimestamp;
    }

    public void setSalTimestamp(Timestamp salTimestamp) {
        this.salTimestamp = salTimestamp;
    }

    public Long getSalUserNo() {
        return salUserNo;
    }

    public void setSalUserNo(Long salUserNo) {
        this.salUserNo = salUserNo;
    }

    public Set<SaleExtension> getSaleExtensionSet() {
        return saleExtensionSet;
    }

    public void setSaleExtensionSet(Set<SaleExtension> saleExtensionSet) {
        this.saleExtensionSet = saleExtensionSet;
    }

    public AttributeExtendedEntityMap getFieldMap() {
        return fieldMap;
    }

    public void setFieldMap(AttributeExtendedEntityMap fieldMap) {
        this.fieldMap = fieldMap;
    }

    public Set<SaleSKU> getSaleSKUSet() {
        return saleSKUSet;
    }

    public void setSaleSKUSet(Set<SaleSKU> saleSKUSet) {
        this.saleSKUSet = saleSKUSet;
    }

    @Override
    public String toString() {
        return "Sale{" +
                "salId=" + salId +
                ", salMerchantNo=" + salMerchantNo +
                ", salLoyaltyId='" + salLoyaltyId + '\'' +
                ", salLocation=" + salLocation +
                ", salDate=" + salDate +
                ", salTime=" + salTime +
                ", salType=" + salType +
                ", salAmount=" + salAmount +
                ", salCurrency=" + salCurrency +
                ", salPaymentMode=" + salPaymentMode +
                ", salTxnChannel=" + salTxnChannel +
                ", salQuantity=" + salQuantity +
                ", salPaymentReference='" + salPaymentReference + '\'' +
                ", salStatus=" + salStatus +
                ", salTimestamp=" + salTimestamp +
                ", salUserNo=" + salUserNo +
                ", saleExtensionSet=" + saleExtensionSet +
                '}';
    }
}
