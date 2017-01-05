package com.inspirenetz.api.core.repository;

import com.inspirenetz.api.core.domain.LoyaltyExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Created by saneesh-ci on 10/9/14.
 */
public interface LoyaltyExtensionRepository extends  BaseRepository<LoyaltyExtension,Long> {

    public LoyaltyExtension findByLexId(Long lexId);
    public Page<LoyaltyExtension> findByLexMerchantNo(Long lexMerchantNo,Pageable pageable);
    public Page<LoyaltyExtension> findByLexMerchantNoAndLexNameLike(Long lexMerchantNo,String lexName,Pageable pageable);

}
