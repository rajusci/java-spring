package com.inspirenetz.api.core.repository;

import com.inspirenetz.api.core.domain.RedemptionMerchantLocation;

import java.util.List;

/**
 * Created by saneesh-ci on 9/9/14.
 */
public interface RedemptionMerchantLocationRepository extends BaseRepository<RedemptionMerchantLocation,Long>{

    public RedemptionMerchantLocation findByRmlId(Long rmlId);
    public List<RedemptionMerchantLocation>  findByRmlMerNo(Long rmlMerNo);

}
