package com.inspirenetz.api.core.repository;

import com.inspirenetz.api.core.domain.AccountArchive;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Created by sandheepgr on 28/3/14.
 */
public interface AccountArchiveRepository extends  BaseRepository<AccountArchive,Long> {

    public AccountArchive findByAarId(Long aarId);
    public AccountArchive findByAarMerchantNoAndAarOldLoyaltyIdAndAarNewLoyaltyId(Long aarMerchantNo, String aarOldLoyaltyId,String aarNewLoyaltyId);

}
