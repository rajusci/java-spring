package com.inspirenetz.api.core.repository;

import com.inspirenetz.api.core.domain.TransferPointSetting;

import java.util.List;

/**
 * Created by sandheepgr on 28/3/14.
 */
public interface TransferPointSettingRepository extends  BaseRepository<TransferPointSetting,Long> {


    public TransferPointSetting findByTpsId(Long tpsId);
    public List<TransferPointSetting>  findByTpsMerchantNo(Long tpsMerchantNo);
    public TransferPointSetting findByTpsMerchantNoAndTpsLocation(Long tpsMerchantNo, Long tpsLocation);


}
