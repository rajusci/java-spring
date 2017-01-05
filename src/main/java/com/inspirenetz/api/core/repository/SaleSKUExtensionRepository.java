package com.inspirenetz.api.core.repository;

import com.inspirenetz.api.core.domain.SaleSKUExtension;

/**
 * Created by sandheepgr on 28/3/14.
 */
public interface SaleSKUExtensionRepository extends  BaseRepository<SaleSKUExtension,Long> {

    public SaleSKUExtension findBySseId(Long sseId);

}
