package com.inspirenetz.api.core.service;

import com.inspirenetz.api.core.domain.LoyaltyExtension;
import com.inspirenetz.api.rest.exception.InspireNetzException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Created by saneesh-ci on 10/9/14.
 */
public interface LoyaltyExtensionService extends BaseService<LoyaltyExtension> {


    public LoyaltyExtension findByLexId(Long lexId);
    public Page<LoyaltyExtension> searchLoyaltyExtensions(String filter,String query,Pageable pageable);
    public LoyaltyExtension getLoyaltyExtensionInfo(Long lexId) throws InspireNetzException;

    public LoyaltyExtension validateAndSaveLoyaltyExtension(LoyaltyExtension loyaltyExtension) throws InspireNetzException;
    public LoyaltyExtension saveLoyaltyExtension(LoyaltyExtension loyaltyExtension);
    public boolean validateAndDeleteLoyaltyExtension(Long lexId) throws InspireNetzException;
    public boolean deleteLoyaltyExtension(Long lexId);


}
