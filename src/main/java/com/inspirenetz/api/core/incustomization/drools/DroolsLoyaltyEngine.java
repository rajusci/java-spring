package com.inspirenetz.api.core.incustomization.drools;

import com.inspirenetz.api.core.dictionary.*;
import com.inspirenetz.api.core.domain.*;
import com.inspirenetz.api.core.incustomization.attrext.AttributeExtendedEntityMap;
import com.inspirenetz.api.core.service.*;
import com.inspirenetz.api.rest.exception.InspireNetzException;
import org.drools.KnowledgeBase;
import org.drools.runtime.StatefulKnowledgeSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by sandheepgr on 10/9/14.
 */
@Component
public class DroolsLoyaltyEngine  {

    // Create the logger
    private static Logger log = LoggerFactory.getLogger(DroolsEngineService.class);

    @Autowired
    private DroolsUtils droolsUtils;

    @Autowired
    private LoyaltyProgramService loyaltyProgramService;

    @Autowired
    private LoyaltyProgramSkuService loyaltyProgramSkuService;

    @Autowired
    private SaleService saleService;

    @Autowired
    private SaleSKUService saleSKUService;

    @Autowired
    private LoyaltyExtensionService loyaltyExtensionService;



    // Autowire the helper classes for drools functions
    @Autowired
    private CustomerHelper customerHelper;

    @Autowired
    private LoyaltyEngineHelper loyaltyEngineHelper;

    @Autowired
    private SalesHelper salesHelper;

    @Autowired
    private TransactionHelper transactionHelper;

    @Autowired
    private CustomerReferralHelper customerReferralHelper;




    @Transactional
    public CustomerRewardPoint processTransaction(LoyaltyProgram loyaltyProgram, Sale sale ) throws InspireNetzException {

        // First make sure that computation source is set to LoyaltyExtension
        if ( loyaltyProgram.getPrgComputationSource() != LoyaltyComputationSource.LOYALTY_EXTENSION ) {

            // Log the information
            log.error("startDroolsEngineProcessing -> Loyalty program computation source is not set to be Loyalty Extension");

            // Throw exception
            throw new InspireNetzException(APIErrorCode.ERR_OPERATION_NOT_ALLOWED);

        }


        // Get the session for the drools
        StatefulKnowledgeSession session = createStatefulKnowledgeSession(loyaltyProgram,sale);

        // Check if the session is valid
        if ( session == null ) {

            // Log the information
            log.error("startDroolsEngineProcessing -> No valid session available");

            // Throw exception
            throw new InspireNetzException(APIErrorCode.ERR_OPERATION_FAILED);

        }



        // Create the CustomerRewardPoint object
        CustomerRewardPoint customerRewardPoint = new CustomerRewardPoint();

        // Create the CustomerReferral object
        CustomerReferral customerReferral = new CustomerReferral();

        // Add the customerRewardPoint to the session
        session.insert(customerRewardPoint);

        // Add the customer referral to the session
        session.insert(customerReferral);

        // Fire all the rules
        session.fireAllRules();


        // Return the customerRewardPoint object
        return customerRewardPoint;

    }


    /**
     * Function to return a session for the StatefulKnowledgeSession
     * Here the knowledgeBase is retrieved  and the entities to be inserted into
     * the session are updated with the fieldMap
     *
     * This will also insert the helper classes as global for the session.
     *
     * @param loyaltyProgram    - The LoyaltyProgram object
     * @param sale              - The Sale object
     *
     * @return                  - StatefulKnowlegeSession with facts inserted
     */
    public StatefulKnowledgeSession createStatefulKnowledgeSession(LoyaltyProgram loyaltyProgram, Sale sale) {

        // Get the rules filename
        String filename = getRulesFileName(loyaltyProgram.getPrgLoyaltyExtension());

        // Get the KnowledgeBase object
        KnowledgeBase knowledgeBase = droolsUtils.createKnowledgeBase(filename);

        // Create the stateful session
        StatefulKnowledgeSession session = knowledgeBase.newStatefulKnowledgeSession();



        // Update the entityMap for the LoyaltyProgram object
        setLoyaltyProgramEntityMap(loyaltyProgram);

        // Update entityMap for the Salse object
        setSaleEntityMap(sale);


        // Get the fieldMap updated SaleSKu ( if applicable)
        List<SaleSKU> saleSKUList = getSaleSkuForSession(sale);



        // Put the entities in session
        //
        // Insert the loyaltyProgram
        session.insert(loyaltyProgram);

        // Insert the Sale object
        session.insert(sale);

        // Iterate through the sale sku if any and add to the session
        if ( saleSKUList != null && !saleSKUList.isEmpty() ) {

            // Iterate
            for( SaleSKU saleSKU : saleSKUList ) {

                // Insert to session
                session.insert(saleSKU);

            }
        }




        // Add the helper classes to the session
        session.setGlobal("customerHelper",customerHelper);

        session.setGlobal("loyaltyEngineHelper", loyaltyEngineHelper);

        session.setGlobal("salesHelper",salesHelper);

        session.setGlobal("transactionHelper",transactionHelper);

        session.setGlobal("customerReferralHelper",customerReferralHelper);



        // Finally return the session object
        return session;

    }


    /**
     * Function to get the rules filename for the given LoyaltyExtension object
     * represented by the lexId
     *
     * @param lexId - The LoyaltyExtension primary key
     *
     * @return      - The filename for the drools file represented by the id
     */
    public String getRulesFileName(Long lexId) {

        // Get the LoyaltyExtension file
        LoyaltyExtension loyaltyExtension = loyaltyExtensionService.findByLexId(lexId);

        // If the entry is null, return null
        if ( loyaltyExtension == null ) {

            return null;

        }

        // Return the filename
        return loyaltyExtension.getLexFile();

    }


    /**
     *
     * Function to get the SalesSKU List for the given Sale
     * If the list is found, then the function will update the fieldMap
     * for the objects
     *
     * @param sale  - The Sale object for which the SaleSKU need to be retrieved
     * @return      - The SaleSKU list objects
     */
    private List<SaleSKU> getSaleSkuForSession(Sale sale) {

        // If the sale is not item based, then return null
        if ( sale.getSalType() != SaleType.ITEM_BASED_PURCHASE ) {

            // Return null
            return null;

        }

        // Get the list of sku data for the sale
        List<SaleSKU> saleSKUList = saleSKUService.findBySsuSaleId(sale.getSalId());

        // If the list is not null, then we need to go through them and then
        // update the fieldMap
        if ( saleSKUList != null && !saleSKUList.isEmpty() ) {

            // Iterate through the loop and then set the fields
            for ( SaleSKU saleSKU : saleSKUList ) {

                // Call the setSaleSkuEntityMap
                saleSKU = setSaleSkuEntityMap(saleSKU);

            }

        }

        // Return the list
        return saleSKUList;

    }

    /**
     * Function to set the entity map in the loyaltyprogram object
     * Here the fieldMap field of the LoyaltyProgram object will be set to the
     * AttributeExtendedEntitymap
     *
     * @param loyaltyProgram    - The loyalty program using which the map need to be genereated.
     *                            The generated map will be set as the fieldMap for the same object
     * @return                  - LoyaltyProgram with the fieldMap set to the attributeExtendedEntityMap
     *
     */
    private LoyaltyProgram setLoyaltyProgramEntityMap( LoyaltyProgram loyaltyProgram ) {

        // Get the AttributeExtendedEntityMap
        AttributeExtendedEntityMap attributeExtendedEntityMap = loyaltyProgramService.toAttributeExtensionMap(loyaltyProgram, AttributeExtensionMapType.ALL);

        // Set the map to the LoyaltyProgram
        loyaltyProgram.setFieldMap(attributeExtendedEntityMap);

        // Return the loyaltyProgram
        return loyaltyProgram;

    }


    /*
     * Function to set the entity map in the LoyaltyProgramSku object
     * Here the fieldMap field of the LoyaltyProgramSku object will be set to the
     * AttributeExtendedEntitymap
     *
     * @param loyaltyProgramSku    - The LoyaltyProgramSku using which the map need to be genereated.
     *                               The generated map will be set as the fieldMap for the same object
     * @return                     - LoyaltyProgramSku with the fieldMap set to the attributeExtendedEntityMap
     *

    private LoyaltyProgramSku setLoyaltyProgramSkuEntityMap( LoyaltyProgramSku loyaltyProgramSku ) {

        // Get the AttributeExtendedEntityMap
        AttributeExtendedEntityMap attributeExtendedEntityMap = loyaltyProgramSkuService.toAttributeExtensionMap(loyaltyProgramSku,AttributeExtensionMapType.ALL);

        // Set the fieldMap
        loyaltyProgramSku.setFieldMap(attributeExtendedEntityMap);

        // Return the LoyaltyProgramSKu
        return loyaltyProgramSku;

    }
    */

    /**
     * Function to set the entity map in the Sale object
     * Here the fieldMap field of the Sale object will be set to the
     * AttributeExtendedEntitymap
     *
     * @param sale                  - The Sale using which the map need to be genereated.
     *                               The generated map will be set as the fieldMap for the same object
     * @return                     - Sale with the fieldMap set to the attributeExtendedEntityMap
     *
     */
    private Sale setSaleEntityMap(Sale sale) {

        // Get the AttributeExtendedEntityMap
        AttributeExtendedEntityMap attributeExtendedEntityMap = saleService.toAttributeExtensionMap(sale,AttributeExtensionMapType.ALL);

        // Set the fieldMap
        sale.setFieldMap(attributeExtendedEntityMap);

        // Return the object
        return sale;

    }


    /**
     * Function to set the entity map in the SaleSKU object
     * Here the fieldMap field of the SaleSKU object will be set to the
     * AttributeExtendedEntitymap
     *
     * @param saleSKU              - The SaleSKU using which the map need to be genereated.
     *                               The generated map will be set as the fieldMap for the same object
     * @return                     - SaleSKU with the fieldMap set to the attributeExtendedEntityMap
     *
     */
    private SaleSKU setSaleSkuEntityMap(SaleSKU saleSKU) {

        // Get the AttributeExtendedEntityMap
        AttributeExtendedEntityMap attributeExtendedEntityMap = saleSKUService.toAttributeExtensionMap(saleSKU,AttributeExtensionMapType.ALL);

        // Set the map to the object
        saleSKU.setFieldMap(attributeExtendedEntityMap);

        // Return the object
        return saleSKU;

    }

}
