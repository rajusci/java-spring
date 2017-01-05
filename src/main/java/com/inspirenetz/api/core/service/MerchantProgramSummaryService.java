package com.inspirenetz.api.core.service;

import com.inspirenetz.api.core.domain.Brand;
import com.inspirenetz.api.core.domain.MerchantProgramSummary;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

/**
 * Created by sandheepgr on 28/3/14.
 */
public interface MerchantProgramSummaryService extends BaseService<MerchantProgramSummary> {


    public MerchantProgramSummary findByMpsId(Long mpsId);
    public List<MerchantProgramSummary> findByMpsMerchantNo(Long mpsMerchantNo);
    public List<MerchantProgramSummary> findByMpsMerchantNoAndMpsBranch(Long mpsMerchantNo, Long mpsBranch);
    public MerchantProgramSummary findByMpsMerchantNoAndMpsBranchAndMpsProgramId(Long mpsMerchantNo, Long mpsBranch, Long mpsProgramId);


    public MerchantProgramSummary saveMerchantProgramSummary(MerchantProgramSummary merchantProgramSummary);
    public boolean deleteMerchantProgramSummary(MerchantProgramSummary merchantProgramSummary);

}
