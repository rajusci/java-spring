package com.inspirenetz.api.core.repository;

import com.inspirenetz.api.core.domain.MerchantProgramSummary;

import java.util.List;

/**
 * Created by sandheepgr on 28/3/14.
 */
public interface MerchantProgramSummaryRepository extends  BaseRepository<MerchantProgramSummary,Long> {

    public MerchantProgramSummary findByMpsId(Long mpsId);
    public List<MerchantProgramSummary> findByMpsMerchantNo(Long mpsMerchantNo);
    public List<MerchantProgramSummary> findByMpsMerchantNoAndMpsBranch(Long mpsMerchantNo, Long mpsBranch);
    public MerchantProgramSummary findByMpsMerchantNoAndMpsBranchAndMpsProgramId(Long mpsMerchantNo, Long mpsBranch, Long mpsProgramId);

}
