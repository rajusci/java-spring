package com.inspirenetz.api.webservices;

import com.inspirenetz.api.core.dictionary.APIResponseStatus;
import com.inspirenetz.api.core.service.CustomerService;
import com.inspirenetz.api.core.service.IntegrationService;
import com.inspirenetz.api.rest.exception.InspireNetzException;
import com.inspirenetz.api.util.APIResponseObject;
import com.inspirenetz.api.util.AuthSessionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.context.support.SpringBeanAutowiringSupport;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebService;
import javax.jws.soap.SOAPBinding;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by sandheepgr on 16/9/14.
 */
@WebService
@SOAPBinding(style = SOAPBinding.Style.RPC)
public class MessagingWebService extends SpringBeanAutowiringSupport {

    @Autowired
    private IntegrationService integrationService;


    @Value("${integration.messaging.sendsms}")
    private String sendSMSUrl;


    @WebMethod
    public String sendSMS(
                                    @WebParam ( name = "mobile") String mobile,
                                    @WebParam ( name = "message") String message
                                  ) {



        // Set the params
        Map<String,String> postParams = new HashMap<>(0);

        // Set the mobile
        postParams.put("mobile",mobile);

        // Set the message
        postParams.put("message",message);

        // call the rest method
        APIResponseObject responseObject = integrationService.placeRestPostAPICall(sendSMSUrl, postParams);

        // Check the response object
        if ( responseObject == null || responseObject.getStatus().equals(APIResponseStatus.failed.name()) ) {

            return "FAILED";

        }

        // Return success
        return "SUCCESS";

    }
}
