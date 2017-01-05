package com.inspirenetz.api.core.service;

import com.inspirenetz.api.core.domain.CatalogueCategory;
import com.inspirenetz.api.core.domain.CatalogueCategory;
import com.inspirenetz.api.rest.exception.InspireNetzException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
 * Created by sandheepgr on 28/3/14.
 */
public interface CatalogueCategoryService extends BaseService<CatalogueCategory> {



    public CatalogueCategory findByCacId(Long cacId);
    public CatalogueCategory findByCacName(String cacName);
    public List<CatalogueCategory> findFirstLevelCategories();
    public List<CatalogueCategory> findByCacParentGroup(Integer cacParentGroup);
    public Page<CatalogueCategory> searchCatalogueCategories(String filter, String query, Pageable pageable);

    public boolean isDuplicateCatalogueCategoryExisting(CatalogueCategory catalogueCategory);

    public CatalogueCategory saveCatalogueCategory(CatalogueCategory catalogueCategory) throws InspireNetzException;
    public boolean deleteCatalogueCategory(Long venId) throws InspireNetzException;

    public CatalogueCategory validateAndSaveCatalogueCategory(CatalogueCategory catalogueCategory) throws InspireNetzException;
    public boolean validateAndDeleteCatalogueCategory(Long venId) throws InspireNetzException;



}
