package com.inspirenetz.api.core.service.impl;

import com.inspirenetz.api.core.dictionary.APIErrorCode;
import com.inspirenetz.api.core.dictionary.FunctionCode;
import com.inspirenetz.api.core.dictionary.IndicatorStatus;
import com.inspirenetz.api.core.domain.Customer;
import com.inspirenetz.api.core.domain.Merchant;
import com.inspirenetz.api.core.domain.MerchantSetting;
import com.inspirenetz.api.core.domain.Setting;
import com.inspirenetz.api.core.repository.BaseRepository;
import com.inspirenetz.api.core.repository.MerchantSettingRepository;
import com.inspirenetz.api.core.service.MerchantSettingService;
import com.inspirenetz.api.core.service.SettingService;
import com.inspirenetz.api.rest.exception.InspireNetzException;
import com.inspirenetz.api.util.AuthSessionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by sandheepgr on 28/3/14.
 */
@Service
public class MerchantSettingServiceImpl extends BaseServiceImpl<MerchantSetting> implements MerchantSettingService {


    private static Logger log = LoggerFactory.getLogger(MerchantSettingServiceImpl.class);


    @Autowired
    MerchantSettingRepository merchantSettingRepository;

    @Autowired
    SettingService settingService;

    @Autowired
    AuthSessionUtils authSessionUtils;


    public MerchantSettingServiceImpl() {

        super(MerchantSetting.class);

    }


    @Override
    protected BaseRepository<MerchantSetting,Long> getDao() {
        return merchantSettingRepository;
    }


    @Override
    public MerchantSetting findByMesMerchantNoAndMesSettingId(Long mesMerchantNo, Long mesSettingId) {

        // Get the MerchantSetting
        MerchantSetting merchantSetting = merchantSettingRepository.findByMesMerchantNoAndMesSettingId(mesMerchantNo,mesSettingId);

        // return the MerchantSetting
        return merchantSetting;

    }

    @Override
    public List<MerchantSetting> findByMesMerchantNo(Long mesMerchantNo) {

        // GEt the MerchantSetting list
        List<MerchantSetting> merchantSettingList =merchantSettingRepository.findByMesMerchantNo(mesMerchantNo);

        // Return the MerchantSettingList
        return merchantSettingList;

    }

    @Override
    public HashMap<Long, String> getSettingAsMap(Long mesMerchantNo) {

        // HashMap holding the values
        HashMap<Long,String> settingsMap = new HashMap<>(0);

        // Get the UARList
        List<MerchantSetting> settingValueList = merchantSettingRepository.findByMesMerchantNo(mesMerchantNo);

        // Iterate through the accessEnableList
        for( MerchantSetting merchantSetting : settingValueList ) {

            // Now add the key value pair to the map
            settingsMap.put(merchantSetting.getMesSettingId(), merchantSetting.getMesValue());

        }


        // REturn the settingsMap
        return settingsMap;
    }

    @Override
    public HashMap<String, String> getSettingValuesAsMap(Long mesMerchantNo) {

        // HashMap holding the values
        HashMap<String,String> settingsMap = new HashMap<>(0);

        // Get the UARList
        List<MerchantSetting> settingValueList = merchantSettingRepository.findByMesMerchantNo(mesMerchantNo);

        // Iterate through the accessEnableList
        for( MerchantSetting merchantSetting : settingValueList ) {

            Setting setting=settingService.findBySetId(merchantSetting.getMesSettingId());

            if(setting==null){

                continue;
            }

            // Now add the key value pair to the map
            settingsMap.put(setting.getSetName(), merchantSetting.getMesValue());

        }


        // REturn the settingsMap
        return settingsMap;
    }

    @Override
    public MerchantSetting saveMerchantSetting(MerchantSetting merchantSetting ) throws InspireNetzException {

        // Save the merchantSetting
        return merchantSettingRepository.save(merchantSetting);

    }

    @Override
    public boolean deleteMerchantSetting(MerchantSetting merchantSetting) throws InspireNetzException {

        // Delete the merchantSetting
        merchantSettingRepository.delete(merchantSetting);

        // return true
        return true;

    }

    @Override
    public MerchantSetting validateAndSaveMerchantSetting(MerchantSetting merchantSetting) throws InspireNetzException {
        //check the access rights of the user
        authSessionUtils.validateFunctionAccess(FunctionCode.FNC_UPDATE_MERCHANT_USER);

        return saveMerchantSetting(merchantSetting);
    }

    @Override
    public boolean validateAndDeleteMerchantSetting(MerchantSetting merchantSetting) throws InspireNetzException {

        //check the access rights of the user
        authSessionUtils.validateFunctionAccess(FunctionCode.FNC_UPDATE_MERCHANT_USER);

        return deleteMerchantSetting(merchantSetting);
    }

    @Override
    public Page<MerchantSetting> getMerchantSettingsForAdmin(Long merchantNo, String filter, String query,Pageable pageable) throws InspireNetzException {

        //initialise page object
        Page<MerchantSetting> merchantSettingPage =null;

        //check the user is super admin or admin
        if(authSessionUtils.getUserType() !=1 && authSessionUtils.getUserType() !=2){

            log.info("MerchantSettingsService ->getMerchantSettingsForAdmin::Unauthorized user");

            throw new InspireNetzException(APIErrorCode.ERR_NOT_AUTHORIZED);
        }

        List<Setting> settingList =null;

        //check filter condition
        if(filter.equals("name")){

            //find settings name like
            settingList =settingService.findBySetNameLike("%"+query+"%");

        }else {

            //get default settings in system if the settings in stored in session
            settingList =settingService.findAll();
        }

        //get merchant settings
        List<MerchantSetting> merchantSettings =findByMesMerchantNo(merchantNo);

        //initialize new settings object
        List<MerchantSetting> merchantSettingList =new ArrayList<>();

        //create merchant settings object
        MerchantSetting merchantSetting=null;

        //check merchant settings null or not if null return default settings
        if(merchantSettings ==null || merchantSettings.size() ==0){

            merchantSettings =new ArrayList<>();

            //check settings also null throw error no data
            if(settingList ==null){

                log.info("MerchantSettingsImpl->Settings is null");

                throw new InspireNetzException(APIErrorCode.ERR_NO_DATA);
            }

            //iterate settings and convert into merchant settings format
            for (Setting setting:settingList){

                //create new object
                merchantSetting =new MerchantSetting();

                //set merchant settings related value
                merchantSetting =setRelatedValue(merchantSetting,setting);

                //add into list
                merchantSettings.add(merchantSetting);
            }

            //convert into page object and return
            merchantSettingPage =new PageImpl<>(merchantSettings);

            //return page
            return merchantSettingPage;

        }

        //merchant settings is not null check the data if its present return that field and defult
        if(settingList ==null){

            return  new PageImpl<>(merchantSettings);
        }

        //declaring variable
        boolean hasSettingPresent =false;

        //iterate merchant settings list and settings list
        for(Setting setting:settingList){

            for(MerchantSetting merchantSetting1 :merchantSettings) {

                //check settings id is equal or not
                if(merchantSetting1.getMesSettingId().intValue() ==setting.getSetId().intValue()){

                    hasSettingPresent =true;

                    //set merchant settings name
                    merchantSetting1.setSetName(setting.getSetName());

                    //set datatype
                    merchantSetting1.setSetDataType(setting.getSetDataType());

                    //add into list
                    merchantSettingList.add(merchantSetting1);
                }
            }

            //check if its present else default
            if(!hasSettingPresent){

                //create new object
                merchantSetting =new MerchantSetting();

                //set related field
                merchantSetting =setRelatedValue(merchantSetting, setting);

                //add into list
                merchantSettingList.add(merchantSetting);
            }

            //set false for next check
            hasSettingPresent =false;

        }

        //return merchant settings
        return new PageImpl<>(merchantSettingList);
    }



    private MerchantSetting setRelatedValue(MerchantSetting merchantSetting, Setting setting) {

        //added all field setname
        merchantSetting.setSetName(setting.getSetName());

        //setting id
        merchantSetting.setMesSettingId(setting.getSetId());

        //default value
        merchantSetting.setMesValue(setting.getSetDefaultValue());

        //datatype
        merchantSetting.setSetDataType(setting.getSetDataType());

        //return merchant settings
        return merchantSetting;
    }

    @Override
    public boolean isSettingEnabledForMerchant(String settingId,Long merchantNo) {

        //get settings id
        Long setting=settingService.getSettingsId(settingId);

        //merchant settings enabled or not
        MerchantSetting merchantSetting=findByMesMerchantNoAndMesSettingId(merchantNo,setting);

        //check if merchant settings null check its default enabled
        if(merchantSetting ==null){

            Setting setting1=settingService.findBySetId(setting);

            if(setting1 !=null){

                //check its default enabled
                int settingDefaultValue = Integer.parseInt(setting1.getSetDefaultValue()==null?"0":setting1.getSetDefaultValue());

                if(settingDefaultValue ==IndicatorStatus.YES){

                    return true;

                }else {

                    return false;
                }

            }else{

                return false;
            }
        }else {

            //check merchant enabled value
            int merchantSettings =Integer.parseInt(merchantSetting.getMesValue()==null?"0":merchantSetting.getMesValue());

            if(merchantSettings ==IndicatorStatus.YES){

                return true;
            }

        }



        return false;
    }

    @Override
    public MerchantSetting getMerchantSettings(String settings,Long merchantNo) {

        //get settings id
        Setting setting=settingService.findBySetName(settings);

        if(setting==null||setting.getSetId()==null){

            return null;
        }

        //merchant settings enabled or not
        MerchantSetting merchantSetting=findByMesMerchantNoAndMesSettingId(merchantNo,setting.getSetId());

        //check if merchant settings null check its default enabled
        if(merchantSetting ==null){

            Setting setting1=settingService.findBySetId(setting.getSetId());

            if(setting1 !=null){

                merchantSetting=new MerchantSetting();

                merchantSetting=setRelatedValue(merchantSetting,setting);

            }
        }
        return merchantSetting;
    }

    @Override
    public List<MerchantSetting> getSettingsEnabledMerchant(String settingsName) {

        //find the merchant settings information
        Setting setting =settingService.findBySetName(settingsName);

        //check settings is found or not
        if(setting ==null){

            //return settings information
            return null;
        }

        //get the merchant list
        List<MerchantSetting> merchantSettingList =findByMesSettingId(setting.getSetId());

        //get settings enabled merchant
        List<MerchantSetting> settingsEnabledList =new ArrayList<>();

        for (MerchantSetting merchantSetting :merchantSettingList){

            //check merchant settings value yes or no
            if((!merchantSetting.getMesValue().equals("")) && merchantSetting.getMesValue().equals("1")){

                //get the merchant settings
                settingsEnabledList.add(merchantSetting);

            }

        }

        //return enabled list
        return settingsEnabledList;


    }

    @Override
    public List<MerchantSetting> findByMesSettingId(Long mesSettingsId) {
        return merchantSettingRepository.findByMesSettingId(mesSettingsId);
    }


}
