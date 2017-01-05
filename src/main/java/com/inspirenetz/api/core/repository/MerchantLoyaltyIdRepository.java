package com.inspirenetz.api.core.repository;

import com.inspirenetz.api.core.domain.MerchantLoyaltyId;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Created by sandheepgr on 28/3/14.
 */
public interface MerchantLoyaltyIdRepository extends  BaseRepository<MerchantLoyaltyId,Long> {

    public MerchantLoyaltyId findByMliMerchantNo(Long mliMerchantNo);

}
