package com.inspirenetz.api.core.service;

import com.inspirenetz.api.core.domain.CustomerProgramSummary;
import com.inspirenetz.api.core.domain.MerchantRewardSummary;

import java.util.Date;
import java.util.List;

/**
 * Created by sandheepgr on 28/3/14.
 */
public interface MerchantRewardSummaryService extends BaseService<MerchantRewardSummary> {


    public MerchantRewardSummary findByMrsId(Long mrsId);
    public List<MerchantRewardSummary> findByMrsMerchantNo(Long mrsMerchantNo);
    public List<MerchantRewardSummary> findByMrsMerchantNoAndMrsCurrencyId(Long mrsMerchantNo,Long mrsCurrencyId);
    public List<MerchantRewardSummary> findByMrsMerchantNoAndMrsCurrencyIdAndMrsBranch(Long mrsMerchantNo,Long mrsCurrencyId,Long mrsBranch);
    public MerchantRewardSummary findByMrsMerchantNoAndMrsCurrencyIdAndMrsBranchAndMrsDate(Long mrsMerchantNo,Long mrsCurrencyId,Long mrsBranch,Date mrsDate);


    public MerchantRewardSummary saveMerchantRewardSummary(MerchantRewardSummary merchantRewardSummary);
    public boolean deleteMerchantRewardSummary(MerchantRewardSummary merchantRewardSummary);

}
