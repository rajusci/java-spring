package com.inspirenetz.api.core.service.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.inspirenetz.api.core.dictionary.APIErrorCode;
import com.inspirenetz.api.core.dictionary.CardTransactionReport;
import com.inspirenetz.api.core.dictionary.CardTransactionType;
import com.inspirenetz.api.core.dictionary.EventReactorCommand;
import com.inspirenetz.api.core.domain.CardMaster;
import com.inspirenetz.api.core.domain.CardTransaction;
import com.inspirenetz.api.core.domain.Transaction;
import com.inspirenetz.api.core.repository.BaseRepository;
import com.inspirenetz.api.core.repository.CardTransactionRepository;
import com.inspirenetz.api.core.service.AnalyzeService;
import com.inspirenetz.api.core.service.CardMasterService;
import com.inspirenetz.api.core.service.CardTransactionService;
import com.inspirenetz.api.rest.exception.InspireNetzException;
import com.inspirenetz.api.util.DBUtils;
import com.microideation.app.dialogue.event.DialogueEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import reactor.core.Reactor;
import reactor.event.Event;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by sandheepgr on 28/3/14.
 */
@Service
public class CardTransactionServiceImpl extends BaseServiceImpl<CardTransaction> implements CardTransactionService {


    private static Logger log = LoggerFactory.getLogger(CardTransactionServiceImpl.class);


    @Autowired
    CardTransactionRepository cardTransactionRepository;

    @Autowired
    CardMasterService cardMasterService;

    @Autowired
    private AnalyzeService analyzeService;


    public CardTransactionServiceImpl() {

        super(CardTransaction.class);

    }


    @Override
    protected BaseRepository<CardTransaction,Long> getDao() {
        return cardTransactionRepository;
    }

    @Override
    public Page<CardTransaction> findByCtxTxnTerminal(Long ctxTxnTerminal, Pageable pageable) {

        // Get the CardTransaction page
        Page<CardTransaction> cardTransactionPage = cardTransactionRepository.findByCtxTxnTerminalOrderByCtxTxnNoDesc(ctxTxnTerminal, pageable);

        // Return the CardTransactionPage
        return cardTransactionPage;

    }

    @Override
    public Page<CardTransaction> findByCtxTxnTerminalAndCtxCrmId(Long ctxTxnTerminal, Long ctxCrmId, Pageable pageable) {

        // Get the CardTransactionPage
        Page<CardTransaction> cardTransactionPage = cardTransactionRepository.findByCtxTxnTerminalAndCtxCrmIdOrderByCtxTxnNoDesc(ctxTxnTerminal, ctxCrmId, pageable);

        // Return the cardTransactionPage
        return cardTransactionPage;

    }

    @Override
    public Page<CardTransaction> findByCtxTxnTerminalAndCtxCardNumber(Long ctxTxnTerminal, String ctxCardNumber, Pageable pageable) {

        // Get the CardTransactonPage
        Page<CardTransaction> cardTransactionPage = cardTransactionRepository.findByCtxTxnTerminalAndCtxCardNumberOrderByCtxTxnNoDesc(ctxTxnTerminal, ctxCardNumber, pageable);

        // Return the cardTransactionPage
        return cardTransactionPage;

    }

    @Override
    public CardTransaction findByCtxTxnNo(Long ctxTxnNo) {

        // Get the CardTransaction object
        CardTransaction cardTransaction = cardTransactionRepository.findByCtxTxnNo(ctxTxnNo);

        // Return the CardTransaction
        return cardTransaction;

    }

    @Override
    public Page<CardTransaction> searchCardTransactions(Long ctxTxnTerminal, String ctxCardNumber, Integer ctxTxnType, Timestamp startTimestamp, Timestamp endTimestamp, Pageable pageable) {

        // Check if the startTimestamp is set or not
        // If the start timestamp is not set, then we need to set the date to the minimum value
        if ( startTimestamp == null ){

           // Set the startTimestamp to an early value
            startTimestamp = DBUtils.convertToSqlTimestamp("1970-01-01 00:00:00");

        }


        // Check if the endTimestamp is set, if not then we need to
        // set the timestamp to the largest possible date
        if ( endTimestamp == null ) {

            // Set the end time stamp
            endTimestamp = DBUtils.convertToSqlTimestamp("9999-12-31 00:00:00");

        }

        // Get the CardTransctionPage
        Page<CardTransaction> cardTransactionPage = cardTransactionRepository.searchCardTransactions(ctxTxnTerminal,ctxCardNumber,ctxTxnType,startTimestamp,endTimestamp,pageable);

        // Return the pageable object
        return cardTransactionPage;

    }

    @Override
    public boolean isDuplicateCardTransactionExisting(CardTransaction cardTransaction) {

        // Get the transactions matching the given ctxTerminal, ctxCrmId, ctxCardNumber , ctxAmount and ctxReference
        List<CardTransaction> cardTransactionList = cardTransactionRepository.searchDuplicateTransactions(cardTransaction.getCtxTxnTerminal(),cardTransaction.getCtxCrmId(),cardTransaction.getCtxCardNumber(),cardTransaction.getCtxTxnType(),cardTransaction.getCtxTxnAmount(),cardTransaction.getCtxReference(),cardTransaction.getCtxLocation());

        // check if the list is null or contains data
        // If the list has got data, then there are duplicate transactions
        if ( cardTransactionList != null || !cardTransactionList.isEmpty() ) {

            return true;

        } else {

            return false;

        }
    }

    @Override
    public CardTransaction saveCardTransaction(CardTransaction cardTransaction ){

        // Get the object
        cardTransaction = cardTransactionRepository.save(cardTransaction);

        // Post to analyze
        // Trigger the card transaction saved event
        analyzeService.postCardTransactionToAnalyze(cardTransaction);

        // Save the cardTransaction
        return cardTransaction;

    }

    @Override
    public boolean deleteCardTransaction(Long ctxTxnNo) {

        // Delete the cardTransaction
        cardTransactionRepository.delete(ctxTxnNo);

        // return true
        return true;

    }

    @Override
    public Page<CardTransaction> getCardTransactionList(String filter, String query, Timestamp startTimestamp, Timestamp endTimestamp, Integer txnType, Long merchantNo,Pageable pageable) {

        Page<CardTransaction> cardTransactionPage = null ;

        List<CardTransaction> cardTransactionList = new ArrayList<>();

        // Check the filter type
        if ( filter.equals("0") && query.equals("0") ) {

            // Get the page
            cardTransactionPage= findByCtxTxnTerminal(merchantNo, pageable);

        } else if ( filter.equalsIgnoreCase("cardnumber") ) {

            // Get the page
            cardTransactionPage = searchCardTransactions(merchantNo,query,txnType,startTimestamp,endTimestamp,pageable);

        }

        return  cardTransactionPage;
    }

    @Override
    public CardTransaction getCardTransactionInfo(Long ctxTxnNo,Long merchantNo) throws InspireNetzException {

        // Get the cardTransaction information
        CardTransaction cardTransaction = findByCtxTxnNo(ctxTxnNo);

        // Check if the cardTransaction is found
        if ( cardTransaction == null || cardTransaction.getCtxTxnNo() == null) {

            // Log the response
            log.info("getCardTransactionInfo - Response : No cardTransaction information found");

            // Thrown not found exception
            throw new InspireNetzException(APIErrorCode.ERR_NO_DATA_FOUND);

        }


        // Check if the passed object's merchant number and the current
        // authenticated merchant number are same.
        // We need to raise an error if not
        if ( cardTransaction.getCtxTxnTerminal().longValue() != merchantNo ) {

            // Log the response
            log.info("getCardTransactionInfo - Response : You are not authorized to view the cardTransaction");

            // Throw InspireNetzException with ERR_NOT_AUTHORIZED as error
            throw new InspireNetzException(APIErrorCode.ERR_NOT_AUTHORIZED);

        }

        return  cardTransaction;

    }

    @Override
    public List<CardTransaction> getCardTransactionCompatible(String crmCardNo, Long merchantNo,Integer maxItem) {

        //set card transaction object is null
        List<CardTransaction> cardTransaction =null;

        //get customer information based on cusLoyaltyId
        CardMaster cardMaster=cardMasterService.findByCrmMerchantNoAndCrmCardNo(merchantNo,crmCardNo);

        if(cardMaster ==null){

            log.info("getCardTransactionCompatible :Inavalid Card number"+cardMaster);

            return cardTransaction;

        }

        //find transaction card transaction
        cardTransaction =cardTransactionRepository.findByCtxCardNumberOrderByCtxTxnNoDesc(cardMaster.getCrmCardNo());

        //check card transaction List is null if null return otherwise take subList and return
        if(cardTransaction ==null){

            return cardTransaction;
        }

        //get the subList for cardTransaction List
        Integer maximumContent =cardTransaction.size()>maxItem?maxItem:cardTransaction.size();

        //return subList
        return cardTransaction.subList(0,maximumContent);

    }

    @Override
    public Double countTxnAmount(Long ctxTxnTerminal, String ctxCardNumber, Integer ctxTxnType) {
        return cardTransactionRepository.countTxnAmount(ctxTxnTerminal,ctxCardNumber,ctxTxnType);
    }

    @Override
    public CardTransactionReport getCardTransactionReport(Long ctxTxnTerminal, String ctxCardNumber) {

        CardTransactionReport cardTransactionReport =new CardTransactionReport();

        //pick all amount balance and put into map
        Double totalTopupAmount =countTxnAmount(ctxTxnTerminal, ctxCardNumber, CardTransactionType.TOPUP);

        Double totalDebitAmount =countTxnAmount(ctxTxnTerminal, ctxCardNumber, CardTransactionType.DEBIT);

        Double totalRefundAmount =countTxnAmount(ctxTxnTerminal, ctxCardNumber, CardTransactionType.REFUND);

        Double totalPromoAmount =countTxnAmount(ctxTxnTerminal,ctxCardNumber,CardTransactionType.PROMO_TOPUP);

        //set value
        cardTransactionReport.setTotalDebitAmount(totalDebitAmount==null?0.0:totalDebitAmount);
        cardTransactionReport.setTotalRefundAmount(totalRefundAmount ==null?0.0:totalRefundAmount);
        cardTransactionReport.setTotalTopupAmount(totalTopupAmount==null?0.0:totalTopupAmount);
        cardTransactionReport.setTotalPromoAmount(totalPromoAmount==null?0.0:totalPromoAmount);


        return cardTransactionReport;
    }



}
