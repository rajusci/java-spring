package com.inspirenetz.api.core.service;

import com.inspirenetz.api.core.domain.Brand;
import com.inspirenetz.api.core.domain.Customer;
import com.inspirenetz.api.core.domain.RedemptionVoucherSource;
import com.inspirenetz.api.core.domain.RedemptionVoucherSource;
import com.inspirenetz.api.rest.exception.InspireNetzException;
import org.springframework.data.domain.Page;

import org.springframework.data.domain.Pageable;
import java.util.List;


/**
 * Created by saneesh-ci on 10/02/15.
 */
public interface RedemptionVoucherSourceService extends BaseService<RedemptionVoucherSource> {


    public RedemptionVoucherSource findByRvsId(Long rvsId) throws InspireNetzException;
    public Page<RedemptionVoucherSource> findByRvsMerchantNoAndRvsNameLike(Long rvsMerchantNo,String rvsName, org.springframework.data.domain.Pageable pageable) throws InspireNetzException;
    public Page<RedemptionVoucherSource> findByRvsMerchantNo(Long rvsMerchantNo,Pageable pageable) throws InspireNetzException;


    public String getRedemptionVoucherCode(RedemptionVoucherSource redemptionVoucherSource) throws InspireNetzException;
    public String getVoucherCode(Long rvsId) throws InspireNetzException;

    public Page<RedemptionVoucherSource> searchVoucherSources(String filter, String query, Long merchantNo, Pageable pageable) throws InspireNetzException;

    public RedemptionVoucherSource validateAndSaveRedemptionVoucherSource(RedemptionVoucherSource redemptionVoucherSource) throws InspireNetzException;
    public RedemptionVoucherSource saveRedemptionVoucherSource(RedemptionVoucherSource redemptionVoucherSource);
    public boolean deleteRedemptionVoucherSource(Long rvrId);


    boolean validateAndDeleteRedemptionVoucherSource(Long rvsId) throws InspireNetzException;

    public Page<RedemptionVoucherSource> findByRvsMerchantNoAndRvsStatus(Long rvsMerchantNo, Integer rvsStatus,Pageable pageable);

    public Page<RedemptionVoucherSource> getActiveVoucherSources(Long rvsMerchantNo,Pageable pageable);


    /*
        * This method will be called once the reading from file is completed or failed
        * */
    void updateVoucherSourceStatus(RedemptionVoucherSource redemptionVoucherSource, Integer status);
}
