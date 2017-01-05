package com.inspirenetz.api.core.repository;

import com.inspirenetz.api.core.domain.MerchantSetting;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
 * Created by sandheepgr on 28/3/14.
 */
public interface MerchantSettingRepository extends  BaseRepository<MerchantSetting,Long> {

    public MerchantSetting findByMesMerchantNoAndMesSettingId(Long mesMerchantNo,Long mesSettingId);
    public List<MerchantSetting> findByMesMerchantNo(Long mesMerchantNo);
    public List<MerchantSetting> findByMesSettingId(Long mesSettingsNo);


}
