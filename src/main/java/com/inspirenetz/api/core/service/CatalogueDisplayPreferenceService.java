package com.inspirenetz.api.core.service;

import com.inspirenetz.api.core.domain.CatalogueDisplayPreference;
import com.inspirenetz.api.rest.exception.InspireNetzException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Created by saneesh-ci on 9/9/14.
 */
public interface CatalogueDisplayPreferenceService extends BaseService<CatalogueDisplayPreference> {


    public CatalogueDisplayPreference findByCdpId(Long cdpId) throws InspireNetzException;
    public CatalogueDisplayPreference getCatalogueDisplayPreference(Long cdpMerchantNo) throws InspireNetzException;

    public CatalogueDisplayPreference validateAndSaveCatalogueDisplayPreference(CatalogueDisplayPreference catalogueDisplayPreference) throws InspireNetzException;
    public CatalogueDisplayPreference saveDisplayPreference(CatalogueDisplayPreference catalogueDisplayPreference);
    public boolean deleteDisplayPreference(Long cdpId);

    public CatalogueDisplayPreference getDefaultCatalogueDisplayPreference() throws InspireNetzException;

    public CatalogueDisplayPreference getUserCatalogueDisplayPreference(Long cdpMerchantNo) throws InspireNetzException;


}
