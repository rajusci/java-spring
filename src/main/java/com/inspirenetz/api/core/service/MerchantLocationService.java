package com.inspirenetz.api.core.service;

import com.inspirenetz.api.core.domain.MerchantLocation;
import com.inspirenetz.api.rest.exception.InspireNetzException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.HashMap;
import java.util.List;
import java.util.Set;

/**
 * Created by sandheepgr on 28/3/14.
 */
public interface MerchantLocationService extends BaseService<MerchantLocation> {

    public MerchantLocation findByMelId(Long melId);
    public Page<MerchantLocation> findByMelMerchantNo(Long melMerchantNo, Pageable pageable);
    public Page<MerchantLocation> findByMelMerchantNoAndMelLocationLike(Long melMerchantNo,String melLocation, Pageable pageable);
    public MerchantLocation findByMelMerchantNoAndMelLocation(Long melMerchantNo, String melLocation);
    public HashMap<Long, MerchantLocation> getMerchantLocationAsMap(Long melMerchantNo);
    public boolean isMerchantLocationDuplicateExisting(MerchantLocation merchantLocation);

    public MerchantLocation saveMerchantLocation(MerchantLocation merchantLocation) throws InspireNetzException;
    public boolean deleteMerchantLocation(Long brnId) throws InspireNetzException;

    public MerchantLocation validateAndSaveMerchantLocation(MerchantLocation merchantLocation) throws InspireNetzException;
    public boolean validateAndDeleteMerchantLocation(Long brnId) throws InspireNetzException;

    void deleteMerchantLocationSet(Set<MerchantLocation> merchantLocationsToDelete);
    public List<MerchantLocation> findByMerchantNoAndMerchantLocation(Long melMerchantNo, Long melLocation);




}
