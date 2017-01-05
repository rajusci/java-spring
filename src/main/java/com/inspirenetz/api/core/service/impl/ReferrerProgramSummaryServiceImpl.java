package com.inspirenetz.api.core.service.impl;

import com.inspirenetz.api.core.domain.ReferrerProgramSummary;
import com.inspirenetz.api.core.repository.BaseRepository;
import com.inspirenetz.api.core.repository.ReferrerProgramSummaryRepository;
import com.inspirenetz.api.core.service.ReferrerProgramSummaryService;
import com.inspirenetz.api.util.AuthSessionUtils;
import com.inspirenetz.api.util.GeneralUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.sql.DataSource;

/**
 * Created by ameen on 8/10/15.
 */
@Service
public class ReferrerProgramSummaryServiceImpl extends BaseServiceImpl<ReferrerProgramSummary> implements ReferrerProgramSummaryService {

    private static Logger log = LoggerFactory.getLogger(ReferrerProgramSummaryServiceImpl.class);


    @Autowired
    ReferrerProgramSummaryRepository referrerProgramSummaryRepository;



    public ReferrerProgramSummaryServiceImpl() {

        super(ReferrerProgramSummary.class);

    }


    @Override
    protected BaseRepository<ReferrerProgramSummary,Long> getDao() {
        return referrerProgramSummaryRepository;
    }


    @Override
    public ReferrerProgramSummary findByRpsId(Long rpsId) {
        return referrerProgramSummaryRepository.findByRpsId(rpsId);
    }

    @Override
    public ReferrerProgramSummary findByRpsMerchantNoAndRpsRefereeLoyaltyId(Long rpsMerchantNo, String refereeLoyaltyId) {
        return referrerProgramSummaryRepository.findByRpsMerchantNoAndRpsRefereeLoyaltyId(rpsMerchantNo,refereeLoyaltyId);
    }

    @Override
    public ReferrerProgramSummary findByRpsMerchantNoAndRpsRefereeLoyaltyIdAndRpsProgramId(Long rpsMerchantNo, String refereeLoyaltyId, Long rpsProgramId) {
        return referrerProgramSummaryRepository.findByRpsMerchantNoAndRpsRefereeLoyaltyIdAndRpsProgramId(rpsMerchantNo,refereeLoyaltyId,rpsProgramId);
    }

    @Override
    public ReferrerProgramSummary saveReferrerProgramSummary(ReferrerProgramSummary referrerProgramSummary) {
        return referrerProgramSummaryRepository.save(referrerProgramSummary);
    }

    @Override
    public void delete(ReferrerProgramSummary referrerProgramSummary) {
        referrerProgramSummaryRepository.delete(referrerProgramSummary);
    }
}
