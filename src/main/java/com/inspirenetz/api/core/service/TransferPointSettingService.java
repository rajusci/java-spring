package com.inspirenetz.api.core.service;

import com.inspirenetz.api.core.domain.TransferPointSetting;
import com.inspirenetz.api.rest.exception.InspireNetzException;

import java.util.List;

/**
 * Created by sandheepgr on 28/3/14.
 */
public interface TransferPointSettingService extends BaseService<TransferPointSetting> {

    public TransferPointSetting findByTpsId(Long tpsId);
    public List<TransferPointSetting> findByTpsMerchantNo(Long tpsMerchantNo);
    public TransferPointSetting getTransferPointSettingInfoForUser() throws InspireNetzException;
    public TransferPointSetting findByTpsMerchantNoAndTpsLocation(Long tpsMerchantNo, Long tpsLocation);
    public TransferPointSetting getDefaultTransferPointSetting(Long tpsMerchantNo);

    public TransferPointSetting saveTransferPointSettingForUser(TransferPointSetting transferPointSetting) throws InspireNetzException;
    public TransferPointSetting saveTransferPointSetting(TransferPointSetting transferPointSetting);
    public boolean removeTransferPointSetting(Long tpsId) throws InspireNetzException;
    public boolean deleteTransferPointSetting(Long tpsId) throws InspireNetzException;

}
