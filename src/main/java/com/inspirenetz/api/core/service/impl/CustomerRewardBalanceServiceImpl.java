package com.inspirenetz.api.core.service.impl;

import com.inspirenetz.api.core.dictionary.*;
import com.inspirenetz.api.core.domain.*;
import com.inspirenetz.api.core.repository.BaseRepository;
import com.inspirenetz.api.core.repository.CustomerRewardBalanceRepository;
import com.inspirenetz.api.core.service.*;
import com.inspirenetz.api.rest.exception.InspireNetzException;
import com.inspirenetz.api.util.AccountBundlingUtils;
import com.inspirenetz.api.util.AuthSessionUtils;
import com.inspirenetz.api.util.DBUtils;
import com.inspirenetz.api.util.GeneralUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

/**
 * Created by sandheepgr on 17/2/14.
 */
@Service
public class CustomerRewardBalanceServiceImpl extends BaseServiceImpl<CustomerRewardBalance> implements CustomerRewardBalanceService {

    private static Logger log = LoggerFactory.getLogger(CustomerRewardBalanceServiceImpl.class);

    @Autowired
    private CustomerRewardBalanceRepository customerRewardBalanceRepository;

    @Autowired
    private AccountBundlingUtils accountBundlingUtils;

    @Autowired
    private LinkedRewardBalanceService linkedRewardBalanceService;

    @Autowired
    private AccountBundlingSettingService accountBundlingSettingService;

    @Autowired
    private TransferPointService transferPointService;

    @Autowired
    CustomerService customerService ;

    @Autowired
    CustomerSubscriptionService customerSubscriptionService;

    @Autowired
    Environment environment;

    @Autowired
    ProductService productService;

    @Autowired
    RewardCurrencyService rewardCurrencyService;

    @Autowired
    MerchantService merchantService;

    @Autowired
    LoyaltyEngineService loyaltyEngineService;

    @Autowired
    private AuthSessionUtils authSessionUtils;

    @Autowired
    private UserMessagingService userMessagingService;

    @Autowired
    DrawChanceService drawChanceService;

    @Autowired
    private GeneralUtils generalUtils;

    @Autowired
    UserService userService;

    @Autowired
    private CustomerActivityService customerActivityService;

    public CustomerRewardBalanceServiceImpl() {

        super(CustomerRewardBalance.class);

    }


    @Override
    protected BaseRepository<CustomerRewardBalance,Long> getDao() {
        return customerRewardBalanceRepository;
    }


    @Override
    public CustomerRewardBalance findByCrbId(Long crbId) {

        // Get the CustomerRewardBalance
        CustomerRewardBalance customerRewardBalance = customerRewardBalanceRepository.findByCrbId(crbId);

        // Return the customererewardbalane
        return customerRewardBalance;
    }

    @Override
    public List<CustomerRewardBalance> findByCrbLoyaltyIdAndCrbMerchantNo(String crbLoyaltyId,Long crbMerchantNo) {

        // Get the list of balance
        List<CustomerRewardBalance> customerRewardBalanceList = customerRewardBalanceRepository.findByCrbLoyaltyIdAndCrbMerchantNo(crbLoyaltyId, crbMerchantNo);

        // Return the reward balance
        return customerRewardBalanceList;

    }

    @Override
    public CustomerRewardBalance findByCrbLoyaltyIdAndCrbMerchantNoAndCrbRewardCurrency(String crbLoyaltyId,Long crbMerchantNo,Long crbRewardCurrency) {

        // Get the balance for customer
        CustomerRewardBalance customerRewardBalanceList = customerRewardBalanceRepository.findByCrbLoyaltyIdAndCrbMerchantNoAndCrbRewardCurrency(crbLoyaltyId, crbMerchantNo, crbRewardCurrency);

        // Return the reward balance
        return customerRewardBalanceList;

    }

    @Override
    public CustomerRewardBalance buyPoints(String loyaltyId, Long merchantNo, Long crbRewardCurrency, Long crbNumPoints) throws InspireNetzException {

        //Get the customer details
        Customer customer = customerService.findByCusLoyaltyIdAndCusMerchantNo(loyaltyId,merchantNo);

        //check the customer is valid , if yes continue else throw error
        if(customer == null ){

            //log the error
            log.error("buyPoints : No Loyalty Id Information Found");

            //throw exception
            throw new InspireNetzException(APIErrorCode.ERR_NO_LOYALTY_ID);

        }

        //get the customer subscription details
        List<CustomerSubscription> customerSubscriptions = customerSubscriptionService.findByCsuCustomerNo(customer.getCusCustomerNo());

        if(customerSubscriptions ==null || customerSubscriptions.size() ==0){

            //log the error
            log.error("buyPoints : No Customer Subscripitons Found");

            //log the activity
            customerActivityService.logActivity(loyaltyId,CustomerActivityType.BUY_POINT,"Failed , No subscription found for customer",merchantNo,"");

            //throw the error
            throw new InspireNetzException(APIErrorCode.ERR_NO_DATA_FOUND);



        }

        //Assuming the customer's major subscription is the first one in the list
        CustomerSubscription customerSubscription = customerSubscriptions.get(0);

        //Getting the subscription name
        String prdCode = customerSubscription.getCsuProductCode();

        //Get the Product Information
        Product product = productService.findByPrdMerchantNoAndPrdCode(merchantNo, prdCode);

        //Get productCategory code for Prepaid
        String prepaidCode = environment.getProperty("sku.category.prepaid-category-code");

        //Get the RewardCurrency details
        RewardCurrency rewardCurrency = rewardCurrencyService.findByRwdCurrencyId(crbRewardCurrency);

        //calculate the amount to deduce and set it to amountToDeduce variable
        double amount = ( 1 * crbNumPoints )/ rewardCurrency.getRwdCashbackRatioDeno();

        boolean isPaymentSuccess = true;

        boolean isPointsAwarded = false;

        //check whether the product is prepaid or post paid
        if(product.getPrdCategory1() .equals(prepaidCode)){

            //call the api to deduce the amount from the account


        }

        //if the payment is success then award points to the customer
        if(isPaymentSuccess){

            //Get the merchant details
            Merchant merchant = merchantService.findByMerMerchantNo(merchantNo);

            //create the pointRewardData object
            PointRewardData pointRewardData = new PointRewardData();
            pointRewardData.setMerchantNo(merchantNo);
            pointRewardData.setMerchantName(merchant.getMerMerchantName());
            pointRewardData.setLoyaltyId(loyaltyId);
            pointRewardData.setUserNo(customer.getCusUserNo());
            pointRewardData.setUsrFName(customer.getCusFName());
            pointRewardData.setUsrLName(customer.getCusLName());
            pointRewardData.setRwdCurrencyId(crbRewardCurrency);
            pointRewardData.setRwdCurrencyName(rewardCurrency.getRwdCurrencyName());
            pointRewardData.setRewardQty(crbNumPoints);
            pointRewardData.setAddToAccumulatedBalance(false);
            pointRewardData.setTxnLocation(customer.getCusLocation());
            pointRewardData.setTxnDate(new java.sql.Date(new Date().getTime()));
            pointRewardData.setAuditDetails(authSessionUtils.getUserNo()+ " "+authSessionUtils.getUserLoginId());

            //create the transaction object
            Transaction transaction = new Transaction();
            transaction.setTxnType(TransactionType.BUY_POINTS);
            transaction.setTxnMerchantNo(merchantNo);
            transaction.setTxnLoyaltyId(loyaltyId);
            transaction.setTxnStatus(TransactionStatus.PROCESSED);
            transaction.setTxnDate(new java.sql.Date(new Date().getTime()));
            transaction.setTxnRewardCurrencyId(crbRewardCurrency);
            transaction.setTxnRewardQty(crbNumPoints);
            transaction.setTxnExternalRef("");
            transaction.setTxnInternalRef("");
            transaction.setTxnRewardExpDt(DBUtils.covertToSqlDate("9999-12-31"));

            //call the loyalty engine method to award points
            isPointsAwarded=loyaltyEngineService.awardPoints(pointRewardData,transaction);

        }
        if(isPointsAwarded){

            //get the current reward balance of the customer
            CustomerRewardBalance customerRewardBalance = findByCrbLoyaltyIdAndCrbMerchantNoAndCrbRewardCurrency(loyaltyId,merchantNo,crbRewardCurrency);

            //log the activity
            customerActivityService.logActivity(loyaltyId,CustomerActivityType.BUY_POINT,"Success",merchantNo,"");

            //return the reward balance object
            return customerRewardBalance;
        }

        //log the activity
        customerActivityService.logActivity(loyaltyId,CustomerActivityType.BUY_POINT,"Points awarding failed",merchantNo,"");

        return null;
    }

    @Override
    public CustomerRewardBalance awardPointsForRewardAdjustment(RewardAdjustment rewardAdjustment) throws InspireNetzException {

        //get the customer data
        Customer customer = customerService.findByCusLoyaltyIdAndCusMerchantNo(rewardAdjustment.getLoyaltyId(),rewardAdjustment.getMerchantNo());



        //check whether the customer exists or not
        if(customer == null ){

            //log the error
            log.error("awardPointsForRewardAdjustment : Customer Doesn't exist");

            //throw the error
            throw new InspireNetzException(APIErrorCode.ERR_NO_DATA_FOUND);

        }

        //get the reward currency details
        RewardCurrency rewardCurrency = rewardCurrencyService.findByRwdCurrencyId(rewardAdjustment.getRwdCurrencyId());

        //check reward currency data
        if(rewardCurrency == null ){

            //log error
            log.error("awardPointsForRewardAdjustment : No Reward Currency Information found");

            //throw error
            throw new InspireNetzException(APIErrorCode.ERR_NO_DATA_FOUND);
        }

        //get the reward currency list
        List<CustomerRewardExpiry > customerRewardExpiries = loyaltyEngineService.getCustomerRewardExpiryList(customer,rewardAdjustment.getRwdCurrencyId());

        //reward expiry date
        Date expiryDate = new Date();

        //if no reward expiry data is found , get the expiry date
        if(customerRewardExpiries == null || customerRewardExpiries.size() == 0){

            //get the reward expiry date
            expiryDate = rewardCurrencyService.getRewardExpiryDate(rewardCurrency,new Date());

        } else {

            //get the expiry date of the existing reward expiry
            expiryDate = customerRewardExpiries.get(0).getCreExpiryDt();
        }

        //get the java.sql format date
        java.sql.Date expiryDateSql = new java.sql.Date(expiryDate.getTime());

        //Get the merchant details
        Merchant merchant = merchantService.findByMerMerchantNo(rewardAdjustment.getMerchantNo());

        //create the pointRewardData object
        PointRewardData pointRewardData = new PointRewardData();
        pointRewardData.setMerchantNo(rewardAdjustment.getMerchantNo());
        pointRewardData.setMerchantName(merchant.getMerMerchantName());
        pointRewardData.setLoyaltyId(rewardAdjustment.getLoyaltyId());
        pointRewardData.setUserNo(customer.getCusUserNo());
        pointRewardData.setUsrFName(customer.getCusFName());
        pointRewardData.setUsrLName(customer.getCusLName());
        pointRewardData.setRwdCurrencyId(rewardAdjustment.getRwdCurrencyId());
        pointRewardData.setRwdCurrencyName(rewardCurrency.getRwdCurrencyName());
        pointRewardData.setProgramId(rewardAdjustment.getProgramNo());
        pointRewardData.setRewardQty(rewardAdjustment.getRwdQty());
        pointRewardData.setExpiryDt(expiryDateSql);

        //check if the rewarding is for point reversal , if set values for reversal reference
        if(rewardAdjustment.isPointReversed()){

            pointRewardData.setFailedRedemption(true);
            pointRewardData.setTrackingId(rewardAdjustment.getExternalReference());

        }

        //check whether the tierData need to be affected
        if(rewardAdjustment.isTierAffected()){

            pointRewardData.setAddToAccumulatedBalance(true);

        } else {

            pointRewardData.setAddToAccumulatedBalance(false);

        }

        pointRewardData.setTxnLocation(customer.getCusLocation());
        pointRewardData.setTxnDate(new java.sql.Date(new Date().getTime()));
        pointRewardData.setAuditDetails(authSessionUtils.getUserNo()+ " "+authSessionUtils.getUserLoginId());

        //create the transaction object
        Transaction transaction = new Transaction();
        transaction.setTxnType(TransactionType.REWARD_ADUJUSTMENT_AWARDING);
        transaction.setTxnMerchantNo(rewardAdjustment.getMerchantNo());
        transaction.setTxnLoyaltyId(rewardAdjustment.getLoyaltyId());

        //if reward adjustment is made for failed redemptions , then set transaction status to
        //point reversal
        if(rewardAdjustment.isPointReversed()){

            transaction.setTxnStatus(TransactionStatus.POINTS_REVERSED);

        } else {

            transaction.setTxnStatus(TransactionStatus.PROCESSED);

        }

        transaction.setTxnDate(new java.sql.Date(new Date().getTime()));
        transaction.setTxnRewardCurrencyId(rewardAdjustment.getRwdCurrencyId());
        transaction.setTxnRewardQty(rewardAdjustment.getRwdQty());
        transaction.setTxnExternalRef(rewardAdjustment.getExternalReference());
        transaction.setTxnInternalRef(rewardAdjustment.getIntenalReference());
        transaction.setTxnRewardExpDt(DBUtils.covertToSqlDate("9999-12-31"));

        //call the loyalty engine method to award points
        boolean isPointsAwarded=loyaltyEngineService.awardPoints(pointRewardData,transaction);

        //if the transaction is success then get the current reward balance
        if(isPointsAwarded){

            //get the current reward balance of the customer
            CustomerRewardBalance customerRewardBalance = findByCrbLoyaltyIdAndCrbMerchantNoAndCrbRewardCurrency(rewardAdjustment.getLoyaltyId(),rewardAdjustment.getMerchantNo(),rewardAdjustment.getRwdCurrencyId());

            //return the reward balance object
            return customerRewardBalance;

        }

        return null;

    }

    @Override
    public CustomerRewardBalance deductPointsForRewardAdjustment(Long merchantNo, String loyaltyId, Long rwdCurrencyId, Double redeemQty,String reference) throws InspireNetzException {


        //get the customer data
        Customer customer = customerService.findByCusLoyaltyIdAndCusMerchantNo(loyaltyId,merchantNo);

        //check whether the customer exists or not
        if(customer == null ){

            //log the error
            log.error("deductPointsForRewardAdjustment : Customer Doesn't exist");

            //throw the error
            throw new InspireNetzException(APIErrorCode.ERR_NO_DATA_FOUND);

        }

        //get the reward currency details
        RewardCurrency rewardCurrency = rewardCurrencyService.findByRwdCurrencyId(rwdCurrencyId);

        //check reward currency data
        if(rewardCurrency == null ){

            //log error
            log.error("deductPointsForRewardAdjustment  : No Reward Currency Information found");

            //throw error
            throw new InspireNetzException(APIErrorCode.ERR_NO_DATA_FOUND);
        }
        //Get the merchant details
        Merchant merchant = merchantService.findByMerMerchantNo(merchantNo);

        //create the pointRewardData object
        PointDeductData pointDeductData = new PointDeductData();
        pointDeductData.setMerchantNo(merchantNo);
        pointDeductData.setMerchantName(merchant.getMerMerchantName());
        pointDeductData.setLoyaltyId(loyaltyId);
        pointDeductData.setUserNo(customer.getCusUserNo());
        pointDeductData.setUsrFName(customer.getCusFName());
        pointDeductData.setUsrLName(customer.getCusLName());
        pointDeductData.setRwdCurrencyId(rwdCurrencyId);
        pointDeductData.setRwdCurrencyName(rewardCurrency.getRwdCurrencyName());
        pointDeductData.setRedeemQty(redeemQty);
        pointDeductData.setTxnLocation(customer.getCusLocation());
        pointDeductData.setTxnDate(new java.sql.Date(new Date().getTime()));
        pointDeductData.setExternalRef(reference);
        pointDeductData.setAuditDetails(authSessionUtils.getUserNo() + " " + authSessionUtils.getUserLoginId());
        pointDeductData.setTxnType(TransactionType.REWARD_ADJUSTMENT_DEDUCTING);
       /* //create the transaction object
        Transaction transaction = new Transaction();
        transaction.setTxnType(TransactionType.REWARD_ADJUSTMENT_DEDUCTING);
        transaction.setTxnMerchantNo(merchantNo);
        transaction.setTxnLoyaltyId(loyaltyId);
        transaction.setTxnStatus(TransactionStatus.PROCESSED);
        transaction.setTxnDate(new java.sql.Date(new Date().getTime()));
        transaction.setTxnRewardCurrencyId(rwdCurrencyId);
        transaction.setTxnRewardQty(redeemQty);
        transaction.setTxnExternalRef("");
        transaction.setTxnInternalRef("");
        transaction.setTxnRewardExpDt(DBUtils.covertToSqlDate("9999-12-31"));
*/
        //call the loyalty engine method to award points
        boolean isPointsAwarded=loyaltyEngineService.deductPoints(pointDeductData);

        //if the transaction is success then get the current reward balance
        if(isPointsAwarded){

            //get the current reward balance of the customer
            CustomerRewardBalance customerRewardBalance = findByCrbLoyaltyIdAndCrbMerchantNoAndCrbRewardCurrency(loyaltyId,merchantNo,rwdCurrencyId);

            //return the reward balance object
            return customerRewardBalance;

        }

        return null;


    }

    @Override
    public CustomerRewardBalance doRewardAdjustment(Long merchantNo, String loyaltyId, Integer adjType, Long adjCurrencyId, Double adjPoints, boolean isTierAffected, Long adjProgramNo,String adjReference) throws InspireNetzException {

        CustomerRewardBalance customerRewardBalance = null;

        //check the type of the adjustment and call the method corresponding to it
        if(adjType == TransactionType.REWARD_ADUJUSTMENT_AWARDING){

            //create reward adjustment object
            RewardAdjustment rewardAdjustment = createRewardAdjustmentObject(merchantNo,loyaltyId,adjCurrencyId,adjPoints,isTierAffected,adjProgramNo,adjReference);

            //call the method for reward adjustment
            customerRewardBalance = awardPointsForRewardAdjustment(rewardAdjustment);

        } else if(adjType == TransactionType.REWARD_ADJUSTMENT_DEDUCTING){

            //call the method for reward adjustment
            customerRewardBalance = deductPointsForRewardAdjustment(merchantNo, loyaltyId, adjCurrencyId, adjPoints,adjReference);

        } else {

            //log error
            log.error("doRewardAdjustment : Invalid RewardAdjustment Type");

            throw new InspireNetzException(APIErrorCode.ERR_INVALID_INPUT);

        }

        return customerRewardBalance;

    }

    private RewardAdjustment createRewardAdjustmentObject(Long merchantNo, String loyaltyId, Long adjCurrencyId, Double adjPoints, boolean isTierAffected, Long adjProgramNo, String adjReference) {

        RewardAdjustment rewardAdjustment = new RewardAdjustment();

        //set values to the object
        rewardAdjustment.setLoyaltyId(loyaltyId);
        rewardAdjustment.setRwdCurrencyId(adjCurrencyId);
        rewardAdjustment.setRwdQty(adjPoints);
        rewardAdjustment.setMerchantNo(merchantNo);
        rewardAdjustment.setIntenalReference("");
        rewardAdjustment.setExternalReference(adjReference);
        rewardAdjustment.setProgramNo(adjProgramNo);
        rewardAdjustment.setTierAffected(isTierAffected);

        //return the reward adjustment object
        return rewardAdjustment;
    }


    @Override
    public List<CustomerRewardBalance> getBalanceList(Long merchantNo,String loyaltyId) {

        // Log the the request
        log.info("getBalanceList -> Request : Merchant No :" + merchantNo + " loyalty Id : " +loyaltyId);

        // List of CustomerRewardBalance to return
        List<CustomerRewardBalance> customerRewardBalanceList = new ArrayList<>(0);

        // Get the primary for customergetBalanceList
        Customer primary = accountBundlingUtils.getPrimaryCustomerForCustomer(merchantNo,loyaltyId);

        // If the primary is null, then the customer is a stand alone account
        // hence return the list balance
        if ( primary == null)  {

            // Set the list
            customerRewardBalanceList =  findByCrbLoyaltyIdAndCrbMerchantNo(loyaltyId,merchantNo);

            // Go through the list and get the lazy data
            for (CustomerRewardBalance customerRewardBalance : customerRewardBalanceList ) {

                customerRewardBalance.getRewardCurrency().getRwdCurrencyId();

            }

            // Return the list
            return customerRewardBalanceList;

        }



        // If the primary is not null, check if the customer himself is primary
        if ( primary.getCusLoyaltyId().equals(loyaltyId) ) {

            // Get the LinkedRewardBalance list
            List<LinkedRewardBalance> linkedRewardBalanceList = linkedRewardBalanceService.findByLrbPrimaryLoyaltyIdAndLrbMerchantNo(loyaltyId,merchantNo);

            // Convert to CustomerRewardBalance
            customerRewardBalanceList = createFromLinkedRewardBalanceList(linkedRewardBalanceList);


        } else {


            // Read the AccountSetting
            AccountBundlingSetting accountBundlingSetting = accountBundlingSettingService.getDefaultAccountBundlingSetting(merchantNo);

            // Get the LinkedRewardBalance list
            List<LinkedRewardBalance> linkedRewardBalanceList = linkedRewardBalanceService.findByLrbPrimaryLoyaltyIdAndLrbMerchantNo(primary.getCusLoyaltyId(),merchantNo);

            // Convert to CustomerRewardBalance
            customerRewardBalanceList = createFromLinkedRewardBalanceList(linkedRewardBalanceList);

        }


        // Return the list
        return customerRewardBalanceList;

    }

    @Override
    public List<CustomerRewardBalance> getBalance(Long merchantNo,String loyaltyId,Long rwdId) {

        // Log the the request
        log.info("getBalance -> Request : Merchant No :" + merchantNo + " loyalty Id : " +loyaltyId);

        // List of CustomerRewardBalance to return
        List<CustomerRewardBalance> customerRewardBalanceList = new ArrayList<>(0);

        // Get the primary for customer
        Customer primary = accountBundlingUtils.getPrimaryCustomerForCustomer(merchantNo,loyaltyId);

        // If the primary is null, then the customer is a stand alone account
        // hence return the list balance
        if ( primary == null)  {

            // Get the CustomerRewardBalance
            CustomerRewardBalance customerRewardBalance  = findByCrbLoyaltyIdAndCrbMerchantNoAndCrbRewardCurrency(loyaltyId,merchantNo,rwdId);

            // If the data is there, add it to the list
            if ( customerRewardBalance != null ) {

                // Call the data
                customerRewardBalance.getRewardCurrency().getRwdCurrencyId();

                // add to the list
                customerRewardBalanceList.add(customerRewardBalance);

            }

            // Return the customerREwardBalanceList
            return customerRewardBalanceList;

        }



        // If the primary is not null, check if the customer himself is primary
        if ( primary.getCusLoyaltyId().equals(loyaltyId) ) {

            // Get the LinkedRewardBalance list
            List<LinkedRewardBalance> linkedRewardBalanceList = new ArrayList<>(0);

            // Get the LinkedRewardBalance
            LinkedRewardBalance linkedRewardBalance = linkedRewardBalanceService.findByLrbPrimaryLoyaltyIdAndLrbMerchantNoAndLrbRewardCurrency(loyaltyId,merchantNo,rwdId);

            // If the linkedRewardBalance exists, then add to the list
            if ( linkedRewardBalance != null ) {

                linkedRewardBalanceList.add(linkedRewardBalance);

            }

            // Convert to CustomerRewardBalance
            customerRewardBalanceList = createFromLinkedRewardBalanceList(linkedRewardBalanceList);


        } else {


          /*  // Read the AccountSetting
            AccountBundlingSetting accountBundlingSetting = accountBundlingSettingService.getDefaultAccountBundlingSetting(merchantNo);


           *//* // We need to check if the accountSetting is transfer, then return no balance
            // else return the balance for primary
            if ( accountBundlingSetting.getAbsLinkingType() == AccountBundlingLinkingType.CONSOLIDATE_TO_PRIMARY ) {
*/
                // Get the LinkedRewardBalance list
                List<LinkedRewardBalance> linkedRewardBalanceList = new ArrayList<>(0);

                // Get the LinkedRewardBalance
                LinkedRewardBalance linkedRewardBalance = linkedRewardBalanceService.findByLrbPrimaryLoyaltyIdAndLrbMerchantNoAndLrbRewardCurrency(primary.getCusLoyaltyId(),merchantNo,rwdId);

                // If the linkedRewardBalance exists, then add to the list
                if ( linkedRewardBalance != null ) {

                    linkedRewardBalanceList.add(linkedRewardBalance);

                }

                // Convert to CustomerRewardBalance
                customerRewardBalanceList = createFromLinkedRewardBalanceList(linkedRewardBalanceList);


        //    }

        }



        // Return the customerRewardBalanceList
        return customerRewardBalanceList;

    }

    @Override
    public List<CustomerRewardBalance> searchBalances(Long merchantNo,String loyaltyId,Long rwdId) {

        // Log the Request
        log.info("searchBalances - Request Received# loyalty_id = " +loyaltyId + " : rwd_currency_id = " +rwdId );
        log.info("searchBalances - Requested User  # Login Id : "+ authSessionUtils.getUserLoginId() + " - User No: "+authSessionUtils.getUserNo() + " - IP Address : " + authSessionUtils.getUserIpAddress());


        // If the userType is merchant, then set the merchant number from the session
        if ( authSessionUtils.getUserType() == UserType.MERCHANT_USER ||authSessionUtils.getUserType() ==UserType.MERCHANT_ADMIN) {

            merchantNo = authSessionUtils.getMerchantNo();

        }

        // The list to return
        List<CustomerRewardBalance> customerRewardBalanceList;

        // If the rwdId is null, then return result of getBalanceList
        if ( rwdId == null || rwdId == 0L ) {

            customerRewardBalanceList = getBalanceList(merchantNo,loyaltyId);

        } else {

            customerRewardBalanceList = getBalance(merchantNo,loyaltyId,rwdId);

        }



        // Finally return the list
        return customerRewardBalanceList;

    }


    @Override
    public void sendRewardBalanceSMS(Long merchantNo, String loyaltyId,Long rwdId ) throws InspireNetzException {

        //get the customer details
        Customer customer = customerService.findByCusLoyaltyIdAndCusMerchantNo(loyaltyId,merchantNo);

        MessageWrapper messageWrapper = new MessageWrapper();

        messageWrapper.setMerchantNo(generalUtils.getDefaultMerchantNo());

        messageWrapper.setLoyaltyId(loyaltyId);

        messageWrapper.setMobile(loyaltyId);

        messageWrapper.setParams(new HashMap<String, String>(0));

        //if customer is not found or is not active send notification to the customer
        if(customer == null || customer.getCusStatus() != CustomerStatus.ACTIVE){

            messageWrapper.setSpielName(MessageSpielValue.CUSTOMER_NOT_REGISTERED);

            userMessagingService.transmitNotification(messageWrapper);
            //return the control
            return;
        }

        //log the activity
        customerActivityService.logActivity(loyaltyId,CustomerActivityType.POINT_ENQUIRY,"Reward Balance Inquiry ",merchantNo,"");

        // The list to return
        List<CustomerRewardBalance> customerRewardBalanceList = searchBalances(merchantNo,loyaltyId,rwdId);

        // Create the map with fields
        HashMap<String,String> params = new HashMap<>(0);

        // Set the data
        params.put("#date",generalUtils.convertDateToFormat(new Date(), "dd MMM, yyyy"));

        // Check if the reward balance list available
        if ( customerRewardBalanceList == null || customerRewardBalanceList.isEmpty() ) {

            // Set the balance in the spiel formatted
            params.put("#points",generalUtils.getFormattedValue(0.0));


        } else {

            // Get the first reward balance
            CustomerRewardBalance customerRewardBalance = customerRewardBalanceList.get(0);

            // Set the balance in the spiel formatted
            params.put("#points",generalUtils.getFormattedValue(customerRewardBalance.getCrbRewardBalance()));

        }

        //for check draw chances for raffle ticket
        DrawChance drawChance =drawChanceService.getDrawChancesByLoyaltyId(loyaltyId,DrawType.RAFFLE_TICKET);

        //check if its null or not
        if(drawChance ==null){

            params.put("#raffle","0");

        } else {

            Double noOfDrawChance=drawChance.getDrcChances()==null?0.0:drawChance.getDrcChances();

            // Set the balance in the spiel formatted
            params.put("#raffle",generalUtils.getFormattedValue(noOfDrawChance));


        }

        messageWrapper.setSpielName(MessageSpielValue.POINT_BALANCE_SMS);

        messageWrapper.setMerchantNo(customer.getCusMerchantNo());

        messageWrapper.setParams(params);

        messageWrapper.setMobile(customer.getCusMobile());

        userMessagingService.transmitNotification(messageWrapper);

    }


    /**
     * Function to create the CustomerRewardBalance List based on the LinkedRewardBalanceList
     *
     * @param linkedRewardBalanceList   - The list of the LinkedRewardBalance list
     *
     * @return                          - Return null if passed param is null
     *                                    Return the CustomerRewardBalance List  from the LinkedRewardBalanceList
     */
    protected List<CustomerRewardBalance> createFromLinkedRewardBalanceList(List<LinkedRewardBalance> linkedRewardBalanceList) {

        // If the list is null, return empty
        if ( linkedRewardBalanceList == null ) {

            return new ArrayList<CustomerRewardBalance>(0);

        }


        // Create the CustomerRewardBalanceList
        List<CustomerRewardBalance> customerRewardBalanceList =  new ArrayList<>(0);

        // Iterate through the LinkedRewardBalance list
        for (LinkedRewardBalance linkedRewardBalance : linkedRewardBalanceList ) {


            // Get the LInkedREwardBaalnce
            linkedRewardBalance.getRewardCurrency().getRwdCurrencyId();

            // Create the CustomerRewardBalance object
            CustomerRewardBalance customerRewardBalance = new CustomerRewardBalance();


            // Set the fields
            customerRewardBalance.setCrbLoyaltyId(linkedRewardBalance.getLrbPrimaryLoyaltyId());

            customerRewardBalance.setCrbMerchantNo(linkedRewardBalance.getLrbMerchantNo());

            customerRewardBalance.setCrbRewardCurrency(linkedRewardBalance.getLrbRewardCurrency());

            customerRewardBalance.setCrbRewardBalance(linkedRewardBalance.getLrbRewardBalance());

            customerRewardBalance.setRewardCurrency(linkedRewardBalance.getRewardCurrency());



            // Add to the list
            customerRewardBalanceList.add(customerRewardBalance);

        }



        // Return the object
        return customerRewardBalanceList;
    }

    @Override
    public CustomerRewardBalance saveCustomerRewardBalance(CustomerRewardBalance customerRewardBalance) {

        // Save the CustomerRewardBalance object
        customerRewardBalance = customerRewardBalanceRepository.save(customerRewardBalance);

        // Return the object
        return customerRewardBalance;

    }

    @Override
    public boolean deleteCustomerRewardBalance(CustomerRewardBalance customerRewardBalance) {

        // Delete the CustomerRewardBalance object
        customerRewardBalanceRepository.delete(customerRewardBalance);;

        // Return true
        return true;

    }


    /**
     * Function to call the transferPoints function for the merchant user
     * Here the merchant number is set to the TransferPointRequest object
     * from the session
     *
     * @param srcLoyaltyId      - Source loyalty id for the customer
     * @param destLoyaltyId     - Destination loyalty id for the customer
     * @param srcCurrencyId     - Source currency id
     * @param destCurrencyId    - Destination currency id
     * @param rwdQty            - reward quantity to be transferred
     *
     * @return                  - Return true if successful
     *
     * @throws InspireNetzException
     */
    @Override
    public boolean transferPointsForMerchantUser(String srcLoyaltyId, String destLoyaltyId,Long srcCurrencyId,Long destCurrencyId,Double rwdQty) throws InspireNetzException {


        // Get the merchantNo
        Long merchantNo = authSessionUtils.getMerchantNo();


        // Create the TransferPointRequest object
        TransferPointRequest transferPointRequest = new TransferPointRequest();

        transferPointRequest.setFromLoyaltyId(srcLoyaltyId);

        transferPointRequest.setToLoyaltyId(destLoyaltyId);

        transferPointRequest.setRewardQty(rwdQty);

        transferPointRequest.setFromRewardCurrency(srcCurrencyId);

        transferPointRequest.setToRewardCurrency(destCurrencyId);




        // Set them transferPointRequest merchantno to be the merchantno from session
        transferPointRequest.setMerchantNo(merchantNo);;

        // Call the transferPointRequest
        return transferPointService.transferPoints(transferPointRequest);

    }



    /**
     * Function to call the transferPoints function for the customer logged
     * Here the src loyalty is  is set to the TransferPointRequest object
     * from the session
     *
     * @param merchantNo        - Merchant number of the merchant
     * @param destLoyaltyId     - Destination loyalty id for the customer
     * @param srcCurrencyId     - Source currency id
     * @param destCurrencyId    - Destination currency id
     * @param rwdQty            - reward quantity to be transferred
     *
     * @return                  - Return true if successful
     *
     * @throws InspireNetzException
     */
    @Override
    public boolean transferPointsForCustomerUser(Long merchantNo,String destLoyaltyId, Long srcCurrencyId, Long destCurrencyId, Double rwdQty) throws InspireNetzException {

        // Get the sourceLoyaltyId as the user login id
        String sourceLoyaltyId = authSessionUtils.getUserLoginId();


        // Create the TransferPointRequest object
        TransferPointRequest transferPointRequest = new TransferPointRequest();

        transferPointRequest.setMerchantNo(merchantNo);

        transferPointRequest.setToLoyaltyId(destLoyaltyId);

        transferPointRequest.setRewardQty(rwdQty);

        transferPointRequest.setFromRewardCurrency(srcCurrencyId);

        transferPointRequest.setToRewardCurrency(destCurrencyId);



        // Set the sourceLoyaltyId
        transferPointRequest.setFromLoyaltyId(sourceLoyaltyId);

        // Call the transferPointRequest
        return transferPointService.transferPoints(transferPointRequest);

    }

    @Override
    public List<CustomerRewardBalance> GetUserRewardBalances(Long merchantNo,Long rwdCurrencyId) throws InspireNetzException{

        //Array list for customer reward balance
        List<CustomerRewardBalance> customerRewardBalances=new ArrayList<>();

        //Array list for customer reward balance
        List<CustomerRewardBalance> customerRewardBalanceList=new ArrayList<>();

        //get current user login id
        String usrLoginId=authSessionUtils.getUserLoginId();

        //get user object
        User user=userService.findByUsrLoginId(usrLoginId);


        if(user==null||user.getUsrUserNo()==null){

            //log the error
            log.error("No User Information Found");

            //throw exception
            return customerRewardBalances;
        }

        //Customer List
        List<Customer> customers=customerService.getUserMemberships(merchantNo,user.getUsrUserNo(),CustomerStatus.ACTIVE);

        if(customers==null ||customers.isEmpty()){

            //log the error
            log.error("No Customer Information Found");

            return customerRewardBalances;

        }

        for(Customer customer: customers){

            // If the rwdCurrencyId is null, then return result of getBalanceList
            if ( rwdCurrencyId == null || rwdCurrencyId == 0L ) {

                customerRewardBalanceList = getBalanceList(customer.getCusMerchantNo(),customer.getCusLoyaltyId());

            } else {

                customerRewardBalanceList = getBalance(customer.getCusMerchantNo(),customer.getCusLoyaltyId(),rwdCurrencyId);

            }

            customerRewardBalances.addAll(customerRewardBalanceList);
        }

        return customerRewardBalances;
    }

    @Override
    public List<CustomerRewardBalance> getUserRewardBalancesForUser(Long merchantNo) throws InspireNetzException {

        //get the reward balance for user
        Customer customer =customerService.findByCusUserNoAndCusMerchantNoAndCusStatus(authSessionUtils.getUserNo(),merchantNo,CustomerStatus.ACTIVE);

        if(customer !=null){

            //find reward balannce for given user
            List<CustomerRewardBalance> customerRewardBalances =findByCrbLoyaltyIdAndCrbMerchantNo(customer.getCusLoyaltyId(),customer.getCusMerchantNo());

            //return reward balance
            return customerRewardBalances;
        }

        return null;
    }


}
