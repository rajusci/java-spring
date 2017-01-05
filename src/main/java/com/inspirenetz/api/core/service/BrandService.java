package com.inspirenetz.api.core.service;

import com.inspirenetz.api.core.domain.Brand;
import com.inspirenetz.api.rest.exception.InspireNetzException;
import com.inspirenetz.api.util.APIResponseObject;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.annotation.Secured;

import java.util.List;

/**
 * Created by sandheepgr on 28/3/14.
 */
public interface BrandService extends BaseService<Brand> {

    public Page<Brand> findByBrnMerchantNo(Long brnMerchantNo,Pageable pageable);
    public Brand findByBrnId(Long brnId);
    public Brand findByBrnMerchantNoAndBrnCode(Long brnMerchantNo, String brnCode);
    public Page<Brand> findByBrnMerchantNoAndBrnNameLike(Long brnMerchantNo, String brnName,Pageable pageable);
    public boolean isBrandCodeDuplicateExisting(Brand brand);

    public Page<Brand> searchBrands(String filter , String query ,Long brnMerchantNo,Pageable pageable);

    public Brand validateAndSaveBrand(Brand brand) throws InspireNetzException;
    public Brand saveBrand(Brand brand) throws InspireNetzException;

    public boolean validateAndDeleteBrand(Long brnId) throws InspireNetzException;
    public boolean deleteBrand(Long brnId) throws InspireNetzException;

    public List<Brand> findByBrnMerchantNo(Long brnMerchantNo);



}
