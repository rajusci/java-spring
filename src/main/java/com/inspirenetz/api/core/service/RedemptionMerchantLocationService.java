package com.inspirenetz.api.core.service;

import com.inspirenetz.api.core.domain.RedemptionMerchantLocation;
import com.inspirenetz.api.rest.exception.InspireNetzException;

import java.util.List;
import java.util.Set;

/**
 * Created by saneesh-ci on 25/9/14.
 */
public interface RedemptionMerchantLocationService extends BaseService<RedemptionMerchantLocation>{

    RedemptionMerchantLocation  findByRmlId(Long rmlId);
    List<RedemptionMerchantLocation> findByRmlMerNo(Long rmlMerNo);

    public RedemptionMerchantLocation saveRedemptionMerchantLocation(RedemptionMerchantLocation redemptionMerchantLocation);
    public boolean deleteRedemptionMerchantLocation(Long rarId);
    public void deleteRedemptionMerchantLocationSet(Set<RedemptionMerchantLocation> redemptionMerchantLocations);
}
