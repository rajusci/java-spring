package com.inspirenetz.api.core.domain;

import com.inspirenetz.api.core.dictionary.*;
import org.hibernate.validator.constraints.NotEmpty;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.sql.Date;

/**
 * Created by sandheepgr on 29/3/14.
 */
@Entity
@Table(name = "CUSTOMER_SEGMENTS")
public class CustomerSegment extends AuditedEntity {

    @Id
    @Column(name = "CSG_SEGMENT_ID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long csgSegmentId;

    @Basic(fetch = FetchType.EAGER)
    @Column(name = "CSG_SEGMENT_NAME")
    @NotNull(message="{customersegment.csgsegmentname.notnull}")
    @NotEmpty(message="{customersegment.csgsegmentname.notempty}")
    @Size(max=200,message="{customersegment.csgsegmentname.size}")
    private String csgSegmentName = "";

    @Basic(fetch = FetchType.EAGER)
    @Column(name = "CSG_DESCRIPTION")
    @Size(max=300,message="{customersegment.csgdescription.size}")
    private String csgDescription = "";

    @Basic(fetch = FetchType.EAGER)
    @Column(name = "CSG_MERCHANT_NO")
    private Long csgMerchantNo;

    @Basic(fetch = FetchType.EAGER)
    @Column(name = "CSG_CREATION_DATE")
    private Date csgCreationDate;

    @Basic(fetch = FetchType.EAGER)
    @Column(name = "CSG_SEGMENT_TYPE")
    private int csgSegmentType = CustomerSegmentType.STATIC;

    @Basic(fetch = FetchType.EAGER)
    @Column(name = "CSG_CRITERIA_TYPE")
    private int csgCriteriaType = CustomerSegmentCriteriaType.OVERALL_STATS;

    @Basic(fetch = FetchType.EAGER)
    @Column(name = "CSG_SEG_DEF_TYPE")
    private int csgSegDefType = CustomerSegmentDefenitionType.USER_DEFINED;

    @Basic(fetch = FetchType.EAGER)
    @Column(name = "CSG_SEG_MODULE_ID")
    private Integer csgSegModuleId = 0;

    @Basic(fetch = FetchType.EAGER)
    @Column(name = "CSG_YEAR")
    private Integer csgYear;

    @Basic(fetch = FetchType.EAGER)
    @Column(name = "CSG_QUARTER")
    private Integer csgQuarter;

    @Basic(fetch = FetchType.EAGER)
    @Column(name = "CSG_MONTH")
    private Integer csgMonth;

    @Basic(fetch = FetchType.EAGER)
    @Column(name = "CSG_AMOUNT_COMP_TYPE")
    private Integer csgAmountCompType = CustomerSegmentComparisonType.MORE_THAN;

    @Basic(fetch = FetchType.EAGER)
    @Column(name = "CSG_AMOUNT_COMP_VALUE1")
    private Integer csgAmountCompValue1 = 0;

    @Basic(fetch = FetchType.EAGER)
    @Column(name = "CSG_AMOUNT_COMP_VALUE2")
    private Integer csgAmountCompValue2 = 0;

    @Basic(fetch = FetchType.EAGER)
    @Column(name = "CSG_VISIT_COMP_TYPE")
    private Integer csgVisitCompType = CustomerSegmentComparisonType.MORE_THAN;

    @Basic(fetch = FetchType.EAGER)
    @Column(name = "CSG_VISIT_COMP_VALUE1")
    private Integer csgVisitCompValue1 = 0;

    @Basic(fetch = FetchType.EAGER)
    @Column(name = "CSG_VISIT_COMP_VALUE2")
    private Integer csgVisitCompValue2 = 0;

    @Basic(fetch = FetchType.EAGER)
    @Column(name = "CSG_QTY_COMP_TYPE")
    private Integer csgQtyCompType = CustomerSegmentComparisonType.MORE_THAN;

    @Basic(fetch = FetchType.EAGER)
    @Column(name = "CSG_QTY_COMP_VALUE1")
    private Integer csgQtyCompValue1= 0;

    @Basic(fetch = FetchType.EAGER)
    @Column(name = "CSG_QTY_COMP_VALUE2")
    private Integer csgQtyCompValue2 = 0;

    @Basic(fetch = FetchType.EAGER)
    @Column(name = "CSG_GENDER_ENABLED_IND")
    private int csgGenderEnabledInd = IndicatorStatus.NO;

    @Basic(fetch = FetchType.EAGER)
    @Column(name = "CSG_GENDER_VALUES")
    private String csgGenderValues;

    @Basic(fetch = FetchType.EAGER)
    @Column(name = "CSG_PROFESSION_ENABLED_IND")
    private int csgProfessionEnabledInd = IndicatorStatus.NO;

    @Basic(fetch = FetchType.EAGER)
    @Column(name = "CSG_PROFESSION_VALUES")
    private String csgProfessionValues;

    @Basic(fetch = FetchType.EAGER)
    @Column(name = "CSG_AGEGROUP_ENABLED_IND")
    private int csgAgegroupEnabledInd = IndicatorStatus.NO;

    @Basic(fetch = FetchType.EAGER)
    @Column(name = "CSG_AGEGROUP_VALUES")
    private String csgAgegroupValues;

    @Basic(fetch = FetchType.EAGER)
    @Column(name = "CSG_INCOMERANGE_ENABLED_IND")
    private int csgIncomerangeEnabledInd = IndicatorStatus.NO;

    @Basic(fetch = FetchType.EAGER)
    @Column(name = "CSG_INCOMERANGE_VALUES")
    private String csgIncomerangeValues;

    @Basic(fetch = FetchType.EAGER)
    @Column(name = "CSG_FAMILYSTATUS_ENABLED_IND")
    private int csgFamilystatusEnabledInd = IndicatorStatus.NO;

    @Basic(fetch = FetchType.EAGER)
    @Column(name = "CSG_FAMILYSTATUS_VALUES")
    private String csgFamilystatusValues;

    @Basic(fetch = FetchType.EAGER)
    @Column(name = "CSG_CHILD_BDAY_ENABLED")
    private int csgChildBdayEnabled = IndicatorStatus.NO;

    @Basic(fetch = FetchType.EAGER)
    @Column(name = "CSG_CHILD_BDAY_INTERVAL")
    private int csgChildBdayInterval;

    @Basic(fetch = FetchType.EAGER)
    @Column(name = "CSG_LOCATION")
    private Long csgLocation = 0L;

    @Basic(fetch = FetchType.EAGER)
    @Column(name = "CSG_CUSTOMER_COUNT")
    private Long csgCustomerCount = 0L;

    @Basic(fetch = FetchType.EAGER)
    @Column(name = "CSG_GENERATION_PERCENT")
    private Double csgGenerationPercent = 0.0;

    @Basic(fetch = FetchType.EAGER)
    @Column(name = "CSG_AUTO_UPGRADE_SEGMENT")
    private Integer csgAutoUpgradeSegment = IndicatorStatus.NO ;

    @Transient
    private Integer memberCount;

    @PrePersist
    private void prePersist() {

        // Set the creation date
        csgCreationDate = new Date(new java.util.Date().getTime());

    }




    public Long getCsgCustomerCount() {
        return csgCustomerCount;
    }

    public void setCsgCustomerCount(Long csgCustomerCount) {
        this.csgCustomerCount = csgCustomerCount;
    }

    public Double getCsgGenerationPercent() {
        return csgGenerationPercent;
    }

    public void setCsgGenerationPercent(Double csgGenerationPercent) {
        this.csgGenerationPercent = csgGenerationPercent;
    }

    public Long getCsgLocation() {
        return csgLocation;
    }

    public void setCsgLocation(Long csgLocation) {
        this.csgLocation = csgLocation;
    }

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

    public Integer getCsgAmountCompType() {
        return csgAmountCompType;
    }

    public void setCsgAmountCompType(Integer csgAmountCompType) {
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

    public Integer getCsgVisitCompType() {
        return csgVisitCompType;
    }

    public void setCsgVisitCompType(Integer csgVisitCompType) {
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

    public Integer getCsgQtyCompType() {
        return csgQtyCompType;
    }

    public void setCsgQtyCompType(Integer csgQtyCompType) {
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

    public Integer getCsgAutoUpgradeSegment() {
        return csgAutoUpgradeSegment;
    }

    public void setCsgAutoUpgradeSegment(int csgAutoUpgradeSegment) {
        this.csgAutoUpgradeSegment = csgAutoUpgradeSegment;
    }

    public void setCsgAutoUpgradeSegment(Integer csgAutoUpgradeSegment) {
        this.csgAutoUpgradeSegment = csgAutoUpgradeSegment;
    }

    public Integer getMemberCount() {
        return memberCount;
    }

    public void setMemberCount(Integer memberCount) {
        this.memberCount = memberCount;
    }

    @Override
    public String toString() {
        return "CustomerSegment{" +
                "csgSegmentId=" + csgSegmentId +
                ", csgSegmentName='" + csgSegmentName + '\'' +
                ", csgDescription='" + csgDescription + '\'' +
                ", csgMerchantNo=" + csgMerchantNo +
                ", csgCreationDate=" + csgCreationDate +
                ", csgSegmentType=" + csgSegmentType +
                ", csgCriteriaType=" + csgCriteriaType +
                ", csgSegDefType=" + csgSegDefType +
                ", csgSegModuleId=" + csgSegModuleId +
                ", csgYear=" + csgYear +
                ", csgQuarter=" + csgQuarter +
                ", csgMonth=" + csgMonth +
                ", csgAmountCompType=" + csgAmountCompType +
                ", csgAmountCompValue1=" + csgAmountCompValue1 +
                ", csgAmountCompValue2=" + csgAmountCompValue2 +
                ", csgVisitCompType=" + csgVisitCompType +
                ", csgVisitCompValue1=" + csgVisitCompValue1 +
                ", csgVisitCompValue2=" + csgVisitCompValue2 +
                ", csgQtyCompType=" + csgQtyCompType +
                ", csgQtyCompValue1=" + csgQtyCompValue1 +
                ", csgQtyCompValue2=" + csgQtyCompValue2 +
                ", csgGenderEnabledInd=" + csgGenderEnabledInd +
                ", csgGenderValues='" + csgGenderValues + '\'' +
                ", csgProfessionEnabledInd=" + csgProfessionEnabledInd +
                ", csgProfessionValues='" + csgProfessionValues + '\'' +
                ", csgAgegroupEnabledInd=" + csgAgegroupEnabledInd +
                ", csgAgegroupValues='" + csgAgegroupValues + '\'' +
                ", csgIncomerangeEnabledInd=" + csgIncomerangeEnabledInd +
                ", csgIncomerangeValues='" + csgIncomerangeValues + '\'' +
                ", csgFamilystatusEnabledInd=" + csgFamilystatusEnabledInd +
                ", csgFamilystatusValues='" + csgFamilystatusValues + '\'' +
                ", csgChildBdayEnabled=" + csgChildBdayEnabled +
                ", csgChildBdayInterval=" + csgChildBdayInterval +
                ", csgLocation=" + csgLocation +
                ", csgCustomerCount=" + csgCustomerCount +
                ", csgGenerationPercent=" + csgGenerationPercent +
                ", csgAutoUpgradeSegment=" + csgAutoUpgradeSegment +
                ", memberCount=" + memberCount +
                '}';
    }
}
