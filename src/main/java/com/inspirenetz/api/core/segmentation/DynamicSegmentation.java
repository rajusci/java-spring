package com.inspirenetz.api.core.segmentation;

import com.inspirenetz.api.core.dictionary.CustomerSegmentComparisonType;
import com.inspirenetz.api.core.dictionary.CustomerSegmentCriteriaType;
import com.inspirenetz.api.core.domain.Customer;
import com.inspirenetz.api.core.domain.CustomerSegment;
import com.inspirenetz.api.core.domain.CustomerSummaryArchive;
import com.inspirenetz.api.core.service.CustomerSummaryArchiveService;
import com.inspirenetz.api.util.SegmentationUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by sandheepgr on 29/5/14.
 */
public class DynamicSegmentation implements Segmentation {


    private static Logger log = LoggerFactory.getLogger(DynamicSegmentation.class);

    private CustomerSummaryArchiveService customerSummaryArchiveService;

    private SegmentationUtils segmentationUtils;




    public DynamicSegmentation(CustomerSummaryArchiveService customerSummaryArchiveService,SegmentationUtils segmentationUtils) {

        this.customerSummaryArchiveService = customerSummaryArchiveService;

        this.segmentationUtils =segmentationUtils;

    }


    @Override
    public boolean isCustomerValidMember(CustomerSegment customerSegment, Customer customer) {



        // Variable holding the location
        Long csaLocation = customerSegment.getCsgLocation();

        // Variable holding the csaYear
        Integer csaYear = 0;

        // Variable holding the csaQuarter
        Integer csaQuarter = 0;

        // Variable holding the csaMonth
        Integer csaMonth = 0;


        // Check the rules for the CustomerSEgment and then populate the fields
        if (customerSegment.getCsgCriteriaType() == CustomerSegmentCriteriaType.YEARLY_STATS ) {

            // Set the year
            csaYear = customerSegment.getCsgYear();

        } else if ( customerSegment.getCsgCriteriaType() == CustomerSegmentCriteriaType.QUARTERLY_STATS ) {

            // Set the Year
            csaYear =  customerSegment.getCsgYear();

            // Set the quarter
            csaQuarter = customerSegment.getCsgQuarter();

        } else if ( customerSegment.getCsgCriteriaType() ==  CustomerSegmentCriteriaType.MONTHLY_STATS) {

            // Set the Year
            csaYear =  customerSegment.getCsgYear();

            // Set the month
            csaMonth = customerSegment.getCsgMonth();

            // Set the quarter for the month
            csaQuarter = segmentationUtils.getQuarterForMonth(csaMonth);

        }

        // Get the CustomerSummaryArchiveInformation for the customer
        List<CustomerSummaryArchive> customerSummaryArchiveList = getCustomerSummaryArchiveForSegmentInfo(customer.getCusMerchantNo(),customer.getCusLoyaltyId(),csaLocation,csaYear,csaQuarter,csaMonth);


        //  if no information fouund, then return false
        if ( customerSummaryArchiveList == null || customerSummaryArchiveList.isEmpty()) {

            // Log the information
            log.info("Segmentation -> DynamicSegmentation -> isCustomerValidMember : No summary information found");

            // return false
            return false;

        }



        // Get the consolidated CustomerSummaryARchive
        CustomerSummaryArchive customerSummaryArchive = getConsolidatedCSA(customerSummaryArchiveList);

        // Log the consolidated information
        log.info("Segmentation -> DynamicSegmentation -> isCustomerValidMember : Consolidated CSA " + customerSummaryArchive.toString());



        // Check the rules for the consolidated value
        boolean isValid = checkDynamicRuleValidity(customerSegment,customer,customerSummaryArchive);

        // Check if the data is valid for the customer
        if ( !isValid ) {

            // Log the information
            log.info("Segmentation -> DynamicSegmentation -> isCustomerValidMember : Customer is not valid for the segment");

            // return false
            return false;

        }

        // Log the information
        log.info("Segmentation -> DynamicSegmentation -> isCustomerValidMember : Customer is valid for the segment");

        // Finally return true
        return true;

    }


    /**
     * Function to get the list of the CustomerSummaryArchive objects based on the
     * fields pased
     *
     * @param merchantNo    - The merchant number of the merchant
     * @param loyaltyId     - The loyalt id of the customer
     * @param csaLocation   - Location of the customer
     * @param csaYear       - Year field
     * @param csaQuarter    - Quarter field
     * @param csaMonth      - Month field
     *
     *
     * @return              - Return the list of the CustomerSummaryArchive based on the the passed information
     */
    public List<CustomerSummaryArchive> getCustomerSummaryArchiveForSegmentInfo(Long merchantNo,String loyaltyId,Long csaLocation, int csaYear,int csaQuarter,int csaMonth ) {

        // List holding the return data
        List<CustomerSummaryArchive> customerSummaryArchiveList = new ArrayList<>(0);

        // Check the fields and then get the list of data
        if ( csaYear != 0L && csaQuarter != 0L && csaMonth != 0L) {

            // Get the data
            CustomerSummaryArchive customerSummaryArchive = customerSummaryArchiveService.findByCsaMerchantNoAndCsaLoyaltyIdAndCsaLocationAndCsaPeriodYyyyAndCsaPeriodQqAndCsaPeriodMm(merchantNo,loyaltyId,csaLocation,csaYear,csaQuarter,csaMonth);

            // Check if its valid
            if ( customerSummaryArchive != null ) {

                customerSummaryArchiveList.add(customerSummaryArchive);

            }

        } else if ( csaYear != 0L && csaQuarter != 0L ) {

            customerSummaryArchiveList = customerSummaryArchiveService.findByCsaMerchantNoAndCsaLoyaltyIdAndCsaLocationAndCsaPeriodYyyyAndCsaPeriodQq(merchantNo,loyaltyId,csaLocation,csaYear,csaQuarter);

        } else if ( csaYear != 0L ) {

            customerSummaryArchiveList = customerSummaryArchiveService.findByCsaMerchantNoAndCsaLoyaltyIdAndCsaLocationAndCsaPeriodYyyy(merchantNo,loyaltyId,csaLocation,csaYear);

        } else {

            customerSummaryArchiveList = customerSummaryArchiveService.findByCsaMerchantNoAndCsaLoyaltyIdAndCsaLocation(merchantNo,loyaltyId,csaLocation);

        }


        // Return the list
        return customerSummaryArchiveList;

    }


    /**
     * Function to get Consolidated values for the CustomerSummaryArchive list
     * This function adds the amount, visit and qty fields and retunrs the consolidated values
     * as a CustomerSummaryArchive object
     *
     * @param customerSummaryArchiveList    - The list of CustomerSummaryArhcives
     * @return                              - Consolidated object
     */
    public CustomerSummaryArchive getConsolidatedCSA(List<CustomerSummaryArchive> customerSummaryArchiveList) {

        // Get the first Item
        CustomerSummaryArchive customerSummaryArchive = null;

        // Loop through the items and then sum the values
        for(CustomerSummaryArchive csaObj : customerSummaryArchiveList ) {

            // If the customerSummaryArchive is null, then we need to set it to the current object
            if ( customerSummaryArchive == null ) {

                // Set the object
                customerSummaryArchive = csaObj;

                // Continue the current iteration
                continue;

            }


            // Add the txnAmount
            customerSummaryArchive.setCsaTxnAmount(customerSummaryArchive.getCsaTxnAmount() + csaObj.getCsaTxnAmount());

            // Add the visit cunt
            customerSummaryArchive.setCsaVisitCount(customerSummaryArchive.getCsaVisitCount() + csaObj.getCsaVisitCount());

            // Add the quantity
            customerSummaryArchive.setCsaQuantity(customerSummaryArchive.getCsaQuantity() + csaObj.getCsaQuantity());

        }


        // Return the customerSummaryArchive object
        return customerSummaryArchive;

    }


    /**
     * Function to check the validity for the comparison type for a segment and the corresponding value
     * for the CustomerSummaryArchive
     *
     * @param csaValue      - The summary archive value
     * @param compType      - The comparison type
     * @param compValue1    - The first comparison value
     * @param compValue2    - The second comparison value
     * @return              - Return boolean value based on the comparion operation
     */
    public boolean checkCompTypeValidity(double csaValue,int compType, double compValue1, double compValue2) {

        // Check the compType and check the validity
        switch(compType) {

            case CustomerSegmentComparisonType.MORE_THAN :
                return csaValue > compValue1;

            case CustomerSegmentComparisonType.EQUAL_TO :
                return csaValue == compValue1;

            case CustomerSegmentComparisonType.EQUAL_OR_MORE_THAN:
                return csaValue >= compValue1;

            case CustomerSegmentComparisonType.LESS_THAN :
                return csaValue < compValue1;

            case CustomerSegmentComparisonType.LESS_THAN_OR_EQUAL:
                return csaValue <= compValue1;

            case CustomerSegmentComparisonType.BETWEEN :
                return (csaValue >= compValue1 && csaValue <= compValue2);

        }

        // Finally return false
        return false;

    }


    /**
     *
     * Check the validity of the Dynamic Segment rules
     * Function will check the validity for the comp type values ( amount, visit and quantity)
     * and return true only if all them are true
     *
     * @param customerSegment           - The CustomerSegment object
     * @param customer                  - Customer object
     * @param customerSummaryArchive    - CustomerSummaryARchive object with the consolidated information
     * @return                          - Return boolean value based on the comparison for all the vlaues
     */
    public boolean checkDynamicRuleValidity(CustomerSegment customerSegment,Customer customer,CustomerSummaryArchive customerSummaryArchive) {

        // Flag value holding the validity of the txnAmount
        boolean isTxnAmountValid = true;

        // Flag value holding the validity of the visitCount
        boolean isVisitCountValid = true;

        // Flag value holding the validity of the qty
        boolean isQtyValid = true;


        // Check if the txnAmount fields are set
        if ( customerSegment.getCsgAmountCompType() != null  && customerSegment.getCsgAmountCompValue1() != null ) {

            isTxnAmountValid = checkCompTypeValidity(customerSummaryArchive.getCsaTxnAmount(),customerSegment.getCsgAmountCompType(),customerSegment.getCsgAmountCompValue1(),customerSegment.getCsgAmountCompValue2());

        }

        // Check if the visit count fields are set
        if ( customerSegment.getCsgVisitCompType() != null && customerSegment.getCsgVisitCompValue1() != null ) {

            isVisitCountValid = checkCompTypeValidity(customerSummaryArchive.getCsaVisitCount(),customerSegment.getCsgVisitCompType(),customerSegment.getCsgVisitCompValue1(),customerSegment.getCsgVisitCompValue2());

        }

        // Check if the quanity fields are set
        if ( customerSegment.getCsgQtyCompType() != null && customerSegment.getCsgQtyCompValue1() != null ) {

            isQtyValid = checkCompTypeValidity(customerSummaryArchive.getCsaQuantity(),customerSegment.getCsgQtyCompType(),customerSegment.getCsgQtyCompValue1(),customerSegment.getCsgQtyCompValue2());

        }


        // Return the and operation of all the flags
        return ( isTxnAmountValid && isVisitCountValid && isQtyValid );

    }
}
