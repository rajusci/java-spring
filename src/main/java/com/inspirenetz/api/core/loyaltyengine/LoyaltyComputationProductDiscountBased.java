package com.inspirenetz.api.core.loyaltyengine;

import com.inspirenetz.api.core.dictionary.CustomerRewardPoint;
import com.inspirenetz.api.core.dictionary.LoyaltyProgramDriver;
import com.inspirenetz.api.core.dictionary.LoyaltyProgramRuleType;
import com.inspirenetz.api.core.dictionary.SaleType;
import com.inspirenetz.api.core.domain.Customer;
import com.inspirenetz.api.core.domain.LoyaltyProgram;
import com.inspirenetz.api.core.domain.Sale;
import com.inspirenetz.api.core.domain.SaleSKU;
import com.inspirenetz.api.core.service.SaleSKUService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

/**
 * Created by sandheepgr on 23/5/14.
 */
public class LoyaltyComputationProductDiscountBased implements LoyaltyComputation {

    // Set the logger
    private static Logger log = LoggerFactory.getLogger(LoyaltyComputationProductBased.class);

    // The SaleSkuService object
    private SaleSKUService saleSKUService;


    public LoyaltyComputationProductDiscountBased(SaleSKUService saleSKUService) {

        this.saleSKUService = saleSKUService;

    }

    @Override
    public boolean isProgramValidForTransaction(LoyaltyProgram loyaltyProgram, Sale sale) {

        // Check if the amount is not 0
        if ( sale.getSalAmount() == 0) {

            // log the informaton
            log.info("LoyaltyEngine -> LoyaltyComputationProductDiscountBased -> isProgramValidForTransaction : Sale amount cannot be 0");

            // return false
            return false;

        }


        // check if the sale type is sku level, if not return false
        if ( sale.getSalType() != SaleType.ITEM_BASED_PURCHASE) {

            // log the informaton
            log.info("LoyaltyEngine -> LoyaltyComputationProductDiscountBased -> isProgramValidForTransaction : Purhcase is not sku type");

            // return false
            return false;

        }


        // Check if the loyalty program is product based driver
        if ( loyaltyProgram.getPrgProgramDriver() != LoyaltyProgramDriver.PRODUCT_DISCOUNT_BASED ) {

            // log the informaton
            log.info("LoyaltyEngine -> LoyaltyComputationProductDiscountBased -> isProgramValidForTransaction : Loyalty Program is not sku based");

            // return false
            return false;

        }


        // Make sure that the rule type is tiered ratio, nothing else is allowed for this program driver
        if ( loyaltyProgram.getPrgRuleType() != LoyaltyProgramRuleType.TIERED_RATIO ) {

            // log the informaton
            log.info("LoyaltyEngine -> LoyaltyComputationProductDiscountBased -> isProgramValidForTransaction : Rule type is not tiered");

            // return false
            return false;

        }

        // Return true
        return true;
         
    }

    @Override
    public CustomerRewardPoint calculatePoints(LoyaltyProgram loyaltyProgram, Sale sale) {

        // Get the list of items for the sku
        List<SaleSKU> saleSKUList = saleSKUService.findBySsuSaleId(sale.getSalId());

        //create object for customer reward point
        CustomerRewardPoint customerRewardPoint =new CustomerRewardPoint();

        // Variable holding total points
        double totalPoints = 0;

        // Variable holding the ratio
        double ratio  = 0;

        // Variable holding the point for the current product
        double productPoints = 0 ;



        // If there is no data, then return
        if ( saleSKUList == null || saleSKUList.isEmpty()) {

            // log the information
            log.info("LoyaltyEngine -> LoyaltyComputationProductDiscountBased -> calculatePoints : Sale does not have any sku item");

            // Return 0
            return null;

        }


        // Go through each of the sku item
        for( SaleSKU saleSKU : saleSKUList ) {

            // Get the discount percent
            Double discountPercent = saleSKU.getSsuDiscountPercent();
            
            // If the discountPercent is null, then we need to set it to 0
            if ( discountPercent == null ) {
                
                discountPercent = 0.0 ;
                
            }

            // Check the applicable range
            if ( discountPercent >= loyaltyProgram.getPrgTier1LimitFrom() &&
                 discountPercent <= loyaltyProgram.getPrgTier1LimitTo() ) {

                ratio = loyaltyProgram.getPrgTier1Num()/loyaltyProgram.getPrgTier1Deno();

            } else if ( discountPercent > loyaltyProgram.getPrgTier2LimitFrom() &&
                        discountPercent <= loyaltyProgram.getPrgTier2LimitTo() ) {

                ratio = loyaltyProgram.getPrgTier2Num()/loyaltyProgram.getPrgTier2Deno();

            } else if ( discountPercent > loyaltyProgram.getPrgTier3LimitFrom() &&
                        discountPercent <= loyaltyProgram.getPrgTier3LimitTo() ) {

                ratio = loyaltyProgram.getPrgTier3Num()/loyaltyProgram.getPrgTier3Deno();

            } else if ( discountPercent > loyaltyProgram.getPrgTier4LimitFrom() &&
                        discountPercent <= loyaltyProgram.getPrgTier4LimitTo() ) {

                ratio = loyaltyProgram.getPrgTier4Num()/loyaltyProgram.getPrgTier4Deno();

            } else if ( discountPercent > loyaltyProgram.getPrgTier5LimitFrom()) {

                ratio = loyaltyProgram.getPrgTier5Num()/loyaltyProgram.getPrgTier5Deno();

            }


            // Calculate the points
            productPoints = saleSKU.getSsuPrice() * saleSKU.getSsuQty() * ratio;

            // Add the product points to the totalPoints
            totalPoints += productPoints;

            // Update the point fields in the saleSKu
            saleSKU.setSsuPoints(productPoints);

            saleSKU.setSsuRatio(ratio);


            // Log the information
            log.info("LoyaltyEngine -> LoyaltyComputationProductBased -> calculatePoints : SKU Level awarding for : " + saleSKU.toString());

        }


        // SAve all the saleSKU items
        saleSKUService.saveAll(saleSKUList);

        //set total point
        customerRewardPoint.setTotalRewardPoint(totalPoints);

        // Return the totalPoints
        return customerRewardPoint;
    }

}
