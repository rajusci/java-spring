package com.inspirenetz.api.core.repository;

import com.inspirenetz.api.core.domain.CardType;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

/**
 * Created by sandheepgr on 28/3/14.
 */
public interface CardTypeRepository extends  BaseRepository<CardType,Long> {

    public Page<CardType> findByCrtMerchantNo(Long crtMerchantNo, Pageable pageable);
    public CardType findByCrtId(Long crtId);
    public Page<CardType> findByCrtMerchantNoAndCrtNameLike(Long crtMerchantNo, String crtName, Pageable pageable);
    public CardType findByCrtMerchantNoAndCrtName(Long crtMerchantNo, String crtName);
    public List<CardType> findByCrtMerchantNo(Long crtMerchantNo);

}
