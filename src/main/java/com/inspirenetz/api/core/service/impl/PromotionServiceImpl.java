package com.inspirenetz.api.core.service.impl;

import com.inspirenetz.api.core.auth.AuthSession;
import com.inspirenetz.api.core.dictionary.*;
import com.inspirenetz.api.core.domain.*;
import com.inspirenetz.api.core.domain.validator.PromotionValidator;
import com.inspirenetz.api.core.repository.BaseRepository;
import com.inspirenetz.api.core.repository.PromotionRepository;
import com.inspirenetz.api.core.service.*;
import com.inspirenetz.api.rest.exception.InspireNetzException;
import com.inspirenetz.api.rest.resource.PromotionResource;
import com.inspirenetz.api.util.AuthSessionUtils;
import com.inspirenetz.api.util.GeneralUtils;
import org.dozer.Mapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.validation.BeanPropertyBindingResult;

import java.sql.Date;
import java.util.*;

/**
 * Created by sandheepgr on 28/3/14.
 */
@Service
public class PromotionServiceImpl extends BaseServiceImpl<Promotion> implements PromotionService {

    private static Logger log = LoggerFactory.getLogger(PromotionServiceImpl.class);


    @Autowired
    PromotionRepository promotionRepository;

    @Autowired
    private SegmentMemberService segmentMemberService;

    @Autowired
    private UserResponseService userResponseService;

    @Autowired
    private CustomerService customerService;

    @Autowired
    AuthSessionUtils authSessionUtils;

    @Autowired
    MerchantService merchantService;

    @Autowired
    ImageService imageService;

    @Autowired
    private Environment environment;

    @Autowired
    private GeneralUtils generalUtils;

    @Autowired
    private UserService userService;

    @Autowired
    private NotificationCampaignService notificationCampaignService;

    @Autowired
    Mapper mapper;

    public PromotionServiceImpl() {

        super(Promotion.class);

    }


    @Override
    protected BaseRepository<Promotion,Long> getDao() {
        return promotionRepository;
    }



    @Override
    public Page<Promotion> findByPrmMerchantNo(Long prmMerchantNo, Pageable pageable) {

        Page<Promotion> promotionPage =  promotionRepository.findByPrmMerchantNo(prmMerchantNo,pageable);

        return promotionPage;

    }

    @Override
    public Promotion findByPrmId(Long prmId) {

        Promotion promotion = promotionRepository.findByPrmId(prmId);

        return promotion;


    }

    @Override
    public Promotion findByPrmMerchantNoAndPrmName(Long prmMerchantNo, String prmName) {

        Promotion promotion = promotionRepository.findByPrmMerchantNoAndPrmName(prmMerchantNo,prmName);

        return promotion;

    }

    @Override
    public Page<Promotion> findByPrmMerchantNoAndPrmNameLike(Long prmMerchantNo, String prmName, Pageable pageable) {

        Page<Promotion> promotionPage = promotionRepository.findByPrmMerchantNoAndPrmNameLike(prmMerchantNo,prmName,pageable);

        return promotionPage;

    }

    @Override
    public boolean isDuplicatePromotionExisting(Promotion promotion) {

        // Get the promotion information
        Promotion exPromotion = promotionRepository.findByPrmMerchantNoAndPrmName(promotion.getPrmMerchantNo(), promotion.getPrmName());

        // If the brnId is 0L, then its a new promotion so we just need to check if there is ano
        // ther promotion code
        if ( promotion.getPrmId() == null || promotion.getPrmId() == 0L ) {

            // If the promotion is not null, then return true
            if ( exPromotion != null ) {

                return true;

            }

        } else {

            // Check if the promotion is null
            if ( exPromotion != null && promotion.getPrmId().longValue() != exPromotion.getPrmId().longValue() ) {

                return true;

            }
        }


        // Return false;
        return false;

    }

    @Override
    public List<Promotion> getPromotionsForUser(User user) {




        // List holding the promotions
        List<Promotion> userPromotionList = new ArrayList<>();


        // Get the customer list for the user
        List<Customer> customerList = customerService.findByCusUserNoOrCusEmailOrCusMobile(user.getUsrUserNo(),user.getUsrLoginId(),user.getUsrMobile());

        // Check if the customerList exists
        if ( customerList == null || customerList.isEmpty() ) {

            return userPromotionList;

        }




        // Go through of the customer list and get the promotions for the customer
        for(Customer customer : customerList ) {

            // Get the List of promotions for customer
            List<Promotion> cusPromotionList = getPromotionsForCustomer(customer);

            //  If the list if not empty, then we need to add it to the userPromotionList
            if ( cusPromotionList == null ||  cusPromotionList.isEmpty() ) {

                // Continue the iteration
                continue;

            }


            // Add the list
            userPromotionList.addAll(cusPromotionList);

        }


        // Finally return the user promotion list
        return userPromotionList;

    }

    @Override
    public boolean isPromotionValidForCustomer(Promotion promotion , Customer customer) {

        // Check whether the Promotion is active
        //
        // Need to check the type of expiry set for the Promotion
        if( promotion.getPrmExpiryOption() == PromotionExpiryOption.EXPIRY_DATE ) {

            // first check the validity of the date
            //
            // Get the current date
            Date currDate = new Date( new java.util.Date().getTime());

            // Compare the expiry date for the coupon to the currDate
            if ( currDate.compareTo(promotion.getPrmExpiryDate())  > 0  ) {

                return false;

            }


        } else if ( promotion.getPrmExpiryOption() == PromotionExpiryOption.NUM_RESPONSES ) {

            // Check that the number of responses is less than the max responses
            if ( promotion.getPrmNumResponses() >=  promotion.getPrmMaxResponses() ) {

                return false;

            }

        }



       // Check the target audience
        if ( promotion.getPrmTargetedOption() == PromotionTargetOption.CUSTOMER_SEGMENT ) {

            // Get the member segments for the customer
            List<SegmentMember> segmentMemberList = segmentMemberService.findBySgmCustomerNo(customer.getCusCustomerNo());

            // If there are not segments, then return false
            if ( segmentMemberList == null || segmentMemberList.isEmpty() ) {

                return false;

            }

            // Set the flag as false
            boolean isEligible = false;

            // Go through the segment members and check if its present
            for(SegmentMember segmentMember : segmentMemberList ) {

                // Check if the promotion segment id is equal to the segment membersegment id
                if ( promotion.getPrmSegmentId() == segmentMember.getSgmSegmentId() ) {

                    // Set the flag to true
                    isEligible = true;

                    // Break the loop
                    break;
                }

            }

            // Check if the flag is false, if yse, then return false
            if ( !isEligible ) {

                return false;

            }

        }


        // Finally return true
        return true;

    }

    @Override
    public boolean isPromotionValid(Promotion promotion) {

        // Catch to handle null reference in expiry date and number of response
        try{

            // Need to check the type of expiry set for the Promotion
            if( promotion.getPrmExpiryOption() == PromotionExpiryOption.EXPIRY_DATE ) {

                // first check the validity of the date
                //
                // Get the current date
                Date currDate = new Date( new java.util.Date().getTime());

                // Compare the expiry date for the coupon to the currDate
                if ( currDate.compareTo(promotion.getPrmExpiryDate())  > 0  ) {

                    return false;

                }


            } else if ( promotion.getPrmExpiryOption() == PromotionExpiryOption.NUM_RESPONSES ) {

                // Check that the number of responses is less than the max responses
                if ( promotion.getPrmNumResponses() >=  promotion.getPrmMaxResponses() ) {

                    return false;

                }

            }

        }catch(NullPointerException e){

            return false;
        }


        // Finally return true
        return true;

    }

    @Override
    public List<Promotion> getPromotionsForCustomer(Customer customer) {


        // List holding the promotions available for the Customer
        List<Promotion> promotionList = new ArrayList<>(0);

        // Get all the Promotions for the merchant number of the customer
        List<Promotion> merPromotionList = promotionRepository.findByPrmMerchantNo(customer.getCusMerchantNo());

        // If the are no promotions, then return empty list
        if ( merPromotionList == null || merPromotionList.isEmpty() ) {

            return promotionList;

        }



        // Go through each of the promotions and check the validity for the customer
        for(Promotion promotion :merPromotionList ) {

            // Check the validity of promotion for the customer
            boolean isValid = isPromotionValidForCustomer(promotion,customer);

            // if the promotion is valid, then add to the list
            if ( isValid ) {

                promotionList.add(promotion);
            }

        }


        // Finally return the list
        return promotionList;

    }





    @Override
    public Promotion savePromotion(Promotion promotion ) throws InspireNetzException {

        // Save the promotion
        return promotionRepository.save(promotion);

    }

    @Override
    public boolean deletePromotion(Long brnId) throws InspireNetzException {


        // Delete the promotion
        promotionRepository.delete(brnId);

        // return true
        return true;

    }

    @Override
    public Promotion validateAndSavePromotion(PromotionResource promotionResource) throws InspireNetzException {

        //check the user access rights
        authSessionUtils.validateFunctionAccess(FunctionCode.FNC_UPDATE_PROMOTION);

        Long merchantNo=authSessionUtils.getMerchantNo();

        String auditDetails=authSessionUtils.getUserNo().toString();

        boolean isNewPromotion=true;

        promotionResource.setPrmMerchantNo(merchantNo);

        Promotion promotion=null;

        if(promotionResource.getPrmId()==null||promotionResource.getPrmId().longValue()==0){

            isNewPromotion=true;

            promotion=mapper.map(promotionResource,Promotion.class);

        }else{

            promotion=findByPrmId(promotionResource.getPrmId());

            if(promotion==null){

                log.info("validateAndSavePromotion -> promotion not found");

                // throw exception
                throw new InspireNetzException(APIErrorCode.ERR_NO_DATA_FOUND);

            }

            isNewPromotion=false;

            mapper.map(promotionResource,promotion);

        }

        promotion.setPrmMerchantNo(merchantNo);


        // Create the PromotionValidator object
        PromotionValidator validator = new PromotionValidator(generalUtils);

        // Create the BeanPropertyBindingResult
        BeanPropertyBindingResult prmResult = new BeanPropertyBindingResult(promotion,"promotion");

        // Validate the result
        validator.validate(promotion,prmResult);

        // Check if the result contains error
        if ( prmResult.hasErrors() ) {

            // Log the response
            log.info("validateAndSavePromotion - Response : Invalid Input - ");

            // Throw InspireNetzException with INVALID_INPUT as error
            throw new InspireNetzException(APIErrorCode.ERR_INVALID_INPUT,prmResult);

        }

        // Check if the promotion is existing
        boolean isExist = isDuplicatePromotionExisting(promotion);

        // Check the boolean value
        if ( isExist ) {

            // Log the response
            log.info("validateAndSavePromotion - Response : Promotion name is already existing");

            // Throw InspireNetzException with ERR_DUPLICATE_ENTRY as error
            throw new InspireNetzException(APIErrorCode.ERR_DUPLICATE_ENTRY);


        }

        // If the promotion.getPrmId is  null, then set the created_by, else set the updated_by
        if ( promotion.getPrmId() == null ) {

            promotion.setCreatedBy(auditDetails);

        } else {

            promotion.setUpdatedBy(auditDetails);

        }

        promotion=savePromotion(promotion);

        if(promotion.getPrmId()!=null&&isNewPromotion){

            //for sending notification to target listeners based on configuration
            broadcastPromotion(promotion);

        }

        return promotion;

    }

    private NotificationCampaign getNotificationCampaignObjectFromPromotion(Promotion promotion){

        NotificationCampaign notificationCampaign=new NotificationCampaign();

        notificationCampaign.setNtcMerchantNo(promotion.getPrmMerchantNo());
        notificationCampaign.setNtcTargetChannels(promotion.getPrmBroadcastOption());

        switch (promotion.getPrmTargetedOption()){

            case PromotionTargetOption. PUBLIC :
                notificationCampaign.setNtcTargetListeners(NotificationCampaignListeners.PUBLIC);
                break;

            case PromotionTargetOption.ALL_MEMBERS:
                notificationCampaign.setNtcTargetListeners(NotificationCampaignListeners.ALL_MEMBERS);
                break;

            case PromotionTargetOption.CUSTOMER_SEGMENT:
                notificationCampaign.setNtcTargetListeners(NotificationCampaignListeners.SEGMENT);
                break;
        }


        notificationCampaign.setNtcSmsContent(promotion.getPrmSmsContent());
        notificationCampaign.setNtcEmailSubject(promotion.getPrmEmailSubject());
        notificationCampaign.setNtcEmailContent(promotion.getPrmEmailContent());
        notificationCampaign.setNtcTargetSegments(promotion.getPrmSegmentId()+"");
        return notificationCampaign;
    }


    private void broadcastPromotion(Promotion promotion){

        if(promotion.getPrmTargetedOption()==PromotionTargetOption.ALL_MEMBERS||promotion.getPrmTargetedOption()==PromotionTargetOption.CUSTOMER_SEGMENT){

            NotificationCampaign notificationCampaign= getNotificationCampaignObjectFromPromotion(promotion);

            notificationCampaignService.sendNotification(notificationCampaign);

        }


    }

    @Override
    public boolean validateAndDeletePromotion(Long prmId) throws InspireNetzException {

        //check the user access rights
        authSessionUtils.validateFunctionAccess(FunctionCode.FNC_DELETE_PROMOTION);

        return deletePromotion(prmId);
    }

    @Override
    public Promotion updatePromotionView(UserResponse userResponse) {

        Promotion promotion =null;

        Long userNo = authSessionUtils.getUserNo();
        //check user response if user response is null then update view count
        UserResponse userResponse1 =userResponseService.findByUrpUserNoAndUrpResponseItemTypeAndUrpResponseItemIdAndUrpResponseType(userNo,userResponse.getUrpResponseItemType(),userResponse.getUrpResponseItemId(), PromotionUserAction.VIEW);

        if(userResponse1 ==null){

            //update promotion count
            promotion = promotionRepository.findByPrmId(userResponse.getUrpResponseItemId());

            if(promotion !=null){

                Integer currentPromotion =promotion.getPrmNumViews() ==null?0:promotion.getPrmNumViews();

                promotion.setPrmNumViews(currentPromotion+1);

                promotion =promotionRepository.save(promotion);

                // save the user response
                //find userNo for customer



                userResponse.setUrpUserNo(userNo);

                userResponse1 =userResponseService.saveUserResponse(userResponse);

                return  promotion;



            }


        }

        return promotion;
    }

    /**
     * @purpose:update the promotion response
     * @param userResponse
     * @return
     * @throws InspireNetzException
     */
    @Override
    public Promotion updatePromotionResponse(UserResponse userResponse) throws InspireNetzException {

        Promotion promotion =null;

        Long userNo = authSessionUtils.getUserNo();

        //check user response if user response is null then update response count
        UserResponse userResponse1 =userResponseService.findByUrpUserNoAndUrpResponseItemTypeAndUrpResponseItemIdAndUrpResponseType(userNo,userResponse.getUrpResponseItemType(),userResponse.getUrpResponseItemId(),PromotionUserAction.CLAIM);

        if(userResponse1 ==null){

            //update promotion count
            promotion = promotionRepository.findByPrmId(userResponse.getUrpResponseItemId());

            if(promotion !=null){

                Integer currentPromotionResponse =promotion.getPrmNumResponses() ==null?0:promotion.getPrmNumResponses();

                promotion.setPrmNumResponses(currentPromotionResponse+1);

                promotion =promotionRepository.save(promotion);
                // save the user response
                //find userNo for customer
                userResponse.setUrpUserNo(userNo);

                userResponse1 =userResponseService.saveUserResponse(userResponse);

                return  promotion;

            }


        }

        return promotion;
    }

    /**
     * @purpose:convert customer promotion into comptable format
     * @param merchantNo
     * @return
     */
    @Override
    public List<Promotion> getComptablePromotion(Long merchantNo) {

        List<Promotion> promotionList =promotionRepository.findByPrmMerchantNo(merchantNo);

        List promotionFormatList =new ArrayList<HashMap>();

        String cusLoyaltyId =authSessionUtils.getUserLoginId();

        String imageUrl = environment.getProperty("IMAGE_PATH_URL");

        Map map;

        if(promotionList !=null){

            //format promotion in compatable format
            for(Promotion promotion:promotionList){

                map =new HashMap();

                map.put("prm_name",promotion.getPrmName()==null?"":promotion.getPrmName());

                map.put("prm_short_desc",promotion.getPrmShortDescription()==null?"":promotion.getPrmShortDescription());

                map.put("prm_long_desc",promotion.getPrmLongDescription()==null?"":promotion.getPrmLongDescription());

                map.put("prm_merchant_no",promotion.getPrmMerchantNo());

                //find merchant name
                Merchant merchant =merchantService.findByMerMerchantNo(promotion.getPrmMerchantNo());

                if(merchant !=null){

                    //get merchant image
                    Image merchantImage = merchant.getImgLogo();

                    //If the image is set, then we need to set the image path
                    if ( merchantImage !=  null ) {

                        // Get the image path
                        String imagePath  = imageService.getPathForImage(merchantImage, ImagePathType.MOBILE);

                        // Set the imagePath
                        map.put("prm_merchant_image",imageUrl + imagePath);

                    }

                    map.put("prm_merchant_name",merchant.getMerMerchantName()==null?"":merchant.getMerMerchantName());

                    map.put("prm_merchant_image_id",merchant.getMerMerchantImage()==null?0L:merchant.getMerMerchantImage());

                }

                //get image path
                // Check if the image exists
                Image image = promotion.getImage();

                //If the image is set, then we need to set the image path
                if ( image !=  null ) {

                    // Get the image path
                    String imagePath  = imageService.getPathForImage(image, ImagePathType.MOBILE);

                    // Set the imagePath
                    map.put("prm_image",imageUrl + imagePath);



                }

                map.put("prm_image_id",promotion.getPrmImage()==null?0L:promotion.getPrmImage());

                map.put("prm_cus_loyalty_id",cusLoyaltyId);

                promotionFormatList.add(map);


            }


        }
        
        return promotionFormatList;
    }

    /**
     * @purpose: cto get public promotions from all merchants
     * @param prmMerchantNo
     * @return
     */
    @Override
    public Page<Promotion> getPublicPromotions(Long prmMerchantNo,String query, Pageable pageable) {

        //page for promotions
        Page<Promotion> promotionPage ;

        //page for fetched promotions
        Page<Promotion> fetchedPromotionPage ;

        //list for validated promotions
        List<Promotion> validPromotions = new ArrayList<>(0);

        //check if default merchant no then set it zero
        if(prmMerchantNo==generalUtils.getDefaultMerchantNo()){

            prmMerchantNo=0L;

        }
        
        //check if merchantNo is 0
        if(prmMerchantNo==null||prmMerchantNo.longValue()==0){

            //call promotion repository for public promotion for all merchants
            promotionPage = promotionRepository.findByPrmTargetedOptionAndPrmNameLike(PromotionTargetOption.PUBLIC,"%"+query+"%", pageable);

        }
        else {

            //call promotion repository for public promotion for particular merchants
            promotionPage = promotionRepository.findByPrmMerchantNoAndPrmTargetedOptionAndPrmNameLike(prmMerchantNo, PromotionTargetOption.PUBLIC,"%"+query+"%", pageable);

        }

        // Go through each of the promotions and check the validity
        for(Promotion promotion :promotionPage ) {

              // Check the validity of promotion
            boolean isValid = isPromotionValid(promotion);

            // if the promotion is valid, then add to the list
            if ( isValid ) {

            Merchant merchant=merchantService.findByMerMerchantNo(promotion.getPrmMerchantNo());

            if(merchant!=null){

                promotion.setMerMerchantName(merchant.getMerMerchantName());
            }

            validPromotions.add(promotion);


            }

        }

        //convert list into page
        promotionPage = new PageImpl<>(validPromotions);

        //return data
        return promotionPage;
    }

    @Override
    public List<Promotion> getPromotionsForCustomer(Customer customer, String query, Pageable pageable) {

        //page for promotions
        Page<Promotion> promotionPage ;

        Set<Promotion> validPromotionSet=new HashSet<>();

        //list for validated promotions available for the Customer
        List<Promotion> validPromotions = new ArrayList<>(0);

        // Get all the Promotions for the merchant number of the customer
        promotionPage = promotionRepository.findByPrmMerchantNoAndPrmNameLike(customer.getCusMerchantNo(),"%"+query+"%",pageable);

        // If the are no promotions, then return empty list
        if ( promotionPage == null || !promotionPage.hasContent() ) {

            return validPromotions;

        }



        // Go through each of the promotions and check the validity for the customer
        for(Promotion promotion :promotionPage ) {

            Merchant merchant=merchantService.findByMerMerchantNo(promotion.getPrmMerchantNo());

            if(merchant!=null&& merchant.getMerMerchantNo()!=0){

                promotion.setMerMerchantName(merchant.getMerMerchantName());
            }

            boolean isValid =false;

            try{

                // Check the validity of promotion for the customer
                 isValid = isPromotionValidForCustomer(promotion,customer);

            }catch(Exception ex){

                isValid =false;

            }


            // if the promotion is valid, then add to the list
            if ( isValid ) {

            validPromotionSet.add(promotion);
            }

        }

        validPromotions.addAll(validPromotionSet);

        // Finally return the list
        return validPromotions;


    }

    @Override
    public Page<Promotion> getPromotionsForUser(String usrLoginId, Long merchantNo, String query, Pageable pageable) {

        // List holding the promotions
        List<Promotion> userPromotionList = new ArrayList<>();

        //Get user object
        User user=userService.findByUsrLoginId(usrLoginId);



        if(user==null||user.getUsrUserNo()==null){

            //log the info
            log.info("No User Information Found");


            return new PageImpl<>(userPromotionList);
        }

        //get member customers,if catMerchantNo is zero or default merchant no return all members
        List<Customer> customers=customerService.getUserMemberships(merchantNo,user.getUsrUserNo(),CustomerStatus.ACTIVE);

        if(customers==null ||customers.isEmpty()){

            //log the info
            log.info("No Customer Information Found");

            return new PageImpl<>(userPromotionList);

        }



        // Go through of the customer list and get the promotions for the customer
        for(Customer customer : customers ) {

            // Get the List of promotions for customer
            List<Promotion> cusPromotionList = getPromotionsForCustomer(customer,query,pageable);

            //  If the list if not empty, then we need to add it to the userPromotionList
            if ( cusPromotionList == null ||  cusPromotionList.isEmpty() ) {

                // Continue the iteration
                continue;

            }


            // Add the list
            userPromotionList.addAll(cusPromotionList);

        }


        // Finally return the user promotion list
        return new PageImpl<>(userPromotionList);
    }

}
