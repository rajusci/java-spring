package com.inspirenetz.api.core.service;

import com.inspirenetz.api.core.domain.ProductCategory;
import com.inspirenetz.api.rest.exception.InspireNetzException;
import com.inspirenetz.api.util.APIResponseObject;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

/**
 * Created by sandheepgr on 28/3/14.
 */
public interface ProductCategoryService extends BaseService<ProductCategory> {

    public Page<ProductCategory> findByPcyMerchantNo(Long pcyMerchantNo,Pageable pageable);
    public ProductCategory findByPcyId(Long pcyId);
    public ProductCategory findByPcyMerchantNoAndPcyCode(Long pcyMerchantNo, String pcyCode);
    public Page<ProductCategory> findByPcyMerchantNoAndPcyNameLike(Long pcyMerchantNo,String pcyName,Pageable pageable);
    public boolean isProductCategoryCodeDuplicateExisting(ProductCategory productCategory);


    public ProductCategory saveProductCategory(ProductCategory productCategory) throws InspireNetzException;
    public boolean deleteProductCategory(Long pcyId) throws InspireNetzException;

    public ProductCategory validateAndSaveProductCategory(ProductCategory productCategory) throws InspireNetzException;
    public boolean validateAndDeleteProductCategory(Long pcyId) throws InspireNetzException;

    Page<ProductCategory> searchProductCategories(String filter, String query, Long pcyMerchantNo, Pageable pageable);
    public List<ProductCategory> findByPcyMerchantNo(Long pcyMerchantNo);
}
