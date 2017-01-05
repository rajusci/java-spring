package com.inspirenetz.api.core.service.impl;

import com.inspirenetz.api.core.domain.MerchantRewardSummary;
import com.inspirenetz.api.core.repository.BaseRepository;
import com.inspirenetz.api.core.repository.MerchantRewardSummaryRepository;
import com.inspirenetz.api.core.service.MerchantRewardSummaryService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 * Created by sandheepgr on 28/3/14.
 */
@Service
public class MerchantRewardSummaryServiceImpl extends BaseServiceImpl<MerchantRewardSummary> implements MerchantRewardSummaryService {

    private static Logger log = LoggerFactory.getLogger(MerchantRewardSummaryServiceImpl.class);


    @Autowired
    MerchantRewardSummaryRepository merchantRewardSummaryRepository;


    public MerchantRewardSummaryServiceImpl() {

        super(MerchantRewardSummary.class);

    }


    @Override
    protected BaseRepository<MerchantRewardSummary,Long> getDao() {
        return merchantRewardSummaryRepository;
    }


    @Override
    public MerchantRewardSummary findByMrsId(Long mrsId) {

        // Get the MerchantRewardSummary
        MerchantRewardSummary merchantRewardSummary = merchantRewardSummaryRepository.findByMrsId(mrsId);

        // Return the object
        return merchantRewardSummary;

    }

    @Override
    public List<MerchantRewardSummary> findByMrsMerchantNo(Long mpsMerchantNo) {

        // Get the list
        List<MerchantRewardSummary> merchantRewardSummaryList = merchantRewardSummaryRepository.findByMrsMerchantNo(mpsMerchantNo);

        // Return the list;
        return merchantRewardSummaryList;
    }

    @Override
    public List<MerchantRewardSummary> findByMrsMerchantNoAndMrsCurrencyId(Long mrsMerchantNo, Long mrsCurrencyId) {

        // Get the list
        List<MerchantRewardSummary> merchantRewardSummaryList = merchantRewardSummaryRepository.findByMrsMerchantNoAndMrsCurrencyId(mrsMerchantNo,mrsCurrencyId);

        // Return the list
        return merchantRewardSummaryList;

    }

    @Override
    public List<MerchantRewardSummary> findByMrsMerchantNoAndMrsCurrencyIdAndMrsBranch(Long mrsMerchantNo, Long mrsCurrencyId, Long mrsBranch) {

        // Get the list
        List<MerchantRewardSummary> merchantRewardSummaryList = merchantRewardSummaryRepository.findByMrsMerchantNoAndMrsCurrencyIdAndMrsBranch(mrsMerchantNo,mrsCurrencyId,mrsBranch);

        // Return the list
        return merchantRewardSummaryList;

    }

    @Override
    public MerchantRewardSummary findByMrsMerchantNoAndMrsCurrencyIdAndMrsBranchAndMrsDate(Long mrsMerchantNo, Long mrsCurrencyId, Long mrsBranch, Date mrsDate) {

        // Get the MerchantRewardSummary
        MerchantRewardSummary merchantRewardSummary = merchantRewardSummaryRepository.findByMrsMerchantNoAndMrsCurrencyIdAndMrsBranchAndMrsDate(mrsMerchantNo,mrsCurrencyId,mrsBranch,mrsDate);

        // Return the object
        return merchantRewardSummary;

    }


    @Override
    public MerchantRewardSummary saveMerchantRewardSummary(MerchantRewardSummary merchantRewardSummary ){

        // Save the merchantRewardSummary
        return merchantRewardSummaryRepository.save(merchantRewardSummary);

    }


    @Override
    public boolean deleteMerchantRewardSummary(MerchantRewardSummary merchantRewardSummary) {

        // Delete the merchantRewardSummary
        merchantRewardSummaryRepository.delete(merchantRewardSummary);

        // return true
        return true;

    }
}
