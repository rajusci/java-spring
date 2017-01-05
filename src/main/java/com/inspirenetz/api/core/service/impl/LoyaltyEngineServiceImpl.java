package com.inspirenetz.api.core.service.impl;

import com.inspirenetz.api.core.dictionary.*;
import com.inspirenetz.api.core.domain.*;
import com.inspirenetz.api.core.loyaltyengine.CurrentProcessQueue;
import com.inspirenetz.api.core.loyaltyengine.LoyaltyComputation;
import com.inspirenetz.api.util.AccountBundlingUtils;
import com.inspirenetz.api.util.LoyaltyEngineUtils;
import com.inspirenetz.api.core.service.*;
import com.inspirenetz.api.rest.exception.InspireNetzException;
import com.inspirenetz.api.util.GeneralUtils;
import org.dozer.Mapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import reactor.spring.annotation.Selector;

import java.util.*;
import java.util.Date;

/**
 * Created by sandheepgr on 28/3/14.
 */
@Service
public class LoyaltyEngineServiceImpl implements LoyaltyEngineService {

    // Maximum customers to be fetched in a Page
    private static final int MAX_ITEMS_IN_PAGE = 50;

    // The format for the day and month for checking the date for the date triggered
    private static final String DATE_TRIGGERED_DAY_MONTH_FORMAT = "dd-MM";


    private static Logger log = LoggerFactory.getLogger(LoyaltyEngineServiceImpl.class);

    @Autowired
    private LoyaltyProgramService loyaltyProgramService;

    @Autowired
    private CustomerRewardBalanceService customerRewardBalanceService;

    @Autowired
    private CustomerRewardExpiryService customerRewardExpiryService;

    @Autowired
    private ReferrerProgramSummaryService referrerProgramSummaryService;

    @Autowired
    private MerchantProgramSummaryService merchantProgramSummaryService;

    @Autowired
    private MerchantRewardSummaryService merchantRewardSummaryService;

    @Autowired
    private CustomerProgramSummaryService customerProgramSummaryService;

    @Autowired
    private CustomerSummaryArchiveService customerSummaryArchiveService;

    @Autowired
    private RewardCurrencyService rewardCurrencyService;

    @Autowired
    private CustomerProfileService customerProfileService;

    @Autowired
    private TransactionService transactionService;

    @Autowired
    private MerchantService merchantService;

    @Autowired
    private CustomerService customerService;

    @Autowired
    private UserService userService;

    @Autowired
    private NotificationService notificationService;

    @Autowired
    private AccumulatedRewardBalanceService accumulatedRewardBalanceService;

    @Autowired
    private LinkedLoyaltyService linkedLoyaltyService;

    @Autowired
    private LinkedRewardBalanceService linkedRewardBalanceService;

    @Autowired
    private PrimaryLoyaltyService primaryLoyaltyService;

    @Autowired
    private SegmentMemberService segmentMemberService;

    @Autowired
    private Mapper mapper;

    @Autowired
    private LoyaltyEngineUtils loyaltyEngineUtils;

    @Autowired
    private AccountBundlingUtils accountBundlingUtils;

    @Autowired
    private GeneralUtils generalUtils;

    @Autowired
    private AccountBundlingSettingService accountBundlingSettingService;

    @Autowired
    private LoyaltyProgramSkuService loyaltyProgramSkuService;

    @Autowired
    UserMessagingService userMessagingService;

    @Autowired
    CustomerActivityService customerActivityService;

    @Autowired
    CustomerPromotionalEventService customerPromotionalEventService;

    @Autowired
    PromotionalEventService promotionalEventService;

    @Autowired
    CustomerReferralService customerReferralService;

    @Autowired
    private CurrentProcessQueue currentTransactionQueue;


    public LoyaltyEngineServiceImpl() {

       super();

    }


    /**
     * Function to check if the customer is valid for the current loyalty program
     * This will do the basic validation like the customer is existing,
     * customr is active and the
     * eligibility is valid
     *
     * @param loyaltyProgram    - The loyalty program to be checked
     * @param customer          - The customer to be validated
     *
     *
     * @return                  - true if the customer is valid
     *                            false if the customer is not valid
     */
    protected boolean isCustomerValid(LoyaltyProgram loyaltyProgram,Customer customer) {

        // Check if the customer is found
        if ( customer == null ) {

            // Set the Log
            log.info("Loyalty Engine -> isCustomerValid :  No customer information");

            // Return false
            return false;

        }


        // check if the customer is active
        if ( customer.getCusStatus() != CustomerStatus.ACTIVE ) {

            // Set the log
            log.info("Loyalty Engine -> isCustomerValid : Customer is not active ");

            // Return false
            return false;

        }


        // Check if the customer eligibiltiy is all
        // TODO - need to check for the customer segment id after completing the segmentation
        if ( !isCustomerEligibilityValid(loyaltyProgram,customer) ) {

            // Set the Log
            log.info("Loyalty Engine -> isCustomerValid : Eligibility criteria is not valid for the customer");

            // Return false
            return false;

        }


        // return true
        return true;

    }

    /**
     * Function to check if a program is valid
     * Here we check for the program status and program validity period
     * and make sure that the program is valid for today
     *
     * @param loyaltyProgram - The loyalty program to be checked
     * @param processingDate - The date on which we are processing the request
     * @return               - true if the program is valid
     *                         false if not
     */
    protected boolean isProgramValid(LoyaltyProgram loyaltyProgram, Date processingDate) {

        // Check if the program is in active status
        if ( loyaltyProgram.getPrgStatus() != LoyaltyProgramStatus.ACTIVE ) {

            // Log the information
            log.info("Loyalty Engine  -> isProgramValid : Program is not active ");

            // return false
            return false;

        }


        // Check if the program is valid for the date
        if ( processingDate.compareTo(loyaltyProgram.getPrgStartDate()) < 0 ||
                processingDate.compareTo(loyaltyProgram.getPrgEndDate()) > 0 ) {

            // Set the Log
            log.info("Loyalty Engine -> isProgramValid: Loyalty Program period is not valid");

            // Return false
            return false;

        }



        // Finally return true
        return true;

    }

    // Awarding related functions
    @Override
    public boolean isProgramGeneralRulesValid(LoyaltyProgram loyaltyProgram, Sale sale) {


        // Check if the program is valid
        boolean isProgramValid = isProgramValid(loyaltyProgram,sale.getSalDate());

        // If the program is not valid, return false
        if ( !isProgramValid ) {

            // Set the log
            log.info("Loyalty Engine -> isProgramGeneralRulesValid : Loyalty program is not valid ");

            // Return false
            return false;

        }



        // Get the customer
        Customer customer = customerService.findByCusLoyaltyIdAndCusMerchantNo(sale.getSalLoyaltyId(),sale.getSalMerchantNo());

        // check if the customer is valid
        boolean isCustomerValid = isCustomerValid(loyaltyProgram,customer);

        // check if the customer is active
        if ( !isCustomerValid ) {

            // Set the log
            log.info("Loyalty Engine -> isProgramGeneralRulesValid : Customer is not valid ");

            // Return false
            return false;

        }



        // Check the minimum transaction amount
        if ( loyaltyProgram.getPrgMinTxnAmount().intValue() !=0 && sale.getSalAmount() < loyaltyProgram.getPrgMinTxnAmount()  ) {

            // Set the Log
            log.info("Loyalty Engine -> isProgramGeneralRulesValid : Txn amount less than minimum amount");

            // Return false
            return false;

        }


        // Check for the transaction currency
        if ( loyaltyProgram.getPrgTxnCurrency() != 0 && sale.getSalCurrency().intValue() != loyaltyProgram.getPrgTxnCurrency() ) {

            // Set the Log
            log.info("Loyalty Engine -> isProgramGeneralRulesValid : Invalid Transaction Currency");

            // Return false
            return false;

        }


        // Check for the paymentModes
        if ( loyaltyProgram.getPrgPaymentModeInd() == IndicatorStatus.YES  &&
             !generalUtils.isTokenizedValueExists(loyaltyProgram.getPrgPaymentModes(), "#", Integer.toString(sale.getSalPaymentMode()))) {

            // Set the Log
            log.info("Loyalty Engine -> isProgramGeneralRulesValid : Invalid Payment Mode");

            // Return false
            return false;

        }


        // Check for the locations
        if ( loyaltyProgram.getPrgLocationInd() == IndicatorStatus.YES  &&
             !generalUtils.isTokenizedValueExists(loyaltyProgram.getPrgLocations(), "#", sale.getSalLocation().toString())) {

            // Set the Log
            log.info("Loyalty Engine -> isProgramGeneralRulesValid : Invalid Location");

            // Return false
            return false;

        }



        // Check for the transaction channels
        if ( loyaltyProgram.getPrgTxnChannelInd() == IndicatorStatus.YES  &&
             !generalUtils.isTokenizedValueExists(loyaltyProgram.getPrgTxnChannels(),"#",Integer.toString(sale.getSalTxnChannel()))) {

            // Set the Log
            log.info("Loyalty Engine -> isProgramGeneralRulesValid : Invalid transaction channels");

            // Return false
            return false;

        }

        // Check for the active during
        if ( loyaltyProgram.getPrgDaysInd() != LoyaltyProgramActiveDuringInd.ALWAYS ) {

            // Check if the active during is proper
            if ( loyaltyProgram.getPrgDaysInd() == LoyaltyProgramActiveDuringInd.SPECIAL_OCCASION) {

                // Get the CustomerProfile
                CustomerProfile  customerProfile = customerProfileService.findByCspCustomerNo(customer.getCusCustomerNo());

                // If the CustomerProfile object does not exist, then we need to return false
                if ( customerProfile == null ) {

                    // Set the Log
                    log.info("Loyalty Engine -> isProgramGeneralRulesValid : Special Occasion , No customer profile information");

                    // Return false
                    return false;
                }

                // Check the specOccValidity
                boolean isValid = loyaltyEngineUtils.isSpecialOccasionAwardingValid(loyaltyProgram,customerProfile,sale.getSalDate());

                // If the special occasion is not vlaid, then return false
                if ( !isValid ) {

                    // Set the Log
                    log.info("Loyalty Engine -> isProgramGeneralRulesValid : Special location is not valid");

                    // Return false
                    return false;

                }

            } else if ( loyaltyProgram.getPrgDaysInd() == LoyaltyProgramActiveDuringInd.DAYS_OF_WEEK ) {

                // Check if the time params are specified
                if ( loyaltyProgram.getPrgHrsActiveFrom() != null && !loyaltyProgram.getPrgHrsActiveFrom().toString().equals("00:00:00")) {

                    // If the sale time is less than the hrs active from, then the program is not valid
                    if ( sale.getSalTime().compareTo(loyaltyProgram.getPrgHrsActiveFrom()) < 0 ) {

                        // Set the Log
                        log.info("Loyalty Engine -> isProgramGeneralRulesValid : Special days time is less than active from");

                        // Return false
                        return false;

                    }
                }


                // Check of the prgHrsActiveTo
                if ( loyaltyProgram.getPrgHrsActiveTo() != null && !loyaltyProgram.getPrgHrsActiveTo().toString().equals("00:00:00")) {

                    // If the sale time is greater than the hrs active to , then the program is not valid
                    if ( sale.getSalTime().compareTo(loyaltyProgram.getPrgHrsActiveTo()) > 0 ){

                        // Set the Log
                        log.info("Loyalty Engine -> isProgramGeneralRulesValid : Time is greater than active to");

                        // Return false
                        return false;
                    }
                }

                // Get the day of week
                int dayOfWeek = generalUtils.getDayOfWeek(new Date(sale.getSalDate().getTime()));

                // Check the day of the week
                if ( !generalUtils.isTokenizedValueExists(loyaltyProgram.getPrgDays(),"#",Integer.toString(dayOfWeek))) {

                    // Set the Log
                    log.info("Loyalty Engine -> isProgramGeneralRulesValid : Invalid day of week");

                    // Return false
                    return false;

                }

            }

        }

        //program valid for awarding frequency
        boolean isPgmValidForFrequency =isValidForFrequency(loyaltyProgram,sale,customer);

        //not valid return false
        if(!isPgmValidForFrequency){

            return false;
        }


        // Return true finally
        return true;

    }

    private boolean isValidForFrequency(LoyaltyProgram loyaltyProgram, Sale sale, Customer customer) {

        //check the value is zero or not if 0 return true
        if (loyaltyProgram.getPrgAwardingTime() ==null || loyaltyProgram.getPrgAwardingTime().intValue() ==0){

            return true;
        }

        //check awarding frequency  if the value is 1 return true
        if(loyaltyProgram.getPrgAwardingTime().intValue() ==ProgramAwardingTime.EVERY_PURCHASE){

            return true;
        }

        if (loyaltyProgram.getPrgAwardingTime().intValue() ==ProgramAwardingTime.END_OF_PROGRAM){

            return true;
        }

        if(loyaltyProgram.getPrgAwardingTime().intValue() == ProgramAwardingTime.FIRST_PURCHASE){


            //check the program driver of the loyalty program
            if(loyaltyProgram.getPrgProgramDriver() ==LoyaltyProgramDriver.TRANSACTION_AMOUNT || loyaltyProgram.getPrgProgramDriver() ==LoyaltyProgramDriver.PRODUCT_BASED){

                //check the role of the program
                if (loyaltyProgram.getPrgRole().intValue() ==LoyaltyRefferalRoles.REFFERRER){

                    //find out the referral awarding frequency
                    CustomerReferral customerReferral =getReferralInformation(customer);

                    if(customerReferral !=null){

                        //get the frequency value
                        ReferrerProgramSummary referrerProgramSummary= referrerProgramSummaryService.findByRpsMerchantNoAndRpsRefereeLoyaltyIdAndRpsProgramId(customer.getCusMerchantNo(),customer.getCusLoyaltyId(), loyaltyProgram.getPrgProgramNo());

                        //boolean
                        if (referrerProgramSummary ==null || referrerProgramSummary.getRpsProgramVisit() <1){

                            return true;
                        }
                    }

                    //other wise return false
                    return false;
                }
            }

            //get the visit count from customer reward summary table
            CustomerProgramSummary customerProgramSummary = customerProgramSummaryService.findByCpsMerchantNoAndCpsLoyaltyIdAndCpsProgramId(sale.getSalMerchantNo(),sale.getSalLoyaltyId(), loyaltyProgram.getPrgProgramNo());

            //no of visit is equal to number of transaction
            if(customerProgramSummary ==null || customerProgramSummary.getCpsProgramVisit() < 1){

                return true;
            }


        }

        if(loyaltyProgram.getPrgAwardingTime().intValue() == ProgramAwardingTime.NO_OF_PURCHASE){

            //check the loyalty program is transactional based
            //check the program driver of the loyalty program
            if(loyaltyProgram.getPrgProgramDriver() ==LoyaltyProgramDriver.TRANSACTION_AMOUNT || loyaltyProgram.getPrgProgramDriver() ==LoyaltyProgramDriver.PRODUCT_BASED){

                //check the role of the program
                if (loyaltyProgram.getPrgRole().intValue() ==LoyaltyRefferalRoles.REFFERRER){

                    //find out the referral awarding frequency
                    CustomerReferral customerReferral =getReferralInformation(customer);

                    if(customerReferral !=null){

                        //get the frequency value
                        ReferrerProgramSummary referrerProgramSummary= referrerProgramSummaryService.findByRpsMerchantNoAndRpsRefereeLoyaltyIdAndRpsProgramId(customer.getCusMerchantNo(),customer.getCusLoyaltyId(), loyaltyProgram.getPrgProgramNo());

                        //boolean
                        if (referrerProgramSummary ==null || referrerProgramSummary.getRpsProgramVisit() <= loyaltyProgram.getPrgAwardFreq().intValue()){

                            return true;
                        }
                    }

                    //other wise return false
                    return false;
                }
            }

            //get the visit count from customer reward summary table
            CustomerProgramSummary customerProgramSummary = customerProgramSummaryService.findByCpsMerchantNoAndCpsLoyaltyIdAndCpsProgramId(sale.getSalMerchantNo(), sale.getSalLoyaltyId(), loyaltyProgram.getPrgProgramNo());

            //no of visit is equal to number of transaction
            if( customerProgramSummary ==null || customerProgramSummary.getCpsProgramVisit() <= loyaltyProgram.getPrgAwardFreq().intValue() ){

                return true;
            }

        }


        return false;


    }

    private CustomerReferral getReferralInformation(Customer customer) {

        //get referral information
        List<CustomerReferral> customerReferrals = customerReferralService.findByCsrMerchantNoAndCsrRefMobileAndCsrRefStatusOrderByCsrRefTimeStampAsc(customer.getCusMerchantNo(), customer.getCusMobile(), CustomerReferralStatus.PROCESSED);

        //get the first item of referral list duplicate referral is not allowed in the system
        if(customerReferrals !=null && customerReferrals.size()>0){

            //get first item into the list
            CustomerReferral customerReferral = customerReferrals.get(0);

            return customerReferral;

        }


        return null;
    }



    /**
     * Function to check if the customerEligibility criteria set in the LoyaltyProgram table
     * is valid for the customer
     *
     * @param loyaltyProgram    - The LoyaltyProgram for that need to be checked
     * @param customer          - The customer object to be checked
     *
     * @return                  - Return true if the criteria is valid for the customer
     *                            Return false otherwise
     */
    protected boolean isCustomerEligibilityValid(LoyaltyProgram loyaltyProgram,Customer customer) {

        // Check if the customer type is valid for the loyalty program
        if  ( loyaltyProgram.getPrgCustomerType() != null && ( loyaltyProgram.getPrgCustomerType() != 0 && loyaltyProgram.getPrgCustomerType().intValue() != customer.getCusType().intValue()) ) {

            // Log the information
            log.info("LoyaltyEngine -> isCustomerEligibilityValid : Customer type is not valid");

            // return false
            return false;

        }


        // Check the eligibility type for the loyalty program
        switch ( loyaltyProgram.getPrgEligibleCustType() ) {

            case LoyaltyProgramEligibleCustomerType.ALL_CUSTOMERS :

                // Return true
                return true;


            case LoyaltyProgramEligibleCustomerType.SPECIFIC_SEGMENT:

                // Get the current segment for the customer
                List<SegmentMember> segmentMemberList =  segmentMemberService.findBySgmCustomerNo(customer.getCusCustomerNo());

                // Check if the list is emtpy
                if ( segmentMemberList == null || segmentMemberList.isEmpty() ) {

                    // Return false
                    return false;

                }

                // Convert to map
                Map<Long,SegmentMember> segmentMap = segmentMemberService.getSegmentMemberMapBySegmentId(segmentMemberList);

                // Check if the customer is member of the segment selected in the loyaltyProgrma
                return segmentMap.containsKey(loyaltyProgram.getPrgEligibleCustSegmentId());


            case LoyaltyProgramEligibleCustomerType.SPECIFIC_TIER :

                // Get the effective tier for the customer
                Tier cusTier = accountBundlingUtils.getEffectiveTierForCustomer(customer);

                // If the tier is null, return false
                return cusTier != null && cusTier.getTieId().longValue() == loyaltyProgram.getPrgEligibleCusTier().longValue();


        }


        // Return false
        return false;

    }

    /**
     * Function to check if the customer is active to be awarded
     *
     * @param loyaltyId     - Loyalty id of the customer
     * @param merchantNo    - Merchant number of the customer
     *
     * @return              - Return true if the customer is existing and active
     *                        Return false otherwise
     */
    @Override
    public boolean isCustomerActive(String loyaltyId,Long merchantNo) {

        // Get the customer information for the given loyaltyId
        Customer customer = customerService.findByCusLoyaltyIdAndCusMerchantNo(loyaltyId,merchantNo);

        // If the customer is null or the status is not active, then we need to return false
        if ( customer == null || customer.getCusStatus() != CustomerStatus.ACTIVE ) {

            return false;

        }

        // Finally return true
        return true;

    }

    @Override
    public boolean processTransaction(Sale sale) {

        // Set the log
        log.info("LoyaltyEngine -> processTranscation Starting for sale : " + sale.toString());

        // As a preliminary check, we need to make sure that the customer is active
        if ( !isCustomerActive(sale.getSalLoyaltyId(),sale.getSalMerchantNo()) ) {

            // Log information
            log.info("LoyaltyEngine -> processTransaction : Customer is not valid .. Skipping processing");

            // Return false
            return false;

        }

        /*
        // Update the SummaryArchive tables
        boolean isUpdated = updateCustomerSummaryArchive(pointRewardData);

        // Check if the CustomerSummaryArchive tables have been updated
        if ( !isUpdated ) {

            // Log the response
            log.error("LoyaltyEngine -> processTranscation : CustomerSummaryArchive update failed");

            // Return false
            return false;

        }
        */

        // Get the list of loyalty programs active for the current merchant
        List<LoyaltyProgram> loyaltyProgramList = loyaltyProgramService.findByPrgMerchantNoAndPrgStatus(sale.getSalMerchantNo(), LoyaltyProgramStatus.ACTIVE);

        // Go through each of the loyalty program
        for (LoyaltyProgram loyaltyProgram : loyaltyProgramList ) {

            // Log the starting of processing
            log.info("LoyaltyEngine -> processTranscation : Processing for program "+ loyaltyProgram.toString());

            try {

                // Get the PointRewardData object for the sale
                PointRewardData pointRewardData = getPointRewardDataObjectForTransaction(sale);

                // Call the program rewarding function to check the points
                boolean isProgramRewardingSuccessful = processProgramRewarding(loyaltyProgram,sale,pointRewardData);



                // If the program rewarding is not successful, then we need to set the data
                if ( !isProgramRewardingSuccessful ) {

                    log.info("LoyaltyEngine -> processTranscation : Program rewarding did not happen");

                } else {

                    log.info("LoyaltyEngine -> processTranscation : Program rewarding successfully completed for program No : " + loyaltyProgram.getPrgProgramNo().toString());

                }


            } catch (InspireNetzException e) {

                log.error("LoyaltyEngine -> processTranscation -> programRewarding failed "+ e.getCause().toString());

            }

        }

        // Return true
        return true;
    }

    protected  void controlConcurrency(String key,String action) {

        // Wait the thread as long as another sale is in progress
        while (!currentTransactionQueue.addCurrentItem(key)) {

            // Log the info
            log.error("Sleeping thread for concurrency - "+action+ ": Key: " + key + " - Thread : " + Thread.currentThread().getName());

            try {

                // check if the customer is in the map
                Thread.sleep(1000);

            } catch (InterruptedException e) {

                // Log the error
                log.error("Concurrency block interrupted");

                // Print the stack trace
                e.printStackTrace();

            }
        }
    }

    @Transactional(rollbackFor = {Exception.class,InspireNetzException.class})
    @Override
    public boolean processProgramRewarding(LoyaltyProgram loyaltyProgram, Sale sale,PointRewardData pointRewardData) throws InspireNetzException {

        // Sanitize the LoyaltyProgram object
        loyaltyProgram = loyaltyEngineUtils.clearLoyaltyProgramFieldsFromNull(loyaltyProgram);

        // Get the LoyaltyComputation object for the loyalty program
        LoyaltyComputation loyaltyComputation = loyaltyProgramService.getLoyaltyComputation(loyaltyProgram);


        // If the loyalty computation is not specified, then we need to
        // return the processing
        if ( loyaltyComputation == null ) {

            // Log the information
            log.info("LoyaltyEngine -> processProgramAwarding -> No computation found");

            // return false
            return false;

        }

        // Check if the general rules and filtering are valid
        if ( !isProgramGeneralRulesValid(loyaltyProgram,sale)) {

            // Set the loyalty program
            log.info("LoyaltyEngine -> processProgramRewarding -> Program Id : "+ loyaltyProgram.getPrgProgramNo().toString() + " - General rules are not valid" );

            // Return false
            return false;

        }

        // Check if the program is valid for the transaction
        boolean isValid = loyaltyComputation.isProgramValidForTransaction(loyaltyProgram,sale);

        // If the program is not valid, then continue the loop
        if ( !isValid ) {

            // Set the loyalty program
            log.info("LoyaltyEngine -> processProgramRewarding -> Pr11ogram Id : "+ loyaltyProgram.getPrgProgramNo().toString() + " - Program rules are not valid" );

            // Return false
            return false;

        }

        // Get the points awarded by the program
        CustomerRewardPoint customerRewardPoint = loyaltyComputation.calculatePoints(loyaltyProgram,sale);

        //Apply Rounding method on Rewards
        customerRewardPoint=ApplyRoundingOnRewards(customerRewardPoint, loyaltyProgram);

        //process referral point awarding if exist
        processReferralPointAwarding(customerRewardPoint, loyaltyProgram,sale);

        // If the points is 0 ( no points ) ,then continue the loop
        if ( customerRewardPoint == null || customerRewardPoint.getTotalRewardPoint() ==0.0) {

            // Set the loyalty program
            log.info("LoyaltyEngine -> processProgramRewarding -> Program Id : "+ loyaltyProgram.getPrgProgramNo().toString() + " - No points awarded" );

            // Return false
            return false;

        }

        // Set the fields to the pointRewardData
        pointRewardData.setProgramId(loyaltyProgram.getPrgProgramNo());
        pointRewardData.setRwdCurrencyId(loyaltyProgram.getPrgCurrencyId());
        pointRewardData.setRewardQty(customerRewardPoint.getTotalRewardPoint());
        pointRewardData.setTotalRewardQty(pointRewardData.getTotalRewardQty() + customerRewardPoint.getTotalRewardPoint());

        // Get the RewardCurrency Information
        RewardCurrency rewardCurrency = rewardCurrencyService.findByRwdCurrencyId(loyaltyProgram.getPrgCurrencyId());

        // Get the expiry date
        Date rwdExpiryDate = rewardCurrencyService.getRewardExpiryDate(rewardCurrency,new Date());

        // Set the expiryDate for the PointRewardData object
        pointRewardData.setExpiryDt(new java.sql.Date(rwdExpiryDate.getTime()));

        // Variable holding the prebalance
        double preBalance = 0;

        // Get the Transaction object
        Transaction transaction = loyaltyEngineUtils.getTransactionForProgram(loyaltyProgram,sale,pointRewardData,preBalance);

        // Log the information on saving the data
        log.info("LoyaltyEngine -> processProgramRewarding -> Program Id : "+ loyaltyProgram.getPrgProgramNo().toString() + " - Transaction object" + transaction.toString());


        // Call the awardPoints function to add the points to user
        awardPointsProxy(pointRewardData, transaction);

        // Log the information regarding the points awarded
        log.info("LoyaltyEngine -> processProgramRewarding -> Program Id : "+ loyaltyProgram.getPrgProgramNo().toString() + " - Awarded " +Double.toString(customerRewardPoint.getTotalRewardPoint()) + " points " );

        // Return true
        return true;


    }

    private CustomerRewardPoint ApplyRoundingOnRewards(CustomerRewardPoint customerRewardPoint, LoyaltyProgram loyaltyProgram){

         double totalRewardPoint =0.0;

         double totalRefereePoint =0.0;

         double totalReferrerPoint =0.0;

        if(customerRewardPoint!=null){

            totalRewardPoint=customerRewardPoint.getTotalRewardPoint();

            totalRefereePoint=customerRewardPoint.getTotalRefereePoint();

            totalReferrerPoint=customerRewardPoint.getTotalReferrerPoint();

            // Get the RewardCurrency Information
            RewardCurrency rewardCurrency = rewardCurrencyService.findByRwdCurrencyId(loyaltyProgram.getPrgCurrencyId());

            if(rewardCurrency==null){

                totalRewardPoint=Math.round(totalRewardPoint);
                totalRefereePoint=Math.round(totalRefereePoint);
                totalReferrerPoint=Math.round(totalReferrerPoint);

            }else{

                int roundingMethod =rewardCurrency.getRwdRoundingMethod()==null?RoundingMethod.ROUND:rewardCurrency.getRwdRoundingMethod();

                switch (roundingMethod){

                    case RoundingMethod.ROUND:

                        totalRewardPoint=Math.round(totalRewardPoint);
                        totalRefereePoint=Math.round(totalRefereePoint);
                        totalReferrerPoint=Math.round(totalReferrerPoint);

                        break;

                    case RoundingMethod.FLOOR:

                        totalRewardPoint=Math.floor(totalRewardPoint);
                        totalRefereePoint=Math.floor(totalRefereePoint);
                        totalReferrerPoint=Math.floor(totalReferrerPoint);

                        break;

                    case RoundingMethod.CEIL:

                        totalRewardPoint=Math.ceil(totalRewardPoint);
                        totalRefereePoint=Math.ceil(totalRefereePoint);
                        totalReferrerPoint=Math.ceil(totalReferrerPoint);

                        break;

                }

            }

            //Set values
            customerRewardPoint.setTotalRewardPoint(totalRewardPoint);
            customerRewardPoint.setTotalRefereePoint(totalRefereePoint);
            customerRewardPoint.setTotalReferrerPoint(totalReferrerPoint);

            return customerRewardPoint;


        }else{

            return null;
        }
    }

    private void processReferralPointAwarding(CustomerRewardPoint customerRewardPoint, LoyaltyProgram loyaltyProgram,Sale sale) throws InspireNetzException {

        List<CustomerReferral> customerReferrals =null;

        //find the referral information
        if(customerRewardPoint ==null){

            return;
        }

        //referral point awarding
        if(customerRewardPoint.getTotalReferrerPoint() !=0.0 || customerRewardPoint.getTotalRefereePoint() !=0.0){

            //get customer information based on sales loyalty id
            Customer customer = customerService.findByCusLoyaltyIdAndCusMerchantNo(sale.getSalLoyaltyId(),sale.getSalMerchantNo());

            if(customer !=null){

                customerReferrals =customerReferralService.findByCsrMerchantNoAndCsrRefMobileAndCsrRefStatusOrderByCsrRefTimeStampAsc(sale.getSalMerchantNo(), customer.getCusMobile(), CustomerReferralStatus.PROCESSED);

            }

            //get the first item of referral list duplicate referral is not allowed in the system
            if(customerReferrals !=null && customerReferrals.size()>0 ){

                //get first item into the list
                CustomerReferral customerReferral = customerReferrals.get(0);

                if(customerReferral !=null && customerRewardPoint.getTotalRefereePoint()>0.0 && loyaltyProgram.getPrgRole().intValue() ==LoyaltyRefferalRoles.REFERREE){

                    //get referee customer information
                    Customer refereeCustomer =customerService.findByCusMobileAndCusMerchantNo(customerReferral.getCsrRefMobile(),customerReferral.getCsrMerchantNo());

                    if(refereeCustomer !=null){

                        //award referee point
                        awardReferralPoint(loyaltyProgram,refereeCustomer,customerRewardPoint.getTotalRefereePoint(),sale,"(Referee Bonus,  Referrer Loyalty Id: "+customerReferral.getCsrLoyaltyId()+")",customerReferral,LoyaltyRefferalRoles.REFERREE);
                    }


                }

                if (customerReferral !=null && customerRewardPoint.getTotalReferrerPoint()>0.0 && loyaltyProgram.getPrgRole().intValue() ==LoyaltyRefferalRoles.REFFERRER){

                    //get referrer  information
                    Customer referrerCustomer = customerService.findByCusLoyaltyIdAndCusMerchantNo(customerReferral.getCsrLoyaltyId(),customerReferral.getCsrMerchantNo());

                    if(referrerCustomer !=null){

                        //award referral bonus
                        awardReferralPoint(loyaltyProgram,referrerCustomer,customerRewardPoint.getTotalReferrerPoint(),sale,"(Referrer Bonus, Referee Mobile No: "+customerReferral.getCsrRefMobile()+")",customerReferral,LoyaltyRefferalRoles.REFFERRER);


                    }


                }

                if (customerReferral !=null && customerRewardPoint.getTotalReferrerPoint() > 0.0 &&customerRewardPoint.getTotalRefereePoint() > 0.0 && loyaltyProgram.getPrgRole().intValue() ==LoyaltyRefferalRoles.BOTH){

                    //get referrer  information
                    Customer referrerCustomer = customerService.findByCusLoyaltyIdAndCusMerchantNo(customerReferral.getCsrLoyaltyId(),customerReferral.getCsrMerchantNo());

                    Customer refereeCustomer = customerService.findByCusMobileAndCusMerchantNo(customerReferral.getCsrRefMobile(), customerReferral.getCsrMerchantNo()); 
                    if(referrerCustomer !=null){

                        //award referral bonus
                        awardReferralPoint(loyaltyProgram,referrerCustomer,customerRewardPoint.getTotalReferrerPoint(),sale,"(Referrer Bonus, Referee Mobile No: "+customerReferral.getCsrRefMobile()+")",customerReferral,LoyaltyRefferalRoles.REFFERRER);


                    }

                    if(refereeCustomer !=null){

                        //award referral bonus
                        awardReferralPoint(loyaltyProgram,refereeCustomer,customerRewardPoint.getTotalRefereePoint(),sale,"(Referee Bonus, Referee Mobile No: "+customerReferral.getCsrRefMobile()+")",customerReferral,LoyaltyRefferalRoles.REFERREE);


                    }


                }


            }

        }


    }

    private boolean awardReferralPoint(LoyaltyProgram loyaltyProgram, Customer customerReferralInfo, double customerRewardPoint,Sale sale,String remark,CustomerReferral customerReferral,int role) throws InspireNetzException {

        //get the point reward data object
        PointRewardData pointRewardData = loyaltyEngineUtils.getPointRewardDataForDTProcessing(loyaltyProgram,customerReferralInfo);


        // Set the rewardQty
        pointRewardData.setRewardQty(customerRewardPoint);

        // Set the totalQty
        pointRewardData.setTotalRewardQty(customerRewardPoint);        //set remarks
        pointRewardData.setRemarks(remark);

        // Get the expiryDate
        Date expiryDate = loyaltyEngineUtils.getExpiryDateForRewardCurrency(loyaltyProgram.getPrgCurrencyId());

        // Set the expiry Date
        pointRewardData.setExpiryDt(new java.sql.Date(expiryDate.getTime()));

        boolean isAwardReferrerProgram =false;

        if(role == LoyaltyRefferalRoles.REFERREE || loyaltyProgram.getPrgRole().intValue() ==LoyaltyRefferalRoles.REFERREE){

            //award points for the referee program
            isAwardReferrerProgram = awardPointsForTransactionBasedReferralProgram(pointRewardData, customerReferralInfo, loyaltyProgram,sale);

        }else {


            //check the is awarded then and loyalty program role is referrer then update the referee program summary
            if(role == LoyaltyRefferalRoles.REFFERRER || loyaltyProgram.getPrgRole().intValue() ==LoyaltyRefferalRoles.REFFERRER){

                //set referee information to point reward data
                Customer refereeCustomer = customerService.findByCusMobileAndCusMerchantNo(customerReferral.getCsrRefMobile(),customerReferral.getCsrMerchantNo());

                //set referee customer information
                if(refereeCustomer !=null){

                    pointRewardData.setRefereeLoyaltyId(refereeCustomer.getCusLoyaltyId());

                    //award points for the referrer
                    isAwardReferrerProgram = awardPointsForTransactionBasedReferralProgram(pointRewardData, customerReferralInfo, loyaltyProgram,sale);

                    if(isAwardReferrerProgram){

                        //update the referrer summary info
                        updateReferrerProgramSummary(pointRewardData);

                    }


                }

            }

        }


        return isAwardReferrerProgram;
    }


    @Override
    public PointRewardData getPointRewardDataObjectForTransaction(Sale sale) {

        // Create the PointRewardData object
        PointRewardData pointRewardData = new PointRewardData();

        // Set the merchant number
        pointRewardData.setMerchantNo(sale.getSalMerchantNo());

        // Set the loyaty id
        pointRewardData.setLoyaltyId(sale.getSalLoyaltyId());

        // Set the transaction amount
        pointRewardData.setTxnAmount(sale.getSalAmount());

        // Set the location
        pointRewardData.setTxnLocation(sale.getSalLocation());

        // Set the transaction date
        pointRewardData.setTxnDate(sale.getSalDate());

        // Set the auditDetails to be same as the sale object
        pointRewardData.setAuditDetails(sale.getCreatedBy());

        // Set the isAddToAccumulatedBalance to true ( Loyalty program transactions are considered for tier upgrade)
        pointRewardData.setAddToAccumulatedBalance(true);

        // Get the Merchant Information
        Merchant merchant = merchantService.findByMerMerchantNo(sale.getSalMerchantNo());

        // If the merhcant is not there,
        if ( merchant == null ) {

            // Log the response
            log.error("LoyaltyEngine -> processTranscation : No merchant information");

            // return null
            return null;

        }


        // Set the merchant specific information
        pointRewardData.setMerchantLogo(merchant.getMerMerchantImage());

        pointRewardData.setMerchantName(merchant.getMerMerchantName());


        // Check if the CustomerInformation exists for the customer
        Customer customer = customerService.findByCusLoyaltyIdAndCusMerchantNo(sale.getSalLoyaltyId(),sale.getSalMerchantNo());

        // Check if the customer is not null
        if ( customer != null ) {

            // Check if the user is registered
            if ( customer.getCusUserNo() == null ) {

                // Get the user
                User user = userService.findByUsrUserNo(customer.getCusUserNo());

                // Check if the user exists
                if ( user != null ) {

                    pointRewardData.setUsrFName(user.getUsrFName());

                    pointRewardData.setUsrLName(user.getUsrLName());

                    pointRewardData.setUserNo(user.getUsrUserNo());

                }

            } else {

                pointRewardData.setUsrFName(customer.getCusFName());

                pointRewardData.setUsrLName(customer.getCusLName());

            }

        }


        // Return the pointRewardData object
        return pointRewardData;

    }



    /////////////////////////// DATE TRIGGERED LOYALTY PROCESSING //////////////////////////////

    @Override
    @Scheduled(cron = "${scheduled.datetriggedloyalty}")
    public void runScheduledProcessing() throws InspireNetzException {

        // Get the default merchantNo
        List<Merchant> merchantList =merchantService.findAll();

        //iterate merchant list and process date triggered loyalty
        for (Merchant merchant :merchantList){

            // Log the information
            log.info("LoyaltyEngine -> RunScheduledProcessing -> Starting processing" );

            // Call the doDTProcessing
            doDTProcessing(merchant.getMerMerchantNo());

            // Log the information
            log.info("LoyaltyEngine -> RunScheduledProcessing -> Finished processing" );
        }




    }


    /**
     * Function to do the processing for the date triggered
     * @param merchantNo - The merchant number of the merchant to be processed
     * @return           - True if the processing was successful
     *                     False otherwise
     */
    protected boolean doDTProcessing(Long merchantNo) throws InspireNetzException {

        // Set the pageIndex to 0
        int pageIndex = 0;

        // Set the customerCount to 0
        Long customerCount = 0L;

        // Get the List of programs for the date triggered type
        List<LoyaltyProgram> loyaltyProgramList = loyaltyProgramService.findByPrgMerchantNoAndPrgProgramDriver(merchantNo,LoyaltyProgramDriver.DATE_TRIGGERED);

        // If the list is empty, then return
        if ( loyaltyProgramList == null || loyaltyProgramList.isEmpty() ) {

            // Log the information
            log.info("Loyalty Engine -> doDTProcessing -> No loyalty program for adhoc processing for the merchantNo :" + merchantNo);

            // Return true
            return true;

        }



        // Get the customers for the merchantNo
        Page<Customer> customerPage = customerService.findByCusMerchantNo(merchantNo, generalUtils.constructCustomerPageSpecification(pageIndex, MAX_ITEMS_IN_PAGE));

        // Check if there are any customers returned
        if ( customerPage == null || !customerPage.hasContent()) {

            // Log the information as no customers for the merchant
            log.info("Segmentation -> processSegmentation : Exiting, no customers");

            // Return true as completed processing
            return true;
        }


        // Repeat the loop till the page has got content
        while( customerPage !=  null && customerPage.hasContent() ) {

            // Repeat through the items in the page as an iterable
            for(Customer customer: customerPage) {

                // Iterate through the available programs for the current customer
                for (LoyaltyProgram loyaltyProgram : loyaltyProgramList ) {

                    // Call the processing
                    processDTLoyaltyProgramsForCustomer(loyaltyProgram,customer);

                }


            }

            // if there is no next page, then break the loop
            if ( !customerPage.hasNextPage() ) break;

            // Get the next page of customers
            customerPage = customerService.findByCusMerchantNo(merchantNo, generalUtils.constructCustomerPageSpecification(++pageIndex, MAX_ITEMS_IN_PAGE));

        }

        // finally return true
        return true;

    }


    /**
     * Function to do the date triggered program processing for the given LoyaltyProgram and Customer
     * object
     *
     * @param loyaltyProgram    - The LoyaltyProgram object
     * @param customer          - The Customer object
     * @return                  - true if the processing is successful
     *                            throws InspirenetzException if the processing is failed
     */
    @Transactional(rollbackFor = {InspireNetzException.class,Exception.class})
    public boolean processDTLoyaltyProgramsForCustomer(LoyaltyProgram loyaltyProgram,Customer customer) throws InspireNetzException {

        // Log the information
        log.info("LoyaltyEngine -> processDTLoyaltyProgramsForCustomer -> Processing Loyalty program " + loyaltyProgram + " for customer : " + customer );

        // Get the points for the customer
        double pointsAwarded = getPointsForDTProcessing(loyaltyProgram,customer);



        // If the points are not awarded, then return
        if ( pointsAwarded == 0.0 ) {

            // Log the information
            log.info("LoyaltyEngine -> processDTLoyaltyProgramsForCustomer -> No points awarded");

            // return true
            return true;

        }



        // Variable holding the prebalace
        double preBalance = 0.0;

        // If points were awarded, then get the current balance for the customer
        // for the given reward currency id
        CustomerRewardBalance customerRewardBalance = customerRewardBalanceService.findByCrbLoyaltyIdAndCrbMerchantNoAndCrbRewardCurrency(customer.getCusLoyaltyId(),customer.getCusMerchantNo(),loyaltyProgram.getPrgCurrencyId());

        // If the customerRewardBalance is not existing, then set the preBalance to 0
        if ( customerRewardBalance == null ) {

            // Set the preBalance to 0
            preBalance = 0.0;

        } else {

            // Set the preBalance to the cusotmer reward balance
            preBalance = customerRewardBalance.getCrbRewardBalance();

        }


        // Get the PointRewardData object
        PointRewardData pointRewardData = loyaltyEngineUtils.getPointRewardDataForDTProcessing(loyaltyProgram,customer);

        // Set the rewardQty
        pointRewardData.setRewardQty(pointsAwarded);

        // Set the totalQty
        pointRewardData.setTotalRewardQty(pointsAwarded);

        // Get the expiryDate
        Date expiryDate = loyaltyEngineUtils.getExpiryDateForRewardCurrency(loyaltyProgram.getPrgCurrencyId());

        // Set the expiry Date
        pointRewardData.setExpiryDt(new java.sql.Date(expiryDate.getTime()));



        // Get the Transaction for the PointRewardData
        Transaction transaction = loyaltyEngineUtils.getTransactionForPointRewardData(pointRewardData,preBalance,loyaltyProgram.getPrgProgramNo().toString());

        // Call the awardPoints
        boolean isAwarded = awardPointsProxy(pointRewardData, transaction);

        // Check if awarded
        if ( !isAwarded ) {

            // Log information
            log.info("LoyaltyEngine -> processDTLoyaltyProgramsForCustomer -> Awarding failed");

            // Throw exception
            throw new InspireNetzException(APIErrorCode.ERR_OPERATION_FAILED);

        }


        // Finally return true
        return true;

    }


    /**
     * Function to get the points applicable for adhoc processing of a loyalty program
     * for the customer
     * This will check the loyaltyprogra sku ( the adhoc settings) and then match with
     * the customer information stored in the system
     *
     * @param loyaltyProgram    - The LoyaltyProgram object that need to checked for awarding
     * @param customer          - The Customer object for whom we need to check the information
     *
     * @return                  - Return a value greater than 0 if points have been awarded
     *                            Return 0 if no points awarded for the loyalty program
     */
    protected Double getPointsForDTProcessing(LoyaltyProgram loyaltyProgram, Customer customer) {

        // Variable holding the total points awarded by this program
        double pointAwarded = 0.0;

        // Create the today
        Date today = new Date();

        // Check if the program is valid
        boolean isProgramValid = isProgramValid(loyaltyProgram,today);

        // If the program is not valid, return false
        if ( !isProgramValid ) {

            // Set the logging information
            log.info("LoyaltyEngine -> getPointsForDTProcessing : Program is not valid");

            // Return 0
            return 0.0;

        }


        // Check if the customer is valid
        boolean isCustomerValid = isCustomerValid(loyaltyProgram,customer);


        if ( !isCustomerValid ) {

            // Set the logging information
            log.info("LoyaltyEngine -> getPointsForDTProcessing : Customer is not valid");

            // Return 0
            return 0.0;


        }



        // Get the effective tier for the customer
        Tier cusTier = accountBundlingUtils.getEffectiveTierForCustomer(customer);

        // If the tier is null, then log error and return
        if ( cusTier == null ) {

            // Log the information
            log.info("LoyaltyEngine -> getPointsForDTProcessing  : Customer does not have an effective tier");

            // return 0.0
            return 0.0;

        }

        try {

            // Get the LoyaltyProgramSKu
            loyaltyProgram.getLoyaltyProgramSkuSet().toString();


        } catch (NullPointerException ex) {

            // Log the info
            log.info("Loyalty Engine ->   getPointsForDTProcessing: No adhoc data");

            // return 0
            return 0.0;

        }


        // Flag showing if the customer profile is updated
        boolean isProfileUpdated = true;

        // Get the customer Profile information for the customer
        CustomerProfile customerProfile = customerProfileService.findByCspCustomerNo(customer.getCusCustomerNo());

        // If the customerProfile is not existing, then set the isProfileUpdated flag to false
        if ( customerProfile == null ) {

            // SEt the flag to false
            isProfileUpdated = false;

        }


        // Convert today to to isoFormatted string
        String strToday = generalUtils.convertToISOFormat(today);

        // Iterate through each of the loyaltyProgramSku items and check the validity
        for ( LoyaltyProgramSku loyaltyProgramSku : loyaltyProgram.getLoyaltyProgramSkuSet() ) {

            // Check if the tier is matching for the customer
            if ( loyaltyProgramSku.getLpuTier() != 0L && cusTier.getTieId().longValue() != loyaltyProgramSku.getLpuTier() ) {

                // Continue
                continue;

            }

            // Check the date based first
            if ( loyaltyProgramSku.getLpuItemType() == LoyaltyProgramSkuType.DT_CUSTOM_DATE ) {

                // Format the date
                Date customDate = generalUtils.convertToDate(loyaltyProgramSku.getLpuItemCode());

                // If the date is not valid, then log error
                if ( customDate == null ) {

                    // Log information
                    log.error("Loyalty Engine ->   getPointsForDTProcessing: Custom date parsing failed");

                    // Continue
                    continue;

                }




                // Check if the customDate is matching today
                if ( generalUtils.convertToISOFormat(customDate).equals(strToday) ) {

                    // Add the points to the customer pointAwarded
                    pointAwarded += loyaltyProgramSku.getLpuPrgRatioNum();

                    // Log the information
                    log.info("Loyalty Engine ->   getPointsForDTProcessing: Awarded " + loyaltyProgramSku.getLpuPrgRatioNum() + " by LPU ID " + loyaltyProgramSku.getLpuId());

                }


                // Continue the iteration
                continue;

            }


            // Check the customer birthday and anniversary based only if the
            // customer profile is updated
            if ( isProfileUpdated ) {

                // Check the type
                if ( loyaltyProgramSku.getLpuItemType() == LoyaltyProgramSkuType.DT_BIRTHDAY ) {

                    // Check if the birthday of the cusotmer is matching
                    if ( customerProfile.getCspCustomerBirthday() != null ) {

                        // Check if today is customer birthday
                        if ( generalUtils.convertDateToFormat(customerProfile.getCspCustomerBirthday(), DATE_TRIGGERED_DAY_MONTH_FORMAT).equals(generalUtils.convertDateToFormat(today,DATE_TRIGGERED_DAY_MONTH_FORMAT)) ) {

                            // Check if the customer birthday is valid for awarding
                            boolean isValid  = customerService.isCustomerValidForDTAwarding(customerProfile,DateTriggeredAwardingType.BIRTHDAY);

                            // If the period is not valid then, we don't award the customer
                            if ( !isValid ) {

                                // Log the information
                                log.info("Loyalty Engine ->   getPointsForDTProcessing : Customer birthday is not valid for awarding. Last awarded: " + customerProfile.getCspBirthDayLastAwarded());

                                // Continue the loop
                                continue;

                            }


                            // Add the points to the customer pointAwarded
                            pointAwarded += loyaltyProgramSku.getLpuPrgRatioNum();

                            // Update the last birthday awarded date to today
                            customerProfile.setCspBirthDayLastAwarded(new java.sql.Date(today.getTime()));

                            // Update the customerProfile
                            customerProfileService.saveCustomerProfile(customerProfile);

                            // Log the information
                            log.info("Loyalty Engine ->   getPointsForDTProcessing: Awarded " + loyaltyProgramSku.getLpuPrgRatioNum() + " by LPU ID " + loyaltyProgramSku.getLpuId());


                        }

                    }

                } else if ( loyaltyProgramSku.getLpuItemType() == LoyaltyProgramSkuType.DT_ANNIVERSARY ) {

                    // Check if the anniversary of the customer is matching
                    if ( customerProfile.getCspCustomerAnniversary() != null ) {

                        // Check if today is customer anniversary
                        if ( generalUtils.convertDateToFormat(customerProfile.getCspCustomerAnniversary(), DATE_TRIGGERED_DAY_MONTH_FORMAT).equals(generalUtils.convertDateToFormat(today,DATE_TRIGGERED_DAY_MONTH_FORMAT)) ) {

                            // Check if the customer birthday is valid for awarding
                            boolean isValid  = customerService.isCustomerValidForDTAwarding(customerProfile,DateTriggeredAwardingType.ANNIVERSARY);

                            // If the period is not valid then, we don't award the customer
                            if ( !isValid ) {

                                // Log the information
                                log.info("Loyalty Engine ->   getPointsForDTProcessing : Customer anniversary is not valid for awarding. Last awarded: " + customerProfile.getCspCustomerAnniversary());

                                // Continue the loop
                                continue;

                            }

                            // Add the points to the customer pointAwarded
                            pointAwarded += loyaltyProgramSku.getLpuPrgRatioNum();

                            // Update the last birthday awarded date to today
                            customerProfile.setCspAnniversaryLastAwarded(new java.sql.Date(today.getTime()));

                            // Update the customerProfile
                            customerProfileService.saveCustomerProfile(customerProfile);

                            // Log the information
                            log.info("Loyalty Engine ->   getPointsForDTProcessing: Awarded " + loyaltyProgramSku.getLpuPrgRatioNum() + " by LPU ID " + loyaltyProgramSku.getLpuId());

                        }

                    }

                }

            }

        }


        // Return the pointAwarded
        return pointAwarded;

    }


    /////////////////////////// ACTIVITY TRIGGERED LOYALTY PROCESSING //////////////////////////////

    @Override
    public void doCustomerRewardActivityProcessing(CustomerRewardActivity customerRewardActivity ) throws InspireNetzException {

        // Check the status of the customerRewardActivity
        if ( customerRewardActivity.getCraStatus() != CustomerRewardActivityStatus.NEW ) {

            // Log the information
            log.info("LoyaltyEngine -> doCustomerRewardActivityProcessing  : CustomerRewardActivity is not new");

            // return
            return ;

        }

        //find the merchant details of customer
        Customer customer =getCustomerDetails(customerRewardActivity.getCraCustomerNo());

        //merchant initialize
        Long merchantNo=0L;

        //check merchant details for the customer
        if(customer !=null){

            merchantNo =customer.getCusMerchantNo()==null?0L:customer.getCusMerchantNo();

        }else {

            return;
        }

        // List the Loyalty programs based on the activity triggered loyalty type
        List<LoyaltyProgram> loyaltyProgramList = loyaltyProgramService.findByPrgMerchantNoAndPrgProgramDriver(merchantNo, LoyaltyProgramDriver.ACTIVITY_TRIGGERED);

        // If there are no items, then return
        if ( loyaltyProgramList == null || loyaltyProgramList.isEmpty() ) {

            return ;

        }


        // Go// Check if ht through the list of programs for processing
        for ( LoyaltyProgram loyaltyProgram : loyaltyProgramList ) {

            // Log the information
            log.info("LoyaltyEngine -> doCustomerRewardActivityProcessing  : Processing for"  + loyaltyProgram);

            // Call the procssing
            boolean isProcessed = doRewardActivityProcessingForProgram(loyaltyProgram,customerRewardActivity);

            // If not processed, then show message
            if ( !isProcessed ) {

                // Log infor
                log.info("LoyaltyEngine -> doCustomerRewardActivityProcessing - No awarding " + loyaltyProgram);

            }

        }

    }

    private Customer getCustomerDetails(Long craCustomerNo) {
        return customerService.findByCusCustomerNo(craCustomerNo);
    }

    @Transactional(rollbackFor = {InspireNetzException.class,Exception.class})
    protected boolean doRewardActivityProcessingForProgram(LoyaltyProgram loyaltyProgram,CustomerRewardActivity customerRewardActivity) throws InspireNetzException {

        // Get the customer information for the given customer number in the activity
        Customer customer = customerService.findByCusCustomerNo(customerRewardActivity.getCraCustomerNo());

        // Check if the customer is valid
        boolean isCustomerValid = isCustomerValid(loyaltyProgram,customer);

        // Check if the cutomer is valid and if not, show message
        if ( !isCustomerValid ) {

            // Log the information
            log.info("Loyalty Engine ->   doRewardActivityProcessingForProgram : Customer is not valid"  );

            // return false
            return false;

        }


        // Get Today
        Date today = new Date();

        // Check if the program is valid
        boolean isProgramValid = isProgramValid(loyaltyProgram,today);

        // If the program is not valid, return false
        if ( !isProgramValid ) {

            // Log the information
            log.info("Loyalty Engine ->   doRewardActivityProcessingForProgram : Program is not valid"  );

            // return false
            return false;

        }


        // Check if the eligiliby is valid
        if ( !isCustomerEligibilityValid(loyaltyProgram,customer) ) {

            // Set the logging information
            log.info("LoyaltyEngine -> doRewardActivityProcessingForProgram : Invalid eligibility of customer");

            // Return false
            return false;


        }


        // Log the information
        log.info("LoyaltyEngine -> doRewardActivityProcessingForProgram -> Processing Loyalty program " + loyaltyProgram + " for customer : " + customer );

        // Get the points for the customer
        double pointsAwarded = processRewardActivityProgram(loyaltyProgram, customerRewardActivity,customer);


        // If the points are not awarded, then return
        if ( pointsAwarded == 0.0 ) {

            // Log the information
            log.info("LoyaltyEngine -> doRewardActivityProcessingForProgram -> No points awarded");

            // return true
            return true;

        }



        // Variable holding the prebalace
        double preBalance = 0.0;

        // If points were awarded, then get the current balance for the customer
        // for the given reward currency id
        CustomerRewardBalance customerRewardBalance = customerRewardBalanceService.findByCrbLoyaltyIdAndCrbMerchantNoAndCrbRewardCurrency(customer.getCusLoyaltyId(),customer.getCusMerchantNo(),loyaltyProgram.getPrgCurrencyId());

        // If the customerRewardBalance is not existing, then set the preBalance to 0
        if ( customerRewardBalance == null ) {

            // Set the preBalance to 0
            preBalance = 0.0;

        } else {

            // Set the preBalance to the cusotmer reward balance
            preBalance = customerRewardBalance.getCrbRewardBalance();

        }


        // Get the PointRewardData object
        PointRewardData pointRewardData = loyaltyEngineUtils.getPointRewardDataForDTProcessing(loyaltyProgram,customer);

        // Set the rewardQty
        pointRewardData.setRewardQty(pointsAwarded);

        // Set the totalQty
        pointRewardData.setTotalRewardQty(pointsAwarded);

        // Get the expiryDate
        Date expiryDate = loyaltyEngineUtils.getExpiryDateForRewardCurrency(loyaltyProgram.getPrgCurrencyId());

        // Set the expiry Date
        pointRewardData.setExpiryDt(new java.sql.Date(expiryDate.getTime()));

        pointRewardData.setRemarks("("+loyaltyProgram.getPrgProgramName()+")");

        // Get the Transaction for the PointRewardData
        Transaction transaction = loyaltyEngineUtils.getTransactionForPointRewardData(pointRewardData,preBalance,loyaltyProgram.getPrgProgramNo().toString());

        // Call the awardPoints
        boolean isAwarded = awardPointsProxy(pointRewardData, transaction);

        // Check if awarded
        if ( !isAwarded ) {

            // Log information
            log.info("LoyaltyEngine -> doRewardActivityProcessingForProgram -> Awarding failed");

            // Throw exception
            throw new InspireNetzException(APIErrorCode.ERR_OPERATION_FAILED);

        }


        // Finally return true
        return true;

    }

    /**
     * Function to process the reward activity program
     *
     * @param loyaltyProgram            - The program to be processed
     * @param customerRewardActivity    - The CustomerRewardActivity object
     * @param customer                  - The customer object
     *
     * @return                          - The points awarded
     */
    protected double processRewardActivityProgram(LoyaltyProgram loyaltyProgram, CustomerRewardActivity customerRewardActivity,Customer customer ) {

        // Variable holding the total points awarded by this program
       double pointAwarded = 0.0;

        // Create the loyaltyProgramSku
        List<LoyaltyProgramSku> loyaltyProgramSkuList = loyaltyProgramSkuService.findByLpuProgramId(loyaltyProgram.getPrgProgramNo());

        // If the list is emtpy, then
        if ( loyaltyProgramSkuList == null || loyaltyProgramSkuList.isEmpty() ) {

            // Log the information
            log.info("LoyaltyEngine -> processRewardActivityProgram : loyalty program sku is empty");

            // reutrn 0.0
            return 0.0;

        }


        // Get the effective tier for the customer
        Tier cusTier = accountBundlingUtils.getEffectiveTierForCustomer(customer);

        // If the tier is null, then log error and return
        if ( cusTier == null ) {

            // Log the information
            log.info("LoyaltyEngine -> processRewardActivityProgram : Customer does not have an effective tier");

            // return 0.0
            return 0.0;

        }


        // Go through the list and check the points
        for ( LoyaltyProgramSku loyaltyProgramSku : loyaltyProgramSkuList ) {

            // Check if the tier is valid for the customer
            if ( loyaltyProgramSku.getLpuTier() != 0L && cusTier.getTieId().longValue() != loyaltyProgramSku.getLpuTier().longValue() ) {

                // Log the information
                log.info("LoyaltyEngine -> processRewardActivityProgram : tier not matching ");

                // Continue
                continue;

            }


            // Check if the customer reward activity type is same as the lpu event registration
            if ( customerRewardActivity.getCraType() == CustomerRewardingType.EVENT_REGISTRATION ) {

                // Check if the lpuType is  event enrollment and event code is matching
                if ( loyaltyProgramSku.getLpuItemType() == LoyaltyProgramSkuType.EVENT_ENROLLMENT  &&
                     customerRewardActivity.getCraActivityRef().equals(loyaltyProgramSku.getLpuItemCode())   ) {

                    // Log the information
                    log.info("LoyaltyEngine -> processRewardActivityProgram :  Awarding for " + loyaltyProgramSku);

                    // Add the points
                    pointAwarded += loyaltyProgramSku.getLpuPrgRatioNum();


                }

            } else if ( customerRewardActivity.getCraType() == CustomerRewardingType.PRODUCT_SUBSCRIPTION ) {

                // Check if the lpuType is  product subscription and product code is matching
                if ( loyaltyProgramSku.getLpuItemType() == LoyaltyProgramSkuType.PRODUCT_SUBSCRIPTION  &&
                     customerRewardActivity.getCraActivityRef().equals(loyaltyProgramSku.getLpuItemCode())   ) {

                    // Log the information
                    log.info("LoyaltyEngine -> processRewardActivityProgram :  Awarding for " + loyaltyProgramSku);

                    // Add the points
                    pointAwarded += loyaltyProgramSku.getLpuPrgRatioNum();

                }

            } else if(loyaltyProgramSku.getLpuItemType() == LoyaltyProgramSkuType.ACTIVITY && loyaltyProgramSku.getLpuItemCode().equals(CustomerRewardingReferralValue.REFEREE_VALUE)
                    && customerRewardActivity.getCraType().intValue() ==CustomerRewardingType.REFEREE_BONUS){

                // Log the information
                log.info("LoyaltyEngine -> processRewardActivityProgram :  Awarding for " + loyaltyProgramSku);

                // Add the points
                pointAwarded += loyaltyProgramSku.getLpuPrgRatioNum();


            }else if(loyaltyProgramSku.getLpuItemType() == LoyaltyProgramSkuType.ACTIVITY && loyaltyProgramSku.getLpuItemCode().equals(CustomerRewardingReferralValue.REFERRER_VALUE)
                    && customerRewardActivity.getCraType().intValue() ==CustomerRewardingType.REFERRER_BONUS){

                // Log the information
                log.info("LoyaltyEngine -> processRewardActivityProgram :  Awarding for " + loyaltyProgramSku);

                // Add the points
                pointAwarded += loyaltyProgramSku.getLpuPrgRatioNum();

            }else if ( customerRewardActivity.getCraType().intValue() == CustomerRewardingType.PORTAL_DIALOGUE ) {


                    // Check if the lpuType is  event enrollment and event code is matching
                    if ( loyaltyProgramSku.getLpuItemType() == LoyaltyProgramSkuType.PORTAL_DIALOGUE  &&
                            customerRewardActivity.getCraActivityRef().equals(loyaltyProgramSku.getLpuItemCode())   ) {

                        // Log the information
                        log.info("LoyaltyEngine -> processRewardActivityProgram :  Awarding for " + loyaltyProgramSku);

                        // Add the points
                        pointAwarded += loyaltyProgramSku.getLpuPrgRatioNum();


                    }


            }
            else  {

                // Check if the lpuType is activity registration and that the coded value
                // in the lpuCode is matching then customerActivityType
                if ( loyaltyProgramSku.getLpuItemType() == LoyaltyProgramSkuType.ACTIVITY &&
                     loyaltyProgramSku.getLpuItemCode().equals(RewardingTypeString.getRewardingTypeString(customerRewardActivity.getCraType())) ) {

                    // Log the information
                    log.info("LoyaltyEngine -> processRewardActivityProgram :  Awarding for " + loyaltyProgramSku);

                    // Add the points
                    pointAwarded += loyaltyProgramSku.getLpuPrgRatioNum();

                }else if ( loyaltyProgramSku.getLpuItemType() == LoyaltyProgramSkuType.ACTIVITY &&
                        loyaltyProgramSku.getLpuItemCode().equals(customerRewardActivity.getCraActivityRef()) ) {

                    // Log the information
                    log.info("LoyaltyEngine -> processRewardActivityProgram :  Awarding for " + loyaltyProgramSku);

                    // Add the points
                    pointAwarded += loyaltyProgramSku.getLpuPrgRatioNum();

                }

            }

        }


        // Return the pointAwarded field
        return pointAwarded;

    }

    //////////////// LOYALTY PROGRAM REWARD TABLE UPDATES //////////////////////////////////

    @Override
    public boolean updateMerchantProgramSummary(PointRewardData pointRewardData) {

        // Create the MerchantProgramSummary
        MerchantProgramSummary merchantProgramSummary = merchantProgramSummaryService.findByMpsMerchantNoAndMpsBranchAndMpsProgramId(pointRewardData.getMerchantNo(),pointRewardData.getTxnLocation(),pointRewardData.getProgramId());

        // Check if the merchantProgramSummary
        if ( merchantProgramSummary == null ) {

            // Create the object
            merchantProgramSummary = new MerchantProgramSummary();

            // Set the data
            merchantProgramSummary.setMpsMerchantNo(pointRewardData.getMerchantNo());

            merchantProgramSummary.setMpsBranch(pointRewardData.getTxnLocation());

            merchantProgramSummary.setMpsProgramId(pointRewardData.getProgramId());

            merchantProgramSummary.setMpsTransactionAmount(pointRewardData.getTxnAmount());

            merchantProgramSummary.setMpsRewardCount(pointRewardData.getRewardQty());

            merchantProgramSummary.setMpsTransactionCount(1);

            merchantProgramSummary.setCreatedBy(pointRewardData.getAuditDetails());


        } else {

            merchantProgramSummary.setMpsTransactionAmount(merchantProgramSummary.getMpsTransactionCount() + pointRewardData.getTxnAmount());

            merchantProgramSummary.setMpsTransactionCount(merchantProgramSummary.getMpsTransactionCount() + 1);

            merchantProgramSummary.setMpsRewardCount(merchantProgramSummary.getMpsRewardCount() + pointRewardData.getRewardQty());

            merchantProgramSummary.setUpdatedBy(pointRewardData.getAuditDetails());

        }


        // Update the data
        merchantProgramSummary = merchantProgramSummaryService.saveMerchantProgramSummary(merchantProgramSummary);


        // Check if the data has been updated
        if ( merchantProgramSummary == null) {

            // Log the information
            log.error("LoyaltyEngine -> updateMerchantProgramSummary - MerchantProgramSummary update failed ");

            // Return false
            return false;

        } else {

            // Log the information
            log.info("LoyaltyEngine -> updateMerchantProgramSummary - MerchantProgramSummary updated successfully: "+ merchantProgramSummary.toString());

            // Return true
            return true;

        }

    }

    @Override
    public boolean updateCustomerRewardBalance(PointRewardData pointRewardData) {

        // Get the cusotmerRewardBalanceInformation
        CustomerRewardBalance customerRewardBalance = customerRewardBalanceService.findByCrbLoyaltyIdAndCrbMerchantNoAndCrbRewardCurrency(pointRewardData.getLoyaltyId(),pointRewardData.getMerchantNo(),pointRewardData.getRwdCurrencyId());

        // Check if the reward balance is found
        if ( customerRewardBalance == null ) {

            // Create a new Rewardbalance
            customerRewardBalance = new CustomerRewardBalance();

            // Set the fields
            customerRewardBalance.setCrbMerchantNo(pointRewardData.getMerchantNo());

            customerRewardBalance.setCrbLoyaltyId(pointRewardData.getLoyaltyId());

            customerRewardBalance.setCrbRewardCurrency(pointRewardData.getRwdCurrencyId());

            customerRewardBalance.setCrbRewardBalance(pointRewardData.getRewardQty());

            customerRewardBalance.setCreatedBy(pointRewardData.getAuditDetails());

        } else {

            // Add the reward balance
            customerRewardBalance.setCrbRewardBalance(customerRewardBalance.getCrbRewardBalance() + pointRewardData.getRewardQty());;

            customerRewardBalance.setUpdatedBy(pointRewardData.getAuditDetails());

        }

        // Save the reward balance
        customerRewardBalance = customerRewardBalanceService.saveCustomerRewardBalance(customerRewardBalance);

        // Check if the data is not null
        if ( customerRewardBalance == null ) {

            // Log the information
            log.error("LoyaltyEngine -> customerRewardbalance - CustomerRewardBalance update failed ");

            // Return false
            return false;

        } else {

            // Log the information
            log.info("LoyaltyEngine -> customerRewardbalance - CustomerRewardBalance update successful : "+customerRewardBalance.toString());

            // Return true
            return true;
        }

    }

    @Override
    public boolean updateAccumulatedRewardBalance(PointRewardData pointRewardData) {

        // Get the AccumulatedRewardBalance information
        AccumulatedRewardBalance accumulatedRewardBalance = accumulatedRewardBalanceService.findByArbMerchantNoAndArbLoyaltyIdAndArbRewardCurrency(pointRewardData.getMerchantNo(), pointRewardData.getLoyaltyId(), pointRewardData.getRwdCurrencyId());

        // Check if the accumulatedRewardBalance object exists
        if ( accumulatedRewardBalance == null ) {

            // Create the object
            accumulatedRewardBalance = new AccumulatedRewardBalance();

            // Set the fields
            accumulatedRewardBalance.setArbMerchantNo(pointRewardData.getMerchantNo());

            accumulatedRewardBalance.setArbLoyaltyId(pointRewardData.getLoyaltyId());

            accumulatedRewardBalance.setArbRewardCurrency(pointRewardData.getRwdCurrencyId());

            accumulatedRewardBalance.setArbRewardBalance(pointRewardData.getRewardQty());

            accumulatedRewardBalance.setCreatedBy(pointRewardData.getAuditDetails());

        } else {

            accumulatedRewardBalance.setArbRewardBalance(accumulatedRewardBalance.getArbRewardBalance() + pointRewardData.getRewardQty());

            accumulatedRewardBalance.setUpdatedBy(pointRewardData.getAuditDetails());

        }


        // Save the reward balance
        accumulatedRewardBalance = accumulatedRewardBalanceService.saveAccumulatedRewardBalance(accumulatedRewardBalance);

        // Check if the data is not null
        if ( accumulatedRewardBalance == null ) {

            // Log the information
            log.error("LoyaltyEngine -> updateAccumulatedRewardBalance - accumulatedRewardBalance update failed : "+accumulatedRewardBalance);

            // Return false
            return false;

        } else {

            // Log the information
            log.info("LoyaltyEngine -> updateAccumulatedRewardBalance - accumulatedRewardBalance update successful : "+accumulatedRewardBalance);

            // Return true
            return true;
        }

    }

    @Override
    public boolean updateLinkedRewardBalance(PointRewardData pointRewardData) {

        // Get the LinkedRewardBalance information
        LinkedRewardBalance linkedRewardBalance = linkedRewardBalanceService.findByLrbPrimaryLoyaltyIdAndLrbMerchantNoAndLrbRewardCurrency(pointRewardData.getLoyaltyId(), pointRewardData.getMerchantNo(), pointRewardData.getRwdCurrencyId());

        // Check if the reward balance is null
        if ( linkedRewardBalance == null ) {

            // Create the object
            linkedRewardBalance = new LinkedRewardBalance();

            // Set the fields
            linkedRewardBalance.setLrbPrimaryLoyaltyId(pointRewardData.getLoyaltyId());

            linkedRewardBalance.setLrbMerchantNo(pointRewardData.getMerchantNo());

            linkedRewardBalance.setLrbRewardCurrency(pointRewardData.getRwdCurrencyId());

            linkedRewardBalance.setLrbRewardBalance(pointRewardData.getRewardQty());


            // Set the created by field
            linkedRewardBalance.setCreatedBy(pointRewardData.getAuditDetails());


        } else {

            // Add the balance
            linkedRewardBalance.setLrbRewardBalance(linkedRewardBalance.getLrbRewardBalance() + pointRewardData.getRewardQty());

            // Set the updated by field
            linkedRewardBalance.setUpdatedBy(pointRewardData.getAuditDetails());

        }



        // Save the LinkedRewardBalance
        linkedRewardBalance = linkedRewardBalanceService.saveLinkedRewardBalance(linkedRewardBalance);

        // Check if the reward balance is saved
        if ( linkedRewardBalance == null ) {

            // Log the information
            log.info("LoyaltyEngine -> updateLinkedRewardBalance  -> Linkedrewardbalance saving failed");

            // Return false;
            return false;

        } else {

            // Log the information
            log.info("LoyaltyEngine -> updateLinkedRewardBalance  -> Linkedrewardbalance saving successful" + linkedRewardBalance);

            // Return true;
            return true;

        }

    }

    @Override
    public boolean updateReferrerProgramSummary(PointRewardData pointRewardData) {

        // Get the CustomerProgramSummary
        ReferrerProgramSummary referrerProgramSummary = referrerProgramSummaryService.findByRpsMerchantNoAndRpsRefereeLoyaltyIdAndRpsProgramId(pointRewardData.getMerchantNo(), pointRewardData.getRefereeLoyaltyId(), pointRewardData.getProgramId());

        // Check if there is data
        if ( referrerProgramSummary == null ) {

            referrerProgramSummary = new ReferrerProgramSummary();

            // Set the fields
            referrerProgramSummary.setRpsMerchantNo(pointRewardData.getMerchantNo());

            referrerProgramSummary.setRpsLoyaltyId(pointRewardData.getLoyaltyId());

            referrerProgramSummary.setRpsProgramId(pointRewardData.getProgramId());

            referrerProgramSummary.setRpsRefereeLoyaltyId(pointRewardData.getRefereeLoyaltyId());

            referrerProgramSummary.setRpsProgramVisit(1);

            referrerProgramSummary.setCreatedBy(pointRewardData.getAuditDetails());


        } else {


            referrerProgramSummary.setRpsProgramVisit(referrerProgramSummary.getRpsProgramVisit() + 1);

            referrerProgramSummary.setUpdatedBy(pointRewardData.getAuditDetails());


        }


        // Save the object and check
        referrerProgramSummary = referrerProgramSummaryService.saveReferrerProgramSummary(referrerProgramSummary);

        // Check if the data has been saved successfully
        if ( referrerProgramSummary == null ) {

            // Set the error message
            log.error("LoyaltyEngine -> updateReferralProgramSummary - CustomerProgramSummary update failed");

            // Return false
            return false;

        } else {

            // Set the message
            log.info("LoyaltyEngine -> updateReferralProgramSummary is  update successful : " + referrerProgramSummary.toString());

            // Return true
            return true;

        }

    }

    @Override
    public boolean updateCustomerRewardExpiry(PointRewardData pointRewardData) {

        // Get the CustomerRewardExpiry field
        CustomerRewardExpiry customerRewardExpiry = customerRewardExpiryService.findByCreMerchantNoAndCreLoyaltyIdAndCreRewardCurrencyIdAndCreExpiryDt(pointRewardData.getMerchantNo(), pointRewardData.getLoyaltyId(), pointRewardData.getRwdCurrencyId(), pointRewardData.getExpiryDt());

        // If the customerRewardExpiry is null, then we need to populate the fields and create the object
        if( customerRewardExpiry == null ) {

            // Create the new CustomerRewardExpiry object
            customerRewardExpiry = new CustomerRewardExpiry();


            // Set the fields
            customerRewardExpiry.setCreMerchantNo(pointRewardData.getMerchantNo());

            customerRewardExpiry.setCreLoyaltyId(pointRewardData.getLoyaltyId());

            customerRewardExpiry.setCreRewardCurrencyId(pointRewardData.getRwdCurrencyId());

            customerRewardExpiry.setCreExpiryDt(pointRewardData.getExpiryDt());

            customerRewardExpiry.setCreRewardBalance(pointRewardData.getRewardQty());

            customerRewardExpiry.setCreatedBy(pointRewardData.getAuditDetails());

        } else {

            // Update the points
            customerRewardExpiry.setCreRewardBalance(customerRewardExpiry.getCreRewardBalance() + pointRewardData.getRewardQty());

            customerRewardExpiry.setUpdatedBy(pointRewardData.getAuditDetails());

        }


        // Save the customerRewardExpiry
        customerRewardExpiry = customerRewardExpiryService.saveCustomerRewardExpiry(customerRewardExpiry);

        // Check if the customerRewardExpiry is saved succesfully
        if ( customerRewardExpiry == null ) {

            // Log the information
            log.error("LoyaltyEngine -> customerRewardExpiry - CustomerRewardExpiry update failed : ");

            // return false
            return false;

        } else {

            // Log the information
            log.info("LoyaltyEngine -> customerRewardExpiry - CustomerRewardExpiry update successful : " + customerRewardExpiry.toString());

            // Return true
            return true;

        }

    }

    @Override
    public boolean updateMerchantRewardSummary(PointRewardData pointRewardData) {


        // Get MerchantRewardSummary
        MerchantRewardSummary merchantRewardSummary = merchantRewardSummaryService.findByMrsMerchantNoAndMrsCurrencyIdAndMrsBranchAndMrsDate(pointRewardData.getMerchantNo(), pointRewardData.getRwdCurrencyId(), pointRewardData.getTxnLocation(), pointRewardData.getTxnDate());

        // Check if the merchantRewardSummary is null
        if ( merchantRewardSummary == null ) {

            // Create the MerchantRewardSummary
            merchantRewardSummary = new MerchantRewardSummary();

            // Set the fields
            merchantRewardSummary.setMrsMerchantNo(pointRewardData.getMerchantNo());

            merchantRewardSummary.setMrsCurrencyId(pointRewardData.getRwdCurrencyId());

            merchantRewardSummary.setMrsBranch(pointRewardData.getTxnLocation());

            merchantRewardSummary.setMrsDate(pointRewardData.getTxnDate());

            merchantRewardSummary.setMrsTotalRewarded(pointRewardData.getRewardQty());

            merchantRewardSummary.setMrsRewardExpired(0.0);

            merchantRewardSummary.setMrsTotalRedeemed(0.0);

            merchantRewardSummary.setCreatedBy(pointRewardData.getAuditDetails());

        } else {

            merchantRewardSummary.setMrsTotalRewarded(merchantRewardSummary.getMrsTotalRewarded() + pointRewardData.getRewardQty());

            merchantRewardSummary.setUpdatedBy(pointRewardData.getAuditDetails());

        }

        // Save the MerchantProgramSummary
        merchantRewardSummary = merchantRewardSummaryService.saveMerchantRewardSummary(merchantRewardSummary);

        // Check the merchantRewardSummary
        if ( merchantRewardSummary == null ) {

            // Log the error
            log.error("LoyaltyEngine -> updateMerchantRewardSummary - MerchantRewardSummary update failed ");

            // Return false
            return false;

        }  else {

            // Log the info
            log.info("LoyaltyEngine -> updateMerchantRewardSummary - MerchantRewardSummary update successful : " + merchantRewardSummary.toString());

            // Return true
            return true;
        }
    }

    @Override
    public boolean updateCustomerProgramSummary(PointRewardData pointRewardData) {

        // Get the CustomerProgramSummary
        CustomerProgramSummary customerProgramSummary = customerProgramSummaryService.findByCpsMerchantNoAndCpsLoyaltyIdAndCpsProgramId(pointRewardData.getMerchantNo(), pointRewardData.getLoyaltyId(), pointRewardData.getProgramId());

        // Check if there is data
        if ( customerProgramSummary == null ) {

            customerProgramSummary = new CustomerProgramSummary();

            // Set the fields
            customerProgramSummary.setCpsMerchantNo(pointRewardData.getMerchantNo());

            customerProgramSummary.setCpsLoyaltyId(pointRewardData.getLoyaltyId());

            customerProgramSummary.setCpsProgramId(pointRewardData.getProgramId());

            customerProgramSummary.setCpsProgramAmount(pointRewardData.getTxnAmount());

            customerProgramSummary.setCpsAwardCount(pointRewardData.getRewardQty());

            customerProgramSummary.setCpsProgramQuantity(1);

            customerProgramSummary.setCpsProgramVisit(1);

            customerProgramSummary.setCreatedBy(pointRewardData.getAuditDetails());


        } else {

            // Update/increment the fields
            customerProgramSummary.setCpsProgramQuantity(customerProgramSummary.getCpsProgramQuantity() + 1);

            customerProgramSummary.setCpsProgramAmount(customerProgramSummary.getCpsProgramAmount() + pointRewardData.getTxnAmount());

            customerProgramSummary.setCpsProgramVisit(customerProgramSummary.getCpsProgramVisit() + 1);

            customerProgramSummary.setCpsAwardCount(customerProgramSummary.getCpsAwardCount() + pointRewardData.getRewardQty());

            customerProgramSummary.setUpdatedBy(pointRewardData.getAuditDetails());


        }

        // Save the object and check
        customerProgramSummary = customerProgramSummaryService.saveCustomerProgramSummary(customerProgramSummary);

        // Check if the data has been saved successfully
        if ( customerProgramSummary == null ) {

            // Set the error message
            log.error("LoyaltyEngine -> updateCustomerProgramSummary - CustomerProgramSummary update failed");

            // Return false
            return false;

        } else {

            // Set the message
            log.info("LoyaltyEngine -> updateCustomerProgramSummary - CustomerProgramSummary update successful : " + customerProgramSummary.toString());

            // Return true
            return true;

        }

    }

    @Override
    public boolean updateCustomerSummaryArchive(PointRewardData pointRewardData) {

        // Set the csaLocation to 0
        Long csaLocation = 0L;

        // set the periodYYYY = 0;
        int csaPeriodYyyy = 0;

        // Set the periodQq = 0;
        int csaPeriodQq = 0;

        // Set the peroidMm = 0
        int csaPeriodMm = 0;

        // Set the merchantNO
        Long merchantNo = pointRewardData.getMerchantNo();

        // Set the loyaltyId
        String loyaltyId = pointRewardData.getLoyaltyId();

        // The CustomerSummaryArchive
        CustomerSummaryArchive  customerSummaryArchive = null;


        // Set the index to 1
        int index = 1;

        // Repeat the loop for each condition
        for (index = 1;index <= 8; index++ ) {

            // Check the index and then set the information
            // No location, all 0
            if ( index ==  1 ) {

                // Nothing to do as we have already initialized the variables to default value

            } else if ( index == 2) {

                // Get the current year
                csaPeriodYyyy = Calendar.getInstance().get(Calendar.YEAR);

            } else if ( index == 3) {

                // Get the current month
                int month = Calendar.getInstance().get(Calendar.MONTH);

                // Get the current quarter
                csaPeriodQq = (month/3) + 1;

            } else if ( index == 4 ) {

                // Get the month
                csaPeriodMm = Calendar.getInstance().get(Calendar.MONTH);

            } else if ( index == 5 ) {

                // Get the location
                csaLocation = pointRewardData.getTxnLocation();

                // Clear all the data fields
                csaPeriodYyyy   =   0;
                csaPeriodQq     =   0;
                csaPeriodMm     =   0;

            } else if ( index == 6 ) {

                // Get the current year
                csaPeriodYyyy = Calendar.getInstance().get(Calendar.YEAR);

            } else if ( index == 7) {

                // Get the current month
                int month = Calendar.getInstance().get(Calendar.MONTH);

                // Get the current quarter
                csaPeriodQq = (month/3) + 1;

            } else if ( index == 8 ) {

                // Get the month
                csaPeriodMm = Calendar.getInstance().get(Calendar.MONTH);

            }


            // Check if the CustomerSummaryArchive data exists
            customerSummaryArchive = customerSummaryArchiveService.findByCsaMerchantNoAndCsaLoyaltyIdAndCsaLocationAndCsaPeriodYyyyAndCsaPeriodQqAndCsaPeriodMm(merchantNo,loyaltyId,csaLocation,csaPeriodYyyy,csaPeriodQq,csaPeriodMm);

            // If the data does not exists , then we need to populate and add information
            if ( customerSummaryArchive == null ) {

                // Create the Object
                customerSummaryArchive = new CustomerSummaryArchive();

                // Set the fields
                customerSummaryArchive.setCsaMerchantNo(merchantNo);

                customerSummaryArchive.setCsaLoyaltyId(loyaltyId);

                customerSummaryArchive.setCsaLocation(csaLocation);

                customerSummaryArchive.setCsaPeriodYyyy(csaPeriodYyyy);

                customerSummaryArchive.setCsaPeriodQq(csaPeriodQq);

                customerSummaryArchive.setCsaPeriodMm(csaPeriodMm);

                customerSummaryArchive.setCsaTxnAmount(pointRewardData.getTxnAmount());

                customerSummaryArchive.setCsaQuantity(1);

                customerSummaryArchive.setCsaVisitCount(1);

                customerSummaryArchive.setCreatedBy(pointRewardData.getAuditDetails());

            } else {

                // Increment the fields
                customerSummaryArchive.setCsaVisitCount( customerSummaryArchive.getCsaVisitCount() + 1);

                customerSummaryArchive.setCsaTxnAmount(customerSummaryArchive.getCsaTxnAmount() + pointRewardData.getTxnAmount());

                customerSummaryArchive.setCsaQuantity(customerSummaryArchive.getCsaQuantity() + 1);

                customerSummaryArchive.setUpdatedBy(pointRewardData.getAuditDetails());
            }


            // Save the object and check
            customerSummaryArchive = customerSummaryArchiveService.saveCustomerSummaryArchive(customerSummaryArchive);

            // Check if saved,
            if ( customerSummaryArchive == null ) {

                // Log the error
                log.error("LoyaltyEngine -> updateCustomerSummaryArchive - CustomerSummaryArchive update failed - ");

                // Return false
                return false;

            }
        }

        // Finally return true
        return true;
    }



    /////////////////////// AWARDING FUNCTIONS //////////////////////////////////////////////////////////////////

    @Override
    public boolean awardPointsProxy(PointRewardData pointRewardData,Transaction transaction) throws InspireNetzException {

        // Get the key
        String key = getCurrentProcessQueueKey(pointRewardData.getMerchantNo(),pointRewardData.getLoyaltyId());

        // control the concurrency
        controlConcurrency(key,"awardPoints");

        // set the value for the isAwarded to false initially
        boolean isAwarded = false;

        try {

            // Call the actual award points method
            isAwarded = awardPoints(pointRewardData,transaction);

        } catch (InspireNetzException e) {

            // Unlock the lock
            currentTransactionQueue.removeItem(key);

            // Print the stacktrace
            e.printStackTrace();

            // Rethrow the exception
            throw new InspireNetzException(e.getErrorCode());

        }

        // Finally, remove the key after finishing the processing
        currentTransactionQueue.removeItem(key);

        // REturn the flag
        return isAwarded;

    }

    @Override
    @Transactional(rollbackFor = {InspireNetzException.class,Exception.class})
    public boolean awardPoints(PointRewardData pointRewardData,Transaction transaction) throws InspireNetzException {

        // Get the RewardBalanceInformation
        CustomerRewardBalance customerRewardBalance = customerRewardBalanceService.findByCrbLoyaltyIdAndCrbMerchantNoAndCrbRewardCurrency(pointRewardData.getLoyaltyId(),pointRewardData.getMerchantNo(),pointRewardData.getRwdCurrencyId());

        // Set the prebalance to 0
        double preBalance = 0;

        // Check if the CustomerRewardBalance object is present
        if ( customerRewardBalance != null ) {

            preBalance = customerRewardBalance.getCrbRewardBalance();

        }

        // Set the prebalance for the transaction
        transaction.setTxnRewardPreBal(preBalance);

        // Set the postbalance
        transaction.setTxnRewardPostBal(preBalance + pointRewardData.getRewardQty());

        //
        // Update the reward summary tables
        //
        boolean isRwdTablesUpdated = updateRewardTables(pointRewardData);

        // Check if the tables have been updated
        if ( !isRwdTablesUpdated ) {

            // Log the error
            log.error("LoyaltyEngine -> awardPoints -> Reward tables not updated "+pointRewardData);

            // Throw the exception
            throw new InspireNetzException(APIErrorCode.ERR_TRANSACTION_EXCEPTION);

        }
        // Add the transaction
        boolean isTxnAdded = addTransaction(transaction);

        // Check if the txn is added
        if ( !isTxnAdded ) {

            // Log the error
            log.error("LoyaltyEngine -> awardPoints -> Unable to save the transaction  " + transaction);

            // Throw exception so that @Transactional is rollback
            throw new InspireNetzException(APIErrorCode.ERR_TRANSACTION_EXCEPTION);

        }

        // Check if the customer is a primary
        if ( accountBundlingUtils.isCustomerPrimary(pointRewardData.getLoyaltyId()) ) {

            // Update the linkedBalance
            boolean isLinkedRewardBalanceUpdated = updateLinkedRewardBalance(pointRewardData);

            // Check if the balance is updated
            if ( !isLinkedRewardBalanceUpdated ) {

                // Throw exception so that @Transactional is rollback
                throw new InspireNetzException(APIErrorCode.ERR_TRANSACTION_EXCEPTION);

            }

        } else {

            // Read the AccountBundlingSetting
            AccountBundlingSetting accountBundlingSetting  = accountBundlingSettingService.getDefaultAccountBundlingSetting(pointRewardData.getMerchantNo());

            // Check if the customer has got linking
            LinkedLoyalty linkedLoyalty = accountBundlingUtils.getCustomerLinkedLoyalty(pointRewardData.getMerchantNo(),pointRewardData.getLoyaltyId());

            // If the linkedLoyalty is not null, then update the primary details
            if ( linkedLoyalty != null ) {

                // Update the linkedBalance
                boolean isLinkedBalanceUpdated = updateLinkedBalance(pointRewardData,linkedLoyalty,accountBundlingSetting);

                // Check if the balance is updated
                if ( !isLinkedBalanceUpdated ) {

                    // Throw exception so that @Transactional is rollback
                    throw new InspireNetzException(APIErrorCode.ERR_TRANSACTION_EXCEPTION);

                }
            }

        }


        // add the notifications
        addNotifications(pointRewardData);


        //check the transation type and
        if(transaction.getTxnType() == TransactionType.CHARGE_CARD_DEBIT){

            pointRewardData.setRemarks("for charge card debit");

        }else if(transaction.getTxnType() == TransactionType.CHARGE_CARD_TOPUP && transaction.getTxnAmount()>0){

            pointRewardData.setRemarks("for charge card topup");


        }else if(transaction.getTxnType() == TransactionType.CHARGE_CARD_TOPUP && transaction.getTxnAmount() <0){

            pointRewardData.setRemarks("for charge card refund");


        }else if (transaction.getTxnType() == TransactionType.REWARD_ADUJUSTMENT_AWARDING){
            pointRewardData.setRemarks("("+transaction.getTxnExternalRef()+")");

        }



        String activityRemark = "Awarded "+generalUtils.getFormattedValue(pointRewardData.getRewardQty())+ " points "+pointRewardData.getRemarks()+"";

        if(pointRewardData.isFailedRedemption()){

            activityRemark = "Reversed  "+generalUtils.getFormattedValue(pointRewardData.getRewardQty())+ " points for "+pointRewardData.getTrackingId();

        }

        HashMap<String ,String > smsParam = new HashMap<>(0);
        smsParam.put("#points",pointRewardData.getRewardQty()+"");
        smsParam.put("#remarks",pointRewardData.getRemarks());

        Customer customer=customerService.findByCusLoyaltyIdAndCusMerchantNo(pointRewardData.getLoyaltyId(),pointRewardData.getMerchantNo());

        if(customer==null){

            MessageWrapper messageWrapper = generalUtils.getMessageWrapperObject(MessageSpielValue.AWARD_POINTS,pointRewardData.getLoyaltyId(),pointRewardData.getLoyaltyId(),"","",pointRewardData.getMerchantNo(),smsParam,MessageSpielChannel.SMS,IndicatorStatus.NO );

            userMessagingService.transmitNotification(messageWrapper);

        }else{

            MessageWrapper messageWrapper = generalUtils.getMessageWrapperObject(MessageSpielValue.AWARD_POINTS,customer.getCusLoyaltyId(),"","","",pointRewardData.getMerchantNo(),smsParam,MessageSpielChannel.ALL,IndicatorStatus.YES );

            userMessagingService.transmitNotification(messageWrapper);

        }
        // Add the earning to the customer activity
        customerActivityService.logActivity(
                pointRewardData.getLoyaltyId(),
                CustomerActivityType.EARNING,
                activityRemark,
                transaction.getTxnMerchantNo(),
                transaction.getTxnProgramId()+"#"+transaction.getTxnType()+"#"+transaction.getTxnRewardCurrencyId()+"#"+transaction.getTxnAmount()
        );

        // Finally return true
        return true;

    }

    @Override
    public boolean updateRewardTables(PointRewardData pointRewardData) {


        // Set the flag to false
        boolean updated = false;

        // Update CustomerRewardBalance
        updated = updateCustomerRewardBalance(pointRewardData);

        // If the CustomerRewardBalance is not saved, then return
        if ( !updated )  return false;


        // Update the CustomerRewardExpiry
        updated = updateCustomerRewardExpiry(pointRewardData);

        // Check if updated
        if ( !updated ) return false;



        /*
        // Update the MerchantRewardSummary
        updated = updateMerchantRewardSummary(pointRewardData);

        // check if updated
        if ( !updated ) return false;



        // check if the programId is not null, then we need to update
        // the program summary
        if ( pointRewardData.getProgramId() != null && pointRewardData.getProgramId() != 0L ) {

            // Update the CustomerProgramSummary
            updated = updateCustomerProgramSummary(pointRewardData);

            // Check if updated
            if ( !updated ) return false;


            // Update the MerchantProgramSummary
            updated = updateMerchantProgramSummary(pointRewardData);

            // Check if updated
            if ( !updated ) return false;

        }
        */


        // check if the pointRewardData.isAddAccumulatedBalance is set
        if ( pointRewardData.isAddToAccumulatedBalance() ) {

            // Update the AccumulatedRewardBalance
            updated = updateAccumulatedRewardBalance(pointRewardData);

            // Check if updated
            if ( !updated ) return false;

        }


        // Finally return true;
        return true;
    }

    @Override
    public boolean addTransaction(Transaction transaction) {

        // Save the transaction
        transaction = transactionService.saveTransaction(transaction);

        // Check if the transaction has been saved successfully
        if ( transaction == null || transaction.getTxnId() == null ) {

            // Log the information
            log.error("LoyaltyEngine -> addTransaction : Adding transaction failed : " + transaction.toString());

            // Return false
            return false;

        }


        // Return true
        return true;

    }

    @Override
    public void addNotifications(PointRewardData pointRewardData) {

        // Check if the PointRewardData is existing
        if ( pointRewardData == null ) {

            // If the pointRewardData is null, then we need to return
            return ;
        }


        // Create the Notification for the merchant
        Notification merchantNotification = new Notification();

        merchantNotification.setNtfType(NotificationType.CUSTOMER_ACTIVITY);

        merchantNotification.setNtfRecepientType(NotificationRecepientType.MERCHANT);

        merchantNotification.setNtfRecepient(pointRewardData.getMerchantNo());

        merchantNotification.setNtfText("Awarded " + pointRewardData.getRewardQty() + " " + pointRewardData.getRwdCurrencyName());



        // Check if the user is existing
        if ( pointRewardData.getUserNo() == null || pointRewardData.getUserNo() == 0L ) {

            // If the user is not specified, then we need to set the customer information
            // as the notification information for the merchant
            // Set the fields
            merchantNotification.setNtfSourceName("Customer - "+pointRewardData.getLoyaltyId());

            merchantNotification.setNtfSourceImageId(ImagePrimaryId.ALERT_IMAGE);

            merchantNotification.setNtfStatus(NotificationStatus.NEW);


        } else {


            // First add the notification for the user
            Notification userNotification = new Notification();

            // Set the fields
            userNotification.setNtfType(NotificationType.USER_ACTIVITY);

            userNotification.setNtfSourceName(pointRewardData.getMerchantName());

            userNotification.setNtfText("You have been warded " + pointRewardData.getRewardQty() + " " + pointRewardData.getRwdCurrencyName());

            userNotification.setNtfRecepientType(NotificationRecepientType.USER);

            userNotification.setNtfRecepient(pointRewardData.getUserNo());

            userNotification.setNtfSourceImageId(pointRewardData.getMerchantLogo());

            userNotification.setNtfStatus(NotificationStatus.NEW);

            // Save the UserNotification
            notificationService.saveNotification(userNotification);


            // Set the notification information for the merchant using the user information
            merchantNotification.setNtfSourceName(pointRewardData.getUsrFName() + " " + pointRewardData.getUsrLName());

            merchantNotification.setNtfSourceImageId(pointRewardData.getUsrProfilePic());

            merchantNotification.setNtfActivityUserNo(pointRewardData.getUserNo());


        }


        // Save the MerchantNotification
        notificationService.saveNotification(merchantNotification);
    }

    @Override
    public boolean updateLinkedBalance( PointRewardData pointRewardData, LinkedLoyalty linkedLoyalty,AccountBundlingSetting accountBundlingSetting ) {

        // Get the primary primary information
        Customer primary = customerService.findByCusCustomerNo(linkedLoyalty.getLilParentCustomerNo());

        // Check if the primary exists
        if ( primary == null ) {

            // Log the information
            log.info("LoyaltyEngine -> updateLinkedBalance -> No primary primary information found");

            // Return false
            return false;

        }



        // Create a PointRewardData object for the primary primary to pass to the accumuldated reward balance
        PointRewardData primaryPointRewardData = mapper.map(pointRewardData,PointRewardData.class);

        // Change the loyalty id of the primary
        primaryPointRewardData.setLoyaltyId(primary.getCusLoyaltyId());

        // Set the location
        primaryPointRewardData.setTxnLocation(primary.getCusLocation());



        // Variable holding the prebalance
        double preBalance= 0.0;

        // Get the Balance for LInkedRewardBalance
        LinkedRewardBalance linkedRewardBalance =  linkedRewardBalanceService.findByLrbPrimaryLoyaltyIdAndLrbMerchantNoAndLrbRewardCurrency(primaryPointRewardData.getLoyaltyId(), primaryPointRewardData.getMerchantNo(), primaryPointRewardData.getRwdCurrencyId());

        // If the linkedRewardBalance is not null, then set the prebalance
        if ( linkedRewardBalance != null ) {

            preBalance = linkedRewardBalance.getLrbRewardBalance();

        }


        // Now update the linked reward balance for the primary using the fields
        boolean isUpdated = updateLinkedRewardBalance(primaryPointRewardData);

        // Check if updated
        if ( !isUpdated ) return false;


        // Check if  the isAddAccumulatedBalance need to be updated
        if ( primaryPointRewardData.isAddToAccumulatedBalance() ) {

            // Update the AccumuldateRewardBalance for the primary using the fields
            isUpdated = updateAccumulatedRewardBalance(primaryPointRewardData);

            // Check if updated
            if ( !isUpdated ) return false;

        }




        // Add a transaction showing the transfer from for the primary
        // Add a transaction for crediting the secondary balance to primary
        Transaction creditTxn = accountBundlingUtils.createTransactionForLinkingTransfer(
                pointRewardData.getMerchantNo(),
                primary.getCusLoyaltyId(),
                pointRewardData.getRwdCurrencyId(),
                pointRewardData.getRewardQty(),
                pointRewardData.getLoyaltyId(),
                pointRewardData.getTxnLocation(),
                preBalance,
                CreditDebitInd.CREDIT,
                TransactionType.LINKING_TRANSFER_FROM);

        // Add the transaction
        transactionService.saveTransaction(creditTxn);


        // If the accountBundlingSetting linking type is transfer, then set the balance to 0 for secondary
        if ( accountBundlingSetting.getAbsLinkingType() == AccountBundlingLinkingType.TRANSFER_TO_PRIMARY  ) {


            // Add a transaction for the debit of point from the secondary to primary
            Transaction debitTxn = accountBundlingUtils.createTransactionForLinkingTransfer(
                    pointRewardData.getMerchantNo(),
                    pointRewardData.getLoyaltyId(),
                    pointRewardData.getRwdCurrencyId(),
                    pointRewardData.getRewardQty(),
                    primary.getCusLoyaltyId(),
                    pointRewardData.getTxnLocation(),
                    preBalance,
                    CreditDebitInd.DEBIT,
                    TransactionType.LINKING_TRANSFER_TO);

            // save the transaction
            transactionService.saveTransaction(debitTxn);


            // Get the child Customer object
            Customer child = customerService.findByCusLoyaltyIdAndCusMerchantNo(pointRewardData.getLoyaltyId(),pointRewardData.getMerchantNo());

            // If the child is not found, then return false
            if ( child == null ) {

                // Log the information
                log.info("LoyaltyEngine -> updateLinkedBalance -> No child information found");

                // return false
                return false;

            }


            // Move the primary reward balance for the secondary to primary
            accountBundlingUtils.moveCustomerRewardBalanceToPrimary(primary,child);

            // Move the customer reward expiry for the secondary to primary
            accountBundlingUtils.moveCustomerRewardExpiryToPrimary(primary,child);


        }


        // finally return true
        return true;

    }



    ///////////////////////// POINT DEDUCT FUNCTIONS ////////////////////////////////////////////////////

    @Override
    public boolean deductPointsProxy(PointDeductData pointDeductData) throws InspireNetzException {

        // Get the key
        String key = getCurrentProcessQueueKey(pointDeductData.getMerchantNo(),pointDeductData.getLoyaltyId());

        // control the concurrency
        controlConcurrency(key,"deductPoints");

        // Set the isDeducted to false
        boolean isDeducted = false;

        try {

            // call the method
            isDeducted = deductPoints(pointDeductData);

        } catch (InspireNetzException e) {

            // Remove the lock
            currentTransactionQueue.removeItem(key);

            // Print the stack trace
            e.printStackTrace();

            // Rethrow the exception with error code
            throw new InspireNetzException(e.getErrorCode());
        }

        // Finally, remove the key after finishing the processing
        currentTransactionQueue.removeItem(key);

        // Return value
        return isDeducted;

    }

    /**
     * Function to deduct the points for a given request
     * The request is given in the form of PointDeductData object
     * This function will do the following
     *
     * 1) Get the list of CustomerRewardExpiry objects from the linked customers
     * 2) Check if the customer in transaction is a primary/ or is linked to a primary
     *    If any of the above condition is true, deduct points from the LInkedREwardBalance for the primary
     * 3) Add a transaction for each debit for the CustomerRewardBalance
     *
     *
     * @param pointDeductData       - The PointDedcutData object
     *
     * @return                      - Return true if the deduction was successful
     * @throws InspireNetzException - Throws InspireNetzException with the appropriate api errorcode
     *
     */
    @Override
    @Transactional (rollbackFor = {InspireNetzException.class,Exception.class})
    public boolean deductPoints(PointDeductData pointDeductData) throws InspireNetzException {


        // Flag indicating whether customer isLinked
        boolean  isLinked = false;

        // Create the variable holding the cre reward balance
        double creRewardBalance =  0;

        // Variable holding the reward qty used for current iteration
        double creCurrRewardQty = 0;

        // Variable holding the quantity to be redeemed
        double redeemQty = 0;

        // Set the totalRewardQty and redeemQty to the quantity to be deducted
        redeemQty = pointDeductData.getRedeemQty();



        // First try to get the primary for the customer
        Customer customer = accountBundlingUtils.getPrimaryCustomerForCustomer(pointDeductData.getMerchantNo(),pointDeductData.getLoyaltyId());

        // If the customer is null, then customer doesnot have a primary and we need to
        // set the customer object to the customer information of the current customer in
        // transaction
        if ( customer == null ) {

            // Set the customer object to be the customer for the current transaction
            customer = customerService.findByCusLoyaltyIdAndCusMerchantNo(pointDeductData.getLoyaltyId(), pointDeductData.getMerchantNo());


        } else {

            // Set the isLinked to true
            isLinked = true;

        }


        // if the customer is not found, then return false
        if ( customer == null ) {

            // Log the information
            log.info("LoyaltyEngine -> deductPoints -> Customer is not found");

            // throw exception
            throw new InspireNetzException(APIErrorCode.ERR_NO_DATA_FOUND);

        }

        // Processing
        log.error("Deduct points processing : " + Thread.currentThread().getName());

        // Get the CustomerRewardExpiry List for customer object
        // The idea here is to pass the customer object as the Primary if the customer
        // is linked as the getCustomerRewardExpiryList will get the linked expiries as well
        //
        // But if there is no primary, then we pass the same customer information as in
        // the transaction to get the reward expiry only for the current customer
        List<CustomerRewardExpiry> customerRewardExpiryList = getCustomerRewardExpiryList(customer, pointDeductData.getRwdCurrencyId());


        // Go through each of the item in the List for CustomerRewardExpiry
        for(CustomerRewardExpiry customerRewardExpiry : customerRewardExpiryList ) {

            // Get the creRewardBalance
            creRewardBalance = customerRewardExpiry.getCreRewardBalance();

            // Check if the current balance is sufficient for the redemption
            if ( creRewardBalance >= redeemQty  ) {

                creRewardBalance -= redeemQty;

                creCurrRewardQty = redeemQty;

                redeemQty = 0;

            } else {

                redeemQty = redeemQty - creRewardBalance;

                creCurrRewardQty = creRewardBalance;

                creRewardBalance = 0;
            }


            // Set the updated reward balance in the CustomerRewardExpiry object
            customerRewardExpiry.setCreRewardBalance(creRewardBalance);

            // Set the updated_by auditDetails as the createdby field from the redemption request
            customerRewardExpiry.setUpdatedBy(pointDeductData.getAuditDetails());

            // Update the value
            customerRewardExpiryService.saveCustomerRewardExpiry(customerRewardExpiry);



            // Get the current balance for the customer
            CustomerRewardBalance customerRewardBalance = null;

            // Get the current balance for the customer
            customerRewardBalance = customerRewardBalanceService.findByCrbLoyaltyIdAndCrbMerchantNoAndCrbRewardCurrency(
                    customerRewardExpiry.getCreLoyaltyId(),
                    customerRewardExpiry.getCreMerchantNo(),
                    customerRewardExpiry.getCreRewardCurrencyId()
            );

                       //Add the transaction for the current debit to the
            Transaction transaction = loyaltyEngineUtils.getTransactionForPointDeductData(pointDeductData,customerRewardBalance.getCrbLoyaltyId(),creCurrRewardQty,customerRewardBalance.getCrbRewardBalance());

            // Save the transaction
            transaction = transactionService.saveTransaction(transaction);

            // Check if the transaction has saved
            if ( transaction.getTxnId() == null ) {

                // Log the information
                log.info("LoyaltyEngine -> deductPoints -> Transaction has been saved");

                // throw exception
                throw new InspireNetzException(APIErrorCode.ERR_TRANSACTION_EXCEPTION);

            }



            // Update the reward balance after deduction
            customerRewardBalance.setCrbRewardBalance(customerRewardBalance.getCrbRewardBalance() - creCurrRewardQty);

            // Set the updated_by auditDetails as the createdby field from the redemption request
            customerRewardBalance.setUpdatedBy(pointDeductData.getAuditDetails());

            // Save the updated the customer reward balance
            customerRewardBalanceService.update(customerRewardBalance);


            // If the redeemQty has become 0, then we don't need to update anything more
            if( redeemQty == 0  ){

                break;

            }

        }



        // If the loop is over and the redeemQty is greater than 0, then we need to report exception
        if ( redeemQty > 0 ) {

            // Log the information
            log.info("Loyalty Engine -> deductPoints -> Not enough balance ");

            MessageWrapper messageWrapper = generalUtils.getMessageWrapperObject(MessageSpielValue.REDEMPTION_FAILED_NOT_ENOUGH_POINTS,pointDeductData.getLoyaltyId(),"","","",pointDeductData.getMerchantNo(),new HashMap<String, String>(0),MessageSpielChannel.ALL,IndicatorStatus.YES);

            userMessagingService.transmitNotification(messageWrapper);

            // Throw exception
            throw new InspireNetzException(APIErrorCode.ERR_INSUFFICIENT_POINT_BALANCE);

        }



        // If the customer is linked, then update the linked reward balance
        if ( isLinked ) {

            // Check if the deductLinkedRewardBalance is true, if its false, the linkedrewardbalance is failed
            // for a linked account
            boolean isDeductLinkedRewardBalance = deductLinkedRewardBalance(pointDeductData,customer.getCusLoyaltyId());

            // Check if the linkedRewardBalance was successfully deducted
            if ( !isDeductLinkedRewardBalance ) {

                // Log the information
                log.info("Loyalty Engine -> deductPoints -> LinkedReweardBalnace reduction failed ");

                // Throw exception
                throw new InspireNetzException(APIErrorCode.ERR_TRANSACTION_EXCEPTION);

            }

        }

        //check for the transaction type and set the remarks
        if(pointDeductData.getTxnType() == TransactionType.REWARD_ADJUSTMENT_DEDUCTING){

            pointDeductData.setRemarks("("+pointDeductData.getExternalRef()+")");
        }

        //add customer activity for point deduction
        customerActivityService.logActivity(customer.getCusLoyaltyId(),CustomerActivityType.POINT_DEDUCTION,"Deducted "+generalUtils.getFormattedValue(pointDeductData.getRedeemQty())+" points"+pointDeductData.getRemarks(),customer.getCusMerchantNo(),pointDeductData.getTxnType()+"#");

        // If the totalRewardQty is 0, then return true
        return true;

    }


    /**
     * Function to get the List of CustomerRewardExpiry entries based on the linked loyalty
     * Here we pass the primary loyaltyid ( the loyalty id of the customer who is redeeming)
     * and the reward currency id of the currency that is needed for redemption
     *
     * @param customer      : The  customer object
     * @param rwdId         : The reward currency id for the reward currency
     * @return              : CustomerRewardExpiry List
     */
    @Override
    public List<CustomerRewardExpiry> getCustomerRewardExpiryList(Customer customer,Long rwdId) {

        // List holding the CustomerRewardExpiries in a List of HashMaps
        List<CustomerRewardExpiry> listCustomerRewardExpiry = new ArrayList<CustomerRewardExpiry>();

        // Now the get the list of CustomerRewardExpiry entry for the current customer
        // Get the CustomerRewardExpiry object for the cusotmer for the given reward currency id
        List<CustomerRewardExpiry> customerRewardExpiryList = customerRewardExpiryService.findByCreLoyaltyIdAndCreRewardCurrencyId(
                customer.getCusLoyaltyId(),
                rwdId
        );

        // If the returned value is not null, then continue to the next item
        if ( customerRewardExpiryList != null ) {

            // Add the list to the listCustomerRewardExpiry
            listCustomerRewardExpiry.addAll(customerRewardExpiryList);

        }



        // Get the list of linked loyalty members
        List<LinkedLoyalty> linkedLoyaltyList = linkedLoyaltyService.findByLilParentCustomerNo(customer.getCusCustomerNo());

        // If there are no linked customers, then we just initialize it to an empty array
        if ( linkedLoyaltyList == null ) {

            linkedLoyaltyList = new ArrayList<LinkedLoyalty>();

        }




        // Go through each of the llCustomers and then get the reward expiry for them
        for(LinkedLoyalty linkedLoyalty: linkedLoyaltyList) {

            // Get the customer object
            Customer secondary = customerService.findByCusCustomerNo(linkedLoyalty.getLilChildCustomerNo());

            // Get the CustomerRewardExpiry object for the cusotmer for the given reward currency id
            customerRewardExpiryList = customerRewardExpiryService.findByCreLoyaltyIdAndCreRewardCurrencyId(
                    secondary.getCusLoyaltyId(),
                    rwdId
            );


            // If the returned value is not null, then continue to the next item
            if ( customerRewardExpiryList == null ) continue;


            // Add the list to the listCustomerRewardExpiry
            listCustomerRewardExpiry.addAll(customerRewardExpiryList);


        }


        // Before we return the List, we need to sort the CustomerRewardExpiry objects using the
        // expiry date field of the CRE table
        //
        // Create the BeanComparator with the field as creExpiryDt
        //BeanComparator fieldComparator = new BeanComparator("creExpiryDt");

        CustomerRewardExpiryComparator customerRewardExpiryComparator = new CustomerRewardExpiryComparator();
        // Sort the List
        Collections.sort(listCustomerRewardExpiry, customerRewardExpiryComparator);

        List<CustomerRewardExpiry > customerRewardExpiries = new ArrayList<>();

        // Store the today date in sql
        java.sql.Date today = new java.sql.Date(new Date().getTime());

        // Iterate through the list and see if there are any entries that has got
        // expiry date older than today, then remote them
        for ( CustomerRewardExpiry customerRewardExpiry : listCustomerRewardExpiry ) {

            // Check the item expiry date is before today
            // Also check if the reward balance for cre is greater than 0
            if ( customerRewardExpiry.getCreExpiryDt().compareTo(today) >= 0  && customerRewardExpiry.getCreRewardBalance() > 0  ) {

                customerRewardExpiries.add(customerRewardExpiry);

            }

        }


        // Return the list
        return customerRewardExpiries;

    }


    /**
     * Function to check if the customer in transaction is a primary is part of a linking.
     * If the customer is primary or is a part of a linking,
     * the linkedrewardbalance for the primary in case is deducted
     *
     *
     * @param pointDeductData       - The PointDeductData object
     * @param primaryLoyaltyId      - Loyalty id of the primary customer
     *
     *
     * @return              - True if the deduction was successful
     *                        False otherwise
     */
    public boolean deductLinkedRewardBalance(PointDeductData pointDeductData,String primaryLoyaltyId) {



        // Update the LinkedRewardBalance for the primary
        // Get the linked reward balance
        LinkedRewardBalance linkedRewardBalance = linkedRewardBalanceService.findByLrbPrimaryLoyaltyIdAndLrbMerchantNoAndLrbRewardCurrency(
                primaryLoyaltyId,
                pointDeductData.getMerchantNo(),
                pointDeductData.getRwdCurrencyId()

        );


        // Update the balance
        linkedRewardBalance.setLrbRewardBalance(linkedRewardBalance.getLrbRewardBalance() - pointDeductData.getRedeemQty());

        // Update the entity
        linkedRewardBalance = linkedRewardBalanceService.update(linkedRewardBalance);



        // Check if the linkedRewardBalance is set
        if ( linkedRewardBalance == null || linkedRewardBalance.getLrbId() == null ) {

            return false;

        } else {

            return true;

        }

    }







    ///////////////////////// REWARD EXPIRY PROCESSING ////////////////////////////////////////////
    /**
     *  Function to run the rewad expiry procssing for the merchants
     *  Here this function just calls the doRewardExpiryProcessing for each merchant
     *  in the system.
     *
     */
    @Override
    @Scheduled(cron = "${scheduled.rewardexpiry}")
    public void runRewardExpiryProcessing() {

        // Get All merchant from the system
        List<Merchant> merchantList =merchantService.findAll();

        for (Merchant merchant :merchantList){

            // Log the information
            log.info("LoyaltyEngine -> runRewardExpiryProcessing : Reward expiry processing for "+ merchant.getMerMerchantNo());

            // Process the data
            boolean isProcessed = doRewardExpiryProcessing(merchant.getMerMerchantNo());

        }


    }


    /**
     * Function to do the reward expiry processing for a given merchant number.
     * Here we will get the list of the entries that are expired or going to expire today
     * and then do a deductPoints call to make the deduction with transaction type as
     * EXPIRATION
     *
     * @param merchantNo        - The merchant number of the merchant for whom the processing need to be
     *                            done
     * @return                  - true if the processing completed successfully
     *                            false if the processing was interuptted
     *
     * @throws InspireNetzException
     */
    protected boolean doRewardExpiryProcessing(Long merchantNo)  {

        // Set the pageIndex to 0
        int pageIndex = 0;

        // Set the entryCount to 0
        Long entryCount = 0L;

        // Get the RewardExpiry for the merchant number in a pageable
        Page<CustomerRewardExpiry> customerRewardExpiryPage = customerRewardExpiryService.getExpiredCustomerRewardExpiry(merchantNo, loyaltyEngineUtils.constructPageSpecification(pageIndex, MAX_ITEMS_IN_PAGE, "creExpiryDt"));

        // Check if the page contains data
        if ( customerRewardExpiryPage == null ) {

            //  Log the information
            log.info("Loyalty Engine -> doRewardExpiryProecessing : No entries to process");

            // Return
            return true;

        }


        // Repeat the loop till the page has got content
        while( customerRewardExpiryPage !=  null && customerRewardExpiryPage.hasContent() ) {

            // Repeat through the items in the page as an iterable
            for(CustomerRewardExpiry customerRewardExpiry: customerRewardExpiryPage) {



                try {

                    // Now call the deductPoints
                    clearExpiredBalance(customerRewardExpiry);

                    // Log the information
                    log.info("LoyaltyEngine -> doCustomerRewardExpiry : Expired " + customerRewardExpiry);


                } catch (InspireNetzException e) {

                    // Print the stack trace
                    e.printStackTrace();

                    // Log the information
                    log.error("LoyaltyEngine -> doCustomerRewardExpiry -> Exception during processing for entry " + customerRewardExpiry);

                }

            }

            // if there is no next page, then break the loop
            if ( !customerRewardExpiryPage.hasNextPage() ) break;

            // Get the next page of customers
            customerRewardExpiryPage = customerRewardExpiryService.getExpiredCustomerRewardExpiry(merchantNo, loyaltyEngineUtils.constructPageSpecification(++pageIndex, MAX_ITEMS_IN_PAGE, "creExpiryDt"));

        }

        // finally return true
        return true;

    }

    /**
     * Function to clear the expired balance from the customer account
     * Here the function will deduct the entry from the customerRewardBalance and also
     * check if the customer is linked ,
     * If the customer is linked, then the LinkedRewardBalance is also deducted
     *
     * @param customerRewardExpiry      - The CustomerRewardExpiry entry that need to be deducted
     * @return                          - true if the deduction was successsful,
     *                                    false if the deduction was a failure
     *
     * @throws InspireNetzException
     */
    @Transactional(rollbackFor = {InspireNetzException.class,Exception.class,RuntimeException.class})
    public boolean clearExpiredBalance( CustomerRewardExpiry customerRewardExpiry ) throws InspireNetzException {


        // Create the PointDeductData object
        PointDeductData pointDeductData = new PointDeductData();

        // Set the fields
        pointDeductData.setTxnDate(new java.sql.Date(new Date().getTime()));

        pointDeductData.setExternalRef("0");

        pointDeductData.setRedeemQty(customerRewardExpiry.getCreRewardBalance());

        pointDeductData.setTxnLocation(0L);

        pointDeductData.setInternalRef(customerRewardExpiry.getCreId().toString());

        pointDeductData.setTxnType(TransactionType.EXPIRATION);

        pointDeductData.setLoyaltyId(customerRewardExpiry.getCreLoyaltyId());

        pointDeductData.setMerchantNo(customerRewardExpiry.getCreMerchantNo());

        pointDeductData.setRwdCurrencyId(customerRewardExpiry.getCreRewardCurrencyId());



        // Get the customerRewardBalance entry
        CustomerRewardBalance customerRewardBalance = customerRewardBalanceService.findByCrbLoyaltyIdAndCrbMerchantNoAndCrbRewardCurrency(customerRewardExpiry.getCreLoyaltyId(),customerRewardExpiry.getCreMerchantNo(),customerRewardExpiry.getCreRewardCurrencyId());

        // If the customerRewardBalance is null, then throw error
        if ( customerRewardBalance == null ) {

            // Log the error
            log.error("LoyaltyEngine -> clearExpiredBalance : No corresponding reward balance information found - " + customerRewardExpiry);

            // Throw not found exception
            throw new InspireNetzException(APIErrorCode.ERR_NO_DATA);


        }


        // Deduct the customer reward balance
        customerRewardBalance.setCrbRewardBalance( customerRewardBalance.getCrbRewardBalance() - customerRewardExpiry.getCreRewardBalance());

        // Update the customerRewardBalance
        customerRewardBalance = customerRewardBalanceService.saveCustomerRewardBalance(customerRewardBalance);



        // Check if there is a linked account
        // First try to get the primary for the customer
        Customer customer = accountBundlingUtils.getPrimaryCustomerForCustomer(pointDeductData.getMerchantNo(),pointDeductData.getLoyaltyId());

        // If the customer is not null, then either the customer is a primary
        // or is linked
        // In either case, we need to deduct the  LinkedRewardBalance
        if ( customer != null ) {

            // Deduct the linked reward balance
            boolean isDeducted = deductLinkedRewardBalance(pointDeductData,customer.getCusLoyaltyId());

            // Check if the balance is deducted
            if ( !isDeducted ) {

                // Log the error
                log.error("LoyaltyEngine -> clearExpiredBalance : Linked reward balance deduction failed");

                // Throw not found exception
                throw new InspireNetzException(APIErrorCode.ERR_NO_DATA);

            }
        }


        // finally log the transaction
        //
        // Get the transaction for reward expiry
        Transaction transaction  = loyaltyEngineUtils.getTransactionForExpiry(customerRewardExpiry);

        //if reward expiry is for account deactivation , set transaction type as account deactivation
        if(customerRewardExpiry.isAccountDeactivation()){

            //set transaction type
            transaction.setTxnType(TransactionType.ACCOUNT_DEACTIVATION);

        }

        // Save the transaction
        transaction = transactionService.saveTransaction(transaction);


        // Set the customerRewardExpiry balance to 0
        customerRewardExpiry.setCreRewardBalance(0.0);

        // Update the customerRewardExpiry
        customerRewardExpiry = customerRewardExpiryService.saveCustomerRewardExpiry(customerRewardExpiry);

        //log reward expiry to customer activities
        customerActivityService.logActivity(customerRewardExpiry.getCreLoyaltyId(),CustomerActivityType.REWARD_EXPIRY,"Expired "+generalUtils.getFormattedValue(pointDeductData.getRedeemQty())+" points",pointDeductData.getMerchantNo(),"");

        // Return true
        return true;

    }


    private class CustomerRewardExpiryComparator  implements Comparator<CustomerRewardExpiry> {

        public int compare(CustomerRewardExpiry cre1, CustomerRewardExpiry cre2) {

            // Return the date comparison for the first date
            // and the second data params
            return cre1.getCreExpiryDt().compareTo(cre2.getCreExpiryDt());


        }
    }


    ////////////////////////  EVENT BASED AWARDING  //////////////////////////////////
    /**
     *  Function to process the awarding for merchant triggered events.
     *  Like milestone achievement awarding .
     * doEventBasedAwarding
     * @param customerRewardActivity
     */

    public void doEventBasedAwarding(CustomerRewardActivity customerRewardActivity) throws InspireNetzException {

        // Check the status of the customerRewardActivity
        if ( customerRewardActivity.getCraStatus() != CustomerRewardActivityStatus.NEW ) {

            // Log the information
            log.info("LoyaltyEngine -> doCustomerRewardActivityProcessing  : CustomerRewardActivity is not new");

            // return
            return ;

        }

        //find the merchant details of customer
        Customer customer =getCustomerDetails(customerRewardActivity.getCraCustomerNo());

        //merchant initialize
        Long merchantNo=0L;

        //check merchant details for the customer
        if(customer !=null){

            merchantNo =customer.getCusMerchantNo()==null?0L:customer.getCusMerchantNo();

        }else {

            return;
        }

        // List the Loyalty programs based on the activity triggered loyalty type
        List<LoyaltyProgram> loyaltyProgramList = loyaltyProgramService.findByPrgMerchantNoAndPrgProgramDriver(merchantNo, LoyaltyProgramDriver.EVENT_BASED);

        // If there are no items, then return
        if ( loyaltyProgramList == null || loyaltyProgramList.isEmpty() ) {

            return ;

        }


        // Go// Check if ht through the list of programs for processing
        for ( LoyaltyProgram loyaltyProgram : loyaltyProgramList ) {

            // Log the information
            log.info("LoyaltyEngine -> doCustomerRewardActivityProcessing  : Processing for"  + loyaltyProgram);

            // Call the procssing
            boolean isProcessed = doEventBasedProcessingForProgram(loyaltyProgram, customerRewardActivity);

            // If not processed, then show message
            if ( !isProcessed ) {

                // Log infor
                log.info("LoyaltyEngine -> doCustomerRewardActivityProcessing - No awarding " + loyaltyProgram);

            }

        }

    }

    /*
        Methods starts the processing for event based awarding.
        It gets the basic customer informations and , valid loyalty
        programs. After checking the validity it will call the awarding process
     */
    @Transactional(rollbackFor = {InspireNetzException.class,Exception.class})
    private boolean doEventBasedProcessingForProgram(LoyaltyProgram loyaltyProgram, CustomerRewardActivity customerRewardActivity) throws InspireNetzException {

        // Get the customer information for the given customer number in the activity
        Customer customer = customerService.findByCusCustomerNo(customerRewardActivity.getCraCustomerNo());

        // Check if the customer is valid
        boolean isCustomerValid = isCustomerValid(loyaltyProgram,customer);

        // Check if the cutomer is valid and if not, show message
        if ( !isCustomerValid ) {

            // Log the information
            log.info("Loyalty Engine ->   doEventBasedProcessingForProgram : Customer is not valid"  );

            // return false
            return false;

        }


        // Get Today
        Date today = new Date();

        // Check if the program is valid
        boolean isProgramValid = isProgramValid(loyaltyProgram,today);

        // If the program is not valid, return false
        if ( !isProgramValid ) {

            // Log the information
            log.info("Loyalty Engine ->   doEventBasedProcessingForProgram : Program is not valid"  );

            // return false
            return false;

        }


        // Check if the eligiliby is valid
        if ( !isCustomerEligibilityValid(loyaltyProgram,customer) ) {

            // Set the logging information
            log.info("LoyaltyEngine -> doEventBasedProcessingForProgram : Invalid eligibility of customer");

            // Return false
            return false;


        }


        // Log the information
        log.info("LoyaltyEngine -> doEventBasedProcessingForProgram -> Processing Loyalty program " + loyaltyProgram + " for customer : " + customer );

        // Get the points for the customer
        double pointsAwarded = processEventBasedProgram(loyaltyProgram, customerRewardActivity, customer);

        // Finally return true
        return true;

    }

    /*
        Methods processes each loyalty program with the promotional event associated
        with it. it will fetch customer promotional event and promotional event data..

        programs. After checking the validity it will call the awarding process
     */
    private double processEventBasedProgram(LoyaltyProgram loyaltyProgram, CustomerRewardActivity customerRewardActivity, Customer customer) throws InspireNetzException {

        // Variable holding the total points awarded by this program
        double pointAwarded = 0.0;

        //get the customer promotional event from reward activity reference
        CustomerPromotionalEvent customerPromotionalEvent = customerPromotionalEventService.findByCpeId(Long.parseLong(customerRewardActivity.getCraActivityRef()));

        //get the promotional event from the reference
        PromotionalEvent promotionalEvent = promotionalEventService.findByPreId(customerPromotionalEvent.getCpeEventId());

        // Create the loyaltyProgramSku
        List<LoyaltyProgramSku> loyaltyProgramSkuList = loyaltyProgramSkuService.findByLpuProgramId(loyaltyProgram.getPrgProgramNo());

        // If the list is emtpy, then
        if ( loyaltyProgramSkuList == null || loyaltyProgramSkuList.isEmpty() ) {

            // Log the information
            log.info("LoyaltyEngine -> processRewardActivityProgram : loyalty program sku is empty");

            // reutrn 0.0
            return 0.0;

        }


        // Get the effective tier for the customer
        Tier cusTier = accountBundlingUtils.getEffectiveTierForCustomer(customer);

        // If the tier is null, then log error and return
        if ( cusTier == null ) {

            // Log the information
            log.info("LoyaltyEngine -> processRewardActivityProgram : Customer does not have an effective tier");

            // return 0.0
            return 0.0;

        }


        // Go through the list and check the points
        for ( LoyaltyProgramSku loyaltyProgramSku : loyaltyProgramSkuList ) {

            // Check if the tier is valid for the customer
            if ( loyaltyProgramSku.getLpuTier() != 0L && cusTier.getTieId().longValue() != loyaltyProgramSku.getLpuTier().longValue() ) {

                // Log the information
                log.info("LoyaltyEngine -> processRewardActivityProgram : tier not matching ");

                // Continue
                continue;


            }


            // Check if the customer reward activity type is same as the lpu event registration
            if ( customerRewardActivity.getCraType() == CustomerRewardingType.MERCHANT_EVENT ) {

                // Check if the lpuType is  event enrollment and event code is matching
                if ( loyaltyProgramSku.getLpuItemType() == LoyaltyProgramSkuType.MERCHANT_EVENT  &&
                        promotionalEvent.getPreEventCode().equals(loyaltyProgramSku.getLpuItemCode())   ) {

                    //get the points for the customer's role
                    double pointsAwardedBasedOnCustomerRole = getPointsAwardedBasedOnCustomer(customer, customerPromotionalEvent.getCpeProduct(), customerRewardActivity.getCustomerRole(), loyaltyProgramSku);

                    if(pointsAwardedBasedOnCustomerRole > 0){

                        //get point reward data object
                        PointRewardData pointRewardData = getPointRewardDataObjectForEventBasedProgram(customer,loyaltyProgram,pointsAwardedBasedOnCustomerRole);

                        //award points for customer
                        awardPointsForEventBasedProgram(pointRewardData,customer,loyaltyProgram);

                    }

                    //if customer role is referee we have to award points to the referrer mins also
                    if(customerRewardActivity.getCustomerRole() == LoyaltyRefferalRoles.REFERREE){

                        int referralRewardBasis = customerReferralService.getReferralSettingForMerchant(customer.getCusMerchantNo());

                        //get all the customer referrals
                        List<CustomerReferral> customerReferrals = customerReferralService.findByCsrMerchantNoAndCsrRefMobileAndCsrRefStatusOrderByCsrRefTimeStampAsc(customer.getCusMerchantNo(), customer.getCusLoyaltyId(), CustomerReferralStatus.PROCESSED);

                        //iterate through the referrals and process awarding
                        for(CustomerReferral customerReferral : customerReferrals){

                            //get the customer details
                            Customer referredCustomer = customerService.findByCusLoyaltyIdAndCusMerchantNo(customerReferral.getCsrLoyaltyId(),customerReferral.getCsrMerchantNo());

                            String productReference = "";

                            if(referralRewardBasis == MerchantReferralSetting.PRODUCT_BASED){

                                //get the customer referred product
                                productReference = customerReferral.getCsrProduct();

                            } else if( referralRewardBasis == MerchantReferralSetting.CUSTOMER_BASED){

                                //get the product which customer achieved milestone
                                productReference = customerPromotionalEvent.getCpeProduct();

                            }

                            //get points for the customer
                            pointAwarded = getPointsAwardedBasedOnCustomerRole(referredCustomer, productReference,LoyaltyRefferalRoles.REFFERRER, loyaltyProgramSkuList,promotionalEvent);

                            //get point rewrad data object
                            PointRewardData pointRewardData = getPointRewardDataObjectForEventBasedProgram(referredCustomer,loyaltyProgram,pointAwarded);

                            //award points for the referrer
                            awardPointsForEventBasedProgram(pointRewardData, referredCustomer, loyaltyProgram);

                        }

                    }

                }

            }

        }

        // Return the pointAwareded field
        return pointAwarded;
        }


    private PointRewardData getPointRewardDataObjectForEventBasedProgram(Customer customer, LoyaltyProgram loyaltyProgram, double pointAwarded) {

        // Get the PointRewardData object
        PointRewardData pointRewardData = loyaltyEngineUtils.getPointRewardDataForDTProcessing(loyaltyProgram, customer);

        // Set the rewardQty
        pointRewardData.setRewardQty(pointAwarded);

        // Set the totalQty
        pointRewardData.setTotalRewardQty(pointAwarded);

        // Get the expiryDate
        Date expiryDate = loyaltyEngineUtils.getExpiryDateForRewardCurrency(loyaltyProgram.getPrgCurrencyId());

        // Set the expiry Date
        pointRewardData.setExpiryDt(new java.sql.Date(expiryDate.getTime()));

        return pointRewardData;

    }

    private boolean awardPointsForEventBasedProgram(PointRewardData pointRewardData,Customer customer,LoyaltyProgram loyaltyProgram) throws InspireNetzException {

        // If the points are not awarded, then return
        if ( pointRewardData.getRewardQty() == 0.0 ) {

            // Log the information
            log.info("LoyaltyEngine -> doEventBasedProcessingForProgram -> No points awarded");

            // return true
            return false;

        }

        // Variable holding the prebalace
        double preBalance = 0.0;

        // If points were awarded, then get the current balance for the customer
        // for the given reward currency id
        CustomerRewardBalance customerRewardBalance = customerRewardBalanceService.findByCrbLoyaltyIdAndCrbMerchantNoAndCrbRewardCurrency(customer.getCusLoyaltyId(),customer.getCusMerchantNo(),loyaltyProgram.getPrgCurrencyId());

        // If the customerRewardBalance is not existing, then set the preBalance to 0
        if ( customerRewardBalance == null ) {

            // Set the preBalance to 0
            preBalance = 0.0;

        } else {

            // Set the preBalance to the cusotmer reward balance
            preBalance = customerRewardBalance.getCrbRewardBalance();

        }

        // Get the Transaction for the PointRewardData
        Transaction transaction = loyaltyEngineUtils.getTransactionForPointRewardData(pointRewardData,preBalance,loyaltyProgram.getPrgProgramNo().toString());

        // Call the awardPoints
        boolean isAwarded = awardPointsProxy(pointRewardData,transaction);

        // Check if awarded
        if ( !isAwarded ) {

            // Log information
            log.info("LoyaltyEngine -> doEventBasedProcessingForProgram -> Awarding failed");

            // Throw exception
            throw new InspireNetzException(APIErrorCode.ERR_OPERATION_FAILED);

        }

        return isAwarded;
    }

    private boolean awardPointsForTransactionBasedReferralProgram(PointRewardData pointRewardData,Customer customer,LoyaltyProgram loyaltyProgram,Sale sale) throws InspireNetzException {

        // If the points are not awarded, then return
        if ( pointRewardData.getRewardQty() == 0.0 ) {

            // Log the information
            log.info("LoyaltyEngine -> doEventBasedProcessingForProgram -> No points awarded");

            // return true
            return false;

        }

        // Variable holding the prebalace
        double preBalance = 0.0;

        // If points were awarded, then get the current balance for the customer
        // for the given reward currency id
        CustomerRewardBalance customerRewardBalance = customerRewardBalanceService.findByCrbLoyaltyIdAndCrbMerchantNoAndCrbRewardCurrency(customer.getCusLoyaltyId(),customer.getCusMerchantNo(),loyaltyProgram.getPrgCurrencyId());

        // If the customerRewardBalance is not existing, then set the preBalance to 0
        if ( customerRewardBalance == null ) {

            // Set the preBalance to 0
            preBalance = 0.0;

        } else {

            // Set the preBalance to the cusotmer reward balance
            preBalance = customerRewardBalance.getCrbRewardBalance();

        }

        // Get the Transaction for the PointRewardData
        Transaction transaction = loyaltyEngineUtils.getTransactionForPointRewardData(pointRewardData,preBalance,loyaltyProgram.getPrgProgramNo().toString());

        //set internal and external reference
        transaction.setTxnInternalRef("0");

        //external reference
        transaction.setTxnExternalRef(sale.getSalPaymentReference());

        // Call the awardPoints
        boolean isAwarded = awardPointsProxy(pointRewardData, transaction);

        // Check if awarded
        if ( !isAwarded ) {

            // Log information
            log.info("LoyaltyEngine -> doEventBasedProcessingForProgram -> Awarding failed");

            // Throw exception
            return false;

        }

        return isAwarded;
    }

    public double getPointsAwardedBasedOnCustomer(Customer customer,String reference,int role,  LoyaltyProgramSku loyaltyProgramSku) {

        double pointsAwarded = 0.0;

        if (reference.equals(loyaltyProgramSku.getLpuReference())
                && role == loyaltyProgramSku.getLpuRole()) {

            // Log the information
            log.info("LoyaltyEngine -> processRewardActivityProgram :  Awarding for " + loyaltyProgramSku);


            // Add the points
            pointsAwarded += loyaltyProgramSku.getLpuPrgRatioNum();
        }


        return pointsAwarded;
    }
    public double getPointsAwardedBasedOnCustomerRole(Customer customer,String reference,int role,  List<LoyaltyProgramSku> loyaltyProgramSkuList,PromotionalEvent promotionalEvent) {

        double pointsAwarded = 0.0;

        for (LoyaltyProgramSku loyaltyProgramSku : loyaltyProgramSkuList) {

            if (reference.equals(loyaltyProgramSku.getLpuReference())
                    && role == loyaltyProgramSku.getLpuRole() && loyaltyProgramSku.getLpuItemCode().equals(promotionalEvent.getPreEventCode())) {

                // Log the information
                log.info("LoyaltyEngine -> processRewardActivityProgram :  Awarding for " + loyaltyProgramSku);


                // Add the points
                pointsAwarded += loyaltyProgramSku.getLpuPrgRatioNum();
            }

        }

        return pointsAwarded;
    }


    /**
     * Method to return the key for the current process queue
     *
     * @param merchantNo    : The merchant number of the merchant
     * @param loyaltyId     : The loyalty id of the customer
     */
    protected String getCurrentProcessQueueKey(Long merchantNo,String loyaltyId) {

        return merchantNo+"#"+loyaltyId;

    }
}
