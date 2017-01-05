package com.inspirenetz.api.core.service.impl;

import com.inspirenetz.api.core.domain.MerchantProgramSummary;
import com.inspirenetz.api.core.repository.BaseRepository;
import com.inspirenetz.api.core.repository.MerchantProgramSummaryRepository;
import com.inspirenetz.api.core.service.MerchantProgramSummaryService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by sandheepgr on 28/3/14.
 */
@Service
public class MerchantProgramSummaryServiceImpl extends BaseServiceImpl<MerchantProgramSummary> implements MerchantProgramSummaryService {

    private static Logger log = LoggerFactory.getLogger(MerchantProgramSummaryServiceImpl.class);


    @Autowired
    MerchantProgramSummaryRepository merchantProgramSummaryRepository;


    public MerchantProgramSummaryServiceImpl() {

        super(MerchantProgramSummary.class);

    }


    @Override
    protected BaseRepository<MerchantProgramSummary,Long> getDao() {
        return merchantProgramSummaryRepository;
    }


    @Override
    public MerchantProgramSummary findByMpsId(Long mpsId) {

        // Get the MerchantProgramSummary
        MerchantProgramSummary merchantProgramSummary = merchantProgramSummaryRepository.findByMpsId(mpsId);

        // Return the object
        return merchantProgramSummary;

    }

    @Override
    public List<MerchantProgramSummary> findByMpsMerchantNo(Long mpsMerchantNo) {

        // Get the list
        List<MerchantProgramSummary> merchantProgramSummaryList = merchantProgramSummaryRepository.findByMpsMerchantNo(mpsMerchantNo);

        // Return the list;
        return merchantProgramSummaryList;
    }

    @Override
    public List<MerchantProgramSummary> findByMpsMerchantNoAndMpsBranch(Long mpsMerchantNo, Long mpsBranch) {

        // Get the list
        List<MerchantProgramSummary> merchantProgramSummaryList = merchantProgramSummaryRepository.findByMpsMerchantNoAndMpsBranch(mpsMerchantNo,mpsBranch);

        // Return the list
        return merchantProgramSummaryList;

    }

    @Override
    public MerchantProgramSummary findByMpsMerchantNoAndMpsBranchAndMpsProgramId(Long mpsMerchantNo, Long mpsBranch, Long mpsProgramId) {

        // Get the MerchantProgramSummary object
        MerchantProgramSummary merchantProgramSummary = merchantProgramSummaryRepository.findByMpsMerchantNoAndMpsBranchAndMpsProgramId(mpsMerchantNo,mpsBranch,mpsProgramId);

        // Return the MerchantProgramSummary object
        return merchantProgramSummary;

    }

    @Override
    public MerchantProgramSummary saveMerchantProgramSummary(MerchantProgramSummary merchantProgramSummary ){

        // Save the merchantProgramSummary
        return merchantProgramSummaryRepository.save(merchantProgramSummary);

    }


    @Override
    public boolean deleteMerchantProgramSummary(MerchantProgramSummary merchantProgramSummary) {

        // Delete the merchantProgramSummary
        merchantProgramSummaryRepository.delete(merchantProgramSummary);

        // return true
        return true;

    }
}
