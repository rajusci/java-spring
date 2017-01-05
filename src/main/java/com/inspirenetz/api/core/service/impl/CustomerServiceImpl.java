package com.inspirenetz.api.core.service.impl;

import com.google.common.io.CountingOutputStream;
import com.inspirenetz.api.core.dictionary.*;
import com.inspirenetz.api.core.domain.*;
import com.inspirenetz.api.core.domain.validator.CustomerProfileValidator;
import com.inspirenetz.api.core.domain.validator.CustomerValidator;
import com.inspirenetz.api.core.repository.BaseRepository;
import com.inspirenetz.api.core.repository.CustomerRepository;
import com.inspirenetz.api.core.service.*;
import com.inspirenetz.api.rest.exception.InspireNetzException;
import com.inspirenetz.api.rest.resource.CustomerProfileResource;
import com.inspirenetz.api.rest.resource.CustomerResource;
import com.inspirenetz.api.rest.resource.CustomerRewardBalanceResource;
import com.inspirenetz.api.rest.resource.MembershipResource;
import com.inspirenetz.api.util.*;
import com.inspirenetz.api.util.integration.IntegrationUtils;
import org.dozer.Mapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.validation.BeanPropertyBindingResult;

import java.io.FileOutputStream;
import java.io.InputStream;
import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Created by sandheepgr on 16/2/14.
 */
@Service
public class CustomerServiceImpl extends BaseServiceImpl<Customer> implements CustomerService,InjectableReward {

    private static Logger log = LoggerFactory.getLogger(CustomerServiceImpl.class);

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    CustomerReferralService customerReferralService;

    CustomerRewardActivityService customerRewardActivityService;

    @Autowired
    private MerchantSettingService merchantSettingService;

    @Autowired
    private AuthSessionUtils authSessionUtils;

    @Autowired
    private DataValidationUtils dataValidationUtils;

    @Autowired
    private SettingService settingService;

    @Autowired
    CodedValueService codedValueService;

    @Autowired
    private AccountBundlingUtils accountBundlingUtils;

    @Autowired
    private CustomerProfileService customerProfileService;

    @Autowired
    private AccountTransferService accountTransferService;

    @Autowired
    private RewardCurrencyService rewardCurrencyService;

    @Autowired
    DrawChanceService drawChanceService;

    @Autowired
    private GeneralUtils generalUtils;

    @Autowired
    private TierService tierService;

    @Autowired
    private TierGroupService tierGroupService;



    @Autowired
    MerchantService merchantService;

    @Autowired
    UserService userService;

    @Autowired
    OneTimePasswordService oneTimePasswordService;

    @Autowired
    UserMessagingService userMessagingService;

    @Autowired
    CustomerRewardExpiryService customerRewardExpiryService;

    @Autowired
    CatalogueService catalogueService;

    @Autowired
    LinkRequestService linkRequestService;

    @Autowired
    CustomerActivityService customerActivityService;

    @Autowired
    ImageService imageService;

    @Autowired
    CustomerRewardBalanceService customerRewardBalanceService;

    @Autowired
    TransactionService transactionService;

    @Autowired
    RedemptionService redemptionService;

    @Autowired
    AccumulatedRewardBalanceService accumulatedRewardBalanceService;

    @Autowired
    LinkedRewardBalanceService linkedRewardBalanceService;

    @Autowired
    private Mapper mapper;

    @Autowired
    private Environment environment;

    @Autowired
    NotificationService notificationService;

    // Define the date format
    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd");

    public CustomerServiceImpl(){
        super(Customer.class);
    }


    @Override
    public Customer findByCusLoyaltyIdAndCusMerchantNo(String cusLoyaltyId,Long cusMerchantNo) {

        // Get customer object
        Customer customer = customerRepository.findByCusLoyaltyIdAndCusMerchantNo(cusLoyaltyId, cusMerchantNo);

        // REturn the customer
        return customer;

    }

    @Override
    public Customer findByCusMobileAndCusMerchantNo(String cusMobile,Long cusMerchantNo) {

        // Get customer object
        Customer customer = customerRepository.findByCusMobileAndCusMerchantNo(cusMobile, cusMerchantNo);

        // REturn the customer
        return customer;

    }

    @Override
    public Customer findByCusEmailAndCusMerchantNo(String cusEmail,Long cusMerchantNo) {

        // Get customer object
        Customer customer = customerRepository.findByCusEmailAndCusMerchantNo(cusEmail, cusMerchantNo);

        // REturn the customer
        return customer;

    }

    @Override
    public Customer findByCusCustomerNo(Long cusCustomerNo) {

        // Get customer object
        Customer customer = customerRepository.findByCusCustomerNo(cusCustomerNo);

        // REturn the customer
        return customer;

    }

    @Override
    public Page<Customer> findByCusMerchantNo(Long cusMerchantNo, Pageable pageable) {

        // Get the list of customers
        Customer customer =new Customer();

        //set merchant number
        customer.setCusMerchantNo(cusMerchantNo);

        //page
        Page<Customer> customerPage=null;
        //user location
        Long userLocation =authSessionUtils.getUserLocation()==null?0L:authSessionUtils.getUserLocation();

        //get merchant settings
        boolean merchantSettings =getMerchantSettings(AdminSettingsConfiguration.MER_ENABLE_USR_FILTER, customer);

        if(merchantSettings){

           customerPage =findByCusMerchantNoAndCusLocationAndCusMerchantUserRegistered(cusMerchantNo,userLocation,authSessionUtils.getUserNo(),pageable);

        }else {

            if(userLocation.longValue() ==0L){

                customerPage = customerRepository.findByCusMerchantNo(cusMerchantNo, pageable);

            }else {

                customerPage = customerRepository.findByCusMerchantNoAndCusLocationOrCusLocation(cusMerchantNo,0L, userLocation, pageable);
            }
        }



        // Return the customerPage object
        return customerPage;

    }

    @Override
    public Page<Customer> findByCusFNameLikeAndCusMerchantNo(String cusFName, Long cusMerchantNo, Pageable pageable) {

        // Get the Customer page
        Page<Customer> customerPage = customerRepository.findByCusFNameLikeAndCusMerchantNo(cusFName, cusMerchantNo, pageable);

        // Return the customerPage
        return customerPage;

    }

    @Override
    public Page<Customer> findByCusLNameLikeAndCusMerchantNo(String cusLName, Long cusMerchantNo, Pageable pageable) {

        // Get the Customer page
        Page<Customer> customerPage = customerRepository.findByCusLNameLikeAndCusMerchantNo(cusLName, cusMerchantNo, pageable);

        // Return the customerPage
        return customerPage;

    }

    @Override
    public List<Customer> findByCusUserNoOrCusEmailOrCusMobile(Long cusUserNo, String cusEmail, String cusMobile) {

        // Check for the values passed and if they are null or empty ,then we need to
        // make them as non existing value so as not to retrieve the information for other
        // or unintended customers
        if ( cusUserNo == null || cusUserNo == 0L ) {

            // Set the userNo to the ignore value
            cusUserNo = DBUtils.LONG_IGNORE_COMPARE_FIELD_CONTENT;

        }


        // Check for the email field
        if ( cusEmail == null || cusEmail.trim().equals("") ) {

            // Set the cusEmail to the ignore value
            cusEmail = DBUtils.STRING_IGNORE_COMPARE_FIELD_CONTENT;

        }


        // Check for the mobile field
        if ( cusMobile == null || cusMobile.trim().equals("")  ) {

            // Set the cusMobile to ignore value
            cusMobile = DBUtils.STRING_IGNORE_COMPARE_FIELD_CONTENT;

        }



        // Get the customer list
        List<Customer> customerList = customerRepository.findByCusUserNoOrCusEmailOrCusMobileAndCusRegisterStatus(cusUserNo, cusEmail, cusMobile, IndicatorStatus.YES);

        // Return the customerlist
        return customerList;

    }

    @Override
    public List<Customer> findByCusUserNo(Long cusUserNo) {

        // Return the value from the repository methods
        return customerRepository.findByCusUserNo(cusUserNo);


    }

    @Override
    public List<Customer> getCustomerDetailsBasedOnUserNo(Long cusUserNo) {

        return customerRepository.findByCusUserNoAndCusStatus(cusUserNo, CustomerStatus.ACTIVE);
    }

    @Override
    public List<Customer> getCustomerDetails(Long cusMerchantNo) {

        //return customer details
        return customerRepository.findByCusMerchantNo(cusMerchantNo);


    }

    @Override
    public  boolean updateCustomerUser(Long merchantNo,String loyaltyId ,Long cusUserNo ) {

        // Get the customer information
        Customer customer = customerRepository.findByCusLoyaltyIdAndCusMerchantNo(loyaltyId, merchantNo);

        // Check if the customer is found
        if ( customer != null ) {

            // Set the cusUserNO
            customer.setCusUserNo(cusUserNo);

            // Update the customer
            customer = saveCustomer(customer);

            // return true
            return true;

        }



        // Return false
        return false;

    }

    @Override
    public Page<Customer> searchCustomers(String searchField, String query, Pageable pageable ) {

        // Get the merchant number and store
        Long merchantNo = authSessionUtils.getMerchantNo();

        //create customer object
        Customer customerObject =new Customer();

        //get user location
        Long userLocation =authSessionUtils.getUserLocation()==null?0L:authSessionUtils.getUserLocation();

        //user number
        Long userNo =authSessionUtils.getUserNo();

        //for skipping search option in admin
        int userType =authSessionUtils.getUserType();

        //set merchant number
        customerObject.setCusMerchantNo(merchantNo);

        //get merchant settings of the customer
        boolean merchantSetting =getMerchantSettings(AdminSettingsConfiguration.MER_ENABLE_USR_FILTER,customerObject);

        // Array holding the customer Page
        Page<Customer> customerPage = null ;

        // Check the searchField and call the service method
        if ( searchField.equalsIgnoreCase("fname")) {

            //check its enabled
            if(merchantSetting){

                //if enabled find user registered skipp user registration
                if(userType !=3){

                    //fetch customer data
                    customerPage =findByCusMerchantNoAndCusLocationAndCusMerchantUserRegisteredAndCusFNameLike(merchantNo,userLocation,userNo,query +"%",pageable);

                }else {

                    //fetch customer data
                    customerPage =findByCusFNameLikeAndCusMerchantNo(query+"%",merchantNo,pageable);
                }


            }else {

                //check the location why checking two time is avoid @query perfomance issue
                if (userType ==3 ||userLocation.longValue() ==0L){

                    //fetch customer data
                    customerPage =findByCusFNameLikeAndCusMerchantNo(query +"%",merchantNo,pageable);

                }else {

                    //find based on location
                    customerPage=customerRepository.findByCusFNameLikeAndCusMerchantNoAndCusLocationOrCusLocation(query +"%",merchantNo,0L,userLocation,pageable);
                }
            }


        } else if ( searchField.equalsIgnoreCase("lname") ) {

            //check merchant settings
            if(merchantSetting){

                //find customer information
                if(userType !=3){

                    //fetch customer data
                    customerPage =findByCusMerchantNoAndCusLocationAndCusMerchantUserRegisteredAndCusLNameLike(merchantNo,userLocation,userNo,query +"%",pageable);

                }else {

                    //fetch customer data
                    customerPage =findByCusLNameLikeAndCusMerchantNo(query +"%",merchantNo,pageable);
                }


            }else {

                //check user location
                if (userType ==3||userLocation.longValue() ==0L){

                    //get page information
                    customerPage =findByCusLNameLikeAndCusMerchantNo(query +"%",merchantNo,pageable);

                }else {

                    //fetch customer data
                    customerPage=findByCusLNameLikeAndCusMerchantNoAndCusRegisterStatusAndCusLocation(query + "%", merchantNo, CustomerStatus.ACTIVE, userLocation, pageable);
                }
            }

        } else if ( searchField.equalsIgnoreCase("mobile") ) {

            if(merchantSetting){

                if(userType !=3){

                    //fetch customer data
                    customerPage =findByCusMerchantNoAndCusLocationAndCusMerchantUserRegisteredAndCusMobileLike(merchantNo,userLocation,userNo,query +"%",pageable);
                }else {

                    ////fetch customer data
                    customerPage = customerRepository.findByCusMobileLikeAndCusMerchantNo(query + "%", merchantNo, pageable);
                }


            }else {

                if ( userType ==3 ||userLocation.longValue() ==0L){

                    ////fetch customer data
                    customerPage = customerRepository.findByCusMobileLikeAndCusMerchantNo(query + "%", merchantNo, pageable);

                }else {

                    ////fetch customer data
                    customerPage=findByCusMobileLikeAndCusMerchantNoAndCusRegisterStatusAndCusLocation(query + "%", merchantNo, CustomerStatus.ACTIVE, userLocation, pageable);
                }
            }

        } else if ( searchField.equalsIgnoreCase("email") ) {

            // Get the result
            if(merchantSetting){

                if(userType !=3){

                    //fetch customer data
                    customerPage =findByCusMerchantNoAndCusLocationAndCusMerchantUserRegisteredAndCusEmailLike(merchantNo, userLocation, userNo,query + "%", pageable);
                }else {

                    //fetch customer data
                    customerPage = customerRepository.findByCusEmailLikeAndCusMerchantNo(query + "%", merchantNo, pageable);
                }


            }else {


                if (userType ==3||userLocation.longValue() ==0L){

                    //fetch customer data
                    customerPage = customerRepository.findByCusEmailLikeAndCusMerchantNo(query + "%", merchantNo,pageable);

                }else {

                    //fetch customer data
                    customerPage=findByCusEmailLikeAndCusMerchantNoAndCusRegisterStatusAndCusLocation(query + "%", merchantNo, CustomerStatus.ACTIVE, userLocation, pageable);
                }
            }


        } else if ( searchField.equalsIgnoreCase("loyaltyid") ) {

            if(merchantSetting){

               if(userType !=3){

                //fetch customer data
                customerPage=findByCusMerchantNoAndCusLocationAndCusMerchantUserRegisteredAndCusLoyaltyIdLike(merchantNo, userLocation, userNo,query + "%", pageable);

               }else{

                   //fetch customer data
                   customerPage = customerRepository.findByCusLoyaltyIdLikeAndCusMerchantNo(query + "%", merchantNo, pageable);
               }

            }else {

                if(userType ==3||userLocation.longValue() ==0L){

                    //fetch customer data
                    customerPage = customerRepository.findByCusLoyaltyIdLikeAndCusMerchantNo(query + "%", merchantNo, pageable);
                }else {

                    //fetch customer data
                    customerPage=findByCusLoyaltyIdLikeAndCusMerchantNoAndCusRegisterStatusAndCusLocation(query + "%", merchantNo, CustomerStatus.ACTIVE, userLocation, pageable);
                }

            }

            // Get the result

            // If the type is loyaltyid and the customerpage is not null, then we need to fetch the
            // tier informaton
            // Here we will be fetching the details only if the result has one data
            // If we don't have this check, this could create a hit on the database during
            // auto complete or searching
            if ( customerPage != null && customerPage.getContent().size() == 1 ) {

                // Go through the list and call the get tier
                for(Customer customer : customerPage.getContent() ) {

                    // Set the tier
                    customer.setTier(getTierForCustomer(customer));

                }
            }
        } else {

            if(merchantSetting){

                if(userType !=3){

                    //fetch customer data
                    customerPage =findByCusMerchantNoAndCusLocationAndCusMerchantUserRegistered(merchantNo, userLocation, userNo, pageable);
                }else {

                    //fetch customer data
                    customerPage = customerRepository.findByCusMerchantNo(merchantNo, pageable);
                }


            }else {

                if(userType ==3||userLocation.longValue() ==0L){

                    //fetch customer data
                    customerPage = customerRepository.findByCusMerchantNo(merchantNo, pageable);

                }else {

                    //fetch customer data
                    customerPage =findByCusMerchantNoAndCusRegisterStatusAndCusLocation(merchantNo, IndicatorStatus.YES, userLocation, pageable);
                }
            }

        }


        // Return the customer page
        return customerPage;
    }

    @Override
    public Customer saveCustomerDetails( CustomerResource customerResource, CustomerProfileResource customerProfileResource) throws InspireNetzException {

        //check the access permission
        authSessionUtils.validateFunctionAccess(FunctionCode.FNC_UPDATE_CUSTOMER);

        // Get the merchant number for the session
        Long merchantNo = authSessionUtils.getMerchantNo();

        //set flag for identifying customer is connecting merchant
        boolean isConnectMerchant=false;

        boolean isReRegister =false;

        // Object storing customer
        Customer customer;

        // First check if the request is update
        if ( customerResource.getCusCustomerNo() != null ) {

            // Get the customer
            customer = findByCusCustomerNo(customerResource.getCusCustomerNo());

            // If the customer is null throw exception
            if ( customer == null ) {

                // Log the information
                log.info("saveCustomerDetails -> Customer is not found");

                // throw exception
                throw new InspireNetzException(APIErrorCode.ERR_NO_DATA_FOUND);

            }

            // Map the customer
            mapper.map(customerResource,customer);


        } else {

            // Map the object check unregistered customer
            customer =findByCusLoyaltyIdAndCusMerchantNo(customerResource.getCusLoyaltyId(),merchantNo);

            if(customer !=null && customer.getCusRegisterStatus().intValue() ==IndicatorStatus.NO){

                //set customer number
                customerResource.setCusCustomerNo(customer.getCusCustomerNo());

                //set merchant user registered
                customerResource.setCusMerchantUserRegistered(authSessionUtils.getUserNo());

                //isReRegister
                isReRegister =true;

                //map resource
                mapper.map(customerResource, customer);

            }else{ 
                //normal mapping
                //map resource
                customer = mapper.map(customerResource,Customer.class);

            }



        }

        // Set the merchantNumber for the customer
        customer.setCusMerchantNo(merchantNo);

        // Map the customerProfile
        CustomerProfile customerProfile =  mapper.map(customerProfileResource,CustomerProfile.class);

        // Create the Validator for customer
        CustomerValidator cusValidator = new CustomerValidator(dataValidationUtils);

        // Create the BeanPropertyBindingResult
        BeanPropertyBindingResult cusResult = new BeanPropertyBindingResult(customer,"customer");

        // Validate customer
        cusValidator.validate(customer,cusResult);


        // Create the Validator for CustomerProfile
        CustomerProfileValidator   customerProfileValidator = new CustomerProfileValidator();

        // Validate the customerProfile
        customerProfileValidator.validate(customerProfile,cusResult);


        // Check if the result contains errors
        if ( cusResult.hasErrors() ) {

            // Get the validation message
            String messages = dataValidationUtils.getValidationMessages(cusResult);

            // Log the response
            log.info("saveCustomerDetails - Response : Invalid Input - "+ messages);

            // Throw InspireNetzException with INVALID_INPUT as error
            throw new InspireNetzException(APIErrorCode.ERR_INVALID_INPUT,messages);

        }

        // Check if the customer is already existing
        boolean isExist = isDuplicateCustomerExisting(customer);

        // Check the boolean value
        if ( isExist ) {

            // Log the response
            log.info("saveCustomerDetails - Response : Customer is already existing");

            // Throw InspireNetzException with ERR_DUPLICATE_ENTRY as error
            throw new InspireNetzException(APIErrorCode.ERR_DUPLICATE_ENTRY);

        }

        //check duplicate mobile existing or not
        boolean isDuplicateMobile =isDuplicateMobileExisting(customer);

        //check mobile is exist or not
        if(isDuplicateMobile){

            // Log the response
            log.info("saveCustomerDetails - Response : Duplicate Mobile number"+customer.toString());

            // Throw InspireNetzException with ERR_DUPLICATE_ENTRY as error
            throw new InspireNetzException(APIErrorCode.ERR_DUPLICATE_MOBILE);

        }

        //check duplicate mobile existing or not
        boolean isDuplicateEmail =isDuplicateEmailExisting(customer);

        //check mobile is exist or not
        if(isDuplicateEmail){

            // Log the response
            log.info("saveCustomerDetails - Response : Duplicate Email Id"+customer.toString());

            // Throw InspireNetzException with ERR_DUPLICATE_ENTRY as error
            throw new InspireNetzException(APIErrorCode.ERR_DUPLICATE_EMAIL);

        }

        // If the customerLocation field is not set, then we need to set the
        // the location to the location of the merchant user registering the customer
        if ( customer.getCusLocation() == null || customer.getCusLocation() == 0L ) {

            customer.setCusLocation(authSessionUtils.getUserLocation());

        }


        // Hold the audit details
        String auditDetails = authSessionUtils.getUserNo().toString() + "#" + authSessionUtils.getUserLoginId();

        // If the customer getCusCustomerNo is  null, then set the created_by, else set the updated_by
        if ( customer.getCusCustomerNo() == null ) {

            // Store the merchantUserRegistered field for the Customer
            customer.setCusMerchantUserRegistered(authSessionUtils.getUserNo());

            // Set the createdBy to the auditDetails
            customer.setCreatedBy(auditDetails);


        } else {



            customer.setUpdatedBy(auditDetails);

            // Get the customerProfile object
            CustomerProfile baseProfile = customerProfileService.findByCspCustomerNo(customer.getCusCustomerNo());

            // If the baseProfile is not null, then we need to set the
            // cspId of the customerProfiile
            if ( baseProfile != null ) {

                customerProfile.setCspId(baseProfile.getCspId());


                //set last birthday and anniversary awarded date  for update mode
                if(customerProfile.getCspId() !=null){

                    //fetch base profile and set birthday and anniversary awarded date
                    customerProfile.setCspBirthDayLastAwarded(baseProfile.getCspBirthDayLastAwarded());

                    customerProfile.setCspAnniversaryLastAwarded(baseProfile.getCspAnniversaryLastAwarded());


                }

            }

        }


        //check user exist with this mobile number
        if ( customer.getCusCustomerNo() == null  || isReRegister) {

           //call to connect merchant
           customer = connectMerchant(merchantNo,customer,CustomerRegisterType.MERCHANT_PORTAL);

           //set connect flag
           isConnectMerchant =true;

        }

        // only we need to process save merchant only when customer not connect if the customer is connect already save and evaluate customer
        if(!isConnectMerchant){

            customer = validateAndSaveCustomer(customer);

            //evaluate tier
            evaulateCustomerTier(customer);

        }

        // If the customer object is not null ,then return the success object
        if ( customer.getCusCustomerNo() != null ) {

            // Set the customerNo for the customer object
            customerProfile.setCspCustomerNo(customer.getCusCustomerNo());

            // Save the customerProfile
            customerProfile = customerProfileService.saveCustomerProfile(customerProfile);

        } else {

            // Log the response
            log.info("saveCustomer - Response : Unable to save the customer information");

            // Throw InspireNetzException with ERR_OPERATION_FAILED as error
            throw new InspireNetzException(APIErrorCode.ERR_OPERATION_FAILED);

        }


        // Return the customer object
        return customer;

    }
    @Override
    public void processSignUpBonus(Customer customer) throws InspireNetzException {

        customer = findByCusLoyaltyIdAndCusMerchantNo(customer.getCusLoyaltyId(),customer.getCusMerchantNo());

        // Create the UniqueIdGenerator
        String uniqueID = UUID.randomUUID().toString();

        CustomerRewardActivity customerRewardActivity=this.customerRewardActivityService.validateAndRegisterCustomerRewardActivity(customer.getCusCustomerNo(),CustomerRewardingType.SIGNUP_BONUS,customer.getCusLoyaltyId()+"#"+uniqueID);

    }

    @Override
    public List<Customer> getCustomerDetailsByLoyaltyId(String loyaltyId) {
        return customerRepository.findByCusLoyaltyId(loyaltyId);
    }

    @Override
    public void processReferralAwarding(Customer customer) throws InspireNetzException {

        //default channel web
        Integer channel =RequestChannel.RDM_WEB;

        List<CustomerReferral> customerReferralList =customerReferralService.findByCsrMerchantNoAndCsrRefMobileAndCsrRefStatusOrderByCsrRefTimeStampAsc(customer.getCusMerchantNo(), customer.getCusMobile(), CustomerReferralStatus.NEW);

        //check its null or not
        if(customerReferralList !=null && customerReferralList.size()>0){

            //get top most entry
            CustomerReferral customerReferral =customerReferralList.get(0);

            //update the status processed
            customerReferral.setCsrRefStatus(CustomerReferralStatus.PROCESSED);

            //update the joined status
            customerReferralService.saveCustomerReferral(customerReferral);

            //If multiple customer referred this one,we need to make all other entries as status rejected
            List<CustomerReferral> rejectedReferrals =new ArrayList<>();

            //Make All other request as REJECTED
            for(CustomerReferral rejectedReferral:customerReferralList){

                if(rejectedReferral.getCsrId()!=customerReferral.getCsrId()){

                    rejectedReferral.setCsrRefStatus(CustomerReferralStatus.REJECTED);

                    rejectedReferrals.add(rejectedReferral);

                }
            }


            //if rejected referral entry greater than zero,save it
            if(rejectedReferrals.size()>0){

                //update the joined status
                customerReferralService.saveAll(rejectedReferrals);
            }

            //process two program for referee  bonus and reference bonus
            Customer referrerCustomer =findByCusLoyaltyIdAndCusMerchantNo(customerReferral.getCsrLoyaltyId(),customer.getCusMerchantNo());


            //check the referrer customer is valid or not
            if(referrerCustomer !=null && referrerCustomer.getCusStatus()==CustomerStatus.ACTIVE){

               //process referee bonus
                processReferralBonus(referrerCustomer, channel, customerReferral, customerReferralList, CustomerRewardingType.REFERRER_BONUS);

            }

            //process referee bonus
            processReferralBonus(customer, channel, customerReferral, customerReferralList, CustomerRewardingType.REFEREE_BONUS);
        }
    }

    @Override
    public Page<Customer> findByCusMerchantNoAndCusLocationAndCusMerchantUserRegistered(Long cusMerchantNo, Long cusLocation, Long cusMerchantUserRegistered, Pageable pageable) {
        return customerRepository.findByCusMerchantNoAndCusLocationAndCusMerchantUserRegisteredAndCusRegisterStatus(cusMerchantNo,cusLocation,cusMerchantUserRegistered,IndicatorStatus.YES, pageable);
    }

    @Override
    public Page<Customer> findByCusMerchantNoAndCusLocationAndCusMerchantUserRegisteredAndCusFNameLike(Long cusMerchantNo, Long cusLocation, Long cusMerchantUserRegistered, String cusFName, Pageable pageable) {
        return customerRepository.findByCusMerchantNoAndCusLocationAndCusMerchantUserRegisteredAndCusRegisterStatusAndCusFNameLike(cusMerchantNo, cusLocation, cusMerchantUserRegistered, IndicatorStatus.YES, cusFName,  pageable);
    }

    @Override
    public Page<Customer> findByCusMerchantNoAndCusLocationAndCusMerchantUserRegisteredAndCusLNameLike(Long cusMerchantNo, Long cusLocation, Long cusMerchantUserRegistered, String cusLName, Pageable pageable) {
        return customerRepository.findByCusMerchantNoAndCusLocationAndCusMerchantUserRegisteredAndCusRegisterStatusAndCusLNameLike( cusMerchantNo,  cusLocation,  cusMerchantUserRegistered, IndicatorStatus.YES,cusLName,  pageable);
    }

    @Override
    public Page<Customer> findByCusMerchantNoAndCusLocationAndCusMerchantUserRegisteredAndCusMobileLike(Long cusMerchantNo, Long cusLocation, Long cusMerchantUserRegistered, String cusMobile, Pageable pageable) {
        return customerRepository.findByCusMerchantNoAndCusLocationAndCusMerchantUserRegisteredAndCusRegisterStatusAndCusMobileLike( cusMerchantNo,  cusLocation,  cusMerchantUserRegistered,IndicatorStatus.YES, cusMobile,pageable);
    }

    @Override
    public Page<Customer> findByCusMerchantNoAndCusLocationAndCusMerchantUserRegisteredAndCusEmailLike(Long cusMerchantNo, Long cusLocation, Long cusMerchantUserRegistered, String cusEmail, Pageable pageable) {
        return customerRepository.findByCusMerchantNoAndCusLocationAndCusMerchantUserRegisteredAndCusRegisterStatusAndCusEmailLike( cusMerchantNo,  cusLocation, cusMerchantUserRegistered,IndicatorStatus.YES, cusEmail, pageable);
    }

    @Override
    public Page<Customer> findByCusMerchantNoAndCusLocationAndCusMerchantUserRegisteredAndCusLoyaltyIdLike(Long cusMerchantNo, Long cusLocation, Long cusMerchantUserRegistered, String cusLoyaltyId, Pageable pageable) {
        return customerRepository.findByCusMerchantNoAndCusLocationAndCusMerchantUserRegisteredAndCusRegisterStatusAndCusLoyaltyIdLike( cusMerchantNo,  cusLocation, cusMerchantUserRegistered,IndicatorStatus.YES, cusLoyaltyId, pageable);
    }

    @Override
    public Page<Customer> findByCusMerchantNoAndCusRegisterStatusAndCusLocation(Long cusMerchantNo, Integer cusRegisterStatus, Long cusLocation, Pageable pageable) {
        return customerRepository.findByCusMerchantNoAndCusLocationOrCusLocation( cusMerchantNo,  0L,cusLocation,pageable);
    }

    @Override
    public Page<Customer> findByCusFNameLikeAndCusMerchantNoAndCusRegisterStatusAndCusLocation(String cusFName, Long cusMerchantNo, Integer cusRegisterStatus, Long cusLocation, Pageable pageable) {
        return customerRepository.findByCusFNameLikeAndCusMerchantNoAndCusRegisterStatusAndCusLocationOrCusLocation( cusFName,  cusMerchantNo,  cusRegisterStatus, 0L,cusLocation, pageable);
    }

    @Override
    public Page<Customer> findByCusLNameLikeAndCusMerchantNoAndCusRegisterStatusAndCusLocation(String cusLName, Long cusMerchantNo, Integer cusRegisterStatus, Long cusLocation, Pageable pageable) {
        return customerRepository.findByCusLNameLikeAndCusMerchantNoAndCusLocationOrCusLocation( cusLName,  cusMerchantNo,0L,  cusLocation,  pageable);
    }

    @Override
    public Page<Customer> findByCusMobileLikeAndCusMerchantNoAndCusRegisterStatusAndCusLocation(String cusMobile, Long cusMerchantNo, Integer cusRegisterStatus, Long cusLocation, Pageable pageable) {
        return customerRepository.findByCusMobileLikeAndCusMerchantNoAndCusLocationOrCusLocation( cusMobile,  cusMerchantNo,0L,  cusLocation,  pageable);
    }

    @Override
    public Page<Customer> findByCusEmailLikeAndCusMerchantNoAndCusRegisterStatusAndCusLocation(String cusEmail, Long cusMerchantNo, Integer cusRegisterStatus, Long cusLocation, Pageable pageable) {
        return customerRepository.findByCusEmailLikeAndCusMerchantNoAndCusLocationOrCusLocation( cusEmail, cusMerchantNo,0L,  cusLocation,  pageable);
    }

    @Override
    public Page<Customer> findByCusLoyaltyIdLikeAndCusMerchantNoAndCusRegisterStatusAndCusLocation(String cusLoyaltyId, Long cusMerchantNo, Integer cusRegisterStatus, Long cusLocation, Pageable pageable) {
        return customerRepository.findByCusLoyaltyIdLikeAndCusMerchantNoAndCusLocationOrCusLocation( cusLoyaltyId,  cusMerchantNo, 0L,  cusLocation,  pageable);
    }


    private boolean processReferralBonus(Customer customer,int channel,CustomerReferral customerReferral,List<CustomerReferral> customerReferList,int rewardingType) throws InspireNetzException {

        //customer Reward activity
        CustomerRewardActivity customerRewardActivity=new CustomerRewardActivity();

        // Create the UniqueIdGenerator
        String uniqueID = UUID.randomUUID().toString();

        //processing customer reward activity for referee bonus
        if(rewardingType ==CustomerRewardingType.REFEREE_BONUS){

            log.info("CustomerServiceImpl->processReferralBonus referee pint");

            customerRewardActivity=this.customerRewardActivityService.validateAndRegisterCustomerRewardActivity(customer.getCusCustomerNo(),CustomerRewardingType.REFEREE_BONUS,customerReferral.getCsrRefMobile()+"#"+uniqueID);

        }else if(rewardingType ==CustomerRewardingType.REFERRER_BONUS){

            log.info("CustomerServiceImpl->processReferralBonus referrer pint");

            customerRewardActivity=this.customerRewardActivityService.validateAndRegisterCustomerRewardActivity(customer.getCusCustomerNo(),CustomerRewardingType.REFERRER_BONUS,customerReferral.getCsrLoyaltyId()+"#"+uniqueID);
        }

        /*//initialise customer referrer list
        List<CustomerReferral> customerReferralList =new ArrayList<>();

        //set updated flag
        boolean update=false;

        if(customerRewardActivity.getCraId() != null){

            if(!update){

                //update status referee status flag
                for (CustomerReferral customerReferral1:customerReferList){

                    //update status
                    customerReferral1.setCsrRefStatus(CustomerReferralStatus.PROCESSED);

                    //put into list
                    customerReferralList.add(customerReferral1);
                }

                //save all method for updated
                customerReferralService.saveAll(customerReferList);

                update=true;
            }

            //return true
            return true;

        }*/

        //return false
        return  false;

    }

    private void sendUserLoginCredential(User user,Customer customer,Merchant merchant) throws InspireNetzException {

        //send notification message to customer
        HashMap<String,String> msgParams = new HashMap<>();

        //add points to the map
        msgParams.put("#merchant",merchant.getMerMerchantName()+"");

        //add product code to the map
        msgParams.put("#min",customer.getCusLoyaltyId());

        msgParams.put("#username",user.getUsrLoginId());

        msgParams.put("#password",user.getUsrPassword());

        msgParams.put("#name",customer.getCusFName()==null?"":customer.getCusFName());

        MessageWrapper messageWrapper = generalUtils.getMessageWrapperObject(MessageSpielValue.USER_LOGIN_CREDENTIAL,customer.getCusLoyaltyId(),customer.getCusMobile(),customer.getCusEmail(),"",customer.getCusMerchantNo(),msgParams,MessageSpielChannel.ALL,IndicatorStatus.YES);

        userMessagingService.transmitNotification(messageWrapper);

        /*//merchant settings
        boolean merchantSetting =getMerchantSettings(AdminSettingsConfiguration.MER_ENABLE_SMS,customer);

        //check its enable
        if(merchantSetting){

            //send notification message to customer
            HashMap<String,String> msgParams = new HashMap<>();

            //add points to the map
            msgParams.put("#merchant",merchant.getMerMerchantName()+"");

            //add product code to the map
            msgParams.put("#min",customer.getCusLoyaltyId());

            msgParams.put("#username",user.getUsrLoginId());

            msgParams.put("#password",user.getUsrPassword());

            msgParams.put("#name",customer.getCusFName()==null?"":customer.getCusFName());

            MessageWrapper messageWrapper = generalUtils.getMessageWrapperObject(MessageSpielValue.USER_LOGIN_CREDENTIAL,customer.getCusLoyaltyId(),customer.getCusMobile(),customer.getCusMerchantNo(),msgParams);

            userMessagingService.transmitNotification(messageWrapper);

        }

        boolean email =getMerchantSettings(AdminSettingsConfiguration.MER_ENABLE_EMAIL,customer);

        if(email){

            //add points to the map
            HashMap<String,String> msgParams = new HashMap<>();

            //messsge params
            msgParams.put("#merchant",merchant.getMerMerchantName()+"");

            msgParams.put("#username",user.getUsrLoginId());

            msgParams.put("#password",user.getUsrPassword());

            msgParams.put("#name",customer.getCusFName()==null?"":customer.getCusFName());

            userMessagingService.sendEmail(MessageSpielValue.USER_LOGIN_CREDENTIAL,customer.getCusEmail(),msgParams);
        }*/

    }



    private User createUserObject(Customer customer) {

        //create user object
        User user =new User();

        //get mobile number
        String mobileNumber =customer.getCusMobile()==null?"":customer.getCusMobile();

        //check mobile number is not null
        if(!mobileNumber.equals("")){

           //set user details
           user.setUsrLoginId(customer.getCusMobile());

           //set first name
           user.setUsrFName(customer.getCusFName());

           //set last name
           user.setUsrLName(customer.getCusLName());

            //set user mobile no
           user.setUsrMobile(customer.getCusMobile());

           user.setUsrEmail(customer.getCusEmail());

        }

        return user;
    }

    private void saveNotificationMessage(Customer customer) {

        // First add the notification for the user
        Notification userNotification = new Notification();

        // Set the fields
        userNotification.setNtfType(NotificationType.USER_ACTIVITY);

        userNotification.setNtfSourceName(customer.getCusMerchantNo() + "");

        Merchant merchant=merchantService.findByMerMerchantNo(customer.getCusMerchantNo());

        userNotification.setNtfText("\"You are now connected to "+merchant==null?"":merchant.getMerMerchantName());

        userNotification.setNtfRecepientType(NotificationRecepientType.USER);

        userNotification.setNtfRecepient(customer.getCusUserNo());

        userNotification.setNtfSourceImageId(merchant==null?0L:(merchant.getMerMerchantImage()==null?0L:merchant.getMerMerchantImage()));

        userNotification.setNtfStatus(NotificationStatus.NEW);

        // Save the UserNotification
        notificationService.saveNotification(userNotification);

    }

    public void sendMessageAndEmail(Customer customer,Merchant merchant) throws InspireNetzException {


        //send notification message to customer
        HashMap<String,String> msgParams = new HashMap<>();

        //add points to the map
        msgParams.put("#merchant",merchant.getMerMerchantName()+"");

        //add product code to the map
        msgParams.put("#min",customer.getCusLoyaltyId());

        msgParams.put("#name",customer.getCusFName());

        MessageWrapper messageWrapper =  generalUtils.getMessageWrapperObject(MessageSpielValue.USER_CONNECT_MERCHANT_SUCCESS,customer.getCusLoyaltyId(),"","","",customer.getCusMerchantNo(),msgParams,MessageSpielChannel.ALL,IndicatorStatus.YES);

        //send sms
        userMessagingService.transmitNotification(messageWrapper);

       /* //merchant settings
        boolean merchantSettingsEnabled =getMerchantSettings(AdminSettingsConfiguration.MER_ENABLE_SMS,customer);

        if(merchantSettingsEnabled){

                //send notification message to customer
                HashMap<String,String> msgParams = new HashMap<>();

                //add points to the map
                msgParams.put("#merchant",merchant.getMerMerchantName()+"");

                //add product code to the map
                msgParams.put("#min",customer.getCusLoyaltyId());

                msgParams.put("#name",customer.getCusFName());

                MessageWrapper messageWrapper =  generalUtils.getMessageWrapperObject(MessageSpielValue.USER_CONNECT_MERCHANT_SUCCESS,customer.getCusLoyaltyId(),"","","",customer.getCusMerchantNo(),msgParams,MessageSpielChannel.ALL,IndicatorStatus.YES);

                //send sms
                userMessagingService.transmitNotification(messageWrapper);

            }
*/
        /*boolean merchantEmailEnabled =getMerchantSettings(AdminSettingsConfiguration.MER_ENABLE_EMAIL,customer);

        if(merchantEmailEnabled){

             //add points to the map
             HashMap<String,String> msgParams = new HashMap<>();

             //messsge params
             msgParams.put("#merchant",customer.getCusMerchantNo()+"");

             userMessagingService.sendEmail(MessageSpielValue.USER_CONNECT_MERCHANT_SUCCESS,customer.getCusEmail(),msgParams);
            }

        }*/
    }
    private boolean getMerchantSettings(String settingsName,Customer customer) {

        //get settings id
        Long setting=settingService.getSettingsId(settingsName);

        //merchant settings enabled or not
        MerchantSetting merchantSetting=merchantSettingService.findByMesMerchantNoAndMesSettingId(customer.getCusMerchantNo(),setting);

        //check if merchant settings null check its default enabled
        if(merchantSetting ==null){

            Setting setting1=settingService.findBySetId(setting);

            if(setting1 !=null){

                //check its default enabled
                int settingDefaultValue = Integer.parseInt((setting1.getSetDefaultValue()==null||setting1.getSetDefaultValue().equals(""))?"0":setting1.getSetDefaultValue());

                if(settingDefaultValue ==IndicatorStatus.YES){

                    return true;

                }else {

                    return false;
                }

            }else{

                return false;
            }
        }
        //check merchant enabled value
        int merchantSettings =Integer.parseInt((merchantSetting.getMesValue()==null||merchantSetting.getMesValue().equals(""))?"0":merchantSetting.getMesValue());

        if(merchantSettings ==IndicatorStatus.YES){

            return true;
        }


        return false;
    }

    @Override
    public boolean updateLoyaltyStatus(String loyaltyId,Long merchantNo,Integer status) throws InspireNetzException {


        // Get the customer information
        Customer customer = findByCusLoyaltyIdAndCusMerchantNo(loyaltyId, merchantNo);

        User user = null;

        if(customer != null){

            //get the user details
            user = userService.findByUsrUserNo(customer.getCusUserNo());

            //if request is for deactivating the customer account , unlink all accounts linked to the customer
            if(status == CustomerStatus.INACTIVE){

                //unlink all accounts linked to customer
                linkRequestService.unlinkCustomerAccounts(customer);
            }
        }

        // If the customer is existing, then check if it is already in the requested
        // status
        if ( customer != null ) {

            //get the redeemable catalogue items for the customer
            Page<Catalogue> catalogues = catalogueService.searchCatalogueByCurrencyAndCategory(customer.getCusMerchantNo(),0L,0,customer.getCusLoyaltyId(),RequestChannel.RDM_WEB,new PageRequest(0,100));

            // Create the list of catalogue
            String catalogueList = "";

            //get the redeemable items
            catalogueList = getRedeemableItemsList(catalogues);

            //create a hashmap for sms parameters
            HashMap<String , String > smsParams = new HashMap<>(0);

            //put the list into smsparams
            smsParams.put("#catalogueList",catalogueList);

            // Check if the customer is already in the given status
            if ( customer.getCusStatus() == status ) {

                // Log the information
                log.info("updateLoyaltyStatus -> Customer is already in status : " + status);

                // If the status is active, then we need to send duplicate register
                if ( status == CustomerStatus.ACTIVE ) {

                    MessageWrapper messageWrapper = generalUtils.getMessageWrapperObject(MessageSpielValue.CUSTOMER_REGISTRATION_DUPLICATE_REGISTER,customer.getCusLoyaltyId(),customer.getCusMobile(),customer.getCusEmail(),"",customer.getCusMerchantNo(),smsParams,MessageSpielChannel.ALL,IndicatorStatus.YES);

                    // Send the sms stating that the customer has been deactivated successfully
                    userMessagingService.transmitNotification(messageWrapper);

                } else {

                    MessageWrapper messageWrapper = generalUtils.getMessageWrapperObject(MessageSpielValue.CUSTOMER_REGISTRATION_DUPLICATE_UNREGISTER,customer.getCusLoyaltyId(),customer.getCusMobile(),customer.getCusEmail(),"",customer.getCusMerchantNo(),smsParams,MessageSpielChannel.ALL,IndicatorStatus.YES);

                    // Send the sms saying that the customer is already un registered
                    userMessagingService.transmitNotification(messageWrapper);
                }

                // Throw operation not allowed
                throw new InspireNetzException(APIErrorCode.ERR_OPERATION_NOT_ALLOWED);
            }
        } else {

            // If the requested status is INACTIVE and if the customer is not found,
            // then we need to send the message stating that the min is not valid
            if ( status == CustomerStatus.INACTIVE ) {

                //create a map for the sms placeholders
                HashMap<String , String > smsParams = new HashMap<>(0);

                //put the placeholders into the map
                smsParams.put("#min",loyaltyId);

                MessageWrapper messageWrapper = generalUtils.getMessageWrapperObject(MessageSpielValue.LOYALTY_REGISTRATION_INVALID_MIN,loyaltyId,loyaltyId,"","",customer.getCusMerchantNo(),smsParams,MessageSpielChannel.SMS,IndicatorStatus.NO);

                // Send the sms saying that the customer is already un registered
                userMessagingService.transmitNotification(messageWrapper);


                // Throw operation not allowed
                throw new InspireNetzException(APIErrorCode.ERR_OPERATION_NOT_ALLOWED);
            }
        }

        // If the status is active, validate the details
        if ( status == CustomerStatus.ACTIVE ) {

            // If its a new customer trying to register for loyalty, then we need to create
            // a customer object if its not already existing
            if ( customer == null ) {

                // Create the customer object
                customer = createCustomerObjectForLoyaltyId(loyaltyId, "", merchantNo, authSessionUtils.getUserLocation(), authSessionUtils.getUserNo());

                //create a user with the customer details
                user = createUserObjectForLoyaltyId(loyaltyId,merchantNo,authSessionUtils.getUserNo());
            }

            // Call the validateDetails
            boolean isValid = validateCustomerServiceDetails(customer);

            // If the customer is not valid then show the message
            if ( !isValid ) {

                //create a map for the sms placeholders
                HashMap<String , String > smsParams = new HashMap<>(0);

                //put the placeholders into the map
                smsParams.put("#min",loyaltyId);

                MessageWrapper messageWrapper = generalUtils.getMessageWrapperObject(MessageSpielValue.LOYALTY_REGISTRATION_INVALID_MIN,loyaltyId,loyaltyId,"","",customer.getCusMerchantNo(),smsParams,MessageSpielChannel.SMS,IndicatorStatus.NO);

                // Send the sms saying that the customer is already un registered
                userMessagingService.transmitNotification(messageWrapper);


                // Throw operation not allowed
                throw new InspireNetzException(APIErrorCode.ERR_OPERATION_NOT_ALLOWED);
            }
        }

        // IF the data is found, then set the status to active
        customer.setCusStatus(status);

        if(customer.getCusStatus() == CustomerStatus.INACTIVE){

            //remove customer from searchesr
            customer.setCusRegisterStatus(IndicatorStatus.NO);

            // Set the user status to deactivated if the user is not null
            if ( user != null ) {

                //deactivate the customer
                user.setUsrStatus(UserStatus.DEACTIVATED);

                //deactivate the user
                user.setUsrRegisterStatus(IndicatorStatus.NO);
            }

            //expire the reward balance
            customerRewardExpiryService.expireBalanceForCustomer(customer);

            //expire draw chances for customer
            expiringDrawChances(customer);

            //calling fir removing the reward balance related entries
            removeBalanceEntries(customer);

            //log the activity
            customerActivityService.logActivity(customer.getCusLoyaltyId(),CustomerActivityType.UNREGISTER,"Customer Unregistered",customer.getCusMerchantNo(),"");

            //archive all customer activities and transactions
            archiveCustomerData(customer);

        } else if(customer.getCusStatus() == CustomerStatus.ACTIVE){

            //if customer is active , make customer register status active
            customer.setCusRegisterStatus(IndicatorStatus.YES);
        }
        if(user != null){

            //save the user details
            user = userService.saveUser(user);

            //set the user number to the customer
            customer.setCusUserNo(user.getUsrUserNo());
        }

        // Save the customer
        customer = saveCustomer(customer);

        // Check if the customer got saved successfully
        if ( customer == null ) {

            // Log the information
            log.info("registerForLoyalty -> Customer information update failed");

            MessageWrapper messageWrapper = generalUtils.getMessageWrapperObject(MessageSpielValue.GENERAL_ERROR_MESSAGE,loyaltyId,loyaltyId,"","",customer.getCusMerchantNo(),new HashMap<String,String>(0),MessageSpielChannel.SMS,IndicatorStatus.NO);

            // Send a response to the requester stating that the request was not procssed
            userMessagingService.transmitNotification(messageWrapper);

            // Throw exception
            throw new InspireNetzException(APIErrorCode.ERR_OPERATION_FAILED);
        }

        // If the customer status is inactive then send the deactivation success message
        // else send the activation success message
        if(customer.getCusStatus() == CustomerStatus.INACTIVE){

            MessageWrapper messageWrapper = generalUtils.getMessageWrapperObject(MessageSpielValue.LOYALTY_ACCOUNT_DEACTIVATION_SMS,customer.getCusLoyaltyId(),customer.getCusMobile(),customer.getCusEmail(),"",customer.getCusMerchantNo(),new HashMap<String, String>(0),MessageSpielChannel.ALL,IndicatorStatus.YES);

            // Send the sms stating that the customer has been deactivated successfully
            userMessagingService.transmitNotification(messageWrapper);

        } else {

           //get the redeemable catalogue items for the customer
            Page<Catalogue> catalogues = catalogueService.searchCatalogueByCurrencyAndCategory(customer.getCusMerchantNo(),0L,0,customer.getCusLoyaltyId(),RequestChannel.RDM_CHANNEL_SMS,new PageRequest(0,100));

            // Create the list of catalogue
            String catalogueList = "";

            //get the redeemable items
            catalogueList = getRedeemableItemsList(catalogues);
            //create a hashmap for sms parameters
            HashMap<String , String > smsParams = new HashMap<>(0);

            //put the list into smsparams
            smsParams.put("#catalogueList",catalogueList);

            MessageWrapper messageWrapper = generalUtils.getMessageWrapperObject(MessageSpielValue.CUSTOMER_REGISTRATION_SUCCESS,customer.getCusLoyaltyId(),customer.getCusMobile(),customer.getCusEmail(),"",customer.getCusMerchantNo(),smsParams,MessageSpielChannel.ALL,IndicatorStatus.YES);

            // Send the sms stating that the customer has been registered successfully
            userMessagingService.transmitNotification(messageWrapper);


            //log the activity
            customerActivityService.logActivity(customer.getCusLoyaltyId(),CustomerActivityType.REGISTER,"Customer registration successful",customer.getCusMerchantNo(),"");
        }

      // return true finally
        return true;

    }


    /**
     * @purpose:Remove all the expired balance entries from CustomerRewardBalance,
     * CustomerRewardExpiry
     * @param customer
     */
    private void removeBalanceEntries(Customer customer) {

        //get all the customerRewardBalances
        List<CustomerRewardBalance> customerRewardBalances = customerRewardBalanceService.findByCrbLoyaltyIdAndCrbMerchantNo(customer.getCusLoyaltyId(), customer.getCusMerchantNo());

        for(CustomerRewardBalance customerRewardBalance: customerRewardBalances){

            //delete all entries
            customerRewardBalanceService.deleteCustomerRewardBalance(customerRewardBalance);

        }

        //get all reward expiries
        List<CustomerRewardExpiry> customerRewardExpiries = customerRewardExpiryService.findByCreMerchantNoAndCreLoyaltyId(customer.getCusMerchantNo(),customer.getCusLoyaltyId());

        for(CustomerRewardExpiry customerRewardExpiry: customerRewardExpiries){

            //delete the expiry entry
            customerRewardExpiryService.deleteCustomerRewardExpiry(customerRewardExpiry);

        }

        List<AccumulatedRewardBalance > accumulatedRewardBalances = accumulatedRewardBalanceService.findByArbMerchantNoAndArbLoyaltyId(customer.getCusMerchantNo(),customer.getCusLoyaltyId());

        for(AccumulatedRewardBalance accumulatedRewardBalance : accumulatedRewardBalances){

            //delete the entry
            accumulatedRewardBalanceService.deleteAccumulatedRewardBalance(accumulatedRewardBalance.getArbId());

        }

        List<LinkedRewardBalance> linkedRewardBalances = linkedRewardBalanceService.findByLrbPrimaryLoyaltyIdAndLrbMerchantNo(customer.getCusLoyaltyId(),customer.getCusMerchantNo());

        for(LinkedRewardBalance linkedRewardBalance : linkedRewardBalances){

            //delete the entry
            linkedRewardBalanceService.deleteLinkedRewardBalance(linkedRewardBalance.getLrbId());

        }





    }

    /**
     * @purpose:archive customer data like redemptions , activities and transactions
     * @param customer
     */
    private void archiveCustomerData(Customer customer) {

        //get all the customer transactions
        List<Transaction> transactions = transactionService.listTransactions(customer.getCusMerchantNo(), customer.getCusLoyaltyId());

        //iterate through the list and set record status to archived
        for(Transaction transaction : transactions){

            //set archived status
            transaction.setTxnRecordStatus(RecordStatus.RECORD_STATUS_ARCHIVED);

        }

        //save all transactions
        transactionService.saveAll(transactions);

        //get all the customer activities
        List<CustomerActivity> customerActivities = customerActivityService.findByCuaMerchantNoAndCuaLoyaltyId(customer.getCusMerchantNo(),customer.getCusLoyaltyId());

        //iterate through the list and set record status to archived
        for(CustomerActivity customerActivity : customerActivities){

            //set archived status
            customerActivity.setCuaRecordStatus(RecordStatus.RECORD_STATUS_ARCHIVED);

        }

        //save all transactions
        customerActivityService.saveAll(customerActivities);

        //get all the redemptions active for the customer
        List<Redemption> redemptions = redemptionService.findRdmLoyaltyIdAndRdmMerchantNo(customer.getCusLoyaltyId(),customer.getCusMerchantNo());

        //iterate through the list and set record status
        for(Redemption redemption : redemptions){

            //set record status as archived
            redemption.setRdmRecordStatus(RecordStatus.RECORD_STATUS_ARCHIVED);

        }

        //save redemptions
        redemptionService.saveAll(redemptions);

    }

    /**
     * @purpose:expiring draw chances for customer in the time of un register
     * @param customer
     */
    private void expiringDrawChances(Customer customer) throws InspireNetzException {


        drawChanceService.expiringDrawChance(customer);

    }

    private User createUserObjectForLoyaltyId(String loyaltyId, Long merchantNo, Long userNo) {

        User user = new User();

        user.setUsrLoginId(loyaltyId);
        user.setUsrMerchantNo(merchantNo);
        user.setUsrUserType(UserType.CUSTOMER);
        user.setUsrRegisterStatus(IndicatorStatus.NO);
        user.setUsrIncorrectAttempt(0);
        user.setUsrStatus(UserStatus.ACCOUNT_CREATED);

        return user;
    }

    @Override
    public boolean transferAccounts(String oldLoyaltyId, String newLoyaltyId ) throws InspireNetzException {

        // Get the merchantNo from the session
        Long merchantNo = authSessionUtils.getMerchantNo();

        // Call the transferAccount function and return the status
        return accountTransferService.transferAccount(oldLoyaltyId,newLoyaltyId,merchantNo);

    }

    @Override
    public Customer getCustomerInfoForSession() throws InspireNetzException {

        // Get the userLoginId
        String loyaltyId = authSessionUtils.getUserLoginId();

        // Get the customer information for the
        Customer customer = findByCusLoyaltyIdAndCusMerchantNo(loyaltyId, generalUtils.getDefaultMerchantNo());

        // Check if the customer is null
        if ( customer == null  ) {

            // Throw the InspirenetzException
            throw new InspireNetzException(APIErrorCode.ERR_NO_DATA_FOUND);

        }


        // Get the tier for customer
        Tier tier = getTierForCustomer(customer);

        //get the merchant data
        Merchant merchant = getMerchantForCustomer(customer);

        //set merchant data to customer
        customer.setMerchant(merchant);

        // Set the tier in the custome object field
        customer.setTier(tier);


        // Check if the customer is primary
        boolean isPrimary = accountBundlingUtils.isCustomerPrimary(customer.getCusLoyaltyId());

        // Set the isPrimary flag
        customer.setPrimary(isPrimary);


        // Return the customer
        return customer;

    }

    @Override
    public Customer getCustomerInfo(Long cusCustomerNo) throws InspireNetzException {

        // Get the merchant number and store
        Long merchantNo = authSessionUtils.getMerchantNo();


        // Get the customer information
        Customer customer = findByCusCustomerNo(cusCustomerNo);

        // Make sure the customer exists
        if ( customer == null || customer.getCusCustomerNo() == null ) {

            // Log the information
            log.info("getCustomerInfo - No customer information found");

            // Throw the exception
            throw new InspireNetzException(APIErrorCode.ERR_NO_DATA_FOUND);

        }



        // Check if the customer belongs to the merchant by the merchant user
        if ( customer.getCusMerchantNo().longValue() != merchantNo ) {

            // Log the information
            log.info("getCustomerInfo - Customer does not belong to you");

            // Throw the exception
            throw new InspireNetzException(APIErrorCode.ERR_NOT_AUTHORIZED);

        }


        // Get the CustomerProfile information for the Customer
        CustomerProfile customerProfile = customerProfileService.findByCspCustomerNo(cusCustomerNo);

        // Set the customerProfile
        customer.setCustomerProfile(customerProfile);



        // Get the tier information
        Tier tier = tierService.findByTieId(customer.getCusTier());

        // Set the tier in the customer object
        customer.setTier(tier);



        // Return the customer object
        return customer;

    }

    @Override
    public Tier getTierForCustomer(Customer customer) {

        // Get the tier id
        Long tierId  = customer.getCusTier();

        // If the tierId is null, return null
        if ( tierId == null  || tierId == 0L ) {

            return null;

        }



        // Get the tier information
        Tier tier = tierService.findByTieId(tierId);

        // Return the tier object
        return tier;

    }

    @Override
    public Merchant getMerchantForCustomer(Customer customer) {

        // Get the tier id
        Long merchantNo  = customer.getCusMerchantNo();

        // If the tierId is null, return null
        if ( merchantNo == null  || merchantNo == 0L ) {

            return null;

        }



        // Get the tier information
        Merchant merchant = merchantService.findByMerMerchantNo(merchantNo);

        // Return the tier object
        return merchant;

    }

    @Override
    public boolean isDuplicateCustomerExisting(Customer customer) {

        // Get the customer information
        Customer exCustomer = customerRepository.findByCusLoyaltyIdLikeAndCusMerchantNoAndCusRegisterStatus(customer.getCusLoyaltyId(), customer.getCusMerchantNo(), IndicatorStatus.YES);

        // If the brnId is 0L, then its a new customer so we just need to check if there is ano
        // the customer code
        if ( customer.getCusCustomerNo() == null || customer.getCusCustomerNo() == 0L ) {

            // If the exCustomer is not null, then return true
            if ( exCustomer != null ) {

                return true;

            }

        } else {

            // Check if the customer is null
            if ( exCustomer != null && customer.getCusCustomerNo().longValue() != exCustomer.getCusCustomerNo().longValue() ) {

                return true;

            }

        }

        // Return false;
        return false;
    }

    @Override
    public boolean isDuplicateMobileExisting(Customer customer) {

        // Get the customer information
        Customer exCustomer = customerRepository.findByCusMerchantNoAndCusMobileAndCusRegisterStatus(customer.getCusMerchantNo(), customer.getCusMobile(),IndicatorStatus.YES);

        // If the brnId is 0L, then its a new customer so we just need to check if there is ano
        // the customer code
        if ( customer.getCusCustomerNo() == null || customer.getCusCustomerNo() == 0L ) {

            // If the exCustomer is not null, then return true
            if ( exCustomer != null ) {

                return true;

            }

        } else {

            // Check if the customer is null
            if ( exCustomer != null && customer.getCusCustomerNo().longValue() != exCustomer.getCusCustomerNo().longValue() ) {

                return true;

            }

        }

        // Return false;
        return false;
    }

    @Override
    public boolean isDuplicateEmailExisting(Customer customer) {

        if(customer.getCusEmail()==null||customer.getCusEmail().equals("")){

            return false;
        }

        // Get the customer information
        Customer exCustomer = customerRepository.findByCusMerchantNoAndCusEmailAndCusRegisterStatus(customer.getCusMerchantNo(), customer.getCusEmail(),IndicatorStatus.YES);

        // If the brnId is 0L, then its a new customer so we just need to check if there is ano
        // the customer code
        if ( customer.getCusCustomerNo() == null || customer.getCusCustomerNo() == 0L ) {

            // If the exCustomer is not null, then return true
            if ( exCustomer != null ) {

                return true;

            }

        } else {

            // Check if the customer is null
            if ( exCustomer != null && customer.getCusCustomerNo().longValue() != exCustomer.getCusCustomerNo().longValue() ) {

                return true;

            }

        }

        // Return false;
        return false;
    }


    /**
     * Function to check if the customer is valid on the service
     * This will call the inspireexchange to check the data
     * This will also add the subscription for the customer based on the
     * service registering
     *
     * @param customer  - The customer object for which data need to be checked
     *
     * @return          - true if data is successful and subscription add
     *                    false otherwise
     */
    public boolean validateCustomerServiceDetails(Customer customer) throws InspireNetzException {

//        // Get the url for the validate account
        /*String url = environment.getProperty("integration.customer.validateaccount");

        // Create the mapping for the pathVariables
        Map<String,String> pathVariables = new HashMap<>(0);

        // Add loyalty id to the field
        pathVariables.put("loyaltyid",customer.getCusLoyaltyId());

        // API Response object
        APIResponseObject retData;

        try {

            // Get the return data and store in the APIResponse object
             retData = integrationService.placeRestGetAPICall(url, pathVariables);


        } catch (Exception e) {

            // Log the information
            log.error("validateCustomerServiceDetails : Error in api call  "+ e.getMessage());

            // Print the stack trace
            e.printStackTrace();

            // Return false;
            return false;

        }


        // Check the status
        if ( retData.getStatus().equals(APIResponseStatus.failed.name()) ) {

           // Throw as operation failed
           throw new InspireNetzException(APIErrorCode.ERR_OPERATION_FAILED);


        }


        // Store the return data in a amp
        Map serviceData = (Map) retData.getData();

        // Check if the status field is existing and is valid
        if ( serviceData == null || !serviceData.containsKey("status") || !serviceData.get("status").equals("SUCCESSFUL") || !serviceData.containsKey("servicestatus") || !serviceData.get("servicestatus").equals("ACTIVE") ) {

            // Throw as operation not allowed
            throw new InspireNetzException(APIErrorCode.ERR_OPERATION_NOT_ALLOWED);

        }


        // Get the product
        Product product = productService.findByPrdMerchantNoAndPrdCode(customer.getCusMerchantNo(), serviceData.get("brand").toString());

        // If the product is not available in the system., then return false
        if ( product == null ) {

            // Throw as operation not allowed
            throw new InspireNetzException(APIErrorCode.ERR_NO_DATA);

        }


        // Set the location from the product as the customer location
        customer.setCusLocation(product.getPrdLocation());

        // Now update the customerType
        customer.setCusType( Integer.parseInt(serviceData.get("customertype").toString()) );

        // Save the customer
        customer = saveCustomer(customer);

        // check if the customer is null
        if ( customer == null || customer.getCusCustomerNo() == null ) {

            // Log the information
            log.error("validateCustomerServiceDetails -> Unable to save the customer");

            // Throw exception
            throw new InspireNetzException(APIErrorCode.ERR_OPERATION_FAILED);

        }


        // Update the subscription for the customer
        CustomerSubscription customerSubscription = null;

        // Check if there is already a CustomerSubscription for the customer number
        // for the same product code
        customerSubscription =  customerSubscriptionService.findByCsuCustomerNoAndCsuProductCode(customer.getCusCustomerNo(),product.getPrdCode());

        // If the customer subscription is not existing, then we need to initialize to
        // a new one
        if ( customerSubscription == null ) {

            // Create a new object
            customerSubscription = new CustomerSubscription();

        }


        // Set the CustomerSubscription information
        customerSubscription.setCsuCustomerNo(customer.getCusCustomerNo());

        customerSubscription.setCsuProductCode(product.getPrdCode());

        customerSubscription.setCsuLocation(customer.getCusLocation());

        customerSubscription.setCsuMerchantNo(customer.getCusMerchantNo());

        customerSubscription.setCsuServiceNo(serviceData.get("accountnumber").toString());

        // Save the customerSubscription
        customerSubscription = customerSubscriptionService.saveCustomerSubscription(customerSubscription);



        // If the customer subscription is not saved, then throw exception
        if ( customerSubscription == null || customerSubscription.getCsuId() == null ) {

            // Log the information
            log.error("validateCustomerServiceDetails -> Unable to save the customer subscription");

            // Throw exception
            throw new InspireNetzException(APIErrorCode.ERR_OPERATION_FAILED);

        }




        // Manually call the the evaluation of the tier for the customer which will be
        // run in async mode
        evaulateCustomerTier(customer);
*/
        // finally return true
        return true;

    }


    /**
     * Function to set the applicable tier for a customer , this is used to explicitly call the
     * tier evaulation when the customer needs to have the tier validated (like during registering)
     * This process will be running async
     *
     * @param customer - The customer for whom the tier need to be evaulated.
     *
     */
    @Async
    protected void evaulateCustomerTier(Customer customer) {

        // Get the tierGroupList for the merchant
        List<TierGroup> tierGroupList = tierGroupService.findByTigMerchantNo(customer.getCusMerchantNo());

        // Call the evaulate
        tierService.evaluateTierForCustomer(customer,tierGroupList);

    }


    @Override
    public Customer saveCustomer(Customer customer) {

        // Save the customer
        customer = customerRepository.save(customer);

        // Return the customer object
        return customer;

    }

    @Override
    public boolean deleteCustomer(Long cusCustomerNo) throws InspireNetzException {
        // Delete the customer
        customerRepository.delete(cusCustomerNo);

        // Return true
        return true;

    }

    @Override
    public Customer validateAndSaveCustomer(Customer customer) throws InspireNetzException {

        //authSessionUtils.validateFunctionAccess(FunctionCode.FNC_UPDATE_CUSTOMER);

        return saveCustomer(customer);

    }

    @Override
    public boolean validateAndDeleteCustomer(Long cusCustomerNo) throws InspireNetzException {

        authSessionUtils.validateFunctionAccess(FunctionCode.FNC_ADD_NEW_CUSTOMER);

        return deleteCustomer(cusCustomerNo);
    }

    @Override
    public boolean registerCustomerCompatible(String loyaltyId,String password, String firstName, String lastName ,String regKey) throws InspireNetzException {

        //check the authenticity of the request
        String authKey = userService.getRegistrationAuthenticationKey(loyaltyId, password);

        //check the two keys are matching
        if(!regKey.equals(authKey)){

            //log the error
            log.error("registerCustomerCompatible :  Authentication Keys doesn't match");

            throw new InspireNetzException(APIErrorCode.ERR_NOT_AUTHORIZED);

        }

        //get default merchant no
        Long merchantNo = generalUtils.getDefaultMerchantNo();

        //check another user exists with same loyaltyid
        User duplicateUser = userService.findByUsrLoginId(loyaltyId);

        //if customer is not null , loyalty id is already registered , throw error
        if( duplicateUser != null){

            //log the error
            log.error("registerCustomerCompatible : User Already exists");

            throw new InspireNetzException(APIErrorCode.ERR_DUPLICATE_ENTRY);

        }else {

            //create the user entity
            User user = new User();

            //set values to the user entity
            user.setUsrLoginId(loyaltyId);
            user.setUsrFName(firstName);
            user.setUsrLName(lastName);
            user.setUsrPassword(password);
            user.setUsrUserType(UserType.CUSTOMER);
            user.setUsrStatus(UserStatus.ACCOUNT_CREATED);

            //save the user account
            user = userService.validateAndSaveUserData(user);

            //get the customer data
            Customer customer = findByCusLoyaltyIdAndCusMerchantNo(loyaltyId,merchantNo);

            //set customer userno as the the new user created
            customer.setCusUserNo(user.getUsrUserNo());

            //save the customer entity
            saveCustomer(customer);

            return true;

        }


    }

    @Override
    public boolean registerCustomer(String loyaltyId, String password, String firstName, String lastName) throws InspireNetzException {

        //get default merchant no
        Long merchantNo = generalUtils.getDefaultMerchantNo();

        //check another user exists with same loyaltyid
        User user = userService.findByUsrLoginId(loyaltyId);

        //get the customer data
        Customer customer = null;

        //if user is registered but not validated
        if(user != null && user.getUsrRegisterStatus().intValue() == 0){

            user.setUsrFName(firstName);
            user.setUsrLName(lastName);
            user.setUsrPassword(password);

            userService.validateAndSaveUserData(user);

            customer = findByCusLoyaltyIdAndCusMerchantNo(loyaltyId,merchantNo);

        } else if( user != null && user.getUsrRegisterStatus() == 1){

            //log the error
            log.error("registerCustomerCompatible : User Already registered");

            customer = findByCusLoyaltyIdAndCusMerchantNo(loyaltyId,merchantNo);

            //get the redeemable catalogue items for the customer
            Page<Catalogue> catalogues = catalogueService.searchCatalogueByCurrencyAndCategory(customer.getCusMerchantNo(),0L,0,customer.getCusLoyaltyId(),RequestChannel.RDM_WEB,new PageRequest(0,100));

            // Create the list of catalogue
            String catalogueList = "";

            //get the redeemable items
            catalogueList = getRedeemableItemsList(catalogues);

            //create a hashmap for sms parameters
            HashMap<String , String > smsParams = new HashMap<>(0);

            //put the list into smsparams
            smsParams.put("#catalogueList",catalogueList);


            //put the placeholders into the map
            smsParams.put("#min",loyaltyId);

            MessageWrapper messageWrapper = generalUtils.getMessageWrapperObject(MessageSpielValue.CUSTOMER_REGISTRATION_DUPLICATE_REGISTER,customer.getCusLoyaltyId(),customer.getCusMobile(),customer.getCusEmail(),"",customer.getCusMerchantNo(),smsParams,MessageSpielChannel.ALL,IndicatorStatus.YES);

            userMessagingService.transmitNotification(messageWrapper);


            // Throw exception
            throw new InspireNetzException(APIErrorCode.ERR_CUSTOMER_ALREADY_ACTIVE);

        } else{

            user = new User();
            //set values to the user entity
            user.setUsrLoginId(loyaltyId);
            user.setUsrFName(firstName);
            user.setUsrLName(lastName);
            user.setUsrPassword(password);
            user.setUsrUserType(UserType.CUSTOMER);
            user.setUsrStatus(UserStatus.ACCOUNT_CREATED);
            user.setUsrRegisterStatus(IndicatorStatus.NO);

            //save the user account
            user = userService.validateAndSaveUserData(user);

            //get the customer data
            customer = findByCusLoyaltyIdAndCusMerchantNo(loyaltyId,merchantNo);

            // If the customer is null, we need to create the customer with mobile number
            // details
            if( customer == null ){

                // Log information
                log.error("registerCustomer : No customer data found");

                // Create the customer
                customer  = createCustomerObjectForLoyaltyId(loyaltyId,firstName,merchantNo,1L,0L);

                //set the register status to 0
                customer.setCusRegisterStatus(IndicatorStatus.NO);

                // Save the customer
                customer = saveCustomer(customer);

                // check if the customer is null
                if ( customer == null || customer.getCusCustomerNo() == null ) {

                    // Log the information
                    log.error("registerCustomer -> Unable to save the customer");

                    MessageWrapper messageWrapper = generalUtils.getMessageWrapperObject(MessageSpielValue.GENERAL_ERROR_MESSAGE,loyaltyId,"","","",merchantNo,new HashMap<String,String>(0),MessageSpielChannel.SMS,IndicatorStatus.NO);

                    userMessagingService.transmitNotification(messageWrapper);


                    // Throw exception
                    throw new InspireNetzException(APIErrorCode.ERR_OPERATION_FAILED);

                }

                // Log information
                log.info("registerCustomer : Customer saved successfully");

            }

        }
/* // To change OTP Generation
        //generate otp for validation
        String  otpCode = oneTimePasswordService.generateOTP(merchantNo,customer.getCusCustomerNo(),OTPType.REGISTER_CUSTOMER);

        //create a map for the sms placeholders
        HashMap<String , String > smsParams  = new HashMap<>(0);

        //put the placeholders into the map
        smsParams.put("#otpCode",otpCode);


        //send the otp to the user
        userMessagingService.sendSMS(MessageSpielValue.CUSTOMER_REGISTRATION_OTP,loyaltyId,smsParams);
*/

        //generate otp for validation
        boolean  isOTPGenerated = oneTimePasswordService.generateOTPGeneric(merchantNo, OTPRefType.CUSTOMER, customer.getCusCustomerNo().toString(), OTPType.REGISTER_CUSTOMER );
        // return true
        return isOTPGenerated;

    }




    @Override
    public boolean confirmCustomerRegistration(String loyaltyId, String otpCode,Long merchantNo) throws InspireNetzException {

        //get default merchant no
        if(merchantNo.longValue() ==0L){

            merchantNo = generalUtils.getDefaultMerchantNo();
        }


        //get the customer data
        Customer customer = findByCusLoyaltyIdAndCusMerchantNo(loyaltyId,merchantNo);

        //get the user details
        User user = userService.findByUsrLoginId(loyaltyId);

        // Check if the user is null
        if(user == null ){

            //log the error
            log.error("No User Information Found");

            //throw exception
            throw new InspireNetzException(APIErrorCode.ERR_NO_DATA);


        }

        //if no customer data is found throw error
        if(customer == null ){

            //log the error
             log.error("registerCustomer : No customer data found");

            //throw exception
            throw new InspireNetzException(APIErrorCode.ERR_NO_DATA_FOUND);

        }


        //check whether the otp is valid or not
        //Integer isOtpValid = oneTimePasswordService.validateOTP(merchantNo, customer.getCusCustomerNo(), OTPType.REGISTER_CUSTOMER, otpCode);

        Integer isOtpValid =oneTimePasswordService.validateOTPGeneric(merchantNo,OTPRefType.CUSTOMER,customer.getCusCustomerNo().toString(),OTPType.REGISTER_CUSTOMER,otpCode);


        //Check th response status
        if(isOtpValid.intValue() == OTPStatus.VALIDATED){

            //call inspireexchange api to update the user status
            boolean isValid = validateCustomerServiceDetails(customer);

            // Check if the min is valid
            if ( !isValid ) {

                //create a map for the sms placeholders
                HashMap<String , String > smsParams  = new HashMap<>(0);

                //put the placeholders into the map
                smsParams.put("#min",loyaltyId);

                MessageWrapper messageWrapper = generalUtils.getMessageWrapperObject(MessageSpielValue.LOYALTY_REGISTRATION_INVALID_MIN,loyaltyId,"","","",merchantNo,smsParams,MessageSpielChannel.SMS,IndicatorStatus.NO);

                userMessagingService.transmitNotification(messageWrapper);



                // Throw operation not allowed
                throw new InspireNetzException(APIErrorCode.ERR_OPERATION_NOT_ALLOWED);

            }


            // Set the user status to active
            user.setUsrStatus(UserStatus.ACTIVE);

            user.setUsrRegisterStatus(IndicatorStatus.YES);

            userService.saveUser(user);

            //set user no
            customer.setCusUserNo(user.getUsrUserNo());

            // Set the customer status to active
            customer.setCusStatus(CustomerStatus.ACTIVE);

            //set the register status to 0
            customer.setCusRegisterStatus(IndicatorStatus.YES);

            //set updated customer name
            customer.setCusFName(user.getUsrFName());

            //set updated last name
            customer.setCusLName(user.getUsrLName());

            //save the updated customer data
            customer = saveCustomer(customer);

            if(customer==null){

                //log the error
                log.error("registerCustomer : No customer data found");

                //throw exception
                throw new InspireNetzException(APIErrorCode.ERR_NO_DATA_FOUND);

            }


            //process referral
            processReferralAwarding(customer);

            //process signup bonus
            processSignUpBonus(customer);

            //log the activity
            customerActivityService.logActivity(customer.getCusLoyaltyId(),CustomerActivityType.REGISTER,"Customer registration successful",customer.getCusMerchantNo(),"");

            //get the redeemable catalogue items for the customer
            Page<Catalogue> catalogues = catalogueService.searchCatalogueByCurrencyAndCategory(customer.getCusMerchantNo(),0L,0,customer.getCusLoyaltyId(),RequestChannel.RDM_WEB,new PageRequest(0,100));


            // Create the list of catalogue
            String catalogueList = "";

            //get the redeemable items
            catalogueList = getRedeemableItemsList(catalogues);

            //create a hashmap for sms parameters
            HashMap<String , String > smsParams = new HashMap<>(0);

            //put the list into smsparams
            smsParams.put("#catalogueList",catalogueList);


            MessageWrapper messageWrapper = generalUtils.getMessageWrapperObject(MessageSpielValue.CUSTOMER_REGISTRATION_SUCCESS,loyaltyId,customer.getCusMobile(),customer.getCusEmail(),"",merchantNo,new HashMap<String,String>(0),MessageSpielChannel.SMS,IndicatorStatus.NO);

            userMessagingService.transmitNotification(messageWrapper);



            // finally return true
            return true;

        } else if(isOtpValid.intValue() == OTPStatus.OTP_NOT_VALID){


            //throw exception
            throw new InspireNetzException(APIErrorCode.ERR_INVALID_OTP);

        } else {

            throw new InspireNetzException(APIErrorCode.ERR_OTP_EXPIRED);

        }

    }

    @Override
    public boolean changeNotificationStatus(String loyaltyId,Long merchantNo) throws InspireNetzException {

        MessageWrapper messageWrapper=generalUtils.getMessageWrapperObject(MessageSpielValue.OPT_OUT_OF_NOTIFICATIONS_SMS,loyaltyId,"","","",merchantNo,new HashMap<String, String>(0),MessageSpielChannel.ALL,IndicatorStatus.YES);

        //get the customer details
        Customer customer = findByCusLoyaltyIdAndCusMerchantNo(loyaltyId,merchantNo);

        if(customer == null){

            //log error
            log.error("changeNotificationStatus : No customer data found ");

            throw new InspireNetzException(APIErrorCode.ERR_NO_DATA_FOUND);
        }

        customer.setCusReceiveNotifications(IndicatorStatus.NO);

        customer = saveCustomer(customer);

        messageWrapper.setLoyaltyId(customer.getCusLoyaltyId());

        messageWrapper.setMerchantNo(customer.getCusMerchantNo());

        messageWrapper.setChannel(MessageSpielChannel.ALL);

        messageWrapper.setIsCustomer(IndicatorStatus.YES);

        userMessagingService.transmitNotification(messageWrapper);

        //userMessagingService.sendSMS(MessageSpielValue.OPT_OUT_OF_NOTIFICATIONS_SMS, loyaltyId, new HashMap<String, String>(0));

        return true;

    }


    public boolean whiteListRetailer(String loyaltyId,Long merchantNo) throws InspireNetzException {

        //get the customer details
        Customer customer = findByCusLoyaltyIdAndCusMerchantNo(loyaltyId, merchantNo);

        //check if customer exists
        if(customer == null){

            //log error
            log.error("whiteListRetailer : No Customer Information Found");

            //throw error
            throw new InspireNetzException(APIErrorCode.ERR_NO_DATA_FOUND);
        }

        //check whether the customer is a retailer
        if(customer.getCusType().intValue() != CustomerType.RETAILER){

            //log error
            log.error("whiteListRetailer : Customer is not a retailer");

            //throw error
            throw new InspireNetzException(APIErrorCode.ERR_OPERATION_NOT_ALLOWED);
        }

        //set the customerwhitelistd status to true
        customer.setCusIsWhiteListed(IndicatorStatus.YES);

        //update the customer details
        saveCustomer(customer);

        return true;

    }

    /**
     * @Purpose:checking point  birthday and anniversary point is previously awarded or not
     * @param customerProfile
     * @param dtType
     * @return:boolean
     * @date:10-12-2014
     */
    @Override
    public boolean isCustomerValidForDTAwarding(CustomerProfile customerProfile, Integer dtType) {

        Date awardedDate = null;

        if ( dtType == DateTriggeredAwardingType.BIRTHDAY ) {

            awardedDate = customerProfile.getCspBirthDayLastAwarded();

        } else {

            awardedDate = customerProfile.getCspAnniversaryLastAwarded();

        }


        // If the last awarded date is null, then the user was never awarded
        // Return true
        if ( awardedDate == null ) {

            return true;

        }


        //first to convert sql date to calender object
        Calendar awardDateCal = Calendar.getInstance();

        awardDateCal.setTime(awardedDate);

        //find date after one year
        awardDateCal.add(Calendar.YEAR, 1);

        java.util.Date afterOneYearDate = awardDateCal.getTime();

        log.info("CustomerServiceImpl::isCustomerValidForDTAwarding ==>Last Birth day Awarded date:"+awardDateCal);

        log.info("CustomerServiceImpl::isCustomerValidForDTAwarding ==>after one year  Awarded date:"+afterOneYearDate);

        //pick today date
        java.util.Date currentDate =new java.util.Date();

        //compare the date if date is less than return false otherwise return true
        Integer dateFlag=currentDate.compareTo(afterOneYearDate);

        //dateFlag  is greater than or equal return true
        if(dateFlag >=0){

            return true;

        } else {

            return false;

        }

    }


    /**
     * Function to get the customer object for the given loyalty id and merchant number
     *
     * @param loyaltyId  - Loyalty id of the customer
     * @param merchantNo - Merchant number of the merchant
     *
     * @return           - Return the Customer object by setting the default details
     */
    protected Customer createCustomerObjectForLoyaltyId(String loyaltyId, String firstName ,Long merchantNo,Long location, Long usrRegistered) {

        // Create the customer
        Customer customer  = new Customer();

        // Set the fields
        customer.setCusMerchantNo(merchantNo);
        customer.setCusFName(firstName);
        customer.setCusLoyaltyId(loyaltyId);
        customer.setCusStatus(CustomerStatus.INACTIVE);
        customer.setCusLocation(location);
        customer.setCusMerchantUserRegistered(usrRegistered);
        customer.setCreatedBy(usrRegistered.toString());
        customer.setCusMobile(loyaltyId);

        // Return the customer
        return customer;
    }

    @Override
    public Customer createCustomerObjectForLoyaltyId(String loyaltyId, String mobile,String email,String firstName ,Long merchantNo,Long location, Long usrRegistered,String referralCode) {

        // Create the customer
        Customer customer  = new Customer();

        // Set the fields
        customer.setCusMerchantNo(merchantNo);
        customer.setCusFName(firstName);
        customer.setCusLoyaltyId(loyaltyId);
        customer.setCusStatus(CustomerStatus.INACTIVE);
        customer.setCusLocation(location);
        customer.setCusMerchantUserRegistered(usrRegistered);
        customer.setCreatedBy(usrRegistered.toString());
        customer.setCusMobile(mobile);
        customer.setCusEmail(email);
        customer.setReferralCode(referralCode);

        // Return the customer
        return customer;
    }

    @Override
    public Customer connectMerchantThroughPortal(Long merchantNo,Long usrUserNo,String referralCode) throws InspireNetzException {

        //get user information
        Customer customer =null;

        User user =userService.findByUsrUserNo(usrUserNo);

        //check user type is null or not
        if(user ==null || user.getUsrStatus() ==UserStatus.DEACTIVATED || user.getUsrRegisterStatus().intValue() ==IndicatorStatus.NO || user.getUsrUserType() !=UserType.CUSTOMER){

            //throw error not authorized
            throw new InspireNetzException(APIErrorCode.ERR_NOT_AUTHORIZED);
        }

        //check the customer is alredy connect or not
       customer =findByCusUserNoAndCusMerchantNo(user.getUsrUserNo(),merchantNo);

        //check the customer is not null
        if(customer !=null && customer.getCusRegisterStatus().intValue() ==IndicatorStatus.YES){

            //customer alredy connected
            throw new InspireNetzException(APIErrorCode.ERR_CUSTOMER_ALREADY_ACTIVE);
        }

        //else to create customer object and connect
        if(customer ==null){

            //create new object
            customer =createCustomerObjectForLoyaltyId(user.getUsrLoginId(),user.getUsrLoginId(),user.getUsrEmail(),user.getUsrFName(),merchantNo,0L,0L,referralCode);

        }

        //call connect method
        customer =connectMerchant(merchantNo,customer,CustomerRegisterType.CUSTOMER_PORTAL);


        return customer;
    }

    @Override
    public Customer findByCusUserNoAndCusMerchantNo(Long cusUserNo, Long cusMerchantNo) {
        return customerRepository.findByCusUserNoAndCusMerchantNo(cusUserNo, cusMerchantNo);
    }

    @Override
    public Customer findByCusUserNoAndCusMerchantNoAndCusRegisterStatus(Long cusUserNo,Long cusMerchantNo,Integer cusRegisterStatus){
        return customerRepository.findByCusUserNoAndCusMerchantNoAndCusRegisterStatus(cusUserNo, cusMerchantNo,cusRegisterStatus);
    }

    @Override
    public List<String> getEmailInformationForMerchant(Long ntcMerchantNo) {

        //initialise the email list
        List<String> emailList = new ArrayList<>();

        //get the customers for the merchant
        List<Customer> customers = findByCusMerchantNo(ntcMerchantNo);

        //iterate through the customer list to get the emails
        for(Customer customer : customers){

            //check if the customer is having a valid email
            if(customer.getCusEmail() != null && customer.getCusEmail().length() > 0){


                //add email id to the email list
                emailList.add(customer.getCusEmail());
            }

        }

        //return the email list
        return emailList;
    }

    @Override
    public List<String> getSMSInformationForMerchant(Long ntcMerchantNo) {

        //initialise the mobile list
        List<String> mobileList = new ArrayList<>();

        //get the customers for the merchant
        List<Customer> customers = findByCusMerchantNo(ntcMerchantNo);

        //iterate through the customer list to get the emails
        for(Customer customer : customers){

            //check if the customer is having a valid email
            if(customer.getCusMobile() != null && customer.getCusMobile().length() > 0){


                //add email id to the email list
                mobileList.add(customer.getCusMobile());
            }

        }

        //return the email list
        return mobileList;
    }

    @Override
    public Customer updateCustomerBulkUpload(Customer customer,Long merchantNo) {

        //first


        return null;
    }

    @Override
    public Customer saveCustomerDetailsFromXl(CustomerResource customerResource, CustomerProfileResource customerProfileResource,Long merchantNo,Long userNo,String usrLoginId,Long userLocation) throws InspireNetzException {

        //set flag for identifying customer is connecting merchant
        boolean isConnectMerchant=false;

        boolean isReRegister =false;

        // Object storing customer
        Customer customer;

        // First check if the request is update
        if ( customerResource.getCusCustomerNo() != null ) {

            // Get the customer
            customer = findByCusCustomerNo(customerResource.getCusCustomerNo());

            // If the customer is null throw exception
            if ( customer == null ) {

                // Log the information
                log.info("saveCustomerDetails -> Customer is not found");

                // throw exception
                throw new InspireNetzException(APIErrorCode.ERR_NO_DATA_FOUND);

            }

            // Map the customer
            mapper.map(customerResource,customer);


        } else {

            // Map the object check unregistered customer
            customer =findByCusLoyaltyIdAndCusMerchantNo(customerResource.getCusLoyaltyId(),merchantNo);

            if(customer !=null && customer.getCusRegisterStatus().intValue() ==IndicatorStatus.NO){

                //set customer number
                customerResource.setCusCustomerNo(customer.getCusCustomerNo());

                //set merchant user registered
                customerResource.setCusMerchantUserRegistered(userNo);

                //isReRegister
                isReRegister =true;

                //map resource
                mapper.map(customerResource, customer);

            }else{
                //normal mapping
                //map resource
                customer = mapper.map(customerResource,Customer.class);

            }



        }

        // Set the merchantNumber for the customer
        customer.setCusMerchantNo(merchantNo);

        // Map the customerProfile
        CustomerProfile customerProfile =  mapper.map(customerProfileResource,CustomerProfile.class);

        // Create the Validator for customer
        CustomerValidator cusValidator = new CustomerValidator(dataValidationUtils);

        // Create the BeanPropertyBindingResult
        BeanPropertyBindingResult cusResult = new BeanPropertyBindingResult(customer,"customer");

        // Validate customer
        cusValidator.validate(customer,cusResult);


        // Create the Validator for CustomerProfile
        CustomerProfileValidator   customerProfileValidator = new CustomerProfileValidator();

        // Validate the customerProfile
        customerProfileValidator.validate(customerProfile,cusResult);


        // Check if the result contains errors
        if ( cusResult.hasErrors() ) {

            // Get the validation message
            String messages = dataValidationUtils.getValidationMessages(cusResult);

            // Log the response
            log.info("saveCustomerDetails - Response : Invalid Input - "+ messages);

            // Throw InspireNetzException with INVALID_INPUT as error
            throw new InspireNetzException(APIErrorCode.ERR_INVALID_INPUT,messages);

        }

        // Check if the customer is already existing
        boolean isExist = isDuplicateCustomerExisting(customer);

        // Check the boolean value
        if ( isExist ) {

            // Log the response
            log.info("saveCustomerDetails - Response : Customer is already existing");

            // Throw InspireNetzException with ERR_DUPLICATE_ENTRY as error
            throw new InspireNetzException(APIErrorCode.ERR_DUPLICATE_ENTRY);

        }

        //check duplicate mobile existing or not
        boolean isDuplicateMobile =isDuplicateMobileExisting(customer);

        //check mobile is exist or not
        if(isDuplicateMobile){

            // Log the response
            log.info("saveCustomerDetails - Response : Duplicate Mobile number"+customer.toString());

            // Throw InspireNetzException with ERR_DUPLICATE_ENTRY as error
            throw new InspireNetzException(APIErrorCode.ERR_DUPLICATE_MOBILE);

        }

        // If the customerLocation field is not set, then we need to set the
        // the location to the location of the merchant user registering the customer
        if ( customer.getCusLocation() == null || customer.getCusLocation() == 0L ) {

            customer.setCusLocation(userLocation);

        }

        //if merchant register is


        // Hold the audit details
        String auditDetails = userNo+ "#" + usrLoginId;

        // If the customer getCusCustomerNo is  null, then set the created_by, else set the updated_by
        if ( customer.getCusCustomerNo() == null ) {

            // Store the merchantUserRegistered field for the Customer
            if(customer.getCusMerchantUserRegistered() ==null || customer.getCusMerchantUserRegistered().longValue()==0L ){

                customer.setCusMerchantUserRegistered(userNo);

            }

            // Set the createdBy to the auditDetails
            customer.setCreatedBy(auditDetails);


        } else {

            customer.setUpdatedBy(auditDetails);

            // Get the customerProfile object
            CustomerProfile baseProfile = customerProfileService.findByCspCustomerNo(customer.getCusCustomerNo());

            // If the baseProfile is not null, then we need to set the
            // cspId of the customerProfiile
            if ( baseProfile != null ) {

                customerProfile.setCspId(baseProfile.getCspId());


                //set last birthday and anniversary awarded date  for update mode
                if(customerProfile.getCspId() !=null){

                    //fetch base profile and set birthday and anniversary awarded date
                    customerProfile.setCspBirthDayLastAwarded(baseProfile.getCspBirthDayLastAwarded());

                    customerProfile.setCspAnniversaryLastAwarded(baseProfile.getCspAnniversaryLastAwarded());


                }

            }

        }


        //check user exist with this mobile number
        if ( customer.getCusCustomerNo() == null  || isReRegister) {

            //call to connect merchant
            customer = connectMerchant(merchantNo,customer,CustomerRegisterType.MERCHANT_PORTAL);

            //set connect flag
            isConnectMerchant =true;

        }

        // only we need to process save merchant only when customer not connect if the customer is connect already save and evaluate customer
        if(!isConnectMerchant){

            customer = validateAndSaveCustomer(customer);

            //evaluate tier
            evaulateCustomerTier(customer);

        }

        // If the customer object is not null ,then return the success object
        if ( customer.getCusCustomerNo() != null ) {

            // Set the customerNo for the customer object
            customerProfile.setCspCustomerNo(customer.getCusCustomerNo());

            // Save the customerProfile
            customerProfile = customerProfileService.saveCustomerProfile(customerProfile);

        } else {

            // Log the response
            log.info("saveCustomer - Response : Unable to save the customer information");

            // Throw InspireNetzException with ERR_OPERATION_FAILED as error
            throw new InspireNetzException(APIErrorCode.ERR_OPERATION_FAILED);

        }

        // Return the customer object
        return customer;


    }


    private Customer processCustomer(CustomerResource customerResource, CustomerProfileResource customerProfileResource) throws InspireNetzException {

        //process customer information
        return saveCustomerDetails(customerResource,customerProfileResource);
    }


    protected String getRedeemableItemsList(Page<Catalogue> catalogues){


        String catalogueList = "";

       /* //variable for storing count of items added to list
        int i = 0;
*/
        // Iterate through the list and then populate the catalogue data
        for( Catalogue catalogue : catalogues ) {

            //add item details to the list
            catalogueList += "ITEM CODE - "+catalogue.getCatProductCode() + " :  ITEM NAME - "+catalogue.getCatDescription() + "\n";

           /* //increment items count
            i++;

            //if 5 items are added , break from the loop
            if(i > 4){

                break;
            }*/
        }

        //check the size of the catalogue list , if its empty add no catalogue message
        if(catalogueList.equals("") || catalogueList.length() == 0){

            catalogueList = "No redeemable items found ";
        }

        return catalogueList;
    }

    @Override
    public List<Map<String, Object>> getCustomerMemberShips(Long merchantNo, String loyaltyId){

        //get the merchant details
        Merchant merchant = merchantService.findByMerMerchantNo(merchantNo);

        //create list for returning membership data
        List<Map<String , Object> > membershipList = new ArrayList<>();

        //map holds the merhant details
        Map<String ,Object > membershipData = new HashMap<>();

        //put the merchant details to the map
        membershipData.put("merchant_no",merchantNo+"");
        membershipData.put("merchant_name",merchant.getMerMerchantName());
        membershipData.put("loyalty_id",loyaltyId);
        membershipData.put("mobile_order_enabled","0");

        // Get the Image
        Image imgLogo = merchant.getImgLogo();

        //get the cover image of the merchant
        Image coverImage = merchant.getImgCoverImage();

        //variable for image path
        String imagePath = "";

        //variable for cover image path
        String coverImagePath = "";

        //get the image url from proprties file
        String imageUrl = environment.getProperty("IMAGE_PATH_URL");

        // If the logo is not null, then set the page
        if ( imgLogo != null ) {

            // Get the imagePath
            imagePath = imageService.getPathForImage(imgLogo, ImagePathType.STANDARD);

            //get the cover image path
            coverImagePath = imageService.getPathForImage(coverImage, ImagePathType.STANDARD);
        }

        //set image path
        membershipData.put("merchant_image",imageUrl + imagePath);

        //set cover image path
        membershipData.put("merchant_cover_image",imageUrl + coverImagePath);

        //set image id
        membershipData.put("merchant_image_id",merchant.getMerMerchantImage()+"");

        //set cover image id
        membershipData.put("merchant_cover_image_id",merchant.getMerCoverImage()+"");

        //add contact details
        membershipData.put("merchant_phone",merchant.getMerPhoneNo());

        //add email to membership data
        membershipData.put("merchant_email",merchant.getMerEmail());

        //get the merchant address
        String address = merchant.getMerAddress1() == null ? "":merchant.getMerAddress1()+
                         merchant.getMerAddress2() == null ? "":merchant.getMerAddress2()+
                         merchant.getMerAddress3() == null ? "":merchant.getMerAddress3()+
                         merchant.getMerCity() == null ? "":merchant.getMerCity()+
                         merchant.getMerState() == null ? "":merchant.getMerState();

        //add merchant address to membership data
        membershipData.put("merchant_address",address);

        //variable holding the reward balance
        String displayPoints  = "0";

        //get the reward balance list of the customer
        List<CustomerRewardBalance> customerRewardBalanceList = customerRewardBalanceService.getBalanceList(merchantNo,loyaltyId);

        //check if customer has any reward balance
        if(customerRewardBalanceList.size() > 0){

            //set reward balance to the field
            displayPoints = customerRewardBalanceList.get(0).getCrbRewardBalance()+"";
        }

        //add reward balance info to membership data
        membershipData.put("display_points",displayPoints);

        //load merchant locations
        merchant.getMerchantLocationSet().toString();

        //list for holding the merchant locations
        List<Map<String , String>> locationList = new ArrayList<>();

        Map<String, String > location = new HashMap<>();

        //iterate through the merchant locations,and add them to the list
        for(MerchantLocation merchantLocation : merchant.getMerchantLocationSet()){

            location = new HashMap<>();

            //put the location details in to the map
            location.put("location",merchantLocation.getMelLocation());
            location.put("latitude",merchantLocation.getMelLatitude());
            location.put("longitude",merchantLocation.getMelLongitude());

            //add map object to the list
            locationList.add(location);

        }

        //add location details list to membership data
        membershipData.put("merchant_locations",locationList);

        //add the current merchant details to the list
        membershipList.add(membershipData);

        //return the list
        return membershipList;
    }

    @Override
    public boolean isRegistrationKeyValid(String loyaltyId, String password, String regKey) throws InspireNetzException {

        //check the authenticity of the request
        String authKey = userService.getRegistrationAuthenticationKey(loyaltyId,password);

        /*//check the two keys are matching
         * Edit disabled by Sandeep on 2014-12-18 as the data was giving error
        if(!regKey.equals(authKey)){

            //log the error
            log.error("registerCustomerCompatible :  Authentication Keys doesn't match");

            throw new InspireNetzException(APIErrorCode.ERR_NOT_AUTHORIZED);

        }*/

        //return true
        return  true;
    }

    @Override
    protected BaseRepository<Customer,Long> getDao() {
        return customerRepository;
    }

    @Override
    public List<Customer> findByCusMerchantNoAndCusLoyaltyIdOrCusEmailOrCusMobile(Long cusMerchantNo, String cusLoyaltyId, String cusEmail, String cusMobile) {

        // Get the customer
        List<Customer> customers = customerRepository.findByCusMerchantNoAndCusLoyaltyIdOrCusEmailOrCusMobile(cusMerchantNo, cusLoyaltyId, cusEmail, cusMobile);

        // Return the customer object
        return customers;

    }

    @Override
    public Customer getCustomerProfileCompatible( String cusLoyaltyId, String cusEmail, String cusMobile) throws InspireNetzException{

        // Get the merchant number and store
        Long merchantNo = authSessionUtils.getMerchantNo();

        //validate cusLoyaltyId,cusEmail,cusMobile
        if((cusLoyaltyId == null || cusLoyaltyId.equals("")) && (cusEmail == null || cusEmail.equals("")) && (cusMobile == null||cusMobile.equals(""))){

            //log the error
            log.error("saveCustomerCompatible :  Please specify loyaltyid /email /mobile");

            throw new InspireNetzException(APIErrorCode.ERR_NO_INFORMATION);

        }

        // Get the customer information
        List<Customer> customers = customerRepository.findByCusMerchantNoAndCusLoyaltyIdOrCusEmailOrCusMobile(merchantNo, cusLoyaltyId, cusEmail, cusMobile);

        // If no data found then show the error message
        if ( customers == null || customers.size()==0 ) {

            // Log the information
            log.info("getCustomerProfileCompatible - No customer information found");

            // Throw the exception
            throw new InspireNetzException(APIErrorCode.ERR_NO_INFORMATION);

        }

        //fetch first one from customer list
        Customer customer=customers.get(0);

        // Check if the customer belongs to the merchant by the merchant user
        if ( customer.getCusMerchantNo().longValue() != merchantNo ) {

            // Log the information
            log.info("getCustomerProfileCompatible - Customer does not belong to you");

            // Throw the exception
            throw new InspireNetzException(APIErrorCode.ERR_NO_AUTHENTICATION);

        }

        // Get the CustomerProfile information for the Customer
        CustomerProfile customerProfile = customerProfileService.findByCspCustomerNo(customer.getCusCustomerNo());

        // Set the customerProfile
        customer.setCustomerProfile(customerProfile);

        //return customer object
        return customer;
    }

    @Override
    public boolean isDuplicateCustomerExistCompatible(Long merchantNo,String cusLoyaltyId,String cusEmail,String cusMobile,Long customerNo) {

        // Get the customer information
        List<Customer> duplicateCustomer = customerRepository.findByCusMerchantNoAndCusLoyaltyIdOrCusEmailOrCusMobile(merchantNo,cusLoyaltyId,cusEmail,cusMobile);

        //check if exist any customers with same loyaltyId or email or mobileNo
        if ( duplicateCustomer == null || duplicateCustomer.size()== 0) {

            return false;

        }else{

            if(customerNo==0){

                return true;
            }

            for(Customer customer:duplicateCustomer){

                if(customer.getCusCustomerNo().longValue()!=customerNo.longValue()){

                    return true;
                }
            }

            return false;
        }


    }

    //Function to register the customer by the merchant user through WPF .

    @Override
    public boolean saveCustomerCompatible(Long customerNo,String loyaltyId, String firstName, String lastName, String email, String mobile, String address, String city, String pincode ,String birthday,String anniversary,String referralCode) throws InspireNetzException {

        //access rights
        authSessionUtils.validateFunctionAccess(FunctionCode.FNC_UPDATE_CUSTOMER);

        // Get the merchant number for the session
        Long merchantNo = authSessionUtils.getMerchantNo();

        //Get the user location
        Long userLocation=authSessionUtils.getUserLocation();

        // Hold the audit details
        String auditDetails = authSessionUtils.getUserNo().toString() + "#" + authSessionUtils.getUserLoginId();

        //connect merchant
        boolean connectMerchant =false;

        boolean isReRegister=false;

        // Object for storing customer
        Customer customer;

        //object for storing customer profile
        CustomerProfile customerProfile=new CustomerProfile();

        // First check if the request is update
        if ( customerNo.longValue() != 0L ) {

            // Get the customer
            customer=customerRepository.findByCusCustomerNo(customerNo);

            // If the customer is null throw exception
            if ( customer == null ) {

                // Log the information
                log.info("saveCustomerDetails -> Customer is not found");

                // throw exception
                throw new InspireNetzException(APIErrorCode.ERR_NO_DATA_FOUND);

            }



        } else {

            // Map the object
            customer=new Customer();

            customer.setCusLoyaltyId(loyaltyId);

            customer.setCusMobile(mobile);

            // Map the object check unregistered customer
            Customer fetchedCustomer =findByCusLoyaltyIdAndCusMerchantNo(loyaltyId,merchantNo);

            if(fetchedCustomer !=null && fetchedCustomer.getCusRegisterStatus().intValue() ==IndicatorStatus.NO){

                //set customer number
                customer.setCusCustomerNo(fetchedCustomer.getCusCustomerNo());

                //set merchant user registered
                customer.setCusMerchantUserRegistered(authSessionUtils.getUserNo());

                customer.setCusRegisterStatus(IndicatorStatus.NO);

                //isReRegister
                isReRegister =true;

            }



        }


        customer.setCusFName(firstName);

        customer.setCusLName(lastName);

        customer.setCusEmail(email);



        customer.setCusMerchantNo(merchantNo);

        customer.setCusLocation(userLocation);

        customerProfile.setCspAddress(address);

        customerProfile.setCspCity(city);

        customerProfile.setCspPincode(pincode);

        Date date = DBUtils.covertToSqlDate(birthday);

        customerProfile.setCspCustomerBirthday(date);

        Date utilAnniversaryDate = DBUtils.covertToSqlDate(anniversary);

        customerProfile.setCspCustomerAnniversary(utilAnniversaryDate);


        // Create the Validator for customer
        CustomerValidator cusValidator = new CustomerValidator(dataValidationUtils);

        // Create the BeanPropertyBindingResult
        BeanPropertyBindingResult cusResult = new BeanPropertyBindingResult(customer,"customer");

        // Validate customer
        cusValidator.validate(customer,cusResult);

        // Create the Validator for CustomerProfile
        CustomerProfileValidator   customerProfileValidator = new CustomerProfileValidator();

        // Validate the customerProfile
        customerProfileValidator.validate(customerProfile,cusResult);

        // Check if the result contains errors
        if ( cusResult.hasErrors() ) {

            // Get the validation message
            String messages = dataValidationUtils.getValidationMessages(cusResult);

            // Log the error
            log.error("saveCustomerCompatible : Invalid Input - " + messages);

            // Throw InspireNetzException with INVALID_INPUT as error
            throw new InspireNetzException(APIErrorCode.ERR_INVALID_INPUT,messages);

        }

        //check if loyaltyId and email and mobile are empty
        if((customer.getCusLoyaltyId() == null || customer.getCusLoyaltyId().equals("")) && (customer.getCusEmail() == null || customer.getCusEmail().equals("")) && (customer.getCusMobile() == null||customer.getCusMobile().equals(""))){

            //log the error
            log.error("saveCustomerCompatible :  Please specify loyaltyid /email /mobile");

            throw new InspireNetzException(APIErrorCode.ERR_NO_ID_FIELD);

        }


        if(isDuplicateCustomerExistCompatible(merchantNo,loyaltyId,email,mobile,customerNo)){

            //log the error
            log.error("saveCustomerCompatible :  Customer is already existing");

            throw new InspireNetzException(APIErrorCode.ERR_DUPLICATE_ID_FIELD);
        }

        // If the customer getCusCustomerNo is  null, then set the created_by, else set the updated_by
        if ( customer.getCusCustomerNo() == null ) {

            // Store the merchantUserRegistered field for the Customer
            customer.setCusMerchantUserRegistered(authSessionUtils.getUserNo());

            // Set the createdBy to the auditDetails
            customer.setCreatedBy(auditDetails);


            //Set Customer Referral Code
            customer.setReferralCode(referralCode);

            // Set the createby for the customerProfile
            // customerProfile.setCreatedBy(auditDetails);

        } else {

            customer.setUpdatedBy(auditDetails);

            // Get the customerProfile object
            CustomerProfile baseProfile = customerProfileService.findByCspCustomerNo(customer.getCusCustomerNo());

            // If the baseProfile is not null, then we need to set the
            // cspId of the customerProfiile
            if ( baseProfile != null ) {

                customerProfile.setCspId(baseProfile.getCspId());


                //set last birthday and anniversary awarded date  for update mode
                if(customerProfile.getCspId() !=null){

                    //fetch base profile and set birthday and anniversary awarded date
                    customerProfile.setCspBirthDayLastAwarded(baseProfile.getCspBirthDayLastAwarded());

                    customerProfile.setCspAnniversaryLastAwarded(baseProfile.getCspAnniversaryLastAwarded());


                }

            }

        }


        //check user exist with this mobile number
        if ( customer.getCusCustomerNo() == null || (customer.getCusCustomerNo() !=null && customer.getCusRegisterStatus() ==IndicatorStatus.NO) ) {


            //call connect merchant
            connectMerchant(customer.getCusMerchantNo() ,customer,CustomerRegisterType.MERCHANT_PORTAL);

            //enabled the connected flag
            connectMerchant =true;


        }

        //only evaluate when customer not connect merchant
        if(!connectMerchant){

            // save the customer object and get the result
            customer = validateAndSaveCustomer(customer);

            //tier evaluation
            evaulateCustomerTier(customer);

        }


        // If the customer object is not null ,then return the success object
        if ( customer.getCusCustomerNo() != null ) {

            // Set the customerNo for the customer object
            customerProfile.setCspCustomerNo(customer.getCusCustomerNo());

            // Save the customerProfile
            customerProfile = customerProfileService.saveCustomerProfile(customerProfile);

        } else {

            // Log the response
            log.info("saveCustomer - Response : Unable to save the customer information");

            // Throw InspireNetzException with ERR_OPERATION_FAILED as error
            throw new InspireNetzException(APIErrorCode.ERR_OPERATION_FAILED);

        }


        //Return isRegistered
        return true;
    }


    @Override
    public List<Customer> findByCusMobile(String cusMobile) {

        return customerRepository.findByCusMobile(cusMobile);
    }

    @Override
    public List<Customer> findByCusUserNoAndCusStatus(Long cusUserNo, Integer cusStatus) {

        return customerRepository.findByCusUserNoAndCusStatus(cusUserNo,cusStatus);

    }

    @Override
    public List<Customer> findByCusUserNoAndCusRegisterStatus(Long cusUserNo, Integer cusRegisterStatus) {
        return customerRepository.findByCusUserNoAndCusRegisterStatus(cusUserNo,cusRegisterStatus);
    }

    @Override
    public List<Customer> getUserMemberships(Long cusMerchantNo,Long cusUserNo,Integer cusStatus){


        if(cusMerchantNo==null||cusMerchantNo.longValue()==0L){

            return customerRepository.findByCusUserNoAndCusStatus(cusUserNo,cusStatus);

        }else{

            return customerRepository.findByCusMerchantNoAndCusUserNoAndCusStatus(cusMerchantNo,cusUserNo,cusStatus);
        }
    }

    @Override
    public Customer validateAndRegisterCustomerThroughXml(Customer customer) throws InspireNetzException {

        //access  validity
        authSessionUtils.validateFunctionAccess(FunctionCode.FNC_UPDATE_CUSTOMER);

        // Get the merchant number for the session
        Long merchantNo = authSessionUtils.getMerchantNo();

        //boolean to connect merchant
        boolean isConnectMerchant =false;

        // First check if the request is update
        if ( customer.getCusCustomerNo() != null ) {

            // Get the customer
            Customer fetchedCustomer = findByCusCustomerNo(customer.getCusCustomerNo());

            // If the customer is null throw exception
            if ( customer == null ) {

                // Log the information
                log.info("validateAndRegisterCustomerThroughXml -> Customer is not found");

                // throw exception
                throw new InspireNetzException(APIErrorCode.ERR_NO_DATA_FOUND);

            }
            customer.setCusCustomerNo(fetchedCustomer.getCusCustomerNo());

            customer.setCusLoyaltyId(fetchedCustomer.getCusLoyaltyId());

            customer.setCusMobile(fetchedCustomer.getCusMobile());

            customer.setCusMerchantNo(fetchedCustomer.getCusMerchantNo());

            customer.setCusEmail(fetchedCustomer.getCusEmail());


        }else{

            // Map the object check unregistered customer
            Customer fetchedCustomer =findByCusLoyaltyIdAndCusMerchantNo(customer.getCusLoyaltyId(),merchantNo);


            if(fetchedCustomer !=null && ( fetchedCustomer.getCusRegisterStatus() ==null ||(fetchedCustomer.getCusRegisterStatus() !=null && fetchedCustomer.getCusRegisterStatus().intValue() ==IndicatorStatus.NO))){

                //set customer number
                customer.setCusCustomerNo(fetchedCustomer.getCusCustomerNo());

                //set merchant user registered
                customer.setCusMerchantUserRegistered(authSessionUtils.getUserNo());

                customer.setCusRegisterStatus(IndicatorStatus.NO);

            }
        }

        // Create the Validator for customer
        CustomerValidator cusValidator = new CustomerValidator(dataValidationUtils);

        // Create the BeanPropertyBindingResult
        BeanPropertyBindingResult cusResult = new BeanPropertyBindingResult(customer,"customer");

        // Validate customer
        cusValidator.validate(customer,cusResult);

        // Check if the result contains errors
        if ( cusResult.hasErrors() ) {

            // Get the validation message
            String messages = dataValidationUtils.getValidationMessages(cusResult);

            // Log the response
            log.info("validateAndRegisterCustomerThroughXml - Response : Invalid Input - "+ messages);

            // Throw InspireNetzException with INVALID_INPUT as error
            throw new InspireNetzException(APIErrorCode.ERR_INVALID_INPUT,messages);

        }

        // Check if the customer is already existing
        boolean isExist = isDuplicateCustomerExistCompatible(customer.getCusMerchantNo(), customer.getCusLoyaltyId(), customer.getCusEmail(), customer.getCusMobile(), customer.getCusCustomerNo()==null?0:customer.getCusCustomerNo());

        // Check the boolean value
        if ( isExist ) {

            // Log the response
            log.info("validateAndRegisterCustomerThroughXml - Response : Customer is already existing");

            // Throw InspireNetzException with ERR_DUPLICATE_ENTRY as error
            throw new InspireNetzException(APIErrorCode.ERR_DUPLICATE_ENTRY);

        }

        // If the customerLocation field is not set, then we need to set the
        // the location to the location of the merchant user registering the customer
        if ( customer.getCusLocation() == null || customer.getCusLocation() == 0L ) {

            customer.setCusLocation(authSessionUtils.getUserLocation());

        }

        // Hold the audit details
        String auditDetails = authSessionUtils.getUserNo().toString() + "#" + authSessionUtils.getUserLoginId();

        // If the customer getCusCustomerNo is  null, then set the created_by, else set the updated_by
        if ( customer.getCusCustomerNo() == null || customer.getCusCustomerNo().longValue() ==0L) {

            // Store the merchantUserRegistered field for the Customer
            customer.setCusMerchantUserRegistered(authSessionUtils.getUserNo());

            // Set the createdBy to the auditDetails
            customer.setCreatedBy(auditDetails);

            // Set the createby for the customerProfile
            // customerProfile.setCreatedBy(auditDetails);

        } else {

            customer.setUpdatedBy(auditDetails);

            // Get the customerProfile object
            CustomerProfile baseProfile = customerProfileService.findByCspCustomerNo(customer.getCusCustomerNo());
        }

        //check user exist with this mobile number
        if ( customer.getCusCustomerNo() == null || customer.getCusCustomerNo().longValue() ==0L || (customer.getCusCustomerNo() !=null && customer.getCusRegisterStatus().intValue() ==IndicatorStatus.NO)) {

            //connect merchant
            connectMerchant(customer.getCusMerchantNo(),customer,CustomerRegisterType.MERCHANT_PORTAL);

            isConnectMerchant =true;

            }

        //check if connect merchant is true or false
        if(!isConnectMerchant){

            // save the customer object and get the result
            customer = validateAndSaveCustomer(customer);

            //evaluate tier
            evaulateCustomerTier(customer);

        }

        // If the customer object is not null ,then return the success object
        if ( customer.getCusCustomerNo() == null ) {

            // Log the response
            log.info("validateAndRegisterCustomerThroughXml - Response : Unable to save the customer information");

            // Throw InspireNetzException with ERR_OPERATION_FAILED as error
            throw new InspireNetzException(APIErrorCode.ERR_OPERATION_FAILED);

        }

        // Return the customer object
        return customer;


    }

    @Override
    public Customer findByCusUserNoAndCusMerchantNoAndCusStatus(Long cusUserNo, Long cusMerchantNo, int cusStatus) {
        return customerRepository.findByCusUserNoAndCusStatusAndCusMerchantNo(cusUserNo,cusStatus,cusMerchantNo);
    }


    public String  writeDataToXml(InputStream inputStream,Long merchantNo,Long userLocation,String xmlType) throws InspireNetzException {

        //initialise filename
        String filename="";

        //create instance on integration util
        IntegrationUtils integrationUtils =new IntegrationUtils();

        //read input file
        try {

            // Set the read value to be 0
            int read = 0;

            // Get the integration filename
            filename = integrationUtils.getIntegrationFileName(merchantNo, userLocation,xmlType, "xml");

            // Create the FileOutputSteam object with the integration filename
            FileOutputStream fos = new FileOutputStream(filename);

            // Create the counting output stream
            CountingOutputStream out = new CountingOutputStream(fos);

            // Create the byteArray of 100 KB
            byte[] bytes = new byte[102400];

            // Read from the inputstream and set to the bytes till the
            // there is no data to read
            while ((read = inputStream.read(bytes)) != -1) {

                // Write the bytes to the output stream
                out.write(bytes, 0, read);

            }

            // Flush the output stream
            out.flush();

            // Close the outputstream
            out.close();


        } catch (Exception e) {

            // TODO throw!
            e.printStackTrace();

            // Log the file
            log.info("readCustomerXML - Error reading the stream");

            // throw
            throw new InspireNetzException(APIErrorCode.ERR_STREAM_ERROR);

        }

        //return file name
        return filename;
    }

    /**
     * Function to check if a customer is valid for the transaction awarding
     *
     * @param loyaltyId - The loyalty id of the customer
     * @param merchantNo - The merchant number of the customer
     *
     * @return  - true if the customer is valid and active
     *            false if the customer does not exist or is not active.
     */
    @Override
    public boolean isCustomerValidForTransaction( String loyaltyId , Long merchantNo ) {

        // Get the customer object
        Customer customer = findByCusLoyaltyIdAndCusMerchantNo(loyaltyId, merchantNo);

        // if the customer does not exist, then return false
        if ( customer == null ) {

            // Return false
            return false;

        }

        // If the customer object is existing, then we need to check if the status
        // is active
        if ( customer.getCusStatus() != CustomerStatus.ACTIVE ) {

            // Return false
            return false;

        }

        // Finally return true
        return true;

    }

    @Override
    public void portalActivationForCustomer(Long merchantNo) throws InspireNetzException{

        //access  validity
        authSessionUtils.validateFunctionAccess(FunctionCode.FNC_UPDATE_CUSTOMER);

        //Variable to hold customers
        List<Customer> customers=new ArrayList<>();


        //check if merchant number provided or if provided merchant no is default merchantno
        if(merchantNo.longValue()==0L ){

            customers=findAll();

        }else{

            customers=findByCusMerchantNo(merchantNo);
        }

        //iterate through customers
        for(Customer customer:customers){


            //confirm customer mobile number exist or not
            if(customer.getCusMobile()==null||customer.getCusMobile().equals("")){

                log.info("Customer Service Impl::portalActivationForCustomer(): customer mobile no empty");

                continue;
            }

            //check user is exist and register status is already active then continue
            if((customer .getCusUserNo() !=null ||customer.getCusUserNo().longValue() ==0L) && customer.getCusRegisterStatus().intValue() ==IndicatorStatus.YES){


                log.info("Customer Service Impl::portalActivationForCustomer(): customer alredy a user");

                continue;
            }

            //connect customer
            connectMerchant(customer.getCusMerchantNo(),customer,CustomerRegisterType.MERCHANT_PORTAL);

        }

    }

    @Override
    public Customer findByCusLoyaltyIdLikeAndCusMerchantNoAndCusRegisterStatus(String cusLoyaltyId, Long cusMerchantNo, Integer cusRegisterStatus) {
        return customerRepository.findByCusLoyaltyIdLikeAndCusMerchantNoAndCusRegisterStatus(cusLoyaltyId,cusMerchantNo, cusRegisterStatus);
    }

    @Override
    public boolean unRegisterLoyaltyCustomer(String loyaltyId,Long merchantNo,Integer status,String mobileNo, Integer userRequest) throws InspireNetzException {

        //identify request is  registration or un registration if customer status inactive then process unregister option
        if(status.intValue() ==CustomerStatus.INACTIVE){

            //check the request is coming from customer optout customer from the loyalty and also remove balance
            Customer customer =findByCusLoyaltyIdAndCusMerchantNo(loyaltyId,merchantNo);

            if(customer ==null || customer.getCusRegisterStatus() ==IndicatorStatus.NO){

                //customer is already in status
                throw new InspireNetzException(APIErrorCode.ERR_NO_LOYALTY_ID);

            }

            //unlink all account
            linkRequestService.unlinkCustomerAccounts(customer);

            //remove customer from search user
            customer.setCusRegisterStatus(IndicatorStatus.NO);

            //set customer is deactive
            customer.setCusStatus(CustomerStatus.INACTIVE);

            //expire the reward balance
            customerRewardExpiryService.expireBalanceForCustomer(customer);

            //expire draw chances for customer
            expiringDrawChances(customer);

            //calling fir removing the reward balance related entries
            removeBalanceEntries(customer);

            //log the activity
            customerActivityService.logActivity(customer.getCusLoyaltyId(),CustomerActivityType.UNREGISTER,"Customer Unregistered",customer.getCusMerchantNo(),"");

            //archive all customer activities and transactions
            archiveCustomerData(customer);

            //save customer
            customer =saveCustomer(customer);

            //unregister customer from merchant
            //create a hashmap for sms parameters
            HashMap<String , String > smsParams = new HashMap<>(0);

            //get merchant name
            Merchant merchant =merchantService.findByMerMerchantNo(customer.getCusMerchantNo() ==null?0L:customer.getCusMerchantNo());

            if(merchant !=null){

                //put merchant name
                smsParams.put("#merchant",merchant.getMerMerchantName() ==null?"":merchant.getMerMerchantName());

            }

            if(customer.getCusCustomerNo() !=null){

                MessageWrapper messageWrapper = generalUtils.getMessageWrapperObject(MessageSpielValue.LOYALTY_ACCOUNT_DEACTIVATION_SMS,customer.getCusLoyaltyId(),customer.getCusMobile(),customer.getCusEmail(),"",customer.getCusMerchantNo(),smsParams,MessageSpielChannel.ALL,IndicatorStatus.YES);

                // Send the sms stating that the customer has been deactivated successfully
                userMessagingService.transmitNotification(messageWrapper);
            }


        }

        return true;
    }

    @Override
    public Customer connectMerchant(Long merchantNo,Customer customer,Integer cusRegisterType) throws InspireNetzException {

        //login details
        boolean isSendLoginDetails =false;

        //send user connect message
        boolean isConnectMessage =false;

        //new registration
        boolean isNewUser =false;

        //customer
        Customer customer1 =null;

        //find merchant information
        Merchant merchant =merchantService.findByMerMerchantNo(merchantNo);

        if(merchant ==null){

            throw new InspireNetzException(APIErrorCode.ERR_INVALID_MERCHANT);
        }


        //check customer exist or not
        if(cusRegisterType.intValue() !=CustomerRegisterType.MERCHANT_PORTAL){

            customer1 =findByCusLoyaltyIdAndCusMerchantNo(customer.getCusLoyaltyId(),merchantNo);
        }


        //check customer is not null and register status is 1 throw error
        if(customer !=null && customer.getCusCustomerNo() !=null && customer.getCusRegisterStatus().intValue() ==IndicatorStatus.YES){

            //throw error its already connected to this merchant
            throw new InspireNetzException(APIErrorCode.ERR_CUSTOMER_ALREADY_ACTIVE);

        }

        //check customer is inactive stage
        if(cusRegisterType.intValue() !=CustomerRegisterType.MERCHANT_PORTAL){

            if(customer1 ==null ){

                //create customer object for new registration
                customer =createCustomerObjectForLoyaltyId(customer.getCusLoyaltyId(),customer.getCusMobile(),customer.getCusEmail(),customer.getCusFName(),merchantNo,0L,0L,customer.getReferralCode());

            }else {

                //set customer to customer
                customer =customer1;
            }

        }

        //check user is new user or not
        if(customer !=null && customer.getCusCustomerNo() ==null){

            isNewUser =true;
        }

        //check mobile number null or not if null then pass loyalty id as mobile number
        if(customer !=null && (customer.getCusMobile() ==null || customer.getCusMobile().equals(""))){

            customer.setCusMobile(customer.getCusMobile().equals("")?customer.getCusLoyaltyId():customer.getCusMobile());
        }

        //check customer is user or not
        User user = userService.findByUsrLoginId(customer.getCusMobile() + "");

        //check user null or not
        if(user !=null){

            if(user.getUsrMobile().length()>0)
            {
                //create customer object
                customer.setCusMobile(user.getUsrMobile());
            }

            //set user fname into customer
            customer.setCusFName(customer.getCusFName());

            //set user number
            customer.setCusUserNo(user.getUsrUserNo());

            //set customer register status is 1
            customer.setCusRegisterStatus(IndicatorStatus.YES);

            //set customer as active
            customer.setCusStatus(CustomerStatus.ACTIVE);

            // check request from merchant portal and user is deactive then activate user and resend user name and password
            if(user.getUsrRegisterStatus().intValue() ==IndicatorStatus.NO &&cusRegisterType.intValue() ==CustomerRegisterType.MERCHANT_PORTAL){

                //activate user
                user.setUsrStatus(UserStatus.ACTIVE);

                //user register status is set 1
                user.setUsrRegisterStatus(IndicatorStatus.YES);

                //resend the login details
                user =userService.validateAndSaveUserFromMerchant(user);

                //set login credential
                isSendLoginDetails =true;


            }else {

                //set user connect
                isConnectMessage =true;

            }

        }else {

            if(cusRegisterType.intValue() ==CustomerRegisterType.MERCHANT_PORTAL){

                //create user for user with temp password
                user =createUserObject(customer);

                //create user and send the message
                user =userService.validateAndSaveUserFromMerchant(user);

                if(user !=null){

                    //save user no to customer user no field
                    customer.setCusUserNo(user.getUsrUserNo());

                    //update register status
                    customer.setCusRegisterStatus(IndicatorStatus.YES);

                    //update set customer active
                    customer.setCusStatus(CustomerStatus.ACTIVE);

                    //true if sending
                    isSendLoginDetails=true;


                }

            }

        }

        //connect merchant call from merchant portal we need to check access rights
        if(cusRegisterType.intValue() ==CustomerRegisterType.MERCHANT_PORTAL){

            //call validate and save method
            customer =validateAndSaveCustomer(customer);

        }else {

            //just save the customer
            customer =saveCustomer(customer);

        }


        //after registering we need to send message
        if(isSendLoginDetails){

            //send login credential
            sendUserLoginCredential(user,customer,merchant);
        }

        if(isConnectMessage){

            //send connect message
            saveNotificationMessage(customer);

            sendMessageAndEmail(customer,merchant);
        }

        //evaluate tier
        evaulateCustomerTier(customer);

        //log the activity
        customerActivityService.logActivity(customer.getCusLoyaltyId(),CustomerActivityType.REGISTER,"Customer Register Successfully",customer.getCusMerchantNo(),"");

        //after evaluation process signup bonus if its available
        if(isNewUser){

            //check whether referral code specified or not
            if(customer.getReferralCode()!=null && !customer.getReferralCode().equals("")){

                //check an user exist with this referral code
                User referredUser=userService.findByUsrUserCode(customer.getReferralCode());

                if(referredUser==null){

                    //Log information
                    log.info("connectMerchant : invalid referral code,there is no user with this referral code");

                    throw new InspireNetzException(APIErrorCode.ERR_INVALID_REFERRAL_CODE);
                }

                Customer referredCustomer=findByCusUserNoAndCusMerchantNo(referredUser.getUsrUserNo(),customer.getCusMerchantNo());

                if(referredCustomer==null){

                    //Log information
                    log.info("connectMerchant : invalid referral code,there is no customer with this referral code");

                    throw new InspireNetzException(APIErrorCode.ERR_INVALID_REFERRAL_CODE);
                }


                CustomerReferral customerReferral=createCustomerReferralObject(customer,referredCustomer);

                customerReferralService.saveCustomerReferralWithPriority(customerReferral);

            }

            //referral bonus
            processReferralAwarding(customer);

            //process signup bonus
            processSignUpBonus(customer);

        }


        return customer;
    }

    public CustomerReferral createCustomerReferralObject(Customer referee,Customer referrer){

        CustomerReferral customerReferral=new CustomerReferral();

        String referrerName="";

        if(!(referrer.getCusFName()==null)){

            referrerName+=referrer.getCusFName();
        }

        if(!(referrer.getCusLName()==null)){

            referrerName+=" "+referrer.getCusLName();
        }

        customerReferral.setCsrFName(referrerName);
        customerReferral.setCsrLoyaltyId(referrer.getCusLoyaltyId());
        customerReferral.setCsrMerchantNo(referrer.getCusMerchantNo());
        customerReferral.setCsrLocation(referrer.getCusLocation());
        customerReferral.setCsrRefEmail(referee.getCusEmail());
        customerReferral.setCsrRefMobile(referee.getCusMobile());
        customerReferral.setCsrRefMobileCountryCode(referee.getCusMobileCountryCode());

        String refereeName="";

        if(!(referee.getCusFName()==null)){

            refereeName+=referee.getCusFName();
        }

        if(!(referee.getCusLName()==null)){

            refereeName+=" "+referee.getCusLName();
        }

        customerReferral.setCsrRefName(refereeName);
        CustomerProfile customerProfile=referee.getCustomerProfile();
        if(customerProfile!=null){

            customerReferral.setCsrRefAddress(customerProfile.getCspAddress());

        }

        return customerReferral;
    }

    @Override
    public Customer saveCustomerDetailsGeneric( CustomerResource customerResource, CustomerProfileResource customerProfileResource) throws InspireNetzException {


        // Get the merchant number for the session
        Long merchantNo = customerResource.getCusMerchantNo();

        //set flag for identifying customer is connecting merchant
        boolean isConnectMerchant=false;

        boolean isReRegister =false;

        User user=userService.findByUsrUserNo(customerResource.getCusMerchantUserRegistered());

        Long usrUserNo=0L;

        String usrLoginId="";

        if(user!=null){

            usrUserNo=user.getUsrUserNo()==null?0:user.getUsrUserNo();

            usrLoginId=user.getUsrLoginId()==null?"":user.getUsrLoginId();
        }

        // Object storing customer
        Customer customer;

        // First check if the request is update
        if ( customerResource.getCusCustomerNo() != null ) {

            // Get the customer
            customer = findByCusCustomerNo(customerResource.getCusCustomerNo());

            // If the customer is null throw exception
            if ( customer == null ) {

                // Log the information
                log.info("saveCustomerDetails -> Customer is not found");

                // throw exception
                throw new InspireNetzException(APIErrorCode.ERR_NO_DATA_FOUND);

            }

            // Map the customer
            mapper.map(customerResource,customer);


        } else {

            // Map the object check unregistered customer
            customer =findByCusLoyaltyIdAndCusMerchantNo(customerResource.getCusLoyaltyId(),merchantNo);

            if(customer !=null && customer.getCusRegisterStatus().intValue() ==IndicatorStatus.NO){

                //set customer number
                customerResource.setCusCustomerNo(customer.getCusCustomerNo());

                //set merchant user registered
                customerResource.setCusMerchantUserRegistered(customerResource.getCusMerchantUserRegistered());

                //isReRegister
                isReRegister =true;

                //map resource
                mapper.map(customerResource, customer);

            }else{
                //normal mapping
                //map resource
                customer = mapper.map(customerResource,Customer.class);

            }



        }

        // Set the merchantNumber for the customer
        customer.setCusMerchantNo(merchantNo);

        // Map the customerProfile
        CustomerProfile customerProfile =  mapper.map(customerProfileResource,CustomerProfile.class);

        // Create the Validator for customer
        CustomerValidator cusValidator = new CustomerValidator(dataValidationUtils);

        // Create the BeanPropertyBindingResult
        BeanPropertyBindingResult cusResult = new BeanPropertyBindingResult(customer,"customer");

        // Validate customer
        cusValidator.validate(customer,cusResult);


        // Create the Validator for CustomerProfile
        CustomerProfileValidator   customerProfileValidator = new CustomerProfileValidator();

        // Validate the customerProfile
        customerProfileValidator.validate(customerProfile,cusResult);


        // Check if the result contains errors
        if ( cusResult.hasErrors() ) {

            // Get the validation message
            String messages = dataValidationUtils.getValidationMessages(cusResult);

            // Log the response
            log.info("saveCustomerDetails - Response : Invalid Input - "+ messages);

            // Throw InspireNetzException with INVALID_INPUT as error
            throw new InspireNetzException(APIErrorCode.ERR_INVALID_INPUT,messages);

        }

        // Check if the customer is already existing
        boolean isExist = isDuplicateCustomerExisting(customer);

        // Check the boolean value
        if ( isExist ) {

            // Log the response
            log.info("saveCustomerDetails - Response : Customer is already existing");

            // Throw InspireNetzException with ERR_DUPLICATE_ENTRY as error
            throw new InspireNetzException(APIErrorCode.ERR_DUPLICATE_ENTRY);

        }

        //check duplicate mobile existing or not
        boolean isDuplicateMobile =isDuplicateMobileExisting(customer);

        //check mobile is exist or not
        if(isDuplicateMobile){

            // Log the response
            log.info("saveCustomerDetails - Response : Duplicate Mobile number"+customer.toString());

            // Throw InspireNetzException with ERR_DUPLICATE_ENTRY as error
            throw new InspireNetzException(APIErrorCode.ERR_DUPLICATE_MOBILE);

        }

        //check duplicate mobile existing or not
        boolean isDuplicateEmail =isDuplicateEmailExisting(customer);

        //check mobile is exist or not
        if(isDuplicateEmail){

            // Log the response
            log.info("saveCustomerDetails - Response : Duplicate Email Id"+customer.toString());

            // Throw InspireNetzException with ERR_DUPLICATE_ENTRY as error
            throw new InspireNetzException(APIErrorCode.ERR_DUPLICATE_EMAIL);

        }

        // If the customerLocation field is not set, then we need to set the
        // the location to the location of the merchant user registering the customer
        if ( customer.getCusLocation() == null || customer.getCusLocation() == 0L ) {

            if(customerResource.getCusLocation()==null){

                customer.setCusLocation(0L);

            }else{

                customer.setCusLocation(customerResource.getCusLocation());
            }

        }



        // Hold the audit details
        String auditDetails = usrUserNo.toString() + "#" + usrLoginId;

        // If the customer getCusCustomerNo is  null, then set the created_by, else set the updated_by
        if ( customer.getCusCustomerNo() == null ) {

            // Store the merchantUserRegistered field for the Customer
            customer.setCusMerchantUserRegistered(customerResource.getCusMerchantUserRegistered());

            // Set the createdBy to the auditDetails
            customer.setCreatedBy(auditDetails);


        } else {



            customer.setUpdatedBy(auditDetails);

            // Get the customerProfile object
            CustomerProfile baseProfile = customerProfileService.findByCspCustomerNo(customer.getCusCustomerNo());

            // If the baseProfile is not null, then we need to set the
            // cspId of the customerProfiile
            if ( baseProfile != null ) {

                customerProfile.setCspId(baseProfile.getCspId());


                //set last birthday and anniversary awarded date  for update mode
                if(customerProfile.getCspId() !=null){

                    //fetch base profile and set birthday and anniversary awarded date
                    customerProfile.setCspBirthDayLastAwarded(baseProfile.getCspBirthDayLastAwarded());

                    customerProfile.setCspAnniversaryLastAwarded(baseProfile.getCspAnniversaryLastAwarded());


                }

            }

        }


        //check user exist with this mobile number
        if ( customer.getCusCustomerNo() == null  || isReRegister) {

            //call to connect merchant
            customer = connectMerchant(merchantNo,customer,CustomerRegisterType.MERCHANT_PORTAL);

            //set connect flag
            isConnectMerchant =true;

        }

        // only we need to process save merchant only when customer not connect if the customer is connect already save and evaluate customer
        if(!isConnectMerchant){

            customer = validateAndSaveCustomer(customer);

            //evaluate tier
            evaulateCustomerTier(customer);

        }

        // If the customer object is not null ,then return the success object
        if ( customer.getCusCustomerNo() != null ) {

            // Set the customerNo for the customer object
            customerProfile.setCspCustomerNo(customer.getCusCustomerNo());

            // Save the customerProfile
            customerProfile = customerProfileService.saveCustomerProfile(customerProfile);

        } else {

            // Log the response
            log.info("saveCustomer - Response : Unable to save the customer information");

            // Throw InspireNetzException with ERR_OPERATION_FAILED as error
            throw new InspireNetzException(APIErrorCode.ERR_OPERATION_FAILED);

        }


        // Return the customer object
        return customer;

    }

    @Override
    public List<Customer> findByCusMerchantNo(Long cusMerchantNo) {
        return customerRepository.findByCusMerchantNo(cusMerchantNo);
    }

    @Override
    public void inject(CustomerRewardUtils beansManager) {

        this.customerRewardActivityService = beansManager.getCustomerRewardActivityService();

    }

    @Override
    public boolean updateCustomerStatus(Long cusMerchantNo, String loyaltyId, int cusStatus) throws InspireNetzException {

        Customer customer = customerRepository.findByCusLoyaltyIdAndCusMerchantNo(loyaltyId, cusMerchantNo);

        if(customer == null || customer.getCusCustomerNo() == null){

            log.info("Customer Response ---> No customer information found");

            throw new InspireNetzException(APIErrorCode.ERR_NO_DATA_FOUND);

        }
        //set the customer status
        customer.setCusStatus(cusStatus);

        //save the customer
        saveCustomer(customer);

        //return true
        return true;

    }

    @Override
    public List<MembershipResource> getCustomerMemberships(String mobile) {

        // List holding the membership information
        List<MembershipResource> membershipResourceList = new ArrayList<>(0);

        // Find the list of customers with specified mobile
        List<Customer> customerList = findByCusMobile(mobile);

        // If the list is null or empty, then return the empty resources
        if ( customerList == null || customerList.isEmpty() ) {

            // Log the  information
            log.info("Customers not found for the mobile " + mobile);

            // Return the lsit
            return membershipResourceList;

        }


        // Iterate through the list and find the balance
        for(Customer cus: customerList ) {

            // Create the membershipObject
            MembershipResource membershipResource = new MembershipResource();

            // Create the CustomerResource object
            CustomerResource customerResource = new CustomerResource();

            // Set the fields
            customerResource.setCusMerchantNo(cus.getCusMerchantNo());
            customerResource.setCusCustomerNo(cus.getCusCustomerNo());
            customerResource.setCusFName(cus.getCusFName());
            customerResource.setCusLName(cus.getCusLName());
            customerResource.setCusEmail(cus.getCusEmail());
            customerResource.setCusMobile(cus.getCusMobile());
            customerResource.setCusLoyaltyId(cus.getCusLoyaltyId());


            // Set the customerResrouce in the membershipResource
            membershipResource.setCustomer(customerResource);

            // Get the reward balance list
            List<CustomerRewardBalance> customerRewardBalanceList = customerRewardBalanceService.findByCrbLoyaltyIdAndCrbMerchantNo(cus.getCusLoyaltyId(),cus.getCusMerchantNo());

            // IF the list is not emtpy, then iterate and set the fields
            if ( customerRewardBalanceList == null ||
                    customerRewardBalanceList.isEmpty() ) {

                // Add to the list
                membershipResourceList.add(membershipResource);

                // return
                continue;

            }

            // Create the list holding the resource
            List<CustomerRewardBalanceResource> customerRewardBalanceResourceList = new ArrayList<CustomerRewardBalanceResource>(0);

            // Iterate over the  customerRewardBalance object and convert to resource
            for( CustomerRewardBalance crb: customerRewardBalanceList) {

                // Create the resource
                CustomerRewardBalanceResource customerRewardBalanceResource = new CustomerRewardBalanceResource();

                // Set the fields
                customerRewardBalanceResource.setCrbMerchantNo(crb.getCrbMerchantNo());
                customerRewardBalanceResource.setCrbRewardCurrency(crb.getCrbRewardCurrency());
                customerRewardBalanceResource.setCrbRewardBalance(crb.getCrbRewardBalance());

                // Get the reward currency object
                RewardCurrency rewardCurrency = crb.getRewardCurrency();

                // Set the reward currency fields
                customerRewardBalanceResource.setRwdCurrencyName(rewardCurrency.getRwdCurrencyName());
                customerRewardBalanceResource.setRwdCashbackValue(rewardCurrencyService.getCashbackValue(rewardCurrency,crb.getCrbRewardBalance()));
                customerRewardBalanceResource.setRwdRatioDeno(rewardCurrency.getRwdCashbackRatioDeno());

                // add to the list
                customerRewardBalanceResourceList.add(customerRewardBalanceResource);

            }

            // Add the list to the membershipResrouce
            membershipResource.setCustomerRewardBalanceResourceList(customerRewardBalanceResourceList);

            // Add to the list
            membershipResourceList.add(membershipResource);

        }

        // Return the list
        return membershipResourceList;

    }

}

