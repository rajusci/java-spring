package com.inspirenetz.api.rest.assembler;

import com.inspirenetz.api.core.dictionary.CardTransactionType;
import com.inspirenetz.api.core.domain.CardMaster;
import com.inspirenetz.api.core.domain.CardTransaction;
import com.inspirenetz.api.core.domain.Customer;
import com.inspirenetz.api.core.domain.User;
import com.inspirenetz.api.core.service.CardMasterService;
import com.inspirenetz.api.core.service.CustomerService;
import com.inspirenetz.api.core.service.UserService;
import com.inspirenetz.api.rest.controller.TransactionController;
import com.inspirenetz.api.rest.resource.TransactionCompatibleResource;
import org.dozer.Mapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.mvc.ResourceAssemblerSupport;
import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by ameen on 28/2/15.
 */
@Component
public class TransactionCompatibleAssembler extends ResourceAssemblerSupport<CardTransaction,TransactionCompatibleResource> {

    @Autowired
    private Mapper mapper;

    @Autowired
    private UserService userService;

    @Autowired
    private CustomerService customerService;

    @Autowired
    private CardMasterService cardMasterService;

    public TransactionCompatibleAssembler() {

        super(TransactionController.class,TransactionCompatibleResource.class);

    }

    @Override
    public TransactionCompatibleResource toResource(CardTransaction cardTransaction) {
        // Create the RedemptionVoucherSourceResource
        TransactionCompatibleResource transactionCompatibleResource = mapper.map(cardTransaction,TransactionCompatibleResource.class);

        // Return the redemptionVoucherSourceResource
        return transactionCompatibleResource;
    }

    /**
     * Function to convert the list of CardTransaction objects into an equivalent list
     * of TransactionCompatibleResource objects
     *
     * @param cardTransactionList - The List object for the CardTransaction objects
     * @return cardTransactionResourceList - This list of TransactionCompatibleResource objects
     */
    public List<TransactionCompatibleResource> toResources(List<CardTransaction> cardTransactionList) {



        // Create the list that will hold the resources
        List<TransactionCompatibleResource> cardTransactionResourceList = new ArrayList<TransactionCompatibleResource>();

        // Create the TransactionCompatibleResource object
        TransactionCompatibleResource cardTransactionResource = null;


        // Go through the cardTransactions and then create the cardTransaction resource
        for(CardTransaction cardTransaction : cardTransactionList ) {

            // Get the TransactionCompatibleResource
            cardTransactionResource =new TransactionCompatibleResource();

            String txnDate=new SimpleDateFormat("MM/dd/yyyy HH:mm:ss").format(cardTransaction.getCtxTxnTimestamp());

            cardTransactionResource.setTxn_date(txnDate);

            if(cardTransaction.getCtxTxnType() == CardTransactionType.DEBIT){

                cardTransactionResource.setTxn_type("Debit");

            }else if(cardTransaction.getCtxTxnType() ==CardTransactionType.ISSUE){

                cardTransactionResource.setTxn_type("Issue");

            }else  if(cardTransaction.getCtxTxnType() ==CardTransactionType.LOCK){

                cardTransactionResource.setTxn_type("Lock");

            }else if(cardTransaction.getCtxTxnType() ==CardTransactionType.PIN_CHANGE){

                cardTransactionResource.setTxn_type("Pin Change");

            }else if(cardTransaction.getCtxTxnType() ==CardTransactionType.REFUND){

                cardTransactionResource.setTxn_type("Refund");

            }else if(cardTransaction.getCtxTxnType() ==CardTransactionType.TOPUP){

                cardTransactionResource.setTxn_type("Topup");

            }else if(cardTransaction.getCtxTxnType() ==CardTransactionType.UNLOCK){

                cardTransactionResource.setTxn_type("Unlock");

            }else  if(cardTransaction.getCtxTxnType() == CardTransactionType.TRANSFER_FROM){

                cardTransactionResource.setTxn_type("Transfer From");

            }else  if(cardTransaction.getCtxTxnType() == CardTransactionType.TRANSFER_TO){

                cardTransactionResource.setTxn_type("TRANSFER_TO");

            }

            cardTransactionResource.setTxn_balance(cardTransaction.getCtxCardBalance());

            cardTransactionResource.setTxn_amount(cardTransaction.getCtxTxnAmount());

            cardTransactionResource.setTxn_mer_reference(cardTransaction.getCtxReference());

            cardTransactionResource.setTxn_card_number(cardTransaction.getCtxCardNumber());

            cardTransactionResource.setTxn_no(cardTransaction.getCtxTxnNo());

            try{

                User user=userService.findByUsrUserNo(cardTransaction.getCtxUserNo());

                if(user!=null){

                    cardTransactionResource.setTxn_user(user.getUsrLoginId());

                }else{

                    cardTransactionResource.setTxn_user("");

                }
            }catch(Exception ex){

                cardTransactionResource.setTxn_user("");

            }

            CardMaster cardMaster=cardMasterService.findByCrmCardNo(cardTransaction.getCtxCardNumber());

            if(cardMaster!=null){

                cardTransactionResource.setTxn_card_holder(cardMaster.getCrmCardHolderName());

                if(cardMaster.getCrmLoyaltyId()!=null && !cardMaster.getCrmLoyaltyId().equals("")){

                    Customer customer=customerService.findByCusLoyaltyIdAndCusMerchantNo(cardMaster.getCrmLoyaltyId(),cardMaster.getCrmMerchantNo());

                    if(customer!=null){

                        cardTransactionResource.setTxn_Id_Type(customer.getCusIdType());

                        cardTransactionResource.setTxn_Id_No(customer.getCusIdNo());
                    }

                }
            }


            // Add the resource to the array list
            cardTransactionResourceList.add(cardTransactionResource);

        }


        // return the cardTransactionResoueceList
        return cardTransactionResourceList;

    }
}
