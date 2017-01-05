package com.inspirenetz.api.core.domain;

import com.inspirenetz.api.core.dictionary.*;
import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotEmpty;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.sql.Date;
import java.util.Set;

/**
 * Created by saneesh-ci on 25/9/14.
 */
@Entity
@Table(name="REDEMPTION_MERCHANTS")
public class RedemptionMerchant extends AuditedEntity {


    @Column(name = "REM_NO",nullable = false)
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long remNo;

    @Column(name = "REM_NAME",nullable = false)
    @Basic(fetch = FetchType.EAGER)
    private String remName;

    @Column(name = "REM_CATEGORY",nullable = false)
    @Basic(fetch = FetchType.EAGER)
    private Long remCategory;

    @Column(name = "REM_ADDRESS",nullable = false)
    @Basic(fetch = FetchType.EAGER)
    private String remAddress;

    @Column(name = "REM_CONTACT_PERSON",nullable = true)
    @Basic(fetch = FetchType.EAGER)
    private String remContactPerson = "";

    @Column(name = "REM_CONTACT_EMAIL",nullable = true)
    @Basic(fetch = FetchType.EAGER)
    private String remContactEmail;

    @Column(name = "REM_CONTACT_MOBILE",nullable = true)
    @Basic(fetch = FetchType.EAGER)
    private String remContactMobile;

    @Column(name = "REM_VOUCHER_PREFIX",nullable = true)
    @Basic(fetch = FetchType.EAGER)
    private String remVoucherPrefix;

    @Column(name = "REM_STATUS",nullable = false)
    @Basic(fetch = FetchType.EAGER)
    private int remStatus = RedemptionMerchantStatus.ACCOUNT_ACTIVE;

    @Column(name = "REM_SETTLEMENT_TYPE",nullable = true)
    @Basic(fetch = FetchType.EAGER)
    private Integer remSettlementType = MerchantSettlementType.LOAD_WALLET;

    @Column(name = "REM_CODE",nullable = true)
    @Basic(fetch = FetchType.EAGER)
    private String remCode;

    @Column(name = "REM_TYPE",nullable = true)
    @Basic(fetch = FetchType.EAGER)
    private Integer remType;

    @Column(name = "REM_SETTLEMENT_LEVEL",nullable = true)
    @Basic(fetch = FetchType.EAGER)
    private Integer remSettlementLevel;

    @Column(name = "REM_VEN_URL",nullable = true)
    @Basic(fetch = FetchType.EAGER)
    private String remVenUrl;

    @Column(name = "REM_SETTLEMENT_FREQUENCY",nullable = true)
    @Basic(fetch = FetchType.EAGER)
    private Integer remSettlementFrequency = MerchantSettlementFrequency.WEEKLY;

    @Column(name = "REM_BANK_NAME",nullable = true)
    @Basic(fetch = FetchType.EAGER)
    private String remBankName = "";

    @Column(name = "REM_BENEFICIARY_NAME",nullable = true)
    @Basic(fetch = FetchType.EAGER)
    private String remBeneficiaryName = "";

    @Column(name = "REM_BRANCH",nullable = true)
    @Basic(fetch = FetchType.EAGER)
    private String remBranch = "";

    @Column(name = "REM_BRANCH_ADDRESS",nullable = true)
    @Basic(fetch = FetchType.EAGER)
    private String remBranchAddress = "";

    @Column(name = "REM_ACCOUNT_NO",nullable = true)
    @Basic(fetch = FetchType.EAGER)
    private String remAccountNo = "";

    @Column(name = "REM_ACCOUNT_TYPE",nullable = true)
    @Basic(fetch = FetchType.EAGER)
    private String remAccountType = "";

    @Column(name = "REM_SWIFT_CODE",nullable = true)
    @Basic(fetch = FetchType.EAGER)
    private String remSwiftCode = "";

    @Column(name = "REM_IBAN_NO",nullable = true)
    @Basic(fetch = FetchType.EAGER)
    private String remIbanNo = "";

    @Column(name = "REM_SUCCESS_REDIRECT",nullable = true)
    @Basic(fetch = FetchType.EAGER)
    private String successRedirect;

    @Column(name = "REM_FAILURE_REDIRECT",nullable = true)
    @Basic(fetch = FetchType.EAGER)
    private String failureRedirect;

    @Column(name = "REM_SECRET_KEY",nullable = true)
    @Basic(fetch = FetchType.EAGER)
    private String remSecretKey = "";

    @OneToMany(fetch = FetchType.EAGER,cascade = CascadeType.ALL)
    @JoinColumn(name="RML_MER_NO")
    public Set<RedemptionMerchantLocation> redemptionMerchantLocations;




    public Long getRemNo() {
        return remNo;
    }

    public void setRemNo(Long remNo) {
        this.remNo = remNo;
    }

    public String getRemName() {
        return remName;
    }

    public void setRemName(String remName) {
        this.remName = remName;
    }

    public Long getRemCategory() {
        return remCategory;
    }

    public void setRemCategory(Long remCategory) {
        this.remCategory = remCategory;
    }

    public String getRemAddress() {
        return remAddress;
    }

    public void setRemAddress(String remAddress) {
        this.remAddress = remAddress;
    }

    public String getRemContactPerson() {
        return remContactPerson;
    }

    public void setRemContactPerson(String remContactPerson) {
        this.remContactPerson = remContactPerson;
    }

    public String getRemContactEmail() {
        return remContactEmail;
    }

    public void setRemContactEmail(String remContactEmail) {
        this.remContactEmail = remContactEmail;
    }

    public String getRemContactMobile() {
        return remContactMobile;
    }

    public void setRemContactMobile(String remContactMobile) {
        this.remContactMobile = remContactMobile;
    }

    public int getRemStatus() {
        return remStatus;
    }

    public void setRemStatus(int remStatus) {
        this.remStatus = remStatus;
    }

    public Set<RedemptionMerchantLocation> getRedemptionMerchantLocations() {
        return redemptionMerchantLocations;
    }

    public void setRedemptionMerchantLocations(Set<RedemptionMerchantLocation> redemptionMerchantLocations) {
        this.redemptionMerchantLocations = redemptionMerchantLocations;
    }

    public String getRemVoucherPrefix() {
        return remVoucherPrefix;
    }

    public void setRemVoucherPrefix(String remVoucherPrefix) {
        this.remVoucherPrefix = remVoucherPrefix;
    }

    public Integer getRemSettlementType() {
        return remSettlementType;
    }

    public void setRemSettlementType(Integer remSettlementType) {
        this.remSettlementType = remSettlementType;
    }

    public String getRemCode() {
        return remCode;
    }

    public void setRemCode(String remCode) {
        this.remCode = remCode;
    }

    public Integer getRemType() {
        return remType;
    }

    public void setRemType(Integer remType) {
        this.remType = remType;
    }

    public Integer getRemSettlementLevel() {
        return remSettlementLevel;
    }

    public void setRemSettlementLevel(Integer remSettlementLevel) {
        this.remSettlementLevel = remSettlementLevel;
    }

    public String getRemVenUrl() {
        return remVenUrl;
    }

    public void setRemVenUrl(String remVenUrl) {
        this.remVenUrl = remVenUrl;
    }

    public Integer getRemSettlementFrequency() {
        return remSettlementFrequency;
    }

    public void setRemSettlementFrequency(Integer remSettlementFrequency) {
        this.remSettlementFrequency = remSettlementFrequency;
    }

    public String getRemBankName() {
        return remBankName;
    }

    public void setRemBankName(String remBankName) {
        this.remBankName = remBankName;
    }

    public String getRemBeneficiaryName() {
        return remBeneficiaryName;
    }

    public void setRemBeneficiaryName(String remBeneficiaryName) {
        this.remBeneficiaryName = remBeneficiaryName;
    }

    public String getRemBranch() {
        return remBranch;
    }

    public void setRemBranch(String remBranch) {
        this.remBranch = remBranch;
    }

    public String getRemBranchAddress() {
        return remBranchAddress;
    }

    public void setRemBranchAddress(String remBranchAddress) {
        this.remBranchAddress = remBranchAddress;
    }

    public String getRemAccountNo() {
        return remAccountNo;
    }

    public void setRemAccountNo(String remAccountNo) {
        this.remAccountNo = remAccountNo;
    }

    public String getRemAccountType() {
        return remAccountType;
    }

    public void setRemAccountType(String remAccountType) {
        this.remAccountType = remAccountType;
    }

    public String getRemSwiftCode() {
        return remSwiftCode;
    }

    public void setRemSwiftCode(String remSwiftCode) {
        this.remSwiftCode = remSwiftCode;
    }

    public String getRemIbanNo() {
        return remIbanNo;
    }

    public void setRemIbanNo(String remIbanNo) {
        this.remIbanNo = remIbanNo;
    }

    public String getSuccessRedirect() {
        return successRedirect;
    }

    public void setSuccessRedirect(String successRedirect) {
        this.successRedirect = successRedirect;
    }

    public String getFailureRedirect() {
        return failureRedirect;
    }

    public void setFailureRedirect(String failureRedirect) {
        this.failureRedirect = failureRedirect;
    }

    public String getRemSecretKey() {
        return remSecretKey;
    }

    public void setRemSecretKey(String remSecretKey) {
        this.remSecretKey = remSecretKey;
    }




    @Override
    public String toString() {
        return "RedemptionMerchant{" +
                "remNo=" + remNo +
                ", remName='" + remName + '\'' +
                ", remCategory=" + remCategory +
                ", remAddress='" + remAddress + '\'' +
                ", remContactPerson='" + remContactPerson + '\'' +
                ", remContactEmail='" + remContactEmail + '\'' +
                ", remContactMobile='" + remContactMobile + '\'' +
                ", remVoucherPrefix='" + remVoucherPrefix + '\'' +
                ", remStatus=" + remStatus +
                ", remSettlementType=" + remSettlementType +
                ", remCode='" + remCode + '\'' +
                ", remType=" + remType +
                ", remSettlementLevel=" + remSettlementLevel +
                ", remVenUrl='" + remVenUrl + '\'' +
                ", remSettlementFrequency=" + remSettlementFrequency +
                ", remBankName='" + remBankName + '\'' +
                ", remBeneficiaryName='" + remBeneficiaryName + '\'' +
                ", remBranch='" + remBranch + '\'' +
                ", remBranchAddress='" + remBranchAddress + '\'' +
                ", remAccountNo='" + remAccountNo + '\'' +
                ", remAccountType='" + remAccountType + '\'' +
                ", remSwiftCode='" + remSwiftCode + '\'' +
                ", remIbanNo='" + remIbanNo + '\'' +
                ", successRedirect='" + successRedirect + '\'' +
                ", failureRedirect='" + failureRedirect + '\'' +
                ", remSecretKey='" + remSecretKey + '\'' +
                ", redemptionMerchantLocations=" + redemptionMerchantLocations +
                '}';
    }
}