package com.inspirenetz.api.core.service.impl;

import com.inspirenetz.api.core.auth.AuthUser;
import com.inspirenetz.api.core.dictionary.*;
import com.inspirenetz.api.core.domain.*;
import com.inspirenetz.api.core.domain.Customer;
import com.inspirenetz.api.core.domain.RedemptionMerchant;
import com.inspirenetz.api.core.domain.RedemptionVoucher;
import com.inspirenetz.api.core.domain.User;
import com.inspirenetz.api.core.domain.validator.RedemptionVoucherValidator;
import com.inspirenetz.api.core.repository.BaseRepository;
import com.inspirenetz.api.core.repository.RedemptionVoucherRepository;
import com.inspirenetz.api.core.service.*;
import com.inspirenetz.api.rest.exception.InspireNetzException;
import com.inspirenetz.api.util.AuthSessionUtils;
import com.inspirenetz.api.util.DBUtils;
import com.inspirenetz.api.util.GeneralUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.validation.BeanPropertyBindingResult;

import java.sql.Date;
import java.util.*;

/**
 * Created by saneeshci on 25/9/14.
 */
@Service
public class RedemptionVoucherServiceImpl extends BaseServiceImpl<RedemptionVoucher> implements RedemptionVoucherService {


    private static Logger log = LoggerFactory.getLogger(RedemptionVoucherServiceImpl.class);


    @Autowired
    RedemptionVoucherRepository redemptionVoucherRepository;

    @Autowired
    CustomerService customerService;

    @Autowired
    GeneralUtils generalUtils;

    @Autowired
    CatalogueService catalogueService;

    @Autowired
    private AuthSessionUtils authSessionUtils;

    @Autowired
    private RedemptionMerchantLocationService redemptionMerchantLocationService;



    @Autowired
    private UserMessagingService userMessagingService;

    @Autowired
    private UserService userService;

    @Autowired
    MerchantSettingService merchantSettingService;

    @Autowired
    RedemptionMerchantService redemptionMerchantService;





    public RedemptionVoucherServiceImpl() {

        super(RedemptionVoucher.class);

    }


    @Override
    protected BaseRepository<RedemptionVoucher,Long> getDao() {
        return redemptionVoucherRepository;
    }

    @Override
    public RedemptionVoucher findByRvrId(Long rvrId) throws InspireNetzException {

        // Get the redemptionVoucher for the given redemptionVoucher id from the repository
        RedemptionVoucher redemptionVoucher = redemptionVoucherRepository.findByRvrId(rvrId);

        // Return the redemptionVoucher
        return redemptionVoucher;

    }


    @Override
    public Page<RedemptionVoucher> getPendingRedemptionVouchers(String loyaltyId,Integer channel,Pageable pageable) throws InspireNetzException {

        //get the customer details
        Customer customer = customerService.findByCusLoyaltyIdAndCusMerchantNo(loyaltyId,authSessionUtils.getMerchantNo());;

        // If the customer does not exist, then send the message
        if ( customer == null ) {

            // Log the information
            log.info("getPendingRedemptionVoucherFoLoyaltyId -> No information for the customer");

            //if request channel is SMS then send sms notification to customer
            if(channel == RequestChannel.RDM_CHANNEL_SMS){

                // Set the user mesage
                MessageWrapper messageWrapper=generalUtils.getMessageWrapperObject(MessageSpielValue.REDEMPTION_VOUCHER_INCORRECT_MIN,"",loyaltyId,"","",0L,new HashMap<String, String>(0),MessageSpielChannel.SMS,IndicatorStatus.NO);

                userMessagingService.transmitNotification(messageWrapper);

                //userMessagingService.sendSMS(MessageSpielValue.REDEMPTION_VOUCHER_INCORRECT_MIN,loyaltyId,new HashMap<String,String>(0));

            }

            // Throw exception
            throw new InspireNetzException(APIErrorCode.ERR_NO_LOYALTY_ID);
        }

        //calling the repository method to get all pending vouchers( having voucher status as new)
        Page<RedemptionVoucher> redemptionVoucherPage = redemptionVoucherRepository.findByRvrMerchantNoAndRvrCustomerNoAndRvrStatus(customer.getCusMerchantNo(),customer.getCusCustomerNo(), RedemptionVoucherStatus.NEW, pageable);

        List<RedemptionVoucher> redemptionVoucherList = new ArrayList<>(0);;

        //get the merchant name for all vouchers
        for(RedemptionVoucher redemptionVoucher : redemptionVoucherPage){

            //get the merchant details
            RedemptionMerchant redemptionMerchant = redemptionMerchantService.findByRemNo(redemptionVoucher.getRvrMerchant());

                if(redemptionMerchant != null ){

                    //set the merchant name
                    redemptionVoucher.setRvrMerchantName(redemptionMerchant.getRemName());

                }

            //get the redemption voucher is not expired
            boolean isExpired =isVoucherExpired(redemptionVoucher);

            //if not expired add to list
            if(!isExpired){

                redemptionVoucherList.add(redemptionVoucher);
            }


        }

        //convert List to page
        redemptionVoucherPage = new PageImpl<>(redemptionVoucherList);

        //if request channel is SMS then send sms notification to customer
        if(channel == RequestChannel.RDM_CHANNEL_SMS){

            //call method for sms sending
            sendVoucherListToCustomer(redemptionVoucherPage,customer);

        }

        // Return the redemptionVoucherPage
        return redemptionVoucherPage;

    }

    private void sendVoucherListToCustomer(Page<RedemptionVoucher> redemptionVoucherPage,Customer customer) throws InspireNetzException {

        MessageWrapper messageWrapper=generalUtils.getMessageWrapperObject("",customer.getCusLoyaltyId(),"","","",customer.getCusMerchantNo(),new HashMap<String, String>(0), MessageSpielChannel.ALL,IndicatorStatus.YES);
        // If the page has got content, then we need to send the sms
        if(redemptionVoucherPage.hasContent()){

            // Create the list of transactions
            String voucherList = "";

            // Iterate through the list and then populate the transaction data
            for( RedemptionVoucher redemptionVoucher : redemptionVoucherPage ) {


                    voucherList += "PRODUCT CODE - "+redemptionVoucher.getRvrProductCode() + " :  Voucher Code - "+redemptionVoucher.getRvrVoucherCode() + "\n";

            }

            // Add to the params
            HashMap<String,String> params = new HashMap<>(0);

            // replace the txnList param
            params.put("#voucherlist",voucherList);

            String date = generalUtils.convertDateToFormat(new java.util.Date(),"dd MMM yyy");

            params.put("#date",date);

            messageWrapper.setSpielName(MessageSpielValue.REDEMPTION_VOUCHER_LIST_SMS);

            messageWrapper.setParams(params);

            userMessagingService.transmitNotification(messageWrapper);
            // Send sms
            //userMessagingService.sendSMS(MessageSpielValue.REDEMPTION_VOUCHER_LIST_SMS,loyaltyId,params);

        } else {

            messageWrapper.setSpielName(MessageSpielValue.NO_REDEMPTION_VOUCHER_SMS);

            messageWrapper.setParams(new HashMap<String, String>(0));

            userMessagingService.transmitNotification(messageWrapper);

            // Send sms
            //userMessagingService.sendSMS(MessageSpielValue.NO_REDEMPTION_VOUCHER_SMS,loyaltyId,new HashMap<String, String>(0));

        }

    }

    @Override
    public RedemptionVoucher redeemRedemptionVoucher(String cusLoyaltyId, String rvrLoginId, String rvrVoucherCode) throws InspireNetzException {

        // Get the information for the redemptionMerchant
        User rvrUser = userService.findByUsrLoginId(rvrLoginId);

        // check if the redemption merchant is registered
        if ( rvrUser == null ) {

            //send sms to the user
            MessageWrapper messageWrapper=generalUtils.getMessageWrapperObject(MessageSpielValue.REDEMPTION_VOUCHER_INVALID_MERCHANT,"",rvrLoginId,"","",0L,new HashMap<String,String>(0),MessageSpielChannel.SMS,IndicatorStatus.NO);

            userMessagingService.transmitNotification(messageWrapper);

            //userMessagingService.sendSMS(MessageSpielValue.REDEMPTION_VOUCHER_INVALID_MERCHANT,rvrLoginId,new HashMap<String,String>(0));

            // Throw exception
            throw new InspireNetzException(APIErrorCode.ERR_OPERATION_NOT_ALLOWED);

        }

        //get the third party vendor number of the user
        Long thirdPartyVendorNumber =rvrUser.getUsrThirdPartyVendorNo();

        //get redemption voucher details
        RedemptionVoucher redemptionVoucher = redemptionVoucherRepository.findByRvrMerchantAndRvrVoucherCodeAndRvrStatus(rvrUser.getUsrThirdPartyVendorNo(), rvrVoucherCode, RedemptionVoucherStatus.NEW);

        //check its valid or not
        if(redemptionVoucher ==null){

            throw new InspireNetzException(APIErrorCode.ERR_INVALID_REDEMPTION_VOUCHER);
        }

        //fi

        //message wrapper
        MessageWrapper messageWrapper =new MessageWrapper();

        //fetch customer information
        Customer customer =customerService.findByCusLoyaltyIdAndCusMerchantNo(cusLoyaltyId,redemptionVoucher.getRvrMerchantNo());

        //check customer is null or not
        if(customer ==null){

            throw new InspireNetzException(APIErrorCode.ERR_NO_LOYALTY_ID);
        }

        messageWrapper.setMerchantNo(customer.getCusMerchantNo());

        messageWrapper.setLoyaltyId(customer.getCusLoyaltyId());

        messageWrapper.setIsCustomer(IndicatorStatus.YES);

        messageWrapper.setChannel(MessageSpielChannel.ALL);

        // If the redemption voucher is not existing or is already
        // used, then we need to show the error message
        if( redemptionVoucher == null){

            // Throw exception
            throw new InspireNetzException(APIErrorCode.ERR_INVALID_REDEMPTION_VOUCHER);

        }

        //check its  already in status
        if(redemptionVoucher.getRvrStatus().intValue() ==RedemptionVoucherStatus.REDEEMED){

            //throw error voucher is already claimed
            throw new InspireNetzException(APIErrorCode.ERR_REDEMPTION_VOUCHER_ALREADY_CLAIMED);
        }


        boolean isVoucherExpired = isVoucherExpired(redemptionVoucher);

        if(isVoucherExpired){


            // Throw exception
            throw new InspireNetzException(APIErrorCode.ERR_VOUCHER_IS_EXPIRED);
        }

        // Set the status as redeemed
        redemptionVoucher.setRvrStatus(RedemptionVoucherStatus.REDEEMED);

        //updating claimed location
        Long userLocation =rvrUser.getUsrLocation()==null?0L:rvrUser.getUsrLocation();

        redemptionVoucher.setRvrClaimedLocation(userLocation);

        // SAve the item
        redemptionVoucher = saveRedemptionVoucher(redemptionVoucher);

        // if not saved successfully, then show the message
        if(redemptionVoucher == null ){

            // Log the information
            log.info("redeemRedemptionVoucher -> Redemption voucher saving failed");

            // Send the general error message
            //userMessagingService.sendSMS(MessageSpielValue.GENERAL_ERROR_MESSAGE,rvrLoginId,new HashMap<String, String>(0));

            // Throw operation failed
            throw new InspireNetzException(APIErrorCode.ERR_OPERATION_FAILED);

        }

        // Hashmap holding the parameters
        HashMap<String,String> params = new HashMap<>(0);

        // Set the voucher code
        params.put("#vouchercode",rvrVoucherCode);

        //send sms to the user
        messageWrapper.setSpielName(MessageSpielValue.REDEMPTION_VOUCHER_CLAIM_SUCCESS);
        messageWrapper.setParams(params);
        userMessagingService.transmitNotification(messageWrapper);

        // Return the redemptionVoucher
        return redemptionVoucher;
    }

    @Override
    public RedemptionVoucher validateAndSaveRedemptionVoucher(RedemptionVoucher redemptionVoucher ) throws InspireNetzException {

        // Hold the audit details
        String auditDetails = authSessionUtils.getUserNo().toString();

        // Create the Validator
        RedemptionVoucherValidator validator = new RedemptionVoucherValidator();

        // Create the bindingREsult
        BeanPropertyBindingResult result = new BeanPropertyBindingResult(redemptionVoucher,"redemptionVoucher");

        // Validate the request
        validator.validate(redemptionVoucher,result);

        // Check if the result contains error
        if ( result.hasErrors() ) {

            // Log the response
            log.info("validateAndSaveRedemptionVoucher - Response : Invalid Input - ");

            // Throw InspireNetzException with INVALID_INPUT as error
            throw new InspireNetzException(APIErrorCode.ERR_INVALID_INPUT,result);

        }

        // If the redemptionVoucher.getLrqId is  null, then set the created_by, else set the updated_by
        if ( redemptionVoucher.getRvrId() == null ) {

            redemptionVoucher.setCreatedBy(auditDetails);

        } else {

            redemptionVoucher.setUpdatedBy(auditDetails);

        }

        //set redemption merchant no
        if(authSessionUtils.getUserType() ==UserType.MERCHANT_ADMIN || authSessionUtils.getUserType() ==UserType.MERCHANT_USER){

            redemptionVoucher.setRvrMerchantNo(authSessionUtils.getMerchantNo());

        }

        redemptionVoucher = saveRedemptionVoucher(redemptionVoucher);

        // Check if the redemptionVoucher is saved
        if ( redemptionVoucher.getRvrId() == null ) {

            // Log the response
            log.info("validateAndSaveRedemptionVoucher - Response : Unable to save the redemptionVoucher information");

            // Throw InspireNetzException with ERR_OPERATION_FAILED as error
            throw new InspireNetzException(APIErrorCode.ERR_OPERATION_FAILED);

        }

        // return the object
        return redemptionVoucher;


    }

    @Override
    public RedemptionVoucher saveRedemptionVoucher(RedemptionVoucher redemptionVoucher ){

        // Save the redemptionVoucher
        return redemptionVoucherRepository.save(redemptionVoucher);

    }

    @Override
    public boolean validateAndDeleteRedemptionVoucher(Long rvrId) throws InspireNetzException {

        // Get the redemptionVoucher information
        RedemptionVoucher redemptionVoucher = findByRvrId(rvrId);

        // Check if the redemptionVoucher is found
        if ( redemptionVoucher == null || redemptionVoucher.getRvrId() == null) {

            // Log the response
            log.info("deleteRedemptionVoucher - Response : No redemptionVoucher information found");

            // Thrown not found exception
            throw new InspireNetzException(APIErrorCode.ERR_NO_DATA_FOUND);

        }


        // Delete the redemptionVoucher and set the retData fields
        deleteRedemptionVoucher(rvrId);

        // Return true
        return true;

    }

    @Override
    public boolean deleteRedemptionVoucher(Long rolId) {

        // Delete the redemptionVoucher
        redemptionVoucherRepository.delete(rolId);

        // return true
        return true;

    }

    @Override
    public Page<RedemptionVoucher> searchRedemptionVoucher(String filter,String query, Pageable pageable) {

        Page<RedemptionVoucher> redemptionVouchers = null;
        //get merchant no
        Long merchantNo =authSessionUtils.getMerchantNo();

        if(filter.equals("0") &&  query.equals("0")){

            redemptionVouchers = redemptionVoucherRepository.findByRvrMerchantNoOrderByRvrIdDesc(merchantNo, pageable);

        }else if(filter.equals("voucherCode")){

            redemptionVouchers = redemptionVoucherRepository.findByRvrMerchantNoAndRvrVoucherCodeLikeOrderByRvrIdDesc(merchantNo, "%" + query + "%", pageable);

        }else if(filter.equals("productCode")){

            redemptionVouchers = redemptionVoucherRepository.findByRvrMerchantNoAndRvrProductCodeLikeOrderByRvrIdDesc(merchantNo, "%" + query + "%", pageable);

        }else if(filter.equals("loyaltyId")){

            redemptionVouchers = redemptionVoucherRepository.findByRvrMerchantNoAndRvrLoyaltyIdLikeOrderByRvrIdDesc(merchantNo, "%" + query + "%", pageable);
        }


        return redemptionVouchers;


    }

    @Override
    public Page<RedemptionVoucher> searchRedemptionVoucherForMerchant(String rvrVoucherCode, String rvrLoyaltyId,Date startDate,Date endDate, Pageable pageable) throws InspireNetzException {


        Page<RedemptionVoucher> redemptionVoucherPage=null;

        //currently logged user
        Long userNo =authSessionUtils.getUserNo();

        User user = userService.findByUsrUserNo(userNo);

        //find logged redemption merchant number
        Long rvrMerchant = user.getUsrThirdPartyVendorNo();

        //get redemption merchant
        RedemptionMerchant redemptionMerchant =redemptionMerchantService.findByRemNo(rvrMerchant);


        if(redemptionMerchant==null){

            throw new InspireNetzException(APIErrorCode.ERR_INVALID_REDEMPTION_MERCHANT);
        }

        if(rvrMerchant !=null){

            // Check if the salStartDate is set or not
            // If the start date is not set, then we need to set the date to the minimum value
            if ( startDate == null ){

                // Create the calendar object
                Calendar cal = Calendar.getInstance();

                // set Date portion to January 1, 1970
                cal.set( cal.YEAR, 1970 );
                cal.set( cal.MONTH, cal.JANUARY );
                cal.set( cal.DATE, 1 );

                startDate = new Date(cal.getTime().getTime());

            }


            // Check if the endDate is set, if not then we need to
            // set the date to the largest possible date
            if ( endDate == null ) {

                // Create the calendar object
                Calendar cal = Calendar.getInstance();

                // set Date portion to December 31, 9999
                cal.set( cal.YEAR, 9999 );
                cal.set( cal.MONTH, cal.DECEMBER );
                cal.set( cal.DATE, 31 );

                endDate = new Date(cal.getTime().getTime());

            }

            rvrVoucherCode =rvrVoucherCode == null?"0":rvrVoucherCode;

            rvrLoyaltyId =rvrLoyaltyId==null?"0":rvrLoyaltyId;


            redemptionVoucherPage = redemptionVoucherRepository.searchForRedemptionVoucher(rvrMerchant,rvrVoucherCode,rvrLoyaltyId,startDate,endDate,pageable);

        }


        return redemptionVoucherPage;

    }

    /**
     * @date 2/02/2014

     * @param rvrVoucherCode
     * @purpose:for checking voucher code is valid or not
     * @return
     */
    @Override
    public RedemptionVoucher redemptionVoucherIsValid(String rvrVoucherCode) throws InspireNetzException {

        //check if the voucher code is present
        RedemptionVoucher redemptionVoucher = redemptionVoucherRepository.findByRvrVoucherCode(rvrVoucherCode);

        //check redemption voucher is present or not if its not null return true if its valid
        if(redemptionVoucher ==null){

            throw new InspireNetzException(APIErrorCode.ERR_INVALID_REDEMPTION_VOUCHER);
        }

        //check redemption voucher is already claimed
        if(redemptionVoucher.getRvrStatus()==RedemptionVoucherStatus.REDEEMED){

            throw new InspireNetzException(APIErrorCode.ERR_REDEMPTION_VOUCHER_ALREADY_CLAIMED);
        }

        //check voucher is expired or not
        boolean isVoucherExpired =isVoucherExpired(redemptionVoucher);

        if(isVoucherExpired){

            throw new InspireNetzException(APIErrorCode.ERR_VOUCHER_IS_EXPIRED);
        }

        return redemptionVoucher;
    }

    /**
     * @date:02-02-15
     * @param rvrVoucherCode
     * @param rvrMerchantLocation
     * @purpose voucher claiming for merchant user
     * @return redemptionVoucher
     *
     */
    @Override
    public RedemptionVoucher voucherClaimForMerchantUser(String rvrVoucherCode, String rvrMerchantLocation) throws InspireNetzException {

        //first check the user is merchant user
        AuthUser authUser =authSessionUtils.getCurrentUser();

        //check auth userType if user type is merchant user then process
        if(authUser.getUserType() !=UserType.MERCHANT_USER){

            log.info("RedemptionVoucher Service::voucherClaimForMerchantUser :Unauthorised User");

            // throw InspireNetzException
            throw new InspireNetzException(APIErrorCode.ERR_NOT_AUTHORIZED);
        }

        //pick the merchant based on voucher code
        RedemptionVoucher redemptionVoucher =redemptionVoucherRepository.findByRvrVoucherCode(rvrVoucherCode);

        //check redemption voucher is exist with specified voucher code
        if(redemptionVoucher ==null){

            log.info("RedemptionVoucher Service::voucherClaimForMerchantUser :in valid redemption voucher");

            //throw new exception for invalid voucher
            throw new InspireNetzException(APIErrorCode.ERR_INVALID_REDEMPTION_VOUCHER);
        }

        //check redemption voucher is already claimed
        if(redemptionVoucher.getRvrStatus()==RedemptionVoucherStatus.REDEEMED){

            throw new InspireNetzException(APIErrorCode.ERR_REDEMPTION_VOUCHER_ALREADY_CLAIMED);
        }

        //check voucher is expired
        boolean isVoucherExpire =isVoucherExpired(redemptionVoucher);

        if(isVoucherExpire){

            throw new InspireNetzException(APIErrorCode.ERR_VOUCHER_IS_EXPIRED);
        }

        //pick the merchant number
        Long merchantNo = redemptionVoucher.getRvrMerchant()==null?0:redemptionVoucher.getRvrMerchant();

        //find the claimed location
        Long claimedLocation = getClaimedLocation(merchantNo,rvrMerchantLocation);

        //set claimed location
        if(claimedLocation ==0L){

            throw new InspireNetzException(APIErrorCode.ERR_INVALID_REDEMPTION_MERCHANT_LOCATION);
        }

        //process the voucher claiming
        redemptionVoucher.setRvrClaimedLocation(claimedLocation);

        //update status flag
        redemptionVoucher.setRvrStatus(RedemptionVoucherStatus.REDEEMED);

        // save the item
        redemptionVoucher = saveRedemptionVoucher(redemptionVoucher);

        // if not saved successfully, then show the message
        if(redemptionVoucher == null ){

            // Log the information
            log.info("redeemRedemptionVoucher -> Redemption voucher saving failed");


            // Throw operation failed
            throw new InspireNetzException(APIErrorCode.ERR_OPERATION_FAILED);

        }

        // Hashmap holding the parameters
        HashMap<String,String> params = new HashMap<>(0);

        // Set the voucher code
        params.put("#vouchercode",rvrVoucherCode);

        MessageWrapper messageWrapper=generalUtils.getMessageWrapperObject(MessageSpielValue.REDEMPTION_VOUCHER_CLAIM_SUCCESS,redemptionVoucher.getRvrLoyaltyId(),"","","",redemptionVoucher.getRvrMerchantNo(),params,MessageSpielChannel.ALL,IndicatorStatus.YES);

        userMessagingService.transmitNotification(messageWrapper);

        //send sms to the user
        //userMessagingService.sendSMS(MessageSpielValue.REDEMPTION_VOUCHER_CLAIM_SUCCESS,redemptionVoucher.getRvrLoyaltyId(),params);


        // Return the redemptionVoucher
        return redemptionVoucher;

    }

    /**
     * @Author:Al Ameen
     * @date:07-02-2015
     * @purpose:check redemption voucher is expire or  not
     * @param redemptionVoucher
     * @return boolean
     *
     */
    @Override
    public boolean isVoucherExpired(RedemptionVoucher redemptionVoucher)  {

        //check redemption voucher is null or not
        if(redemptionVoucher ==null){

            log.info("Redemption voucher is not valid");

            return true;

        }
        //check voucher is expired or not
        Date expiryDate =redemptionVoucher.getRvrExpiryDate();

        if(expiryDate ==null){

            log.info("RedemptionVoucherServiceImpl ->isVoucherExpired::Invalid date");

           return true;
        }

        //compare current Date to expiry Date
        java.util.Date currentDate =new java.util.Date();

        Calendar calendar = Calendar.getInstance();

        calendar.setTime( currentDate );

        //clear all the timestamp field
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);

        java.util.Date curDate =calendar.getTime();


        //integer for date comparission
        Integer compareDate =curDate.compareTo(expiryDate);

        // Compare the expiry date for the coupon to the currDate
        if (compareDate > 0  ) {

            return true;

        }

        return false;
    }

    @Override
    public Page<RedemptionVoucher> searchRedemptionVoucherForCustomer(String filter,String query,Date startDate,Date endDate, Long merchantNo, Pageable pageable) throws InspireNetzException {

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
        Page<RedemptionVoucher> redemptionVoucherPage=null;

        //initialize array list
        List<RedemptionVoucher> redemptionVoucherList =new ArrayList<>();

        //initialize list
        List<RedemptionVoucher> redemptionVoucherList1 =null;

        //check customer is connected with any merchant
        if(customerList ==null ||customerList.size() ==0){

            log.info("RedemptionVoucherService impl:searchRedemptionVoucher::No membership");

            throw new InspireNetzException(APIErrorCode.ERR_NOT_CONNECTED);

        }

        for (Customer customer:customerList){

            //check the filter and query condition
            if(filter.equals("0") && query.equals("0")){

                //find all voucher
                redemptionVoucherList1 =redemptionVoucherRepository.findByRvrMerchantNoAndRvrLoyaltyIdAndCreatedAtBetweenOrderByRvrIdDesc(customer.getCusMerchantNo(),customer.getCusLoyaltyId(), startDate, endDate);

            }else if(filter.equals("voucherCode")){

                //voucher code like
                redemptionVoucherList1 =redemptionVoucherRepository.findByRvrMerchantNoAndRvrVoucherCodeLikeAndRvrLoyaltyIdAndCreatedAtBetweenOrderByRvrIdDesc(customer.getCusMerchantNo(),"%"+query+"%",customer.getCusLoyaltyId(), startDate, endDate);

            }else if(filter.equals("productCode")){

                //search for product code
                redemptionVoucherList1 =redemptionVoucherRepository.findByRvrMerchantNoAndRvrProductCodeLikeAndRvrLoyaltyIdAndCreatedAtBetweenOrderByRvrIdDesc(customer.getCusMerchantNo(),"%"+query+"%", customer.getCusLoyaltyId(), startDate, endDate);

            }


            //iterate add customer list into referral
            for (RedemptionVoucher  redemptionVoucher:redemptionVoucherList1){

                //add into redemption voucher list
                redemptionVoucherList.add(redemptionVoucher);
            }

        }

        //convert list of item into pageable Parameter
        redemptionVoucherPage =new PageImpl<>(redemptionVoucherList);

        //return redemption voucher
        return  redemptionVoucherPage;
    }

    @Override
    public RedemptionVoucher updateRedemptionVoucher(RedemptionVoucherUpdateRequest redemptionVoucherUpdateRequest) throws InspireNetzException {

        //check the access right for updating voucher
        authSessionUtils.validateFunctionAccess(FunctionCode.FNC_UPDATE_VOUCHER);

        //get merchant number
        Long merchantNo =authSessionUtils.getMerchantNo();

        //initialise spiel value
        String voucherSpiel ="";

        //check the voucher update is possible for merchant
        boolean isEnabled =merchantSettingService.isSettingEnabledForMerchant(AdminSettingsConfiguration.MER_ENABLE_VOUCHER_UPDATE,merchantNo);

        if(!isEnabled){

            log.info("pls check MER_ENABLE_VOUCHER_UPDATE Settings received Merchant Number"+merchantNo);

            throw new InspireNetzException(APIErrorCode.ERR_INVALID_REQUEST);
        }

        //find the redemption voucher
        RedemptionVoucher redemptionVoucher = findByRvrId(redemptionVoucherUpdateRequest.getRvrReqId() ==null?0L:redemptionVoucherUpdateRequest.getRvrReqId());

        //redemption voucher is null throw error
        if(redemptionVoucher ==null){

          //get the redemption voucher using tracking id
          if(redemptionVoucherUpdateRequest.getRvrReqUniqueBatchId() ==null) {

              //throw error for invalid tracking id
              throw new InspireNetzException(APIErrorCode.ERR_INVALID_TRACKING_ID);

          }

          //get the redemption voucher
          redemptionVoucher = findByRedemptionVoucher(redemptionVoucherUpdateRequest.getRvrReqUniqueBatchId());

          if(redemptionVoucher ==null){

              //the throw error invalid voucher code
              throw new InspireNetzException(APIErrorCode.ERR_INVALID_TRACKING_ID);
          }

          redemptionVoucher=createRedemptionVoucherObject(redemptionVoucherUpdateRequest,redemptionVoucher);

        }

        //check the expiry option if is enabled set the new voucher expiry date
        if(redemptionVoucherUpdateRequest.getRvrExpiryOption() ==IndicatorStatus.YES){

            //update the expiry date
            redemptionVoucher.setRvrExpiryDate(redemptionVoucherUpdateRequest.getRvrReqExpiryDate());

        }

        if(redemptionVoucherUpdateRequest.getRvrReqVoucherCode() ==null || redemptionVoucherUpdateRequest.getRvrReqVoucherCode().equals("")){

            throw new InspireNetzException(APIErrorCode.ERR_INVALID_INPUT);
        }

        //update the voucher and voucher status
        redemptionVoucher.setRvrVoucherCode(redemptionVoucherUpdateRequest.getRvrReqVoucherCode());

        //update the status
        redemptionVoucher.setRvrAssignedStatus(VoucherUpdateStatus.ASSIGNED);

        //update redemption voucher
        redemptionVoucher =validateAndSaveRedemptionVoucher(redemptionVoucher);

        if ( redemptionVoucher.getRvrId() == null ) {

            // Log the response
            log.info("validateAndSaveRedemptionVoucher - Response : Unable to save the redemptionVoucher information");

            // Throw InspireNetzException with ERR_OPERATION_FAILED as error
            throw new InspireNetzException(APIErrorCode.ERR_OPERATION_FAILED);

        }

        //check the notification status is enabled send sms and email
        if(redemptionVoucherUpdateRequest.getRvrNotification().intValue() ==IndicatorStatus.YES){

            //send notification message to customer
            HashMap<String,String> msgParams = new HashMap<>();

            //add points to the map
            msgParams.put("#voucherCode",redemptionVoucher.getRvrVoucherCode()+"");

            //add customer name
            msgParams.put("#productCode",redemptionVoucher.getRvrVoucherCode());

            //set expiry option
            msgParams.put("#amount",redemptionVoucherUpdateRequest.getAmount());

            //set the pin
            msgParams.put("#pin",redemptionVoucherUpdateRequest.getPin());

            //set the expiry date
            msgParams.put("#expiryDate",redemptionVoucher.getRvrExpiryDate()+"");

            //get the Message spiel of catalogue
            Catalogue catalogue =catalogueService.findByCatProductCodeAndCatMerchantNo(redemptionVoucher.getRvrProductCode(),redemptionVoucher.getRvrMerchantNo());

            //check the catalogue
            if(catalogue !=null){

              voucherSpiel = catalogue.getCatVoucherSpiel() ==null?"":catalogue.getCatVoucherSpiel();

              //set catalogue name
              msgParams.put("#productName",catalogue.getCatDescription());

              //create message wrapper object
              MessageWrapper messageWrapper =  generalUtils.getMessageWrapperObject(voucherSpiel,redemptionVoucher.getRvrLoyaltyId(),"","","",redemptionVoucher.getRvrMerchantNo(),msgParams,MessageSpielChannel.ALL,IndicatorStatus.YES);

              //send sms
              userMessagingService.transmitNotification(messageWrapper);
            }


        }

        return redemptionVoucher;
    }

    @Override
    public RedemptionVoucher findByRedemptionVoucher(String uniqueTrackingId) {
        return redemptionVoucherRepository.findByRvrUniqueBatchId(uniqueTrackingId);
    }

    @Override
    public List<RedemptionVoucher> findByRvrIdIn(String rvrList) throws InspireNetzException {

        //split the string and add into a list
        List<Long> rvrIdList =new ArrayList<>();

        if(rvrList !=null && !rvrList.equals("")){

            String rvrString ="";

            for (int i=0;i<rvrList.length();i++){

                //parse the coming request
                if(rvrList.charAt(i) ==',' || rvrList.charAt(i) =='\n'){

                    Long rvrId = Long.parseLong(rvrString);

                    rvrIdList.add(rvrId);

                    rvrString ="";

                }else {

                    rvrString = rvrString+rvrList.charAt(i);
                }


            }

            //add the final data into the list
            if(!rvrString.equals("")){

                Long rvrId = Long.parseLong(rvrString);

                rvrIdList.add(rvrId);
            }

        }

        //get the redemption voucher based on id
        List<RedemptionVoucher> redemptionVoucherList = redemptionVoucherRepository.findByRvrIdIn(rvrIdList);

        //check the duplicate loyalty id is duplicate or not
        boolean isDuplicateLoyaltyId = checkDuplicateLoyaltyId(redemptionVoucherList);

        //check the duplicate
        if(isDuplicateLoyaltyId){

            log.info("More than one  Loyalty id in the list ------------->"+redemptionVoucherList);
            throw new InspireNetzException(APIErrorCode.ERR_MORE_THAN_ONE_LOYALTY_ID);

        }



        //check the duplicate loyalty id is duplicate or not
        boolean isDuplicateProductCode  = checkDuplicateProductCode(redemptionVoucherList);

        //check the duplicate
        if(isDuplicateProductCode){

            log.info("more than one  product code in  the list ------------->"+redemptionVoucherList);
            throw new InspireNetzException(APIErrorCode.ERR_MORE_THAN_ONE_PRODUCT_CODE);

        }




        return redemptionVoucherList;
    }

    private boolean checkDuplicateProductCode(List<RedemptionVoucher> redemptionVoucherList) {

        //add into set
        HashSet<String> hashSet =new HashSet<>();

        if(redemptionVoucherList !=null && redemptionVoucherList.size() >0){

            for (RedemptionVoucher redemptionVoucher :redemptionVoucherList){

                hashSet.add(redemptionVoucher.getRvrProductCode());
            }

        }else {

            return false;
        }

        //check the size of set and list if size greater than 1
        if (hashSet.size() >1){

            return true;
        }

        return  false;


    }

    @Override
    public void sendVoucherVoucherNotificationList(RedemptionVoucherUpdateRequest redemptionVoucherUpdateRequest) throws InspireNetzException {


        //access right
        authSessionUtils.validateFunctionAccess(FunctionCode.FNC_UPDATE_VOUCHER_REQUEST);

        //update redemption update request
        List<RedemptionVoucherUpdateRequest> redemptionVoucherUpdateRequests = redemptionVoucherUpdateRequest.getRedemptionVoucherUpdateRequestList();

        //get merchant information
        Long merchantNo =authSessionUtils.getMerchantNo();

        //catalogue information
        Catalogue catalogue =null;

        //boolean enable notification
        boolean notification =false;

        //set loyalty Id
        String loyaltyId ="";

        String messageSpiel ="<table border=\"1\">\n" +
                             "<th>Voucher Code</>\n" +
                               "<th>Redeemed Product Name</th>\n" +
                                "<th>Amount</th>\n" +
                                    "<th>Pin</th>\n" +
                                        "<th>Expiry Date</th>\n" ;


        //check voucher request is null or not
        if(redemptionVoucherUpdateRequest ==null || redemptionVoucherUpdateRequests.size() ==0){

            log.info("Update voucher request is null --------->"+redemptionVoucherUpdateRequest);

            return;
        }

        //then update voucher and send notification to customer
        for(RedemptionVoucherUpdateRequest redemptionVoucherUpdateRequest1 :redemptionVoucherUpdateRequests){

            //get rvr id from the list if the rvrId is present then update voucher
            Long rvrId = redemptionVoucherUpdateRequest1.getRvrReqId() ==null?0L:redemptionVoucherUpdateRequest1.getRvrReqId();

            //set loyalty id


            //check the rvrId present or
            if(rvrId.longValue() !=0L){

                //find redemption voucher data and update voucher
                RedemptionVoucher redemptionVoucher = findByRvrId(rvrId);

                if(redemptionVoucher ==null){

                    log.info("Invalid Input Received voucher information-------->"+redemptionVoucherUpdateRequest1);

                    continue;
                }

                //set loyalty id information
                loyaltyId = redemptionVoucher.getRvrLoyaltyId();

                if(catalogue == null){


                    catalogue = catalogueService.findByCatProductCodeAndCatMerchantNo(redemptionVoucher.getRvrProductCode(),redemptionVoucher.getRvrMerchantNo());

                }


                //set the new voucher code and update new voucher information
                redemptionVoucher.setRvrAssignedStatus(VoucherUpdateStatus.ASSIGNED);

                //get expiry option
                Integer rvrExpiryOption = redemptionVoucherUpdateRequest1.getRvrExpiryOption() ==null?0:redemptionVoucherUpdateRequest1.getRvrExpiryOption();

                //check the status
                if(rvrExpiryOption.intValue() == IndicatorStatus.YES){

                    //set new value other wise set voucher expiry date
                    redemptionVoucher.setRvrExpiryDate(redemptionVoucherUpdateRequest1.getRvrReqExpiryDate());
                }

                redemptionVoucher.setRvrVoucherCode(redemptionVoucherUpdateRequest1.getRvrReqVoucherCode());

                //update voucher information
                redemptionVoucher= saveRedemptionVoucher(redemptionVoucher);

                //get notification option
                Integer rvrNotification = redemptionVoucherUpdateRequest1.getRvrNotification() ==null?0:redemptionVoucherUpdateRequest1.getRvrNotification();

                //check notification enabled
                if (rvrNotification.intValue() ==IndicatorStatus.YES){

                    notification =true;

                    String formattedSpiel = formatMessage(redemptionVoucherUpdateRequest1,redemptionVoucher);

                    //put the notification spiel name
                    messageSpiel = messageSpiel +formattedSpiel;
                }




            }else {

                //find redemption list based on tracking id
                List<RedemptionVoucher> redemptionVoucherList = findByRvrUniqueBatchIdAndRvrMerchantNo(redemptionVoucherUpdateRequest1.rvrReqUniqueBatchId,merchantNo);

                if(redemptionVoucherList ==null || redemptionVoucherList.size()==0){

                    log.info("Invalid tracking id ----------------->"+redemptionVoucherUpdateRequest1);

                    continue;
                }

                //get the first voucher information
                RedemptionVoucher redemptionVoucher = redemptionVoucherList.get(0);

                loyaltyId = redemptionVoucher.getRvrLoyaltyId();

                if(catalogue == null){

                    catalogue = catalogueService.findByCatProductCodeAndCatMerchantNo(redemptionVoucher.getRvrProductCode(),redemptionVoucher.getRvrMerchantNo());

                }


                //get the redemption voucher object
                RedemptionVoucher redemptionVoucher1 = createRedemptionVoucherObject(redemptionVoucherUpdateRequest1,redemptionVoucher);

                //save redemption vpoucher information
                redemptionVoucher1 = saveRedemptionVoucher(redemptionVoucher1);

                Integer rvrNotification = redemptionVoucherUpdateRequest1.getRvrNotification() ==null?0:redemptionVoucherUpdateRequest1.getRvrNotification();

                //check notification enabled
                if (rvrNotification.intValue() ==IndicatorStatus.YES){

                    notification =true;

                    String formattedSpiel1 = formatMessage(redemptionVoucherUpdateRequest1,redemptionVoucher1);

                    //update voucher information
                    messageSpiel = messageSpiel + formattedSpiel1;

                }


            }
        }

        //send notification to customer
        MessageWrapper messageWrapper =new MessageWrapper();

        messageWrapper.setLoyaltyId(loyaltyId);

        messageWrapper.setIsCustomer(IndicatorStatus.YES);

        messageWrapper.setMerchantNo(authSessionUtils.getMerchantNo());

        HashMap<String,String> msgParams = new HashMap<>();

        if (catalogue !=null){

            //put terms and condition
            msgParams.put("#termsAndCondition",catalogue.getCatTermsAndConditions());

        }



        //send message to
        sendSmsNotification(messageWrapper);

        //send mail notification
        sendMailNotification(messageWrapper,notification,messageSpiel,msgParams);

        log.info("message spiel information ---------->"+messageSpiel);


    }

    private void sendMailNotification(MessageWrapper messageWrapper, boolean notification, String messageSpiel,HashMap msgParams) throws InspireNetzException {

        //update mail notification
        messageWrapper.setChannel(MessageSpielChannel.EMAIL);

        messageWrapper.setSpielName(MessageSpielValue.VOUCHER_UPDATE_NOTIFICATION_EMAIL);

        if(notification){

            messageSpiel = messageSpiel +"</table>";

            msgParams.put("#mailContent",messageSpiel);

        }

        messageWrapper.setParams(msgParams);

        //transmit mail notification
        userMessagingService.transmitNotification(messageWrapper);

    }

    private void sendSmsNotification(MessageWrapper messageWrapper) throws InspireNetzException {

        //set the channel as sms
        messageWrapper.setChannel(MessageSpielChannel.SMS);

        messageWrapper.setSpielName(MessageSpielValue.VOUCHER_UPDATE_NOTIFICATION_SMS);

        //transmit notification
        userMessagingService.transmitNotification(messageWrapper);

    }

    private String formatMessage(RedemptionVoucherUpdateRequest redemptionVoucherUpdateRequest1, RedemptionVoucher redemptionVoucher) {


        //get the Message spiel of catalogue
        Catalogue catalogue =catalogueService.findByCatProductCodeAndCatMerchantNo(redemptionVoucher.getRvrProductCode(),redemptionVoucher.getRvrMerchantNo());

        String formatString = "<tr><td>"+redemptionVoucher.getRvrVoucherCode()
                +"</td>"
                +"<td>"+catalogue.getCatDescription()+"</td>"+"<td>"+redemptionVoucherUpdateRequest1.getAmount()+"</td>"
                +"<td>"+redemptionVoucherUpdateRequest1.getPin()+"</td>"
                +"<td>"+redemptionVoucher.getRvrExpiryDate()+"</td> </tr>";

        return formatString;
    }

    @Override
    public List<RedemptionVoucher> findByRvrUniqueBatchIdAndRvrMerchantNo(String rvrUniqueBatchId, Long merchantNo) {
        return redemptionVoucherRepository.findByRvrUniqueBatchIdAndRvrMerchantNo(rvrUniqueBatchId,merchantNo);
    }

    private boolean checkDuplicateLoyaltyId(List<RedemptionVoucher> redemptionVoucherList) {

        //add into set
        HashSet<String> hashSet =new HashSet<>();

        if(redemptionVoucherList !=null && redemptionVoucherList.size() >0){

            for (RedemptionVoucher redemptionVoucher :redemptionVoucherList){

                hashSet.add(redemptionVoucher.getRvrLoyaltyId());
            }

        }else {

            return false;
        }

        //check the size of set and list if size greater than 1
        if (hashSet.size() >1){

            return true;
        }

        return  false;

    }

    private RedemptionVoucher createRedemptionVoucherObject(RedemptionVoucherUpdateRequest redemptionVoucherUpdateRequest,RedemptionVoucher redemptionVoucher) {

        //create redemption voucher object
        RedemptionVoucher redemptionVoucher1 =new RedemptionVoucher();

        redemptionVoucher1.setRvrMerchantNo(redemptionVoucher.getRvrMerchantNo());

        redemptionVoucher1.setRvrProductCode(redemptionVoucher.getRvrProductCode());

        redemptionVoucher1.setRvrMerchant(redemptionVoucher.getRvrMerchant());

        redemptionVoucher1.setRvrCustomerNo(redemptionVoucher.getRvrCustomerNo());
        redemptionVoucher1.setRvrLoyaltyId(redemptionVoucher.getRvrLoyaltyId());

        //created date
        redemptionVoucher1.setRvrCreateDate(DBUtils.getSystemDate());

        //check the expiry option
        if(redemptionVoucherUpdateRequest.getRvrExpiryOption().intValue() == IndicatorStatus.NO){

            redemptionVoucher1.setRvrExpiryDate(redemptionVoucher.getRvrExpiryDate());
        }

        redemptionVoucher1.setRvrUniqueBatchId(redemptionVoucher.getRvrUniqueBatchId());

        String auditDetails = authSessionUtils.getUserNo().toString();

        redemptionVoucher1.setCreatedBy(auditDetails);

        return  redemptionVoucher1;
    }

    /**
     *
     * @param merchantNo
     * @param rvrMerchantLocation
     * @purpose get claimed location
     * @return
     */
    private Long getClaimedLocation(Long merchantNo, String rvrMerchantLocation) throws InspireNetzException {

        //get all location for redemption merchant
        List<RedemptionMerchantLocation> redemptionMerchantLocationList =redemptionMerchantLocationService.findByRmlMerNo(merchantNo);

        //check the location is exist or not
        if(redemptionMerchantLocationList ==null || redemptionMerchantLocationList.size() ==0){

            log.info("Invalid merchant location");

            throw new InspireNetzException(APIErrorCode.ERR_INVALID_REDEMPTION_MERCHANT_LOCATION);
        }

        //find the redemption merchant location with the merchant location name
        for(RedemptionMerchantLocation redemptionMerchantLocation:redemptionMerchantLocationList){

            if(redemptionMerchantLocation.getRmlLocation().equals(rvrMerchantLocation)){

                //get the claimed location
                return redemptionMerchantLocation.getRmlId();
            }
        }

        return 0L;
    }


}

