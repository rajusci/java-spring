package com.inspirenetz.api.core.service;

import com.inspirenetz.api.core.domain.Brand;
import com.inspirenetz.api.core.domain.CustomerProgramSummary;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

/**
 * Created by sandheepgr on 28/3/14.
 */
public interface CustomerProgramSummaryService extends BaseService<CustomerProgramSummary> {

    public CustomerProgramSummary findByCpsId(Long cpsId);
    public List<CustomerProgramSummary> findByCpsMerchantNoAndCpsLoyaltyId(Long cpsMerchantNo,String cpsLoyaltyId);
    public CustomerProgramSummary findByCpsMerchantNoAndCpsLoyaltyIdAndCpsProgramId(Long cpsMerchantNo,String cpsLoyaltyId,Long cpsProgramId);

    public CustomerProgramSummary saveCustomerProgramSummary(CustomerProgramSummary customerProgramSummary);
    public boolean deleteCustomerProgramSummary(CustomerProgramSummary customerProgramSummary);



}
