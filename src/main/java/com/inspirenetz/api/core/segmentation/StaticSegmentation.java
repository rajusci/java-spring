package com.inspirenetz.api.core.segmentation;

import com.inspirenetz.api.core.dictionary.IndicatorStatus;
import com.inspirenetz.api.core.domain.Customer;
import com.inspirenetz.api.core.domain.CustomerProfile;
import com.inspirenetz.api.core.domain.CustomerSegment;
import com.inspirenetz.api.core.service.CustomerProfileService;
import com.inspirenetz.api.util.GeneralUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Created by sandheepgr on 29/5/14.
 */
public class StaticSegmentation implements Segmentation {

    private static Logger log = LoggerFactory.getLogger(StaticSegmentation.class);

    private CustomerProfileService customerProfileService;

    private GeneralUtils generalUtils;

    public StaticSegmentation(CustomerProfileService customerProfileService,GeneralUtils generalUtils) {

        this.customerProfileService = customerProfileService;

        this.generalUtils = generalUtils;


    }

    @Override
    public boolean isCustomerValidMember(CustomerSegment customerSegment, Customer customer) {

        // Get the CustomerProfile object for the customer
        CustomerProfile customerProfile = customerProfileService.findByCspCustomerNo(customer.getCusCustomerNo());

        // If there is no customer profile , the return false
        if ( customerProfile == null ) {

            // Log the information
            log.info("Segmentation -> StaticSegmentation -> isCustomerValidMember : No profile found");

            // Return false;
            return false;

        }



        // Check for the rules
        // Gender values
        if ( customerSegment.getCsgGenderEnabledInd() == IndicatorStatus.YES ) {

            // if the customerprofile field is null, then return false
            if ( customerProfile.getCspGender() == null ) {

                // Log the information
                log.info("Segmentation -> StaticSegmentation -> isCustomerValidMember : Gender value is null for customer");

                // Return false;
                return false;

            }


            // Check if the gender for the customer is as the one specified in the segment
            if ( !generalUtils.isTokenizedValueExists(customerSegment.getCsgGenderValues(),",",customerProfile.getCspGender()) ) {

                // Log the information
                log.info("Segmentation -> StaticSegmentation -> isCustomerValidMember : Gender based rule failed");

                // Return false;
                return false;

            }
        }



        // Profession values
        if ( customerSegment.getCsgProfessionEnabledInd() == IndicatorStatus.YES ) {

            // if the customerprofile field is null, then return false
            if ( customerProfile.getCspProfession() == null ) {

                // Log the information
                log.info("Segmentation -> StaticSegmentation -> isCustomerValidMember : Profession value is null for customer");

                // Return false;
                return false;

            }


            // Check if the profession for the customer is as the one specified in the segment
            if ( !generalUtils.isTokenizedValueExists(customerSegment.getCsgProfessionValues(),",",customerProfile.getCspProfession().toString()) ) {

                // Log the information
                log.info("Segmentation -> StaticSegmentation -> isCustomerValidMember : Profession based rule failed");

                // Return false;
                return false;

            }
        }



        // Agegroup values
        if ( customerSegment.getCsgAgegroupEnabledInd() == IndicatorStatus.YES ) {

            // if the customerprofile field is null, then return false
            if ( customerProfile.getCspAgeGroup() == null ) {

                // Log the information
                log.info("Segmentation -> StaticSegmentation -> isCustomerValidMember : Agegroup value is null for customer");

                // Return false;
                return false;

            }


            // Check if the agegroup for the customer is as the one specified in the segment
            if ( !generalUtils.isTokenizedValueExists(customerSegment.getCsgAgegroupValues(),",",customerProfile.getCspAgeGroup().toString()) ) {

                // Log the information
                log.info("Segmentation -> StaticSegmentation -> isCustomerValidMember : Agegroup based rule failed");

                // Return false;
                return false;

            }
        }



        // Incomerange values
        if ( customerSegment.getCsgIncomerangeEnabledInd() == IndicatorStatus.YES ) {

            // if the customerprofile field is null, then return false
            if ( customerProfile.getCspIncomeRange() == null ) {

                // Log the information
                log.info("Segmentation -> StaticSegmentation -> isCustomerValidMember : Income range value is null for customer");

                // Return false;
                return false;

            }


            // Check if the incomerange for the customer is as the one specified in the segment
            if ( !generalUtils.isTokenizedValueExists(customerSegment.getCsgIncomerangeValues(),",",customerProfile.getCspIncomeRange().toString()) ) {

                // Log the information
                log.info("Segmentation -> StaticSegmentation -> isCustomerValidMember : Agegroup based rule failed");

                // Return false;
                return false;

            }
        }



        // Familystatus values
        if ( customerSegment.getCsgFamilystatusEnabledInd() == IndicatorStatus.YES ) {

            // if the customerprofile field is null, then return false
            if ( customerProfile.getCspFamilyStatus() == null ) {

                // Log the information
                log.info("Segmentation -> StaticSegmentation -> isCustomerValidMember : Family status value is null for customer");

                // Return false;
                return false;

            }


            // Check if the agegroup for the customer is as the one specified in the segment
            if ( !generalUtils.isTokenizedValueExists(customerSegment.getCsgFamilystatusValues(),",",customerProfile.getCspFamilyStatus().toString()) ) {

                // Log the information
                log.info("Segmentation -> StaticSegmentation -> isCustomerValidMember : Family status  based rule failed");

                // Return false;
                return false;

            }
        }

        // Finally return true
        return true;
    }
}
