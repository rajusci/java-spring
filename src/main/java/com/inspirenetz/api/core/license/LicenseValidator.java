package com.inspirenetz.api.core.license;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.web.context.support.SpringBeanAutowiringSupport;

import java.io.File;
import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.Date;

/**
 * Created by sandheepgr on 9/9/14.
 */
public class LicenseValidator extends SpringBeanAutowiringSupport implements InitializingBean {

    private static final Logger log = LoggerFactory.getLogger(LicenseValidator.class);

    @Value("${license.publicKey}")
    private String publicKey;

    @Value("${license.privateKey}")
    private String privateKey;

    @Value("${license.licenseFile}")
    private String licenseFile;


    @Autowired
    private ApplicationContext applicationContext;


    @Override
    public void afterPropertiesSet() throws Exception {

        /*// Flag showing whether the license is valid
        boolean isValid = true;

        // Set the LicenseManager to null
        LicenseManager licenseManager = null;

        try {

            // Get the LicenseManager instance
            licenseManager = new LicenseManager(publicKey, privateKey);

            // Get the License instance
            License license = (License) licenseManager.readLicenseFile(new File(licenseFile));

            // Validate the license
            license.validate(new Date(),"2.0");

        }
        catch (GeneralSecurityException e) {

            // Print stack trace
            e.printStackTrace();

            // Set the flag to false
            isValid = false;

        }
        catch (IOException e) {

            // Print stack trace
            e.printStackTrace();

            // Set the flag to false
            isValid = false;

        }
        catch (ClassNotFoundException e) {

            // Print stack trace
            e.printStackTrace();

            // Set the flag to false
            isValid = false;

        }
        catch(LicenseExpiredException e) {

            // Print stack trace
            e.printStackTrace();

            // Set the flag to false
            isValid = false;

        }
        catch(LicenseVersionExpiredException e) {

            // Print stack trace
            e.printStackTrace();

            // Set the flag to false
            isValid = false;

        }


        // Check if the isValid flag and if its false, then exit
        if ( !isValid ) {

            // Log the information
            log.error("LicenseValidator -> License is not valid, exiting application");

            // Register the shutdown hook on the application context
            ConfigurableApplicationContext ctx = (ConfigurableApplicationContext) applicationContext;

            // Exit the system
            System.exit(1);

            // Register a shutdown hook for the context to bring down the instance
            ctx.registerShutdownHook();

        }*/
    }

}
