package com.inspirenetz.api.core.repository;

import com.inspirenetz.api.core.domain.SaleExtension;

/**
 * Created by sandheepgr on 28/3/14.
 */
public interface SaleExtensionRepository extends  BaseRepository<SaleExtension,Long> {


    public SaleExtension findBySaeId(Long saeId);


}
