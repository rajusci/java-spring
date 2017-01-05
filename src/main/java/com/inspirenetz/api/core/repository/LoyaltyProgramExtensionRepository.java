package com.inspirenetz.api.core.repository;

import com.inspirenetz.api.core.domain.LoyaltyProgramExtension;

/**
 * Created by sandheepgr on 28/3/14.
 */
public interface LoyaltyProgramExtensionRepository extends  BaseRepository<LoyaltyProgramExtension,Long> {


    public LoyaltyProgramExtension findByLpeId(Long lpeId);


}
