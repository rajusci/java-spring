package com.inspirenetz.api.core.service;

import com.inspirenetz.api.core.auth.AuthUser;
import com.inspirenetz.api.core.domain.Setting;
import com.inspirenetz.api.rest.exception.InspireNetzException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

/**
 * Created by sandheepgr on 28/3/14.
 */
public interface SettingService extends BaseService<Setting> {


    public Setting findBySetId(Long setId);
    public Setting findBySetName(String setName);
    public Page<Setting> findBySetNameLike(String setName, Pageable pageable);
    public Page<Setting> listSettings(Pageable pageable);
    public boolean isDuplicateSettingExisting(Setting setting);
    public Page<Setting> searchSettings(String filter,String query, Pageable pageable);
    public boolean isUserValidForOperation(AuthUser user) throws InspireNetzException;

    public Setting saveSetting(Setting setting) throws InspireNetzException;
    public boolean deleteSetting(Long setId) throws InspireNetzException;

    public Setting validateAndSaveSetting(Setting setting) throws InspireNetzException;
    public boolean validateAndDeleteSetting(Long setId) throws InspireNetzException;
    public List<Setting> findBySetSessionStoreInd(Integer indicatorStatus);

    public List<Setting> findBySetNameLike(String setName);

    public Long getSettingsId(String settingsName);

}
