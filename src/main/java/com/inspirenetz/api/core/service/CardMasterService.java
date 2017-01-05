package com.inspirenetz.api.core.service;

import com.inspirenetz.api.core.dictionary.*;
import com.inspirenetz.api.core.domain.CardMaster;
import com.inspirenetz.api.core.domain.CardTransaction;
import com.inspirenetz.api.core.domain.CardType;
import com.inspirenetz.api.core.domain.Sale;
import com.inspirenetz.api.rest.exception.InspireNetzException;
import com.inspirenetz.api.rest.resource.CardMasterResource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;


import java.sql.Date;
import java.util.List;
import java.util.Map;

/**
 * Created by sandheepgr on 28/3/14.
 */
public interface CardMasterService extends BaseService<CardMaster> {


    public CardMaster findByCrmId(Long crmId);
    public CardMaster findByCrmMerchantNoAndCrmCardNo(Long crmMerchantNo, String crmCardNo);

    public Page<CardMaster> findByCrmMerchantNo(Long crmMerchantNo, Pageable pageable);
    public Page<CardMaster> findByCrmMerchantNoAndCrmCardNoLike(Long crmMerchantNo,String crmCardNo, Pageable pageable);
    public Page<CardMaster> findByCrmMerchantNoAndCrmMobileLike(Long crmMerchantNo,String crmMobile, Pageable pageable);
    public Page<CardMaster> findByCrmMerchantNoAndCrmLoyaltyIdLike(Long crmMerchantNo,String crmLoyaltyId, Pageable pageable);
    public Page<CardMaster> findByCrmMerchantNoAndCrmCardHolderNameLike(Long crmMerchantNo,String crmCardHolderName, Pageable pageable);
    public List<CardMaster> listCardsForCustomer(Long crmMerchantNo, String crmMobile, String crmEmailId, String crmLoyaltyId);
    public boolean isCardExpired(CardType cardType,CardMaster cardMaster);
    public String getCardExpiry(CardType cardType,CardMaster cardMaster );


    public boolean isDuplicateCardExisting(CardMaster cardMaster);
    public CardMaster saveCardMaster(CardMaster cardMaster);
    public CardMaster validateAndSaveCardMaster(CardMasterResource cardMasterResource) throws InspireNetzException;

    public boolean deleteCardMaster(Long crmId,Long merchantNo) throws InspireNetzException;
    public CardMaster getCardMasterByCrmLoyaltyId(Long merchantNo,String crmLoyaltyId) throws InspireNetzException;

    public List<CardMaster> findByCrmMerchantNoAndCrmLoyaltyIdOrderByCrmIdDesc(Long merchantNo,String crmLoyaltyId);

    public CardMaster findByCrmCardNo(String crmCardNo);






    public CardMasterOperationResponse setCardLockStatus(CardMasterLockStatusRequest cardMasterLockStatusRequest) throws InspireNetzException;
/*
    public CardMasterOperationResponse changeCardPin(CardMasterChangePinRequest cardMasterChangePinRequest) throws InspireNetzException;
*/
    /*public CardMasterOperationResponse transferCard(CardMasterTransferRequest cardMasterTransferRequest) throws InspireNetzException;*/
    public CardMasterOperationResponse issueCard(CardMaster cardMaster, Long userNo,String userLoginId, boolean isReissue,Integer overrideStatus) throws InspireNetzException;
/*
    public CardMasterOperationResponse refundCard(CardMasterRefundRequest cardMasterRefundRequest) throws InspireNetzException;
*/
/*
    public CardMasterOperationResponse topupCard(CardMasterTopupRequest cardMasterTopupRequest) throws InspireNetzException;
*/
    /*public CardMasterOperationResponse debitCard(CardMasterDebitRequest cardMasterDebitRequest) throws InspireNetzException;*/

    public CardMasterOperationResponse validateAndIssueCard(CardMaster cardMaster, Long userNo,String userLoginId, boolean isReissue,Integer overrideStatus) throws InspireNetzException;
    public boolean validateAndDeleteCardMaster(Long crmId,Long merchantNo) throws InspireNetzException;


    public Page<CardMaster> searchCardMasters(String filter, String query, Long merchantNo,Pageable pageable);

    public CardMaster getCardMasterInfo(Long crmId, Long merchantNo) throws InspireNetzException;

    public CardMasterOperationResponse changeCardLockStatus(String cardNo, String status, Long merchantNo) throws InspireNetzException;

    public CardMasterOperationResponse changeCardPin(String cardNo, String crmPin, Long merchantNo) throws InspireNetzException;

    public CardMasterOperationResponse topupCard(String cardNo, Double amount, Long merchantNo,String reference,Integer paymentMode,String location,boolean awardIncentive,boolean promoTopup,Double incentiveAmount) throws InspireNetzException;

    public CardMasterOperationResponse transferCard(String sourceCardNo, String destCardNo, Long merchantNo,String location) throws InspireNetzException;

    public CardMasterOperationResponse debitCard(String cardNo, String reference, String pin,Double amount,Long merchantNo,CardTransferAuthenticationRequest cardTransferAuthenticationRequest,Integer paymentMode,String location) throws InspireNetzException;

    public CardMasterOperationResponse refundCard(String cardNo,Double amount,String reference,Long merchantNo,Integer paymentMode,String location,Double promoRefundAmount) throws InspireNetzException;

    public CardMasterOperationResponse returnCard(String cardNo,String reference,Long merchantNo,Integer paymentMode,String location) throws InspireNetzException;


    public CardMasterOperationResponse debitCardCompatible(String cardNo, String reference, String pin,Double amount,Long merchantNo,String otpCode,Integer paymentMode,String location) throws InspireNetzException;

    public CardMasterOperationResponse refundCardWithOTP(String cardNo,Double amount,String reference,String pin,Long merchantNo,String otpCode,Integer paymentMode,String location,Double promoRefundAmount) throws InspireNetzException;


    public CardMasterOperationResponse topupCard(String cardNo, Double amount,Long merchantNo,Integer isLoyaltyPoint,Long rwdCurrencyId,String reference,Integer paymentMode,String location,boolean awardIncentive,Double incentiveAmount) throws InspireNetzException;

    public boolean isDuplicateMobileNumberExisting(CardMaster cardMaster);

    public boolean isCardBalanceExpired(CardType cardType,CardMaster cardMaster) ;

    public void startCardBalanceExpiry();

    public CardMaster cardBalanceExpiryOperation(CardMaster cardMaster);

    public List<CardMaster> findByCrmMerchantNo(Long crmMerchantNo);

    public void processCardBalanceExpiry();

    public void processCardExpiry();

    public void startCardExpiry();

    public CardMaster cardExpiryOperation(CardMaster cardMaster);

    public CardMasterOperationResponse validateAndIssueCardFromMerchant(CardMasterResource cardMasterResource,Long userNo,String userLoginId, boolean isReissue,Integer overridestatus,String location) throws InspireNetzException;

    public CardMasterPublicResponse issueCardFromPublic(String cardNumber,String pin,String mobile,String cardHolderName,String email,String dob,Long merchantNo,String otpCode,Integer overrideStatus) throws InspireNetzException;

    public boolean getCardBalanceOTP(String cardNumber,Long merchantNo) throws InspireNetzException;


    public CardMasterPublicResponse getCardBalancePublic(String cardNumber,Long merchantNo,String otpCode) throws InspireNetzException;

    public boolean transferAmount(String fromCardNumber,String toCardNumber,Long merchantNo,Double transferAmount,String pin,String reference,Integer authType,String otpCode,String location) throws InspireNetzException;

    public boolean generateTransferAmountOtp(String crmCardNo,Long merchantNo) throws InspireNetzException;

    public boolean isActivationDateValid(CardMaster cardMaster,Long merchantNo);

    public void issueMultipleCard(CardMaster cardMaster,Long userNo,String userLoginId, boolean isReissue,Integer overridestatus) throws InspireNetzException;
    public List<CardMaster> findByCrmMerchantNoAndCrmMobile(Long crmMerchantNo,String crmMobile);

    public CardMaster updateTier(String cardNumber, Long tierId,Long merchantNo, Boolean updateCardExpiry, Date cardExpiryDate) throws InspireNetzException;

    public Double calculateTieredRatioIncentiveAmount(CardType cardType, CardMasterTopupRequest cardMasterTopupRequest);

    public Double calculatePromoAmount(CardType cardType, CardMasterTopupRequest cardMasterTopupRequest);

    public Sale prepaidCardSaleObject(CardMaster cardMaster ,CardTransaction cardTransaction);

    public void processChargeCardTransactionForLoyalty(CardMaster cardMaster,CardTransaction cardTransaction) throws InspireNetzException;

    public void processChargeCardTransactionForLoyaltyAsync(Sale sale) throws InspireNetzException;

    public boolean isUserPinRequiredForDebit(CardMaster cardMaster);

    public String getEncodedCrmPin(String crmPin);

    public Map<String,Double> getRefundableBalance(Double amount,String cardNo) throws InspireNetzException;

    public Boolean deductAndConvertToPoints(CashBackRequest cashBackRequest, Double pointsRequired) throws InspireNetzException;
}
