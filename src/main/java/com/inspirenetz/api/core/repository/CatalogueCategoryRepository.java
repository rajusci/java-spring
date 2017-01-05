package com.inspirenetz.api.core.repository;

import com.inspirenetz.api.core.domain.CatalogueCategory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
 * Created by sandheepgr on 28/3/14.
 */
public interface CatalogueCategoryRepository extends  BaseRepository<CatalogueCategory,Long> {

    public CatalogueCategory findByCacId(Long cacId);
    public CatalogueCategory findByCacName(String cacName);

    public List<CatalogueCategory> findByCacParentGroup(Integer cacParentGroup);

    @Query("select C from CatalogueCategory C where C.cacFirstLevelInd = 1")
    public List<CatalogueCategory> findFirstLevelCategories();

    public Page<CatalogueCategory> findAll(Pageable pageable);
    public Page<CatalogueCategory> findByCacMerchantNoAndCacNameLike(Long merchantNo,String cacName, Pageable pageable);
    public Page<CatalogueCategory> findByCacMerchantNo(Long merchantNo, Pageable pageable);



}
