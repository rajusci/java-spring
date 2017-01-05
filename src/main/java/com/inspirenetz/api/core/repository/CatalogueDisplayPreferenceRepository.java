package com.inspirenetz.api.core.repository;

import com.inspirenetz.api.core.domain.CatalogueDisplayPreference;


/**
 * Created by saneesh-ci on 25/9/14.
 */
public interface CatalogueDisplayPreferenceRepository extends  BaseRepository<CatalogueDisplayPreference,Long> {

    public CatalogueDisplayPreference findByCdpId(Long cdpId);
    public CatalogueDisplayPreference findByCdpMerchantNo(Long cdpMerchantNo);

}
