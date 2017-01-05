package com.inspirenetz.api.rest.resource;

import com.inspirenetz.api.core.dictionary.*;

import java.sql.Date;

/**
 * Created by sandheepgr on 19/5/14.
 */
public class CustomerSegmentResource extends  BaseResource {


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

    private int csgAmountCompType;

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

    private int memberCount;

    private Long csgCustomerCount;


    public Long getCsgSegmentId() {
        return csgSegmentId;
    }

    public void setCsgSegmentId(Long csgSegmentId) {
        this.csgSegmentId = csgSegmentId;
    }

    public String getCsgSegmentName() {
        return csgSegmentName;
    }

    public void setCsgSegmentName(String csgSegmentName) {
        this.csgSegmentName = csgSegmentName;
    }

    public String getCsgDescription() {
        return csgDescription;
    }

    public void setCsgDescription(String csgDescription) {
        this.csgDescription = csgDescription;
    }

    public Long getCsgMerchantNo() {
        return csgMerchantNo;
    }

    public void setCsgMerchantNo(Long csgMerchantNo) {
        this.csgMerchantNo = csgMerchantNo;
    }

    public Date getCsgCreationDate() {
        return csgCreationDate;
    }

    public void setCsgCreationDate(Date csgCreationDate) {
        this.csgCreationDate = csgCreationDate;
    }

    public int getCsgSegmentType() {
        return csgSegmentType;
    }

    public void setCsgSegmentType(int csgSegmentType) {
        this.csgSegmentType = csgSegmentType;
    }

    public int getCsgCriteriaType() {
        return csgCriteriaType;
    }

    public void setCsgCriteriaType(int csgCriteriaType) {
        this.csgCriteriaType = csgCriteriaType;
    }

    public int getCsgSegDefType() {
        return csgSegDefType;
    }

    public void setCsgSegDefType(int csgSegDefType) {
        this.csgSegDefType = csgSegDefType;
    }

    public Integer getCsgSegModuleId() {
        return csgSegModuleId;
    }

    public void setCsgSegModuleId(Integer csgSegModuleId) {
        this.csgSegModuleId = csgSegModuleId;
    }

    public Integer getCsgYear() {
        return csgYear;
    }

    public void setCsgYear(Integer csgYear) {
        this.csgYear = csgYear;
    }

    public Integer getCsgQuarter() {
        return csgQuarter;
    }

    public void setCsgQuarter(Integer csgQuarter) {
        this.csgQuarter = csgQuarter;
    }

    public Integer getCsgMonth() {
        return csgMonth;
    }

    public void setCsgMonth(Integer csgMonth) {
        this.csgMonth = csgMonth;
    }

    public int getCsgAmountCompType() {
        return csgAmountCompType;
    }

    public void setCsgAmountCompType(int csgAmountCompType) {
        this.csgAmountCompType = csgAmountCompType;
    }

    public Integer getCsgAmountCompValue1() {
        return csgAmountCompValue1;
    }

    public void setCsgAmountCompValue1(Integer csgAmountCompValue1) {
        this.csgAmountCompValue1 = csgAmountCompValue1;
    }

    public Integer getCsgAmountCompValue2() {
        return csgAmountCompValue2;
    }

    public void setCsgAmountCompValue2(Integer csgAmountCompValue2) {
        this.csgAmountCompValue2 = csgAmountCompValue2;
    }

    public int getCsgVisitCompType() {
        return csgVisitCompType;
    }

    public void setCsgVisitCompType(int csgVisitCompType) {
        this.csgVisitCompType = csgVisitCompType;
    }

    public Integer getCsgVisitCompValue1() {
        return csgVisitCompValue1;
    }

    public void setCsgVisitCompValue1(Integer csgVisitCompValue1) {
        this.csgVisitCompValue1 = csgVisitCompValue1;
    }

    public Integer getCsgVisitCompValue2() {
        return csgVisitCompValue2;
    }

    public void setCsgVisitCompValue2(Integer csgVisitCompValue2) {
        this.csgVisitCompValue2 = csgVisitCompValue2;
    }

    public int getCsgQtyCompType() {
        return csgQtyCompType;
    }

    public void setCsgQtyCompType(int csgQtyCompType) {
        this.csgQtyCompType = csgQtyCompType;
    }

    public Integer getCsgQtyCompValue1() {
        return csgQtyCompValue1;
    }

    public void setCsgQtyCompValue1(Integer csgQtyCompValue1) {
        this.csgQtyCompValue1 = csgQtyCompValue1;
    }

    public Integer getCsgQtyCompValue2() {
        return csgQtyCompValue2;
    }

    public void setCsgQtyCompValue2(Integer csgQtyCompValue2) {
        this.csgQtyCompValue2 = csgQtyCompValue2;
    }

    public int getCsgGenderEnabledInd() {
        return csgGenderEnabledInd;
    }

    public void setCsgGenderEnabledInd(int csgGenderEnabledInd) {
        this.csgGenderEnabledInd = csgGenderEnabledInd;
    }

    public String getCsgGenderValues() {
        return csgGenderValues;
    }

    public void setCsgGenderValues(String csgGenderValues) {
        this.csgGenderValues = csgGenderValues;
    }

    public int getCsgProfessionEnabledInd() {
        return csgProfessionEnabledInd;
    }

    public void setCsgProfessionEnabledInd(int csgProfessionEnabledInd) {
        this.csgProfessionEnabledInd = csgProfessionEnabledInd;
    }

    public String getCsgProfessionValues() {
        return csgProfessionValues;
    }

    public void setCsgProfessionValues(String csgProfessionValues) {
        this.csgProfessionValues = csgProfessionValues;
    }

    public int getCsgAgegroupEnabledInd() {
        return csgAgegroupEnabledInd;
    }

    public void setCsgAgegroupEnabledInd(int csgAgegroupEnabledInd) {
        this.csgAgegroupEnabledInd = csgAgegroupEnabledInd;
    }

    public String getCsgAgegroupValues() {
        return csgAgegroupValues;
    }

    public void setCsgAgegroupValues(String csgAgegroupValues) {
        this.csgAgegroupValues = csgAgegroupValues;
    }

    public int getCsgIncomerangeEnabledInd() {
        return csgIncomerangeEnabledInd;
    }

    public void setCsgIncomerangeEnabledInd(int csgIncomerangeEnabledInd) {
        this.csgIncomerangeEnabledInd = csgIncomerangeEnabledInd;
    }

    public String getCsgIncomerangeValues() {
        return csgIncomerangeValues;
    }

    public void setCsgIncomerangeValues(String csgIncomerangeValues) {
        this.csgIncomerangeValues = csgIncomerangeValues;
    }

    public int getCsgFamilystatusEnabledInd() {
        return csgFamilystatusEnabledInd;
    }

    public void setCsgFamilystatusEnabledInd(int csgFamilystatusEnabledInd) {
        this.csgFamilystatusEnabledInd = csgFamilystatusEnabledInd;
    }

    public String getCsgFamilystatusValues() {
        return csgFamilystatusValues;
    }

    public void setCsgFamilystatusValues(String csgFamilystatusValues) {
        this.csgFamilystatusValues = csgFamilystatusValues;
    }

    public int getCsgChildBdayEnabled() {
        return csgChildBdayEnabled;
    }

    public void setCsgChildBdayEnabled(int csgChildBdayEnabled) {
        this.csgChildBdayEnabled = csgChildBdayEnabled;
    }

    public int getCsgChildBdayInterval() {
        return csgChildBdayInterval;
    }

    public void setCsgChildBdayInterval(int csgChildBdayInterval) {
        this.csgChildBdayInterval = csgChildBdayInterval;
    }

    public int getMemberCount() {
        return memberCount;
    }

    public void setMemberCount(int memberCount) {
        this.memberCount = memberCount;
    }

    public Long getCsgCustomerCount() {
        return csgCustomerCount;
    }

    public void setCsgCustomerCount(Long csgCustomerCount) {
        this.csgCustomerCount = csgCustomerCount;
    }
}
