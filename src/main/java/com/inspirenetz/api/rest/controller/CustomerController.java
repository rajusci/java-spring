package com.inspirenetz.api.rest.controller;


import com.google.common.io.CountingOutputStream;
import com.inspirenetz.api.core.dictionary.APIErrorCode;
import com.inspirenetz.api.core.dictionary.APIResponseStatus;
import com.inspirenetz.api.core.dictionary.CustomerStatus;
import com.inspirenetz.api.core.dictionary.UserRequest;
import com.inspirenetz.api.core.domain.Customer;
import com.inspirenetz.api.core.service.CustomerProfileService;
import com.inspirenetz.api.core.service.CustomerService;
import com.inspirenetz.api.core.service.TierService;
import com.inspirenetz.api.rest.assembler.CustomerAssembler;
import com.inspirenetz.api.rest.assembler.CustomerCompatibleAssembler;
import com.inspirenetz.api.rest.assembler.CustomerInfoAssembler;
import com.inspirenetz.api.rest.exception.InspireNetzException;
import com.inspirenetz.api.rest.resource.*;
import com.inspirenetz.api.util.APIResponseObject;
import com.inspirenetz.api.util.APIResponseObjectFactory;
import com.inspirenetz.api.util.AuthSessionUtils;
import com.inspirenetz.api.util.GeneralUtils;
import com.inspirenetz.api.util.integration.CustomerDataXMLParser;
import com.inspirenetz.api.util.integration.IntegrationUtils;
import org.dozer.Mapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.ws.rs.Path;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.util.List;
import java.util.Map;


/**
 * Created by sandheepgr on 16/2/14.
 */
@Controller
public class CustomerController {

    @Autowired
    private CustomerService customerService;

    @Autowired
    private CustomerAssembler customerAssembler;

    @Autowired
    private CustomerCompatibleAssembler customerCompatibleAssembler;

    @Autowired
    private CustomerProfileService customerProfileService;

    @Autowired
    private TierService tierService;

    @Autowired
    private AuthSessionUtils authSessionUtils;

    @Autowired
    private CustomerInfoAssembler customerInfoAssembler;

    @Autowired
    private Mapper mapper;

    @Autowired
    GeneralUtils generalUtils;

    @Autowired
    CustomerDataXMLParser customerDataXMLParser;


    // Create the logger instance
    private static Logger log = LoggerFactory.getLogger(CustomerController.class);


    @RequestMapping(value = "/api/0.9/json/merchant/customer", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public APIResponseObject saveCustomer(CustomerResource customerResource,CustomerProfileResource customerProfileResource) throws InspireNetzException {



        // Create the response object
        APIResponseObject retData = APIResponseObjectFactory.getJSONAPIResponseObject();

        // Log the Request
        log.info("saveCustomer - Request Received# Customer : "+customerResource + " - CustomerProfile : " + customerProfileResource) ;
        log.info("saveCustomer - "+generalUtils.getLogTextForRequest());


        // Call the saveData
        Customer customer = customerService.saveCustomerDetails(customerResource,customerProfileResource);

        // Set the status as success as otherwise for any error condition, we will
        // have the exception thrown from there
        retData.setStatus(APIResponseStatus.success);

        // Set the data to the customer number
        retData.setData(customer.getCusCustomerNo());



        // Log the response
        log.info("saveCustomer - "+generalUtils.getLogTextForResponse(retData));

        // Return the retData
        return retData;


    }



    @RequestMapping(value = "/api/0.9/json/merchant/customer/search/{searchField}/{query}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public APIResponseObject searchCustomers(
                                                @PathVariable(value = "searchField") String searchField,
                                                @PathVariable(value = "query") String query,
                                                Pageable pageable
                                            ) {

        // Create the APIResponseObject
        APIResponseObject retData = new APIResponseObject();

        // Log the Request
        log.info("searchCustomers - Request Received# searchField -" +searchField + " : query - "+query);
        log.info("searchCustomers - "+generalUtils.getLogTextForRequest());


        // Get the customer page
        Page<Customer> customerPage = customerService.searchCustomers(searchField,query,pageable);

        // convert to list
        List<CustomerResource> customerResourceList = customerAssembler.toResources(customerPage);

        // Set the pageable params to the retData
        retData.setPageableParams(customerPage);


        // Set the data
        retData.setData(customerResourceList);
        // Set the status to success
        retData.setStatus(APIResponseStatus.success);


        // Log the response
        log.info("searchCustomers - "+generalUtils.getLogTextForResponse(retData));

        // Return the customerList
        return retData;

    }



    @RequestMapping(value = "/api/0.9/json/merchant/customers", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public APIResponseObject listCustomers(Pageable pageable){


        // Log the Request
        log.info("listCustomers - Request Received# ");
        log.info("listCustomers - "+generalUtils.getLogTextForRequest());


        // Create the APIResponseObject
        APIResponseObject retData = APIResponseObjectFactory.getJSONAPIResponseObject();

        // Get the merchant number and store
        Long merchantNo = authSessionUtils.getMerchantNo();

        // Get the list of matching customers
        Page<Customer> customerList = customerService.findByCusMerchantNo(merchantNo,pageable);

        // Get the list of the customerResources
        List<CustomerResource> customerResourceList = customerAssembler.toResources(customerList);


        // Set the pageable params for the retData
        retData.setPageableParams(customerList);

        // Set the data
        retData.setData(customerResourceList);


        // Log the response
        log.info("listCustomers - "+generalUtils.getLogTextForResponse(retData));


        // Return the success object
        return retData;


    }


    @RequestMapping(value="/api/0.9/json/merchant/customer/{cusCustomerNo}",method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public APIResponseObject getCustomerInfo(@PathVariable(value="cusCustomerNo") Long cusCustomerNo) throws InspireNetzException {


        // Create the APIResponseObject
        APIResponseObject retData = new APIResponseObject();

        // Log the Request
        log.info("getCustomerInfo - Request Received# searchField -" + cusCustomerNo);
        log.info("getCustomerInfo - "+generalUtils.getLogTextForRequest());


        // Get the customer object
        Customer customer = customerService.getCustomerInfo(cusCustomerNo);

        // CustomerResource object
        CustomerResource customerResource = customerAssembler.toResource(customer,customer.getCustomerProfile(),customer.getTier());


        // Set the retData object
        retData.setStatus(APIResponseStatus.success);

        // Set the data
        retData.setData(customerResource);


        // Log the information
        log.info("getCustomerInfo - "+generalUtils.getLogTextForResponse(retData));

        // Return the retData object
        return retData;


    }


    @RequestMapping(value="/api/0.9/json/customer/info",method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public APIResponseObject getCustomerInfoForSession() throws InspireNetzException {


        // Create the APIResponseObject
        APIResponseObject retData = new APIResponseObject();

        // Log the Request
        log.info("getCustomerInfo - Request Received# ");
        log.info("getCustomerInfo - "+generalUtils.getLogTextForRequest());

        // Get the customer information
        Customer customer = customerService.getCustomerInfoForSession();

        // Get the CustomerInfoResource
        CustomerInfoResource customerInfoResource = customerInfoAssembler.toResource(customer,customer.getTier(),customer.getMerchant());

        // Set the retData object
        retData.setStatus(APIResponseStatus.success);

        // Set the data
        retData.setData(customerInfoResource);

        // Log the information
        log.info("getCustomerInfo - "+generalUtils.getLogTextForResponse(retData));

        // Return the retData object
        return retData;


    }


    @RequestMapping(value="/api/0.9/json/merchant/customer/loyalty/register/{loyaltyId}",method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public APIResponseObject registerForLoyalty(@PathVariable(value = "loyaltyId") String loyaltyId) throws InspireNetzException {


        // Create the APIResponseObject
        APIResponseObject retData = new APIResponseObject();

        // Log the Request
        log.info("registerForLoyalty - Request Received# LoyaltyId : " + loyaltyId);
        log.info("registerForLoyalty - Requested User  # Login Id : " + authSessionUtils.getUserLoginId() + " - User No: " + authSessionUtils.getUserNo() + " - IP Address : " + authSessionUtils.getUserIpAddress());

        // Get the merchantNo
        Long merchantNo = authSessionUtils.getMerchantNo();

        // Call the updateLoyaltyStatus with status as ACTIVE
        customerService.updateLoyaltyStatus(loyaltyId,merchantNo, CustomerStatus.ACTIVE);

        // Set the retData object
        retData.setStatus(APIResponseStatus.success);

        // Set the data
        retData.setData(loyaltyId);

        // Log the information
        log.info("registerForLoyalty - "+generalUtils.getLogTextForResponse(retData));

        // Return the retData object
        return retData;


    }


    @RequestMapping(value="/api/0.9/json/merchant/customer/loyalty/unregister/{loyaltyId}",method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public APIResponseObject unRegisterFromLoyalty(@PathVariable(value = "loyaltyId") String loyaltyId) throws InspireNetzException {


        // Create the APIResponseObject
        APIResponseObject retData = new APIResponseObject();

        // Log the Request
        log.info("registerForLoyalty - Request Received# LoyaltyId : " + loyaltyId);
        log.info("registerForLoyalty - Requested User  # Login Id : " + authSessionUtils.getUserLoginId() + " - User No: " + authSessionUtils.getUserNo() + " - IP Address : " + authSessionUtils.getUserIpAddress());

        // Get the merchantNo
        Long merchantNo = authSessionUtils.getMerchantNo();

        // Call the updateLoyaltyStatus with status as ACTIVE
        customerService.unRegisterLoyaltyCustomer(loyaltyId, merchantNo, CustomerStatus.INACTIVE,"", UserRequest.CUSTOMER);

        // Set the retData object
        retData.setStatus(APIResponseStatus.success);

        // Set the data
        retData.setData(loyaltyId);

        // Log the information
        log.info("registerForLoyalty - "+generalUtils.getLogTextForResponse(retData));

        // Return the retData object
        return retData;


    }


    @RequestMapping(value={"/api/0.9/json/merchant/customer/loyalty/transfer","/local/api/0.9/json/merchant/customer/loyalty/transfer"},method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public APIResponseObject transferAccount(@RequestParam(value = "srcLoyaltyId") String srcLoyaltyId,
                                             @RequestParam(value = "destLoyaltyId") String destLoyaltyId) throws InspireNetzException {


        // Create the APIResponseObject
        APIResponseObject retData = new APIResponseObject();

        // Log the Request
        log.info("transferAccount - Request Received# Src Loyalty Id : " + srcLoyaltyId + " - Dest loyalty id : " + destLoyaltyId);
        log.info("transferAccount - "+generalUtils.getLogTextForRequest());

        // Call the transferAccount
        customerService.transferAccounts(srcLoyaltyId, destLoyaltyId);


        // Set the retData object
        retData.setStatus(APIResponseStatus.success);

        // Set the data
        retData.setData(destLoyaltyId);


        // Log the information
        log.info("transferAccount - "+generalUtils.getLogTextForResponse(retData));

        // Return the retData object
        return retData;


    }

    @RequestMapping(value={"/api/0.9/json/customer/register/compatible"},method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public APIResponseObject registerCustomerCompatible(@RequestParam(value = "username") String loyaltyId,
                                                        @RequestParam(value = "firstname") String firstName,
                                                        @RequestParam(value = "lastname") String lastName ,
                                                        @RequestParam(value = "password") String password ,
                                                        @RequestParam(value = "reg_key") String regKey) throws InspireNetzException {


        // Create the APIResponseObject
        APIResponseObject retData = new APIResponseObject();

        // Log the Request
        log.info("registerCustomerCompatible - Request Received# Src Loyalty Id : " + loyaltyId + " - Dest loyalty id : " + loyaltyId +" FirstName: "+firstName + " LastName : "+ lastName);

        //check whehter the registration key is valid
        boolean isAuthenticated = customerService.isRegistrationKeyValid(loyaltyId,password,regKey);

        if(isAuthenticated){

            //call the customer registration method
            boolean isRegistered = customerService.registerCustomer(loyaltyId,password,firstName,lastName);

            //if registration is successful , set status to success
            if(isRegistered){

                retData.setStatus(APIResponseStatus.success);
            }

        } else {

            // Set the retData object
            retData.setStatus(APIResponseStatus.failed);

        }

        // Log the information
        log.info("registerCustomerCompatible - "+generalUtils.getLogTextForResponse(retData));

        // Return the retData object
        return retData;


    }
    @RequestMapping(value={"/api/0.9/json/customer/register"},method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public APIResponseObject registerCustomer(@RequestParam(value = "loyaltyId") String loyaltyId,
                                                        @RequestParam(value = "firstName") String firstName,
                                                        @RequestParam(value = "lastName",defaultValue = "") String lastName ,
                                                        @RequestParam(value = "password") String password)
                                                         throws InspireNetzException {


        // Create the APIResponseObject
        APIResponseObject retData = new APIResponseObject();

        // Log the Request
        log.info("registerCustomer - Request Received# Src Loyalty Id : " + loyaltyId + " - Dest loyalty id : " + loyaltyId +" FirstName: "+firstName + " LastName : "+ lastName);

        // Call the register cutsomer method
        boolean isRegistered = customerService.registerCustomer(loyaltyId, password ,firstName,lastName);

        //check whether the registration is success
        if(isRegistered){

            // Set the retData object status to success
            retData.setStatus(APIResponseStatus.success);

        } else {

            // Set the retData object
            retData.setStatus(APIResponseStatus.failed);

        }

        // Log the information
        log.info("registerCustomer - "+generalUtils.getLogTextForResponse(retData));

        // Return the retData object
        return retData;


    }

    @RequestMapping(value={"/api/0.9/json/customer/register/validate"},method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public APIResponseObject confirmCustomerRegister(@RequestParam(value = "loyaltyId") String loyaltyId,
                                                     @RequestParam(value = "otpCode") String otpCode,
                                                     @RequestParam(value = "merchantno", defaultValue = "0") Long merchantNo)
            throws InspireNetzException {


        // Create the APIResponseObject
        APIResponseObject retData = new APIResponseObject();

        // Log the Request
        log.info("confirmCustomerRegister - Request Received# Src Loyalty Id : " + loyaltyId + " - Dest loyalty id : " + loyaltyId +" OtpCOde: "+otpCode);

        // Call the register cutsomer method
        boolean isRegistered = customerService.confirmCustomerRegistration(loyaltyId, otpCode,merchantNo);

        //check whether the registration is success
        if(isRegistered){


            // Set the retData object status to success
            retData.setStatus(APIResponseStatus.success);

        } else {

            // Set the retData object
            retData.setStatus(APIResponseStatus.failed);

            //set error code
            retData.setErrorCode(APIErrorCode.ERR_INVALID_OTP);

        }

        // Log the information
        log.info("confirmCustomerRegister - "+generalUtils.getLogTextForResponse(retData));

        // Return the retData object
        return retData;


    }


    @RequestMapping(value={"/api/0.9/json/customer/notification/optout"},method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public APIResponseObject customerOptOutOfNotifications(@RequestParam(value = "loyaltyId") String loyaltyId)
            throws InspireNetzException {


        Long merchantNo = authSessionUtils.getMerchantNo();

        // Create the APIResponseObject
        APIResponseObject retData = new APIResponseObject();

        // Log the Request
        log.info("customerOptOutOfNotifications - Request Received# Src Loyalty Id : " + loyaltyId  );
        log.info("customerOptOutOfNotifications - "+generalUtils.getLogTextForRequest());

        // Call the register cutsomer method
        boolean isChanged = customerService.changeNotificationStatus(loyaltyId, merchantNo);

        //check whether the operation is success
        if(isChanged){

            // Set the retData object status to success
            retData.setStatus(APIResponseStatus.success);

        } else {

            // Set the retData object
            retData.setStatus(APIResponseStatus.failed);

            //set error code
            retData.setErrorCode(APIErrorCode.ERR_INVALID_OTP);

        }

        // Log the information
        log.info("customerOptOutOfNotifications - "+generalUtils.getLogTextForResponse(retData));

        // Return the retData object
        return retData;

    }


    @RequestMapping(value={"/api/0.9/json/customer/whitelist"},method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public APIResponseObject whiteListCustomer(@RequestParam(value = "loyaltyId") String loyaltyId)throws InspireNetzException {

        Long merchantNo = authSessionUtils.getMerchantNo();

        // Create the APIResponseObject
        APIResponseObject retData = new APIResponseObject();


        log.info("whiteListCustomer - Request Received#  Loyalty Id : " + loyaltyId );
        log.info("whiteListCustomer - "+generalUtils.getLogTextForRequest());

        // Call the register cutsomer method
        boolean isRegistered = customerService.whiteListRetailer(loyaltyId,merchantNo);

        //check whether the registration is success
        if(isRegistered){

            // Set the retData object status to success
            retData.setStatus(APIResponseStatus.success);

        } else {

            // Set the retData object
            retData.setStatus(APIResponseStatus.failed);

            //set error code
            retData.setErrorCode(APIErrorCode.ERR_INVALID_OTP);

        }

        // Log the information
        log.info("confirmCustomerRegister - "+generalUtils.getLogTextForResponse(retData));

        // Return the retData object
        return retData;


    }

    @RequestMapping(value={"/api/0.9/json/customer/memberships"},method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public APIResponseObject getMemberShips()throws InspireNetzException {

        Long merchantNo = 1L;

        log.info("getMemberShips - Request Received#  MerchantNo : " + merchantNo );
        log.info("getMemberShips - "+generalUtils.getLogTextForRequest());

        // Create the APIResponseObject
        APIResponseObject retData = new APIResponseObject();

        String loyaltyId = authSessionUtils.getUserLoginId();

        // Call the register cutsomer method
        List<Map<String,Object>> membershipData = customerService.getCustomerMemberShips(merchantNo, loyaltyId);

        retData.setData(membershipData);

        // Set the retData object status to success
        retData.setStatus(APIResponseStatus.success);

        // Log the information
        log.info("getMemberShips - "+generalUtils.getLogTextForResponse(retData));

        // Return the retData object
        return retData;


    }


    @RequestMapping(value="/api/0.9/json/customer/loyalty/unregister/{loyaltyId}",method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public APIResponseObject unRegisterLoyaltyFromCustomer(@PathVariable(value = "loyaltyId") String loyaltyId,@RequestParam(value="merchantNo",defaultValue = "1") Long merchantNo) throws InspireNetzException {

        // Create the APIResponse Object
        APIResponseObject retData = new APIResponseObject();

        // Log the Request
        log.info("registerForCustomer Loyalty - Request Received# LoyaltyId : " + loyaltyId);
        log.info("registerForCustomer Loyalty - "+generalUtils.getLogTextForRequest());

        // Check unregister from user or customer
        customerService.unRegisterLoyaltyCustomer(loyaltyId, merchantNo, CustomerStatus.INACTIVE,"", UserRequest.CUSTOMER);

        // Set the retData object
        retData.setStatus(APIResponseStatus.success);

        // Set the data
        retData.setData(loyaltyId);

        // Log the information
        log.info("registerForCustomer Loyalty  - "+generalUtils.getLogTextForResponse(retData));

        // Return the retData object
        return retData;


    }


    @RequestMapping(value="/api/0.9/json/merchant/customerprofile",method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public APIResponseObject getCustomerProfileCompatible(@RequestParam(value="customer_search_text") String searchText) throws InspireNetzException {


        // Create the APIResponseObject
        APIResponseObject retData = new APIResponseObject();

        // Log the Request
        log.info("getCustomerProfileCompatible - Request Received# searchField -" + searchText);

        log.info("getCustomerProfileCompatible - "+generalUtils.getLogTextForRequest());


        // Get the customer object
        Customer customer = customerService.getCustomerProfileCompatible(searchText, searchText, searchText);

        // CustomerResource object
        CustomerCompatibleResource customerCompatibleResource = customerCompatibleAssembler.toResource(customer,customer.getCustomerProfile());


        // Set the retData object
        retData.setStatus(APIResponseStatus.success);

        // Set the data
        retData.setData(customerCompatibleResource);


        // Log the Response
        log.info("getCustomerProfileCompatible - "+generalUtils.getLogTextForResponse(retData));

        // Return the retData object
        return retData;


    }

    @RequestMapping(value={"/api/0.9/json/merchant/customerregister"},method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public APIResponseObject saveCustomerCompatible(@RequestParam(value = "customer_no",defaultValue = "0") Long customerNo,
                                                        @RequestParam(value = "card_number",defaultValue = "") String loyaltyId,
                                                        @RequestParam(value = "customer_fname",defaultValue = "") String firstName,
                                                        @RequestParam(value = "customer_lname" ,required = false,defaultValue = "") String lastName ,
                                                        @RequestParam(value = "customer_email",required = false,defaultValue = "") String email ,
                                                        @RequestParam(value = "customer_mobile",required = false,defaultValue = "") String mobile ,
                                                        @RequestParam(value = "customer_address",required = false,defaultValue = "") String address ,
                                                        @RequestParam(value = "customer_city",required = false,defaultValue = "") String city ,
                                                        @RequestParam(value = "customer_pincode",required = false,defaultValue = "") String pincode,
                                                        @RequestParam(value = "customer_birthday",required = false,defaultValue = "") String birthday,
                                                        @RequestParam(value = "customer_anniversary",required = false,defaultValue = "") String anniversary,
                                                        @RequestParam(value = "referral_code",required = false,defaultValue = "") String referralCode) throws InspireNetzException {


        // Create the APIResponseObject
        APIResponseObject retData = new APIResponseObject();

        // Log the Request
        log.info("saveCustomerCompatible - Request Received# card_number : " + loyaltyId + " - customer_fname : " + firstName +" customer_lname: "+lastName + " customer_email : "+ email+ " customer_mobile : "+ mobile+ " customer_address : "+ address+ " customer_city : "+ city+ " customer_pincode : "+ pincode+ " customer_birthday : "+ birthday+ " customer_anniversary : "+ anniversary);

        log.info("saveCustomerCompatible - "+generalUtils.getLogTextForRequest());


        //call the save customer compatible method method
        boolean isRegistered = customerService.saveCustomerCompatible(customerNo, loyaltyId, firstName, lastName, email, mobile, address, city, pincode,birthday,anniversary,referralCode);

        //if registration is successful , set status to success
        if(isRegistered){

            // Set the retData object status as success
            retData.setStatus(APIResponseStatus.success);

        }else {

            // Set the retData object status as failed
            retData.setStatus(APIResponseStatus.failed);

        }

        // Log the information
        log.info("saveCustomerCompatible - "+generalUtils.getLogTextForResponse(retData));

        // Return the retData object
        return retData;


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

    @RequestMapping(value = "/api/0.9/xml/sku/customermaster", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE,consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    @ResponseBody
    public APIResponseObject readCustomerMasterXml(InputStream inputStream) throws InspireNetzException {

        // Create the APIResponseObject
        APIResponseObject retData = new APIResponseObject();

        // Log the Request
        log.info("readCustomerXml - "+generalUtils.getLogTextForRequest());

        //get merchantNumber
        Long merchantNo = authSessionUtils.getMerchantNo();

        // Get the location
        Long userLocation = authSessionUtils.getUserLocation()==null?0L:authSessionUtils.getUserLocation();

        try{

            //write input data to xml file
            String fileName = writeDataToXml(inputStream,merchantNo,userLocation,"customer");


            // Parse the xml
            customerDataXMLParser.CustomerDataXMLParser(fileName);

            // Set the data to be successful
            retData.setStatus(APIResponseStatus.success);


        } catch(NullPointerException e){

            // TODO throw!
            e.printStackTrace();

            // Set the data to be successful
            retData.setStatus(APIResponseStatus.failed);

            // throw
            throw new InspireNetzException(APIErrorCode.ERR_INVALID_INPUT);

        }
        catch (Exception e) {

            // TODO throw!
            e.printStackTrace();

            // Set the data to be successful
            retData.setStatus(APIResponseStatus.failed);

            // Log the file
            log.info("readCustomerXml - Error reading the stream");

            // throw
            throw new InspireNetzException(APIErrorCode.ERR_STREAM_ERROR);

        }

        // Log the response
        log.info("readCustomerXml - "+generalUtils.getLogTextForResponse(retData));

        // Return the retData object
        return retData;


    }

    @RequestMapping(value = "/api/0.9/json/merchant/customers/useractivation", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public APIResponseObject portalActivationForCustomer(@RequestParam(value="merchantNo",required = true) Long merchantNo )throws InspireNetzException{


        // Log the Request
        log.info("portalActivationForCustomer - Request Received# ");
        log.info("portalActivationForCustomer - "+generalUtils.getLogTextForRequest());


        // Create the APIResponseObject
        APIResponseObject retData = APIResponseObjectFactory.getJSONAPIResponseObject();


        // Get the list of matching customers
        customerService.portalActivationForCustomer(merchantNo);



        // Set the data
        retData.setStatus(APIResponseStatus.success);


        // Log the response
        log.info("listCustomers - "+generalUtils.getLogTextForResponse(retData));


        // Return the success object
        return retData;


    }

    @RequestMapping(value = "/api/0.9/json/customer/connectMerchant", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public APIResponseObject connectMerchant(@RequestParam(value="merchantNo",required = true) Long merchantNo ,@RequestParam(value="referralCode",required = false,defaultValue = "") String referralCode )throws InspireNetzException{

        // Log the Request
        log.info("connectMerchant - Request Received# Merchant No"+merchantNo);

        log.info("connectMerchant - "+generalUtils.getLogTextForRequest());

        // Create the APIResponseObject
        APIResponseObject retData = APIResponseObjectFactory.getJSONAPIResponseObject();

        Customer customer =customerService.connectMerchantThroughPortal(merchantNo,authSessionUtils.getUserNo(),referralCode);

        if(customer ==null){

            retData.setStatus(APIResponseStatus.failed);
        }else {

            // Set the data
            retData.setStatus(APIResponseStatus.success);
        }

        // Log the response
        log.info("connectMerchant - "+generalUtils.getLogTextForResponse(retData));


        // Return the success object
        return retData;


    }

    @RequestMapping( value = "/api/0.9/json/customer/changestatus/{cusLoyaltyId}", method = RequestMethod.POST , produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public APIResponseObject updateCustomerStatus( @PathVariable (value = "cusLoyaltyId") String cusLoyaltyId,
                                                   @RequestParam (value = "cusStatus") int cusStatus) throws InspireNetzException {

        // log the Request
        log.info("Customer status - Request Received: Customer Loyaltyd" + cusLoyaltyId + "customer Status :" + cusStatus);

        log.info("Customer status -  "+generalUtils.getLogTextForRequest());

        //create the APIResponseObject
        APIResponseObject retData = APIResponseObjectFactory.getJSONAPIResponseObject();

        // Get the merchant No and store
        Long merchantNo = authSessionUtils.getMerchantNo();

        // Call the update customer status
        boolean isUpdated = customerService.updateCustomerStatus(merchantNo,cusLoyaltyId,cusStatus);

        // if the isUpdated is true then set the data
        if( isUpdated){

            //set the retData to success
            retData.setStatus(APIResponseStatus.success);

        } else {

            // Set the status to failed
            retData.setStatus(APIResponseStatus.failed);

        }

        // Set the data to the status which was set
        retData.setData(cusStatus);

        // Log the response
        log.info("update customer status - "+generalUtils.getLogTextForResponse(retData));

        //return the success object
        return retData;


    }



    @RequestMapping( value = "/api/0.9/json/trusted/customer/memberships/{mobile}" , method = RequestMethod.GET,  produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public APIResponseObject getCustomerMemberships(@PathVariable(value = "mobile") String mobile) throws InspireNetzException {

        // log the Request
        log.info("getCustomerMemberships - Request Received: Customer Mobile" + mobile);
        log.info("getCustomerMemberships -  "+generalUtils.getLogTextForRequest());

        // Check the session and vaildate
        //
        // This need to be inside the controller
        if ( authSessionUtils.getCurrentUser() == null ||
                !authSessionUtils.getCurrentUser().getUserLoginId().equals("localipuser") ) {

            // Log the excception
            log.error("Current user is not authorized for the operation " + authSessionUtils.getCurrentUser());

            // Throw exception
            throw new InspireNetzException(APIErrorCode.ERR_NOT_AUTHORIZED);

        }


        // Create the APIResponseObject
        APIResponseObject response = APIResponseObjectFactory.getJSONAPIResponseObject();

        // Call the method
        List<MembershipResource> membershipResourceList =  customerService.getCustomerMemberships(mobile);

        // Set the data
        response.setData(membershipResourceList);

        // Log the response
        log.info("getCustomerMemberships - Response " + response);

        // Return the object
        return response;

    }


}
