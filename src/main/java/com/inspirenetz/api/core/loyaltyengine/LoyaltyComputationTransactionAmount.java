package com.inspirenetz.api.core.loyaltyengine;

import com.inspirenetz.api.core.dictionary.*;
import com.inspirenetz.api.core.domain.CustomerProfile;
import com.inspirenetz.api.core.domain.LoyaltyProgram;
import com.inspirenetz.api.core.domain.Sale;
import com.inspirenetz.api.core.service.LoyaltyProgramService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.StringTokenizer;

/**
 * Created by sandheepgr on 23/5/14.
 */
public class LoyaltyComputationTransactionAmount implements LoyaltyComputation {

    @Override
    public boolean isProgramValidForTransaction(LoyaltyProgram loyaltyProgram, Sale sale) {

        // Check if the amount is not 0
        if ( sale.getSalAmount() == 0) {

            return false;

        }


        //setting default value as sale transaction
        if(loyaltyProgram.getPrgTxnSource() == null || loyaltyProgram.getPrgTxnSource().intValue()==0 ){

            loyaltyProgram.setPrgTxnSource(LoyaltyTransactionSource.SALES_TRANSACTION);

        }


        //Get the sale type
        Integer saleType = sale.getSalType();

        // if transaction source is sales Transaction then  sale type should be Standard purchase or ITEM_BASED_PURCHASE
        if(loyaltyProgram.getPrgTxnSource()== LoyaltyTransactionSource.SALES_TRANSACTION){

            if ((saleType.intValue() != SaleType.ITEM_BASED_PURCHASE) && (saleType.intValue() !=SaleType.STANDARD_PURCHASE )){

                return false;
            }

        // if transaction source is charge card debit then set sale type to CHARGE_CARD_DEBIT
        }else if (loyaltyProgram.getPrgTxnSource()==LoyaltyTransactionSource.CHARGE_CARD_DEBIT){

            if (saleType.intValue() !=SaleType.CHARGE_CARD_DEBIT){

                return false;
            }

        // if transaction source is charge card topup then set sale type to CHARGE_CARD_TOPUP
        }else if (loyaltyProgram.getPrgTxnSource()==LoyaltyTransactionSource.CHARGE_CARD_TOPUP){

            if (saleType.intValue() !=SaleType.CHARGE_CARD_TOPUP){

                return false;
            }
        }


        // Return true
        return true;
         
    }

    @Override
    public CustomerRewardPoint calculatePoints(LoyaltyProgram loyaltyProgram, Sale sale) {

        // Variable holding the points
        double points  =0;

        // Variable holding the ratio
        double ratio = 1;

        //create object for reward
        CustomerRewardPoint customerRewardPoint =new CustomerRewardPoint();


        // Check the rule type
        if ( loyaltyProgram.getPrgRuleType() == LoyaltyProgramRuleType.FIXED) {

            points = loyaltyProgram.getPrgFixedValue();

        } else if ( loyaltyProgram.getPrgRuleType() == LoyaltyProgramRuleType.RATIO ) {

            // Set the ratio
            ratio = loyaltyProgram.getPrgRatioNum()/loyaltyProgram.getPrgRatioDeno();

            // Calculate the points
            points = sale.getSalAmount() * ratio;

        } else if ( loyaltyProgram.getPrgRuleType() == LoyaltyProgramRuleType.TIERED_RATIO ) {

            // Get the transaction amount
            double txnAmount = sale.getSalAmount();

            // Check the applicable range
            if ( txnAmount >= loyaltyProgram.getPrgTier1LimitFrom() &&
                 txnAmount <= loyaltyProgram.getPrgTier1LimitTo() ) {

                ratio = loyaltyProgram.getPrgTier1Num()/loyaltyProgram.getPrgTier1Deno();

            } else if ( txnAmount > loyaltyProgram.getPrgTier2LimitFrom() &&
                        txnAmount <= loyaltyProgram.getPrgTier2LimitTo() ) {

                ratio = loyaltyProgram.getPrgTier2Num()/loyaltyProgram.getPrgTier2Deno();

            } else if ( txnAmount > loyaltyProgram.getPrgTier3LimitFrom() &&
                        txnAmount <= loyaltyProgram.getPrgTier3LimitTo() ) {

                ratio = loyaltyProgram.getPrgTier3Num()/loyaltyProgram.getPrgTier3Deno();

            } else if ( txnAmount > loyaltyProgram.getPrgTier4LimitFrom() &&
                        txnAmount <= loyaltyProgram.getPrgTier4LimitTo() ) {

                ratio = loyaltyProgram.getPrgTier4Num()/loyaltyProgram.getPrgTier4Deno();

            } else if ( txnAmount > loyaltyProgram.getPrgTier5LimitFrom()) {

                ratio = loyaltyProgram.getPrgTier5Num()/loyaltyProgram.getPrgTier5Deno();

            }

            // Set the points
            points = txnAmount * ratio;
        }

        //if the prg role is null or the type is zero consider as a normal customer
        if (loyaltyProgram.getPrgRole() ==null ||loyaltyProgram.getPrgRole().intValue() ==LoyaltyRefferalRoles.CUSTOMER){

            //finally set point
            customerRewardPoint.setTotalRewardPoint(points);

        }else if (loyaltyProgram.getPrgRole().intValue() ==LoyaltyRefferalRoles.REFERREE){

            customerRewardPoint.setTotalRefereePoint(points);

        }else if (loyaltyProgram.getPrgRole().intValue() ==LoyaltyRefferalRoles.REFFERRER){

            customerRewardPoint.setTotalReferrerPoint(points);

        } else if (loyaltyProgram.getPrgRole().intValue() ==LoyaltyRefferalRoles.BOTH){

            customerRewardPoint.setTotalReferrerPoint(points);

            customerRewardPoint.setTotalRefereePoint(points);

        }
        // Return the points
        return customerRewardPoint;
    }

}
