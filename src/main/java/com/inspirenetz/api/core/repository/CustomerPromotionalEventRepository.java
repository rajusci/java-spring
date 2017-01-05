package com.inspirenetz.api.core.repository;

import com.inspirenetz.api.core.domain.CustomerPromotionalEvent;
import com.inspirenetz.api.core.domain.CustomerPromotionalEvent;

import java.util.List;

/**
 * Created by saneesh-ci on 25/6/15.
 */
public interface CustomerPromotionalEventRepository extends  BaseRepository<CustomerPromotionalEvent,Long> {

    public CustomerPromotionalEvent findByCpeId(Long cpeId);
    public List<CustomerPromotionalEvent> findByCpeLoyaltyIdAndCpeMerchantNo(String cpeLoyaltyId, Long cpeMerchantNo);
    public CustomerPromotionalEvent findByCpeLoyaltyIdAndCpeEventIdAndCpeMerchantNo(String cpeLoyaltyId,Long cpeEventId, Long cpeMerchantNo);
    public CustomerPromotionalEvent findByCpeEventIdAndCpeProductAndCpeMerchantNoAndCpeLoyaltyId(Long eventId,String productCode,Long cpeMerchantNo,String cpeLoyaltyId);

}
