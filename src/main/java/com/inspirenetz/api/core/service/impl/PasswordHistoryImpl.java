package com.inspirenetz.api.core.service.impl;

import com.inspirenetz.api.core.dictionary.APIErrorCode;
import com.inspirenetz.api.core.dictionary.SystemUserStatus;
import com.inspirenetz.api.core.dictionary.UserType;
import com.inspirenetz.api.core.domain.PasswordHistory;
import com.inspirenetz.api.core.domain.Role;
import com.inspirenetz.api.core.domain.SecuritySetting;
import com.inspirenetz.api.core.domain.User;
import com.inspirenetz.api.core.repository.BaseRepository;
import com.inspirenetz.api.core.repository.PasswordHistoryRepository;
import com.inspirenetz.api.core.service.PasswordHistoryService;
import com.inspirenetz.api.core.service.SecuritySettingService;
import com.inspirenetz.api.core.service.UserService;
import com.inspirenetz.api.rest.exception.InspireNetzException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * Created by ameenci on 9/10/14.
 */
@Service
public class PasswordHistoryImpl extends BaseServiceImpl<PasswordHistory> implements PasswordHistoryService {

    private static Logger log = LoggerFactory.getLogger(PasswordHistoryImpl.class);

    @Autowired
    PasswordHistoryRepository passwordHistoryRepository;

    @Autowired
    SecuritySettingService securitySettingService;

    @Autowired
    UserService userService;


    public PasswordHistoryImpl() {

        super(PasswordHistory.class);
    }

    @Override
    protected BaseRepository<PasswordHistory, Long> getDao() {
        return passwordHistoryRepository;
    }

    @Override
    public PasswordHistory savePasswordHistory(Long usrUserNo,String usrPassword) {

        PasswordHistory passwordHistory = new PasswordHistory();

        passwordHistory.setPasHistoryPassword(usrPassword);

        passwordHistory.setPasHistoryUserNo(usrUserNo);

        passwordHistory.setPasChangedAt(new Date());

        return passwordHistoryRepository.save(passwordHistory);
    }

    @Override
    public List<PasswordHistory> findByPasHistoryUserNo(Long pasHistoryUserNo) {

        List<PasswordHistory> passwordHistoryList=passwordHistoryRepository.findByPasHistoryUserNo(pasHistoryUserNo);

        return  passwordHistoryList;
    }

    @Override
    public Date findByMaxPasChangedAt(Long pasHistoryUserNo) {

        Date changedAt=passwordHistoryRepository.findByMaxPasChangedAt(pasHistoryUserNo);

        return  changedAt;
    }

    @Override
    public boolean delete(Long passwordHistoryId) {

        passwordHistoryRepository.delete(passwordHistoryId);

        return true;
    }

    @Override
    public boolean matchedPreviousHistory(Long passHistoryCount, String password, Long pasHistoryUserNo) throws InspireNetzException {

        //for getting information about last logged
        List<PasswordHistory> passwordHistoryList = passwordHistoryRepository.findByLastChangedAtDate(pasHistoryUserNo);

        passHistoryCount = passHistoryCount ==null? 0:passHistoryCount;

        //for matching password in last history count
        if(passwordHistoryList !=null){

            passHistoryCount = passHistoryCount>= passwordHistoryList.size() ? passwordHistoryList.size():passHistoryCount;

            for(int i=0;i< passHistoryCount;i++){

                PasswordHistory passwordHistory= passwordHistoryList.get(i);

                if(password.equals(passwordHistory.getPasHistoryPassword())){

                    throw new InspireNetzException(APIErrorCode.ERR_MATCH_PREVIOUS_PASSWORD_SECURITY_SETTINGS);
                }
            }
        }
        return  true;
    }


    /**
     * @purpose checking password is expired based on security settings
     * @param user - user number for logged use
     * @return boolean
     * @throws InspireNetzException
     */
    @Override
    public boolean isPasswordExpired(User user) throws InspireNetzException {


        //check user is null then return
        if(user ==null ){

            //return false
            return false;
        }

        //return if the user is other than merchant user and customer return false
        if(user.getUsrUserType() == UserType.MERCHANT_ADMIN || user.getUsrUserType() ==UserType.ADMIN||user.getUsrUserType() ==UserType.SUPER_ADMIN){

            //return false
            return false;
        }

        //check user is system user or not
        Integer isSystemUser = user.getUsrSystemUser() ==null ?0:user.getUsrSystemUser();

        //if user system user return false other wise check the password expiration
        if(isSystemUser == SystemUserStatus.YES){

            return false;
        }

        //get security settings of the user
        SecuritySetting securitySetting =securitySettingService.getSecuritySetting(user);

        Date lastChangedDate = findByMaxPasChangedAt(user.getUsrUserNo());

        //get the added days
        Long addedDays = securitySetting.getSecPwdExpiration();


        //security settings information
        if(addedDays !=null){

            Calendar calendar=Calendar.getInstance();

            calendar.setTime(lastChangedDate);

            calendar.add(Calendar.DATE, addedDays.intValue());

            Date expiringDate = calendar.getTime();

            Date date =new Date();

            //current date is grater than expiring date return true password is expired otherwise return false
            if(date.compareTo(expiringDate)>0){

                return true;

            }else {

                return false;
            }

        }

        return false;


    }

    @Override
    public boolean setLoggedInDetails(User user) {

        log.info("Update Users ------>"+user);

        // Get the current timestamp
        user.setUsrLastLoggedIn(new Timestamp(System.currentTimeMillis()));

        //update the user logged in information
        userService.saveUser(user);

        return true;
    }



}
