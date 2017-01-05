package com.inspirenetz.api.webservices;

import com.inspirenetz.api.core.auth.AuthSession;
import com.inspirenetz.api.core.domain.SegmentMember;
import com.inspirenetz.api.core.service.CustomerService;
import com.inspirenetz.api.core.service.SegmentMemberService;
import com.inspirenetz.api.rest.exception.InspireNetzException;
import com.inspirenetz.api.util.AuthSessionUtils;
import com.inspirenetz.api.util.GeneralUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.context.support.SpringBeanAutowiringSupport;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebService;
import javax.jws.soap.SOAPBinding;

/**
 * Created by sandheepgr on 16/9/14.
 */
@WebService
@SOAPBinding(style = SOAPBinding.Style.RPC)
public class CustomerWebService extends SpringBeanAutowiringSupport {

    @Autowired
    public CustomerService customerService;

    @Autowired
    SegmentMemberService segmentMemberService;

    @Autowired
    GeneralUtils generalUtils;


    @Autowired
    public AuthSessionUtils authSessionUtils;



    @WebMethod
    public String transferAccount(
                                    @WebParam ( name = "srcLoyaltyId") String srcLoyaltyId,
                                    @WebParam ( name = "destLoyaltyId") String destLoyaltyId
                                  ) {

        // Get the merchantNo
        Long merchantNo = authSessionUtils.getMerchantNo();

        try {

            // call the transfer accounts
            customerService.transferAccounts(srcLoyaltyId,destLoyaltyId);

        } catch(InspireNetzException ex) {

            // On error , return the error code
            return ex.getErrorCode().name();

        }


        // Return success
        return "SUCCESS";

    }

    @WebMethod
    public String assignCustomerToSegment(
            @WebParam ( name = "loyaltyId") String loyaltyId,
            @WebParam ( name = "segmentName") String segmentName
    ) {

        // Get the merchantNo
        Long merchantNo = generalUtils.getDefaultMerchantNo();

        try {

            // call the transfer accounts
            segmentMemberService.assignCustomerToSegment(loyaltyId, segmentName,merchantNo);

        } catch(InspireNetzException ex) {

            // On error , return the error code
            return ex.getErrorCode().name();

        }


        // Return success
        return "SUCCESS";

    }
}
