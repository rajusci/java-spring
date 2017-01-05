package com.inspirenetz.api.core.repository;

import com.inspirenetz.api.core.domain.ProductCategory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

/**
 * Created by sandheepgr on 28/3/14.
 */
public interface ProductCategoryRepository extends  BaseRepository<ProductCategory,Long> {

    public Page<ProductCategory> findByPcyMerchantNo(Long pcyMerchantNo,Pageable pageable);
    public ProductCategory findByPcyId(Long pcyId);
    public ProductCategory findByPcyMerchantNoAndPcyCode(Long pcyMerchantNo, String pcyCode);
    public Page<ProductCategory> findByPcyMerchantNoAndPcyNameLike(Long pcyMerchantNo,String pcyName,Pageable pageable);
    public Page<ProductCategory> findByPcyMerchantNoAndPcyCodeLike(Long pcyMerchantNo,String pcyCode,Pageable pageable);

    public List<ProductCategory> findByPcyMerchantNo(Long pcyMerchantNo);
}
