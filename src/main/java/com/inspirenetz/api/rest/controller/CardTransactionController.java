package com.inspirenetz.api.rest.controller;

import com.inspirenetz.api.core.auth.AuthSession;
import com.inspirenetz.api.core.dictionary.APIErrorCode;
import com.inspirenetz.api.core.dictionary.APIResponseStatus;
import com.inspirenetz.api.core.dictionary.CardTransactionReport;
import com.inspirenetz.api.core.dictionary.CardTransactionType;
import com.inspirenetz.api.core.domain.CardTransaction;
import com.inspirenetz.api.core.domain.Merchant;
import com.inspirenetz.api.core.domain.MerchantLocation;
import com.inspirenetz.api.core.service.CardTransactionService;
import com.inspirenetz.api.core.service.MerchantLocationService;
import com.inspirenetz.api.core.service.MerchantService;
import com.inspirenetz.api.rest.assembler.CardTransactionAssembler;
import com.inspirenetz.api.rest.assembler.CardTransactionReportAssembler;
import com.inspirenetz.api.rest.exception.InspireNetzException;
import com.inspirenetz.api.rest.resource.CardTransactionReportResource;
import com.inspirenetz.api.rest.resource.CardTransactionResource;
import com.inspirenetz.api.util.APIResponseObject;
import com.inspirenetz.api.util.APIResponseObjectFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.sql.Date;
import java.sql.Time;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by sandheepgr on 16/2/14.
 *
 */
@Controller
public class CardTransactionController {



    // Create the logger instance
    private static Logger log = LoggerFactory.getLogger(CardTransactionController.class);

    @Autowired
    private CardTransactionService cardTransactionService;

    @Autowired
    private CardTransactionAssembler cardTransactionAssembler;

    @Autowired
    private MerchantService merchantService;

    @Autowired
    private MerchantLocationService merchantLocationService;

    @Autowired
    private CardTransactionReportAssembler cardTransactionReportAssembler;



    @RequestMapping(value = "/api/0.9/json/merchant/storedvaluecard/cardtransactions/{filter}/{query}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public APIResponseObject listCardTransactions(  @PathVariable(value ="filter") String filter,
                                                    @PathVariable(value ="query") String query,
                                                    @RequestParam(value="startTimestamp",required = false) Timestamp startTimestamp,
                                                    @RequestParam(value="endTimestamp",required = false) Timestamp endTimestamp,
                                                    @RequestParam(value="txnType",required = false,defaultValue = "0") Integer txnType,
                                                    Pageable pageable){


        // Log the Request
        log.info("listCardTransactions - Request Received# filter "+ filter +" query :" +query );
        log.info("listCardTransactions - Requested User  # Login Id : "+AuthSession.getUserLoginId() + " - User No: "+AuthSession.getUserNo() + " - IP Address : " + AuthSession.getUserIpAddress());


        // Create the APIResponseObject
        APIResponseObject retData = APIResponseObjectFactory.getJSONAPIResponseObject();

        // Get the merchant number and store
        Long merchantNo = AuthSession.getMerchantNo();

        // Variable holding the cardTransaction
        List<CardTransactionResource> cardTransactionResourceList = new ArrayList<>(0);

        //get the transaction list
        Page<CardTransaction> cardTransactionPage = cardTransactionService.getCardTransactionList(filter,query,startTimestamp,endTimestamp,txnType,merchantNo,pageable);

        if ( filter.equalsIgnoreCase("cardnumber") && txnType.intValue()==0){

            //get sum of item
            CardTransactionReport cardTransactionReport =cardTransactionService.getCardTransactionReport(merchantNo,query);

            CardTransactionReportResource  cardTransactionReportResource=cardTransactionReportAssembler.toResource(cardTransactionReport);

            //set the value in return data
            retData.setBalance(cardTransactionReportResource);
        }

        // Convert to Resource
        cardTransactionResourceList = cardTransactionAssembler.toResources(cardTransactionPage);

        // Set the pageable params for the retData
        retData.setPageableParams(cardTransactionPage);

        // Set the data
        retData.setData(cardTransactionResourceList);


        // Log the response
        log.info("listCardTransactions - Response : " + retData.toString());


        // Return the success object
        return retData;


    }


    @RequestMapping(value = "/api/0.9/json/merchant/storedvaluecard/cardtransaction/{ctxTxnNo}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public APIResponseObject getCardTransactionInfo(@PathVariable(value="ctxTxnNo") Long ctxTxnNo) throws InspireNetzException {

        // Create the APIResponseObject
        APIResponseObject retData = APIResponseObjectFactory.getJSONAPIResponseObject();

        // Get the merchant number and store
        Long merchantNo = AuthSession.getMerchantNo();

        // Log the Request
        log.info("getCardTransactionInfo - Request Received# CardTransaction ID: "+ctxTxnNo);
        log.info("getCardTransactionInfo - Requested User  # Login Id : "+AuthSession.getUserLoginId() + " - User No: "+AuthSession.getUserNo() + " - IP Address : " + AuthSession.getUserIpAddress());

        //get the card transaction info
        CardTransaction cardTransaction = cardTransactionService.getCardTransactionInfo(ctxTxnNo,merchantNo);

        // Convert the CardTransaction to CardTransactionResource
        CardTransactionResource cardTransactionResource = cardTransactionAssembler.toResource(cardTransaction);

        // Check if the merchantNo is not null
        if ( cardTransaction.getCtxTxnTerminal() != null && cardTransaction.getCtxTxnTerminal() != 0L ) {

            // Get the merchant information
            Merchant merchant = merchantService.findByMerMerchantNo(cardTransaction.getCtxTxnTerminal());

            // If the merchant is not null, set the merchant name
            if ( merchant != null ) {

                cardTransactionResource.setMerchantName(merchant.getMerMerchantName());

            }

        }


        // Check if the location is set
        if ( cardTransaction.getCtxLocation() != null && cardTransaction.getCtxLocation() != 0L ) {

            // Get the MerchantLocation information
            MerchantLocation merchantLocation = merchantLocationService.findByMelId(cardTransaction.getCtxLocation());

            // If the merchantLocation is not null, the set the location name
            if ( merchantLocation != null ) {

                cardTransactionResource.setLocation(merchantLocation.getMelLocation());

            }

        }

        // Set the retData to success
        retData.setStatus(APIResponseStatus.success);

        // Set the data to the cardTransactionResource object
        retData.setData(cardTransactionResource);




        // Log the response
        log.info("getCardTransactionInfo - Response : " + retData.toString());

        // Return the retdata object
        return retData;


    }

}
