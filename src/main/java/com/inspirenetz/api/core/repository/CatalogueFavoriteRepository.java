package com.inspirenetz.api.core.repository;

import com.inspirenetz.api.core.domain.CatalogueCategory;
import com.inspirenetz.api.core.domain.CatalogueFavorite;

import java.util.List;

/**
 * Created by alameen on 21/10/14.
 */
public interface CatalogueFavoriteRepository extends  BaseRepository<CatalogueFavorite,Long>{


    public List<CatalogueFavorite> findByCafLoyaltyId(String cafLoyaltyId);

    public List<CatalogueFavorite> findByCafLoyaltyIdAndCafMerchantNo(String cafLoyaltyId,Long cafMerchantNo);

    public List<CatalogueFavorite> findByCafLoyaltyIdAndCafMerchantNoAndCafFavoriteFlag(String cafLoyaltyId,Long cafMerchantNo,Integer cafFavoriteFlag);

    public CatalogueFavorite findByCafLoyaltyIdAndCafProductNo(String cafLoyaltyId,Long cafProductNo);

    public CatalogueFavorite findByCafLoyaltyIdAndCafProductNoAndCafMerchantNo(String cafLoyaltyId,Long cafProductNo,Long cafMerchantNo);

}
