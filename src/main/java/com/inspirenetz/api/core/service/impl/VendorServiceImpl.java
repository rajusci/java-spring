package com.inspirenetz.api.core.service.impl;

import com.inspirenetz.api.core.dictionary.APIErrorCode;
import com.inspirenetz.api.core.domain.Vendor;
import com.inspirenetz.api.core.repository.BaseRepository;
import com.inspirenetz.api.core.repository.VendorRepository;
import com.inspirenetz.api.core.service.VendorService;
import com.inspirenetz.api.rest.exception.InspireNetzException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

/**
 * Created by sandheepgr on 28/3/14.
 */
@Service
public class VendorServiceImpl extends BaseServiceImpl<Vendor> implements VendorService {


    private static Logger log = LoggerFactory.getLogger(VendorServiceImpl.class);


    @Autowired
    VendorRepository vendorRepository;


    public VendorServiceImpl() {

        super(Vendor.class);

    }


    @Override
    protected BaseRepository<Vendor,Long> getDao() {
        return vendorRepository;
    }



    @Override
    public Vendor findByVenId(Long venId) {

        // Get the vendor for the given vendor id from the repository
        Vendor vendor = vendorRepository.findByVenId(venId);

        // Return the vendor
        return vendor;


    }

    @Override
    public Vendor findByVenMerchantNoAndVenName(Long venMerchantNo, String venName) {

        // Get the vendor information for the merchant no and vendor name
        Vendor vendor = vendorRepository.findByVenMerchantNoAndVenName(venMerchantNo,venName);

        // return the vendor
        return vendor;

    }

    @Override
    public Page<Vendor> searchVendors(Long venMerchantNo, String filter, String query, Pageable pageable) {

        // The return Page
        Page<Vendor> vendorPage;

        // Check the filter
        if ( filter.equals("name") ) {

            // Get the page
            vendorPage = vendorRepository.findByVenMerchantNoAndVenNameLike(venMerchantNo,"%"+query+"%",pageable);


        } else {

            // Get all the vendors for the merchant
            vendorPage = vendorRepository.findByVenMerchantNo(venMerchantNo,pageable);
        }


        // Return the vendorPage
        return vendorPage;
    }

    @Override
    public boolean isDuplicateVendorExisting(Vendor vendor) {

        // Get the vendor information
        Vendor exVendor = vendorRepository.findByVenMerchantNoAndVenName(vendor.getVenMerchantNo(), vendor.getVenName());

        // If the venId is 0L, then its a new vendor so we just need to check if there is ano
        // ther vendor code
        if ( vendor.getVenId() == null || vendor.getVenId() == 0L ) {

            // If the vendor is not null, then return true
            if ( exVendor != null ) {

                return true;

            }

        } else {

            // Check if the vendor is null
            if ( exVendor != null && vendor.getVenId().longValue() != exVendor.getVenId().longValue() ) {

                return true;

            }
        }


        // Return false;
        return false;

    }

    @Override
    public Vendor saveVendor(Vendor vendor ) throws InspireNetzException {


        // Check if the vendor is existing
        boolean isExist = isDuplicateVendorExisting(vendor);

        // Check the boolean value
        if ( isExist ) {

            // Log the response
            log.info("saveVendor - Response : Vendor code is already existing");

            // Throw InspireNetzException with ERR_DUPLICATE_ENTRY as error
            throw new InspireNetzException(APIErrorCode.ERR_DUPLICATE_ENTRY);


        }


        // Save the vendor
        return vendorRepository.save(vendor);

    }

    @Override
    public boolean deleteVendor(Long venId) {

        // Delete the vendor
        vendorRepository.delete(venId);

        // return true
        return true;

    }

}
