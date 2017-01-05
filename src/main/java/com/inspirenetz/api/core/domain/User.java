package com.inspirenetz.api.core.domain;

import com.inspirenetz.api.core.dictionary.ImagePrimaryId;
import com.inspirenetz.api.core.dictionary.IndicatorStatus;
import com.inspirenetz.api.core.dictionary.UserStatus;
import com.inspirenetz.api.core.dictionary.UserType;

import org.hibernate.validator.constraints.NotEmpty;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.crypto.Data;
import java.sql.Date;
import java.sql.Timestamp;
import java.util.Set;

/**
 * Created by sandheepgr on 12/3/14.
 */
@Entity
@Table(name = "USERS")
public class User  extends  AuditedEntity {

    @Column(name = "USR_USER_NO",nullable = false)
    @Basic(fetch = FetchType.EAGER)
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long usrUserNo;


    @Column(name = "USR_LOGIN_ID",nullable = false)
    @Basic(fetch = FetchType.EAGER)
    @NotEmpty(message = "{user.usrloginid.notempty}")
    @NotNull(message = "{user.usrloginid.notnull}")
    @Size(min=3,max=100,message = "{user.usrloginid.size}")
    private String usrLoginId;


    @Column(name = "USR_FNAME",nullable = false)
    @Basic(fetch = FetchType.EAGER)
    private String usrFName = "";


    @Column(name = "USR_LNAME",nullable = true)
    @Basic(fetch = FetchType.EAGER)

    private String usrLName = "";


    @Column(name = "USR_MOBILE",nullable = true)
    @Basic(fetch = FetchType.EAGER)
    @Size(max=16,message = "{user.usrmobile.size}")
    private String usrMobile = "";

    @Column(name = "USR_EMAIL",nullable = true)
    @Basic(fetch = FetchType.EAGER)
    @Size(message = "{user.usrEmail.size}")
    private String usrEmail = "";


    @Column(name = "USR_USER_TYPE",nullable = true)
    @Basic(fetch = FetchType.EAGER)
    private int usrUserType = UserType.CUSTOMER;


    @Column(name = "USR_PASSWORD",nullable = true)
    @Basic(fetch = FetchType.EAGER)
    private String usrPassword = "";


    @Column(name = "USR_STATUS",nullable = true)
    @Basic(fetch = FetchType.EAGER)
    private Integer usrStatus = UserStatus.ACCOUNT_CREATED ;


    @Column(name = "USR_PROFILE_PIC",nullable = true)
    @Basic(fetch = FetchType.EAGER)
    private Long usrProfilePic = ImagePrimaryId.PRIMARY_DEFAULT_IMAGE;


    @Column(name = "USR_MERCHANT_NO",nullable = false)
    @Basic(fetch = FetchType.EAGER)
    private Long usrMerchantNo = 0L;


    @Column(name = "USR_THIRD_PARTY_VENDOR_NO",nullable = false)
    @Basic(fetch = FetchType.EAGER)
    private Long usrThirdPartyVendorNo = 0L;


    @Column(name = "USR_LOCATION",nullable = false)
    @Basic(fetch = FetchType.EAGER)
    private Long usrLocation = 0L;


    @Column(name = "USR_CREATE_TIMESTAMP",nullable = false)
    @Basic(fetch = FetchType.EAGER)
    private Timestamp usrCreateTimestamp;


    @Column(name = "USR_MAIL_SUMMARY_SENT_DATE",nullable = true)
    @Basic(fetch = FetchType.EAGER)
    private Date usrMailSummarySentDate;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "USR_PROFILE_PIC",insertable = false,updatable = false)
    private Image image;

    @OneToMany(fetch = FetchType.EAGER,cascade = CascadeType.ALL)
    @JoinColumn(name="UER_USER_ID")
    public Set<UserRole> userRoleSet;

    @OneToMany(fetch = FetchType.EAGER,cascade = CascadeType.ALL)
    @JoinColumn(name="UAL_USER_ID")
    public Set<UserAccessLocation> userAccessLocationSet;

    @OneToMany(fetch = FetchType.EAGER,cascade = CascadeType.ALL)
    @JoinColumn(name="UAR_USER_NO")
    public Set<UserAccessRight> userAccessRightsSet;

    @Column(name = "USR_INCORRECT_ATTEMPT",nullable = false)
    @Basic(fetch = FetchType.EAGER)
    private Integer usrIncorrectAttempt = 0;

    @Column(name = "USR_REGISTER_STATUS",nullable = true)
    @Basic(fetch = FetchType.EAGER)
    private Integer usrRegisterStatus = IndicatorStatus.YES;

    @Column(name="USR_SYSTEM_USER",nullable = true)
    @Basic(fetch = FetchType.EAGER)
    private Integer usrSystemUser;

    @Column(name="USR_DEFAULT_ROLE",nullable = true)
    @Basic(fetch = FetchType.EAGER)
    private Long usrDefaultRole;

    @Column(name = "USR_USER_CODE",nullable = true)
    @Basic(fetch = FetchType.EAGER)
    private String usrUserCode = "";

    @Column(name="USR_IS_SYSTEM_GENERATE_PASSWORD",nullable = true)
    private Integer usrIsSystemGeneratePassword=IndicatorStatus.NO;

    @Column(name = "USR_PIN_ENABLED",nullable = true)
    @Basic(fetch = FetchType.EAGER)
    private Integer usrPinEnabled = IndicatorStatus.NO;

    @Column(name = "USR_PIN",nullable = true)
    @Basic(fetch = FetchType.EAGER)
    private String usrPin = "";

    @Column(name = "USR_LAST_LOGGED_IN", nullable = true)
    @Basic(fetch = FetchType.EAGER)
    private Timestamp usrLastLoggedIn;


    @Transient
    public Set<UserAccessRight> userAccessRights;

    @Transient
    private Merchant merchant;

    @Transient
    private MerchantLocation merchantLocation;



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

    public String getUsrMobile() {
        return usrMobile;
    }

    public void setUsrMobile(String usrMobile) {
        this.usrMobile = usrMobile;
    }

    public String getUsrEmail() {
        return usrEmail;
    }

    public void setUsrEmail(String usrEmail) {
        this.usrEmail = usrEmail;
    }

    public int getUsrUserType() {
        return usrUserType;
    }

    public void setUsrUserType(int usrUserType) {
        this.usrUserType = usrUserType;
    }

    public String getUsrPassword() {
        return usrPassword;
    }

    public void setUsrPassword(String usrPassword) {
        this.usrPassword = usrPassword;
    }

    public Integer getUsrStatus() {
        return usrStatus;
    }

    public void setUsrStatus(Integer usrStatus) {
        this.usrStatus = usrStatus;
    }

    public Long getUsrProfilePic() {
        return usrProfilePic;
    }

    public void setUsrProfilePic(Long usrProfilePic) {
        this.usrProfilePic = usrProfilePic;
    }

    public Long getUsrMerchantNo() {
        return usrMerchantNo;
    }

    public void setUsrMerchantNo(Long usrMerchantNo) {
        this.usrMerchantNo = usrMerchantNo;
    }

    public Long getUsrThirdPartyVendorNo() {
        return usrThirdPartyVendorNo;
    }

    public void setUsrThirdPartyVendorNo(Long usrThirdPartyVendorNo) {
        this.usrThirdPartyVendorNo = usrThirdPartyVendorNo;
    }

    public Long getUsrLocation() {
        return usrLocation;
    }

    public void setUsrLocation(Long usrLocation) {
        this.usrLocation = usrLocation;
    }


    public Timestamp getUsrCreateTimestamp() {
        return usrCreateTimestamp;
    }

    public void setUsrCreateTimestamp(Timestamp usrCreateTimestamp) {
        this.usrCreateTimestamp = usrCreateTimestamp;
    }

    public Date getUsrMailSummarySentDate() {
        return usrMailSummarySentDate;
    }

    public void setUsrMailSummarySentDate(Date usrMailSummarySentDate) {
        this.usrMailSummarySentDate = usrMailSummarySentDate;
    }

    public Image getImage() {
        return image;
    }

    public void setImage(Image image) {
        this.image = image;
    }

    public Set<UserRole> getUserRoleSet() {
        return userRoleSet;
    }

    public void setUserRoleSet(Set<UserRole> userRoleSet) {
        this.userRoleSet = userRoleSet;
    }

    public Set<UserAccessLocation> getUserAccessLocationSet() {
        return userAccessLocationSet;
    }

    public void setUserAccessLocationSet(Set<UserAccessLocation> userAccessLocationSet) {
        this.userAccessLocationSet = userAccessLocationSet;
    }

    public Set<UserAccessRight> getUserAccessRightsSet() {
        return userAccessRightsSet;
    }

    public void setUserAccessRightsSet(Set<UserAccessRight> userAccessRightsSet) {
        this.userAccessRightsSet = userAccessRightsSet;
    }

    public Merchant getMerchant() {
        return merchant;
    }

    public void setMerchant(Merchant merchant) {
        this.merchant = merchant;
    }

    public MerchantLocation getMerchantLocation() {
        return merchantLocation;
    }

    public void setMerchantLocation(MerchantLocation merchantLocation) {
        this.merchantLocation = merchantLocation;
    }

    public Set<UserAccessRight> getUserAccessRights() {
        return userAccessRights;
    }

    public void setUserAccessRights(Set<UserAccessRight> userAccessRights) {
        this.userAccessRights = userAccessRights;
    }

    public Integer getUsrIncorrectAttempt() {
        return usrIncorrectAttempt;
    }

    public void setUsrIncorrectAttempt(Integer usrIncorrectAttempt) {
        this.usrIncorrectAttempt = usrIncorrectAttempt;
    }

    public Integer getUsrRegisterStatus() {
        return usrRegisterStatus;
    }

    public void setUsrRegisterStatus(Integer usrRegisterStatus) {
        this.usrRegisterStatus = usrRegisterStatus;
    }

    public Integer getUsrSystemUser() {
        return usrSystemUser;
    }

    public void setUsrSystemUser(Integer usrSystemUser) {
        this.usrSystemUser = usrSystemUser;
    }


    public Long getUsrDefaultRole() {
        return usrDefaultRole;
    }

    public void setUsrDefaultRole(Long usrDefaultRole) {
        this.usrDefaultRole = usrDefaultRole;
    }

    public String getUsrUserCode() {
        return usrUserCode;
    }

    public void setUsrUserCode(String usrUserCode) {
        this.usrUserCode = usrUserCode;
    }

    public Integer getUsrIsSystemGeneratePassword() {
        return usrIsSystemGeneratePassword;
    }

    public void setUsrIsSystemGeneratePassword(Integer usrIsSystemGeneratePassword) {
        this.usrIsSystemGeneratePassword = usrIsSystemGeneratePassword;
    }

    public Timestamp getUsrLastLoggedIn() {
        return usrLastLoggedIn;
    }

    public void setUsrLastLoggedIn(Timestamp usrLastLoggedIn) {
        this.usrLastLoggedIn = usrLastLoggedIn;
    }

    public Integer getUsrPinEnabled() {
        return usrPinEnabled;
    }

    public void setUsrPinEnabled(Integer usrPinEnabled) {
        this.usrPinEnabled = usrPinEnabled;
    }

    public String getUsrPin() {
        return usrPin;
    }

    public void setUsrPin(String usrPin) {
        this.usrPin = usrPin;
    }

    @Override
    public String toString() {
        return "User{" +
                "usrUserNo=" + usrUserNo +
                ", usrLoginId='" + usrLoginId + '\'' +
                ", usrFName='" + usrFName + '\'' +
                ", usrLName='" + usrLName + '\'' +
                ", usrMobile='" + usrMobile + '\'' +
                ", usrEmail='" + usrEmail + '\'' +
                ", usrUserType=" + usrUserType +
                ", usrPassword='" + usrPassword + '\'' +
                ", usrStatus=" + usrStatus +
                ", usrProfilePic=" + usrProfilePic +
                ", usrMerchantNo=" + usrMerchantNo +
                ", usrThirdPartyVendorNo=" + usrThirdPartyVendorNo +
                ", usrLocation=" + usrLocation +
                ", usrCreateTimestamp=" + usrCreateTimestamp +
                ", usrMailSummarySentDate=" + usrMailSummarySentDate +
                ", image=" + image +
                ", userRoleSet=" + userRoleSet +
                ", userAccessLocationSet=" + userAccessLocationSet +
                ", userAccessRightsSet=" + userAccessRightsSet +
                ", usrIncorrectAttempt=" + usrIncorrectAttempt +
                ", usrRegisterStatus=" + usrRegisterStatus +
                ", usrSystemUser=" + usrSystemUser +
                ", usrDefaultRole=" + usrDefaultRole +
                ", usrUserCode='" + usrUserCode + '\'' +
                ", usrIsSystemGeneratePassword=" + usrIsSystemGeneratePassword +
                ", usrPinEnabled=" + usrPinEnabled +
                ", usrPin='" + usrPin + '\'' +
                ", usrLastLoggedIn=" + usrLastLoggedIn +
                ", userAccessRights=" + userAccessRights +
                ", merchant=" + merchant +
                ", merchantLocation=" + merchantLocation +
                '}';
    }
}

