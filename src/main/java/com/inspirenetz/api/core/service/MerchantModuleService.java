package com.inspirenetz.api.core.service;

import com.inspirenetz.api.core.domain.MerchantModule;
import com.inspirenetz.api.rest.exception.InspireNetzException;
import org.springframework.data.domain.Page;

import java.util.HashMap;
import java.util.List;

/**
 * Created by sandheepgr on 28/3/14.
 */
public interface MerchantModuleService extends BaseService<MerchantModule> {

    public MerchantModule findByMemMerchantNoAndMemModuleId(Long memMerchantNo, Long memModuleId);
    public List<MerchantModule> findByMemMerchantNo(Long memMerchantNo);
    public HashMap<Long,String> getModulesAsMap(Long memMerchantNo);

    public MerchantModule saveMerchantModule(MerchantModule merchantModule) throws InspireNetzException;
    public boolean deleteMerchantModule(MerchantModule merchantModule) throws InspireNetzException;

    public MerchantModule validateAndSaveMerchantModule(MerchantModule merchantModule) throws InspireNetzException;
    public boolean validateAndDeleteMerchantModule(MerchantModule merchantModule) throws InspireNetzException;

    public Page<MerchantModule> getMerchantModule(Long merchantNo,String filter,String query) throws InspireNetzException;

}
