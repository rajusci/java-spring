package com.inspirenetz.api.core.repository;

import com.inspirenetz.api.core.domain.Catalogue;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
 * Created by sandheepgr on 17/2/14.
 */
public interface CatalogueRepository extends BaseRepository<Catalogue,Long> {

    public Catalogue findByCatProductNo(Long catProductNo);
    public Catalogue findByCatProductNoAndCatDescriptionLike(Long catProductNo,String query);
    public Page<Catalogue> findByCatMerchantNo(Long catMerchantNo,Pageable pageable);
    public Catalogue findByCatProductCodeAndCatMerchantNo(String catProductCode, Long catMerchantNo);
    public Page<Catalogue> findByCatMerchantNoAndCatDescriptionLike(Long catMerchantNo, String query, Pageable pageable);
    public Page<Catalogue> findByCatTypeAndCatMerchantNo(Integer catType, Long merchantNo,Pageable pageable);

    @Query("select C from Catalogue C where C.catMerchantNo = ?1 and ( ?2 = 0L or C.catRewardCurrencyId = ?2 ) and ( ?3 = 0 or C.catCategory = ?3 )")
    public Page<Catalogue> searchCatalogueByCurrencyAndCategory(Long catMerchantNo, Long catRewardCurrencyId,Integer catCategory, Pageable pageable);

    @Query("select C from Catalogue C where C.catMerchantNo = ?1 and  ( ?2 = 0 or C.catCategory = ?2 ) and (?3='0' or C.catDescription like ?3)")
    public List<Catalogue> searchCatalogueByCategoryAndCatDescription(Long catMerchantNo,Integer catCategory,String catDescription);

    public Page<Catalogue> findByCatMerchantNoAndCatCategoryAndCatDescriptionLike(Long catMerchantNo,Integer catCategory, String query, Pageable pageable);

    public Page<Catalogue> findByCatDescriptionLike(String query, Pageable pageable);

    public Page<Catalogue> findByCatCategoryAndCatDescriptionLike(Integer catCategory,String query, Pageable pageable);


    @Query("select distinct C from Catalogue C where (?1='0' or C.catDescription like ?1) and  C.catCategory in(?2) and  C.catMerchantNo in (?3)")
    public Page<Catalogue> listCataloguesByCatDescriptionAndCatCategoriesAndCatMerchants(String catDescription,List<Integer> catCategories,List<Long> catMerchants, Pageable pageable);

    @Query("select C from Catalogue C where (?1='0' or C.catDescription like ?1) and  C.catCategory in(?2)")
    public List<Catalogue> listCataloguesByCatDescriptionAndCatCategories(String catDescription,List<Integer> catCategories);

    @Query("select C from Catalogue C where (?1='0' or C.catDescription like ?1) and  C.catMerchantNo in(?2)")
    public List<Catalogue> listCataloguesByCatDescriptionAndCatMerchants(String catDescription,List<Long> catMerchants);


    @Query("select C from Catalogue C where ( C.catMerchantNo = ?1) and  ( ?2 = 0 or C.catCategory = ?2 ) and (?3='0' or C.catDescription like ?3)")
    public List<Catalogue> listCatalogueByCatMerchantNoAndCatCategoryAndCatDescriptionLike(Long catMerchantNo,Integer catCategory, String query);

    @Query("select C from Catalogue C where ( ?1 = 0 or C.catCategory = ?1 ) and (?2='0' or C.catDescription like ?2)")
    public List<Catalogue> listCatalogueByCatCategoryAndCatDescriptionLike(Integer catCategory, String query);

    public Catalogue findByCatPartnerProductAndCatMerchantNo(Long pacId, Long merchantNo);
}
