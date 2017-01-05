package com.inspirenetz.api.core.service;

import com.inspirenetz.api.core.domain.RedemptionMerchant;
import com.inspirenetz.api.core.domain.User;
import com.inspirenetz.api.rest.exception.InspireNetzException;
import com.inspirenetz.api.rest.resource.RedemptionMerchantResource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Map;

/**
 * Created by saneesh-ci on 25/9/14.
 */
public interface RedemptionMerchantService extends BaseService<RedemptionMerchant> {


    public RedemptionMerchant findByRemNo(Long remNo) throws InspireNetzException;
    public Page<RedemptionMerchant> searchRedemptionMerchants(String filter, String query, Pageable pageable) throws InspireNetzException;

    public RedemptionMerchant validateAndSaveRedemptionMerchant(RedemptionMerchant redemptionMerchant) throws InspireNetzException;
    public RedemptionMerchant saveRedemptionMerchant(RedemptionMerchant redemptionMerchant);
    public boolean validateAndDeleteRedemptionMerchant(Long remNo) throws InspireNetzException;
    public boolean deleteRedemptionMerchant(Long remNo);

    public List<RedemptionMerchant> searchRedemptionMerchants(String query);
    public RedemptionMerchant findByRemCode(String remVendorCode);
    public RedemptionMerchant findByRemVendorUrl(String remVenUrl);


    public Double validateCustomer(Long merchantNo, String loyaltyId,Double amount) throws InspireNetzException;


    RedemptionMerchantResource getRedemptionMerchantWithPartners(String remCode);

}
