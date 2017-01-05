package com.inspirenetz.api.rest.controller;

import com.inspirenetz.api.core.auth.AuthSession;
import com.inspirenetz.api.core.auth.AuthUser;
import com.inspirenetz.api.core.dictionary.APIResponseStatus;
import com.inspirenetz.api.core.dictionary.MerchantPartnerTransactionSearchType;
import com.inspirenetz.api.core.domain.MerchantPartnerTransaction;
import com.inspirenetz.api.core.domain.User;
import com.inspirenetz.api.core.service.MerchantPartnerTransactionService;
import com.inspirenetz.api.core.service.UserService;
import com.inspirenetz.api.rest.assembler.MerchantPartnerTransactionAssembler;
import com.inspirenetz.api.rest.resource.MerchantPartnerTransactionResource;
import com.inspirenetz.api.util.APIResponseObject;
import com.inspirenetz.api.util.APIResponseObjectFactory;
import com.inspirenetz.api.util.AuthSessionUtils;
import com.sun.mail.imap.protocol.ENVELOPE;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.sql.Date;
import java.util.List;


/**
 * Created by abhi on 13/7/16.
 */
@Controller
public class MerchantPartnerTransactionController {

    private static Logger log = LoggerFactory.getLogger(MerchantPartnerTransactionController.class);

    @Autowired
    private MerchantPartnerTransactionService merchantPartnerTransactionService;

    @Autowired
    private MerchantPartnerTransactionAssembler merchantPartnerTransactionAssembler;


    @Autowired
    AuthSessionUtils authSessionUtils;

    @Autowired
    UserService userService;


    @RequestMapping(value = "/api/0.9/json/merchant/partner/transactions/{productno}/{merchantno}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public APIResponseObject listMerchantPartnerTransactionsPartnerPortal(  @PathVariable(value ="productno") Long mptProductNo,
                                                    @PathVariable(value ="merchantno") Long mptMerchantNo,
                                                    @RequestParam(value="startDate",required = false) Date startDate,
                                                    @RequestParam(value="endDate",required = false) Date endDate,
                                                    Pageable pageable){


        // Log the Request
        log.info("listMerchantPartnerTransactions - Request Received# productno :"+ mptProductNo +"merchantno : " +mptMerchantNo);
        log.info("listMerchantPartnerTransactions - Requested User  # Login Id : "+ authSessionUtils.getUserLoginId() + " - User No: "+authSessionUtils.getUserNo() + " - IP Address : " + authSessionUtils.getUserIpAddress());


        // Create the APIResponseObject
        APIResponseObject retData = APIResponseObjectFactory.getJSONAPIResponseObject();

        // Get the current user
        AuthUser user=authSessionUtils.getCurrentUser();

        // Get partner no
        Long mptPartnerNo = user.getThirdPartyVendorNo();

        // Variable holding the cardTransaction
        List<MerchantPartnerTransactionResource> merchantPartnerTransactionResourceList = new ArrayList<>(0);

        //get the transaction list
        Page<MerchantPartnerTransaction> merchantPartnerTransactionPage = merchantPartnerTransactionService.getMerchantPartnerTransactionsForPartner(mptProductNo, mptMerchantNo, mptPartnerNo, startDate, endDate, pageable);

        // Convert to Resource
        merchantPartnerTransactionResourceList = merchantPartnerTransactionAssembler.toResources(merchantPartnerTransactionPage);

        // Set the pageable params for the retData
        retData.setPageableParams(merchantPartnerTransactionPage);

        // Set the data
        retData.setData(merchantPartnerTransactionResourceList);

        //Set the status
        retData.setStatus(APIResponseStatus.success);

        // Log the response
        log.info("listMerchantPartnerTransactions - Response : " + retData.toString());


        // Return the success object
        return retData;


    }

    @RequestMapping(value = "/api/0.9/json/merchant/merchantpartnertransactions/list/{productno}/{partnerno}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public APIResponseObject listMerchantPartnerTransactionsMerchantPortal(  @PathVariable(value ="productno") Long mptProductNo,
                                                                @PathVariable(value ="partnerno") Long mptPartnerNo,
                                                                @RequestParam(value="startDate",required = false) Date startDate,
                                                                @RequestParam(value="endDate",required = false) Date endDate,
                                                                Pageable pageable){


        // Log the Request
        log.info("listMerchantPartnerTransactionsMerchantPortal - Request Received# productno :"+ mptProductNo +"partnerno : " +mptPartnerNo);
        log.info("listMerchantPartnerTransactionsMerchantPortal - Requested User  # Login Id : "+ authSessionUtils.getUserLoginId() + " - User No: "+authSessionUtils.getUserNo() + " - IP Address : " + authSessionUtils.getUserIpAddress());


        // Create the APIResponseObject
        APIResponseObject retData = APIResponseObjectFactory.getJSONAPIResponseObject();

        // Get the user details
        Long mptMerchantNo = authSessionUtils.getMerchantNo();

        // Variable holding the cardTransaction
        List<MerchantPartnerTransactionResource> merchantPartnerTransactionResourceList = new ArrayList<>(0);

        //get the transaction list
        Page<MerchantPartnerTransaction> merchantPartnerTransactionPage = merchantPartnerTransactionService.getMerchantPartnerTransactionsForMerchant(mptProductNo, mptMerchantNo, mptPartnerNo, startDate, endDate, pageable);

        // Convert to Resource
        merchantPartnerTransactionResourceList = merchantPartnerTransactionAssembler.toResources(merchantPartnerTransactionPage);

        // Set the pageable params for the retData
        retData.setPageableParams(merchantPartnerTransactionPage);

        // Set the data
        retData.setData(merchantPartnerTransactionResourceList);

        //Set the status
        retData.setStatus(APIResponseStatus.success);

        // Log the response
        log.info("listMerchantPartnerTransactionsMerchantPortal - Response : " + retData.toString());


        // Return the success object
        return retData;


    }


}
