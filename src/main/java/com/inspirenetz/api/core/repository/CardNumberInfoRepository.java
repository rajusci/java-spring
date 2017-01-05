package com.inspirenetz.api.core.repository;

import com.inspirenetz.api.core.domain.CardNumberInfo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

/**
 * Created by ameen on 20/10/15.
 */
public interface CardNumberInfoRepository extends  BaseRepository<CardNumberInfo,Long>{

    public Page<CardNumberInfo> findByCniMerchantNo(Long cniMerchantNo,Pageable pageable);
    public CardNumberInfo findByCniId(Long cniId);
    public CardNumberInfo findByCniMerchantNoAndCniCardNumber(Long cniMerchantNo, String cniCardNumber);
    public Page<CardNumberInfo> findByCniMerchantNoAndCniCardNumberLike(Long cniMerchantNo, String cniCardNumber,Pageable pageable);
    public CardNumberInfo findByCniCardNumber(String cniCardNumber);

    public CardNumberInfo findByCniCardNumberAndCniCardStatus(String cniCardNumber,Integer cniCardStatus);

    public Page<CardNumberInfo> findByCniMerchantNoAndCniCardTypeAndCniCardStatus(Long cniMerchantNo,Long cniCardType,Integer cniCardStatus,Pageable pageable);

    public CardNumberInfo findByCniMerchantNoAndCniCardNumberAndCniCardStatus(Long cniMerchantNo,String cniCardNumber,Integer cniCardStatus);

    public Page<CardNumberInfo> findByCniMerchantNoAndCniBatchIdAndCniCardNumberLike(Long cniMerchantNo,Long cniBatchId, String cniCardNumber,Pageable pageable);
    public Page<CardNumberInfo> findByCniMerchantNoAndCniBatchId(Long cniMerchantNo,Long cniBatchId,Pageable pageable);


}
