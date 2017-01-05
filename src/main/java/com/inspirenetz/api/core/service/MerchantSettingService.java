package com.inspirenetz.api.core.service;

import com.inspirenetz.api.core.domain.MerchantSetting;
import com.inspirenetz.api.rest.exception.InspireNetzException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.HashMap;
import java.util.List;

/**
 * Created by sandheepgr on 28/3/14.
 */
public interface MerchantSettingService extends BaseService<MerchantSetting> {

    public MerchantSetting findByMesMerchantNoAndMesSettingId(Long mesMerchantNo,Long mesSettingId);
    public List<MerchantSetting> findByMesMerchantNo(Long mesMerchantNo);
    public HashMap<Long,String> getSettingAsMap(Long mesMerchantNo);
    public HashMap<String, String> getSettingValuesAsMap(Long mesMerchantNo);

    public MerchantSetting saveMerchantSetting(MerchantSetting merchantSetting) throws InspireNetzException;
    public boolean deleteMerchantSetting(MerchantSetting merchantSetting) throws InspireNetzException;

    public MerchantSetting validateAndSaveMerchantSetting(MerchantSetting merchantSetting) throws InspireNetzException;
    public boolean validateAndDeleteMerchantSetting(MerchantSetting merchantSetting) throws InspireNetzException;

    public Page<MerchantSetting> getMerchantSettingsForAdmin(Long merchantNo,String filter,String query,Pageable pageable) throws InspireNetzException;
    public boolean isSettingEnabledForMerchant(String settingName,Long merchantNo);

    public MerchantSetting getMerchantSettings(String settings,Long merchantNo);

    public List<MerchantSetting> getSettingsEnabledMerchant(String settingsName);

    public List<MerchantSetting> findByMesSettingId(Long mesSettingsId);
}
