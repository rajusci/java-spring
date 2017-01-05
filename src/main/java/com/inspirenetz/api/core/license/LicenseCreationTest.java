package com.inspirenetz.api.core.license;

import java.io.File;
import java.io.IOException;
import java.security.GeneralSecurityException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author FFL
 */
public class LicenseCreationTest {
    public static void main(String [] args) throws IOException, GeneralSecurityException, ParseException {
        License license = new License();
        license.setName("InspirePro: CustomerInspire Pte Limited");
        license.setEmail("info@customerinspire.com");

        String input = "28-02-2020";
        DateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
        Date expiryDate = dateFormat.parse(input);

        license.setExpiration(expiryDate);
        license.setLicenseNumber("IP|1.0|2014|001");
        license.setVersion("1.0");
        license.setLicenseType(License.TYPE_TRIAL);
        license.setNoOfConcurrentUsers(10);

        LicenseManager licenseManager = new LicenseManager("/home/sandheepgr/auth/public_key.der", "/home/sandheepgr/auth/private_key.der");
        licenseManager.writeLicense(license, new File("/home/sandheepgr/auth/testkey.ipl"));

    }
}