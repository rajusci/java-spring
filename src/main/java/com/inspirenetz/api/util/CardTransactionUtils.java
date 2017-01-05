package com.inspirenetz.api.util;

import com.inspirenetz.api.core.dictionary.CardMasterOperationResponse;
import com.inspirenetz.api.core.domain.*;

import java.util.HashMap;


/**
 * Created by sandheepgr on 23/7/14.
 */
public class CardTransactionUtils {


    /**
     * Function to get a CardTransaction object from the CardMaster object
     * This function will initialize the fields of CardTransaction using the values from
     * CardMaster and return the object
     *
     * @param cardMaster    - The reference object from which CardTransaction need to be created
     * @return              - The CardTransaction object created
     */
    public static CardTransaction getCardTransactionFromCardMaster(CardMaster cardMaster) {

        // Create the cardtransaction using builder 
        CardTransaction cardTransaction = new CardTransaction();


        // Set the fields
        cardTransaction.setCtxTxnTerminal(cardMaster.getCrmMerchantNo());
        cardTransaction.setCtxCrmId(cardMaster.getCrmId());
        cardTransaction.setCtxCardNumber(cardMaster.getCrmCardNo());
        cardTransaction.setCtxCardBalance(cardMaster.getCrmCardBalance() + cardMaster.getCrmPromoBalance());
        cardTransaction.setCreatedBy(cardMaster.getCreatedBy());
        cardTransaction.setUpdatedBy(cardMaster.getUpdatedBy());
        cardTransaction.setCtxLocation(cardMaster.getCrmLocation()==null?0:cardMaster.getCrmLocation());
        cardTransaction.setCtxTxnAmount(0.0);

        // If the cardMaster is not null, set the reference as the crmId
        // else set if as 0
        if ( cardMaster != null && cardMaster.getCrmId() != null ) {

            cardTransaction.setCtxReference(cardMaster.getCrmId().toString());

        } else {

            cardTransaction.setCtxReference("0");
        }



        // Return the object
        return cardTransaction;


    }


    /**
     * Function to create a response for the card operation with the common fields
     * This function creates a CardMasterOpreationResponse with balance,txnref and amount fields
     *
     * @param cardTransaction   - The CardTransaction object from which details needs to be populated
     * @return                  - The cardMasterOperationResponse with selected fields
     */
    public static CardMasterOperationResponse getCardOperationResponse(CardTransaction cardTransaction) {

        // Create the CardMasterOperationResponse
        CardMasterOperationResponse cardMasterOperationResponse = new CardMasterOperationResponse();

        // Set the balance
        cardMasterOperationResponse.setBalance(cardTransaction.getCtxCardBalance());

        // Set the transaction amount
        cardMasterOperationResponse.setAmount(cardTransaction.getCtxTxnAmount());

        // Set the refernece id
        cardMasterOperationResponse.setTxnref(cardTransaction.getCtxTxnNo().toString());

        //Set the card number
        cardMasterOperationResponse.setCardnumber(cardTransaction.getCtxCardNumber());

        cardMasterOperationResponse.setPaymentMode(cardTransaction.getCtxPaymentMode());

        // Return the object
        return cardMasterOperationResponse;
    }

    /**
     * Function to create a response for the card operation with the common fields
     * This function creates a CardMasterOpreationResponse with balance,txnref and amount fields
     *
     * @param cardTransaction   - The CardTransaction object from which details needs to be populated
     * @param cardMaster        - The CardMaster object from which details needs to be populated
     * @param merchant          -The Merchant object from which details needs to be populated
     * @param customer          -The Customer object from which details needs to be populated
     * @return                  - The cardMasterOperationResponse with selected fields
     */
    public static CardMasterOperationResponse getCardOperationResponse(CardTransaction cardTransaction,CardMaster cardMaster,Merchant merchant,Customer customer) {

        // Create the CardMasterOperationResponse
        CardMasterOperationResponse cardMasterOperationResponse = new CardMasterOperationResponse();

        // Set the transaction amount
        cardMasterOperationResponse.setAmount(cardTransaction.getCtxTxnAmount());

        // Set the refernece id
        cardMasterOperationResponse.setTxnref(cardTransaction.getCtxTxnNo().toString());

        //Set the card number
        cardMasterOperationResponse.setCardnumber(cardTransaction.getCtxCardNumber());

        if(cardMaster!=null){

            cardMasterOperationResponse.setName(cardMaster.getCrmCardHolderName());

            // Set the balance
            cardMasterOperationResponse.setBalance(cardMaster.getCrmCardBalance());

            //set the card promo balance
            cardMasterOperationResponse.setPromoBalance(cardMaster.getCrmPromoBalance());

            CardType cardType=cardMaster.getCardType();

            if(cardType!=null){

                cardMasterOperationResponse.setCardtype(cardType.getCrtName());
            }
        }

        if(merchant!=null){

            cardMasterOperationResponse.setMerchant(merchant.getMerMerchantName());
        }

        if(customer!=null){

            cardMasterOperationResponse.setCustomerIdType(customer.getCusIdType());

            cardMasterOperationResponse.setCustomerIdNo(customer.getCusIdNo());
        }

        // Return the object
        return cardMasterOperationResponse;
    }
}
