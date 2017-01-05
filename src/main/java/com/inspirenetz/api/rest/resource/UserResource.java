package com.inspirenetz.api.rest.resource;

import com.inspirenetz.api.core.dictionary.ImagePrimaryId;
import com.inspirenetz.api.core.dictionary.IndicatorStatus;
import com.inspirenetz.api.core.dictionary.UserStatus;
import com.inspirenetz.api.core.domain.UserAccessLocation;
import com.inspirenetz.api.core.domain.UserAccessRight;
import com.inspirenetz.api.core.domain.UserRole;

import java.util.Set;

/**
 * Created by sandheepgr on 25/6/14.
 */
public class UserResource extends  BaseResource{

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

    private Long usrThirdPartyVendorNo = 0L;

    private Integer usrRegisterStatus = IndicatorStatus.YES;

    private Long usrDefaultRole;

    private String merchantLogo;

    private String usrOldPassword;

    private Integer usrChangePassword;

    public Integer getUsrChangePassword() {
        return usrChangePassword;
    }

    public void setUsrChangePassword(Integer usrChangePassword) {
        this.usrChangePassword = usrChangePassword;
    }

    public String getUsrOldPassword() {
        return usrOldPassword;
    }

    public void setUsrOldPassword(String usrOldPassword) {
        this.usrOldPassword = usrOldPassword;
    }

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

    public Long getUsrThirdPartyVendorNo() {
        return usrThirdPartyVendorNo;
    }

    public void setUsrThirdPartyVendorNo(Long usrThirdPartyVendorNo) {
        this.usrThirdPartyVendorNo = usrThirdPartyVendorNo;
    }

    public Integer getUsrRegisterStatus() {
        return usrRegisterStatus;
    }

    public void setUsrRegisterStatus(Integer usrRegisterStatus) {
        this.usrRegisterStatus = usrRegisterStatus;
    }

    public Long getUsrDefaultRole() {
        return usrDefaultRole;
    }

    public void setUsrDefaultRole(Long usrDefaultRole) {
        this.usrDefaultRole = usrDefaultRole;
    }

    public String getMerchantLogo() {
        return merchantLogo;
    }

    public void setMerchantLogo(String merchantLogo) {
        this.merchantLogo = merchantLogo;
    }
}
