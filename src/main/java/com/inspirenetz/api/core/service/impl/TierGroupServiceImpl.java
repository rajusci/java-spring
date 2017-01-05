package com.inspirenetz.api.core.service.impl;

import com.inspirenetz.api.core.dictionary.APIErrorCode;
import com.inspirenetz.api.core.dictionary.UserType;
import com.inspirenetz.api.core.domain.TierGroup;
import com.inspirenetz.api.core.repository.BaseRepository;
import com.inspirenetz.api.core.repository.TierGroupRepository;
import com.inspirenetz.api.core.service.TierGroupService;
import com.inspirenetz.api.rest.exception.InspireNetzException;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by saneeshci on 20/8/14.
 */
@Service
public class TierGroupServiceImpl extends BaseServiceImpl<TierGroup> implements TierGroupService {


    private static Logger log = LoggerFactory.getLogger(TierGroupServiceImpl.class);


    @Autowired
    TierGroupRepository tierGroupRepository;


    public TierGroupServiceImpl() {

        super(TierGroup.class);

    }


    @Override
    protected BaseRepository<TierGroup,Long> getDao() {
        return tierGroupRepository;
    }


    @Override
    public TierGroup findByTigId(Long tigId) {

        // Get the TierGroup for the given tigId from the repository
        TierGroup tierGroup = tierGroupRepository.findByTigId(tigId);

        // Get the RewardCurrency by lazy loading
        tierGroup.getRewardCurrency().getRwdCurrencyId();

        // Return the TierGroup
        return tierGroup;


    }

    @Override
    public List<TierGroup> findByTigMerchantNo(Long tigMerchantNo) {

        // Get the TierGroup list
        List<TierGroup> tierGroupList = tierGroupRepository.findByTigMerchantNo(tigMerchantNo);

        // Go through the tierGroupList and then call the lazy getRewardCurrency method
        for( TierGroup tierGroup : tierGroupList ) {

            // Call the get reward currency method for lazy loading
            tierGroup.getRewardCurrency().getRwdCurrencyId();

        }

        // Return the list
        return tierGroupList;

    }


    @Override
    public TierGroup findByTigMerchantNoAndTigName(Long merchantNo,String tigName){

        TierGroup tierGroup = tierGroupRepository.findByTigMerchantNoAndTigName(merchantNo, tigName);

        // Get the RewardCurrency by lazy loading
        tierGroup.getRewardCurrency().getRwdCurrencyId();

        // Return the group
        return tierGroup;

    }


    @Override
    public Page<TierGroup> searchTierGroups(Long tigMerchantNo, Long tigLocation, Integer userType, String filter, String query, Pageable pageable) {

        Page<TierGroup> tierGroupList ;


        //if usertype is merchant admin then , we need to return all the locations data
        //,set lcoation to zero
        if(userType == UserType.MERCHANT_ADMIN) {

            tigLocation = 0L;

        }

        // If the filter is name, then get the details for the name
        // else get the details for the merchant number
        if(filter.equalsIgnoreCase("name")) {

            // Get the data from the repository and store in the list
            tierGroupList = tierGroupRepository.searchTierGroupsByName(tigMerchantNo, tigLocation, query, pageable);

        } else {

            // Get the data from the repository and store in the list
            tierGroupList = tierGroupRepository.searchTierGroups(tigMerchantNo, tigLocation, pageable);

        }


        // Go through the tierGroupList and then call the lazy getRewardCurrency method
        for( TierGroup tierGroup : tierGroupList.getContent() ) {

            // Call the get reward currency method for lazy loading
            tierGroup.getRewardCurrency().getRwdCurrencyId();

        }
        

        // Return the list
        return tierGroupList;

    }

    @Override
    public boolean isTierGroupNameDuplicateExisting(TierGroup tierGroup) {

        // Get the TierGroup information
        TierGroup exTierGroup = tierGroupRepository.findByTigMerchantNoAndTigName(tierGroup.getTigMerchantNo(), tierGroup.getTigName());

        // If the tigId is 0L, then its a new TierGroup so we just need to check if there is any
        // there TierGroup name
        if ( tierGroup.getTigId() == null || tierGroup.getTigId() == 0L ) {

            // If the TierGroup is not null, then return true
            if ( exTierGroup != null ) {

                return true;

            }

        } else {

            // Check if the brand is null
            if ( exTierGroup != null && tierGroup.getTigId().longValue() != exTierGroup.getTigId().longValue() ) {

                return true;

            }
        }
        // Return false;
        return false;

    }

    @Override
    public TierGroup saveTierGroup(TierGroup tierGroup ) throws InspireNetzException {


        // Check if the tierGroup is existing
        boolean isExist = isTierGroupNameDuplicateExisting(tierGroup);

        // Check the boolean value
        if ( isExist ) {

            // Log the response
            log.info("saveTierGroup - Response : TierGroup name is already existing");

            // Throw InspireNetzException with ERR_DUPLICATE_ENTRY as error
            throw new InspireNetzException(APIErrorCode.ERR_DUPLICATE_ENTRY);


        }

        // Save the TierGroup
        return tierGroupRepository.save(tierGroup);

    }

    @Override
    public boolean deleteTierGroup(Long tigId) {

        // Delete the TierGroup
        tierGroupRepository.delete(tigId);

        // return true
        return true;

    }

}
