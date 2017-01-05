package com.inspirenetz.api.core.service;

import com.inspirenetz.api.core.domain.Customer;
import com.inspirenetz.api.core.domain.CustomerPromotionalEvent;
import com.inspirenetz.api.rest.exception.InspireNetzException;

import java.util.List;


/**
 * Created by saneesh-ci on 9/9/14.
 */
public interface CustomerPromotionalEventService extends BaseService<CustomerPromotionalEvent> {


    public CustomerPromotionalEvent findByCpeId(Long cpeId);
    public List<CustomerPromotionalEvent> findByCpeLoyaltyIdAndCpeMerchantNo(String cpeLoyaltyId, Long cpeMerchantNo);
    public CustomerPromotionalEvent findByCpeLoyaltyIdAndCpeEventIdCpeMerchantNo(String cpeLoyaltyId,Long cpeEventId, Long cpeMerchantNo);

    public CustomerPromotionalEvent validateAndSaveCustomerPromotionalEvent(CustomerPromotionalEvent customerPromotionalEvent) throws InspireNetzException;
    public boolean validateAndDeleteCustomerPromotionalEvent(CustomerPromotionalEvent customerPromotionalEvent);

    public CustomerPromotionalEvent saveCustomerPromotionalEvent(CustomerPromotionalEvent customerPromotionalEvent)  ;
    public boolean deleteCustomerPromotionalEvent(Long cpeId);

    public void triggerAwardingForCustomerPromotionalEvent(CustomerPromotionalEvent customerPromotionalEvent) throws InspireNetzException;

    public void triggerAwardingForCustomerPromotionalEventList(List<CustomerPromotionalEvent> customerPromotionalEvents) throws InspireNetzException;

    public boolean isDuplicate(CustomerPromotionalEvent customerPromotionalEvent);

    public CustomerPromotionalEvent findByCpeEventIdAndCpeProductAndCpeMerchantNoAndCpeLoyaltyId(Long eventId,String productCode,Long cpeMerchantNo,String cpeLoyaltyId);
}


