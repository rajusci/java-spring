package com.inspirenetz.api.core.repository;

import com.inspirenetz.api.core.domain.LoyaltyProgram;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

/**
 * Created by sandheepgr on 17/2/14.
 */
public interface LoyaltyProgramRepository extends BaseRepository<LoyaltyProgram,Long> {

    public LoyaltyProgram findByPrgProgramNo(Long prgProgramNo);
    public LoyaltyProgram findByPrgMerchantNoAndPrgProgramName(Long prgMerchantNo,String prgProgramName);
    public Page<LoyaltyProgram> findByPrgMerchantNo(Long prgMerchantNo, Pageable pageable);
    public Page<LoyaltyProgram> findByPrgMerchantNoAndPrgProgramNameLike(Long prgMerchantNo, String query, Pageable pageable);
    public List<LoyaltyProgram> findByPrgMerchantNoAndPrgStatus(Long prgMerchantNo, int prgStatus);
    public List<LoyaltyProgram> findByPrgMerchantNoAndPrgStatusAndPrgProgramNameLike(Long prgMerchantNo, int prgStatus, String query);
    public List<LoyaltyProgram> findByPrgMerchantNoAndPrgProgramDriver(Long prgMerchantNo,Integer prgProgramDriver);

}


