package com.inspirenetz.api.core.repository;

import com.inspirenetz.api.core.domain.LoyaltyProgramSkuExtension;

/**
 * Created by sandheepgr on 28/3/14.
 */
public interface LoyaltyProgramSkuExtensionRepository extends  BaseRepository<LoyaltyProgramSkuExtension,Long> {


    public LoyaltyProgramSkuExtension findByLueId(Long lueId);


}
