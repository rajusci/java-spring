package com.inspirenetz.api.core.repository;

import com.inspirenetz.api.core.domain.MerchantLocation;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

/**
 * Created by sandheepgr on 28/3/14.
 */
public interface MerchantLocationRepository extends  BaseRepository<MerchantLocation,Long> {

    public MerchantLocation findByMelId(Long melId);
    public List<MerchantLocation> findByMelMerchantNo(Long melMerchantNo);
    public Page<MerchantLocation> findByMelMerchantNo(Long melMerchantNo, Pageable pageable);
    public Page<MerchantLocation> findByMelMerchantNoAndMelLocationLike(Long melMerchantNo,String melLocation, Pageable pageable);
    public MerchantLocation findByMelMerchantNoAndMelLocation(Long melMerchantNo, String melLocation);
    public List<MerchantLocation> findByMelMerchantNoAndMelId(Long melMerchantNo, Long melId);

}
