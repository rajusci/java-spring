package com.inspirenetz.api.util;

import com.inspirenetz.api.core.dictionary.*;
import com.inspirenetz.api.core.domain.*;
import com.inspirenetz.api.core.service.RewardCurrencyService;
import com.inspirenetz.api.core.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Component;

import java.sql.Date;
import java.util.Calendar;
import java.util.StringTokenizer;

/**
 * Created by sandheepgr on 23/5/14.
 */
@Component
public class LoyaltyEngineUtils {


    @Autowired
    private UserService userService;

    @Autowired
    private RewardCurrencyService rewardCurrencyService;

    @Autowired
    private GeneralUtils generalUtils;


    /**
     * Function to check if the special occasion filtering set for the loyalty program is
     * valid for the given program and the spec occasion
     *
     * @param loyaltyProgram    - The LoyaltyProgram object
     * @param customerProfile   - The customer profile object of the customer
     * @param purchaseDate      - The date of purchase
     * @return                  - true if the date of purchase is matching with the
     *                            special occasion filtering
     *                            false otherwise
     *
     */
    public boolean isSpecialOccasionAwardingValid(LoyaltyProgram loyaltyProgram,CustomerProfile customerProfile,Date purchaseDate) {

        // Create the StringTokenizer
        StringTokenizer tokenizer = new StringTokenizer(loyaltyProgram.getPrgSpecOcc(),"#");

        // Check eack token and then check the validation
        while(tokenizer.hasMoreTokens()) {

            // Get the token
            String specOcc = tokenizer.nextToken();


            // Check the on  birthday condition
            if ( specOcc.equals(Integer.toString(LoyaltyProgramSpecialOccasionInd.ON_BIRTHDAY)) ) {

                // If the birthday field is not specified, then return false
                if ( customerProfile.getCspCustomerBirthday() == null || customerProfile.getCspCustomerBirthday().toString().equals("9999-12-31")) {

                    continue;

                }


                // Check the date
                if ( customerProfile.getCspCustomerBirthday().compareTo(purchaseDate) == 0 ) {

                    return true;

                }


            } else if ( specOcc.equals(Integer.toString(LoyaltyProgramSpecialOccasionInd.ON_BIRTHDAY_MONTH)) ) {

                // If the birthday field is not specified, then return false
                if ( customerProfile.getCspCustomerBirthday() == null || customerProfile.getCspCustomerBirthday().toString().equals("9999-12-31")) {

                    continue;

                }


                // Get the calendar
                Calendar calendar = Calendar.getInstance();

                // Set the time as customerProfile date
                calendar.setTime(customerProfile.getCspCustomerBirthday());

                // Get the cspMonth
                int cspMonth = calendar.get(Calendar.MONTH);


                // set the date as purchase date
                calendar.setTime(purchaseDate);

                // Get the prcMonth
                int prcMonth = calendar.get(Calendar.MONTH);


                // Check if both are equal
                if ( cspMonth == prcMonth ) {

                    return true;

                }

            } else if ( specOcc.equals(Integer.toString(LoyaltyProgramSpecialOccasionInd.ON_ANNIVERSARY)) ) {

                // If the anniversary field is not specified, then return false
                if ( customerProfile.getCspCustomerAnniversary() == null || customerProfile.getCspCustomerAnniversary().toString().equals("9999-12-31")) {

                    continue;

                }


                // Check the date
                if ( customerProfile.getCspCustomerAnniversary().compareTo(purchaseDate) == 0 ) {

                    return true;

                }


            } else if ( specOcc.equals(Integer.toString(LoyaltyProgramSpecialOccasionInd.ON_ANNIVERSARY_MONTH)) ) {

                // If the anniversary field is not specified, then return false
                if ( customerProfile.getCspCustomerAnniversary() == null || customerProfile.getCspCustomerAnniversary().toString().equals("9999-12-31")) {

                    continue;

                }


                // Get the calendar
                Calendar calendar = Calendar.getInstance();

                // Set the time as customerProfile date
                calendar.setTime(customerProfile.getCspCustomerAnniversary());

                // Get the cspMonth
                int cspMonth = calendar.get(Calendar.MONTH);


                // set the date as purchase date
                calendar.setTime(purchaseDate);

                // Get the prcMonth
                int prcMonth = calendar.get(Calendar.MONTH);


                // Check if both are equal
                if ( cspMonth == prcMonth ) {

                    return true;

                }

            }

        }

        // Finally return false
        return false;
    }


    /**
     * Function to get the Transaction object for the given loyalty program
     *
     * @param loyaltyProgram    - The LoyaltyProgram object
     * @param sale          - The Purchase object for which the transaction is being added
     * @param pointRewardData   - The PointRewardData object
     * @param preBalance        - The balance before entering the transaction
     *
     *
     * @return                  - Return transaction object
     */
    public  Transaction getTransactionForProgram(LoyaltyProgram loyaltyProgram,Sale sale,PointRewardData pointRewardData,double preBalance) {

        // Create the Transaction object
        Transaction transaction = new Transaction();

        //get sale type
        Integer saleType = sale.getSalType() ==null?0:sale.getSalType();

        // setting transaction type based on sale type
        if(saleType.intValue() == SaleType.CHARGE_CARD_DEBIT){

            transaction.setTxnType(TransactionType.CHARGE_CARD_DEBIT);

        }else if(saleType.intValue() ==SaleType.CHARGE_CARD_TOPUP){

            transaction.setTxnType(TransactionType.CHARGE_CARD_TOPUP);

        }else {

            transaction.setTxnType(TransactionType.PURCHASE);
        }



        transaction.setTxnMerchantNo(sale.getSalMerchantNo());

        transaction.setTxnLoyaltyId(sale.getSalLoyaltyId());

        transaction.setTxnStatus(TransactionStatus.PROCESSED);

        transaction.setTxnDate(sale.getSalDate());

        transaction.setTxnLocation(sale.getSalLocation());

        transaction.setTxnInternalRef(sale.getSalId().toString());

        transaction.setTxnExternalRef(sale.getSalPaymentReference());

        transaction.setTxnRewardCurrencyId(loyaltyProgram.getPrgCurrencyId());

        transaction.setTxnCrDbInd(CreditDebitInd.CREDIT);

        transaction.setTxnAmount(sale.getSalAmount());

        transaction.setTxnProgramId(loyaltyProgram.getPrgProgramNo());

        transaction.setTxnRewardQty(pointRewardData.getTotalRewardQty());

        transaction.setTxnRewardPreBal(preBalance);

        transaction.setTxnRewardPostBal(preBalance + pointRewardData.getRewardQty());

        transaction.setTxnRewardExpDt(pointRewardData.getExpiryDt());


        // Set the audit information
        transaction.setCreatedBy(sale.getCreatedBy());

        // Return the transaction
        return transaction;

    }

    /**
     *
     * Function to get the Transaction object for the given PointRewardData object
     *
     * @param pointRewardData       - The PointRewardData object based on with the object will be created
     * @param preBalance            - The preBalance field
     * @param ref                   - The reference data
     *
     * @return                      - Return the Transaction object
     */
    public Transaction getTransactionForPointRewardData(PointRewardData pointRewardData,Double preBalance,String ref) {

        // Create the Transaction object
        Transaction transaction = new Transaction();


        // Set the fields
        transaction.setTxnType(pointRewardData.getTxnType());

        transaction.setTxnMerchantNo(pointRewardData.getMerchantNo());

        transaction.setTxnLoyaltyId(pointRewardData.getLoyaltyId());

        transaction.setTxnStatus(TransactionStatus.PROCESSED);

        transaction.setTxnDate(pointRewardData.getTxnDate());

        transaction.setTxnLocation(pointRewardData.getTxnLocation());

        transaction.setTxnInternalRef("0");

        transaction.setTxnExternalRef(ref);

        transaction.setTxnRewardCurrencyId(pointRewardData.getRwdCurrencyId());

        transaction.setTxnRewardQty(pointRewardData.getRewardQty());

        transaction.setTxnCrDbInd(CreditDebitInd.CREDIT);

        transaction.setTxnAmount(pointRewardData.getTxnAmount());

        transaction.setTxnProgramId(pointRewardData.getProgramId());

        transaction.setTxnRewardPreBal(preBalance);

        transaction.setTxnRewardPostBal(preBalance + pointRewardData.getRewardQty());

        transaction.setTxnRewardExpDt(pointRewardData.getExpiryDt());



        // Set the audit information
        transaction.setCreatedBy(pointRewardData.getAuditDetails());

        // Return the transaction
        return transaction;
    }


    /**
     * Function to get the Transaction object for an Expiry transaction
     * Here we pass the CustomerRewardExpiry object and the function returns
     * the Transaction object
     *
     * @param customerRewardExpiry   - The CustomerRewardExpiry object that is being expired
     *
     * @return                       - The transaction object
     */
    public Transaction getTransactionForExpiry(CustomerRewardExpiry customerRewardExpiry) {

        // Create the Transaction object
        Transaction transaction = new Transaction();


        // Set the fields
        transaction.setTxnType(TransactionType.EXPIRATION);

        transaction.setTxnMerchantNo(customerRewardExpiry.getCreMerchantNo());

        transaction.setTxnLoyaltyId(customerRewardExpiry.getCreLoyaltyId());

        transaction.setTxnStatus(TransactionStatus.PROCESSED);

        transaction.setTxnDate(new Date(new java.util.Date().getTime()));

        transaction.setTxnLocation(0L);

        transaction.setTxnInternalRef(customerRewardExpiry.getCreId().toString());

        transaction.setTxnExternalRef("0");

        transaction.setTxnRewardCurrencyId(customerRewardExpiry.getCreRewardCurrencyId());

        transaction.setTxnRewardQty(customerRewardExpiry.getCreRewardBalance());

        transaction.setTxnCrDbInd(CreditDebitInd.DEBIT);

        transaction.setTxnAmount(0.0);

        transaction.setTxnProgramId(0l);

        transaction.setTxnRewardPreBal(customerRewardExpiry.getCreRewardBalance());

        transaction.setTxnRewardPostBal(0);

        transaction.setTxnRewardExpDt(customerRewardExpiry.getCreExpiryDt());

        // Return the transaction
        return transaction;

    }

    /**
     *
     * Function to get the Transaction object for the deductPoint operation
     * Here we pass the PoinDeductData object, redeemQty for the transaction, preBalance and the reference
     *
     * @param pointDeductData   - PointDeductObject data
     * @param loyaltyId         - Loyalty id of the customer
     * @param redeemQty         - REdeem quantity for the current transasction
     * @param preBalance        - Prebalance of the reward balance for the customer
     *
     * @return                  - Return the transaction object built from the passed values
     */
    public Transaction getTransactionForPointDeductData(PointDeductData pointDeductData,String loyaltyId,Double redeemQty,Double preBalance) {

        // Create the Transaction object
        Transaction transaction = new Transaction();


        // Set the fields
        transaction.setTxnType(pointDeductData.getTxnType());

        transaction.setTxnMerchantNo(pointDeductData.getMerchantNo());

        transaction.setTxnLoyaltyId(loyaltyId);

        transaction.setTxnStatus(TransactionStatus.PROCESSED);

        transaction.setTxnDate(pointDeductData.getTxnDate());

        transaction.setTxnLocation(pointDeductData.getTxnLocation());

        transaction.setTxnInternalRef(pointDeductData.getInternalRef());

        transaction.setTxnExternalRef(pointDeductData.getExternalRef());

        transaction.setTxnRewardCurrencyId(pointDeductData.getRwdCurrencyId());

        transaction.setTxnRewardQty(redeemQty);

        transaction.setTxnCrDbInd(CreditDebitInd.DEBIT);

        transaction.setTxnAmount(pointDeductData.getTxnAmount());

        transaction.setTxnProgramId(0L);

        transaction.setTxnRewardPreBal(preBalance);

        transaction.setTxnRewardPostBal(preBalance - redeemQty);

        transaction.setTxnRewardExpDt(DBUtils.covertToSqlDate("9999-12-31"));



        // Set the audit information
        transaction.setCreatedBy(pointDeductData.getAuditDetails());

        // Return the transaction
        return transaction;
    }

    /**
     * Function to clear the null values from the loyalty program fields
     * Funnction will check for the fields where null is possible and set the default
     * value for that field.
     *
     * @param loyaltyProgram    - The LoyaltyProgram object that need to be checked for the null values
     *
     * @return                  - Return the object after clearing the null values and replacing
     *                            them with the defautl values
     */
    public  LoyaltyProgram clearLoyaltyProgramFieldsFromNull(LoyaltyProgram loyaltyProgram) {

        // Check the transaction currency
        if ( loyaltyProgram.getPrgTxnCurrency() == null ) {

            loyaltyProgram.setPrgTxnCurrency(0);

        }


        // Check the fixed value
        if ( loyaltyProgram.getPrgFixedValue() == null) {

            loyaltyProgram.setPrgFixedValue(0.0);

        }


        // check the ratio deno
        if ( loyaltyProgram.getPrgRatioDeno() == null ) {

            loyaltyProgram.setPrgRatioDeno(1.0);

        }


        // Check the ratio num
        if ( loyaltyProgram.getPrgRatioNum() == null ) {

            loyaltyProgram.setPrgRatioNum(0.0);

        }



        // Check the tiered ratio fields
        // Check for the tier1 fields
        if ( loyaltyProgram.getPrgTier1Num() == null) {

            loyaltyProgram.setPrgTier1Num(0.0);

        }

        if ( loyaltyProgram.getPrgTier1Deno() == null ) {

            loyaltyProgram.setPrgTier1Deno(1.0);

        }

        if ( loyaltyProgram.getPrgTier1LimitFrom() == null ) {

            loyaltyProgram.setPrgTier1LimitFrom(0.0);

        }


        if ( loyaltyProgram.getPrgTier1LimitTo() == null ) {

            loyaltyProgram.setPrgTier1LimitTo(0.0);

        }



        // Check for the tier2 fields
        if ( loyaltyProgram.getPrgTier2Num() == null) {

            loyaltyProgram.setPrgTier2Num(0.0);

        }

        if ( loyaltyProgram.getPrgTier2Deno() == null ) {

            loyaltyProgram.setPrgTier2Deno(1.0);

        }

        if ( loyaltyProgram.getPrgTier2LimitFrom() == null ) {

            loyaltyProgram.setPrgTier2LimitFrom(0.0);

        }


        if ( loyaltyProgram.getPrgTier2LimitTo() == null ) {

            loyaltyProgram.setPrgTier2LimitTo(0.0);

        }



        // Check for the tier3 fields
        if ( loyaltyProgram.getPrgTier3Num() == null) {

            loyaltyProgram.setPrgTier3Num(0.0);

        }

        if ( loyaltyProgram.getPrgTier3Deno() == null ) {

            loyaltyProgram.setPrgTier3Deno(1.0);

        }

        if ( loyaltyProgram.getPrgTier3LimitFrom() == null ) {

            loyaltyProgram.setPrgTier3LimitFrom(0.0);

        }


        if ( loyaltyProgram.getPrgTier3LimitTo() == null ) {

            loyaltyProgram.setPrgTier3LimitTo(0.0);

        }



        // Check for the tier4 fields
        if ( loyaltyProgram.getPrgTier4Num() == null) {

            loyaltyProgram.setPrgTier4Num(0.0);

        }

        if ( loyaltyProgram.getPrgTier4Deno() == null ) {

            loyaltyProgram.setPrgTier4Deno(1.0);

        }

        if ( loyaltyProgram.getPrgTier4LimitFrom() == null ) {

            loyaltyProgram.setPrgTier4LimitFrom(0.0);

        }


        if ( loyaltyProgram.getPrgTier4LimitTo() == null ) {

            loyaltyProgram.setPrgTier4LimitTo(0.0);

        }



        // Check for the tier5 fields
        if ( loyaltyProgram.getPrgTier5Num() == null) {

            loyaltyProgram.setPrgTier5Num(0.0);

        }

        if ( loyaltyProgram.getPrgTier5Deno() == null ) {

            loyaltyProgram.setPrgTier5Deno(1.0);

        }

        if ( loyaltyProgram.getPrgTier5LimitFrom() == null ) {

            loyaltyProgram.setPrgTier5LimitFrom(0.0);

        }


        if ( loyaltyProgram.getPrgTier5LimitTo() == null ) {

            loyaltyProgram.setPrgTier5LimitTo(0.0);

        }



        // Check the txn min amount
        if ( loyaltyProgram.getPrgMinTxnAmount() == null ) {

            loyaltyProgram.setPrgMinTxnAmount(0);

        }


        // Check the AwardCustCount field
        if ( loyaltyProgram.getPrgAwardCustCount() == null ) {

            loyaltyProgram.setPrgAwardCustCount(0);

        }


        // Check the awardfreq
        if ( loyaltyProgram.getPrgAwardFreq() ==  null ) {

            loyaltyProgram.setPrgAwardFreq(1);
        }



        // Return the LoyaltyProgram object
        return loyaltyProgram;
    }


    /**
     * Function to get the PointRewardData object for date triggered loyalty processing
     *
     * @param loyaltyProgram    - The LoyaltyProgram object
     * @param customer          - The customer object
     * @return                  - The PointRewardData object with the field filled up using
     *                            the objects passed
     */
    public PointRewardData getPointRewardDataForDTProcessing(LoyaltyProgram loyaltyProgram,Customer customer) {

        // Create the PointRewardData object
        PointRewardData pointRewardData = new PointRewardData();

        // Set the merchant number
        pointRewardData.setMerchantNo(customer.getCusMerchantNo());

        // Set the loyaty id
        pointRewardData.setLoyaltyId(customer.getCusLoyaltyId());

        // Set the programId
        pointRewardData.setProgramId(loyaltyProgram.getPrgProgramNo());

        // Set the reward currency
        pointRewardData.setRwdCurrencyId(loyaltyProgram.getPrgCurrencyId());

        // Set the transaction type
        pointRewardData.setTxnType(TransactionType.PURCHASE);

        // Set the transaction amount
        pointRewardData.setTxnAmount(0.0);

        // Set the location
        pointRewardData.setTxnLocation(customer.getCusLocation());

        // Set the transaction date
        pointRewardData.setTxnDate(new Date(new java.util.Date().getTime()));

        // Set the auditDetails to be same as the sale object
        pointRewardData.setAuditDetails("system");

        // Set the isAddToAccumulatedBalance to true ( Loyalty program transactions are considered for tier upgrade)
        pointRewardData.setAddToAccumulatedBalance(true);

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



        // Return the pointRewardData object
        return pointRewardData;

    }


    /**
     * Function to get the expiry date for a given reward currency
     *
     * @param rwdCurrencyId - The reward currency id for which the reward currency
     *                        expiry need to be fetched
     * @return              - The date of reward currency on success
     *                        null on failure
     */
    public java.util.Date getExpiryDateForRewardCurrency( Long rwdCurrencyId ) {

        // Get the rewardCurrency object
        RewardCurrency rewardCurrency = rewardCurrencyService.findByRwdCurrencyId(rwdCurrencyId);

        // If the reward currency is null, return null
        if ( rewardCurrency == null ) {

            return null;

        }


        // Variable holding the expiryDate
        java.util.Date expiryDate = new java.util.Date();


        // Check the expiry option for the reward currecy
        if ( rewardCurrency.getRwdExpiryOption() == RewardCurrencyExpiryOption.NO_EXPIRY ) {

            // Set the date to highest date
            expiryDate = generalUtils.convertToDate("9999-12-31");


        } else if ( rewardCurrency.getRwdExpiryOption() == RewardCurrencyExpiryOption.EXPIRY_DATE ) {

            // Set the date
            expiryDate = new java.util.Date(rewardCurrency.getRwdExpiryDate().getTime());

        } else if ( rewardCurrency.getRwdExpiryOption() == RewardCurrencyExpiryOption.EXPIRY_DAYS ){

            // Set the date
            expiryDate = generalUtils.addDaysToToday(rewardCurrency.getRwdExpiryDays());

        }

        // Return the expiryDate
        return expiryDate;

    }

    /**
     * Returns a new object which specifies the the wanted result page.
     * @param pageIndex The index of the wanted result page
     * @param numItems  The number of items in a page
     *
     * @return  - The Pageable object with specified values
     */
    public Pageable constructPageSpecification(int pageIndex,int numItems,String sortField) {

        // Create the Pageable object
        Pageable pageSpecification = new PageRequest(pageIndex, numItems, new Sort(Sort.Direction.ASC,sortField));

        // return the pageSpecification
        return pageSpecification;

    }
}
