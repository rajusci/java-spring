package com.inspirenetz.api.core.service;

import com.inspirenetz.api.core.domain.Catalogue;
import com.inspirenetz.api.core.loyaltyengine.CatalogueRedemption;
import com.inspirenetz.api.rest.exception.InspireNetzException;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.sql.Date;
import java.util.List;

/**
 * Created by sandheepgr on 17/2/14.
 */
public interface CatalogueService extends BaseService<Catalogue> {


    public Catalogue findByCatProductNo(Long catProductNo);
    public Catalogue findByCatProductNoAndCatDescriptionLike(Long catProductNo,String query);
    public Page<Catalogue> findByCatMerchantNo(Long catMerchantNo,Pageable pageable);
    public Catalogue findByCatProductCodeAndCatMerchantNo(String catProductCode,Long catMerchantNo);
    public Page<Catalogue> findByCatMerchantNoAndCatDescriptionLike(Long catMerchantNo, String query, Pageable pageable);
    public boolean isDuplicateCatalogueExisting(Catalogue catalogue);
    public Page<Catalogue> searchCatalogueByCurrencyAndCategory(Long catMerchantNo, Long catRewardCurrencyId, Integer catCategory, String loyaltyId,Integer channel, Pageable pageable) throws InspireNetzException;
    public CatalogueRedemption getCatalogueRedemption(Catalogue catalogue) throws InspireNetzException;

    public Catalogue saveCatalogue(Catalogue catalogue);
    public boolean deleteCatalogue(Long catProductNo);

    public Catalogue validateAndSaveCatalogue(Catalogue catalogue) throws InspireNetzException;
    public boolean validateAndDeleteCatalogue(Long catProductNo) throws InspireNetzException;

    List<Catalogue> getCatalogueListCompatible(String category, String loyaltyId, Long merchantNo,String query) throws InspireNetzException;

    List<Catalogue> getCatalogueFavourites(String loyaltyId, Long merchantNo);



    List<Catalogue> getCatalogueFavourites(String loyaltyId, Long merchantNo,String query);

    public Date getExpiryDateForVoucher(Catalogue catalogue,Date rvrCreatedDate) throws InspireNetzException;

    public Page<Catalogue> getPublicCatalogues(Long catMerchantNo,Integer catCategory,Integer catChannel, String query, Pageable pageable);

    public boolean isCatalogueValidForChannelAndCustomerType(Catalogue catalogue,Integer catChannel,Integer catCustomerType);

    public Page<Catalogue>listCataloguesUser(Long catMerchantNo,String usrLoginId,String filter,String query,String categories,String merchants,Integer channel,Pageable pageable )throws InspireNetzException;


    List<Catalogue> getCatalogueFavouritesForUser(String usrLoginId,Long merchantNo,String query)throws InspireNetzException;

    public Page<Catalogue> searchCatalogueByCurrencyAndCategoryCustomerPortal(Long merchantNo, Long rewardCurrencyId,String filter,String query,int channel, Pageable pageable) throws InspireNetzException;

    public Page<Catalogue> searchCatalogueByCatMerchantNoAndCatCategoryAndSortOption(String usrLoginId,Long catMerchantNo,Integer catCategory,String filter,String query,Integer sortOption,Integer channel,Pageable pageable)throws InspireNetzException;

    public Page<Catalogue> findByCatTypeAndCatMerchantNo(Integer catType, Long merchantNo,Pageable pageable);

    public boolean updateCatalogueStockOfPartnerProduct(Long stock, Long pacId,Long merchantNo) throws InspireNetzException;

    public Catalogue findByCatPartnerProductAndCatMerchantNo(Long pacId, Long merchantNo);
}
