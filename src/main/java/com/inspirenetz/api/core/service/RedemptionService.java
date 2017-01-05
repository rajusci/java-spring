package com.inspirenetz.api.core.service;

import com.inspirenetz.api.core.dictionary.*;
import com.inspirenetz.api.core.domain.Catalogue;
import com.inspirenetz.api.core.domain.Customer;
import com.inspirenetz.api.core.domain.CustomerRewardExpiry;
import com.inspirenetz.api.core.domain.Redemption;
import com.inspirenetz.api.core.loyaltyengine.CatalogueRedemption;
import com.inspirenetz.api.rest.exception.InspireNetzException;
import com.sun.org.apache.xpath.internal.operations.Bool;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.sql.Date;
import java.util.List;
import java.util.Map;

/**
 * Created by sandheepgr on 16/2/14.
 */
public interface RedemptionService extends BaseService<Redemption> {

    public Redemption findByRdmId(Long rdmId);
    public List<Redemption> findByRdmMerchantNoAndRdmUniqueBatchTrackingId(Long rdmMerchantNo,String rdmUniqueBatchTrackingId);
    public boolean deductPoints(Redemption redemption,List<CustomerRewardExpiry> customerRewardExpiryList, double totalRewardQty,Long location) ;
    public boolean addRedemptionTransactionEntry(Redemption redemption,Long location,double rewardQtyPreBal,double rewardQty);
    public CatalogueRedemptionItemResponse redeemCatalogue(CatalogueRedemptionRequest catalogueRedemptionRequest, String prdCode, Integer qty);
    public List<RedemptionCatalogue> getRedemptionCatalogues(Map<String,String> params);
    public CatalogueRedemptionResponse doCatalogueRedemption(CatalogueRedemptionRequest catalogueRedemptionRequest) throws InspireNetzException;
    public CashBackRedemptionResponse doCashbackRedemption(CashBackRedemptionRequest cashbackRedemptionRequest) throws InspireNetzException;
    public Page<Redemption> findByRdmMerchantNoAndRdmLoyaltyIdAndRdmDateBetween(Long rdmMerchantNo,String rdmLoyaltyId,Date rdmStartDate,Date rdmEndDate, Pageable pageable);
    public Page<Redemption> findByRdmMerchantNoAndRdmTypeAndRdmDateBetween(Long rdmMerchantNo, int rdmType,Date rdmStartDate, Date rdmEndDate,Pageable pageable);
    public Page<Redemption> findByRdmMerchantNoAndRdmLoyaltyIdAndRdmTypeAndRdmDateBetween(Long rdmMerchantNo,String rdmLoyaltyId,Integer rdmType,Date rdmStartDate,Date rdmEndDate, Pageable pageable);
    public CatalogueRedemptionItemResponse redeemCatalogueItems(CatalogueRedemptionItemRequest catalogueRedemptionItemRequest) throws  InspireNetzException;

    public CatalogueRedemptionItemResponse redeemCatalogueItemsForUser(String userLoginId, String prdCode, Integer quantity, Long merchantNo, Integer rdmChannel, String destLoyaltyId) throws InspireNetzException;

    public Page<Redemption> listRedemptionRequests(Long rdmMerchantNo, String filterType, String query, Integer status, Pageable pageable);

    public CatalogueRedemptionItemResponse processCatalogueRedemption(CatalogueRedemption catalogueRedemption, CatalogueRedemptionItemRequest catalogueRedemptionItemRequest) throws InspireNetzException;

    void checkPasaRewardRequestValidity(String destLoyaltyId, String prdCode,Long merchantNo) throws InspireNetzException;

    public Page<Redemption> searchRedemptionsForPay( Long rdmMerchantNo,Long userNo);

    public boolean checkGeneralRulesValidity(CatalogueRedemptionItemRequest catalogueRedemptionItemRequest) throws InspireNetzException;
    public CatalogueRedemptionItemResponse redeemSingleCatalogueItem(CatalogueRedemptionItemRequest catalogueRedemptionItemRequest) throws InspireNetzException;

    void updateRedemptionStatus(Long rdmId, int rdmStatus);

    public CatalogueRedemptionItemRequest getRedemptionRequestObject(Customer customer,Catalogue catalogue) throws InspireNetzException;


    public Redemption saveRedemption(Redemption redemption);
    public boolean deleteRedemption(Long rdmId) throws InspireNetzException;

    public CatalogueRedemptionResponse validateAndDoCatalogueRedemption(CatalogueRedemptionRequest catalogueRedemptionRequest) throws InspireNetzException;
    public boolean validateAndDeleteRedemption(Long rdmId) throws InspireNetzException;

    List<Map<String, String>> redeemCatalogueItemsCompatible(String loyaltyId, Long merchantNo,String destLoyaltyId, Map<String, String> params);

    String getRedemptionErrorMessage(APIErrorCode apiErrorCode);

    List<Redemption> findRdmLoyaltyIdAndRdmMerchantNo(String rdmLoyaltyId, Long rdmMerchantNo);

    public CashBackRedemptionResponse doCashBackRedemption(CashBackRedemptionRequest cashbackRedemptionRequest,String otpCode) throws InspireNetzException;


    public CashBackRedemptionResponse doCashBackRedemptionForMerchant(String loyaltyId,double purchaseAmount,Long rwdCurrencyId,String location,String txnRef) throws InspireNetzException;

    public  RewardAdjustment createRewardAdjustmentObject(Long merchantNo, String loyaltyId, Long adjCurrencyId, Double adjPoints, boolean isTierAffected, Long adjProgramNo, String adjIntReference,String adExtReference);
    public CatalogueRedemptionItemResponse redeemCatalogueItems(String prdCode, Integer quantity, Long merchantNo, Integer rdmChannel, String destLoyaltyId) throws InspireNetzException;

    public Page<Redemption> listRedemptionRequestsInPartnerPortal(Long rdmPartnerNo, String filterType, String query, Integer status, Pageable pageable);

    public List<Redemption> findByRdmPartnerNoAndRdmUniqueBatchTrackingId(Long rdmPartnerNo, String trackingId);

    public Page<Redemption> listPayRedemptionsInPartnerPortal(Long rdmMerchantNo,Date startDate, Date endDate, Pageable pageable) throws InspireNetzException;

    public Boolean voidTransactions(Long rdmMerchantNo,Long rdmId) throws InspireNetzException;

    public Redemption updateRedemptionDetails(String trackingId, Integer status, String rdmDeliveryCourierInfo, String rdmDeliveryCourierTracking) throws InspireNetzException ;

    public CatalogueRedemptionItemRequest getCatalogueRedemptionRequestObject(String loyaltyId, String prdCode, Integer quantity, Long merchantNo, Integer rdmChannel, String destLoyaltyId,String rdmDeliveryAddress1,String rdmDeliveryAddress2,String rdmDeliveryAddress3,String rdmDeliveryCity,String rdmDeliveryState,String rdmDeliveryCountry,String rdmDeliveryPostCode, String rdmContactNumber) throws InspireNetzException;
}
