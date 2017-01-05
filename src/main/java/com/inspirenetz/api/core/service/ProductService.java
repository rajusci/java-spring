package com.inspirenetz.api.core.service;

import com.inspirenetz.api.core.domain.Product;
import com.inspirenetz.api.rest.exception.InspireNetzException;
import com.inspirenetz.api.util.APIResponseObject;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

/**
 * Created by sandheepgr on 28/3/14.
 */
public interface ProductService extends BaseService<Product> {

    public Page<Product> findByPrdMerchantNoAndPrdLocation(Long prdMerchantNo,Long prdLocation,Pageable pageable);
    public Product findByPrdId(Long prdId);
    public Product findByPrdMerchantNoAndPrdCode(Long prdMerchantNo, String prdCode);
    public Page<Product> findByPrdMerchantNoAndPrdLocationAndPrdNameLike(Long prdMerchantNo,Long prdLocation,String prdName,Pageable pageable);
    public Page<Product> findByPrdMerchantNoAndPrdLocationAndPrdCodeLike(Long prdMerchantNo,Long prdLocation,String prdCode,Pageable pageable);
    public boolean isProductCodeDuplicateExisting(Product product);


    public Product saveProduct(Product product) throws InspireNetzException;
    public boolean deleteProduct(Long prdId) throws InspireNetzException;

    public Product validateAndSaveProduct(Product product) throws InspireNetzException;
    public boolean validateAndDeleteProduct(Long prdId) throws InspireNetzException;
    public List<Product> findByPrdMerchantNoAndPrdLocation(Long prdMerchantNo,Long prdLocation);

    public Page<Product> listProductInCustomer(Long merchantNo,String filter,String query,Pageable pageable) throws InspireNetzException;

    public List<Product> saveAllProducts(List<Product> products);


}
