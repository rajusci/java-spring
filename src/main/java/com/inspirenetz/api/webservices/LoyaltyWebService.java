package com.inspirenetz.api.webservices;


import com.inspirenetz.api.core.auth.AuthSession;
import com.inspirenetz.api.core.dictionary.APIErrorCode;
import com.inspirenetz.api.core.dictionary.CustomerStatus;
import com.inspirenetz.api.core.dictionary.RequestChannel;
import com.inspirenetz.api.core.dictionary.RewardBalance;
import com.inspirenetz.api.core.domain.CustomerRewardActivity;
import com.inspirenetz.api.core.domain.CustomerRewardBalance;
import com.inspirenetz.api.core.service.CustomerRewardActivityService;
import com.inspirenetz.api.core.service.CustomerRewardBalanceService;
import com.inspirenetz.api.core.service.CustomerService;
import com.inspirenetz.api.rest.assembler.CustomerRewardBalanceAssembler;
import com.inspirenetz.api.rest.exception.InspireNetzException;
import com.inspirenetz.api.rest.resource.CustomerRewardBalanceResource;
import com.inspirenetz.api.util.AuthSessionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.context.support.SpringBeanAutowiringSupport;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebService;
import javax.jws.soap.SOAPBinding;
import java.util.List;

/**
 * Created by sandheepgr on 24/7/14.
 */
@WebService
@SOAPBinding(style = SOAPBinding.Style.RPC)
public class LoyaltyWebService extends SpringBeanAutowiringSupport {

    @Autowired
    private CustomerRewardBalanceService customerRewardBalanceService;

    @Autowired
    private CustomerRewardBalanceAssembler customerRewardBalanceAssembler;

    @Autowired
    private CustomerRewardActivityService customerRewardActivityService;

    @Autowired
    private CustomerService customerService;

    @Autowired
    private AuthSessionUtils authSessionUtils;

    private static Logger log = LoggerFactory.getLogger(LoyaltyWebService.class);


    @WebMethod
    public RewardBalance[] getRewardBalance(@WebParam(name="merchantNo")Long merchantNo, @WebParam(name="loyaltyId")String loyaltyId) {


        // Get the reward balance
        List<CustomerRewardBalance> customerRewardBalanceList = customerRewardBalanceService.searchBalances(merchantNo,loyaltyId,null);

        // Convert the list to Resource
        List<CustomerRewardBalanceResource> customerRewardBalanceResourceList = customerRewardBalanceAssembler.toResources(customerRewardBalanceList);


        // Get the list of RewardBalance
        RewardBalance rewardBalance[] = new RewardBalance[customerRewardBalanceResourceList.size()];


        for(int i=0;i<rewardBalance.length ; i++ ) {

            // Get the CustomeRewardBalance object
            CustomerRewardBalanceResource customerRewardBalanceResource = customerRewardBalanceResourceList.get(i);

            // Initialize the RewardBAlance object
            rewardBalance[i] =new RewardBalance();

            // Set the loyalyt id
            rewardBalance[i].setLoyaltyId(customerRewardBalanceResource.getCrbLoyaltyId());

            rewardBalance[i].setRwdBalance(customerRewardBalanceResource.getCrbRewardBalance());

            rewardBalance[i].setRwdCurrency(customerRewardBalanceResource.getCrbRewardCurrency());

            rewardBalance[i].setRwdCashbackValue(customerRewardBalanceResource.getRwdCashbackValue());

            rewardBalance[i].setRwdCurrencyName(customerRewardBalanceResource.getRwdCurrencyName());

        }


        // Return the reward blaance
        return rewardBalance;

    }



    @WebMethod
    public String registerForLoyalty(@WebParam(name="loyaltyId") String loyaltyId) {


        // Get the merchantNo of the merchant
        // Get the merchantNo
        Long merchantNo = authSessionUtils.getMerchantNo();

        try {

            // Call the updateLoyaltyStatus with status as ACTIVE
            customerService.updateLoyaltyStatus(loyaltyId,merchantNo, CustomerStatus.ACTIVE);

        } catch(InspireNetzException ex) {

            // Return the api error code
            return ex.getErrorCode().name();

        } catch (Exception ex) {

            // Return ERR_EXCEPTIon
            return APIErrorCode.ERR_EXCEPTION.name();

        }


        // Return success
        return "success";


        // POSSIBLE ERRORCODES
        // ERR_NO_DATA_FOUND - No customer found for the given loyalty id
        // ERR_OPERATION_FAILED - Unable to complete the operation
        // ERR_OPERATION_NOT_ALLOWED - Operation is not allowed ( number is not valid)

    }


    @WebMethod
    public String unregisterFromLoyalty(@WebParam(name="loyaltyId") String loyaltyId) {

        // Get the merchantNo of the merchant
        // Get the merchantNo
        Long merchantNo = authSessionUtils.getMerchantNo();

        try {

            // Call the updateLoyaltyStatus with status as ACTIVE
            customerService.updateLoyaltyStatus(loyaltyId,merchantNo, CustomerStatus.INACTIVE);

        } catch(InspireNetzException ex) {

            // Return the api error code
            return ex.getErrorCode().name();

        } catch (Exception ex) {

            // Return ERR_EXCEPTIon
            return APIErrorCode.ERR_EXCEPTION.name();

        }


        // Return success
        return "success";

    }


    @WebMethod
    public String transferPoints(
                                    @WebParam( name =  "srcLoyaltyId") String srcLoyaltyId,
                                    @WebParam( name =  "destLoyaltyId") String destLoyaltyId,
                                    @WebParam( name =  "srcCurrency") Long srcCurrency,
                                    @WebParam( name =  "destCurrency") Long destCurrency,
                                    @WebParam( name =  "rwdQty") Double rwdQty
                                ) {


        try {

            // Call the transfer points for the merchant user
            customerRewardBalanceService.transferPointsForMerchantUser(srcLoyaltyId, destLoyaltyId, srcCurrency, destCurrency, rwdQty);

        } catch(InspireNetzException ex) {

            // Return the api error code
            return ex.getErrorCode().name();

        } catch (Exception ex) {

            // Return ERR_EXCEPTIon
            return APIErrorCode.ERR_EXCEPTION.name();

        }


        // Return success
        return "success";


    }


    @WebMethod
    public String registerForEvent(
            @WebParam(name = "loyaltyId") String loyaltyId,
            @WebParam(name = "type") Integer type,
            @WebParam(name = "activityRef") String activityRef,
            @WebParam(name = "merchantNo") Long merchantNo
    )  {


        // Log the information
        log.info("LoyaltyWebService -> registerForEvent : loyaltyId - " + loyaltyId + " : type - " + type + " :  activityRef - " +activityRef +" merchantNo"+merchantNo);


        CustomerRewardActivity customerRewardActivity = null;

        try {

            // Call the customerActivityService
            customerRewardActivity = customerRewardActivityService.saveCustomerRewardActivityByLoyaltyId(loyaltyId,type,activityRef,merchantNo);

        } catch (InspireNetzException ex) {

            // Log the information
            log.info("LoyaltyWebService -> registerForEvent : " + ex);

            // Print the stack trace
            ex.printStackTrace();

        }catch (Exception e){

            // Log the information
            log.info("LoyaltyWebService -> registerForEvent : " + e);

            // Print the stack trace
            e.printStackTrace();
        }

        // Log the response
        log.info("LoyaltyWebService -> registerForEvent :  " + customerRewardActivity);

        // Check if the object is created and return the response
        if ( customerRewardActivity != null ){

            return "success";

        } else {

            return "failed";

        }

    }


}
