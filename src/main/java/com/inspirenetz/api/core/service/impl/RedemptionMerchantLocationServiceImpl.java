package com.inspirenetz.api.core.service.impl;

import com.inspirenetz.api.core.dictionary.APIErrorCode;
import com.inspirenetz.api.core.dictionary.UserType;
import com.inspirenetz.api.core.domain.RedemptionMerchantLocation;
import com.inspirenetz.api.core.repository.BaseRepository;
import com.inspirenetz.api.core.repository.RedemptionMerchantLocationRepository;
import com.inspirenetz.api.core.service.RedemptionMerchantLocationService;
import com.inspirenetz.api.rest.exception.InspireNetzException;
import com.inspirenetz.api.util.AuthSessionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;

/**
 * Created by saneesh-ci on 25/9/14.
 */

@Service
public class RedemptionMerchantLocationServiceImpl extends BaseServiceImpl<RedemptionMerchantLocation> implements RedemptionMerchantLocationService {



    private static Logger log = LoggerFactory.getLogger(RedemptionMerchantLocationServiceImpl.class);

    @Autowired
    RedemptionMerchantLocationRepository redemptionMerchantLocationRepository;


    @Autowired
    private AuthSessionUtils authSessionUtils;

    public RedemptionMerchantLocationServiceImpl() {

        super(RedemptionMerchantLocation.class);

    }
    @Override
    protected BaseRepository<RedemptionMerchantLocation, Long> getDao() {
        return redemptionMerchantLocationRepository;
    }


    @Override
    public RedemptionMerchantLocation findByRmlId(Long rmlId) {

        // Getting data based by rmlId
        RedemptionMerchantLocation redemptionMerchantLocation=redemptionMerchantLocationRepository.findByRmlId(rmlId);
        return redemptionMerchantLocation;
    }

    @Override
    public List<RedemptionMerchantLocation> findByRmlMerNo(Long rmlMerNo) {

        // Getting data by rmlMerNo
        List<RedemptionMerchantLocation> redemptionMerchantLocationList=redemptionMerchantLocationRepository.findByRmlMerNo(rmlMerNo);

        return redemptionMerchantLocationList;
    }

    @Override
    public RedemptionMerchantLocation saveRedemptionMerchantLocation(RedemptionMerchantLocation redemptionMerchantLocation) {

        RedemptionMerchantLocation redemptionMerchantLocation1  =redemptionMerchantLocationRepository.save(redemptionMerchantLocation);

        return redemptionMerchantLocation1;
    }


    @Override
    public boolean deleteRedemptionMerchantLocation(Long rmlId) {

        //for deleting the redemptionMerchantLocation
        redemptionMerchantLocationRepository.delete(rmlId);

        return true;
    }


    /**
     * modified by :saneeshci
     * purpose:delete redemptionMerchantLocation
     * @param redemptionMerchantLocationSet
     */
    @Override
    public void deleteRedemptionMerchantLocationSet(Set<RedemptionMerchantLocation> redemptionMerchantLocationSet){

        redemptionMerchantLocationRepository.delete(redemptionMerchantLocationSet);

    }

}
