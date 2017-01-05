package com.inspirenetz.api.util.integration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Properties;

/**
 * Created by sandheepgr on 27/4/14.
 */
@Component
public class IntegrationUtils {

    @Autowired
    private static  Environment environment;


    public static final String INTEGRATION_FILES_PATH = "/var/www/inspirenetz/upload/integration";



    /**
     * Function to get the integration file name for the given merhcant informaiton
     * This function will build the filename and check if the parent folders exists.
     * if the folder does not exist, then the function will create the folders.
     *
     * @param merchantNo        - The merchant number of the merchant
     * @param location          - The location of the merchant user
     * @param prefix            - The prefix for the filename (salesmaster,itemmaster)
     * @param extension         - The extension for the filename
     *
     * @return - The file path with the filename
     */
    public  String getIntegrationFileName(Long merchantNo,Long location, String prefix, String extension ) {


        //Integration file upload root
      //  String integrationFileUploadRoot=environment.getProperty("INTEGRATION_FILE_UPLOAD_ROOT");

        String integrationFilePath = getIntegrationFileUploadPath();

        // Build the filename
        String filename =integrationFilePath + "/integration/" +merchantNo.toString() + "/" + location.toString() + "/";

        // Create the SimpleDataformat
        SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMddHHmmss");

        // Create the data object with the current time
        Date date = new Date(System.currentTimeMillis());

        // Get the timestamp suffix
        String timestampSuffix = formatter.format(date);

        // Appdend the filename with the prefix and the timestampsuffic
        filename += prefix + "_" + timestampSuffix;

        // Set the extension for the filename
        filename += "."+extension;

        // Create the file and see if the file path exists
        File file = new File(filename);

        // Get the parent path
        File parentFile = file.getParentFile();

        // If the parent does not exist, then we need to create it
        if ( !parentFile.exists() ) {

            //create parent directory
            parentFile.mkdirs();

        }

        // Return the filename;
        return filename;

    }

    private String getIntegrationFileUploadPath() {

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

        return props.getProperty("INTEGRATION_FILE_UPLOAD_ROOT");


    }

}
