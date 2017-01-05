package com.inspirenetz.api.core.service.impl;

import com.inspirenetz.api.core.dictionary.APIErrorCode;
import com.inspirenetz.api.core.dictionary.FunctionCode;
import com.inspirenetz.api.core.dictionary.UserType;
import com.inspirenetz.api.core.domain.PasswordHistory;
import com.inspirenetz.api.core.domain.SecuritySetting;
import com.inspirenetz.api.core.domain.User;
import com.inspirenetz.api.core.repository.BaseRepository;
import com.inspirenetz.api.core.repository.SecuritySettingRepository;
import com.inspirenetz.api.core.service.PasswordHistoryService;
import com.inspirenetz.api.core.service.SecuritySettingService;
import com.inspirenetz.api.core.service.UserService;
import com.inspirenetz.api.rest.exception.InspireNetzException;
import com.inspirenetz.api.util.AuthSessionUtils;
import com.inspirenetz.api.util.GeneralUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.jws.soap.SOAPBinding;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;


/**
 * Created by saneeshci on 29/9/14.
 */

@Service
public class SecuritySettingServiceImpl extends BaseServiceImpl<SecuritySetting> implements SecuritySettingService {



    private static Logger log = LoggerFactory.getLogger(SecuritySettingServiceImpl.class);

    @Autowired
    SecuritySettingRepository securitySettingRepository;

    @Autowired
    UserService userService;

    @Autowired
    GeneralUtils generalUtils;


    @Autowired
    private AuthSessionUtils authSessionUtils;

    @Autowired
    private PasswordHistoryService passwordHistoryService;

    public SecuritySettingServiceImpl() {

        super(SecuritySetting.class);

    }
    @Override
    protected BaseRepository<SecuritySetting, Long> getDao() {
        return securitySettingRepository;
    }

    @Override
    public SecuritySetting findBySecId(Long secId) {

        // Getting data based by secid
        SecuritySetting securitySetting=securitySettingRepository.findBySecId(secId);
        return securitySetting;
    }

    @Override
    public SecuritySetting getSecuritySetting(User user) throws InspireNetzException {

        //initialize security settings
        List<SecuritySetting> securitySettingList =null;

        //check auth session type if the user type 5 then get default security settings
        if(user.getUsrUserType() ==UserType.CUSTOMER || user.getUsrUserType()==UserType.REDEMPTION_MERCHANT_USER){

            //get default merchant settings
            securitySettingList = findBySecMerchantNo(generalUtils.getDefaultMerchantNo());

        }else if(user.getUsrUserType() ==UserType.MERCHANT_USER){

           //get user merchant settings
           securitySettingList = findBySecMerchantNo(user.getUsrMerchantNo());

        }

        if(securitySettingList == null){

            log.error("getSecuritySetting Info : No Setting Found");

            throw new InspireNetzException(APIErrorCode.ERR_NO_DATA_FOUND);
        }
        else{

            //return the security setting
            return securitySettingList.get(0);

        }
    }


    @Override
    public SecuritySetting saveSecuritySetting(SecuritySetting securitySetting) {
        //saving the role access right
        return securitySettingRepository.save(securitySetting);
    }

    @Override
    public boolean deleteSecuritySetting(SecuritySetting securitySetting) {

        //for deleting the roll access right
        securitySettingRepository.delete(securitySetting);

        return true;
    }


    @Override
    public SecuritySetting validateAndSaveSecuritySetting(SecuritySetting securitySetting) throws InspireNetzException {


        authSessionUtils.validateFunctionAccess(FunctionCode.FNC_UPDATE_SECURITY_SETTING);

        // Hold the audit details
        String auditDetails = authSessionUtils.getUserNo().toString();

        Integer userType = authSessionUtils.getUserType();

        if ( userType != UserType.SUPER_ADMIN  &&  userType != UserType.MERCHANT_ADMIN ) {

            // Log the information
            log.info("Exception in :: validateAndSaveSecuritySetting UnAuthorised User userType="+userType);

            // Throw not authrorized exception
            throw new InspireNetzException(APIErrorCode.ERR_NOT_AUTHORIZED);

        }

        //set merchant number of the customer
        securitySetting.setSecMerchantNo(authSessionUtils.getMerchantNo());

        // If the roll access right .getMsiId is  null, then set the created_by, else set the updated_by
        if ( securitySetting.getSecId() == null ) {

            securitySetting.setCreatedBy(auditDetails);

        } else {

            securitySetting.setUpdatedBy(auditDetails);

        }

        // Save the object
        securitySetting = saveSecuritySetting(securitySetting);

        // Check if the messageSpiel is saved
        if ( securitySetting.getSecId() == null ) {

            // Log the response
            log.info("validateAndSaveSecuritySetting - Response : Unable to save the message spiel information");

            // Throw InspireNetzException with ERR_OPERATION_FAILED as error
            throw new InspireNetzException(APIErrorCode.ERR_OPERATION_FAILED);

        }

        // return the object
        return securitySetting;

    }

    @Override
    public boolean isPasswordValid(User user,String newPassword) throws InspireNetzException {


        //for skipping password if the user type is web admin or merchant admin
        if(user.getUsrUserType() ==UserType.ADMIN ||user.getUsrUserType() ==UserType.MERCHANT_ADMIN){

            //return true information
            return  true;
        }
        //get security information
        SecuritySetting securitySetting=getSecuritySetting(user);

        if(securitySetting !=null){

            String password=newPassword;

            //checking preference conditions
            String preference = securitySetting.getSecPwdPreferences();

            if(preference !=null && !preference.equals("")){

                List<String> prefStringList= Arrays.asList(preference.split(":"));

                log.info("SecuritySettingServiceImpl::isPasswordValid prefStringList:"+prefStringList);

                for(String preferCase :prefStringList){

                    Integer eachCase=Integer.parseInt(preferCase);

                    if(eachCase ==1){

                        if(!password.matches(".*[A-Z].*")){

                            throw new InspireNetzException(APIErrorCode.ERR_NOT_MATCH_UPPER_SECURITY_SETTINGS);
                        }

                    }else if(eachCase ==2){

                        if(!password.matches(".*[a-z].*")){

                            throw new InspireNetzException(APIErrorCode.ERR_NOT_MATCH_LOWER_SECURITY_SETTINGS);
                        }


                    }else if(eachCase == 3){

                        if(!password.matches(".*[$&+,:;=?@#|].*")){

                            throw new InspireNetzException(APIErrorCode.ERR_NOT_MATCH_SPE_SECURITY_SETTINGS);
                        }

                    }else if(eachCase == 4){

                        if(!password.matches(".*[0-9].*")){

                            throw new InspireNetzException(APIErrorCode.ERR_NOT_MATCH_NUM_SECURITY_SETTINGS);
                        }
                    }
                }


            }

            //for checking length criteria
            if(securitySetting.getSecPwdLength() !=null){

                //check length criteria
                checkLengthCriteria(securitySetting.getSecPwdLength(),password);
            }


            //for checking minimum password validity
            if(securitySetting.getSecPwdMinValidity() !=null){

                //minimum validity criteria
                if(user.getUsrUserNo() !=null){

                    checkMinimumPasswordValidity(securitySetting.getSecPwdMinValidity(),user.getUsrUserNo());
                }


            }

        }

        return true;
    }

    @Override
    public List<SecuritySetting> findBySecMerchantNo(Long secMerchantNo) {
        return securitySettingRepository.findBySecMerchantNo(secMerchantNo);
    }

    @Override
    public SecuritySetting getSecuritySetting() throws InspireNetzException {

        //find current user information
        User user =userService.findByUsrUserNo(authSessionUtils.getUserNo());

        return getSecuritySetting(user);
    }

    @Override
    public SecuritySetting getSecuritySettingsDetails() throws InspireNetzException {

        //get the security settings details based on merchant number
        Long merchantNo =authSessionUtils.getMerchantNo();

        //security setting object
        SecuritySetting securitySetting =new SecuritySetting();

        //get the details based on merchant number
        List<SecuritySetting> securitySettings =findBySecMerchantNo(merchantNo);

        if(securitySettings !=null){

            securitySetting =securitySettings.get(0);
        }

        //return security settings details
        return  securitySetting;

    }

    /**
     * @purpose:checking length criteria
     * @param passwordLength
     * @param password
     * @throws InspireNetzException
     */

    public void checkLengthCriteria(Long passwordLength, String password) throws InspireNetzException {

        //checking the  minimum password length criteria
        if((password.length() < passwordLength.longValue())){


            throw new  InspireNetzException(APIErrorCode.ERR_NOT_MATCH_MIN_LENGTH_SECURITY_SETTINGS);

        }

    }

    /**
     * @purpose:checking minimum validity for password
     * @param addedDay
     * @param UsrUsrNo
     * @throws InspireNetzException
     */
    public void checkMinimumPasswordValidity(Long addedDay, Long UsrUsrNo) throws InspireNetzException{

        Date date = new Date();

        //for getting date from password history
        //we can identify the when user is changed password for using password history table
        Date lastDatePasswordHistory=passwordHistoryService.findByMaxPasChangedAt(UsrUsrNo);

        if(lastDatePasswordHistory !=null){

            Date previousChanged = lastDatePasswordHistory;

            Calendar calendar=Calendar.getInstance();

            calendar.setTime(previousChanged);

            calendar.add(Calendar.DATE,addedDay.intValue());

            Date previousDate=calendar.getTime();

            if(date.compareTo(previousDate) <0){

                throw new InspireNetzException(APIErrorCode.ERR_NOT_MATCH_MIN_PASS_VALIDITY_SECURITY_SETTINGS);
            }
        }

    }



}
