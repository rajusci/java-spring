package com.inspirenetz.api.core.service;

import com.inspirenetz.api.core.dictionary.RedemptionVoucherUpdateRequest;
import com.inspirenetz.api.core.domain.RedemptionVoucher;
import com.inspirenetz.api.rest.exception.InspireNetzException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.sql.Date;
import java.util.List;

/**
 * Created by saneesh-ci on 9/9/14.
 */
public interface RedemptionVoucherService extends BaseService<RedemptionVoucher> {


    public RedemptionVoucher findByRvrId(Long rvrId) throws InspireNetzException;

    public Page<RedemptionVoucher> getPendingRedemptionVouchers(String loyaltyId,Integer channel, Pageable pageable) throws InspireNetzException;

    public RedemptionVoucher redeemRedemptionVoucher(String cusLoyaltyId, String rvrLoginId,String rvrVoucherCode) throws InspireNetzException;

    public RedemptionVoucher validateAndSaveRedemptionVoucher(RedemptionVoucher redemptionVoucher) throws InspireNetzException;
    public RedemptionVoucher saveRedemptionVoucher(RedemptionVoucher redemptionVoucher);
    public boolean validateAndDeleteRedemptionVoucher(Long rvrId) throws InspireNetzException;
    public boolean deleteRedemptionVoucher(Long rvrId);

    public Page<RedemptionVoucher> searchRedemptionVoucher(String filter,String query, Pageable pageable);

    public Page<RedemptionVoucher> searchRedemptionVoucherForMerchant(String rvrVoucherCode, String rvrLoyaltyId,Date startDate,Date endDate,Pageable pageable) throws InspireNetzException;

    public RedemptionVoucher redemptionVoucherIsValid(String rvrVoucherCode) throws InspireNetzException;

    public RedemptionVoucher voucherClaimForMerchantUser(String rvrVoucherCode,String rvrMerchantLocation) throws InspireNetzException;

    public boolean isVoucherExpired(RedemptionVoucher redemptionVoucher) throws InspireNetzException;

    public Page<RedemptionVoucher> searchRedemptionVoucherForCustomer(String filter,String query,Date startDate,Date endDate, Long merchantNo, Pageable pageable) throws InspireNetzException;

    public RedemptionVoucher updateRedemptionVoucher(RedemptionVoucherUpdateRequest redemptionVoucherUpdateRequest) throws InspireNetzException;

    public RedemptionVoucher findByRedemptionVoucher(String uniqueTrackingId);

    public List<RedemptionVoucher> findByRvrIdIn(String rvrList) throws InspireNetzException;

    public void sendVoucherVoucherNotificationList(RedemptionVoucherUpdateRequest redemptionVoucherUpdateRequest) throws InspireNetzException;

    public  List<RedemptionVoucher> findByRvrUniqueBatchIdAndRvrMerchantNo(String rvrUniqueBatchId,Long merchantNo);

}
