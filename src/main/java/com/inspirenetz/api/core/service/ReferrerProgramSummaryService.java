package com.inspirenetz.api.core.service;

import com.inspirenetz.api.core.domain.ReferrerProgramSummary;

/**
 * Created by ameen on 8/10/15.
 */
public interface ReferrerProgramSummaryService extends BaseService<ReferrerProgramSummary> {


    public ReferrerProgramSummary findByRpsId(Long rpsId);
    public ReferrerProgramSummary findByRpsMerchantNoAndRpsRefereeLoyaltyId(Long rpsMerchantNo,String refereeLoyaltyId);
    public ReferrerProgramSummary findByRpsMerchantNoAndRpsRefereeLoyaltyIdAndRpsProgramId(Long rpsMerchantNo,String refereeLoyaltyId,Long rpsProgramId);
    public ReferrerProgramSummary saveReferrerProgramSummary(ReferrerProgramSummary referrerProgramSummary);

    public void delete(ReferrerProgramSummary referrerProgramSummary);
}
