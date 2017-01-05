package com.inspirenetz.api.rest.controller;

import com.inspirenetz.api.core.auth.AuthSession;
import com.inspirenetz.api.core.dictionary.*;
import com.inspirenetz.api.core.domain.Brand;
import com.inspirenetz.api.core.domain.User;
import com.inspirenetz.api.core.service.*;
import com.inspirenetz.api.rest.assembler.BrandAssembler;
import com.inspirenetz.api.rest.exception.InspireNetzException;
import com.inspirenetz.api.rest.resource.BrandResource;
import com.inspirenetz.api.util.APIResponseObject;
import com.inspirenetz.api.util.APIResponseObjectFactory;
import com.inspirenetz.api.util.GeneralUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by sandheepgr on 16/2/14.
 *
 */
@Controller
public class FileUploadController {

    @Autowired
    private FileUploadService fileUploadService;

    @Autowired
    private BulkuploadRawdataService bulkuploadRawdataService;

    @Autowired
    GeneralUtils generalUtils;

    // Create the logger instance
    private static Logger log = LoggerFactory.getLogger(FileUploadController.class);


    @RequestMapping(value = "/upload/image", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public ImageUploadResponse handleImageUpload(@RequestParam Map<String,String> params,@RequestParam(value = "file") MultipartFile  file) throws InspireNetzException {

        // Get the username
        String username = params.get("username");

        // Get the authentication
        String authentication = params.get("authentication");

        // Get the imageType
        Integer imageType = Integer.parseInt(params.get("imagetype"));

        // Get the filename
        String filename = params.get("filename");


        // Call the uploadImage of the fileUploadService
        ImageUploadResponse imageUploadResponse = fileUploadService.uploadImage(file,username,authentication,imageType,filename);


        // Return the imageUploadResponse
        return imageUploadResponse;

    }

    @RequestMapping(value = "/upload/merchant/vouchersorce/file", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public FileUploadResponse handleFileUpload(@RequestParam Map<String,String> params,@RequestParam(value = "file") MultipartFile  file) throws InspireNetzException {

        // Get the username
        String username = params.get("username");

        // Get the authentication
        String authentication = params.get("authentication");

        // Get the imageType
        Integer fileType = Integer.parseInt(params.get("fileType"));

        // Get the filename
        String filename = params.get("filename");


        // Call the uploadImage of the fileUploadService
        FileUploadResponse fileUploadResponse = fileUploadService.uploadFile(file,username,authentication,fileType,filename);


        // Return the imageUploadResponse
        return fileUploadResponse;

    }

    @RequestMapping(value = "/upload/merchant/bulkupload", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE )
    @ResponseBody
    public APIResponseObject bulkUploadFile(@RequestParam Map<String,String> params,@RequestParam(value = "file") MultipartFile  file) throws InspireNetzException {


        // Create the APIResponseObject
        APIResponseObject retData = APIResponseObjectFactory.getJSONAPIResponseObject();

        // Get the username
        String username = params.get("username");

        // Get the authentication
        String authentication = params.get("authentication");

        // Get the imageType
        Integer fileType = Integer.parseInt(params.get("fileType"));

        // Get the filename
        String filename = params.get("filename");


        // Call the uploadImage of the fileUploadService
        FileUploadResponse fileUploadResponse = fileUploadService.bulkUploadFile(file, username, authentication, fileType, filename);



        //process file upload controller
        Map<String,String> fileUploadHeader = bulkuploadRawdataService.getHeaderContent(fileUploadResponse.getStandardPath());

        retData.setStatus(APIResponseStatus.success);

        retData.setData(fileUploadHeader);

        // Return the success object
        return retData;

    }

}
