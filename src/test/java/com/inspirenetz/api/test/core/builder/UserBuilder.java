package com.inspirenetz.api.test.core.builder;

import com.inspirenetz.api.core.dictionary.ImagePrimaryId;
import com.inspirenetz.api.core.dictionary.IndicatorStatus;
import com.inspirenetz.api.core.dictionary.UserStatus;
import com.inspirenetz.api.core.dictionary.UserType;
import com.inspirenetz.api.core.domain.*;

import java.sql.Date;
import java.sql.Timestamp;
import java.util.Set;

/**
 * Created by saneesh-ci on 11/11/14.
 */
public class UserBuilder {
    public Set<UserRole> userRoleSet;
    public Set<UserAccessLocation> userAccessLocationSet;
    public Set<UserAccessRight> userAccessRightsSet;
    public Set<UserAccessRight> userAccessRights;
    private Long usrUserNo;
    private String usrLoginId;
    private String usrFName = "";
    private String usrLName = "";
    private String usrMobile = "";
    private int usrUserType = UserType.CUSTOMER;
    private String usrPassword = "";
    private Integer usrStatus = UserStatus.ACCOUNT_CREATED ;
    private Long usrProfilePic = ImagePrimaryId.PRIMARY_DEFAULT_IMAGE;
    private Long usrMerchantNo = 0L;
    private Long usrThirdPartyVendorNo = 0L;
    private Long usrLocation = 0L;
    private Timestamp usrCreateTimestamp;
    private Date usrMailSummarySentDate;
    private Image image;
    private Integer usrIncorrectAttempt = 0;
    private Integer usrRegisterStatus = IndicatorStatus.YES;
    private Merchant merchant;
    private MerchantLocation merchantLocation;
    private java.util.Date createdAt;
    private String createdBy;
    private java.util.Date updatedAt;
    private String updatedBy;

    private UserBuilder() {
    }

    public static UserBuilder anUser() {
        return new UserBuilder();
    }

    public UserBuilder withUsrUserNo(Long usrUserNo) {
        this.usrUserNo = usrUserNo;
        return this;
    }

    public UserBuilder withUsrLoginId(String usrLoginId) {
        this.usrLoginId = usrLoginId;
        return this;
    }

    public UserBuilder withUsrFName(String usrFName) {
        this.usrFName = usrFName;
        return this;
    }

    public UserBuilder withUsrLName(String usrLName) {
        this.usrLName = usrLName;
        return this;
    }

    public UserBuilder withUsrMobile(String usrMobile) {
        this.usrMobile = usrMobile;
        return this;
    }

    public UserBuilder withUsrUserType(int usrUserType) {
        this.usrUserType = usrUserType;
        return this;
    }

    public UserBuilder withUsrPassword(String usrPassword) {
        this.usrPassword = usrPassword;
        return this;
    }

    public UserBuilder withUsrStatus(Integer usrStatus) {
        this.usrStatus = usrStatus;
        return this;
    }

    public UserBuilder withUsrProfilePic(Long usrProfilePic) {
        this.usrProfilePic = usrProfilePic;
        return this;
    }

    public UserBuilder withUsrMerchantNo(Long usrMerchantNo) {
        this.usrMerchantNo = usrMerchantNo;
        return this;
    }

    public UserBuilder withUsrThirdPartyVendorNo(Long usrThirdPartyVendorNo) {
        this.usrThirdPartyVendorNo = usrThirdPartyVendorNo;
        return this;
    }

    public UserBuilder withUsrLocation(Long usrLocation) {
        this.usrLocation = usrLocation;
        return this;
    }

    public UserBuilder withUsrCreateTimestamp(Timestamp usrCreateTimestamp) {
        this.usrCreateTimestamp = usrCreateTimestamp;
        return this;
    }

    public UserBuilder withUsrMailSummarySentDate(Date usrMailSummarySentDate) {
        this.usrMailSummarySentDate = usrMailSummarySentDate;
        return this;
    }

    public UserBuilder withImage(Image image) {
        this.image = image;
        return this;
    }

    public UserBuilder withUserRoleSet(Set<UserRole> userRoleSet) {
        this.userRoleSet = userRoleSet;
        return this;
    }

    public UserBuilder withUserAccessLocationSet(Set<UserAccessLocation> userAccessLocationSet) {
        this.userAccessLocationSet = userAccessLocationSet;
        return this;
    }

    public UserBuilder withUserAccessRightsSet(Set<UserAccessRight> userAccessRightsSet) {
        this.userAccessRightsSet = userAccessRightsSet;
        return this;
    }

    public UserBuilder withUsrIncorrectAttempt(Integer usrIncorrectAttempt) {
        this.usrIncorrectAttempt = usrIncorrectAttempt;
        return this;
    }

    public UserBuilder withUsrRegisterStatus(Integer usrRegisterStatus) {
        this.usrRegisterStatus = usrRegisterStatus;
        return this;
    }

    public UserBuilder withUserAccessRights(Set<UserAccessRight> userAccessRights) {
        this.userAccessRights = userAccessRights;
        return this;
    }

    public UserBuilder withMerchant(Merchant merchant) {
        this.merchant = merchant;
        return this;
    }

    public UserBuilder withMerchantLocation(MerchantLocation merchantLocation) {
        this.merchantLocation = merchantLocation;
        return this;
    }

    public UserBuilder withCreatedAt(java.util.Date createdAt) {
        this.createdAt = createdAt;
        return this;
    }

    public UserBuilder withCreatedBy(String createdBy) {
        this.createdBy = createdBy;
        return this;
    }

    public UserBuilder withUpdatedAt(java.util.Date updatedAt) {
        this.updatedAt = updatedAt;
        return this;
    }

    public UserBuilder withUpdatedBy(String updatedBy) {
        this.updatedBy = updatedBy;
        return this;
    }

    public UserBuilder but() {
        return anUser().withUsrUserNo(usrUserNo).withUsrLoginId(usrLoginId).withUsrFName(usrFName).withUsrLName(usrLName).withUsrMobile(usrMobile).withUsrUserType(usrUserType).withUsrPassword(usrPassword).withUsrStatus(usrStatus).withUsrProfilePic(usrProfilePic).withUsrMerchantNo(usrMerchantNo).withUsrThirdPartyVendorNo(usrThirdPartyVendorNo).withUsrLocation(usrLocation).withUsrCreateTimestamp(usrCreateTimestamp).withUsrMailSummarySentDate(usrMailSummarySentDate).withImage(image).withUserRoleSet(userRoleSet).withUserAccessLocationSet(userAccessLocationSet).withUserAccessRightsSet(userAccessRightsSet).withUsrIncorrectAttempt(usrIncorrectAttempt).withUsrRegisterStatus(usrRegisterStatus).withUserAccessRights(userAccessRights).withMerchant(merchant).withMerchantLocation(merchantLocation).withCreatedAt(createdAt).withCreatedBy(createdBy).withUpdatedAt(updatedAt).withUpdatedBy(updatedBy);
    }

    public User build() {
        User user = new User();
        user.setUsrUserNo(usrUserNo);
        user.setUsrLoginId(usrLoginId);
        user.setUsrFName(usrFName);
        user.setUsrLName(usrLName);
        user.setUsrMobile(usrMobile);
        user.setUsrUserType(usrUserType);
        user.setUsrPassword(usrPassword);
        user.setUsrStatus(usrStatus);
        user.setUsrProfilePic(usrProfilePic);
        user.setUsrMerchantNo(usrMerchantNo);
        user.setUsrThirdPartyVendorNo(usrThirdPartyVendorNo);
        user.setUsrLocation(usrLocation);
        user.setUsrCreateTimestamp(usrCreateTimestamp);
        user.setUsrMailSummarySentDate(usrMailSummarySentDate);
        user.setImage(image);
        user.setUserRoleSet(userRoleSet);
        user.setUserAccessLocationSet(userAccessLocationSet);
        user.setUserAccessRightsSet(userAccessRightsSet);
        user.setUsrIncorrectAttempt(usrIncorrectAttempt);
        user.setUsrRegisterStatus(usrRegisterStatus);
        user.setUserAccessRights(userAccessRights);
        user.setMerchant(merchant);
        user.setMerchantLocation(merchantLocation);
        user.setCreatedAt(createdAt);
        user.setCreatedBy(createdBy);
        user.setUpdatedAt(updatedAt);
        user.setUpdatedBy(updatedBy);
        return user;
    }
}
