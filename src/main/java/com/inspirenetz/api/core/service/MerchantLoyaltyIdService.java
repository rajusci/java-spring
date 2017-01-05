package com.inspirenetz.api.core.service;

import com.inspirenetz.api.core.domain.MerchantLoyaltyId;
import com.inspirenetz.api.core.domain.MerchantLoyaltyId;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Created by sandheepgr on 28/3/14.
 */
public interface MerchantLoyaltyIdService extends BaseService<MerchantLoyaltyId> {

    public MerchantLoyaltyId findByMliMerchantNo(Long mliMerchantNo);
    public Long getNextLoyaltyId(Long mliMerchantNo);

    public MerchantLoyaltyId saveMerchantLoyaltyId(MerchantLoyaltyId merchantLoyaltyId);
    public boolean deleteMerchantLoyaltyId(Long mliMerchantNo);

}
