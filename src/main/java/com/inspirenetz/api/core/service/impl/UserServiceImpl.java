package com.inspirenetz.api.core.service.impl;

import com.google.common.collect.Lists;
import com.inspirenetz.api.core.auth.AuthUser;
import com.inspirenetz.api.core.dictionary.*;
import com.inspirenetz.api.core.domain.*;
import com.inspirenetz.api.core.repository.*;
import com.inspirenetz.api.core.service.*;
import com.inspirenetz.api.rest.exception.InspireNetzException;
import com.inspirenetz.api.rest.resource.UserResource;
import com.inspirenetz.api.util.AuthSessionUtils;
import com.inspirenetz.api.util.GeneralUtils;
;
import org.dozer.Mapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.Timestamp;
import java.util.*;

/**
 * Created by sandheepgr on 12/3/14.
 */
@Service
public class UserServiceImpl extends BaseServiceImpl<User> implements UserService {

    private static Logger log = LoggerFactory.getLogger(UserServiceImpl.class);

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private GeneralUtils generalUtils;

    @Autowired
    private MerchantService merchantService;

    @Autowired
    private UserRoleService userRoleService;

    @Autowired
    private UserAccessLocationService userAccessLocationService;

    @Autowired
    private UserAccessRightService userAccessRightService;

    @Autowired
    private RoleAccessRightService roleAccessRightService;

    @Autowired
    private SecuritySettingService securitySettingService;

    @Autowired
    private PasswordHistoryService passwordHistoryService;

    @Autowired
    private AuthSessionUtils authSessionUtils;

    @Autowired
    private OneTimePasswordService oneTimePasswordService;

    @Autowired
    private CustomerService customerService;

    @Autowired
    private CustomerActivityService customerActivityService;

    @Autowired
    private UserMessagingService userMessagingService;

    @Autowired
    private RoleService roleService;

    @Autowired
    CustomerRewardBalanceService customerRewardBalanceService;

    @Autowired
    MerchantSettingService merchantSettingService;

    @Autowired
    ImageService imageService;

    @Autowired
    CatalogueService catalogueService;


    @Autowired
    IntegrationService integrationService;


    public UserServiceImpl(){
        super(User.class);
    }

    @Override
    protected BaseRepository<User,Long> getDao() {
        return userRepository;
    }


    @Override
    public User findByUsrUserNo(Long usrUserNo) {

        // Get User object
        User user = userRepository.findByUsrUserNo(usrUserNo);

        // Update the related fields
        user = setRelatedFieldsForEntity(user);

        // Return  the user
        return user;

    }

    @Override
    public User findByUsrLoginId(String usrLoginId) {

        // Get User object
        User user = userRepository.findByUsrLoginId(usrLoginId);

        // Update the related fields
        user = setRelatedFieldsForEntity(user);

        // Return  the user
        return user;

    }

    @Override
    public User findByUsrLoginIdOrUsrEmail(String usrLoginId,String usrEmail) {

        // Get User object
        User user = userRepository.findByUsrLoginIdOrUsrEmail(usrLoginId,usrEmail);

        // Update the related fields
        user = setRelatedFieldsForEntity(user);

        // Return  the user
        return user;

    }

    @Override
    public User findByUsrFNameAndUsrMerchantNo(String usrFName,Long usrMerchantNo) {

        // Get User object
        User user = userRepository.findByUsrFNameAndUsrMerchantNo(usrFName,usrMerchantNo);

        // Update the related fields
        user = setRelatedFieldsForEntity(user);

        // Return  the user
        return user;

    }


    /**
     * Function to authenticate the user based on the loginId and the passwordDigest information
     * received
     *
     * @param usrLoginId        : The login id for the user
     * @param passwordDigest    : The password digest information obtained from the api
     * @return                  : Return null if the validation failed
     *                            Return User object if the validation successful.
     */
    public User authenticateUser(String usrLoginId,String passwordDigest) {

        // Get the user information
        User user = this.findByUsrLoginId(usrLoginId);

        // Check if the user is valid
        if ( user != null ) {

            // Check the password validity
            if ( user.getUsrPassword().equals(passwordDigest) ) {

                // Update the related fields
                user = setRelatedFieldsForEntity(user);





                // Return the user
                return user;

            } else {

                return null;
            }


        }


        // Return null;
        return null;

    }


    /**
     * Function to get all the users
     * @param usrMerchantNo
     * @return
     */
    public List<User> findByUsrMerchantNo(Long usrMerchantNo) {

        // Get User object
        List<User> userList = userRepository.findByUsrMerchantNo(usrMerchantNo);

        // Return  the user
        return userList;

    }

    @Override
    public Page<User> getRedemptionMerchantUsers(Pageable pageable) {

        // Get User object
        Page<User> userList = userRepository.findByUsrUserType(UserType.REDEMPTION_MERCHANT_USER, pageable);

        // Return  the user
        return userList;

    }

    @Override
    public boolean isDuplicateUserExisting(User user) {

        // Get the user information
        User exUser = null;

        // Get the user information
        if(user.getUsrEmail()==null ||user.getUsrEmail().equals("")){


            exUser = userRepository.findByUsrLoginId(user.getUsrLoginId());

        }else{

            exUser=userRepository.findByUsrLoginIdOrUsrEmail(user.getUsrLoginId(),user.getUsrEmail());
        }

        // If the userUserNo is 0L, then its a new user so we just need to check if there is ano
        // ther user code
        if ( user.getUsrUserNo() == null || user.getUsrUserNo() == 0L ) {

            // If the user is not null, then return true
            if ( exUser != null ) {

                return true;

            }

        } else {

            // Check if the user is null
            if ( exUser != null && user.getUsrUserNo().longValue() != exUser.getUsrUserNo().longValue() ) {

                return true;

            }
        }


        // Return false;
        return false;
    }

    @Override
    public User checkDuplicateUserExistence(User user) {

        // Get the user information
        User exUser = null;

        // Get the user information
        if(user.getUsrEmail()==null||user.getUsrEmail().equals("")){


            exUser = userRepository.findByUsrLoginId(user.getUsrLoginId());

        }else{

            exUser=userRepository.findByUsrLoginIdOrUsrEmail(user.getUsrLoginId(),user.getUsrEmail());
        }

        // If the userUserNo is 0L, then its a new user so we just need to check if there is ano
        // ther user code
        if ( user.getUsrUserNo() == null || user.getUsrUserNo() == 0L ) {

            // If the user is not null, then return true
            if ( exUser != null ) {

                return exUser;

            }

        } else {

            // Check if the user is null
            if ( exUser != null && user.getUsrUserNo().longValue() != exUser.getUsrUserNo().longValue() ) {

                return exUser;

            }
        }


        // Return false;
        return null;
    }

    @Override
    public Page<User> searchMerchantUsers(Long merchantNo, Integer usrUserType, String filter, String query, Pageable pageable) {

        // The list to return
        Page<User> userPage;

        //get the type of user is pick web admin user and merchant admin user
        if(authSessionUtils.getUserType() == UserType.ADMIN){

            //get web admin and merchant admin information
            // Check the filter and query
            if ( filter.equalsIgnoreCase("username") ) {

                userPage = userRepository.findByUsrUserTypeAndUsrLoginIdLike(UserType.MERCHANT_ADMIN, "%" + query + "%", pageable);

            } else if ( filter.equalsIgnoreCase("fname") ) {

                userPage = userRepository.findByUsrUserTypeAndUsrFNameLike(UserType.MERCHANT_ADMIN, "%" + query + "%", pageable);

            } else {

                userPage = userRepository.findByUsrUserType(UserType.MERCHANT_ADMIN, pageable);

            }
        }else if(authSessionUtils.getUserType() ==UserType.SUPER_ADMIN){

            //get merchant admin details
            // Check the filter and query
            if ( filter.equalsIgnoreCase("username") ) {

                userPage = userRepository.findByUsrLoginIdLike( "%"+query+"%",pageable);

            } else if ( filter.equalsIgnoreCase("fname") ) {

                userPage = userRepository.findByUsrFNameLike("%" + query + "%", pageable);

            } else {

                userPage = userRepository.findByUser(pageable);

            }
        }else {

            // Check the filter and query
            if ( filter.equalsIgnoreCase("username") ) {

                userPage = userRepository.findByUsrMerchantNoAndUsrLoginIdLike(merchantNo, usrUserType, "%"+query+"%",pageable);

            } else if ( filter.equalsIgnoreCase("fname") ) {

                userPage = userRepository.findByUsrMerchantNoAndUsrFNameLike(merchantNo, usrUserType, "%"+query+"%",pageable);

            } else {

                userPage = userRepository.findByUsrMerchantNo(merchantNo, usrUserType, pageable);

            }
        }

        // Return the userPage
        return userPage;

    }

    @Override
    public String getEncodedPassword(String username, String password) {

        // Get the realm
        String realm = generalUtils.getDigestAuthenticationRealm();

        // If the realm is null, log error and return null
        if ( realm == null ) {

            // Log the information
            log.info("getEncodedPassword ->  Unable to retrieve the realm"  );

            // Return null
            return null;

        }


        // The encodedPassword
        String encPassword = null;


        try {

            // Get the MessageDigest instnace
            MessageDigest messageDigest = null;

            // Get the MD5 instance
            messageDigest = MessageDigest.getInstance("MD5");

            // Create the password string
            String plainString = username+":"+realm+":"+password;

            // Create the byteArray
            byte digest[] = messageDigest.digest(plainString.getBytes("UTF-8"));

            // Create the string builder with double the size of the digest
            StringBuilder sb = new StringBuilder(2*digest.length);

            // Go through each of the byte and convert to hexadecimal
            for(byte b : digest){

                sb.append(String.format("%02x", b&0xff));

            }

            // Get the encodedPassword from the StringBuilder object
            encPassword =sb.toString();


        } catch (NoSuchAlgorithmException e) {

            // Log as error
            log.info("getEncodedPassword -> No such alogirhtm ");

            // Print stack trace
            e.printStackTrace();

        } catch (UnsupportedEncodingException e) {

            // Log as error
            log.info("getEncodedPassword -> Unsupported encoding exception ");

            // Print stack trace
            e.printStackTrace();

        }


        // Return the encoded password
        return encPassword;

    }

    /**
     * Function to set the related fields for the User entity
     * This function will check for the type of the user and set the fields
     * based on the type.
     *
     * For merchant, it will set the Merchant and MerchantLocation object
     *
     * @param user      - The user object for which the fields need to be set
     *
     * @return          - Return the updated User object with the fields set
     *                    Return null if the passed object is null.
     */
    public User setRelatedFieldsForEntity(User user) {

        // Check if the user is null
        if ( user == null ) {

            return null;

        }

        //get merchant information and set into user field
        Merchant merchant =getMerchantDetails(user);

        //set append merchant information into user
        if(merchant !=null){

            user.setMerchant(merchant);
        }

        //set logged in time

        // Return the user
        return user;

    }

    private Merchant getMerchantDetails(User user) {

        //get merchant details for user
        Merchant merchant =merchantService.findByMerMerchantNo(user.getUsrMerchantNo() ==null?0L:user.getUsrMerchantNo());

        return merchant;
    }

    @Override
    public Page<User> searchRedemptionMerchantUsers(Long merchantNo,Long usrThirdPartyVendorNo,String filter, String query, Pageable pageable) throws InspireNetzException {


        authSessionUtils.validateFunctionAccess(FunctionCode.FNC_VIEW_REDEMPTION_MERCHANT_USER);

        // The list to return
        Page<User> userPage;

        Integer usrUserType = UserType.REDEMPTION_MERCHANT_USER;

        // Check the filter and query
        if ( filter.equalsIgnoreCase("username") ) {

            userPage = userRepository.findByUsrMerchantNoAndUsrUserTypeAndUsrThirdPartyVendorNoAndUsrLoginIdLike(merchantNo, usrUserType,usrThirdPartyVendorNo,"%"+query+"%",pageable);

        } else {

            userPage = userRepository.findByUsrUserTypeAndUsrThirdPartyVendorNo(usrUserType,usrThirdPartyVendorNo, pageable);

        }
        // Return the userPage
        return userPage;

    }




    @Override
    public User validateAndSaveUserData(User user) throws InspireNetzException {

        //get default merchant no
        Set<UserAccessRight> userAccessRightSet = new HashSet<>(0);

        // Check if the user is already existing
        boolean isExists = isDuplicateUserExisting(user);

        //flag for identifying password changes
        boolean isPasswordChanged = false;

        // If the user is existing, then we need to show the error message
        if ( isExists ) {

            // Log the response
            log.info("saveUser - Response : There is another user with same login id and user type");

            // Throw InspireNetzException with ERR_DUPLICATE_ENTRY as error
            throw new InspireNetzException(APIErrorCode.ERR_DUPLICATE_ENTRY);

        }

        // If the user.usrUserNO is not null and password is not empty, then we need to change
        if ( (user.getUsrUserNo() != null && user.getUsrPassword() != null && !user.getUsrPassword().equals("")) || user.getUsrUserNo() == null ) {

            boolean isPasswordValid = false;

            if(user.getUsrPassword() != null && !user.getUsrPassword().equals("")){

                isPasswordValid = securitySettingService.isPasswordValid(user,user.getUsrPassword());
            }

            if(isPasswordValid ==true){

                // Encode the password
                String encPassword = getEncodedPassword(user.getUsrLoginId(),user.getUsrPassword());

                // If the encpassword is null, then log the message
                if ( encPassword == null ) {

                    // Log the response
                    log.info("saveUser - Unable to generated the encoded password");

                    // Throw InspireNetzException with ERR_OPERATION_FAILED as error
                    throw new InspireNetzException(APIErrorCode.ERR_OPERATION_FAILED);

                }

                // Set the encoded password as the password for the user
                user.setUsrPassword(encPassword);

                //check if the user data is updating if true set incorrect attempts
                if(user.getUsrUserNo() != null){

                    User userData = findByUsrUserNo(user.getUsrUserNo());

                    user.setUsrIncorrectAttempt(userData.getUsrIncorrectAttempt());

                }

                isPasswordChanged = true;

            }

        }

        if(user.getUsrUserNo() != null && user.getUsrPassword().equals("")){

            User userData = findByUsrUserNo(user.getUsrUserNo());

            user.setUsrIncorrectAttempt(userData.getUsrIncorrectAttempt());

            user.setUsrPassword(userData.getUsrPassword());


        }

        userAccessRightSet = userAccessRightService.setUserAccessRights(user);

        user.setUserAccessRightsSet(userAccessRightSet);

        // save
        if(user.getUsrUserNo()==null){

            //set user incorrect attempts to zero
            user.setUsrIncorrectAttempt(0);

            if(user.getUsrPassword() == null || user.getUsrPassword().equals("")){

                user.setUsrPassword("");
            }

            if(user.getUsrUserType()==UserType.CUSTOMER){

                //generate unique code for user to use for referral
                String usrUserCode=generateUsrUserCode();

                //set generated unique code as user Code
                user.setUsrUserCode(usrUserCode);
            }

            //save the user data
            user = userRepository.save(user);

            //if user type is redemption merchant , add redemption merchant role
            if(user.getUsrUserType() == UserType.REDEMPTION_MERCHANT_USER){

                addRedemptionMerchantRole(user);
            }
            //if password is changed , save an entry to password history
            passwordHistoryService.savePasswordHistory(user.getUsrUserNo(),user.getUsrPassword());


        }else{

            //check if the password is changed
            if(isPasswordChanged){

                //check the user type not in merchant user or customer
                if(user.getUsrUserType() ==UserType.MERCHANT_USER || user.getUsrUserType() ==UserType.CUSTOMER){

                    //for checking encrypted password is present in history
                    SecuritySetting securitySetting = securitySettingService.getSecuritySetting(user);

                    //pass history checking  in security settings
                    Long passHistoryCount = securitySetting.getSecPwdPasHistory();

                    // password is matched for the last histories if there will matched return true otherwise throw inspire netz exception
                    boolean passHistoryMatched =passwordHistoryService.matchedPreviousHistory(passHistoryCount,user.getUsrPassword(),user.getUsrUserNo());


                }

            }

            //for checking system user value if the value present keep  that value
            User userData = findByUsrUserNo(user.getUsrUserNo());

            //for updating system user field
            user.setUsrSystemUser(userData.getUsrSystemUser());

            user =updateUser(user);

            //check if the password is changed
            if(isPasswordChanged){

                //if password is changed , save an entry to password history
                passwordHistoryService.savePasswordHistory(user.getUsrUserNo(),user.getUsrPassword());

            }

        } 
        
        //call jasper integration
        jasperUserIntegration(user);

        // Save and return the object
        return user;

    }

    private void jasperUserIntegration(User user) {

        try{

            //only we need to report user for merchant users
            if(user.getUsrUserType() ==UserType.MERCHANT_ADMIN || user.getUsrUserType() ==UserType.MERCHANT_USER){

                //call the integration
                boolean jasperServerUserCreation =integrationService.integrateJasperServerUserCreation(user,user.getUsrMerchantNo()==null?0L:user.getUsrMerchantNo());


            }

        } catch (RuntimeException e){

            log.info("RunTime Exception"+user.getUsrUserNo());

            e.printStackTrace();

        } catch (Exception e){

            //do nothing this is not affectd any operation
            log.info("Exception occured for jasper user creation UserNo:"+user.getUsrUserNo());

        }
    }

    private void addRedemptionMerchantRole(User user) throws InspireNetzException {

        // get redemption merchant role
        Role role =authSessionUtils.getDefaultRole(user);

        //throw exception if role null
        if(role ==null){

            throw new InspireNetzException(APIErrorCode.ERR_INVALID_DEFAULT_ROLE);
        }
        //create new user role
        UserRole userRole = new UserRole();

        //set user role
        userRole.setUerRole(role.getRolId());

        userRole.setUerUserId(user.getUsrUserNo());

        userRoleService.saveUserRole(userRole);


    }


    public User updateUser(User user) throws InspireNetzException {

        if(user.getUsrUserType() != UserType.REDEMPTION_MERCHANT_USER){

            //deletes removed user roles
            userRoleService.updateUserRole(user);

        } else {

            //get the user role for redemption usr
            UserRole userRole = getRedemptionMerchantRole(user);

            //add the role to a set
            Set<UserRole> userRoles = new HashSet<>();
            userRoles.add(userRole);

            //set the role set to user
            user.setUserRoleSet(userRoles);

        }
        //deletes removed user access locations
        userAccessLocationService.updateUserAccessLocation(user);

        //deletes removed user access rights
        userAccessRightService.updateUserAccessRights(user);

        //saving the user and user lists
        user=userRepository.save(user);

        log.info("user is successfully updated---"+user.getUsrUserNo());

        return user;

    }


    /*
    Method returns the role for redemption voucher
    * */
    private UserRole getRedemptionMerchantRole(User user) throws InspireNetzException {

        // get redemption merchant role
        Long roleId =user.getUsrDefaultRole() ==null?0L:user.getUsrDefaultRole();

        //find role information
        Role role=roleService.findByRolId(roleId);

        UserRole userRole = new UserRole();

        if(userRole !=null){

            //set the user role as RedemptionMerchantUser
            userRole.setUerRole(role.getRolId());

            //set user no to user role
            userRole.setUerUserId(user.getUsrUserNo());

            //save user role
            userRole = userRoleService.saveUserRole(userRole);
        }

        //return user role
        return userRole;


    }



    @Override
    public boolean deleteUser(User user) throws InspireNetzException {

        // Delete the user
        userRepository.delete(user);

        // Return false
        return false;

    }

    @Override
    public User validateAndSaveUser(User user) throws InspireNetzException {

        //check the user type is super admin or web admin
        if(authSessionUtils.getUserType() ==UserType.SUPER_ADMIN || authSessionUtils.getUserType() ==UserType.ADMIN){

            //save user type based on user role
            Role role =authSessionUtils.getDefaultRole(user);

            //set the user type of user based on role default role is only available in super admin or admin
            if(role !=null){

                //if the default role is not null
                user.setUsrUserType(role.getRolUserType().intValue());
            }

        }else if(authSessionUtils.getUserType() ==UserType.MERCHANT_ADMIN && user.getUsrUserType() !=UserType.REDEMPTION_MERCHANT_USER){

            //only merchant admin is create merchant user
            user.setUsrUserType(UserType.MERCHANT_USER);

            //check the max user
            Merchant merchant =merchantService.findByMerMerchantNo(authSessionUtils.getMerchantNo());

            if(merchant !=null && user.getUsrUserNo() ==null){

                checkMaxUserExceed(merchant);
            }
        }

        //check the user access rights
        authSessionUtils.validateFunctionAccess(FunctionCode.FNC_UPDATE_MERCHANT_USER);

        return validateAndSaveUserData(user);

    }

    private void checkMaxUserExceed(Merchant merchant) throws InspireNetzException {

        Integer maxUser =merchant.getMerMaxUsers();

        if(maxUser !=null){

            List<User> userList = findByUsrUserTypeAndUsrMerchantNo(UserType.MERCHANT_USER,merchant.getMerMerchantNo());

            if(userList.size()>= maxUser){

                throw new InspireNetzException(APIErrorCode.ERR_REACHED_MAX_USER);
            }
        }
    }

    @Override
    public boolean validateAndDeleteUser(User user) throws InspireNetzException {

        //check the user access rights
        authSessionUtils.validateFunctionAccess(FunctionCode.FNC_UPDATE_MERCHANT_USER);

        return deleteUser(user);
    }

    @Override
    public User getUserDataByUsrUserNo(Long usrUserNo) throws InspireNetzException {

        // Get User object
        User user = userRepository.findByUsrUserNo(usrUserNo);

        //lazily loading user roles
        user.getUserRoleSet().toString();

        //lazily loading user access right set
        user.getUserAccessRightsSet().toString();

        //getting user accessrights for getting access overriden items only
        Set<UserAccessRight> userAccessRights = user.getUserAccessRightsSet();

        //lazily loading user access locations
        user.getUserAccessLocationSet().toString();

        Set<UserAccessRight> userAccessRightSetToSent = new HashSet<>(0);

        //checks whether the access right is overriden if yes adds it to the set which
        //will be sent as return data
        if(userAccessRights != null){

            for(UserAccessRight userAccessRight: userAccessRights){

                if(userAccessRight.getUarAccessOverridenFlag() != null && userAccessRight.getUarAccessOverridenFlag().intValue() == 1){

                    userAccessRightSetToSent.add(userAccessRight);

                }

            }

        }


        user.setUserAccessRights(userAccessRightSetToSent);

        // Update the related fields
        user = setRelatedFieldsForEntity(user);

        // Return  the user
        return user;

    }

    @Override
    public String getRegistrationAuthenticationKey(String userName, String password) {

        String registrationKey = "";

        //get the key
        String key = generalUtils.getFixedKeyForRegistrationAuthentication();

        try {

            // Get the MessageDigest instnace
            MessageDigest messageDigest = null;

            // Get the MD5 instance
            messageDigest = MessageDigest.getInstance("MD5");

            // Create the password string
            String plainString = userName+":"+password+":"+key;

            // Create the byteArray
            byte digest[] = messageDigest.digest(plainString.getBytes("UTF-8"));

            // Create the string builder with double the size of the digest
            StringBuilder sb = new StringBuilder(2*digest.length);

            // Go through each of the byte and convert to hexadecimal
            for(byte b : digest){

                sb.append(String.format("%02x", b&0xff));

            }

            // Get the encodedPassword from the StringBuilder object
            registrationKey =sb.toString();


        } catch (NoSuchAlgorithmException e) {

            // Log as error
            log.info("getRegistrationAuthenticationKey -> No such alogirhtm ");

            // Print stack trace
            e.printStackTrace();

        } catch (UnsupportedEncodingException e) {

            // Log as error
            log.info("getRegistrationAuthenticationKey -> Unsupported encoding exception ");

            // Print stack trace
            e.printStackTrace();

        }

        return registrationKey;


    }

    /**
     * @purpose checking account is Active or not
     * @param usrUsrNo
     * @return boolean
     * @throws InspireNetzException
     */

    @Override
    public boolean isAccountLockout(Long usrUsrNo) throws InspireNetzException {

        //get user information
        User user =findByUsrUserNo(usrUsrNo);

        //check user null or not if null
        if(user ==null){

            //return false
            return false;
        }

        //check the user type other than merchant user , customer
        if(user.getUsrUserType() ==UserType.ADMIN ||user.getUsrUserType() ==UserType.SUPER_ADMIN||user.getUsrUserType()==UserType.MERCHANT_ADMIN){

            //return false
            return false;
        }

        //get security settings based on merchant number
        SecuritySetting securitySetting =securitySettingService.getSecuritySetting(user);

        //get password lockout information
        Long pwdLockout = securitySetting.getSecPwdLockout();

        if(pwdLockout !=null){

            // if current attempt count  is greater than security settings lockout count  return true otherwise return false
            if(user.getUsrIncorrectAttempt()> pwdLockout){

                return  true;

            }else{

                return  false;
            }
        }

        return false;
    }


    /**
     * @purpose:increment the in valid attempt for login
     * @param usrUsrNo
     * @return boolean
     */
    @Override
    public boolean incrementInvalidAttempt(Long usrUsrNo) {

        User user = userRepository.findByUsrUserNo(usrUsrNo);

        Integer invalidAttempt = user.getUsrIncorrectAttempt();

        if(invalidAttempt !=null){

            user.setUsrIncorrectAttempt(invalidAttempt+1);

            userRepository.save(user);

            return true;
        }

        return  false;

    }

    /**
     * @purpose:set invalid attempt field  to zero after successful login
     * @param usrUsrNo
     * @return boolean
     */
    @Override
    public boolean clearValidAttempt(Long usrUsrNo) {

        User user = userRepository.findByUsrUserNo(usrUsrNo);

        Integer invalidAttempt = user.getUsrIncorrectAttempt();

        if(invalidAttempt !=null){

            user.setUsrIncorrectAttempt(0);

            userRepository.save(user);

            return true;
        }

        return  false;

    }

    /**
     * Purpose:saving FName and LName for change of user profile
     * @param user
     * @return
     */
    @Override
    public User saveFNameAndLName(User user) {

        return userRepository.save(user);
    }


    /**
     * purpose:for update password when user use forget password option for customer
     * @param
     * @return
     */

    @Override
    public User forgetPasswordUpdation(String cusLoyaltyId,String newPassword,String otpCode) throws InspireNetzException {

        //get default merchant no
        Long merchantNo = generalUtils.getDefaultMerchantNo();

        Customer customer = customerService.findByCusLoyaltyIdAndCusMerchantNo(cusLoyaltyId,merchantNo);

        User user = userRepository.findByUsrUserNo(customer.getCusUserNo());

        if(user !=null && customer !=null){


            //check whether the otp is valid or not
            //Integer isOtpValid = oneTimePasswordService.validateOTP(merchantNo, customer.getCusCustomerNo(), OTPType.FORGOT_PASSWORD, otpCode);
            Integer isOtpValid =oneTimePasswordService.validateOTPGeneric(merchantNo,OTPRefType.CUSTOMER,customer.getCusCustomerNo().toString(),OTPType.FORGOT_PASSWORD,otpCode);

            //Check th response status
            if(isOtpValid.intValue() == OTPStatus.VALIDATED){

            //check password is valid for security settings
            boolean passwordIsValid = securitySettingService.isPasswordValid(user,newPassword);

            if(passwordIsValid ==true){

                // Encode the password
                String encPassword = getEncodedPassword(user.getUsrLoginId(),newPassword);

                // If the encpassword is null, then log the message
                if ( encPassword == null ) {

                    // Log the response
                    log.info("forgetPasswordUpdation - Unable to generated the encoded password");

                    // Throw InspireNetzException with ERR_OPERATION_FAILED as error
                    throw new InspireNetzException(APIErrorCode.ERR_OPERATION_FAILED);

                }

                // Set the encoded password as the password for the user
                user.setUsrPassword(encPassword);

                //save the user data
                user = userRepository.save(user);

                //if password is changed , save an entry to password history
                passwordHistoryService.savePasswordHistory(user.getUsrUserNo(),user.getUsrPassword());

                return user;

            }


          } else if(isOtpValid.intValue() == OTPStatus.OTP_NOT_VALID){

                //throw exception
                throw new InspireNetzException(APIErrorCode.ERR_INVALID_OTP);

            } else {

                throw new InspireNetzException(APIErrorCode.ERR_OTP_EXPIRED);

            }

        }
        return user;
    }

    @Override
    public boolean generateOtp(String cusLoyaltyId) throws InspireNetzException{

        //get default merchant no
        Long merchantNo = generalUtils.getDefaultMerchantNo();

        //check customer is valid user
        User user = userRepository.findByUsrLoginId(cusLoyaltyId);

        Customer customer = customerService.findByCusLoyaltyIdAndCusMerchantNo(cusLoyaltyId,merchantNo);

        if(user !=null && customer !=null){

            /*
            //To support generateOTPGeneric
            //generate otp for validation
            String  otpCode = oneTimePasswordService.generateOTP(merchantNo,customer.getCusCustomerNo(), OTPType.FORGOT_PASSWORD);


            //create a map for the sms placeholders
            HashMap<String , String > smsParams  = new HashMap<>(0);

            //put the placeholders into the map
            smsParams.put("#otpCode",otpCode);

            //send the otp to the user
            userMessagingService.sendSMS(MessageSpielValue.CUSTOMER_FORGOT_PASSWORD_OTP,cusLoyaltyId,smsParams);*/

            boolean isOTPGenerated= oneTimePasswordService.generateOTPGeneric(merchantNo,OTPRefType.CUSTOMER,customer.getCusCustomerNo().toString(),OTPType.FORGOT_PASSWORD);


            return isOTPGenerated;
        }else{

            // Log the response
            log.info("generate otp invalid customer");

            // Throw InspireNetzException with ERR_OPERATION_FAILED as error
            throw new InspireNetzException(APIErrorCode.ERR_USER_NOT_FOUND);
        }

    }

    @Override
    public boolean generateUserRegistrationOtp(String usrLoginId,Long merchantNo) throws InspireNetzException{

        //get default merchant no
        if(merchantNo ==0L){

            merchantNo = generalUtils.getDefaultMerchantNo();
        }
        //check customer is valid user
        User user = userRepository.findByUsrLoginId(usrLoginId);

        if(user !=null){


            boolean isOTPGenerated= oneTimePasswordService.generateOTPGeneric(merchantNo,OTPRefType.USER,user.getUsrUserNo().toString(),OTPType.REGISTER_CUSTOMER);


            return isOTPGenerated;
        }else{

            // Log the response
            log.info("generateForgetPasswordOtp invalid user");

            // Throw InspireNetzException with ERR_OPERATION_FAILED as error
            throw new InspireNetzException(APIErrorCode.ERR_USER_NOT_FOUND);
        }

    }



    @Override
    public User getRegistrationStatus(String usrLoginId) {

        //get the user details
        User user = findByUsrLoginId(usrLoginId);

        User user1 = new User();

        if(user != null){

            //set user registration status to a new object
            user1.setUsrRegisterStatus(user.getUsrRegisterStatus());
            user1.setUsrUserNo(user.getUsrUserNo());

        }

        //return the new user object
        return user1;
    }

    @Override
    public List<UserAccessRight> getUserAccessRightsByUserNo(Long uarUserNo) throws InspireNetzException {

        //get the user details
        User user = getUserDataByUsrUserNo(uarUserNo);

        //get the user's available roles
        Set<UserRole> userRoles = user.getUserRoleSet();

        List<UserAccessRight> userAccessRights = new ArrayList<>();

        List<RoleAccessRight> roleAccessRights = null;

        UserAccessRight userAccessRight = null;

        //get the access rights for all the roles assigned to the user
        for(UserRole userRole : userRoles){

            //get the list of available access rights for the role
            roleAccessRights = roleAccessRightService.findByRarRole(userRole.getUerRole());

            for(RoleAccessRight roleAccessRight : roleAccessRights){

                userAccessRight= new UserAccessRight();

                //add the access right to accessRight list
                userAccessRight.setUarFunctionCode(roleAccessRight.getRarFunctionCode());
                userAccessRight.setUarAccessEnableFlag("Y");
                userAccessRights.add(userAccessRight);
            }

        }

        //check the ovverridden access rights
        for(UserAccessRight userAccessRight1 : userAccessRights){

            for(UserAccessRight userAccessRight2 : user.getUserAccessRights()){

                //if the access right is overridden then set the overriden access enable flag
                if(userAccessRight1.getUarFunctionCode().longValue() == userAccessRight2.getUarFunctionCode().longValue()){

                    userAccessRight1.setUarAccessEnableFlag(userAccessRight2.getUarAccessEnableFlag());

                    userAccessRight2.setAddedTolList(true);
                }

            }

        }

        //add all access rights that are overridden and not present with the roles
        //to the user access rights list
        for(UserAccessRight userAccessRight3 : user.getUserAccessRights()){

            if(!userAccessRight3.isAddedTolList()){

                userAccessRights.add(userAccessRight3);
            }
        }

        return userAccessRights;

    }

    @Override
    public User saveUser(User user) {

        return userRepository.save(user);
    }

    /**
     * @Puropse:find user based on redemption merchant
     * @param thirdPartyVendorNo
     * @return:userList
     * @date:10-12-2014
     */
    @Override
    public List<User> findByUsrThirdPartyVendorNo(Long thirdPartyVendorNo) {
        
        //get user type
        Integer userType= UserType.REDEMPTION_MERCHANT_USER;

        List<User> userList = userRepository.findByUsrUserTypeAndUsrThirdPartyVendorNo(userType,thirdPartyVendorNo);


        return userList;
    }

    public User createUserObject(String usrFName, String usrLName, String usrLoginId,String usrMobile, String usrEmail,String usrPassword,Long usrMerchantNo){

        User user=new User();

        user.setUsrFName(usrFName);
        user.setUsrLName(usrLName);

        //set user details
        user.setUsrLoginId(usrLoginId);

        user.setUsrPassword(usrPassword);

        //set user mobile no
        user.setUsrMobile(usrMobile);

        user.setUsrEmail(usrEmail);

        user.setUsrMerchantNo(usrMerchantNo);

        return user;
    }

    @Override
    public boolean registerUser(String usrFName, String usrLName, String usrLoginId, String usrEmail,String usrPassword,Integer channel,Long merchantNo) throws InspireNetzException {

        //check current api call is exclusive or not if exclusive get  merchantNo otherwise set default merchant no
        if(merchantNo ==0L){

            merchantNo = generalUtils.getDefaultMerchantNo();
        }
        //create a hashmap for sms parameters
        HashMap<String , String > smsParams = new HashMap<>(0);

        //put the placeholders into the map
        smsParams.put("#min",usrLoginId);

        User user=createUserObject(usrFName,usrLName,usrLoginId,usrLoginId,usrEmail,usrPassword,merchantNo);

        //check another user exists with same userLoginId
        user = checkDuplicateUserExistence(user);

        //if user is registered but not validated
        if(user != null && user.getUsrRegisterStatus().intValue() == 0){

            user.setUsrFName(usrFName);
            user.setUsrLName(usrLName);
            user.setUsrPassword(usrPassword);
            user.setUsrMobile(usrLoginId);
            user.setUsrEmail(usrEmail);
            user.setUsrMerchantNo(merchantNo);
            user=validateAndSaveUserData(user);

        } else if( user != null && user.getUsrRegisterStatus() == 1){

            //log the error
            log.error("registerUser : User Already registered");

            MessageWrapper messageWrapper = generalUtils.getMessageWrapperObject(MessageSpielValue.CUSTOMER_REGISTRATION_DUPLICATE_REGISTER,"",usrLoginId,usrEmail,"Registration Status",merchantNo,smsParams,MessageSpielChannel.SMS,IndicatorStatus.NO);


            userMessagingService.transmitNotification(messageWrapper);

            // Throw exception
            throw new InspireNetzException(APIErrorCode.ERR_USER_ALREADY_EXIST);

        } else{

            user = new User();
            //set values to the user entity
            user.setUsrLoginId(usrLoginId);
            user.setUsrFName(usrFName);
            user.setUsrLName(usrLName);
            user.setUsrMobile(usrLoginId);
            user.setUsrEmail(usrEmail);
            user.setUsrPassword(usrPassword);
            user.setUsrUserType(UserType.CUSTOMER);
            user.setUsrStatus(UserStatus.ACCOUNT_CREATED);
            user.setUsrRegisterStatus(IndicatorStatus.NO);
            user.setUsrMerchantNo(merchantNo);
            //save the user account
            user = validateAndSaveUserData(user);


        }
        // check if the customer is null
        if ( user == null || user.getUsrUserNo() == null ) {

            // Log the information
            log.error("registerCustomer -> Unable to save the user");

            MessageWrapper messageWrapper = generalUtils.getMessageWrapperObject(MessageSpielValue.GENERAL_ERROR_MESSAGE,"",usrLoginId,usrEmail,"Registration Status",merchantNo,smsParams,MessageSpielChannel.SMS,IndicatorStatus.NO);


            userMessagingService.transmitNotification(messageWrapper);


            // Throw exception
            throw new InspireNetzException(APIErrorCode.ERR_OPERATION_FAILED);

        }

        boolean isOTPGenerated=oneTimePasswordService.generateOTPGeneric(user.getUsrMerchantNo(),OTPRefType.USER,user.getUsrUserNo().toString(),OTPType.REGISTER_CUSTOMER);

        return isOTPGenerated;

    }

    protected String getRedeemableItemsList(Page<Catalogue> catalogues){


        String catalogueList = "";

       /* //variable for storing count of items added to list
        int i = 0;
*/
        // Iterate through the list and then populate the catalogue data
        for( Catalogue catalogue : catalogues ) {

            //add item details to the list
            catalogueList += "ITEM CODE - "+catalogue.getCatProductCode() + " :  ITEM NAME - "+catalogue.getCatDescription() + "\n";

           /* //increment items count
            i++;

            //if 5 items are added , break from the loop
            if(i > 4){

                break;
            }*/
        }

        //check the size of the catalogue list , if its empty add no catalogue message
        if(catalogueList.equals("") || catalogueList.length() == 0){

            catalogueList = "No redeemable items found ";
        }

        return catalogueList;
    }

    @Override
    public boolean confirmUserRegistration(String usrLoginId, String otpCode,Integer channel,Long exclusiveMerchantNo) throws InspireNetzException {

        Long merchantNo =0L;

        //get default merchant no
        if(exclusiveMerchantNo.longValue() ==0L){

            merchantNo = generalUtils.getDefaultMerchantNo();

        }else {

            merchantNo =exclusiveMerchantNo;
        }

        //get the user details
        User user = findByUsrLoginId(usrLoginId);

        // Check if the user is null
        if(user == null ||user.getUsrUserNo()==null){

            //log the error
            log.error("No User Information Found");

            //throw exception
            throw new InspireNetzException(APIErrorCode.ERR_NO_DATA);

        }

        //check whether the otp is valid or not
        Integer isOtpValid =oneTimePasswordService.validateOTPGeneric(merchantNo,OTPRefType.USER,user.getUsrUserNo().toString(),OTPType.REGISTER_CUSTOMER,otpCode);

        //Check th response status
        if(isOtpValid.intValue() == OTPStatus.VALIDATED){

            // Set the user status to active
            user.setUsrStatus(UserStatus.ACTIVE);

            user.setUsrRegisterStatus(IndicatorStatus.YES);

            user.setUsrMobile(user.getUsrLoginId());

            //generate unique code for user to use for referral
            String usrUserCode=generateUsrUserCode();

            //set generated unique code as user Code
            user.setUsrUserCode(usrUserCode);

            saveUser(user);

            //normal connection
            List<Customer> customers=customerService.findByCusMobile(user.getUsrLoginId());

            //link customers to this user
            for(Customer customer:customers){

                  //set customer location and merchant user is 0
                  customer.setCusLocation(0L);

                  customer.setCusMerchantUserRegistered(0L);

                  //connected customer into merchant
                  customerService.connectMerchant(customer.getCusMerchantNo(),customer,CustomerRegisterType.CUSTOMER_PORTAL);

            }

            //check request is coming from exclusive merchant
            if(merchantNo.longValue() !=0L){

                //check the setting available for auto_customer
                boolean autoCustomerEnabled = merchantSettingService.isSettingEnabledForMerchant(AdminSettingsConfiguration.MER_ENABLE_AUTO_CUSTOMER_VIA_EXCLUSIVE,merchantNo);

                //if settings enable process auto signup
                if(autoCustomerEnabled){

                    //find the customer is already register or not
                    Customer customer =customerService.findByCusMobileAndCusMerchantNo(user.getUsrLoginId(),merchantNo);

                    //check customer is null or unregister then connect customer
                    if(customer ==null){

                        //create customer object
                        customer =customerService.createCustomerObjectForLoyaltyId(user.getUsrLoginId(),user.getUsrLoginId(),user.getUsrEmail(),user.getUsrFName(),merchantNo,0L,0L,"");

                        //connect merchant
                        customerService.connectMerchant(merchantNo,customer,CustomerRegisterType.CUSTOMER_PORTAL);

                    }else if(customer.getCusCustomerNo() !=null && customer.getCusRegisterStatus() ==IndicatorStatus.NO){

                        //reregister the customer
                        customerService.connectMerchant(customer.getCusMerchantNo(),customer,CustomerRegisterType.CUSTOMER_PORTAL);

                    }
                }

            }

            List<Catalogue> cataloguesList=new ArrayList<>();

            // Create the list of catalogue
            String catalogueList = "";

            //create a hashmap for sms parameters
            HashMap<String , String > smsParams = new HashMap<>(0);

            //check channel for catalogue send when sms
            if(channel==RequestChannel.RDM_CHANNEL_SMS){

                List<Customer> customerList=customerService.getUserMemberships(0L,user.getUsrUserNo(),CustomerStatus.ACTIVE);

                for(Customer customer:customerList){

                    Page<Catalogue> catalogues = catalogueService.searchCatalogueByCurrencyAndCategory(customer.getCusMerchantNo(),0L,0,customer.getCusLoyaltyId(),RequestChannel.RDM_WEB,new PageRequest(0,100));

                    cataloguesList.addAll(Lists.newArrayList((Iterable<Catalogue>)catalogues));
                }

            //get the redeemable items
            catalogueList = getRedeemableItemsList(new PageImpl<>(cataloguesList));

            //put the list into smsparams
            smsParams.put("#catalogueList",catalogueList);

            }

            //get the message wrapper object
            MessageWrapper messageWrapper = generalUtils.getMessageWrapperObject(MessageSpielValue.CUSTOMER_REGISTRATION_SUCCESS,user.getUsrMobile(),user.getUsrMobile(),"","",merchantNo,smsParams,MessageSpielChannel.SMS,IndicatorStatus.NO);

            //check merchant number is equal to default merchant No
            if(merchantNo.longValue() !=generalUtils.getDefaultMerchantNo().longValue()){

                messageWrapper.setIsCustomer(IndicatorStatus.NO);
            }

            //send sms
            userMessagingService.transmitNotification(messageWrapper);

            // finally return true
            return true;

        } else if(isOtpValid.intValue() == OTPStatus.OTP_NOT_VALID){

            //throw exception
            throw new InspireNetzException(APIErrorCode.ERR_INVALID_OTP);

        } else {

            throw new InspireNetzException(APIErrorCode.ERR_OTP_EXPIRED);

        }
    }

    @Override
    public boolean generateForgetPasswordOtp(String usrLoginId,Long merchantNo) throws InspireNetzException{

        //get default merchant no
        if(merchantNo ==0L){

            merchantNo = generalUtils.getDefaultMerchantNo();
        }
        //check customer is valid user
        User user = userRepository.findByUsrLoginId(usrLoginId);

        if(user !=null && (user.getUsrStatus().intValue()==UserStatus.ACTIVE||user.getUsrStatus().intValue()==UserStatus.PASSWORD_EXPIRED)){


            boolean isOTPGenerated= oneTimePasswordService.generateOTPGeneric(merchantNo,OTPRefType.USER,user.getUsrUserNo().toString(),OTPType.FORGOT_PASSWORD);


            return isOTPGenerated;
        }else{

            // Log the response
             log.info("generateForgetPasswordOtp invalid user");

            // Throw InspireNetzException with ERR_OPERATION_FAILED as error
            throw new InspireNetzException(APIErrorCode.ERR_USER_NOT_FOUND);
        }

    }

    @Override
    public User forgetUserPasswordOtpValidation(String usrLoginId, String newPassword, String otpCode,Long merchantNo) throws InspireNetzException {

        //get default merchant no otherwise its exclusive merchant
        if(merchantNo ==0L){

            merchantNo = generalUtils.getDefaultMerchantNo();
        }

        User user = userRepository.findByUsrLoginId(usrLoginId);

        if(user !=null && (user.getUsrStatus().intValue()==UserStatus.ACTIVE||user.getUsrStatus().intValue()==UserStatus.PASSWORD_EXPIRED)){


            //check whether the otp is valid or not
            Integer isOtpValid =oneTimePasswordService.validateOTPGeneric(merchantNo,OTPRefType.USER,user.getUsrUserNo().toString(),OTPType.FORGOT_PASSWORD,otpCode);


            //Check th response status
            if(isOtpValid.intValue() == OTPStatus.VALIDATED){

                //check password is valid for security settings
                boolean passwordIsValid = securitySettingService.isPasswordValid(user, newPassword);

                if(passwordIsValid ==true){

                    // Encode the password
                    String encPassword = getEncodedPassword(user.getUsrLoginId(),newPassword);

                    // If the encpassword is null, then log the message
                    if ( encPassword == null ) {

                        // Log the response

                        log.info("forgetPasswordUpdation - Unable to generated the encoded password");


                        // Throw InspireNetzException with ERR_OPERATION_FAILED as error
                        throw new InspireNetzException(APIErrorCode.ERR_OPERATION_FAILED);

                    }
                    // Set the encoded password as the password for the user
                    user.setUsrPassword(encPassword);

                    //save the user data
                    user = userRepository.save(user);

                    //if password is changed , save an entry to password history
                    passwordHistoryService.savePasswordHistory(user.getUsrUserNo(),user.getUsrPassword());


                    return user;

                }

            } else if(isOtpValid.intValue() == OTPStatus.OTP_NOT_VALID){

                //throw exception
                throw new InspireNetzException(APIErrorCode.ERR_INVALID_OTP);

            } else {

                throw new InspireNetzException(APIErrorCode.ERR_OTP_EXPIRED);

            }

        }else{

            // Log the response
            log.info("generateForgetPasswordOtp invalid user");

            // Throw InspireNetzException with ERR_OPERATION_FAILED as error
            throw new InspireNetzException(APIErrorCode.ERR_USER_NOT_FOUND);
        }

        return user;
    }

    @Override
    public List<Map<String, Object>> getUserMemberships(Long usrMerchantNo, String usrLoginId,String filter,String query) throws InspireNetzException{


        //create list for returning membership data
        List<Map<String , Object> > membershipList = new ArrayList<>();

        //get user object using userLoginId
        User user=findByUsrLoginId(usrLoginId);

        if(user==null||user.getUsrUserNo()==null){

            //log the error
            log.error("No User Information Found");

            //throw exception
            return membershipList;
        }

        List<Customer> customers=customerService.getUserMemberships(usrMerchantNo,user.getUsrUserNo(),CustomerStatus.ACTIVE);

        if(customers==null ||customers.isEmpty()){

            //log the error
            log.error("No Customer Information Found");

            return membershipList;

        }

        for(Customer customer:customers){

            Merchant merchant=null;

            if ( filter.equals("0") && query.equals("0") ) {

                merchant=merchantService.findByMerMerchantNo(customer.getCusMerchantNo());

            } else if ( filter.equalsIgnoreCase("name") ) {

                merchant=merchantService.findByMerMerchantNoAndMerMerchantNameLike(customer.getCusMerchantNo(),query);

            }

            if(merchant==null || merchant.getMerMerchantNo()==null)
            {
                //log the error
                log.error("No merchnat Information Found");

                continue;

            }

            //map holds the merhant details
            Map<String ,Object > membershipData = new HashMap<>();

            //put the merchant details to the map
            membershipData.put("merMerchantNo",merchant.getMerMerchantNo()+"");
            membershipData.put("merMerchantName",merchant.getMerMerchantName());
            membershipData.put("cusLoyaltyId",customer.getCusLoyaltyId());
            membershipData.put("mobileOrderEnabled","0");

            // Get the Image
            Image imgLogo = merchant.getImgLogo();

            //get the cover image of the merchant
            Image coverImage = merchant.getImgCoverImage();

            //variable for image path
            String imagePath = "";

            //variable for cover image path
            String coverImagePath = "";


            // If the logo is not null, then set the page
            if ( imgLogo != null ) {

                // Get the imagePath
                imagePath = imageService.getPathForImage(imgLogo, ImagePathType.STANDARD);

            }

            // If the logo is not null, then set the page
            if ( coverImage != null ) {


                //get the cover image path
                coverImagePath = imageService.getPathForImage(coverImage, ImagePathType.STANDARD);
            }

            //set image path
            membershipData.put("merMerchantImagePath", imagePath);

            //set cover image path
            membershipData.put("merCoverImagePath", coverImagePath);

            //set image id
            membershipData.put("merMerchantImage",merchant.getMerMerchantImage()+"");

            //set cover image id
            membershipData.put("merCoverImage",merchant.getMerCoverImage()+"");

            //add contact details
            membershipData.put("merPhoneNo",merchant.getMerPhoneNo());

            //add email to membership data
            membershipData.put("merEmail",merchant.getMerEmail());

            membershipData.put("merContactName",merchant.getMerContactName());

            membershipData.put("merContactEmail",merchant.getMerContactEmail());

            //get the merchant address
            String address = merchant.getMerAddress1() == null ? "":merchant.getMerAddress1()+
                    merchant.getMerAddress2() == null ? "":merchant.getMerAddress2()+
                    merchant.getMerAddress3() == null ? "":merchant.getMerAddress3()+
                    merchant.getMerCity() == null ? "":merchant.getMerCity()+
                    merchant.getMerState() == null ? "":merchant.getMerState();

            //add merchant address to membership data
            membershipData.put("merAddress",address);

            //add merchant address to membership data
            membershipData.put("merAddress1",merchant.getMerAddress1());

            //add merchant address to membership data
            membershipData.put("merAddress2",merchant.getMerAddress2());

            //add merchant address to membership data
            membershipData.put("merAddress3",merchant.getMerAddress3());

            //add merchant address to membership data
            membershipData.put("merCity",merchant.getMerCity());

            //add merchant address to membership data
            membershipData.put("merState",merchant.getMerState());

            //add merchant address to membership data
            membershipData.put("merCountry",merchant.getMerCountry());

            //variable holding the reward balance
            String displayPoints  = "0";

            String displayCurrency="";

            //get the reward balance list of the customer
            List<CustomerRewardBalance> customerRewardBalanceList = customerRewardBalanceService.getBalanceList(customer.getCusMerchantNo(),customer.getCusLoyaltyId());

            //check if customer has any reward balance
            if(customerRewardBalanceList.size() > 0){

                //set reward balance to the field
                displayPoints = customerRewardBalanceList.get(0).getCrbRewardBalance()+"";

                //Set reward Currency
                RewardCurrency rewardCurrency=customerRewardBalanceList.get(0).getRewardCurrency();

                displayCurrency=rewardCurrency.getRwdCurrencyName();

            }

            //add reward balance info to membership data
            membershipData.put("crbRewardBalance",displayPoints);

            membershipData.put("rwdCurrencyName",displayCurrency);

            //load merchant locations
            merchant.getMerchantLocationSet().toString();

            //list for holding the merchant locations
            List<Map<String , String>> locationList = new ArrayList<>();

            Map<String, String > location = new HashMap<>();

            //iterate through the merchant locations,and add them to the list
            for(MerchantLocation merchantLocation : merchant.getMerchantLocationSet()){

                location = new HashMap<>();

                //put the location details in to the map
                location.put("location",merchantLocation.getMelLocation());
                location.put("latitude",merchantLocation.getMelLatitude());
                location.put("longitude",merchantLocation.getMelLongitude());

                //add map object to the list
                locationList.add(location);

            }

            //add location details list to membership data
            membershipData.put("merLocations",locationList);

            //add the current merchant details to the list
            membershipList.add(membershipData);

        }

        return membershipList;
    }

    @Override
    public User changeAccountInformation(UserResource userResource) throws InspireNetzException {

        //find the user information based on auth session
        String userLoginId =authSessionUtils.getUserLoginId();

        //boolean
        boolean isPasswordChanged =false;

        //find user information
        User user =findByUsrLoginId(userLoginId);

        if(user ==null){

            log.info("UserServiceImpl ->changeAccountInformation::invalid user");

            throw new InspireNetzException(APIErrorCode.ERR_NOT_AUTHORIZED);
        }

        //check user is change password
        Integer usrChangePassword = userResource.getUsrChangePassword()==null?IndicatorStatus.NO:userResource.getUsrChangePassword();

        if(usrChangePassword ==IndicatorStatus.YES){

            // Encode the password old password
            String oldPassword = getEncodedPassword(user.getUsrLoginId(),userResource.getUsrOldPassword());

            //if not matched throw error password not matched
            if(!user.getUsrPassword().equals(oldPassword)){

                throw new InspireNetzException(APIErrorCode.ERR_OLD_PASSWORD_NOT_MATCHED);
            }

            //check the password field is not null
            if(userResource.getUsrPassword() !=null ||!userResource.getUsrPassword().equals("")){

                //check the password is valid or not
                boolean isPasswordValid = securitySettingService.isPasswordValid(user, userResource.getUsrPassword());

                if(isPasswordValid ==true){

                    // Encode the password
                    String encPassword = getEncodedPassword(user.getUsrLoginId(),userResource.getUsrPassword());

                    // If the encpassword is null, then log the message
                    if ( encPassword == null ) {


                        log.info("changeAccountInformation - Unable to generated the encoded password");


                        // Throw InspireNetzException with ERR_OPERATION_FAILED as error
                        throw new InspireNetzException(APIErrorCode.ERR_OPERATION_FAILED);

                    }

                    // Set the encoded password as the password for the user
                    user.setUsrPassword(encPassword);

                    //check the user is not
                    if(user.getUsrUserType() ==UserType.CUSTOMER || user.getUsrUserType() ==UserType.MERCHANT_USER){

                        //for checking encrypted password is present in history
                        SecuritySetting securitySetting = securitySettingService.getSecuritySetting();

                        //check if not null
                        if(securitySetting.getSecPwdPasHistory() !=null){

                            Long passHistoryCount = securitySetting.getSecPwdPasHistory();

                            passwordHistoryService.matchedPreviousHistory(passHistoryCount,user.getUsrPassword(),user.getUsrUserNo());

                        }


                    }


                    isPasswordChanged=true;


                }

            }
        }

        //else map with data with current information
        user.setUsrFName(userResource.getUsrFName());

        user.setUsrLName(userResource.getUsrLName());

        user.setUsrProfilePic(userResource.getUsrProfilePic());

        //save user
        saveUser(user);

        if(isPasswordChanged){

            //if password is changed , save an entry to password history
            passwordHistoryService.savePasswordHistory(user.getUsrUserNo(),user.getUsrPassword());

        }

        return user;

    }

    @Override
    public User validateAndSaveUserFromMerchant(User user) throws InspireNetzException {

        String usrLoginId=user.getUsrLoginId();

        Long merchantNo=user.getUsrMerchantNo();

        if(merchantNo==0L){

            merchantNo=generalUtils.getDefaultMerchantNo();
        }

        // Check if the user is already existing
        boolean isExists = isDuplicateUserExisting(user);

        // If the user is existing, then we need to show the error message
        if ( isExists ) {

            // Log the response
            log.info("saveUser - Response : There is another user with same login id and user type");

            // Throw InspireNetzException with ERR_DUPLICATE_ENTRY as error
            throw new InspireNetzException(APIErrorCode.ERR_DUPLICATE_ENTRY);

        }

        //temporary password

        //generating a 6 digit random number between 100000 and 999999
        Random random = new Random();

        int randomNumber = 100000 + random.nextInt(900000);

        String uniquePassword =randomNumber+"";

        //generate password
        String encPassword = getEncodedPassword(user.getUsrLoginId(),uniquePassword);

        // If the encpassword is null, then log the message
        if ( encPassword == null ) {


            log.info("validateAndSaveUserFromMerchant - Unable to generated the encoded password");

            // Throw InspireNetzException with ERR_OPERATION_FAILED as error
            throw new InspireNetzException(APIErrorCode.ERR_OPERATION_FAILED);

        }

        // Set the encoded password as the password for the user
        user.setUsrPassword(encPassword);

        //set user registration is active
        user.setUsrRegisterStatus(IndicatorStatus.YES);
        user.setUsrUserType(UserType.CUSTOMER);
        user.setUsrStatus(UserStatus.ACTIVE);
        user.setUsrIsSystemGeneratePassword(IndicatorStatus.YES);

        //generate unique code for user to use for referral
        String usrUserCode=generateUsrUserCode();

        //set generated unique code as user Code
        user.setUsrUserCode(usrUserCode);

        //validate and save user details
        user =saveUser(user);

        // check if the customer is null
        if ( user == null || user.getUsrUserNo() == null ) {

            // Log the information
            log.error("registerCustomer -> Unable to save the user");

            MessageWrapper messageWrapper = generalUtils.getMessageWrapperObject(MessageSpielValue.GENERAL_ERROR_MESSAGE,"",usrLoginId,"","",merchantNo,new HashMap<String,String>(0),MessageSpielChannel.SMS,IndicatorStatus.NO);

            userMessagingService.transmitNotification(messageWrapper);

            // Throw exception
            return null;

        }

        //add into password history table
        passwordHistoryService.savePasswordHistory(user.getUsrUserNo(),user.getUsrPassword());

        //we are sending password before generated so set the before generated password means unique key this key is set password
        user.setUsrPassword(uniquePassword);

        //return user
        return  user;

    }

    public boolean generateOTPForCustomer(Long merchantNo,String usrLoginId,Integer otpRefType,Integer otpType)throws InspireNetzException{

        if(merchantNo==0){

            //get default merchant no
            merchantNo = generalUtils.getDefaultMerchantNo();

        }


        //check customer is valid user
        User user = userRepository.findByUsrLoginId(usrLoginId);

        if(user !=null){


            boolean isOTPGenerated= oneTimePasswordService.generateOTPGeneric(merchantNo,otpRefType,user.getUsrUserNo().toString(),otpType);


            return isOTPGenerated;
        }else{

            // Log the response
            log.info("generateForgetPasswordOtp invalid user");

            // Throw InspireNetzException with ERR_OPERATION_FAILED as error
            throw new InspireNetzException(APIErrorCode.ERR_USER_NOT_FOUND);
        }
    }

    @Override
    public List<User> findByUsrUserTypeAndUsrMerchantNo(int userType, Long usrMerchantNo) {
        return userRepository.findByUsrUserTypeAndUsrMerchantNo(userType,usrMerchantNo);
    }

    @Override
    public void userOptOutOperation(Long usrUserNo) throws InspireNetzException {

        //find the connected customer
        List<Customer> connectedCustomerList =customerService.findByCusUserNoAndCusRegisterStatus(authSessionUtils.getUserNo(),IndicatorStatus.YES);

        //check the customer list is empty or null
        if(connectedCustomerList != null && connectedCustomerList.size()>0){

            for (Customer customer:connectedCustomerList){

                //update the customer loyalty
                customerService.unRegisterLoyaltyCustomer(customer.getCusLoyaltyId(), customer.getCusMerchantNo(), CustomerStatus.INACTIVE, "", UserRequest.CUSTOMER);

            }

        }

        //get the user details
        User user =findByUsrUserNo(usrUserNo);

        // Set the user status to deactivated if the user is not null
        if ( user != null ) {

            //deactivate the user when req
            user.setUsrStatus(UserStatus.DEACTIVATED);

            //deactivate the user
            user.setUsrRegisterStatus(IndicatorStatus.NO);

            //sms param
            HashMap<String , String > smsParams = new HashMap<>(0);

            MessageWrapper messageWrapper = generalUtils.getMessageWrapperObject(MessageSpielValue.LOYALTY_USER_DEACTIVATION_SMS,"",user.getUsrMobile(),"","",generalUtils.getDefaultMerchantNo(),smsParams,MessageSpielChannel.SMS,IndicatorStatus.NO);



            // Send the sms stating that the customer has been deactivated successfully
            userMessagingService.transmitNotification(messageWrapper);

        }
        //save user
        saveUser(user);

    }


    /**
     *  This method used for generating unique user code,and after code generation if duplicate exist ,it will recursively call
     *  to generate new unique code
     * @return String -Unique User Code
     */
    @Override
    public String generateUsrUserCode(){

        String usrUserCode=generalUtils.generateUniqueCode(6);

        //fetch user with generated user code
        User existingUser=userRepository.findByUsrUserCode(usrUserCode);

        //if no duplicate return the generated user code else call recursively to generate unique code
        if(existingUser==null){

            return usrUserCode;
        }

        //recursive call to generate user code
        return generateUsrUserCode();
    }

    /**
     *  This method used for getting user code ,if user code is empty
     *  to generate new unique code
     * @return String -Unique User Code
     */
    @Override
    public String getUsrUserCode(Long usrUserNo) throws InspireNetzException{


        User user=findByUsrUserNo(usrUserNo);

        // Check if the user is null
        if(user == null ||user.getUsrUserNo()==null){

            //log the error
            log.error("No User Information Found");

            //throw exception
            throw new InspireNetzException(APIErrorCode.ERR_NO_DATA);

        }

        if(user.getUsrUserType()!=UserType.CUSTOMER){

            //log the error
            log.error("Invalid User Type");

            //throw exception
            throw new InspireNetzException(APIErrorCode.ERR_INVALID_REQUEST);
        }

        // Hold the audit details
        String auditDetails = authSessionUtils.getUserNo().toString() + "#" + authSessionUtils.getUserLoginId();

        if(user.getUsrUserCode()==null||user.getUsrUserCode().equals("")){


            String usrUserCode=generateUsrUserCode();
            user.setUsrUserCode(usrUserCode);
            user.setUpdatedBy(auditDetails);
            user=saveUser(user);

            if(user==null){

                //log the error
                log.error("Save Operation Failed");

                //throw exception
                throw new InspireNetzException(APIErrorCode.ERR_OPERATION_FAILED);
            }

            return usrUserCode;

        }

        return user.getUsrUserCode();
    }


    @Override
    public User findByUsrUserCode(String usrUserCode){

        return userRepository.findByUsrUserCode(usrUserCode);
    }

}