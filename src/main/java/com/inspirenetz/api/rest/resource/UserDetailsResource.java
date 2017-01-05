package com.inspirenetz.api.rest.resource;

import com.inspirenetz.api.core.dictionary.ImagePrimaryId;
import com.inspirenetz.api.core.dictionary.UserStatus;
import com.inspirenetz.api.core.domain.UserAccessLocation;
import com.inspirenetz.api.core.domain.UserAccessRight;
import com.inspirenetz.api.core.domain.UserRole;

import java.util.Set;

/**
 * Created by sandheepgr on 25/6/14.
 */
public class UserDetailsResource extends  BaseResource{

    private  Long usrUserNo;

    private  String usrLoginId;

    private String usrMobile = "";

    private String usrFName = "";

    private String usrLName = "";

    private Integer usrStatus = UserStatus.ACCOUNT_CREATED;

    private Long usrProfilePic = ImagePrimaryId.PRIMARY_DEFAULT_IMAGE;

    private String profilePic;

    private Long usrLocation = 0L;

    private String locationName;

    private String merchantName;

    private String userName;

    private Long usrMerchantNo = 0L;

    private String usrPassword = "";

    private Integer usrUserType;

    public Set<UserAccessRight> userAccessRights;

    public Set<UserAccessRight> userAccessRightsSet;

    public Set<UserAccessLocation> userAccessLocationSet;

    public Set<UserRole> userRoleSet;

    public Long usrDefaultRole;



    public Long getUsrUserNo() {
        return usrUserNo;
    }

    public void setUsrUserNo(Long usrUserNo) {
        this.usrUserNo = usrUserNo;
    }

    public String getUsrLoginId() {
        return usrLoginId;
    }

    public void setUsrLoginId(String usrLoginId) {
        this.usrLoginId = usrLoginId;
    }

    public String getUsrFName() {
        return usrFName;
    }

    public void setUsrFName(String usrFName) {
        this.usrFName = usrFName;
    }

    public String getUsrLName() {
        return usrLName;
    }

    public void setUsrLName(String usrLName) {
        this.usrLName = usrLName;
    }

    public Integer getUsrStatus() {
        return usrStatus;
    }

    public void setUsrStatus(Integer usrStatus) {
        this.usrStatus = usrStatus;
    }

    public String getProfilePic() {
        return profilePic;
    }

    public void setProfilePic(String profilePic) {
        this.profilePic = profilePic;
    }

    public Long getUsrLocation() {
        return usrLocation;
    }

    public void setUsrLocation(Long usrLocation) {
        this.usrLocation = usrLocation;
    }

    public String getLocationName() {
        return locationName;
    }

    public void setLocationName(String locationName) {
        this.locationName = locationName;
    }

    public String getMerchantName() {
        return merchantName;
    }

    public void setMerchantName(String merchantName) {
        this.merchantName = merchantName;
    }

    public Long getUsrProfilePic() {
        return usrProfilePic;
    }

    public void setUsrProfilePic(Long usrProfilePic) {
        this.usrProfilePic = usrProfilePic;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public Long getUsrMerchantNo() {
        return usrMerchantNo;
    }

    public void setUsrMerchantNo(Long usrMerchantNo) {
        this.usrMerchantNo = usrMerchantNo;
    }

    public String getUsrPassword() {
        return usrPassword;
    }

    public void setUsrPassword(String usrPassword) {
        this.usrPassword = usrPassword;
    }

    public String getUsrMobile() {
        return usrMobile;
    }

    public void setUsrMobile(String usrMobile) {
        this.usrMobile = usrMobile;
    }

    public Integer getUsrUserType() {
        return usrUserType;
    }

    public void setUsrUserType(Integer usrUserType) {
        this.usrUserType = usrUserType;
    }

    public Set<UserAccessRight> getUserAccessRights() {
        return userAccessRights;
    }

    public void setUserAccessRights(Set<UserAccessRight> userAccessRights) {
        this.userAccessRights = userAccessRights;
    }

    public Set<UserAccessLocation> getUserAccessLocationSet() {
        return userAccessLocationSet;
    }

    public void setUserAccessLocationSet(Set<UserAccessLocation> userAccessLocationSet) {
        this.userAccessLocationSet = userAccessLocationSet;
    }

    public Set<UserRole> getUserRoleSet() {
        return userRoleSet;
    }

    public void setUserRoleSet(Set<UserRole> userRoleSet) {
        this.userRoleSet = userRoleSet;
    }

    public Set<UserAccessRight> getUserAccessRightsSet() {
        return userAccessRightsSet;
    }

    public void setUserAccessRightsSet(Set<UserAccessRight> userAccessRightsSet) {
        this.userAccessRightsSet = userAccessRightsSet;
    }

    public Long getUsrDefaultRole() {
        return usrDefaultRole;
    }

    public void setUsrDefaultRole(Long usrDefaultRole) {
        this.usrDefaultRole = usrDefaultRole;
    }
}
