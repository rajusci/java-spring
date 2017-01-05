package com.inspirenetz.api.core.repository;

import com.inspirenetz.api.core.domain.SecuritySetting;

import java.util.List;

/**
 * Created by saneeshci on 29/9/14.
 */
public interface SecuritySettingRepository extends BaseRepository<SecuritySetting,Long>{

    public SecuritySetting findBySecId(Long rarId);
    public List<SecuritySetting> findAll();
    public List<SecuritySetting> findBySecMerchantNo(Long secMerchantNo);


}
