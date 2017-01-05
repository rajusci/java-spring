package com.inspirenetz.api.util;

import com.inspirenetz.api.core.dictionary.AccountBundlingSettingLinkBehaviour;
import com.inspirenetz.api.core.dictionary.CreditDebitInd;
import com.inspirenetz.api.core.dictionary.TransactionStatus;
import com.inspirenetz.api.core.dictionary.TransactionType;
import com.inspirenetz.api.core.domain.*;
import com.inspirenetz.api.core.service.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.sql.Date;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

/**
 * Created by sandheepgr on 29/8/14.
 */
@Component
public class AccountBundlingUtils {


    @Autowired
    private PrimaryLoyaltyService primaryLoyaltyService;

    @Autowired
    private CustomerService customerService;

    @Autowired
    private LinkedLoyaltyService linkedLoyaltyService;

    @Autowired
    private CustomerRewardExpiryService customerRewardExpiryService;

    @Autowired
    private CustomerRewardBalanceService customerRewardBalanceService;

    @Autowired
    private AccountBundlingSettingService accountBundlingSettingService;

    @Autowired
    private TierService tierService;



    // Create the logger class
    private static Logger log = LoggerFactory.getLogger(AccountBundlingUtils.class);

    /**
     * Function to create the AccumulatedRewardBalance object for the given merchant number
     * loyaltyId, rewardcurrency and balance
     *
     * @param merchantNo        - Merchant number of the merchant
     * @param loyaltyId         - Loyalty id of the customer
     * @param rewardCurrency    - Reward currency of the awarding points
     * @param balance           - The balance to be set
     *
     * @return                  - Return the object with the fields specified
     */
    public AccumulatedRewardBalance createAccumulatedRewardBalance(Long merchantNo,String loyaltyId, Long rewardCurrency, Double balance) {

        // Create a new object
        AccumulatedRewardBalance accumulatedRewardBalance = new AccumulatedRewardBalance();


        // Set the fields
        accumulatedRewardBalance.setArbMerchantNo(merchantNo);

        accumulatedRewardBalance.setArbLoyaltyId(loyaltyId);

        accumulatedRewardBalance.setArbRewardCurrency(rewardCurrency);

        accumulatedRewardBalance.setArbRewardBalance(balance);


        // Return the object
        return accumulatedRewardBalance;

    }


    /**
     * Function to create LinkedRewardBalance object from the parameters passed
     *
     * @param merchantNo        - The merchant number of the merchant
     * @param loyaltyId         - The loyalty id of the customer
     * @param rewardCurrency    - The reward currency id of the currency
     * @param balance           - The balance to be set on the object
     *
     *
     * @return                  - Return the object with data
     */
    public LinkedRewardBalance createLinkedRewardBalance(Long merchantNo,String loyaltyId, Long rewardCurrency, Double balance) {

        // Create new object
        LinkedRewardBalance  linkedRewardBalance = new LinkedRewardBalance();

        // Set the fields
        linkedRewardBalance.setLrbMerchantNo(merchantNo);

        linkedRewardBalance.setLrbPrimaryLoyaltyId(loyaltyId);

        linkedRewardBalance.setLrbRewardCurrency(rewardCurrency);

        linkedRewardBalance.setLrbRewardBalance(balance);


        // Return the object
        return linkedRewardBalance;

    }


    /**
     * Function to check if a customer is primary or not
     * The function accepts the loyaltyid and then check in the PrimaryLoyalty table
     * Returns true if the customer is present in the PrimaryLoyalty table
     *
     * @param loyaltyId     - The loyalty id of the customer that need to be checked for primary
     * @return              - Return true if the customer is in the PrimaryLoyalty table
     *                        Return false otherwise.
     */
    public boolean isCustomerPrimary(String loyaltyId) {

        // Log the incoming loyaltyId
        log.info("isCustomerPrimary -> Loyalty id : " + loyaltyId);

        // Check if there is entry in tne PrimaryLoyalty table
        PrimaryLoyalty primaryLoyalty = primaryLoyaltyService.findByPllLoyaltyId(loyaltyId);

        // check if the PrimaryLoyalty is present
        if( primaryLoyalty == null ) {

            // Log the information
            log.info("LoyaltyEngineUtilsUtils -> isCustomerPrimary ->  Customer is not a primary");

            // Return false'
            return false;

        }

        // Return true
        return true;

    }

    public boolean isCustomerPrimary(Customer customer) {

        // Log the incoming loyaltyId
        log.info("isCustomerPrimary -> Customer : " + customer);

        // Check if there is entry in tne PrimaryLoyalty table
        PrimaryLoyalty primaryLoyalty = primaryLoyaltyService.findByPllCustomerNo(customer.getCusCustomerNo());

        // check if the PrimaryLoyalty is present
        if( primaryLoyalty == null ) {

            // Log the information
            log.info("LoyaltyEngineUtilsUtils -> isCustomerPrimary ->  Customer is not a primary");

            // Return false'
            return false;

        }

        // Return true
        return true;

    }


    /**
     * Function to get the Primary customer for the passed customer
     * This function check if the passed customer itself is a primary or
     * checks if the customer is part of a linking and return the primary of the
     * linked group
     *
     * @param merchantNo        - Merchant number of the merchant
     * @param loyaltyId         - The loyalty id of the customer that need to be checked
     *
     * @return                  - The Customer object if the customer is a primary or has a primary
     *                            Return null if none of the above cases are matching
     */
    public Customer getPrimaryCustomerForCustomer( Long merchantNo, String loyaltyId ) {

        Customer customer = customerService.findByCusLoyaltyIdAndCusMerchantNo(loyaltyId,merchantNo);

        // First check if the customer is primary
        boolean isPrimary = isCustomerPrimary(customer);

        // If the customer is primary, then we need to deduct the linked reward balance
        if ( isPrimary ) {

            // Return the object
            return customer;

        }



        // Check if the customer is linked to any other customer who is primary
        LinkedLoyalty linkedLoyalty = getCustomerLinkedLoyalty(merchantNo,loyaltyId);

        // If the linkedLoyalty is not null, then we need to find the data
        if ( linkedLoyalty != null ) {

            // Get the primary customer
            Customer primary = customerService.findByCusCustomerNo(linkedLoyalty.getLilParentCustomerNo());

            // Return the primary
            return primary;

        }


        // If nothing matches, then the customer is not primary and is not linked to any account
        return null;

    }


    /**
     * Function to get the LinkedLoyalty object for a given merchant number and loyalty id
     *
     * @param merchantNo    - The merchant number of the merchant
     * @param loyaltyId     - The loyalty id of the customer for whom we need to check
     *
     *
     * @return              - Return LinkedLoyalty object if the customer has entry in the LinkedLoyalty table
     *                        Return null if the customer is not in the LinkedLoyalty table
     */
    public LinkedLoyalty getCustomerLinkedLoyalty(Long merchantNo, String loyaltyId) {


        // Get the Customer Information
        Customer customer = customerService.findByCusLoyaltyIdAndCusMerchantNo(loyaltyId,merchantNo);

        // If the customer is null, return
        if ( customer == null ) {

            // Log the information
            log.info("LoyaltyEngineUtils -> getCustomerLinkedLoyalty -> No customer information found " );

            // Return
            return null;

        }



        // Check if the customer is linked to any primary
        LinkedLoyalty linkedLoyalty = linkedLoyaltyService.findByLilChildCustomerNo(customer.getCusCustomerNo());

        // Check if the linkedLoyaltyExists
        if ( linkedLoyalty == null ) {

            // Log the information
            log.info("LoyaltyEngineUtils -> getCustomerLinkedLoyalty -> No linking found " );

        }


        // Return the LinkedLoyalty object
        return linkedLoyalty;

    }


    /**
     * Function to create a transaction for a linking account balance transfer
     *
     * @param merchantNo        - Merchant number of the merchant
     * @param srcLoyaltyId      - The source loyalty id ( loyalty id under which transaction is stored)
     * @param rwdId             - The reward currency id
     * @param rwdQty            - The reward qty
     * @param destLoyaltyId     - Destination loyalty id ( reference)
     * @param location          - Location of transaction
     * @param preBalance        - Prebalanc value
     * @param creditDebitInd    - Credit or debit type
     * @param txnType           - Transaction type
     * @return                  - REturn the Transaction object
     */
    public Transaction createTransactionForLinkingTransfer(Long merchantNo,String srcLoyaltyId,Long rwdId, Double rwdQty,String destLoyaltyId,Long location,Double preBalance,Integer creditDebitInd,Integer txnType) {

        // Create the Transaction object
        Transaction transaction = new Transaction();


        // Set the fields
        transaction.setTxnType(txnType);

        transaction.setTxnMerchantNo(merchantNo);

        transaction.setTxnLoyaltyId(srcLoyaltyId);

        transaction.setTxnStatus(TransactionStatus.PROCESSED);

        transaction.setTxnDate(new Date(new java.util.Date().getTime()));

        transaction.setTxnLocation(location);

        transaction.setTxnInternalRef("0");

        transaction.setTxnExternalRef(destLoyaltyId);

        transaction.setTxnRewardCurrencyId(rwdId);

        transaction.setTxnRewardQty(rwdQty);

        transaction.setTxnCrDbInd(creditDebitInd);

        transaction.setTxnAmount(0.0);

        transaction.setTxnProgramId(0L);

        transaction.setTxnRewardPreBal(preBalance);

        transaction.setTxnRewardPostBal(preBalance + rwdQty);

        transaction.setTxnRewardExpDt(DBUtils.covertToSqlDate("9999-12-31"));



        // Return the transaction
        return transaction;

    }


    /**
     * Function to move all the customer reward expiry entries from the secondary to primary
     * if there is a entry with same rwdId and expiry date, we consolidate
     * After each checking, the secondary balance is deleted
     * Otherwise a new entry is put in the list and finally the list is saved
     *
     * @param primary       - The primary customer object
     * @param secondary     - The secondary customer object
     * @return              - True if moved successfully
     */
    public boolean moveCustomerRewardExpiryToPrimary(Customer primary, Customer secondary) {

        // Get the customerRewardExpiry for the secondary
        List<CustomerRewardExpiry> secRewardExpiryList = customerRewardExpiryService.findByCreMerchantNoAndCreLoyaltyId(secondary.getCusMerchantNo(),secondary.getCusLoyaltyId());

        // Check if the rewardexpiryList is valid
        if ( secRewardExpiryList == null || secRewardExpiryList.isEmpty() ) {

            // Log the information
            log.info("moveCustomerRewardExpiryToPrimary -> Secondary does not have any reward expiry entries");

            // Return true
            return true;

        }


        // Create the List for primary
        List<CustomerRewardExpiry> priRewardExpiryList = customerRewardExpiryService.findByCreMerchantNoAndCreLoyaltyId(primary.getCusMerchantNo(),primary.getCusLoyaltyId());

        // If the list is null, the set the list to new Arraylist
        if ( priRewardExpiryList == null ) {

            priRewardExpiryList = new ArrayList<>(0);

        }


        // Go through the list of secondary reward expiry list and check
        for( CustomerRewardExpiry secCustomerRewardExpiry : secRewardExpiryList ) {

            // Flag showing if the reward expiry exists for the primary
            boolean isEntryExists  = false;

            // Go through the list of primary reward expiry
            for(CustomerRewardExpiry priCustomerRewardExpiry : priRewardExpiryList ) {

                if ( secCustomerRewardExpiry.getCreRewardCurrencyId().longValue() == priCustomerRewardExpiry.getCreRewardCurrencyId().longValue() &&
                        getDateWithoutTime(secCustomerRewardExpiry.getCreExpiryDt()).compareTo(getDateWithoutTime(priCustomerRewardExpiry.getCreExpiryDt())) == 0) {

                    // Set the balance exists to true
                    isEntryExists = true;

                    // Add the balance to the primary
                    priCustomerRewardExpiry.setCreRewardBalance( priCustomerRewardExpiry.getCreRewardBalance() + secCustomerRewardExpiry.getCreRewardBalance() );

                }

            }


            // If the entry does not exists, then we need to create one
            if ( !isEntryExists ) {

                // Create the object
                CustomerRewardExpiry customerRewardExpiry = new CustomerRewardExpiry();


                // Set the fields
                customerRewardExpiry.setCreLoyaltyId(primary.getCusLoyaltyId());;

                customerRewardExpiry.setCreMerchantNo(primary.getCusMerchantNo());

                customerRewardExpiry.setCreExpiryDt(secCustomerRewardExpiry.getCreExpiryDt());

                customerRewardExpiry.setCreRewardCurrencyId(secCustomerRewardExpiry.getCreRewardCurrencyId());

                customerRewardExpiry.setCreRewardBalance(secCustomerRewardExpiry.getCreRewardBalance());


                // Add to the list
                priRewardExpiryList.add(customerRewardExpiry);

            }



            // Delete the secondary customer reward expiry entry
            customerRewardExpiryService.deleteCustomerRewardExpiry(secCustomerRewardExpiry);

        }


        // save the list
        customerRewardExpiryService.saveAll(priRewardExpiryList);


        // Return true
        return true;

    }

    /*
    Method converts the date time to a date only value.
    All the time related fields will be set to zero
    * */
    public java.util.Date getDateWithoutTime(Date expiryDate){

        java.util.Date retDate = expiryDate;

        Calendar calendar = Calendar.getInstance();

        calendar.setTime( expiryDate );
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);

        retDate = calendar.getTime();

        return retDate;

    }

    /**
     * Function to move all the customer reward balance entries from the secondary to primary
     * if there is a entry with same rwdId and expiry date, we consolidate
     * After each checking, the secondary balance is deleted
     * Otherwise a new entry is put in the list and finally the list is saved
     *
     * @param primary       - The primary customer object
     * @param secondary     - The secondary customer object
     * @return              - True if moved successfully
     */
    public boolean moveCustomerRewardBalanceToPrimary(Customer primary, Customer secondary) {

        // Get the customerRewardBalance for the secondary
        List<CustomerRewardBalance> secRewardBalanceList = customerRewardBalanceService.findByCrbLoyaltyIdAndCrbMerchantNo(secondary.getCusLoyaltyId(),secondary.getCusMerchantNo());

        // Check if the rewardBalanceList is valid
        if ( secRewardBalanceList == null || secRewardBalanceList.isEmpty() ) {

            // Log the information
            log.info("moveCustomerRewardBalanceToPrimary -> Secondary does not have any reward Balance entries");

            // Return true
            return true;

        }


        // Crbate the List for primary
        List<CustomerRewardBalance> priRewardBalanceList = customerRewardBalanceService.findByCrbLoyaltyIdAndCrbMerchantNo(primary.getCusLoyaltyId(),primary.getCusMerchantNo());

        // If the list is null, the set the list to new Arraylist
        if ( priRewardBalanceList == null ) {

            priRewardBalanceList = new ArrayList<>(0);

        }


        // Go through the list of secondary reward Balance list and check
        for( CustomerRewardBalance secCustomerRewardBalance : secRewardBalanceList ) {

            // Flag showing if the reward Balance exists for the primary
            boolean isEntryExists  = false;

            // Go through the list of primary reward Balance
            for(CustomerRewardBalance priCustomerRewardBalance : priRewardBalanceList ) {

                if ( secCustomerRewardBalance.getCrbRewardCurrency().longValue() == priCustomerRewardBalance.getCrbRewardCurrency().longValue()) {

                    // Set the balance exists to true
                    isEntryExists = true;

                    // Add the balance to the primary
                    priCustomerRewardBalance.setCrbRewardBalance( priCustomerRewardBalance.getCrbRewardBalance() + secCustomerRewardBalance.getCrbRewardBalance() );

                }

            }


            // If the entry does not exists, then we need to create one
            if ( !isEntryExists ) {

                // Crbate the object
                CustomerRewardBalance customerRewardBalance = new CustomerRewardBalance();


                // Set the fields
                customerRewardBalance.setCrbLoyaltyId(primary.getCusLoyaltyId());;

                customerRewardBalance.setCrbMerchantNo(primary.getCusMerchantNo());

                customerRewardBalance.setCrbRewardCurrency(secCustomerRewardBalance.getCrbRewardCurrency());

                customerRewardBalance.setCrbRewardBalance(secCustomerRewardBalance.getCrbRewardBalance());


                // Add to the list
                priRewardBalanceList.add(customerRewardBalance);

            }



            // Delete the secondary customer reward Balance entry
            customerRewardBalanceService.deleteCustomerRewardBalance(secCustomerRewardBalance);

        }


        // Save the list
        customerRewardBalanceService.saveAll(priRewardBalanceList);

        // Return true
        return true;

    }


    /**
     * Function to get the effective tier for the customer based on the
     * linking and the account bundling setting
     *
     * @param customer  - The Customer object
     *
     * @return          - Return the tier for the customer
     */
    public Tier getEffectiveTierForCustomer(Customer customer) {

        // Get the primary for the customer
        Customer primary = getPrimaryCustomerForCustomer(customer.getCusMerchantNo(),customer.getCusLoyaltyId());

        // If the primary is not null, check is self is primary
        if ( primary != null ) {

            // Check if the primary is same as the current customer
            if ( primary.getCusLoyaltyId().equals(customer.getCusLoyaltyId()) ) {

                // Return the tier for customer
                return tierService.findByTieId(primary.getCusTier());

            } else {

                // Get the AccountBundlingSetting
                AccountBundlingSetting accountBundlingSetting = accountBundlingSettingService.getDefaultAccountBundlingSetting(customer.getCusMerchantNo());

                // Check the setting for the  tier
                if ( accountBundlingSetting.getAbsTierBehaviour() == AccountBundlingSettingLinkBehaviour.BASED_ON_PRIMARY) {

                    // Return the tier for the primary
                    return tierService.findByTieId(primary.getCusTier());

                }
            }
        }

        // Return the tier for the customer
        return tierService.findByTieId(customer.getCusTier());

    }



    /**
     * Function to get the list of linked customers for a given customer
     * This function will check if the current customer is primary and if its
     * primary , then we will load the linked customer information
     *
     * @param customer  - The customer object for which we need to get the information
     * @return          - Return the list of customers if the customer is primary
     *                    If the customer is not primary, the list will only contain the
     *                    current customer passed as parameter
     */
    public List<Customer> getLinkedCustomers(Customer customer) {

        // The list to return
        List<Customer> customerList = new ArrayList<>(0);

        // add the current customer to the list
        customerList.add(customer);

        // Check if the customer is primary
        boolean isPrimary = isCustomerPrimary(customer.getCusLoyaltyId());

        // If the customer is primary, then we need to get the linked accounts
        if ( isPrimary ) {

            // Get the LinkedAccounts
            List<LinkedLoyalty> linkedLoyaltyList =  linkedLoyaltyService.findByLilParentCustomerNo(customer.getCusCustomerNo());

            // Iterate through the LinkedLoyalty and add the customers
            for( LinkedLoyalty linkedLoyalty : linkedLoyaltyList ) {

                // Lazily load the customer object
                linkedLoyalty.getChildCustomer().toString();

                // Add to the list
                customerList.add(linkedLoyalty.getChildCustomer()) ;
            }

        }

        // Return the list
        return customerList;

    }
}
