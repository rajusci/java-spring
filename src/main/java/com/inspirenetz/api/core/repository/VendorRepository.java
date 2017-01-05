package com.inspirenetz.api.core.repository;

import com.inspirenetz.api.core.domain.Vendor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Created by sandheepgr on 28/3/14.
 */
public interface VendorRepository extends  BaseRepository<Vendor,Long> {

    public Vendor findByVenId(Long venId);
    public Vendor findByVenMerchantNoAndVenName(Long venMerchantNo,String venName);
    public Page<Vendor> findByVenMerchantNo(Long venMerchantNo, Pageable pageable);
    public Page<Vendor> findByVenMerchantNoAndVenNameLike(Long venMerchantNo, String venName, Pageable pageable);

}
