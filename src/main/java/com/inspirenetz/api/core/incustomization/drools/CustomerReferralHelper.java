package com.inspirenetz.api.core.incustomization.drools;

import com.inspirenetz.api.core.dictionary.CustomerReferralAwardingPeriod;
import com.inspirenetz.api.core.dictionary.CustomerReferralStatus;
import com.inspirenetz.api.core.domain.Customer;
import com.inspirenetz.api.core.domain.CustomerReferral;
import com.inspirenetz.api.core.service.CustomerReferralService;
import com.inspirenetz.api.core.service.CustomerService;
import com.inspirenetz.api.util.GeneralUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * Created by sandheepgr on 24/10/15.
 */
@Component
public class CustomerReferralHelper {

    @Autowired
    private CustomerReferralService customerReferralService;

    @Autowired
    private CustomerService customerService;

    @Autowired
    private GeneralUtils generalUtils;

    // Create the logger
    private static Logger log = LoggerFactory.getLogger(CustomerReferralService.class);


    /**
     * Function to get the referrer customer object for a given referee customer object
     * The function will try to find the referrers for the given customer using the mobile number as the reference and
     * will identify the referrer as the one who referrer the customer first.
     * After that the Customer object is identified for the referrer and returned from the function.
     *
     * @param referee  : Customer object indicating the referee
     * @return         : On success, return the Customer object for the referrer,
     *                   On failure, return null
     */
    public Customer getReferrerCustomerForReferee(Customer referee) {

        // Get the list of customer referrals
        List<CustomerReferral> customerReferrals = customerReferralService.findByCsrMerchantNoAndCsrRefMobileAndCsrRefStatusOrderByCsrRefTimeStampAsc(referee.getCusMerchantNo(), referee.getCusMobile(), CustomerReferralStatus.PROCESSED);

        // Log the information
        log.info("CustomerReferrals : " + customerReferrals);

        // Check if the referral is null
        if  ( customerReferrals == null || customerReferrals.size() == 0) {

            // Log the error
            log.error("Customer referrals are null");

            // return null
            return null;

        }


        // Get the first entry in the list
        CustomerReferral customerReferral = customerReferrals.get(0);

        // Get the customer information for the customer
        Customer referrer = customerService.findByCusLoyaltyIdAndCusMerchantNo(customerReferral.getCsrLoyaltyId(),customerReferral.getCsrMerchantNo());

        // Check if the customer is found
        if ( referrer == null ) {

            // Log the error
            log.error("Customer information not found for the referral");

            // return null
            return null;

        }


        // Log the referrer customer details
        log.info("Referrer customer details :  " + referrer);

        // Return the customer object
        return referrer;

    }


    /**
     * Function to get the number of referrals done by a referrer in a given time period
     *
     * @param referrer  - The customer object for the referrer
     * @param period    - The CustomerReferralAwardingPeriod
     *
     *
     * @return          - Returns the count of the referrals ( 0 if no data found)
     */
    public Integer getTotalRefferalCount(Customer referrer,Integer period) {

        // The end date to be the current date
        Date endDate = new Date();

        // Based on the period passed, calculate the start date
        Date startDate = getReferralPeriodStartDate(endDate,period);

        // Get the list of referrals
        List<CustomerReferral> customerReferralList = customerReferralService.findByCsrMerchantNoAndCsrLoyaltyIdAndCsrRefTimeStampBetween(
                                                                                                                                            referrer.getCusMerchantNo(),
                                                                                                                                            referrer.getCusLoyaltyId(),
                                                                                                                                            generalUtils.convertUtilDateToSqlDate(startDate),
                                                                                                                                            generalUtils.convertUtilDateToSqlDate(endDate)
                                                                                                                                         );

        // Check if there is a list returned
        // If the list is empty or null, return 0
        if ( customerReferralList == null || customerReferralList.isEmpty() ) {

            return 0;

        }


        // Return the size of the list
        return customerReferralList.size();

    }


    /**
     * Function to get the startDate for a transaction range given the endDate and the
     * period of the calcuation
     *
     * @param endDate  : The endDate of the period range
     * @param period   : The period for the range calculation ( value from the CustomerReferralAwardingPeriod constants)
     *
     *
     * @return         : The startDate based on the values passed
     *
     */
    private Date getReferralPeriodStartDate(Date endDate, Integer period )  {

        // Get the start date based on the period and the endDate passed
        Date startDate;

        // Define the Calendar instance
        Calendar calendar = Calendar.getInstance();

        // Set the time of the calendar to the endDate
        calendar.setTime(endDate);

        // Get the data
        switch(period) {

            // When the period is year
            case CustomerReferralAwardingPeriod.YEAR:

                // Calculate the calendar as one year before
                calendar.add(Calendar.YEAR,-1);

                // break;
                break;


            // When the period is month
            case CustomerReferralAwardingPeriod.MONTH:

                // Calculate the calendar as one year before
                calendar.add(Calendar.MONTH,-1);

                // break;
                break;

        }


        // Set the date in the startDAte
        startDate = calendar.getTime();

        // Return the startDate
        return startDate;

    }

    /**
     * Function to get the list of referrals
    */
    public List<CustomerReferral> getCustomerReferralList(Customer referrer){

        //get the customer referral list based on earned status
        List<CustomerReferral> customerReferralList = customerReferralService.findByCsrMerchantNoAndCsrLoyaltyIdAndCsrEarnedStatus(referrer.getCusMerchantNo(),referrer.getCusLoyaltyId(),true);

        // Log the information
        log.info("CustomerReferrals : " + customerReferralList);

        //check if the customer referral list is returned,if list is empty return null
        if(customerReferralList == null || customerReferralList.isEmpty()){

            // Log the error
            log.error("Customer referrals are null");

            //return null
            return null;

        }

        //return the list of customerReferrals
        return customerReferralList;

    }


    /**
     * Purpose: To create an array list
     * @return
     */
    public List<CustomerReferral> getArrayList() {

        //return an array list
        return new ArrayList<CustomerReferral>(0);
    }

    /**
     * purpose: update the earned status of referral
     * @param referrer
     * @param customer
     * @param actualPoints
     */
    public void updateStatus(Customer referrer, Customer customer, int actualPoints) {

        //get the current customer referral
        CustomerReferral customerReferral = customerReferralService.findByCsrMerchantNoAndCsrLoyaltyIdAndCsrRefMobile(referrer.getCusMerchantNo(), referrer.getCusLoyaltyId(), customer.getCusMobile());

        //set the earned status as true
        customerReferral.setCsrEarnedStatus(true);


        customerReferral.setCsrPoints((double)actualPoints);

        //save the referral
        customerReferralService.saveCustomerReferral(customerReferral);

    }


}
