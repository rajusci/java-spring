package com.inspirenetz.api.test.core.builder;

import com.inspirenetz.api.core.dictionary.*;
import com.inspirenetz.api.core.domain.CustomerSegment;

import java.sql.Date;

/**
 * Created by sandheepgr on 29/5/14.
 */
public class CustomerSegmentBuilder {
    private Long csgSegmentId;
    private String csgSegmentName = "";
    private String csgDescription = "";
    private Long csgMerchantNo;
    private Date csgCreationDate;
    private int csgSegmentType = CustomerSegmentType.STATIC;
    private int csgCriteriaType = CustomerSegmentCriteriaType.OVERALL_STATS;
    private int csgSegDefType = CustomerSegmentDefenitionType.USER_DEFINED;
    private Integer csgSegModuleId = 0;
    private Integer csgYear;
    private Integer csgQuarter;
    private Integer csgMonth;
    private int csgAmountCompType = CustomerSegmentComparisonType.MORE_THAN;
    private Integer csgAmountCompValue1 = 0;
    private Integer csgAmountCompValue2 = 0;
    private int csgVisitCompType = CustomerSegmentComparisonType.MORE_THAN;
    private Integer csgVisitCompValue1 = 0;
    private Integer csgVisitCompValue2 = 0;
    private int csgQtyCompType = CustomerSegmentComparisonType.MORE_THAN;
    private Integer csgQtyCompValue1= 0;
    private Integer csgQtyCompValue2 = 0;
    private int csgGenderEnabledInd = IndicatorStatus.NO;
    private String csgGenderValues;
    private int csgProfessionEnabledInd = IndicatorStatus.NO;
    private String csgProfessionValues;
    private int csgAgegroupEnabledInd = IndicatorStatus.NO;
    private String csgAgegroupValues;
    private int csgIncomerangeEnabledInd = IndicatorStatus.NO;
    private String csgIncomerangeValues;
    private int csgFamilystatusEnabledInd = IndicatorStatus.NO;
    private String csgFamilystatusValues;
    private int csgChildBdayEnabled = IndicatorStatus.NO;
    private int csgChildBdayInterval;
    private Long csgLocation = 0L;
    private Long csgCustomerCount = 0L;
    private Double csgGenerationPercent = 0.0;
    private java.util.Date createdAt;
    private String createdBy;
    private java.util.Date updatedAt;
    private String updatedBy;
    private Integer csgAutoUpgradeSegment = IndicatorStatus.NO;

    private CustomerSegmentBuilder() {
    }

    public static CustomerSegmentBuilder aCustomerSegment() {
        return new CustomerSegmentBuilder();
    }

    public CustomerSegmentBuilder withCsgSegmentId(Long csgSegmentId) {
        this.csgSegmentId = csgSegmentId;
        return this;
    }

    public CustomerSegmentBuilder withCsgSegmentName(String csgSegmentName) {
        this.csgSegmentName = csgSegmentName;
        return this;
    }

    public CustomerSegmentBuilder withCsgDescription(String csgDescription) {
        this.csgDescription = csgDescription;
        return this;
    }

    public CustomerSegmentBuilder withCsgMerchantNo(Long csgMerchantNo) {
        this.csgMerchantNo = csgMerchantNo;
        return this;
    }

    public CustomerSegmentBuilder withCsgCreationDate(Date csgCreationDate) {
        this.csgCreationDate = csgCreationDate;
        return this;
    }

    public CustomerSegmentBuilder withCsgSegmentType(int csgSegmentType) {
        this.csgSegmentType = csgSegmentType;
        return this;
    }

    public CustomerSegmentBuilder withCsgCriteriaType(int csgCriteriaType) {
        this.csgCriteriaType = csgCriteriaType;
        return this;
    }

    public CustomerSegmentBuilder withCsgSegDefType(int csgSegDefType) {
        this.csgSegDefType = csgSegDefType;
        return this;
    }

    public CustomerSegmentBuilder withCsgSegModuleId(Integer csgSegModuleId) {
        this.csgSegModuleId = csgSegModuleId;
        return this;
    }

    public CustomerSegmentBuilder withCsgYear(Integer csgYear) {
        this.csgYear = csgYear;
        return this;
    }

    public CustomerSegmentBuilder withCsgQuarter(Integer csgQuarter) {
        this.csgQuarter = csgQuarter;
        return this;
    }

    public CustomerSegmentBuilder withCsgMonth(Integer csgMonth) {
        this.csgMonth = csgMonth;
        return this;
    }

    public CustomerSegmentBuilder withCsgAmountCompType(int csgAmountCompType) {
        this.csgAmountCompType = csgAmountCompType;
        return this;
    }

    public CustomerSegmentBuilder withCsgAmountCompValue1(Integer csgAmountCompValue1) {
        this.csgAmountCompValue1 = csgAmountCompValue1;
        return this;
    }

    public CustomerSegmentBuilder withCsgAmountCompValue2(Integer csgAmountCompValue2) {
        this.csgAmountCompValue2 = csgAmountCompValue2;
        return this;
    }

    public CustomerSegmentBuilder withCsgVisitCompType(int csgVisitCompType) {
        this.csgVisitCompType = csgVisitCompType;
        return this;
    }

    public CustomerSegmentBuilder withCsgVisitCompValue1(Integer csgVisitCompValue1) {
        this.csgVisitCompValue1 = csgVisitCompValue1;
        return this;
    }

    public CustomerSegmentBuilder withCsgVisitCompValue2(Integer csgVisitCompValue2) {
        this.csgVisitCompValue2 = csgVisitCompValue2;
        return this;
    }

    public CustomerSegmentBuilder withCsgQtyCompType(int csgQtyCompType) {
        this.csgQtyCompType = csgQtyCompType;
        return this;
    }

    public CustomerSegmentBuilder withCsgQtyCompValue1(Integer csgQtyCompValue1) {
        this.csgQtyCompValue1 = csgQtyCompValue1;
        return this;
    }

    public CustomerSegmentBuilder withCsgQtyCompValue2(Integer csgQtyCompValue2) {
        this.csgQtyCompValue2 = csgQtyCompValue2;
        return this;
    }

    public CustomerSegmentBuilder withCsgGenderEnabledInd(int csgGenderEnabledInd) {
        this.csgGenderEnabledInd = csgGenderEnabledInd;
        return this;
    }

    public CustomerSegmentBuilder withCsgGenderValues(String csgGenderValues) {
        this.csgGenderValues = csgGenderValues;
        return this;
    }

    public CustomerSegmentBuilder withCsgProfessionEnabledInd(int csgProfessionEnabledInd) {
        this.csgProfessionEnabledInd = csgProfessionEnabledInd;
        return this;
    }

    public CustomerSegmentBuilder withCsgProfessionValues(String csgProfessionValues) {
        this.csgProfessionValues = csgProfessionValues;
        return this;
    }

    public CustomerSegmentBuilder withCsgAgegroupEnabledInd(int csgAgegroupEnabledInd) {
        this.csgAgegroupEnabledInd = csgAgegroupEnabledInd;
        return this;
    }

    public CustomerSegmentBuilder withCsgAgegroupValues(String csgAgegroupValues) {
        this.csgAgegroupValues = csgAgegroupValues;
        return this;
    }

    public CustomerSegmentBuilder withCsgIncomerangeEnabledInd(int csgIncomerangeEnabledInd) {
        this.csgIncomerangeEnabledInd = csgIncomerangeEnabledInd;
        return this;
    }

    public CustomerSegmentBuilder withCsgIncomerangeValues(String csgIncomerangeValues) {
        this.csgIncomerangeValues = csgIncomerangeValues;
        return this;
    }

    public CustomerSegmentBuilder withCsgFamilystatusEnabledInd(int csgFamilystatusEnabledInd) {
        this.csgFamilystatusEnabledInd = csgFamilystatusEnabledInd;
        return this;
    }

    public CustomerSegmentBuilder withCsgFamilystatusValues(String csgFamilystatusValues) {
        this.csgFamilystatusValues = csgFamilystatusValues;
        return this;
    }

    public CustomerSegmentBuilder withCsgChildBdayEnabled(int csgChildBdayEnabled) {
        this.csgChildBdayEnabled = csgChildBdayEnabled;
        return this;
    }

    public CustomerSegmentBuilder withCsgChildBdayInterval(int csgChildBdayInterval) {
        this.csgChildBdayInterval = csgChildBdayInterval;
        return this;
    }

    public CustomerSegmentBuilder withCsgLocation(Long csgLocation) {
        this.csgLocation = csgLocation;
        return this;
    }

    public CustomerSegmentBuilder withCsgCustomerCount(Long csgCustomerCount) {
        this.csgCustomerCount = csgCustomerCount;
        return this;
    }

    public CustomerSegmentBuilder withCsgGenerationPercent(Double csgGenerationPercent) {
        this.csgGenerationPercent = csgGenerationPercent;
        return this;
    }

    public CustomerSegmentBuilder withCreatedAt(java.util.Date createdAt) {
        this.createdAt = createdAt;
        return this;
    }

    public CustomerSegmentBuilder withCreatedBy(String createdBy) {
        this.createdBy = createdBy;
        return this;
    }

    public CustomerSegmentBuilder withUpdatedAt(java.util.Date updatedAt) {
        this.updatedAt = updatedAt;
        return this;
    }

    public CustomerSegmentBuilder withUpdatedBy(String updatedBy) {
        this.updatedBy = updatedBy;
        return this;
    }


    public CustomerSegmentBuilder withCsgAutoUpgradeSegment(Integer csgAutoUpgradeSegment) {

        this.csgAutoUpgradeSegment = csgAutoUpgradeSegment;
        return this;
    }

    public CustomerSegment build() {
        CustomerSegment customerSegment = new CustomerSegment();
        customerSegment.setCsgSegmentId(csgSegmentId);
        customerSegment.setCsgSegmentName(csgSegmentName);
        customerSegment.setCsgDescription(csgDescription);
        customerSegment.setCsgMerchantNo(csgMerchantNo);
        customerSegment.setCsgCreationDate(csgCreationDate);
        customerSegment.setCsgSegmentType(csgSegmentType);
        customerSegment.setCsgCriteriaType(csgCriteriaType);
        customerSegment.setCsgSegDefType(csgSegDefType);
        customerSegment.setCsgSegModuleId(csgSegModuleId);
        customerSegment.setCsgYear(csgYear);
        customerSegment.setCsgQuarter(csgQuarter);
        customerSegment.setCsgMonth(csgMonth);
        customerSegment.setCsgAmountCompType(csgAmountCompType);
        customerSegment.setCsgAmountCompValue1(csgAmountCompValue1);
        customerSegment.setCsgAmountCompValue2(csgAmountCompValue2);
        customerSegment.setCsgVisitCompType(csgVisitCompType);
        customerSegment.setCsgVisitCompValue1(csgVisitCompValue1);
        customerSegment.setCsgVisitCompValue2(csgVisitCompValue2);
        customerSegment.setCsgQtyCompType(csgQtyCompType);
        customerSegment.setCsgQtyCompValue1(csgQtyCompValue1);
        customerSegment.setCsgQtyCompValue2(csgQtyCompValue2);
        customerSegment.setCsgGenderEnabledInd(csgGenderEnabledInd);
        customerSegment.setCsgGenderValues(csgGenderValues);
        customerSegment.setCsgProfessionEnabledInd(csgProfessionEnabledInd);
        customerSegment.setCsgProfessionValues(csgProfessionValues);
        customerSegment.setCsgAgegroupEnabledInd(csgAgegroupEnabledInd);
        customerSegment.setCsgAgegroupValues(csgAgegroupValues);
        customerSegment.setCsgIncomerangeEnabledInd(csgIncomerangeEnabledInd);
        customerSegment.setCsgIncomerangeValues(csgIncomerangeValues);
        customerSegment.setCsgFamilystatusEnabledInd(csgFamilystatusEnabledInd);
        customerSegment.setCsgFamilystatusValues(csgFamilystatusValues);
        customerSegment.setCsgChildBdayEnabled(csgChildBdayEnabled);
        customerSegment.setCsgChildBdayInterval(csgChildBdayInterval);
        customerSegment.setCsgLocation(csgLocation);
        customerSegment.setCsgCustomerCount(csgCustomerCount);
        customerSegment.setCsgGenerationPercent(csgGenerationPercent);
        customerSegment.setCreatedAt(createdAt);
        customerSegment.setCreatedBy(createdBy);
        customerSegment.setUpdatedAt(updatedAt);
        customerSegment.setUpdatedBy(updatedBy);
        customerSegment.setCsgAutoUpgradeSegment(csgAutoUpgradeSegment);
        return customerSegment;
    }
}
