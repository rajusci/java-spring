package com.inspirenetz.api.rest.assembler;

import com.inspirenetz.api.core.dictionary.TransactionType;
import com.inspirenetz.api.core.domain.Transaction;
import com.inspirenetz.api.core.domain.User;
import com.inspirenetz.api.core.service.UserService;
import com.inspirenetz.api.rest.controller.TransactionController;
import com.inspirenetz.api.rest.resource.LoyaltyTransactionCompatibleResource;
import org.dozer.Mapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.mvc.ResourceAssemblerSupport;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ameen on 1/3/15.
 */
@Component
public class LoyaltyTransactionCompatibleAssembler extends ResourceAssemblerSupport<Transaction,LoyaltyTransactionCompatibleResource> {

    @Autowired
    private Mapper mapper;


    @Autowired
    private UserService userService;

    public LoyaltyTransactionCompatibleAssembler() {

        super(TransactionController.class,LoyaltyTransactionCompatibleResource.class);

    }

    @Override
    public LoyaltyTransactionCompatibleResource toResource(Transaction transaction) {

        LoyaltyTransactionCompatibleResource loyaltyTransactionCompatibleResource =new LoyaltyTransactionCompatibleResource();

        loyaltyTransactionCompatibleResource =mapper.map(transaction,LoyaltyTransactionCompatibleResource.class);

        return loyaltyTransactionCompatibleResource;
    }


    public List<LoyaltyTransactionCompatibleResource> toResources(List<Transaction> transactionList) {

        // Create the list that will hold the resources
        List<LoyaltyTransactionCompatibleResource> loyaltyTransactionCompatibleResourceList = new ArrayList<LoyaltyTransactionCompatibleResource>();

        // Create the CouponResource object
        LoyaltyTransactionCompatibleResource loyaltyTransactionCompatibleResource=null;


        // Go through the coupons and then create the coupon resource
        for(Transaction transaction : transactionList) {

            //write mapping source
            loyaltyTransactionCompatibleResource =new LoyaltyTransactionCompatibleResource();

            String transactionType =getTransactionType(transaction);

            loyaltyTransactionCompatibleResource.setTxn_type(transactionType);

            loyaltyTransactionCompatibleResource.setTxn_date(transaction.getTxnDate());

            loyaltyTransactionCompatibleResource.setTxn_amount(transaction.getTxnAmount());

            loyaltyTransactionCompatibleResource.setTxn_rwd_qty(transaction.getTxnRewardQty());

            try{

                User user=userService.findByUsrUserNo(Long.parseLong(transaction.getCreatedBy()));

                if(user!=null){

                    loyaltyTransactionCompatibleResource.setTxn_user(user.getUsrLoginId());

                }else{

                    loyaltyTransactionCompatibleResource.setTxn_user("");

                }
            }catch(Exception ex){

                loyaltyTransactionCompatibleResource.setTxn_user("");

            }

            loyaltyTransactionCompatibleResourceList.add(loyaltyTransactionCompatibleResource);

        }

        // return the couponResoueceList
        return loyaltyTransactionCompatibleResourceList;

    }

    private String getTransactionType(Transaction transaction) {

        if(transaction.getTxnType() ==TransactionType.ACCOUNT_DEACTIVATION){

            return "AccountDeactivation";

        }else if(transaction.getTxnType() ==TransactionType.BUY_POINTS){

            return "Buy Points";

        }else if(transaction.getTxnType() ==TransactionType.CASHBACK){

            return "Cash Back";

        }else if(transaction.getTxnType() ==TransactionType.EXPIRATION){

            return "Expiration";

        }else if(transaction.getTxnType() ==TransactionType.LINKING_TRANSFER_FROM){

            return "Linking Transfer From";

        }else if(transaction.getTxnType() ==TransactionType.PURCHASE){

            return "Purchase";

        }else if(transaction.getTxnType() ==TransactionType.POINT_REVERSAL){

            return "Point Reversal";

        }else if(transaction.getTxnType() ==TransactionType.REDEEM){

            return "Redeem";

        }else if(transaction.getTxnType() ==TransactionType.TRANSFER_POINT_TO){

            return "Transfer Point To";

        }else if(transaction.getTxnType() ==TransactionType.TRANSFER_POINT_FROM){

            return "Transfer Point From";

        } else if(transaction.getTxnType() ==TransactionType.LINKING_TRANSFER_TO){


            return "Linking Transfer To";

        }else if(transaction.getTxnType() == TransactionType.REWARD_ADUJUSTMENT_AWARDING){


            return "Reward Adjustment Awarding";

        }else if(transaction.getTxnType() ==TransactionType.REWARD_ADJUSTMENT_DEDUCTING){


            return "Reward Adjustment Deducting";
        }else if(transaction.getTxnType() ==TransactionType.TRANSFER_ACCOUNT_FROM){

            return "Transfer Account From";

        }else if(transaction.getTxnType() ==TransactionType.TRANSFER_ACCOUNT_TO){


            return "Transfer Account To";
        }

        return "";
    }
}
