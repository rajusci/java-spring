package com.inspirenetz.api.core.repository;

import com.inspirenetz.api.core.domain.CardMaster;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
 * Created by sandheepgr on 28/3/14.
 */
public interface CardMasterRepository extends  BaseRepository<CardMaster,Long> {

    public Page<CardMaster> findByCrmMerchantNo(Long crmMerchantNo, Pageable pageable);
    public List<CardMaster> findByCrmCardStatusNot(Integer crmCardStatus);
    public Page<CardMaster> findByCrmMerchantNoAndCrmCardNoLike(Long crmMerchantNo,String crmCardNo, Pageable pageable);
    public Page<CardMaster> findByCrmMerchantNoAndCrmMobileLike(Long crmMerchantNo,String crmMobile, Pageable pageable);
    public Page<CardMaster> findByCrmMerchantNoAndCrmLoyaltyIdLike(Long crmMerchantNo,String crmLoyaltyId, Pageable pageable);
    public Page<CardMaster> findByCrmMerchantNoAndCrmCardHolderNameLike(Long crmMerchantNo,String crmCardHolderName, Pageable pageable);

    @Query("select C from CardMaster C where C.crmMerchantNo = ?1 and ( C.crmMobile = ?2 or C.crmEmailId = ?3 or C.crmLoyaltyId = ?4)")
    public List<CardMaster> listCardsForCustomer(Long crmMerchantNo, String crmMobile, String crmEmailId, String crmLoyaltyId);

    public CardMaster findByCrmId(Long crmId);
    public CardMaster findByCrmMerchantNoAndCrmCardNo(Long crmMerchantNo, String crmCardNo);

    public List<CardMaster> findByCrmMerchantNoAndCrmLoyaltyIdOrderByCrmIdDesc(Long merchantNo,String crmLoyaltyId);

    public CardMaster findByCrmMerchantNoAndCrmMobileAndCrmType(Long merchantNo,String crmMobileNo,Long crmType);
    public List<CardMaster> findByCrmMerchantNo(Long crmMerchantNo);

    public CardMaster findByCrmCardNo(String crmCardNo);

    public List<CardMaster> findByCrmMerchantNoAndCrmMobile(Long merchantNo,String crmMobile);



}
