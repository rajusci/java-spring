package com.inspirenetz.api.core.service.impl;

import com.inspirenetz.api.core.auth.AuthSession;
import com.inspirenetz.api.core.dictionary.APIErrorCode;
import com.inspirenetz.api.core.dictionary.CouponDistributionType;
import com.inspirenetz.api.core.dictionary.FunctionCode;
import com.inspirenetz.api.core.dictionary.NotificationCampaignListeners;
import com.inspirenetz.api.core.domain.CouponDistribution;
import com.inspirenetz.api.core.domain.Customer;
import com.inspirenetz.api.core.domain.NotificationCampaign;
import com.inspirenetz.api.core.domain.SegmentMember;
import com.inspirenetz.api.core.domain.validator.CouponDistributionValidator;
import com.inspirenetz.api.core.repository.BaseRepository;
import com.inspirenetz.api.core.repository.CouponDistributionRepository;
import com.inspirenetz.api.core.service.CouponDistributionService;
import com.inspirenetz.api.core.service.NotificationCampaignService;
import com.inspirenetz.api.core.service.SegmentMemberService;
import com.inspirenetz.api.rest.exception.InspireNetzException;
import com.inspirenetz.api.rest.resource.CouponDistributionResource;
import com.inspirenetz.api.util.AuthSessionUtils;
import com.inspirenetz.api.util.GeneralUtils;
import org.dozer.Mapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.validation.BeanPropertyBindingResult;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by sandheepgr on 28/3/14.
 */
@Service
public class CouponDistributionServiceImpl extends BaseServiceImpl<CouponDistribution> implements CouponDistributionService {


    private static Logger log = LoggerFactory.getLogger(CouponDistributionServiceImpl.class);


    @Autowired
    CouponDistributionRepository couponDistributionRepository;

    @Autowired
    private SegmentMemberService segmentMemberService;

    @Autowired
    private GeneralUtils generalUtils;

    @Autowired
    AuthSessionUtils authSessionUtils ;

    @Autowired
    NotificationCampaignService notificationCampaignService;

    @Autowired
    Mapper mapper;

    public CouponDistributionServiceImpl() {

        super(CouponDistribution.class);

    }

    @Override
    protected BaseRepository<CouponDistribution,Long> getDao() {
        return couponDistributionRepository;
    }



    @Override
    public Page<CouponDistribution> findByCodMerchantNo(Long codMerchantNo,Pageable pageable) {

        // Get the data from the repository and store in the list
        Page<CouponDistribution> couponDistributionList = couponDistributionRepository.findByCodMerchantNo(codMerchantNo,pageable);

        // Return the list
        return couponDistributionList;

    }

    @Override
    public CouponDistribution findByCodId(Long codId) {

        // Get the couponDistribution for the given couponDistribution id from the repository
        CouponDistribution couponDistribution = couponDistributionRepository.findByCodId(codId);

        // Return the couponDistribution
        return couponDistribution;


    }

    @Override
    public CouponDistribution findByCodMerchantNoAndCodCouponCode(Long codMerchantNo,String codCouponCode) {

        // Get the couponDistribution using the couponDistribution code and the merchant number
        CouponDistribution couponDistribution = couponDistributionRepository.findByCodMerchantNoAndCodCouponCode(codMerchantNo, codCouponCode);

        // Return the couponDistribution object
        return couponDistribution;

    } 

    @Override
    public boolean isDuplicateCouponDistributionExisting(CouponDistribution couponDistribution) {

        // Get the couponDistribution information
        CouponDistribution exCouponDistribution = couponDistributionRepository.findByCodMerchantNoAndCodCouponCode(couponDistribution.getCodMerchantNo(), couponDistribution.getCodCouponCode());

        // If the codId is 0L, then its a new couponDistribution so we just need to check if there is ano
        // ther couponDistribution code
        if ( couponDistribution.getCodId() == null || couponDistribution.getCodId() == 0L ) {

            // If the couponDistribution is not null, then return true
            if ( exCouponDistribution != null ) {

                return true;

            }

        } else {

            // Check if the couponDistribution is null
            if ( exCouponDistribution != null && couponDistribution.getCodId().longValue() != exCouponDistribution.getCodId().longValue() ) {

                return true;

            }
        }


        // Return false;
        return false;

    }

    @Override
    public List<CouponDistribution> getCouponDistributionForCustomers(Customer customer) {

        // List holding the distribution list for the customer
        List<CouponDistribution> couponDistributionList = new ArrayList<>(0);

        // Get all the distributions for the merchant
        List<CouponDistribution>  merCouponDistributionList = couponDistributionRepository.findByCodMerchantNo(customer.getCusMerchantNo());


        // If there are not  coupons distributed, then return the list
        if ( merCouponDistributionList == null || merCouponDistributionList.isEmpty() ) {

            return couponDistributionList;

        }



        // Go through each of the distribution list
        for(CouponDistribution couponDistribution : merCouponDistributionList ) {

            // Check the type of the distribution
            switch(couponDistribution.getCodDistributionType()) {


                // Public type
                case CouponDistributionType.PUBLIC :

                    // Add to the list
                    couponDistributionList.add(couponDistribution);

                    // break
                    break;


                // All members type
                case CouponDistributionType.MEMBERS :

                    // Add to the list
                    couponDistributionList.add(couponDistribution);

                    // break
                    break;


                // Selected customer segments
                case CouponDistributionType.CUSTOMER_SEGMENTS :

                    // Get the list the SegmentMembers for the customer
                    List<SegmentMember> segmentMemberList = segmentMemberService.findBySgmCustomerNo(customer.getCusCustomerNo());

                    // If the list is empty , break
                    if ( segmentMemberList == null || segmentMemberList.isEmpty()) {

                        break;

                    }


                    // Go through each of the segment member and see if the item is present
                    for(SegmentMember segmentMember : segmentMemberList ) {

                        if ( generalUtils.isTokenizedValueExists(couponDistribution.getCodCustomerSegments(),"#",segmentMember.getSgmSegmentId().toString()) ) {

                            // Add the segment to the list
                            couponDistributionList.add(couponDistribution);

                            // Break the for loop
                            break;

                        }
                    }

                    // Break the switch case
                    break;


                // Selected individual customer ids
                case CouponDistributionType.CUSTOMER_IDS :

                    // Check if the customer id field of the coupon distribution contains
                    // the current customer
                    if ( generalUtils.isTokenizedValueExists(couponDistribution.getCodCustomerIds(),"#",customer.getCusCustomerNo().toString()) ) {

                        // Add the segment to the list
                        couponDistributionList.add(couponDistribution);

                    }

                    // Break the switch case
                    break;



                default:break;

            }

        }

        // Return the coupondistributionlist
        return couponDistributionList;

    }

    @Override
    public boolean updateCouponDistributionStatus(Long codId, Integer codStatus, Long merchantNo, Long userNo) throws InspireNetzException {

        // Get the couponDistribution information
        CouponDistribution couponDistribution = findByCodId(codId);

        // If no data found, then set error
        if ( couponDistribution == null ) {

            // Log the response
            log.info("updateCouponDistributionStatus - Response : No couponDistribution information found");

            // Throw InspireNetzException with ERR_NO_DATA_FOUND as error
            throw new InspireNetzException(APIErrorCode.ERR_NO_DATA_FOUND);

        }

        // Check if the passed object's merchant number and the current
        // authenticoded merchant number are same.
        // We need to raise an error if not
        if ( couponDistribution.getCodMerchantNo().longValue() != merchantNo ) {

            // Log the response
            log.info("updateCouponDistributionStatus - Response : You are not authorized to delete the couponDistribution");

            // Throw InspireNetzException with ERR_NOT_AUTHORIZED as error
            throw new InspireNetzException(APIErrorCode.ERR_NOT_AUTHORIZED);

        }



        // Update the status
        couponDistribution.setCodStatus(codStatus);

        // Set the updateBy to the userNo
        couponDistribution.setUpdatedBy(Long.toString(userNo));


        // Log the object that we are going to save
        log.info("updateCouponDistributionStatus -> Saving CouponDistriution object " + couponDistribution.toString());

        // Save the object
        saveCouponDistribution(couponDistribution);



        // Return true
        return true;

    }


    @Override
    public CouponDistribution saveCouponDistribution(CouponDistribution couponDistribution ) throws InspireNetzException {

       // Save the couponDistribution
        return couponDistributionRepository.save(couponDistribution);

    }

    @Override
    public boolean deleteCouponDistribution(Long codId) throws InspireNetzException {

        // Delete the couponDistribution
        couponDistributionRepository.delete(codId);

        // return true
        return true;

    }

    @Override
    public CouponDistribution validateAndSaveCouponDistribution(CouponDistributionResource couponDistributionResource) throws InspireNetzException {

        authSessionUtils.validateFunctionAccess(FunctionCode.FNC_UPDATE_COUPON_DISTRIBUTION);

        Long merchantNo=authSessionUtils.getMerchantNo();

        String auditDetails=authSessionUtils.getUserNo().toString();

        boolean isNewDistribution=true;

        CouponDistribution couponDistribution=null;

        if(couponDistributionResource.getCodId()==null || couponDistributionResource.getCodId().longValue()==0){

            isNewDistribution=true;
            couponDistribution=mapper.map(couponDistributionResource,CouponDistribution.class);

        }else{

            couponDistribution=findByCodId(couponDistributionResource.getCodId());

            if(couponDistribution==null){

                log.info("validateAndSaveCouponDistribution -> no coupon distribution info found");

                throw new InspireNetzException(APIErrorCode.ERR_NO_DATA_FOUND);

            }

            isNewDistribution=false;

            mapper.map(couponDistributionResource,couponDistribution);
        }

        couponDistribution.setCodMerchantNo(merchantNo);

        CouponDistributionValidator couponDistributionValidator=new CouponDistributionValidator();

        BeanPropertyBindingResult result=new BeanPropertyBindingResult(couponDistributionValidator,"CouponDistribution");

        couponDistributionValidator.validate(couponDistribution,result);

        // Check if the result contains error
        if ( result.hasErrors() ) {

            // Log the response
            log.info("validateAndSaveCouponDistribution - Response : Invalid Input - ");

            // Throw InspireNetzException with INVALID_INPUT as error
            throw new InspireNetzException(APIErrorCode.ERR_INVALID_INPUT,result);

        }

        // Check if the couponDistribution is existing
        boolean isExist = isDuplicateCouponDistributionExisting(couponDistribution);

        // Check the boolean value
        if ( isExist ) {

            // Log the response
            log.info("validateAndSaveCouponDistribution - Response : CouponDistribution code is already existing");

            // Throw InspireNetzException with ERR_DUPLICATE_ENTRY as error
            throw new InspireNetzException(APIErrorCode.ERR_DUPLICATE_ENTRY);


        }



        // If the couponDistribution.getCodId is  null, then set the created_by, else set the updated_by
        if ( couponDistribution.getCodId() == null ) {

            couponDistribution.setCreatedBy(auditDetails);

        } else {

            couponDistribution.setUpdatedBy(auditDetails);

        }


        couponDistribution= saveCouponDistribution(couponDistribution);

        if(couponDistribution.getCodId()!=null&&isNewDistribution){

            //for sending notification to target listeners based on configuration
            broadcastCouponDistribution(couponDistribution);

        }

        return couponDistribution;
    }

    private NotificationCampaign getNotificationCampaignObjForCouponDistribution(CouponDistribution couponDistribution){

        NotificationCampaign notificationCampaign=new NotificationCampaign();

        notificationCampaign.setNtcMerchantNo(couponDistribution.getCodMerchantNo());
        notificationCampaign.setNtcTargetChannels(couponDistribution.getCodBroadCastType());

        switch (couponDistribution.getCodDistributionType()){

            case CouponDistributionType. PUBLIC :
                notificationCampaign.setNtcTargetListeners(NotificationCampaignListeners.PUBLIC);
                break;

            case CouponDistributionType.MEMBERS:
                notificationCampaign.setNtcTargetListeners(NotificationCampaignListeners.ALL_MEMBERS);
                break;

            case CouponDistributionType.CUSTOMER_SEGMENTS:
                notificationCampaign.setNtcTargetListeners(NotificationCampaignListeners.SEGMENT);
                notificationCampaign.setNtcTargetSegments(couponDistribution.getCodCustomerSegments());
                break;

            case CouponDistributionType.CUSTOMER_IDS:
                notificationCampaign.setNtcTargetListeners(NotificationCampaignListeners.INDIVIDUAL_CUSTOMERS);
                if(couponDistribution.getCodCustomerIds()!=null&&couponDistribution.getCodCustomerIds().length()>0)
                notificationCampaign.setNtcTargetCustomers(couponDistribution.getCodCustomerIds().replace("#",":"));

                break;


        }


        notificationCampaign.setNtcSmsContent(couponDistribution.getCodSmsContent());
        notificationCampaign.setNtcEmailSubject(couponDistribution.getCodEmailSubject());
        notificationCampaign.setNtcEmailContent(couponDistribution.getCodEmailContent());

        HashMap<String,String> params=new HashMap<String, String>(0);
        params.put("<coupon>",couponDistribution.getCodCouponCode());

        notificationCampaign.setParams(params);

        return notificationCampaign;
    }

    private void broadcastCouponDistribution(CouponDistribution couponDistribution){

        if(couponDistribution.getCodDistributionType()==CouponDistributionType.MEMBERS||couponDistribution.getCodDistributionType()==CouponDistributionType.CUSTOMER_SEGMENTS||couponDistribution.getCodDistributionType()==CouponDistributionType.CUSTOMER_IDS){

            NotificationCampaign notificationCampaign=getNotificationCampaignObjForCouponDistribution(couponDistribution);

            notificationCampaignService.sendNotification(notificationCampaign);

        }

    }

    @Override
    public boolean validateAndDeleteCouponDistribution(Long codId) throws InspireNetzException {

        authSessionUtils.validateFunctionAccess(FunctionCode.FNC_DELETE_COUPON);

        return deleteCouponDistribution(codId);

    }


}
