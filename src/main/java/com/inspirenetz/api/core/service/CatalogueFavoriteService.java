package com.inspirenetz.api.core.service;

import com.inspirenetz.api.core.domain.CatalogueFavorite;
import com.inspirenetz.api.rest.exception.InspireNetzException;

import java.util.List;

/**
 * Created by alameen on 21/10/14.
 */
public interface CatalogueFavoriteService {

    public List<CatalogueFavorite> findByCafLoyaltyId(String loyaltyId);

    public List<CatalogueFavorite> findByCafLoyaltyIdAndCafMerchantNo(String loyaltyId,Long cafMerchantNo);

    public List<CatalogueFavorite> findByCafLoyaltyIdAndCafMerchantNoAndCafFavoriteFlag(String loyaltyId,Long cafMerchantNo,Integer cafFavoriteFlag);

    public CatalogueFavorite saveCatalogueFavorite(CatalogueFavorite catalogueFavorite);

    public CatalogueFavorite findByCafLoyaltyIdAndCafProductNo(String cafLoyaltyId, Long cafProductNo);

    public boolean delete(CatalogueFavorite catalogueFavorite);

    public CatalogueFavorite saveCatalogueFavoriteForUser(String usrLoginId,Long cafProductNo,Integer cafFavoriteFlag) throws InspireNetzException;

}
