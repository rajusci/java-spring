package com.inspirenetz.api.core.service.impl;

import com.inspirenetz.api.core.dictionary.APIErrorCode;
import com.inspirenetz.api.core.dictionary.FunctionCode;
import com.inspirenetz.api.core.domain.CatalogueCategory;
import com.inspirenetz.api.core.repository.BaseRepository;
import com.inspirenetz.api.core.repository.CatalogueCategoryRepository;
import com.inspirenetz.api.core.service.CatalogueCategoryService;
import com.inspirenetz.api.rest.exception.InspireNetzException;
import com.inspirenetz.api.util.AuthSessionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by sandheepgr on 28/3/14.
 */
@Service
public class CatalogueCategoryServiceImpl extends BaseServiceImpl<CatalogueCategory> implements CatalogueCategoryService {


    private static Logger log = LoggerFactory.getLogger(CatalogueCategoryServiceImpl.class);


    @Autowired
    CatalogueCategoryRepository catalogueCategoryRepository;

    @Autowired
    AuthSessionUtils authSessionUtils;


    public CatalogueCategoryServiceImpl() {

        super(CatalogueCategory.class);

    }


    @Override
    protected BaseRepository<CatalogueCategory,Long> getDao() {
        return catalogueCategoryRepository;
    }



    @Override
    public CatalogueCategory findByCacId(Long cacId) {

        // Get the catalogueCategory for the gicac catalogueCategory id from the repository
        CatalogueCategory catalogueCategory = catalogueCategoryRepository.findByCacId(cacId);

        // Return the catalogueCategory
        return catalogueCategory;


    }

    @Override
    public CatalogueCategory findByCacName(String cacName) {

        // Get the CatalogueCategory by name
        CatalogueCategory catalogueCategory = catalogueCategoryRepository.findByCacName(cacName);

        // Return the CatalogueCategory
        return catalogueCategory;

    }

    @Override
    public List<CatalogueCategory> findFirstLevelCategories() {

        // Get the list of catalgoue categories
        List<CatalogueCategory> catalogueCategoryList =  catalogueCategoryRepository.findFirstLevelCategories();

        // Return catalogueCategoryList
        return catalogueCategoryList;

    }

    @Override
    public List<CatalogueCategory> findByCacParentGroup(Integer cacParentGroup) {

        // Get the List for the given parent group
        List<CatalogueCategory> catalogueCategoryList = catalogueCategoryRepository.findByCacParentGroup(cacParentGroup);

        // Return the list
        return catalogueCategoryList;

    }

    @Override
    public Page<CatalogueCategory> searchCatalogueCategories(String filter, String query, Pageable pageable) {

        // The return Page
        Page<CatalogueCategory> catalogueCategoryPage;

        // Check the filter
        if ( filter.equals("name") ) {

            // Get the page
            catalogueCategoryPage = catalogueCategoryRepository.findByCacMerchantNoAndCacNameLike(authSessionUtils.getMerchantNo(), "%" + query + "%", pageable);


        } else {

            // Get all the catalogueCategorys for the merchant
            catalogueCategoryPage = catalogueCategoryRepository.findByCacMerchantNo(authSessionUtils.getMerchantNo(), pageable);

        }


        // Return the catalogueCategoryPage
        return catalogueCategoryPage;
    }

    @Override
    public boolean isDuplicateCatalogueCategoryExisting(CatalogueCategory catalogueCategory) {

        // Get the catalogueCategory information
        CatalogueCategory exCatalogueCategory = catalogueCategoryRepository.findByCacName(catalogueCategory.getCacName());

        // If the cacId is 0L, then its a new catalogueCategory so we just need to check if there is ano
        // ther catalogueCategory code
        if ( catalogueCategory.getCacId() == null || catalogueCategory.getCacId() == 0L ) {

            // If the catalogueCategory is not null, then return true
            if ( exCatalogueCategory != null ) {

                return true;

            }

        } else {

            // Check if the catalogueCategory is null
            if ( exCatalogueCategory != null && catalogueCategory.getCacId().longValue() != exCatalogueCategory.getCacId().longValue() ) {

                return true;

            }
        }


        // Return false;
        return false;

    }

    @Override
    public CatalogueCategory saveCatalogueCategory(CatalogueCategory catalogueCategory ) throws InspireNetzException {


        // Check if the catalogueCategory is existing
        boolean isExist = isDuplicateCatalogueCategoryExisting(catalogueCategory);

        // Check the boolean value
        if ( isExist ) {

            // Log the response
            log.info("saveCatalogueCategory - Response : CatalogueCategory code is already existing");

            // Throw InspireNetzException with ERR_DUPLICATE_ENTRY as error
            throw new InspireNetzException(APIErrorCode.ERR_DUPLICATE_ENTRY);


        }


        // Save the catalogueCategory
        return catalogueCategoryRepository.save(catalogueCategory);

    }

    @Override
    public boolean deleteCatalogueCategory(Long cacId) throws InspireNetzException {

        // Delete the catalogueCategory
        catalogueCategoryRepository.delete(cacId);

        // return true
        return true;

    }

    @Override
    public CatalogueCategory validateAndSaveCatalogueCategory(CatalogueCategory catalogueCategory) throws InspireNetzException {


        authSessionUtils.validateFunctionAccess(FunctionCode.FNC_UPDATE_CATALOGUE_CATEGORY);

        //set merchant number
        catalogueCategory.setCacMerchantNo(authSessionUtils.getMerchantNo());

        return saveCatalogueCategory(catalogueCategory);
    }

    @Override
    public boolean validateAndDeleteCatalogueCategory(Long venId) throws InspireNetzException {

        authSessionUtils.validateFunctionAccess(FunctionCode.FNC_DELETE_CATALOGUE_CATEGORY);

        return deleteCatalogueCategory(venId);

    }

}
