package com.inspirenetz.api.rest.controller;

import com.fasterxml.jackson.core.type.TypeReference;
import com.inspirenetz.api.core.dictionary.APIErrorCode;
import com.inspirenetz.api.core.dictionary.APIResponseStatus;
import com.inspirenetz.api.core.domain.MessageSpiel;
import com.inspirenetz.api.core.service.LinkRequestService;
import com.inspirenetz.api.core.service.MessageSpielService;
import com.inspirenetz.api.rest.assembler.LinkRequestAssembler;
import com.inspirenetz.api.rest.assembler.MessageSpielAssembler;
import com.inspirenetz.api.rest.exception.InspireNetzException;
import com.inspirenetz.api.rest.resource.MessageSpielResource;
import com.inspirenetz.api.util.APIResponseObject;
import com.inspirenetz.api.util.APIResponseObjectFactory;
import com.inspirenetz.api.util.AuthSessionUtils;
import com.inspirenetz.api.util.GeneralUtils;
import org.dozer.Mapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by ameenci on 9/9/14.
 */
@Controller
public class MessageSpielController {


    // Create the logger instance
    private static Logger log = LoggerFactory.getLogger(MessageSpielController.class);

    @Autowired
    private MessageSpielService messageSpielService;


    @Autowired
    private MessageSpielAssembler messageSpielAssembler;

    @Autowired
    AuthSessionUtils authSessionUtils;

    @Autowired
    private Mapper mapper;

    @Autowired
    GeneralUtils generalUtils;



    @RequestMapping(value = "/api/0.9/json/admin/messagespiel", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE,consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public APIResponseObject saveMessageSpiel(@RequestBody Map<String,Object> params) throws InspireNetzException {


        // Create the APIResponseObject
        APIResponseObject retData = APIResponseObjectFactory.getJSONAPIResponseObject();

        // Log the Request
        log.info("saveMessageSpiel - Request Received# "+params.toString());

        log.info("saveMessageSpiel - "+generalUtils.getLogTextForRequest());

        // convert the Messsage spiel  to object
        MessageSpiel messageSpiel = mapper.map(params,MessageSpiel.class);

        // save the messageSpiel object and get the result
        messageSpiel = messageSpielService.validateAndSaveMessageSpiel(messageSpiel);

        // Get the messageSpiel id
        retData.setData(messageSpiel.getMsiId());

        // Set the status to succes
        retData.setStatus(APIResponseStatus.success);

        // Log the response
        log.info("saveMessageSpiel -  " + generalUtils.getLogTextForResponse(retData));

        // Return the APIResponseobject
        return retData;


    }

    @RequestMapping(value = "/api/0.9/json/admin/messagespiel/{filter}/{query}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public APIResponseObject listMessageSpiel(@PathVariable(value ="filter") String filter,@PathVariable("query") String query,
                                                Pageable pageable){


        // Log the Request
        log.info("listMessageSpiel - Request Received# filter and query "+ filter+":and:"+query);
        log.info("listMessageSpiel - "+generalUtils.getLogTextForRequest());


        // Create the APIResponseObject
        APIResponseObject retData = APIResponseObjectFactory.getJSONAPIResponseObject();


        // Variable holding the MessageSpiel
        List<MessageSpielResource> messageSpielResourceList = new ArrayList<>(0);

        Page<MessageSpiel> messageSpielPage=messageSpielService.searchMessageSpiel(filter,query ,pageable);

        messageSpielResourceList=messageSpielAssembler.toResources(messageSpielPage);

        // Set the data
        retData.setData(messageSpielResourceList);

        // Log the response
        log.info("listMessageSpiel-   " + generalUtils.getLogTextForResponse(retData));


        // Return the success object
        return retData;


    }

    // find by msiId

    @RequestMapping(value = "/api/0.9/json/admin/messagespiel/{msiId}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public APIResponseObject getMessageSpielByMsId(@PathVariable(value ="msiId") Long msiId){

        // Log the Request
        log.info("getMessageSpielByMsId - Request Received# ::listMessageSpiel1 MsiId="+ msiId);
        log.info("getMessageSpielByMsId - "+generalUtils.getLogTextForRequest());


        // Create the APIResponseObject
        APIResponseObject retData = APIResponseObjectFactory.getJSONAPIResponseObject();


        // Variable holding the MessageSpiel
        MessageSpiel messageSpiel=messageSpielService.findByMsiId(msiId);

        // Convert the message spiel to messageSpielResource
        MessageSpielResource messageSpielResource = messageSpielAssembler.toResource(messageSpiel);

        // Set the retData to success
        retData.setStatus(APIResponseStatus.success);

        // Set the data to the messageSpielResource object
        retData.setData(messageSpielResource);

        // Log the response
        log.info("getMessageSpielByMsId -   " + generalUtils.getLogTextForResponse(retData));


        // Return the success object
        return retData;


    }


    @RequestMapping(value = "/api/0.9/json/admin/messagespiel/delete/{msiId}", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public APIResponseObject deleteMessageSpiel(@PathVariable(value ="msiId") Long msiId) throws InspireNetzException {

        // Log the Request
        log.info("deleteMessageSpiel - Request Received# ::deleteMessageSpiel MsiId="+ msiId);
        log.info("deleteMessageSpiel - "+generalUtils.getLogTextForRequest());


        // Create the APIResponseObject
        APIResponseObject retData = APIResponseObjectFactory.getJSONAPIResponseObject();

        // for deleting message spiel
        messageSpielService.validateAndDeleteMessageSpiel(msiId);

        // Set the retData to success
        retData.setStatus(APIResponseStatus.success);

        retData.setData(msiId);

        // Log the response
        log.info("deleteMessageSpiel -   " + generalUtils.getLogTextForResponse(retData));


        // Return the success object
        return retData;


    }


}
