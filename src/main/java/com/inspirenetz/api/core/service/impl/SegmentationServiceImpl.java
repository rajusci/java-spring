package com.inspirenetz.api.core.service.impl;

import com.inspirenetz.api.core.dictionary.CustomerSegmentType;
import com.inspirenetz.api.core.dictionary.IndicatorStatus;
import com.inspirenetz.api.core.domain.Customer;
import com.inspirenetz.api.core.domain.CustomerSegment;
import com.inspirenetz.api.core.domain.SegmentMember;
import com.inspirenetz.api.core.segmentation.Segmentation;
import com.inspirenetz.api.rest.exception.InspireNetzException;
import com.inspirenetz.api.util.AuthSessionUtils;
import com.inspirenetz.api.util.SegmentationUtils;
import com.inspirenetz.api.core.service.CustomerSegmentService;
import com.inspirenetz.api.core.service.CustomerService;
import com.inspirenetz.api.core.service.SegmentMemberService;
import com.inspirenetz.api.core.service.SegmentationService;
import com.inspirenetz.api.util.GeneralUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by sandheepgr on 17/2/14.
 */
@Service
public class SegmentationServiceImpl  implements SegmentationService {


    // Get the logger instance
    private static Logger log = LoggerFactory.getLogger(SegmentationServiceImpl.class);

    // Maximum customers to be fetched in a Page
    private static final int MAX_CUSTOMERS_IN_PAGE = 50;

    @Autowired
    private SegmentMemberService segmentMemberService;

    @Autowired
    AuthSessionUtils authSessionUtils;

    @Autowired
    private CustomerSegmentService customerSegmentService;

    @Autowired
    private CustomerService customerService;

    @Autowired
    private SegmentationUtils segmentationUtils;

    @Autowired
    private GeneralUtils generalUtils;



    @Override
    public boolean processSegmentation(CustomerSegment customerSegment) throws InspireNetzException {

        // Set the logging as started
        log.info("Segmentation -> processSegmentation : Starting for - " + customerSegment.toString());


        // If the customerSegment type is EXPLICIT, then we need to return false
        if ( customerSegment.getCsgSegmentType() == CustomerSegmentType.EXPLICIT ) {

            // Log the information as no customers for the merchant
            log.info("Segmentation -> processSegmentation : Exiting, segment is explicit");

            // Return true as completed processing
            return true;

        }

        // Set the pageindex to 0
        int pageIndex = 0;

        // Set the customerCount to 0
        Long customerCount = 0L;

        // Get the customers for the merchantNo
        Page<Customer> customerPage = customerService.findByCusMerchantNo(customerSegment.getCsgMerchantNo(), generalUtils.constructCustomerPageSpecification(pageIndex,MAX_CUSTOMERS_IN_PAGE));

        // Check if there are any customers returned
        if ( customerPage == null || !customerPage.hasContent()) {

            // Log the information as no customers for the merchant
            log.info("Segmentation -> processSegmentation : Exiting, no customers");

            // Return true as completed processing
            return true;
        }


        // Get the Segmentation object
        Segmentation segmentation = customerSegmentService.getSegmentationForCustomerSegment(customerSegment);

        // Repeat the loop till the page has got content
        while( customerPage !=  null && customerPage.hasContent() ) {

            // Repeat through the items in the page as an iterable
            for(Customer customer: customerPage) {

                // Log the customer information being processing
                log.info("Segmentation -> processSegmentation : Processing for customer no:"+customer.getCusCustomerNo());

                // Process the customer
                boolean isAdded = processCustomer(customerSegment,customer,segmentation);

                // Log the completion
                log.info("Segmentation -> processSegmentation : Processing completed for customer no:"+customer.getCusCustomerNo());


                // If isAdded, then increment the customer count
                if ( isAdded )  customerCount++;

            }


            // Update the SegmentGenerationProgress
            updateSegmentGenerationProgress(customerSegment,customerPage,customerCount);;


            // if there is no next page, then break the loop
            if ( !customerPage.hasNextPage() ) break;

            // Get the next page of customers
            customerPage = customerService.findByCusMerchantNo(customerSegment.getCsgMerchantNo(), generalUtils.constructCustomerPageSpecification(++pageIndex,MAX_CUSTOMERS_IN_PAGE));

        }

        // finally return true
        return true;

    }

    @Override
    public void updateSegmentGenerationProgress(CustomerSegment customerSegment,Page<Customer> customerPage,Long customerCount) throws InspireNetzException {

        // Get the percent completed
        double percentCompleted = ((double)(customerPage.getNumber() + 1) / (double)customerPage.getTotalPages()) * 100.0;


        // Set the percent completed
        customerSegment.setCsgGenerationPercent(percentCompleted);

        // Set the customer count
        customerSegment.setCsgCustomerCount(customerCount);


        // Save the customerSegment
        customerSegmentService.saveCustomerSegment(customerSegment);


    }

    @Override
    public boolean addSegmentMember(CustomerSegment customerSegment, Customer customer) throws InspireNetzException {

        // Add the SegmentMember
        SegmentMember segmentMember = new SegmentMember();

        //check segment member is present or not if its present we need to update
        SegmentMember segmentMember1 = getSegmentMemberWithSegmentAndCustomer(customerSegment, customer);

        if(segmentMember1 !=null){

            segmentMember.setSgmId(segmentMember1.getSgmId());
        }

        // Set the segmentID
        segmentMember.setSgmSegmentId(customerSegment.getCsgSegmentId());

        // Set the customer no
        segmentMember.setSgmCustomerNo(customer.getCusCustomerNo());


        // Set the auditDetails as same as the one for the CustomerSegment
        segmentMember.setCreatedBy(customerSegment.getCreatedBy());

        //set merchant number
        segmentMember.setSgmMerchantNo(customerSegment.getCsgMerchantNo());


        // Save the segment
        segmentMember = segmentMemberService.validateAndSaveSegmentMember(segmentMember);

        // Check if save
        if ( segmentMember == null || segmentMember.getSgmSegmentId() == null ) {

            return false;

        }


        // finally return true;
        return true;

    }

    private SegmentMember getSegmentMemberWithSegmentAndCustomer(CustomerSegment customerSegment, Customer customer) {

        return  segmentMemberService.findBySgmSegmentIdAndSgmCustomerNo(customerSegment.getCsgSegmentId(),customer.getCusCustomerNo());
    }

    @Override
    public boolean processCustomer(CustomerSegment customerSegment, Customer customer,Segmentation segmentation) {

        // Call the processing
        // We put the processing in a try catch block since if we may have the segment
        // try adding a customer to the SegmentMember again if we are running a update.
        // In those cases, we could catch the Dataintegrity exception ( UNIQUE constraint for segmentid+customerno)
        // and continue with the next one
        try{



            // Process the customer
            boolean isCustomerValid = segmentation.isCustomerValidMember(customerSegment,customer);

            // If the cusotmer is not valid, the log the information and continue
            if ( !isCustomerValid ) {

                // Log the information
                log.info("Segmentation -> processSegmentation : Customer is not valid for the segment");

                // return false
                return false;
            }

            // add the customer to the SegmentMember
            boolean customerAdded = addSegmentMember(customerSegment,customer);

            // If the customer is added , set the log
            if ( customerAdded ) {

                // Log as added
                log.info("Segmentation -> processSegmentation : Customer has been added - "+customerSegment.getCsgSegmentId()+"#"+customer.getCusCustomerNo());

                // Return
                return true;

            } else {

                // Log as not added
                log.info("Segmentation -> processSegmentation : Unable to add the customer - "+customerSegment.getCsgSegmentId()+"#"+customer.getCusCustomerNo());

                // Return false
                return false;

            }

        } catch (Exception e) {

            // Log the exception
            log.info("Segmentation -> processSegmentation : Exception " + e.getMessage() + " : customer - " +customer.toString());

            // return false
            return false;
        }
    }


    /**
     * Function to refresh the customer segments for a customer
     * This is usually called when a customer is created / updated.
     *
     * @param customer - The Customer object who is getting updated
     */
    @Override
    public void refreshCustomerMemberSegments(Customer customer) throws InspireNetzException {


        // Log the information
        log.info("Segmentation -> refreshCustomerMemberSegments : Staring customer segments refresh");


        // List holding the segments to which the customer is newly member of
        List<SegmentMember> newMemberSegmentList = new ArrayList<>(0);

        // Get all the customer segments defined for the Merchant
        List<CustomerSegment> merchantSegmentList = customerSegmentService.findByCsgMerchantNo(customer.getCusMerchantNo());

        // If there are not segments, then return the control
        if ( merchantSegmentList == null || merchantSegmentList.isEmpty() ) {

            // Log the infomration
            log.info("Segmentation -> refreshCustomerMemberSegments : No segments defined for merchant");

            return ;

        }



        // Get the list of all the segments for the customer from the SegmentMemberService
        List<SegmentMember> segmentMemberList = segmentMemberService.findBySgmCustomerNo(customer.getCusCustomerNo());

        // Convert the list into map
        Map<Long,SegmentMember> segmentMemberMap = segmentMemberService.getSegmentMemberMapBySegmentId(segmentMemberList);



        // Go through each of the item in the merchantSegmentList
        for(CustomerSegment customerSegment : merchantSegmentList ) {

            // Log information
            log.info("Segmentation -> refreshCustomerMemberSegments : Processing for segment "+customerSegment.getCsgSegmentId());

            // Check if the segmentType is explicit, if yes, then we dont touch it
            // The customers are manully added and removed from the explicit list
            if ( customerSegment.getCsgSegmentType() == CustomerSegmentType.EXPLICIT ) {

                // Log information
                log.info("Segmentation -> refreshCustomerMemberSegments : Segment is explicit continue iteration");

                continue;

            }



            // Store the current segment id
            Long csgSegmentId = customerSegment.getCsgSegmentId();

            // Check if the customer is member of the segment
            //
            // Get the segmentation
            Segmentation segmentation = customerSegmentService.getSegmentationForCustomerSegment(customerSegment);

            // check if the customer is valid
            boolean isCustomerValid = segmentation.isCustomerValidMember(customerSegment,customer);

            // check if the customer is valid,
            // If the customer is not valid, then we need to check if the customer is
            // already in the list and then remove then remove them
            //
            //
            // If the customer is valid, then if they are not already in the list,
            // we need to add an entry for the customer on current segment
            if ( !isCustomerValid ) {

                // Log information
                log.info("Segmentation -> refreshCustomerMemberSegments : Customer is not valid");

                // If the customer is not valid and if the customer is in the
                // segmentMemberList, then we need to delete it
                if ( segmentMemberMap.containsKey(csgSegmentId) ) {

                    // Get the segmentMember
                    SegmentMember delSegmentMember = segmentMemberMap.get(csgSegmentId);

                    // Delete the segmentMember
                    segmentMemberService.deleteSegmentMember(delSegmentMember.getSgmId());

                    // remove from the list
                    segmentMemberMap.remove(csgSegmentId);

                    // Log information
                    log.info("Segmentation -> refreshCustomerMemberSegments : Removed from segmentMembermap array");
                }

            } else {

                // If the customer is valid and is already in the list, then iterate
                if ( segmentMemberMap.containsKey(csgSegmentId) ) {

                    // Log information
                    log.info("Segmentation -> refreshCustomerMemberSegments : Segment is already in the list");

                    continue;

                }

                // Create the new SegmentMember
                SegmentMember segmentMember = new SegmentMember();

                // Set the customer no
                segmentMember.setSgmCustomerNo(customer.getCusCustomerNo());

                // Set the segment id
                segmentMember.setSgmSegmentId(customerSegment.getCsgSegmentId());

                // Add the list to the newMemberSegmentList
                newMemberSegmentList.add(segmentMember);


                // Log information
                log.info("Segmentation -> refreshCustomerMemberSegments : Added the segment as a new segment");


                /*
                 * Here it doesn't seems like we have to call the removeLowerTierSegments
                 * Since we are checking each every segment and if its not a member and is in the customer
                 * segment list, we are manually deleting it.
                 * So if the customser tier changes the current tier will get auotmaticaly removed.
                 *

                // If the newly added segment has auto tier upgrade then check for lower tiers
                if (customerSegment.getCsgAutoUpgradeSegment() == IndicatorStatus.YES ) {

                    // Delete the lower segments for which the customer is a member of
                    removeLowerTierSegments(customer,customerSegment,segmentMemberMap);

                }
                */

            }

        }

        // Log information
        log.info("Segmentation -> refreshCustomerMemberSegments : Segments to save -> " + newMemberSegmentList.toString());

        // At the end of the loop, we need to add the new segments in the newSegmentMemberList
        segmentMemberService.saveAll(newMemberSegmentList);

    }

    /**
     * Function to remove the lower tier segments for a given customer segment
     * Here we are passing customer information, current segment and the map of segments
     * for which the customer is already a member
     * Function will get all the lower tiers for the passed segment and then
     * check if the customer is in any of the segments
     * If he is then ,it will be removed
     *
     * @param customer          - The customer object
     * @param customerSegment   - The CustomerSegment object
     * @param segmentMemberMap  - The SegmentMember Map object containting the customers current segments
     */
    @Override
    public void removeLowerTierSegments(Customer customer,CustomerSegment customerSegment,Map<Long,SegmentMember> segmentMemberMap) throws InspireNetzException {

        // Check if the segment has got the tier auto upgrade option on, if not return false
        if ( customerSegment.getCsgAutoUpgradeSegment() == IndicatorStatus.NO) {

            return ;

        }


        // Get the tiered segments
        List<CustomerSegment> tieredSegments = customerSegmentService.getUpgradeTiers(customerSegment);

        // Get the lower tier segments for the given segment
        List<CustomerSegment> lowerTierSegmentList = customerSegmentService.getLowerTierSegments(tieredSegments,customerSegment);

        // Create the SegmentMemberList using the customer number and segment list
        for(CustomerSegment lowerSegment : lowerTierSegmentList ) {

            // Check if the segment is existing
            if ( segmentMemberMap.containsKey(lowerSegment.getCsgSegmentId()) ) {

                // Get the SegmentMember object and remove it
                SegmentMember segmentMember =segmentMemberMap.get(lowerSegment.getCsgSegmentId());

                // Delete the segmentMember
                segmentMemberService.deleteSegmentMember(segmentMember.getSgmId());

                // Log the information about the removal of the tier
                log.info("SegmentationService -> removeLowerTierSegments -> removingSegment for customer "+ customer.getCusCustomerNo() + " : " + segmentMember.toString());

            }

        }

    }


}
