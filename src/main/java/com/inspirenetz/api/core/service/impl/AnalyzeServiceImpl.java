package com.inspirenetz.api.core.service.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.inspirenetz.api.core.dictionary.EventReactor;
import com.inspirenetz.api.core.dictionary.EventReactorCommand;
import com.inspirenetz.api.core.domain.CardTransaction;
import com.inspirenetz.api.core.domain.MerchantLocation;
import com.inspirenetz.api.core.domain.Transaction;
import com.inspirenetz.api.core.service.AnalyzeService;
import com.inspirenetz.api.core.service.MerchantLocationService;
import com.inspirenetz.api.rest.resource.CardTransactionResource;
import com.inspirenetz.api.rest.resource.TransactionResource;
import com.microideation.app.dialogue.event.DialogueEvent;
import org.dozer.Mapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import reactor.spring.annotation.Selector;

/**
 * Created by sandheepgr on 28/7/16.
 */
@Service
public class AnalyzeServiceImpl implements AnalyzeService {

    // Create the logger
    private static Logger log = LoggerFactory.getLogger(AnalyzeServiceImpl.class);

    @Autowired
    private RabbitTemplate rabbitTemplate;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private MerchantLocationService merchantLocationService;

    @Autowired
    private Mapper mapper;

    @Value("${analyze.post}")
    private boolean isPostEnabled;

    @Value("${analyze.txn.queue}")
    private String txnAnalyzeQueue;

    @Value("${analyze.cardtxn.queue}")
    private String cardTxnAnalyzeQueue;



    /**
     * Method to post the transaction to analyze
     * @param transaction : The object to be passed
     */
    @Override
    @Async("threadPoolTaskExecutor")
    public void postTransactionToAnalyze(Transaction transaction) {

        try {

            // if the post is not enabled, then return
            if ( !isPostEnabled ) {

                // Log the info
                log.info("Posting to analyze is not enabled");

                // return
                return;

            }

            // Create the dialogEvent
            DialogueEvent dialogueEvent = new DialogueEvent();

            // Get the location by the id
            MerchantLocation merchantLocation = merchantLocationService.findByMelId(transaction.getTxnLocation());

            // Map it to the resource
            TransactionResource transactionResource = mapper.map(transaction,TransactionResource.class);

            // Set the location
            if ( merchantLocation != null) {

                transactionResource.setLocationName(merchantLocation.getMelLocation());

            } else  {

                transactionResource.setLocationName("UNKNOWN");

            }

            // Set the payload as JSON of transaction
            dialogueEvent.setPayload(objectMapper.writeValueAsString(transactionResource));

            // Send to the rabbit
            rabbitTemplate.convertAndSend(txnAnalyzeQueue,dialogueEvent);

        } catch (JsonProcessingException e) {

            // Log the error
            log.info("Error during json processing: " + e.getMessage());

            // Print the stacktrace
            e.printStackTrace();

        }

    }


    /**
     * Method to post the transaction to analyze
     * @param transaction : The object to be passed
     */
    @Override
    @Async("threadPoolTaskExecutor")
    public void postCardTransactionToAnalyze(CardTransaction transaction) {

        try {

            // if the post is not enabled, then return
            if ( !isPostEnabled ) {

                // Log the info
                log.info("Posting to analyze is not enabled");

                // return
                return;

            }

            // Create the dialogEvent
            DialogueEvent dialogueEvent = new DialogueEvent();

            // Create the CardTransactionResource
            CardTransactionResource cardTransactionResource = mapper.map(transaction,CardTransactionResource.class);

            // Get the location by the id
            MerchantLocation merchantLocation = merchantLocationService.findByMelId(transaction.getCtxLocation());

            // Set the location
            if ( merchantLocation != null) {

                cardTransactionResource.setLocation(merchantLocation.getMelLocation());

            } else  {

                cardTransactionResource.setLocation("UNKNOWN");

            }

            // Set the payload as JSON of transaction
            dialogueEvent.setPayload(objectMapper.writeValueAsString(cardTransactionResource));

            // Send to the rabbit
            rabbitTemplate.convertAndSend(cardTxnAnalyzeQueue,dialogueEvent);

        } catch (JsonProcessingException e) {

            // Log the error
            log.info("Error during json processing: " + e.getMessage());

            // Print the stacktrace
            e.printStackTrace();

        }

    }

}
