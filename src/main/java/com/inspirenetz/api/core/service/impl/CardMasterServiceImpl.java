package com.inspirenetz.api.core.service.impl;

import com.inspirenetz.api.core.dictionary.*;
import com.inspirenetz.api.core.domain.*;
import com.inspirenetz.api.core.domain.validator.CardMasterValidator;
import com.inspirenetz.api.core.repository.BaseRepository;
import com.inspirenetz.api.core.repository.CardMasterRepository;
import com.inspirenetz.api.core.service.*;
import com.inspirenetz.api.rest.exception.InspireNetzException;
import com.inspirenetz.api.rest.resource.CardMasterResource;
import com.inspirenetz.api.rest.resource.CustomerProfileResource;
import com.inspirenetz.api.rest.resource.CustomerResource;
import com.inspirenetz.api.util.*;
import org.antlr.grammar.v3.ANTLRv3Parser;
import org.dozer.Mapper;
import org.drools.lang.DRLExpressions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.BeanPropertyBindingResult;
import reactor.core.Reactor;
import reactor.event.Event;
import reactor.spring.annotation.Selector;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.Date;


/**
 * Created by sandheepgr on 28/3/14.
 */
@Service
public class CardMasterServiceImpl extends BaseServiceImpl<CardMaster> implements CardMasterService {


    private static Logger log = LoggerFactory.getLogger(CardMasterServiceImpl.class);


    @Autowired
    CardMasterRepository cardMasterRepository;

    @Autowired
    AccountBundlingUtils accountBundlingUtils;

    @Autowired
    TierService tierService;

    @Autowired
    GeneralUtils generalUtils;

    @Autowired
    CustomerActivityService customerActivityService;

    @Autowired
    RedemptionService redemptionService;

    @Autowired
    RewardCurrencyService rewardCurrencyService;

    @Autowired
    CustomerRewardBalanceService customerRewardBalanceService;

    @Autowired
    UserMessagingService userMessagingService;

    @Autowired
    private CardTransactionService cardTransactionService;

    @Autowired
    private MerchantSettingService merchantSettingService;

    @Autowired
    private  MerchantLocationService merchantLocationService;

    @Autowired
    OneTimePasswordService oneTimePasswordService;

    @Autowired
    private CardTypeService cardTypeService;

    @Autowired
    private SettingService settingService;

    @Autowired
    private CustomerService customerService;

    @Autowired
    AuthSessionUtils authSessionUtils;

    @Autowired
    UserService userService;

    @Autowired
    MerchantService merchantService;

    @Autowired
    private Mapper mapper;

    @Autowired
    private CardNumberInfoService cardNumberInfoService;

    @Autowired
    private DataValidationUtils dataValidationUtils;

    @Autowired
    private LoyaltyEngineService loyaltyEngineService;

    @Autowired
    private SaleService saleService;

    @Autowired
    private TransactionService transactionService;

    @Autowired
    private CustomerRewardExpiryService customerRewardExpiryService;
    @Autowired
    private Reactor eventReactor;

    public CardMasterServiceImpl() {

        super(CardMaster.class);

    }


    @Override
    protected BaseRepository<CardMaster,Long> getDao() {
        return cardMasterRepository;
    }


    @Override
    public CardMaster findByCrmId(Long crmId) {

        // Get the CardMaster for id
        CardMaster cardMaster = cardMasterRepository.findByCrmId(crmId);

        // Return the cardmaster
        return cardMaster;

    }

    @Override
    public CardMaster findByCrmMerchantNoAndCrmCardNo(Long crmMerchantNo, String crmCardNo) {

        // Get the CardMaster for the merchantno and card no
        CardMaster cardMaster = cardMasterRepository.findByCrmMerchantNoAndCrmCardNo(crmMerchantNo,crmCardNo);

        // Return the cardMaster
        return cardMaster;

    }

    @Override
    public Page<CardMaster> findByCrmMerchantNo(Long crmMerchantNo, Pageable pageable) {

        // Get the CardMaster Page
        Page<CardMaster> cardMasterPage = cardMasterRepository.findByCrmMerchantNo(crmMerchantNo,pageable);

        // return the page
        return cardMasterPage;

    }

    @Override
    public Page<CardMaster> findByCrmMerchantNoAndCrmCardNoLike(Long crmMerchantNo, String crmCardNo, Pageable pageable) {

        // Get the CardMasterPage
        Page<CardMaster> cardMasterPage = cardMasterRepository.findByCrmMerchantNoAndCrmCardNoLike(crmMerchantNo,crmCardNo,pageable);

        // Return the CardMasterPage
        return cardMasterPage;

    }

    @Override
    public Page<CardMaster> findByCrmMerchantNoAndCrmMobileLike(Long crmMerchantNo, String crmMobile, Pageable pageable) {

        // Get the CardMasterPage
        Page<CardMaster> cardMasterPage = cardMasterRepository.findByCrmMerchantNoAndCrmMobileLike(crmMerchantNo,crmMobile,pageable);

        // Return the page
        return cardMasterPage;

    }

    @Override
    public Page<CardMaster> findByCrmMerchantNoAndCrmLoyaltyIdLike(Long crmMerchantNo, String crmLoyaltyId, Pageable pageable) {

        // Get the CardMasterPage for the loyatly id
        Page<CardMaster> cardMasterPage = cardMasterRepository.findByCrmMerchantNoAndCrmLoyaltyIdLike(crmMerchantNo,crmLoyaltyId,pageable);

        // return the page
        return cardMasterPage;

    }

    @Override
    public Page<CardMaster> findByCrmMerchantNoAndCrmCardHolderNameLike(Long crmMerchantNo, String crmCardHolderName, Pageable pageable) {

        // Get the CardMasterPage for the card holder name
        Page<CardMaster> cardMasterPage = cardMasterRepository.findByCrmMerchantNoAndCrmCardHolderNameLike(crmMerchantNo,crmCardHolderName,pageable);

        // Return page
        return cardMasterPage;

    }

    @Override
    public List<CardMaster> listCardsForCustomer(Long crmMerchantNo, String crmMobile, String crmEmailId, String crmLoyaltyId) {

        // Check if the crmMobile is not empty or null, if its not specified, then set it
        // to placeholder vlaue
        if ( crmMobile == null || crmMobile == "" ){

            crmMobile = DBUtils.STRING_IGNORE_COMPARE_FIELD_CONTENT;

        }


        // Check if the crmEmailId is not empty or null, if its not specified, then set it
        // to placeholder vlaue
        if ( crmEmailId == null || crmEmailId == "" ){

            crmEmailId = DBUtils.STRING_IGNORE_COMPARE_FIELD_CONTENT;

        }


        // Check if the crmLoyaltyId is not empty or null, if its not specified, then set it
        // to placeholder vlaue
        if ( crmLoyaltyId == null || crmLoyaltyId == "" ){

            crmLoyaltyId = DBUtils.STRING_IGNORE_COMPARE_FIELD_CONTENT;

        }




        // Get the data
        List<CardMaster> cardMasterList = cardMasterRepository.listCardsForCustomer(crmMerchantNo,crmMobile,crmEmailId,crmLoyaltyId);

        // Return the list
        return cardMasterList;


    }

    @Override
    public boolean isDuplicateCardExisting(CardMaster cardMaster) {

        // Get the cardMaster information
        CardMaster exCardMaster = cardMasterRepository.findByCrmMerchantNoAndCrmCardNo(cardMaster.getCrmMerchantNo(), cardMaster.getCrmCardNo());

        // If the crmId is 0L, then its a new cardMaster so we just need to check if there is ano
        // ther cardMaster code
        if ( cardMaster.getCrmId() == null || cardMaster.getCrmId() == 0L  ) {

            // If the cardMaster is not null, then return true
            if ( exCardMaster != null  ) {

                return true;

            }

        } else {

            // Check if the cardMaster is null
            if ( exCardMaster != null && cardMaster.getCrmId().longValue() != exCardMaster.getCrmId().longValue() ) {

                return true;

            }
        }


        // Return false;
        return false;
    }

    @Override
    public boolean isDuplicateMobileNumberExisting(CardMaster cardMaster) {

        // Get the cardMaster information
        CardMaster exCardMaster = cardMasterRepository.findByCrmMerchantNoAndCrmMobileAndCrmType(cardMaster.getCrmMerchantNo(), cardMaster.getCrmMobile(), cardMaster.getCrmType());

        // If the crmId is 0L, then its a new cardMaster so we just need to check if there is ano
        // ther cardMaster code
        if ( cardMaster.getCrmId() == null || cardMaster.getCrmId() == 0L ) {

            // If the cardMaster is not null, then return true
            if ( exCardMaster != null && cardMaster.getCrmType().longValue() ==exCardMaster.getCrmType().longValue() ) {

                return true;

            }

        } else {

            // Check if the cardMaster is null
            if ( exCardMaster != null && cardMaster.getCrmId().longValue() != exCardMaster.getCrmId().longValue() && exCardMaster.getCrmType().longValue() ==cardMaster.getCrmType().longValue() ) {

                return true;

            }
        }


        // Return false;
        return false;
    }

    @Override
    public boolean isCardBalanceExpired(CardType cardType, CardMaster cardMaster) {


        //check card master is null or not
        if(cardMaster ==null||cardMaster.getCrmId()==null || cardMaster.getCrmId()==0){

            return false;
        }

        //check card type null or  not
        if(cardType ==null){

            return false;

        }

        //check the card alredy expired or not
        boolean isCardExpired =isCardExpired(cardMaster.getCardType(),cardMaster);

        if(isCardExpired){

            //return false
            return false;
        }

        //check the merchant settings for card expired
        boolean isSettingsEnabled =merchantSettingService.isSettingEnabledForMerchant(AdminSettingsConfiguration.MER_CARD_BALANCE_EXPIRY,cardMaster.getCrmMerchantNo());

        //if its not enabled return false
        if(!isSettingsEnabled){

            return false;

        }

        // Check the expiry option for the cardType
        if ( cardType.getCrtBalanceExpiryOption().intValue() == CardTypeExpiryOption.NUM_TXNS ) {

            // Check if the NUM_TXNS on the CardMaster is greater than or equal to
            // the max transactions set by the CardType
            if ( cardMaster.getCrmNumTxns() >= cardType.getCrtBalExpMaxTxn().intValue() ) {

                // Return true
                return true;

            }

        }else if(cardType.getCrtBalanceExpiryOption().intValue() ==CardTypeExpiryOption.DAYS_AFTER_ISSUANCE){

            //get the issued date
            Date date =cardMaster.getCrmIssuedDate();

            //added issue date plus number of days
            Integer afterNumberOfDays =cardType.getCrtBalExpiryDays()==null?0:cardType.getCrtBalExpiryDays();

            //get added date
            Date expiryDate =generalUtils.addDaysToDate(date,afterNumberOfDays.intValue());

            //compare date
            if(generalUtils.clearTimeStamp(expiryDate).compareTo(generalUtils.clearTimeStamp(new Date()))<0){

                //then return true
                return true;

            }


        }else if(cardType.getCrtBalanceExpiryOption().intValue() ==CardTypeExpiryOption.EXPIRY_DATE){

            // Store the date for today
            java.util.Date today = new Date();

            // Compare the cardMaster expiry date with current date
            if ( cardType.getCrtBalExpiryDate().compareTo(today) <= 0 ) {

                // Return true
                return true;

            }

        }else if (cardType.getCrtBalanceExpiryOption().intValue() ==CardTypeExpiryOption.DAYS_AFTER_TOPUP){

            //get the Added day of settings
            Integer addedDay =cardType.getCrtBalTopExpiryDays()==null?0:cardType.getCrtBalTopExpiryDays();

            //get the added time information
            String timeInfo =cardType.getCrtBalTopExpiryTime()==null?"":cardType.getCrtBalTopExpiryTime();

            //define calender
            Calendar calendar =Calendar.getInstance();

            //set date to calendar
            calendar.setTime(cardMaster.getCrmTopupDate());

            //add number of days
            calendar.add(Calendar.DATE, addedDay);

            //check the time is null or not
            if(!timeInfo.equals("")){

                //time is in hh:mm:ss format
                String [] timeParse = timeInfo.split(":");

                //add get hour minute second
                String hour = timeParse[0];

                String minute =timeParse[1];

                String second =timeParse[2];

                //check hour is present
                if (hour !=null && !hour.equals("")){

                    //convert string to integer
                    Integer hour1 =Integer.parseInt(hour);

                    //add hour value into calender
                    calendar.add(Calendar.HOUR,hour1);

                }

                if(minute !=null && !minute.equals("")){

                    //convert string to integer
                    Integer minute1 =Integer.parseInt(minute);

                    //add hour value into calender
                    calendar.add(Calendar.MINUTE,minute1);
                }

                if(second !=null && !second.equals("")){

                    //convert string to integer
                    Integer second1 =Integer.parseInt(second);

                    //add hour value into calender
                    calendar.add(Calendar.SECOND,second1);
                }


            }

            //after adding day we got expiry balance date
            Timestamp timestamp =new Timestamp(calendar.getTime().getTime());

            //get long value of two time stamp
            Timestamp currentDateTime =new Timestamp(new Date().getTime());

            //get two value
            Long expiryTime = timestamp.getTime();

            //get current value
            Long currentTime =currentDateTime.getTime();

            //check the current time greater than expiry time return true
            if (currentTime.longValue() >expiryTime.longValue()){

                //return true
                return true;
            }

        }

        // Return false
        return false;
    }

    @Override
    public void startCardBalanceExpiry()  {

        //get the merchant enable for balance expiry option
        List<MerchantSetting> merchantSettingList =merchantSettingService.getSettingsEnabledMerchant(AdminSettingsConfiguration.MER_CARD_BALANCE_EXPIRY);

        //find the card master information
        for (MerchantSetting merchantSetting:merchantSettingList){

            //find the card information
            List<CardMaster> cardMasterList =findByCrmMerchantNo(merchantSetting.getMesMerchantNo());

            //process the card expiry information
            for (CardMaster cardMaster:cardMasterList){


                //check the card expiry information
                boolean isCardBalanceExpired = isCardBalanceExpired(cardMaster.getCardType(),cardMaster);

                //check card balance is expired or not if its expired we need to set balance zero
                if(isCardBalanceExpired){

                    //process card balance expiry
                    cardBalanceExpiryOperation(cardMaster);

                }

            }
        }


    }



    public CardMaster cardBalanceExpiryOperation(CardMaster cardMaster) {

        //check the card expired or not
        boolean isCardExpired =isCardExpired(cardMaster.getCardType(),cardMaster);

        //if its  return the process
        if(isCardExpired){

            return null;
        }

        //check balance is greter than zero
        if(getTotalCardBalance(cardMaster).doubleValue() <=0.0){


            //return null
            return null;
        }

        // Create the CardTransaction object check the card is expired or not
        CardTransaction cardTransaction =  CardTransactionUtils.getCardTransactionFromCardMaster(cardMaster);

        // Set the transaction amount
        cardTransaction.setCtxTxnAmount(getTotalCardBalance(cardMaster));

        //set card transaction
        cardTransaction.setCtxTxnType(CardTransactionType.BALANCE_EXPIRED);

        // SetR the user no
        cardTransaction.setCtxUserNo(0L);

        // Set the transaction location
        cardTransaction.setCtxLocation(cardMaster.getCrmLocation());

        //balance of the card is set to zero
        cardTransaction.setCtxCardBalance(0.0);

        // Set the reference to reference passed
        cardTransaction.setCtxReference("blnceExpiry_"+generalUtils.clearTimeStamp(new Date())+"");

        // Save the CardTransaction
        cardTransaction = cardTransactionService.saveCardTransaction(cardTransaction);

        // Log the information
       log.info("Process Card Balance Expiry -> CardTransaction object to save -> "+cardTransaction.toString());

        //after that process the card master
        //set card is zero
        cardMaster.setCrmCardBalance(0.0);

        //set crmPromobalance to zero
        cardMaster.setCrmPromoBalance(0.0);

        cardMaster.setCrmBalExpStatus(IndicatorStatus.YES);

        //save the card master balance is zero and added into transaction table
        CardMaster cardMasterBalanceExpiry =this.saveCardMaster(cardMaster);

        return cardMasterBalanceExpiry;

    }

    @Override
    @Scheduled(cron = "${scheduled.cardexpiry}")
    public void processCardExpiry() {

        log.info("Card balance expiry process is started with date and time :"+new Date()+"");

        startCardExpiry();
    }

    @Override
    public void startCardExpiry()  {



        //find the card information
        List<CardMaster> cardMasterList =cardMasterRepository.findByCrmCardStatusNot(CardMasterStatus.EXPIRED);

        //process the card expiry information
        for (CardMaster cardMaster:cardMasterList){


            //check the card expiry information
            boolean isCardExpired = isCardExpired(cardMaster.getCardType(),cardMaster);

            //check card balance is expired or not if its expired we need to set balance zero
            if(isCardExpired){


                //process card balance expiry
                cardExpiryOperation(cardMaster);

            }

        }

    }


    @Override
    @Transactional(rollbackFor = Exception.class)
    public CardMaster cardExpiryOperation(CardMaster cardMaster) {


        // Create the CardTransaction object check the card is expired or not
/*
        CardTransaction cardTransaction =  CardTransactionUtils.getCardTransactionFromCardMaster(cardMaster);

        // Set the transaction amount
        cardTransaction.setCtxTxnAmount(0.0);

        //set card transaction
        cardTransaction.setCtxTxnType(CardTransactionType.CARD_EXPIRED);

        // SetR the user no
        cardTransaction.setCtxUserNo(0L);

        // Set the transaction location
        cardTransaction.setCtxLocation(cardMaster.getCrmLocation());

        //balance of the card is set to zero
        cardTransaction.setCtxCardBalance(cardMaster.getCrmCardBalance());

        // Set the reference to reference passed
        cardTransaction.setCtxReference("blnceExpiry_"+generalUtils.clearTimeStamp(new Date())+"");

        // Save the CardTransaction
        cardTransaction = cardTransactionService.saveCardTransaction(cardTransaction);

        // Log the information
        log.info("Process Card Balance Expiry -> CardTransaction object to save -> "+cardTransaction.toString());

        //after that process the card master
        //set card is zero
*/

        cardMaster.setCrmCardStatus(CardMasterStatus.EXPIRED);

        //save the card master balance is zero and added into transaction table
        cardMaster =saveCardMaster(cardMaster);

        return cardMaster;

    }

    @Override
    public List<CardMaster> findByCrmMerchantNo(Long crmMerchantNo) {

        return cardMasterRepository.findByCrmMerchantNo(crmMerchantNo);
    }


    @Override
    @Scheduled(cron = "${scheduled.cardbalanceexpiry}")
    public void processCardBalanceExpiry() {

        log.info("Card balance expiry process is started with date and time :"+new Date()+"");

        startCardBalanceExpiry();
    }




    @Override
    public boolean isCardExpired(CardType cardType,CardMaster cardMaster) {

        //check card master is null or not
        if(cardMaster ==null || cardMaster.getCrmId()==null ||cardMaster.getCrmId()==0){

            return false;
        }

        //check card type null or  not
        if(cardType ==null){

            return false;

        }

        // Check the expiry option for the cardType
        if ( cardType.getCrtExpiryOption() == CardTypeExpiryOption.NUM_TXNS ) {

            // Check if the NUM_TXNS on the CardMaster is greater than or equal to
            // the max transactions set by the CardType
            if ( cardMaster.getCrmNumTxns() >= cardType.getCrtMaxNumTxns() ) {

                // Return true
                return true;

            }

        } else {

            // Store the date for today
            java.util.Date today = new Date();

            // Compare the cardMaster expiry date with current date
            if ( cardMaster.getCrmExpiryDate().compareTo(new Date(today.getTime())) <= 0 ) {

                // Return true
                return true;

            }

        }


        // Return false
        return false;

    }

    @Override
    public String getCardExpiry(CardType cardType,CardMaster cardMaster ) {

        // String variable holding the expiryText
        String expiryText = "";

        // Check if its expired
        if ( isCardExpired(cardType,cardMaster ) ) {

            expiryText = "Card Expired";

        } else {

            // Check if the
            if ( cardType.getCrtExpiryOption() == CardTypeExpiryOption.NUM_TXNS ) {

                // Set the expiryText as the remaining number of days
                expiryText = "Expires in "+ ( cardType.getCrtMaxNumTxns() - cardMaster.getCrmNumTxns()) + " txns";

            } else {

                // Create the SimpleDateFormat
                SimpleDateFormat dateFormat = new SimpleDateFormat("d MMM yyyy");

                // Set the expiryText to the dateFormat
                expiryText = dateFormat.format(cardMaster.getCrmExpiryDate());


            }

        }

        // Return the expiryText
        return expiryText;

    }


    @Override
    public CardMaster saveCardMaster(CardMaster cardMaster ){

        // Save the cardMaster
        return cardMasterRepository.save(cardMaster);

    }

    @Override
    public CardMaster validateAndSaveCardMaster(CardMasterResource cardMasterResource) throws InspireNetzException {

        authSessionUtils.validateFunctionAccess(FunctionCode.FNC_UPDATE_CARD);

        // Hold the audit details
        String auditDetails = authSessionUtils.getUserNo().toString() + "#" + authSessionUtils.getUserLoginId();

        CardMaster cardMaster=null;

        if(cardMasterResource.getCrmId() !=null ){

            cardMaster=findByCrmId(cardMasterResource.getCrmId());

            if(cardMaster==null){

                log.info("validateAndSaveCardMaster ->Invalid card details"+cardMasterResource.toString());

                throw new InspireNetzException(APIErrorCode.ERR_INVALID_CARD);
            }

            mapper.map(cardMasterResource,cardMaster);

        }else{

            log.info("validateAndSaveCardMaster ->Invalid card details"+cardMasterResource.toString());

            throw new InspireNetzException(APIErrorCode.ERR_INVALID_CARD);

        }

        // Create the BeanPropertyBindingResult
        BeanPropertyBindingResult crmResult = new BeanPropertyBindingResult(cardMaster,"cardmaster");




        // Check if the result contains errors
        if ( crmResult.hasErrors() ) {

            // Get the validation message
            String messages = dataValidationUtils.getValidationMessages(crmResult);

            // Log the response
            log.info("validateAndIssueCardFromMerchant - Response : Invalid Input - "+ messages);

            // Throw InspireNetzException with INVALID_INPUT as error
            throw new InspireNetzException(APIErrorCode.ERR_INVALID_INPUT,messages);

        }


        // If the cardMaster.getCrmId is  null, then set the created_by, else set the updated_by
        if ( cardMaster.getCrmId() == null ) {

            cardMaster.setCreatedBy(auditDetails);

        } else {

            cardMaster.setUpdatedBy(auditDetails);

        }

        boolean isDuplicateExist=isDuplicateCardExisting(cardMaster);

        if(isDuplicateExist){

            log.info("CardMasterServiceImpl ->duplicate card exist"+cardMaster.toString());

            throw new InspireNetzException(APIErrorCode.ERR_DUPLICATE_ENTRY);
        }


        return saveCardMaster(cardMaster);

    }

    @Override
    public CardMasterOperationResponse validateAndIssueCardFromMerchant(CardMasterResource cardMasterResource,Long userNo,String userLoginId, boolean isReissue,Integer overrideStatus,String location) throws InspireNetzException {

        authSessionUtils.validateFunctionAccess(FunctionCode.FNC_ISSUE_CARD);

        // Hold the audit details
        String auditDetails = userNo.toString() + "#" + userLoginId;

        CardMaster cardMaster=null;

        CardNumberInfo cardNumberInfo=null;

        if(cardMasterResource.getCrmId()==null ||cardMasterResource.getCrmId().longValue()==0){

            if(cardMasterResource.getCrmCardNo()==null || cardMasterResource.getCrmCardNo().equals("")){

                // First get the card type for the CardMaster
                //check the card is reissue after expiry
                CardType cardType = cardTypeService.findByCrtId(cardMasterResource.getCrmType());

                // Check if the cardType information is found
                if ( cardType ==  null ) {

                    // Log the information
                    log.info("validateAndIssueCardFromMerchant -> No card type information found");

                    // Throw the exception
                    throw new InspireNetzException(APIErrorCode.ERR_NO_DATA);

                }

                if(cardType.getCrtCardNumberAssignment().intValue()==CardNumberAssignmentMode.AUTO_ASSIGN){

                    cardNumberInfo=cardNumberInfoService.getAvailableCardNumber(cardMasterResource.getCrmMerchantNo(),cardMasterResource.getCrmType());

                    if(cardNumberInfo==null){

                        log.info("validateAndIssueCardFromMerchant ->Card Not found"+cardMasterResource.toString());

                        throw new InspireNetzException(APIErrorCode.ERR_NO_DATA_FOUND);

                    }

                }else{

                    log.info("validateAndIssueCardFromMerchant ->Invalid card details"+cardMasterResource.toString());

                    throw new InspireNetzException(APIErrorCode.ERR_INVALID_CARD);
                }

            }else{

                cardNumberInfo=cardNumberInfoService.createCardNumberInfoObject(cardMasterResource.getCrmCardNo(),cardMasterResource.getCrmMerchantNo(),cardMasterResource.getCrmType(),cardMasterResource.getActivationPin());

                cardNumberInfo=cardNumberInfoService.isCardNumberValid(cardNumberInfo);

                if(cardNumberInfo==null ){

                    log.info("validateAndIssueCardFromMerchant ->Invalid card details"+cardMasterResource.toString());

                    throw new InspireNetzException(APIErrorCode.ERR_INVALID_CARD);

                }

                //check the card is reissue after expiry
                CardType cardType = cardTypeService.findByCrtId(cardNumberInfo.getCniCardType());

                // Check if the cardType information is found
                if ( cardType ==  null ) {

                    // Log the information
                    log.info("validateAndIssueCardFromMerchant -> No card type information found");

                    // Throw the exception
                    throw new InspireNetzException(APIErrorCode.ERR_NO_DATA);

                }

                if(cardType.getCrtCardNumberAssignment().intValue()==CardNumberAssignmentMode.AUTO_ASSIGN){

                    log.info("validateAndIssueCardFromMerchant ->Invalid card details"+cardMasterResource.toString());

                    throw new InspireNetzException(APIErrorCode.ERR_INVALID_CARD);

                }
            }



            cardMasterResource.setCrmCardNo(cardNumberInfo.getCniCardNumber());

            cardMasterResource.setCrmMerchantNo(cardNumberInfo.getCniMerchantNo());

            cardMasterResource.setCrmType(cardNumberInfo.getCniCardType());

            cardMaster=mapper.map(cardMasterResource,CardMaster.class);

        }else if(cardMasterResource.getCrmId() !=null && isReissue){

            cardMaster=findByCrmId(cardMasterResource.getCrmId());

            if(cardMaster==null){

                log.info("validateAndIssueCardFromMerchant ->Invalid card details"+cardMasterResource.toString());

                throw new InspireNetzException(APIErrorCode.ERR_INVALID_CARD);
            }

            mapper.map(cardMasterResource,cardMaster);

        }

        if(cardMaster==null){

            log.info("validateAndIssueCardFromMerchant ->Invalid card details"+cardMasterResource.toString());

            throw new InspireNetzException(APIErrorCode.ERR_INVALID_INPUT);
        }

        if(location!=null&& !location.equals("")){

            MerchantLocation merchantLocation=merchantLocationService.findByMelMerchantNoAndMelLocation(cardMaster.getCrmMerchantNo(),location);

            if(merchantLocation!=null){

                cardMaster.setCrmLocation(merchantLocation.getMelId());
            }

        }


        // Create the BeanPropertyBindingResult
        BeanPropertyBindingResult crmResult = new BeanPropertyBindingResult(cardMaster,"cardmaster");

        CardMasterValidator cardMasterValidator=new CardMasterValidator(dataValidationUtils);

        cardMasterValidator.validate(cardMaster,crmResult);

        // Check if the result contains errors
        if ( crmResult.hasErrors() ) {

            // Get the validation message
            String messages = dataValidationUtils.getValidationMessages(crmResult);

            // Log the response
            log.info("validateAndIssueCardFromMerchant - Response : Invalid Input - "+ messages);

            // Throw InspireNetzException with INVALID_INPUT as error
            throw new InspireNetzException(APIErrorCode.ERR_INVALID_INPUT,messages);

        }


        // If the cardMaster.getCrmId is  null, then set the created_by, else set the updated_by
        if ( cardMaster.getCrmId() == null ) {

            cardMaster.setCreatedBy(auditDetails);

        } else {

            cardMaster.setUpdatedBy(auditDetails);

        }


        return issueCard(cardMaster, userNo,userLoginId, isReissue,overrideStatus);

    }



    @Override
    public boolean deleteCardMaster(Long crmId , Long merchantNo) throws InspireNetzException {

        // Get the cardMaster information
        CardMaster cardMaster = findByCrmId(crmId);

        // Check if the cardMaster is found
        if ( cardMaster == null || cardMaster.getCrmId() == null) {

            // Log the response
            log.info("deleteCardMaster - Response : No cardMaster information found");

            // Thrown not found exception
            throw new InspireNetzException(APIErrorCode.ERR_NO_DATA_FOUND);

        }


        // Check if the passed object's merchant number and the current
        // authenticated merchant number are same.
        // We need to raise an error if not
        if ( cardMaster.getCrmMerchantNo().longValue() != merchantNo ) {

            // Log the response
            log.info("deleteCardMaster - Response : You are not authorized to delete the cardMaster");

            // Throw InspireNetzException with ERR_NOT_AUTHORIZED as error
            throw new InspireNetzException(APIErrorCode.ERR_NOT_AUTHORIZED);

        }
        // Delete the cardMaster
        cardMasterRepository.delete(crmId);

        // return true
        return true;

    }

    @Override
    public CardMaster getCardMasterByCrmLoyaltyId(Long merchantNo, String crmLoyaltyId) throws InspireNetzException {

        //first check loyalty id is valid
        Customer customer =customerService.findByCusLoyaltyIdAndCusMerchantNo(crmLoyaltyId,merchantNo);

        //customer null throw error
        if(customer ==null){

            log.info("CardMasterServiceImpl->getCardMasterByCrmLoyaltyId:: Inavlid customer");

            throw new InspireNetzException(APIErrorCode.ERR_NO_LOYALTY_ID);
        }

        //pick the card information in sorted order
        List<CardMaster> cardMasters =findByCrmMerchantNoAndCrmLoyaltyIdOrderByCrmIdDesc(merchantNo, crmLoyaltyId);

        if(cardMasters ==null){

            log.info("CardMasterServiceImpl->getCardMasterByCrmLoyaltyId:: No data found");

            throw new InspireNetzException(APIErrorCode.ERR_NO_DATA);

        }

        CardMaster cardMaster=cardMasters.get(0);

        if(cardMasters ==null){

            log.info("CardMasterServiceImpl->getCardMasterByCrmLoyaltyId:: No data found");

            throw new InspireNetzException(APIErrorCode.ERR_NO_DATA);

        }

        cardMaster.setCusIdType(customer.getCusIdType());

        cardMaster.setCusIdNo(customer.getCusIdNo());

        //return card last created card details
        return cardMaster;
    }

    @Override
    public List<CardMaster> findByCrmMerchantNoAndCrmLoyaltyIdOrderByCrmIdDesc(Long merchantNo, String crmLoyaltyId) {

        return cardMasterRepository.findByCrmMerchantNoAndCrmLoyaltyIdOrderByCrmIdDesc(merchantNo,crmLoyaltyId);
    }

    @Override
    public CardMaster   findByCrmCardNo(String crmCardNo) {
        return cardMasterRepository.findByCrmCardNo(crmCardNo);
    }

    @Override
    public boolean isActivationDateValid(CardMaster cardMaster,Long merchantNo) {

       //check the activation date of the card if the activation type is null or instantly t
       if(cardMaster.getCrmActivationType() ==null || cardMaster.getCrmActivationType().intValue() == CardActivationType.CARD_INSTANTLY){

          return  true;

       }

       //if its the activation type is based on card type check the card type option
       if(cardMaster.getCrmActivationType().intValue() == CardActivationType.CARD_TYPE){

           //if the card activate option card type check the card type option
           CardType cardType = cardTypeService.findByCrtId(cardMaster.getCrmType());

           if(cardType ==null){

               log.info("Received card type is null----------------->pls check card Number#:"+cardMaster.getCrmType());

               return  false;
           }

           //get the current date
           Date currentDate =generalUtils.clearTimeStamp(new Date());

           //check the card activation option
           if(cardType.getCrtActivateOption().intValue() == CardTypeActivateOption.CRT_ACTIVITY_FIXED_DATE){

              //compare date
              if(currentDate.compareTo(cardType.getCrtActivateDate()) < 0 ){

                  return false;
              }



           }else if(cardType.getCrtActivateOption().intValue() ==CardTypeActivateOption.CRT_ACTIVITY_DAYS){

               //check the number of days if the number of days is 0 is zero then return true
               if(cardType.getCrtActivateDays().intValue() ==0){

                   return true;
               }

               //clear time stamp
               Date issuedDate = cardMaster.getCrmIssuedDate();

               //get the added date
               Date addedDate = generalUtils.addDaysToDate(issuedDate,cardType.getCrtActivateDays());


               //check added date is less the n return false
               if(currentDate.compareTo(addedDate) < 0){

                   return false;
               }

           }

       }

       return true;
    }

    @Override
    public void issueMultipleCard(CardMaster cardMaster, Long userNo, String userLoginId, boolean isReissue, Integer overridestatus) throws InspireNetzException {

        //get the card number details
        List<CardMasterResource> cardMasterResourcesList =new ArrayList<>();

        List<CardNumberDetails> cardNumberList = new ArrayList<>();

        //multiple card number
        List<CardNumberDetails> subCardNumberList = cardMaster.getMultipleCardNumber();

        if(subCardNumberList !=null && subCardNumberList.size() >0){


            for(CardNumberDetails cardNumberDetails:subCardNumberList){

                //create card master object
                CardMaster multipleCard =new CardMaster();

                //cradmaster resource
                CardMasterResource multipleCardResource =new CardMasterResource();

                //map the multiple card resource
                multipleCardResource =mapper.map(cardMaster,CardMasterResource.class);

                //set the resource value
                multipleCardResource.setCrmCardNo(cardNumberDetails.getCrmCardNumber());
                multipleCardResource.setCrmActivationType(cardNumberDetails.getCrmActivationType());
                multipleCardResource.setCrmPromoBalance(0.0);

                //multipleCardResource.setCrtMinTopupValue(cardNumberDetails.getCrmTopupAmount()==null?0.0:cardNumberDetails.getCrmTopupAmount());
                multipleCardResource.setCrmCardBalance(cardNumberDetails.getCrmTopupAmount() ==null?0.0:cardNumberDetails.getCrmTopupAmount());
                multipleCardResource.setActivationPin(cardNumberDetails.getCrmActivationPin());

                //add into the list
                cardMasterResourcesList.add(multipleCardResource);
            }


        }

        if(cardMaster !=null){

           //set the existing card
           CardMasterResource existingCard = mapper.map(cardMaster,CardMasterResource.class);

           cardMasterResourcesList.add(existingCard);
        }

        CardMasterOperationResponse cardMasterOperationResponse =null;

        //iterate the list and and issue multiple card
        for (CardMasterResource cardMasterResource :cardMasterResourcesList){

            try{

                 cardMasterOperationResponse =validateAndIssueCardFromMerchant(cardMasterResource, userNo, userLoginId, false, 0,"");

                 log.info("Issued Card Response:#"+cardMasterOperationResponse);
            }catch (InspireNetzException ex){

                log.info("Issue Multiple Card Card Number: ------------->"+cardMasterResource.getCrmCardNo()+" #"+ex);
            }catch (Exception e){

                log.info("Issue Multiple Card Card Number: ------------->"+cardMasterResource.getCrmCardNo()+" #"+e);
            }

        }


    }

    @Override
    public List<CardMaster> findByCrmMerchantNoAndCrmMobile(Long crmMerchantNo,String crmMobile) {

        List<CardMaster> cardMasterList = cardMasterRepository.findByCrmMerchantNoAndCrmMobile(crmMerchantNo,crmMobile);


        return cardMasterList;
    }

    @Override
    public CardMaster updateTier(String cardNumber, Long tierId,Long merchantNo,Boolean updateCardExpiry, java.sql.Date cardExpiryDate) throws InspireNetzException {

        //find the card information
        CardMaster cardMaster = findByCrmMerchantNoAndCrmCardNo(merchantNo,cardNumber);

        //check the card exist or not
        if(cardMaster ==null){

            log.info("updateTier -->invalid Card Number :#"+cardNumber);

            throw new InspireNetzException(APIErrorCode.ERR_INVALID_CARD);
        }

        //check tier is exist or not
        Tier tier = tierService.findByTieId(tierId);

        if(tier ==null){

            log.info("updateTier -->invalid Tier Id  :#"+tierId);

            throw new InspireNetzException(APIErrorCode.ERR_INVALID_TIER);

        }

        //update tier information
        cardMaster.setCrmTier(tierId);


        if(updateCardExpiry){

            //Check if the expiry date is not null
            if(cardExpiryDate !=null){

                //Set the expiry date
                cardMaster.setCrmExpiryDate(cardExpiryDate);

            }else {

                //Get the cardtype
                CardType cardType = cardTypeService.findByCrtId(cardMaster.getCrmType());

                if(cardType == null){

                    // Log the error
                    log.info("UpdateTier -->No card type info found");

                    //Throw an exception
                    throw new InspireNetzException(APIErrorCode.ERR_NO_DATA_FOUND);

                }

                //Get the expiry date based on card type expiry settings
                java.sql.Date expiryDate = cardTypeService.getExpiryDateForCardType(cardType);


                if(expiryDate != null) {

                    //Set the expiry date
                    cardMaster.setCrmExpiryDate(expiryDate);

                }

            }


        }


        //save the tier information
        cardMaster = saveCardMaster(cardMaster);

        return cardMaster;

    }


    @Override
    @Transactional(rollbackFor = Exception.class)
    public CardMasterOperationResponse setCardLockStatus(CardMasterLockStatusRequest cardMasterLockStatusRequest) throws InspireNetzException {

        // Log the request information
        log.info("setCardLockStatus -> CardMasterLockStatusRequest object : "+ cardMasterLockStatusRequest.toString());

        // If the passed status is other than LOCKED or ACTIVE, then we need to show invalid input exception
        if ( cardMasterLockStatusRequest.getLockStatus() != CardMasterStatus.ACTIVE && cardMasterLockStatusRequest.getLockStatus() != CardMasterStatus.LOCKED ) {

            // Log the information
            log.info("setCardLockStatus -> status not supported");

            // Throw invalid input exception
            throw new InspireNetzException(APIErrorCode.ERR_INVALID_INPUT);

        }


        // Get the information for the card number and merchantnumber
        CardMaster cardMaster = this.findByCrmMerchantNoAndCrmCardNo(cardMasterLockStatusRequest.getMerchantNo(), cardMasterLockStatusRequest.getCardNo());


        // Check if the cardMaster exists
        if ( cardMaster == null || cardMaster.getCrmId() == null ) {

            // Log the information
            log.info("setCardLockStatus -> No card master information found");

            // Throw the exception
            throw new InspireNetzException(APIErrorCode.ERR_NO_DATA);

        }




        // If the request is to make it active and if the current status is not locked
        // then we need to show an error message as OPERATION_NOT_ALLOWED.
        // If don't check this, an expired card can also be unlocked using the
        // unlock mechanism
        if ( cardMasterLockStatusRequest.getLockStatus() == CardMasterStatus.ACTIVE && cardMaster.getCrmCardStatus() != CardMasterStatus.LOCKED) {

            // log the information
            log.info("setCardLockStatus -> Card is not locked, Activating the card is not permitted");

            // Throw new exception
            throw new InspireNetzException(APIErrorCode.ERR_OPERATION_NOT_ALLOWED);

        }



        // Set the card status as the status passed
        cardMaster.setCrmCardStatus(cardMasterLockStatusRequest.getLockStatus());

        // Set the audit details
        cardMaster.setUpdatedBy(cardMasterLockStatusRequest.getUserNo().toString());

        // Save the cardMaster
        this.saveCardMaster(cardMaster);

        //  Log the information
        log.info("setCardLockStatus -> CardMaster object " + cardMaster.toString());



        // Create a CardTransaction object and insert 
        CardTransaction cardTransaction = CardTransactionUtils.getCardTransactionFromCardMaster(cardMaster);

        // Set the user number of the user who updated the information
        cardTransaction.setCtxUserNo(cardMasterLockStatusRequest.getUserNo());

        // Check the current status and set the transaction type
        if ( cardMasterLockStatusRequest.getLockStatus() == CardMasterStatus.ACTIVE ) {

            // Set the transaction type as unlocking
            cardTransaction.setCtxTxnType(CardTransactionType.UNLOCK);

        } else {

            // Set the transaction type as locking
            cardTransaction.setCtxTxnType(CardTransactionType.LOCK);

        }



        // Set the location
        cardTransaction.setCtxLocation(cardMaster.getCrmLocation()==null?0:cardMaster.getCrmLocation());

        // Set the reference as location#userno
        cardTransaction.setCtxReference(cardMasterLockStatusRequest.getCtxLocation()+"#"+cardMasterLockStatusRequest.getUserNo());


        // Log the CardTransaction object being saved
        log.info("setCardLockStatus -> Card Transaction object : " + cardTransaction.toString());

        // Save the transaction
        cardTransactionService.saveCardTransaction(cardTransaction);



        // Return CardMasterOperationResponse
        return getProxyCardOperationResponse(cardTransaction);

    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public CardMasterOperationResponse changeCardPin(String cardNo,String crmPin,Long merchantNo) throws InspireNetzException {

        // Create the PinChangeRequest
        CardMasterChangePinRequest cardMasterChangePinRequest = new CardMasterChangePinRequest();

        // Set the merchantNo
        cardMasterChangePinRequest.setMerchantNo(merchantNo);

        // Set the userNo
        cardMasterChangePinRequest.setUserNo(authSessionUtils.getUserNo());

        // Set the cardNo
        cardMasterChangePinRequest.setCardNo(cardNo);

        // Set the pin
        cardMasterChangePinRequest.setCardPin(crmPin);

        // Set the location
        cardMasterChangePinRequest.setCtxLocation(authSessionUtils.getUserLocation());

        // Log the request information
        log.info("changeCardPin -> CardMasterChangePinRequest : "+cardMasterChangePinRequest.toString());

        // Check the pin lenght validity. If the pin is not 4 digits, then throw exception
        if ( cardMasterChangePinRequest.getCardPin().length()!=4  ) {

            // Log the informaiton
            log.info("changeCardPin -> Pin is not 4 digits");

            // Throw invalid input
            throw new InspireNetzException(APIErrorCode.ERR_INVALID_INPUT);

        }

        // First check if the cardMaster information exists for the given card
        // number and merchantNo in the CardMaster object
        CardMaster cardMaster = cardMasterRepository.findByCrmMerchantNoAndCrmCardNo(cardMasterChangePinRequest.getMerchantNo(),cardMasterChangePinRequest.getCardNo());

        // Check if the cardMaster object is valid
        if ( cardMaster == null || cardMaster.getCrmId() == null ) {

            // Log the information
            log.info("changeCardPin -> No card master information found");

            // Throw the exception
            throw new InspireNetzException(APIErrorCode.ERR_NO_DATA);

        }



        // Get the CardType information for the cardmaster
        CardType cardType = cardMaster.getCardType();

        // Check if the cardType is null
        // Check if the cardType information is found
        if ( cardType ==  null ) {

            // Log the information
            log.info("changeCardPin -> No card type information found");

            // Throw the exception
            throw new InspireNetzException(APIErrorCode.ERR_NO_DATA_FOUND);

        }

        // If any of source or dest card is not active, then the operation cannot be
        // carried out.
        if (  cardMaster.getCrmCardStatus() == CardMasterStatus.EXPIRED||cardMaster.getCrmCardStatus()==CardMasterStatus.LOCKED ) {

            // Log the information
            log.info("changeCardPin -> Card may be expired or locked. ");

            // Throw the exception
            throw new InspireNetzException(APIErrorCode.ERR_OPERATION_NOT_ALLOWED);

        }

        // Check if pin is enabled for card type
        /*if ( cardType.getCrtAllowPinIndicator() != IndicatorStatus.YES ) {

            // Log the information
            log.info("changeCardPin -> Pin is not enabled for the card type");

            // Throw the exception
            throw new InspireNetzException(APIErrorCode.ERR_OPERATION_NOT_ALLOWED);

        }
*/
        //Get the encoded crm pin
        String encCrmPin = getEncodedCrmPin(cardMasterChangePinRequest.getCardPin());

        // Set the new pin
        cardMaster.setCrmPin(encCrmPin);

        // Save the cardMaster
        cardMaster=this.saveCardMaster(cardMaster);

        if(cardMaster==null){

            // Log the information
            log.info("changeCardPin -> Card Master Save Failed");

            // Throw the exception
            throw new InspireNetzException(APIErrorCode.ERR_OPERATION_FAILED);
        }

        //  Log the information
        log.info("changeCardPin -> CardMaster object " + cardMaster.toString());



        // Create a CardTransaction object and insert
        CardTransaction cardTransaction = CardTransactionUtils.getCardTransactionFromCardMaster(cardMaster);

        // Set the user number of the user who updated the information
        cardTransaction.setCtxUserNo(cardMasterChangePinRequest.getUserNo());

        // Set the transaction type as locking
        cardTransaction.setCtxTxnType(CardTransactionType.PIN_CHANGE);

        // Set the location
        cardTransaction.setCtxLocation(cardMasterChangePinRequest.getCtxLocation());

        // Set the reference as location#userno
        cardTransaction.setCtxReference(cardMasterChangePinRequest.getCtxLocation()+"#"+cardMasterChangePinRequest.getUserNo());



        // Log the CardTransaction object being saved
        log.info("setCardLockStatus -> Card Transaction object : " + cardTransaction.toString());

        // Save the transaction
        cardTransaction=cardTransactionService.saveCardTransaction(cardTransaction);



        // Return CardMasterOperationResponse
        return getProxyCardOperationResponse(cardTransaction);


    }


    public CardMasterOperationResponse getProxyCardOperationResponse(CardTransaction cardTransaction){

        // Create the CardMasterOperationResponse
        CardMasterOperationResponse cardMasterOperationResponse = new CardMasterOperationResponse();

        CardMaster cardMaster=findByCrmCardNo(cardTransaction.getCtxCardNumber());

        Merchant merchant=null;

        Customer customer=null;

        if(cardMaster!=null && cardMaster.getCrmId().longValue()!=0L){

            merchant=merchantService.findByMerMerchantNo(cardMaster.getCrmMerchantNo());

            customer=customerService.findByCusLoyaltyIdAndCusMerchantNo(cardMaster.getCrmLoyaltyId(),cardMaster.getCrmMerchantNo());
        }

        cardMasterOperationResponse=CardTransactionUtils.getCardOperationResponse(cardTransaction, cardMaster, merchant, customer);

        return cardMasterOperationResponse;
    }

    /*@Override
    @Transactional(rollbackFor = Exception.class)
    public CardMasterOperationResponse issueCard(CardMaster cardMaster, Long userNo, boolean isReissue) throws InspireNetzException {

        // First get the card type for the CardMaster
        //check the card is reissue after expiry
        CardType cardType = cardTypeService.findByCrtId(cardMaster.getCrmType());

        // Check if the cardType information is found
        if ( cardType ==  null ) {

            // Log the information
            log.info("issueCard -> No card type information found");

            // Throw the exception
            throw new InspireNetzException(APIErrorCode.ERR_NO_DATA);

        }

        // Check for the range
        if ( !cardTypeService.isCardNumberValid(cardMaster.getCrmCardNo(),cardType) ) {

            // Log the information
            log.info("issueCard -> Card number is not valid range");

            // Throw the exception
            throw new InspireNetzException(APIErrorCode.ERR_INVALID_CARD_NUMBER_RANGE);

        }

        // Check if the reIssue is true, then we need to make sure that the card
        // is in refunded status
        if ( isReissue && cardMaster.getCrmCardStatus().intValue() != CardMasterStatus.REFUNDED && cardMaster.getCrmCardStatus().intValue() !=CardMasterStatus.BALANCE_EXPIRED) {

            // Log the information
            log.info("issueCard -> Card status is not refunded");

            // Throw the exception
            throw new InspireNetzException(APIErrorCode.ERR_OPERATION_NOT_ALLOWED);

        }

        // Check the cardType and if its fixed value, the set the value of the card
        // to be the fixed value

        if ( cardType.getCrtType() == CardTypeType.FIXED_VALUE ) {

            cardMaster.setCrmCardBalance(cardType.getCrtFixedValue());

        } else {


            // Check if the card value is less than minimum topup value
            if ( cardMaster.getCrmCardBalance() < cardType.getCrtMinTopupValue() ) {

                // Log the information
                log.info("issueCard -> Card value is less than minimum topup value allowed");

                // Throw the exception
                throw new InspireNetzException(APIErrorCode.ERR_TOPUP_VALUE_LESS_THAN_MINIMUM);

            } else if ( cardMaster.getCrmCardBalance() > cardType.getCrtMaxValue() ) {

                // Log the information
                log.info("issueCard -> Card value is greater than maximum allowed");

                // Throw the exception
                throw new InspireNetzException(APIErrorCode.ERR_CARD_VALUE_HIGHER_THAN_MAXIMUM);

            }

        }

        //check duplicate mobile number exist
        boolean duplicateMobile =isDuplicateMobileNumberExisting(cardMaster);

        if(duplicateMobile){

            // Log the information
            log.info("issueCard -> Mobile Number Is duplicate :"+cardMaster);

            // Throw the exception
            throw new InspireNetzException(APIErrorCode.ERR_DUPLICATE_MOBILE);
        }

        // Set the expiry date for the card
        cardMaster.setCrmExpiryDate(cardTypeService.getExpiryDateForCardType(cardType));

        //check customer is already exist or if exist get the loyalty id and set into crmloyalty id field
        Customer customer =checkCustomerIsExist(cardMaster);

        //check customer is exist or not if exist then set loyalty id into card
        if(customer !=null){

            //set card master loyalty id
            cardMaster.setCrmLoyaltyId(customer.getCusLoyaltyId());
        }

        // If the crmLoyaltyId is not specified, then we set the card number as loyalty id itsefl
        if ( cardMaster.getCrmLoyaltyId() == null || cardMaster.getCrmLoyaltyId() == "" ) {

            cardMaster.setCrmLoyaltyId(cardMaster.getCrmCardNo());

        }


        // Set the CardMaster fields
        cardMaster.setCrmCardStatus(CardMasterStatus.ACTIVE);

        // Set the audit details
        cardMaster.setCreatedBy(userNo.toString());

        // Set the updated by
        cardMaster.setUpdatedBy(userNo.toString());

        // Set the numTxns to be 0
        cardMaster.setCrmNumTxns(0);

        // Log the cardmaster object
        log.info("issueCard -> CardMaster saving object "+ cardMaster.toString());

        //after issue card we need to register card holder in loyalty based on settings enabled
        boolean registerCustomer =registerCustomerInLoyalty(cardMaster);

        // Save the Card Master
        if(registerCustomer){

            //set issued date
            if(isReissue || cardMaster.getCrmId() ==null){

                //added created date
                Date currentDate  = new Date();

                //convert into sql
                java.sql.Date issuedDate =new java.sql.Date(currentDate.getTime());

                //set the issued date
                cardMaster.setCrmIssuedDate(issuedDate);

                //check its card type is topup
                if(cardType.getCrtType() == CardTypeType.RECHARGEBLE){

                    //set the topup date
                    cardMaster.setCrmTopupDate(new Timestamp(currentDate.getTime()));
                }

            }

            //save the card master information
            cardMaster = this.saveCardMaster(cardMaster);

        }

        // CardTransaction object holding the card information for response
        CardTransaction retData ;


        // Create the CardTransaction object for the issue of card
        CardTransaction ctxIssueCard = CardTransactionUtils.getCardTransactionFromCardMaster(cardMaster);

        // Set the transaction type
        ctxIssueCard.setCtxTxnType(CardTransactionType.ISSUE);

        // If the cardType is  rechargeable, we need to set the balance to be 0 for issue
        if ( cardType.getCrtType() == CardTypeType.RECHARGEBLE ) {

            // Set the balance to be 0
            ctxIssueCard.setCtxCardBalance(0.0);

        }

        // Set the reference
        ctxIssueCard.setCtxReference(cardMaster.getCrmId().toString());

        // Log the CardTransaction object being saved
        log.info("IssueCard -> Issue CardTransaction object : "+ctxIssueCard.toString());

        // Save the issue card transaction
        ctxIssueCard = cardTransactionService.saveCardTransaction(ctxIssueCard);

        // If the cardType is rechargeable , we need to add a CardTransaction for
        // topup as well.
        if ( cardType.getCrtType() == CardTypeType.RECHARGEBLE ) {

            // Define the CardTransaction object for the topup
            CardTransaction ctxTopupCard = CardTransactionUtils.getCardTransactionFromCardMaster(cardMaster);

            // Set the transaction type
            ctxTopupCard.setCtxTxnType(CardTransactionType.TOPUP);

            // Set the transaction amount to be the balance of the card ( topup amount )
            ctxTopupCard.setCtxTxnAmount(cardMaster.getCrmCardBalance());

            // Set the refereence to the ctxCrmId
            ctxTopupCard.setCtxReference(cardMaster.getCrmId().toString());

            // Log the CardTransaction object being saved
            log.info("IssueCard -> Topup CardTransaction object : "+ctxTopupCard.toString());

            // Save the CardTransaction object
            ctxTopupCard = cardTransactionService.saveCardTransaction(ctxTopupCard);

            // Set the retData to be ctxTopupCard
            retData = ctxTopupCard;

        } else {

            // Set the retData to be ctxIssueCard
            retData = ctxIssueCard;

        }

        // Finally return the response object
        return CardTransactionUtils.getCardOperationResponse(retData);
    }

*/

    @Override
    @Transactional(rollbackFor = Exception.class)
    public CardMasterOperationResponse issueCard(CardMaster cardMaster, Long userNo,String userLoginId, boolean isReissue,Integer overrideStatus) throws InspireNetzException {

        // First get the card type for the CardMaster
        //check the card is reissue after expiry
        CardType cardType = cardTypeService.findByCrtId(cardMaster.getCrmType());

        // Hold the audit details
        String auditDetails = userNo.toString() + "#" + userLoginId;

        // Check if the cardType information is found
        if ( cardType ==  null ) {

            // Log the information
            log.info("issueCard -> No card type information found");

            // Throw the exception
            throw new InspireNetzException(APIErrorCode.ERR_NO_DATA);

        }


        // We will only check for the duplicate ( as the current duplicate checking is only based on the
        // card number ) when the the card number is not auto- assign
        if(cardType.getCrtCardNumberAssignment().intValue() != CardNumberAssignmentMode.AUTO_ASSIGN){

            // Check if the duplicate is existing
            boolean isDuplicateExist=isDuplicateCardExisting(cardMaster);

            // If the duplicate exists, then we need to throw the exception
            if(isDuplicateExist){

                // Log the infor
                log.info("CardMasterServiceImpl ->duplicate card exist"+cardMaster.toString());

                // Throw the exception
                throw new InspireNetzException(APIErrorCode.ERR_DUPLICATE_ENTRY);
            }
        }


        // Check if the card is expired
        boolean isExpired = this.isCardExpired(cardType,cardMaster);

        // If the card is expired, then we need to set the cardStatus as expired
        // and then return the error messag

        // Check if the reIssue is true, then we need to make sure that the card
        // is in refunded status
        if ( isReissue && cardMaster.getCrmCardStatus().intValue() != CardMasterStatus.RETURNED && cardMaster.getCrmCardStatus().intValue() !=CardMasterStatus.BALANCE_EXPIRED && cardMaster.getCrmCardStatus().intValue() != CardMasterStatus.EXPIRED && !isExpired) {

            // Log the information
            log.info("issueCard -> Card status is not refunded");

            // Throw the exception
            throw new InspireNetzException(APIErrorCode.ERR_OPERATION_NOT_ALLOWED);

        }


        // Check the cardType and if its fixed value, the set the value of the card
        // to be the fixed value

        if ( cardType.getCrtType() == CardTypeType.FIXED_VALUE ) {

            cardMaster.setCrmCardBalance(cardType.getCrtFixedValue());

        } else {


            // Check if the card value is less than minimum topup value
            if ( cardMaster.getCrmCardBalance() < cardType.getCrtMinTopupValue() ) {

                // Log the information
                log.info("issueCard -> Card value is less than minimum topup value allowed");

                // Throw the exception
                throw new InspireNetzException(APIErrorCode.ERR_TOPUP_VALUE_LESS_THAN_MINIMUM);

            } else if ( cardMaster.getCrmCardBalance() > cardType.getCrtMaxValue() ) {

                // Log the information
                log.info("issueCard -> Card value is greater than maximum allowed");

                // Throw the exception
                throw new InspireNetzException(APIErrorCode.ERR_CARD_VALUE_HIGHER_THAN_MAXIMUM);

            }

        }



        // Set the expiry date for the card
        cardMaster.setCrmExpiryDate(cardTypeService.getExpiryDateForCardType(cardType));

        //check customer is already exist or if exist get the loyalty id and set into crmloyalty id field
        Customer customer =checkCustomerIsExist(cardMaster);

        //check customer is exist or not if exist then set loyalty id into card
        if(customer !=null){

            //check the settings multiple mobile allowed or not if multiple mobile not allowed throw error multiple card not allowed
            boolean enableMultipleCardReg = merchantSettingService.isSettingEnabledForMerchant(AdminSettingsConfiguration.MER_ALLOWED_MULTIPLE_CARD_IN_SAME_MOBILE,cardMaster.getCrmMerchantNo());

            if(!enableMultipleCardReg){

                //check the card number exist with mobile number
                boolean isExistCardWithMobile = isDuplicateMobile(cardMaster);

                //if the card existing same mobile throw error
                if(isExistCardWithMobile){

                    throw new InspireNetzException(APIErrorCode.ERR_DUPLICATE_MOBILE);
                }


            }
            //set card master loyalty id
            cardMaster.setCrmLoyaltyId(customer.getCusLoyaltyId());

        }else{

            //check merchant enable for auto registration
            boolean isAutoSettingEnabled =merchantSettingService.isSettingEnabledForMerchant(AdminSettingsConfiguration.MER_ENABLE_AUTO_REG_CHARGE_CARD,cardMaster.getCrmMerchantNo());

            //check if enable
            if(isAutoSettingEnabled){

                boolean isCardNumberAsLoyaltyId =merchantSettingService.isSettingEnabledForMerchant(AdminSettingsConfiguration.MER_SET_CC_NUMBER_AS_LOYALTY_ID,cardMaster.getCrmMerchantNo());

                //check if enable
                if(isCardNumberAsLoyaltyId){

                    // If the crmLoyaltyId is not specified, then we set the card number as loyalty id it sefl
                    if ( cardMaster.getCrmLoyaltyId() == null || cardMaster.getCrmLoyaltyId() == ""||isReissue ) {

                        cardMaster.setCrmLoyaltyId(cardMaster.getCrmCardNo());

                    }


                }else{

                    // If the crmLoyaltyId is not specified, then we set the card number as loyalty id it sefl
                    if ( cardMaster.getCrmLoyaltyId() == null || cardMaster.getCrmLoyaltyId() == ""||isReissue ) {

                        cardMaster.setCrmLoyaltyId(cardMaster.getCrmMobile());

                    }

                }


                //after issue card we need to register card holder in loyalty based on settings enabled
                Customer registeredCustomer =registerCustomerInLoyalty(cardMaster,userNo);

                if(registeredCustomer==null){

                    // Log the cardmaster object
                    log.info("issueCard -> CardMaster saving customer object failed"+ cardMaster.toString());

                    throw new InspireNetzException(APIErrorCode.ERR_OPERATION_FAILED);
                }

                cardMaster.setCrmLoyaltyId(registeredCustomer.getCusLoyaltyId());

            }

        }


        // Set the override status for card master
        if(overrideStatus.intValue() ==0){

            //set as active otherwise set as passed value
            cardMaster.setCrmCardStatus(CardMasterStatus.ACTIVE);

        }else {

            //set as active otherwise set as passed value
            cardMaster.setCrmCardStatus(overrideStatus);
        }


        // Set the audit details
        cardMaster.setCreatedBy(auditDetails);

        // Set the updated by
        cardMaster.setUpdatedBy(auditDetails);

        // Set the numTxns to be 0
        cardMaster.setCrmNumTxns(0);

        // Save the Card Master
        if(isReissue || cardMaster.getCrmId() ==null){

            //added created date
            Date currentDate  = new Date();

            //convert into sql
            java.sql.Date issuedDate =new java.sql.Date(currentDate.getTime());

            //set the issued date
            cardMaster.setCrmIssuedDate(issuedDate);

            //check its card type is topup
            if(cardType.getCrtType() == CardTypeType.RECHARGEBLE){

                //set the topup date
                cardMaster.setCrmTopupDate(new Timestamp(currentDate.getTime()));
            }

        }

        // Log the cardmaster object
        log.info("issueCard -> CardMaster saving object "+ cardMaster.toString());

        // Set the issueRetryCount to 0
        int issueRetryCount =0;

        // Iterate for 3 tries
        while ( issueRetryCount < 3 ) {

            try{

                // Try to save the card master
                cardMaster = this.saveCardMaster(cardMaster);

                // break from the loop if the saving was successful
                break;

            } catch (DataIntegrityViolationException dataIntegrityException){

                // Log the exception
                log.info("Data Integrity Exception for issue card------------->"+dataIntegrityException);

                //check the type of card if the card type is auto assigned retry the issue card option
                if(cardType.getCrtCardNumberAssignment().intValue() ==CardNumberAssignmentMode.AUTO_ASSIGN){

                    // Increment the issueretry count
                    issueRetryCount = issueRetryCount +1;

                    // If issueCount is already 3, then we need to throw the exception
                    if (issueRetryCount >= 3 ) {

                        // Log the information
                        log.info("issueCard -> Card number duplicated after 3 tries");

                        // Throw the exception
                        throw new InspireNetzException(APIErrorCode.ERR_CARD_NUMBER_DUPLICATE);

                    }

                    //get the available card number
                    CardNumberInfo  cardNumberInfo=cardNumberInfoService.getAvailableCardNumber(cardMaster.getCrmMerchantNo(),cardType.getCrtId());

                    // check if the card number is null
                    if(cardNumberInfo==null){

                        // Log the error
                        log.info("issue Card ->No Available Card Exist"+cardNumberInfo.toString());

                        // Throw exception
                        throw new InspireNetzException(APIErrorCode.ERR_NO_DATA_FOUND);

                    }

                    //set the card the new card
                    cardMaster.setCrmCardNo(cardNumberInfo.getCniCardNumber());

                }

            }

        }



        if(cardMaster==null){

            // Log the cardmaster object
            log.info("issueCard -> CardMaster object save failed "+ cardMaster.toString());

            throw new InspireNetzException(APIErrorCode.ERR_OPERATION_FAILED);
        }

        if(!isReissue){

            CardNumberInfo cardNumberInfo=cardNumberInfoService.findByCniMerchantNoAndCniCardNumber(cardMaster.getCrmMerchantNo(),cardMaster.getCrmCardNo());

            if(cardNumberInfo==null){

                // Log the cardmaster object
                log.info("issueCard -> CardNumberInfo update failed "+ cardMaster.toString());

                throw new InspireNetzException(APIErrorCode.ERR_OPERATION_FAILED);
            }

            cardNumberInfo.setCniCardStatus(IndicatorStatus.YES);

            cardNumberInfo=cardNumberInfoService.saveCardNumberInfo(cardNumberInfo);

            if(cardNumberInfo==null){

                // Log the cardmaster object
                log.info("issueCard -> CardNumberInfo update failed "+ cardMaster.toString());

                throw new InspireNetzException(APIErrorCode.ERR_OPERATION_FAILED);
            }
        }

        // CardTransaction object holding the card information for response
        CardTransaction retData ;


        // Create the CardTransaction object for the issue of card
        CardTransaction ctxIssueCard = CardTransactionUtils.getCardTransactionFromCardMaster(cardMaster);

        // Set the transaction type
        ctxIssueCard.setCtxTxnType(CardTransactionType.ISSUE);

        // If the cardType is  rechargeable, we need to set the balance to be 0 for issue
        if ( cardType.getCrtType() == CardTypeType.RECHARGEBLE ) {

            // Set the balance to be 0
            ctxIssueCard.setCtxCardBalance(0.0);

        }

        // Set the reference
        ctxIssueCard.setCtxReference(cardMaster.getCrmId().toString());

        // Log the CardTransaction object being saved
        log.info("IssueCard -> Issue CardTransaction object : "+ctxIssueCard.toString());

        // Save the issue card transaction
        ctxIssueCard = cardTransactionService.saveCardTransaction(ctxIssueCard);

        // If the cardType is rechargeable , we need to add a CardTransaction for
        // topup as well.
        if ( cardType.getCrtType() == CardTypeType.RECHARGEBLE && cardMaster.getCrmCardBalance()>0) {


            // Define the CardTransaction object for the topup
            CardTransaction ctxTopupCard = CardTransactionUtils.getCardTransactionFromCardMaster(cardMaster);

            // Set the transaction type
            ctxTopupCard.setCtxTxnType(CardTransactionType.TOPUP);

            // Set the transaction amount to be the balance of the card ( topup amount )
            ctxTopupCard.setCtxTxnAmount(cardMaster.getCrmCardBalance());

            // Set the reference to the ctxCrmId
            ctxTopupCard.setCtxReference(cardMaster.getCrmId().toString());

            // Log the CardTransaction object being saved
            log.info("IssueCard -> Topup CardTransaction object : "+ctxTopupCard.toString());

            // Save the CardTransaction object
            ctxTopupCard = cardTransactionService.saveCardTransaction(ctxTopupCard);


            //after topup for normal issue we need to process the promo topup
            //Avoid null pointer exception in existing values
            Integer cardPromoIncentive = cardType.getCrtPromoIncentive() ==null ?0:cardType.getCrtPromoIncentive();

            //check the whether promo topup enabled or not
            if(cardPromoIncentive.intValue() == IndicatorStatus.YES){

                //create new object for card master
                CardMasterTopupRequest cardMasterTopupRequest = new CardMasterTopupRequest();

                cardMasterTopupRequest.setTopupAmount(cardMaster.getCrmCardBalance());

                //calculate incentive value
                Double incentiveAmount = calculatePromoAmount(cardType,cardMasterTopupRequest);

                //if the value not equal zero
                if(incentiveAmount !=null && incentiveAmount.doubleValue() !=0.0){


                   /* //save cardMaster balance
                    Double finalAmount = cardMaster.getCrmCardBalance() + incentiveAmount;

                    log.info("Promo Amount is------>"+finalAmount);

                    //save card master details
                    cardMaster.setCrmCardBalance(finalAmount);
*/
                    cardMaster.setCrmPromoBalance(incentiveAmount);

                    cardMaster = saveCardMaster(cardMaster);

                    CardTransaction ctxTopupCard1 = CardTransactionUtils.getCardTransactionFromCardMaster(cardMaster);

                    ctxTopupCard1.setCtxTxnAmount(incentiveAmount);

                    //save the transaction
                    ctxTopupCard1.setCtxTxnType(CardTransactionType.PROMO_TOPUP);

                    //save the card transaction
                    ctxTopupCard1 = cardTransactionService.saveCardTransaction(ctxTopupCard1);

                    //log the promo toppup infromation
                    log.info("Promo Toppup information In Issue Card --------------->"+ctxTopupCard1);


                }

            }

            // Set the retData to be ctxTopupCard
            retData = ctxTopupCard;

        } else {

            // Set the retData to be ctxIssueCard
            retData = ctxIssueCard;

        }

        CardMasterOperationResponse cardMasterOperationResponse=getProxyCardOperationResponse(retData);

        if(cardMasterOperationResponse!=null){

            HashMap<String , String > smsParams = new HashMap<>(0);

            String cardNumber = cardMasterOperationResponse.getCardnumber();

            //get the last 4 digit
            if(cardNumber !=null && !cardNumber.equals("")){

                cardNumber = cardNumber.length() >4 ? cardNumber.substring(cardNumber.length() -4):cardNumber;
            }

            //split full cardname into 4 digits
            String formatedCardNumber =getFormatedCardNumber(cardMasterOperationResponse.getCardnumber());

            smsParams.put("#cardNumber", cardNumber);


            smsParams.put("#fullCardNumber",formatedCardNumber);
            smsParams.put("#cardHolderName",cardMasterOperationResponse.getName());
            smsParams.put("#txnRef",cardMasterOperationResponse.getTxnref());
            smsParams.put("#txnAmount",cardMasterOperationResponse.getAmount().toString());
            smsParams.put("#txnDate",generalUtils.convertDateToFormat(retData.getCreatedAt(),"dd-MMM-yyyy"));
            smsParams.put("#balance",cardMasterOperationResponse.getBalance().toString());
            smsParams.put("#cardName",cardMasterOperationResponse.getCardtype());

            sendNotificationForChargeCardOperation(cardMaster,smsParams,MessageSpielValue.CHARGE_CARD_ISSUE_RESPONSE,MessageSpielChannel.ALL );
        }
        // Finally return the response object
        return cardMasterOperationResponse;


    }

    private String getFormatedCardNumber(String cardnumber) {

        String format ="";

        for(int i=0;i<cardnumber.length();i++){

            if(i !=0 && (i%4 ==0)){

                format = format+"    "+cardnumber.charAt(i);

            }else {

                format =format+cardnumber.charAt(i);
            }
        }

        log.info("Formated   Card Number +----------"+format);

        return  format;
    }

    private boolean isDuplicateMobile(CardMaster cardMaster) {

        //find the mobile list
        List<CardMaster> cardMasterList = findByCrmMerchantNoAndCrmMobile(cardMaster.getCrmMerchantNo(),cardMaster.getCrmMobile()==null?"":cardMaster.getCrmMobile());

        //check the size of list greater than 0
        if(cardMasterList !=null && cardMasterList.size()>0){

            return true;
        }

        return false;
    }


    private Customer checkCustomerIsExist(CardMaster cardMaster) {

        //find customer based on loyalty id
        Customer customer =customerService.findByCusMobileAndCusMerchantNo(cardMaster.getCrmMobile()==null?"0":cardMaster.getCrmMobile(),cardMaster.getCrmMerchantNo()==null?0l:cardMaster.getCrmMerchantNo());

        //return customer
        return customer;
    }

    private Customer registerCustomerInLoyalty(CardMaster cardMaster,Long userNo) throws InspireNetzException {



        Customer customer=customerService.findByCusLoyaltyIdAndCusMerchantNo(cardMaster.getCrmLoyaltyId(), cardMaster.getCrmMerchantNo());

        //check its exist or not if exist return
        if(customer !=null && customer.getCusRegisterStatus().intValue() ==IndicatorStatus.YES){

            //return is not enable
            log.info("charge card auto registration :Customer Already exist for given loyalty id:"+cardMaster.getCrmCardNo());

            return null;
        }

        //create customer object and register
        Customer newCustomer =registerLoyalty(cardMaster,userNo);

        if(newCustomer ==null || newCustomer.getCusCustomerNo() ==0L){

            //return false
            return null;
        }

        //return
        return newCustomer;

    }

    private Customer registerLoyalty(CardMaster cardMaster,Long userNo) throws InspireNetzException {

        //create customer object
        CustomerResource customer =new CustomerResource();

        //create customer profile
        CustomerProfileResource customerProfileResource=new CustomerProfileResource();
        customer.setCusMobile(cardMaster.getCrmMobile());
        customer.setCusLocation(cardMaster.getCrmLocation()==null?0:cardMaster.getCrmLocation());
        customer.setCusEmail(cardMaster.getCrmEmailId());
        customer.setCusFName(cardMaster.getCrmCardHolderName());
        customer.setCusLoyaltyId(cardMaster.getCrmLoyaltyId());
        customer.setCusIdType(0);
        customer.setCusMerchantNo(cardMaster.getCrmMerchantNo());
        customer.setCusMerchantUserRegistered(userNo);

        customer.setCusIdType(cardMaster.getCusIdType());
        customer.setCusIdNo(cardMaster.getCusIdNo());

        //set profile information
        customerProfileResource.setCspAddress(cardMaster.getCrmAddress());
        customerProfileResource.setCspCity(cardMaster.getCrmCity());
        customerProfileResource.setCspCountry(cardMaster.getCrmCountry());
        customerProfileResource.setCspCustomerBirthday(cardMaster.getCrmDob());
        customerProfileResource.setCspNomineeName(cardMaster.getCrmNomineeName());
        customerProfileResource.setCspNomineeRelation(cardMaster.getCrmNomineeRelation());
        customerProfileResource.setCspNomineeAddress(cardMaster.getCrmNomineeAddress());
        customerProfileResource.setCspNomineeDob(cardMaster.getCrmNomineeDob());
        customerProfileResource.setCspState(cardMaster.getCrmState());
        customerProfileResource.setCspPincode(cardMaster.getCrmPincode());

        //register the customer in loyalty
        Customer customer1 =customerService.saveCustomerDetailsGeneric(customer, customerProfileResource);

        //return customer object
        return customer1;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public CardMasterOperationResponse topupCard(String cardNo,Double amount,Long merchantNo,String reference,Integer paymentMode,String location,boolean awardIncentive,boolean promoTopup,Double incentiveAmount) throws InspireNetzException {


        // Create the CardMasterTopupRequest
        CardMasterTopupRequest cardMasterTopupRequest = new CardMasterTopupRequest();

        // Set the Merchant No
        cardMasterTopupRequest.setMerchantNo(merchantNo);

        Long ctxLocation=authSessionUtils.getUserLocation();

        if(location!=null&& !location.equals("")){

            MerchantLocation merchantLocation=merchantLocationService.findByMelMerchantNoAndMelLocation(merchantNo,location);

            if(merchantLocation!=null){

                ctxLocation=merchantLocation.getMelId();
            }

        }

        // Set the location
        cardMasterTopupRequest.setCtxLocation(ctxLocation);

        //set reference
        cardMasterTopupRequest.setReference(reference);

        // Set the card no
        cardMasterTopupRequest.setCardNo(cardNo);

        // Set the user no
        cardMasterTopupRequest.setUserNo(authSessionUtils.getUserNo());

        // Set the topupamount
        cardMasterTopupRequest.setTopupAmount(amount);

        //Set the payment mode
        cardMasterTopupRequest.setPaymentMode(paymentMode);

        // Log the request information
        log.info("topupCard -> CardMasterTopupRequest : "+cardMasterTopupRequest.toString());

        // First check if the cardMaster information exists for the given card
        // number and merchantNo in the CardMaster object
        CardMaster cardMaster = cardMasterRepository.findByCrmMerchantNoAndCrmCardNo(cardMasterTopupRequest.getMerchantNo(),cardMasterTopupRequest.getCardNo());

        // Check if the cardMaster object is valid
        if ( cardMaster == null || cardMaster.getCrmId() == null ) {

            // Log the information
            log.info("topupCard -> No card master information found");

            // Throw the exception
            throw new InspireNetzException(APIErrorCode.ERR_NO_DATA);

        }

        // Get the CardType information for the cardmaster
        CardType cardType = cardMaster.getCardType();

        // Check if the cardType is null
        // Check if the cardType information is found
        if ( cardType ==  null ) {

            // Log the information
            log.info("topupCard -> No card type information found");

            // Throw the exception
            throw new InspireNetzException(APIErrorCode.ERR_NO_DATA_FOUND);

        }


        // Check if the cardtype is rechargeable
        if ( cardType.getCrtType() != CardTypeType.RECHARGEBLE ) {

            // Log the information
            log.info("topupCard -> Card is not rechargeable. Cannot topup");

            // Throw the exception
            throw new InspireNetzException(APIErrorCode.ERR_OPERATION_NOT_ALLOWED);

        }

        //check the card is active or not
        boolean isCardActive = isActivationDateValid(cardMaster,merchantNo);

        if(!isCardActive){

            // Log the information
            log.info("debitCard -> Card has not yet active. ");

            // Throw the exception
            throw new InspireNetzException(APIErrorCode.ERR_CARD_NOT_ACTIVE);
        }



        // Check if the card is expired
        boolean isExpired = this.isCardExpired(cardType,cardMaster);


        // If the card is expired, then we need to set the cardStatus as expired
        // and then return the error message
        if ( isExpired ) {

            // Set the status to expired
            cardMaster.setCrmCardStatus(CardMasterStatus.EXPIRED);

            // Save the cardmaster
            saveCardMaster(cardMaster);

            // Log the information
            log.info("topupCard -> Card has expired. ");

            // Log the information
            log.info("topupCard -> CardMaster object : " + cardMaster.toString());

            // Throw the exception
            throw new InspireNetzException(APIErrorCode.ERR_CARD_EXPIRED);

        }

        //check card balance is expired or not
        boolean isCardBalanceExpired =isCardBalanceExpired(cardType,cardMaster);

        //if its true process card balance expiry
        if (isCardBalanceExpired){

            //process card balance expiry operation
            CardMaster cardMaster1 =cardBalanceExpiryOperation(cardMaster);

            //if card master 1 is not null
            if (cardMaster1 !=null){

                //if the card balance expired set next topup date after first expiry
                cardMaster =cardMaster1;


            }


        }

        // If any of source or dest card is not active, then the operation cannot be
        // carried out.
        if (  cardMaster.getCrmCardStatus() == CardMasterStatus.RETURNED||cardMaster.getCrmCardStatus() == CardMasterStatus.EXPIRED||cardMaster.getCrmCardStatus()==CardMasterStatus.LOCKED ) {

            // Log the information
            log.info("topupCard -> Card may be expired or locked. ");

            // Throw the exception
            throw new InspireNetzException(APIErrorCode.ERR_OPERATION_NOT_ALLOWED);

        }


        // Check if the topup amount is valid for the vard
        boolean isTopupValid = cardTypeService.isCardValueValid(cardType,cardMasterTopupRequest.getTopupAmount(),cardMaster.getCrmCardBalance(),CardTransactionType.TOPUP);

        // If the card value is not valid, then show message
        if ( !isTopupValid && !promoTopup) {

            // Log the information
            log.info("topupCard -> Topup value is invalid. ");

            // Throw the exception
            throw new InspireNetzException(APIErrorCode.ERR_TOPUP_AMOUNT_INVALID);

        }

        //card balance information
        Integer cardBalanceStatus =cardMaster.getCrmBalExpStatus() ==null?0:cardMaster.getCrmBalExpStatus();

        //check card Balance status
        if(cardBalanceStatus.intValue() ==IndicatorStatus.YES){

            //get current date
            Date currentDate =new Date();

            //update the topup date
            cardMaster.setCrmTopupDate(new Timestamp(currentDate.getTime()));

            //set cardBalance status is set 0
            cardMaster.setCrmBalExpStatus(IndicatorStatus.NO);
        }

        if(promoTopup){

            // Add the promo balance
            cardMaster.setCrmPromoBalance(cardMaster.getCrmPromoBalance() + cardMasterTopupRequest.getTopupAmount());


        } else {

            // Add the card balance
            cardMaster.setCrmCardBalance(cardMaster.getCrmCardBalance() + cardMasterTopupRequest.getTopupAmount());


        }

        cardMaster.setCrmCardStatus(CardMasterStatus.ACTIVE);

        // Save the cardMaster
        cardMaster = saveCardMaster(cardMaster);

        // Create the CardTransaction object
        CardTransaction cardTransaction =  CardTransactionUtils.getCardTransactionFromCardMaster(cardMaster);

        // Set the transaction amount as topupAmount
        cardTransaction.setCtxTxnAmount(cardMasterTopupRequest.getTopupAmount());

        // Set the user no
        cardTransaction.setCtxUserNo(cardMasterTopupRequest.getUserNo());

        //check the topup is normal or promo topup
        if(promoTopup){

           //set the transaction type is promo topup
           cardTransaction.setCtxTxnType(CardTransactionType.PROMO_TOPUP);

        }else {

            //set transaction type is normal topup
            cardTransaction.setCtxTxnType(CardTransactionType.TOPUP);

        }



        // Set the location
        cardTransaction.setCtxLocation(cardMasterTopupRequest.getCtxLocation());

        // Set the reference to crmId
        cardTransaction.setCtxReference(reference);

        cardTransaction.setCtxPaymentMode(cardMasterTopupRequest.getPaymentMode());


        // Save the CardTransaction
        cardTransaction = cardTransactionService.saveCardTransaction(cardTransaction);

        // Log the information
        log.info("topupCard -> CardTransaction object : "+cardTransaction.toString());

        CardMasterOperationResponse cardMasterOperationResponse=getProxyCardOperationResponse(cardTransaction);

        if(cardMasterOperationResponse!=null){

            HashMap<String , String > smsParams = new HashMap<>(0);

            String cardNumber = cardMasterOperationResponse.getCardnumber();

            //get the last 4 digit
            if(cardNumber !=null && !cardNumber.equals("")){

                cardNumber = cardNumber.length() >4 ? cardNumber.substring(cardNumber.length() -4):cardNumber;
            }


            smsParams.put("#cardNumber", cardNumber);
            smsParams.put("#fullCardNumber",cardMasterOperationResponse.getCardnumber());
            smsParams.put("#cardHolderName",cardMasterOperationResponse.getName());
            smsParams.put("#txnRef",cardMasterOperationResponse.getTxnref());
            smsParams.put("#txnAmount",cardMasterOperationResponse.getAmount().toString());
            smsParams.put("#txnDate",generalUtils.convertDateToFormat(cardTransaction.getCreatedAt(),"dd-MMM-yyyy"));
            smsParams.put("#balance",cardMasterOperationResponse.getBalance().toString());
            smsParams.put("#cardName",cardMasterOperationResponse.getCardtype());

            //if the topup is promo topup send the promo topup spiel other wise send normal topup

            if(promoTopup){

                sendNotificationForChargeCardOperation(cardMaster,smsParams,MessageSpielValue.CHARGE_CARD_PROMO_TOPUP_RESPONSE,MessageSpielChannel.ALL );

            }else {

                sendNotificationForChargeCardOperation(cardMaster,smsParams,MessageSpielValue.CHARGE_CARD_TOPUP_RESPONSE,MessageSpielChannel.ALL );

            }

        }

        //we need to check the current card type is available for promo topup
        if(awardIncentive && !promoTopup){

            //if the topup is not promo topup call the process transaction method
            processChargeCardTransactionForLoyalty(cardMaster,cardTransaction);

            CardMasterOperationResponse cardMasterOperationResponse1 = null;

            // Check  if the incentive amount is present in topup request
            if(incentiveAmount != null && incentiveAmount.doubleValue() != 0.0 ){

                //call the promo topup operation
               cardMasterOperationResponse1 = topupCard(cardMasterTopupRequest.getCardNo(),incentiveAmount,cardMasterTopupRequest.getMerchantNo(),cardMasterTopupRequest.getReference(),cardMasterTopupRequest.getPaymentMode(),location,awardIncentive,true,incentiveAmount);

                //set the cardMasteroperation response  main balance after promo topup
                cardMasterOperationResponse.setBalance(cardMasterOperationResponse1.getBalance());

                //set the cardMasteroperation response  main balance after promo topup
                cardMasterOperationResponse.setPromoBalance(cardMasterOperationResponse1.getPromoBalance());

                //set the promo topup done
                cardMasterOperationResponse.setPromoTopup(cardMasterOperationResponse1.getAmount());


                //log the promo topup information
                log.info("Promo Topup Information -------------->"+cardMasterOperationResponse1);

                //if incentive amount is not present, calculate based on card type settings.
            } else {

                //Avoid null pointer exception in existing values
                Integer cardPromoIncentive = cardType.getCrtPromoIncentive() ==null ?0:cardType.getCrtPromoIncentive();

                //check the whether promo topup enabled or notc
                if(cardPromoIncentive.intValue() == IndicatorStatus.YES){

                    //calculate incentive value
                    incentiveAmount = calculatePromoAmount(cardType,cardMasterTopupRequest);

                    //if the value not equal zero
                    if(incentiveAmount !=null && incentiveAmount.doubleValue() !=0.0){

                        //call the promo topup operation
                        cardMasterOperationResponse1 = topupCard(cardMasterTopupRequest.getCardNo(),incentiveAmount,cardMasterTopupRequest.getMerchantNo(),cardMasterTopupRequest.getReference(),cardMasterTopupRequest.getPaymentMode(),location,awardIncentive,true,incentiveAmount);

                        //set the cardMasteroperation response  main balance after promo topup
                        cardMasterOperationResponse.setBalance(cardMasterOperationResponse1.getBalance());

                        //set the cardMasteroperation response  main balance after promo topup
                        cardMasterOperationResponse.setPromoBalance(cardMasterOperationResponse1.getPromoBalance());

                        //set the promo topup done
                        cardMasterOperationResponse.setPromoTopup(cardMasterOperationResponse1.getAmount());

                        //log the promo topup information
                        log.info("Promo Topup Information -------------->"+cardMasterOperationResponse1);

                    }

                }

            }



        }


        // Finally return the response object
        return cardMasterOperationResponse;

    }

    public Double calculatePromoAmount(CardType cardType, CardMasterTopupRequest cardMasterTopupRequest) {

        //check card type is not null and cardType is enable promo topup
        Integer promoIncentive = cardType.getCrtPromoIncentive() ==null ?0:cardType.getCrtPromoIncentive();

        if(cardType !=null && promoIncentive.intValue() ==IndicatorStatus.YES){

            //check the incentive type
            Integer incentiveType = cardType.getCrtPromoIncentiveType() ==null?0:cardType.getCrtPromoIncentiveType();

            //if the incentive type is fixed amount return flat amount
            if(incentiveType.intValue() ==PromoIncentiveType.INCENTIVE_AMOUNT){

                return cardType.getCrtIncentiveAmount() ==null?0.0:cardType.getCrtIncentiveAmount();
            }

            //if the the type is discount percentage calculate amount
            if(incentiveType.intValue() == PromoIncentiveType.INCENTIVE_PERCENTAGE){

                Double incentiveValue = cardType.getCrtIncentiveDiscount() ==null?0.0:cardType.getCrtIncentiveDiscount();

                //calculate percentage
                return  (cardMasterTopupRequest.getTopupAmount() * incentiveValue)/100;
            }

            //if the promo type is tired ration
            if(incentiveType.intValue() == PromoIncentiveType.TIERED_RATIO){

                //calculate incentive Amount
                Double incentiveAmount= calculateTieredRatioIncentiveAmount(cardType, cardMasterTopupRequest);

                return incentiveAmount;
            }

        }
        return 0.0;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public CardMasterOperationResponse refundCard(String cardNo,Double amount,String reference,Long merchantNo,Integer paymentMode,String location,Double promoRefundAmount) throws InspireNetzException {


        // Create the CardMasterTopupRequest
        CardMasterRefundRequest cardMasterRefundRequest = new CardMasterRefundRequest();

        // Set the merchantnO
        cardMasterRefundRequest.setMerchantNo(merchantNo);

        Long userLocation =authSessionUtils.getUserLocation();

        if(location!=null&& !location.equals("")){

            MerchantLocation merchantLocation=merchantLocationService.findByMelMerchantNoAndMelLocation(merchantNo,location);

            if(merchantLocation!=null){

                userLocation=merchantLocation.getMelId();
            }

        }



        // Set the locationc
        cardMasterRefundRequest.setCtxLocation(userLocation);

        // Set the card no
        cardMasterRefundRequest.setCardNo(cardNo);

        // Set the user no
        cardMasterRefundRequest.setUserNo(authSessionUtils.getUserNo());

        // Set the reference
        cardMasterRefundRequest.setReference(reference);

        cardMasterRefundRequest.setRefundAmount(amount);

        cardMasterRefundRequest.setPaymentMode(paymentMode);

        cardMasterRefundRequest.setPromoRefund(promoRefundAmount);

        // Log the request
        log.info("refundCard -> CardMasterRefundRequest : " + cardMasterRefundRequest.toString());


        // First check if the cardMaster information exists for the given card
        // number and merchantNo in the CardMaster object
        CardMaster cardMaster = cardMasterRepository.findByCrmMerchantNoAndCrmCardNo(cardMasterRefundRequest.getMerchantNo(),cardMasterRefundRequest.getCardNo());

        // Check if the cardMaster object is valid
        if ( cardMaster == null || cardMaster.getCrmId() == null ) {

            // Log the information
            log.info("refundCard -> No card master information found");

            // Throw the exception
            throw new InspireNetzException(APIErrorCode.ERR_NO_DATA);

        }



        // Get the CardType information for the cardmaster
        CardType cardType = cardMaster.getCardType();

        // Check if the cardType is null
        // Check if the cardType information is found
        if ( cardType ==  null ) {

            // Log the information
            log.info("refundCard -> No card type information found");

            // Throw the exception
            throw new InspireNetzException(APIErrorCode.ERR_NO_DATA_FOUND);

        }

        //check the card is active or not
        boolean isCardActive = isActivationDateValid(cardMaster,merchantNo);

        if(!isCardActive){

            // Log the information
            log.info("debitCard -> Card has not yet active. ");

            // Throw the exception
            throw new InspireNetzException(APIErrorCode.ERR_CARD_NOT_ACTIVE);
        }


        // Check if the card is expired
        boolean isExpired = this.isCardExpired(cardType,cardMaster);

        // If the card is expired, then we need to set the cardStatus as expired
        // and then return the error message
        if ( isExpired ) {

            // Set the status to expired
            cardMaster.setCrmCardStatus(CardMasterStatus.EXPIRED);

            // Save the cardmaster
            saveCardMaster(cardMaster);


            // Log the information
            log.info("refundCard -> Card has expired. ");

            // Throw the exception
            throw new InspireNetzException(APIErrorCode.ERR_CARD_EXPIRED);

        }

        boolean isCardBalanceExpired =isCardBalanceExpired(cardType,cardMaster);

        //if its true process card balance expiry
        if (isCardBalanceExpired){

            //process card balance expiry operation
            CardMaster cardMaster1 =cardBalanceExpiryOperation(cardMaster);

            //if card master 1 is not null
            if (cardMaster1 !=null){

                cardMaster =cardMaster1;
            }

            log.info("Processing Refund: The Card Balance of Card No :"+cardMaster.getCrmCardNo()+" has been Expired  ");

            CardMasterOperationResponse cardMasterOperationResponse =new CardMasterOperationResponse();

            cardMasterOperationResponse.setTxnref("ERR_CARD_BALANCE_EXPIRED");

            return cardMasterOperationResponse;

        }


        // If any of source or dest card is not active, then the operation cannot be
        // carried out.
        if (  cardMaster.getCrmCardStatus() == CardMasterStatus.RETURNED||cardMaster.getCrmCardStatus() == CardMasterStatus.EXPIRED||cardMaster.getCrmCardStatus()==CardMasterStatus.LOCKED ) {

            // Log the information
            log.info("refundCard -> Card may be expired or locked. ");

            // Throw the exception
            throw new InspireNetzException(APIErrorCode.ERR_OPERATION_NOT_ALLOWED);

        }

        // Check if the card has got sufficient balance for the operation
        if ( cardMaster.getCrmCardBalance() < cardMasterRefundRequest.getRefundAmount() ) {

            // Log the information
            log.info("refundCard -> Insufficient balance. ");

            log.info("Your Current Card Balance is --------->"+cardMaster.getCrmCardBalance());

            // Throw the exception
            throw new InspireNetzException(APIErrorCode.ERR_INSUFFICIENT_BALANCE);


        }


        //Refund the main balance with the promo balance
        boolean isRefunded = refundAmount(cardMaster,cardMasterRefundRequest);

        //Check if refund is success
        if(isRefunded){

            // Set the status
            cardMaster.setCrmCardStatus(CardMasterStatus.REFUNDED);

            // Save the cardMaster
            cardMaster = saveCardMaster(cardMaster);

            // Log the cardmaster
            log.info("refundCard -> CardMaster object : " + cardMaster.toString());

        }
        //Create cardTransaction object
        CardTransaction cardTransaction = null;

        if ( cardMasterRefundRequest.getPromoRefund() != 0.0) {

            // Create the CardTransaction object
            cardTransaction = getCardTransactionForRefund(cardMaster, cardMasterRefundRequest);

            //set the transaction amount
            cardTransaction.setCtxTxnAmount(cardMasterRefundRequest.getPromoRefund());

            //set the transaction balance
            cardTransaction.setCtxCardBalance(cardMaster.getCrmPromoBalance());

            //set the transaction type
            cardTransaction.setCtxTxnType(CardTransactionType.PROMO_REFUND);

            // Save the transaction
            cardTransaction = cardTransactionService.saveCardTransaction(cardTransaction);

            // Log the information
            log.info("refundCard -> PromoCardTransaction object to save -> "+cardTransaction.toString());

            //check if the debited money is from main balance or promo balance
        }if( cardMasterRefundRequest.getRefundAmount() != 0.0) {

            cardTransaction = getCardTransactionForRefund(cardMaster, cardMasterRefundRequest);

            //set the transaction amount
            cardTransaction.setCtxTxnAmount(cardMasterRefundRequest.getRefundAmount());

            //set the transaction balance
            cardTransaction.setCtxCardBalance(cardMaster.getCrmCardBalance());

            //set the transaction type
            cardTransaction.setCtxTxnType(CardTransactionType.REFUND);

            // Save the transaction
            cardTransaction = cardTransactionService.saveCardTransaction(cardTransaction);

            // Log the information
            log.info("refundcard -> CardTransaction object to save -> "+cardTransaction.toString());

        }

        CardMasterOperationResponse cardMasterOperationResponse=getProxyCardOperationResponse(cardTransaction);

        if(cardMasterOperationResponse!=null){

            //set the promo amount refunded in response object
            cardMasterOperationResponse.setPromoRefund(cardMasterRefundRequest.getPromoRefund());

            HashMap<String , String > smsParams = new HashMap<>(0);

            String cardNumber = cardMasterOperationResponse.getCardnumber();

            //get the last 4 digit
            if(cardNumber !=null && !cardNumber.equals("")){

                cardNumber = cardNumber.length() >4 ? cardNumber.substring(cardNumber.length() -4):cardNumber;
            }

            smsParams.put("#cardNumber", cardNumber);
            smsParams.put("#fullCardNumber",cardMasterOperationResponse.getCardnumber());
            smsParams.put("#cardHolderName",cardMasterOperationResponse.getName());
            smsParams.put("#txnRef",cardMasterOperationResponse.getTxnref());
            smsParams.put("#txnAmount",cardMasterOperationResponse.getAmount().toString());
            smsParams.put("#txnDate",generalUtils.convertDateToFormat(cardTransaction.getCreatedAt(),"dd-MMM-yyyy"));
            smsParams.put("#balance",cardMasterOperationResponse.getBalance().toString());
            smsParams.put("#cardName",cardMasterOperationResponse.getCardtype());
            sendNotificationForChargeCardOperation(cardMaster,smsParams,MessageSpielValue.CHARGE_CARD_REFUND_RESPONSE,MessageSpielChannel.ALL );
        }

        //post refund value for awarding loytalty points
        processChargeCardTransactionForLoyalty(cardMaster,cardTransaction);

        // Finally return the response object
        return cardMasterOperationResponse;

    }

    private CardTransaction getCardTransactionForRefund(CardMaster cardMaster, CardMasterRefundRequest cardMasterRefundRequest) {

        // Create the CardTransaction object
        CardTransaction cardTransaction =  CardTransactionUtils.getCardTransactionFromCardMaster(cardMaster);

        // Set the user no
        cardTransaction.setCtxUserNo(cardMasterRefundRequest.getUserNo());

        // Set the location
        cardTransaction.setCtxLocation(cardMasterRefundRequest.getCtxLocation());

        // Set the reference to crmId
        cardTransaction.setCtxReference(cardMasterRefundRequest.getReference());

        cardTransaction.setCtxPaymentMode(cardMasterRefundRequest.getPaymentMode());

        return cardTransaction;
    }

    private Double getPromoTopupAmount(CardMaster cardMaster, CardMasterRefundRequest cardMasterRefundRequest) {

        //get the promo topup amount from transaction
        Double promoTopup = cardTransactionService.countTxnAmount(cardMaster.getCrmMerchantNo(),cardMaster.getCrmCardNo(),CardTransactionType.PROMO_TOPUP);

        //return promo topup amount
        return promoTopup ==null?0.0:promoTopup;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public CardMasterOperationResponse returnCard(String cardNo,String reference,Long merchantNo,Integer paymentMode,String location) throws InspireNetzException {

        // Create the CardMasterTopupRequest
        CardMasterReturnRequest cardMasterReturnRequest = new CardMasterReturnRequest();

        // Set the merchantnO
        cardMasterReturnRequest.setMerchantNo(merchantNo);

        Long userLocation =authSessionUtils.getUserLocation();

        if(location!=null&& !location.equals("")){

            MerchantLocation merchantLocation=merchantLocationService.findByMelMerchantNoAndMelLocation(merchantNo,location);

            if(merchantLocation!=null){

                userLocation=merchantLocation.getMelId();
            }

        }

        // Set the location
        cardMasterReturnRequest.setCtxLocation(userLocation);

        // Set the card no
        cardMasterReturnRequest.setCardNo(cardNo);

        // Set the user no
        cardMasterReturnRequest.setUserNo(authSessionUtils.getUserNo());

        // Set the reference
        cardMasterReturnRequest.setReference(reference);

        cardMasterReturnRequest.setPaymentMode(paymentMode);

        // Log the request
        log.info("returnCard -> CardMasterReturnRequest : " + cardMasterReturnRequest.toString());


        // First check if the cardMaster information exists for the given card
        // number and merchantNo in the CardMaster object
        CardMaster cardMaster = cardMasterRepository.findByCrmMerchantNoAndCrmCardNo(cardMasterReturnRequest.getMerchantNo(),cardMasterReturnRequest.getCardNo());

        // Check if the cardMaster object is valid
        if ( cardMaster == null || cardMaster.getCrmId() == null ) {

            // Log the information
            log.info("returnCard -> No card master information found");

            // Throw the exception
            throw new InspireNetzException(APIErrorCode.ERR_NO_DATA);

        }



        // Get the CardType information for the cardmaster
        CardType cardType = cardMaster.getCardType();

        // Check if the cardType is null
        // Check if the cardType information is found
        if ( cardType ==  null ) {

            // Log the information
            log.info("returnCard -> No card type information found");

            // Throw the exception
            throw new InspireNetzException(APIErrorCode.ERR_NO_DATA_FOUND);

        }

        //check the card is active or not
        boolean isCardActive = isActivationDateValid(cardMaster,merchantNo);

        if(!isCardActive){

            // Log the information
            log.info("returnCard -> Card has not yet active. ");

            // Throw the exception
            throw new InspireNetzException(APIErrorCode.ERR_CARD_NOT_ACTIVE);
        }


        // Check if the card is expired
        boolean isExpired = this.isCardExpired(cardType,cardMaster);

        // If the card is expired, then we need to set the cardStatus as expired
        // and then return the error message
        if ( isExpired ) {

            // Set the status to expired
            cardMaster.setCrmCardStatus(CardMasterStatus.EXPIRED);

            // Save the cardmaster
            saveCardMaster(cardMaster);


            // Log the information
            log.info("returnCard -> Card has expired. ");

            // Throw the exception
            throw new InspireNetzException(APIErrorCode.ERR_CARD_EXPIRED);

        }

        boolean isCardBalanceExpired =isCardBalanceExpired(cardType,cardMaster);

        //if its true process card balance expiry
        if (isCardBalanceExpired){

            //process card balance expiry operation
            CardMaster cardMaster1 =cardBalanceExpiryOperation(cardMaster);

            //if card master 1 is not null
            if (cardMaster1 !=null){

                cardMaster =cardMaster1;
            }

            log.info("Processing Return: The Card Balance of Card No :"+cardMaster.getCrmCardNo()+" has been Expired  ");

            CardMasterOperationResponse cardMasterOperationResponse =new CardMasterOperationResponse();

            cardMasterOperationResponse.setTxnref("ERR_CARD_BALANCE_EXPIRED");

            return cardMasterOperationResponse;

        }

        // Create the CardTransaction object
        CardTransaction cardTransaction =  CardTransactionUtils.getCardTransactionFromCardMaster(cardMaster);

        // Set the transaction amount the refunded amount ( balance of card)
        cardTransaction.setCtxTxnAmount(getTotalCardBalance(cardMaster));

        // Set the balance to 0
        cardTransaction.setCtxCardBalance(0.0);

        // Set the user no
        cardTransaction.setCtxUserNo(cardMasterReturnRequest.getUserNo());

        // Set the location
        cardTransaction.setCtxLocation(cardMasterReturnRequest.getCtxLocation());

        // Set the transaction type as topup
        cardTransaction.setCtxTxnType(CardTransactionType.RETURN);

        cardTransaction.setCtxPaymentMode(cardMasterReturnRequest.getPaymentMode());

        // Set the reference to crmId
        cardTransaction.setCtxReference(cardMasterReturnRequest.getReference());

        // Save the CardTransaction
        cardTransaction = cardTransactionService.saveCardTransaction(cardTransaction);



        // Log the cardTransaction
        log.info("returnCard -> CardTransaction object : " + cardTransaction.toString());


        // Set the cardBalance to 0 and status to refunded
        cardMaster.setCrmCardBalance(0.0);

        // Set the promoBalance to 0 and status to refunded
        cardMaster.setCrmPromoBalance(0.0);

        // Set the status
        cardMaster.setCrmCardStatus(CardMasterStatus.RETURNED);

        // Save the cardMaster
        cardMaster = saveCardMaster(cardMaster);

        // Log the cardmaster
        log.info("returnCard -> CardMaster object : " + cardMaster.toString());

        CardMasterOperationResponse cardMasterOperationResponse=getProxyCardOperationResponse(cardTransaction);

        if(cardMasterOperationResponse!=null){

            HashMap<String , String > smsParams = new HashMap<>(0);

            String cardNumber = cardMasterOperationResponse.getCardnumber();

            //get the last 4 digit
            if(cardNumber !=null && !cardNumber.equals("")){

                cardNumber = cardNumber.length() >4 ? cardNumber.substring(cardNumber.length() -4):cardNumber;
            }


            smsParams.put("#cardNumber",cardNumber);
            smsParams.put("#fullCardNumber",cardMasterOperationResponse.getCardnumber());
            smsParams.put("#cardHolderName",cardMasterOperationResponse.getName());
            smsParams.put("#txnRef",cardMasterOperationResponse.getTxnref());
            smsParams.put("#txnAmount",cardMasterOperationResponse.getAmount().toString());
            smsParams.put("#txnDate",generalUtils.convertDateToFormat(cardTransaction.getCreatedAt(),"dd-MMM-yyyy"));
            smsParams.put("#balance",cardMasterOperationResponse.getBalance().toString());
            smsParams.put("#cardName",cardMasterOperationResponse.getCardtype());
            sendNotificationForChargeCardOperation(cardMaster,smsParams,MessageSpielValue.CHARGE_CARD_RETURN_RESPONSE,MessageSpielChannel.ALL );
        }
        // Finally return the response object
        return cardMasterOperationResponse;

    }



    /**
     * @purpose:make payment using compatible api
     * @param cardNo
     * @param reference
     * @param pin
     * @param amount
     * @param merchantNo
     * @param otpCode
     * @return
     */
    @Override
    public CardMasterOperationResponse debitCardCompatible(String cardNo, String reference, String pin, Double amount, Long merchantNo, String otpCode,Integer paymentMode,String location) throws InspireNetzException {


        //check merchant is enabled or disabled
        Long settingsId =getSettingsId(AdminSettingsConfiguration.MER_ENABLE_CC_DEBIT_OTP);

        //card authentication object
        CardTransferAuthenticationRequest cardTransferAuthenticationRequest =new CardTransferAuthenticationRequest();
        cardTransferAuthenticationRequest.setAuthenticationType(CardAuthType.NOT_APPLICABLE);

        //get merchant settings value
        String settingsValue =getMerchantSettingsValue(merchantNo,settingsId);

        //convert settings value to integer
        Integer settings=Integer.parseInt(settingsValue);

        //if otp is disabled then we don't want any otp validation
        if(settings !=MerchantOtpConfiguration.OTPENABLED){

            //call payment process
            CardMasterOperationResponse cardMasterOperationResponse =debitCard(cardNo,reference,pin,amount,merchantNo,cardTransferAuthenticationRequest,paymentMode,location);

            //return card master response
            return cardMasterOperationResponse;

        }

        //get customer loyalty id from card master using merchant number and card number
        CardMaster cardMaster =cardMasterRepository.findByCrmMerchantNoAndCrmCardNo(merchantNo,cardNo);

        //check card is val
        // id or not
        if(cardMaster ==null){

            log.info("CardMasterOperationResponse ->debitCardCompatible :Invalid Card Number:"+cardNo);

            //throw error invalid card
            throw new InspireNetzException(APIErrorCode.ERR_INVALID_CARD);
        }

        /*//get customer loyalty Id
        String cusLoyaltyId =cardMaster.getCrmLoyaltyId()==null?"":cardMaster.getCrmLoyaltyId();

        //get customer number based on customer loyalty id
        Long cusCustomerNo =getCustomerNo(cusLoyaltyId,merchantNo);*/

        //check otp is valid or not
        // Integer otpStatus =oneTimePasswordService.validateOTP(merchantNo,cusCustomerNo,OTPType.CHARGE_CARD_PAYMENT,otpCode);
        Integer otpStatus =oneTimePasswordService.validateOTPGeneric(merchantNo,OTPRefType.CARDMASTER,cardMaster.getCrmId().toString(),OTPType.CHARGE_CARD_PAYMENT,otpCode);


        //check otpStatus
        if(otpStatus != OTPStatus.VALIDATED){

            log.info("Otp is not valid with status ="+otpStatus);

            //throw new inspireNetz error invalid otp
            throw new InspireNetzException(APIErrorCode.ERR_INVALID_OTP);

        }

        //call payment process
        CardMasterOperationResponse cardMasterOperationResponse =debitCard(cardNo,reference,pin,amount,merchantNo,cardTransferAuthenticationRequest,paymentMode,location);


        //return card master response
        return cardMasterOperationResponse;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public CardMasterOperationResponse refundCardWithOTP(String cardNo,Double amount,String reference,String pin,Long merchantNo,String otpCode,Integer paymentMode,String location,Double promoRefundAmount) throws InspireNetzException {

        //check merchant is enabled or disabled
        Long settingsId =getSettingsId(AdminSettingsConfiguration.MER_ENABLE_CC_REFUND_OTP);

        //card authentication object
        CardTransferAuthenticationRequest cardTransferAuthenticationRequest =new CardTransferAuthenticationRequest();
        cardTransferAuthenticationRequest.setAuthenticationType(CardAuthType.NOT_APPLICABLE);

        //get merchant settings value
        String settingsValue =getMerchantSettingsValue(merchantNo,settingsId);

        //convert settings value to integer
        Integer settings=Integer.parseInt(settingsValue);

        //if otp is disabled then we don't want any otp validation
        if(settings !=MerchantOtpConfiguration.OTPENABLED){

            //call refund process
            CardMasterOperationResponse cardMasterOperationResponse =refundCard(cardNo,amount,reference,merchantNo,paymentMode,location,promoRefundAmount);

            //return card master response
            return cardMasterOperationResponse;

        }

        //get customer loyalty id from card master using merchant number and card number
        CardMaster cardMaster =cardMasterRepository.findByCrmMerchantNoAndCrmCardNo(merchantNo,cardNo);

        //check card is val
        // id or not
        if(cardMaster ==null){

            log.info("CardMasterOperationResponse ->refundCardCompatible :Invalid Card Number:"+cardNo);

            //throw error invalid card
            throw new InspireNetzException(APIErrorCode.ERR_INVALID_CARD);
        }

        //check otp is valid or not
        Integer otpStatus =oneTimePasswordService.validateOTPGeneric(merchantNo,OTPRefType.CARDMASTER,cardMaster.getCrmId().toString(),OTPType.CHARGE_CARD_REFUND,otpCode);


        //check otpStatus
        if(otpStatus != OTPStatus.VALIDATED){

            log.info("Otp is not valid with status ="+otpStatus);

            //throw new inspireNetz error invalid otp
            throw new InspireNetzException(APIErrorCode.ERR_INVALID_OTP);

        }

        //call payment process
        CardMasterOperationResponse cardMasterOperationResponse =refundCard(cardNo,amount,reference,merchantNo,paymentMode,location,promoRefundAmount);


        //return card master response
        return cardMasterOperationResponse;
    }

    @Override
    public CardMasterOperationResponse topupCard(String cardNo, Double amount, Long merchantNo, Integer isLoyaltyPoint, Long rwdCurrencyId,String reference,Integer paymentMode,String location,boolean awardIncentive,Double incentiveAmount) throws InspireNetzException {

        //initialize card master object
        CardMasterOperationResponse cardMasterOperationResponse =new CardMasterOperationResponse();

        //api error code null
        APIErrorCode apiErrorCode =null;

        //check the topup is using loyalty point
        if(isLoyaltyPoint.intValue() ==IndicatorStatus.YES){

            //first check the card is registered in loyalty
            CardMaster cardMaster =findByCrmMerchantNoAndCrmCardNo(merchantNo,cardNo);

            //check card master is null or not
            if(cardMaster ==null){

                log.info("CardMasterServiceImpl->No Card information found");

                throw new InspireNetzException(APIErrorCode.ERR_INVALID_CARD);
            }

            //find customer is register in loyalty
            Customer customer =customerService.findByCusLoyaltyIdAndCusMerchantNo(cardMaster.getCrmLoyaltyId()==null?"0":cardMaster.getCrmLoyaltyId(),merchantNo);

            //check customer is null or not
            if(customer ==null){

                log.info("CardMasterServiceImpl->invalid customer");

                throw new InspireNetzException(APIErrorCode.ERR_NO_LOYALTY_ID);
            }


            //do cash back redemption
            CashBackRedemptionResponse cashBackRedemptionResponse =redemptionService.doCashBackRedemptionForMerchant(cardMaster.getCrmLoyaltyId(),amount,rwdCurrencyId,location,reference);

            //check the status
            if(cashBackRedemptionResponse.getStatus().equals("success")){

                try{

                    //do topup and after that do cash back why because we need to check the card valid
                    cardMasterOperationResponse =topupCard(cardNo,amount,merchantNo,reference,paymentMode,location,awardIncentive,false,incentiveAmount);

                }catch (InspireNetzException ex){


                    log.error("Inspirenetz exception processCardTopup Based on loyalty point :"+ex);

                    //print stack trace
                    ex.printStackTrace();
                    adjustPoint(customer,rwdCurrencyId,amount);

                    //check
                    apiErrorCode =ex.getErrorCode();

                    if(apiErrorCode !=null){

                        //throw inspirenetz exception
                        throw new InspireNetzException(apiErrorCode);
                    }


                }catch (Exception e){

                    log.error("system exception processCardTopup Based on loyalty point :"+e);
                    adjustPoint(customer,rwdCurrencyId,amount);
                    e.printStackTrace();

                }

            }

        }else {

            //operation for normal topup
            cardMasterOperationResponse =topupCard(cardNo,amount,merchantNo,reference,paymentMode,location,awardIncentive,false,incentiveAmount);

        }


        return cardMasterOperationResponse;
    }

    //adjust balance for topup fail
    private  void adjustPoint (Customer customer,Long rwdCurrency,double rewardPoint) throws InspireNetzException {

        customerActivityService.logActivity(customer.getCusLoyaltyId(),CustomerActivityType.CASH_BACK_REDEMPTION,"Cash Back redemption failed",customer.getCusMerchantNo(),"Topup Failed");

        //get the customer to which reward adjustment to be made
        Customer rwdAdjustCustomer = accountBundlingUtils.getPrimaryCustomerForCustomer( customer.getCusMerchantNo() ,customer.getCusLoyaltyId());

        String adLoyaltyId  = rwdAdjustCustomer != null ?rwdAdjustCustomer.getCusLoyaltyId():customer.getCusLoyaltyId();

        //create reward adjustment object
        RewardAdjustment rewardAdjustment = redemptionService.createRewardAdjustmentObject(customer.getCusMerchantNo(), adLoyaltyId, rwdCurrency, rewardPoint, false, 0l, "", "Topup Failed");

        //reverse the points redeemed
        customerRewardBalanceService.awardPointsForRewardAdjustment(rewardAdjustment);


    }

    private boolean isEnoughBalance(Customer customer, Double amount,Long rwdCurrencyId) throws InspireNetzException {


        //check amount is valid or not
        if(amount <=0){

            throw new InspireNetzException(APIErrorCode.ERR_INVALID_AMOUNT);

        }

        //find customer reward balance
        CustomerRewardBalance customerRewardBalance =customerRewardBalanceService.findByCrbLoyaltyIdAndCrbMerchantNoAndCrbRewardCurrency(customer.getCusLoyaltyId(),customer.getCusMerchantNo(),rwdCurrencyId);

        //check reward balance
        if(customerRewardBalance ==null){

            log.info("CardMasterServiceImpl->getCashBackAmount :: No Reward balance entry for this customer loyaltyId:"+customer.getCusLoyaltyId());

            throw new InspireNetzException(APIErrorCode.ERR_INSUFFICIENT_POINT_BALANCE);
        }

        //find rewardCurrency
        RewardCurrency rewardCurrency =getRewardCurrency(rwdCurrencyId);

        //convert customer reward balance point into amount
        double rewardQty = rewardCurrencyService.getCashbackQtyForAmount(rewardCurrency,amount);

        //get actual reward balance of customer
        double totalRewardBalance =rewardCurrencyService.getCashbackQtyForAmount(rewardCurrency,customerRewardBalance.getCrbRewardBalance());

        //check reward qty amount is greater than total balance throw error
        if(rewardQty>totalRewardBalance){

            throw new InspireNetzException(APIErrorCode.ERR_INSUFFICIENT_POINT_BALANCE);

        }

        //else return true
        return true;

    }

    private RewardCurrency getRewardCurrency(Long rwdCurrencyId) throws InspireNetzException {

        //find reward currency
        RewardCurrency rewardCurrency =rewardCurrencyService.findByRwdCurrencyId(rwdCurrencyId);

        //check currency is null or not
        if(rewardCurrency ==null){

            throw new InspireNetzException(APIErrorCode.ERR_INVALID_CURRENCY);
        }

        return  rewardCurrency;

    }

    private String getMerchantSettingsValue(Long merchantNo, Long settingsId) {

        MerchantSetting merchantSetting =merchantSettingService.findByMesMerchantNoAndMesSettingId(merchantNo,settingsId);

        if(merchantSetting ==null){

            log.info("merchant settings is not available nfor this operation");


            return "0";
        }

        return merchantSetting.getMesValue();
    }

    /**
     * @purpose:to get settings information based on settings name
     * @param merchantOtpSettingsName
     * @return
     */
    private Long getSettingsId(String merchantOtpSettingsName) {

        Setting setting = settingService.findBySetName(merchantOtpSettingsName);

        if(setting ==null){

            log.info("Otp enabled settings  not avialable in this merchant");

            //return 0
            return 0L;

        }

        return setting.getSetId()==null?0L:setting.getSetId();
    }

    private Long getCustomerNo(String cusLoyaltyId,Long merchantNo) {

        //find customer information
        Customer customer = customerService.findByCusLoyaltyIdAndCusMerchantNo(cusLoyaltyId,merchantNo);

        //check customer is null or not
        if(customer ==null){

            log.info("CardMasterService->getCustomerNo:Invalid Customer");

            return 0L;
        }

        //return customer number
        return customer.getCusCustomerNo()==null?0L:customer.getCusCustomerNo();
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public CardMasterOperationResponse debitCard(String cardNo, String reference, String pin,Double amount,Long merchantNo,CardTransferAuthenticationRequest cardTransferAuthenticationRequest,Integer paymentMode,String location) throws InspireNetzException{

        // Create the CardMasterDebitRequest
        CardMasterDebitRequest cardMasterDebitRequest = new CardMasterDebitRequest();

        // Set the merchantnO
        cardMasterDebitRequest.setMerchantNo(merchantNo);

        Long userLocation =authSessionUtils.getUserLocation();

        if(location!=null&& !location.equals("")){

            MerchantLocation merchantLocation=merchantLocationService.findByMelMerchantNoAndMelLocation(merchantNo,location);

            if(merchantLocation!=null){

                userLocation=merchantLocation.getMelId();
            }

        }

        // Set the location
        cardMasterDebitRequest.setTxnLocation(userLocation);

        // Set the reference
        cardMasterDebitRequest.setTxnReference(reference);

        // Set the pin
        cardMasterDebitRequest.setCardPin(pin);

        // Set the user no
        cardMasterDebitRequest.setUserNo(authSessionUtils.getUserNo());

        // Set the debit amount
        cardMasterDebitRequest.setDebitAmount(amount);

        // Set the card number
        cardMasterDebitRequest.setCardNo(cardNo);

        cardMasterDebitRequest.setPaymentMode(paymentMode);

        // Log the request information
        log.info("debitCard CardMasterOperationResponse object : " + cardMasterDebitRequest.toString());

        // First check if the cardMaster information exists for the given card
        // number and merchantNo in the CardMaster object
        CardMaster cardMaster = cardMasterRepository.findByCrmMerchantNoAndCrmCardNo(cardMasterDebitRequest.getMerchantNo(),cardMasterDebitRequest.getCardNo());

        // Check if the cardMaster object is valid
        if ( cardMaster == null || cardMaster.getCrmId() == null ) {

            // Log the information
            log.info("debitCard -> No card master information found");

            // Throw the exception
            throw new InspireNetzException(APIErrorCode.ERR_NO_DATA);

        }

        // Get the CardType information for the cardmaster
        CardType cardType = cardMaster.getCardType();

        // Check if the cardType is null
        // Check if the cardType information is found
        if ( cardType ==  null ) {

            // Log the information
            log.info("debitCard -> No card type information found");

            // Throw the exception
            throw new InspireNetzException(APIErrorCode.ERR_NO_DATA_FOUND);

        }

        //check the card is active or not
        boolean isCardActive = isActivationDateValid(cardMaster,merchantNo);

        if(!isCardActive){

            // Log the information
            log.info("debitCard -> Card has not yet active. ");

            // Throw the exception
            throw new InspireNetzException(APIErrorCode.ERR_CARD_NOT_ACTIVE);
        }

        // Check if the card is expired
        boolean isExpired = this.isCardExpired(cardType,cardMaster);



        // If the card is expired, then we need to set the cardStatus as expired
        // and then return the error message
        if ( isExpired ) {

            // Set the status to expired
            cardMaster.setCrmCardStatus(CardMasterStatus.EXPIRED);

            // Save the cardmaster
            saveCardMaster(cardMaster);

            // Log the information
            log.info("debitCard -> Card has expired. ");

            // Throw the exception
            throw new InspireNetzException(APIErrorCode.ERR_CARD_EXPIRED);

        }

        boolean isCardBalanceExpired =isCardBalanceExpired(cardType,cardMaster);

        //if its true process card balance expiry
        if (isCardBalanceExpired ){

            //process card balance expiry operation
            CardMaster cardMaster1 = this.cardBalanceExpiryOperation(cardMaster);

            //if card master 1 is not null
            if (cardMaster1 !=null){

                cardMaster =cardMaster1;
            }

            log.info("Processing Card Debit: The Card Balance of Card No :"+cardMaster.getCrmCardNo()+" has been Expired  ");

        }

        // If any of source or dest card is not active, then the operation cannot be
        // carried out.
        if (  cardMaster.getCrmCardStatus() == CardMasterStatus.RETURNED||cardMaster.getCrmCardStatus() == CardMasterStatus.EXPIRED||cardMaster.getCrmCardStatus()==CardMasterStatus.LOCKED ) {

            // Log the information
            log.info("debit card -> Card may be expired or locked. ");

            // Throw the exception
            throw new InspireNetzException(APIErrorCode.ERR_OPERATION_NOT_ALLOWED);

        }


        // Check if the card has got sufficient balance for the operation
        if ( getTotalCardBalance(cardMaster).doubleValue() < cardMasterDebitRequest.getDebitAmount() ) {

            // Log the information
            log.info("debitCard -> Insufficient balance. ");

            // Throw the exception
            throw new InspireNetzException(APIErrorCode.ERR_INSUFFICIENT_BALANCE);


        }
        // Check if the pin is enabled for the card , We need to verify
        // that the pin is va
        // lid

        if ( cardMaster.getCrmPin() != null && !cardMaster.getCrmPin().equals("")){

            //convert the debit request crm pin to encrypted
            String encCrmPin = getEncodedCrmPin(cardMasterDebitRequest.getCardPin());

            //check the one more request if authentication type
            // Check if the pin is matching
            if (!encCrmPin.equals(cardMaster.getCrmPin()) && (cardTransferAuthenticationRequest.getAuthenticationType().intValue() !=CardAuthType.OTP)) {

                // Log the information
                log.info("debitCard -> Invalid PIN. ");

                // Throw the exception
                throw new InspireNetzException(APIErrorCode.ERR_INVALID_PIN);

            }
        }

        boolean isDebited = debitBalanceBasedOnSettings(cardType,cardMaster,cardMasterDebitRequest);

        //check if is debited
        if( isDebited ) {

            // increment the CardMaster Num txns
            cardMaster.setCrmNumTxns(cardMaster.getCrmNumTxns() + 1);

            cardMaster=saveCardMaster(cardMaster);

//        //check the card balance expired or not
//        boolean isCardBalanceExpired1 =isCardBalanceExpired(cardType,cardMaster);
//
//        //if its true
//        if(isCardBalanceExpired1){
//
//            //process card balance expiry
//            cardBalanceExpiryOperation(cardMaster);
//        }

            // Log the object
            log.info("debitCard -> CardMaster save object : "+cardMaster.toString());

        }


        // Create the CardTransaction object
        CardTransaction cardTransaction = null;

        if ( cardMasterDebitRequest.getPromoDebit() != 0.0) {

            // Create the CardTransaction object
            cardTransaction = getCardTransaction(cardMaster,cardMasterDebitRequest);

            //set the transaction amount
            cardTransaction.setCtxTxnAmount(cardMasterDebitRequest.getPromoDebit());

            //set the transaction balance
            cardTransaction.setCtxCardBalance(cardMaster.getCrmPromoBalance());

            //set the transaction type
            cardTransaction.setCtxTxnType(CardTransactionType.PROMO_DEBIT);

            // Save the transaction
            cardTransaction = cardTransactionService.saveCardTransaction(cardTransaction);

            // Log the information
            log.info("debitCard -> PromoCardTransaction object to save -> "+cardTransaction.toString());

        //check if the debited money is from main balance or promo balance
        }if( cardMasterDebitRequest.getMainDebit() != 0.0) {

            cardTransaction = getCardTransaction(cardMaster,cardMasterDebitRequest);

            //set the transaction amount
            cardTransaction.setCtxTxnAmount(cardMasterDebitRequest.getMainDebit());

            //set the transaction balance
            cardTransaction.setCtxCardBalance(cardMaster.getCrmCardBalance());

            //set the transaction type
            cardTransaction.setCtxTxnType(CardTransactionType.DEBIT);

            // Save the transaction
            cardTransaction = cardTransactionService.saveCardTransaction(cardTransaction);

            // Log the information
            log.info("debitCard -> CardTransaction object to save -> "+cardTransaction.toString());

        }


        CardMasterOperationResponse cardMasterOperationResponse=getProxyCardOperationResponse(cardTransaction);

        if(cardMasterOperationResponse!=null){

            // Set promodebit amount to response object
            cardMasterOperationResponse.setPromoDebit(cardMasterDebitRequest.getPromoDebit());

            HashMap<String , String > smsParams = new HashMap<>(0);

            String cardNumber = cardMasterOperationResponse.getCardnumber();

            //get the last 4 digit
            if(cardNumber !=null && !cardNumber.equals("")){

                cardNumber = cardNumber.length() >4 ? cardNumber.substring(cardNumber.length() -4):cardNumber;
            }

            smsParams.put("#cardNumber", cardNumber);
            smsParams.put("#fullCardNumber",cardMasterOperationResponse.getCardnumber());
            smsParams.put("#cardHolderName",cardMasterOperationResponse.getName());
            smsParams.put("#txnRef",cardMasterOperationResponse.getTxnref());
            smsParams.put("#txnAmount",cardMasterOperationResponse.getAmount().toString());
            smsParams.put("#txnDate",generalUtils.convertDateToFormat(cardTransaction.getCreatedAt(),"dd-MMM-yyyy"));
            smsParams.put("#balance",cardMasterOperationResponse.getBalance().toString());
            smsParams.put("#cardName",cardMasterOperationResponse.getCardtype());
            sendNotificationForChargeCardOperation(cardMaster,smsParams,MessageSpielValue.CHARGE_CARD_PAYMENT_RESPONSE,MessageSpielChannel.ALL );
        }

        if(cardTransaction.getCtxTxnType() == CardTransactionType.DEBIT){

            //post debit value for awarding loyalty points
            processChargeCardTransactionForLoyalty(cardMaster,cardTransaction);

        }

        // Finally return the response object
        return cardMasterOperationResponse;

    }

    @Override
    public CardMasterOperationResponse validateAndIssueCard(CardMaster cardMaster, Long userNo,String userLoginId, boolean isReissue,Integer overrideStatus) throws InspireNetzException {

        //check the user's access rights
        authSessionUtils.validateFunctionAccess(FunctionCode.FNC_ISSUE_CARD);

        //check card master is not a rei
        return issueCard(cardMaster,userNo,userLoginId,isReissue,overrideStatus);
    }

    @Override
    public boolean validateAndDeleteCardMaster(Long crmId,Long merchantNo) throws InspireNetzException {

        //check the user's access rights
        authSessionUtils.validateFunctionAccess(FunctionCode.FNC_DELETE_CARD_TYPE);

        return deleteCardMaster(crmId,merchantNo);
    }

    @Override
    public Page<CardMaster> searchCardMasters(String filter, String query, Long merchantNo,Pageable pageable) {

        Page<CardMaster> cardMasterPage = null;

        // Check the filter type
        if ( filter.equals("0") && query.equals("0") ) {

            // Get the page
            cardMasterPage = findByCrmMerchantNo(merchantNo,pageable);

        } else if ( filter.equalsIgnoreCase("cardno") ) {

            // Get the page
            cardMasterPage = findByCrmMerchantNoAndCrmCardNoLike(merchantNo, "%" + query + "%", pageable);

        } else if ( filter.equalsIgnoreCase("mobile")) {

            // Get the cardMasterPage
            cardMasterPage = findByCrmMerchantNoAndCrmMobileLike(merchantNo, "%" + query + "%", pageable);

        } else if ( filter.equalsIgnoreCase("loyaltyid")) {

            // Get the cardMasterPage
            cardMasterPage = findByCrmMerchantNoAndCrmLoyaltyIdLike(merchantNo, "%" + query + "%", pageable);

        } else if ( filter.equalsIgnoreCase("cardholdername")) {

            // Get the cardMasterPage
            cardMasterPage = findByCrmMerchantNoAndCrmCardHolderNameLike(merchantNo, "%" + query + "%", pageable);

        }

        return cardMasterPage;
    }

    @Override
    public CardMaster getCardMasterInfo(Long crmId, Long merchantNo) throws InspireNetzException {

        // Get the cardMaster information
        CardMaster cardMaster = findByCrmId(crmId);

        // Check if the cardMaster is found
        if ( cardMaster == null || cardMaster.getCrmId() == null) {

            // Log the response
            log.info("getCardMasterInfo - Response : No cardMaster information found");

            // Thrown not found exception
            throw new InspireNetzException(APIErrorCode.ERR_NO_DATA_FOUND);

        }



        // Check if the passed object's merchant number and the current
        // authenticated merchant number are same.
        // We need to raise an error if not
        if ( cardMaster.getCrmMerchantNo().longValue() != merchantNo ) {

            // Log the response
            log.info("getCardMasterInfo - Response : You are not authorized to view the cardMaster");

            // Throw InspireNetzException with ERR_NOT_AUTHORIZED as error
            throw new InspireNetzException(APIErrorCode.ERR_NOT_AUTHORIZED);

        }

        Customer customer=customerService.findByCusLoyaltyIdAndCusMerchantNo(cardMaster.getCrmLoyaltyId(),cardMaster.getCrmMerchantNo());

        if(customer!=null){

            cardMaster.setCusIdType(customer.getCusIdType());

            cardMaster.setCusIdNo(customer.getCusIdNo());
        }

        return cardMaster;
    }

    @Override
    public CardMasterOperationResponse changeCardLockStatus(String cardNo, String status, Long merchantNo) throws InspireNetzException {

        int crmStatus = 0;

        // Check the status. if its equal lock, then set the status as LOCKED
        if ( status.equalsIgnoreCase("lock") ) {

            crmStatus = CardMasterStatus.LOCKED;

        } else if ( status.equalsIgnoreCase("active") ) {

            crmStatus = CardMasterStatus.ACTIVE;

        }

        // Create CardMasterLockStatusRequest
        CardMasterLockStatusRequest cardMasterLockStatusRequest = new CardMasterLockStatusRequest();

        // Set the merchantNo
        cardMasterLockStatusRequest.setMerchantNo(merchantNo);

        // Set the userNo
        cardMasterLockStatusRequest.setUserNo(authSessionUtils.getUserNo());

        // Set the cardNo
        cardMasterLockStatusRequest.setCardNo(cardNo);

        // Set the crmStatus
        cardMasterLockStatusRequest.setLockStatus(crmStatus);

        // Set the location
        cardMasterLockStatusRequest.setCtxLocation(authSessionUtils.getUserLocation());

        CardMasterOperationResponse cardMasterOperationResponse = setCardLockStatus(cardMasterLockStatusRequest);

        return cardMasterOperationResponse;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public CardMasterOperationResponse transferCard(String sourceCardNo, String destCardNo, Long merchantNo,String location) throws InspireNetzException {

        // Create the CardMasterTransferRequest
        CardMasterTransferRequest cardMasterTransferRequest = new CardMasterTransferRequest();

        // Set the merchantnO
        cardMasterTransferRequest.setMerchantNo(merchantNo);

        Long userLocation =authSessionUtils.getUserLocation();

        if(location!=null&& !location.equals("")){

            MerchantLocation merchantLocation=merchantLocationService.findByMelMerchantNoAndMelLocation(merchantNo,location);

            if(merchantLocation!=null){

                userLocation=merchantLocation.getMelId();
            }

        }

        // Set the location
        cardMasterTransferRequest.setCtxLocation(userLocation);

        // Set the source card no
        cardMasterTransferRequest.setSourceCardNo(sourceCardNo);

        // Set the dest card no
        cardMasterTransferRequest.setDestCardNo(destCardNo);

        // Set the user no
        cardMasterTransferRequest.setUserNo(authSessionUtils.getUserNo());

        // Log the requset information
        log.info("transferCard -> CardMasterTransferRequeset : " + cardMasterTransferRequest.toString());

        // First check if the cardMaster information exists for the given source card
        // number and merchantNo in the CardMaster object
        CardMaster sourceCardMaster = cardMasterRepository.findByCrmMerchantNoAndCrmCardNo(cardMasterTransferRequest.getMerchantNo(),cardMasterTransferRequest.getSourceCardNo());

        // Check if the cardMaster object is valid
        if ( sourceCardMaster == null || sourceCardMaster.getCrmId() == null ) {

            // Log the information
            log.info("transferCard -> No source card master information found");

            // Throw the exception
            throw new InspireNetzException(APIErrorCode.ERR_NO_DATA);

        }


        // Get the CardType information for the source card
        CardType sourceCardType = sourceCardMaster.getCardType();

        // Check if the cardType is null
        // Check if the cardType information is found
        if ( sourceCardType ==  null ) {

            // Log the information
            log.info("debitCard -> No card type information found for source card");

            // Throw the exception
            throw new InspireNetzException(APIErrorCode.ERR_NO_DATA_FOUND);

        }


        // Next check if the cardMaster information exists for the given destination card
        // number and merchantNo in the CardMaster object
        CardMaster destCardMaster = cardMasterRepository.findByCrmMerchantNoAndCrmCardNo(cardMasterTransferRequest.getMerchantNo(),cardMasterTransferRequest.getDestCardNo());

        // Check if the cardMaster object is valid
        if ( destCardMaster == null || destCardMaster.getCrmId() == null ) {

            // Log the information
            log.info("transferCard -> No dest card master information found");

            // Throw the exception
            throw new InspireNetzException(APIErrorCode.ERR_NO_DATA);

        }

        // Get the CardType information for the source card
        CardType destCardType = destCardMaster.getCardType();

        // Check if the cardType is null
        // Check if the cardType information is found
        if ( destCardType ==  null ) {

            // Log the information
            log.info("debitCard -> No card type information found for source card");

            // Throw the exception
            throw new InspireNetzException(APIErrorCode.ERR_NO_DATA_FOUND);

        }

        //check both card is active or not
        //check the card is active or not
        boolean isSourceCardActive = isActivationDateValid(sourceCardMaster,merchantNo);

        if(!isSourceCardActive){

            // Log the information
            log.info("debitCard -> Source Card has not yet active. ");

            // Throw the exception
            throw new InspireNetzException(APIErrorCode.ERR_SOURCE_CARD_NOT_ACTIVE);
        }


        //check both card is active or not
        //check the card is active or not
        boolean destinationCard = isActivationDateValid(sourceCardMaster,merchantNo);

        if(!destinationCard){

            // Log the information
            log.info("debitCard -> Destination  Card has not yet active. ");

            // Throw the exception
            throw new InspireNetzException(APIErrorCode.ERR_DESTINATION_CARD_NOT_ACTIVE);
        }

        // Check if the sourCard is expired
        // Check if the card is expired
        boolean isExpired = this.isCardExpired(sourceCardType,sourceCardMaster);

        // If the card is expired, then we need to set the cardStatus as expired
        // and then return the error message
        if ( isExpired ) {

            // Set the status to expired
            sourceCardMaster.setCrmCardStatus(CardMasterStatus.EXPIRED);

            // Save the cardmaster
            saveCardMaster(sourceCardMaster);

            // Log the information
            log.info("transferCard -> Source Card has expired. ");

            // Throw the exception
            throw new InspireNetzException(APIErrorCode.ERR_CARD_EXPIRED);

        }

        //check source card baster information is expired or not
        boolean isCardBalanceExpired =isCardBalanceExpired(sourceCardType,sourceCardMaster);

        //if the card  is expired assign into
        if(isCardBalanceExpired){

            //process card expiry operation
            CardMaster sourceCardMaster1 =cardBalanceExpiryOperation(sourceCardMaster);

            //after that process
            if(sourceCardMaster !=null){

                //throw error
                sourceCardMaster =sourceCardMaster1;
            }


            log.info("Processing transfer card: The Card Balance of Card No :"+sourceCardMaster.getCrmCardNo()+" has been Expired  ");

            CardMasterOperationResponse cardMasterOperationResponse =new CardMasterOperationResponse();

            cardMasterOperationResponse.setTxnref("ERR_CARD_BALANCE_EXPIRED");

            return cardMasterOperationResponse;

        }

        // Check if the sourCard is expired
        // Check if the card is expired
        boolean isDestExpired = this.isCardExpired(destCardType,destCardMaster);

        // If the card is expired, then we need to set the cardStatus as expired
        // and then return the error message
        if ( isDestExpired ) {

            // Set the status to expired
            destCardMaster.setCrmCardStatus(CardMasterStatus.EXPIRED);

            // Save the cardmaster
            saveCardMaster(destCardMaster);

            // Log the information
            log.info("transferCard -> Destination Card has expired. ");

            // Throw the exception
            throw new InspireNetzException(APIErrorCode.ERR_CARD_EXPIRED);

        }

        //check source card baster information is expired or not
        boolean isDestCardBalanceExpired =isCardBalanceExpired(destCardType,destCardMaster);

        //if the card  is expired assign into
        if(isDestCardBalanceExpired){

            //process card expiry operation
            CardMaster destCardMaster1 =cardBalanceExpiryOperation(destCardMaster);

            //after that process
            if(destCardMaster1 !=null){

                //throw error
                destCardMaster =destCardMaster1;
            }



        }

        // If any of source or dest card is not active, then the operation cannot be
        // carried out.
        if ( (sourceCardMaster.getCrmCardStatus() != CardMasterStatus.ACTIVE && sourceCardMaster.getCrmCardStatus() != CardMasterStatus.REFUNDED) || destCardMaster.getCrmCardStatus() == CardMasterStatus.RETURNED||destCardMaster.getCrmCardStatus() == CardMasterStatus.EXPIRED||destCardMaster.getCrmCardStatus()==CardMasterStatus.LOCKED ) {

            // Log the information
            log.info("transferCard -> Source or dest card is not active. ");

            // Throw the exception
            throw new InspireNetzException(APIErrorCode.ERR_OPERATION_NOT_ALLOWED);

        }

        // Check if the topup amount is valid for the vard
        boolean isTransferValid = cardTypeService.isCardValueValid(destCardType,sourceCardMaster.getCrmCardBalance(),destCardMaster.getCrmCardBalance(),CardTransactionType.TRANSFER_TO);

        // If the card value is not valid, then show message
        if ( !isTransferValid ) {

            // Log the information
            log.info("transferCard -> That much amount can't transfer to destination card ");

            // Throw the exception
            throw new InspireNetzException(APIErrorCode.ERR_INVALID_AMOUNT);

        }

        // Set the audit details for sourceCard
        sourceCardMaster.setUpdatedBy(cardMasterTransferRequest.getUserNo().toString());

        // Set the audit details for destCard
        destCardMaster.setUpdatedBy(cardMasterTransferRequest.getUserNo().toString());

        //card balance information
        Integer cardBalanceStatus =destCardMaster.getCrmBalExpStatus() ==null?0:destCardMaster.getCrmBalExpStatus();

        //check card Balance status
        if(cardBalanceStatus.intValue() ==IndicatorStatus.YES){

            //get current date
            Date currentDate =new Date();

            //update the topup date
            destCardMaster.setCrmTopupDate(new Timestamp(currentDate.getTime()));

            //set cardBalance status is set 0
            destCardMaster.setCrmBalExpStatus(IndicatorStatus.NO);
        }

        // Now set the destination card balance as the cardbalance + source balance
        // NOTE : it is important to follow these exact sequence of steps
        destCardMaster.setCrmCardBalance(destCardMaster.getCrmCardBalance() + sourceCardMaster.getCrmCardBalance());

        //set the promo balance
        destCardMaster.setCrmPromoBalance(destCardMaster.getCrmPromoBalance() + sourceCardMaster.getCrmPromoBalance());

        destCardMaster.setCrmCardStatus(CardMasterStatus.ACTIVE);

        // Save the destCardMaster
        destCardMaster = saveCardMaster(destCardMaster);

        // Log the information
        log.info("transferCard -> Destination Card Master " + destCardMaster.toString());


        // Create the CardTransaction object for transfer to
        CardTransaction sourceCardTransaction = CardTransactionUtils.getCardTransactionFromCardMaster(sourceCardMaster);

        // Set the cardBalance as 0
        sourceCardTransaction.setCtxCardBalance(0.0);

        // Set the locatin
        sourceCardTransaction.setCtxLocation(cardMasterTransferRequest.getCtxLocation());

        // Set the transactionType
        sourceCardTransaction.setCtxTxnType(CardTransactionType.TRANSFER_FROM);

        // Set the txnAmount to be the balance of source card
        sourceCardTransaction.setCtxTxnAmount(getTotalCardBalance(sourceCardMaster));

        // Save the source CardTransaction
        sourceCardTransaction = cardTransactionService.saveCardTransaction(sourceCardTransaction);

        // Log the information
        log.info("transferCard -> Source card Transaction " + sourceCardTransaction.toString());


        // Create the CardTransaction object for transfer to
        CardTransaction destCardTransaction = CardTransactionUtils.getCardTransactionFromCardMaster(destCardMaster);

        // Set the cardBalance as 0
        destCardTransaction.setCtxCardBalance(0.0);

        // Set the location
        destCardTransaction.setCtxLocation(cardMasterTransferRequest.getCtxLocation());

        // Set the transactionType
        destCardTransaction.setCtxTxnType(CardTransactionType.TRANSFER_TO);

        // Set the txnAmount to be the balance of source card
        destCardTransaction.setCtxTxnAmount(getTotalCardBalance(sourceCardMaster));

        // Set the final balance as the balance of dest card
        destCardTransaction.setCtxCardBalance(getTotalCardBalance(destCardMaster));

        // Set the reference as the transaction number of the sourceTransaction
        destCardTransaction.setCtxReference(sourceCardTransaction.getCtxTxnNo().toString());

        // Save the source CardTransaction
        destCardTransaction = cardTransactionService.saveCardTransaction(destCardTransaction);

        // Log the information
        log.info("transferCard -> Destination Transaction " + destCardTransaction.toString());

        // Set the source card master status as refunded
        sourceCardMaster.setCrmCardStatus(CardMasterStatus.RETURNED);

        // Set the balance to 0
        sourceCardMaster.setCrmCardBalance(0.0);

        // Set the balance to 0
        sourceCardMaster.setCrmPromoBalance(0.0);

        // Save the information
        sourceCardMaster = saveCardMaster(sourceCardMaster);

        // Log the informaiton
        log.info("transferCard -> Source Card Master : " + sourceCardMaster.toString() );

        // Return the CardTransaction for the destCard
        return getProxyCardOperationResponse(destCardTransaction);


    }

    private CardMasterPublicResponse getCardMasterPublicResponseObject(CardMaster cardMaster){

        CardMasterPublicResponse cardMasterPublicResponse=new CardMasterPublicResponse();

        cardMasterPublicResponse.setCardNumber(cardMaster.getCrmCardNo());

        cardMasterPublicResponse.setBalance(getTotalCardBalance(cardMaster));

        CardType cardType1=cardMaster.getCardType();

        cardMasterPublicResponse.setCardName(cardType1.getCrtName());

        cardMasterPublicResponse.setCardHolderName(cardMaster.getCrmCardHolderName());

        Merchant merchant=merchantService.findByMerMerchantNo(cardMaster.getCrmMerchantNo());

        if(merchant!=null){

            cardMasterPublicResponse.setMerchantName(merchant.getMerMerchantName());
        }

        List<CardTransaction> cardTransactions=cardTransactionService.getCardTransactionCompatible(cardMaster.getCrmCardNo(),cardMaster.getCrmMerchantNo(),5);

        List<CardTransactionPublicResponse> cardTransactionPublicResponses=new ArrayList<CardTransactionPublicResponse>() ;

        for(CardTransaction cardTransaction :cardTransactions){

            CardTransactionPublicResponse cardTransactionPublicResponse=mapper.map(cardTransaction,CardTransactionPublicResponse.class);

            if(merchant!=null){

                cardTransactionPublicResponse.setMerchantName(merchant.getMerMerchantName());
            }
            cardTransactionPublicResponses.add(cardTransactionPublicResponse);
        }

        cardMasterPublicResponse.setCardTransaction(cardTransactionPublicResponses);

        return cardMasterPublicResponse;
    }

    @Override
    public CardMasterPublicResponse issueCardFromPublic(String cardNumber,String pin,String mobile,String cardHolderName,String email,String dob,Long merchantNo,String otpCode,Integer overrideStatus) throws InspireNetzException {


        CardMaster cardMaster=null;

        CardNumberInfo cardNumberInfo=cardNumberInfoService.createCardNumberInfoObject(cardNumber,merchantNo,0L,pin);

        cardNumberInfo=cardNumberInfoService.isValidCardNumber(cardNumberInfo, true);

        if(cardNumberInfo==null){

            log.info("issueCardFromPublic ->Invalid card number "+cardNumber);

            throw new InspireNetzException(APIErrorCode.ERR_INVALID_CARD);

        }

        if(merchantNo==0){

            merchantNo=generalUtils.getDefaultMerchantNo();
        }

        Integer isOtpValid=oneTimePasswordService.validateOTPGeneric(merchantNo,OTPRefType.PUBLIC,mobile,OTPType.PUBLIC_CARD_REGISTER,otpCode);

        //Check th response status
        if(isOtpValid.intValue() == OTPStatus.VALIDATED){

            cardMaster=new CardMaster();
            cardMaster.setCrmCardNo(cardNumberInfo.getCniCardNumber());
            cardMaster.setCrmMerchantNo(cardNumberInfo.getCniMerchantNo());
            cardMaster.setCrmType(cardNumberInfo.getCniCardType());
            cardMaster.setCrmMobile(mobile);
            cardMaster.setCrmCardHolderName(cardHolderName);
            cardMaster.setCrmEmailId(email);

            java.sql.Date date = DBUtils.covertToSqlDate(dob);
            cardMaster.setCrmDob(date);

            if(cardMaster==null){

                log.info("issueCardFromPublic ->Invalid card details"+cardMaster.toString());

                throw new InspireNetzException(APIErrorCode.ERR_INVALID_INPUT);
            }


            // Create the BeanPropertyBindingResult
            BeanPropertyBindingResult crmResult = new BeanPropertyBindingResult(cardMaster,"cardmaster");




            // Check if the result contains errors
            if ( crmResult.hasErrors() ) {

                // Get the validation message
                String messages = dataValidationUtils.getValidationMessages(crmResult);

                // Log the response
                log.info("issueCardFromPublic - Response : Invalid Input - "+ messages);

                // Throw InspireNetzException with INVALID_INPUT as error
                throw new InspireNetzException(APIErrorCode.ERR_INVALID_INPUT,messages);

            }


            CardMasterOperationResponse cardMasterOperationResponse= issueCard(cardMaster, 0L,"", false,overrideStatus);

            if(cardMasterOperationResponse==null){

                return null;

            }else{

                cardMaster=findByCrmMerchantNoAndCrmCardNo(cardMaster.getCrmMerchantNo(),cardMaster.getCrmCardNo());

                if(cardMaster==null){

                    // Log the response
                    log.info("issueCardFromPublic - Response : Card issue failed- ");

                    // Throw InspireNetzException with INVALID_INPUT as error
                    throw new InspireNetzException(APIErrorCode.ERR_NO_DATA_FOUND);
                }

                return getCardMasterPublicResponseObject(cardMaster);

            }

        }else if(isOtpValid.intValue() == OTPStatus.OTP_NOT_VALID){

            //throw exception
            throw new InspireNetzException(APIErrorCode.ERR_INVALID_OTP);

        } else {

            throw new InspireNetzException(APIErrorCode.ERR_OTP_EXPIRED);

        }

    }

    @Override
    public boolean getCardBalanceOTP(String cardNumber,Long merchantNo) throws InspireNetzException{

        CardMaster cardMaster=null;

        if(merchantNo==0){

            cardMaster=findByCrmCardNo(cardNumber);

        }else{

            cardMaster=findByCrmMerchantNoAndCrmCardNo(merchantNo,cardNumber);
        }

        if(cardMaster==null){

            log.error("getCardBalanceOTP : Invalid Card Details ");

            throw new InspireNetzException(APIErrorCode.ERR_INVALID_CARD);
        }

        CardType cardType=cardMaster.getCardType();

        if(cardType==null){

            log.error("getCardBalanceOTP : Invalid Card Type ");

            throw new InspireNetzException(APIErrorCode.ERR_INVALID_CARD_TYPE);
        }

        // Check if the sourCard is expired
        // Check if the card is expired
        boolean isExpired = this.isCardExpired(cardType,cardMaster);

        // If the card is expired, then we need to set the cardStatus as expired
        // and then return the error message
        if ( isExpired ) {

            // Set the status to expired
            cardMaster.setCrmCardStatus(CardMasterStatus.EXPIRED);

            // Save the cardmaster
            saveCardMaster(cardMaster);

            // Log the information
            log.info("getCardBalanceOTP -> Card has expired. ");

            // Throw the exception
            throw new InspireNetzException(APIErrorCode.ERR_CARD_EXPIRED);

        }

        //check source card baster information is expired or not
        boolean isCardBalanceExpired =isCardBalanceExpired(cardType,cardMaster);

        //if the card  is expired assign into
        if(isCardBalanceExpired){

            //process card expiry operation
            CardMaster cardMaster1 =cardBalanceExpiryOperation(cardMaster);

            //after that process
            if(cardMaster !=null){

                //throw error
                cardMaster =cardMaster1;
            }


            log.info("getCardBalanceOTP: The Card Balance of Card No :"+cardMaster.getCrmCardNo()+" has been Expired  ");


        }


        if(cardMaster.getCrmCardStatus()==CardMasterStatus.RETURNED||cardMaster.getCrmCardStatus()==CardMasterStatus.LOCKED||cardMaster.getCrmCardStatus()==CardMasterStatus.EXPIRED){

            log.error("getCardBalanceOTP : Card may be lock or expired ");

            throw new InspireNetzException(APIErrorCode.ERR_INVALID_CARD);
        }

        if(merchantNo==0){

            merchantNo=generalUtils.getDefaultMerchantNo();

        }



        boolean isOtpGenerated=oneTimePasswordService.generateOTPGeneric(merchantNo,OTPRefType.CARDMASTER,cardMaster.getCrmId().toString(),OTPType.CHARGE_CARD_BALANCE_OTP);

        return isOtpGenerated;


    }

    @Override
    public CardMasterPublicResponse getCardBalancePublic(String cardNumber,Long merchantNo,String otpCode) throws InspireNetzException{

        CardMaster cardMaster=null;

        if(merchantNo==0){

            cardMaster=findByCrmCardNo(cardNumber);

        }else{

            cardMaster=findByCrmMerchantNoAndCrmCardNo(merchantNo,cardNumber);
        }

        if(cardMaster==null){

            log.error("getCardBalancePublic : Invalid Card Details ");

            throw new InspireNetzException(APIErrorCode.ERR_INVALID_CARD);
        }

        CardType cardType=cardMaster.getCardType();

        if(cardType==null){

            log.error("getCardBalancePublic : Invalid Card Type ");

            throw new InspireNetzException(APIErrorCode.ERR_INVALID_CARD_TYPE);
        }

        // Check if the sourCard is expired
        // Check if the card is expired
        boolean isExpired = this.isCardExpired(cardType,cardMaster);

        // If the card is expired, then we need to set the cardStatus as expired
        // and then return the error message
        if ( isExpired ) {

            // Set the status to expired
            cardMaster.setCrmCardStatus(CardMasterStatus.EXPIRED);

            // Save the cardmaster
            saveCardMaster(cardMaster);

            // Log the information
            log.info("getCardBalancePublic -> Card has expired. ");

            // Throw the exception
            throw new InspireNetzException(APIErrorCode.ERR_CARD_EXPIRED);

        }

        //check source card baster information is expired or not
        boolean isCardBalanceExpired =isCardBalanceExpired(cardType,cardMaster);

        //if the card  is expired assign into
        if(isCardBalanceExpired){

            //process card expiry operation
            CardMaster cardMaster1 =cardBalanceExpiryOperation(cardMaster);

            //after that process
            if(cardMaster !=null){

                //throw error
                cardMaster =cardMaster1;
            }


            log.info("getCardBalancePublic: The Card Balance of Card No :"+cardMaster.getCrmCardNo()+" has been Expired  ");


        }


        if(cardMaster.getCrmCardStatus()==CardMasterStatus.LOCKED||cardMaster.getCrmCardStatus()==CardMasterStatus.EXPIRED){

            log.error("getCardBalancePublic : Card may be lock or expired ");

            throw new InspireNetzException(APIErrorCode.ERR_INVALID_CARD);
        }

        if(merchantNo==0){

            merchantNo=generalUtils.getDefaultMerchantNo();

        }

        Integer isOtpValid=oneTimePasswordService.validateOTPGeneric(merchantNo,OTPRefType.CARDMASTER,cardMaster.getCrmId().toString(),OTPType.CHARGE_CARD_BALANCE_OTP,otpCode);

        //Check th response status
        if(isOtpValid.intValue() == OTPStatus.VALIDATED){

            return getCardMasterPublicResponseObject(cardMaster);

        }else if(isOtpValid.intValue() == OTPStatus.OTP_NOT_VALID){

            //throw exception
            throw new InspireNetzException(APIErrorCode.ERR_INVALID_OTP);

        } else {

            throw new InspireNetzException(APIErrorCode.ERR_OTP_EXPIRED);

        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean transferAmount(String fromCardNumber, String toCardNumber, Long merchantNo, Double amount,String pin,String reference, Integer cardAuthType,String otpCode,String location) throws InspireNetzException {

        //first debit the card operation
        //check the auth type if auth type is OTP validate OTP else set PIN
        //from account card master details
        CardMaster fromCardMaster = findByCrmCardNo(fromCardNumber);

        CardTransferAuthenticationRequest cardTransferAuthenticationRequest =new CardTransferAuthenticationRequest();

        if(cardAuthType.intValue() ==CardAuthType.PIN){

            cardTransferAuthenticationRequest.setAuthenticationType(CardAuthType.PIN);

        }else if(cardAuthType.intValue() ==CardAuthType.OTP){

            //validate otp
            int validateOtp = oneTimePasswordService.validateOTPGeneric(merchantNo,OTPRefType.CARDMASTER,fromCardMaster.getCrmId()+"",OTPType.CHARGE_CARD_AMOUNT_TRANSFER_OTP,otpCode);

            if(validateOtp == OTPStatus.OTP_NO_DATA || validateOtp ==OTPStatus.OTP_NOT_VALID || validateOtp ==OTPStatus.NEW){

                throw new InspireNetzException(APIErrorCode.ERR_INVALID_OTP);

            }else if(validateOtp ==OTPStatus.EXPIRED) {

                throw new InspireNetzException(APIErrorCode.ERR_OTP_EXPIRED);
            }

            //else set the otp and process the data
            cardTransferAuthenticationRequest.setAuthenticationType(CardAuthType.OTP);
        }


        CardMasterOperationResponse cardMasterDebitOperationResponse = debitCard(fromCardNumber,reference,pin,amount,merchantNo,cardTransferAuthenticationRequest,1,location);

        //to customer
        CardMaster toCardDetails =findByCrmCardNo(toCardNumber);

        log.info("Debited  Amount-- :"+cardMasterDebitOperationResponse.getAmount()+"From Source Account --->"+fromCardNumber);

        //variable to check whether is topup
        boolean awardIncentive = true;

        //variable holding incentive amount
        Double incentiveAmount = 0.0;


        //then process topup operation
        CardMasterOperationResponse cardMasterTopupOperationResponse =null;

        if(cardMasterDebitOperationResponse.getTxnref() !=null && (!cardMasterDebitOperationResponse.getTxnref().equals(""))){

            //topup the amount after debit from source to destination account
            try{

                cardMasterTopupOperationResponse = topupCard(toCardNumber,amount,merchantNo,reference,PaymentMode.CASH,location,awardIncentive,false,incentiveAmount);
            }catch (InspireNetzException ex){

                if(ex.getErrorCode() ==APIErrorCode.ERR_TOPUP_AMOUNT_INVALID){

                    throw new InspireNetzException(APIErrorCode.ERR_INVALID_TRANSFER_AMOUNT);

                }else if(ex.getErrorCode() ==APIErrorCode.ERR_CARD_EXPIRED){

                    throw new InspireNetzException(APIErrorCode.ERR_INVALID_DESTINATION_CARD);
                }

                //else throw exceptions
                throw new InspireNetzException(ex);
            }


        }


        //check the operation status
        if(cardMasterTopupOperationResponse.getTxnref() !=null && !cardMasterDebitOperationResponse.getTxnref().equals("")){

            log.info("Credited   Amount-- :"+cardMasterTopupOperationResponse.getAmount()+"To Destination Account --->"+toCardNumber);

            Long userLocation =authSessionUtils.getUserLocation();

            if(location!=null&& !location.equals("")){

                MerchantLocation merchantLocation=merchantLocationService.findByMelMerchantNoAndMelLocation(merchantNo,location);

                if(merchantLocation!=null){

                    userLocation=merchantLocation.getMelId();
                }

            }

            //added the transaction for transfer amount
            // Create the CardTransaction object
            addCardTransaction(authSessionUtils.getUserNo(), userLocation, CardTransactionType.AMOUNT_TRANSFER_TO, reference, cardMasterDebitOperationResponse.getAmount(),fromCardMaster);

            addCardTransaction(authSessionUtils.getUserNo(), userLocation, CardTransactionType.AMOUNT_TRANSFER_FROM, reference, cardMasterTopupOperationResponse.getAmount(),toCardDetails);

            sendNotificationForSource(fromCardMaster,cardMasterDebitOperationResponse,merchantNo,toCardDetails);
            sendNotificationForDestination(toCardDetails,cardMasterTopupOperationResponse,merchantNo,fromCardMaster);

        }

        //after debit operation process the credit
        return true;
    }

    @Override
    public boolean generateTransferAmountOtp(String crmCardNumber, Long merchantNo) throws InspireNetzException {

        //check the card information
        CardMaster cardMaster =findByCrmCardNo(crmCardNumber);

        if(cardMaster ==null){

            throw new InspireNetzException(APIErrorCode.ERR_INVALID_CARD);
        }

        boolean isOtpGenerated=oneTimePasswordService.generateOTPGeneric(merchantNo,OTPRefType.CARDMASTER,cardMaster.getCrmId()+"",OTPType.CHARGE_CARD_AMOUNT_TRANSFER_OTP);

        return isOtpGenerated;
    }

    private void sendNotificationForDestination(CardMaster toCardDetails, CardMasterOperationResponse cardMasterTopupOperationResponse, Long merchantNo, CardMaster fromCardMaster) throws InspireNetzException {

        //message Wrapper object
        MessageWrapper messageWrapper =new MessageWrapper();
        messageWrapper.setMerchantNo(merchantNo);
        messageWrapper.setIsCustomer(IndicatorStatus.NO);
        messageWrapper.setMobile(toCardDetails.getCrmMobile());

        //create a hash map for sms parameters
        HashMap<String , String > smsParams = new HashMap<>(0);
        smsParams.put("sourceCardNumber",fromCardMaster.getCrmCardNo());
        smsParams.put("sourceCardHolderName",fromCardMaster.getCrmCardHolderName());
        messageWrapper.setSpielName(MessageSpielValue.CARD_AMOUNT_TRANSFER_FROM);
        messageWrapper.setParams(smsParams);
        messageWrapper.setChannel(MessageSpielChannel.ALL);

        //send message
        userMessagingService.transmitNotification(messageWrapper);

    }

    private void sendNotificationForSource(CardMaster fromCardMaster, CardMasterOperationResponse cardMasterDebitOperationResponse,Long merchantNo,CardMaster toCard) throws InspireNetzException {

        //message Wrapper object
        MessageWrapper messageWrapper =new MessageWrapper();
        messageWrapper.setMerchantNo(merchantNo);
        messageWrapper.setIsCustomer(IndicatorStatus.NO);
        messageWrapper.setMobile(fromCardMaster.getCrmMobile());

        //create a hash map for sms parameters
        HashMap<String , String > smsParams = new HashMap<>(0);
        smsParams.put("destCardNumber",toCard.getCrmCardNo());
        smsParams.put("destCardHolderName",toCard.getCrmCardHolderName());
        messageWrapper.setSpielName(MessageSpielValue.CARD_AMOUNT_TRANSFER_TO);
        messageWrapper.setParams(smsParams);
        messageWrapper.setChannel(MessageSpielChannel.ALL);

        //set card issue subject message
        //get the link string of the merchant sms configuration



        //send message
        userMessagingService.transmitNotification(messageWrapper);



    }

    private void sendNotificationForChargeCardOperation(CardMaster cardMaster,HashMap<String , String > smsParams,String spielName,Integer channel ) throws InspireNetzException {

        //message Wrapper object
        MessageWrapper messageWrapper =new MessageWrapper();
        messageWrapper.setMerchantNo(cardMaster.getCrmMerchantNo());
        messageWrapper.setIsCustomer(IndicatorStatus.NO);
        messageWrapper.setMobile(cardMaster.getCrmMobile());
        messageWrapper.setEmailId(cardMaster.getCrmEmailId() ==null?"":cardMaster.getCrmEmailId());
        messageWrapper.setSpielName(spielName);
        messageWrapper.setParams(smsParams);
        messageWrapper.setChannel(channel);

        //send message
        userMessagingService.transmitNotification(messageWrapper);



    }


    private void addCardTransaction(Long userNo, Long userLocation, int transactionType, String reference, Double amount,CardMaster cardMaster) {

        CardTransaction cardTransaction =  CardTransactionUtils.getCardTransactionFromCardMaster(cardMaster);

        // Set the transaction amount as topupAmount
        cardTransaction.setCtxTxnAmount(amount);

        // Set the user no
        cardTransaction.setCtxUserNo(userNo);

        // Set the transaction type as topup
        cardTransaction.setCtxTxnType(transactionType);

        // Set the location
        cardTransaction.setCtxLocation(userLocation);

        // Set the reference to crmId
        cardTransaction.setCtxReference(reference);

        // Save the CardTransaction
        cardTransaction = cardTransactionService.saveCardTransaction(cardTransaction);

        // Log the information
        log.info("Transaction Form Amount Transfer -> CardTransaction object : "+cardTransaction.toString());



    }

    // method to calculate the incentive amount based on tiered ratio.
    public Double calculateTieredRatioIncentiveAmount(CardType cardType, CardMasterTopupRequest cardMasterTopupRequest){

        // Variable holding the ratio
        double ratio = 1;

        //get the current topup amount
        Double topUpAmount = cardMasterTopupRequest.getTopupAmount() ==null?0.0:cardMasterTopupRequest.getTopupAmount();

            if(cardType.getCrtTier1Upto().intValue() == IndicatorStatus.YES){

                //get the crtTier1LimitTo Value
                Double crtTier1LimitToValue = cardType.getCrtTier1LimitTo();

                //check topup amount is less than or equal to limit
                if (topUpAmount.doubleValue() <= crtTier1LimitToValue.doubleValue()){

                     ratio = cardType.getCrtTier1Num()/cardType.getCrtTier1Deno();

                    //return the incentive
                    return topUpAmount * ratio;

                }

            }if (cardType.getCrtTier2Upto().intValue() == IndicatorStatus.YES){

                    if(topUpAmount.doubleValue() <= cardType.getCrtTier2LimitTo().doubleValue()){

                         ratio = cardType.getCrtTier2Num()/cardType.getCrtTier2Deno();

                        //return the incentive
                        return topUpAmount * ratio;
                    }

            }if (cardType.getCrtTier3Upto().intValue() == IndicatorStatus.YES){

                    if(topUpAmount.doubleValue() <= cardType.getCrtTier3LimitTo().doubleValue()){

                         ratio = cardType.getCrtTier3Num()/cardType.getCrtTier3Deno();

                        //return the incentive
                        return topUpAmount * ratio;
                    }

            }if(cardType.getCrtTier4Upto().intValue() == IndicatorStatus.YES){

                     if(topUpAmount <= cardType.getCrtTier4LimitTo().doubleValue()){

                        ratio = cardType.getCrtTier4Num()/cardType.getCrtTier4Deno();

                        //return the incentive
                        return topUpAmount * ratio;
                   }

            }if(cardType.getCrtTier5Upto() == IndicatorStatus.YES){

                if(topUpAmount.doubleValue() != 0.0){

                     ratio = cardType.getCrtTier5Num()/cardType.getCrtTier5Deno();

                    //return the incentive
                    return topUpAmount * ratio;
                }
            }

        return 0.0;

    }


    @Override
    public Sale prepaidCardSaleObject(CardMaster cardMaster, CardTransaction cardTransaction) {

        //get the customer loyalty Id based card master mobile
        Customer customer = customerService.findByCusMobileAndCusMerchantNo(cardMaster.getCrmMobile() ==null?"0":cardMaster.getCrmMobile(),cardMaster.getCrmMerchantNo());

        if(customer ==null){

            //no customer information
            log.info("No Customer Information found ---------->" +cardMaster.toString());

            return null;
        }

        //create sale object
        Sale sale =new Sale();

        //check the cardTransaction type is refund add amount as negative
        if(cardTransaction.getCtxTxnType().intValue() == CardTransactionType.REFUND){

            sale.setSalAmount(-cardTransaction.getCtxTxnAmount());

        }else {

            sale.setSalAmount(cardTransaction.getCtxTxnAmount());

        }

        sale.setSalMerchantNo(cardMaster.getCrmMerchantNo());

        sale.setSalLoyaltyId(customer.getCusLoyaltyId());

        //check the transaction type if the transaction is topup set sale type
        if(cardTransaction.getCtxTxnType().intValue() == CardTransactionType.DEBIT){

            sale.setSalType(SaleType.CHARGE_CARD_DEBIT);

        }else if(cardTransaction.getCtxTxnType().intValue() ==CardTransactionType.TOPUP || cardTransaction.getCtxTxnType().intValue() ==CardTransactionType.REFUND){

            sale.setSalType(SaleType.CHARGE_CARD_TOPUP);
        }

        //set sale location
        sale.setSalLocation(cardTransaction.getCtxLocation());

        //set sale quantity is one
        sale.setSalQuantity(1.0);

        sale.setSalPaymentReference(cardTransaction.getCtxTxnNo()+"");

        sale.setSalPaymentMode(cardTransaction.getCtxPaymentMode());

        //get the sale date and sale time from card transaction time stamp
        String saleDate = DBUtils.getDateFromTimeStamp(cardTransaction.getCtxTxnTimestamp());

        java.sql.Date saleFormatedDate = DBUtils.covertToSqlDate(saleDate);

        sale.setSalDate(saleFormatedDate);

        //get the time object and put sale time
        String salTime  = DBUtils.getTimeFromTimeStamp(cardTransaction.getCtxTxnTimestamp());

        Time time = DBUtils.convertToSqlTime(salTime);

        sale.setSalTime(time);

        log.info("Created Sale object is for prepaid card loyalty--------------"+sale.toString());

        return sale;
    }

    //method for processing transaction based on charge card operation
    @Override
    public void processChargeCardTransactionForLoyalty(CardMaster cardMaster,CardTransaction cardTransaction) throws InspireNetzException {

        //check the charge card loyalty program is enable or not
        boolean isChargeCardLoyaltyEnabled = merchantSettingService.isSettingEnabledForMerchant(AdminSettingsConfiguration.MER_ALLOW_CHARGE_CARD_LOYALTY_PROGRAM,cardMaster.getCrmMerchantNo());

        // If the charge card loyalty is not enabled, then return the control
        if(!isChargeCardLoyaltyEnabled){

            log.info("Merchant Not enabled for charge card loyalty Program -----------");

            return;
        }

        // Get the sale objet
        Sale sale = prepaidCardSaleObject(cardMaster, cardTransaction);

        // Throw the reactor event with the method
        eventReactor.notify(EventReactorCommand.ERC_CHARGE_CARD_SALE_RECORDED, Event.wrap(sale));


    }

    @Override
    @Selector(value= EventReactorCommand.ERC_CHARGE_CARD_SALE_RECORDED,reactor = EventReactor.REACTOR_NAME)
    public void processChargeCardTransactionForLoyaltyAsync(Sale sale) throws InspireNetzException {

        // Save the sale object
        sale = saleService.saveSale(sale);

        // If the sale is saved, then we need to call the method to process the sale
        // for loyalty
        if (sale !=null && sale.getSalId()!= null){

            // Call the process transaction method
            loyaltyEngineService.processTransaction(sale);

        }

    }

    /**
     * @purpose: to calculate the deduction amouont for debit operation based on deduction settings
     * @param cardType
     * @param cardMasterDebitRequest
     * @return
     */


    /**
     * @purpose To calculte the total balance
     * @param cardMaster
     * @return
     */
    private Double getTotalCardBalance(CardMaster cardMaster){

        //calculate the total balance
        Double totalBalance = cardMaster.getCrmCardBalance() + cardMaster.getCrmPromoBalance();

        return totalBalance;

    }

    /*private Double getPromoAmountToDeduct(CardType cardType, CardMasterRefundRequest cardMasterRefundRequest){

       //check if promo incenve is enabled
        Integer promoIncentive = cardType.getCrtPromoIncentive() == null?0 :cardType.getCrtPromoIncentive();

       if(promoIncentive != null && promoIncentive.intValue() == IndicatorStatus.YES){

           //get the incentive type
           Integer promoIncentiveType = cardType.getCrtPromoIncentiveType() == null?0 : cardType.getCrtPromoIncentiveType();

           //check if incentive type is null
           if(promoIncentiveType != null && promoIncentiveType == PromoIncentiveType.)
       }

    }*/


    private CardTransaction getCardTransaction(CardMaster cardMaster, CardMasterDebitRequest cardMasterDebitRequest){

        // Create the CardTransaction object
        CardTransaction cardTransaction =  CardTransactionUtils.getCardTransactionFromCardMaster(cardMaster);

        // Set the user no
        cardTransaction.setCtxUserNo(cardMasterDebitRequest.getUserNo());

        // Set the transaction location
        cardTransaction.setCtxLocation(cardMasterDebitRequest.getTxnLocation());

        // Set the reference to reference passed
        cardTransaction.setCtxReference(cardMasterDebitRequest.getTxnReference());

        cardTransaction.setCtxPaymentMode(cardMasterDebitRequest.getPaymentMode());

        return cardTransaction;

    }

    private Double getPromoAmountForRefund(CardType cardType, CardMasterRefundRequest cardMasterRefundRequest){

        //variable to store promorefund amount
        Double promoRefundAmount = 0.0;

        //variable to hold the promoIncentve type
        Integer promoIncentiveType = 0;

        // Get the refund settings
        Integer refundIncentiveType = cardType.getCrtRefundIncentiveType() == null ? 0 : cardType.getCrtRefundIncentiveType();

        //check if the incentve type is null
        if(refundIncentiveType != null && refundIncentiveType.intValue() == RefundIncentiveType.NO_REFUND_FROM_PROMO ){

            //set promo refund amount to zero
            promoRefundAmount = 0.0;

        }else if ( refundIncentiveType.intValue() == RefundIncentiveType.REFUND_INCENTIVE_PERCENTAGE){

            //calculate the promo refund amount
            promoRefundAmount = (cardMasterRefundRequest.getRefundAmount() * cardType.getCrtRefundIncentivePercentage()) / 100 ;

        }else if (refundIncentiveType.intValue() == RefundIncentiveType.USE_PROMO_TOPUP_SETTINGS) {


            if(cardType.getCrtPromoIncentive() == IndicatorStatus.YES){

                CardMasterTopupRequest cardMasterTopupRequest = new CardMasterTopupRequest();

                // get the promo incentive settings
                promoIncentiveType =  cardType.getCrtPromoIncentiveType() == null ? 0 : cardType.getCrtPromoIncentiveType();

                //check the incentive type
                if(promoIncentiveType != PromoIncentiveType.INCENTIVE_AMOUNT && (promoIncentiveType == PromoIncentiveType.INCENTIVE_PERCENTAGE || promoIncentiveType == PromoIncentiveType.TIERED_RATIO)){

                    //check the incentve type
                    if(promoIncentiveType.intValue() == PromoIncentiveType.INCENTIVE_PERCENTAGE){

                        // Get the promo  debit amount
                        promoRefundAmount  = (cardMasterRefundRequest.getRefundAmount() * cardType.getCrtIncentiveDiscount()) / 100 ;

                    }else if(promoIncentiveType.intValue() == PromoIncentiveType.TIERED_RATIO){

                        // set carmaster topup amount to refund amount to call the method for calculating the promodebit
                        cardMasterTopupRequest.setTopupAmount(cardMasterRefundRequest.getRefundAmount());

                        //call the method
                        promoRefundAmount = calculateTieredRatioIncentiveAmount(cardType, cardMasterTopupRequest);

                    }

                }else {

                    // Set the promo refund amount to zero
                    promoRefundAmount = 0.0;

                }
            }

        }
        // return promo refund amount
        return promoRefundAmount;
    }

    private boolean debitBalanceBasedOnSettings(CardType cardType,CardMaster cardMaster, CardMasterDebitRequest cardMasterDebitRequest) throws InspireNetzException {

        // Variable to store the amount debited from main balance
        Double mainDebitAmount = 0.0;

        // Variable to store the amount debited from main balance
        Double promoDebitAmount = 0.0;

        // Get the debit settings
        Integer debitIncentiveType = cardType.getCrtDebitIncentiveType() == null ? 0 : cardType.getCrtDebitIncentiveType();

        // Check if incentive type is not null
        if( debitIncentiveType != null && debitIncentiveType.intValue() == DebitIncentiveType.DEBIT_MAIN_BALANCE){

           //check amount to be debited is less than main balance
           if( cardMasterDebitRequest.getDebitAmount() > cardMaster.getCrmCardBalance()){

               // Debit entire main balance
               mainDebitAmount = cardMaster.getCrmCardBalance();

               //set main balance to zero
               cardMaster.setCrmCardBalance(0.0);

               //get the remaining amount to be debited from promo balance
               promoDebitAmount = cardMasterDebitRequest.getDebitAmount() - mainDebitAmount;

               // Set the promo balance
               cardMaster.setCrmPromoBalance(cardMaster.getCrmPromoBalance() - promoDebitAmount);

           } else {

               //get the amount to be debited from main balance
               mainDebitAmount = cardMasterDebitRequest.getDebitAmount();

               //set the card balance
               cardMaster.setCrmCardBalance(cardMaster.getCrmCardBalance() - cardMasterDebitRequest.getDebitAmount());

           }

        }else if (debitIncentiveType != null && debitIncentiveType.intValue() == DebitIncentiveType.DEBIT_PROMO_BALANCE) {

            //check amount to be debited is less than main balance
            if( cardMasterDebitRequest.getDebitAmount() > cardMaster.getCrmPromoBalance()){

                // Debit entire main balance
                promoDebitAmount = cardMaster.getCrmPromoBalance();

                //set main balance to zero
                cardMaster.setCrmPromoBalance(0.0);

                //get the remaining amount to be debited from promo balance
                mainDebitAmount = cardMasterDebitRequest.getDebitAmount() - promoDebitAmount;

                // Set the promo balance
                cardMaster.setCrmCardBalance(cardMaster.getCrmCardBalance() - mainDebitAmount);

            } else {

                //store the amount to be debited from promo balance
                promoDebitAmount = cardMasterDebitRequest.getDebitAmount();

                //set the card balance
                cardMaster.setCrmPromoBalance(cardMaster.getCrmPromoBalance() - promoDebitAmount);
            }
        }else if ( debitIncentiveType == IndicatorStatus.NO){

            mainDebitAmount = cardMasterDebitRequest.getDebitAmount();

            if(mainDebitAmount <= cardMaster.getCrmCardBalance()){

                //set the card balance
                cardMaster.setCrmCardBalance(cardMaster.getCrmCardBalance() - cardMasterDebitRequest.getDebitAmount());

            }else {

                //log the info
                log.info("CardMasterdebitrequest - Insufficient Balance, Cannot debit from promo balance");

                //throw an exception
                throw new InspireNetzException(APIErrorCode.ERR_INSUFFICIENT_BALANCE);
            }

        }
        //set the main debit of cardmaster debit request
        cardMasterDebitRequest.setMainDebit(mainDebitAmount);

        //set promo debt of cardmaster debit request
        cardMasterDebitRequest.setPromoDebit(promoDebitAmount);

        return  true;

    }

    /**
     * @purpose Method to check whether user pin is required for debit
     * @param cardMaster
     * @return
     */
    public boolean isUserPinRequiredForDebit(CardMaster cardMaster){

        //variable holding crmpin exists or not
        boolean isPinExists = false;

        //Check if the card master is null
        if (cardMaster != null && cardMaster.getCrmId().intValue() != 0){

            //check if the crmPin exists
            if (cardMaster.getCrmPin() != null && !cardMaster.getCrmPin().equals("")) {

                // Set isPin exists true
                isPinExists = true;

            } else {

                // Set isPin exists false
                isPinExists =  false;
            }

        }
            return isPinExists;
    }


    @Override
    public String getEncodedCrmPin(String crmPin) {

        // Get the realm
        String realm = generalUtils.getDigestAuthenticationRealm();

        // If the realm is null, log error and return null
        if ( realm == null ) {

            // Log the information
            log.info("getEncodedPassword ->  Unable to retrieve the realm"  );

            // Return null
            return null;

        }


        // The encodedPassword
        String encCrmPin= null;


        try {

            // Get the MessageDigest instnace
            MessageDigest messageDigest = null;

            // Get the MD5 instance
            messageDigest = MessageDigest.getInstance("MD5");

            // Create the password string
            String plainString = realm+":"+crmPin;

            // Create the byteArray
            byte digest[] = messageDigest.digest(plainString.getBytes("UTF-8"));

            // Create the string builder with double the size of the digest
            StringBuilder sb = new StringBuilder(2*digest.length);

            // Go through each of the byte and convert to hexadecimal
            for(byte b : digest){

                sb.append(String.format("%02x", b&0xff));

            }

            // Get the encodedPassword from the StringBuilder object
            encCrmPin=sb.toString();


        } catch (NoSuchAlgorithmException e) {

            // Log as error
            log.info("getEncodedPassword -> No such alogirhtm ");

            // Print stack trace
            e.printStackTrace();

        } catch (UnsupportedEncodingException e) {

            // Log as error
            log.info("getEncodedPassword -> Unsupported encoding exception ");

            // Print stack trace
            e.printStackTrace();

        }


        // Return the encoded password
        return encCrmPin;

    }


    private boolean refundAmount(CardMaster cardMaster,CardMasterRefundRequest cardMasterRefundRequest) throws InspireNetzException {

        // Get the promoRefundAmount
        Double promoRefundAmount = cardMasterRefundRequest.getPromoRefund();

        //Check if the promoRefund is valid
        if(promoRefundAmount > cardMaster.getCrmPromoBalance()){

            //deduct promorefundfrom Main
            Double promoRefundAmountDeducted = cardMaster.getCrmPromoBalance();

            //promoRefund need to deduct from main
            Double promoFromMain = promoRefundAmount - promoRefundAmountDeducted;

            //Set crm card balance
            cardMaster.setCrmCardBalance(cardMaster.getCrmCardBalance() - promoFromMain);

            //Add the promoFrom main to promoBalanace
            cardMaster.setCrmPromoBalance(cardMaster.getCrmPromoBalance() + promoFromMain);

            //update the cardMaster
            cardMaster = saveCardMaster(cardMaster);

            // Create the CardTransaction object
            CardTransaction cardTransaction = getCardTransactionForRefund(cardMaster, cardMasterRefundRequest);;

            //set transaction amount
            cardTransaction.setCtxTxnAmount(promoFromMain);

            //Set transaction type
            cardTransaction.setCtxTxnType(CardTransactionType.MOVE_TO_PROMO);

            // Save the transaction
            cardTransaction = cardTransactionService.saveCardTransaction(cardTransaction);

            // Log the information
            log.info("refundCard -> Move promo amount required from Main balance to promo balance -> "+cardTransaction.toString());

        }

        // Refund from Main balance
        cardMaster.setCrmCardBalance(cardMaster.getCrmCardBalance() -  cardMasterRefundRequest.getRefundAmount());

        // Refund from Promo balance
        cardMaster.setCrmPromoBalance(cardMaster.getCrmPromoBalance() - cardMasterRefundRequest.getPromoRefund());

        return true;
    }



    @Override
    public Map<String,Double> getRefundableBalance(Double amount, String cardNo) throws InspireNetzException {

        Map<String,Double> balance = new HashMap<String,Double>();

        //Variable to hold refundable balance
        Double refundableBalance = 0.0;

        //Get the cardinfo
        CardMaster cardMaster = cardMasterRepository.findByCrmCardNo(cardNo);

        //Check if the cardMaster is null
        if(cardMaster == null){

            // throw an exception
            throw new InspireNetzException(APIErrorCode.ERR_NO_DATA_FOUND);

        }

        //Get the cardType
        CardType cardType = cardTypeService.getCardTypeInfo(cardMaster.getCrmType(),cardMaster.getCrmMerchantNo());

        //Create a cardMasterRefundRequest object
        CardMasterRefundRequest cardMasterRefundRequest = new CardMasterRefundRequest();

        cardMasterRefundRequest.setRefundAmount(amount);

        //variable holding promorefund amount
        Double promoRefundAmount = 0.0;

        //check if the card type is null
        if(cardType != null){

            // Get the promo amount to be refund
            promoRefundAmount = getPromoAmountForRefund(cardType,cardMasterRefundRequest);

            // Check if the promo refund amount is valid amount
            if( promoRefundAmount > cardMaster.getCrmPromoBalance()){

                //refund the entire balance from promo balance
                Double promoRefundAmountdeducted= cardMaster.getCrmPromoBalance();

                //calculate the remaining promorefund amount
                Double promoRefundFromMain = promoRefundAmount-promoRefundAmountdeducted;

                // Calculate the total amount to refund
                refundableBalance = cardMasterRefundRequest.getRefundAmount() - promoRefundFromMain;

                // Check if the total refund amount is valid
                if ( refundableBalance.doubleValue() > cardMaster.getCrmCardBalance()){

                    //log the error
                    log.info("CardMasterRefund Response - Insufficient Balance");

                    // Throw an exception
                    throw new InspireNetzException(APIErrorCode.ERR_INSUFFICIENT_BALANCE);

                }

            } else {

                // Set refundable balance as requested refund amount
                refundableBalance = cardMasterRefundRequest.getRefundAmount();

            }

        }

        balance.put("refundableBalance",refundableBalance);
        balance.put("promoRefundAmount",promoRefundAmount);

        return balance;
    }





    @Override
    public Boolean deductAndConvertToPoints(CashBackRequest cashBackRequest, Double pointsRequired) throws InspireNetzException {


        //Get the card balance for the loyalty id
        List<CardMaster> cardMasterList = findByCrmMerchantNoAndCrmLoyaltyIdOrderByCrmIdDesc(cashBackRequest.getMerchantNo(), cashBackRequest.getLoyaltyId());

        Double availablePoints = 0.0;

        Double amountToBeDeduct = 0.0;

        //Get the reward currency
        RewardCurrency rewardCurrency = rewardCurrencyService.findByRwdCurrencyId(cashBackRequest.getRwdCurrencyId());

        //check if the customer has reward balance if so combine both reward balance and converted points and deduct
        //Get the customer reward balance
        CustomerRewardBalance customerRewardBalance = customerRewardBalanceService.findByCrbLoyaltyIdAndCrbMerchantNoAndCrbRewardCurrency(cashBackRequest.getLoyaltyId(),cashBackRequest.getMerchantNo(),cashBackRequest.getRwdCurrencyId());

        //Create a pointReward object for converting wallet balance to points
        PointRewardData pointRewardData = null;

        //card authentication object
        CardTransferAuthenticationRequest cardTransferAuthenticationRequest =new CardTransferAuthenticationRequest();
        cardTransferAuthenticationRequest.setAuthenticationType(CardAuthType.NOT_APPLICABLE);

        // Create a transaction object
        Transaction transaction = new Transaction();

        if(cardMasterList != null && cardMasterList.size() != 0){

            //Get the card master
            CardMaster cardMaster = cardMasterList.get(0);

            //Check if the customer has valid card balance
            if(cardMaster.getCrmCardBalance() != 0.0){

                //Calculate the points available
                availablePoints = rewardCurrencyService.getCashbackQtyForAmount(rewardCurrency,cardMaster.getCrmCardBalance());

                if(availablePoints >= pointsRequired){

                    //Deduct the corresponding amount from card balance
                    amountToBeDeduct = rewardCurrencyService.getCashbackValue(rewardCurrency,pointsRequired);

                    // Get pointRewardData Object for awarding
                    pointRewardData = getPointRewardObject(cashBackRequest,pointsRequired,amountToBeDeduct);

                    // Get transaction object for awarding
                    transaction = getTransactionObject(cashBackRequest,pointsRequired,amountToBeDeduct);

                    //Move the converted points to rewward balance
                    loyaltyEngineService.awardPointsProxy(pointRewardData,transaction);

                    //debit the balance
                    debitCard(cardMaster.getCrmCardNo(),"CTP_"+cashBackRequest.getRef(),cashBackRequest.getPin(),amountToBeDeduct,cardMaster.getCrmMerchantNo(),cardTransferAuthenticationRequest,1,"");




                } else {

                    //Combine available points and check enough balance is there
                    double totalPoints = availablePoints + customerRewardBalance.getCrbRewardBalance();

                    if(totalPoints >= pointsRequired){

                        //Get the points to be deduct from reward balance
                        double crbDeductQty  = pointsRequired - availablePoints;

                        //Set the amount to be deduct as entire card balance
                        amountToBeDeduct = cardMaster.getCrmCardBalance();

                        // Get pointRewardData Object for awarding
                        pointRewardData = getPointRewardObject(cashBackRequest,crbDeductQty,cardMaster.getCrmCardBalance());

                        // Get transaction object for awarding
                        transaction = getTransactionObject(cashBackRequest,crbDeductQty,amountToBeDeduct);

                        //Move the converted points to rewward balance
                        loyaltyEngineService.awardPointsProxy(pointRewardData,transaction);

                        //debit the balance
                        debitCard(cardMaster.getCrmCardNo(),"CTP_"+cashBackRequest.getRef(),cashBackRequest.getPin(),amountToBeDeduct,cardMaster.getCrmMerchantNo(),cardTransferAuthenticationRequest,1,"");



                    } else {

                        //log an error
                        log.info("deductAndConvertToPoints --> Insufficient point balance");

                        // Throw an exception
                        throw new InspireNetzException(APIErrorCode.ERR_INSUFFICIENT_POINT_BALANCE);

                    }
                }

            }

        }

        return true;
    }

    private Transaction getTransactionObject(CashBackRequest cashBackRequest,Double pointsRequired, Double amountToBeDeduct) {


        //Create a transaction object
        Transaction transaction = new Transaction();


        transaction.setTxnInternalRef("CTP"+cashBackRequest.getRef());
        transaction.setTxnExternalRef("CTP"+cashBackRequest.getRef());
        transaction.setTxnType(TransactionType.REWARD_ADUJUSTMENT_AWARDING);
        transaction.setTxnDate(new java.sql.Date(new java.util.Date().getTime()));
        transaction.setTxnRewardExpDt(DBUtils.covertToSqlDate("9999-12-31"));
        transaction.setTxnMerchantNo(cashBackRequest.getMerchantNo());
        transaction.setTxnLoyaltyId(cashBackRequest.getLoyaltyId());
        transaction.setTxnRewardCurrencyId(cashBackRequest.getRwdCurrencyId());
        transaction.setTxnProgramId(0L);
        transaction.setTxnAmount(amountToBeDeduct);
        transaction.setTxnRewardQty(pointsRequired);
        transaction.setTxnStatus(TransactionStatus.PROCESSED);
        transaction.setTxnCrDbInd(CreditDebitInd.CREDIT);
        transaction.setCreatedBy(authSessionUtils.getUserLoginId());

        return transaction;

    }

    private PointRewardData getPointRewardObject(CashBackRequest cashBackRequest, Double pointsRequired, Double amount){

        //Set the values
        PointRewardData pointRewardData = new PointRewardData();

        pointRewardData.setMerchantNo(cashBackRequest.getMerchantNo());
        pointRewardData.setAuditDetails(authSessionUtils.getUserLoginId());

        pointRewardData.setExpiryDt(DBUtils.covertToSqlDate("9999-12-31"));
        pointRewardData.setLoyaltyId(cashBackRequest.getLoyaltyId());
        pointRewardData.setTxnDate(new java.sql.Date(new java.util.Date().getTime()));
        pointRewardData.setProgramId(0L);
        pointRewardData.setRewardQty(pointsRequired);
        pointRewardData.setTxnAmount(amount);
        pointRewardData.setTxnType(TransactionType.REWARD_ADUJUSTMENT_AWARDING);
        pointRewardData.setRwdCurrencyId(cashBackRequest.getRwdCurrencyId());

        return pointRewardData;
    }

}
