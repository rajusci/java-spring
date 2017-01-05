package com.inspirenetz.api.core.service.impl;

import com.google.common.collect.Lists;
import com.inspirenetz.api.core.dictionary.APIErrorCode;
import com.inspirenetz.api.core.dictionary.CustomerActivityType;
import com.inspirenetz.api.core.dictionary.CustomerStatus;
import com.inspirenetz.api.core.dictionary.RecordStatus;
import com.inspirenetz.api.core.domain.Customer;
import com.inspirenetz.api.core.domain.CustomerActivity;
import com.inspirenetz.api.core.domain.User;
import com.inspirenetz.api.core.domain.validator.CustomerActivityValidator;
import com.inspirenetz.api.core.repository.BaseRepository;
import com.inspirenetz.api.core.repository.CustomerActivityRepository;
import com.inspirenetz.api.core.service.CustomerService;
import com.inspirenetz.api.core.service.CustomerActivityService;
import com.inspirenetz.api.core.service.UserService;
import com.inspirenetz.api.rest.exception.InspireNetzException;
import com.inspirenetz.api.util.AuthSessionUtils;
import com.inspirenetz.api.util.GeneralUtils;
import org.apache.commons.beanutils.BeanComparator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;
import org.springframework.validation.BeanPropertyBindingResult;

import java.sql.Date;
import java.util.*;

/**
 * Created by saneeshci on 27/9/14.
 */
@Service
public class CustomerActivityServiceImpl extends BaseServiceImpl<CustomerActivity> implements CustomerActivityService {


    private static Logger log = LoggerFactory.getLogger(CustomerActivityServiceImpl.class);


    @Autowired
    CustomerActivityRepository customerActivityRepository;

    @Autowired
    CustomerService customerService;

    @Autowired
    UserService userService;

    @Autowired
    GeneralUtils generalUtils;

    @Autowired
    private AuthSessionUtils authSessionUtils;


    public CustomerActivityServiceImpl() {

        super(CustomerActivity.class);

    }


    @Override
    protected BaseRepository<CustomerActivity,Long> getDao() {
        return customerActivityRepository;
    }



    @Override
    public CustomerActivity findByCuaId(Long cuaId) throws InspireNetzException {

        // Get the customerActivity for the given customerActivity id from the repository
        CustomerActivity customerActivity = customerActivityRepository.findByCuaId(cuaId);

        // Return the customerActivity
        return customerActivity;

    }

    @Override
    public Page<CustomerActivity> searchCustomerActivities(String cuaLoyaltyId,Integer cuaActivityType,Date fromDate, Date toDate,Long cuaMerchantNo,Pageable pageable) {

        //for get page number and page size from  pageable request and add sorting parameter for displaying last created one into top
        int page = pageable.getPageNumber();

        int pageSize =pageable.getPageSize();

        //create request with sorting parameter
        Pageable newPageableRequest = new PageRequest(page,pageSize, new Sort(Sort.Direction.DESC,"cuaId"));

        //get the draw chances list
        Page<CustomerActivity> customerActivitys = null;

        if(cuaMerchantNo!=0){

            if(cuaLoyaltyId.equals("0") && cuaActivityType == 0 ){

                customerActivitys = customerActivityRepository.findByCuaMerchantNoAndCuaRecordStatusAndCuaDateBetween(cuaMerchantNo,RecordStatus.RECORD_STATUS_ACTIVE,fromDate,toDate,newPageableRequest);

            } else if(!cuaLoyaltyId.equals("0") && cuaActivityType ==0){

                customerActivitys = customerActivityRepository.findByCuaMerchantNoAndCuaLoyaltyIdAndCuaRecordStatusAndCuaDateBetween(cuaMerchantNo,cuaLoyaltyId, RecordStatus.RECORD_STATUS_ACTIVE, fromDate, toDate, newPageableRequest);

            } else if(!cuaLoyaltyId.equals("0") && cuaActivityType !=0){

                customerActivitys = customerActivityRepository.findByCuaMerchantNoAndCuaLoyaltyIdAndCuaActivityTypeAndCuaRecordStatusAndCuaDateBetween(cuaMerchantNo, cuaLoyaltyId, cuaActivityType, RecordStatus.RECORD_STATUS_ACTIVE, fromDate, toDate, newPageableRequest);

            }  else if(cuaLoyaltyId.equals("0") && cuaActivityType !=0){

                customerActivitys = customerActivityRepository.findByCuaMerchantNoAndCuaActivityTypeAndCuaRecordStatusAndCuaDateBetween(cuaMerchantNo, cuaActivityType, RecordStatus.RECORD_STATUS_ACTIVE, fromDate, toDate, newPageableRequest);
            }

        }else {

            if(cuaLoyaltyId.equals("0") && cuaActivityType == 0 ){

                customerActivitys = customerActivityRepository.findByCuaRecordStatusAndCuaDateBetween(RecordStatus.RECORD_STATUS_ACTIVE, fromDate, toDate, newPageableRequest);

            } else if(!cuaLoyaltyId.equals("0") && cuaActivityType ==0){

                customerActivitys = customerActivityRepository.findByCuaLoyaltyIdAndCuaRecordStatusAndCuaDateBetween(cuaLoyaltyId, RecordStatus.RECORD_STATUS_ACTIVE, fromDate, toDate, newPageableRequest);

            } else if(!cuaLoyaltyId.equals("0") && cuaActivityType !=0){

                customerActivitys = customerActivityRepository.findByCuaLoyaltyIdAndCuaActivityTypeAndCuaRecordStatusAndCuaDateBetween(cuaLoyaltyId, cuaActivityType, RecordStatus.RECORD_STATUS_ACTIVE, fromDate, toDate, newPageableRequest);

            }  else if(cuaLoyaltyId.equals("0") && cuaActivityType !=0){

                customerActivitys = customerActivityRepository.findByCuaActivityTypeAndCuaRecordStatusAndCuaDateBetween(cuaActivityType, RecordStatus.RECORD_STATUS_ACTIVE, fromDate, toDate, newPageableRequest);
            }

        }

        //return the list
        return  customerActivitys;

    }


    @Override
    public CustomerActivity logActivity(String loyaltyId,Integer activityType,String remarks,Long merchantNo,String params) throws InspireNetzException {

        String auditDetails = "";

       /* if(activityType.intValue() != CustomerActivityType.REGISTER){

            //get the logged in user details
            auditDetails = authSessionUtils.getUserNo() + "";

        } else {

            //set loyalty id if activity type is customer registration
            auditDetails = loyaltyId ;
        }*/

        //create customer activity object
        CustomerActivity customerActivity = new CustomerActivity();

        //set values to the object
        customerActivity.setCuaLoyaltyId(loyaltyId);
        customerActivity.setCuaMerchantNo(merchantNo);
        customerActivity.setCuaActivityType(activityType);
        customerActivity.setCuaParams(params);
        customerActivity.setCuaRemarks(remarks);
        customerActivity.setCreatedBy(auditDetails);

        // Create the customer activity validator object
        CustomerActivityValidator validator = new CustomerActivityValidator();

        // Create the bindingResult
        BeanPropertyBindingResult result = new BeanPropertyBindingResult(customerActivity,"customerActivity");

        // Validate the request
        validator.validate(customerActivity,result);

        // Check if the result contains error
        if ( result.hasErrors() ) {

            // Log the response
            log.info("validateAndSaveCustomerActivity - Response : Invalid Input - ");

            // Throw InspireNetzException with INVALID_INPUT as error
            throw new InspireNetzException(APIErrorCode.ERR_INVALID_INPUT,result);

        }

        customerActivity = saveCustomerActivity(customerActivity);

        // Check if the customerActivity is saved
        if ( customerActivity.getCuaId() == null ) {

            // Log the response
            log.info("validateAndSaveCustomerActivity - Response : Unable to save the customerActivity information");

            // Throw InspireNetzException with ERR_OPERATION_FAILED as error
            throw new InspireNetzException(APIErrorCode.ERR_OPERATION_FAILED);

        }

        // return the object
        return customerActivity;


    }

    @Override
    public List<Map<String, String >> getCustomerRecentActivities(String usrLoginId, Long merchantNo) {

        //for get page number and page size from  pageable request and add sorting parameter for displaying last created one into top
        int page = 0;

        int pageSize = 10;

        // List holding the promotions
        List<CustomerActivity> customerActivities = new ArrayList<>();

        //Get user object
        User user=userService.findByUsrLoginId(usrLoginId);



        if(user==null||user.getUsrUserNo()==null){

            //log the info
            log.info("No User Information Found");


            return null;
        }

        //get member customers,if catMerchantNo is zero or default merchant no return all members
        List<Customer> customers=customerService.getUserMemberships(merchantNo,user.getUsrUserNo(), CustomerStatus.ACTIVE);

        if(customers==null ||customers.isEmpty()){

            //log the info
            log.info("No Customer Information Found");

            return null;

        }

        //create request with sorting parameter
        Pageable newPageableRequest = new PageRequest(page,pageSize, new Sort(Sort.Direction.DESC,"cuaId"));


        // Go through of the customer list and get the promotions for the customer
        for(Customer customer : customers ) {

            //get the draw chances list
            Page<CustomerActivity> customerActivitys = searchCustomerActivities(customer.getCusLoyaltyId(), 0, Date.valueOf("0000-12-31"), Date.valueOf("9999-12-31"), customer.getCusMerchantNo(), newPageableRequest);


            //  If the list if not empty, then we need to add it to the userPromotionList
            if ( customerActivitys == null ||  !customerActivitys.hasContent() ) {

                // Continue the iteration
                continue;

            }


            // Add the list
            customerActivities.addAll(Lists.newArrayList((Iterable<CustomerActivity>) customerActivitys));

        }

        BeanComparator fieldComparator = new BeanComparator("cuaId");

        // Sort the List
        Collections.sort(customerActivities, fieldComparator);


        //to sort a list
        Collections.sort(customerActivities, new Comparator<CustomerActivity>() {
            @Override
            public int compare(final CustomerActivity object1, final CustomerActivity object2) {
                return object2.getCuaId().compareTo(object1.getCuaId());
            }
        });



        List<Map<String , String >>  activities = new ArrayList<>();

        Map<String , String > activity = new HashMap<>();

        for(CustomerActivity customerActivity : customerActivities){

            activity = new HashMap<>();

            activity.put("act_date",customerActivity.getCuaDate()+"");

            activity.put("act_time",customerActivity.getCuaTime()+"");

            String activityType = getCustomerActivityType(customerActivity.getCuaActivityType());

            activity.put("act_type",activityType);

            activity.put("act_value",customerActivity.getCuaRemarks());

            activities.add(activity);

        }

        return activities;
    }

    private String getCustomerActivityType(Integer cuaActivityType) {

        switch (cuaActivityType){

            case CustomerActivityType.REGISTER: return "Register";

            case CustomerActivityType.ACCOUNT_LINKING : return "Account Linking";

            case CustomerActivityType.BUY_POINT : return "Buy Point";

            case CustomerActivityType.EARNING : return "Awarding";

            case CustomerActivityType.EVENT_REGISTER : return "Event Registration";

            case CustomerActivityType.POINT_DEDUCTION : return "Point Deduction";

            case CustomerActivityType.POINT_ENQUIRY : return "Point Inquiry";

            case CustomerActivityType.REDEMPTION : return "Redemption";

            case CustomerActivityType.REDEMPTION_ENQUIRY : return "Redemption Inquiry";

            case CustomerActivityType.TRANSFER_POINT: return "Transfer Point";

            case CustomerActivityType.UNREGISTER : return "Unregister";

            case CustomerActivityType.TRANSFER_ACCOUNT : return "Account Transfer";
        }

        return "";
    }

    @Override
    public CustomerActivity saveCustomerActivity(CustomerActivity customerActivity ){

        // Save the customerActivity
        return customerActivityRepository.save(customerActivity);

    }


    @Override
    public boolean deleteCustomerActivity(Long cuaId) {

        // Delete the customerActivity
        customerActivityRepository.delete(cuaId);

        // return true
        return true;

    }

    @Override
    public List<CustomerActivity > findByCuaMerchantNoAndCuaLoyaltyId(Long merchantNo,String loyaltyId){

        //get all the active activities of the customer
        List<CustomerActivity> customerActivities = customerActivityRepository.findByCuaMerchantNoAndCuaLoyaltyIdAndCuaRecordStatus(merchantNo,loyaltyId,RecordStatus.RECORD_STATUS_ACTIVE);

        //return the list
        return customerActivities;

    }

    @Override
    public Page<CustomerActivity> searchUserActivity(Long merchantNo, Integer cuaActivityType, Date fromDate, Date toDate, Long cuaMerchantNo,Pageable pageable) throws InspireNetzException {


        List<Customer> customerList =new ArrayList<>();

        List<CustomerActivity> customerActivities = new ArrayList<>();

        //get user number
        Long userNo =authSessionUtils.getUserNo();

        //for get page number and page size from  pageable request and add sorting parameter for displaying last created one into top
        int page = pageable.getPageNumber();

        int pageSize =pageable.getPageSize();

        //create request with sorting parameter
        Pageable newPageableRequest = new PageRequest(page,pageSize, new Sort(Sort.Direction.DESC,"cuaId"));

        //get the draw chances list
        Page<CustomerActivity> customerActivitys = null;


        //check the merchant number exist or not if the merchant number exist that is exclusive otherwise its normal
        if (merchantNo.longValue() !=0L){

            Customer customer = customerService.findByCusUserNoAndCusMerchantNoAndCusStatus(userNo,merchantNo,CustomerStatus.ACTIVE);

            customerList.add(customer);

        }else {

            //Customer list
            customerList =customerService.findByCusUserNoAndCusStatus(userNo, CustomerStatus.ACTIVE);

        }

        if(customerList ==null ||customerList.size() ==0){

            log.info("Customer Referral service impl:searchReferralForCustomerPortal::No membership");

            throw new InspireNetzException(APIErrorCode.ERR_NOT_CONNECTED);

        }

        for(Customer customer:customerList){

            //get the draw chances list
            Page<CustomerActivity> customerActivity1 = searchCustomerActivities(customer.getCusLoyaltyId(),cuaActivityType, fromDate, toDate,customer.getCusMerchantNo(), newPageableRequest);

            //If the list if not empty, then we need to add it to the userPromotionList
            if ( customerActivity1 == null ||  !customerActivity1.hasContent() ) {

                    // Continue the iteration
                    continue;

            }

            // Add the list
            customerActivities.addAll(Lists.newArrayList((Iterable<CustomerActivity>) customerActivity1));

        }

        BeanComparator fieldComparator = new BeanComparator("cuaId");

        // Sort the List
        Collections.sort(customerActivities, fieldComparator);


        //to sort a list
        Collections.sort(customerActivities, new Comparator<CustomerActivity>() {
            @Override
            public int compare(final CustomerActivity object1, final CustomerActivity object2) {
                return object2.getCuaId().compareTo(object1.getCuaId());
            }
        });

        customerActivitys =new PageImpl<>(customerActivities);


        return customerActivitys;
    }

}

