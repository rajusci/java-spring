package com.inspirenetz.api.core.service;

import com.inspirenetz.api.core.domain.Merchant;
import com.inspirenetz.api.core.domain.MerchantRedemptionPartner;
import com.inspirenetz.api.core.domain.RedemptionMerchant;
import com.inspirenetz.api.rest.exception.InspireNetzException;
import com.inspirenetz.api.rest.resource.MerchantRedemptionPartnerResource;
import org.springframework.data.domain.Page;

import java.util.List;

/**
 * Created by ameen on 25/6/15.
 */
public interface MerchantRedemptionPartnerService {

    public List<MerchantRedemptionPartner> findByMrpMerchantNo(Long mrpMerchantNo);
    public MerchantRedemptionPartner findByMrpId(Long mrpId);
    public MerchantRedemptionPartner findByMrpMerchantNoAndMrpRedemptionMerchant(Long mrpMerchantNo,Long mrpRedemptionMerchant);

    public MerchantRedemptionPartner  saveMerchantRedemptionPartner(MerchantRedemptionPartner merchantRedemptionPartner);
    public void deleteMerchantRedemptionPartner(MerchantRedemptionPartner merchantRedemptionPartner);
    public MerchantRedemptionPartner validateAndSaveMerchantRedemptionPartner(MerchantRedemptionPartner merchantRedemptionPartner);

    public Page<MerchantRedemptionPartner> getRedemptionPartnerFormAdminFilter(Long merchantNo,String filter,String query);
    public List<MerchantRedemptionPartner> findByMrpMerchantNoAndMrpEnabled(Long merchantNo);

    public List<Merchant> getMerchantsForRedemptionPartner(String userLoginId) throws InspireNetzException;

    List<Merchant> getMerchantsListByRedemptionPartner(Long redemptionPartnerNo);

    List<MerchantRedemptionPartner> findByMrpRedemptionMerchantAndMrpEnabled(Long redemptionPartnerNo, int mrpEnabledStatus);

    List<Merchant> getMerchantPartnersByPartnerCode(String partnerCode);

    List<RedemptionMerchant> getPartnersForMerchant(Long merchantNo) throws InspireNetzException;

    public List<MerchantRedemptionPartner> findByMrpRedemptionMerchant(Long mrpRedemptionMerchant);

    public double getCashbackQtyForAmount(Double mrpCashbackRatioDeno,double amount);

    List<MerchantRedemptionPartnerResource> getMerchantRedemptionPartnerResources(Long mrpRedemptionMerchant);
}
