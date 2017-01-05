package com.inspirenetz.api.core.service.impl;

import com.inspirenetz.api.core.auth.AuthUser;
import com.inspirenetz.api.core.dictionary.APIErrorCode;
import com.inspirenetz.api.core.dictionary.FunctionCode;
import com.inspirenetz.api.core.dictionary.UserType;
import com.inspirenetz.api.core.domain.Setting;
import com.inspirenetz.api.core.repository.BaseRepository;
import com.inspirenetz.api.core.repository.SettingRepository;
import com.inspirenetz.api.core.service.SettingService;
import com.inspirenetz.api.rest.exception.InspireNetzException;
import com.inspirenetz.api.util.AuthSessionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by sandheepgr on 28/3/14.
 */
@Service
public class SettingServiceImpl extends BaseServiceImpl<Setting> implements SettingService {


   private static Logger log = LoggerFactory.getLogger(SettingServiceImpl.class);


    @Autowired
    SettingRepository settingRepository;

    @Autowired
    AuthSessionUtils authSessionUtils;


    public SettingServiceImpl() {

        super(Setting.class);

    }


    @Override
    protected BaseRepository<Setting,Long> getDao() {
        return settingRepository;
    }




    @Override
    public Setting findBySetId(Long setId) {

        // Get the Setting for the given Setting id from the repository
        Setting setting = settingRepository.findBySetId(setId);

        // Return the Setting
        return setting;


    }

    @Override
    public Setting findBySetName(String setName) {

        // Get the settings
        Setting setting = settingRepository.findBySetName(setName);

        // Return the setting
        return setting;

    }

    @Override
    public Page<Setting> findBySetNameLike(String setName, Pageable pageable) {
        
        // Get the setting page
        Page<Setting> settingPage = settingRepository.findBySetNameLike(setName,pageable);

        // return the settingpage
        return settingPage;

    }

    @Override
    public Page<Setting> listSettings(Pageable pageable) {

        // Get the settings
        Page<Setting> settingPage = settingRepository.findAll(pageable);

        // Return the page
        return settingPage;

    }

    @Override
    public boolean isDuplicateSettingExisting(Setting setting) {

        // Get the Setting information
        Setting exSetting = settingRepository.findBySetName(setting.getSetName());

        // If the setId is 0L, then its a new Setting so we just need to check if there is ano
        // ther Setting code
        if ( setting.getSetId() == null || setting.getSetId() == 0L ) {

            // If the Setting is not null, then return true
            if ( exSetting != null ) {

                return true;

            }

        } else {

            // Check if the Setting is null
            if ( exSetting != null && setting.getSetId().longValue() != exSetting.getSetId().longValue() ) {

                return true;

            }
        }


        // Return false;
        return false;

    }

    @Override
    public Page<Setting> searchSettings(String filter, String query, Pageable pageable) {

        // Return page object
        Page<Setting> settingPage;

        // Check the filter option and call the repository method
        if ( filter.equalsIgnoreCase("name") ) {

            settingPage = settingRepository.findBySetNameLike("%"+query+"%",pageable);

        } else {

            settingPage = settingRepository.findAll(pageable);

        }

        // Return the page object
        return settingPage;


    }




    @Override
    public boolean isUserValidForOperation(AuthUser user) throws InspireNetzException {

        // If the user is not admin / super admin, throw InspireNetzException
        if ( user.getUserType() != UserType.ADMIN && user.getUserType() != UserType.SUPER_ADMIN ) {

            // log information
            log.info("isUserValidForOperation -> You are not allowed for the operation");

            // throw InspireNetzException
            throw new InspireNetzException(APIErrorCode.ERR_NOT_AUTHORIZED);

        }


        // Else return true
        return true;

    }

    @Override
    public Setting saveSetting(Setting setting ) throws InspireNetzException {


        // Check if the setting is existing
        boolean isExist = isDuplicateSettingExisting(setting);

        // Check the boolean value
        if ( isExist ) {

            // Log the response
            log.info("saveSetting - Response : Setting code is already existing");

            // Throw InspireNetzException with ERR_DUPLICATE_ENTRY as error
            throw new InspireNetzException(APIErrorCode.ERR_DUPLICATE_ENTRY);


        }

        // Save the Setting
        return settingRepository.save(setting);

    }

    @Override
    public boolean deleteSetting(Long setId) throws InspireNetzException {


        // Delete the Setting
        settingRepository.delete(setId);

        // return true
        return true;

    }

    @Override
    public Setting validateAndSaveSetting(Setting setting) throws InspireNetzException {

        //check the user access rights
        authSessionUtils.validateFunctionAccess(FunctionCode.FNC_UPDATE_SETTING);

        return saveSetting(setting);
    }

    @Override
    public boolean validateAndDeleteSetting(Long setId) throws InspireNetzException {

        //check the user access rights
        authSessionUtils.validateFunctionAccess(FunctionCode.FNC_DELETE_SETTING);

        return deleteSetting(setId);
    }

    @Override
    public List<Setting> findBySetSessionStoreInd(Integer indicatorStatus) {
        return settingRepository.findBySetSessionStoreInd(indicatorStatus);
    }

    @Override
    public List<Setting> findBySetNameLike(String setName) {
        return settingRepository.findBySetNameLike(setName);
    }

    /**
     * @purpose:to get settings information based on settings name
     * @param settingsName
     * @return
     */
    public Long getSettingsId(String settingsName) {

        Setting setting = findBySetName(settingsName);

        if(setting ==null){

            log.info(settingsName+" settings  not avialable in this merchant");

            //return 0
            return 0L;

        }

        return setting.getSetId()==null?0L:setting.getSetId();
    }
}
