package com.inspirenetz.api.core.service.impl;
import com.inspirenetz.api.core.dictionary.*;
import com.inspirenetz.api.core.domain.*;
import com.inspirenetz.api.core.domain.validator.CustomerReferralValidator;
import com.inspirenetz.api.core.repository.BaseRepository;
import com.inspirenetz.api.core.repository.CustomerReferralRepository;
import com.inspirenetz.api.core.service.*;
import com.inspirenetz.api.rest.exception.InspireNetzException;
import com.inspirenetz.api.rest.resource.CustomerReferralResource;
import com.inspirenetz.api.util.AuthSessionUtils;
import com.inspirenetz.api.util.CustomerRewardUtils;
import com.inspirenetz.api.util.DataValidationUtils;
import com.inspirenetz.api.util.GeneralUtils;
import org.dozer.Mapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;
import org.springframework.validation.BeanPropertyBindingResult;

import java.sql.Date;
import java.sql.Timestamp;
import java.util.*;

/**
 * Created by saneeshci on 27/9/14.
 */
@Service
public class CustomerReferralServiceImpl extends BaseServiceImpl<CustomerReferral> implements CustomerReferralService,InjectableReward {


    private static Logger log = LoggerFactory.getLogger(CustomerReferralServiceImpl.class);


    @Autowired
    CustomerReferralRepository customerReferralRepository;


    @Autowired
    CustomerService customerService;

    @Autowired
    GeneralUtils generalUtils;

    @Autowired
    UserMessagingService userMessagingService;

    @Autowired
    DataValidationUtils dataValidationUtils;

    @Autowired
    private AuthSessionUtils authSessionUtils;

    @Autowired
    SettingService settingService;

    @Autowired
    MerchantSettingService merchantSettingService;

    @Autowired
    UserService userService;

    @Autowired
    MerchantLocationService merchantLocationService;

    @Autowired
    private Mapper mapper;


    private CustomerRewardActivityService customerRewardActivityService;

    public CustomerReferralServiceImpl() {

        super(CustomerReferral.class);

    }

    @Override
    protected BaseRepository<CustomerReferral,Long> getDao() {
        return customerReferralRepository;
    }

    @Override
    public boolean isDuplicateReferralExisting(CustomerReferral customerReferral) {

        // Get the customer referral information
        CustomerReferral exCustomerReferral=customerReferralRepository.findByCsrMerchantNoAndCsrLoyaltyIdAndCsrRefMobileAndCsrRefStatusNot(customerReferral.getCsrMerchantNo(), customerReferral.getCsrLoyaltyId(), customerReferral.getCsrRefMobile(), CustomerReferralStatus.OVERRIDDEN); 

        // If the casrId is 0L, then its a new customer referral so we just need to check if there is duplicate

        if ( customerReferral.getCsrId() == null || customerReferral.getCsrId() == 0L ) {

            // If the exCustomerReferral is not null, then return true
            if ( exCustomerReferral != null ) {

                return true;

            }

        } else {

            // Check if the exCustomerReferral is null
            if ( exCustomerReferral != null && customerReferral.getCsrId().longValue() != exCustomerReferral.getCsrId().longValue() ) {

                return true;

            }
        }


        // Return false;
        return false;
    }

    @Override
    public CustomerReferral saveCustomerReferralThroughMerchantPortal(CustomerReferralResource customerReferralResource) throws InspireNetzException {

        //get the user information
        User user =userService.findByUsrUserNo(authSessionUtils.getUserNo());


        //map the referral object
        CustomerReferral customerReferral=mapper.map(customerReferralResource,CustomerReferral.class);

        Long merchantNo =authSessionUtils.getMerchantNo();

        //set merchant number
        customerReferral.setCsrMerchantNo(merchantNo);

        if(customerReferral.getCsrUserNo()==null&&customerReferralResource.getCsrUserFName()!=null&&!customerReferralResource.getCsrUserFName().equals("")){

            User userDetails =userService.findByUsrFNameAndUsrMerchantNo(customerReferralResource.getCsrUserFName(),customerReferral.getCsrMerchantNo());

            if(userDetails!=null&&userDetails.getUsrUserNo()!=null){

                customerReferral.setCsrUserNo(userDetails.getUsrUserNo());
            }
        }
        if(customerReferral.getCsrLocation()==null&&customerReferralResource.getCsrLocationName()!=null&&!customerReferralResource.getCsrLocationName().equals("")){

            MerchantLocation merchantLocation =merchantLocationService.findByMelMerchantNoAndMelLocation(customerReferral.getCsrMerchantNo(), customerReferralResource.getCsrLocationName());

            if(merchantLocation!=null&&merchantLocation.getMelId()!=null){

                customerReferral.setCsrLocation(merchantLocation.getMelId());
            }
        }

        return validateCustomerReferral(customerReferral, user);
    }

    @Override
    public CustomerReferral validateCustomerReferral(CustomerReferral customerReferral, User user) throws InspireNetzException {



        //boolean send message
        boolean sendReferMessage =false;


        String auditDetails = user.getUsrUserNo().toString();


        //create a hashmap for sms parameters
        HashMap<String , String > smsParams = new HashMap<>(0);


        //put the refereeMin into smsparams
        smsParams.put("#referrerMin",customerReferral.getCsrLoyaltyId());

        //put referrerMin in sms param
        smsParams.put("#refereeMin",customerReferral.getCsrRefMobile());

        if(customerReferral.getCsrLoyaltyId()==null||customerReferral.getCsrLoyaltyId().equals("")){

            // Log the response
            log.info("validateCustomerReferral  : no customer found");

            // Thrown not found exception
            throw new InspireNetzException(APIErrorCode.ERR_NO_DATA_FOUND);
        }

        Customer referrer=customerService.findByCusLoyaltyIdAndCusMerchantNo(customerReferral.getCsrLoyaltyId(),customerReferral.getCsrMerchantNo());

        if(referrer==null){

            // Log the response
            log.info("validateCustomerReferral  : no customer found");

            // Thrown not found exception
            throw new InspireNetzException(APIErrorCode.ERR_NO_DATA_FOUND);
        }

        customerReferral.setCsrFName(referrer.getCusFName());

        //check the referral field is update
        if(customerReferral.getCsrId() ==null){

            //check referee is valid for transaction
            boolean checkReferralValidity =checkReferralValidity(customerReferral,user);

            //set customer referral status new
            customerReferral.setCsrRefStatus(CustomerReferralStatus.NEW);

            //for first time creation
            sendReferMessage =true;

            customerReferral.setCreatedBy(auditDetails);

        }else {

            customerReferral.setUpdatedBy(auditDetails);
        }


        customerReferral= validateAndSaveCustomerReferral(customerReferral);

        if(customerReferral==null || customerReferral.getCsrId()==0L){

            // Log the response
            log.info("getRedemptionInfo - Response : operation failed");

            // Thrown not found exception
            throw new InspireNetzException(APIErrorCode.ERR_OPERATION_FAILED);

        }

        //Award Successfull referral bonus
        processAwardingOnSuccessfulReferral(customerReferral);

        //send message one time
        if(sendReferMessage){

            MessageWrapper messageWrapper = generalUtils.getMessageWrapperObject("",customerReferral.getCsrLoyaltyId(),"","","",customerReferral.getCsrMerchantNo(),smsParams,MessageSpielChannel.ALL,IndicatorStatus.YES);

            messageWrapper.setSpielName(MessageSpielValue.CUSTOMER_REFERRAL_REFERRER_RESPONSE);

            userMessagingService.transmitNotification(messageWrapper);

            MessageWrapper messageWrapper1 = generalUtils.getMessageWrapperObject("","",customerReferral.getCsrRefMobile(),customerReferral.getCsrRefEmail(),"",customerReferral.getCsrMerchantNo(),smsParams,MessageSpielChannel.ALL,IndicatorStatus.NO);

            messageWrapper1.setSpielName(MessageSpielValue.CUSTOMER_REFERRAL_REFEREE_RESPONSE);

           /* messageWrapper1.setLoyaltyId(customerReferral.getCsrRefMobile());

            messageWrapper1.setIsCustomer(IndicatorStatus.NO);*/

            userMessagingService.transmitNotification(messageWrapper1);
        }


        return customerReferral;

    }

    @Override
    public CustomerReferral validateAndSaveCustomerReferral(CustomerReferral customerReferral) throws InspireNetzException{

        // Create the Validator
        CustomerReferralValidator validator = new CustomerReferralValidator(dataValidationUtils);

        // Create the bindingREsult
        BeanPropertyBindingResult result = new BeanPropertyBindingResult(customerReferral,"customerReferral");

        // Validate the request
        validator.validate(customerReferral,result);

        //create a hashmap for sms parameters
        HashMap<String , String > smsParams = new HashMap<>(0);

        MessageWrapper messageWrapper = generalUtils.getMessageWrapperObject("",customerReferral.getCsrLoyaltyId(),"","","",customerReferral.getCsrMerchantNo(),smsParams,MessageSpielChannel.ALL,IndicatorStatus.YES);

        // Check if the result contains error
        if ( result.hasErrors() ) {

            // Log the response
            log.info("validateAndSaveCustomerReferral - Response : Invalid Input - ");

            messageWrapper.setSpielName(MessageSpielValue.ERR_INVALID_PATTERN);


            userMessagingService.transmitNotification(messageWrapper);

            // Throw InspireNetzException with INVALID_INPUT as error
            throw new InspireNetzException(APIErrorCode.ERR_INVALID_INPUT);

        }

        //check if duplicate exists
       if(isDuplicateReferralExisting(customerReferral)){

            // Log the response
            log.info("validateAndSaveCustomerReferral - Response : Duplicate Entry - ");


           messageWrapper.setSpielName(MessageSpielValue.CUSTOMER_REFERRAL_ALREADY_REFERRED);

           userMessagingService.transmitNotification(messageWrapper);

            // Throw InspireNetzException with INVALID_INPUT as error
            throw new InspireNetzException(APIErrorCode.ERR_DUPLICATE_ENTRY);

        }


        //check it is duplicate referral
        boolean refereeDuplicate = checkDuplicateReferral(customerReferral);

        if(refereeDuplicate){

            log.info("checkReferralValidity:duplicate entry");

            throw new InspireNetzException(APIErrorCode.ERR_REFEREE_DUPLICATE);
        }

        //save customer referral object
        CustomerReferral savedCustomerReferral=saveCustomerReferral(customerReferral);

        return savedCustomerReferral;
    }

    @Override
    public CustomerReferral findByCsrId(Long csrId) {

        // Get the customer referral  for the given csr id from the repository
        CustomerReferral customerReferral = customerReferralRepository.findByCsrId(csrId);

        //return customer referral object
        return customerReferral;
    }

    @Override
    public Page<CustomerReferral> findByCsrMerchantNoAndCsrLoyaltyIdOrderByCsrRefTimeStampDesc(Long csrMerchantNo, String csrLoyaltyId, Pageable pageable) {

        // Get the customer referral list for the given merchantNo and Loyalty Id from the repository
        Page<CustomerReferral> customerReferrals = customerReferralRepository.findByCsrMerchantNoAndCsrLoyaltyIdOrderByCsrRefTimeStampDesc(csrMerchantNo, csrLoyaltyId, pageable);

        //return  list of customer referrals
        return customerReferrals;
    }

    @Override
    public List<CustomerReferral> findByCsrMerchantNoAndCsrLoyaltyIdOrderByCsrRefTimeStampDesc(Long csrMerchantNo, String csrLoyaltyId) {

        // Get the customer referral list for the given merchantNo and Loyalty Id from the repository
        List<CustomerReferral> customerReferrals = customerReferralRepository.findByCsrMerchantNoAndCsrLoyaltyIdOrderByCsrRefTimeStampDesc(csrMerchantNo, csrLoyaltyId);

        //return  list of customer referrals
        return customerReferrals;
    }

    @Override
    public List<CustomerReferral> findByCsrMerchantNoAndCsrLoyaltyIdAndCsrRefNameLike(Long csrMerchantNo,String csrLoyaltyId, String csrRefName) {
        return customerReferralRepository.findByCsrMerchantNoAndCsrLoyaltyIdAndCsrRefNameLike(csrMerchantNo, csrLoyaltyId, csrRefName);
    }

    @Override
    public List<CustomerReferral> findByCsrMerchantNoAndCsrLoyaltyIdAndCsrRefMobileLike(Long csrMerchantNo, String csrLoyaltyId,String csrRefMobile) {
        return customerReferralRepository.findByCsrMerchantNoAndCsrLoyaltyIdAndCsrRefMobileLike(csrMerchantNo, csrLoyaltyId, csrRefMobile);
    }

    @Override
    public CustomerReferral validateCustomerReferralThroughCustomer(CustomerReferral customerReferral) throws InspireNetzException {

        //boolean message to send referral message
        boolean referralMessage =false;

        //get user information
        User user = userService.findByUsrUserNo(authSessionUtils.getUserNo());

        //check the user type of customer
        if(authSessionUtils.getUserType() !=UserType.CUSTOMER){

            log.info("Customer Referral Service Impl:validateCustomerReferralThroughCustomer: invalid user"+authSessionUtils.getUserType());

            //throw error if its invalid
            throw new InspireNetzException(APIErrorCode.ERR_NOT_AUTHORIZED);
        }

        //find out the customer details get active customer
        Customer customer =customerService.findByCusUserNoAndCusMerchantNoAndCusStatus(authSessionUtils.getUserNo(),customerReferral.getCsrMerchantNo(),CustomerStatus.ACTIVE);

        //check customer is present or active or not
        if(customer ==null){

            log.info("Customer Referral Service Impl:validateCustomerReferralThroughCustomer: invalid Loyalty id");

            throw new InspireNetzException(APIErrorCode.ERR_NO_LOYALTY_ID);

        }


        //check its id is null or
        if(customerReferral.getCsrId() ==null){

            //set referrer information
            customerReferral.setCsrLoyaltyId(customer.getCusLoyaltyId());

            //set customer referral name
            customerReferral.setCsrFName(customer.getCusFName());

            //set location also
            customerReferral.setCsrLocation(customer.getCusLocation()==null?0:customer.getCusLocation());

            //boolean check validity of customer
            boolean checkReferralValidity =checkReferralValidity(customerReferral,user);

            //check it is duplicate referral
            boolean refereeDuplicate = checkDuplicateReferral(customerReferral);

            if(refereeDuplicate){

                log.info("checkReferralValidity:duplicate entry");

                throw new InspireNetzException(APIErrorCode.ERR_REFEREE_DUPLICATE);
            }


            //set status is new
            customerReferral.setCsrRefStatus(CustomerReferralStatus.NEW);

            //add user information
            customerReferral.setCsrUserNo(customer.getCusMerchantUserRegistered());

            referralMessage =true;

        }

        //save the data
        customerReferral =saveCustomerReferral(customerReferral);

        //Award Successful referral bonus
        processAwardingOnSuccessfulReferral(customerReferral);

        if(referralMessage){

            //create a hashmap for sms parameters
            HashMap<String , String > smsParams = new HashMap<>(0);

            //put the refereeMin into smsparams
            smsParams.put("#referrerMin",customerReferral.getCsrLoyaltyId());
            smsParams.put("#referrerName",customerReferral.getCsrFName());

            //put referrerMin in sms param
            smsParams.put("#refereeMin",customerReferral.getCsrRefMobile());
            smsParams.put("#refereeName",customerReferral.getCsrRefName());

            MessageWrapper messageWrapper = generalUtils.getMessageWrapperObject("",customerReferral.getCsrLoyaltyId(),"","","",customerReferral.getCsrMerchantNo(),smsParams,MessageSpielChannel.ALL,IndicatorStatus.YES);

            messageWrapper.setSpielName(MessageSpielValue.CUSTOMER_REFERRAL_REFERRER_RESPONSE);

            userMessagingService.transmitNotification(messageWrapper);

            MessageWrapper messageWrapper1 = generalUtils.getMessageWrapperObject("","",customerReferral.getCsrRefMobile(),customerReferral.getCsrRefEmail(),"",customerReferral.getCsrMerchantNo(),smsParams,MessageSpielChannel.ALL,IndicatorStatus.NO);

            messageWrapper1.setSpielName(MessageSpielValue.CUSTOMER_REFERRAL_REFEREE_RESPONSE);


            userMessagingService.transmitNotification(messageWrapper1);

        }


        //return referral information
        return customerReferral;
    }

    private boolean checkDuplicateReferral(CustomerReferral customerReferral) {

        //check referral is already in table
        List<CustomerReferral> existingReferralList = customerReferralRepository.findByCsrMerchantNoAndCsrRefMobileAndCsrRefStatusNotOrderByCsrRefTimeStampAsc(customerReferral.getCsrMerchantNo(), customerReferral.getCsrRefMobile(), CustomerReferralStatus.OVERRIDDEN);


        if(existingReferralList.size()>0 &&(customerReferral.getCsrId()==null ||customerReferral.getCsrId()==0L)){

            return true;
        }

        for(CustomerReferral existingReferral:existingReferralList){

            if(existingReferral.getCsrId()!=customerReferral.getCsrId()){

                return true;
            }
        }

        // Return false;
        return false;
    }

    @Override
    public List<CustomerReferral> findByCsrMerchantNoAndCsrRefMobileAndCsrRefStatusOrderByCsrRefTimeStampAsc(Long csrMerchantNo, String csrRefMobile, Integer csrStatus) {

        // Get the customer referral list for the given merchantNo and Loyalty Id from the repository
        List<CustomerReferral> customerReferrals=new ArrayList<>();

        customerReferrals = customerReferralRepository.findByCsrMerchantNoAndCsrRefMobileAndCsrRefStatusOrderByCsrRefTimeStampAsc(csrMerchantNo, csrRefMobile, csrStatus);

        //return  customer referral object
        return customerReferrals;
    }

    @Override
    public List<CustomerReferral> findByCsrMerchantNoAndCsrRefMobileAndCsrRefStatusNotOrderByCsrRefTimeStampAsc(Long csrMerchantNo, String csrRefMobile, Integer csrStatus) {

        // Get the customer referral list for the given merchantNo and Loyalty Id from the repository
        List<CustomerReferral> customerReferrals=new ArrayList<>();

        customerReferrals = customerReferralRepository.findByCsrMerchantNoAndCsrRefMobileAndCsrRefStatusNotOrderByCsrRefTimeStampAsc(csrMerchantNo, csrRefMobile, csrStatus);

        //return  customer referral object
        return customerReferrals;
    }

    @Override
    public List<CustomerReferral> findByCsrMerchantNoAndCsrRefMobileOrderByCsrRefTimeStampAsc(Long csrMerchantNo, String csrRefMobile) {

        List<CustomerReferral> customerReferrals = customerReferralRepository.findByCsrMerchantNoAndCsrRefMobileOrderByCsrRefTimeStampAsc(csrMerchantNo,csrRefMobile);

        return customerReferrals;
    }

    @Override
    public boolean deleteCustomerReferral(Long csrId) throws InspireNetzException {

        customerReferralRepository.delete(csrId);

        // return true
        return true;
    }

    @Override
    public CustomerReferral saveCustomerReferral(CustomerReferral customerReferral) {
        return customerReferralRepository.save(customerReferral);
    }


    public CustomerReferral findByCsrMerchantNoAndCsrLoyaltyIdAndCsrRefMobile(Long csrMerchantNo, String csrLoyaltyId, String csrRefMobile) {
        return customerReferralRepository.findByCsrMerchantNoAndCsrLoyaltyIdAndCsrRefMobile(csrMerchantNo, csrLoyaltyId, csrRefMobile);
    }

    @Override
    public Page<CustomerReferral> searchReferral(String filter, String query, Pageable pageable) {

        // Variable holding the page
        Page<CustomerReferral> customerReferralPage = null;

        //merchant number
        Long merchantNo =authSessionUtils.getMerchantNo();

        //user type
        Integer userType =authSessionUtils.getUserType();

        //get user number
        Long userNo =authSessionUtils.getUserNo();

        // First check the filter and query
        if ( filter.equals("referrer") ) {

            if(userType.intValue() == UserType.MERCHANT_ADMIN){

                // Get the data specific to name
                customerReferralPage = customerReferralRepository.findByCsrMerchantNoAndCsrLoyaltyIdLike(merchantNo,"%"+query+"%",pageable);

            }else  if(userType.intValue() == UserType.MERCHANT_USER){

                //get merchant user based filter
                customerReferralPage=customerReferralRepository.findByCsrMerchantNoAndCsrUserNoAndCsrLoyaltyIdLike(merchantNo,userNo,"%"+query+"%",pageable);

            }



        } else if (filter.equals("referee")){

            // GEt all the data
            if(userType.intValue() == UserType.MERCHANT_ADMIN){

                // Get the data specific to name
                customerReferralPage = customerReferralRepository.findByCsrMerchantNoAndCsrRefMobileLike(merchantNo, "%" + query + "%", pageable);

            }else  if(userType.intValue() == UserType.MERCHANT_USER){

                //get merchant user based filter
                customerReferralPage=customerReferralRepository.findByCsrMerchantNoAndCsrUserNoAndCsrRefMobileLike(merchantNo, userNo, "%" + query + "%", pageable);

            }
        }else if(filter.equals("0") && query.equals("0")) {


            // GEt all the data
            if(userType.intValue() == UserType.MERCHANT_ADMIN){

                // Get the data specific to name
                customerReferralPage = customerReferralRepository.findByCsrMerchantNo(merchantNo, pageable);

            }else  if(userType.intValue() == UserType.MERCHANT_USER){

                //get merchant user based filter
                customerReferralPage=customerReferralRepository.findByCsrMerchantNoAndCsrUserNo(merchantNo,userNo, pageable);

            }
        }


        // Return the page object
        return customerReferralPage;
    }

    @Override
    public Page<CustomerReferral> searchReferralForCustomerPortal(String filter, String query,Long merchantNo, Pageable pageable) throws InspireNetzException {

        //get user number
        Long userNo =authSessionUtils.getUserNo();

        List<Customer> customerList =new ArrayList<>();

        //get of customer information
        if(merchantNo ==0L){

             //Customer list
             customerList =customerService.findByCusUserNoAndCusStatus(userNo, CustomerStatus.ACTIVE);

        }else {

             //pick the customer information about exclusive portal
             Customer customer = customerService.findByCusUserNoAndCusMerchantNoAndCusStatus(userNo,merchantNo,CustomerStatus.ACTIVE);

             customerList.add(customer);
        }


        //initialize pageable parameter
        Page<CustomerReferral> customerReferralPage=null;

        //initialize array list
        List<CustomerReferral> customerReferralList =new ArrayList<>();

        //initialize list
        List<CustomerReferral> customerReferralList1 =null;

        //check customer is connected with any merchant
        if(customerList ==null ||customerList.size() ==0){

            log.info("Customer Referral service impl:searchReferralForCustomerPortal::No membership");

            throw new InspireNetzException(APIErrorCode.ERR_NOT_CONNECTED);

        }

        for (Customer customer:customerList){

            //check the filter and query condition
            if(filter.equals("0") && query.equals("0")){

                //pick all reference
                 customerReferralList1 =findByCsrMerchantNoAndCsrLoyaltyIdOrderByCsrRefTimeStampDesc(customer.getCusMerchantNo(),customer.getCusLoyaltyId());


            }else if(filter.equals("name") ){

                //get details
                customerReferralList1 =findByCsrMerchantNoAndCsrLoyaltyIdAndCsrRefNameLike(customer.getCusMerchantNo(),customer.getCusLoyaltyId(),"%"+query+"%");

            }else if(filter.equals("mobile")){

                //get customer details
                customerReferralList1 =findByCsrMerchantNoAndCsrLoyaltyIdAndCsrRefMobileLike(customer.getCusMerchantNo(),customer.getCusLoyaltyId(),"%"+query+"%");

            }

            //iterate add customer list into referral
            for (CustomerReferral customerReferral:customerReferralList1){

                //add into customer referral list
                customerReferralList.add(customerReferral);
            }

        }

        //convert list of item into pageable Parameter
        customerReferralPage =new PageImpl<>(customerReferralList);

        //return data
        return customerReferralPage;
    }

    //check validity of referral
    public boolean checkReferralValidity(CustomerReferral customerReferral,User user) throws InspireNetzException {

        MessageWrapper messageWrapper = generalUtils.getMessageWrapperObject("",customerReferral.getCsrLoyaltyId(),"","","",customerReferral.getCsrMerchantNo(),new HashMap<String,String >(),MessageSpielChannel.ALL,IndicatorStatus.YES);

        //first check duplicate referral
        boolean isDuplicateExisting =isDuplicateReferralExisting(customerReferral);

        //duplicate referral throw error
        if(isDuplicateExisting){

            log.info("checkReferralValidity:duplicate entry");

            throw new InspireNetzException(APIErrorCode.ERR_DUPLICATE_ENTRY);
        }

        int referralRewardBasis = getReferralSettingForMerchant(customerReferral.getCsrMerchantNo());

        if(referralRewardBasis == MerchantReferralSetting.CUSTOMER_BASED){

            //check if the customer is already referred by someone else
            boolean isDuplicate=checkDuplicateReferral(customerReferral);

            if(isDuplicate){

                log.error("checkReferralValidity  : Customer already referred "+customerReferral);

                return false;

            }

           /* List<CustomerReferral > customerReferrals = findByCsrMerchantNoAndCsrRefMobileAndCsrRefStatusOrderByCsrRefTimeStampAsc(customerReferral.getCsrMerchantNo(), customerReferral.getCsrRefMobile(), CustomerReferralStatus.NEW);

            if(customerReferral != null && customerReferrals.size() > 0 ){

              log.error("checkReferralValidity  : Customer already referred "+customerReferral);

                return false;
            }*/
        }

        //create a hashmap for sms parameters
        HashMap<String , String > smsParams = new HashMap<>(0);

        //put the refereeMin into smsparams
        smsParams.put("#referrerMin",customerReferral.getCsrLoyaltyId());

        //put referrerMin in sms param
        smsParams.put("#refereeMin",customerReferral.getCsrRefMobile());


        //check referrer is valid for transaction
        boolean isReferrerValid=customerService.isCustomerValidForTransaction(customerReferral.getCsrLoyaltyId(),customerReferral.getCsrMerchantNo());


        if (!isReferrerValid) {

            // Log the response
            log.info("checkReferralValidity - Response : Error in valid referrer");

            messageWrapper.setIsCustomer(IndicatorStatus.NO);


            messageWrapper.setSpielName(MessageSpielValue.CUSTOMER_NOT_REGISTERED);

            messageWrapper.setMobile(customerReferral.getCsrLoyaltyId());

            messageWrapper.setChannel(MessageSpielChannel.SMS);

            messageWrapper.setParams(smsParams);

            userMessagingService.transmitNotification(messageWrapper);

            // Thrown not found exception
            throw new InspireNetzException(APIErrorCode.ERR_NO_LOYALTY_ID);

        }


        boolean isAuthenticated = authSessionUtils.isFunctionAccessAllowed(FunctionCode.FNC_REFERRAL_REFFEREE_ACTIVE,user);

        if(!isAuthenticated){

            //check referee is already in loyalty member throw error
            boolean isRefereeValid=customerService.isCustomerValidForTransaction(customerReferral.getCsrRefMobile(),customerReferral.getCsrMerchantNo());

            if (isRefereeValid) {

                // Log the response
                log.info("CustomerReferralServiceImpl :checkReferralValidity- Response : Referee already a member");


                messageWrapper.setSpielName(MessageSpielValue.CUSTOMER_REFERRAL_ALREADY_LOYALTY_MEMBER);

                messageWrapper.setIsCustomer(IndicatorStatus.YES);

                messageWrapper.setChannel(MessageSpielChannel.ALL);

                messageWrapper.setParams(smsParams);

                userMessagingService.transmitNotification(messageWrapper);

                // Thrown not found exception
                throw new InspireNetzException(APIErrorCode.ERR_REFEREE_ACTIVE);

            }
        }


        //check its duplicate referral
        return true;

    }


    @Override
    public int getReferralSettingForMerchant(Long cusMerchantNo) {

        //get settings id
        Long settingId = settingService.getSettingsId(AdminSettingsConfiguration.MER_REFERRAL_BASIS);

        //get the link string of the merchant sms configuration
        MerchantSetting merchantSetting = merchantSettingService.findByMesMerchantNoAndMesSettingId(cusMerchantNo,settingId);

        int referralSetting = 0 ;

        if(null != merchantSetting ){

            referralSetting = Integer.parseInt(merchantSetting.getMesValue());

        }

        return referralSetting;

    }

    @Override
    public CustomerReferral findByCsrMerchantNoAndCsrRefMobile(Long csrMerchantNo, String csrRefMobile) {
        return customerReferralRepository.findByCsrMerchantNoAndCsrRefMobile(csrMerchantNo, csrRefMobile);
    }

    @Override
    public void saveReferralDataFromXmlFile(List<CustomerReferral> customerReferralList,Long userNo) {

        //get the user information
        User user =userService.findByUsrUserNo(userNo);

        //process the referral data
        try{

            for (CustomerReferral customerReferral :customerReferralList){

                //validate and save customer referral information
                CustomerReferral customerReferral1 = validateCustomerReferral(customerReferral, user);
            }

        }catch (InspireNetzException ex){


            log.info("saveReferralDataFromXmlFile processing Inspirenetz Exception:"+ex);

        }catch (Exception e){

            log.info("System Exception :"+e);
        }

    }

    @Override
    public List<CustomerReferral> findByCsrMerchantNoAndCsrLoyaltyIdAndCsrRefTimeStampBetween(Long csrMerchantNo, String csrLoyaltyId, Date fromDate, Date toDate) {


        //conversion of time stamp
        Timestamp timestamp =null;

        Timestamp toTimeStamp =null;

        //check the from date
        if(fromDate !=null){

            //set conversion date to time stamp
            Calendar calendar =Calendar.getInstance();
            calendar.setTime(fromDate);

            //from date to add 00:00:00
            timestamp =new Timestamp(calendar.getTimeInMillis());
            timestamp.setHours(0);
            timestamp.setMinutes(0);
            timestamp.setSeconds(0);

        }

        if(toDate !=null){

            //set conversion date to time stamp
            Calendar calendar1 =Calendar.getInstance();
            calendar1.setTime(toDate);

            //to date add 23:59:59
            toTimeStamp =new Timestamp(calendar1.getTimeInMillis());
            toTimeStamp.setHours(23);
            toTimeStamp.setMinutes(59);
            toTimeStamp.setSeconds(59);

        }



        return customerReferralRepository.findByCsrMerchantNoAndCsrLoyaltyIdAndCsrRefTimeStampBetween(csrMerchantNo,csrLoyaltyId,timestamp,toTimeStamp);
    }





    @Override
    public CustomerReferral saveCustomerReferralWithPriority(CustomerReferral customerReferral)throws InspireNetzException{

        List<CustomerReferral> customerReferrals= findByCsrMerchantNoAndCsrRefMobileAndCsrRefStatusOrderByCsrRefTimeStampAsc(customerReferral.getCsrMerchantNo(), customerReferral.getCsrRefMobile(), CustomerReferralStatus.NEW);

        for(CustomerReferral fetchedCustomerReferral:customerReferrals){

            fetchedCustomerReferral.setCsrRefStatus(CustomerReferralStatus.OVERRIDDEN);

            saveCustomerReferral(fetchedCustomerReferral);
        }

        customerReferral= validateAndSaveCustomerReferral(customerReferral);

        return customerReferral;
    }


    @Override
    public List<CustomerReferral> findByCsrMerchantNoAndCsrLoyaltyIdAndCsrEarnedStatus(Long csrMerchantNo, String csrLoyaltyId, boolean csrEarnedStatus){

        List<CustomerReferral> customerReferrals = customerReferralRepository.findByCsrMerchantNoAndCsrLoyaltyIdAndCsrEarnedStatus(csrMerchantNo,csrLoyaltyId,csrEarnedStatus);

        return customerReferrals;

    }

    @Override
    public void processAwardingOnSuccessfulReferral(CustomerReferral customerReferral) throws  InspireNetzException{

        //get the referrer details
        Customer referrer = customerService.findByCusLoyaltyIdAndCusMerchantNo(customerReferral.getCsrLoyaltyId(),customerReferral.getCsrMerchantNo());

        // Create the UniqueIdGenerator
        String uniqueID = UUID.randomUUID().toString();

        //check if the customerReferral added successfully
        if (referrer != null && referrer.getCusCustomerNo() != 0l){

           //Call the reward Activity program
            CustomerRewardActivity customerRewardActivity= this.customerRewardActivityService.validateAndRegisterCustomerRewardActivity(referrer.getCusCustomerNo(),CustomerRewardingType.ADD_REFERRAL,referrer.getCusLoyaltyId()+"#"+uniqueID);

        }



    }


    @Override
    public void inject(CustomerRewardUtils beansManager) {

        this.customerRewardActivityService = beansManager.getCustomerRewardActivityService();

    }
}



