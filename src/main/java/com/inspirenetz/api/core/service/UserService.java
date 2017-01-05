package com.inspirenetz.api.core.service;

import com.inspirenetz.api.core.auth.AuthUser;
import com.inspirenetz.api.core.domain.User;
import com.inspirenetz.api.core.domain.UserAccessRight;
import com.inspirenetz.api.rest.exception.InspireNetzException;
import com.inspirenetz.api.rest.resource.UserResource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.security.NoSuchAlgorithmException;
import java.util.List;
import java.util.Map;

/**
 * Created by sandheepgr on 12/3/14.
 */
public interface UserService extends BaseService<User> {

    public User findByUsrUserNo(Long usrUserNo);
    public User findByUsrLoginId(String usrLoginId);
    public User findByUsrLoginIdOrUsrEmail(String usrLoginId,String usrEmail);
    public User findByUsrFNameAndUsrMerchantNo(String usrFName,Long usrMerchantNo);
    public User authenticateUser(String usrLoginId,String passwordDigest);
    public List<User> findByUsrMerchantNo(Long usrMerchantNo);
    public Page<User> getRedemptionMerchantUsers(Pageable pageable);
    public boolean isDuplicateUserExisting(User user);
    public User checkDuplicateUserExistence(User user);

    public Page<User> searchMerchantUsers(Long merchantNo, Integer usrUserType, String filter, String query, Pageable pageable);
    public Page<User> searchRedemptionMerchantUsers(Long merchantNo, Long usrThirdPartyVendorNo,String filter, String query, Pageable pageable) throws InspireNetzException;
    public String getEncodedPassword(String username,String password);

    public User validateAndSaveUserData(User user) throws InspireNetzException;
    public boolean deleteUser(User user) throws InspireNetzException;

    public User validateAndSaveUser(User user) throws InspireNetzException;
    public boolean validateAndDeleteUser(User user) throws InspireNetzException;

    public User getUserDataByUsrUserNo(Long usrUserNo) throws InspireNetzException;

    public String getRegistrationAuthenticationKey(String userName,String password);

    public boolean isAccountLockout(Long usrUserNo) throws InspireNetzException;

    public boolean incrementInvalidAttempt(Long usrUserNo);

    public boolean clearValidAttempt(Long usrUser);

    public User saveFNameAndLName(User user);

    public User forgetPasswordUpdation(String usrLoginId,String newPassword,String otpCode) throws InspireNetzException;

    public boolean generateOtp(String usrLoyaltyId) throws InspireNetzException;

    public boolean generateUserRegistrationOtp(String usrLoginId,Long exMerchantNo) throws InspireNetzException;

    public User getRegistrationStatus(String usrLoginId);

    List<UserAccessRight> getUserAccessRightsByUserNo(Long uarUserNo) throws InspireNetzException;

    User saveUser(User user);

    List<User> findByUsrThirdPartyVendorNo(Long thirdPartyVendorNo);

    boolean registerUser( String usrFName, String usrLName,String usrLoginId,String usrEmail, String usrPassword,Integer channel,Long merchantNo) throws InspireNetzException;

    boolean confirmUserRegistration(String usrLoginId, String otpCode,Integer channel,Long merchantNo) throws InspireNetzException;

    public boolean generateForgetPasswordOtp(String usrLoginId,Long merchantNo) throws InspireNetzException;

    public User forgetUserPasswordOtpValidation(String usrLoginId,String newPassword,String otpCode,Long merchantNo) throws InspireNetzException;

    public List<Map<String,Object>> getUserMemberships(Long merchantNo,String usrLoginId,String filter,String query) throws InspireNetzException;

    public User changeAccountInformation(UserResource userResource) throws InspireNetzException;

    public User validateAndSaveUserFromMerchant(User user) throws InspireNetzException;

    public boolean generateOTPForCustomer(Long merchantNo,String usrLoginId,Integer otpRefType,Integer otpType)throws InspireNetzException;
    public List<User> findByUsrUserTypeAndUsrMerchantNo(int userType,Long usrMerchantNo);
    public void userOptOutOperation(Long usrUserNo) throws InspireNetzException;
    public String generateUsrUserCode();

    public String getUsrUserCode(Long usrUserNo)throws InspireNetzException;

    public User findByUsrUserCode(String usrUserCode);


}
