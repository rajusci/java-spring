package com.inspirenetz.api.core.service;

import com.inspirenetz.api.core.domain.SecuritySetting;
import com.inspirenetz.api.core.domain.User;
import com.inspirenetz.api.rest.exception.InspireNetzException;

import java.util.List;


/**
 * Created by saneeshci on 29/9/14.
 */
public interface SecuritySettingService extends BaseService<SecuritySetting>{

    SecuritySetting  findBySecId(Long secId);

    SecuritySetting getSecuritySetting(User user) throws InspireNetzException;
    boolean deleteSecuritySetting(SecuritySetting securitySetting);

    public SecuritySetting validateAndSaveSecuritySetting(SecuritySetting securitySetting) throws InspireNetzException;
    public SecuritySetting saveSecuritySetting(SecuritySetting securitySetting);

    public boolean isPasswordValid(User user,String newPassword) throws InspireNetzException;

    public List<SecuritySetting> findBySecMerchantNo(Long secMerchantNo);


    public SecuritySetting getSecuritySetting() throws InspireNetzException;

    public SecuritySetting getSecuritySettingsDetails() throws InspireNetzException;




}
