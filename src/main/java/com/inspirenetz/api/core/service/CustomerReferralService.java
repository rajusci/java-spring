package com.inspirenetz.api.core.service;

import com.inspirenetz.api.core.domain.Customer;
import com.inspirenetz.api.core.domain.CustomerReferral;
import com.inspirenetz.api.core.domain.PointTransferRequest;
import com.inspirenetz.api.core.domain.User;
import com.inspirenetz.api.rest.exception.InspireNetzException;

import org.glassfish.jersey.internal.inject.Custom;

import com.inspirenetz.api.rest.resource.CustomerReferralResource;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.sql.Date;
import java.sql.Timestamp;
import java.util.List;


/**
 * Created by fayiz on 27/4/15.
 */
public interface CustomerReferralService extends BaseService<CustomerReferral> {

    public boolean isDuplicateReferralExisting(CustomerReferral customerReferral);

    public CustomerReferral saveCustomerReferralThroughMerchantPortal(CustomerReferralResource customerReferralResource) throws InspireNetzException;

    public CustomerReferral validateCustomerReferral(CustomerReferral customerReferral, User user)throws InspireNetzException;

    public CustomerReferral validateAndSaveCustomerReferral(CustomerReferral customerReferral)throws InspireNetzException;

    public CustomerReferral findByCsrId(Long csrId);

    public Page<CustomerReferral> findByCsrMerchantNoAndCsrLoyaltyIdOrderByCsrRefTimeStampDesc(Long csrMerchantNo,String csrLoyaltyId,  Pageable pageable);

    public List<CustomerReferral> findByCsrMerchantNoAndCsrRefMobileAndCsrRefStatusOrderByCsrRefTimeStampAsc(Long csrMerchantNo, String csrRefMobile, Integer csrStatus);

    public List<CustomerReferral> findByCsrMerchantNoAndCsrRefMobileAndCsrRefStatusNotOrderByCsrRefTimeStampAsc(Long csrMerchantNo, String csrRefMobile, Integer csrStatus);

    public List<CustomerReferral>  findByCsrMerchantNoAndCsrRefMobileOrderByCsrRefTimeStampAsc(Long csrMerchantNo,String csrRefMobile);

    public boolean deleteCustomerReferral(Long csrId) throws InspireNetzException;

    public CustomerReferral saveCustomerReferral(CustomerReferral customerReferral);

    public CustomerReferral findByCsrMerchantNoAndCsrLoyaltyIdAndCsrRefMobile(Long csrMerchantNo, String csrLoyaltyId,String csrRefMobile);

    public Page<CustomerReferral> searchReferral(String filter, String query,Pageable pageable);

    public Page<CustomerReferral> searchReferralForCustomerPortal(String filter, String query,Long merchantNo,Pageable pageable) throws InspireNetzException;

    public List<CustomerReferral> findByCsrMerchantNoAndCsrLoyaltyIdOrderByCsrRefTimeStampDesc(Long csrMerchantNo, String csrLoyaltyId);

    public List<CustomerReferral> findByCsrMerchantNoAndCsrLoyaltyIdAndCsrRefNameLike(Long csrMerchantNo, String csrLoyaltyId,String csrRefName);
    public List<CustomerReferral> findByCsrMerchantNoAndCsrLoyaltyIdAndCsrRefMobileLike(Long csrMerchantNo, String csrLoyaltyId,String csrRefMobile);
    public CustomerReferral validateCustomerReferralThroughCustomer(CustomerReferral customerReferral)throws InspireNetzException;


    int getReferralSettingForMerchant(Long cusMerchantNo);

    public CustomerReferral  findByCsrMerchantNoAndCsrRefMobile(Long csrMerchantNo,String csrRefMobile);

    public void saveReferralDataFromXmlFile(List<CustomerReferral> customerReferralList,Long userNo);

    public List<CustomerReferral> findByCsrMerchantNoAndCsrLoyaltyIdAndCsrRefTimeStampBetween(Long csrMerchantNo,String csrLoyaltyId,Date fromDate,Date toDate);



    public CustomerReferral saveCustomerReferralWithPriority(CustomerReferral customerReferral)throws InspireNetzException;

    public boolean checkReferralValidity(CustomerReferral customerReferral,User user) throws InspireNetzException;

    public List<CustomerReferral> findByCsrMerchantNoAndCsrLoyaltyIdAndCsrEarnedStatus(Long csrMerchantNo, String csrLoyaltyId, boolean csrEarnedStatus);

    public void processAwardingOnSuccessfulReferral(CustomerReferral customerReferral) throws InspireNetzException;



}
