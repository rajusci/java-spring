package com.inspirenetz.api.core.repository;

import com.inspirenetz.api.core.domain.MerchantModule;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
 * Created by sandheepgr on 28/3/14.
 */
public interface MerchantModuleRepository extends  BaseRepository<MerchantModule,Long> {

    public MerchantModule findByMemMerchantNoAndMemModuleId(Long memMerchantNo, Long memModuleId);
    public List<MerchantModule> findByMemMerchantNo(Long memMerchantNo);

}
