package com.inspirenetz.api.core.service;

import com.inspirenetz.api.core.domain.Vendor;
import com.inspirenetz.api.core.domain.Vendor;
import com.inspirenetz.api.rest.exception.InspireNetzException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Created by sandheepgr on 28/3/14.
 */
public interface VendorService extends BaseService<Vendor> {


    public Vendor findByVenId(Long venId);
    public Vendor findByVenMerchantNoAndVenName(Long venMerchantNo,String venName);
    public Page<Vendor> searchVendors(Long venMerchantNo, String filter, String query, Pageable pageable);
    public boolean isDuplicateVendorExisting(Vendor vendor);

    public Vendor saveVendor(Vendor vendor) throws InspireNetzException;
    public boolean deleteVendor(Long venId);




}
