package com.inspirenetz.api.core.service.impl;

import com.inspirenetz.api.core.dictionary.*;
import com.inspirenetz.api.core.domain.*;
import com.inspirenetz.api.core.service.FileUploadService;
import com.inspirenetz.api.core.service.ImageService;
import com.inspirenetz.api.core.service.UserService;
import com.inspirenetz.api.rest.exception.InspireNetzException;
import com.inspirenetz.api.util.AuthSessionUtils;
import org.apache.commons.io.FilenameUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.*;
import java.io.File;
import java.util.Date;
import java.util.Properties;

/**
 * Created by sandheepgr on 28/3/14.
 */
@Service
public class FileUploadServiceImpl implements FileUploadService {


    // Create the logger
    private static Logger log = LoggerFactory.getLogger(FileUploadServiceImpl.class);

    @Autowired
    private UserService userService;

    @Autowired
    private ImageService imageService;

    @Autowired
    AuthSessionUtils authSessionUtils;
    // Constructor
    public FileUploadServiceImpl() {

    }


    @Override
    public ImageUploadResponse uploadImage(MultipartFile file, String username, String authentication, Integer imageType, String filename) throws InspireNetzException {

        //check the file validity
        checkFileValidity(file, username, authentication, imageType, filename);

        // Get the extension
        String extension = getExtension(filename);

        // Save the image
        Image image = new Image();

        // Set the fields
        image.setImgInUseIndicator(IndicatorStatus.YES);

        // Set the image extension
        image.setImgImageExt(extension);

        // Set the imagetype
        image.setImgImageType(imageType);

        // Save the image
        image = imageService.saveImage(image);

        // Check if the image has been saved
        if ( image.getImgImageId() == null ) {

            // Log the information
            log.info("handleFileUpload -> Image object was not saved");

            // throw exception
            throw new InspireNetzException(APIErrorCode.ERR_OPERATION_FAILED);

        }




        // File to save the temp file
        File tmpFile = getTempFile(filename);

        // Create the ImageUploadResponse object
        ImageUploadResponse imageUploadResponse = new ImageUploadResponse();

        // Set the response status as failed
        imageUploadResponse.setStatus(APIResponseStatus.failed.toString());


        // Get the bytes
        try {

            // Read the bytes
            byte[] bytes = file.getBytes();

            // Save the stream
            BufferedOutputStream stream = new BufferedOutputStream(new FileOutputStream(tmpFile));

            // Write the bytes to the stream
            stream.write(bytes);

            // Close the stream
            stream.close();


        } catch (IOException e) {

            // Print the stack trace
            e.printStackTrace();

            // Return response
            return imageUploadResponse;

        }



        // Get the buffered image
        try {

            // Get the original buffered image
            BufferedImage orgImage = ImageIO.read(tmpFile);


            // Save the standarImage
            imageService.saveImageByType(orgImage,image,ImagePathType.STANDARD);

            // Save the mobileImage
            imageService.saveImageByType(orgImage,image,ImagePathType.MOBILE);

            // Save the thumbnail image
            imageService.saveImageByType(orgImage,image,ImagePathType.THUMBNAIL);


        } catch (IOException e) {

            e.printStackTrace();

            return imageUploadResponse;
        }


        // Set the API response status
        imageUploadResponse.setStatus(APIResponseStatus.success.toString());

        // set the image id
        imageUploadResponse.setImgId(image.getImgImageId());

        // Set the standard path
        imageUploadResponse.setStandardPath(imageService.getPathForImage(image,ImagePathType.STANDARD));

        // Set the mobile path
        imageUploadResponse.setMobilePath(imageService.getPathForImage(image,ImagePathType.MOBILE));

        // set the thumbnail path
        imageUploadResponse.setThumbnailPath(imageService.getPathForImage(image,ImagePathType.THUMBNAIL));


        // Return response
        return imageUploadResponse;


    }

    private void checkFileValidity(MultipartFile file, String username, String authentication, Integer imageType, String filename) throws InspireNetzException {
        // If the username/authentication is null, then return invalid input
        if ( username == null || username.equals("") || authentication == null || authentication.equals("") || imageType == null || imageType == 0 || filename == null || filename.equals("") ) {

            // Log the information
            log.info("handleFileUpload -> Required parameter username/authentication/imagetype/filename not found");

            // Throw the exception
            throw new InspireNetzException(APIErrorCode.ERR_INVALID_INPUT);

        }


        // Get the user information
        User user = userService.authenticateUser(username,authentication);

        // If the user is null, then show message
        if ( user == null ) {

            // Log the information
            log.info("handleFileUpload -> Unable to authenticate the user");

            // Throw exception
            throw new InspireNetzException(APIErrorCode.ERR_NO_AUTHENTICATION);

        }


        // Check if the file is empty
        if ( file.isEmpty() ) {

            // Log the information
            log.info("handleFileUpload -> You have not selected a file");

            // Throw the error
            throw new InspireNetzException(APIErrorCode.ERR_OPERATION_NOT_ALLOWED);

        }
    }

    @Override
    public FileUploadResponse uploadFile(MultipartFile file, String username, String authentication, Integer fileType, String filename) throws InspireNetzException {

        //check the file validity
        checkFileValidity(file, username, authentication, fileType, filename);

        // Get the extension
        String extension = getExtension(filename);

        //get the file upload path
        String uploadRoot = getFileUploadPathForType(FileUploadPath.FILE_UPLOAD_ROOT);

        //get the user details
        User user = userService.findByUsrLoginId(username);

        String filePath = "/vouchersource/"+user.getUsrLocation()+"/"+filename;

        // File to save the csv
        File csvFile = createFile(filename,username,uploadRoot+filePath);

        // Create the ImageUploadResponse object
        FileUploadResponse fileUploadResponse = new FileUploadResponse();

        // Set the response status as failed
        fileUploadResponse.setStatus(APIResponseStatus.failed.toString());


        // Get the bytes
        try {

            // Read the bytes
            byte[] bytes = file.getBytes();

            // Save the stream
            BufferedOutputStream stream = new BufferedOutputStream(new FileOutputStream(csvFile));

            // Write the bytes to the stream
            stream.write(bytes);

            // Close the stream
            stream.close();


        } catch (IOException e) {

            // Print the stack trace
            e.printStackTrace();

            // Return response
            return fileUploadResponse;

        }


        //set the file path
        fileUploadResponse.setStandardPath(filePath);

        // Return response
        return fileUploadResponse;
    }

    @Override
    public FileUploadResponse bulkUploadFile(MultipartFile file, String username, String authentication, Integer fileType, String filename) throws InspireNetzException {

        //check the file validity
        checkFileValidity(file, username, authentication, fileType, filename);

        // Get the extension
        String extension = getExtension(filename);

        //get the file upload path
        String uploadRoot = getFileUploadPathForType(FileUploadPath.FILE_UPLOAD_ROOT);

        //file path information
        String filePath = "/bulkupload/"+Long.toString(new Date().getTime()).toString()+""+filename;

        // create directory
        File  fileDirectory= createFile(filename,username,uploadRoot+filePath);

        // Create the ImageUploadResponse object
        FileUploadResponse fileUploadResponse = new FileUploadResponse();

        // Set the response status as failed
        fileUploadResponse.setStatus(APIResponseStatus.failed.toString());


        // Get the bytes
        try {

            // Read the bytes
            byte[] bytes = file.getBytes();

            // Save the stream
            BufferedOutputStream stream = new BufferedOutputStream(new FileOutputStream(fileDirectory));

            // Write the bytes to the stream
            stream.write(bytes);

            // Close the stream
            stream.close();


        } catch (IOException e) {

            // Print the stack trace
            e.printStackTrace();

            // Return response
            return fileUploadResponse;

        }


        //set the file path
        fileUploadResponse.setStandardPath(filePath);

        // Return response
        return fileUploadResponse;
    }


    private File createFile(String filename,String username,String filePath) {

        // Create the temp folder
        File file = new File(filePath);

        // Check if the file exists
        if ( !file.getParentFile().exists() ){

            // Create the directory
            file.getParentFile().mkdirs();

        }


        // Return the file
        return file;
    }

    @Override
    public File getTempFile(String name) {

        // Get the uploadRoot
        String uploadRoot = getFileUploadPathForType(FileUploadPath.UPLOAD_ROOT);

        // Create the temp folder
        File file = new File(uploadRoot+"/tmp/"+name.hashCode() +"-" +Long.toString(new Date().getTime()));

        // Check if the file exists
        if ( !file.getParentFile().exists() ){

            // Create the directory
            file.getParentFile().mkdirs();

        }


        // Return the file
        return file;

    }

    @Override
    public String getExtension(String filename) {

        // Create a File object
        String extension = FilenameUtils.getExtension(filename);

        // Return the extension
        return extension;

    }

    @Override
    public String getFileUploadPathForType(Integer uploadType) {

        // Open the resource
        Resource resource = new ClassPathResource("inconfig.properties");

        // read the properties
        Properties props = new Properties();


        try {

            // Load the resource
            props.load(resource.getInputStream());

        } catch (IOException e) {

            // Print the stact trace
            e.printStackTrace();

            // Return null
            return null;
        }

        String uploadRoot = "";

        if(uploadType == FileUploadPath.UPLOAD_ROOT){

            // Get the upload root
            uploadRoot = props.get("UPLOAD_ROOT").toString();

        } else {

            // Get the upload root
            uploadRoot = props.get("FILE_UPLOAD_ROOT").toString();

        }

        // Return the uploadRoot
        return uploadRoot;


    }



}
