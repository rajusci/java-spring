package com.inspirenetz.api.core.service.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.inspirenetz.api.core.dictionary.*;
import com.inspirenetz.api.core.dictionary.PaymentStatus;
import com.inspirenetz.api.core.domain.*;
import com.inspirenetz.api.core.service.*;
import com.inspirenetz.api.rest.exception.InspireNetzException;
import com.inspirenetz.api.util.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;

import javax.sql.DataSource;
import java.sql.Date;
import java.sql.Time;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * * Created by saneeshci on 16/09/15..
 */
@Service
public class CashBackServiceImpl implements CashBackService {


    // Create the logger
    private static Logger log = LoggerFactory.getLogger(CashBackServiceImpl.class);

    @Autowired
    private UserService userService;

    @Autowired
    private ImageService imageService;

    @Autowired
    AuthSessionUtils authSessionUtils;

    @Autowired
    XmlParserUtil xmlParserUtil;

    @Autowired
    Environment environment;

    @Autowired
    CustomerService customerService;

    @Autowired
    IntegrationService integrationService;

    @Autowired
    LinkedLoyaltyService linkedLoyaltyService;

    @Autowired
    CustomerRewardBalanceService customerRewardBalanceService;

    @Autowired
    RewardCurrencyService rewardCurrencyService;

    @Autowired
    LoyaltyEngineService loyaltyEngineService;

    @Autowired
    UserMessagingService userMessagingService;

    @Autowired
    CustomerActivityService customerActivityService;

    @Autowired
    AccountBundlingSettingService accountBundlingSettingService;

    @Autowired
    AccountBundlingUtils accountBundlingUtils;

    @Autowired
    PartyApprovalService partyApprovalService;

    @Autowired
    OneTimePasswordService oneTimePasswordService;

    @Autowired
    private DataSource dataSource;

    @Autowired
    RedemptionService redemptionService;

    @Autowired
    GeneralUtils generalUtils;

    @Autowired
    RedemptionMerchantService redemptionMerchantService;

    @Autowired
    MerchantSettlementService merchantSettlementService;

    @Autowired
    SecurityService securityService;

    @Autowired
    MerchantService merchantService;

    @Autowired
    CustomerSubscriptionService customerSubscriptionService;

    @Autowired
    ProductService productService;

    @Autowired
    MerchantLocationService merchantLocationService;

    @Autowired
    MerchantSettingService merchantSettingService;

    @Autowired
    CardMasterService cardMasterService;

    @Autowired
    private ObjectMapper mapper;

    @Autowired
    private MerchantRedemptionPartnerService merchantRedemptionPartnerService;

    @Autowired
    private TransactionService transactionService;

    @Autowired
    private CardTransactionService cardTransactionService;

    Map<String,CashBackConfig> cashBackConfigHashMap;

    // Constructor
    public CashBackServiceImpl() {

    }

    /**
     *
     * @param cashBackRequest
     * @return
     * @throws java.io.IOException
     * @throws org.xml.sax.SAXException
     * @throws javax.xml.parsers.ParserConfigurationException
     * @throws com.inspirenetz.api.rest.exception.InspireNetzException
     */
    @Override
    public CashBackResponse doCashBack(CashBackRequest cashBackRequest) throws InspireNetzException {

        //Variable holding the points required for cashback
        double pointsRequired = 0.0;

        if(cashBackConfigHashMap == null){

            cashBackConfigHashMap = xmlParserUtil.getCashBackMap();

        }
        CashBackResponse cashBackResponse = new CashBackResponse();

        validateCashBackRequestFields(cashBackRequest);

        //get the merchant code from request
        String merchantIdentifier = cashBackRequest.getMerchantIdentifier();

        String cuaParams = cashBackRequest.getLoyaltyId()+"#"+cashBackRequest.getAmount()+"#"+cashBackRequest.getRef()+"#"+cashBackRequest.getChannel();

        MessageWrapper messageWrapper = setMessageWrapperParameters(cashBackRequest);

        //get the sms params
        HashMap<String,String> smsParams = getSMSParamsForCashBack(cashBackRequest);

        smsParams.put("#points",generalUtils.getFormattedValue(cashBackRequest.getRedeemQty()));

        //set sms params
        messageWrapper.setParams(smsParams);

        //check pay eith wallet balance enabled
        boolean isPayWithBalanceEnabled = merchantSettingService.isSettingEnabledForMerchant(AdminSettingsConfiguration.MER_ENABLE_PAY_WITH_WALLET_BALANCE,cashBackRequest.getMerchantNo());

        //check if multiple card for single mobile enabled or not
        boolean isMultiCardEnabled = merchantSettingService.isSettingEnabledForMerchant(AdminSettingsConfiguration.MER_ALLOWED_MULTIPLE_CARD_IN_SAME_MOBILE,cashBackRequest.getMerchantNo());




        //check whether merchant identifier is null or empty
        if(merchantIdentifier == null || merchantIdentifier.length() == 0){

            //log the error
            log.error("doCashBack : No merchant identifier in request");

            //add a customer activity for the failed request
            customerActivityService.logActivity(cashBackRequest.getLoyaltyId(), CustomerActivityType.CASHBACK,"Cashback failed, no merchant identifier in request",cashBackRequest.getMerchantNo(),cuaParams);

            //set spiel name
            messageWrapper.setSpielName(MessageSpielValue.CASHBACK_INVALID_REQUEST);

            //send notification
            userMessagingService.transmitNotification(messageWrapper);

            //throw the error
            throw new InspireNetzException(APIErrorCode.ERR_OPERATION_FAILED);
        }

        //get the redemption merchant info
        RedemptionMerchant redemptionMerchant = redemptionMerchantService.findByRemCode(merchantIdentifier);

        //check whether the merchant is valid
        if(redemptionMerchant == null){

            //log.error
            log.error("doCashBack : No redemption merchant information found");

            //add a customer activity for the failed request
            customerActivityService.logActivity(cashBackRequest.getLoyaltyId(), CustomerActivityType.CASHBACK,"Cashback failed, no redemption merchant information found",cashBackRequest.getMerchantNo(),cuaParams);

            //set spiel name
            messageWrapper.setSpielName(MessageSpielValue.CASHBACK_INVALID_REDEMPTION_MERCHANT);

            //send notification
            userMessagingService.transmitNotification(messageWrapper);

            throw new InspireNetzException(APIErrorCode.ERR_INVALID_REDEMPTION_MERCHANT);

        }

        cashBackRequest.setRedemptionMerchant(redemptionMerchant);
        cashBackRequest.setRemName(redemptionMerchant.getRemName());

        //check the redemption merchant type
        if(redemptionMerchant.getRemType() == RedemptionMerchantType.WEBSERVICE){

            //set cashback type as merchant code
            cashBackRequest.setCashBackType(cashBackRequest.getMerchantIdentifier());

        } else {

            //set cashback type as default
            cashBackRequest.setCashBackType("default");

        }

        CashBackConfig cashBackConfig = cashBackConfigHashMap.get(cashBackRequest.getCashBackType().toLowerCase());

        if(cashBackConfig == null){

            //log.error
            log.error("doCashBack : No configuration found : "+ cashBackRequest);

            //add a customer activity for the failed request
            customerActivityService.logActivity(cashBackRequest.getLoyaltyId(), CustomerActivityType.CASHBACK,"Cashback failed, pre validation failed",cashBackRequest.getMerchantNo(),cuaParams);

            //set spiel name
            messageWrapper.setSpielName(MessageSpielValue.CASHBACK_VALIDATION_FAILED_NO_CONFIGURATION);

            //send notification
            userMessagingService.transmitNotification(messageWrapper);

            throw new InspireNetzException(APIErrorCode.ERR_CASHBACK_NO_CONFIG_FOUND);

        }

        // Get the entity for the MerchantRewardPartner
        MerchantRedemptionPartner merchantRedemptionPartner = merchantRedemptionPartnerService.findByMrpMerchantNoAndMrpRedemptionMerchant(cashBackRequest.getMerchantNo(),redemptionMerchant.getRemNo());

        // Check if the entry is found
        if ( merchantRedemptionPartner == null ) {

            //log.error
            log.error("doCashBack : No configuration found : "+ cashBackRequest);

            //add a customer activity for the failed request
            customerActivityService.logActivity(cashBackRequest.getLoyaltyId(), CustomerActivityType.CASHBACK,"Cashback failed, pre validation failed",cashBackRequest.getMerchantNo(),cuaParams);

            //set spiel name
            messageWrapper.setSpielName(MessageSpielValue.CASHBACK_VALIDATION_FAILED_NO_CONFIGURATION);

            //send notification
            userMessagingService.transmitNotification(messageWrapper);

            // Throw the exception
            throw new InspireNetzException(APIErrorCode.ERR_CASHBACK_NO_REWARD_CURRENCY);


        }

        // Set the reward currency id to the reward currency id specified in the entry
        cashBackRequest.setRwdCurrencyId(merchantRedemptionPartner.getMrpRewardCurrency());

        //get reward qty
        RewardCurrency rewardCurrency = rewardCurrencyService.findByRwdCurrencyId(cashBackRequest.getRwdCurrencyId());

        if(rewardCurrency == null){

            log.error("doCashBack : Reward currency not found");

            //add a customer activity for the failed request
            customerActivityService.logActivity(cashBackRequest.getLoyaltyId(), CustomerActivityType.CASHBACK,"Cashback failed, pre validation failed",cashBackRequest.getMerchantNo(),cuaParams);

            //set spiel name
            messageWrapper.setSpielName(MessageSpielValue.CASHBACK_INVALID_CURRENCY);

            //send notification
            userMessagingService.transmitNotification(messageWrapper);


            throw new InspireNetzException(APIErrorCode.ERR_CASHBACK_INVALID_CURRENCY);
        }

        if(rewardCurrency.getRwdCashbackIndicator() == IndicatorStatus.NO){

            log.error("doCashBack : Cashback is not enabled for reward currency");

            //add a customer activity for the failed request
            customerActivityService.logActivity(cashBackRequest.getLoyaltyId(), CustomerActivityType.CASHBACK,"Cashback failed, pre validation failed",cashBackRequest.getMerchantNo(),cuaParams);

            //set spiel name
            messageWrapper.setSpielName(MessageSpielValue.CASHBACK_NOT_ENABLED_FOR_CURRENCY);

            //send notification
            userMessagingService.transmitNotification(messageWrapper);

            throw new InspireNetzException(APIErrorCode.ERR_CASHBACK_NOT_ENABLED_FOR_CURRENCY);
        }


        // Set the reward currency name in the object
        cashBackRequest.setRwdCurrencyName(rewardCurrency.getRwdCurrencyName());

        if(merchantRedemptionPartner.getMrpCashbackRatioDeno() !=null && merchantRedemptionPartner.getMrpCashbackRatioDeno() !=0.0){

            //get the required reward balance for the customer
            pointsRequired = merchantRedemptionPartnerService.getCashbackQtyForAmount(merchantRedemptionPartner.getMrpCashbackRatioDeno(),cashBackRequest.getAmount());

        } else{

            //get the required reward balance for the customer
            pointsRequired = rewardCurrencyService.getCashbackQtyForAmount(rewardCurrency,cashBackRequest.getAmount());

        }



        //set redeem qty of cashback request as the calculated points
        cashBackRequest.setRedeemQty(pointsRequired);

        smsParams.put("#points",generalUtils.getFormattedValue(cashBackRequest.getRedeemQty()));

        //set sms params
        messageWrapper.setParams(smsParams);


        try {

            cashBackRequest = doValidations(cashBackRequest);

        } catch(InspireNetzException e){

        if(e.getErrorCode() == APIErrorCode.ERR_NO_PROPERTY_FOUND_IN_CASHBACK_FILE){

            //log.error
            log.error("doCashBack : No property found : "+ cashBackRequest);


            //add a customer activity for the failed request
            customerActivityService.logActivity(cashBackRequest.getLoyaltyId(), CustomerActivityType.CASHBACK,"Cashback failed, pre validation failed",cashBackRequest.getMerchantNo(),cuaParams);

            //set spiel name
            messageWrapper.setSpielName(MessageSpielValue.CASHBACK_VALIDATION_FAILED_NO_PROPERTY);

            //send notification
            userMessagingService.transmitNotification(messageWrapper);

            throw new InspireNetzException(APIErrorCode.ERR_CASHBACK_PRE_VALIDATION_FAILED);

        } else {

            throw new InspireNetzException(e.getErrorCode());
        }
    }


        if(cashBackRequest.isValid()){

            try{

                //call the pre api validation api's
                cashBackRequest = makePreValidationApiCall(cashBackRequest);

            } catch(InspireNetzException e){

                if(e.getErrorCode() == APIErrorCode.ERR_NO_PROPERTY_FOUND_IN_CASHBACK_FILE){

                    //log.error
                    log.error("doCashBack : No property found : "+ cashBackRequest);

                    //add a customer activity for the failed request
                    customerActivityService.logActivity(cashBackRequest.getLoyaltyId(), CustomerActivityType.CASHBACK,"Cashback failed, pre validation failed",cashBackRequest.getMerchantNo(),cuaParams);

                    //set spiel name
                    messageWrapper.setSpielName(MessageSpielValue.CASHBACK_PRE_VALIDATION_FAILED_NO_PROPERTY);

                    //send notification
                    userMessagingService.transmitNotification(messageWrapper);

                    throw new InspireNetzException(APIErrorCode.ERR_CASHBACK_PRE_VALIDATION_FAILED);
                }
            }

        }

        boolean isDeducted = false;

        // if the pay with balance enabled
        if(isPayWithBalanceEnabled && !isMultiCardEnabled && cashBackRequest.isValid()){

            isDeducted = cardMasterService.deductAndConvertToPoints(cashBackRequest,pointsRequired);

        }
        //flag for point debiting
        boolean isDebit = false;

        //if the preApi calls are valid then deduct pointss
        if(cashBackRequest.isValid()){

            //debit points from customer account
            isDebit = debitPointsForCashBack(cashBackRequest);

        }  else {

            //log.error
            log.error("doCashBack : Pre validation is failed : "+ cashBackRequest);

            //add a customer activity for the failed request
            customerActivityService.logActivity(cashBackRequest.getLoyaltyId(), CustomerActivityType.CASHBACK,"Cashback failed, pre validation failed",cashBackRequest.getMerchantNo(),cuaParams);

            //set spiel name
            messageWrapper.setSpielName(MessageSpielValue.CASHBACK_PRE_VALIDATION_FAILED);

            //send notification
            userMessagingService.transmitNotification(messageWrapper);

            throw new InspireNetzException(APIErrorCode.ERR_CASHBACK_PRE_VALIDATION_FAILED);
        }

        //if any error happens after the point deduction, reversal is needed
        try{

            if(isDebit){

                //call the pre api validation api's
                cashBackRequest = makePostDebitApiCall(cashBackRequest);

            } else {

                //log.error
                log.error("doCashBack : Point debiting failed for request : "+ cashBackRequest);

                //add a customer activity for the failed request
                customerActivityService.logActivity(cashBackRequest.getLoyaltyId(), CustomerActivityType.CASHBACK,"Cashback failed, point debit failed",cashBackRequest.getMerchantNo(),cuaParams);

                //set spiel name
                messageWrapper.setSpielName(MessageSpielValue.CASHBACK_POINT_DEBIT_FAILED);

                //send notification
                userMessagingService.transmitNotification(messageWrapper);

                throw new InspireNetzException(APIErrorCode.ERR_CASHBACK_DEBIT_FAILED);
            }


        } catch(Exception e){

            //log the activity
            customerActivityService.logActivity(cashBackRequest.getLoyaltyId(), CustomerActivityType.REDEMPTION,"Cash back request failed",cashBackRequest.getMerchantNo(),"");

            //set spiel name
            messageWrapper.setSpielName(MessageSpielValue.CASHBACK_INVALID_REDEMPTION_MERCHANT);

            //send notification
            userMessagingService.transmitNotification(messageWrapper);

            //create reward adjustment object
            RewardAdjustment rewardAdjustment = createRewardAdjustmentObject(cashBackRequest.getMerchantNo(),cashBackRequest.getLoyaltyId(),rewardCurrency.getRwdCurrencyId(),cashBackRequest.getRedeemQty(),false,0l,cashBackRequest.getRdmId()+"",cashBackRequest.getRef(),cashBackRequest.getChannel());

            //reverse the points redeemed
            customerRewardBalanceService.awardPointsForRewardAdjustment(rewardAdjustment);

            throw new InspireNetzException(APIErrorCode.ERR_CASHBACK_FAILED);

        }

        try {

            //do settlement if the call is success
            if(cashBackRequest.isValid()){

                //add settlement entry for the cashback request
                cashBackRequest = doMerchantSettlement(cashBackRequest);

            } else {

                //log.error
                log.error("doCashBack : Post call failed: "+ cashBackRequest);

                //add a customer activity for the failed request
                customerActivityService.logActivity(cashBackRequest.getLoyaltyId(), CustomerActivityType.CASHBACK,"Cashback failed, point debit failed",cashBackRequest.getMerchantNo(),cuaParams);

                //set spiel name
                messageWrapper.setSpielName(MessageSpielValue.CASHBACK_POST_CALL_FAILED);

                //send notification
                userMessagingService.transmitNotification(messageWrapper);

                //create reward adjustment object
                RewardAdjustment rewardAdjustment = createRewardAdjustmentObject(cashBackRequest.getMerchantNo(),cashBackRequest.getLoyaltyId(),rewardCurrency.getRwdCurrencyId(),cashBackRequest.getRedeemQty(),false,0l,cashBackRequest.getRdmId()+"",cashBackRequest.getRef(),cashBackRequest.getChannel());

                //reverse the points redeemed
                customerRewardBalanceService.awardPointsForRewardAdjustment(rewardAdjustment);


                throw new InspireNetzException(APIErrorCode.ERR_CASHBACK_POST_CALL_FAILED);
            }
        }catch(InspireNetzException e){

            throw new InspireNetzException(e.getErrorCode());

        }
        catch(Exception e){

            log.error("doCashBack : Exception during merchant settlement");

        }


        //call the method for sending cash back success notifications
        transmitNotifications(cashBackRequest);

        //update the redemption status to success
        redemptionService.updateRedemptionStatus(cashBackRequest.getRdmId(), RedemptionStatus.RDM_STATUS_PROCESSED_SUCCESSFULLY);

        //set redemption id as reference
        cashBackResponse.setReference(cashBackRequest.getRdmId());

        //set deducted points
        cashBackResponse.setPoints(cashBackRequest.getRedeemQty());

        //set tracking id as redemption tracking id
        cashBackResponse.setTrackingId(cashBackRequest.getTrackingId());

        String remarks = "Successful cashback of amount "+generalUtils.getFormattedValue(cashBackRequest.getAmount())+ " for "+cashBackRequest.getCashBackType();

        //add a customer activity for the failed request
        customerActivityService.logActivity(cashBackRequest.getLoyaltyId(), CustomerActivityType.CASHBACK,remarks,cashBackRequest.getMerchantNo(),cuaParams);

        //return response object
        return cashBackResponse;
    }

    @Override
    public CashBackRequest getCashbackRequestObjectForPartner(String mobile, String userLoginId, Long merchantNo, String reference, String amount, Integer channel, String otpCode) throws InspireNetzException {

        //get the customer details from mobile and merchant no
        Customer customer = customerService.findByCusMobileAndCusMerchantNo(mobile,merchantNo);

        if(customer == null || customer.getCusStatus() !=CustomerStatus.ACTIVE){

            //log error
            log.error("getCashbackRequestObjectForPartner : No customer information found for pay request Mobile: "+mobile );

            throw new InspireNetzException(APIErrorCode.ERR_NO_DATA);

        }

        //get a new object of cashback request
        CashBackRequest cashBackRequest = new CashBackRequest();

        //set fields
        cashBackRequest.setAmount(Double.parseDouble(amount));
        cashBackRequest.setChannel(channel);
        cashBackRequest.setLoyaltyId(customer.getCusLoyaltyId());
        cashBackRequest.setMerchantNo(merchantNo);
        cashBackRequest.setOtpCode(otpCode);

        //Get the requested user details
        User user = userService.findByUsrLoginId(userLoginId);

        //get the redemption merchant no
        Long redemptionMerchantNo = user.getUsrThirdPartyVendorNo();

        //get the redemption merchant information
        RedemptionMerchant redemptionMerchant = redemptionMerchantService.findByRemNo(redemptionMerchantNo);

        if(redemptionMerchant == null){

            //log the error
            log.error("getCashbackRequestObjectForPartner : Redemption merchant not found for requested user ::: "+cashBackRequest);

            throw new InspireNetzException(APIErrorCode.ERR_NO_REDEMPTION_MERCHANT);
        }

        //get the redemption merchant code
        String merchantCode = redemptionMerchant.getRemCode();

        if(merchantCode == null){

            log.error("getCashbackRequestObjectForPartner : No merchant code defined for redemption merchant :::" +cashBackRequest);

            throw new InspireNetzException(APIErrorCode.ERR_NO_DATA);
        }


        //set merchant code , user login id , reference etc.
        cashBackRequest.setMerchantIdentifier(merchantCode);
        cashBackRequest.setRef(userLoginId);
        cashBackRequest.setReference(reference);

        return cashBackRequest;


    }

    @Override
    public CashBackResponse doCashbackFromPartner(String mobile, String userLoginId, Long merchantNo, String amount, Integer channel, String reference, String otpCode) throws InspireNetzException {

        //get cashback request object from parameters
        CashBackRequest cashBackRequest = getCashbackRequestObjectForPartner(mobile, userLoginId , merchantNo, reference, amount, channel,otpCode);

        // Get the CashBack
        CashBackResponse cashBackResponse = doCashBack(cashBackRequest);


        return cashBackResponse;
    }

    private void validateCashBackRequestFields(CashBackRequest cashBackRequest) throws InspireNetzException {

        //check if the merchant identifier field is not null or empty
        if(cashBackRequest.getMerchantIdentifier() == null || cashBackRequest.getMerchantIdentifier().equals("")){

            log.error("validateCashBackRequestFields : No merchant identifier found in request "+cashBackRequest);

            throw new InspireNetzException(APIErrorCode.ERR_CASHBACK_FAILED_NO_MERCHANT_IDENTIFIER);

        }

        //check if the customer loyaltyid field is not null or empty
        if(cashBackRequest.getLoyaltyId() == null || cashBackRequest.getLoyaltyId().equals("")){

            log.error("validateCashBackRequestFields : No merchant identifier found in request "+cashBackRequest);

            throw new InspireNetzException(APIErrorCode.ERR_CASHBACK_FAILED_NO_LOYALTY_ID);

        }

        //check if the reference field is not null or empty
        if(cashBackRequest.getRef() == null || cashBackRequest.getRef().equals("")){

            log.error("validateCashBackRequestFields : No reference field found in request "+cashBackRequest);

            throw new InspireNetzException(APIErrorCode.ERR_CASHBACK_FAILED_NO_REFERENCE_FIELD);

        }

        //get the customer
        Customer customer = customerService.findByCusLoyaltyIdAndCusMerchantNo(cashBackRequest.getLoyaltyId(),cashBackRequest.getMerchantNo());

        //check if the customer is active
        if(customer == null || customer.getCusStatus() != CustomerStatus.ACTIVE ){

            //log the message
            log.info("validateCashBackRequestFields : No customer info found");

            //throw an exception
            throw new InspireNetzException(APIErrorCode.ERR_CUSTOMER_NOT_ACTIVE);

        }

    }

    private RewardAdjustment createRewardAdjustmentObject(Long merchantNo, String loyaltyId, Long adjCurrencyId, Double adjPoints, boolean isTierAffected, Long adjProgramNo, String adjIntReference,String adExtReference,int channel) {

        RewardAdjustment rewardAdjustment = new RewardAdjustment();

        //set values to the object
        rewardAdjustment.setLoyaltyId(loyaltyId);
        rewardAdjustment.setRwdCurrencyId(adjCurrencyId);
        rewardAdjustment.setRwdQty(adjPoints);
        rewardAdjustment.setMerchantNo(merchantNo);
        rewardAdjustment.setExternalReference(adExtReference);
        rewardAdjustment.setIntenalReference(adjIntReference);
        rewardAdjustment.setProgramNo(adjProgramNo);
        rewardAdjustment.setTierAffected(isTierAffected);
        rewardAdjustment.setPointReversed(true);
        rewardAdjustment.setChannel(channel);

        //return the reward adjustment object
        return rewardAdjustment;
    }

    private void transmitNotifications(CashBackRequest cashBackRequest) throws InspireNetzException {

        //get the validation properties for the cash back type
        List<String> recipientList = cashBackConfigHashMap.get(cashBackRequest.getCashBackType().toLowerCase()).getNotifications();


        MessageWrapper messageWrapper = null;

        if(recipientList != null && recipientList.size() > 0){

            messageWrapper= setMessageWrapperParameters(cashBackRequest);

        } else {

            return;

        }

        //get the sms params
        HashMap<String,String> smsParams = getSMSParamsForCashBack(cashBackRequest);

        //set sms params
        messageWrapper.setParams(smsParams);

        //Loop through the recipient list and send notification
        for(String recipient : recipientList){

            if(recipient.equals(CashBackNotificationType.CUSTOMER)){

                //set the customer loyalty id as messagewrapper destination
                messageWrapper.setLoyaltyId(cashBackRequest.getLoyaltyId());

                messageWrapper.setIsCustomer(IndicatorStatus.YES);

                String customerSpiel= cashBackConfigHashMap.get(cashBackRequest.getCashBackType().toLowerCase()).getCustomerSpiel();

                messageWrapper.setSpielName(customerSpiel);

                //send notification
                userMessagingService.transmitNotification(messageWrapper);

            } else if(recipient.equals(CashBackNotificationType.MERCHANT) ){

                MessageWrapper messageWrapper1 = setMessageWrapperParameters(cashBackRequest);

                messageWrapper1.setParams(smsParams);

                //set cashier mobile no as the destination min
                messageWrapper1.setMobile(cashBackRequest.getRef());

                String merchantSpiel = cashBackConfigHashMap.get(cashBackRequest.getCashBackType().toLowerCase()).getMerchantSpiel();

                messageWrapper1.setSpielName(merchantSpiel);

                //send notification
                userMessagingService.transmitNotification(messageWrapper1);
            }

        }

        //check if the redemption merchant type is sms
        if(cashBackRequest.getRedemptionMerchant().getRemType() == RedemptionMerchantType.SMS){

            MessageWrapper messageWrapper2 = setMessageWrapperParameters(cashBackRequest);

            messageWrapper2.setParams(smsParams);

            //if the type is sms we have to send sms to the redemption settlement reference also, set cashier mobile no as the destination min
            messageWrapper2.setMobile(cashBackRequest.getRef());

            messageWrapper2.setSpielName(MessageSpielValue.CASHBACK_SUCCESS_MERCHANT);

            //send notificaiton to the redemption merchant
            userMessagingService.transmitNotification(messageWrapper2);
        }

    }

    private HashMap<String,String> getSMSParamsForCashBack(CashBackRequest cashBackRequest) {

        //get the parameters in api
        HashMap<String,String> smsParams = (HashMap<String, String>) getParameterMapForApiCall(cashBackRequest);

        List<String> keyList = new ArrayList<>(0);

        HashMap<String,String> returnList = new HashMap<>(0);

        for (Map.Entry<String, String> entry : smsParams.entrySet()) {

            String key = String.valueOf(entry.getKey());
            String value = String.valueOf(entry.getValue());
            returnList.put("#"+key,value);

        }

        //add formatted redeem qty value
        returnList.put("#redeemQty",generalUtils.getFormattedValue(cashBackRequest.getRedeemQty()));

        //add formatted redeem qty value
        returnList.put("#amount",generalUtils.getFormattedValue(cashBackRequest.getAmount(),"#0.00"));

        //return the map
        return returnList;

    }

    private MessageWrapper setMessageWrapperParameters(CashBackRequest cashBackRequest) {

        //get a new object of message wrapper
        MessageWrapper messageWrapper = new MessageWrapper();

        //set the field values
        messageWrapper.setChannel(MessageSpielChannel.SMS);
        messageWrapper.setMerchantNo(cashBackRequest.getMerchantNo());
        messageWrapper.setSpielName(MessageSpielValue.CASH_BACK_SUCCESS_SMS);
        messageWrapper.setLoyaltyId(cashBackRequest.getLoyaltyId());


        return messageWrapper;
    }

    private CashBackRequest doMerchantSettlement(CashBackRequest cashBackRequest) throws InspireNetzException {

        //get redemption merchant from cashback request
        RedemptionMerchant redemptionMerchant = cashBackRequest.getRedemptionMerchant();

        //get message wrapper object
        MessageWrapper messageWrapper = setMessageWrapperParameters(cashBackRequest);

        //set the params for activity
        String cuaParams = cashBackRequest.getCashBackType()+"#"+cashBackRequest.getRedeemQty()+"#"+cashBackRequest.getChannel();

        //get the settlement url for load wallet api calls
        String loadWalletUrl = environment.getProperty("integration.cashback.loadwallet");

        if(redemptionMerchant != null){

            //get settlement type
            int settlementType = redemptionMerchant.getRemSettlementType();

            //set the settlement type
            cashBackRequest.setSettlementType(settlementType);

            if(cashBackRequest.getSettlementType() == MerchantSettlementType.LOAD_WALLET){

                //get map from the cashback object
                Map<String,String> requestParams = getParameterMapForApi(cashBackRequest);

                //if settlement level is merchant , replace the ref field in cashback request
                if(redemptionMerchant.getRemSettlementLevel() == MerchantSettlementLevel.MERCHANT_LEVEL){

                    //get the merchant min
                    if(redemptionMerchant.getRemContactMobile() == null || redemptionMerchant.getRemContactMobile().length() ==0){

                        //log error
                        log.error("doMerchantSettlement : Merchant doesn't have contact details, unable to do load wallet");

                        //add a customer activity for the failed request
                        customerActivityService.logActivity(cashBackRequest.getLoyaltyId(), CustomerActivityType.CASHBACK,"Cashback failed, contact details missing for merchant",cashBackRequest.getMerchantNo(),cuaParams);

                        //set spiel name
                        messageWrapper.setSpielName(MessageSpielValue.CASHBACK_REDEMPTION_MERCHANT_NO_MIN_DETAILS);

                        //send notification
                        userMessagingService.transmitNotification(messageWrapper);

                        //throw error
                        throw new InspireNetzException(APIErrorCode.ERR_REDEMPTION_MERCHANT_NO_MOBILE);
                    }

                    //replace the loyalty id with the redemption merchant contact mobile
                    requestParams.put(LmsPcParamKey.MOBILE_NUMBER,redemptionMerchant.getRemContactMobile());

                    cashBackRequest.setRef(redemptionMerchant.getRemContactMobile());
                }

                try {


                    String data = createNameValuePairForApiCall(requestParams);

                    APIResponseObject retData = integrationService.placeRestJSONPostAPICall(loadWalletUrl, data);

                    if(retData.getStatus().toString().equals(APIResponseStatus.success.toString())){


                        //check the return data's result code status
                        Map<String ,String> result = (Map<String, String>) retData.getData();

                        // Check the response and send the sms
                        if ( result == null || result.get("status").equalsIgnoreCase("failed") || result.get("status").equalsIgnoreCase("error") ) {

                            cashBackRequest.setValid(false);

                        } else {

                            cashBackRequest.setValid(true);
                        }

                    } else {

                        cashBackRequest.setValid(false);

                    }

                }catch(Exception e){

                    cashBackRequest.setValid(false);

                    //call method to make the settlement as bank payment
                    addSettlementEntry(cashBackRequest, redemptionMerchant);

                    throw new InspireNetzException(APIErrorCode.ERR_LOAD_WALLET_FAILED);

                }


            }  else {

                cashBackRequest.setValid(true);

                if(redemptionMerchant.getRemSettlementLevel() == MerchantSettlementLevel.MERCHANT_LEVEL){

                    cashBackRequest.setRef(redemptionMerchant.getRemContactMobile());

                }


            }

            //call method to make the settlement as bank payment
            addSettlementEntry(cashBackRequest, redemptionMerchant);


        } else {

            log.error("doMerchantSettlement : Redemption merchant is null, settlement failed");

            //add a customer activity for the failed request
            customerActivityService.logActivity(cashBackRequest.getLoyaltyId(), CustomerActivityType.CASHBACK,"Cashback failed, contact details missing for merchant",cashBackRequest.getMerchantNo(),cuaParams);

            //set spiel name
            messageWrapper.setSpielName(MessageSpielValue.CASHBACK_INVALID_REQUEST);

            //send notification
            userMessagingService.transmitNotification(messageWrapper);

            //throw exception
            throw new InspireNetzException(APIErrorCode.ERR_MERCHANT_SETTLEMENT_FAILED);

        }

        //return cash back request
        return cashBackRequest;
    }

    private void addSettlementEntry(CashBackRequest cashBackRequest, RedemptionMerchant redemptionMerchant) throws InspireNetzException {

        //get merchant settlement object
        MerchantSettlement merchantSettlement = new MerchantSettlement();

        //set internal reference as the redemption id
        merchantSettlement.setMesInternalRef(cashBackRequest.getRdmId()+"");

        //if merchant is of type web service , set settlement type as bank payment
        if(redemptionMerchant.getRemType() == RedemptionMerchantType.WEBSERVICE){

            cashBackRequest.setSettlementType(MerchantSettlementType.BANK_PAYMENT);
        }

        //if settlement type is otjer than bank payment set isSettled flag as false
        if(cashBackRequest.getSettlementType() == MerchantSettlementType.BANK_PAYMENT){

            //set isSettled flag as no if type is bank
            merchantSettlement.setMesIsSettled(MerchantSettlementStatus.NOT_SETTLED);

        } else if(cashBackRequest.isValid() && cashBackRequest.getSettlementType() ==MerchantSettlementType.LOAD_WALLET){

            //set is settled flag as true
            merchantSettlement.setMesIsSettled(MerchantSettlementStatus.SETTLED);

        } else {

            //set is settled flag as true
            merchantSettlement.setMesIsSettled(MerchantSettlementStatus.SETTLEMENT_FAILED);
        }

        //if settlement level is merchant , replace the ref field in cashback request
        if(redemptionMerchant.getRemSettlementLevel() == MerchantSettlementLevel.MERCHANT_LEVEL || redemptionMerchant.getRemType() == RedemptionMerchantType.WEBSERVICE){

            //set redemption merchant contact as settlement reference
            merchantSettlement.setMesSettlementRef(redemptionMerchant.getRemContactMobile());

            //if the settlement is merchant level , set location as 0
            merchantSettlement.setMesLocation(0L);

        }  else {

            //set cashback request ref as settlement reference
            merchantSettlement.setMesSettlementRef(cashBackRequest.getRef());

            //get the redemption merchant user
            User user = userService.findByUsrLoginId(cashBackRequest.getRef());

            //set settlement location as user's location
            merchantSettlement.setMesLocation(user.getUsrLocation());

        }

        //set settlement loyalty id as the requestor loyalty id
        merchantSettlement.setMesLoyaltyId(cashBackRequest.getLoyaltyId());
        merchantSettlement.setMesVendorNo(cashBackRequest.getRedemptionMerchant().getRemNo());
        merchantSettlement.setMesMerchantNo(cashBackRequest.getMerchantNo());

        merchantSettlement.setMesSettlementType(redemptionMerchant.getRemSettlementType());

        merchantSettlement.setMesAmount(cashBackRequest.getAmount());
        merchantSettlement.setMesPoints(cashBackRequest.getRedeemQty());
        merchantSettlement.setMesDate(new Date(System.currentTimeMillis()));

        //save the settlement details
        merchantSettlement = merchantSettlementService.validateAndSaveMerchantSettlement(merchantSettlement);

        //if settlement id is null after save operation , saving is failed
        if(merchantSettlement.getMesId() != null){

            log.info("addSettlementEntry : Settlement added successfully"+merchantSettlement);

        }

    }

    /*

     This method will be placing the post api call, which will read the post api call detail from the
     cashbackconfig.xml file and the parameters in cashback request will be added as the request paramters
     for the api call

     */
    private CashBackRequest makePostDebitApiCall(CashBackRequest cashBackRequest) throws InspireNetzException {

        //get the validation properties for the cash back type
        String apiUrl = cashBackConfigHashMap.get(cashBackRequest.getCashBackType().toLowerCase()).getPostapi();

        if(apiUrl == null){

            cashBackRequest.setValid(true);

            return cashBackRequest;
        }

        //set valid flag to true
        boolean isValid = true;

        //get map from the cashback object
        Map<String,String> requestParams = getParameterMapForApi(cashBackRequest);

        String data = createNameValuePairForApiCall(requestParams);

        String url = environment.getProperty(apiUrl);

        //place the pre api call
        APIResponseObject response = integrationService.placeRestJSONPostAPICall(url, data);

        log.info("makePostDebitApiCall : Response :"+response);

        // Check if the outer response is successful
        if( response.getStatus().toString().equals(APIResponseStatus.success.toString())){

            Map<String,String > result = (Map<String, String>) response.getData();

            // Check if the result is success
            if ( result.get("status").equalsIgnoreCase("success")) {

                //set valid flag to true
                cashBackRequest.setValid(true);

                //set response data to cashback request
                if(result.get("reference") != null){

                    cashBackRequest.setData(result.get("reference"));
                }

            } else {

                //if result returned validity flag as false, set valid to false
                cashBackRequest.setValid(false);

                return cashBackRequest;
            }


        } else {

            //if status is failed, set valid flag as failed
            cashBackRequest.setValid(false);

            log.error("makePostDebitApiCall : Post call failed" + response);

            //return cashBackRequest
            return cashBackRequest;
        }


        //return cashBackRequest
        return cashBackRequest;
    }

    private Map<String, String> getParameterMapForApi(CashBackRequest cashBackRequest) {

        Map<String,String> postParams = new HashMap<>(0);

        postParams.put(LmsPcParamKey.SERVICE_ACCOUNT_NO,cashBackRequest.getRef());

        postParams.put(LmsPcParamKey.REFERENCE,cashBackRequest.getTrackingId());

        postParams.put(LmsPcParamKey.AMOUNT,cashBackRequest.getAmount().toString());

        postParams.put(LmsPcParamKey.CUSTOMER_LOYALTY_ID,cashBackRequest.getLoyaltyId());

        postParams.put(LmsPcParamKey.MOBILE_NUMBER,cashBackRequest.getRef());

        postParams.put(LmsPcParamKey.TRACKING_ID,cashBackRequest.getTrackingId());

        String prodCategory = environment.getProperty("sku.category.bro-category-code");

        Customer customer = customerService.findByCusLoyaltyIdAndCusMerchantNo(cashBackRequest.getLoyaltyId(), cashBackRequest.getMerchantNo());

        List<CustomerSubscription> customerSubscription = customerSubscriptionService.findByCsuCustomerNo(customer.getCusCustomerNo());

        String productCode = customerSubscription.get(0).getCsuProductCode();

        //get the merchant location for sun customer
        MerchantLocation merchantLocation = merchantLocationService.findByMelMerchantNoAndMelLocation(1l,"Sun");

        String cusLocation = "";

        if(customer.getCusLocation().intValue() == merchantLocation.getMelId()){

            cusLocation = merchantLocation.getMelLocation();
        }

        postParams.put(LmsPcParamKey.REFERENCE_FIELD1,cusLocation);

        postParams.put(LmsPcParamKey.REFERENCE_FIELD2,customerSubscription.get(0).getCsuProductCode());

        return postParams;
    }

    private int getPaymentType(String loyaltyId, Long merchantNo) {

        String prodCategory = environment.getProperty("sku.category.bro-category-code");

        Customer customer = customerService.findByCusLoyaltyIdAndCusMerchantNo(loyaltyId,merchantNo);

        List<CustomerSubscription> customerSubscription = customerSubscriptionService.findByCsuCustomerNo(customer.getCusCustomerNo());

        String productCode = customerSubscription.get(0).getCsuProductCode();

        Product product = productService.findByPrdMerchantNoAndPrdCode(merchantNo,productCode);

        //get the merchant location for sun customer
        MerchantLocation merchantLocation = merchantLocationService.findByMelMerchantNoAndMelLocation(1l,"Sun");

        if(product != null ){

            if(product.getPrdLocation() == merchantLocation.getMelId() ){

                return 3;
            }

            else if(product.getPrdCategory3() == null || !product.getPrdCategory3().equals(prodCategory)){

                 return 2;

            } else {

                return 1;
            }

        }

        return 2;
    }


    /**
     *
     * Method will do the point debiting from the requested customer.
     * Point debiting happens based on the cashback request object
     *
     * @param cashBackRequest
     * @return
     * @throws com.inspirenetz.api.rest.exception.InspireNetzException
     */
    private boolean debitPointsForCashBack(CashBackRequest cashBackRequest) throws InspireNetzException {

        APIErrorCode apiErrorCode = null;

        //set the params for activity
        String cuaParams = cashBackRequest.getCashBackType()+"#"+cashBackRequest.getRedeemQty()+"#"+cashBackRequest.getChannel();

        //get point deduct data from cashback request
        PointDeductData pointDeductData = getPointDeductDataFromCashBackRequest(cashBackRequest);

        boolean isDebit = false;

        try {

            // Now debit the points from source account
            isDebit = loyaltyEngineService.deductPoints(pointDeductData);

        } catch (InspireNetzException ex){

            apiErrorCode = ex.getErrorCode();

        } catch (Exception ex){

            log.error("Exception during point debiting "+pointDeductData);
        }

        // Check if the debit is successful
        if( !isDebit ) {

            // Log the information
            log.info(" doCashBack -> Debiting failed ");

            // Create the messageWrapper
            MessageWrapper messageWrapper = new MessageWrapper();
            messageWrapper.setParams(new HashMap<String, String>(0));
            messageWrapper.setLoyaltyId(cashBackRequest.getLoyaltyId());
            messageWrapper.setSpielName(MessageSpielValue.CASHBACK_DEBIT_FAILED);
            userMessagingService.transmitNotification(messageWrapper);

            //if exception occured is insufficient point , log activity
            if(apiErrorCode.equals(APIErrorCode.ERR_INSUFFICIENT_POINT_BALANCE)){

                //log the activity
                customerActivityService.logActivity(cashBackRequest.getLoyaltyId(), CustomerActivityType.CASHBACK,"Failed , insufficient point balance ",cashBackRequest.getMerchantNo(),cuaParams);

            } else {

                //log the activity
                customerActivityService.logActivity(cashBackRequest.getLoyaltyId(), CustomerActivityType.CASHBACK,"Failed , point debiting failed ",cashBackRequest.getMerchantNo(),cuaParams);

            }

            // Throw exception
            throw new InspireNetzException(APIErrorCode.ERR_OPERATION_FAILED);

        }

        return isDebit;
    }

    private PointDeductData getPointDeductDataFromCashBackRequest(CashBackRequest cashBackRequest) {

        //get reward qty
        RewardCurrency rewardCurrency = rewardCurrencyService.findByRwdCurrencyId(cashBackRequest.getRwdCurrencyId());

        // Create the PointDeductData object
        PointDeductData pointDeductData = new PointDeductData();

        // Set the fields
        pointDeductData.setMerchantNo(cashBackRequest.getMerchantNo());

        pointDeductData.setRedeemQty(cashBackRequest.getRedeemQty());

        pointDeductData.setRwdCurrencyId(rewardCurrency.getRwdCurrencyId());

        pointDeductData.setRwdCurrencyName(rewardCurrency.getRwdCurrencyName());

        pointDeductData.setLoyaltyId(cashBackRequest.getDebitLoyaltyId());

        pointDeductData.setTxnDate(new Date(new java.util.Date().getTime()));

        pointDeductData.setTxnType(TransactionType.CASHBACK);

        pointDeductData.setExternalRef(cashBackRequest.getRef());

        pointDeductData.setTxnAmount(cashBackRequest.getAmount());

        pointDeductData.setInternalRef(cashBackRequest.getRdmId().toString());

        return pointDeductData;
    }

    private CashBackRequest makePreValidationApiCall(CashBackRequest cashBackRequest) throws InspireNetzException {

        //get the validation properties for the cash back type
        String apiUrl = cashBackConfigHashMap.get(cashBackRequest.getCashBackType().toLowerCase()).getPreapi();

        if(apiUrl == null){

            cashBackRequest.setValid(true);

            return cashBackRequest;
        }
        //set valid flag to true
        boolean isValid = true;

        //get map from the cashback object
        Map<String,String> requestParams = getParameterMapForApiCall(cashBackRequest);

        String data = createNameValuePairForApiCall(requestParams);

        String url = environment.getProperty(apiUrl);

        //place the pre api call
        APIResponseObject response = integrationService.placeRestJSONPostAPICall(url, data);

        // Check if the outer response is successful
        if( response.getStatus().toString().equals(APIResponseStatus.success.toString())){

            Map<String,String > result = (Map<String, String>) response.getData();

            // Check if the result is success
            if ( result.get("isValid").equalsIgnoreCase("success")) {

                //set valid flag to true
                cashBackRequest.setValid(true);

                //if data in the response is not null , set it to cashBackRequest
                if(result.get("data") != null){

                    cashBackRequest.setData(result.get("data"));
                }

            } else {

                //if not valid, set valid status as false
                cashBackRequest.setValid(false);

                //return cashBackRequest object
                return cashBackRequest;
            }


        } else {

            //if status is failed, set valid flag as false
            cashBackRequest.setValid(false);

            //return cashBackRequest object
            return cashBackRequest;
        }


        //return validity flag
        return cashBackRequest;
    }

    private String createNameValuePairForApiCall(Map<String,String> requestParams) {

        try{
            String data =  mapper.writeValueAsString(requestParams);

            return data;


        } catch (Exception e){


        }

        return null;

    }

    private Map<String, String> getParameterMapForApiCall(CashBackRequest cashBackRequest) {

        //map holding the request key-value pair
        Map<String,String> requestParameters = new HashMap<>(0);

        //convert th cashback object to map
        requestParameters = mapper.convertValue(cashBackRequest,Map.class);

        //return the map
        return requestParameters;

    }

    private CashBackRequest doValidations(CashBackRequest cashBackRequest) throws InspireNetzException {

        //get the validation properties for the cash back type
        List<String> validationList = cashBackConfigHashMap.get(cashBackRequest.getCashBackType().toLowerCase()).getValidations();

        validationList.add("VDL_AMOUNT_VALIDATION");

        validationList.add("VDL_LINKED_VALIDATION");

        MessageWrapper messageWrapper = setMessageWrapperParameters(cashBackRequest);

        //get the sms params
        HashMap<String,String> smsParams = getSMSParamsForCashBack(cashBackRequest);

        smsParams.put("#points", generalUtils.getFormattedValue(cashBackRequest.getRedeemQty()));

        //set sms params
        messageWrapper.setParams(smsParams);

        String cuaParams = cashBackRequest.getCashBackType()+"#"+cashBackRequest.getRedeemQty()+"#"+cashBackRequest.getChannel();

        //set valid flag to true
        boolean isValid = true;

        //get validation request for request validations
        ValidationRequest validationRequest = getValidationRequestObject(cashBackRequest);

        //get a validation response object
        ValidationResponse validationResponse = new ValidationResponse();

        //for each key read from the file , do validations
        for(String validationKey : validationList){

            //get the validation type object
            ValidationService validationService = getValidationObject(validationKey);

            if(validationService == null){

                log.error("doCashBack : Invalid validation type for cash back request : "+cashBackRequest);

                //add a customer activity for the failed request
                customerActivityService.logActivity(cashBackRequest.getLoyaltyId(), CustomerActivityType.CASHBACK,"Cashback failed, invalid validation type",cashBackRequest.getMerchantNo(),cuaParams);

                //set spiel name
                messageWrapper.setSpielName(MessageSpielValue.CASHBACK_FAILED_SYSTEM_ERROR);

                //send notification
                userMessagingService.transmitNotification(messageWrapper);


                throw new InspireNetzException(APIErrorCode.ERR_CASHBACK_INVALID_VALIDATION_TYPE);

            }
            //check the request validity
             validationResponse = validationService.isValid(validationRequest);

            //if request is not valid , throw error
            if( !validationResponse.isValid()) {

                log.error("doCashBack : Cashback request is not valid, Remarks :"+validationResponse.getValidationRemarks());

                //add a customer activity for the failed request
                customerActivityService.logActivity(cashBackRequest.getLoyaltyId(), CustomerActivityType.CASHBACK,validationResponse.getValidationRemarks(),cashBackRequest.getMerchantNo(),cuaParams);

                //set spiel name
                messageWrapper.setSpielName(MessageSpielValue.CASHBACK_FAILED_VALIDATION_ERROR);

                if(validationResponse.getSpielName() != null && !validationResponse.getSpielName().equals("")){

                    messageWrapper.setSpielName(validationResponse.getSpielName());
                }

                //send notification
                userMessagingService.transmitNotification(messageWrapper);

                throw new InspireNetzException(validationResponse.getApiErrorCode());
            }

            //if validation type is linked account cash back validity check the elgiibility status
            if(validationKey.equals("VDL_LINKED_VALIDATION")){

                //if account is not eligible return false
                if(validationResponse.getEligibilityStatus() == RequestEligibilityStatus.INELIGIBLE){

                    //log the error
                    log.error("doCashBack : Customer not eligible to do cashback, linked account");

                    //set is valid to false
                    cashBackRequest.setValid(false);

                    return cashBackRequest;

                } else if(validationResponse.getEligibilityStatus() == RequestEligibilityStatus.APPROVAL_NEEDED){

                    //process the approval flow if the request is of kind , approval needed
                    cashBackRequest = processCashBackApproval(validationResponse,cashBackRequest);

                    //if cash back is not allowed, check party approval flow
                    if(!cashBackRequest.isCashBackAllowed()){

                        //if the csahback approval status is false, request is rejected
                        if(!cashBackRequest.isRedemptionApprovalStatus() ){

                            //log error
                            log.error("redeemSingleCatalogueItem: Redemption request rejected by approver");

                            //set redemption status to approval rejected equest
                            redemptionService.updateRedemptionStatus(cashBackRequest.getRdmId(), RedemptionStatus.RDM_STATUS_REJECTED);

                            //add a customer activity for the failed request
                            customerActivityService.logActivity(cashBackRequest.getLoyaltyId(), CustomerActivityType.CASHBACK,"Cashback failed, request rejected by approver",cashBackRequest.getMerchantNo(),cuaParams);

                            //set spiel name
                            messageWrapper.setSpielName(MessageSpielValue.CASHBACK_FAILED_REQUEST_REJECTED);

                            //send notification
                            userMessagingService.transmitNotification(messageWrapper);

                            //throw error
                            throw new InspireNetzException(APIErrorCode.ERR_REDEMPTION_REQUEST_REJECTED);

                        } else {

                            //log error
                            log.error("redeemSingleCatalogueItem: Redemption not allowed by approver , waiting for approval");

                            //set redemption status to approval rejected equest
                            redemptionService.updateRedemptionStatus(cashBackRequest.getRdmId(), RedemptionStatus.RDM_STATUS_APPROVAL_WAITING);

                            cashBackRequest.setDebitCustomerNo(validationResponse.getDebitCustomerNo());

                            //add a customer activity for the failed request
                            customerActivityService.logActivity(cashBackRequest.getLoyaltyId(), CustomerActivityType.CASHBACK,"Cashback request is pending, waiting for approval from linked account",cashBackRequest.getMerchantNo(),cuaParams);

                            //set spiel name
                            messageWrapper.setSpielName(MessageSpielValue.CASHBACK_WAITING_FOR_APPROVAL);

                            //send notification
                            userMessagingService.transmitNotification(messageWrapper);

                            //throw error
                            throw new InspireNetzException(APIErrorCode.ERR_REDEMPTION_WAITING_FOR_APPROVAL);

                        }

                    } else {

                        //set is valid to false
                        cashBackRequest.setValid(true);

                        cashBackRequest.setDebitCustomerNo(validationResponse.getDebitCustomerNo());

                        cashBackRequest.setDebitLoyaltyId(validationResponse.getDebitLoyaltyId());

                        //return the cashback request object
                        return cashBackRequest;

                    }


                } else {

                    //if no approval is needed process the request
                    cashBackRequest = addRedemptionEntry(validationResponse, cashBackRequest);

                    //set is valid to false
                    cashBackRequest.setValid(true);

                    //set debit loyalty id
                    cashBackRequest.setDebitLoyaltyId(validationRequest.getCustomer().getCusLoyaltyId());

                    //set debit customer no
                    cashBackRequest.setDebitCustomerNo(validationRequest.getCustomer().getCusCustomerNo());

                    //return the cashback request object
                    return cashBackRequest;

                }

            }

            if(validationKey.equals("VDL_REDEMPTION_MERCHANT_VALIDATION")){

                //set redemption merchant returned from validation stage
                cashBackRequest.setRedemptionMerchant(validationResponse.getRedemptionMerchant());

            }

        }

        return cashBackRequest;
    }


    private CashBackRequest processCashBackApproval(ValidationResponse validationResponse,CashBackRequest cashBackRequest) throws InspireNetzException {

        //get the approver customer
        Customer approver = customerService.findByCusCustomerNo(validationResponse.getApproverCustomerNo());

        //get the requestor details
        Customer requestor = customerService.findByCusLoyaltyIdAndCusMerchantNo(cashBackRequest.getLoyaltyId(), cashBackRequest.getMerchantNo());

        //if redemption id is 0 , then add a new redemption request and add a party approval request
        if(cashBackRequest.getRdmId() == null || cashBackRequest.getRdmId() == 0){

            log.info("processCashBackApproval : New redemption request received , adding entry to redemption table");

            //call the redemption creation method
            cashBackRequest = addRedemptionEntry(validationResponse,cashBackRequest);

            //send party approval
            partyApprovalService.sendApproval(requestor,approver,cashBackRequest.getRdmId(), PartyApprovalType.PARTY_APPROVAL_CASHBACK, cashBackRequest.getMerchantIdentifier(),generalUtils.getFormattedValue(cashBackRequest.getAmount()));

            //
            log.info("processCashBackApproval : Approval request sent to approver account");

            //set redemption allowed xto false
            cashBackRequest.setCashBackAllowed(false);

        } else{

            //check whether the
            boolean isRedemptionAllowed = isPartyApprovedCashBack(cashBackRequest, approver, requestor);

            if(isRedemptionAllowed){

                log.info("processCashBackApproval : Redemption is approved by approver");

                //set redemption allowed to true
                cashBackRequest.setCashBackAllowed(true);

            } else {

                log.info("processCashBackApproval : Redemption request rejected by primary");

                //set redemption allowed to false
                cashBackRequest.setCashBackAllowed(false);

            }

        }

        return cashBackRequest;
    }

    private boolean isPartyApprovedCashBack(CashBackRequest cashBackRequest, Customer approver, Customer requestor) {

        // Check if there is a entry in the LinkingApproval table
        PartyApproval partyApproval = partyApprovalService.getExistingPartyApproval(approver,requestor, PartyApprovalType.PARTY_APPROVAL_CASHBACK, cashBackRequest.getRdmId());

        // If the partyApproval is not found, then return false
        if ( partyApproval == null) {

            // Log the information
            log.info("isPartyApproved -> Party has not approved linking");

            // return false
            return false;

        } else {

            //return the initial status , true
            return cashBackRequest.isRedemptionApprovalStatus();
        }


    }

    private CashBackRequest addRedemptionEntry(ValidationResponse validationResponse,CashBackRequest cashBackRequest) {

        // Create the Redemption object
        Redemption redemption = new Redemption();

        // Set the fields in the Redemption
        //
        // Set the rdmMerchatNo
        redemption.setRdmMerchantNo(cashBackRequest.getMerchantNo());

        // Set the status
        redemption.setRdmStatus(RedemptionStatus.RDM_STATUS_FAILED);

        // Set the type
        redemption.setRdmType(RedemptionType.PAY);

        // Set the reward currency id
        redemption.setRdmRewardCurrencyId(cashBackRequest.getRwdCurrencyId());

        // set the reward quantity
        redemption.setRdmRewardCurrencyQty(cashBackRequest.getRedeemQty());

        // set the product code
        redemption.setRdmProductCode(cashBackRequest.getMerchantIdentifier());

        // set quantity
        redemption.setRdmQty(1);

        // Set the loyalty id
        redemption.setRdmLoyaltyId(cashBackRequest.getLoyaltyId());

        // Set the delivery ind to be 0
        redemption.setRdmDeliveryInd(0);

        // Set the totalCashAmount
        redemption.setRdmCashbackAmount(cashBackRequest.getAmount());

        // Set the date
        redemption.setRdmDate(new Date(new java.util.Date().getTime()));

        // Set the time
        redemption.setRdmTime(new Time(new java.util.Date().getTime()));

        String trackingId = generalUtils.getUniqueId(cashBackRequest.getLoyaltyId());

        // Set the tracking id
        redemption.setRdmUniqueBatchTrackingId(trackingId);

        // Set the cashPaymentStatus
        redemption.setRdmCashPaymentStatus(PaymentStatus.PAYMENT_STATUS_PAID);

        // Set the contact number
        redemption.setRdmContactNumber("");

        redemption.setRdmIdenitifier(cashBackRequest.getCashBackType());

        //set the redemption channel
        redemption.setRdmChannel(cashBackRequest.getChannel());

        RedemptionMerchant redemptionMerchant = cashBackRequest.getRedemptionMerchant();

        if(redemptionMerchant.getRemSettlementLevel() == MerchantSettlementLevel.MERCHANT_LEVEL){

            //set destLoyaltyId as the reference of cashback request
            redemption.setRdmDestLoyaltyId(redemptionMerchant.getRemContactMobile());

        } else {

            //set destLoyaltyId as the reference of cashback request
            redemption.setRdmDestLoyaltyId(cashBackRequest.getRef());
        }

        if(!cashBackRequest.getCashBackType().equals("default")){

            redemption.setRdmRef(cashBackRequest.getRef());
        }

        //set user no to 0
        redemption.setRdmUserNo(0L);

        // Insert the redemption
        redemption = redemptionService.saveRedemption(redemption);

        //set redemption id to cashback request object
        cashBackRequest.setRdmId(redemption.getRdmId());

        //set tracking id to cashback request object
        cashBackRequest.setTrackingId(redemption.getRdmUniqueBatchTrackingId());

        //return cashback request
        return cashBackRequest;

    }

    private ValidationRequest getValidationRequestObject(CashBackRequest cashBackRequest) {

        //get new validation request object
        ValidationRequest validationRequest = new ValidationRequest();

        //set validation request field
        validationRequest.setLoyaltyId(cashBackRequest.getLoyaltyId());
        validationRequest.setAmount(cashBackRequest.getAmount());
        validationRequest.setCashBackType(cashBackRequest.getCashBackType().toLowerCase());
        validationRequest.setMerchantIdentifier(cashBackRequest.getMerchantIdentifier());
        validationRequest.setPin(cashBackRequest.getPin());
        validationRequest.setRef(cashBackRequest.getRef());
        validationRequest.setApprovalRequest(cashBackRequest.isApprovalRequest());
        validationRequest.setRwdCurrencyId(cashBackRequest.getRwdCurrencyId());
        validationRequest.setMerchantNo(cashBackRequest.getMerchantNo());
        validationRequest.setOtpCode(cashBackRequest.getOtpCode());
        validationRequest.setRedeemQty(cashBackRequest.getRedeemQty());
        validationRequest.setRedemptionMerchantNo(cashBackRequest.getRedemptionMerchant().getRemNo());

        //get the customer details
        Customer customer = customerService.findByCusLoyaltyIdAndCusMerchantNo(cashBackRequest.getLoyaltyId(),cashBackRequest.getMerchantNo());

        //set the customer object to validate
        validationRequest.setCustomer(customer);

        //return the object
        return validationRequest;
    }

    public ValidationService getValidationObject(String validationKey){

        //get the validation class name for the validation key
        String validationClassName = environment.getProperty(validationKey);

        //initialize validation service instance
        ValidationService validationService = null;

        //switch to get the validationService object
        switch (validationClassName){

            case ValidationTypes.CUSTOMER_VALIDATION                : validationService =  new CustomerValidation();
                                                                      break;

            case ValidationTypes.BALANCE_VALIDATION                 : validationService =  new BalanceValidation(linkedLoyaltyService,rewardCurrencyService,customerRewardBalanceService,accountBundlingSettingService,accountBundlingUtils,customerService,merchantSettingService,cardMasterService);
                                                                      break;

            case ValidationTypes.LINKED_ACCOUNT_APPROVAL_VALIDATION : validationService =  new LinkedAccountApprovalValidation(linkedLoyaltyService,rewardCurrencyService,customerRewardBalanceService,accountBundlingSettingService,accountBundlingUtils);
                                                                      break;

            case ValidationTypes.REDEMPTION_MERCHANT_VALIDATION     : validationService =  new RedemptionMerchantValidation(redemptionMerchantService,userService);
                                                                      break;

            case ValidationTypes.PIN_VALIDATION                     : validationService =  new PinValidation(userService,securityService,merchantService);
                                                                      break;

            case ValidationTypes.ACCOUNT_NO_VALIDATION              : validationService = new AccountNoValidation();
                                                                      break;


            case ValidationTypes.VDL_AMOUNT_VALIDATION              : validationService = new NaturalNumberValidation();
                                                                      break;

            case ValidationTypes.VDL_OTP_VALIDATION                 : validationService = new OTPValidation(oneTimePasswordService);
                                                                        break;

            case ValidationTypes.VDL_SETTLEMENT_THRESHOLD_VALIDATION: validationService = new SettlementThresholdValidation(merchantSettlementService,merchantRedemptionPartnerService);
                                                                        break;

        }


        //return the validation service
        return validationService;

    }
}
