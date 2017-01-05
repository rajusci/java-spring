package com.inspirenetz.api.rest.controller;

import com.inspirenetz.api.core.dictionary.APIResponseStatus;
import com.inspirenetz.api.core.domain.Attribute;
import com.inspirenetz.api.core.service.AttributeService;
import com.inspirenetz.api.rest.assembler.AttributeAssembler;
import com.inspirenetz.api.rest.exception.InspireNetzException;
import com.inspirenetz.api.rest.resource.AttributeResource;
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
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.w3c.dom.Attr;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ameenci on 16/9/14.
 */
@Controller
public class AttributeController {


    // Create the logger instance
    private static Logger log = LoggerFactory.getLogger(AttributeController.class);

    @Autowired
    private AttributeService attributeService;

    @Autowired
    private AttributeAssembler attributeAssembler;

    @Autowired
    AuthSessionUtils authSessionUtils;

    @Autowired
    GeneralUtils generalUtils;

    @Autowired
    private Mapper mapper;

    @RequestMapping(value = "/api/0.9/json/merchantadmin/attributeextension", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE,consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    @ResponseBody
    public APIResponseObject saveAttributeExtension(AttributeResource attributeResource) throws InspireNetzException {


        // Create the APIResponseObject
        APIResponseObject retData = APIResponseObjectFactory.getJSONAPIResponseObject();

        // Log the Request
        log.info("save Attribute extension - Request Received# "+attributeResource.toString());
        log.info("save Attribute extension l - "+generalUtils.getLogTextForRequest());




        // convert the Messsage spiel  to object
        Attribute attribute = mapper.map(attributeResource,Attribute.class);

        // save the Attribute extension object and get the result
        attribute = attributeService.validateAndSaveAttribute(attribute);

        // Get the Attribute extension id
        retData.setData(attribute.getAtrId());

        // Set the status to succes
        retData.setStatus(APIResponseStatus.success);

        // Log the response
        log.info("saveAttribute extension -"+generalUtils.getLogTextForResponse(retData));

        // Return the APIResponseobject
        return retData;


    }


    @RequestMapping(value = "/api/0.9/json/admin/attribute/{atrId}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public APIResponseObject findByAttributeExtension(@PathVariable(value ="atrId") Long atrId){

        // Log the Request
        log.info("Attributeextension - Request Received#  " +atrId);
        log.info("Attributeextension - "+generalUtils.getLogTextForRequest());


        // Create the APIResponseObject
        APIResponseObject retData = APIResponseObjectFactory.getJSONAPIResponseObject();


        // Variable holding the Attribute



        Attribute attribute=attributeService.findByAtrId(atrId);

        AttributeResource attributeResource=attributeAssembler.toResource(attribute);

        // Set the retData to success
        retData.setStatus(APIResponseStatus.success);

        // Set the data to the AttributeResource object
        retData.setData(attributeResource);

        // Log the response
        log.info("Attribute - - "+generalUtils.getLogTextForResponse(retData));


        // Return the success object
        return retData;


    }

    @RequestMapping(value = "/api/0.9/json/admin/attributeextension/{filter}/{query}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public APIResponseObject listAttributeExtension(@PathVariable(value ="filter") String filter,@PathVariable("query") String query,
                                              Pageable pageable){


        // Log the Request
        log.info("listAttributeExtension - Request Received# filter and query "+ filter+":and:"+query);
        log.info("listAttributeExtension - "+generalUtils.getLogTextForRequest());


        // Create the APIResponseObject
        APIResponseObject retData = APIResponseObjectFactory.getJSONAPIResponseObject();


        // Variable holding the Attribute
        List<AttributeResource> attributeResourceList = new ArrayList<>(0);

        Page<Attribute> attributePage=attributeService.findAttributeNameLike(filter,query ,pageable);

        attributeResourceList=attributeAssembler.toResources(attributePage);

        // Set the data
        retData.setData(attributeResourceList);

        // Log the response
        log.info("Attribute - - "+generalUtils.getLogTextForResponse(retData));


        // Return the success object
        return retData;


    }


}
