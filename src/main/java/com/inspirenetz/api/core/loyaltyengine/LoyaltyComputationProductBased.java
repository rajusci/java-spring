package com.inspirenetz.api.core.loyaltyengine;

import com.inspirenetz.api.core.dictionary.*;
import com.inspirenetz.api.core.domain.*;
import com.inspirenetz.api.core.service.*;
import com.inspirenetz.api.util.AccountBundlingUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

/**
 * Created by sandheepgr on 23/5/14.
 */
public class LoyaltyComputationProductBased implements LoyaltyComputation {



    // Create the logger
    private static Logger log = LoggerFactory.getLogger(LoyaltyComputationProductBased.class);

    // The LoyaltyprogramSku service
    private LoyaltyProgramSkuService loyaltyProgramSkuService;

    // The SaleSkuService
    private SaleSKUService saleSKUService;

    // The Product Service
    private ProductService productService;

    // The CustomerService
    private CustomerService customerService;

    // The CustomerSubscriptionService
    private CustomerSubscriptionService customerSubscriptionService;


    // Account bundling utils
    private AccountBundlingUtils accountBundlingUtils;



    public LoyaltyComputationProductBased(LoyaltyProgramSkuService loyaltyProgramSkuService, SaleSKUService saleSKUService, ProductService productService, CustomerService customerService, AccountBundlingUtils accountBundlingUtils,
                                          CustomerSubscriptionService customerSubscriptionService) {

        this.loyaltyProgramSkuService = loyaltyProgramSkuService;
        this.saleSKUService = saleSKUService;
        this.productService = productService;
        this.customerService = customerService;
        this.accountBundlingUtils = accountBundlingUtils;
        this.customerSubscriptionService = customerSubscriptionService;

    }

    @Override
    public boolean isProgramValidForTransaction(LoyaltyProgram loyaltyProgram, Sale sale) {


        // Check if the amount is not 0
        if ( sale.getSalAmount() == 0) {

            // log the informaton
            log.info("LoyaltyEngine -> LoyaltyComputationProductBased -> isProgramValidForTransaction : Sale amount cannot be 0");

            // return false
            return false;

        }


        // check if the sale type is sku level, if not return false
        if ( sale.getSalType() != SaleType.ITEM_BASED_PURCHASE) {

            // log the informaton
            log.info("LoyaltyEngine -> LoyaltyComputationProductBased -> isProgramValidForTransaction : Purhcase is not sku type");

            // return false
            return false;

        }


        // Check if the loyalty program is product based driver
        if ( loyaltyProgram.getPrgProgramDriver() != LoyaltyProgramDriver.PRODUCT_BASED ) {

            // log the informaton
            log.info("LoyaltyEngine -> LoyaltyComputationProductBased -> isProgramValidForTransaction : Loyalty Program is not sku based");

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

        //customer Reward data
        CustomerRewardPoint customerRewardPoint =new CustomerRewardPoint();

        // Variable holding total points
        double totalPoints = 0;

        // Variable holding the ratio
        double ratio  = 0;

        // Variable holding the point for the current product
        double productPoints = 0 ;

        //get the total referral point
        double totalReferrerPoint =0;

        //get the total Referee Point
        double totalRefereePoint =0;


        // If there is no data, then return
        if ( saleSKUList == null || saleSKUList.isEmpty()) {

            // log the information
            log.info("LoyaltyEngine -> LoyaltyComputationProductBased -> calculatePoints : Sale does not have any sku item");

            // Return 0
            return null;

        }


        // Get the customer information
        Customer customer = customerService.findByCusLoyaltyIdAndCusMerchantNo(sale.getSalLoyaltyId(),sale.getSalMerchantNo());

        // If the customer is not available then, return
        if ( customer == null ) {

            // log the information
            log.info("LoyaltyEngine -> LoyaltyComputationProductBased -> calculatePoints : Customer not found");

            // Return 0
            return null;

        }


        // Get the effective tier for the customer
        // Get the effective tier for the customer
        Tier cusTier = accountBundlingUtils.getEffectiveTierForCustomer(customer);

        // If the tier is null, then log error and return
        if ( cusTier == null ) {

            // Log the information
            log.info("LoyaltyEngine -> LoyaltyComputationProductBased -> calculatePoints : Customer does not have an effective tier");

            // return 0.0
            return null;

        }


        // Go through each of the sku item
        for( SaleSKU saleSKU : saleSKUList ) {


            // If the product code is null, then we need to set the product code
            // from the subscription of the customer
            if ( saleSKU.getSsuProductCode() == null || saleSKU.getSsuProductCode().equalsIgnoreCase("UNKNOWN")) {

                // Get the product code from the product subscription for the customer
                String prdCode = customerSubscriptionService.getCustomerSubscriptionProductCode(customer);

                // Set product code in the saleSKU
                saleSKU.setSsuProductCode(prdCode);

            }

            // Get the information for the product
            Product product = productService.findByPrdMerchantNoAndPrdCode(sale.getSalMerchantNo(), saleSKU.getSsuProductCode());

            // If the product is not found, then continue the loop
            if ( product == null ) {

                // Log the information
                log.info("LoyaltyEngine -> LoyaltyComputationProductBased -> calculatePoints : No corresponding product found");

                // Continue
                continue;

            }


            // Update the PurchasaSku parameters with the product information
            saleSKU.setSsuBrand(product.getPrdBrand());
            saleSKU.setSsuCategory1(product.getPrdCategory1());
            saleSKU.setSsuCategory2(product.getPrdCategory2());
            saleSKU.setSsuCategory3(product.getPrdCategory3());

            // See if there are any rules setup for the product information
            List<LoyaltyProgramSku> loyaltyProgramSkuList =  loyaltyProgramSkuService.listRulesForLineItem(loyaltyProgram.getPrgProgramNo(),product.getPrdBrand(),product.getPrdCategory1(),product.getPrdCategory2(),product.getPrdCategory3(),product.getPrdCode());


            // If there are not rules setup for the product, then return empty
            if ( loyaltyProgramSkuList == null || loyaltyProgramSkuList.isEmpty() ) {

                // Log the information
                log.info("LoyaltyEngine -> LoyaltyComputationProductBased -> calculatePoints : No loyalty program sku found");

                // Continue
                continue;

            }

            //get first item of the sku
            LoyaltyProgramSku loyaltyProgramSku = loyaltyProgramSkuList.get(0);

            boolean isValid =checkValidity(loyaltyProgramSku,cusTier,saleSKU);

            if(isValid){

                double productPoints1 =getProductPoint(loyaltyProgramSku,saleSKU);

                //if prg role is null consider that role is a customer role
                if(loyaltyProgram.getPrgRole() ==null ||loyaltyProgram.getPrgRole().intValue() == LoyaltyRefferalRoles.CUSTOMER){

                    totalPoints = totalPoints +productPoints1;

                }else if(loyaltyProgram.getPrgRole().intValue() == LoyaltyRefferalRoles.REFFERRER){

                    totalReferrerPoint =totalReferrerPoint+productPoints1;

                }else if (loyaltyProgram.getPrgRole().intValue() == LoyaltyRefferalRoles.REFERREE){


                    totalRefereePoint =totalRefereePoint+productPoints1;

                }else if (loyaltyProgram.getPrgRole().intValue() == LoyaltyRefferalRoles.BOTH){

                    totalReferrerPoint = totalReferrerPoint+productPoints1;

                    totalRefereePoint =totalRefereePoint+productPoints1;
                }


            }

        }


        // SAve all the saleSKU items
        saleSKUService.saveAll(saleSKUList);

        //set customer reward point
        customerRewardPoint.setTotalRewardPoint(totalPoints);

        //set referee point
        customerRewardPoint.setTotalRefereePoint(totalRefereePoint);

        //set referrer point
        customerRewardPoint.setTotalReferrerPoint(totalReferrerPoint);


        // Return the totalPoints
        return customerRewardPoint;
    }


    private boolean checkValidity(LoyaltyProgramSku loyaltyProgramSku, Tier cusTier, SaleSKU saleSKU) {

        if ( loyaltyProgramSku.getLpuTier() != 0L && cusTier.getTieId().longValue() != loyaltyProgramSku.getLpuTier().longValue() ) {

            // Log the information
            log.info("LoyaltyEngine -> LoyaltyComputationProductBased -> calculatePoints : tier not matching ");

            // Continue
            return false;

        }


        // Check if the transaction type is specified and is valid
//        if ( loyaltyProgramSku.getLpuTransactionType() != 0 && loyaltyProgramSku.getLpuTransactionType().intValue() != saleSKU.getSsuTransactionType().intValue() ) {
//
//            // Log the information
//            log.info("LoyaltyEngine -> LoyaltyComputationProductBased -> calculatePoints : Transaction type is not matching");
//
//            // Continue
//            return false;
//
//        }

        return true;
    }


    protected double getComputationAmount(double amount,double ratioNum,double ratioDeno) {

        // Calculate the modl
        double mod = amount % ratioDeno;

        // Take the actual value as amount - mod
        double actualAmount = amount - mod;

        // Return the actualAmount
        return actualAmount;

    }

    private double getProductPoint(LoyaltyProgramSku loyaltyProgramSku, SaleSKU saleSKU) {

        // Get the ratio
        double ratio = loyaltyProgramSku.getLpuPrgRatioNum()/loyaltyProgramSku.getLpuPrgRatioDeno();

        // Get the computable amount
        // We need to ignore the values for the mod in ratio
        double amount = getComputationAmount(saleSKU.getSsuPrice(),loyaltyProgramSku.getLpuPrgRatioNum(),loyaltyProgramSku.getLpuPrgRatioDeno());

        // Calcuate the points
        double productPoints = amount * saleSKU.getSsuQty() * ratio;

        return productPoints;

    }

}
