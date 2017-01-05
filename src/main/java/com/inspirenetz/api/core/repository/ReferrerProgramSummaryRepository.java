package com.inspirenetz.api.core.repository;

import com.inspirenetz.api.core.domain.ReferrerProgramSummary;

/**
 * Created by ameen on 8/10/15.
 */
public interface ReferrerProgramSummaryRepository extends BaseRepository<ReferrerProgramSummary,Long>{

    public ReferrerProgramSummary findByRpsId(Long rpsId);
    public ReferrerProgramSummary findByRpsMerchantNoAndRpsRefereeLoyaltyId(Long rpsMerchantNo,String refereeLoyaltyId);
    public ReferrerProgramSummary findByRpsMerchantNoAndRpsRefereeLoyaltyIdAndRpsProgramId(Long rpsMerchantNo,String refereeLoyaltyId,Long rpsProgramId);
}
