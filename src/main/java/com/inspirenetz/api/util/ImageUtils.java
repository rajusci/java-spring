package com.inspirenetz.api.util;

import org.springframework.core.io.ClassPathResource;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * Created by sandheepgr on 13/5/14.
 */
public class ImageUtils {

    /**
     * Function to get the Properties object for the images.properties file
     *
     * @return - The properties object with the keys and values on success
     *           If an IOException happens, then function will return emtpy object
     *
     */
    public static Properties getImageProperites() {

        // Create the Properties object
        Properties prop = new Properties();

        // Load the images.properites file
        ClassPathResource resource = new ClassPathResource("images.properties");

        // Get the input stream
        InputStream input = null;
        try {

            // Try getting the input stream
            input = resource.getInputStream();

            // Load the properties
            prop.load(input);

        } catch (IOException e) {

            e.printStackTrace();

        }

        // Return the properties
        return prop;

    }


    /**
     * Function to get a particular property from the images.properties file
     * Here the function will read the particular property from the file
     *
     * @param key   - The key for the entry to be read
     * @return      - Return the value for the key on succes,
     *                Return empty string as the default value if nothing is matching
     */
    public static String getImageProperty(String key) {

        // Read the properties
        Properties prop = getImageProperites();

        // REturn the value
        return prop.getProperty(key,"");

    }

}
