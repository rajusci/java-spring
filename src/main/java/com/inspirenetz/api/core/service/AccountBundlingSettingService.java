package com.inspirenetz.api.core.service;

import com.inspirenetz.api.core.domain.AccountBundlingSetting;
import com.inspirenetz.api.core.domain.AccountBundlingSetting;
import com.inspirenetz.api.rest.exception.InspireNetzException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

/**
 * Created by sandheepgr on 28/3/14.
 */
public interface AccountBundlingSettingService extends BaseService<AccountBundlingSetting> {

    public AccountBundlingSetting findByAbsId(Long absId);
    public List<AccountBundlingSetting> findByAbsMerchantNo(Long absMerchantNo);
    public AccountBundlingSetting getAccountBundlingSettingInfoForUser( ) throws InspireNetzException;
    public AccountBundlingSetting findByAbsMerchantNoAndAbsLocation(Long absMerchantNo, Long absLocation);
    public AccountBundlingSetting getDefaultAccountBundlingSetting(Long absMerchantNo);

    public AccountBundlingSetting saveAccountBundlingSettingForUser(AccountBundlingSetting accountBundlingSetting ) throws InspireNetzException;
    public AccountBundlingSetting saveAccountBundlingSetting(AccountBundlingSetting accountBundlingSetting);
    public boolean removeAccountBundlingSetting(Long absId) throws InspireNetzException;
    public boolean deleteAccountBundlingSetting(Long absId);

    public boolean checkPrimaryAccountIsReachedLinkedLimit(String cusLoyaltyId,Long cusMerchantNo) throws InspireNetzException;

    public AccountBundlingSetting getAccountBundlingSetting() throws InspireNetzException;


}
