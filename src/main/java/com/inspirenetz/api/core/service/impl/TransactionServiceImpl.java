package com.inspirenetz.api.core.service.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.inspirenetz.api.core.dictionary.*;
import com.inspirenetz.api.core.domain.Customer;
import com.inspirenetz.api.core.domain.MerchantLocation;
import com.inspirenetz.api.core.domain.Transaction;
import com.inspirenetz.api.core.repository.BaseRepository;
import com.inspirenetz.api.core.repository.TransactionRepository;
import com.inspirenetz.api.core.service.*;
import com.inspirenetz.api.rest.exception.InspireNetzException;
import com.inspirenetz.api.rest.resource.TransactionResource;
import com.inspirenetz.api.util.AuthSessionUtils;
import com.inspirenetz.api.util.GeneralUtils;
import com.microideation.app.dialogue.event.DialogueEvent;
import org.dozer.Mapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.env.Environment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import reactor.core.Reactor;
import reactor.event.Event;
import reactor.spring.annotation.Selector;

import java.sql.Date;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;

/**
 * Created by sandheepgr on 17/2/14.
 */
@Service
public class TransactionServiceImpl extends BaseServiceImpl<Transaction> implements TransactionService {

    private static Logger log = LoggerFactory.getLogger(TransactionServiceImpl.class);

    @Autowired
    private TransactionRepository transactionRepository;

    @Autowired
    CustomerService customerService;

    @Autowired
    AuthSessionUtils authSessionUtils;

    @Autowired
    private GeneralUtils generalUtils;

    @Autowired
    private UserMessagingService userMessagingService;

    @Autowired
    private AnalyzeService analyzeService;



    public TransactionServiceImpl() {

        super(Transaction.class);

    }

    @Override
    protected BaseRepository<Transaction,Long> getDao() {
        return transactionRepository;
    }


    @Override
    public Page<Transaction> findByTxnMerchantNoAndTxnDateBetweenOrderByTxnIdDesc(Long txnMerchantNo, Date startDate, Date endDate, Pageable pageable) {

        // Get the transactionPage
        Page<Transaction> transactionPage = transactionRepository.findByTxnMerchantNoAndTxnRecordStatusAndTxnDateBetweenOrderByTxnIdDesc(txnMerchantNo, RecordStatus.RECORD_STATUS_ACTIVE,startDate, endDate, pageable);

        // Return the transactionPage
        return transactionPage;

    }

    @Override
    public Page<Transaction> findByTxnMerchantNoAndTxnLoyaltyIdAndTxnDateBetweenOrderByTxnIdDesc(Long txnMerchantNo, String txnLoyaltyId, Date startDate, Date endDate, Pageable pageable) {

        // Get the Transaction Page
        Page<Transaction> transactionPage = transactionRepository.findByTxnMerchantNoAndTxnLoyaltyIdAndTxnRecordStatusAndTxnDateBetweenOrderByTxnIdDesc(txnMerchantNo,txnLoyaltyId,RecordStatus.RECORD_STATUS_ACTIVE,startDate,endDate,pageable);

        // REturn the page
        return transactionPage;

    }

    @Override
    public List<Transaction> listTransactions(Long merchantNo, String loyaltyId) {

        // Get the list of sms
        List<Transaction>  transactionList = transactionRepository.listLastTransactions(merchantNo,loyaltyId,RecordStatus.RECORD_STATUS_ACTIVE);

        // REturn the list
        return transactionList;

    }


    @Override
    public void sendTransactionSMS(String loyaltyId) throws InspireNetzException {

        // get the merchant no for the session
        Long merchantNo = authSessionUtils.getMerchantNo();

        // Get the list of sms
        List<Transaction>  transactionList = transactionRepository.listLastTransactions(merchantNo,loyaltyId,RecordStatus.RECORD_STATUS_ACTIVE);

        MessageWrapper messageWrapper=generalUtils.getMessageWrapperObject("",loyaltyId,"","","",merchantNo,new HashMap<String, String>(0),MessageSpielChannel.ALL,IndicatorStatus.YES);

        // If there are not transactions, then show the message
        if ( transactionList == null || transactionList.isEmpty() ) {

            messageWrapper.setSpielName(MessageSpielValue.NO_TRANSACTIONS_FOUND);

            userMessagingService.transmitNotification(messageWrapper);

           // userMessagingService.sendSMS(MessageSpielValue.NO_TRANSACTIONS_FOUND,loyaltyId,new HashMap<String, String>(0));

        } else {

            // Create the list of transactions
            String txnList = "";

            // Index of the item
            int index = 0;

            // Iterate through the list and then populate the transaction data
            for( Transaction transaction : transactionList ) {

                txnList += "Date - "+generalUtils.convertDateToFormat(transaction.getTxnDate(),"dd-MMM-yyyy") + " :  Points - "+transaction.getTxnRewardQty() + "\n";

                // Check if the index is greater than 5 , then we need to break
                if ( index++ == 5 ) {

                    break;

                }
            }

            // Add to the params
            HashMap<String,String> params = new HashMap<>(0);

            // replace the txnList param
            params.put("#txnlist",txnList);

            // Send sms
            messageWrapper.setSpielName(MessageSpielValue.TRANSACTION_LIST_SMS);

            userMessagingService.transmitNotification(messageWrapper);

            //userMessagingService.sendSMS(MessageSpielValue.TRANSACTION_LIST_SMS,loyaltyId,params);

        }

    }

    @Override
    public Transaction findByTxnMerchantNoAndTxnId(Long txnMerchantNo, Long txnId) {

        // Get the transaction
        Transaction transaction = transactionRepository.findByTxnMerchantNoAndTxnId(txnMerchantNo,txnId);

        // Return the transaciton
        return transaction;

    }

    @Override
    public List<Transaction> searchTransactionByTypeAndDateRange(Long txnMerchantNo, String txnLoyaltyId, Integer txnType, Date startDate, Date endDate) {

        // Get the List
        List<Transaction> transactionList =  transactionRepository.searchTransactionByTypeAndDateRange(txnMerchantNo,txnLoyaltyId,txnType,startDate,endDate,RecordStatus.RECORD_STATUS_ACTIVE);

        // Return the list
        return transactionList;


    }

    @Override
    public Transaction saveTransaction(Transaction transaction) {

        // Save the transaction
        transaction = transactionRepository.save(transaction);

        // Trigger the transaction saved event
        analyzeService.postTransactionToAnalyze(transaction);

        // return the transaction
        return transaction;

    }

    @Override
    public boolean deleteTransaction(Long txnId) {

        // Delete the transaction
        transactionRepository.delete(txnId);

        // return true
        return true;
    }

    @Override
    public Page<Transaction> searchCustomerTransaction(Long txnMerchantNo, Date txnStartDate, Date txnEndDate, Pageable pageable) throws InspireNetzException {


         Page<Transaction> transactionPage =null;

        //get loyalty id based on user
        Long userNo =authSessionUtils.getUserNo();

        //find customer details of current user based on user number
        Customer customer =customerService.findByCusUserNoAndCusMerchantNoAndCusStatus(userNo,txnMerchantNo, CustomerStatus.ACTIVE);

        //check customer null or not
        if(customer ==null){

            log.info("Transaction Service Impl->searchCustomerTransaction: customer not a loyalty member  ");

            throw new InspireNetzException(APIErrorCode.ERR_NO_LOYALTY_ID);
        }

        //get loyalty id of the customer
        String txnLoyaltyId =customer.getCusLoyaltyId()==null?"":customer.getCusLoyaltyId();


        if(!txnLoyaltyId.equals("")){

            // Check if the txnStartDate is set or not
            // If the start date is not set, then we need to set the date to the minimum value
            if ( txnStartDate == null  ){

                // Create the calendar object
                Calendar cal = Calendar.getInstance();

                // set Date portion to January 1, 1970
                cal.set( cal.YEAR, 1970 );
                cal.set( cal.MONTH, cal.JANUARY );
                cal.set( cal.DATE, 1 );

                txnStartDate = new Date(cal.getTime().getTime());

            }

            // Check if the endDate is set, if not then we need to
            // set the date to the largest possible date
            if ( txnEndDate == null ) {

                // Create the calendar object
                Calendar cal = Calendar.getInstance();

                // set Date portion to December 31, 9999
                cal.set( cal.YEAR, 9999 );
                cal.set( cal.MONTH, cal.DECEMBER );
                cal.set( cal.DATE, 31 );

                txnEndDate = new Date(cal.getTime().getTime());

            }

            transactionPage = transactionRepository.findByTxnMerchantNoAndTxnLoyaltyIdAndTxnRecordStatusAndTxnDateBetweenOrderByTxnIdDesc(txnMerchantNo,txnLoyaltyId,RecordStatus.RECORD_STATUS_ACTIVE,txnStartDate,txnEndDate,pageable);

            return transactionPage;
        }

        return transactionPage;
    }

    @Override
    public List<Transaction> getLastTransactionCompatible(Long merchantNo, String cusLoyaltyId,Integer rowCount) {


        //get find last transaction
        List<Transaction> listTransactionList =transactionRepository.findByTxnMerchantNoAndTxnLoyaltyIdOrderByTxnIdDesc(merchantNo,cusLoyaltyId);

        //check row count
        rowCount = listTransactionList.size()>rowCount?rowCount:listTransactionList.size();

        //get subList of transaction
        if(listTransactionList !=null){


            //return subList
            return listTransactionList.subList(0,rowCount);
        }


        return listTransactionList;
    }

    @Override
    public Transaction findByTxnMerchantNoAndTxnLoyaltyIdAndTxnInternalRefAndTxnLocationAndTxnDateOrderByTxnIdDesc(Long txnMerchantNo, String txnLoyaltyId, String txnInternalRef, Long txnLocation, Date txnDate) {

        //get transaction object
        List<Transaction> transactionList=transactionRepository.findByTxnMerchantNoAndTxnLoyaltyIdAndTxnInternalRefAndTxnLocationAndTxnDateOrderByTxnIdDesc(txnMerchantNo, txnLoyaltyId, txnInternalRef, txnLocation, txnDate);

        //Check sales list null
        if(transactionList==null || transactionList.size()==0){

            // Log the response
            log.info("transactionFetchWithSale - Response : No such transaction exists");

            return null;
        }
        return transactionList.get(0);
    }

    @Override
    public List<Transaction> findByTxnLoyaltyIdAndTxnMerchantNoAndTxnPgmId(String txnLoyaltyId, Long txnMerchantNo, Long txnProgramId) {

        //get transaction object
        List<Transaction> transactionList=transactionRepository.findByTxnLoyaltyIdAndTxnMerchantNoAndTxnProgramId(txnLoyaltyId,txnMerchantNo,txnProgramId);

        //Check sales list null
        if(transactionList==null || transactionList.size()==0){

            // Log the response
            log.info("transactionFetchWithSale - Response : No such transaction exists");

            return null;
        }
        return transactionList;


    }


}
