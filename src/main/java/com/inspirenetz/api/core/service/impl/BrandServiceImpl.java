package com.inspirenetz.api.core.service.impl;

import com.inspirenetz.api.core.dictionary.FunctionCode;
import com.inspirenetz.api.core.domain.Brand;
import com.inspirenetz.api.core.domain.CustomerProfile;
import com.inspirenetz.api.core.domain.CustomerProfilePK;
import com.inspirenetz.api.core.repository.BaseRepository;
import com.inspirenetz.api.core.repository.BrandRepository;
import com.inspirenetz.api.core.service.BrandService;
import com.inspirenetz.api.rest.exception.InspireNetzException;
import com.inspirenetz.api.rest.resource.BrandResource;
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
public class BrandServiceImpl extends BaseServiceImpl<Brand> implements BrandService {


    private static Logger log = LoggerFactory.getLogger(BrandServiceImpl.class);


    @Autowired
    BrandRepository brandRepository;

    @Autowired
    AuthSessionUtils authSessionUtils;


    public BrandServiceImpl() {

        super(Brand.class);

    }


    @Override
    protected BaseRepository<Brand,Long> getDao() {
        return brandRepository;
    }

    @Override
    public Page<Brand> findByBrnMerchantNo(Long brnMerchantNo,Pageable pageable) {

        // Get the data from the repository and store in the list
        Page<Brand> brandList = brandRepository.findByBrnMerchantNo(brnMerchantNo,pageable);

        // Return the list
        return brandList;

    }

    @Override
    public Brand findByBrnId(Long brnId) {

        // Get the brand for the given brand id from the repository
        Brand brand = brandRepository.findByBrnId(brnId);

        // Return the brand
        return brand;


    }

    @Override
    public Brand findByBrnMerchantNoAndBrnCode(Long brnMerchantNo,String brnCode) {

        // Get the brand using the brand code and the merchant number
        Brand brand = brandRepository.findByBrnMerchantNoAndBrnCode(brnMerchantNo,brnCode);

        // Return the brand object
        return brand;

    }

    @Override
    public Page<Brand> findByBrnMerchantNoAndBrnNameLike(Long brnMerchantNo, String brnName,Pageable pageable) {

        // Get the data from the repository and store in the list
        Page<Brand> brandList = brandRepository.findByBrnMerchantNoAndBrnNameLike(brnMerchantNo, brnName,pageable);

        // Return the list
        return brandList;

    }

    @Override
    public boolean isBrandCodeDuplicateExisting(Brand brand) {

        // Get the brand information
        Brand exBrand = brandRepository.findByBrnMerchantNoAndBrnCode(brand.getBrnMerchantNo(),brand.getBrnCode());

        // If the brnId is 0L, then its a new brand so we just need to check if there is ano
        // ther brand code
        if ( brand.getBrnId() == null || brand.getBrnId() == 0L ) {

            // If the brand is not null, then return true
            if ( exBrand != null ) {

                return true;

            }

        } else {

            // Check if the brand is null
            if ( exBrand != null && brand.getBrnId().longValue() != exBrand.getBrnId().longValue() ) {

                return true;

            }
        }


        // Return false;
        return false;

    }

    @Override
    public Page<Brand> searchBrands(String filter, String query, Long brnMerchantNo, Pageable pageable) {

        Page<Brand> brandPage = null;

        // Check the filter type
        if ( filter.equals("0") && query.equals("0") ) {

             // Get the page
             List<Brand> brandList =findByBrnMerchantNo(brnMerchantNo);

             brandPage = new PageImpl<>(brandList);


        } else if ( filter.equalsIgnoreCase("code") ) {

             brandPage = brandRepository.findByBrnMerchantNoAndBrnCodeLike(brnMerchantNo,"%"+query+"%",pageable);



        } else if ( filter.equalsIgnoreCase("name")) {

            // Get the brandPage
            brandPage = brandRepository.findByBrnMerchantNoAndBrnNameLike(brnMerchantNo, "%" + query + "%", pageable);

        }
        return brandPage;

    }

    @Override
    public Brand validateAndSaveBrand(Brand brand) throws InspireNetzException {

        //check the user's access rights
        authSessionUtils.validateFunctionAccess(FunctionCode.FNC_UPDATE_BRAND);

        brand = saveBrand(brand);

        return brand;
    }

    @Override
    public Brand saveBrand(Brand brand ) throws InspireNetzException {

        // Save the brand
        return brandRepository.save(brand);

    }

    @Override
    public boolean validateAndDeleteBrand(Long brnId) throws InspireNetzException {

        //check the user's access rights
        authSessionUtils.validateFunctionAccess(FunctionCode.FNC_DELETE_BRAND);

        return deleteBrand(brnId);

    }

    @Override
    public boolean deleteBrand(Long brnId) throws InspireNetzException {

        // Delete the brand
        brandRepository.delete(brnId);

        // return true
        return true;

    }

    @Override
    public List<Brand> findByBrnMerchantNo(Long brnMerchantNo) {
        return brandRepository.findByBrnMerchantNo(brnMerchantNo);
    }

}
