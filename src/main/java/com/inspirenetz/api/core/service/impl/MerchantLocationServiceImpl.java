package com.inspirenetz.api.core.service.impl;

import com.inspirenetz.api.core.dictionary.FunctionCode;
import com.inspirenetz.api.core.domain.MerchantLocation;
import com.inspirenetz.api.core.repository.BaseRepository;
import com.inspirenetz.api.core.repository.MerchantLocationRepository;
import com.inspirenetz.api.core.service.MerchantLocationService;
import com.inspirenetz.api.rest.exception.InspireNetzException;
import com.inspirenetz.api.util.AuthSessionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Set;

/**
 * Created by sandheepgr on 28/3/14.
 */
@Service
public class MerchantLocationServiceImpl extends BaseServiceImpl<MerchantLocation> implements MerchantLocationService {

    private static Logger log = LoggerFactory.getLogger(MerchantLocationServiceImpl.class);


    @Autowired
    MerchantLocationRepository merchantLocationRepository;

    @Autowired
    AuthSessionUtils authSessionUtils;


    public MerchantLocationServiceImpl() {

        super(MerchantLocation.class);

    }


    @Override
    protected BaseRepository<MerchantLocation,Long> getDao() {
        return merchantLocationRepository;
    }




    @Override
    public Page<MerchantLocation> findByMelMerchantNo(Long melMerchantNo, Pageable pageable) {

        // Get the merchant locations
        Page<MerchantLocation> merchantLocations = merchantLocationRepository.findByMelMerchantNo(melMerchantNo,pageable);

        // return the location
        return merchantLocations;

    }

    @Override
    public Page<MerchantLocation> findByMelMerchantNoAndMelLocationLike(Long melMerchantNo, String melLocation, Pageable pageable) {

        // Get the MerchantLocation
        Page<MerchantLocation> merchantLocationPage = merchantLocationRepository.findByMelMerchantNoAndMelLocationLike(melMerchantNo,melLocation,pageable);

        // Return the merchantLocationPage
        return merchantLocationPage;

    }

    @Override
    public MerchantLocation findByMelId(Long melId) {

        // Get the merchant location
        MerchantLocation merchantLocation = merchantLocationRepository.findByMelId(melId);

        // Return the merchant location
        return merchantLocation;

    }

    @Override
    public MerchantLocation findByMelMerchantNoAndMelLocation(Long melMerchantNo, String melLocation) {

        // Get the merchantLocation
        MerchantLocation merchantLocation = merchantLocationRepository.findByMelMerchantNoAndMelLocation(melMerchantNo,melLocation);

        // Return the merchantLocation
        return merchantLocation;

    }

    @Override
    public HashMap<Long, MerchantLocation> getMerchantLocationAsMap(Long melMerchantNo) {

        // Get the list of merchants
        List<MerchantLocation> merchantLocationList = merchantLocationRepository.findByMelMerchantNo(melMerchantNo);

        // HashMap for storing the information with key as the location id adn
        // value as the map
        HashMap<Long,MerchantLocation> locationHashMap =  new HashMap<>(0);


        // Go through the list and add the items to hashmap
        for(MerchantLocation merchantLocation : merchantLocationList ) {

            locationHashMap.put(merchantLocation.getMelId(),merchantLocation);

        }


        // Return the HashMap
        return locationHashMap;

    }

    @Override
    public boolean isMerchantLocationDuplicateExisting(MerchantLocation merchantLocation) {

        // Get the merchantLocation information
        MerchantLocation exMerchantLocation = merchantLocationRepository.findByMelMerchantNoAndMelLocation(merchantLocation.getMelMerchantNo(), merchantLocation.getMelLocation());

        // If the melId is 0L, then its a new merchantLocation so we just need to check if there is ano
        // ther merchantLocation code
        if ( merchantLocation.getMelId() == null || merchantLocation.getMelId() == 0L ) {

            // If the merchantLocation is not null, then return true
            if ( exMerchantLocation != null ) {

                return true;

            }

        } else {

            // Check if the merchantLocation is null
            if ( exMerchantLocation != null && merchantLocation.getMelId().longValue() != exMerchantLocation.getMelId().longValue() ) {

                return true;

            }
        }


        // Return false;
        return false;

    }




    @Override
    public MerchantLocation saveMerchantLocation(MerchantLocation merchantLocation ) throws InspireNetzException {

        // Save the merchantLocation
        return merchantLocationRepository.save(merchantLocation);

    }

    @Override
    public boolean deleteMerchantLocation(Long melId) throws InspireNetzException {

        // Delete the merchantLocation
        merchantLocationRepository.delete(melId);

        // return true
        return true;

    }

    @Override
    public MerchantLocation validateAndSaveMerchantLocation(MerchantLocation merchantLocation) throws InspireNetzException {

        //check the access rights of the user
        authSessionUtils.validateFunctionAccess(FunctionCode.FNC_UPDATE_MERCHANT_USER);

        return saveMerchantLocation(merchantLocation);
    }

    @Override
    public boolean validateAndDeleteMerchantLocation(Long brnId) throws InspireNetzException {


        //check the access rights of the user
        authSessionUtils.validateFunctionAccess(FunctionCode.FNC_DELETE_MERCHANT_USER);

        return deleteMerchantLocation(brnId);

    }

    @Override
    public void deleteMerchantLocationSet(Set<MerchantLocation> merchantLocationsToDelete) {

        merchantLocationRepository.delete(merchantLocationsToDelete);
    }

    @Override
    public List<MerchantLocation> findByMerchantNoAndMerchantLocation(Long merMerchantNo,Long merLocation) {

        if(merLocation==0){

            return merchantLocationRepository.findByMelMerchantNo(merMerchantNo);

        }else{

            return merchantLocationRepository.findByMelMerchantNoAndMelId(merMerchantNo,merLocation);
        }
    }


}
