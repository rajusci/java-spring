package com.inspirenetz.api.core.service;

import com.inspirenetz.api.core.domain.*;
import com.inspirenetz.api.rest.exception.InspireNetzException;
import com.inspirenetz.api.rest.resource.PromotionResource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

/**
 * Created by sandheepgr on 28/3/14.
 */
public interface PromotionService extends BaseService<Promotion> {

    public Page<Promotion> findByPrmMerchantNo(Long prmMerchantNo, Pageable pageable);
    public Promotion findByPrmId(Long prmId);
    public Promotion findByPrmMerchantNoAndPrmName(Long prmMerchantNo, String prmName);
    public Page<Promotion> findByPrmMerchantNoAndPrmNameLike(Long prmMerchantNo, String prmName, Pageable pageable);
    public boolean isDuplicatePromotionExisting(Promotion promotion);
    public List<Promotion> getPromotionsForUser(User user);
    public List<Promotion> getPromotionsForCustomer(Customer customer);
    public boolean isPromotionValidForCustomer(Promotion promotion , Customer customer);
    public boolean isPromotionValid(Promotion promotion);
    public Promotion savePromotion(Promotion promotion) throws InspireNetzException;
    public boolean deletePromotion(Long prmId) throws InspireNetzException;

    public Promotion validateAndSavePromotion(PromotionResource promotionResource) throws InspireNetzException;
    public boolean validateAndDeletePromotion(Long prmId) throws InspireNetzException;

    public Promotion updatePromotionView(UserResponse userResponse);

    public Promotion updatePromotionResponse(UserResponse userResponse) throws InspireNetzException;

    public List<Promotion> getComptablePromotion(Long merchantNo);

    public Page<Promotion>getPublicPromotions(Long prmMerchantNo,String query, Pageable pageable);

    public List<Promotion> getPromotionsForCustomer(Customer customer,String query,Pageable pageable);

    public Page<Promotion> getPromotionsForUser(String usrLoginId,Long merchantNo,String query,Pageable pageable);
}
