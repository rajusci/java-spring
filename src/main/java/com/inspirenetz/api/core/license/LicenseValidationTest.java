package com.inspirenetz.api.core.license;

import java.io.File;
import java.io.IOException;
import java.security.GeneralSecurityException;

/**
 * @author FFL
 */
public class LicenseValidationTest {
    public static void main(String [] args) throws IOException, GeneralSecurityException, ClassNotFoundException {

        LicenseManager licenseManager = new LicenseManager("/home/sandheepgr/auth/public_key.der", "/home/sandheepgr/auth/private_key.der");
        License license = (License) licenseManager.readLicenseFile(new File("/home/sandheepgr/auth/testkey.ipl"));
        System.out.println(license.toString());

    }
}
