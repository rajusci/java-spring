package com.inspirenetz.api.core.service;

import com.inspirenetz.api.core.domain.TierGroup;
import com.inspirenetz.api.rest.exception.InspireNetzException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

/**
 * Created by saneeshci on 20/8/14.
 */
public interface TierGroupService extends BaseService<TierGroup> {

    public TierGroup findByTigId(Long TigId);
    public List<TierGroup> findByTigMerchantNo(Long tigMerchantNo);
    public boolean isTierGroupNameDuplicateExisting(TierGroup TierGroup);
    public TierGroup findByTigMerchantNoAndTigName(Long merchantNo,String tigName);

    public TierGroup saveTierGroup(TierGroup TierGroup) throws InspireNetzException;
    public boolean deleteTierGroup(Long TigId);
    public Page<TierGroup> searchTierGroups(Long tigMerchantNo, Long tigLocation, Integer userType, String filter, String query, Pageable pageable);





}
