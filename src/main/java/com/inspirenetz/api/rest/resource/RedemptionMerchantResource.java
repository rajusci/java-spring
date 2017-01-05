package com.inspirenetz.api.rest.resource;

import com.inspirenetz.api.core.domain.RedemptionMerchantLocation;

import java.util.List;
import java.util.Set;

/**
 * Created by sandheepgr on 15/4/14.
 */
public class RedemptionMerchantResource extends BaseResource {

    private Long remNo;
    private String remName;
    private Long remCategory;
    private String remAddress;
    private String remContactPerson;
    private String remContactEmail;
    private String remContactMobile;
    private String remVoucherPrefix;
    private Integer remStatus = 0;
    private Integer remSettlementType;
    private String remCode;
    private Integer remType;
    private Integer remSettlementLevel;
    private String remVenUrl;
    private Integer remSettlementFrequency;
    private String remBankName;
    private String remBeneficiaryName;
    private String remBranch;
    private String remBranchAddress;
    private String remAccountNo;
    private String remAccountType;
    private String remSwiftCode;
    private String successRedirect;
    private String failureRedirect;
    private String remIbanNo;

    // The secret key is not given the name matching key field in the RedemptionMerchant entity.
    // This is to avoid auto mapping of the secret key by the mappers as it exposes the field
    private String secretKey;

    public Set<RedemptionMerchantLocation> redemptionMerchantLocations;
    private List<MerchantRedemptionPartnerResource> merchantRedemptionPartnerResources;


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

    public Integer getRemStatus() {
        return remStatus;
    }

    public void setRemStatus(Integer remStatus) {
        this.remStatus = remStatus;
    }

    public String getRemVoucherPrefix() {
        return remVoucherPrefix;
    }

    public void setRemVoucherPrefix(String remVoucherPrefix) {
        this.remVoucherPrefix = remVoucherPrefix;
    }

    public Set<RedemptionMerchantLocation> getRedemptionMerchantLocations() {
        return redemptionMerchantLocations;
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

    public String getSecretKey() {
        return secretKey;
    }

    public void setSecretKey(String secretKey) {
        this.secretKey = secretKey;
    }

    public void setRedemptionMerchantLocations(Set<RedemptionMerchantLocation> redemptionMerchantLocations) {
        this.redemptionMerchantLocations = redemptionMerchantLocations;
    }

    public List<MerchantRedemptionPartnerResource> getMerchantRedemptionPartnerResources() {
        return merchantRedemptionPartnerResources;
    }

    public void setMerchantRedemptionPartnerResources(List<MerchantRedemptionPartnerResource> merchantRedemptionPartnerResources) {
        this.merchantRedemptionPartnerResources = merchantRedemptionPartnerResources;
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


    @Override
    public String toString() {
        return "RedemptionMerchantResource{" +
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
                ", successRedirect='" + successRedirect + '\'' +
                ", failureRedirect='" + failureRedirect + '\'' +
                ", remIbanNo='" + remIbanNo + '\'' +
                ", secretKey='" + secretKey + '\'' +
                ", redemptionMerchantLocations=" + redemptionMerchantLocations +
                ", merchantRedemptionPartnerResources=" + merchantRedemptionPartnerResources +
                '}';
    }
}
