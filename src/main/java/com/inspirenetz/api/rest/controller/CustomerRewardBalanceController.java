package com.inspirenetz.api.rest.controller;

import com.inspirenetz.api.core.auth.AuthSession;
import com.inspirenetz.api.core.dictionary.APIErrorCode;
import com.inspirenetz.api.core.dictionary.APIResponseStatus;
import com.inspirenetz.api.core.dictionary.DrawType;
import com.inspirenetz.api.core.dictionary.TransferPointRequest;
import com.inspirenetz.api.core.domain.*;
import com.inspirenetz.api.core.service.*;
import com.inspirenetz.api.rest.assembler.CustomerAssembler;
import com.inspirenetz.api.rest.assembler.CustomerRewardBalanceAssembler;
import com.inspirenetz.api.rest.assembler.CustomerRewardBalanceCompatibleAssembler;
import com.inspirenetz.api.rest.exception.InspireNetzException;
import com.inspirenetz.api.rest.resource.CustomerRewardBalanceCompatibleResource;
import com.inspirenetz.api.rest.resource.CustomerRewardBalanceResource;
import com.inspirenetz.api.util.APIResponseObject;
import com.inspirenetz.api.util.APIResponseObjectFactory;
import com.inspirenetz.api.util.AuthSessionUtils;
import com.inspirenetz.api.util.GeneralUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by sandheepgr on 16/2/14.
 */
@Controller
public class CustomerRewardBalanceController {

    @Autowired
    private CustomerRewardBalanceService customerRewardBalanceService;

    @Autowired
    private CustomerRewardBalanceCompatibleAssembler customerRewardBalanceCompatibleAssembler;

    @Autowired
    private CustomerRewardBalanceAssembler customerRewardBalanceAssembler;

    @Autowired
    private DrawChanceService drawChanceService;

    @Autowired
    private AuthSessionUtils authSessionUtils;

    @Autowired
    GeneralUtils generalUtils;

    @Autowired
    RewardCurrencyService rewardCurrencyService;

    @Autowired
    MerchantRedemptionPartnerService merchantRedemptionPartnerService;

    // Create the logger
    private static Logger log = LoggerFactory.getLogger(CustomerRewardBalanceController.class);

    @RequestMapping(value = "/api/0.9/json/loyalty/rewardbalance", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public APIResponseObject getRewardBalanceCompatible(
                                                @RequestParam(value = "loyalty_id",required = true) String loyaltyId,
                                                @RequestParam(value = "rwd_currency_id",required = false,defaultValue = "0") Long rwdCurrencyId
                                             ) throws InspireNetzException {

        // Create the APIResponseObject
        APIResponseObject retData = APIResponseObjectFactory.getJSONAPIResponseObject();

        // Get the merchant number and store
        long merchantNo = authSessionUtils.getMerchantNo();


        // Log the Request
        log.info("getRewardBalanceCompatible - Request Received# loyalty_id = " +loyaltyId + " : rwd_currency_id = " +rwdCurrencyId );
        log.info("getRewardBalanceCompatible -  "+generalUtils.getLogTextForRequest());


        // Get the list of CustomerRewardBalance
        List<CustomerRewardBalance> customerRewardBalanceList = new ArrayList<CustomerRewardBalance>();


        // Check if the rwdCurrencyId is specified, if yes, then we need to
        // get the data filtered for the reward currency id
        if ( rwdCurrencyId != 0 ) {

            // Get the rewardbalance object
            CustomerRewardBalance customerRewardBalance = customerRewardBalanceService.findByCrbLoyaltyIdAndCrbMerchantNoAndCrbRewardCurrency(loyaltyId,merchantNo,rwdCurrencyId);

            // Check if the reward balance exists
            if ( customerRewardBalance != null  ){

                customerRewardBalanceList.add(customerRewardBalance);

            }

        } else {

            customerRewardBalanceList = customerRewardBalanceService.findByCrbLoyaltyIdAndCrbMerchantNo(loyaltyId,merchantNo);

        }


        // If the list is empty, then we don't have any result
        if ( customerRewardBalanceList.isEmpty() ) {

            // Log the response
            log.info("getRewardBalanceCompatible - Response : No data for the request " );

            // Set the data
            retData.setData(new ArrayList<CustomerRewardBalanceCompatibleResource>());

            // Set the balance to make it compatible for wpf
            retData.setBalance(new ArrayList<CustomerRewardBalanceCompatibleResource>());

            // Set the status as success
            retData.setStatus(APIResponseStatus.failed);

            //set error code
            retData.setErrorCode(APIErrorCode.ERR_NO_DATA_FOUND);


        } else {

            // Log the response
            log.info("getRewardBalanceCompatible -"+generalUtils.getLogTextForResponse(retData));

            // Convert the data to resource
            List<CustomerRewardBalanceCompatibleResource> customerRewardBalanceCompatibleResourceList = customerRewardBalanceCompatibleAssembler.toResources(customerRewardBalanceList,merchantNo);

            // Set the data
            retData.setData(customerRewardBalanceCompatibleResourceList);

            // Set the balance to make it compatible for wpf
            retData.setBalance(customerRewardBalanceCompatibleResourceList);

            // Set the status as success
            retData.setStatus(APIResponseStatus.success);

        }

        // Return the retData object
        return retData;

    }




    @RequestMapping(value = "/api/0.9/json/merchant/customer/rewardbalance/{loyaltyId}/{rwdCurrencyId}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public APIResponseObject getRewardBalanceForMerchantRequest(
                        @PathVariable(value = "loyaltyId") String loyaltyId,
                        @PathVariable(value = "rwdCurrencyId") long rwdCurrencyId
    ) throws InspireNetzException {

        // Create the APIResponseObject
        APIResponseObject retData = APIResponseObjectFactory.getJSONAPIResponseObject();


        log.info("getRewardBalanceForMerchantRequest - Request Received# loyalty_id = " +loyaltyId + " : rwd_currency_id = " +rwdCurrencyId );
        log.info("getRewardBalanceForMerchantRequest -  "+generalUtils.getLogTextForRequest());


        // Get the list of CustomerRewardBalance
        List<CustomerRewardBalance> customerRewardBalanceList = customerRewardBalanceService.searchBalances(null,loyaltyId,rwdCurrencyId);

        //get draw chance for customer
        DrawChance drawChance = drawChanceService.getDrawChancesByLoyaltyId(loyaltyId,DrawType.RAFFLE_TICKET);

        List<CustomerRewardBalanceResource> customerRewardBalanceResourceList = customerRewardBalanceAssembler.toResources(customerRewardBalanceList,drawChance);

        // convert the reward balance list to CustomerRewardBalanceResource
      //  List<CustomerRewardBalanceResource> customerRewardBalanceResourceList = customerRewardBalanceAssembler.toResources(customerRewardBalanceList);

        //find the customer draw chance

        // Set the list
        retData.setData(customerRewardBalanceResourceList);

        // Set the status as success
        retData.setStatus(APIResponseStatus.success);

        // Log the response
        log.info("getRewardBalance - "+generalUtils.getLogTextForResponse(retData));



        // Return the retData object
        return retData;
    }



    @RequestMapping(value = "/api/0.9/json/merchant/customer/rewardbalance/sms/{loyaltyId}/{rwdCurrencyId}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public APIResponseObject sendRewardBalanceSMSForCustomer(
            @PathVariable(value = "loyaltyId") String loyaltyId,
            @PathVariable(value = "rwdCurrencyId") long rwdCurrencyId
    ) throws InspireNetzException {

        // Create the APIResponseObject
        APIResponseObject retData = APIResponseObjectFactory.getJSONAPIResponseObject();


        log.info("sendRewardBalanceSMSForCustomer - Request Received# loyalty_id = " +loyaltyId + " : rwd_currency_id = " +rwdCurrencyId );
        log.info("sendRewardBalanceSMSForCustomer -  "+generalUtils.getLogTextForRequest());


        // Send the reward balance
        customerRewardBalanceService.sendRewardBalanceSMS(authSessionUtils.getMerchantNo(),loyaltyId,rwdCurrencyId);

        // Set the status as success
        retData.setStatus(APIResponseStatus.success);

        // Log the response
        log.info("getRewardBalance - "+generalUtils.getLogTextForResponse(retData));



        // Return the retData object
        return retData;
    }



    @RequestMapping(value = "/api/0.9/json/merchant/customer/rewardbalance", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public APIResponseObject getRewardBalanceForMerchantRequestParams(
            @RequestParam(value = "loyaltyId") String loyaltyId,
            @RequestParam(value = "rwdCurrencyId") long rwdCurrencyId
    ) throws InspireNetzException {

        // Create the APIResponseObject
        APIResponseObject retData = APIResponseObjectFactory.getJSONAPIResponseObject();


        log.info("getRewardBalanceForMerchantRequest - Request Received# loyalty_id = " +loyaltyId + " : rwd_currency_id = " +rwdCurrencyId );
        log.info("getRewardBalanceForMerchantRequest -  "+generalUtils.getLogTextForRequest());


        // Get the list of CustomerRewardBalance
        List<CustomerRewardBalance> customerRewardBalanceList = customerRewardBalanceService.searchBalances(null,loyaltyId,rwdCurrencyId);

        // convert the reward balance list to CustomerRewardBalanceResource
        List<CustomerRewardBalanceResource> customerRewardBalanceResourceList = customerRewardBalanceAssembler.toResources(customerRewardBalanceList);



        // Set the list
        retData.setData(customerRewardBalanceResourceList);

        // Set the status as success
        retData.setStatus(APIResponseStatus.success);

        // Log the response
        log.info("getRewardBalance - "+generalUtils.getLogTextForResponse(retData));



        // Return the retData object
        return retData;
    }



    @RequestMapping(value = "/api/0.9/json/customer/rewardbalance/{merchantNo}/{rwdCurrencyId}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public APIResponseObject getRewardBalanceForCustomerRequest(
            @PathVariable(value = "merchantNo") Long merchantNo,
            @PathVariable(value = "rwdCurrencyId") long rwdCurrencyId
    ) throws InspireNetzException {

        // Create the APIResponseObject
        APIResponseObject retData = APIResponseObjectFactory.getJSONAPIResponseObject();

        log.info("getRewardBalanceForCustomerRequest - Request Received# merchantNo = " +merchantNo + " : rwd_currency_id = " +rwdCurrencyId );
        log.info("getRewardBalanceForCustomerRequest -  "+generalUtils.getLogTextForRequest());


        // Get the list of CustomerRewardBalance
        List<CustomerRewardBalance> customerRewardBalanceList = customerRewardBalanceService.searchBalances(merchantNo,null,rwdCurrencyId);

        // convert the reward balance list to CustomerRewardBalanceResource
      //  List<CustomerRewardBalanceResource> customerRewardBalanceResourceList = customerRewardBalanceAssembler.toResources(customerRewardBalanceList);

        //get loyalty id for customer
        String loyaltyId =authSessionUtils.getUserLoginId();

        DrawChance drawChance = drawChanceService.getDrawChancesByLoyaltyId(loyaltyId,DrawType.RAFFLE_TICKET);

        List<CustomerRewardBalanceResource> customerRewardBalanceResourceList = customerRewardBalanceAssembler.toResources(customerRewardBalanceList,drawChance);

        // Set the list
        retData.setData(customerRewardBalanceResourceList);

        // Set the status as success
        retData.setStatus(APIResponseStatus.success);

        // Log the response
        log.info("getRewardBalance - "+generalUtils.getLogTextForResponse(retData));



        // Return the retData object
        return retData;
    }




    @RequestMapping(value = "/api/0.9/json/customer/rewardbalance/transferpoints", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public APIResponseObject transferPointsForCustomer(
                        @RequestParam(value = "merchantNo") Long merchantNo,
                        @RequestParam(value = "destLoyaltyId") String destLoyaltyId,
                        @RequestParam(value = "srcCurrencyId") long srcCurrencyId,
                        @RequestParam(value = "destCurrencyId") long destCurrencyId,
                        @RequestParam(value = "rwdQty") double rwdQty
                ) throws InspireNetzException {


        // Create the APIResponseObject
        APIResponseObject retData = APIResponseObjectFactory.getJSONAPIResponseObject();


        log.info("transferPointsForCustomer - Request Received# merchantNo = " +merchantNo + " : destLoyaltyId = " +destLoyaltyId + " : srcCurrencyid "+srcCurrencyId + " destCurrencyId : "+destCurrencyId);
        log.info("transferPointsForCustomer -  "+generalUtils.getLogTextForRequest());


        // Call the api for performing transfer points
        boolean isTransferred = customerRewardBalanceService.transferPointsForCustomerUser(merchantNo,destLoyaltyId,srcCurrencyId,destCurrencyId,rwdQty);

        // If not transferred, set as failed
        if ( !isTransferred ) {

            // Set the status as failed
            retData.setStatus(APIResponseStatus.failed);

        } else {

            // Set the status as success
            retData.setStatus(APIResponseStatus.success);

        }




        // Set the list
        retData.setData(rwdQty);

        // Log the response
        log.info("getRewardBalance - "+generalUtils.getLogTextForResponse(retData));


        // Return the retData object
        return retData;
    }




    @RequestMapping(value = "/api/0.9/json/merchant/customer/rewardbalance/transferpoints", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public APIResponseObject transferPointsForMerchant(
            @RequestParam(value = "srcLoyaltyId") String srcLoyaltyId,
            @RequestParam(value = "destLoyaltyId") String destLoyaltyId,
            @RequestParam(value = "srcCurrencyId") long srcCurrencyId,
            @RequestParam(value = "destCurrencyId") long destCurrencyId,
            @RequestParam(value = "rwdQty") double rwdQty
    ) throws InspireNetzException {


        // Create the APIResponseObject
        APIResponseObject retData = APIResponseObjectFactory.getJSONAPIResponseObject();


        log.info("transferPointsForMerchant - Request Received# srcLoyaltyId = " +srcLoyaltyId + " : destLoyaltyId = " +destLoyaltyId + " : srcCurrencyid "+srcCurrencyId + " destCurrencyId : "+destCurrencyId);
        log.info("transferPointsForMerchant -  "+generalUtils.getLogTextForRequest());


        // Call the api for performing transfer points
        boolean isTransferred = customerRewardBalanceService.transferPointsForMerchantUser(srcLoyaltyId, destLoyaltyId, srcCurrencyId, destCurrencyId, rwdQty);

        // If not transferred, set as failed
        if ( !isTransferred ) {

            // Set the status as failed
            retData.setStatus(APIResponseStatus.failed);

        } else {

            // Set the status as success
            retData.setStatus(APIResponseStatus.success);

        }




        // Set the list
        retData.setData(rwdQty);

        // Log the response
        log.info("getRewardBalance - "+generalUtils.getLogTextForResponse(retData));


        // Return the retData object
        return retData;
    }

    @RequestMapping(value = "/api/0.9/json/merchant/rewardbalance/buypoints", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public APIResponseObject buyPointsForMerchant(
            @RequestParam(value = "loyaltyid") String loyaltyId,
            @RequestParam(value = "crbRwdCurrencyId") Long crbRewardCurrency,
            @RequestParam(value = "crbNumPoints") Long crbNumPoints
    ) throws InspireNetzException {


        // Create the APIResponseObject
        APIResponseObject retData = APIResponseObjectFactory.getJSONAPIResponseObject();

        Long merchantNo = authSessionUtils.getMerchantNo();

        log.info("BuyPointsForMerchant - Request Received# merchantNo = " +merchantNo + " : crbRewardCurreny = " +crbRewardCurrency + " : crbNumPoints "+crbNumPoints );
        log.info("BuyPointsForMerchant -  "+generalUtils.getLogTextForRequest());


        // Call the api for performing transfer points
        CustomerRewardBalance customerRewardBalance = customerRewardBalanceService.buyPoints(loyaltyId,merchantNo,crbRewardCurrency,crbNumPoints);

        // If not transferred, set as failed
        if ( null!= customerRewardBalance ) {

            // Set the status as failed
            retData.setStatus(APIResponseStatus.success);

        } else {

            // Set the status as success
            retData.setStatus(APIResponseStatus.failed);

        }




        // Set the list
        retData.setData(customerRewardBalance.getCrbRewardBalance());

        // Log the response
        log.info("buyPointForCustomer - "+generalUtils.getLogTextForResponse(retData));


        // Return the retData object
        return retData;
    }

    @RequestMapping(value = "/api/0.9/json/customer/rewardbalance/buypoints", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public APIResponseObject buyPointsForCustomer(
            @RequestParam(value = "merchantno") Long merchantNo,
            @RequestParam(value = "crbRwdCurrencyId") Long crbRewardCurrency,
            @RequestParam(value = "crbNumPoints") Long crbNumPoints
    ) throws InspireNetzException {


        // Create the APIResponseObject
        APIResponseObject retData = APIResponseObjectFactory.getJSONAPIResponseObject();

        String loyaltyId = authSessionUtils.getUserLoginId();

        log.info("BuyPointsForCustomer - Request Received# merchantNo = " +merchantNo + " : crbRewardCurreny = " +crbRewardCurrency + " : crbNumPoints "+crbNumPoints );
        log.info("BuyPointsForCustomer -  "+generalUtils.getLogTextForRequest());


        // Call the api for performing transfer points
        CustomerRewardBalance customerRewardBalance = customerRewardBalanceService.buyPoints(loyaltyId,merchantNo,crbRewardCurrency,crbNumPoints);

        // If not transferred, set as failed
        if ( null!= customerRewardBalance ) {

            // Set the status as failed
            retData.setStatus(APIResponseStatus.success);

        } else {

            // Set the status as success
            retData.setStatus(APIResponseStatus.failed);

        }




        // Set the list
        retData.setData(customerRewardBalance.getCrbRewardBalance());

        // Log the response
        log.info("buyPointForCustomer - "+generalUtils.getLogTextForResponse(retData));


        // Return the retData object
        return retData;
    }

    @RequestMapping(value = "/api/0.9/json/customer/compatible/rewardbalance", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public APIResponseObject getRewardBalanceCompatibleForCustomer(
            @RequestParam(value = "merchant_no",required = true) Long merchantNo,
            @RequestParam(value = "merchant_redemption_partner", defaultValue = "0") Long mrpRedeptionMerchant) throws InspireNetzException {
        // Create the APIResponseObject
        APIResponseObject retData = APIResponseObjectFactory.getJSONAPIResponseObject();

        String loyaltyId = authSessionUtils.getUserLoginId();
        // Log the Request
        log.info("getRewardBalanceCompatible - Request Received# loyalty_id = " +loyaltyId );
        log.info("getRewardBalanceCompatible -  "+generalUtils.getLogTextForRequest());


        // Get the list of CustomerRewardBalance
        List<CustomerRewardBalance> customerRewardBalanceList = new ArrayList<CustomerRewardBalance>();

        customerRewardBalanceList = customerRewardBalanceService.searchBalances(merchantNo,loyaltyId,0L);


        //Get the merchantRedemptionPartner
        MerchantRedemptionPartner merchantRedemptionPartner = merchantRedemptionPartnerService.findByMrpMerchantNoAndMrpRedemptionMerchant(merchantNo,mrpRedeptionMerchant);



        // If the list is empty, then we don't have any result
        if ( customerRewardBalanceList.isEmpty() ) {

            // Log the response
            log.info("getRewardBalanceCompatible - Response : No data for the request " );

            // Throw the exception
            throw new InspireNetzException(APIErrorCode.ERR_NO_DATA);


        } else {

            // Log the response
            log.info("getRewardBalanceCompatible - Response: " + retData.toString() );

            // Convert the data to resource
            List<CustomerRewardBalanceCompatibleResource> customerRewardBalanceCompatibleResourceList = customerRewardBalanceCompatibleAssembler.toResources(customerRewardBalanceList,merchantNo);

            //for setting mobile api format
            List CustomerRewardBalanceList =new ArrayList<HashMap>();


            Map map;

            for(CustomerRewardBalanceCompatibleResource customerRewardBalance :customerRewardBalanceCompatibleResourceList){

                 map =new HashMap<>();

                 map.put("rwd_name",customerRewardBalance.getRwd_name() ==null?"":customerRewardBalance.getRwd_name());

                 map.put("rwd_currency_id",customerRewardBalance.getRwd_id() ==null?0L:customerRewardBalance.getRwd_id());

                 map.put("rwd_balance",customerRewardBalance.getRwd_balance()==null?0.0:customerRewardBalance.getRwd_balance());

                 map.put("cashback_value",customerRewardBalance.getRwd_cashback_value() == null ? 0.0 : customerRewardBalance.getRwd_cashback_value());


                if(merchantRedemptionPartner != null ){


                    // check if the mrp reward currency and the customer reward curency matches
                    if(merchantRedemptionPartner.getMrpRewardCurrency().longValue() == customerRewardBalance.getRwd_id()){

                        map.put("pay_cashback_value",merchantRedemptionPartner.getMrpCashbackRatioDeno() == null ? 0.0 : merchantRedemptionPartner.getMrpCashbackRatioDeno());

                    }else {

                        // make pay_cashback_value as 0
                        map.put("pay_cashback_value",0);

                    }

                }




                 CustomerRewardBalanceList.add(map);

            }

            // Set the data
            retData.setData(CustomerRewardBalanceList);

            // Set the status as success
            retData.setStatus(APIResponseStatus.success);

        }

        //log response
        log.info( "getRewardBalanceCompatible- "+generalUtils.getLogTextForResponse(retData));

        // Return the retData object
        return retData;

    }


    @RequestMapping(value = "/api/0.9/json/customer/rewardbalance/adjustment", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public APIResponseObject makeRewardAdjustMents(
            @RequestParam(value = "adjLoyaltyId") String adjLoyaltyId,
            @RequestParam(value = "adjPoints") Double adjPoints,
            @RequestParam(value = "adjCurrencyId") Long adjCurrencyId,
            @RequestParam(value = "adjProgramNo") Long adjProgramNo,
            @RequestParam(value = "adjType") Integer adjType,
            @RequestParam(value = "adjReference") String reference,
            @RequestParam(value = "isTierAffected") boolean isTierAffected) throws InspireNetzException {


        // Create the APIResponseObject
        APIResponseObject retData = APIResponseObjectFactory.getJSONAPIResponseObject();

        Long merchantNo = authSessionUtils.getMerchantNo();

        log.info("makeRewardAdjustMents - Request Received# merchantNo = " +merchantNo + " : crbRewardCurreny = " +adjCurrencyId + " : loyaltyid "+adjLoyaltyId );
        log.info("makeRewardAdjustMents -  "+generalUtils.getLogTextForRequest());


        // Call the api for performing reward adjustment points
        CustomerRewardBalance customerRewardBalance = customerRewardBalanceService.doRewardAdjustment(merchantNo,adjLoyaltyId,adjType,adjCurrencyId,adjPoints,isTierAffected,adjProgramNo,reference);

        // If not transferred, set as failed
        if ( null!= customerRewardBalance ) {

            // Set the status as failed
            retData.setStatus(APIResponseStatus.success);

        } else {

            // Set the status as success
            retData.setStatus(APIResponseStatus.failed);

        }




        // Set the list
        retData.setData(customerRewardBalance.getCrbRewardBalance());

        // Log the response
        log.info("makeRewardAdjustMents -   "+generalUtils.getLogTextForResponse(retData));


        // Return the retData object
        return retData;
    }

    @RequestMapping(value = "/api/0.9/json/loyalty/rewardbalancesms", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public APIResponseObject sendRewardBalanceSMSCompatible(
            @RequestParam(value = "loyalty_id") String loyaltyId,
            @RequestParam(value = "rwd_currency_id",required = false,defaultValue = "0") Long rwdCurrencyId
    ) throws InspireNetzException {

        // Create the APIResponseObject
        APIResponseObject retData = APIResponseObjectFactory.getJSONAPIResponseObject();

        // Log the request
        log.info("sendRewardBalanceSMSCompatible - Request Received# loyalty_id = " +loyaltyId + " : rwd_currency_id = " +rwdCurrencyId );

        log.info("sendRewardBalanceSMSCompatible -  "+generalUtils.getLogTextForRequest());


        // Send the reward balance
        customerRewardBalanceService.sendRewardBalanceSMS(authSessionUtils.getMerchantNo(),loyaltyId,rwdCurrencyId);

        // Set the status as success
        retData.setStatus(APIResponseStatus.success);

        // Log the response
        log.info("sendRewardBalanceSMSCompatible - "+generalUtils.getLogTextForResponse(retData));

        // Return the retData object
        return retData;
    }

    @RequestMapping(value = "/api/0.9/json/portal/rewardbalance/{merchantNo}/{rwdCurrencyId}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public APIResponseObject getRewardBalanceForUser(
            @PathVariable(value = "merchantNo") Long merchantNo,
            @PathVariable(value = "rwdCurrencyId") long rwdCurrencyId
    ) throws InspireNetzException {

        // Create the APIResponseObject
        APIResponseObject retData = APIResponseObjectFactory.getJSONAPIResponseObject();

        log.info("getRewardBalanceForCustomerRequest - Request Received# merchantNo = " +merchantNo  );
        log.info("getRewardBalanceForCustomerRequest -  "+generalUtils.getLogTextForRequest());


        // Get the list of CustomerRewardBalance
        List<CustomerRewardBalance> customerRewardBalanceList = customerRewardBalanceService.GetUserRewardBalances(merchantNo,rwdCurrencyId);


        List<CustomerRewardBalanceResource> customerRewardBalanceResourceList = customerRewardBalanceAssembler.toResources(customerRewardBalanceList);

        // Set the list
        retData.setData(customerRewardBalanceResourceList);

        // Set the status as success
        retData.setStatus(APIResponseStatus.success);

        // Log the response
        log.info("getRewardBalance - "+generalUtils.getLogTextForResponse(retData));



        // Return the retData object
        return retData;
    }


    @RequestMapping(value = "/api/0.9/json/customer/rewardbalance/{merchantNo}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public APIResponseObject getRewardBalanceForUserGivenMerchant(
            @PathVariable(value = "merchantNo") Long merchantNo

    ) throws InspireNetzException {

        // Create the APIResponseObject
        APIResponseObject retData = APIResponseObjectFactory.getJSONAPIResponseObject();

        log.info("getRewardBalanceForUserGivenMerchant - Request Received# merchantNo = " +merchantNo  );
        log.info("getRewardBalanceForUserGivenMerchant -  "+generalUtils.getLogTextForRequest());


        // Get the list of CustomerRewardBalance
        List<CustomerRewardBalance> customerRewardBalanceList = customerRewardBalanceService.getUserRewardBalancesForUser(merchantNo);

        //get the reward balance
        List<CustomerRewardBalanceResource> customerRewardBalanceResourceList = customerRewardBalanceAssembler.toResources(customerRewardBalanceList);

        // Set the list
        retData.setData(customerRewardBalanceResourceList);

        // Set the status as success
        retData.setStatus(APIResponseStatus.success);

        // Log the response
        log.info("getRewardBalance - "+generalUtils.getLogTextForResponse(retData));



        // Return the retData object
        return retData;
    }

}
