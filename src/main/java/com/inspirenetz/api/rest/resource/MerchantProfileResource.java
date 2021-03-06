package com.inspirenetz.api.rest.resource;

import com.inspirenetz.api.core.domain.MerchantLocation;

import java.sql.Date;
import java.util.Set;

/**
 * Created by sandheepgr on 14/5/14.
 */
public class MerchantProfileResource extends BaseResource {


    private Long merMerchantNo;

    private String merMerchantName;

    private int merExclusiveInd ;

    private String merUrlName;

    private String merAddress1 = "";

    private String merAddress2 = "";

    private String merAddress3 = "";

    private String merCity;

    private String merState;

    private int merCountry ;

    private String merPostCode;

    private String merContactName;

    private String merContactEmail;

    private String merPhoneNo;

    private String merEmail;

    private String merSignupType = "";

    private String merAutoSignupEnable;

    private String merMembershipName;

    private String merPaymentModes = "";

    private String merCustIdTypes = "";

    private Long merMerchantImage ;

    private Long merCoverImage;

    private int merSignupRewardEnabledInd;

    private int merSignupRewardCurrency =0;

    private double merSignupRewardPoints = 0;

    private String merLogoPath;

    private String merCoverImagePath;

    private Set<MerchantLocation> merchantLocationSet;


    public Long getMerMerchantNo() {
        return merMerchantNo;
    }

    public void setMerMerchantNo(Long merMerchantNo) {
        this.merMerchantNo = merMerchantNo;
    }

    public String getMerMerchantName() {
        return merMerchantName;
    }

    public void setMerMerchantName(String merMerchantName) {
        this.merMerchantName = merMerchantName;
    }

    public String getMerAddress1() {
        return merAddress1;
    }

    public void setMerAddress1(String merAddress1) {
        this.merAddress1 = merAddress1;
    }

    public String getMerAddress2() {
        return merAddress2;
    }

    public void setMerAddress2(String merAddress2) {
        this.merAddress2 = merAddress2;
    }

    public String getMerAddress3() {
        return merAddress3;
    }

    public void setMerAddress3(String merAddress3) {
        this.merAddress3 = merAddress3;
    }

    public String getMerCity() {
        return merCity;
    }

    public void setMerCity(String merCity) {
        this.merCity = merCity;
    }

    public String getMerState() {
        return merState;
    }

    public void setMerState(String merState) {
        this.merState = merState;
    }

    public int getMerCountry() {
        return merCountry;
    }

    public void setMerCountry(int merCountry) {
        this.merCountry = merCountry;
    }

    public String getMerPostCode() {
        return merPostCode;
    }

    public void setMerPostCode(String merPostCode) {
        this.merPostCode = merPostCode;
    }

    public String getMerContactName() {
        return merContactName;
    }

    public void setMerContactName(String merContactName) {
        this.merContactName = merContactName;
    }

    public String getMerContactEmail() {
        return merContactEmail;
    }

    public void setMerContactEmail(String merContactEmail) {
        this.merContactEmail = merContactEmail;
    }

    public String getMerPhoneNo() {
        return merPhoneNo;
    }

    public void setMerPhoneNo(String merPhoneNo) {
        this.merPhoneNo = merPhoneNo;
    }

    public String getMerEmail() {
        return merEmail;
    }

    public void setMerEmail(String merEmail) {
        this.merEmail = merEmail;
    }

    public String getMerSignupType() {
        return merSignupType;
    }

    public void setMerSignupType(String merSignupType) {
        this.merSignupType = merSignupType;
    }

    public String getMerAutoSignupEnable() {
        return merAutoSignupEnable;
    }

    public void setMerAutoSignupEnable(String merAutoSignupEnable) {
        this.merAutoSignupEnable = merAutoSignupEnable;
    }

    public String getMerMembershipName() {
        return merMembershipName;
    }

    public void setMerMembershipName(String merMembershipName) {
        this.merMembershipName = merMembershipName;
    }

    public String getMerPaymentModes() {
        return merPaymentModes;
    }

    public void setMerPaymentModes(String merPaymentModes) {
        this.merPaymentModes = merPaymentModes;
    }

    public String getMerCustIdTypes() {
        return merCustIdTypes;
    }

    public void setMerCustIdTypes(String merCustIdTypes) {
        this.merCustIdTypes = merCustIdTypes;
    }

    public Long getMerMerchantImage() {
        return merMerchantImage;
    }

    public void setMerMerchantImage(Long merMerchantImage) {
        this.merMerchantImage = merMerchantImage;
    }

    public Long getMerCoverImage() {
        return merCoverImage;
    }

    public void setMerCoverImage(Long merCoverImage) {
        this.merCoverImage = merCoverImage;
    }

    public int getMerSignupRewardEnabledInd() {
        return merSignupRewardEnabledInd;
    }

    public void setMerSignupRewardEnabledInd(int merSignupRewardEnabledInd) {
        this.merSignupRewardEnabledInd = merSignupRewardEnabledInd;
    }

    public int getMerSignupRewardCurrency() {
        return merSignupRewardCurrency;
    }

    public void setMerSignupRewardCurrency(int merSignupRewardCurrency) {
        this.merSignupRewardCurrency = merSignupRewardCurrency;
    }

    public double getMerSignupRewardPoints() {
        return merSignupRewardPoints;
    }

    public void setMerSignupRewardPoints(double merSignupRewardPoints) {
        this.merSignupRewardPoints = merSignupRewardPoints;
    }

    public String getMerLogoPath() {
        return merLogoPath;
    }

    public void setMerLogoPath(String merLogoPath) {
        this.merLogoPath = merLogoPath;
    }

    public String getMerCoverImagePath() {
        return merCoverImagePath;
    }

    public void setMerCoverImagePath(String merCoverImagePath) {
        this.merCoverImagePath = merCoverImagePath;
    }

    public Set<MerchantLocation> getMerchantLocationSet() {
        return merchantLocationSet;
    }

    public void setMerchantLocationSet(Set<MerchantLocation> merchantLocationSet) {
        this.merchantLocationSet = merchantLocationSet;
    }
}
