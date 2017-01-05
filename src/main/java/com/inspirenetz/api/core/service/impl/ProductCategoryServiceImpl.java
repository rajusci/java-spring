package com.inspirenetz.api.core.service.impl;

import com.inspirenetz.api.core.auth.AuthSession;
import com.inspirenetz.api.core.dictionary.FunctionCode;
import com.inspirenetz.api.core.domain.CustomerProfile;
import com.inspirenetz.api.core.domain.CustomerProfilePK;
import com.inspirenetz.api.core.domain.ProductCategory;
import com.inspirenetz.api.core.repository.BaseRepository;
import com.inspirenetz.api.core.repository.ProductCategoryRepository;
import com.inspirenetz.api.core.service.ProductCategoryService;
import com.inspirenetz.api.rest.exception.InspireNetzException;
import com.inspirenetz.api.util.AuthSessionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by sandheepgr on 28/3/14.
 */
@Service
public class ProductCategoryServiceImpl extends BaseServiceImpl<ProductCategory> implements ProductCategoryService {

    private static Logger log = LoggerFactory.getLogger(CustomerServiceImpl.class);


    @Autowired
    ProductCategoryRepository productCategoryRepository;

    @Autowired
    AuthSessionUtils authSessionUtils;


    public ProductCategoryServiceImpl() {

        super(ProductCategory.class);

    }


    @Override
    protected BaseRepository<ProductCategory,Long> getDao() {
        return productCategoryRepository;
    }


    @Override
    public Page<ProductCategory> findByPcyMerchantNo(Long pcyMerchantNo,Pageable pageable) {

        // Get the data from the repository and store in the list
        Page<ProductCategory> productCategoryList = productCategoryRepository.findByPcyMerchantNo(pcyMerchantNo,pageable);

        // Return the list
        return productCategoryList;

    }


    @Override
    public ProductCategory findByPcyId(Long pcyId) {

        // Get the productCategory for the given productCategory id from the repository
        ProductCategory productCategory = productCategoryRepository.findByPcyId(pcyId);

        // Return the productCategory
        return productCategory;

    }

    @Override
    public ProductCategory findByPcyMerchantNoAndPcyCode(Long pcyMerchantNo,String pcyCode) {

        // Get the productCategory using the productCategory code and the merchant number
        ProductCategory productCategory = productCategoryRepository.findByPcyMerchantNoAndPcyCode(pcyMerchantNo,pcyCode);

        // Return the productCategory object
        return productCategory;

    }


    @Override
    public Page<ProductCategory> findByPcyMerchantNoAndPcyNameLike(Long pcyMerchantNo,String pcyName, org.springframework.data.domain.Pageable pageable) {

        // Get the data from the repository and store in the list
        Page<ProductCategory> productCategoryList = productCategoryRepository.findByPcyMerchantNoAndPcyNameLike(pcyMerchantNo, pcyName,pageable);

        // Return the list
        return productCategoryList;

    }


    @Override
    public boolean isProductCategoryCodeDuplicateExisting(ProductCategory productCategory) {

        // Get the productCategory information
        ProductCategory exProductCategory = productCategoryRepository.findByPcyMerchantNoAndPcyCode(productCategory.getPcyMerchantNo(),productCategory.getPcyCode());

        // If the pcyId is 0L, then its a new productCategory so we just need to check if there is ano
        // ther productCategory code
        if ( productCategory.getPcyId() == null || productCategory.getPcyId() == 0L ) {

            // If the productCategory is not null, then return true
            if ( exProductCategory != null ) {

                return true;

            }

        } else {

            // Check if the productCategory is null
            if ( exProductCategory != null && productCategory.getPcyId().longValue() != exProductCategory.getPcyId().longValue() ) {

                return true;

            }
        }


        // Return false;
        return false;

    }

    @Override
    public ProductCategory saveProductCategory(ProductCategory productCategory ) throws InspireNetzException {


        // Save the productCategory
        return productCategoryRepository.save(productCategory);

    }

    @Override
    public boolean deleteProductCategory(Long pcyId) throws InspireNetzException {


        // Delete the productCategory
        productCategoryRepository.delete(pcyId);

        // return true
        return true;

    }

    @Override
    public ProductCategory validateAndSaveProductCategory(ProductCategory productCategory) throws InspireNetzException {

        //check the access rights of the user
        authSessionUtils.validateFunctionAccess(FunctionCode.FNC_UPDATE_PRODUCT_CATEGORY);

        return saveProductCategory(productCategory);
    }

    @Override
    public boolean validateAndDeleteProductCategory(Long pcyId) throws InspireNetzException {

        authSessionUtils.validateFunctionAccess(FunctionCode.FNC_DELETE_PRODUCT_CATEGORY);

        return deleteProductCategory(pcyId);
    }

    @Override
    public Page<ProductCategory> searchProductCategories(String filter, String query, Long pcyMerchantNo, Pageable pageable) {

        Page<ProductCategory> productCategoryPage = null;

        // Check the filter type
        if ( filter.equals("0") && query.equals("0") ) {

            // Get the page
            List<ProductCategory> productCategoryList =findByPcyMerchantNo(pcyMerchantNo);

            productCategoryPage = new PageImpl<>(productCategoryList);



        } else if ( filter.equalsIgnoreCase("code") ) {

            productCategoryPage = productCategoryRepository.findByPcyMerchantNoAndPcyCodeLike(pcyMerchantNo,"%"+query+"%",pageable);



        } else if ( filter.equalsIgnoreCase("name")) {

            // Get the brandPage
            productCategoryPage = productCategoryRepository.findByPcyMerchantNoAndPcyNameLike(pcyMerchantNo, "%" + query + "%", pageable);

        }
        return productCategoryPage;

    }

    @Override
    public List<ProductCategory> findByPcyMerchantNo(Long pcyMerchantNo) {
        return productCategoryRepository.findByPcyMerchantNo(pcyMerchantNo);
    }

}
