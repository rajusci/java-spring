package com.inspirenetz.api.core.service.impl;

import com.inspirenetz.api.core.dictionary.*;
import com.inspirenetz.api.core.domain.*;
import com.inspirenetz.api.core.service.*;
import com.inspirenetz.api.rest.exception.InspireNetzException;
import com.inspirenetz.api.util.AccountBundlingUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by sandheepgr on 8/9/14.
 */
@Service
public class AccountTransferServiceImpl implements AccountTransferService {

    // Initialize the logger
    private static Logger log = LoggerFactory.getLogger(LoyaltyEngineServiceImpl.class);

    @Autowired
    private CustomerService customerService;

    @Autowired
    private CustomerRewardBalanceService customerRewardBalanceService;
    
    @Autowired
    private CustomerRewardExpiryService customerRewardExpiryService;

    @Autowired
    private AccumulatedRewardBalanceService accumulatedRewardBalanceService;

    @Autowired
    private LinkedRewardBalanceService linkedRewardBalanceService;

    @Autowired
    private LinkedLoyaltyService linkedLoyaltyService;

    @Autowired
    private PrimaryLoyaltyService primaryLoyaltyService;

    @Autowired
    private AccountBundlingUtils accountBundlingUtils;

    @Autowired
    private TransactionService transactionService;

    @Autowired
    private AccountArchiveService accountArchiveService;

    @Autowired
    CustomerActivityService customerActivityService;

    @Autowired
    LinkRequestService linkRequestService;

    @Override
    public boolean transferAccount( String oldLoyaltyId, String newLoyaltyId ,Long merchantNo) throws InspireNetzException {

        // Get the customer information for oldLoyaltyId
        Customer oldCustomer = customerService.findByCusLoyaltyIdAndCusMerchantNo(oldLoyaltyId,merchantNo);

        // Create the new customer object with the customer loyalty id
        Customer newCustomer = new Customer();

        // Set the merchant no
        newCustomer.setCusMerchantNo(merchantNo);

        // Set the loyalty id
        newCustomer.setCusLoyaltyId(newLoyaltyId);


        // Check if the request is valid
        boolean isValid = isRequestValid(oldCustomer,newCustomer);

        // If the request is not valid, then throw exception
        if ( !isValid ) {

            // Log the information
            log.info("transferAccount -> Request is not valid");

            // Throw exception
            throw new InspireNetzException(APIErrorCode.ERR_OPERATION_NOT_ALLOWED);

        }

        // read the new cusotmer information
        newCustomer = customerService.findByCusLoyaltyIdAndCusMerchantNo(newCustomer.getCusLoyaltyId(),newCustomer.getCusMerchantNo());

        // if the new customer is not valid, then w e need to show the error message
        if ( newCustomer == null ) {

            // Log the information
            log.info("transferAccount -> New customer not saved successfully");

            //log the activity
            customerActivityService.logActivity(oldCustomer.getCusLoyaltyId(),CustomerActivityType.TRANSFER_POINT,"Failed , customer saving failed",oldCustomer.getCusMerchantNo(),"");

            // Throw exception
            throw new InspireNetzException(APIErrorCode.ERR_OPERATION_FAILED);

        }

        // Log the information
        log.info("TransferAccounts -> Source account : " +oldCustomer.toString());

        log.info("TransferAccount -> Destination Account :" +newCustomer.toString());

        //process the account transfer
        boolean isTransferred = processAccountTransfer(oldCustomer,newCustomer);

        //log the activity
        customerActivityService.logActivity(oldCustomer.getCusLoyaltyId(),CustomerActivityType.TRANSFER_ACCOUNT,"Account transferred to :"+newCustomer.getCusLoyaltyId(),oldCustomer.getCusMerchantNo(),"");

        //log the activity
        customerActivityService.logActivity(newCustomer.getCusLoyaltyId(),CustomerActivityType.TRANSFER_ACCOUNT,"Account transferred from :"+oldCustomer.getCusLoyaltyId(),oldCustomer.getCusMerchantNo(),"");

        // finally return true
        return true;

    }

    @Transactional(rollbackFor = {InspireNetzException.class,Exception.class})
    private boolean processAccountTransfer(Customer oldCustomer, Customer newCustomer) throws InspireNetzException {

        //unlink all accounts linked with customer
        processAccountLinking(oldCustomer);

        // Call the moveBalances
        boolean isMoved = moveBalances(oldCustomer,newCustomer);


        // Check if the balance is moved
        if ( !isMoved ) {

            // Log the information
            log.info("TransferAccounts -> Moving of balances failed");

            //log the activity
            customerActivityService.logActivity(oldCustomer.getCusLoyaltyId(),CustomerActivityType.TRANSFER_POINT,"Failed , moving of balance failed",oldCustomer.getCusMerchantNo(),"");

            // Throw exception
            throw new InspireNetzException(APIErrorCode.ERR_OPERATION_FAILED);

        }



        // Call the updateLinkedTalbes
        boolean isUpdated = updateLinkedTables(oldCustomer,newCustomer);

        // Check if the data is updated
        if ( !isUpdated ) {

            // Log the information
            log.info("TransferAccounts -> Updating of linked loyalty tables failed");

            //log the activity
            customerActivityService.logActivity(oldCustomer.getCusLoyaltyId(),CustomerActivityType.TRANSFER_POINT,"Failed , updating of linked loyalty failed",oldCustomer.getCusMerchantNo(),"");

            // Throw exception
            throw new InspireNetzException(APIErrorCode.ERR_OPERATION_FAILED);

        }



        // Update the customers
        isUpdated = updateCustomers(oldCustomer,newCustomer);

        // Check if the update is successful
        if ( !isUpdated ) {

            // Log the information
            log.info("TransferAccounts -> Updating of customers not successful");

            //log the activity
            customerActivityService.logActivity(oldCustomer.getCusLoyaltyId(),CustomerActivityType.TRANSFER_POINT,"Failed , updating of customers not successful",oldCustomer.getCusMerchantNo(),"");

            // Throw exception
            throw new InspireNetzException(APIErrorCode.ERR_OPERATION_FAILED);

        }


        // Record in the accountarchive
        boolean isRecorded = recordTransfer(oldCustomer,newCustomer);

        // Check if record was added successfully
        if ( !isRecorded ) {

            // Log the information
            log.info("TransferAccounts -> Updating of customers not successful");

            //log the activity
            customerActivityService.logActivity(oldCustomer.getCusLoyaltyId(),CustomerActivityType.TRANSFER_POINT,"Failed , updating of customers failed",oldCustomer.getCusMerchantNo(),"");

            // Throw exception
            throw new InspireNetzException(APIErrorCode.ERR_OPERATION_FAILED);

        }

        return true;
    }

    private void processAccountLinking(Customer customer) throws InspireNetzException {

        //call the unlinkCustomerAccounts to unlink all accounts linked
        linkRequestService.unlinkCustomerAccounts(customer);

    }


    /**
     * Function to put an entry in the AccountArchive
     *
     * @param oldCustomer - Old customer object
     * @param newCustomer - New customer object
     *
     * @return            - True if the entry was made in the AccountArchive
     *
     */
    private boolean recordTransfer(Customer oldCustomer, Customer newCustomer) {

        // Create the ACcountArchive object
        AccountArchive accountArchive = new AccountArchive();

        // Set the fields
        accountArchive.setAarMerchantNo(oldCustomer.getCusMerchantNo());
        accountArchive.setAarOldLoyaltyId(oldCustomer.getCusLoyaltyId());
        accountArchive.setAarNewLoyaltyId(newCustomer.getCusLoyaltyId());

        // Save the object
        accountArchive = accountArchiveService.saveAccountArchive(accountArchive);

        // Check if the object is not null
        if ( accountArchive != null ) {

            return true;

        } else {

            return false;

        }

    }

    /**
     * Function to update the new customer
     * Here we update the customer status of the new customer to active and old customer to inactive
     *
     * @param oldCustomer   - The old customer object
     * @param newCustomer   - The new customer object
     *
     * @return              - true if update was successful
     *                        false if update failed
     */
    private boolean updateCustomers(Customer oldCustomer, Customer newCustomer) {

        // Set the status of the newCustomer to be active
        newCustomer.setCusStatus(CustomerStatus.ACTIVE);

        // Update the object
        newCustomer = customerService.saveCustomer(newCustomer);

        // Check if the customer is saved
        if ( newCustomer == null ) {

            // Return false
            return false;

        }



        // Set the oldCustomer status to inactive
        oldCustomer.setCusStatus(CustomerStatus.INACTIVE);

        // Set the oldCustomer register status to no so that its not listed
        // in the customers screen
        oldCustomer.setCusRegisterStatus(IndicatorStatus.NO);

        // Save the old customer
        oldCustomer = customerService.saveCustomer(oldCustomer);


        // Check if the update was successful
        if ( oldCustomer == null ) {

            // Return false
            return false;

        }


        // Return true
        return true;

    }

    /**
     * Function to move the reward balancess
     *
     * @param oldCustomer   - The old customer from which balance need to be moved
     * @param newCustomer   - The new customer to whom we need to move the balance
     *
     *
     * @return              - Return true, if successful
     *
     */
    private boolean moveBalances( Customer oldCustomer, Customer newCustomer ) {


        // Call the moveCustomerRewardBalance
        moveCustomerRewardBalance(oldCustomer,newCustomer);

        // Call the moveCustomerRewardExpiry
        moveCustomerRewardExpiry(oldCustomer,newCustomer);

        // Call the moveAccumulatedRewardBalance
        moveAccumulatedRewardBalance(oldCustomer,newCustomer);

        // Call the moveLinkedRewardBalance
        moveLinkedRewardBalance(oldCustomer,newCustomer);



        // Return true
        return true;

    }


    /**
     * Function to update the Linked reward tables
     * This will check if the customer is primary and then update the primary loyalty data
     *
     *
     * @param oldCustomer   - The old customer object
     * @param newCustomer   - The new customer object
     *
     * @return              - Return true is successful
     */
    private boolean updateLinkedTables(Customer oldCustomer, Customer newCustomer) {

        // Get the PrimaryLoyalty entity for the old customer ( if existing )
        PrimaryLoyalty primaryLoyalty = primaryLoyaltyService.findByPllCustomerNo(oldCustomer.getCusCustomerNo());

        // If the primaryLoyalty is existing, then update it
        if ( primaryLoyalty != null ) {

            // Update the customer no
            primaryLoyalty.setPllCustomerNo(newCustomer.getCusCustomerNo());

            // Update the loyalty id
            primaryLoyalty.setPllLoyaltyId(newCustomer.getCusLoyaltyId());

            // Save the PrimaryLoyalty object
            primaryLoyaltyService.savePrimaryLoyalty(primaryLoyalty);


            // Get the LinkedLoyalty list for the customer
            List<LinkedLoyalty> linkedLoyaltyList = linkedLoyaltyService.findByLilParentCustomerNo(primaryLoyalty.getPllCustomerNo());

            // Check if the list is not empty
            if ( linkedLoyaltyList != null && !linkedLoyaltyList.isEmpty() ) {

                // Go through the list and update the primary loyalty
                for(LinkedLoyalty linkedLoyalty : linkedLoyaltyList ) {

                    // Set the parent customer no
                    linkedLoyalty.setLilParentCustomerNo(newCustomer.getCusCustomerNo());

                }


                // Save the list
                linkedLoyaltyService.saveAll(linkedLoyaltyList);

                // Return the controler
                return true;

            }

        }



        // If the customer is not a primary, then check if he is a secondary
        LinkedLoyalty linkedLoyalty = linkedLoyaltyService.findByLilChildCustomerNo(oldCustomer.getCusCustomerNo());

        // If the linkedLoyalty entry is existing, then we need to have the data
        // update the customerNo
        if ( linkedLoyalty != null ) {

            // Set the child customer no to the newCustomer no
            linkedLoyalty.setLilChildCustomerNo(newCustomer.getCusCustomerNo());

            // Save the linkedLoyalty
            linkedLoyalty = linkedLoyaltyService.saveLinkedLoyalty(linkedLoyalty);

            // Check if the linkedLoyalty is valid
            if ( linkedLoyalty == null ) {

                // Return false
                return false;

            }

        }

        // Return true
        return true;

    }


    /**
     * Function to move the linked reward balance to the new customer object
     *
     * @param oldCustomer   - The old customer from which reward balance need to be moved
     * @param newCustomer   - The new customer to which the reward balance need to be moved
     *
     * @return              - Return true if the operation was successful
     */
    private boolean moveLinkedRewardBalance(Customer oldCustomer,Customer newCustomer) {

        // Get the linked reward balance list
        List<LinkedRewardBalance> linkedRewardBalanceList = linkedRewardBalanceService.findByLrbPrimaryLoyaltyIdAndLrbMerchantNo(oldCustomer.getCusLoyaltyId(),oldCustomer.getCusMerchantNo());

        // Check if the list is not null and is not empty
        if ( linkedRewardBalanceList != null && !linkedRewardBalanceList.isEmpty() ) {

            // Iterate through the LinkedRewardBalances and update the loyalty id
            for (LinkedRewardBalance linkedRewardBalance : linkedRewardBalanceList ) {

                // Update the loyalty id for the linkedreward balance
                linkedRewardBalance.setLrbPrimaryLoyaltyId(newCustomer.getCusLoyaltyId());

            }


            // SAve the list
            linkedRewardBalanceService.saveAll(linkedRewardBalanceList);

        }


        // Return true
        return true;

    }

    /**
     * Function to move the accumulatedRewardBalance from oldCustomer to the new Customer
     *
     * @param oldCustomer   - The oldCustomer object
     * @param newCustomer   - The new customer object
     *
     * @return              - True if the accumulated reward balance has been moved
     *                        False otherwise
     */
    private boolean moveAccumulatedRewardBalance(Customer oldCustomer,Customer newCustomer) {

        // Get the list of accumulaterewardBalance for the oldCustomer
        List<AccumulatedRewardBalance> accumulatedRewardBalanceList = accumulatedRewardBalanceService.findByArbMerchantNoAndArbLoyaltyId(oldCustomer.getCusMerchantNo(),oldCustomer.getCusLoyaltyId());

        // Check if the accumulatedRewardBalance list is not empty
        if (accumulatedRewardBalanceList != null && !accumulatedRewardBalanceList.isEmpty() ) {

            // Iterate through the list and then update the loyalty id
            for(AccumulatedRewardBalance accumulatedRewardBalance : accumulatedRewardBalanceList ) {

                // Update the loyalty id
                accumulatedRewardBalance.setArbLoyaltyId(newCustomer.getCusLoyaltyId());

            }


            // Save the list
            accumulatedRewardBalanceService.saveAll(accumulatedRewardBalanceList);

        }

        // Return true
        return true;

    }


    /**
     * function to move the customer reward expiry from old customer to the new customer
     * This function will list the reward expiry entries and update the loyalty id to the
     * new customer
     *
     * @param oldCustomer   - The old customer object
     * @param newCustomer   - The new customer object to which the balance need to be transferred
     *
     * @return              - True if the transfer was successful
     */
    private boolean moveCustomerRewardExpiry( Customer oldCustomer, Customer newCustomer ) {
        
        // Get the list of CUstomerRewardExpirys
        List<CustomerRewardExpiry> customerRewardExpiryList = customerRewardExpiryService.findByCreMerchantNoAndCreLoyaltyId(oldCustomer.getCusMerchantNo(),oldCustomer.getCusLoyaltyId());

        // Check if the customerList is null or empty
        if ( customerRewardExpiryList != null || !customerRewardExpiryList.isEmpty() ) {

            // Go through the list of CustomerRewardExpiryList entries
            for(CustomerRewardExpiry customerRewardExpiry : customerRewardExpiryList ) {

                // Update the customer loyalty id to the newCustomer loyalty id
                customerRewardExpiry.setCreLoyaltyId(newCustomer.getCusLoyaltyId());


            }


            // save the list
            customerRewardExpiryService.saveAll(customerRewardExpiryList);

        }


        // Return true;
        return true;
    }

    /**
     * Function to move the CustomerRewardBalanceEntry from oldCustomer to newCustomer
     * Here the customerRewardBalance loyalty id gets updated to the newCustomer
     * Also we log transaction for the transferTo and transferFrom points
     *
     * @param oldCustomer   - The old customer reward balance
     * @param newCustomer   - The new customer reward balance
     *
     * @return              - Return true if the reward balance has been transfered successfully
     *
     */
    private boolean moveCustomerRewardBalance(Customer oldCustomer, Customer newCustomer) {

        // Get the customerrewardbalance for the cusotmer
        List<CustomerRewardBalance> customerRewardBalanceList = customerRewardBalanceService.findByCrbLoyaltyIdAndCrbMerchantNo(oldCustomer.getCusLoyaltyId(),oldCustomer.getCusMerchantNo());

        // If the rewardbalance is not null, then we need to change the loyalty id to the
        // new customer's loyalty id
        if ( customerRewardBalanceList != null && !customerRewardBalanceList.isEmpty() ) {

            // Go through the list of the reward balances
            for(CustomerRewardBalance customerRewardBalance : customerRewardBalanceList ) {

                // Update the loyalty id to the new loyalty id
                customerRewardBalance.setCrbLoyaltyId(newCustomer.getCusLoyaltyId());


                // Log  point to transaction in oldCustomer loyalty id
                Transaction srcTransaction =  accountBundlingUtils.createTransactionForLinkingTransfer(
                                                                                                        oldCustomer.getCusMerchantNo(),
                                                                                                        oldCustomer.getCusLoyaltyId(),
                                                                                                        customerRewardBalance.getCrbRewardCurrency(),
                                                                                                        customerRewardBalance.getCrbRewardBalance(),
                                                                                                        newCustomer.getCusLoyaltyId(),
                                                                                                        oldCustomer.getCusLocation(),
                                                                                                        customerRewardBalance.getCrbRewardBalance(),
                                                                                                        CreditDebitInd.DEBIT,
                                                                                                        TransactionType.TRANSFER_ACCOUNT_TO
                                                                                                      );

                // Save the transaction
                transactionService.saveTransaction(srcTransaction);




                // Log the point from transaction in the newCustomer loyalty id
                Transaction destTransaction = accountBundlingUtils.createTransactionForLinkingTransfer(
                                                                                                        newCustomer.getCusMerchantNo(),
                                                                                                        newCustomer.getCusLoyaltyId(),
                                                                                                        customerRewardBalance.getCrbRewardCurrency(),
                                                                                                        customerRewardBalance.getCrbRewardBalance(),
                                                                                                        oldCustomer.getCusLoyaltyId(),
                                                                                                        newCustomer.getCusLocation(),
                                                                                                        customerRewardBalance.getCrbRewardBalance(),
                                                                                                        CreditDebitInd.CREDIT,
                                                                                                        TransactionType.TRANSFER_ACCOUNT_FROM
                                                                                                     );

                // Save the transaction
                transactionService.saveTransaction(destTransaction);





            }

            // Save the list
            customerRewardBalanceService.saveAll(customerRewardBalanceList);

            // Return true
            return true;

        } else {

            // Return true as there are no balance to transfer and hence is operation
            // is considered successful
            return true;

        }
    }

    /**
     * Function to check if the request is valid for transfer of account.
     * This function will check for the validity of the source and destination
     * customers
     *
     * @param oldCustomer   - The source customer object
     * @param newCustomer   - The destination customer object
     *
     * @return              - True if the customers are valid
     *                        False otherwise
     */
    private boolean isRequestValid( Customer oldCustomer, Customer newCustomer ) throws InspireNetzException {

        // Check if the first customer is active
        if ( oldCustomer == null || oldCustomer.getCusStatus() != CustomerStatus.ACTIVE ) {

            // Log the information
            log.info("AccountTransfer -> isRequestValid -> Existing customer is not active ");

            //log the activity
            customerActivityService.logActivity(oldCustomer.getCusLoyaltyId(),CustomerActivityType.TRANSFER_ACCOUNT,"Failed , existing customer is not active",oldCustomer.getCusMerchantNo(),"");

            // Throw exception
            throw new InspireNetzException(APIErrorCode.ERR_SOURCE_CUSTOMER_INVALID);

        }


        // Make sure the destination customer is existing and is not active in loyalty
        if ( newCustomer != null && newCustomer.getCusStatus() == CustomerStatus.ACTIVE ) {

            // Log the information
            log.info("AccountTransfer -> isRequestValid -> New customer is already active in loyalty");

            //log the activity
            customerActivityService.logActivity(oldCustomer.getCusLoyaltyId(),CustomerActivityType.TRANSFER_ACCOUNT,"Failed , new customer is already active",oldCustomer.getCusMerchantNo(),"");

            // Throw exception
            throw new InspireNetzException(APIErrorCode.ERR_DEST_CUSTOMER_INVALID);

        }

        boolean isDestValid = false;

        try{

            // validate the information for the MIN
            isDestValid = customerService.updateLoyaltyStatus(newCustomer.getCusLoyaltyId(),newCustomer.getCusMerchantNo(),CustomerStatus.ACTIVE);

        }catch(InspireNetzException ex){


        }

        // Check if the result is valid
        if ( !isDestValid ) {

            // Log the information
            log.info("AccountTransfer -> isRequestValid -> New customer details were not validated");

            //log the activity
            customerActivityService.logActivity(oldCustomer.getCusLoyaltyId(),CustomerActivityType.TRANSFER_ACCOUNT,"Failed , new customer details are not valid",oldCustomer.getCusMerchantNo(),"");

            // Throw exception
            throw new InspireNetzException(APIErrorCode.ERR_DEST_CUSTOMER_INVALID);

        }

        // Finally return true
        return true;


    }


}
