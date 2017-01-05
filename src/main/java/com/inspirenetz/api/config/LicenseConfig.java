package com.inspirenetz.api.config;

import com.inspirenetz.api.core.license.LicenseManager;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

import java.io.IOException;
import java.security.GeneralSecurityException;

/**
 * Created with IntelliJ IDEA.
 * User: SAJEEVkhan
 * Date: 15/2/14
 * Time: 1:43 PM
 * To change this template use File | Settings | File Templates.
 */
@Configuration
@PropertySource(value = "classpath:license.properties")
public class LicenseConfig {
    @Value("${license.publicKey}")
    private String publicKey;

    @Value("${license.privateKey}")
    private String privateKey;

    @Value("${license.licenseFile}")
    private String licenseFile;

    @Bean
    public LicenseManager licenseManager(){
        LicenseManager licenseManager = null;
        try {
                licenseManager = new LicenseManager(publicKey, privateKey);
        }
        catch (GeneralSecurityException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }
        catch (IOException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }
        return licenseManager;
    }
}
