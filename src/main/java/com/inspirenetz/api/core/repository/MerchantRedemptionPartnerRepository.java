package com.inspirenetz.api.core.repository;

import com.inspirenetz.api.core.domain.Merchant;
import com.inspirenetz.api.core.domain.MerchantRedemptionPartner;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

/**
 * Created by ameen on 25/6/15.
 */
public interface MerchantRedemptionPartnerRepository extends BaseRepository<MerchantRedemptionPartner,Long> {

    public List<MerchantRedemptionPartner> findByMrpMerchantNo(Long mrpMerchantNo);
    public MerchantRedemptionPartner findByMrpId(Long mrpId);
    public MerchantRedemptionPartner findByMrpMerchantNoAndMrpRedemptionMerchant(Long mrpMerchantNo,Long mrpRedemptionMerchant);
    public List<MerchantRedemptionPartner> findByMrpRedemptionMerchant(Long mrpRedemptionMerchant);
    public List<MerchantRedemptionPartner> findByMrpMerchantNoAndMrpEnabled(Long mrpMerchantNo,Integer mrpEnabled);
    public List<MerchantRedemptionPartner> findByMrpRedemptionMerchantAndMrpEnabled(Long mrpRedemptionMerchant, Integer mrpEnabled);

}
