package com.inspirenetz.api.core.service;

import com.inspirenetz.api.core.dictionary.LoyaltyProgramProductBasedItem;
import com.inspirenetz.api.core.domain.LoyaltyProgram;
import com.inspirenetz.api.core.domain.LoyaltyProgramSku;
import com.inspirenetz.api.core.incustomization.attrext.AttributeExtensionService;
import com.inspirenetz.api.core.loyaltyengine.LoyaltyComputation;
import com.inspirenetz.api.rest.exception.InspireNetzException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Created by sandheepgr on 17/2/14.
 */
public interface LoyaltyProgramService extends BaseService<LoyaltyProgram> , AttributeExtensionService {


    public LoyaltyProgram findByPrgProgramNo(Long prgProgramNo);
    public Page<LoyaltyProgram> findByPrgMerchantNo(Long prgMerchantNo, Pageable pageable);
    public Page<LoyaltyProgram> findByPrgMerchantNoAndPrgProgramNameLike(Long prgMerchantNo, String query, Pageable pageable);
    public List<LoyaltyProgram> findByPrgMerchantNoAndPrgStatus(Long prgMerchantNo, int prgStatus);
    public LoyaltyComputation getLoyaltyComputation(LoyaltyProgram loyaltyProgram);
    public boolean updateLoyaltyProgramStatus(Long prgProgramNo, Integer prgStatus, Long merchantNo, Long userNo) throws InspireNetzException;
    public List<LoyaltyProgramProductBasedItem> getLoyaltyProgramProductBasedItems(String filter, String query,Pageable pageable);
    public LoyaltyProgram saveLoyaltyProgramDataFromParams(Map<String,Object> params) throws InspireNetzException;
    public boolean validateAndDeleteLoyaltyProgram(Long prgProgramNo) throws InspireNetzException;
    public LoyaltyProgram getLoyaltyProgramInfo(Long prgProgramNo) throws InspireNetzException;
    public Page<LoyaltyProgram> searchLoyaltyPrograms(String filter, String query, Pageable pageable);
    public List<LoyaltyProgram> findByPrgMerchantNoAndPrgProgramDriver(Long prgMerchantNo,Integer prgProgramDriver);

    public boolean isDuplicateProgramNameExisting(LoyaltyProgram loyaltyProgram);
    public LoyaltyProgram saveLoyaltyProgram(LoyaltyProgram loyaltyProgram) throws InspireNetzException;
    public boolean deleteLoyaltyProgram(Long catProgramNo);
    public LoyaltyProgram validateAndSaveLoyaltyProgramDataFromParams(Map<String,Object> params) throws InspireNetzException;
    public List<LoyaltyProgram> searchLoyaltyProgramsForCustomer(String usrLoginId,Long merchantNo,String filter, String query);
    public List<LoyaltyProgram>findByPrgMerchantNoAndPrgStatusAndPrgProgramNameLike(Long prgMerchantNo, int prgStatus,String query);

}
