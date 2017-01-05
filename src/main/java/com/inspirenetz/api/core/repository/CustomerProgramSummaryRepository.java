package com.inspirenetz.api.core.repository;

import com.inspirenetz.api.core.domain.Brand;
import com.inspirenetz.api.core.domain.CustomerProgramSummary;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

/**
 * Created by sandheepgr on 28/3/14.
 */
public interface CustomerProgramSummaryRepository extends  BaseRepository<CustomerProgramSummary,Long> {

    public CustomerProgramSummary findByCpsId(Long cpsId);
    public List<CustomerProgramSummary> findByCpsMerchantNoAndCpsLoyaltyId(Long cpsMerchantNo,String cpsLoyaltyId);
    public CustomerProgramSummary findByCpsMerchantNoAndCpsLoyaltyIdAndCpsProgramId(Long cpsMerchantNo,String cpsLoyaltyId,Long cpsProgramId);


}
